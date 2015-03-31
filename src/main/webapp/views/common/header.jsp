<%@page import="com.sql.project.startup.common.SessionInfo"%>
<%@page import="com.sql.project.startup.common.Constant"%>
<%@page import="com.sql.project.startup.entity.User"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-inverse navbar-fixed-top" >
  <div class="navbar-inner" >
    <div class="container-fluid" >
      <button type="button"  class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      <a href="#" class="brand" style="width: 230px;"><img src="<%=path%>/img/logo.png" style="width: 40px;height: 40px;padding-bottom:0px;"> 后台管理系统</a>
      <div class="nav-collapse collapse">

        <ul class="nav pull-right">
          <li>
          	<%
		Object sessioninfo = request.getSession().getAttribute(Constant.KEY_SESSION_INFO);
		 if(sessioninfo==null){
		%>
       <p class="navbar-text pull-right">
        <%}else{ 
        	SessionInfo sif = (SessionInfo)sessioninfo;
        	User user = sif.getLoginUser();
        %>
        <div  class="btn-group pull-right" style="float:right;">
			<button class="btn btn-warning dropdown-toggle" style="background-color:#AAAA55;" data-toggle="dropdown">
			<i  class="icon-user icon-white"></i> <%=user.getLoginName() %>
			<span  class="caret"></span>
			</button>
			<ul  class="dropdown-menu">
				<li>
					<a href="<%=path%>/loginUser/toupdatepwd">修改密码</a>
				</li>
				<li class="divider"></li>
				<li>
					<a href="<%=path%>/logout">退出</a>
				</li>
			</ul>
		</div>
         <%} %>
          </li>
          </ul>
      </div>
     <div id="dialog_simple" style="display: none;" title="系统警告">
			<p>你确定要删除此项?</p>
	</div>
	<div id="dialog_simples" style="display: none;" title="系统警告">
		<p>你确定要删除选中项?</p>
	</div>
	<div id="dialog_simpless" style="display: none;" title="系统提示">
		<p>你至少选择一项进行删除操作</p>
	</div>
      <!--/.nav-collapse --> 
    </div>
  </div>
</div>