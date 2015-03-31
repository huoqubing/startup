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
	baseUrl = 'pushRecord';
	$().ready(function() {
		$('#type-dropdown-menu li a').click(function (e) {
		    var newHeading = $(this).text();
		    var newType = e.currentTarget['id'];
		    var $heading = $('#receivertypeValue');
		    $("#receivertype").val(newType.substring(13));
		    $heading.html(newHeading + ' ');
		});
		var typeValue = $("#receivertype").val();
		if(null!=typeValue&&typeValue.length>0){
			$('#receivertype_'+typeValue).get(0).click();
		}else{
			$('#receivertype_-1').get(0).click();
		}
	});
</script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/menu.jsp"%>

	<div class="content">
		<div class="header">
			<h3>推送列表</h3>
			<%@ include file="../common/message.jsp"%>
		</div>

		<div class="alert alert-info form-search">
			<form:form id="filter_form" action="list" cssStyle="margin:0px"
				method="post" modelAttribute="dto">
				<fieldset>
					<label>类型:</label>
					<div class="btn-group">
						<button class="btn btn-warning dropdown-toggle" data-toggle="dropdown">
							<span id="receivertypeValue"></span>
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" id="type-dropdown-menu">
							<li><a href="#" id='receivertype_-1'>全部</a></li>
							<li><a href="#" id='receivertype_2'>指定TAG</a></li>
							<li><a href="#" id='receivertype_3'>指定用户</a></li>
							<li><a href="#" id='receivertype_4'>全部发送</a></li>
						</ul>
					</div>
					<form:input type="hidden" id="receivertype" path="receivertype"/>
					<label>内容:</label>
					<form:input cssClass="input-medium search-query" type="text"
						path="content" />
					<button class="btn" type="submit">查找</button>
				</fieldset>
			</form:form>
		</div>
		<form id="list_form" action="del" method="post">
			<table class="table table-bordered" id="list_table">
				<thead>
					<tr style="background-color: #E8EAEB">
						<th><input type="checkbox" /></th>
						<th>类型</th>
						<!-- 
						<th>标题</th>
						 -->
						<th>内容</th>
						<th>接收者</th>
						<th>状态</th>
						<th>描述</th>
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
								<c:if test="${item.receivertype =='2' }">
									<c:out value="指定TAG" />
								</c:if>
								<c:if test="${item.receivertype =='3' }">
									<c:out value="指定用户" />
								</c:if>
								<c:if test="${item.receivertype =='4' }">
									<c:out value="全部发送" />
								</c:if>
								
							</td>
							<!-- 
							<td>${item.title }</td>
							 -->
							<td>${item.content }</td>
							<td>${item.receivervalue }</td>
							<td>${item.status }</td>
							<td>${item.description }</td>
							<td><fmt:formatDate value='${item.createtime}'
									pattern='yyyy-MM-dd HH:mm:ss' /></td>
									
							<td style="width: 130px">
								<console:input funcnum="p_pushRecord_del" type="button"
									cssClass="btn btn-warning" value="删除"
									onclick="removeItem('${item.id}')"></console:input>
							</td>
							<!-- 
							<td style="width: 130px"><console:input
									funcnum="p_pushRecord_edit" type="button" cssClass="btn btn-info"
									value="修改" onclick="editItem('${item.id}')"></console:input>
								&nbsp; <console:input funcnum="p_pushRecord_del" type="button"
									cssClass="btn btn-warning" value="删除"
									onclick="removeItem('${item.id}')"></console:input></td>
							 -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<console:input funcnum="p_pushRecord_add" cssClass="btn btn-primary"
				style="float: left;" type="button" onclick="goAdd()" value="推送消息"></console:input>
			&nbsp;&nbsp;
			<console:input funcnum="p_pushRecord_del" cssClass="btn btn-warning"
				type="button" onclick="removeItems()" value="删除所选"></console:input>
		</form>
		<console:pagination resultKey="list" javascript="void(0)"
			onclick="doPagnation(this)"></console:pagination>
	</div>
</body>
</html>


