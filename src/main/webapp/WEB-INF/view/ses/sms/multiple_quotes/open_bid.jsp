<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<title>项目管理</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript"></script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">我的项目</a>
					</li>
					<li>
						<a href="#">标书管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container clear mt20">
   		<div class="list-unstyled padding-10 breadcrumbs-v3">
   			<span>
				  <a href="${pageContext.request.contextPath}/mulQuo/openBid.html?projectId=${project.id}" class="img-v1">开标一览表</a>
				  <span class="green_link">→</span>
			</span>
			<span>
				  <a href="${pageContext.request.contextPath}/mulQuo/priceBuild.html?projectId=${project.id}" class="img-v1">价格构成表</a>
				  <span class="green_link">→</span>
			</span>
			<span>
				  <a href="${pageContext.request.contextPath}/mulQuo/priceView.html?projectId=${project.id}" class="img-v1">明细表</a><!--货物材料、部件、工具价格明细表  -->
				  <span class="green_link">→</span>
			</span>
		    <span>
		    	<c:if test="${std.bidFinish == 0}">
				  <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v2 orange_link">编制标书</a>
				  <span class="green_link">→</span>
		    	</c:if>
		    	<c:if test="${std.bidFinish != 0}">
				  <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v1">编制标书</a>
				  <span class="">→</span>
		    	</c:if>
			</span>
			<span>
				<c:if test="${std.bidFinish == 1}">
				  <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v2 orange_link">绑定指标</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 2 || std.bidFinish == 3 || std.bidFinish == 4}">
				  <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v1">绑定指标</a>
				  <span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">绑定指标</a>
				  <span class="">→</span>
				</c:if>
			</span>
			<span>
				<c:if test="${std.bidFinish == 2 }">
				  <a href="${pageContext.request.contextPath}/mulQuo/quoteHistory.html?projectId=${project.id}"  class="img-v2 orange_link">填写报价</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">填写报价</a>
				  <span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 1}">
				  <a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">填写报价</a>
				  <span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 3 || std.bidFinish == 4}">
				  <a href="${pageContext.request.contextPath}/mulQuo/quoteHistory.html?projectId=${project.id}" class="img-v1">填写报价</a>
				  <span class="">→</span>
				</c:if>
			</span>
		    <span>
		    	<c:if test="${std.bidFinish == 3 }">
			  		<a href="${pageContext.request.contextPath}/supplierProject/result.html?projectId=${project.id}" class="img-v2  orange_link">完成</a>
		    	</c:if>
		    	<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 1}">
				  <a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 2}">
				  <a href="javascript:void(0);" onclick="tishi('请先填写报价');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 4}">
				  <a href="${pageContext.request.contextPath}/supplierProject/result.html?projectId=${project.id}"  class="img-v1">完成</a>
				</c:if>
			</span>
   		</div>
  	</div>
		<!-- 开标一览表-->
		<div class="container">
			<div class="content table_box">
			    <h2 class="tc">开标一览表</h2>
				<span class="ml10">投标人全称:桥头玩 </span><span class="ml50">项目名称:买车项目 </span><span class="ml100">项目编号:mcxm100 </span><span class="ml140">包号:2016001 </span><span class="ml160">金额单位:元 </span>
				<table class="table table-bordered table-condensed table-hover table-striped">
					<tr>
						<td>序号</td>
						<td>货物名称</td>
						<td>品牌</td>
						<td>规格型号</td>
						<td>计量单位</td>
						
						<td>数量</td>
						<td>单价(含税)</td>
						<td>金额(含税)</td>
						<td>交货时间</td>
						<td>备注</td>
					</tr>
					<tr>
					    <td>1</td>
						<td>大卡车</td>
						<td>3*15m</td>
						<td>辆</td>
						<td>100</td>
						<td>100.50</td>
						<td>100.50</td>
						<td>100.50</td>
						<td>100.50</td>
						<td>100.50</td>
					</tr>
					<tr>
						<td colspan="10">投标总价（大写人民币）：壹佰元        (小写¥：100)</td>
					</tr>
				</table>
				<span class="ml10">投标人全称：政法大学（盖章）</span><span class="ml100">法定代表人（或授权代表）：宋彪伟（签字）</span><span class="ml200">2016年  12月  13日</span>
			</div>
		</div>
	</body>

</html>