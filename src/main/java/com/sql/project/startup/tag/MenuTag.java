package com.sql.project.startup.tag;

import com.opensymphony.oscache.util.StringUtil;
import com.sql.project.startup.common.Constant;
import com.sql.project.startup.common.SessionInfo;
import com.sql.project.startup.entity.Menu;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class MenuTag extends TagSupport {
	private static Logger logger = Logger.getLogger(MenuTag.class);

	private static final long serialVersionUID = -7586286213014517034L;
	private String id;
	private String cssClass;
	private String childClass;

	private String path;

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		path = request.getContextPath();
		StringBuffer sb = new StringBuffer();
		
		sb.append("<div");
		if (!StringUtil.isEmpty(id)) {
			sb.append(" id=\"" + id + "\" ");
		}
		if (!StringUtil.isEmpty(cssClass)) {
			sb.append(" class=\"" + cssClass + "\"");
		}
		sb.append(">");

		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(Constant.KEY_SESSION_INFO);
		if(null!=sessionInfo&&
				null!=sessionInfo.getLoginUser()){
			List<Menu> menus = sessionInfo.getLoginUser().getMenuList();
			for (Menu m : menus) {
				prepareView(m, sb);
			}
		}

		sb.append("</div>");
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

	private void prepareView(Menu m, StringBuffer sb) {
		
		if(null!=m.getSubMenus()&&
				!m.getSubMenus().isEmpty()){
			sb.append("<a href=\"#").append(m.getMenuId())
			.append("\" class=\"nav-header\" data-toggle=\"collapse\"><i class=\"icon-download-alt\"></i>")
			.append(m.getMenuName())
			.append("</a>");
			
			sb.append("<ul id=\"")
			.append(m.getMenuId())
			.append("\" class=\"nav nav-list in collapse\">");
			for(Menu subMenu:m.getSubMenus()){
				sb.append("<li><a href=\"")
				.append(path)
				.append(subMenu.getUrl())
				.append("\">")
				.append(subMenu.getMenuName())
				.append("</a></li>");
			}
			sb.append("</ul>");
		}
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

	public String getChildClass() {
		return childClass;
	}

	public void setChildClass(String childClass) {
		this.childClass = childClass;
	}

}
