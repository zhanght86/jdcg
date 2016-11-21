<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>编制招标文件</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
	function OpenFile(fileId) {
		var obj = document.getElementById("TANGER_OCX");
		obj.Menubar = true;
		obj.Caption = "( 双击可放大 ! )";
		if(fileId != 0){
			obj.BeginOpenFromURL("${pageContext.request.contextPath}/open_bidding/loadFile.html?fileId="+fileId, true);// 异步加载, 服务器文件路径
		} else {
	    	obj.BeginOpenFromURL("${pageContext.request.contextPath}/stash/bidFileTemp.docx", true);// 异步加载, 服务器文件路径
		}
		
		//obj.OpenFromURL("http://localhost/${pageContext.request.contextPath}/stash/bidFileTemp.doc");
		
	}
	
	
	function exportWord() {
		var obj = document.getElementById("TANGER_OCX");
		// 参数说明
		// 1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
		//obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html", "bidFile", "", "bid.doc", "MyFile");
	}
	
	function queryVersion(){
	
		var obj = document.getElementById("TANGER_OCX");
		var v = obj.GetProductVerString();
		obj.ShowTipMessage("当前ntko版本",v);
	}
	
	function inputTemplete(){
		var obj = document.getElementById("TANGER_OCX");
	}
	
	function saveFile(){
		var projectId = $("#projectId").val();
		var flowDefineId = $("#flowDefineId").val();
		var projectName = $("#projectName").val();
		var obj = document.getElementById("TANGER_OCX");
		//参数说明
		//1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
		obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html?projectId="+projectId+"&flowDefineId="+flowDefineId, "ntko", "", projectName+"_招标文件.doc", "MyFile");
	}
	
	function closeFile(){
		var obj = document.getElementById("TANGER_OCX");
		obj.close();
	}
		
	function jump(url){
      	$("#open_bidding_main").load(url);
    }
</script>

<!-- 打开文档后只读 -->
<script type="text/javascript" for="TANGER_OCX" event="OnDocumentOpened(a,b)">
		var obj = document.getElementById("TANGER_OCX");
		var st = $("#ope").val();
		if(st == 'view'){
			obj.SetReadOnly(true);
		}
</script>
</head>

<body onload="OpenFile('${fileId}')">
	 <div class="col-md-12 p0">
	   <ul class="flow_step">
	   	 <c:if test="${ope == 'add' }">
		     <li >
			   <a  href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${project.id}&flowDefineId=${flowDefineId}" >01、符合性</a>
			   <i></i>
			 </li>
			 
			 <li >
			   <a  href="${pageContext.request.contextPath}/firstAudit/toPackageFirstAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}" >02、符合性关联</a>
			   <i></i>							  
			 </li>
		     <li>
			   <a  href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${project.id}&flowDefineId=${flowDefineId}">03、评标细则</a>
			   <i></i>
			 </li>
			 <li class="active">
			   <a  href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${project.id}&flowDefineId=${flowDefineId}" >
			   <c:if test="${type eq 'gkzb' }">
			     04、招标文件
			     </c:if>
			    <c:if test="${type eq 'jzxtp' }">
			     04、竞谈文件
			    </c:if>
			   </a>
			 </li>
	   	 </c:if>
	   	 <c:if test="${ope == 'view' }">
	   	 	<li >
		   <a  href="${pageContext.request.contextPath}/open_bidding/firstAduitView.html?projectId=${project.id}&flowDefineId=${flowDefineId }" >01、符合性</a>
		   <i></i>
		 </li>
		 <li>
		   <a href="${pageContext.request.contextPath}/open_bidding/packageFirstAuditView.html?projectId=${project.id}&flowDefineId=${flowDefineId }">02、符合性关联</a>
		   <i></i>							  
		 </li>
	     <li>
		   <a  href="${pageContext.request.contextPath}/intelligentScore/packageListView.html?projectId=${project.id}&flowDefineId=${flowDefineId }">03、评标细则</a>
		   <i></i>
		 </li>
		 <li class="active">
		   <a  href="${pageContext.request.contextPath}/open_bidding/bidFileView.html?id=${project.id}&flowDefineId=${flowDefineId }" >
		     <c:if test="${type eq 'gkzb' }">04、招标文件</c:if>
		     <c:if test="${type eq 'jzxtp' }"> 04、竞谈文件</c:if>
		   </a>
		   <i></i>
		 </li>
		 <li>
		    <c:if test="${project.confirmFile == 0 || project.confirmFile==null}"><a onclick="confirmOk(this,'${projectId}','${flowDefineId }');" id="queren">05、确认</a></c:if>
		    <c:if test="${project.confirmFile == 1 }"><a>05、已确认</a></c:if>
		 </li>
	   	 </c:if>
	   </ul>
	 </div>
	 <!-- 按钮 -->
	 <c:if test="${project.confirmFile == 0 && ope =='add'}">
	     <div class="mt10 mb10">
	      	 <!-- <input type="button" class="btn btn-windows cancel" onclick="delMark()" value="删除标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="closeFile()" value="关闭当前文档"></input> -->
	      	 <!-- <input type="button" class="btn btn-windows " onclick="queryVersion()" value="版本查询"></input> -->
	     	<input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input>
	        <input type="button" class="btn btn-windows save" onclick="saveFile()" value="保存到服务器"></input>
	    </div>
	 </c:if>
	<form id="MyFile" method="post">
		<input type="hidden" id="ope" value="${ope }">
		<input type="hidden" id="confirmFileId" value="${project.confirmFile}">
		<input type="hidden" id="flowDefineId" value="${flowDefineId }">
    	<input type="hidden" id="projectId" value="${project.id }">
    	<input type="hidden" id="projectName" value="${project.name }">
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
	</form>
</body>
</html>
