package com.sql.project.startup.tag;

import com.sql.project.startup.common.Constant;
import com.sql.project.startup.common.SessionInfo;
import com.sql.project.startup.entity.Privilege;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class InputTag extends TagSupport {
	private static Logger logger = Logger.getLogger(InputTag.class);
	private static final long serialVersionUID = -7586286213014517034L;
	private String id;
	private String funcnum;
	private String cssClass;
	private String style;
	private String type;
	private String value;
	private String onclick;

	@Override
	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(Constant.KEY_SESSION_INFO);
		if (null!=sessionInfo&&
				null!=sessionInfo.getLoginUser()) {
			List<Privilege> privilegeList = sessionInfo.getLoginUser().getPrivilegeList();
			for (Privilege privilege : privilegeList) {
				if (privilege.getPrivilegeId().equals(funcnum)) {
						sb.append("<input ");
						if (cssClass != null && !cssClass.equals("")) {
							sb.append(" class=\"" + cssClass + "\"");
						}
						if (style != null && !style.equals("")) {
							sb.append(" style=\"" + style + "\"");
						}
						if (type != null && !type.equals("")) {
							sb.append(" type=\"" + type + "\"");
						}
						if (id != null && !id.equals("")) {
							sb.append(" id=\"" + id + "\"");
						}
						if (value != null && !value.equals("")) {
							sb.append(" value=\"" + value + "\"");
						}
						if (onclick != null && !onclick.equals("")) {
							sb.append(" onclick=\"" + onclick + "\"");
						}
						sb.append("</input>");
						break;
					}
			}
		}
		try {
			pageContext.getOut().println(sb.toString());
		} catch (IOException e) {
			logger.warn("IOException", e);
		}

		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	/**
	 * @return the onclick
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * @param onclick
	 *            the onclick to set
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * @return the funcnum
	 */
	public String getFuncnum() {
		return funcnum;
	}

	/**
	 * @param funcnum
	 *            the funcnum to set
	 */
	public void setFuncnum(String funcnum) {
		this.funcnum = funcnum;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
