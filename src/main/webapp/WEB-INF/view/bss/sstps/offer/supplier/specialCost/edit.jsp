<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改</title>
    
<script type="text/javascript">
function down(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath %>specialCost/select.html?proId="+proId;
}
</script>
  </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">修改专项费用明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>

<div class="container bggrey border1 mt20"> 
    <form action="<%=basePath %>specialCost/update.html" method="post">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   <input type="hidden" id="id" name="id" class="w230 mb0" value="${sc.id }" readonly>
   
   <div>
   <h2 class="f16 count_flow mt40"><i>01</i>专项费用信息</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class=""><i class="red">＊</i>项目名称：</span>
	   <div class="input-append">
        <input type="text" class="w220" id="projectName" name="projectName" value="${sc.projectName }">
        <div class="validate">${ERR_projectName}</div>
       </div>
	 </li>
	 <li class="col-md-6 p0">
	   <span class=""><i class="red">＊</i>项目明细：</span>
	   <div class="input-append">
        <input id="productDetal" name="productDetal" value="${sc.productDetal }" type="text" class="w220" >
        <div class="validate">${ERR_productDetal}</div>
       </div>
	 </li>
	 <li class="col-md-6 p0">
	   <span class=""><i class="red">＊</i>名称：</span>
	   <div class="input-append">
        <input id="name" name="name" type="text" value="${sc.name }" class="w220" >
        <div class="validate">${ERR_name}</div>
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">规格型号：</span>
	   <div class="input-append">
        <input id="norm" name="norm" type="text" value="${sc.norm }" class="w220">
        <div class="validate">${ERR_norm}</div>
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">计量单位：</span>
	   <div class="input-append">
        <input id="measuringUnit" name="measuringUnit" type="text" value="${sc.measuringUnit }" class="w220" >
        <div class="validate">${ERR_measuringUnit}</div>
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">数量(消耗使用)：</span>
	   <div class="input-append">
        <input id="amount" name="amount" type="text" value="${sc.amount }" class="w220" >
        <div class="validate">${ERR_paperCode}</div>
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">单价：</span>
	   <div class="input-append">
        <input id="price" name="price" type="text" value="${sc.price }" class="w220" >
        <div class="validate">${ERR_paperCode}</div>
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">金额：</span>
	   <div class="input-append">
        <input id="money" name="money" type="text" value="${sc.money }" class="w220">
        <div class="validate">${ERR_money}</div>
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">分摊数量：</span>
	   <div class="input-append">
        <input id="proportionAmout" name="proportionAmout" type="text" value="${sc.proportionAmout }" class="w220">
        <div class="validate">${ERR_proportionAmout}</div>
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">单位产品分摊额：</span>
	   <div class="input-append">
        <input id="proportionPrice" name="proportionPrice" type="text" value="${sc.proportionPrice }" class="w220">
        <div class="validate">${ERR_proportionPrice}</div>
       </div>
	 </li>
	 <li class="col-md-12  p0 ">
	   <span class="fl">备注：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " id="remark" name="remark" title="不超过200个字" placeholder="不超过200个字">${sc.remark }</textarea>
       </div>
	 </li>
   </ul>
   </div>
   
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn btn-windows edit" type="submit">修改</button>
			    <button class="btn btn-windows cancel" type="button" onclick="down()">取消</button>
			</div>
		</div>
	
</form>	
</div>
		  
  </body>
</html>
