<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<script type="text/javascript">
	/** 保存基本信息 */
	function otherPage(flag) {
		$("input[name='flag']").val(flag);
		$("#template_download_form_id").submit();
	}
	//下载
	function download1(){
		var supplierId = "${supplierId}";
		window.location.href="${pageContext.request.contextPath}/expert/downloadSupplier.html?supplierId=" + supplierId;
	}
	//下载
	function download2(){
		window.location.href="${pageContext.request.contextPath}/expert/downloadSupplierNotice.html";
	}
</script>

</head>

<body>
	<div class="wrapper">

		<!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
			<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
<!-- 						<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
 -->						<div class="line"></div> <span class="step_desc_01">基本信息</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_02">供应商类型</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_02">资质文件维护</span> </span> <span class="new_step current fl"><i class=""> 5</i>
						<div class="line"></div> <span class="step_desc_01">品目合同上传</span> </span> <span class="new_step current fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_02">初审采购机构</span> </span> <span class="new_step current fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> <span class="new_step fl"><i class="">8</i> 
						<span class="step_desc_02">申请表承诺书上传</span> 
					</span>
					<div class="clear"></div>
				</h2>
			</div>
		</c:if>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="template_download_form_id" action="${pageContext.request.contextPath}/supplier/perfect_download.html" method="post">
							<input name="id" value="${currSupplier.id}" type="hidden" /> 
							<input name="jsp" type="hidden" />
							<input name="flag" type="hidden" />
							<input name="supplierTypeIds" value="${supplierTypeIds }"  type="hidden" /> 
							<div class="tab-content padding-top-20">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class="margin-bottom-0  categories">
										<h1 class="f16  mt40">
										 申请表和承诺书下载 
										</h2>
										
									<p style="font-size:15px;">	下载 《供应商入库申请表》<a class="mt3 color7171C6" href="javascript:download1()"><i class="download mr5"></i></a> <span style="margin-left:200px;"></span> 下载《军队供应商承诺书》<a class="mt3 color7171C6" href="javascript:download2()"><i class="download mr5"></i></a></p>
										
										
										 <!-- <table class="table table-bordered">
										  <tr>
										    <td class="info"></td>
  										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
 										   <td class="info">下载《军队供应商承诺书》：</td> -->
<!-- 										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
 -->										  </tr>
										<!--   <tr>
										    <td class="info">军队供应商入库申请表：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										    <td class="info">军队供应商抽取记录表：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										  </tr>
										  <tr>
										    <td class="info">军队供应商实地考察记录表：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										    <td class="info">军队供应商实地考察廉政意见函：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										  </tr>
										  <tr>
										    <td class="info">军队供应商注册信息变更申请表：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										    <td class="info">军队供应商退库申请表：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										  </tr> 
										
										</table>-->
										<%--
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class="zzzx w245"> 军队供应商分级方法：</span>
												<div class="input-append">
													<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商承诺书：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商入库申请表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商抽取记录表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商实地考察记录表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商实地考察廉政意见函：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商注册信息变更申请表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商退库申请表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<div class="clear"></div>
										</ul>
									--%></div>
								</div>
							</div>
						
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	  <div class="btmfix">
	  	  <div class="mt15 tc" >
			  <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="otherPage('prve')">上一步</button>
			  <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="otherPage('next')">下一步</button>
	  	  </div>
	  </div>
	  
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}">
		<jsp:include page="/index_bottom.jsp" />
	</c:if>
</body>
</html>
