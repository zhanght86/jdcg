<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人得分页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		var score = "${score}";
		var pass = "${pass}";
		$(function(){
			if(score < pass){
				$("#isPass").html("很遗憾,您未通过本场考试!");
			}else{
				$("#isPass").html("恭喜您通过了本场考试");
			}
		})
		
		//退出
		function exitExam(){
			layer.confirm('您确定要退出吗?', {title:'提示',offset: ['30%','40%'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/exitExam.html";
			});
		}
	</script>
	
  </head>
  
  <body onload="countTime()">
  	<div class="container tc"> 
  		 <div class="score_box border1">
  		    <div><span class="f18">得分：</span><span class="f22 red">${score }</span><span class="f18">分</span></div>
  			<div id="isPass" class="f18 mt10"></div>
  			<div class="mt20">
	  			<button type="button" class="btn" onclick="exitExam()" id="exitExam">退出</button>
  		    </div>	  
  		 </div>
  	</div>
  </body>
</html>
