/**
 * 
 */
package com.sql.project.startup.service.impl;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.sql.project.startup.service.FileService;
import com.sql.project.startup.util.UUIDGenerator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * TODO
 * 
 * @author SQL
 * 
 *         2014-1-1 下午1:49:02
 */
@Service
public class FileServiceImpl implements FileService {
	private static final Logger logger = Logger
			.getLogger(FileServiceImpl.class);

	@Value("${imgBaseDownUrl}")
	private String imgBaseDownUrl;
	@Value("${fileBaseDownUrl}")
	private String fileBaseDownUrl;
	@Value("${imgBucketName}")
	private String imgBucketName;
	@Value("${fileBucketName}")
	private String fileBucketName;
	@Value("${defaultQuality}")
	private String defaultQuality;
	@Value("${accessKey}")
	private String accessKey;
	@Value("${secrteKey}")
	private String secrteKey;
	@Value("${imageFileWidth}")
	private int imageFileWidth;
	@Value("${imageFileHeight}")
	private int imageFileHeight;

	public String[] upload(File file, String keyPath, String type,
			boolean compress) {
		String[] result = new String[5];
		String filePath = null;
		if (type.startsWith("image")
				&& !StringUtils.startsWith(keyPath, "commercial") && compress) {
			filePath = cropImage(file, defaultQuality);
			if (StringUtils.isNotBlank(filePath)) {
				file = new File(filePath);
			}
		}
		String md5 = file.getName();
		try {
			md5 = DigestUtils.md5Hex(FileUtils.openInputStream(file));
		} catch (Exception e) {
			logger.warn("generate md5 failed,file name:" + file.getName());
		}

		String url = save(file, keyPath, type, md5);
		if (StringUtils.isNotBlank(url)) {
			result[0] = "0";
			result[1] = md5;
			result[2] = url;
			if(StringUtils.startsWith(keyPath, "travelNote") &&
					null!=file){
				BufferedImage buff;
				try {
					buff = ImageIO.read(file);
					int sw = buff.getWidth();
					int sh = buff.getHeight();
					result[3]=sh+"";
					result[4]=sw+"";
				} catch (IOException e) {
					//ignore
				}
			}
		} else {
			result[0] = "1";
		}
		// 如果压缩，删除本地emp文件
		if (compress && StringUtils.isNotBlank(filePath)) {
			try {
				if (file.exists()) {
					file.delete();
				}
			} catch (Exception e) {
				// ignore
			}
		}
		return result;
	}

	public String cropImage(File file, String quality) {
		String result = "";
		try {
			BufferedImage buff = ImageIO.read(file);

			int sw = buff.getWidth();
			int sh = buff.getHeight();

			if (sw <= 0 || sh <= 0 || imageFileWidth <= 0 || imageFileHeight <= 0)
				return result;

			IMOperation op = new IMOperation();
			op.addImage();

			if ((sw <= imageFileWidth) && (sh <= imageFileHeight)) // 如果源图宽度和高度都小于目标宽高，则仅仅压缩图片
			{
				op.resize(sw, sh);
			}

			if ((sw <= imageFileWidth) && (sh > imageFileHeight))// 如果源图宽度小于目标宽度，并且源图高度大于目标高度
			{
				op.resize(sw, sh); // 压缩图片
				op.append().crop(sw, imageFileHeight, 0, (sh - imageFileHeight) / 2);// 切割图片
			}

			if ((sw > imageFileWidth) && (sh <= imageFileHeight))// 如果源宽度大于目标宽度，并且源高度小于目标高度
			{
				op.resize(sw, sh);
				op.append().crop(imageFileWidth, sh, (sw - imageFileWidth) / 2, 0);
			}

			if (sw > imageFileWidth && sh > imageFileHeight) // 如果源图宽、高都大于目标宽高
			{
				float ratiow = (float) imageFileWidth / sw; // 宽度压缩比
				float ratioh = (float) imageFileHeight / sh; // 高度压缩比

				if (ratiow >= ratioh) // 宽度压缩比小（等）于高度压缩比（是宽小于高的图片）
				{
					int ch = (int) (ratiow * sh); // 压缩后的图片高度

					op.resize(imageFileWidth, null); // 按目标宽度压缩图片
					op.append()
							.crop(imageFileWidth, imageFileHeight, 0, (ch > imageFileHeight) ? ((ch - imageFileHeight) / 2) : 0); // 根据高度居中切割压缩后的图片
				} else // （宽大于高的图片）
				{
					int cw = (int) (ratioh * sw); // 压缩后的图片宽度

					op.resize(cw, null); // 按计算的宽度进行压缩
					op.append()
							.crop(imageFileWidth, imageFileHeight, (cw > imageFileWidth) ? ((cw - imageFileWidth) / 2) : 0, 0); // 根据宽度居中切割压缩后的图片

				}
			}

			op.addRawArgs("-quality", quality);
			op.addImage();

			ConvertCmd cmd = new ConvertCmd();

			// 系统类型
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.indexOf("win") != -1) {
				cmd = new ConvertCmd(true);
			}
			if (osName.indexOf("win") != -1) {
				// linux下不要设置此值，不然会报错
				cmd.setSearchPath("C:/Program Files/GraphicsMagick-1.3.19-Q8");
			}

			// 新文件路径
			result = FileUtils.getTempDirectoryPath() + UUIDGenerator.getUUID()
					+ "." + getSuffix(file.getName());
			// 压缩
			cmd.run(op, file.getAbsolutePath(), result);
		} catch (Exception e) {
			logger.warn("compress pic failed:" + file.getName(), e);
			result = "";
		}
		return result;

	}

	private String save(File file, String keyPath, String type, String md5) {
		String url = "";
		try {
			Mac mac = new Mac(accessKey, secrteKey);
			String bucketName = imgBucketName;
			String baseDownUrl = imgBaseDownUrl;
			String key = md5 + "." + getSuffix(file.getName());
			if (StringUtils.isNotBlank(type) && "file".equals(type)) {
				bucketName = fileBucketName;
				baseDownUrl = fileBaseDownUrl;
				key = file.getName();
			}

			PutPolicy putPolicy = new PutPolicy(bucketName);
			String uptoken = putPolicy.token(mac);

			PutExtra extra = new PutExtra();
			if (!StringUtils.endsWith(keyPath, "/")) {
				keyPath += "/";
			}
			if (StringUtils.startsWith(keyPath, "/")) {
				keyPath = StringUtils.substringAfter(keyPath, "/");
			}
			key = keyPath + key;

			PutRet ret = IoApi.putFile(uptoken, key, file, extra);
			url = baseDownUrl + key;
			logger.info("save file," + file.getName() + ". download url:" + url);
		} catch (Exception e) {
			logger.warn("save file failed." + file.getName());
		}
		return url;
	}

	public String getSuffix(String imageName) {
		if (StringUtils.isBlank(imageName)) {
			return "";
		}
		return StringUtils.substringAfterLast(imageName, ".");
	}

}
