<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>品目合同</title>
		<script type="text/javascript">
			function jump(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
				}
				if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				}
				if(str == "materialProduction") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
				}
				if(str == "materialSales") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
				}
				if(str == "engineering") {
					action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
				}
				if(str == "serviceInformation") {
					action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
				}
				if(str == "items") {
					action = "${pageContext.request.contextPath}/supplierAudit/items.html";
				}
				if(str == "contract") {
					action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
				}
				if(str == "applicationForm") {
					action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
				}
				if(str == "reasonsList") {
					action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<script type="text/javascript">
			//默认不显示叉
			$(function() {
				$("td").each(function() {
					$(this).find("p").hide();
				});
			});
		
			function reason(auditField, auditFieldName) {
				var supplierId = $("#supplierId").val();
				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px'
					},
					function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
						  data: {"auditType":"contract_page","auditFieldName":auditFieldName,"auditContent":"附件信息","suggest":text,"supplierId":supplierId,"auditField":auditField},
							dataType: "json",
							success: function(result) {
								result = eval("(" + result + ")");
								if(result.msg == "fail") {
									layer.msg('该条信息已审核过！', {
										shift: 6, //动画类型
										offset: '100px'
									});
								}
							}
						});
							$("#" + auditField + "_hidden").hide();
							$("#" + auditField + "_show").show();
						layer.close(index);
					});
			}
		
			//下一步
			function nextStep(){
			  var action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
		
			//上一步
			function lastStep(){
			  var action = "${pageContext.request.contextPath}/supplierAudit/items.html";
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
		</script>
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
						<a href="#">供应商管理</a>
					</li>
					<li>
						<a href="#">供应商审核</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class="content ">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgdd">
						<li onclick="jump('essential')">
							<a aria-expanded="false" href="#tab-1">详细信息</a>
						</li>
						<li onclick="jump('financial')">
							<a aria-expanded="true" href="#tab-2">财务信息</a>
						</li>
						<li onclick="jump('shareholder')">
							<a aria-expanded="false" href="#tab-3">股东信息</a>
						</li>
						<c:if test="${fn:contains(supplierTypeNames, '生产')}">
							<li onclick="jump('materialProduction')">
								<a aria-expanded="false" href="#tab-4">生产信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '销售')}">
							<li onclick="jump('materialSales')">
								<a aria-expanded="false" href="#tab-4">销售信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '工程')}">
							<li onclick="jump('engineering')">
								<a aria-expanded="false" href="#tab-4">工程信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '服务')}">
							<li onclick="jump('serviceInformation')">
								<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务信息</a>
							</li>
						</c:if>
						<li onclick="jump('items')">
							<a aria-expanded="false" href="#tab-4">品目信息</a>
						</li>
						<li onclick="jump('contract')" class="active">
							<a aria-expanded="false" href="#tab-4">品目合同</a>
						</li>
						<li onclick="jump('applicationForm')">
							<a aria-expanded="false" href="#tab-4">申请表</a>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-4">审核汇总</a>
						</li>
					</ul>
					<ul class="count_flow ul_list hand">
						<table class="table table-bordered  table-condensed table-hover">
							<tr>
								<td class="tc info"> 品目名称</td>
								<td colspan="3" class="tc info">合同上传</td>
								<td colspan="3" class="tc info">收款进账单</td>
								<td class="tc info" rowspan="2">操作</td>
							</tr>
							<tr>
								<td class="tc info"> 末级节点</td>
								<c:forEach items="${years}" var="year">
									<td class="tc info">${year}</td>
								</c:forEach>
								<c:forEach items="${years}" var="year">
									<td class="tc info">${year}</td>
								</c:forEach>
							</tr>
							<c:forEach items="${contract}" var="obj">
								<tr>
									<td class="tc">${obj.name }</td>
									<td class="">
										<u:show showId="pShow${(vs.index+1)*6-1}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" />
									</td>
									<td class="">
										<u:show showId="pShow${(vs.index+1)*6-2}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" />
									</td>
									<td class="">
										<u:show showId="pShow${(vs.index+1)*6-3}" delete="false" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" />
									</td>
									<td class="">
										<u:show showId="pShow${(vs.index+1)*6-4}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" />
									</td>
									<td class="">
										<u:show showId="pShow${(vs.index+1)*6-5}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" />
									</td>
									<td class="">
										<u:show showId="pShow${(vs.index+1)*6-6}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" />
									</td>
									<td class="tc w50">
											<a onclick="reason('${obj.id}','${obj.name }');" id="${obj.id}_hidden" class="btn">审核</a>
											<p id="${obj.id}_show"><img src='/zhbj/public/backend/images/sc.png'></p>
								  </td>
								</tr>
							</c:forEach>
						</table>
					</ul>
				</div>
				<div class="col-md-12 add_regist tc">
          <a class="btn"  type="button" onclick="lastStep();">上一步</a>
          <a class="btn"  type="button" onclick="nextStep();">下一步</a>
	     </div>
			</div>
		</div>
		<form id="form_id" action="" method="post">
			<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
		</form>
	</body>

</html>