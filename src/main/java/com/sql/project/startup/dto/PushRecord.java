package com.sql.project.startup.dto;

import com.sql.project.startup.spring.constraint.StringConstraint;

import java.util.Date;


/**
 * push 记录
 * @author SQL
 *  
 * 2014-2-11 下午8:46:21
 */
public class PushRecord extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 发送编号
	 */
	private int sendno;
	/**
	 * 接收者类型
	 *	2、指定的 tag。
	 *  3、指定的 alias。
     *	4、广播：对 app_key 下的所有用户推送消息
	 */
	private String receivertype;
	/**
	 * 发送范围值，与 receivertype 相对应
	 * 2、tag 支持多达 10 个，使用 "," 间隔
	 * 3、alias 支持多达 1000 个，使用 "," 间隔
	 * 4、不需要填
	 */
	private String receivervalue;
	/**
	 * 类型
	 * 1、通知
	 * 2、自定义消息（只有 Android 支持）
	 */
	private String type;
	
	/**
	 * 内容
	 */
	@StringConstraint(max=256)
	private String content;
	/**
	 * 描述
	 */
	@StringConstraint(max=128,isMandatory=false)
	private String description;
	/**
	 * 标题
	 * 只有 Android支持这个参考
	 */
	private String title;
	
	/**
	 * 额外参数
	 * 预留
	 */
	private String extras;
	/**
	 * 状态码
	 */
	private String statuscode;
	/**
	 * 状态描述
	 */
	private String status;
	/**
	 * 发送时间
	 */
	private Date createtime; 
	
	public PushRecord(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSendno() {
		return sendno;
	}

	public void setSendno(int sendno) {
		this.sendno = sendno;
	}

	public String getReceivertype() {
		return receivertype;
	}

	public void setReceivertype(String receivertype) {
		this.receivertype = receivertype;
	}

	public String getReceivervalue() {
		return receivervalue;
	}

	public void setReceivervalue(String receivervalue) {
		this.receivervalue = receivervalue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
