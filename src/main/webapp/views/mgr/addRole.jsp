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
baseUrl = 'role';
</script>
</head>
<script language="javascript">
function doSave() {
	if(validate()){
		var rids="";
		$("#selectedItems").find("option").each(function(){
           	if(rids.length>0){
					rids+="|";
				}
				rids+=$(this).val();
        });
		$("#privilegeIds").val(rids);
		$("#config_form").submit();
	}
}
function validate() {
	var pass = true;
	return pass;
}
function checkIsExist(pass) {
	$.ajax({
		url:"checkRoleId",
		dataType:"json",
		type:"POST",
		async:false,
		data:{loginId:$(val).val()},
		success: function(data) {
			if(data.status=="1"){
				setmessage("roleId", data.message);
				pass=false;
			}else{
				removemessage("roleId");
			}
	    },
	    error:function(data){
	    	setmessage("roleId", "角色名唯一性验证异常");
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
			<form:form id="config_form"
				class="form-horizontal form-newapp hero-unit" method="post"
				action="save" modelAttribute="roleDto">
				<fieldset>
					<div id="legend">
						<legend>新建</legend>
					</div>
					<div class="control-group">
						<!-- Text input-->
						<label class="control-label" for="input01">角色标识</label>
						<div class="controls">
							<form:input type="text" id="roleId" path="roleId"
								onblur="checkIsExist();" />
							<form:errors path="roleId" cssClass="validate-error"></form:errors>
						</div>
					</div>
					<div class="control-group">
						<!-- Text input-->
						<label class="control-label" for="input01">角色名</label>
						<div class="controls">
							<form:input type="text" id="roleName" path="roleName" />
							<form:errors path="roleName" cssClass="validate-error"></form:errors>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">权限</label>
						<div class="controls">
							<div style="width:130px;float:left;">
								<select size="10" multiple name="availableItems" id="availableItems" style="width:120px;">
								  	<c:forEach items="${privilege}" var="privilege">
										<option id="privilege_<c:out value="${privilege.id}"/>" value="<c:out value="${privilege.id}"/>">
											<c:out value="${privilege.privilegeName}"/>
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
							<form:hidden path="privilegeIds"  id="privilegeIds" /> 
							<form:errors path="privilegeIds" cssClass="validate-error">
							</form:errors>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label"></label>

						<!-- Button -->
						<div class="controls">
							<console:input funcnum="p_role_add" type="button"
								cssClass="btn btn-primary" value="保存" onclick="doSave();"></console:input>
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


