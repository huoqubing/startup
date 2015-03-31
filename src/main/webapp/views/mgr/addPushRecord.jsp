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
var BASE_URL = '<%=path %>';
</script>
</head>
<script language="javascript">
	function validate() {
		var pass = true;
		var tags = "";
		$('input[type=checkbox]:checked').each(function (e) {
			if(tags.length>0){
				tags+=",";
			}
			tags+=$(this).val();
		});
		if($("#receivertype").val()==2){
			$("#receivervalue").val(tags);
		}
		
		return pass;
	}

	function checkIsExist(pass) {
		return pass;
	}
	$().ready(function() {
		$('#parent-id-dropdown-menu li a').click(function (e) {
		    var newHeading = $(this).text();
		    var newType = e.currentTarget['id'];
		    var $heading = $('#receivertypeValue');
		    $("#receivertype").val(newType);
		    $heading.html(newHeading + ' ');
		    if(newType == 2){
		    	$("#receiverValue").val();
		    	$("#receiverValue").hide();
				$("#checkBoxTags").show();
		    }else if(newType==3){
		    	$("#checkBoxTags").hide();
		    	$("#receiverValue").val();
		    	$("#receiverValue").show();
		    }else if(newType==4){
		    	$("#receiverValue").hide();
		    	$("#receiverValue").val();
		    }
		});
		$('#4').get(0).click();
		$("#receiverValue").hide();
		$("#checkBoxTags").hide();

	});
</script>

<body>

	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/menu.jsp"%>
	<div class="content">
		<div class="header">
			<h3>列表</h3>
		</div>
		<br />
		<div>
			<form:form id="config_form"
				class="form-horizontal form-newapp hero-unit" method="post"
				action="save" modelAttribute="pushRecord">
				<fieldset>
					<div id="legend">
						<legend>新建</legend>
					</div>
					<div class="control-group">
						<!-- Text input-->
						<label class="control-label" for="input01">发送方式</label>
						<div class="controls">
							<div class="btn-group">
								<button class="btn btn-warning dropdown-toggle" data-toggle="dropdown">
									<span id="receivertypeValue"></span>
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" id="parent-id-dropdown-menu">
									<li><a href="#" id="2">指定TAG</a></li>
									<li><a href="#" id="3">指定用户</a></li>
									<li><a href="#" id="4">全部发送</a></li>
								</ul>
							</div>
							<form:input type="hidden" id="receivertype" path="receivertype" value="4" />
							<form:errors path="receivertype" cssClass="validate-error"></form:errors>
						</div>
					</div>
					<div class="control-group" id="checkBoxTags" style="display: none">
						<label class="control-label" for="input01">发送范围</label>
						<div class="controls">
							<c:forEach items="${tagList}" var="item" varStatus="sn">
								<label class="checkbox inline">
				             		<input type="checkbox" value="${item.tag }">${item.title }
				            	</label>
							</c:forEach>
						</div>
					</div>
					<div class="control-group" id="receiverValue" style="display: none">
						<label class="control-label" for="input01">发送范围</label>
						<div class="controls">
							<form:input type="text" id="receivervalue" path="receivervalue" />
							<form:errors path="receivervalue" cssClass="validate-error"></form:errors>
						</div>
					</div>
					
					<!-- 
					<div class="control-group">
						<label class="control-label" for="input01">标题</label>
						<div class="controls">
							<form:input type="text" id="title" path="title" />
							<form:errors path="title" cssClass="validate-error"></form:errors>
						</div>
					</div>
					 -->
					<div class="control-group">
						<label class="control-label" for="input01">内容</label>
						<div class="controls">
							<form:textarea type="text" id="content" path="content" />
							<form:errors path="content" cssClass="validate-error"></form:errors>
						</div>
					</div>	
					<div class="control-group">
						<label class="control-label" for="input01">描述</label>
						<div class="controls">
							<form:textarea id="description" path="description" />
							<form:errors path="description" cssClass="validate-error"></form:errors>
						</div>
					</div>					

					<div class="control-group">
						<label class="control-label"></label>

						<!-- Button -->
						<div class="controls">
							<console:input funcnum="p_pushRecord_add" type="button"
								cssClass="btn btn-primary" value="发送" onclick="doSave();"></console:input>
							<input type="button" class="btn btn-danger" value="取消"
								onclick="doCancel();" />
						</div>
					</div>

				</fieldset>
			</form:form>
		</div>
	</div>
</body>
</html>


