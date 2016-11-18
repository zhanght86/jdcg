<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<script type="text/javascript">
	$(function(){
		var value = "${performance.completedStatus}";
		$("#completedStatus").val(value);
	});
</script>
</head>
<body>


<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">履约情况管理</a></li><li><a href="#">履约情况修改</a></li></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->

	<div class="container container_box">
     <div>
   <h2 class="list_title">新增履约情况</h2>
  	<form action="${pageContext.request.contextPath}/performance/updatePerformance.html" method="post" id="form">
  	<input type="hidden" name="contractId" value="${performance.contractId}"/>
  	<input type="hidden" name="id" value="${performance.id}"/>
  		<ul class="list-unstyled ul_list">
  	<li class="col-md-3 p0 ">
	   <span class="">合同草稿签订时间：</span>
	   <div class="input-append">
	   <input id="draftSignedAt" name="draftSignedAt" value="<fmt:formatDate value="${performance.draftSignedAt}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" class="Wdate mb0 w220" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',lang:'zh-cn'})"/>
       </div>
	 </li> 
     <li class="col-md-3 p0 ">
	   <span class="">正式合同签订时间：</span>
	   <div class="input-append">
	   <input id="formalSignedAt" name="formalSignedAt" value="<fmt:formatDate value="${performance.formalSignedAt}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" class="Wdate mb0 w220" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',lang:'zh-cn'})"/>
       </div>
	 </li> 
     <li class="col-md-3 p0 ">
	   <span class="">交付日期：</span>
	   <div class="input-append">
	   <input id="delivery" name="delivery" value="<fmt:formatDate value="${performance.delivery}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" class="Wdate mb0 w220" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',lang:'zh-cn'})"/>
       </div>
	 </li> 
	<li class="col-md-3 p0">
	   <span class="">交货进度：</span>
	   <div class="input-append">
        <input class="span2" id="deliverySchedule" value="${performance.deliverySchedule }" type="text" name="deliverySchedule">
       </div>
	 </li>
     <li class="col-md-3 p0 ">
	   <span class="">资金支付百分比：</span>
	   <div class="input-append">
        <input class="span2" id="fundsPaid" value="${performance.fundsPaid}" type="text" name="fundsPaid">
       </div>
	 </li>
     <li class="col-md-3 p0 ">
	   <span class="">质量检验结果：</span>
	   <div class="input-append">
        <input class="span2" id="checkMass" value="${performance.checkMass}" name="checkMass" type="text">
       </div>
	 </li>
     <li class="col-md-3 p0 ">
	   <span class="col-md-12 p0">合同执行状态：</span>
	   <div class="select_common">
        <select name="completedStatus" id="completedStatus">
        	<option></option>
        	<option value="0">执行中</option>
        	<option value="1">终止</option>
        	<option value="2">变更</option>
        	<option value="3">完成</option>
        </select>
        </div>
	 </li> 
  	</ul>
  	<!-- 按钮 -->
		<div class="col-md-12 tc ">
		 	<input type="submit" value="保存" class="btn btn-windows save"/>
   			<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
 		</div>
  	</form>
  	</div>
 </div>
</body>
</html>
