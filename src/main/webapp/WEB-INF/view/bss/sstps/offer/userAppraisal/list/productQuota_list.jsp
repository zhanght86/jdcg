<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="../../../../../common.jsp"%>
    
    <title>产品工时定额明细</title>
	
	
<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/yearPlan/userGetAll.do?productId="+proId;
}

</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">审价人员审价</a></li><li><a href="javascript:void(0)">产品工时定额明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>产品工时定额明细</h2>
	 	</div>
		
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<form action="${pageContext.request.contextPath}/productQuota/userUpdate.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th rowspan="3" class="info">序号</th>
						<th rowspan="3" class="info">零组部件名称</th>
						<th rowspan="3" class="info">零组部件图号</th>
						<th rowspan="3" class="info">工序名称</th>
						<th colspan="14" class="info">单位产品工时定额</th>
						<th rowspan="3" class="info">计量单位</th>
						<th rowspan="2" colspan="2" class="info">配套数量</th>
						<th rowspan="2" colspan="3" class="info">单位产品工时审核核定数</th>
						<th rowspan="3" class="info">备   注</th>
					</tr>
					<tr>
						<th colspan="2" class="info">准结工时</th>
						<th colspan="2" class="info">加工工时</th>
						<th colspan="2" class="info">装配工时</th>
						<th colspan="2" class="info">调试工时</th>
						<th colspan="2" class="info">试验工时</th>
						<th colspan="2" class="info">其他工时</th>
						<th colspan="2" class="info">小计</th>
					</tr>
					<tr>
						<th class="info">报价</th>
						<th class="info">核定</th>
						<th class="info">报价</th>
						<th class="info">核定</th>
						<th class="info">报价</th>
						<th class="info">核定</th>
						<th class="info">报价</th>
						<th class="info">核定</th>
						<th class="info">报价</th>
						<th class="info">核定</th>
						<th class="info">报价</th>
						<th class="info">核定</th>
						<th class="info">报价</th>
						<th class="info">核定</th>
						<th class="info">报价</th>
						<th class="info">核定</th>
						<th class="info">报价</th>
						<th class="info">核定</th>
						<th class="info">核减</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="yp" varStatus="vs">
					<tr>
						<td class="tc"><input type="hidden" name="productQuotaList['${vs.index }'].id" value="${yp.id }" />${vs.index+1 }</td>
						<td class="tc">${yp.partsName }</td>
						<td class="tc">${yp.partsDrawingCode }</td>
						<td class="tc">${yp.processName }</td>
						
						<td class="tc">${yp.offer }</td>
						<td class="tc"><input type="text" value='${yp.ratify }' name="productQuotaList['${vs.index }'].ratify"></td>
						<td class="tc">${yp.processingOffer }</td>
						<td class="tc"><input type="text" value='${yp.processingRatify }' name="productQuotaList['${vs.index }'].processingRatify"></td>
						<td class="tc">${yp.assemblyOffer }</td>
						<td class="tc"><input type="text" value='${yp.assemblyRatify }' name="productQuotaList['${vs.index }'].assemblyRatify"></td>
						<td class="tc">${yp.debuggingOffer }</td>
						<td class="tc"><input type="text" value='${yp.debuggingRatify }' name="productQuotaList['${vs.index }'].debuggingRatify"></td>
						<td class="tc">${yp.testOffer }</td>
						<td class="tc"><input type="text" value='${yp.testRatify }' name="productQuotaList['${vs.index }'].testRatify"></td>
						<td class="tc">${yp.otherOffer }</td>
						<td class="tc"><input type="text" value='${yp.otherRatify }' name="productQuotaList['${vs.index }'].otherRatify"></td>
						<td class="tc">${yp.subtotalOffer }</td>
						<td class="tc"><input type="text" value='${yp.subtotalRatify }' name="productQuotaList['${vs.index }'].subtotalRatify"></td>
						
						<td class="tc">${yp.measuringUnit }</td>
						
						<td class="tc">${yp.assortOffer }</td>
						<td class="tc"><input type="text" value='${yp.assortRatify }' name="productQuotaList['${vs.index }'].assortRatify"></td>
						<td class="tc">${yp.approvedOffer }</td>
						<td class="tc"><input type="text" value='${yp.approvedRatify }' name="productQuotaList['${vs.index }'].approvedRatify"></td>
						<td class="tc"><input type="text" value='${yp.approvedSubtract }' name="productQuotaList['${vs.index }'].approvedSubtract"></td>
						<td class="tc">${yp.remark }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div  class="col-md-12">
		   <div class="mt40 tc mb50">
		    <button class="btn" type="button" onclick="onStep()">上一步</button>
		    <button class="btn" type="submit">下一步</button>
		   </div>
	 	 </div>
	 	 </form>
  </div>
  
  </body>
</html>
