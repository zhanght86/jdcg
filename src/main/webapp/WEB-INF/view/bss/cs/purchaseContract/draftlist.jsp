<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>采购合同管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>public/ZHH/js/jquery.min.js" type="text/javascript"></script>
	  <script src="<%=basePath%>public/layer/layer.js"></script>
	  <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
  <script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${list.total}",
		    startRow: "${list.startRow}",
		    endRow: "${list.endRow}",
		    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	var articleId = "${articleId}";
		        	var condition = "${userName}";
		            location.href = "<%=basePath%>downloadUser/selectDownloadUserByArticleId.html?page="+e.curr+"&articleId="+articleId+"&userName="+condition;
		        }
		    }
		});
  });
	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 }
	}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
	
  	function view(id){
  		window.location.href="<%=basePath%>articletype/view.html?id="+id;
  	}
    
  	function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="<%=basePath%>downloadUser/deleteDownloadUser.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
  	
  	function query(){
  		var projectName = $("#projectName").val();
  		var projectCode = $("#projectCode").val();
  		var purchaseDep = $("#purchaseDep").val();
  		window.location.href="<%=basePath%>purchaseContract/selectAllPuCon.html?projectName="+projectName+"&projectCode="+projectCode+"&purchaseDep="+purchaseDep;
  	}
  	
  	function reset(){
  		$("#projectName").val("");
  		$("#projectCode").val("");
  		$("#purchaseDep").val("");
  	}
  	
  	function updateDraft(){
  		var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条修改",{offset: ['222px', '390px'], shade:0.01});
			}else{
				window.location.href="<%=basePath%>purchaseContract/createDraftContract.html?ids="+ids;
			}
		}else{
			layer.alert("请选择要修改的草稿",{offset: ['222px', '390px'], shade:0.01});
		}
  	}
  </script>
  </head>
  
  <body>

	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">采购合同管理</a></li><li><a href="#">合同草稿管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container">
   <div class="headline-v2">
      <h2>查询条件</h2>
   </div>
<!-- 项目戳开始 -->
  <div class="container clear">
  <div class="p10_25">
     <h2 class="padding-10 border1">
    	<ul class="demand_list">
          <li class="fl ml8"><label class="fl mt10">采购项目：</label><span><input type="text" value="" id="projectName" class="mb0 mt5"/></span></li>
	      <li class="fl ml8"><label class="fl mt10">合同编号：</label><span><input type="text" value="" id="projectCode" class="mb0 mt5"/></span></li>
	      <li class="fl ml8"><label class="fl mt10">供应商：</label><span><input type="text" value="" id="purchaseDep" class="mb0 mt5"/></span></li>
	      <li class="fl ml8"><label class="fl mt10">采购机构：</label><span><input type="text" value="" id="purchaseDep" class="mb0 mt5"/></span></li>
	      <li class="fl ml8"><label class="fl mt10">需求部门：</label><span><input type="text" value="" id="purchaseDep" class="mb0 mt5"/></span></li>
	      <li class="fl ml8"><label class="fl mt10">计划文件号：</label><span><input type="text" value="" id="purchaseDep" class="mb0 mt5"/></span></li>
	      <li class="fl ml8"><label class="fl mt10">年度：</label><span><input type="text" value="" id="purchaseDep" class="mb0 mt5"/></span></li>
	      <li class="fl ml8"><label class="fl mt10">项级预算科目：</label><span><input type="text" value="" id="purchaseDep" class="mb0 mt5"/></span></li>
    	  <div class="fl col-md-4 tc">
    	    <button type="button" onclick="query()" class="btn">查询</button>
    	    <button type="reset" onclick="reset()" class="btn">重置</button>
    	  </div>
    	</ul>

    	  <div class="clear"></div>
     </h2>
   </div>
   </h2>
  </div>
   <div class="headline-v2 fl">
      <h2>合同草稿列表
	  </h2>
   </div> 
   	  <span class="fr option_btn margin-top-20 mr10">
   	  	<button class="btn padding-left-10 padding-right-10 btn_back" onclick="updateDraft()">修改</button>
   	  	<button class="btn padding-left-10 padding-right-10 btn_back" onclick="delDraft()">删除</button>
	    <button class="btn padding-left-10 padding-right-10 btn_back" onclick="createContract()">生成正式合同</button>
	  </span>
   <div class="container clear">
    <div class="p10_25">
   	<table class="table table-bordered table-condensed mt5">
		<thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
			    <th class="info w50">序号</th>
				<th class="info">合同名称</th>
				<th class="info">合同编号</th>
				<th class="info">合同金额</th>
				<th class="info">项目名称</th>
				<th class="info">供应商名称</th>
				<th class="info">采购机构</th>
				<th class="info">需求部门</th>
				<th class="info">计划文件号</th>
				<th class="info">预算</th>
				<th class="info">年度</th>
				<th class="info">项级预算科目</th>
			</tr>
		</thead>
		<c:forEach items="${draftConList}" var="draftCon" varStatus="vs">
			<tr>
				<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${draftCon.id}" /></td>
				<td class="tc pointer">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				<td class="tc pointer">${draftCon.name}</td>
				<td class="tc pointer">${draftCon.code}</td>
				<td class="tc pointer">${draftCon.money}</td>
				<td class="tc pointer">${draftCon.projectName}</td>
				<td class="tc pointer">${draftCon.purchaseDepName}</td>
				<td class="tc pointer">${draftCon.supplierDepName}</td>
				<td class="tc pointer">${draftCon.demandSector}</td>
				<td class="tc pointer">${draftCon.documentNumber}</td>
				<td class="tc pointer">${draftCon.budget}</td>
				<td class="tc pointer">${draftCon.year}</td>
				<td class="tc pointer">${draftCon.budgetSubjectItem}</td>
			</tr>
		</c:forEach>
	</table>
     </div>
    </div>
   <div id="pagediv" align="right"></div>
   </div>
</body>
</html>
