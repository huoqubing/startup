<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../common/include.jsp"%>
<script src="<%=path %>/js/webuploader.js"></script>
<script src="<%=path %>/js/uploader.js"></script>
<script langeuage="javascript">
baseUrl = 'pushTag';
var BASE_URL = '<%=path %>';
</script>
</head>
<script language="javascript">
	function validate() {
		var pass = true;
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
				action="save" modelAttribute="pushTag">
				<fieldset>
					<div id="legend">
						<legend>新建</legend>
					</div>
					<div class="control-group">
						<label class="control-label" for="input01">名称</label>
						<div class="controls">
							<form:input type="text" id="tag" path="title" />
							<form:errors path="title" cssClass="validate-error"></form:errors>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="input01">TAG</label>
						<div class="controls">
							<form:input type="text" id="tag" path="tag" />
							<form:errors path="tag" cssClass="validate-error"></form:errors>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"></label>

						<!-- Button -->
						<div class="controls">
							<console:input funcnum="p_pushTag_add" type="button"
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


