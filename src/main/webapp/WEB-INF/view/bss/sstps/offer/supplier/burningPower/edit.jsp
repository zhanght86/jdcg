<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>修改</title>
    
  </head>
  
  <body>
  
 <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li><li><a href="#">产品报价</a></li><li><a href="#">燃料动力费明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   
  <div class="container bggrey border1 mt20""> 
    <form action="<%=basePath %>burningPower/update.html" method="post" enctype="multipart/form-data">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   <input type="hidden" id="id" name="id" class="w230 mb0" value="${burningPower.id }" readonly>
   <div>
   <h2 class="f16 count_flow mt40"><i>01</i>材料信息</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">上级项目：</span>
	   <div class="input-append">
        <input type="text" id="firsetProduct" name="firsetProduct" value="${burningPower.firsetProduct }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">二级项目：</span>
	   <div class="input-append">
        <input type="text" id="secondProduct" name="secondProduct" value="${burningPower.secondProduct }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">项目名称：</span>
	   <div class="input-append">
        <input id="thirdProduct" name="thirdProduct" type="text" value="${burningPower.thirdProduct }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">计量单位：</span>
	   <div class="input-append">
        <input id="unit" name="unit" type="text" value="${burningPower.unit }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>02</i>报价前2年</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">数量：：</span>
	   <div class="input-append">
        <input type="text" id="tyaAcount" name="tyaAcount" value="${burningPower.tyaAcount }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">平均单价(元)：</span>
	   <div class="input-append">
        <input type="text" id="tyaAvgPrice" name="tyaAvgPrice" value="${burningPower.tyaAvgPrice }" class="w220">
       </div>
	 </li>
	 
	 <li class="col-md-6  p0 ">
	   <span class="">金额(元)：</span>
	   <div class="input-append">
        <input type="text" id="tyaMoney" name="tyaMoney" value="${burningPower.tyaMoney }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>03</i>报价前1年</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">数量：：</span>
	   <div class="input-append">
        <input type="text" id="oyaAcount" name="oyaAcount" value="${burningPower.oyaAcount }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">平均单价(元)：</span>
	   <div class="input-append">
        <input type="text" id="oyaAvgPrice" name="oyaAvgPrice" value="${burningPower.oyaAvgPrice }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">金额(元)：</span>
	   <div class="input-append">
        <input type="text" id="oyaMoney" name="oyaMoney" value="${burningPower.oyaMoney }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>04</i>报价当年</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">数量：：</span>
	   <div class="input-append">
        <input type="text" id="newAcount" name="newAcount" value="${burningPower.newAcount }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">平均单价(元)：</span>
	   <div class="input-append">
        <input type="text" id="newAvgPrice" name="newAvgPrice" value="${burningPower.newAvgPrice }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">金额(元)：</span>
	   <div class="input-append">
        <input type="text" id="newMoney" name="newMoney" value="${burningPower.newMoney }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
    <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>05</i>其他</h2>
   <ul class="list-unstyled list-flow ul_list">
	 <li class="col-md-12  p0 ">
	   <span class="fl">备注：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " id="remark" name="remark" title="不超过250个字" placeholder="不超过250个字">${burningPower.remark }</textarea>
       </div>
	 </li>
   </ul>
   </div>
   
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn btn-windows add" type="submit">确定</button>
			    <button class="btn btn-windows cancel" type="button" onclick="location.href='javascript:history.go(-1);'">取消</button>
			</div>
		</div>
	
</form>	
</div>
		  
  </body>
</html>