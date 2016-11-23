<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>

<html>
<head>
</head>
<script type="text/javascript">
	/** 全选全不选 */
	$(function() {

		$("#temType").val('${templet.temType}');

	});
</script>
<body>

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a>
				</li>
				<li><a href="#">支撑系统</a>
				</li>
				<li><a href="#">后台管理</a>
				</li>
				<li class="active"><a href="#">修改模板</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 修改订列表开始-->
	<div class="container container_box">
		<form action="${pageContext.request.contextPath}/templet/update.do"
			method="post">
			<input class="span2" name="id" type="hidden" value="${templet.id}">
			<div>
                <h2 class="list_title">修改模板</h2>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>模板名称</span>
                        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="input_group" name="name" type="text"
								value="${templet.name}">
								<span class="add-on">i</span>
						</div>
						<div id="contractCodeErr" class="clear red">${ERR_name}</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>模板类型</span>
							<select id="temType" name=temType class="w220">
								<option value="-请选择-">-请选择-</option>
								<option value="采购公告">采购公告</option>
								<option value="中标公告">中标公告</option>
								<option value="合同公告">合同公告</option>
								<option value="招标公告">招标公告</option>
								<option value="合同模板">合同模板</option>
							</select>
							<div id="contractCodeErr" class="clear red">${ERR_temType}</div>
					</li>
					<li class="col-md-12 col-sm-12 col-xs-12">
                      <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>模板内容</span>
                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
							<script id="editor" name="content" type="text/plain" class=""></script>
							<div id="contractCodeErr" class="clear red">${ERR_content}</div>
							<!-- <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea> -->
						</div></li>


				</ul>
				<div class="col-md-12 col-sm-12 col-xs-12 tc">
					<button class="btn btn-windows save" type="submit">保存</button>
					<button class="btn btn-windows back" onclick="history.go(-1)"
						type="button">返回</button>
				</div>
			</div>
		</form>

	</div>

	<script type="text/javascript">
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		var ue = UE.getEditor('editor');
		var content = '${templet.content}';
		ue.ready(function() {
			ue.setContent(content);
		});
	</script>
</body>
</html>
