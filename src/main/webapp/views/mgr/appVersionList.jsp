<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../common/include.jsp"%>
<script langeuage="javascript">
	baseUrl = 'appVersion';
</script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/menu.jsp"%>

	<div class="content">
		<div class="header">
			<h3>版本列表</h3>
			<%@ include file="../common/message.jsp"%>
		</div>

		<div class="alert alert-info form-search">
			<form:form id="filter_form" action="list" cssStyle="margin:0px"
				method="post" modelAttribute="dto">
				<fieldset>
					<label>版本号:</label>
					<form:input cssClass="input-medium search-query" type="text"
						path="version" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<button class="btn" type="submit">查找</button>
				</fieldset>
			</form:form>
		</div>
		<form id="list_form" action="del" method="post">
			<table class="table table-bordered" id="list_table">
				<thead>
					<tr style="background-color: #E8EAEB">
						<th><input type="checkbox" /></th>
						<!-- <th>类型</th> -->
						<th>版本号</th>
						<th style="width: 100px;">下载链接</th>
						<th style="width: 200px;">内容</th>
						<th>创建时间</th>
						<th>更新时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list.result }" var="item" varStatus="sn">
						<tr>
							<td><input type="checkbox" name="selected_item"
								value="<c:out value='${item.id }' />" /></td>
<%-- 							<td>
								<c:if test="${item.type =='1' }">
									<c:out value="IOS" />
								</c:if>
								<c:if test="${item.type =='0' }">
									<c:out value="ANDROID" />
								</c:if>
								
							</td> --%>
							<td><c:out value="${item.version }" /></td>
							<td title="${item.downloadUrl}">
								<c:if
									test="${fn:length(item.downloadUrl)>25}">
									<c:out value="${fn:substring(item.downloadUrl,0,25)}..." />
								</c:if> <c:if test="${fn:length(item.downloadUrl)<=25}">
									<c:out value="${item.downloadUrl}" />
								</c:if>
							</td>
							<td title="${item.content}">
								<c:if
									test="${fn:length(item.content)>25}">
									<c:out value="${fn:substring(item.content,0,25)}..." />
								</c:if> <c:if test="${fn:length(item.content)<=25}">
									<c:out value="${item.content}" />
								</c:if>
							</td>
							<td><fmt:formatDate value='${item.createTime}'
									pattern='yyyy-MM-dd HH:mm:ss' /></td>
							<td><fmt:formatDate value='${item.updateTime}'
									pattern='yyyy-MM-dd HH:mm:ss' /></td>
							<td style="width: 130px"><console:input
									funcnum="p_app_version_edit" type="button" cssClass="btn btn-info"
									value="修改" onclick="editItem('${item.id}')"></console:input>
								&nbsp; <console:input funcnum="p_app_version_del" type="button"
									cssClass="btn btn-warning" value="删除"
									onclick="removeItem('${item.id}')"></console:input></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<console:input funcnum="p_app_version_add" cssClass="btn btn-primary"
				style="float: left;" type="button" onclick="goAdd()" value="增加"></console:input>
			&nbsp;&nbsp;
			<console:input funcnum="p_app_version_del" cssClass="btn btn-warning"
				type="button" onclick="removeItems()" value="删除所选"></console:input>
		</form>
		<console:pagination resultKey="list" javascript="void(0)"
			onclick="doPagnation(this)"></console:pagination>
	</div>
</body>
</html>


