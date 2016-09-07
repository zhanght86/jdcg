<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>进口供应商注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript">
</script>
</head>

<body>
	<div class="wrapper">
		<div class="header-v4">
			<!-- Navbar -->
			<div class="navbar navbar-default mega-menu" role="navigation">
				<div class="container">
					<!-- logo和搜索 -->
					<div class="navbar-header">
						<div class="row container margin-bottom-10">
							<div class="col-md-8">
								<a href=""> <img alt="Logo" src="${pageContext.request.contextPath}/public/ZHQ/images/logo.png" id="logo-header"> </a>
							</div>
							<!--搜索开始-->
							<div class="col-md-4 mt50">
								<div class="search-block-v2">
									<div class="">
										<form accept-charset="UTF-8" action="" method="get">
											<div style="display:none">
												<input name="utf8" value="" type="hidden">
											</div>
											<input id="t" name="t" value="search_products" type="hidden">
											<div class="col-md-12 pull-right">
												<div class="input-group bround4">
													<input class="form-control h38" id="k" name="k" placeholder="" type="text"> <span class="input-group-btn"> <input class="btn-u h38" name="commit" value="搜索" type="submit"> </span>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
							<!--搜索结束-->
						</div>
					</div>

					<button data-target=".navbar-responsive-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
						<span class="full-width-menu">全部商品分类</span> <span class="icon-toggle"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </span>
					</button>
				</div>

				<div class="clearfix"></div>

				<div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse">
					<div class="container">
						<ul class="nav navbar-nav">
							<!-- 通知 -->
							<li class="active dropdown tongzhi_li"><a class=" dropdown-toggle p0_30" href=""><i class="tongzhi nav_icon"></i>通知</a></li>
							<!-- End 通知 -->

							<!-- 公告 -->
							<li class="dropdown gonggao_li"><a class=" dropdown-toggle p0_30" href=""><i class="gonggao nav_icon"></i>公告</a></li>
							<!-- End 公告 -->

							<!-- 公示 -->
							<li class="dropdown gongshi_li"><a data-toggle="dropdown" class="dropdown-toggle p0_30 " href=""><i class="gongshi nav_icon"></i>公示</a></li>
							<!-- End 公示 -->

							<!-- 专家 -->
							<li class="dropdown zhuanjia_li"><a href="#" class="dropdown-toggle p0_30 " data-toggle="dropdown"><i class="zhuanjia nav_icon"></i>专家</a></li>
							<!-- End 专家 -->

							<!-- 投诉 -->
							<li class="dropdown tousu_li"><a data-toggle="dropdown" class="dropdown-toggle p0_30" href=""><i class="tousu nav_icon"></i>投诉</a></li>
							<!-- End 投诉 -->

							<!-- 法规 -->
							<li class="dropdown  fagui_li"><a href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="fagui nav_icon"></i>法规</a></li>
							<!-- End 法规 -->

							<li class="dropdown luntan_li"><a aria-expanded="false" href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="luntan nav_icon"></i>论坛</a></li>

						</ul>
					</div>
				</div>

				<!--/end container-->
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/supplierFsInfo/registerStep7.html?id=${id }" method="post">
			<div class="container clear margin-top-30">
		  	<h2 class="padding-20 mt40" style="margin-left:280px;">
				<span class="new_step current fl"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step current fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表和承诺书</span> </span> <span class="new_step current fl"><i class="">5</i><span class="step_desc_01">申请表和承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
			<!-- 供应商申请书上传：<input type="file" name=""/>
			供应商承诺书上传：<input type="file" name=""/> -->
				   	<div class="input-append mt40" style="margin-left:280px;">
						<div class="uploader orange m0">
							<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
							<input type="button" name="file" class="button" value="选择文件..."/>
							<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
						</div>
						<div class="uploader orange m0">
							<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
							<input type="button" name="file" class="button" value="选择文件..."/>
							<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
						</div>
					</div>
			<div class="col-md-12 add_regist" style="margin-left:170px;">
				 <div class="fl mr20"><label class="regist_name">采购机构名称：</label><span class="regist_desc">广州军区物资采购站</span></div>
				 <div class="fl mr20"><label class="regist_name">采购机构联系人：</label><span class="regist_desc">张三</span></div>
				 <div class="fl mr20"><label class="regist_name">采购机构地址：</label><span class="regist_desc">广州军区物资采购站</span></div>
				 <div class="fl mr20"><label class="regist_name">联系电话：</label><span class="regist_desc">1882928798</span></div>
			 </div>
		    <div class="tc mt20 clear col-md-11">
		   		<button class="btn btn-windows git"   type="button" onclick="location='${pageContext.request.contextPath}/supplierFsInfo/registerStep6.html?id=${id }'">上一步</button>
				<button class="btn btn-windows git"   type="button" onclick="location='${pageContext.request.contextPath}/supplierFsInfo/registerStep7.html?id=${id }'">下一步</button>
			</div>
		</div>
		</form>
		<div class="footer-v2 clear " id="footer-v2">
			<div class="footer">
				<!-- Address -->
				<address class="">Copyright &#169 2016 版权所有：中央军委后勤保障部 京ICP备09055519号</address>
				<div class="">浏览本网主页，建议将电脑显示屏的分辨率调为1024*768</div>
				<!-- End Address -->
				<!--/footer-->
			</div>
		</div>
</body>
</html>
