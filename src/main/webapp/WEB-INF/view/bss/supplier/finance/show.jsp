<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title></title>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<script type="text/javascript">
			function download(id, key) {
				var form = $("<form>");
				form.attr('style', 'display:none');
				form.attr('method', 'post');
				form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
				$('body').append(form);
				form.submit();
			}
		</script>
	</head>

	<body>

		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">个人信息</a>
					</li>
					<li class="active">
						<a href="#">提报每年财务审计</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<!-- 修改订列表开始-->
		<div class="container">
			<div>
				<div class="headline-v2">
					<h2>财务审计报告查看</h2>
				</div>
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="bggrey">年份：</td>
							<td>${supplierFinance.year }</td>
							<td class="bggrey">会计事务所名称：</td>
							<td>${supplierFinance.name }</td>
							<td class="bggrey">事务所联系电话：</td>
							<td>${supplierFinance.telephone }</td>
						</tr>
						<tr>
							<td class="bggrey">审计人姓名：</td>
							<td>${supplierFinance.auditors }</td>
							<td class="bggrey">资产总额（万元）：</td>
							<td>${supplierFinance.totalAssets }</td>
							<td class="bggrey">负债总额（万元）：</td>
							<td>${supplierFinance.totalLiabilities }</td>
						</tr>
						<tr>
							<td class="bggrey">净资产总额（万元）：</td>
							<td>${supplierFinance.totalNetAssets }</td>
							<td class="bggrey">营业收入（万元）：</td>
							<td>${supplierFinance.taking }</td>
							<td class="bggrey">财务利润表</td>
							<td><a class="mt3 color7171C6" href="javascript:download('${supplierFinance.profitListId}', '${sysKey}')">${supplierFinance.profitList}</a></td>
						</tr>
						<tr>
							<td class="bggrey">审计报告的审计意见</td>
							<td><a class="mt3 color7171C6" href="javascript:download('${supplierFinance.auditOpinionId}', '${sysKey}')">${supplierFinance.auditOpinion}</a></td>
							<td class="bggrey ">资产负债表</td>
							<td><a class="mt3 color7171C6" href="javascript:download('${supplierFinance.liabilitiesListId}', '${sysKey}')">${supplierFinance.liabilitiesList}</a></td>
							<td class="bggrey">现金流量表</td>
							<td><a class="mt3 color7171C6" href="javascript:download('${supplierFinance.cashFlowStatementId}', '${sysKey}')">${supplierFinance.cashFlowStatement}</a></td>
						</tr>
						<tr>
							<td class="bggrey">所有者权益变动表</td>
							<td colspan="5"><a class="mt3 color7171C6" href="javascript:download('${supplierFinance.changeListId}', '${sysKey}')">${supplierFinance.changeList}</a></td>
						</tr>
					</tbody>
				</table>
				<div class="col-md-12 tc mt20">
					<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
				</div>
			</div>
		</div>
	</body>

</html>