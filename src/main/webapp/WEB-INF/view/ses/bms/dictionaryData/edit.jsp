<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
	<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
	<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
	<!--[if !IE]><!-->
	<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>public/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>public/ztree/css/zTreeStyle.css" type="text/css">
	
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/lodop/LodopFuncs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.excheck.js"></script>
</head>
<script type="text/javascript">
	function goback(){
		var currpage = $("#currpage").val();
		var kind = $("#k").val();
		window.location.href = '<%=basePath%>dictionaryData/list.html?page='+currpage+'&kind='+kind;
	}
</script>
<body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
	   <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   	<li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">数据字典</a></li><li class="active"><a href="#">修改数据字典</a></li>
		   </ul>
		   <div class="clear"></div>
	   </div>
   </div>
   <div class="container bggrey border1 mt20">
   	   <sf:form action="${pageContext.request.contextPath}/dictionaryData/update.html" method="post" modelAttribute="dd">
		   <div>
			   <div class="headline-v2 bggrey">
			   		<h2>修改数据字典</h2>
			   </div>
			   <input type="hidden" name="id" id="dId" value="${dd.id }">
			   <input type="hidden" name="kind" id="k" value="${dd.kind }">
			   <input type="hidden" name="currpage" id="currpage" value="${currpage }">
			   <ul class="list-unstyled list-flow ul_list">
			   	 	<li class="col-md-6 p0">
					   	<span class="span2"><div class="fr">编码：</div><div class="red">*</div></span>
					   	<div class="input-append pr">
					        <input class="span2" name="code" value="${dd.code }" maxlength="40" type="text">
					        <span class="add-on">i</span>
					        <div class="b f14 red tip pa l260"><sf:errors path="code"/></div>
					        <div class="b f14 ml10 red hand">${exist}</div>
				       	</div>
				 	</li>
				 	<li class="col-md-6 p0">
					   	<span class="span2"><div class="fr">名称：</div><div class="red">*</div></span>
					   	<div class="input-append pr">
					        <input class="span2" name="name" value="${dd.name }"  type="text">
					        <span class="add-on">i</span>
					        <div class="b f14 red tip pa l260"><sf:errors path="name"/></div>
				       	</div>
				 	</li>
				 	<li class="col-md-12 p0">
					   	<span class="span2">描述：</span>
					   	<div class="col-md-12 pl200 fn mt5 pwr9">
				        	<textarea class="text_area col-md-12 " name="description"  title="" placeholder="请输入100字以内中文描述">${dd.description }</textarea>
				       	</div>
				 	</li>
			   	</ul>
		   </div> 
	       <div class="col-md-12 tc mt20" >
			   <button class="btn btn-windows reset"  type="submit">更新</button>
			   <button class="btn btn-windows back" onclick="goback()" type="button">返回</button>
       	   </div>
  	   </sf:form>
   </div>
</body>
</html>
