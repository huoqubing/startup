package com.sql.project.startup.tag;

import com.opensymphony.oscache.util.StringUtil;
import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.util.PageUtil;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 * @ClassName:     PagenationTag.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-8-1 下午9:54:07 
 */
public class PagenationTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger =Logger.getLogger(PagenationTag.class);

	private String resultKey;
	private int pageNo;
	private int pageSize;
	private int totalRecords=0;
	private String url;
	private String style;
	private String javascript;
	private String onclick;
	private String param;

	@Override
	public int doStartTag() throws JspException {
/*		int totalRecords=FormatUtil.parseInt(this.totalRecords);
		int pageNo=FormatUtil.parseInt(this.pageNo);
		int pageSize=FormatUtil.parseInt(this.pageSize);*/
		if(!StringUtil.isEmpty(resultKey)){
			PageBean bean= getResult();
			if(bean!=null){
				totalRecords=bean.getTotalRows();
				pageNo=bean.getCurrentPage();
				pageSize=bean.getPageSize();
				
				if(pageNo<1) pageNo=1;
				if(pageSize<1) pageSize=10;
				
			}
		}
		
		if(totalRecords>0){
			PageUtil pu=new  PageUtil(totalRecords,pageNo,pageSize,url,param,javascript,onclick,style);
			
			StringBuffer sb=new StringBuffer();
			sb.append(pu.descriptPageLink());

			try {
				pageContext.getOut().println(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
				logger.debug("Encounter an exception when parse tag."+e.getMessage());
			}
		
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	
	private PageBean getResult(){
		PageBean result=(PageBean) this.pageContext.getAttribute(resultKey);
		if(result==null){
			result=(PageBean) this.pageContext.getRequest().getAttribute(resultKey);
		}
		return result;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
}
