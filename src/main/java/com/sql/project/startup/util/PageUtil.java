package com.sql.project.startup.util;

import com.opensymphony.oscache.util.StringUtil;


/**
 * @ClassName:     PageUtil.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-8-1 下午9:55:42 
 */
public class PageUtil {
	
	private int totalRecords;
	private int currentPage;
	private int pageSize;
	private String url="";
	private String params="";
	private String javascript;
	private String onclick;
	private String jsPrefix="javascript:";
	private String style="pagination";

	public PageUtil(int totalRecords, int currentPage,int pageSize,  String url) {
		super();
		this.totalRecords = totalRecords;
		this.currentPage = currentPage;
		this.pageSize=pageSize;
		this.url = url;
	}

	public PageUtil(int totalRecords, int currentPage,int pageSize ,String url,
			String params, String javascript, String style) {
		super();
		this.totalRecords = totalRecords;
		this.currentPage = currentPage;
		this.url = url;
		this.params = params;
		this.pageSize=pageSize;
		this.javascript = javascript;
		if(!StringUtil.isEmpty(style)){
			this.style=style;
		}
	}
	
	public PageUtil(int totalRecords, int currentPage,int pageSize ,String url,
			String params, String javascript,String onclick, String style) {
		super();
		this.totalRecords = totalRecords;
		this.currentPage = currentPage;
		this.url = url;
		this.params = params;
		this.pageSize=pageSize;
		this.javascript = javascript;
		this.onclick=onclick;
		if(!StringUtil.isEmpty(style)){
			this.style=style;
		}
	}

	private int indicateTotalPages() {
		int totalPage = this.totalRecords / this.pageSize;
		if (this.totalRecords % this.pageSize != 0) {
		    totalPage++;
		}
		return totalPage;

	}

	public String descriptPageLink(){
		
		/*
		 * <div class="pagination">
					<ul>
						<li><a href="#">首页</a></li>
						<li><a href="#">上一页</a></li>
						<li><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">下一页</a></li>
						<li><a href="#">尾页</a></li>
					</ul>
				</div>
		 * 
		 * */
		
		StringBuffer sb=new StringBuffer();
		sb.append("<div class=\""+this.style+"\" style=\"float:right;\" >");
		sb.append("<ul>");
		if(currentPage==1){
			sb.append("<li><a title=\"首页\""+">&laquo; 首页</a></li>");
			sb.append("<li><a title=\"上一页\""+">&laquo; 上一页</a></li>");
		}else{
			sb.append("<li><a "+descOnclick()+" title=\"1\" href=\""+descFirstUrl()+"\">&laquo; 首页</a></li>");
			sb.append("<li><a "+descOnclick()+" title=\""+(this.currentPage-1)+"\" href=\""+descPreviousUrl()+"\">&laquo; 上一页</a></li>");
		}
		/*if (this.currentPage >=4) {
		    sb.append("<li>&nbsp;...&nbsp;</li>");
		}*/
		int submitIndex;

		submitIndex = this.currentPage -2;
		if (submitIndex > 0) {
			sb.append("<li><a "+descOnclick()+" class=\"number\" title=\""+submitIndex+"\" href=\"").append(descUrl(submitIndex)).append("\">").append(submitIndex).append("</a></li>");
		}

		submitIndex = this.currentPage - 1;
		if (submitIndex > 0) {
			sb.append("<li><a "+descOnclick()+" class=\"number\" title=\""+submitIndex+"\" href=\"").append(descUrl(submitIndex)).append("\">").append(submitIndex).append("</a></li>");

		}

		sb.append("<li><a "+descOnclick()+" class=\"number\" style=\"background-color:#DDDDDD\" title=\""+this.currentPage+"\">"+this.currentPage+"</a></li>");

		submitIndex = this.currentPage + 1;
		if (submitIndex <= indicateTotalPages()) {
			sb.append("<li><a "+descOnclick()+" class=\"number\" title=\""+submitIndex+"\" href=\"").append(descUrl(submitIndex)).append("\">").append(submitIndex).append("</a></li>");

		}

		submitIndex = this.currentPage + 2;
		if (submitIndex <= indicateTotalPages()) {
			sb.append("<li><a "+descOnclick()+" class=\"number\" title=\""+submitIndex+"\" href=\"").append(descUrl(submitIndex)).append("\">").append(submitIndex).append("</a></li>");

		}

		if(this.currentPage<3){
			submitIndex = this.currentPage + 3;
			if (submitIndex <= indicateTotalPages()) {
				sb.append("<li><a "+descOnclick()+" class=\"number\" title=\""+submitIndex+"\" href=\"").append(descUrl(submitIndex)).append("\">").append(submitIndex).append("</a></li>");

			}
		}
		
		
		if(this.currentPage<=1){
			submitIndex = this.currentPage + 4;
			if (submitIndex <= indicateTotalPages()) {
				sb.append("<li><a "+descOnclick()+" class=\"number\" title=\""+submitIndex+"\" href=\"").append(descUrl(submitIndex)).append("\">").append(submitIndex).append("</a></li>");
	
			}
		}

		/*if (this.currentPage + 3<= indicateTotalPages()) {
		    sb.append("<li>&nbsp;...&nbsp;</li>");
		}*/
		
		
		if(this.currentPage== indicateTotalPages()){
			sb.append("<li><a title=\"下一页\""+">下一页 &raquo;</a></li>");
			sb.append("<li><a title=\"尾页\""+">尾页 &raquo;</a></li>");
			
		}else{
			sb.append("<li><a title=\""+(this.currentPage+1)+"\" "+descOnclick()+" href=\""+descNextUrl()+"\" >下一页 &raquo;</a></li>");
			sb.append("<li><a title=\""+indicateTotalPages()+"\" "+descOnclick()+" href=\""+descLastUrl()+"\" >尾页 &raquo;</a></li>");
		}
		sb.append("</ul>");
		sb.append("</div>");
		return sb.toString();
	}
	
	private String descOnclick(){
		if(onclick==null || onclick.trim().length()==0) return "";
		String s="onclick=\""+onclick+"\"";
		
		return s;
	}

	private String descFirstUrl(){
		return descUrl(1);
	}
	
	private String descPreviousUrl(){
		int page=this.currentPage-1;
		if(page<=0){
			page=1;
		}
		return descUrl(page);
	}

	private String descNextUrl(){
		int nextPage = this.currentPage+1;
		if(nextPage>indicateTotalPages()){
			nextPage=indicateTotalPages();
		}
		return descUrl(nextPage);
				
	}
	
	private String descLastUrl(){
		return descUrl(indicateTotalPages());
	}
	
	private String descUrl(int page){
		if (javascript != null && javascript.trim().length() > 0) {
			//String url = javaScript + "(" + pageIndex + ")";
		    String url = jsPrefix+javascript;
		    return url;
		} else {
		    String url = this.url + "?pageNo=" + page;
		    if (params != null && params.trim().length() > 0) {
			url += "&" + params;
		    }
		    return url;
		}
	}
	
	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	public String getJsPrefix() {
		return jsPrefix;
	}

	public void setJsPrefix(String jsPrefix) {
		this.jsPrefix = jsPrefix;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
}
