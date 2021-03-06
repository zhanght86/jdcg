<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>资质文件</title>
		<script type="text/javascript">
			function tijiao(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierQuery/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierQuery/financial.html";
				}
				if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierQuery/shareholder.html";
				}
				
				if(str == "chengxin") {
					action = "${pageContext.request.contextPath}/supplierQuery/list.html";
				}
				if(str == "item") {
					action = "${pageContext.request.contextPath}/supplierQuery/item.html";
				}
				if(str == "product") {
					action = "${pageContext.request.contextPath}/supplierQuery/product.html";
				}
				if(str == "updateHistory") {
					action = "${pageContext.request.contextPath}/supplierQuery/showUpdateHistory.html";
				}
				if (str == "zizhi") {
					action = "${pageContext.request.contextPath}/supplierQuery/aptitude.html";
				}
				if (str == "contract") {
					action = "${pageContext.request.contextPath}/supplierQuery/contract.html";
				}
				if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierQuery/supplierType.html";
				}
				$("#form_id").attr("action", action);
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
						<a href="#">支撑环境</a>
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
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="tijiao('essential');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('shareholder');">股东信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('supplierType');">供应商类型</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">品目信息</a>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">品目合同</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li>
					</ul>
					<ul class="count_flow ul_list count_flow">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
						<c:set value="0" var="liCount"/>
							<c:if test="${fn:contains(supplierTypeNames, '生产')}">
							<c:set value="${liCount+1}" var="liCount"/>
								<li id="li_id_1" class="active">
									<a aria-expanded="true" href="#tab-1" data-toggle="tab">物资-生产型品目信息</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '销售')}">
								<li id="li_id_2" class='<c:if test="${liCount == 0}">active</c:if>'>
									<a aria-expanded="false" href="#tab-2" data-toggle="tab">物资-销售型品目信息</a>
								</li>
								<c:set value="${liCount+1}" var="liCount"/>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<li id="li_id_3" class='<c:if test="${liCount == 0}">active</c:if>'>
									<a aria-expanded="false" href="#tab-3" data-toggle="tab">工程品目信息</a>
									<c:set value="${liCount+1}" var="liCount"/>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<li id="li_id_4" class='<c:if test="${liCount == 0}">active</c:if>'>
									<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务品目信息</a>
									<c:set value="${liCount+1}" var="liCount"/>
								</li>
							</c:if>
						</ul>

						<div class="tab-content padding-top-20" id="tab_content_div_id">

							<!-- 物资生产型 -->
							<c:if test="${fn:contains(supplierTypeNames, '生产')}">
								<c:set value="0" var="prolength" />
								<div class="tab-pane fade active in height-300" id="tab-1">
									<table class="table table-bordered">
										<c:forEach items="${cateList }" var="obj" varStatus="vs">
											<tr>
												<td class="tc info">${obj.categoryName } </td>
												<c:forEach items="${obj.list }" var="quaPro">
													<td>
														<c:set value="${prolength+1}" var="prolength"></c:set>
														<span class="hand" onclick="reason('${quaPro.flag}','${obj.categoryName }','生产-${quaPro.name}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${quaPro.name}：</span>
													  <u:show showId="pShow${prolength}" groups="${saleShow}" delete="false" businessId="${quaPro.flag}" sysKey="${sysKey }" typeId="${typeId}" />
														<p id="${quaPro.flag}" ></p>
													</td>
												</c:forEach>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 物资销售型 -->
							<c:if test="${fn:contains(supplierTypeNames, '销售')}">
								<c:set value="0" var="length"> </c:set>
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-300" id="tab-2">
									<table class="table table-bordered">
										<c:forEach items="${saleQua }" var="sale">
											<tr>
												<td class="tc info">${sale.categoryName } </td>
												<c:forEach items="${sale.list }" var="saua" varStatus="vs">
													<td>
														<c:set value="${length+1}" var="length"></c:set>
														<span class="hand"  onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${saua.name}：</span>
														<u:show showId="saleShow${length}" groups="${saleShow}" delete="false" businessId="${saua.flag}" sysKey="${sysKey }" typeId="${typeId}" />							
														<p id="${saua.flag}" ></p>
													</td>
												</c:forEach>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 工程 -->
							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-200" id="tab-3">
									<table class="table table-bordered">
										<c:set value="0" var="plength"> </c:set>

										<c:forEach items="${projectQua }" var="project">
											<tr>
												<td class="info">${project.categoryName }
													<c:forEach items="${project.list }" var="pr" varStatus="vs">
														<td class="tc">
															<c:set value="${plength+1}" var="plength"></c:set>
															<span class="hand"  onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${pr.name}：</span>
															<u:show showId="projectShow${plength}" delete="false" groups="${saleShow}" businessId="${pr.flag}" sysKey="${sysKey }" typeId="${typeId}" />
															<p id="${pr.flag}" ></p>
														</td>
													</c:forEach>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 服务 -->
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-200" id="tab-4">
									<table class="table table-bordered">
										<c:set value="0" var="slength"> </c:set>
										<c:forEach items="${serviceQua }" var="server">
											<tr>
												<td class="info">${server.categoryName }
												</td>
													<c:forEach items="${server.list }" var="ser" varStatus="vs">
														<td class="tc">
															<c:set value="${slength+1}" var="slength"></c:set>
															<span class="hand"  onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${ser.name}：</span>
															<u:show showId="serverShow${plength}" delete="false" groups="${saleShow}" businessId="${ser.flag}" sysKey="${sysKey }" typeId="${typeId}" />
															<p id="${ser.flag}"></p>
														</td>
													</c:forEach>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>
						</div>
						</ul>
				</div>
			</div>
		</div>
		
		<form id="form_id" action="" method="post">
			<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
		</form>
	</body>

</html>