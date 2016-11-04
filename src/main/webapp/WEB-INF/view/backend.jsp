<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
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
<link href="<%=basePath%>public/ZHH/css/common.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/animate.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/ui-dialog.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/dialog-select.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fileupload-ui.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/zTreeStyle.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/sky-forms.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/custom-sky-forms.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fancybox.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.carousel.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.theme.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style-switcher.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shortcode_timeline2.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blocks.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/datepicker.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/WdatePicker.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/select2.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons-inversed.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blog_magazine.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_log_reg_v1.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v4.css" media="screen"
    rel="stylesheet">


<link href="<%=basePath%>public/accordion/SpryAccordion.css"
    media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/masterslider.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/james.css" media="screen"
    rel="stylesheet">
<link href="<%=basePath%>public/supplier/css/supplieragents.css"
    media="screen" rel="stylesheet">
<script src="<%=basePath%>public/ZHH/js/hm.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/back-to-top.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.query.js"></script>
<script src="<%=basePath%>public/ZHH/js/dialog-plus-min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fancybox.pack.js"></script>
<script src="<%=basePath%>public/ZHH/js/smoothScroll.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.parallax.js"></script>
<script src="<%=basePath%>public/ZHH/js/app.js"></script>
<script src="<%=basePath%>public/ZHH/js/common.js"></script>
<script src="<%=basePath%>public/ZHH/js/dota.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/fancy-box.js"></script>
<script src="<%=basePath%>public/ZHH/js/style-switcher.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl.carousel.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl-carousel.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl-recent-works.js"></script>
<script
    src="<%=basePath%>public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/WdatePicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.form.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.maskedinput.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-ui.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/masking.js"></script>
<script src="<%=basePath%>public/ZHH/js/datepicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/timepicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/dialog-select.js"></script>
<script src="<%=basePath%>public/ZHH/js/locale.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.ui.widget.js"></script>
<script src="<%=basePath%>public/ZHH/js/load-image.js"></script>
<script src="<%=basePath%>public/ZHH/js/canvas-to-blob.js"></script>
<script src="<%=basePath%>public/ZHH/js/tmpl.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.iframe-transport.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload-fp.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload-ui.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-fileupload.js"></script>
<script src="<%=basePath%>public/ZHH/js/form.js"></script>
<script src="<%=basePath%>public/ZHH/js/select2.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/select2_locale_zh-CN.js"></script>
<script src="<%=basePath%>public/ZHH/js/application.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.counterup.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/modernizr.js"></script>
<script src="<%=basePath%>public/ZHH/js/touch.js"></script>
<script src="<%=basePath%>public/ZHH/js/product-quantity.js"></script>
<script src="<%=basePath%>public/ZHH/js/master-slider.js"></script>
<script src="<%=basePath%>public/ZHH/js/shop.app.js"></script>
<script src="<%=basePath%>public/ZHH/js/masterslider.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.easing.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/james.js"></script>
<script src="<%=basePath%>public/accordion/SpryAccordion.js"
    type="text/javascript"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
$(function(){
    laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${listTodosf.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${listTodosf.total}",
          startRow: "${listTodosf.startRow}",
          endRow: "${listTodosf.endRow}",
          groups: "${listTodosf.pages}">=5?5:"${listTodosf.pages}", //连续显示分页数
          curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
              var page = location.search.match(/page=(\d+)/);
              return page ? page[1] : 1;
          }(), 
          jump: function(e, first){ //触发分页后的回调
              if(!first){ //一定要加此判断，否则初始时会无限刷新
                $("#page").val(e.curr);
               $("#form2").submit();
              }
          }
      });
    
    $("#menu a").click(function() {
        $('#menu li').each(function(index) {
            $(this).removeClass('active');  // 删除其他兄弟元素的样式
          });
        $(this).parent().addClass('active');                            // 添加当前元素的样式
    });
    
});
</script>
</head>
<body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="#"> 首页</a></li>
                <li><a href="#" class="active">后台管理</a></li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>

    <!-- 后台管理内容开始-->
    <div class="container content height-350 job-content ">

        <div class="row magazine-page">
            <div class="col-md-3 col-md-12 padding-0">
                <div class="col-md-12 p0_10 margin-bottom-20">
                    <div class="tag-box tag-box-v3 margin-0 p0_10">
                        <div class="margin-0">
                            <h2 class="margin-0 news cursor"
                                onclick="window.location.href='<%=basePath%>StationMessage/listStationMessage.do'">通知</h2>
                        </div>
                        <ul 
                            class="padding-left-20  categories hex padding-bottom-5 padding-top-5" >
                            <c:forEach items="${stationMessage }" var="station">
                                <li><a
                                    href="<%=basePath%>${station.url}" title="${station.name }"> <c:choose>
                                            <c:when test="${fn:length(station.name) > 10}">  
                                           ${fn:substring(station.name, 0, 14)}......
                                        </c:when>
                                            <c:otherwise>  
                                          ${station.name }
                                        </c:otherwise>
                                        </c:choose>
                                </a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-9 padding-0">
                <div class="col-md-12 tab-v2 job-content">
                    <div class="">
                        <ul class="nav nav-tabs" id="menu">
                            <li class="active"><a  href="<%=basePath%>todo/todos.html" target="open_main" class=" f18">待办事项</a></li>
                            <li class=""><a  href="<%=basePath%>todo/havetodo.html" target="open_main"  class=" f18">已办事项</a></li>
                        </ul>
                         <script type="text/javascript" language="javascript">   
                        function iFrameHeight() {   
                        var ifm= document.getElementById("open_main");   
                        var subWeb = document.frames ? document.frames["open_main"].document : ifm.contentDocument;   
                        if(ifm != null && subWeb != null) {
                           ifm.height = subWeb.body.scrollHeight;
                           /*ifm.width = subWeb.body.scrollWidth;*/
                        }   
                        }   
                        </script>
                      <!-- 右侧内容开始-->
                      <div class="">
                         <iframe  frameborder="0" name="open_main" id="open_main" scrolling="no" marginheight="0"  width="100%" onLoad="iFrameHeight()" src="<%=basePath%>todo/todos.html" ></iframe>
                      </div>
                    </div>
                </div>
            </div>

        </div>
    </div>


</body>
</html>
