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
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li><li><a href="#">产品报价</a></li><li><a href="#">应付工资明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   
  <div class="container bggrey border1 mt20""> 
    <form action="<%=basePath %>wagesPayable/update.html" method="post" enctype="multipart/form-data">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   <input type="hidden" id="id" name="id" class="w230 mb0" value="${wp.id }" readonly>
   
   <div>
   <h2 class="f16 count_flow mt40"><i>01</i>材料信息</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">部门：</span>
	   <div class="input-append">
        <input type="text" id="department" name="department" value="${wp.department }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">上级项目：</span>
	   <div class="input-append">
        <input type="text" id="firsetProduct" name="firsetProduct" value="${wp.firsetProduct }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">项目名称：</span>
	   <div class="input-append">
        <input id="secondProduct" name="secondProduct" value="${wp.secondProduct }"  type="text" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>02</i>报价前2年</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">基本生产人员：</span>
	   <div class="input-append">
        <input type="text" id="tyaProduceUser" name="tyaProduceUser" value="${wp.tyaProduceUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">车间管理人员：</span>
	   <div class="input-append">
        <input type="text" id="tyaWorkshopUser" name="tyaWorkshopUser" value="${wp.tyaWorkshopUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">管理人员：</span>
	   <div class="input-append">
        <input type="text" id="tyaManageUser" name="tyaManageUser" value="${wp.tyaOtherUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">其他人员：</span>
	   <div class="input-append">
        <input type="text" id="tyaOtherUser" name="tyaOtherUser" value="${wp.tyaOtherUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">合计：</span>
	   <div class="input-append">
        <input type="text" id="tyaTotal" name="tyaTotal" value="${wp.tyaTotal }"  class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>03</i>报价前1年</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">基本生产人员：</span>
	   <div class="input-append">
        <input type="text" id="oyaProduceUser" name="oyaProduceUser" value="${wp.oyaProduceUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">车间管理人员：</span>
	   <div class="input-append">
        <input type="text" id="oyaWorkshopUser" name="oyaWorkshopUser" value="${wp.oyaWorkshopUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">管理人员：</span>
	   <div class="input-append">
        <input type="text" id="oyaManageUser" name="oyaManageUser" value="${wp.oyaManageUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">其他人员：</span>
	   <div class="input-append">
        <input type="text" id="oyaOtherUser" name="oyaOtherUser" value="${wp.oyaTotal }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">合计：</span>
	   <div class="input-append">
        <input type="text" id="oyaTotal" name="oyaTotal" value="${wp.oyaTotal }"  class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>04</i>报价当年</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">基本生产人员：</span>
	   <div class="input-append">
        <input type="text" id="newProduceUser" name="newProduceUser" value="${wp.newProduceUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">车间管理人员：</span>
	   <div class="input-append">
        <input type="text" id="newWorkshopUser" name="newWorkshopUser" value="${wp.newWorkshopUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">管理人员：</span>
	   <div class="input-append">
        <input type="text" id="newManageUser" name="newManageUser" value="${wp.newManageUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">其他人员：</span>
	   <div class="input-append">
        <input type="text" id="newOtherUser" name="newOtherUser" value="${wp.newOtherUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">合计：</span>
	   <div class="input-append">
        <input type="text" id="newTotal" name="newTotal" value="${wp.newTotal }"  class="w220">
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
        <textarea class="text_area col-md-12 " id="remark" name="remark" title="不超过250个字" placeholder="不超过250个字">${wp.remark }</textarea>
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