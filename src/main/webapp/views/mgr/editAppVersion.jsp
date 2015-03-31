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


function validate(){
	var pass=true;
	return pass;
}

function checkIsExist(pass){
	return pass;
}

function doAfterUploadAccept(object,data){
	if(null!=data&&data.status=='0'){
   		$('#downloadUrl').val(data.url);
   		$('#currentUrl').val(data.url);
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
			<form:form id="config_form" action="editItem"
				class="form-horizontal form-newapp hero-unit" method="post"
				modelAttribute="appVersionDto">
				<input type="hidden" name="oldItemKey"
					value="<c:out value="${appVersionDto.id }" />" />
				<fieldset>
					<div id="legend">
						<legend>修改</legend>
					</div>
					<form:input path="id" type="hidden"/>
					<div class="control-group">
						<%-- <label class="control-label" for="input01">类型</label>
						<div class="controls">
							<c:if test="${appVersionDto.type=='0' }">
								<span id="typeValue" readonly="readonly">ANDROID</span>
							</c:if>
							<c:if test="${appVersionDto.type=='1' }">
								<span id="typeValue" readonly="readonly">IOS</span>
							</c:if>
							<form:input type="hidden" id="type" path="type" />
							<form:errors path="type" cssClass="validate-error"></form:errors>
						</div> --%>
						<form:input type="hidden" id="type" path="type" value="0"/>
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
						<label class="control-label" for="input01">目前文件</label>
						<div class="controls">
							<span id="currentUrl">${appVersionDto.downloadUrl}</span>
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
							<console:input funcnum="p_app_version_edit" type="button"
								cssClass="btn btn-primary" value="保存" onclick="doSave();"></console:input>
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


