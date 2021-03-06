<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>申请表</title>
		<script type="text/javascript">
		  $(function() {
		    $("li").each(function() {
		      $(this).find("p").hide();
		    });
		    
		    $("li").find("span").each(function() {
		      	var onMouseMove = "this.style.background='#E8E8E8'";
						var onmouseout = "this.style.background='#FFFFFF'";
		       $(this).attr("onMouseMove",onMouseMove);
		       $(this).attr("onmouseout",onmouseout);
		    });
		  });
		
		
			function reason1(ele,auditField){
			  var supplierId=$("#supplierId").val();
			  var auditFieldName = $(ele).parents("li").find("span").text().replace("：","");//审批的字段名字
			  var index = layer.prompt({
				  title: '请填写不通过的理由：', 
				  formType: 2, 
				  offset: '100px'
			    },
		    function(text){
		      $.ajax({
		        url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
		        type: "post",
		          data: {"auditType":"download_page","auditFieldName":auditFieldName,"auditContent":"附件","suggest":text,"supplierId":supplierId,"auditField":auditField},
		          dataType: "json",
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
			  /* $(ele).parent("li").find("div").eq(1).show(); //显示叉
			         layer.close(index); */
			         
			   $(ele).parents("li").find("p").show(); //显示叉
		       layer.close(index);
		    });
		  }
			
			//下一步
			function nextStep(){
			  var action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
		
			//上一步
			function lastStep(){
			  var action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
			
		
		  //文件下載
		  function downloadFile(fileName) {
		    $("input[name='fileName']").val(fileName);
		    $("#download_form_id").submit();
		  }
		</script>
		<script type="text/javascript">
		/*   function zhancun(){
		    var supplierId=$("#supplierId").val();
		    $.ajax({
		      url:"${pageContext.request.contextPath}/supplierAudit/temporaryAudit.html",
		      type:"post",
		      data:"id="+supplierId,
		      dataType:"json",
		      success:function(result){
		        result = eval("(" + result + ")");
		        if(result.msg == "success"){
		          layer.msg("暂存成功！");
		        }
		      },error:function(){
		        layer.msg("暂存失败！");
		      }
		    });
		  } */
		</script>
		<script type="text/javascript">
			function jump(str){
			  var action;
			  if(str=="essential"){
			     action ="${pageContext.request.contextPath}/supplierAudit/essential.html";
			  }
			  if(str=="financial"){
			    action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
			  }
			  if(str=="shareholder"){
			    action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
			  }
			  /*if(str=="materialProduction"){
			    action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
			  }
			  if(str=="materialSales"){
			    action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
			  }
			  if(str=="engineering"){
			    action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
			  }
			  if(str=="serviceInformation"){
			    action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
			  }*/
			  if(str=="items"){
			    action = "${pageContext.request.contextPath}/supplierAudit/items.html";
			  }
			  if(str == "aptitude") {
					action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
				}
			  if(str=="contract"){
			    action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
			  }
			  if(str=="applicationForm"){
			    action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
			  }
			  if(str=="reasonsList"){
			    action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
			  }
			  if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
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
            <a href="#"> 首页</a>
          </li>
          <li>
						<a href="#">支撑环境</a>
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
        <div class="content ">
          <div class="col-md-12 tab-v2 job-content">
	          <ul class="flow_step">
		          <li onclick = "jump('essential')">
		            <a aria-expanded="false" href="#tab-1">详细信息</a>
		            <i></i>
		          </li>
		          <li onclick = "jump('financial')">
		            <a aria-expanded="true" href="#tab-2">财务信息</a>
		            <i></i>                            
		          </li>
		          <li onclick = "jump('shareholder')" >
		            <a aria-expanded="false" href="#tab-3">股东信息</a>
		            <i></i>
		          </li>
		          <%--<c:if test="${fn:contains(supplierTypeNames, '生产')}">
		            <li onclick = "jump('materialProduction')">
		              <a aria-expanded="false" href="#tab-4">生产信息</a>
		              <i></i>
		            </li>
		          </c:if>
		          <c:if test="${fn:contains(supplierTypeNames, '销售')}">
		            <li onclick = "jump('materialSales')" >
		              <a aria-expanded="false" href="#tab-4" >销售信息</a>
		              <i></i>
		            </li>
		          </c:if>
		          <c:if test="${fn:contains(supplierTypeNames, '工程')}">
		            <li onclick = "jump('engineering')">
		              <a aria-expanded="false" href="#tab-4">工程信息</a>
		              <i></i>
		            </li>
		          </c:if>
		          <c:if test="${fn:contains(supplierTypeNames, '服务')}">
		            <li onclick = "jump('serviceInformation')" >
		              <a aria-expanded="false" href="#tab-4" >服务信息</a>
		              <i></i>
		            </li>
		          </c:if>
		          --%>
		          <li onclick = "jump('supplierType')">
		           	<a aria-expanded="false">供应商类型</a>
		           	<i></i>
			      </li>
		          <li onclick = "jump('items')">
	            	<a aria-expanded="false" href="#tab-4" >产品类别</a>
	            	<i></i>
	          	</li>
	          	<li onclick="jump('aptitude')">
								<a aria-expanded="false">资质文件维护</a>
								<i></i>
							</li>
		          <li onclick = "jump('contract')" >
		            <a aria-expanded="false" href="#tab-4">销售合同</a>
		             <i></i>
		          </li>
		          <li onclick = "jump('applicationForm')" class="active" >
		            <a aria-expanded="false" href="#tab-4" data-toggle="tab">承诺书和申请表</a>
		            <i></i>
		          </li>
		          <li onclick = "jump('reasonsList')">
		            <a aria-expanded="false" href="#tab-4" >审核汇总</a>
		          </li>
		        </ul>
            
            <form id="form_id" action="" method="post" >
                <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                <input name="supplierStatus" value="${supplierStatus}" type="hidden">
            </form>
            
            <ul class="count_flow ul_list hand">
              <%-- <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierLevel');" >军队供应商分级方法：</span>
                <up:show showId="lvel_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLevel}"/>
                <p class="b f18 ml10 red">×</p>
              </li> --%>
              <li class="col-md-6 mt10 mb25" >
	              <span class="col-md-5 padding-left-5" onclick="reason1(this,'supplierPledge');" >军队供应商承诺书：</span>
	                <u:show showId="pledge_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierPledge}"/>
	                <p><img style="padding-left: 80px;" src='/zhbj/public/backend/images/sc.png'></p>
              </li>
	             <li class="col-md-6 p0 mt10 mb25">
                <span class="col-md-5 padding-left-5" onclick="reason1(this,'supplierRegList');" >军队供应商入库申请表：</span>
                <u:show showId="regList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierRegList}"/>
                <p><img style="padding-left: 80px;" src='/zhbj/public/backend/images/sc.png'></p>
	            </li>
	            <%-- <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierInspectList');" >军队供应商实地考察记录表：</span>
                <up:show showId="inspectList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierInspectList}"/>
                <p class="b f18 ml10 red">×</p>
	            </li>
	            <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierReviewList');" >军队供应商考察廉政意见函：</span>
                <up:show showId="reviewList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierReviewList}"/>
                <p class="b f18 ml10 red">×</p>
	            </li>
	            <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierChangeList');" >军队供应商注册变更申请表：</span>
                <up:show showId="changeList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierChangeList}"/>
                <p class="b f18 ml10 red">×</p>
	            </li>
	            <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierExitList');" >军队供应商退库申请表：</span>
                <up:show showId="exitList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierExitList}"/>
                <p class="b f18 ml10 red">×</p>
	             </li> --%>
             </ul>
           </div>
	         <div class="col-md-12 add_regist tc">
	           <!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
	           <a class="btn"  type="button" onclick="lastStep();">上一步</a>
	           <a class="btn"  type="button" onclick="nextStep();">下一步</a>
	         </div>
         </div>
       </div> 
     <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
       <input type="hidden" name="fileName" />
     </form>
  </body>
</html>
