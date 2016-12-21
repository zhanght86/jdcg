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
	<meta name="author" content="WangHuijie">
 <jsp:include page="../../ses/bms/page_style/backend_common.jsp"></jsp:include>	
<script type="text/javascript">
	function backUp(){
		$("#tab-6").load("${pageContext.request.contextPath}/packageExpert/detailedReview.html?packageId=${packageId}&projectId=${projectId}");
	}
</script>
</head>
<body>
  <div class="container">
  <div class="headline-v2">
    <h2>${expert.relName }</h2>
  </div>
  <!-- 表格开始-->
  <div>
	        <table class="table table-bordered table-condensed mt5" id="table2" style="overflow: hidden;word-spacing: keep-all;" >
			  <tr>
			    <th></th>
			    <c:forEach items="${supplierList}" var="supplier">
			      <th>${supplier.suppliers.supplierName}</th>
			    </c:forEach>
			  </tr>
			  <tr>
			   	  	  <th>评审项目</th>
			   	  	  <c:forEach items="${supplierList}" var="supplier">
     		        	<th>评审得分</th>
	    		  	  </c:forEach>
			   		</tr>
			  <c:forEach items="${markTermTypeList}" var="type">
			    <tr>
			      <td class="info" colspan="${length}">${type.name}</td>
			    </tr>
			    <c:forEach items="${markTermList}" var="markTerm">
			      <c:if test="${markTerm.typeName eq type.id}">
			        <tr>
			          <td class="info" colspan="${length}">${markTerm.name}</td>
			        </tr>
			   		<c:forEach items="${scoreModelList}" var="score" varStatus="vs">
			    	  <c:if test="${score.markTerm.pid eq markTerm.id}">
			    	    <tr>
			 	  		  <td class="w100"><a href="javascript:void();" title="${score.reviewContent}">${score.name}</a></td>
				 	      <c:forEach items="${supplierList}" var="supplier">
					 	    <c:set var="expertScore" value=""/>
					 	    <c:forEach items="${scores}" var="sco">
					 	      <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}"><c:set var="expertScore" value="${sco.score}"/></c:if>
					 	    </c:forEach>
					 	    <td class="tc">
					 	      <span>${expertScore}</span>
					 	    </td>
				 	      </c:forEach>
					    </tr> 
					  </c:if>
			        </c:forEach>
			      </c:if>
			    </c:forEach>
			  </c:forEach>
			</table>
			<div class="tc">
			  <%--<input class="btn btn-windows back" value="返回" type="button" onclick="backUp()">--%>
		    </div>
		  </div>
   </div>
</body>
</html>
