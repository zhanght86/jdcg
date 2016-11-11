<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>进口供应商注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="${pageContext.request.contextPath}/public/ZHH/css/import_supplier.css" media="screen" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/supplier/validateSupplier.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/expert/TestAddress1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/expert/TestChooseAddress.js"></script>
<script type="text/javascript">
var parentId ;
var addressId="${is.address}";
$.ajax({
		url : "${pageContext.request.contextPath}/area/find_by_id.do",
		data:{"id":addressId},
		success:function(obj){
			//alert(JSON.stringify(obj));
			var data = eval('(' + obj+ ')');
			$.each(data,function(i,result){
				if(addressId == result.id){
					parentId = result.areaType;
				$("#haha").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
				}else{
					$("#haha").append("<option value='"+result.id+"'>"+result.name+"</option>");
				}
				
			});
			//alert(JSON.stringify(data));
			//alert(parentId);
			
		},
		error:function(obj){
			
		}
		
	});

	   function tijiao(){
    	if(!validateBusinessSupplierInfo()){
    		return;
    	}else{
    		form1.submit();
    	}
    }
    
	$(function() {
	/** 校验用户名是否存在 */
		$("#loginName").blur(function() {
			var loginName = $(this).val();
			var id = $("#id").val();
			if(loginName) {
				$.ajax({
					url : "${pageContext.request.contextPath}/importSupplier/checkLoginName.do",
					type : "post",
					data : {
						loginName : loginName,
						id:id
					},
					success : function(result) {
						 if(result == false) {
							layer.tips("用户名已存在，请重新填写.", "#loginName");
							$("#loginName").val("");
						}
					},
				});
			}
		});
		
			$("#name").blur(function() {
			var name = $(this).val();
			var id=$("#id").val();
			if(name) {
				$.ajax({
					url : "${pageContext.request.contextPath}/importSupplier/checkSupName.do",
					type : "post",
					data : {
						name : name,
						id:id
					},
					success : function(result) {
						 if(result == false) {
							layer.tips("企业已存在，请重新填写.", "#name");
							$("#name").val("");
						}
					},
				});
			}
		}); 
		
		/** 校验手机号是否存在 */
		$("#mobile").blur(function() {
			var mobile = $(this).val();
			if(mobile) {
				$.ajax({
					url : "${pageContext.request.contextPath}/importSupplier/checkMobile.do",
					type : "post",
					data : {
						mobile : mobile
					},
					success : function(result) {
						 if(result == false) {
							layer.tips("手机号已注册，请重新填写.", "#mobile", {
								tips : 1
							});
							$("#mobile").val();
						}
					},
				});
			}
		});
		
		$("#password").change(function() {
			var password = $("#password").val();
			if(!password) {
				layer.tips("请输入密码", "#password", {
					tips : 1
				});
				return false;
			} else if(!password.match(/^(?!(?:\d*$))[A-Za-z0-9_]{6,20}$/)) {
				layer.tips("密码由6-20位字母 数字组成 !", "#password", {
					tips : 1
				});
				return false;
			}
		});
		
		$("#confirmPassword").change(function() {
			var confirmPassword = $("#confirmPassword").val();
			var password = $("#password").val();
			if (!confirmPassword) {
				layer.tips("请输入确认密码 !", "#confirmPassword", {
					tips : 1
				});
				return false;
			} else if (confirmPassword != password) {
				layer.tips("密码不一致 !", "#confirmPassword", {
					tips : 1
				});
				return false;
			}
		});
			$.ajax({
			url : "${pageContext.request.contextPath}/area/listByOne.do",
			success:function(obj){
				var data = eval('(' + obj + ')');
				$.each(data,function(i,result){
					if(parentId == result.id){
						$("#hehe").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
					}else{
					$("#hehe").append("<option value='"+result.id+"'>"+result.name+"</option>");
					}
				});
				
				//alert(JSON.stringify(obj));
			},
			error:function(obj){
				
			}
			
		});
	});
		function fun(){
		var parentId = $("#hehe").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_area_by_parent_id.do",
			data:{"id":parentId},
			success:function(obj){
				$("#haha").append("<option value=''>-请选择-</option>");
				$.each(obj,function(i,result){
					
					$("#haha").append("<option value='"+result.id+"'>"+result.name+"</option>");
				});
				
				//alert(JSON.stringify(obj));
			},
			error:function(obj){
				
			}
			
		});
	}
</script>

</head>

