<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
 <jsp:include page="../../ses/bms/page_style/backend_common.jsp"></jsp:include>	
<script type="text/javascript">
	function getNumScore(){
		var scores = document.getElementsByName("score");
		var scoreNum = 0;
		for (var i = 0; i < scores.length; i++) {
			if(scores[i].innerHTML != "暂未评分"){
				scoreNum = scoreNum + parseInt(scores[i].innerHTML);
			}
		}
		document.getElementById("scoreNum").innerHTML = scoreNum;
	}
</script>
</head>
<body onload="getNumScore()">
  <!-- 我的订单页面开始-->
  <div class="container">
  <div class="headline-v2">
    <h2>${expertName }</h2>
  </div>   
  <!-- 表格开始-->
  <div class="content table_box">
    <table class="table table-bordered table-condensed table-hover table-striped">
	  <tr>
	    <th class="w150 tc">评审项/供应商</th>
		<c:forEach items="${supplierList}" var="supplier">
	      <c:if test="${fn:contains(supplier.packages,packageId)}">
		    <td class="tc">${supplier.suppliers.supplierName}</td>
		  </c:if>
        </c:forEach>
      </tr>
      <c:forEach items="${auditModelList}" var="auditModel">
	    <c:if test="${packageId eq auditModel.packageId and projectId eq auditModel.projectId}">
		  <tr>
            <th class="tc w150">${auditModel.markTermName}</td>
            <c:set var="count1" value="0"/>
            <c:forEach items="${supplierList}" var="supplier">
              <c:forEach items="${scores}" var="score">
                <c:if test="${fn:contains(supplier.packages,packageId) and fn:contains(supplier.packages,score.PACKAGEID) and score.EXPERTID eq expertId and supplier.suppliers.id eq score.SUPPLIERID and auditModel.markTermId eq score.MARKTERMID}">
                  <c:set var="count1" value="1"/>
                  <td class="tc" name="score">${score.SCORE}</td>
		        </c:if>
              </c:forEach>
            </c:forEach>
            <c:if test="${count1 ne '1'}">
              <td class="tc" name="score">暂未评分</td>
            </c:if>
		  </tr>
		</c:if>
      </c:forEach>
      <tr>
        <th class="tc w150">合计</th>
        <td class="tc" id="scoreNum"></td>
      </tr>
	</table>

   </div>
      <div id="pagediv" align="center"><input type="button" class="btn btn-windows back" value="返回" onclick="javascript:window.location.href='${pageContext.request.contextPath}/packageExpert/detailedReview.html?projectId=${projectId}&packageId=${packageId}'"></div>
   </div>
</body>
</html>
