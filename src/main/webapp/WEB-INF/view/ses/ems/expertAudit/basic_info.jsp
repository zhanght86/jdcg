<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
			<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<style type="text/css">
			input {
			  cursor:pointer;
			}
			textarea {
			  cursor:pointer;
			}
		</style>
		
		<script type="text/javascript">
			$(function() {
	      $(":input").each(function() {
	        var onMouseMove = "this.style.background='#E8E8E8'";
					var onmouseout = "this.style.background='#FFFFFF'";
	        $(this).attr("onMouseMove",onMouseMove);
	        $(this).attr("onmouseout",onmouseout);
	    	});
	    	
	    	//为只读
	    	$(":input").each(function() {
	      $(this).attr("readonly", "readonly");
	      });
	      
	      layer.alert('点击审核项,弹出不通过理由框！', {
					title: '审核操作说明：',
					skin: 'layui-layer-molv', //样式类名
					closeBtn: 0,
					offset: '100px',
					shift: 4 //动画类型
				});
	      
	    });
		</script>
		
		<script type="text/javascript">
		
			//审核input框
			function reason(obj,str){
			  var expertId = $("#expertId").val();
			  var auditField;
			  var auditContent;
			  var html = "<div class='abolish'><img src='/zhbj/public/backend/images/sc.png'></div>";
		    $("#"+obj.id+"").each(function() {
		      auditField = $(this).parents("li").find("span").text().replace("：","").trim();
          auditContent = $(this).parents("li").find("input").val();
          if(str == "主要工作经历" || str=="专业学术成果" || str == "参加军队地方采购评审情况" || str == "需要申请回避的情况"){
          	auditField = str;
          	auditContent = $(this).parents("li").find("textarea").text();
          }
    		});
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
				      data:"suggestType=one"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField,
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
				   $("#"+obj.id+"").css('border-color','#FF0000');
						$(obj).after(html);
		      	layer.close(index);
			    });
		  	}
		  	
		  	//审核附件
		  	function reasonFile(obj,str){
				  var expertId = $("#expertId").val();
				  var showId =  obj.id+"1";
			    $("#"+obj.id+"").each(function() {
			      auditField = $(this).parents("li").find("span").text().replace("：","");
	    		});
	    		var auditContent = auditField + "附件信息";
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
					      data:"suggestType=one"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField,
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
						 $("#"+showId+"").css('visibility', 'visible');
			       layer.close(index);
				  });
		  	}
		  	
		  	
			//获取选中子节点id
			$(function (){
				var ids=new Array();
				var checklist1 = document.getElementsByName ("chkItem_1");
				for(var i=0;i<checklist1.length;i++){
			 		var vals=checklist1[i].value;
			 		if(checklist1[i].checked){
			 			ids.push(vals);
			 		}
				}
				var checklist2 = document.getElementsByName ("chkItem_2");
				for(var i=0;i<checklist2.length;i++){
			 		var vals=checklist2[i].value;
			 		if(checklist2[i].checked){
			 			ids.push(vals);
			 		}
				}
				var isEdit = "${isEdit}";
				if (isEdit == "0") {
					// 没有修改过
					var index = layer.confirm('该专家在上次审核退回后未做任何修改!', {
						btn : [ '确定' ],
			            offset:'100px'
					}, function() {
						layer.close(index);
					});
				}
			});
			
			// 提示之前的信息
			function isCompare(inputName,fieldName, type){
				$.ajax({
					url: "${pageContext.request.contextPath}/expertAudit/getFieldContent.do",
					data: {"field":fieldName,"type":type,"expertId":"${expertId}"},
					async: false,
					success: function(response){
						layer.tips("原值:" + response, "#" + inputName, {
		    				tips : 3
		    			});
					}
				});
			}
			
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/expertType.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<script type="text/javascript">
			function jump(str){
			  var action;
			  if(str=="experience"){
			     action ="${pageContext.request.contextPath}/expertAudit/experience.html";
			  }
			  if(str=="expertType"){
			    action = "${pageContext.request.contextPath}/expertAudit/expertType.html";
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
					<!-- <ul class="nav nav-tabs bgdd"> -->
						<ul class="flow_step">
						<li class="active">
							<a aria-expanded="false"  data-toggle="tab">基本信息</a>
							<i></i>
						</li>
						<!-- <li onclick="jump('experience')" >
							<a aria-expanded="false"  data-toggle="tab">经历经验</a>
							<i></i>
						</li> -->
						<li onclick="jump('expertType')" >
							<a aria-expanded="false"  data-toggle="tab">专家类别</a>
							<i></i>
						</li>
						<li onclick="jump('product')" >
							<a aria-expanded="false"  data-toggle="tab">产品目录</a>
							<i></i>
						</li>
						<li onclick="jump('expertFile')" >
							<a aria-expanded="false"  data-toggle="tab">附件</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')" >
							<a aria-expanded="false"  data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					
					<h2 class="count_flow"><i>1</i>专家个人信息</h2>
					<ul class="ul_list">
						<%-- <li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家来源：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="expertsFrom" <c:if test="${fn:contains(editFields,'getExpertsFrom')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('expertsFrom','getExpertsFrom','1');"</c:if> value="${expertsFrom }" type="text" onclick="reason(this);"/>
							</div>
						</li> --%>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家姓名：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="relName" <c:if test="${fn:contains(editFields,'getRelName')}"> style="border: 1px solid #FF8C00;" style="border: 1px solid #FF8C00;" onmouseover="isCompare('relName','getRelName','0');"</c:if> value="${expert.relName}" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">性别：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="gender" <c:if test="${fn:contains(editFields,'getGender')}"> style="border: 1px solid #FF8C00;" style="border: 1px solid #FF8C00;" onmouseover="isCompare('gender','getGender','1');"</c:if> value="${gender }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">出生日期：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input readonly="readonly" <c:if test="${fn:contains(editFields,'getBirthday')}"> style="border: 1px solid #FF8C00;" style="border: 1px solid #FF8C00;" onmouseover="isCompare('birthday','getBirthday','2');"</c:if> value="<fmt:formatDate type='date' value='${expert.birthday}' dateStyle='default' pattern='yyyy-MM-dd'/>" id="birthday" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">政治面貌：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="politicsStatus" <c:if test="${fn:contains(editFields,'getPoliticsStatus')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('politicsStatus','getPoliticsStatus','1');"</c:if> value="${politicsStatus }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">民族：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.nation}" <c:if test="${fn:contains(editFields,'getNation')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('nation','getNation','0');"</c:if> id="nation" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">健康状态：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.healthState}" <c:if test="${fn:contains(editFields,'getHealthState')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('healthState','getHealthState','0');"</c:if> id="healthState" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">所在单位：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.workUnit}" <c:if test="${fn:contains(editFields,'getWorkUnit')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('workUnit','getWorkUnit','0');"</c:if> id="workUnit" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">缴纳社会保险证明：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.coverNote}" <c:if test="${fn:contains(editFields,'getCoverNote')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('coverNote','getCoverNote','0');"</c:if> id="coverNote" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<c:if test="${froms eq 'LOCAL'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"  onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="coverNoteFile" onclick="reasonFile(this);">社保证明：</span>
              	<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
              		<up:show showId="show1" groups="show1,show2,show3,show4,show5" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}"/>
           				<a style="visibility:hidden" id="coverNoteFile1"><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></a>
            		</div>
            	<li>
						</c:if>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">省：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="parentName" value="${parentName }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">市：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="sonName" value="${sonName }" type="text" onclick="reason(this);" <c:if test="${fn:contains(editFields,'getAddress')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('address','getAddress','1');"</c:if>/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">单位地址：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.unitAddress}" <c:if test="${fn:contains(editFields,'getUnitAddress')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('unitAddress','getUnitAddress','0');"</c:if> id="unitAddress" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 单位邮编：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.postCode}" <c:if test="${fn:contains(editFields,'getPostCode')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('postCode','getPostCode','0');"</c:if> id="postCode" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 现任职务：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.atDuty}" <c:if test="${fn:contains(editFields,'getAtDuty')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('atDuty','getAtDuty','0');"</c:if> id="atDuty" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" >居民身份证号码：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.idCardNumber}" <c:if test="${fn:contains(editFields,'getIdCardNumber')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('idCardNumber','getIdCardNumber','0');"</c:if> id="idCardNumber" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand"  onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="idCardNumberFile" onclick="reasonFile(this);"> 身份证复印件（正反面）</span>
            	<up:show showId="show2" groups="show1,show2,show3,show4,show5" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDCARDNUMBER_TYPEID}"/>
           		<a style="visibility:hidden" id="idCardNumberFile1"><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></a>
            <li>
            <c:if test="${froms eq 'ARMY'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 hand">军队人员身份证件类型：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input id="idType" <c:if test="${fn:contains(editFields,'getIdType')}">onmouseover="isCompare('idType','getIdType','1');"</c:if> value="${idType }" type="text" onclick="reason(this);"/>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">证件号码：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input value="${expert.idNumber}" <c:if test="${fn:contains(editFields,'getIdNumber')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('idNumber','getIdNumber','0');"</c:if> id="idNumber" type="text" onclick="reason(this);"/>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand"  onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="idTypeFile" onclick="reasonFile(this);">军队人员身份证件：</span>
              	<up:show showId="show3" groups="show1,show2,show3,show4,show5" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}"/>
           				<a style="visibility:hidden" id="idCardNumberFile1"><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></a>
            	<li>
            </c:if>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 参加工作时间：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input readonly="readonly" <c:if test="${fn:contains(editFields,'getTimeToWork')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('timeToWork','getTimeToWork','3');"</c:if> value="<fmt:formatDate value='${expert.timeToWork}' pattern='yyyy-MM'/>" id="timeToWork" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">从事专业：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.major}" <c:if test="${fn:contains(editFields,'getMajor')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('major','getMajor','0');"</c:if> id="major" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 从事专业起始年度：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input <c:if test="${fn:contains(editFields,'getTimeStartWork')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('timeStartWord','getTimeStartWord','3');"</c:if> value="<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle='default' pattern='yyyy-MM-dd'/>" readonly="readonly" id="timeStartWork" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家技术职称：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input maxlength="20" <c:if test="${fn:contains(editFields,'getProfessTechTitles')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('professTechTitles','getProfessTechTitles','0');"</c:if> value="${expert.professTechTitles}" name="professTechTitles" id="professTechTitles" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" onclick="reasonFile(this);" id="professTechTitlesFile">资格证书：</span>
              <up:show showId="show5" groups="show1,show2,show3,show4,show5" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}"/>
              <a style="visibility:hidden" id="professTechTitlesFile1"><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></a>
            </li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">取得技术职称时间：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input <c:if test="${fn:contains(editFields,'getMakeTechDate')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('makeTechDate','getMakeTechDate','3');"</c:if> value="<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM-dd'/>" readonly="readonly" id="makeTechDate" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">执业资格：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input <c:if test="${fn:contains(editFields,'getProfessional')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('professional','getProfessional','0');"</c:if> value="${expert.professional}" readonly="readonly" id="professional" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand"  onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="titleType" onclick="reasonFile(this);">执业资格证书：</span>
             	<up:show showId="show3" groups="show1,show2,show3,show4,show5" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}"/>
          			<a style="visibility:hidden" id="idCardNumberFile1"><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></a>
           	<li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">执业资格时间：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input <c:if test="${fn:contains(editFields,'getTimeProfessional')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('timeProfessional','getTimeProfessional','3');"</c:if> value="<fmt:formatDate type='date' value='${expert.timeProfessional}' dateStyle='default' pattern='yyyy-MM-dd'/>" readonly="readonly" id="timeProfessional" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">毕业院校及专业：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.graduateSchool}" <c:if test="${fn:contains(editFields,'getGraduateSchool')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('graduateSchool','getGraduateSchool','0');"</c:if> id="graduateSchool" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">最高学历：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="hightEducation" <c:if test="${fn:contains(editFields,'getHightEducation')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('hightEducation','getHightEducation','1');"</c:if> value="${hightEducation }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand"  onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="graduateSchoolFile" onclick="reasonFile(this);">毕业证书：</span>
              <up:show showId="show3" groups="show1,show2,show3,show4,show5" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}"/>
              <a style="visibility:hidden" id="graduateSchoolFile1"><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></a>
            </li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="degreeFile" onclick="reasonFile(this);">学位证书：</span>
              <up:show showId="show4" groups="show1,show2,show3,show4,show5" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}"/>
              <a style="visibility:hidden" id="degreeFile1"><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></a>
            </li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 最高学位：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${degree}" <c:if test="${fn:contains(editFields,'getDegree')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('degree','getDegree','1');"</c:if> id="degree" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">手机：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.mobile}" <c:if test="${fn:contains(editFields,'getMobile')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('mobile','getMobile','0');"</c:if> readonly="readonly" id="mobile" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">固定电话：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.telephone}" <c:if test="${fn:contains(editFields,'getTelephone')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('telephone','getTelephone','0');"</c:if> id="telephone" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 传真电话：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.fax}" <c:if test="${fn:contains(editFields,'getFax')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('fax','getFax','0');"</c:if> id="fax" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">个人邮箱：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.email}" <c:if test="${fn:contains(editFields,'getEmail')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('email','getEmail','0');"</c:if> id="email" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<%-- <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">相关机关事业部门推荐信：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.email}" <c:if test="${fn:contains(editFields,'getEmail')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('email','getEmail','0');"</c:if> id="email" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="degreeFile" onclick="reasonFile(this);">推荐信：</span>
              <up:show showId="show4" groups="show1,show2,show3,show4,show5" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}"/>
              <a style="visibility:hidden" id="degreeFile1"><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></a>
            </li> --%>
						
					</ul>
					
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>2</i>主要工作经历</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" <c:if test="${fn:contains(editFields,'getJobExperiences')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('jobExperiences','getJobExperiences','0');"</c:if> id="jobExperiences" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'主要工作经历');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.jobExperiences}</textarea>
								</div>
							</li>
						</ul>
					</div>
					<!-- 专业学术成果 -->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>3</i>专业学术成果</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" <c:if test="${fn:contains(editFields,'getAcademicAchievement')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('academicAchievement','getAcademicAchievement','0');"</c:if> id="academicAchievement" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'专业学术成果');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.academicAchievement}</textarea>
								</div>
							</li>
						</ul>
					</div>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>4</i>参加军队地方采购评审情况</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" <c:if test="${fn:contains(editFields,'getReviewSituation')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('reviewSituation','getReviewSituation','0');"</c:if> id="reviewSituation" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'参加军队地方采购评审情况');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.reviewSituation}</textarea>
								</div>
							</li>
						</ul>
					</div>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>5</i>需要申请回避的情况</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" <c:if test="${fn:contains(editFields,'getAvoidanceSituation')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('avoidanceSituation','getAvoidanceSituation','0');"</c:if> id="avoidanceSituation" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'需要申请回避的情况');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.avoidanceSituation}</textarea>
								</div>
							</li>
						</ul>
					</div>
					
					<!-- 专家专业信息 -->
					<%-- <h2 class="count_flow"><i>4</i>专家类别</h2>
					<ul class="ul_list" id="expertType" onclick="reason(this);">
						<li class="col-md-3 col-sm-6 col-xs-12 pl10" >
							<div class="input-append col-sm-12 col-xs-12 col-md-12 p0" >
								<c:forEach items="${spList}" var="sp" >
							  	<span><input type="checkbox" name="chkItem_1" value="${sp.id}" />${sp.name} </span>
							  </c:forEach> 
							  <c:forEach items="${jjList}" var="jj" >
									<span><input type="checkbox" name="chkItem_2"  value="${jj.id}" />${jj.name} </span>
								</c:forEach>
							</div>
						</li>
					</ul> --%>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
					<a class="btn" type="button" onclick="nextStep();">下一步</a>
				</div>
			</div>
		</div>
		<input value="${expertId}" id="expertId" type="hidden">
		<form id="form_id" action="" method="post">
   	  <input name="expertId" value="${expertId }" type="hidden">
   	  <input name="sign" value="${sign }" type="hidden">
    </form>
	</body>
</html>