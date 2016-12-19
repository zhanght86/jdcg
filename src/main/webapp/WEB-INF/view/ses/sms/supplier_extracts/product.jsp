<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    type="text/css">

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript">
    var datas;
    var treeObj;
  $(function(){
	  var setting={
              async:{
                          autoParam:["id"],
                          enable:true,
                          url:"${pageContext.request.contextPath}/SupplierExtracts/getTree.do?projectId=${projectId}",
                          dataType:"json",
                          type:"post",
                      },
                      callback:{
                          onClick:zTreeOnClick,//点击节点触发的事件
                          onCheck:zTreeOnCheck
                          
                      }, 
                      data:{
                          simpleData:{
                              enable:true,
                              idKey:"id",
                              pIdKey:"pId",
                              rootPId:0,
                          }
                      },
                     check:{
                          chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
                          chkStyle:"checkbox", 
                          enable: true
                     }
        };
	     treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
	     
    });
    var treeid=null;
  /*树点击事件*/
  function zTreeOnClick(event,treeId,treeNode){
      treeid=treeNode.id;
  }
  /*树点击事件*/
  var check;
  function zTreeOnCheck(event,treeId,treeNode){
      treeid=treeNode.id;
     
  }
  //获取选中子节点id
  function getChildren(cate){
      var Obj=$.fn.zTree.getZTreeObj("ztree");  
       var nodes=Obj.getCheckedNodes(true);  
       var ids = new Array();
       var names=new Array();
       for(var i=0;i<nodes.length;i++){ 
           if(!nodes[i].isParent){
          //获取选中节点的值  
           ids+=nodes[i].id+","; 
           names+=nodes[i].name+",";
           }
       } 
        //专家类型
        parent.$("#exttypeid").val();
        //是否满足
       var issatisfy=$('input[name="radio"]:checked ').val();
         
       if(cate!=null){
           $(cate).val(names.substring(0,names.length-1));
           $(cate).parent().parent().parent().parent().parent().find("#extCategoryNames").val(names.substring(0,names.length-1));
           $(cate).parent().parent().parent().parent().parent().find("#extCategoryId").val(ids.substring(0,ids.length-1));
           $(cate).parent().parent().parent().parent().parent().find("#isSatisfy").val(issatisfy);
           
       }
	             var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	             parent.layer.close(index);
  }     
  function resetQuery(){
	  alert("sds");
//       $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("10");
  }
  
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container margin-top-30">
		<form action="${pageContext.request.contextPath}/SupplierExtracts/listSupplier.do"
			method="post" id="form1">
			<div>
				<ul class="list-unstyled list-flow p0_20">
					<input class="span2" name="id" type="hidden">
<!-- 					<li class="col-md-6 p0 "> -->
<!-- 				    	<span class="red textspan">*</span>抽取数量： -->
<!-- 						<div class="input-append"> -->
<!-- 							<input class="span2 w200" maxlength="3" id="extcount" value="10" name="title" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" -->
<!--                                 onafterpaste="this.value=this.value.replace(/\D/g,'')"> -->
<!-- 						</div> -->
<!-- 					</li> -->
					<li class="col-md-6  p0 ">
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" checked="checked"
								value="1" class="fl" />
							<div class="ml5 fl">满足某一产品条件即可</div>
						</div>
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" value="2" class="fl" />
							<div class="ml5 fl">同时满足多个产品条件</div>
						</div>
					</li>
				</ul>
				<br />
			</div>
			 <span class="red textspan margin-left-13">*</span>
			<div id="ztree" class="ztree margin-left-13"></div>
<!-- 			<br /> -->
<!-- 			<ul id="ultype" class="list-unstyled list-flow p0_20 dnone"> -->
<!-- 				<li class="col-md-6 p0 "> -->
<!-- 				<div  class="ml5 fl">供应商类型:</div> -->
<!-- 					<div class="fl mr10"> -->
<!-- 					  <input name="expertstypeid" class="fl"  id="xschecked"  type="checkbox" -->
<!--                             value="SALES"> -->
<!-- 						<div class="ml5 fl">销售型</div> -->
<!-- 					</div> -->
<!-- 					<div class="fl mr10"> -->
<!-- 						<input name="expertstypeid" class="fl" type="checkbox" value="PRODUCT"> -->
<!-- 						<div class="ml5 fl">生产型</div> -->
<!-- 					</div> -->
<!-- 					<div class="fl mr10 dnone"> -->
<!--                         <input name="expertstypeid"  class="fl " id="gcchecked" type="checkbox" value="PROJECT"> -->
<!--                         <div class="ml5 fl">工程</div> -->
<!--                     </div> -->
<!-- 				    <div class="fl mr10 dnone"> -->
<!--                         <input name="expertstypeid" class="fl " id="fwchecked" type="checkbox" value="SERVICE"> -->
<!--                         <div class="ml5 fl">服务</div> -->
<!--                     </div> -->
<!-- 				</li> -->
<!-- 			</ul> -->
		</form>
	</div>
</body>
</html>
