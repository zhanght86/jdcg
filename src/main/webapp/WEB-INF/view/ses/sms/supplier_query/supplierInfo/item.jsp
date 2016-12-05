<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<script type="text/javascript">
function reason(id){
  var supplierId=$("#id").val();
  var id1=id+"1";
  var id2=id+"2";
  var id3=id+"3";
  var auditField=$("#"+id2+"").text().replaceAll("＊","").replaceAll("：",""); //审批的字段名字
  var  auditContent= document.getElementById(""+id3+"").value; //审批的字段内容
  var auditType=$("#essential").text(); //审核类型
  layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditField="+auditField+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId,
      });
  $("#"+id1+"").hide();
  layer.msg("审核不通过的理由是："+text);    
    });
}
function tijiao(str){
  var action;
  if(str=="essential"){
     action ="${pageContext.request.contextPath}/supplierQuery/essential.html";
  }
  if(str=="financial"){
    action = "${pageContext.request.contextPath}/supplierQuery/financial.html";
  }
  if(str=="shareholder"){
    action = "${pageContext.request.contextPath}/supplierQuery/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialProduction.html";
  }
  if(str=="materialSales"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialSales.html";
  }
  if(str=="engineering"){
    action = "${pageContext.request.contextPath}/supplierQuery/engineering.html";
  }
  if(str=="service"){
    action = "${pageContext.request.contextPath}/supplierQuery/serviceInformation.html";
  }
  if(str=="chengxin"){
    action = "${pageContext.request.contextPath}/supplierQuery/list.html";
  }
  if(str=="item"){
     action = "${pageContext.request.contextPath}/supplierQuery/item.html";
  }
    if(str=="product"){
     action = "${pageContext.request.contextPath}/supplierQuery/product.html";
  }
     if(str=="updateHistory"){
     action = "${pageContext.request.contextPath}/supplierQuery/showUpdateHistory.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}

function downloadFile(fileName){
	  fileName=encodeURI(fileName);
      fileName=encodeURI(fileName);
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/downLoadFile.html?fileName="+fileName;
}
function fanhui(){
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address="+encodeURI(encodeURI('${suppliers.address}'))+"&status=${status}";
}
   $(function() {
    var zTreeObj;
	var zNodes;
	loadZtree();
	function loadZtree() {
		var setting = {
			async : {
				enable : true,
				url : "${pageContext.request.contextPath}/category/find_category_and_disabled.do",
				otherParam : {
					supplierId : "${id}",
				},
				dataType : "json",
				type : "post",
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
		};
		zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
	}
	});
</script>

<style type="text/css">
.line{
	border: 4px solid #fff0;
}

</style>
</head>
  
<body>
 <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="#"> 首页</a>
                </li>
                <li><a href="#">支撑系统</a>
                </li>
                <li><a href="#">供应商查看</a>
                </li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <!-- 项目戳开始 -->
    <div class="container clear margin-top-30">
        <!-- <div class="container">
   <div class="col-md-12">
    <button class="btn btn-windows back" onclick="fanhui()">返回</button> 
    </div>
    </div> -->
        <!--详情开始-->
        <div class="container content pt0">
            <div class="tab-v2">
                <ul class="nav nav-tabs bgwhite">
            <li class=""><a aria-expanded="true" class="f18"  href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
            <li class=""><a aria-expanded="false" class="f18"  href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
            <li class=""><a aria-expanded="fale" class="f18"  href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
            <c:if test="${fn:contains(suppliers.supplierType, '生产')}">
            <li class=""><a aria-expanded="fale" href="#tab-2" class="f18"  data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '销售')}">
            <li class=""><a aria-expanded="fale" href="#tab-3" class="f18"  data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
            </c:if>
            <c:if test="${fn:contains(suppliers.supplierType, '工程')}">
            <li class=""><a aria-expanded="false" href="#tab-3" class="f18"  data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '服务')}">
            <li class=""><a aria-expanded="false" href="#tab-3"  class="f18" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
            </c:if>
            <li class="active"><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18"  onclick="tijiao('item');">品目信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3"  class="f18" data-toggle="tab" onclick="tijiao('product');" >产品信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2"  class="f18" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" class="f18"  data-toggle="tab" onclick="tijiao('updateHistory');">历史修改记录</a></li>
          </ul>
            <div class="tab-content padding-top-20">
              <div class="tab-pane fade active in height-450" id="tab-1">
                <form id="form_id" action="" method="post">
                    <input name="supplierId" id="id" value="${id }" type="hidden">
                  </form> 
                  <div class="col-md-3">
	 					<div id="ztree" class="ztree"></div>
					</div>                
              </div>
          </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
