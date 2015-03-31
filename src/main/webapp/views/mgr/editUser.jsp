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
baseUrl = 'loginUser';
</script>
</head>

<script language="javascript">
$().ready(function() {
/* 	if($("#status").val()=="1"){
		$("input[name='t_status'][value='1']").attr("checked",true);
	}else if($("#status").val()=="2"){
		$("input[name='t_status'][value='2']").attr("checked",true);
	} */
	var roleIds = $("#roleIds").val().split("|");
    var si = $("#selectedItems");
	for(var i=0;i<roleIds.length;i++){
		var option = $("#role_"+roleIds[i]);
		option.remove();
		si.append(option);
	}
/* 	$("input[name='t_status']").bind('click',function(){
		if($(this).prop("checked")){
			$("#status").val($(this).val());
		}
	}); */
});
function doUpdate() {
	if(validate()){
		var rids="";
		$("#selectedItems").find("option").each(function(){
           	if(rids.length>0){
					rids+="|";
				}
				rids+=$(this).val();
        });
		$("#roleIds").val(rids);						
		$("#config_form").submit();
	}
}
function validate() {
	var pass = true;
	return pass;
}
function validatLoginName(val,pass){
	$.ajax({
		url:"checkLoginName",
		dataType:"json",
		type:"POST",
		async:false,
		data:{loginName:$(val).val()},
		success: function(data) {
			if(data.status=="0"){
				setmessage("userId", "用户编号不存在,无法修改");
				pass=false;
			}else{
				removemessage("userId");
			}
	    },
	    error:function(data){
	    	setmessage("userId", "用户编号唯一性验证异常");
	    	pass=false;
	    }
	});
	return pass;
}
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
			<form:form id="config_form" action="editItem"
				class="form-horizontal form-newapp hero-unit" method="post"
				modelAttribute="userDto">
				<input type="hidden" name="oldItemKey"
					value="<c:out value="${userDto.id }" />" />
				<fieldset>
					<div id="legend">
						<legend>修改</legend>
					</div>
					<form:input path="id" type="hidden"/>
					<div class="control-group">
						<!-- Text input-->
						<label class="control-label" for="input01">登陆名</label>
						<div class="controls">
							<form:input type="text" id="loginName" path="loginName"
								onblur="checkIsExist();" readonly="true"/>
							<form:errors path="loginName" cssClass="validate-error"></form:errors>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="input01">邮箱</label>
						<div class="controls">
							<form:input type="text" id="email" path="email"/>
							<form:errors path="email" cssClass="validate-error"></form:errors>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="input01">电话</label>
						<div class="controls">
							<form:input type="text" id="phone" path="phone"/>
							<form:errors path="phone" cssClass="validate-error"></form:errors>
						</div>
					</div>
<%-- 					<div class="control-group">
						<label class="control-label">状态</label>
						<div class="controls">
							<label class="radio"> 
							<input value="1" type="radio" name="t_status">激活
							</label> 
							<label class="radio">
								<input value="2" name="t_status" type="radio">禁用
							</label>
							<form:hidden path="status" id="status" /> 
							<form:errors path="status" cssClass="validate-error"></form:errors>
						</div>
					</div> --%>
					<form:hidden path="status" id="status" value="1"/> 
					<form:errors path="status" cssClass="validate-error"></form:errors>
					
					<div class="control-group">
						<label class="control-label">用户角色</label>
						<div class="controls">
							<div style="width:130px;float:left;">
								<select size="10" multiple name="availableItems" id="availableItems" style="width:120px;">
								  	<c:forEach items="${role}" var="role">
										<option id="role_<c:out value="${role.id}"/>" value="<c:out value="${role.id}"/>">
											<c:out value="${role.roleName}"/>
										</option> 
									</c:forEach>
								</select>
							</div>
							<div style="width:60px;float:left;">
								  <input type="button" style="width:50px" class="btn" value="&gt;" onclick="addOptionItem();" />
								  <input type="button" style="width:50px" class="btn" value="&gt;&gt;" onclick="addAllOption();" />
								  <input type="button" style="width:50px" class="btn" value="&lt;" onclick="removeOptionItem();" />
								  <input type="button" style="width:50px" class="btn" value="&lt;&lt;" onclick="removeAllOption();" />
							</div>
							<div style="width:130px;float:left">
								<select size="10" multiple="multiple" name="selectedItems" id="selectedItems" style="width:120px;">
								</select>
							</div>
							<form:hidden path="roleIds"  id="roleIds" /> 
							<form:errors path="roleIds" cssClass="validate-error">
							</form:errors>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"></label>

						<!-- Button -->
						<div class="controls">
							<console:input funcnum="p_login_user_edit" type="button"
								cssClass="btn btn-primary" value="保存" onclick="doUpdate();"></console:input>
							<input type="button" class="btn btn-danger" value="取消"
								onclick="doCancel();" />
						</div>
					</div>

				</fieldset>
			</form:form>
		</div>
	</div>

	<div class="row-fluid">
		<div class="span12">
			<%@ include file="../common/footer.jsp"%>
		</div>
	</div>


</body>
</html>


