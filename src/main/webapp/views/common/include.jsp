<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.viway.com/tag/view" prefix="console" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String path=request.getContextPath();
%>
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.7.2/jquery.min.js"></script> -->
<script src="<%=path %>/js/jquery-1.10.2.min.js"></script>
<script src="<%=path %>/js/md5-min.js"></script>
<script src="<%=path %>/js/main.js"></script>
<script src="<%=path %>/js/bootstrap.min.js"></script>
<script src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js"></script>
<script src="<%=path %>/js/jquery.ztree.all-3.5.js"></script>
<script src="<%=path %>/js/jquery.fancybox.js?v=2.1.5"></script>
<script src="<%=path %>/js/jquery-ui-timepicker-addon.js"></script>

<link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="<%=path %>/css/main.css" rel="stylesheet" media="screen">
<link href="<%=path %>/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
<link href="<%=path %>/css/custom-theme/jquery-ui-1.10.0.custom.css" rel="stylesheet" media="screen">
<link href="<%=path %>/css/elements.css" rel="stylesheet" media="screen">
<link href="<%=path %>/css/theme.css" rel="stylesheet" media="screen">
<link href="<%=path %>/css/screen.css" rel="stylesheet" media="screen" />
<link href="<%=path %>/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" media="screen">
<link href="<%=path %>/css/jquery.fancybox.css?v=2.1.5" rel="stylesheet" media="screen">

<script langeuage="javascript">
var baseUrl="";
var t_itemKey="";
function removeItem(itemKey){
	t_itemKey=itemKey;
	$('#dialog_simple').dialog('open');
}
$(document).ready(function(){
	$('#dialog_simple').dialog({
		autoOpen: false,
		width: 350,
		resizable:false,
		buttons: {
			"确定": function () {
				$("input[name='selected_item'][value='"+t_itemKey+"']").attr("checked",true);
				$('#list_form').append("<input type='hidden' name='action' value='single' />");
				$('#list_form').submit();
				$(this).dialog("close");
			},
			"取消": function () {
				$(this).dialog("close");
			}
		}
	});
	$('#dialog_simples').dialog({
		autoOpen: false,
		width: 350,
		resizable:false,
		buttons: {
			"确定": function () {
				$('#list_form').submit();
				$(this).dialog("close");
			},
			"取消": function () {
				$(this).dialog("close");
			}
		}
	});
	$('#dialog_simpless').dialog({
		autoOpen: false,
		width: 350,
		resizable:false,
		buttons: {
			"确定": function () {
				$(this).dialog("close");
			}
		}
	});
	$('.fancybox').fancybox({
		closeClick : true
	});
});
function removeItems(){
	if($("#list_table tbody input[type='checkbox']:checked").length>0){
		$('#dialog_simples').dialog('open');
	}else{
		$('#dialog_simpless').dialog('open');
	}	
}

function goAdd(){
	var url='<%=path%>/'+baseUrl+'/goAddItem';
	goUrl(url);
	
}

function editItem(itemKey){
	var url='<%=path%>/'+baseUrl+'/prepareEditItem?itemKey='+itemKey;
	goUrl(url);
}

function displayItem(itemKey){
	var url='<%=path%>/'+baseUrl+'/displayItem?itemKey='+itemKey;
	goUrl(url);
}

function doPagnation(obj){
	var page=$(obj).attr("title");
	$("#filter_form").append("<input type='hidden' name='currentPage' value='"+page+"'/>");
	doFilter();
}

function doFilter(){
	$("#filter_form").submit();
}

function doSave(){
	if(validate()){
		$("#config_form").submit();
	}
}
function doUpdate(){
	if(validate()){
		$("#config_form").submit();
	}
}

function doCancel(){
	var url='<%=path%>/'+baseUrl+'/list';
	goUrl(url);
}
function addOptionItem() {
    var ai = document.getElementById("availableItems");
    var si = document.getElementById("selectedItems");
    for (i=0;i<ai.options.length;i++) {
      if (ai.options[i].selected) {
        var opt = ai.options[i];
        si.options[si.options.length] = new Option(opt.innerHTML, opt.value);
        ai.options[i] = null;
        i = i - 1;
      }
    }
  }
 
  function addAllOption() {
    var ai = document.getElementById("availableItems");
    var si = document.getElementById("selectedItems");
    for (i=0;i<ai.options.length;i++) {
      var opt = ai.options[i];
      si.options[si.options.length] = new Option(opt.innerHTML, opt.value);
    }
    ai.options.length = 0;
  }
    
  function removeOptionItem() {
    var ai = document.getElementById("availableItems");
    var si = document.getElementById("selectedItems");
    for (i=0;i<si.options.length;i++) {
      if (si.options[i].selected) {
        var opt = si.options[i];
        ai.options[ai.options.length] = new Option(opt.innerHTML, opt.value);
        si.options[i] = null;
        i = i - 1;
      }
    }
  }
  
  function removeAllOption() {
    var ai = document.getElementById("availableItems");
    var si = document.getElementById("selectedItems");
    for (i=0;i<si.options.length;i++) {
      var opt = si.options[i];
      ai.options[ai.options.length] = new Option(opt.innerHTML, opt.value);
    }
    si.options.length = 0;
  }
</script>
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->