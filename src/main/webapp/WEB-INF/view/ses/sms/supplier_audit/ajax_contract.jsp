<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<link href="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/skin/laypage.css" rel="stylesheet" type="text/css">
		<title>品目合同</title>

		<script type="text/javascript">
			$(function() {
			 //默认不显示叉
				$("td").each(function() {
					$(this).find("p").hide();
				});
			
				layer.close(index);
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${result.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${result.total}",
					startRow: "${result.startRow}",
					endRow: "${result.endRow}",
					groups: "${result.pages}" >= 3 ? 3 : "${result.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						return "${result.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							index = layer.load(1, {
								shade: [0.1, '#fff'] //0.1透明度的白色背景
							});
							$("#pageNum").val(e.curr);
							var pageNum = $("#pageNum").val();
							var supplierId = $("#supplierId").val();
							var type = "${supplierTypeId}";
							var path = "${pageContext.request.contextPath}/supplierAudit/ajaxContract.html?supplierId=" + supplierId + "&supplierTypeId=" + type + "&pageNum=" + pageNum;
							if(type == "PRODUCT") {
								$("#tab-1").load(path);
							} else if(type == "SALES") {
								$("#tab-2").load(path);
							} else if(type == "PROJECT") {
								$("#tab-3").load(path);
							} else if(type == "SERVICE") {
								$("#tab-4").load(path);
							}
						}
					}
				});
			});
		</script>
	</head>

	<body>
		<c:if test="${supplierTypeId eq 'PRODUCT'}">
			<c:set var="fileShow" value="pShow" />
			<input type="hidden" id="pro_val" value="生产">
		</c:if>
		<c:if test="${supplierTypeId eq 'SALES'}">
			<c:set var="fileShow" value="saleShow" />
			<input type="hidden" id="sal_val" value="销售">
		</c:if>
		<c:if test="${supplierTypeId eq 'PROJECT'}">
			<c:set var="fileShow" value="projectShow" />
			<input type="hidden" id="eng_val" value="工程">
		</c:if>
		<c:if test="${supplierTypeId eq 'SERVICE'}">
			<c:set var="fileShow" value="serShow" />
			<input type="hidden" id="ser_val" value="服务">
		</c:if>

		<form id="formSearch" action="${pageContext.request.contextPath}/supplierAudit/ajaxContract.html">
			<input type="hidden" name="pageNum" id="pageNum">
			<input type="hidden" name="supplierId" id="supplierId" value="${supplierId}">
			<input type="hidden" name="supplierTypeId" id="supplierTypeId" value="${supplierTypeId}">
			<table class="table table-bordered">
				<tr>
					<td class="tc info"> 品目名称</td>
					<td colspan="3" class="tc info">合同上传（体现甲乙双方盖章及工程名称、地点的相关页）</td>
					<td colspan="3" class="tc info">收款进账单</td>
					<td class="tc info w50" rowspan="2">操作</td>
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
				<c:forEach items="${contract}" var="obj" varStatus="vs">
					<tr>
						<td class="">${obj.name }</td>
						<td class="">
							<u:show showId="${fileShow}${(vs.index + 1)*6-1}" delete="false" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.oneContract}" />
						</td>
						<td class="">
							<u:show showId="${fileShow}${(vs.index + 1)*6-2}" delete="false" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.twoContract}" />
						</td>
						<td class="">
							<u:show showId="${fileShow}${(vs.index + 1)*6-3}" delete="false" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.threeContract}" />
						</td>
						<td class="">
							<u:show showId="${fileShow}${(vs.index + 1)*6-4}" delete="false" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.oneBil}" />
						</td>
						<td class="">
							<u:show showId="${fileShow}${(vs.index + 1)*6-5}" delete="false" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.twoBil}" />
						</td>
						<td class="">
							<u:show showId="${fileShow}${(vs.index + 1)*6-6}" delete="false" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.threeBil}" />
						</td>
						<td class="tc w50">
							<a onclick="reason('${obj.id}','${obj.name }');" id="${obj.id}_hidden" class="editItem"><img src='/zhbj/public/backend/images/light_icon.png'></a>
							<p id="${obj.id}_show"><img src='/zhbj/public/backend/images/sc.png'></p>
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
		<div id="pagediv" align="right" class="mb50"></div>
	</body>

</html>