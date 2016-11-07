<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>添加</title>
    
<script type="text/javascript">
function down(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath %>periodCost/select.html?proId="+proId;
}
</script>    

  </head>
  
  <body>
  
 <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li><li><a href="#">产品报价</a></li><li><a href="#">期间费用明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   
  <div class="container bggrey border1 mt20""> 
    <form action="<%=basePath %>periodCost/save.html" method="post">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   
   <div>
   <h2 class="f16 count_flow mt40"><i>01</i>费用明细</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class=""><i class="red">＊</i>项目名称：</span>
	   <div class="input-append">
        <input type="text" id="projectName" name="projectName" value="${pc.projectName }" class="w220">
        <div class="validate">${ERR_projectName}</div>
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">报价前2年：</span>
	   <div class="input-append">
        <input type="text" id="tyaQuoteprice" name="tyaQuoteprice" value="${pc.tyaQuoteprice }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">报价前1年：</span>
	   <div class="input-append">
        <input id="oyaQuoteprice" name="oyaQuoteprice" type="text" value="${pc.oyaQuoteprice }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">报价当年：</span>
	   <div class="input-append">
        <input id="newQuoteprice" name="newQuoteprice" type="text" value="${pc.newQuoteprice }" class="w220">
       </div>
	 </li>
	 <li class="col-md-12  p0 ">
	   <span class="fl">备注：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " id="remark" name="remark" title="不超过250个字" placeholder="不超过250个字">${pc.remark }</textarea>
       </div>
	 </li>
   </ul>
   </div>
   
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn btn-windows save" type="submit">确定</button>
			    <button class="btn btn-windows cancel" type="button" onclick="down()">取消</button>
			</div>
		</div>
	
</form>	
</div>
		  
  </body>
</html>
