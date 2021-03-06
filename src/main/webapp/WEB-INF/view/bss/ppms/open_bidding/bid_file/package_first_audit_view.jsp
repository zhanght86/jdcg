<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
    	  function confirmOk(obj, id, flowDefineId){
	      	   layer.confirm('您已经确认了吗?', {title:'提示',offset: ['100px'],shade:0.01}, function(index){
		 			layer.close(index);
		 			$.ajax({
		 				url:"${pageContext.request.contextPath}/open_bidding/confirmOk.html?projectId="+id+"&flowDefineId="+flowDefineId,
		 				dataType: 'json',
		 	       		success:function(result){
		                   $("#queren").after("<a href='javascript:volid(0);' >05、已确认</a>");
		                    $("#queren").remove();
		                },
		                error: function(result){
		                    layer.msg("确认失败",{offset: '222px'});
		                }
		 	       	});
		 		});
	      }
      
      function jump(url){
       	$("#open_bidding_main").load(url);
	   }
	</script>
  </head>
  <body>
     <div class="col-md-12 p0">
	   <ul class="flow_step">
	     <li >
		   <a  href="${pageContext.request.contextPath}/open_bidding/firstAduitView.html?projectId=${projectId}&flowDefineId=${flowDefineId }" >01、资格性和符合性审查</a>
		   <i></i>
		 </li>
		 <%-- <li class="active">
		   <a  href="${pageContext.request.contextPath}/open_bidding/packageFirstAuditView.html?projectId=${projectId}&flowDefineId=${flowDefineId }" >02、符合性关联</a>
		   <i></i>							  
		 </li> --%>
	     <li>
		   <a  href="${pageContext.request.contextPath}/intelligentScore/packageListView.html?projectId=${projectId}&flowDefineId=${flowDefineId }">02、经济和技术评审细则</a>
		   <i></i>
		 </li>
		 <li>
		   <a href="${pageContext.request.contextPath}/open_bidding/bidFileView.html?id=${projectId}&flowDefineId=${flowDefineId }" >
		         03、招标文件
		         <%-- <c:if test="${project.dictionary.code eq 'GKZB' }">
			     03、招标文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'XJCG' }">
			     03、询价文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'YQZB' }">
			     04、招标文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'JZXTP' }">
			     04、竞谈文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'DYLY' }">
			     04、单一来源文件
			     </c:if> --%>
		   </a>
		   <i></i>
		 </li>
		 <li>
		    <c:if test="${project.confirmFile == 0 || project.confirmFile==null}"><a  onclick="confirmOk(this,'${projectId}','${flowDefineId }');" id="queren">05、确认</a></c:if>
		    <c:if test="${project.confirmFile == 1 }"><a>05、已确认</a></c:if>
		 </li>
	   </ul>
	 </div>
	 <div class="tab-content clear step_cont">
		 <!--第二个 -->
		 <div class="col-md-12 tab-pane active"  id="tab-1">
		 	   <div class="container" id="package">
				   <c:forEach items="${packageList }" var="pack" varStatus="p">
				   		<h2 class="f16 spread">包名：${pack.name }</h2>
				   		<div class="content table_box" >
					       <table class="table table-bordered table-condensed table-hover table-striped">
				 	            <h5>项目初审项信息</h5>
				 	            <thead>
							      <tr>
							        <th>初审项名称</th>
							        <th>要求类型</th>
							        <th>创建人</th>
							      </tr>
							    </thead>
							      <c:forEach items="${list }" var="l" varStatus="vs">
								       <tr>
								         <c:forEach items="${idList }" var="id" varStatus="p">
									 	      <c:if test="${id.firstAuditId==l.id && id.packageId==pack.id }">
													<td align="center">${l.name } </td>
											        <td align="center">${l.kind }</td>
											        <td align="center">${l.creater }</td>
											  </c:if>
									 	 </c:forEach>
								      </tr>
						      	  </c:forEach>
				   		  </table>
				   		</div>
				   </c:forEach>
			   </div> 
		 </div>
     </div>
  </body>
</html>
