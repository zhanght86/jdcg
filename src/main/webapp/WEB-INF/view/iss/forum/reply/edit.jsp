<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->	
	<script type="text/javascript">    
  
	</script>
  </head>
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a >论坛管理</a></li><li class="active"><a >回复管理</a></li><li class="active"><a >回复修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
    <form action="${ pageContext.request.contextPath }/reply/update.html" method="post">  
    <div>
    <div class="headline-v2">
	   		<h2 class="count_flow">修改回复</h2>
	   		</div>
	    <input  name ="replyId" type="hidden" value = '${reply.id}'>
	   <ul class="ul_list mb20">			 
			<li class="col-md-11 margin-0 padding-0 ">	  	 			
				<span class="col-md-12 padding-left-5"> <div class="red fl">*</div>回复内容：</span>			
				<div class="mb5">
	  				 <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
       			</div>		
				<div class="red clear f12">${ERR_content}</div>
				</div>			
	  	 	</li>
	  	 </ul>
	</div>  	
	<!-- 底部按钮 -->			          
    <div class="col-md-12 tc">     
    	<button class="btn btn-windows save" type="submit">更新</button>
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
     </form>
     </div>
 <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例

    var option ={
            toolbars: [[
                        'undo', 'redo', '|',
                        'bold', 'italic', 'underline',  'formatmatch', 'autotypeset', '|', 'forecolor', 'backcolor',                
                         'fontfamily', 'fontsize', '|',
                         'indent', '|',
                        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|','emotion',
                ]]

        }
    var ue = UE.getEditor('editor',option);
    var content='${reply.content}';
    ue.ready(function(){
        ue.setContent(content);    
    });
</script>
  </body>
</html>

