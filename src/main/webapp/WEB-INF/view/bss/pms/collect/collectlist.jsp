<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>采购需求管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	

<jsp:include page="/WEB-INF/view/common.jsp"/> 

 
  <script type="text/javascript">
  
  /*分页  */
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${info.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    total: "${info.total}",
		    startRow: "${info.startRow}",
		    endRow: "${info.endRow}",
		    skip: true, //是否开启跳页
		    groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//			        var page = location.search.match(/page=(\d+)/);
//			        return page ? page[1] : 1;
				return "${info.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		            if(!first){ //一定要加此判断，否则初始时会无限刷新
		       	$("#page").val(e.curr);
		        	  $("#add_form").submit();
		        	
<%-- 		         location.href = '<%=basePath%>purchaser/list.do?page='+e.curr;
 --%>		        }  
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
	
  	function view(no){
  		
  		window.location.href="<%=basePath%>purchaser/queryByNo.html?planNo="+no+"&&type=1";
  	}
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>purchaser/queryByNo.html?planNo="+id+"&&type=2";;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function del(){
    	var id =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val()); 
		}); 
		if(id.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				 $.ajax({
		 			 url:"<%=basePath%>purchaser/delete.html",
		 			 type:"post",
		 			 data:{
		 				 planNo:$('input[name="chkItem"]:checked').val()
		 				 },
		 			 success:function(){
		 				window.location.reload();
		 				 
		 			 },error:function(){
		 				 
		 			 }
		 		 });
			});
		}else{
			layer.alert("请选择要删除的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    var index;
    function collect(){
    	var flag=true;
    	var ceck=$('input[name="chkItem"]:checked:first').prev().val();
    
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			var type=$(this).prev().val();
			if(ceck!=type){
				flag=false;
			}
			id.push($(this).val());
		}); 
		if(flag==false){
			layer.alert("物资类别需要一样",{offset: ['222px', '390px'], shade:0.01});
		}
		
		else if(id.length>=1){
			 
	 
    	index=layer.open({
			  type: 1, //page层
			  area: ['500px', '300px'],
			  title: '生成采购计划',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['80px', '600px'],
			  content: $('#content'),
			});
		}else{
				layer.alert("请选中一条",{offset: ['222px', '390px'], shade:0.01});
			}
   
    }
    
	//鼠标移动显示全部内容
	function out(content){
	if(content.length>10){
	layer.msg(content, {
			icon:6,
			shade:false,
			area: ['600px'],
			time : 1000    //默认消息框不关闭
		});//去掉msg图标
	}else{
		layer.closeAll();//关闭消息框
	}
}
	
	function closeLayer(){
	 
		 var id =[]; 
		 var de=[];
		 var type="";
	 
		$('input[name="chkItem"]:checked').each(function(){ 
		    type=$(this).prev().val();
			var dep=$(this).next().val();
			de.push(dep);
		
			id.push($(this).val()); 
		}); 
	    $("#goodsType").val(type);
	  	$("#plannos").val(id);
	  	$("#dep").val(de);
	    $("#collect_form").submit();
		
 
		  layer.close(index);	
	}
	
	function cancels(){
		layer.close(index);	
	}
	var ids=[]; 
	function collected(){
		var flag=true;
		var ceck=$('input[name="chkItem"]:checked:first').prev().val();
		var goodsType="";
		$('input[name="chkItem"]:checked').each(function(){ 
		    goodsType=$(this).prev().val();
			if(ceck!=goodsType){
				flag=false;
			}
			ids.push($(this).val());
		}); 
		
		if(flag==false){
			layer.alert("物资类别需要一样",{offset: ['222px', '390px'], shade:0.01});
		}
		
		else  if(ids.length>=1){
			  layer.open({
				  type: 2, //page层
				  area: ['600px', '500px'],
				  title: '汇入采购计划',
				  closeBtn: 1,
				  shade:0.01, //遮罩透明度
				  moveType: 0, //拖拽风格，0是默认，1是传统拖动
				  shift: 1, //0-6的动画形式，-1不开启
				  offset: ['80px', '100px'],
				  content:  '<%=basePath%>collect/collectlist.html?type='+goodsType,
				});
			 
			 
		}else{
			layer.alert("请选中一条",{offset: ['222px', '390px'], shade:0.01});
		}
		
	}
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">障碍作业系统</a></li><li><a href="#">采购计划管理</a></li><li class="active"><a href="#">采购需求汇总</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
 <div class="headline-v2 fl">
      <h2>需求计划列表
	  </h2>
   </div> 
   
   <h2 class="search_detail">
    <form id="add_form" class="mb0" action="${pageContext.request.contextPath }/collect/list.html" method="post" >
  
	
	  
	   <ul class="demand_list">
			    	  <li>
				    	<label class="fl">  需求计划名称：</label><span>
				    	<input type="hidden" name="page" id="page">
				  	 <input   type="text" name="planName" value="${inf.planName }"/>
				    	
				    	</span>
				      </li>
				   <li>
				    	<label class="fl">  需求计划编号：</label><span>
				  	  <input   type="text" name="planNo" value="${inf.planNo }"/>
				    	
				    	</span>
				      </li>
				      <li>
				    	<label class="fl">    	   状态 :</label><span>
				    	 <select name="status"  >
								<option value=""> 请选项状态</option>
						   	   <option value="1" <c:if test="${inf.status=='1'}"> selected</c:if> > 	 已编制为采购计划</option>
						   	   <option value="2" <c:if test="${inf.status=='2'}"> selected</c:if> > 	已提交</option>
						   	   <option value="3" <c:if test="${inf.status=='3'}"> selected</c:if> > 	 受理退回</option>
						   	    <option value="4" <c:if test="${inf.status=='4'}"> selected</c:if> > 	 已受理</option>
						   	   <option value="5" <c:if test="${inf.status=='5'}"> selected</c:if> > 已汇总</option>
						   	    <option value="6" <c:if test="${inf.status=='6'}"> selected</c:if> >审核通过</option>
				   	    		 <option value="6" <c:if test="${inf.status=='7'}"> selected</c:if> >审核暂存</option>
			 	   	   </select>
				    	
				    	</span>
				      </li>
			    	</ul>
			    	
	   	 <input class="btn padding-left-10 padding-right-10 btn_back"   type="submit" name="" value="查询" /> 
 

	 <input type="button" onclick="resetQuery()" class="btn" value="重置"/>
   
   </form>
  	</h2>
   
   <div class="col-md-12 pl20 mt10">
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="collect()">汇总</button>
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="collected()">汇入采购计划</button>
	 </div>
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">需求部门</th>
		  <th class="info">需求计划名称</th>
		  <!-- <th class="info">编报人</th> -->
		  <th class="info">物资类别</th> 
		  <th class="info">提交日期</th>
		  <th class="info">预算总金额</th>
		  <th class="info">状态</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
			  <td class="tc w30"><input type="hidden" name="department" value="${obj.planType }"> <input type="checkbox" value="${obj.planNo }" name="chkItem" onclick="check()"  alt=""> <input type="hidden" name="department" value="${obj.department }"> </td>
			  <td class="tc w50"   >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			  
			    <td class="tc"  >${obj.department }</td>
			    
			  <td class="tc"  >${obj.planName }</td>
			
			  <td class="tc"  >
			   <c:if test="${obj.planType==1}">
			 	 货物
			  </c:if>
			   <c:if test="${obj.planType=='2' }">
		     	工程
			  </c:if>
			   <c:if test="${obj.planType=='3' }">
			 	 服务
			  </c:if>
			  
			  </td>
			  <td class="tc"  ><fmt:formatDate value="${obj.createdAt }"/></td>
			  <td class="tc"  ><fmt:formatNumber>${obj.budget }</fmt:formatNumber> </td>
			  <td class="tc"  >
			 <c:if test="${obj.status=='1' }">
			 	 已编制为采购计划
			  </c:if>
			  
			     <c:if test="${obj.status=='2' }">
			 	已提交
			  </c:if>
			  <c:if test="${obj.status=='3' }">
			 	受理退回
			  </c:if>
			    <c:if test="${obj.status=='4' }">
			 	已受理
			  </c:if>
			   <c:if test="${obj.status=='5' }">
			 	已经汇总
			  </c:if>
			   <c:if test="${obj.status=='6' }">
			 	审核通过
			  </c:if>
			   <c:if test="${obj.status=='7' }">
			 	审核暂存
			  </c:if>
			  
			  </td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
 </div>


 <div id="content" class="dnone">
	 
	<form id="collect_form" action="${pageContext.request.contextPath }/collect/add.html" method="post" style="margin-top: 20px;">
	<div style="text-align: center;"><span>计划编号：</span><input  type="text" name="cno" value=""></div>
	  <div style="text-align: center;"><span>文件名称：</span><input  type="text" name="fileName" value=""></div>
	       <div  style="text-align: center;"><span>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</span><input  type="password" name="password" value=""></div>
	       
	       
		<!--  文件名称：<input type="text" name="fileName" value=""><br>
		 密码:<input type="password" name="password" value=""><br> -->
		 <input type="hidden" name="planNo" id="plannos" value="">
		  <input type="hidden" name="department" id="dep" value="">
		  <input type="hidden" name="goodsType" id="goodsType" value="">
	   	<button class="btn padding-left-10 padding-right-10 btn_back"  style="margin-top: 20px;margin-left: 180px;" onclick="closeLayer()" >生成采购计划</button>
	   	<button class="btn padding-left-10 padding-right-10 btn_back"  style="margin-top: 20px;margin-left: 20px" onclick="cancels()" >取消</button>
	   
	 </form>   
 </div>
 
 

 
 
	 </body>
</html>
