<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>新增模板</title>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	
  </head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">新增模板</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 新增模板开始-->
   <div class="container">
   		<form action="<%=basePath %>templet/save.do" method="post">
   		<div class="headline-v2">
   			<h2>新增模板</h2>
   		</div>
   		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-6  p0 ">
			   <span class="">模板名称：</span>
			   <div class="input-append">
		        <input class="span2" name="name" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">模板类型：</span>
		        <div class="btn-group ">
          			<select id="temType" name =temType class="w230" >
						<option></option>
			  	  		<option value="1">1</option>
			  	  	 	<option value="2">2</option>
	  				</select>
       			</div>
			 </li>
		     
			 <li class="col-md-12 p0">
	   			<span class="fl">模板内容：</span>
	  			<div class="col-md-12 pl200 fn mt5 pwr9">
	  				 <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
        			<!-- <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea> -->
       			</div>
			 </li> 
		     
	 		<%-- <li class="col-md-6 p0">
			   <span class="">角色：</span>
			   <div class="input-append">
			   	 <input class="span2" id="roleId" name="roleId" type="hidden">
		         <input class="span2" id="roleName" name="roleName" type="text">
				 <div class="btn-group ">
		          <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
				  <img src="<%=basePath%>public/ZHH/images/down.png" class="margin-bottom-5"/>
		          </button>
		          <ul class="dropdown-menu list-unstyled">
		          	<c:forEach items="${roles}" var="role" varStatus="vs">
		          		<li class="select_opt">
		          			<input type="checkbox" name="chkItem" value="${role.id };${role.name }" onclick="cheClick();" class="select_input">${role.name }
		          		</li>
		          	</c:forEach>
		          </ul>
		       </div>
		      </div>
			 </li> --%>
			 
   		</ul>
   
  		<div  class="col-md-12 ml185">
   			<div class="fl padding-10">
    			<button class="btn btn-windows save" type="submit">新增</button>
    			<button class="btn btn-windows git" onclick="history.go(-1)" type="button">返回</button>
			</div>
  		</div>
  	</form>
 </div>
 
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
</script>
</body>
</html>
