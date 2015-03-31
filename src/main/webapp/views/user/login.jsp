<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>印象苏州</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../common/include.jsp"%>
<script type="text/javascript">
function changidateCode(obj) {
	var timenow = new Date().getTime();
	obj.src = "checkcode/captcha-image?d=" + timenow;
}
 function doLogin(){
	$("#password").val(hex_md5($("#password").val()));
	$("#login_form").submit();
}
</script>
<style type="text/css">
body {
  background: url("<%=path%>/img/bg.jpg") no-repeat center center fixed; 
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
		opacity: 0.9;
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 30px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
</style>
</head> 
<body>
	<div class="container">
		<%@ include file="../common/message.jsp"%>
		<form:form class="form-signin" action="login" method="post" modelAttribute="loginBean" id="login_form">
			<h2 class="form-signin-heading">印象苏州</h2>
			<form:input type="text" class="input-block-level" id="loginName" path="loginName"
				placeholder="用户名"/> 
			<form:input type="password" class="input-block-level"
				placeholder="密码" id="password" path="password"/>
			<form:input type="text" class="input-block-level"
				placeholder="验证码" id="validCode" path="validCode"/><img id="imgUrl" src="<%=path %>/checkcode/captcha-image" style="cursor: pointer;/* margin-bottom:10px;width: 120px;height: 40px;" */ onclick="changidateCode(this);" />
			<label class="checkbox"> <input
				type="checkbox" value="remember-me"> 自动登录
			</label>
			<button class="btn btn-large btn-primary" type="button" onclick="doLogin()">登录</button>
		</form:form>
	</div>
</body>
</html>