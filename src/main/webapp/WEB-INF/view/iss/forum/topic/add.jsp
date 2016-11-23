<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	$(function(){
		$("#parkId").val("${topic.park.id}");
	})
	</script>
  </head>
  
  <body>

  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a >论坛管理</a></li><li class="active"><a >主题管理</a></li><li class="active"><a >增加主题</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
    <form action="${ pageContext.request.contextPath }/topic/save.html" method="post">  
    <div>
	   	<h2 class="list_title">新增主题</h2>
	    <ul class="ul_list mb20">
	   		  
	   		 <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> <div class="red star_red">*</div>主题名称：</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input name="name" type="text"  value = '${topic.name}'>
		        <span class="add-on">i</span>
		        <div class="cue">${ERR_name}</div>
		       </div>
			 </li>
			 
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>所属版块：</span>
			   <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select  id ="parkId" name ="parkId" class=" col-md-12 col-sm-12 col-xs-12 p0" >
					<option></option>
			  	  	<c:forEach items="${parks}" var="park">
			  	  		<option  value="${park.id}">${park.name}</option>
			  	  	</c:forEach> 
	  			</select>
	  			<div class="cue">${ERR_park}</div>
	  			</div>
	  			
			 </li>
			 
			<li class="col-md-12 col-sm-12 col-xs-12">	  	 			
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 主题介绍：</span>
				<div class=" col-md-12 col-sm-12 col-xs-12 p0">
					<textarea  class="h130 col-md-12 col-sm-12 col-xs-12" title="不超过800个字" name="content">${topic.content}</textarea>		
				</div>			
	  	 	</li>
	  	 </ul>
	<!-- 底部按钮 -->			          
    <div class="col-md-12 col-sm-12 col-xs-12 tc"> 
    	<button class="btn btn-windows save" type="submit">保存</button>
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  	</div>
     </div>
     </form>
     </div>

  </body>
</html>

