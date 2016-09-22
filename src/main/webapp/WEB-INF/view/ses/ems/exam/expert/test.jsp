<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title>开始考试页面</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<script type="text/javascript">
		$(function(){
			//document.getElementById("second").innerHTML = ${examRule.testTime} + "分钟" + 0 + "秒"; 
          	//默认显示第一页
			var exam = document.getElementsByName("exam");
       		for(var i=1;i<=exam.length;i++){
       			if(i==1){
       				$("#pageNum"+i).show();
       			}else{
       				$("#pageNum"+i).hide();
       			}
       			
       		}
          	
        });
		
		//答题时上一页下一页切换
        function setTab(index){
          	var exam = document.getElementsByName("exam");
          	for(var i=1;i<=exam.length;i++){
          		if(index==i){
          			$("#pageNum"+index).show();
          		}else{
          			$("#pageNum"+i).hide();
          		}
          	}
        }
		
		//考试倒计时
        /**var timeLeft = ${examRule.testTime}*60*1000-1000;//这里设定时间
		function countTime(){ 
		     if(timeLeft == 0){//这里就是时间到了之后应该执行的动作了，这里只是弹了一个警告框 
		         alert("123"); 
		         return; 
		     }
		     var startMinutes = parseInt(timeLeft / (60 * 1000), 10); 
		     var startSec = parseInt((timeLeft - startMinutes * 60 * 1000)/1000); 
		     document.getElementById("second").innerHTML = startMinutes + "分钟" + startSec + "秒"; 
		     timeLeft = timeLeft - 1000; 
		     setTimeout('countTime()',1000); 
		 }*/
	</script>
  </head>
  
  <body>
  <div class="container">
  <div class="col-md-12 mb10 border1 bggrey">
  	<div class="fl f18 gary b">XXX考试进行中</div>
  	<%--<div class="fr red mt5" id="time">距离考试还有<span id="second"></span></div>
  --%></div>
  <form action="<%=path %>/expertExam/saveScore.html" method="post">
  <c:choose>
  	<c:when test="${pageSize==1 }">
	  <table class="clear table table-bordered table-condensed" id="pageNum1" name="exam">
	    <tbody>
		    <c:forEach items="${queRandom }" var="que" varStatus="l">
		      <tr>
		        <td class="col-md-1 tc">${l.index+1 }</td>
		        <td class="col-md-11">
		          <div>${que.topic }</div>
		          <div class="mt10">
		          <c:if test="${que.examQuestionType.name=='单选题' }">
				    	<input type="radio" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(que.items,';')[0]}
				    	<input type="radio" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(que.items,';')[1]}
				    	<input type="radio" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(que.items,';')[2]}
				    	<input type="radio" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(que.items,';')[3]}
				  </c:if>
				  <c:if test="${que.examQuestionType.name=='多选题' }">
				    	<input type="checkbox" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(que.items,';')[0]}
				    	<input type="checkbox" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(que.items,';')[1]}
				    	<input type="checkbox" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(que.items,';')[2]}
				    	<input type="checkbox" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(que.items,';')[3]}
				   </c:if>
		          </div>
		        </td>
		      </tr>
		    </c:forEach>
	    </tbody>
	  </table>
	  	<div class="col-md-12 tc">
	    	<button class="btn btn-windows save" type="submit">提交</button>
	  	</div>
  </c:when>
  <c:otherwise>
  		<c:forEach items="${pageNum }" varStatus="p">
  		<c:choose>
  		<c:when test="${p.first}">
  		<div id="pageNum${p.index+1 }" name="exam">
  			<table class="clear table table-bordered table-condensed">
		  	
			    <c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				    <tr>
		       			 <td class="col-md-1 tc">${l.index+1 }</td>
				    	<td class="col-md-11">
				          <div>${que.topic }</div>
				          <div class="mt10">
				    			<c:if test="${que.examQuestionType.name=='单选题' }">
				    				<input type="radio" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(que.items,';')[0]}
				    				<input type="radio" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(que.items,';')[1]}
				    				<input type="radio" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(que.items,';')[2]}
				    				<input type="radio" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(que.items,';')[3]}
				    			</c:if>
				    			<c:if test="${que.examQuestionType.name=='多选题' }">
				    				<input type="checkbox" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(que.items,';')[0]}
				    				<input type="checkbox" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(que.items,';')[1]}
				    				<input type="checkbox" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(que.items,';')[2]}
				    				<input type="checkbox" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(que.items,';')[3]}
				    			</c:if>
				    		 </div>
		        		</td>
		     		 </tr>
			    </c:forEach>
			    
		    </table>
		    <div class="col-md-12 tc">
    			<button class="btn" onclick="setTab(${p.index+2})" type="button">下一页</button>
  			</div>
		   </div>
		    </c:when>
		    
		    <c:when test="${p.last}">
		    <div id="pageNum${p.index+1 }" name="exam">
		    <table class="clear table table-bordered table-condensed">
		  	
			    <c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				     <tr>
		       			 <td class="col-md-1 tc">${l.index+1 }</td>
				    	<td class="col-md-11">
				          <div>${que.topic }</div>
				          <div class="mt10">
				    			<c:if test="${que.examQuestionType.name=='单选题' }">
				    				<input type="radio" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(que.items,';')[0]}
				    				<input type="radio" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(que.items,';')[1]}
				    				<input type="radio" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(que.items,';')[2]}
				    				<input type="radio" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(que.items,';')[3]}
				    			</c:if>
				    			<c:if test="${que.examQuestionType.name=='多选题' }">
				    				<input type="checkbox" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(que.items,';')[0]}
				    				<input type="checkbox" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(que.items,';')[1]}
				    				<input type="checkbox" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(que.items,';')[2]}
				    				<input type="checkbox" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(que.items,';')[3]}
				    			</c:if>
				    		</div>
		        		</td>
		     		 </tr>
			    </c:forEach>
			   
		    </table>
		     	<div class="col-md-12 tc">
			    	<button class="btn" type="button" onclick="setTab(${p.index})">上一页</button>
    				<button class="btn" type="submit">提交</button>
  				</div>
  				</div>
		    </c:when>
		    
		    <c:otherwise>
		    <div id="pageNum${p.index+1 }" name="exam">
		    <table class="clear table table-bordered table-condensed">
		    	
			    <c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				    <tr>
		       			 <td class="col-md-1 tc">${l.index+1 }</td>
				    	<td class="col-md-11">
				          <div>${que.topic }</div>
				          <div class="mt10">
				    			<c:if test="${que.examQuestionType.name=='单选题' }">
				    				<input type="radio" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(que.items,';')[0]}
				    				<input type="radio" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(que.items,';')[1]}
				    				<input type="radio" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(que.items,';')[2]}
				    				<input type="radio" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(que.items,';')[3]}
				    			</c:if>
				    			<c:if test="${que.examQuestionType.name=='多选题' }">
				    				<input type="checkbox" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(que.items,';')[0]}
				    				<input type="checkbox" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(que.items,';')[1]}
				    				<input type="checkbox" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(que.items,';')[2]}
				    				<input type="checkbox" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(que.items,';')[3]}
				    			</c:if>
				    		</div>
		        		</td>
		     		 </tr>
			    </c:forEach>
		    </table>
		    <div class="col-md-12 tc">
		    	<button class="btn" onclick="setTab(${p.index})" type="button">上一页</button>
    			<button class="btn" onclick="setTab(${p.index+2})" type="button">下一页</button>
  			</div>
  			</div>
		    </c:otherwise>
		 </c:choose>
   	</c:forEach>
  </c:otherwise>
  
  </c:choose>
    <input type="hidden" name="lawAnswer" value="${queAnswer}"/>
    <input type="hidden" name="lawPoint" value="${quePoint}"/>
    
    </form>
    </div>
  </body>
</html>