<body>
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">进口供应商登记</a></li><li class="active"><a href="#">修改进口供应商</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
		<form id="form1" action="${pageContext.request.contextPath}/importSupplier/update.html" method="post">
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<div class="tab-content padding-top-20  h730">
							<div class="tab-pane fade active in height-450">
								<div class=" margin-bottom-0">
									<ul class="list-unstyled list-flow">
										 	<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 企业名称：</span>
											<div class="input-append">
											    <input  value="${is.id }" name="id"  type="hidden">
												<input class="span3" id="name" value="${is.name }" name="name"  type="text">
												<div class="validate">${ERR_name}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 企业类别：</span>
											<div class="input-append">
												<input class="span3" id="supplierType" value="${is.supplierType }" name="supplierType"   type="text">
												<div class="validate">${ERR_supplierType}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 中文译名：</span>
											<div class="input-append">
												<input class="span3" id="chinesrName" value="${is.chinesrName }" name="chinesrName"  type="text">
												<div class="validate">${ERR_chinesrName}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 法定代表人：</span>
											<div class="input-append">
												<input class="span3" id="legalName" value="${is.legalName }" name="legalName" type="text">
												<div class="validate">${ERR_legalName}</div>
											</div>
										</li>
										<li class="col-md-6 p0"><span class=""><i class="red">＊</i>企业注册地址：</span>
											<select id="hehe" onchange="fun();">
													<option>-请选择-</option>
												</select>
												<select name="address" id="haha">
													<option>-请选择-</option>
												</select>
  												<div class="validate">${ERR_address}</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>邮政编码：</span>
											<div class="input-append">
												<input class="span3" id="postCode" value="${is.postCode }" name="postCode"  type="text">
												<div class="validate">${ERR_postCode}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>经营产品大类：</span>
											<div class="input-append">
												<input class="span3" id="productType" value="${is.productType }" name="productType"  type="text">
												<div class="validate">${ERR_productType}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>主营产品：</span>
											<div class="input-append">
												<input class="span3" id="majorProduct" value="${is.majorProduct }" name="majorProduct"  type="text">
												<div class="validate">${ERR_majorProduct}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>兼营产品：</span>
											<div class="input-append">
												<input class="span3" id="byproduct" value="${is.byproduct }" name="byproduct"  type="text">
												<div class="validate">${ERR_byproduct}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>生产商名称：</span>
											<div class="input-append">
												<input class="span3" id="producerName" value="${is.producerName }" name="producerName"   type="text">
												<div class="validate">${ERR_producerName}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 联系人：</span>
											<div class="input-append">
												<input class="span3" id="contactPerson" value="${is.contactPerson }" name="contactPerson"  type="text">
												<div class="validate">${ERR_contactPerson}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 电话：</span>
											<div class="input-append">
												<input class="span3" id="telephone" value="${is.telephone }" name="telephone"  type="text">
												<div class="validate">${ERR_telephone}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 传真：</span>
											<div class="input-append">
												<input class="span3" id="fax" value="${is.fax }" name="fax" type="text">
												<div class="validate">${ERR_fax}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 电子邮件：</span>
											<div class="input-append">
												<input class="span3" id="email" value="${is.email }" name="email"  type="text">
												<div class="validate">${ERR_email}</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 企业网址：</span>
											<div class="input-append">
												<input class="span3" id="website" value="${is.website }" name="website"  type="text">
												<div class="validate">${ERR_website}</div>
											</div>
										</li>
										<li class="col-md-12 p0 mt10"><span class="fl">国内供货业绩：</span>
											<div class="col-md-9 mt5">
												<div class="row">
													<textarea class="text_area col-md-12"  id="civilAchievement" name="civilAchievement"  title="不超过800个字" placeholder="">${is.civilAchievement }</textarea>
												</div>
											</div>
											<div class="clear"></div>
										</li>
										<li class="col-md-12 p0 mt10"><span class="fl">企业简介：</span>
											<div class="col-md-9 mt5">
												<div class="row">
													<textarea class="text_area col-md-12" id="remark" name="remark" title="不超过800个字" placeholder="">${is.remark }</textarea>
												</div>
											</div>
											<div class="clear"></div>
										</li> 
									</ul>
									</div>
									<div class="tc mt20 clear col-md-11">
									        <button class="btn btn-windows reset" onclick="history.go(-1)" type="button">返回</button>
										<button class="btn btn-windows git"   onclick="tijiao()"  >更新</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
</body>
</html>
