<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人成绩查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js" type="text/javascript"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(function(){
			$("#relName").val("${relName}");
			$("#code").val("${code}");
			var status_options = document.getElementById("status").options;
			for(var i=0;i<status_options.length;i++){
				if($(status_options[i]).attr("value")=="${status}"){
					status_options[i].selected=true;
				}
			}
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${purchaserResultList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    groups: "${purchaserResultList.pages}">=3?3:"${purchaserResultList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var relName = "${relName}";
						var status = "${status}";
						var code = "${code}";
			            location.href = "<%=path%>/purchaserExam/result.do?relName="+relName+"&status="+status+"&code="+code+"&page="+e.curr;
			        }
			    }
			});		
		})
	
		//查询方法
		function query(){
			var relName = $("#relName").val();
			var status = $("#status").val();
			var code = $("#code").val();
			if((relName==""||relName==null)&&(status==""||status==null)&&(code==""||code==null)){
				window.location.href = "<%=path%>/purchaserExam/result.do";
				return;
			}else{
				window.location.href = "<%=path%>/purchaserExam/result.do?relName="+relName+"&status="+status+"&code="+code;
			}
		}
		
		//重置方法
		function reset(){
			$("#relName").val("");
			$("#code").val("");
			var status = document.getElementById("status").options;
			status[0].selected=true;
		}
	</script>
	
  </head>
  
  <body>
  <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">成绩管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>查询条件</h2>
	   </div>
   </div>
   
    <div class="container">
    	<div class="border1 col-md-12 ml30">
	    	姓名:<input type="text" id="relName" name="relName" class="mt10"/>
	    	试卷编号:<input type="text" id="code" name="code" class="mt10"/>
	    	考试状态:<select name="status" id="status">
	    		<option value="">请选择</option>
	    		<option value="及格">及格</option>
	    		<option value="不及格">不及格</option>
	    	</select>
	    	<button class="btn" type="button" onclick="query()">查询</button>
	  		<button class="btn" type="button" onclick="reset()">重置</button>
    	</div>
    </div>
    
    <div class="container">
	   <div class="headline-v2">
	   		<h2>采购人成绩列表</h2>
	   </div>
   	</div>
    
    <div class="container">
  		<div class="content padding-left-25 padding-right-25 padding-top-5">
	  		<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="info">序号</th>
						<th class="info">采购人姓名</th>
						<th class="info">身份证号</th>
						<th class="info">试卷编号</th>
					    <th class="info">考试时间</th>
						<th class="info">得分</th>
						<th class="info">考试状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${purchaserResultList.list }" varStatus="vs" var="result">
						<tr>
							<td class="tc">${(vs.index+1)+(purchaserResultList.pageNum-1)*(purchaserResultList.pageSize)}</td>
							<td class="tc">${result.relName }</td>
							<td class="tc">${result.card }</td>
							<td class="tc">${result.code }</td>
							<td class="tc">${result.formatDate }</td>
							<td class="tc">${result.score }</td>
							<td class="tc">${result.status }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pageDiv" align="right"></div>
  	</div>
  </body>
</html>
