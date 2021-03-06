<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    <title>查看未考</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<script type="text/javascript">
		$(function(){
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${userList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${userList.total}",
			    startRow: "${userList.startRow}",
			    endRow: "${userList.endRow}",
			    groups: "${userList.pages}">=5?5:"${userList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var id = "${id}";
			            location.href = "${pageContext.request.contextPath }/expertExam/viewReference.do?id="+id+"&page="+e.curr;
			        }
			    }
			});
		})
	
		//endsWith方法
		if (typeof String.prototype.endsWith != 'function') {  
		    String.prototype.endsWith = function (str){  
		         return this.slice(-str.length) == str;  
		    };  
		}
	
		//Excel导入
		function poiExcel(){
			var file = $("#excelFile").val();
			if(file==null||file==""){
				layer.alert("请选择文件",{offset: ['30%', '40%']});
				$(".layui-layer-shade").remove();
				return;
			}
			if(!file.endsWith(".xls")&&!file.endsWith(".xlsx")){
				layer.alert("请选择以.xls结尾或者以.xlsx结尾的文件",{offset: ['30%', '40%']});
				$(".layui-layer-shade").remove();
				return;
			}
			var ruleId = $("#ruleId").val();
			$.ajaxFileUpload({
			    url: "${pageContext.request.contextPath }/expertExam/importReference.do?ruleId="+ruleId,  
			    secureuri: false,
			    fileElementId: "excelFile",
			    dataType: "text",
			    success: function(data) {
			    	if(data.length<=5){
			    		layer.msg('添加成功',{offset: ['40%', '45%']});
			    		window.setTimeout(function(){
				       		window.location.reload();
				       	}, 1000);
			    	}else{
			    		var array = data.split(";");
			    		if(array[array.length-1].indexOf("1")>-1){
			    			$("#errorNews").html("Excel表中以下人员不是专家");
			    			var html = "";
				    		for(var i=0;i<array.length-1;i++){
				            	  html = html + "<tr class='tc'>";
				            	  html = html + "<td>"+(i+1)+"</td>";
				            	  if(i==0){
				            		  html = html + "<td>"+array[i].split(",")[0].substring(1)+"</td>";
				            	  }else{
				            		  html = html + "<td>"+array[i].split(",")[0]+"</td>";
				            	  }
				            	  html = html + "<td>"+array[i].split(",")[2]+"</td>";
				            	  html = html + "<td>"+array[i].split(",")[1]+"</td>";
				            	  html = html + "</tr>";
				            }
				    		$("#errResult").html(html);
				    		layer.open({
								  type: 1,
								  title: '错误信息',
								  skin: 'layui-layer-rim',
								  shadeClose: true,
								  area: ['580px','410px'],
								  content: $("#errorExpert")
							});
							$(".layui-layer-shade").remove();
			    		}else if(array[array.length-1].indexOf("2")>-1){
			    			$("#errorNews").html("Excel表中以下人员已在本场考试中");
			    			var html = "";
				    		for(var i=0;i<array.length-1;i++){
				            	  html = html + "<tr class='tc'>";
				            	  html = html + "<td>"+(i+1)+"</td>";
				            	  if(i==0){
				            		  html = html + "<td>"+array[i].split(",")[0].substring(1)+"</td>";
				            	  }else{
				            		  html = html + "<td>"+array[i].split(",")[0]+"</td>";
				            	  }
				            	  html = html + "<td>"+array[i].split(",")[2]+"</td>";
				            	  html = html + "<td>"+array[i].split(",")[1]+"</td>";
				            	  html = html + "</tr>";
				            }
				    		$("#errResult").html(html);
				    		layer.open({
								  type: 1,
								  title: '错误信息',
								  skin: 'layui-layer-rim',
								  shadeClose: true,
								  area: ['580px','410px'],
								  content: $("#errorExpert")
							});
							$(".layui-layer-shade").remove();
			    		}
			    	}
			    }  
			}); 
		}
	
		//删除参考人员
		function deleteById(){
			var paperId = $("#paperId").val();
			var count = 0;
			var ids = "";
			var info = document.getElementsByName("info");
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count == 0){
				layer.alert("请选择删除内容",{offset: ['30%', '40%']});
				$(".layui-layer-shade").remove();
				return;
			}
			for(var i=0;i<info.length;i++){    
		        if(info[i].checked){    
		        	ids += info[i].value+',';
		        }
			}
			layer.confirm("您确定要删除吗?", {title:'提示',offset: ['30%', '40%'],shade:0.01}, function(index){
				layer.close(index);
				$.ajax({
					type:"POST",
					dataType:"json",
					url:"${pageContext.request.contextPath }/expertExam/deleteByCondition.do?id="+ids,
			       	success:function(data){
			       		layer.msg('删除成功',{offset: ['40%', '45%']});
				       	window.setTimeout(function(){
				       		window.location.reload();
				       	}, 1000);
			       	}
		       	});
			});
		}
	
		//全选方法
		function selectAll(){
			var info = document.getElementsByName("info");
			var selectAll = document.getElementById("selectAll");
			if(selectAll.checked){
				for(var i = 0;i<info.length;i++){
					info[i].checked = true;
				}
			}else{
				for(var i = 0;i<info.length;i++){
					info[i].checked = false;
				}
			}
		}
		
		//下载模板
		function download(){
			window.location.href = "${pageContext.request.contextPath }/expertExam/loadTemplet.html";
		}
	
		//取消方法
	    function cancel(){
	    	layer.closeAll();
	    }
	
		//检查全选
		function check(){
			var count = 0;
			var info = document.getElementsByName("info");
			var selectAll = document.getElementById("selectAll");
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == false){
					selectAll.checked = false;
					break;
				}
			}
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count == info.length){
				selectAll.checked = true;
			}
		}
		
		//添加考试人员
		function addUser(){
			var ruleId = $("#ruleId").val();
			window.location.href = "${pageContext.request.contextPath }/expertExam/userAdd.html?ruleId="+ruleId;
		}
	</script>

  </head>
  
  <body>
    	<!--面包屑导航开始-->
	   	<div class="margin-top-10 breadcrumbs">
	      	<div class="container">
			   	<ul class="breadcrumb margin-left-0">
			   	<li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">专家考试规则管理</a></li>
			   	</ul>
				<div class="clear"></div>
		  	</div>
	   	</div>
	   	
		<div class="container mt20">
		  	<div class="ml20 mt10 tc f18 b">
	  		考试年份：${examRule.year }
	  	  	</div>
  		</div>
  
  	<div class="container">
    
	   	<div class="headline-v2">
	   		<h2>参考人员列表</h2>
	  	</div>
   	
		<!-- 按钮开始 -->
		<div class="col-md-12 pl20 mt10">
			<button class="btn btn-windows add" type="button" onclick="addUser()">添加</button>
			<button class="btn btn-windows delete" type="button" onclick="deleteById()">删除</button>
		    <button class="btn btn-windows output" type="button" onclick="download()">模板下载</button>
		    <span class="">
			    <input type="button" value="导入" class="btn btn-windows input" onclick="poiExcel()"/>
		    	<input type="file" name="file" id="excelFile" style="display:inline;"/>
		    </span>
	    </div>
    
  		<div class="content table_box">
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()"/></th>
						<th class="w50">序号</th>
						<th>姓名</th>
						<th>证件号</th>
						<th>专家类型</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userList.list }" var="user" varStatus="vs">
						<tr class="tc">
							<td><input type="checkbox" name="info" value="${user.id }" onclick="check()"/></td>
							<td>${(vs.index+1)+(userList.pageNum-1)*(userList.pageSize)}</td>
							<td>${user.relName }</td>
							<td>${user.idNumber }</td>
							<c:if test="${user.userType==1 }">
								<td>技术</td>
							</c:if>
							<c:if test="${user.userType==2 }">
								<td>法律</td>
							</c:if>
							<c:if test="${user.userType==3 }">
								<td>商务</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pageDiv" align="right" class="mr10"></div>
  	</div>
  	
  		<!-- 返回按钮 -->
  		<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
	      	<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='${pageContext.request.contextPath }/expertExam/ruleList.html'">
	  	</div>
	  	
	  	<div class="dnone layui-layer-wrap col-md-12" id="errorExpert">
	  		<div id="errorNews" class="col-md-12 tc red mb5 f18"></div>
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50">序号</th>
						<th class="w60">姓名</th>
						<th>证件号</th>
						<th>专家类型</th>
					</tr>
				</thead>
				<tbody id="errResult">
				</tbody>
			</table>
		</div>
	  	
	  	<input type="hidden" value="${examRule.id }" name="ruleId" id="ruleId"/>
  </body>
</html>
