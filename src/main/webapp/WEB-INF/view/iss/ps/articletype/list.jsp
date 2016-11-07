<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>栏目管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js" type="text/javascript"></script>
	  <script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
	  <script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
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
		            location.href = '${pageContext.request.contextPath}/articletype/getAll.do?page='+e.curr;
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
  		window.location.href="${pageContext.request.contextPath}/articletype/view.html?id="+id;
  	}
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="${pageContext.request.contextPath}/articletype/edit.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的栏目",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
	//鼠标移动显示全部内容
	function out(describe){
	if(describe.length>10){
	layer.msg(describe, {
			icon:6,
			shade:false,
			area: ['600px'],
			time : 1000    //默认消息框不关闭
		});//去掉msg图标
	}else{
		layer.closeAll();//关闭消息框
	}
}
  </script>
  </head>
  
  <body>

	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">信息服务</a></li><li><a href="#">栏目管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>栏目管理</h2>
	   </div>
<!-- 表格开始-->
   <div class="container">
    <div class="col-md-12 pl20">
	 <button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
	</div>
   </div>
   
   <div class="container">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    	<table class="table table-bordered table-condensed table-hover">
		<thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
			    <th class="info" >序号</th>
				<th class="info" >栏目名</th>
				<th class="info">栏目介绍</th>
				<th class="info">创建时间</th>
				<th class="info">更新时间</th>
				<th class="info">创建人</th>
			</tr>
		</thead>
		
		<c:forEach items="${list.list}" var="articletype" varStatus="vs">
			<tr>
				<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${articletype.id}" /></td>
				<td class="tc pointer" onclick="view('${articletype.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				<td class="tc pointer" onclick="view('${articletype.id}')">${articletype.name}</td>
				
				<c:set value="${articletype.describe}" var="describe"></c:set>
				<c:set value="${fn:length(describe)}" var="length"></c:set>
				<c:if test="${length>10}">
					<td onclick="view('${articletype.id}')" onmouseover="out('${articletype.describe}')" class="tc pointer ">${fn:substring(describe,0,10)}...</td>
				</c:if>
				<c:if test="${length<10}">
					<td onclick="view('${articletype.id}')" onmouseover="out('${articletype.describe}')" class="tc pointer ">${articletype.describe } </td>
				</c:if>	
				<td class="tc pointer" onclick="view('${articletype.id}')"><fmt:formatDate value='${articletype.createdAt}' pattern="yyyy年MM月dd日  HH:mm:ss" /></td>
				<td class="tc pointer" onclick="view('${articletype.id}')"><fmt:formatDate value='${articletype.updatedAt}' pattern="yyyy年MM月dd日  HH:mm:ss" /></td>
				<td class="tc pointer" onclick="view('${articletype.id}')">${articletype.creater.relName}</td>
			</tr>
		</c:forEach>
	</table>
     </div>

   </div>
      <div id="pagediv" align="right"></div>
   </div>
	 </body>
</html>
