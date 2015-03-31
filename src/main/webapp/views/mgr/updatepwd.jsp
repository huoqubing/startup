<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../common/include.jsp"%>
<script type="text/javascript">
	function doUpdatepwd() {
		if(validate()){
			$("#password").val(hex_md5($("#password").val()));
			$("#newPassword").val(hex_md5($("#newPassword").val()));
			$("#confirmPassword").val(hex_md5($("#confirmPassword").val()));
			$("#updatePwd").submit();
		}
	}
	function validate() {
		var pass = true;
		return pass;
	}
</script>
</head>

<body>

	<%-- <div style="margin-top:100px;">
<%@ include file="./common/message.jsp"%>

main page

</div> --%>

	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/menu.jsp"%>
			
		<div class="content">	
			
				<div class="header"><h3>修改密码</h3>
				<%@ include file="../common/message.jsp" %>
				</div>
				
					<br/><br/>
				<form:form id="updatePwd"
					class="form-horizontal form-newapp hero-unit" method="post" action="updatePwd"
					modelAttribute="userDto">
					<fieldset>
						<div id="legend">
							<legend>修改密码</legend>
						</div>
						<div class="control-group">

							<!-- Text input-->
							<label class="control-label" for="input01">原密码</label>
							<div class="controls">
								<form:password id="password" cssClass="input-xlarge"
									path="password" />
								<form:errors path="password" cssClass="validate-error"></form:errors>
							</div>
						</div>

						<div class="control-group">

							<!-- Text input-->
							<label class="control-label" for="input01">新密码</label>
							<div class="controls">
								<form:password id="newPassword" cssClass="input-xlarge"
									path="newPassword" />
								<form:errors path="newPassword" cssClass="validate-error"></form:errors>
								<p class="help-block"></p>
							</div>
						</div>

						<div class="control-group">

							<!-- Text input-->
							<label class="control-label" for="input01">确认密码</label>
							<div class="controls">
								<form:password id="confirmPassword" cssClass="input-xlarge"
									path="confirmPassword" />
								<form:errors path="confirmPassword" cssClass="validate-error"></form:errors>
								<p class="help-block"></p>
							</div>
						</div>



						<div class="control-group">
							<label class="control-label"></label>

							<!-- Button -->
							<div class="controls">
							<input type="button" class="btn btn-primary" value="修改密码" onclick="doUpdatepwd();"/>
							</div>
						</div>

					</fieldset>
				</form:form>
			
			</div>
		
		<div class="row-fluid">
			<div class="span12">
				<%@ include file="../common/footer.jsp"%>
			</div>
		</div>
	

</body>
</html>


