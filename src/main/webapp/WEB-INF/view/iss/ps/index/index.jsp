<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	<link href="<%=basePath%>public/ZHQ/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/bootstrap.min.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/style.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/line-icons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/app.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/application.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/header-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/page_job.css" media="screen" rel="stylesheet">

<!--导航js-->
<script src="<%=basePath%>public/ZHQ/js/jquery.min.js"></script>
<script src="<%=basePath%>public/ZHQ/js/jquery_ujs.js"></script>
<script src="<%=basePath%>public/ZHQ/js/bootstrap.min.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript">
$(function(){
	$(document).keyup(function(event){
	  if(event.keyCode ==13){
	    login();
	  }
	});
})

function login(){
	if($("#inputEmail").val()==""){
		layer.tips("请输入用户名","#inputEmail",{
			tips : 1
		});		
	}else if($("#inputPassword").val()==""){
		layer.tips("请输入密码","#inputPassword",{
			tips : 1
		});		
	}else if($("#inputCode").val()==""){
		layer.tips("请输入验证码","#inputCode",{
			tips : 1
		});		
	}else{
		var index=layer.load();
		$.ajax({
			url:"<%=basePath%>login/login.html",
			type:"post",
			data:{loginName:$("#inputEmail").val(),password:$("#inputPassword").val(),rqcode:$("#inputCode").val()},
			success:function(data){
				if(data=="errorcode"){
					layer.tips("验证码不正确","#inputCode",{
						tips : 1
					});	
					layer.close(index);
				}else if(data=="errorlogin"){				
					layer.msg("用户名或密码错误！");
					layer.close(index);
				}else if(data=="nullcontext"){				
					layer.msg("请输入用户名密码或者验证码!");
				}else if(data=="scuesslogin"){				
					layer.close(index);
					window.location.href="<%=basePath%>login/index.html";
				}else if(data="deleteLogin"){
					layer.msg("账号不存在!");
					layer.close(index);
				}
				kaptcha();
			}
		});
	}

}
function kaptcha(){
	$("#kaptchaImage").hide().attr('src','Kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
}
</script>
</head>

<body>
  <div class="wrapper">
  <div class="head_top col-md-12">
   <div class="container">
    <div class="row">
     <div class="col-md-9">你好，欢迎来到中国军队采购网！<a href="" class="red">【请登录】</a></div>
	   <div class="col-md-3 head_right"> 
         <a href=" ">我的信息</a> |
         <a href=" ">意见反馈</a> |
         <a href=" ">采购系统首页</a>
	   </div>
	  </div>
    </div>
  </div>
  </div>
	<div class="header-v4 clear">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
	  <div class="head_main">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container margin-bottom-10">
            <div class="col-md-8 m20_0">
              <a href="">
                 <img alt="Logo" src="<%=path%>/public/ZHQ/images/logo.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-4 p0">
              <div class="search-block-v2">
                  <form accept-charset="UTF-8" action="" method="get">
				    <div style="display:none">
				     <input name="utf8" value="✓" type="hidden">
					</div>
                    <input id="t" name="t" value="search_products" type="hidden">
                    <div class="col-md-12 pull-left p0">
                      <div class="search-group">
                        <input class="search-input" id="k" name="k" placeholder="" type="text">
                        <span class="input-group-btn">
                          <input class="btn-search" name="commit" value="搜索" type="submit">
                        </span>
                      </div>
                    </div>
                  </form>
              </div>
            </div>
          <!--搜索结束-->
          </div>
		 </div>

          <button data-target=".navbar-responsive-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
            <span class="full-width-menu">全部商品分类</span>
            <span class="icon-toggle">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </span>
          </button>
      </div>
    </div>
    <div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse">
    <div class="container">
      <ul class="nav navbar-nav">
      <!-- 通知 -->
        <li class="active dropdown shouye_li mega-menu-fullwidth">
          <a class=" dropdown-toggle " href=""><i class="shouye nav_icon"></i>首 页</a>
        </li>
      <!-- End 通知 -->

      <!-- 公告 -->
        <li class="dropdown gonggao_li mega-menu-fullwidth">
          <a data-toggle="dropdown" class="dropdown-toggle " href="javascript:void(0);" ><i class="gonggao nav_icon"></i>信息公告</a>
	  <!--	信息公告鼠标移动开始   -->
	  <div class="drop_next dropdown-menu" >
	   <div class="row magazine-page clear">
	    <div class="col-md-12 drop_hover">
	    <div class="drop_main">
	    <div class="col-md-4">
	    <div class="headline-v2">
         <h2>采购公告</h2>
        </div>
		<div class="border1 margin-bottom-10 login_box job-content">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-21" data-toggle="tab"> 物资</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-22" data-toggle="tab"> 工程</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-23" data-toggle="tab"> 服务</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-24" data-toggle="tab">进口</a></li>
		 </ul>
		</h2>
		<div class="tab-content buyer_list">
		    <div id="tab-21" class="categories tab-pane fade active in">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-22" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-23" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-24" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	  </div>
	 </div>
	 </div>

	   <div class="col-md-4">
	    <div class="headline-v2">
         <h2>中标公告</h2>
        </div>
		<div class="border1 margin-bottom-10 login_box job-content">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-21" data-toggle="tab"> 物资</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-22" data-toggle="tab"> 工程</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-23" data-toggle="tab"> 服务</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-24" data-toggle="tab">进口</a></li>
		 </ul>
		</h2>
		<div class="tab-content  buyer_list">
		    <div id="tab-21" class="categories tab-pane fade active in">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-22" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-23" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-24" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	  </div>
	 </div>
	 </div>
		   <div class="col-md-4">
	    <div class="headline-v2">
         <h2>单一来源公告</h2>
        </div>
		<div class="border1 margin-bottom-10 login_box job-content">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-21" data-toggle="tab"> 物资</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-22" data-toggle="tab"> 工程</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-23" data-toggle="tab"> 服务</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-24" data-toggle="tab">进口</a></li>
		 </ul>
		</h2>
		<div class="tab-content  buyer_list">
		    <div id="tab-21" class="categories tab-pane fade active in">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-22" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-23" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-24" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	  </div>
	 </div>
	</div>
   </div>
   </div>
  </div>
 </div>
        </li>
      <!-- End 公告 -->

      <!-- 公示 -->
        <li class="dropdown gongshi_li mega-menu-fullwidth" >
          <a data-toggle="dropdown" class="dropdown-toggle  " href="javascript:void(0);"><i class="gongshi nav_icon"></i>网上采购</a>
<!--	网上采购鼠标移动开始   -->
		  <div class="drop_next dropdown-menu" >
	   <div class="row magazine-page clear">
      <div class="col-md-12 drop_hover">
	   <div class="drop_main ">
	    <div class="col-md-4 mt25" id="drop-1">
		  <div class="ywbl_01">
	       <a href="#" class="wssc">
            <span>网上商城</span> 
		   </a>
	      </div>
		  <div class="ywbl_01">
	       <a href="#" class="dxcpjj">
            <span>定型产品竞价</span> 
		   </a>
	     </div>
		 <div class="ywbl_01">
	      <a href="#" class="ypcg">
           <span>药品采购</span> 
		  </a>
	     </div>
		 <div class="ywbl_01">
	      <a href="#" class="fwcg">
           <span>服务采购</span> 
		  </a>
	   </div>
		</div>
		<div class="col-md-8 mt10">
		<div class="margin-bottom-10 login_box job-content">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-24" data-toggle="tab"> 需求公告 </a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-25" data-toggle="tab"> 成交公告</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-26" data-toggle="tab"> 废标公告</a></li>
		 </ul>
		</h2>
		<div class="tab-content">
		    <div id="tab-24" class="categories tab-pane fade active in">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-25" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-26" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		</div>
		</div>
	  </div>
	 </div>
	</div>
	</div>
	</div>
	
        </li>
      <!-- End 公示 -->

      <!-- 专家 -->
        <li class="dropdown zhuanjia_li mega-menu-fullwidth">
          <a class="dropdown-toggle  " data-toggle="dropdown" href="javascript:void(0);"><i class="zhuanjia nav_icon"></i>供应商</a>
	<!--供应商鼠标移动开始-->
		<div class="drop_next dropdown-menu" >
	     <div class="row magazine-page clear">
	     <div class="col-md-12 drop_hover" >
	      <div class="drop_main">
	       <div class="col-md-2 mt20 supp_login">
	        <a href="#">
		     供应商注册
		     <i></i>
		    </a>	  
	       </div>
	  <div class="col-md-4 mt10 ml30">
	   <div class="headline-v2">
         <h2>供应商名录<a  href="" class="fr f14">更多>></a></h2>
        </div>
         <div class="job-content col-md-12">
		    <div class="categories">
             <ul class="p0_10 list-unstyled">   
              <li></li>
			 </ul>
			</div>
		  </div>
	  </div>
	  
		<div class="login_box job-content col-md-5 mt10">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-30" data-toggle="tab">诚信记录</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-31" data-toggle="tab"> 军队处罚公告</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-32" data-toggle="tab"> 地方处罚公告</a></li>
		 </ul>
		</h2>
		<div class="tab-content">
		    <div id="tab-30" class="categories tab-pane fade active in">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-31" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-32" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		</div>
	  </div>
	</div>
   </div>
   </div>
   </div>
        </li>
      <!-- End 专家 -->

      <!-- 投诉 -->
        <li class="dropdown tousu_li mega-menu-fullwidth">
          <a data-toggle="dropdown" class="dropdown-toggle " href="javascript:void(0);" ><i class="tousu nav_icon"></i>评审专家</a>
	<!--评审专家鼠标移动开始-->
		 <div class="drop_next dropdown-menu" >
	   <div class="row magazine-page clear">
	<div class="col-md-12 drop_hover"  id="drop-4">
	 <div class="drop_main">
	  <div class="col-md-2 mt20 supp_login">
	    <a href="<%=basePath%>expert/toRegisterNotice.html">
		 专家注册
		 <i></i>
		</a>	  
	  </div>
	  <div class="col-md-4 mt10 ml30">
	   <div class="headline-v2">
         <h2>专家名录<a href="#" class="fr f14">更多>></a></h2>
        </div>
         <div class="job-content col-md-12">
		    <div class="categories zhuanjia_list">
               <a href="#" title=" " target="_blank">张三</a>
			</div>
		  </div>
	  </div>
	  
		<div class="login_box job-content col-md-5 mt10">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-30" data-toggle="tab">诚信记录</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-31" data-toggle="tab">处罚公告</a></li>
		 </ul>
		</h2>
		<div class="tab-content">
		    <div id="tab-32" class="categories tab-pane fade active in">
             <ul class="p0_10">   
              <li></li>
          </ul>
          </div>
		    <div id="tab-33" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		</div>
	  </div>
	</div>
   </div>
   </div>
   </div>
        </li>
      <!-- End 投诉 -->

      <!-- 法规 -->
        <li class="dropdown  cgfw_li mega-menu-fullwidth">
          <a class="dropdown-toggle " data-toggle="dropdown" href="javascript:void(0);"><i class="cgfw nav_icon"></i>采购服务</a>
	<!--采购服务鼠标移动开始-->
		  <div class="drop_next dropdown-menu" >
	   <div class="row magazine-page clear">

	<div class="col-md-12 drop_hover">
	 <div class="drop_main supp_service">
	 <div class="col-md-2 mt60">
	  <a href="">
	    <div class="col-md-12 tc"><img src="<%=path%>/public/ZHQ/images/cpml.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">产品目录</div>
	  </a>
	 </div>
	 <div class="col-md-2 mt60">
	  <a href="">
	    <div class="col-md-12 tc"><img src="<%=path%>/public/ZHQ/images/jscsk.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">技术参数库</div>
	  </a>
	 </div>
	 <div class="col-md-2 mt60">
	  <a href="">
	    <div class="col-md-12 tc"><img src="<%=path%>/public/ZHQ/images/cglt.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">采购论坛</div>
	  </a>
	 </div>
	 <div class="col-md-2 mt60">
	  <a href="">
	    <div class="col-md-12 tc"><img src="<%=path%>/public/ZHQ/images/zlxz.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">资料下载</div>
	  </a>
	 </div>
	 <div class="col-md-2 mt60">
	  <a href="">
	    <div class="col-md-12 tc"><img src="<%=path%>/public/ZHQ/images/yjfk.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">意见反馈</div>
	  </a>
	 </div>
	 <div class="col-md-2 mt60">
	  <a href="">
	    <div class="col-md-12 tc"><img src="<%=path%>/public/ZHQ/images/shfw.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">售后服务</div>
	  </a>
	 </div>
	</div>
    </div>
	</div>
   </div>
   
        </li>
      <!-- End 法规 -->

        <li class="dropdown luntan_li mega-menu-fullwidth">
          <a href="javascript:void(0);" class="dropdown-toggle " data-toggle="dropdown"><i class="luntan nav_icon"></i>投诉处理</a>
	<!-- 投诉处理鼠标移动开始-->
	<div class="drop_next dropdown-menu" >
	 <div class="row magazine-page clear">
     <div class="col-md-12 drop_hover"  id="drop-6">
	  <div class="drop_main">
	   <div class="col-md-4 mt20">
	    <a href="#">
	     <div class="col-md-12 tc p0"><img src="<%=path%>/public/ZHQ/images/tou_pic.jpg"/></div>
		 <div class="col-md-12 f22 tc mt20 p0">在线投诉</div>
		</a>
	  </div>
	  <div class="col-md-8 mt10">
	  	<div class="margin-bottom-10 login_box job-content col-md-8">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a>投诉处理公告</a></li>
		 </ul>
		</h2>
		<div class="tab-content">
		    <div id="tab-27" class="categories tab-pane fade active in">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		</div>
	 </div>
	 </div>
	</div>
	</div>
	</div> 
   </div>
   
        </li>
        <li class="dropdown fagui_li mega-menu-fullwidth">
          <a class="dropdown-toggle " data-toggle="dropdown" href="javascript:void(0);"><i class="fagui nav_icon"></i>采购法规</a>
    <!--采购法规鼠标动开始-->
	<div class="drop_next dropdown-menu" >
	   <div class="row magazine-page clear">
      <div class="col-md-12 drop_hover"  id="drop-7">
	   <div class="drop_main">
		<div class="margin-bottom-10 login_box job-content col-md-5 mt10">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-27" data-toggle="tab">国家采购法规</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-28" data-toggle="tab"> 军队采购法规</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-29" data-toggle="tab"> 重要业务通知</a></li>
		 </ul>
		</h2>
		<div class="tab-content">
		    <div id="tab-27" class="categories tab-pane fade active in">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-28" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-29" class="categories tab-pane fade">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
		</div>
		</div>
      <div class="col-md-7 mt20">
	  <div class="col-md-12">
	  <div class="col-md-5">
	    <div class="col-md-12 fg_rule">
		 <img src="<%=path%>/public/ZHQ/images/fg_01.jpg" class="fl" style="width:100%; height:55%;"/>
		</div>
	  </div>
	  <div class="col-md-5">
		 <a href="">主题：2016年9月30日烈士纪念日</a>
	  </div>
	  </div>
	  <div class="col-md-12 mt20">
         <div class="job-content col-md-12">
		    <div class="categories">
             <ul class="p0_10 list-unstyled">   
              <li></li>
			 </ul>
			</div>
		  </div>
	   </div>
	  </div>  
	  </div>
	 </div>
	</div>
	</div>
   </li>

      </ul>
	  





  <!--/end container-->
   </div>
  </div>
  <!-- End Navbar -->
  <div class="container content job-content ">
     <div class="row magazine-page clear">
      <div class="col-md-5  margin-bottom-10">
        <div class="section-focus-pic" id="section-focus-pic">
	      <div class="pages" data-scro="list">
		   <ul>
			<li class="item" style="left:0px;">
				<a href="#" target="_blank"><img src="<%=path%>/public/ZHQ/images/1.jpg" width="100%" height="100%"></a>
			</li>
			<li class="item">
				<a href="#" target="_blank"><img src="<%=path%>/public/ZHQ/images/2.jpg" width="100%" height="100%"></a>
			</li>
			<li class="item">
				<a href="#" target="_blank"><img src="<%=path%>/public/ZHQ/images/3.jpg" width="100%" height="100%"></a>
			</li>
		   </ul>
	      </div>
	      <div class="controler" data-scro="controler">
		   <b class="down">1</b>
		   <b>2</b>
		   <b>3</b>
	      </div>
        </div>
        <script src="<%=basePath%>public/ZHQ/js/script.js"></script>
       </div>
       <div class="col-md-4 ">
        <div class="tab-v1">
          <h2 class="nav nav-tabs bb1 mt0">
            <span class="bg12_white"><a href="#">工作动态</a></span>
		  </h2>
		  </div>
          <div class="">
                <ul class="list-unstyled categories list_common">            
                   <li></li>
                </ul>
          </div>
       </div>
  <!-- Begin 登录 -->
  <div class="col-md-3">

    <div class="border1 margin-bottom-10 login_box">
        <h2 class="f17">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-1" data-toggle="tab"> 用户登录</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-2" data-toggle="tab"> CA登录</a></li>
		 </ul>
		</h2>
	<div class="tab-content p17-0">
	 <div id="tab-1" class="tab-pane fade active in">
      <form class="form-horizontal p15_0">
        <div class="control-group ">
          <label class="control-label" for="inputEmail">用户名：</label>
			  <div class="controls padding-right-10">
            <input type="text" id="inputEmail" class="form-control" placeholder="请输入用户名"/>
          </div>
        </div>
        <div class="control-group padding-right-10 margin-top-10">
          <label class="control-label" for="inputPassword">密码：</label>
          <div class="controls">
          <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码">
          </div>
        </div>
        <div class="control-group  margin-top-10 ">
         <label class="control-label" for="inputPassword">验证码：</label>
        <div class="controls">
          <input type="text" placeholder="" id="inputCode" class="input-mini fl ">
          	<img src="Kaptcha.jpg" onclick="kaptcha();" id="kaptchaImage" />
        </div>
       </div>
       <div class="control-group margin-top-22 clear ml30">
          <button class="btn" type="button" onclick="login();">登录</button>
      </div>
    </form>
  </div>
	 <div id="tab-2" class="tab-pane fade">
      <form class="form-horizontal p15_0">
        <div class="control-group ">
          <label class="control-label" for="inputEmail">用户名：</label>
			  <div class="controls padding-right-10">
            <input type="text" id="inputEmail" class="form-control" placeholder="请输入用户名"/>
          </div>
        </div>
        <div class="control-group padding-right-10 margin-top-10">
          <label class="control-label" for="inputPassword">密码：</label>
          <div class="controls">
          <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码">
          </div>
        </div>
        <div class="control-group  margin-top-10 ">
        <label class="control-label" for="inputPassword">验证码：</label>
        <div class="controls">
          <input type="text" placeholder="" id="inputCode" class="input-mini fl ">
          	<img src="Kaptcha.jpg" onclick="kaptcha();" id="kaptchaImage" />
        </div>
       </div>
       <div class="control-group margin-top-22 clear ml30">
          <button class="btn login_btn" type="submit">登录</button>
      </div>
    </form>
  </div>
  </div>
  </div>
  </div>
  </div>
 </div>
</div>

  
  
<!-- /* 集中采购开始*/-->
  <div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
    集中采购
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">采购公告</span>
		  <ul class="list-unstyled col-md-7 buyer_news">
		    <li class="active fl"><a aria-expanded="true" href="#tab-3" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-4" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-5" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-3" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-4" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-5" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div>
	
       <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">中标公告</span>
		  <ul class="list-unstyled buyer_news col-md-7">
		    <li class="active fl"><a aria-expanded="true" href="#tab-6" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-7" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-8" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-6" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-7" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-8" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div>
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">单一来源公告</span>
		  <ul class="list-unstyled buyer_news col-md-7">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-9" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-10" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-11" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-9" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-10" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-11" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div> 
  </div> 
</div>
<!--部队采购开始-->

 <div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
    部队采购
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">采购公告</span>
		  <ul class="list-unstyled mt0 fr col-md-7 p0">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-12" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-13" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-14" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-12" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-13" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-14" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div>
	
       <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">中标公告</span>
		  <ul class="list-unstyled mt0 fr col-md-7 p0">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-15" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-16" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-17" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-15" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-16" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-17" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div>
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">单一来源公告</span>
		  <ul class="list-unstyled mt0 fr col-md-7 p0">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-18" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-19" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-20" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-18" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-19" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-20" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div>  
  </div>
</div>
<!--业务办理开始-->
<div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
    业务办理
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
    <div class="col-md-12">
     <div class="border1 flow_btn fl mr14">
	   <div class="ywbl_01">
	     <a href="${pageContext.request.contextPath}/supplier/registration_page.html" class="qyzc">
          <span>企业注册</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="<%=basePath%>expert/toRegisterNotice.html" class="zjzc">
          <span>专家注册</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="#" class="jksdj">
          <span>进口商登记</span> 
		 </a>
	   </div>
	 </div>
	 
     <div class="border1 flow_btn fl">
	   <div class="ywbl_01">
	     <a href="#" class="wssc">
          <span>网上商城</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="#" class="dxcpjj">
          <span>定型产品竞价</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="#" class="ypcg">
          <span>药品采购</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="#" class="fwcg">
          <span>服务采购</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="#" class="fwrx">
          <span>服务热线</span> 
		 </a>
	   </div>
	 </div>
	 
	 
     <div class="border1 flow_btn clear">
	   <div class="ywbl_02">
	     <a href="#" class="cpml">
          <span>产品目录</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="jscsk">
          <span>技术参数库</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="zlxz">
          <span>资料下载</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="cpshfw">
          <span>产品售后服务</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="zxts">
          <span>在线投诉</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="cglt">
          <span>采购论坛</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="yjfk">
          <span>意见反馈</span> 
		 </a>
	   </div>
	 </div>
	 </div>
   </div>
</div>

<!--采购信息开始-->
<div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
      采购信息
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
      <div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   重要通知<a class="news_more">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	</div>
	</div>
	<div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   采购法规<a class="news_more">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	</div>
	</div>
      <div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   投诉处理公告<a class="news_more">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	</div>
	</div>
      <div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   处罚公告<a class="news_more">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	</div>
	</div>
  </div>
</div>
<!--底部代码开始-->
<div class="footer-v2" id="footer-v2">

      <div class="footer">

            <!-- Address -->
              <address class="">
			  Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->
       </div>
	 
<!--/footer--> 
    </div>
</div>
</div>
<!--[if lt IE 9]>
    <script src="/assets/plugins/respond.js?body=1"></script>
<script src="/assets/plugins/html5shiv.js?body=1"></script>
<script src="/assets/plugins/html5.js?body=1"></script>
<script src="/assets/plugins/placeholder-IE-fixes.js?body=1"></script>
<script src="/assets/ie_9.js?body=1"></script>
<![endif]-->

</body>
</html>
