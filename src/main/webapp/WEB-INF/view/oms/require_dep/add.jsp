<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript">
	function save(){
		var index = parent.layer.getFrameIndex(window.name); 
		$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/saveOrg.do?",
		    data: $("#formID").serialize(),
		    success: function(data) {
		        truealert(data.message,data.success == false ? 5:1);
		    }
		});
		
	}
	function truealert(text,iconindex){
		layer.open({
		    content: text,
		    icon: iconindex,
		    shade: [0.3, '#000'],
		    yes: function(index){
		        //do something
		    	 layer.closeAll();
		    	 parent.layer.close(index); //执行关闭
		    }
		});
	}
</script>
</head>
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
				<li class="active"><a href="#">需求部门管理</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 修改订列表开始-->
	<div class="container">
		<form action="<%=basePath%>purchaseManage/create.do" method="post" id="formID">
			<div>
				<div class="headline-v2">
					<h2>新增需求部门</h2>
				</div>
				<ul class="list-unstyled list-flow p0_20">
					<li class="col-md-6 p0"><span class="">名称：</span>
						<div class="input-append">
							<input class="span2" name="name" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">地址：</span>
						<div class="input-append">
							<input class="span2" name="addr" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">手机号：</span>
						<div class="input-append">
							<input class="span2" name="phone" 
								type="password"> <span class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">邮编：</span>
						<div class="input-append">
							<input class="span2" name="postCode" type="password"> <span
								class="add-on">i</span>
						</div></li>
					</li>

				</ul>
			</div>

			<div class="col-md-12">
				<div class="fl padding-10">
					<button onclick="save();" class="btn btn-windows save" type="button">保存</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
