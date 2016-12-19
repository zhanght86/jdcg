<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
		<script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
		<script type="text/javascript">
			$(function() {
				var typeIds = "${expert.expertsTypeId}";
				var ids = typeIds.split(",");
				//回显
				var checklist1 = document.getElementsByName("chkItem_1");
				for(var i = 0; i < checklist1.length; i++) {
					var vals = checklist1[i].value;
					for(var j = 0; j < ids.length; j++) {
						if(ids[j] == vals) {
							checklist1[i].checked = true;
						}
					}
				}
				var checklist2 = document.getElementsByName("chkItem_2");
				for(var i = 0; i < checklist2.length; i++) {
					var vals = checklist2[i].value;
					for(var j = 0; j < ids.length; j++) {
						if(ids[j] == vals) {
							checklist2[i].checked = true;
						}
					}
				}
			});
			
			function reason(auditField,auditContent){
			  var expertId = $("#expertId").val();		
			  var appear = auditField + "_show";
				var index = layer.prompt({
			    title : '请填写不通过的理由：', 
			    formType : 2, 
			    offset : '100px',
				}, 
		    function(text){
				    $.ajax({
				      url:"${pageContext.request.contextPath}/expertAudit/auditReasons.html",
				      type:"post",
				      dataType:"json",
				      data:"suggestType=seven"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField,
				      success:function(result){
				        result = eval("(" + result + ")");
				        if(result.msg == "fail"){
				           layer.msg('该条信息已审核过！', {	            
				             shift: 6, //动画类型
				             offset:'100px'
				          });
				        }
				      }
				    });
				    $("#"+appear+"").css('visibility', 'visible');
		      layer.close(index);
			    });
		  	}
		</script>
		<script type="text/javascript">
			function jump(str){
			  var action;
			  if(str == "basicInfo") {
					action = "${pageContext.request.contextPath}/expertAudit/basicInfo.html";
				}
			  if(str=="experience"){
			     action ="${pageContext.request.contextPath}/expertAudit/experience.html";
			  }
			  if(str=="product"){
			    action = "${pageContext.request.contextPath}/expertAudit/product.html";
			  }
			  if(str=="expertFile"){
			    action = "${pageContext.request.contextPath}/expertAudit/expertFile.html";
			  }
			  if(str=="reasonsList"){
			    action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
			  }
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0)">首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家审核</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgdd">
						<li onclick="jump('basicInfo')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a>
						</li>
						<li onclick="jump('experience')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">经历经验</a>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">专家类别</a>
						</li>
						<li onclick="jump('product')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">产品目录</a>
						</li>
						<li onclick="jump('expertFile')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">附件</a>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					<!-- 专家专业信息 -->
					<ul class="ul_list count_flow">
						<li>
							<div>
								<c:forEach items="${spList}" var="sp">
									<span class="margin-left-30 hand" onclick="reason('${sp.id}','${sp.name}技术');"><input type="checkbox"  disabled="disabled"  name="chkItem_1" value="${sp.id}" />${sp.name}技术 </span>
									<a class="b f18 ml10 red" id="${sp.id}_show" style="visibility:hidden">×</a>
								</c:forEach>
								<c:forEach items="${jjList}" var="jj">
									<span class="margin-left-30 hand" onclick="reason('${jj.id}','${jj.name}');"><input type="checkbox"  disabled="disabled" name="chkItem_2"  value="${jj.id}" />${jj.name} </span>
									<a class="b f18 ml10 red" id="${jj.id}_show" style="visibility:hidden">×</a>
								</c:forEach>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<input value="${expert.id}" id="expertId" type="hidden">
		<form id="form_id" action="" method="post">
   	  <input name="expertId" value="${expert.id}" type="hidden">
    </form>
	</body>
</html>