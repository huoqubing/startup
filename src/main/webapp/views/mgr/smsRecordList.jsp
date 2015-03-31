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
	baseUrl = 'smsRecord';
	$().ready(function() {
	});
</script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/menu.jsp"%>

	<div class="content">
		<div class="header">
			<h3>列表</h3>
			<%@ include file="../common/message.jsp"%>
		</div>

		<div class="alert alert-info form-search">
			<form:form id="filter_form" action="list" cssStyle="margin:0px"
				method="post" modelAttribute="dto">
				<fieldset>
					<label>电话号码:</label>
					<form:input cssClass="input-medium search-query" type="text"
						path="phone" />
					<button class="btn" type="submit">查找</button>
				</fieldset>
			</form:form>
		</div>
		<form id="list_form" action="del" method="post">
			<table class="table table-bordered" id="list_table">
				<thead>
					<tr style="background-color: #E8EAEB">
						<th><input type="checkbox" /></th>
						<th>手机号码</th>
						<th>验证码</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list.result }" var="item" varStatus="sn">
						<tr>
							<td><input type="checkbox" name="selected_item"
								value="<c:out value='${item.id }' />" /></td>
							<td>
								${item.phone }
							</td>
							<td>${item.code }</td>
							<td><fmt:formatDate value='${item.createtime}'
									pattern='yyyy-MM-dd HH:mm:ss' /></td>
									
							<td style="width: 130px">
							<console:input funcnum="p_smsRecord_del" type="button"
									cssClass="btn btn-warning" value="删除"
									onclick="removeItem('${item.id}')"></console:input></td>
							<!-- 
							<td style="width: 130px">
							<console:input
									funcnum="p_smsRecord_edit" type="button" cssClass="btn btn-info"
									value="修改" onclick="editItem('${item.id}')"></console:input>
								&nbsp; <console:input funcnum="p_smsRecord_del" type="button"
									cssClass="btn btn-warning" value="删除"
									onclick="removeItem('${item.id}')"></console:input></td>
							 -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<console:input funcnum="p_smsRecord_del" cssClass="btn btn-warning"
				type="button" onclick="removeItems()" value="删除所选"></console:input>
		</form>
		<console:pagination resultKey="list" javascript="void(0)"
			onclick="doPagnation(this)"></console:pagination>
	</div>
</body>
</html>


