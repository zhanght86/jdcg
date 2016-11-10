<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>审核信息</title>
    
    <script type="text/javascript" charset="utf-8" src="${ pageContext.request.contextPath }//public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ pageContext.request.contextPath }//public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="${ pageContext.request.contextPath }//public/ueditor/lang/zh-cn/zh-cn.js"></script>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    
<script type="text/javascript">

$(function(){
	var range="${article.range}";
	if(range==2){
		$("input[name='ranges']").attr("checked",true); 
	}else{
		$("input[name='ranges'][value="+range+"]").attr("checked",true); 
	}
	$("#articleTypeId").val("${article.articleType.id }");
});

function sub(){
	var id = $("#id").val();
	//var name = $("#name").val();
	//var articleTypeId = $("#articleTypeId").val();
	layer.confirm('您确定需要审核通过吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
		layer.close(index);
		window.location.href="${ pageContext.request.contextPath }/article/audit.html?id="+id+"&status=2";
	});
}

function back(){
	var id = $("#id").val();
	var reason = $("#reason").val();
	layer.confirm('您确定需要退回吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
		layer.close(index);
		$.ajax({
			type:"post",
			//contentType: "application/json;charset=UTF-8",
			dataType:"json",
			url:"${ pageContext.request.contextPath }/article/audit.html?id="+id+"&reason="+reason+"&status=3",
			
			success:function(){
				window.location.href="${ pageContext.request.contextPath }/article/getAll.html";
			}
		});
	});
}

</script>    
  </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">信息服务</a></li><li><a href="#">信息管理</a></li><li class="active"><a href="#">修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container container_box">
   <form action="${ pageContext.request.contextPath }/article/audit.html?status=2" method="post">
     <div>
	   <h2 class="count_flow"><i>1</i>审核信息</h2>
	  <input type="hidden" name="id" id="id" value="${article.id }" readonly>
	  <input type="hidden" name="user.id" id="user.id" value="${article.user.id }" readonly>
	 
	   <ul class="ul_list mb20">
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">信息标题：</span>
	   <div class="input-append">
        <input class="span2" type="text" name="name" value="${article.name }" readonly>
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-3 margin-0 padding-0  ">
	   <span class="col-md-12 padding-left-5">信息类型：</span>
      <select id="articleTypeId" name="articleType.id" class="select w220" disabled>
   		 	<option></option>
          	<c:forEach items="${list}" var="list" varStatus="vs">
          		<option value="${list.id }" >${list.name }</option>
		    </c:forEach>
         </select>
	 </li> 
	 <li class="col-md-3 margin-0 padding-0  ">
	   <span class="col-md-12 padding-left-5">发布范围：</span>
	   <div class="input-append">
        <label class="fl margin-bottom-0"><input type="checkbox" name="ranges" value="0" disabled>内网</label>
        <label class="ml10 fl"><input type="checkbox" name="ranges" value="1" disabled>外网</label>
       </div>
	 </li> 
	 <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">文章来源：</span>
       <div class="input-append">
        <input class="span2" id="source" name="source" value="${article.source }"  type="text" readonly>
       <span class="add-on">i</span>
       </div>
	 </li> 
	 <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5"><i class="red">＊</i>链接来源：</span>
       <div class="input-append">
        <input class="span2" id="sourceLink" name="sourceLink" type="text" value="${article.sourceLink }" readonly>
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-11 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">信息正文：</span>
	   <div class="mb5">
         <script id="editor"  type="text/plain" class="col-md-12 p0"></script>
       </div>
	 </li> 
	 <li class="col-md-12 p0">
	 <span class="fl">已上传的附件：</span>
	 <div class="fl mt5">
  	   <c:forEach items="${article.articleAttachments}" var="a">
  	   	<a href="#">${fn:split(a.fileName, '_')[1]}</a>,
  	   </c:forEach>
	 </div>
	 </li> 
  	 </ul> 
	 </div>
	  <div class="padding-top-10 clear">
	   <h2 class="count_flow"><i>2</i>审核</h2>
	  <ul class="ul_list mb20">
	  
       
		     <li class="col-md-11 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5">退回理由：</span>
			   <div class="mb5">
		        <textarea class="h130 col-md-12 " id="reason" name="reason" title="不超过250个字" placeholder="不超过250个字"></textarea>
		       </div>
			 </li> 
		   </ul>
	  
	 <div  class="col-md-12 tc">
	    <button class="btn btn-windows check" type="button" onclick="sub()">审核</button>
	    <button class="btn btn-windows withdraw" type="button" onclick="back()">驳回</button>
	    <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
	   </div>
 	 </div>
  
	 </form>
     
    </div>
    
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
	var content="${article.content}";
	ue.ready(function(){
  		ue.setContent(content); 
  		ue.setDisabled(true);
	});
	
</script>
    
  </body>
</html>
