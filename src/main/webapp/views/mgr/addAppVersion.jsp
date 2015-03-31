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
<script src="<%=path %>/js/uploaderFile.js"></script>
<script langeuage="javascript">
baseUrl = 'appVersion';
var BASE_URL = '<%=path %>';
</script>
</head>
<script language="javascript">
	function validate() {
		var pass = true;
		return pass;
	}

	function checkIsExist(pass) {
		return pass;
	}
/* 	$().ready(function() {
		$('#parent-id-dropdown-menu li a').click(function (e) {
		    var newHeading = $(this).text();
		    var newType = e.currentTarget['id'];
		    var $heading = $('#typeValue');
		    $("#type").val(newType);
		    $heading.html(newHeading + ' ');
		});
		$('#0').get(0).click();
	}); */
	
 	function doAfterUploadAccept(object,data){
		if(null!=data&&data.status=='0'){
    		$('#downloadUrl').val(data.url);
    		return true;
    	}else{
    		return false;
    	}
	}
	
	function doUploadBeforeSend(object,data){
		data['path']='/';
		data['type']="file";
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
				action="save" modelAttribute="appVersionDto">
				<fieldset>
					<div id="legend">
						<legend>新建</legend>
					</div>
					<div class="control-group">
<%-- 						<label class="control-label" for="input01">类型</label>
						<div class="controls">
							<div class="btn-group">
								<button class="btn btn-warning dropdown-toggle" data-toggle="dropdown">
									<span id="typeValue"></span>
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" id="parent-id-dropdown-menu">
									<li><a href="#" id="0">ANDROID</a></li>
									<li><a href="#" id="1">IOS</a></li>
								</ul>
							</div>
							<form:input type="hidden" id="type" path="type" value="0" />
							<form:errors path="type" cssClass="validate-error"></form:errors>
						</div> --%>
						<form:input type="hidden" id="type" path="type" value="0" />
					</div>

					<div class="control-group">
						<label class="control-label" for="input01">版本</label>
						<div class="controls">
							<form:input type="text" id="version" path="version"
								onblur="checkIsExist();" />
							<form:errors path="version" cssClass="validate-error"></form:errors>
						</div>
					</div>

 					<div class="control-group">
						<label class="control-label" for="input01">文件</label>
						<div class="controls">
							<div id="uploader">
							    <div id="thelist" class="uploader-list"></div>
							    <div class="btns">
							        <div id="picker">选择文件</div>
							        <!-- <button id="ctlBtn">开始上传</button> -->
							    </div>
							</div>
							<form:input type="hidden" id="downloadUrl" path="downloadUrl" />
							<form:errors path="downloadUrl" cssClass="validate-error"></form:errors>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="input01">内容</label>
						<div class="controls">
							<form:input type="text" id="content" path="content" />
							<form:errors path="content" cssClass="validate-error"></form:errors>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"></label>

						<!-- Button -->
						<div class="controls">
							<console:input funcnum="p_app_version_add" type="button"
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


