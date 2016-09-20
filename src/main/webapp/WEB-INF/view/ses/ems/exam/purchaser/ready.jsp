<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>准备页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		//生成考卷方法
		function generatePaper(){
			var paperNo = $("#paperNo").val().trim();
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=path%>/purchaserExam/entryPaperNumber.do?paperNo="+paperNo,
		       	success:function(data){
			    	if(data==1){
			    		window.location.href="<%=path%>/purchaserExam/timing.html?paperNo="+paperNo;
			    	}else if(data==0){
			    		alert("没有当前考卷");
			    	}
		       	}
		    });
		}
	</script>

  </head>
  
  <body>
    <div class="container tc">输入考试编号<input type="text" id="paperNo"/></div>
    <!-- 按钮 -->
  	<div class="padding-top-10 clear">
		<div class="col-md-12 pl200 ">
			<div class="mt40 tc mb50">
    			<input type="button" class="btn" value="生成考卷" onclick="generatePaper()"/>
    		</div>
    	</div>
    </div>
  </body>
</html>
