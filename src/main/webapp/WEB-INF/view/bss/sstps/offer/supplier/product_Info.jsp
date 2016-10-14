<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>装备（产品）技术资料概述</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <form action="<%=basePath %>offerProduct/save.html" method="post" enctype="multipart/form-data">
   
	<div class="container">
	 	<div class="headline-v2">
	  		 <h2>装备（产品）技术资料概述</h2>
	 	</div>
	 	
	 	<input type="hidden" id="contractProduct.id" name="contractProduct.id" class="w230 mb0" value="${contractProduct.id }" readonly>
	 	<input type="hidden" id="id" name="id" class="w230 mb0" value="${productInfo.id }" readonly>
	 	
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered">
				 <tobody>
				  	<tr>
				 		<td width="10%" class="bggrey tr">产品名称：</td>
				 		<td width="25%">
				 			<input type="text" id="name" name="name" class="w230 mb0" value="${contractProduct.name }" readonly>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="10%" class="bggrey tr">设计单位：</td>
				 		<td width="25%">
				 			<input id="designDepartment" name="designDepartment" type="text" class="w230 mb0" value="${productInfo.designDepartment }" >
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="10%" class="bggrey tr">一、产品概述：</td>
				 		<td width="25%">
				 		<textarea class="text_area w220 mt10" id="productOverview" name="productOverview" title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productOverview } </textarea>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="10%" class="bggrey tr">二、 产品生产过程概述：</td>
				 		<td width="25%">
				 			<textarea class="text_area w220 mt10" id="productProcess" name="productProcess"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productProcess }</textarea>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="10%" class="bggrey tr">三、产品技术状况概述：</td>
				 		<td width="25%">
				 			<textarea class="text_area w220 mt10" id="productSkill" name="productSkill"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productSkill }</textarea>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="10%" class="bggrey tr">四、结论：</td>
				 		<td width="25%">
				 			<textarea class="text_area w220 mt10" id="conclusion" name="conclusion"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.conclusion }</textarea>
				 		</td>
				 	</tr>
				 </tobody>
			</table>
        </div>
	 	
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn btn-windows " type="submit">下一页</button>
			    <button class="btn btn-windows cancel" type="button" onclick="location.href='javascript:history.go(-1);'">取消</button>
			</div>
		</div>
	
	  </div>	
	</form>	
  
  
  </body>
</html>
