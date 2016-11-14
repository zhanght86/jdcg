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
  		
  		
  		window.location.href="<%=basePath%>purchaser/queryByNo.html?planNo="+no;
  	}
  	
  	 function aadd(){
		  var  s=$("#count").val();
	      	s++;
	      	$("#count").val(s);
	        var tr = $("input[name=dyadds]").parent().parent();
	        $(tr).before("<tr><td class='tc'><input type='text' name='list["+s+"].seq' /></td>"+
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].department' /> </td>"+
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsName' /> </td>"+ 
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].stand' /> </td>"+ 
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].qualitStand' /> </td>"+ 
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].item' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseCount' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].price' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].budget' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].deliverDate' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseType' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].supplier' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;'type='text' name='list["+s+"].isFreeTax' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsUse' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].useUnit' /> </td>"+
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].memo' /> </td>"+  
	        +"<tr/>");
	  }
  	 
  	 
	
   	var flag=true;
	function checks(obj){
		  var name=$(obj).attr("name");
		  var planNo=$("#pNo").val();
		  var val=$(obj).val();
		  var defVal=obj.defaultValue;
			if(val!=defVal){
				$.ajax({
					url:"<%=basePath%>adjust/filed.html",
					type:"post",
					data:{
						planNo:planNo,
						name:name
					},
					success: function(data){
						 if(data=='exit'){
							 flag=false;
							 layer.tips("该字段不允许修改",obj);
						 }
					 },
					error:function(data){
						 
					 }
					 
				});
			} 
	}
	
	
	 function sum2(obj){  //数量
	        var purchaseCount = $(obj).val()-0;//数量
	        var price2 = $(obj).parent().next().children(":last").prev();//价钱
	        var price = $(price2).val()-0;
	        var sum = purchaseCount*price;
	        var budget = $(obj).parent().next().next().children(":last").prev();
	        $(budget).val(sum);
	      	var id=$(obj).next().val();
	      	aa(id);
	    } 
	    
	       function sum1(obj){
	        var purchaseCount = $(obj).val()-0; //价钱
	         var price2 = $(obj).parent().prev().children(":last").prev().val()-0;//数量
	      	 var sum = purchaseCount*price2;
	         $(obj).parent().next().children(":last").prev().val(sum);
		     	var id=$(obj).next().val();
		     	aa(id);
	    }
	
	       function aa(id){// id是指当前的父级parentid
	    	  
	    	   var budget=0;
	    	   $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		var same= $(this).find("td:eq(8)").children(":last").prev().val()-0;
		 	       if(id==cid){
		 	    	 
		 	    	  budget=budget+same; //查出所有的子节点的值
		 	       }
	    	   });
	    	  
	    	    $("#table tr").each(function(){
	    		var pid= $(this).find("td:eq(8)").children(":first").val();//上级id
	    		
	    		if(id==pid){
	    			$(this).find("td:eq(8)").children(":first").next().val(budget);
	    		}
	    	  	 $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		  if(pid==cid&&id!=pid){
	 	    		
	 	    			 $(this).find("td:eq(8)").children(":first").next().val(budget);
	 	    		  }
	 	    		});  
	    		});  
	   
	    	  var did=$("table tr:eq(1)").find("td:eq(8)").children(":first").val();
	    	    var total=0;
	    	    $("#table tr").each(function(){
	    	    	
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		var same= $(this).find("td:eq(8)").children(":last").prev().val()-0;
	 	    		 if(did==cid){
	 	    			
	 	    			total=total+same;
	 	    		 }
	    	   }); 
	    	    $("table tr:eq(1)").find("td:eq(8)").children(":first").next().val(total);
	       }
	         
	       function sel(obj){
	    	   var val=$(obj).val();
	    	   $("select option").each(function(){
	    		   var opt=$(this).val();
	    		   if(val==opt){
	    			   $(this).attr("selected", "selected");  
	    		   }
	    	   });
	       }  
	       
	
