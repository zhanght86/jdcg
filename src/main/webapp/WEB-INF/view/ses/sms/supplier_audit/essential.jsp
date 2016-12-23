<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<!DOCTYPE HTML>

<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>详细信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<style type="text/css">
			input {
				cursor: pointer;
			}
			
			textarea {
				cursor: pointer;
			}
		</style>
		<script type="text/javascript">
			$(function() {
				layer.alert('点击审核项,弹出不通过理由框！', {
					title: '审核操作说明：',
					skin: 'layui-layer-molv', //样式类名
					closeBtn: 0,
					offset: '100px',
					shift: 1 //动画类型
				});
			});

			//隐藏叉 
			$(function() {
				$(":input").each(function() {
					$(this).parent("div").find("div").hide();
					var onMouseMove = "this.style.border='solid 1px #FF0000'";
					var onmouseout = "this.style.border='solid 1px #D3D3D3'";
					$(this).attr("onMouseMove", onMouseMove);
					$(this).attr("onmouseout", onmouseout);
				});

				$("li").each(function() {
					$(this).find("p").hide();
				});
			});
			
			//审核input框
			function reason(obj){
			  var supplierId = $("#id").val();
			  var auditField = obj.id;;
			  var auditContent;
			  var auditFieldName ;
			  var html = "<a class='abolish'><img src='/zhbj/public/backend/images/sc.png'></a>";
		    $("#"+obj.id+"").each(function() {
		      auditFieldName = $(this).parents("li").find("span").text().replace("：","").trim();
          auditContent = $(this).parents("li").find("input").val();
    		});
					var index = layer.prompt({
				    title : '请填写不通过的理由：', 
				    formType : 2, 
				    offset : '100px',
				}, 
		    function(text){
				    $.ajax({
				      url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
				      type: "post",
				      dataType: "json",
				      data: {"auditType":"basic_page","auditFieldName":auditFieldName,"auditContent":auditContent,"suggest":text,"supplierId":supplierId,"auditField":auditField},
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
					$(obj).after(html);
		      layer.close(index);
			    });
		  	}
			
			
			/* function reason(id, auditField) {
				var offset = "";
		    if (window.event) {
		      e = event || window.event;
		      var x = "";
		      var y = "";
		      x = e.clientX + 20 + "px";
		      y = e.clientY + 20 + "px";
		      offset = [y, x];
		    } else {
		      offset = "200px";
		    }
        var w = window.screen.height / 2 + "px";
         
				var supplierId = $("#id").val();
				var id2 = id + "2";
				var id3 = id + "3";
				var auditFieldName = $("#" + id2 + "").text().replace("：", ""); //审批的字段名字
				var auditContent = document.getElementById("" + id + "").value; //审批的字段内容
				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px',
					},
					function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
							dataType: "json",
							data: "auditType=basic_page" + "&auditFieldName=" + auditFieldName + "&auditContent=" + auditContent + "&suggest=" + text + "&supplierId=" + supplierId + "&auditField=" + auditField,
							success: function(result) {
								result = eval("(" + result + ")");
								if(result.msg == "fail") {
									layer.msg('该条信息已审核过！', {
										shift: 6, //动画类型
										offset: '100px'
									});
								}
							}
						});
						$("#" + id3 + "").show();
						$("#" + id3 + "").parents("li").find("input").css("padding-right", "30px");
						layer.close(index);
						$("input[name='auditType']").val(auditType);
						   $("input[name='auditField']").val(auditField);
						   $("input[name='auditContent']").val(auditContent);
						   $("input[name='suggest']").val(text);
						   $("#save_reaeon").submit();
					});
			} */

			function reason1(ele, auditField) {
				var supplierId = $("#id").val();
				var auditFieldName = $(ele).parents("li").find("span").text().replace("：", "").replace("view", ""); //审批的字段名字
				var index = layer.prompt({
					title: '请填写不通过的理由：',
					formType: 2,
					offset: '100px'
				}, function(text) {
					$.ajax({
						url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
						type: "post",
						data: "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditType=basic_page" + "&auditContent=附件" + "&auditField=" + auditField,
						dataType: "json",
						success: function(result) {
							result = eval("(" + result + ")");
							if(result.msg == "fail") {
								layer.msg('该条信息已审核过！', {
									shift: 6, //动画类型
									offset: '100px'
								});
							}
						}
					});
					$(ele).parents("li").find("p").show(); //显示叉
					layer.close(index);
				});
			}

			function nextStep() {
				$("#form_id").submit();
			}

			//文件下載
			function downloadFile(fileName) {
				$("input[name='fileName']").val(fileName);
				$("#download_form_id").submit();
			}

			//为只读
			$(function() {
				$(":input").each(function() {
					$(this).attr("readonly", "readonly");
				});
			});

			// 提示之前的信息
			function isCompare(field) {
				var supplierId = $("#id").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/supplierAudit/showModify.do",
					data: "&supplierId=" + supplierId + "&beforeField=" + field,
					dataType: "text",
					success: function(result) {
						layer.tips("修改前:" + result, "#" + field, {
							tips: 1
						});
					}
				});
			}
		</script>

		<script type="text/javascript">
			function jump(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
				}
				if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				}
				if(str == "materialProduction") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
				}
				if(str == "materialSales") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
				}
				if(str == "engineering") {
					action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
				}
				if(str == "serviceInformation") {
					action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
				}
				if(str == "items") {
					action = "${pageContext.request.contextPath}/supplierAudit/items.html";
				}
				if(str == "product") {
					action = "${pageContext.request.contextPath}/supplierAudit/product.html";
				}
				if(str == "applicationForm") {
					action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
				}
				if(str == "reasonsList") {
					action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
				}
				$("#form_id").attr("action", action);
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
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">供应商管理</a>
					</li>
					<li>
						<a href="#">供应商审核</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<%-- <ul class="nav nav-tabs bgdd">
          <li class="active"><a >详细信息</a></li>
          <li class=""><a >财务信息</a></li>
          <li class=""><a >股东信息</a></li>
          <c:if test="${fn:contains(supplierTypeNames, '生产')}">
          <li class=""><a >物资-生产专业信息</a></li>
          </c:if>
          <c:if test="${fn:contains(supplierTypeNames, '销售')}">
          <li class=""><a >物资-销售专业信息</a></li>
          </c:if>
          <c:if test="${fn:contains(supplierTypeNames, '工程')}">
          <li class=""><a >工程-专业信息</a></li>
          </c:if>
          <c:if test="${fn:contains(supplierTypeNames, '服务')}">
          <li class=""><a >服务-专业信息</a></li>
          </c:if>
          <li class=""><a >品目信息</a></li>
          <li class=""><a >产品信息</a></li>
          <li class=""><a>申请表</a></li>
          <li class=""><a>审核汇总</a></li>
          </ul> --%>
					<ul class="nav nav-tabs bgdd">
						<li onclick="jump('essential')" class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a>
							<i></i>
						</li>
						<li onclick="jump('financial')">
							<a aria-expanded="true" href="#tab-2">财务信息</a>
							<i></i>
						</li>
						<li onclick="jump('shareholder')">
							<a aria-expanded="false" href="#tab-3">股东信息</a>
							<i></i>
						</li>
						<c:if test="${fn:contains(supplierTypeNames, '生产')}">
							<li onclick="jump('materialProduction')">
								<a aria-expanded="false" href="#tab-4">生产信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '销售')}">
							<li onclick="jump('materialSales')">
								<a aria-expanded="false" href="#tab-4">销售信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '工程')}">
							<li onclick="jump('engineering')">
								<a aria-expanded="false" href="#tab-4">工程信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '服务')}">
							<li onclick="jump('serviceInformation')">
								<a aria-expanded="false" href="#tab-4">服务信息</a>
								<i></i>
							</li>
						</c:if>
						<li onclick="jump('items')">
							<a aria-expanded="false" href="#tab-4">品目信息</a>
							<i></i>
						</li>
						<!-- <li onclick = "jump('product')">
	              <a aria-expanded="false" href="#tab-4" >产品信息</a>
	               <i></i>
	            </li> -->
						<li onclick="jump('applicationForm')">
							<a aria-expanded="false" href="#tab-4">申请表</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-4">审核汇总</a>
						</li>
					</ul>

					<form id="form_id" action="${pageContext.request.contextPath}/supplierAudit/financial.html" method="post">
						<input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
					</form>

					<h2 class="count_flow"><i>1</i>企业信息</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" >公司名称：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="supplierName" onclick="reason(this)" value="${suppliers.supplierName } " type="text" <c:if test="${fn:contains(field,'supplierName')}">onMouseOver="isCompare('supplierName');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">公司网址：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input class="hand " id="website" value="${suppliers.website } " type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'website')}">onMouseOver="isCompare('website');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">成立日期：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="foundDate" onclick="reason(this)" class="hand " value="<fmt:formatDate value='${suppliers.foundDate}' pattern='yyyy-MM-dd'/>" type="text" <c:if test="${fn:contains(field,'foundDate')}">onMouseOver="isCompare('foundDate');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业执照类型：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="businessType" class="hand " value="${suppliers.businessType } " type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'businessType')}">onMouseOver="isCompare('businessType');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">基本账户开户行：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="bankName" class="hand " value="${suppliers.bankName } " type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'bankName')}">onMouseOver="isCompare('bankName');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">开户行账户：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="bankAccount" class="hand " value="${suppliers.bankAccount } " type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'bankAccount')}">onMouseOver="isCompare('bankAccount');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
				   		<span class="hand" onclick="reason1(this,'supplierBank');" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'">基本账户开户许可证：</span> 
				      <up:show showId="bank_show" delete="false" groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" />
							<p><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></p>
						</li>
					</ul>

					<h2 class="count_flow"><i>2</i>地址信息</h2>
					<ul class="ul_list hand">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">注册地址邮编：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="postCode" class="hand " value="${suppliers.postCode }" type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'postCode')}">onMouseOver="isCompare('postCode');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">注册公司地址：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="address" class="hand " value="${parentAddress}${sonAddress } " type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'address')}">onMouseOver="isCompare('address');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">注册公司详细地址：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="detailAddress" class="hand fl" onclick="reason(this)" value="${suppliers.detailAddress}" <c:if test="${fn:contains(field,'detailAddress')}">onMouseOver="isCompare('detailAddress');"</c:if>>
							</div>
						</li>
						<!-- 遍历生产地址 -->
						<%-- <c:forEach items="${supplierAddress }" var="supplierAddress" varStatus="vs">
							<li class="col-md-3 col-sm-6 col-xs-12">
								<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" id="code2">生产经营地址邮编：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input type="text" id="code" value="${supplierAddress.code}" class="hand " onclick="reason(this.id,'code')">
									<div id="code3" class="abolish">×</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12">
								<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" id="address2">生产经营地址：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input type="text" id="address" value="<c:forEach items=" ${privnce } " var="privnce " varStatus="vs "><c:if test="${supplierAddress.parentId eq privnce.id} ">${privnce.name }</c:if></c:forEach>${supplierAddress.subAddressName }" class="hand " onclick="reason(this.id,'address')">
									<div id="address3" class="abolish">×</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 pl10">
								<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" id="detailAddress2">生产经营详细地址：
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input type="text" id="detailAddress" value="${supplierAddress.detailAddress}" class="hand " onclick="reason(this.id,'detailAddress')">
									<div id="detailAddress3" class="abolish">×</div>
								</div>
							</li>
						</c:forEach> --%>
					</ul>

					<h2 class="count_flow"><i>3</i>资质资信</h2>
					<ul class="ul_list hand">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="hand" onclick="reason1(this,'taxCert');" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'">近三个月完税凭证：</span>
							<up:show showId="taxcert_show" delete="false" groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" />
							<p><img src='/zhbj/public/backend/images/sc.png'></p>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onclick="reason1(this,'billCert');" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'">近三年银行基本账户年末对账单：</span>
							<up:show showId="billcert_show" delete="false" groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
							<p><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></p>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onclick="reason1(this,'securityCert');" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'">近三个月缴纳社会保险金凭证：</span>
							<up:show showId="curitycert_show" delete="false" groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" />
							<p><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></p>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onclick="reason1(this,'breachCert');" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'">近三年内无重大违法记录声明：</span>
							<up:show showId="bearchcert_show" groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show" delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
							<p><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></p>
						</li>
					</ul>

					<h2 class="count_flow"><i>4</i>法定代表信息</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">姓名：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="legalName" class="hand " value="${suppliers.legalName } " type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'legalName')}">onMouseOver="isCompare('legalName');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" >身份证号：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="legaIdCard" class="hand " value="${suppliers.legalIdCard } " type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'legalIdCard')}">onMouseOver="isCompare('legalIdCard');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">固定电话：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="legalTelephone" class="hand " value="${suppliers.legalTelephone } " type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'legalTelephone')}">onMouseOver="isCompare('legalTelephone');"</c:if>>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="legalMobile" class="hand " value="${suppliers.legalMobile } " type="text" onclick="reason(this)" <c:if test="${fn:contains(field,'legalMobile')}">onMouseOver="isCompare('legalMobile');"</c:if>>
							</div>
						</li>
						<%-- <li class="col-md-3 col-sm-6 col-xs-12">
							<span class="hand" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'" onclick="reason1(this,'supplierIdentityUp');">身份证正面: </span>
							<up:show showId="bearchcert_up_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
							<p class="b f18 ml10 red">×</p>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="hand" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'" onclick="reason1(this,'supplierIdentitydown');">身份证反面: </span>
							<up:show showId="identity_down_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" />
							<p class="b f18 ml10 red">×</p>
						</li> --%>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'" onclick="reason1(this,'supplierIdentityUp');"> 身份证复印件（正反面）:</span>
					    <up:show showId="bearchcert_up_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
           		<p><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></p>
           	</li>
					</ul>

					<h2 class="count_flow"><i>5</i>注册联系人</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">姓名：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="contactName" class="hand " value="${suppliers.contactName } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="contactFax" class="hand " value="${suppliers.contactFax } " type="text" onclick="reason(this)"">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">固定电话：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="contactTelephone" class="hand " value="${suppliers.contactMobile } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="contactMobile" class="hand " value="${suppliers.mobile } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮箱：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="contactEmail" class="hand " value="${suppliers.contactEmail } " type="text" onclick="reason(this)">
							</div>
						</li>
						<!-- <li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactAddress2">地址：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 p0">
								<input id="contactAddress" class="hand " value="" type="text" onclick="reason(this.id,'contactAddress')">
								<div id="contactAddress3" onclick="reason(this.id)" class="abolish">×</div>
							</div>
						</li> -->
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">详细地址：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 p0">
								<input id="contactAddress" class="hand " value="${suppliers.contactAddress } " type="text" onclick="reason(this)">
							</div>
						</li>
					</ul>

					<h2 class="count_flow"><i>6</i>军队业务联系人</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">姓名：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="armyBusinessName" class="hand " value="${suppliers.armyBusinessName } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="armyBusinessFax" class="hand " value="${suppliers.armyBusinessFax } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" >固定电话：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="armyBuinessMobile" class="hand " value="${suppliers.armyBuinessMobile } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="armyBuinessTelephone" class="hand " value="${suppliers.armyBuinessTelephone } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" onclick="reason(this)">邮箱：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="armyBuinessEmail" class="hand " value="${suppliers.armyBuinessEmail } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">详细地址：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 p0">
								<input id="armyBuinessAddress" class="hand " value="${suppliers.armyBuinessAddress } " type="text" onclick="reason(this)">
							</div>
						</li>
					</ul>

					<h2 class="count_flow"><i>7</i>营业执照</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">统一社会信用代码：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="creditCode" class="hand " value="${suppliers.creditCode } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">登记机关：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="registAuthority" class="hand " value="${suppliers.registAuthority } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">注册资本(万元)：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="registFund" class="hand " value="${suppliers.registFund } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业有效期 ：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="businessEndDate" class="hand " onclick="reason(this)" value="<fmt:formatDate value='${suppliers.businessStartDate}' pattern='yyyy-MM-dd'/>" type="text" />
							</div>
						</li>
						<%-- <li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业截止时间：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="businessStartDate" class="hand " onclick="reason(this)" value="<fmt:formatDate value='${suppliers.businessEndDate}' pattern='yyyy-MM-dd'/>" type="text" />
							</div>
						</li> --%>
						<%-- <li class="col-md-3 col-sm-6 col-xs-12">
							<span class="fl" id="businessAddress2">生产或经营地址：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="businessAddress" class="hand " value="${suppliers.businessAddress } " type="text" onclick="reason(this.id,'businessAddress')">
								<div id="businessAddress3" class="abolish">×</div>
							</div>
						</li> --%>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮编：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="businessPostCode" class="hand " value="${suppliers.businessPostCode } " type="text" onclick="reason(this)">
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="hand" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'" onclick="reason1(this,'businessCert');">营业执照：</span>
							<%-- <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <c:if test="${suppliers.businessCert != null }">
                  <a class="span5 green" onclick="downloadFile('${suppliers.businessCert}')">下载附件</a>
                </c:if>
                <c:if test="${suppliers.businessCert == null}">
                  <a class="span5 red">无附件下载</a>
                </c:if>
                <div class="b f18 ml10 fl hand red">×</div>
              </div> --%>
							<up:show showId="business_show" delete="false" groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" />
							<p><img style="padding-left: 10px;" src='/zhbj/public/backend/images/sc.png'></p>
						</li>
						<li class="col-md-12 col-sm-12 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">经营范围：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="businessScope" onclick="reason(this)">${suppliers.businessScope }</textarea>
							</div>
						</li>
					</ul>

					<h2 class="count_flow"><i>8</i>境外分支</h2>
					<ul class="ul_list">
						<c:forEach items="${supplierBranchList }" var="supplierBranch" varStatus="vs">
							<%-- <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">境外分支机构：</span>
              	<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                <c:if test="${suppliers.overseasBranch == 0}">
	                  <input id="overseasBranch" class="hand " value="无" type="text" onclick="reason(this)" >
	                </c:if>
	                <c:if test="${suppliers.overseasBranch == 1}">
	                  <input id="overseasBranch" class="hand " value="有" type="text" onclick="reason(this)" >
	                </c:if>
              	</div>
            	</li> --%>
							<li class="col-md-3 col-sm-6 col-xs-12 pl15">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">所在国家(地区)：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input id="branchCountry" class="hand " value="${supplierBranch.countryName } " type="text" onclick="reason(this)">
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">机构名称：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input id="branchName" class="hand " value="${supplierBranch.organizationName } " type="text" onclick="reason(this)">
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" >详细地址：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input id="branchAddress" class="hand " value="${supplierBranch.detailAddress } " type="text" onclick="reason(this)">
								</div>
							</li>
							<li class="col-md-12 col-sm-12 col-xs-12">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">分支生产经营范围：</span>
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="branchBusinessScope" onclick="reason(this)">${supplierBranch.businessSope }</textarea>
								</div>
							</li>
						</c:forEach>
					</ul>

					<%-- <h2 class="count_flow"><i>8</i>境外分支</h2>
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="overseasBranch2">境外分支机构：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <c:if test="${suppliers.overseasBranch == 0}">
                  <input id="overseasBranch" class="hand " value="无" type="text" onclick="reason(this.id,'overseasBranch')" >
                </c:if>
                <c:if test="${suppliers.overseasBranch == 1}">
                  <input id="overseasBranch" class="hand " value="有" type="text" onclick="reason(this.id,'overseasBranch')" >
                </c:if>
                <div id="overseasBranch3" class="abolish">×</div>
              </div>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="overseasBranch" class="hand " value="<c:if test="${suppliers.overseasBranch == 0}">无</c:if><c:if test="${suppliers.overseasBranch == 1}">有</c:if>" type="text" onclick="reason(this.id,'overseasBranch')" >
                <div id="overseasBranch3" class="abolish">×</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchCountry2">境外分支所在国家(地区)：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="branchCountry" class="hand " value="${suppliers.branchCountry } " type="text" onclick="reason(this.id,'branchCountry')" >
                <div id="branchCountry3" class="abolish">×</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 ">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchAddress2">分支地址：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="branchAddress" class="hand " value="${suppliers.branchAddress } " type="text" onclick="reason(this.id,'branchAddress')" >
                <div id="branchAddress3" class="abolish">×</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchName2">机构名称：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="branchName" class="hand " value="${suppliers.branchName } " type="text" onclick="reason(this.id,'branchName')" >
                <div id="branchName3" class="abolish">×</div>
              </div>
            </li>
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchBusinessScope2">分支生产经营范围：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0">
                <textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="branchBusinessScope" onclick="reason(this.id,'branchBusinessScope')" >${suppliers.branchBusinessScope }</textarea>
                <div id="branchBusinessScope3" onclick="reason(this.id)" class="abolish">×</div>
              </div>
            </li>
          </ul> --%>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
					<!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
					<a class="btn" type="button" onclick="nextStep();">下一步</a>
				</div>
			</div>
			<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
				<input type="hidden" name="fileName" />
			</form>
		</div>
	</body>

</html>