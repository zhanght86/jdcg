<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>
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
			    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			             location.href = '${pageContext.request.contextPath}/purchaseManage/purchaseDepdetailList.html?page='+e.curr+"&address=${address}";
			        }
			    }
			});
	  });
	  function fanhui(){
	  	window.location.href="${pageContext.request.contextPath}/purchaseManage/purchaseDepMapList.html";
	  }
function chongzhi(){
	$("#supplierName").val('');
	$("#startDate").val('');
	$("#endDate").val('');
	$("#contactName").val('');
	$("option")[0].selected = true;
}
$(function() {
		/* var optionNodes = $("option");
		for ( var i = 1; i < optionNodes.length; i++) {
			if ("${supplier.supplierType}" == $(optionNodes[i]).val()) {
				optionNodes[i].selected = true;
			}
		} */
	});
</script>
</head>
  <body>
  	<div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
			   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">机构管理</a></li><li class="active"><a href="#">采购机构查询管理</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
	   <div class="container">
		   <div class="headline-v2">
		     <h2>采购机构信息</h2>
		   </div>  
		   <h2 class="search_detail">
  				<form id="form1" action="${pageContext.request.contextPath}/purchaseManage/purchaseDepdetailList.html" method="post" class="mb0">
		       <input type="hidden" name="page" id="page">
		       <input type="hidden" name="parentName" value="${parentName }">
		      <ul class="demand_list">
                       <li>
                        <label class="fl">采购机构名称：</label><span><input id="name" name="name" value="${purchaseDep.name }" type="text"></span>
                      </li>
                       <li>
                        <label class="fl">资质起止日期：</label>
                        <input id="quaStartDate" name="quaStartDate" class="Wdate w230" type="text"  value='<fmt:formatDate value="${purchaseDep.quaStartDate }" pattern="YYYY-MM-dd"/>'
                        onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){quaStartDate.focus();},maxDate:'#F{$dp.$D(\'quaStartDate\')}'})"/>
                        <span >至</span>
                        <input id="quaEdndate" name="quaEdndate" value='<fmt:formatDate value="${purchaseDep.quaEdndate }" pattern="YYYY-MM-dd"/>' class="Wdate w230" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'quaEdndate\')}'})"/>
                      </li>
                      <li>
                        <label class="fl">上级监管部门：</label>
                        <span class="fl">
                          <select name="" class="w178">
                                     <option selected="selected" value=''>-请选择-</option>
                                    <option  value="生产型">部门1</option>
                                    <option  value="销售型">部门2</option>
                          </select>
                        </span>
                      </li>
                  </ul>
                   <div class="col-md-12 clear tc mt10">
                        <input class="btn" onclick="submit()" type="button" value="查询">
                        <input class="btn" onclick="chongzhi()" type="button" value="重置"> 
                   
                   </div>
                   <div class="clear"></div>
		     </form>
		     </h2>
		     <div class="col-md-12 pl20 mt10">
		          <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
		     </div>
		      <div class="content table_box">
                 <table id="tb1" class="table table-bordered table-condensed table-hover table-striped">
		      <thead>
				<tr>
					<th class="info w50">序号</th>
					<th class="info">采购机构名称</th>
					<th class="info">采购资质编号</th>
					<th class="info">等级</th>
					<th class="info">地址</th>
					<th class="info">采购资质开始日期</th>
					<th class="info">采购资质截止日期</th>
					<th class="info">上级监管部门</th>
				</tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${list.list }" var="list" varStatus="vs">
					<tr>
						<td class="tc w50">${vs.index+1 }</td>
						<td><a href="${pageContext.request.contextPath}/purchaseManage/purchaseDepMapShow.html?orgId=${list.orgId}">${list.name }</a></td>
						<td>${list.quaCode }</td>
						<td>${list.levelDep }</td>
						<%-- <td><fmt:formatDate value="${list.createdAt }" pattern="yyyy-MM-dd HH:mm:ss" /></td> --%>
						<td>${list.address }</td>
						<td><fmt:formatDate value="${list.quaStartDate }" pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatDate value="${list.quaEdndate }" pattern="yyyy-MM-dd" /></td>
						<td>军区采购</td>
					</tr>
				</c:forEach> 
			  </tbody>
		 </table>
		 <div id="pagediv" align="right"></div>
     </div>
  </body>
</html>