</script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">障碍作业系统</a></li>
				<li><a href="#">采购计划管理</a></li>
				<li class="active"><a href="#">采购需求管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2 fl">
			<h2>计划明细</h2>
		</div>
		<div class="container clear margin-top-30">

			<form action="${pageContext.request.contextPath}/purchaser/update.html" method="post">
				<table id="table" class="table table-bordered table-condensed mt5">
					<thead>
						<tr>
							<th class="info w50">序号</th>
							<th class="info">需求部门</th>
							<th class="info">物资类别及物种名称</th>
							<th class="info">规格型号</th>
							<th class="info">质量技术标准（技术参数）</th>
							<th class="info">计量单位</th>
							<th class="info">采购数量</th>
							<th class="info">单位（元）</th>
							<th class="info">预算金额（万元）</th>
							<th class="info">交货期限</th>
							<th class="info">采购方式建议</th>
							<th class="info">供应商名称</th>
							<th class="info">是否申请办理免税</th>
							<th class="info">物资用途（仅进口）</th>
							<th class="info">使用单位（仅进口）</th>
							<th class="info">备注</th>
						</tr>
					</thead>

					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr>
							<td class="tc w50"><input style="border: 0px;width: 50px;" type="text" name="list[${vs.index }].seq" value="${obj.seq }"><input style="border: 0px;" type="hidden" name="list[${vs.index }].id" value="${obj.id }">
							</td>
							<td><input style="border: 0px;" type="text" onblur="checks(this)" name="list[${vs.index }].department" value="${obj.department }"></td>
							<td><input style="border: 0px;" type="text" onblur="checks(this)" name="list[${vs.index }].goodsName" value="${obj.goodsName }"></td>
							<td class="tc"><input style="border: 0px;width: 60px;" type="text" onblur="checks(this)" name="list[${vs.index }].stand" value="${obj.stand }"></td>
							<td class="tc"><input style="border: 0px;" type="text" onblur="checks(this)" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }"></td>
							<td class="tc"><input style="border: 0px;width: 60px;" type="text" onblur="checks(this)" name="list[${vs.index }].item" value="${obj.item }"></td>
							
							<td class="tc">
							<c:if test="${obj.purchaseCount!=null }">
							<input   type="hidden" name="ss"   value="${obj.id }">
							<input  style="border: 0px;width: 60px;" onblur="sum2(this)"  type="text" name="list[${vs.index }].purchaseCount" onblur="checks(this)"  value="${obj.purchaseCount }">
							<input type="hidden" name="ss"   value="${obj.parentId }">
							</c:if>
							<c:if test="${obj.purchaseCount==null }">
							<input style="border: 0px;width: 60px;"  readonly="readonly"  type="text" name="list[${vs.index }].purchaseCount" onblur="checks(this)"  value="${obj.purchaseCount }">
							
							</c:if>
							</td>
							
							
							<td class="tc">
							<c:if test="${obj.price!=null}">
							<input   type="hidden" name="ss"   value="${obj.id }">
							<input  style="border: 0px;width: 60px;"  onblur="sum1(this)"  type="text" name="list[${vs.index }].price" value="${obj.price }">
							<input type="hidden" name="ss"   value="${obj.parentId }">
							</c:if>
							<c:if test="${obj.price==null}">
							<input style="border: 0px;width: 60px;"  readonly="readonly" onblur="sum1(this)"  type="text" name="list[${vs.index }].price" value="${obj.price }">
						 
							</c:if>
							
							</td>
							<td class="tc">
							<input type="hidden" name="ss"    value="${obj.id}">
							<input  style="border: 0px;" readonly="readonly" type="text" name="list[${vs.index }].budget" onblur="checks(this)"  value="${obj.budget }">
							<input type="hidden" name="ss"  value="${obj.parentId }">
							</td>
							
							
							<td><input style="border: 0px;" type="text" onblur="checks(this)" name="list[${vs.index }].deliverDate" value="${obj.deliverDate }"></td>
							<td>
							
<%-- 							<input style="border: 0px;" type="text" onblur="checks(this)" name="list[${vs.index }].purchaseType" value="${obj.purchaseType }">
 --%>							
					<select onchange="sel(this)" name="list[${vs.index }].purchaseType" style="width:100px" id="select">
              				    <option value="" >请选择</option>
	                            <option value="gkzb" <c:if test="${'gkzb'==obj.purchaseType}">selected="selected"</c:if>>公开招标</option>
	                            <option value="yqzb" <c:if test="${'yqzb'==obj.purchaseType}">selected="selected"</c:if>>邀请招标</option>
	                            <option value="jzxtp" <c:if test="${'jzxtp'==obj.purchaseType}">selected="selected"</c:if>>竞争性谈判</option>
	                            <option value="xjcg" <c:if test="${'xjcg'==obj.purchaseType}">selected="selected"</c:if>>询价采购</option>
	                            <option value="dyly" <c:if test="${'dyly'==obj.purchaseType}">selected="selected"</c:if>>单一来源</option>
			                </select>
			                
			                		
							</td>
							<td class="tc"><input style="border: 0px;width: 60px;" type="text" onblur="checks(this)" name="list[${vs.index }].supplier" value="${obj.supplier }"></td>
							<td class="tc"><input style="border: 0px;width: 60px;" type="text" onblur="checks(this)" onblur="checks(this)" name="list[${vs.count }].isFreeTax" value="${obj.isFreeTax }"></td>
							<td class="tc"><input style="border: 0px;width: 60px;"type="text" onblur="checks(this)" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }"></td>
							<td class="tc"><input  style="border: 0px;width: 60px;" type="text" onblur="checks(this)"  name="list[${vs.index }].useUnit" value="${obj.useUnit }"></td>
							<td class="tc"><input style="border: 0px;" type="text" onblur="checks(this)" name="list[${vs.index }].memo" value="${obj.memo }">
							<input type="hidden" name="list[${vs.index }].planName" value="${obj.planName }">
							<input type="hidden" name="list[${vs.index }].planNo" value="${obj.planNo }">
							<input type="hidden" name="list[${vs.index }].planType" value="${obj.planType }">
							<input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
							<input type="hidden" name="list[${vs.index }].historyStatus" value="${obj.historyStatus }">
							<input type="hidden" name="list[${vs.index }].goodsType" value="${obj.goodsType }">
							<input type="hidden" name="list[${vs.index }].organization" value="${obj.organization }">
							<input type="hidden" name="list[${vs.index }].auditDate" value="${obj.auditDate }">
							<input type="hidden" name="list[${vs.index }].isMaster" value="${obj.isMaster }">
							<input type="hidden" name="list[${vs.index }].isDelete" value="${obj.isDelete }">
							<input type="hidden" name="list[${vs.index }].status" value="${obj.status }">
							<input type="hidden" name="list[${vs.index }].userId" value="${obj.userId }">
							</td>
						</tr>

					</c:forEach>
					
					<%-- <tr>

					<td class="tc" colspan="16"> <input type="hidden" name="type" value="${fn:length(list)}"> <input class="btn btn-windows add" name="dyadds" type="button" onclick="aadd()" value="添加"></td>
				</tr> --%>
				
				</table>
				<input class="btn btn-windows save" type="submit" value="提交">
				<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</form>
		</div>
	</div>

</body>
</html>
