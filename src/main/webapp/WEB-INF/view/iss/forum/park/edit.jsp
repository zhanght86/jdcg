<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link href="${ pageContext.request.contextPath }/public/select2/css/select2.css"  rel="stylesheet">
    <script src="${ pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <script src="${ pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">    
	$(function(){ 
		 $("#isHoT").val("${park.isHot}");
         $.ajax({
             url:"<%=basePath %>park/getUserForSelect.do",   
             contentType: "application/json;charset=UTF-8", 
             dataType:"json",   //返回格式为json
             type:"POST",   //请求方式           
             success : function(users) {     
                 if (users) {           
                   $("#user").html("<option></option>");                
                   $.each(users, function(i, user) {  
                	   if(user.relName != null && user.relName!=''){
                           $("#user").append("<option  value="+user.id+">"+user.relName+"</option>"); 
                       }                    
                   });  
                 }
                 $("#user").select2();
                 $("#user").select2("val", "${park.user.id}"); 
             }
         });          
         
		}); 
	   function change(id){
	        $("#userId").val(id);
	    }

	</script>


  </head>
  <body>

  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a>论坛管理</a></li><li class="active"><a >版块管理</a></li><li class="active"><a >版块修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
      <form action="<%=basePath %>park/update.html" method="post">  
    <div>
	    <div class="count_flow">
	   		<i>1</i><h2>修改版块</h2>
	   </div>
	    <input class="span2" name ="parkId" type="hidden" value = '${park.id}'>
	    <input class="span2" name ="oldUserId" type="hidden" value = '${park.user.id }'>
	    <input class="span2" name ="oldParkName" type="hidden" value = '${park.name }'>
	   
	   <ul class="ul_list">
	   		  
	   		   <li class="col-md-3 margin-0 padding-0 ">
			   <span class="col-md-12 padding-left-5"><div class="red star_red">*</div>版块名称：</span>
			   <div class="input-append">
		        <input class="span2"  type="text" name="name" value = '${park.name}'>
		        <div class="validate">${ERR_name}</div>
		        <span class="add-on">i</span>
		       </div>
			 </li>
			 
			 <li class="col-md-3 margin-0 padding-0 ">
			   <span class="col-md-12 padding-left-5 h30">版主：</span>
                 <select id="user"  class="w220" onchange="change(this.options[this.selectedIndex].value)">
                    <input class="span2" id="userId" type="hidden" name="userId" value =""/>
                </select>
			 </li>
			 
			  <li class="col-md-3 margin-0 padding-0 ">
               <span class="col-md-12 padding-left-5 h30">热门：</span>
                <select id="isHoT" name="isHot" class="w220">
                <option value="0" selected="selected">不是热门</option>
                <option value="1">热门</option>
                </select> 
                <div class="validate">${ERR_isHot}</div>                  
             </li>
             
			<li class="col-md-11 margin-0 padding-0 ">	  	 			
				<span class="col-md-12 padding-left-5"> 版块介绍：</span>
				<div class="">
					<textarea  class="col-md-12 h130" title="不超过800个字" name="content">${park.content}</textarea>		
				</div>			
	  	 	</li>
	  	 </ul>
	</div>  	
	<!-- 底部按钮 -->			          
    <div class="padding-top-10 clear">                
      <div  class="col-md-12 pl185 ">
	     <div class="mt40 tc mb50">
	    <button class="btn btn-windows save" type="submit">更新</button>
	    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
		</div>
	  </div>
  </div>
     </form>
     </div>
     </div>

  </body>
</html>
