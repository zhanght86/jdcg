<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp" %> 
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
		        	$("#page").val(e.curr);
		            location.href = '${pageContext.request.contextPath}/purchase/list.html?page='+e.curr;
		        }
		    }
		});
  });
    	
   /** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("allId");
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
	
	function submit() {
		$("#form1").submit();
	}
	function chongzhi() {
		$("#relName").val('');
		$("#purchaseDepName").val('');
	}
	function add(){
    	window.location.href="${pageContext.request.contextPath}/purchase/add.do";
    }
    function edit(){
    	var id = []; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		
		if(id.length !=1){
			layer.msg("请选择一条记录进行编辑");
			return;
		}
		window.location.href = "${pageContext.request.contextPath}/purchase/edit.html?id=" + id[0]; 
    }
    function delPurchaser(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length > 0){
			layer.confirm('您确定要删除吗?', {
				btn:['确认','取消']
			},function(){
				ajaxDelUser(ids.toString());
			});
		}else{
			layer.alert("请选择需要删除的用户");
		}
    }
    
    function ajaxDelUser(idstr){
    	$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchase/delajax.do",
		    data : {ids:idstr},
		    success: function(data) {
		    	if (data.success){
		    		layer.msg("删除成功");
		    		window.location.href= "${pageContext.request.contextPath}/purchase/list.html";
		    	} else {
		    		layer.msg("删除失败");
		    	}
		       
		    }
		});
    }
    
	function resetQuery(){
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
</script>
</head>

<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="#"> 首页</a>
					</li>
					<li><a href="#">支撑系统</a>
					</li>
					<li><a href="#">机构管理</a>
					</li>
					<li class="active"><a href="#">采购人管理</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>采购机构人员列表</h2>
			</div>
				<h2 class="search_detail">
					<form action="${pageContext.request.contextPath}/purchase/list.html" method="post" id="form1"  class="mb0">
						<input type="hidden" name="page" id="page"/> 
						<ul class="demand_list">
							<li><label class="fl">姓名：</label><span><input type="text" name="relName" id="relName"
							value="${purchaseInfo.relName }"/>
							</span></li>
							<li><label class="fl">采购机构名称：</label><span><input type="text" name="purchaseDepName" id="purchaseDepName"
							value="${purchaseInfo.purchaseDepName }"/>
							</span>
							</li>
							<button type="button" onclick="submit();" class="btn fl mt1">查询</button>
                            <button type="button" onclick="resetQuery();" class="btn fl mt1">重置</button>
						</ul>
						<div class="clear"></div>
					</form>
				</h2>
			<!-- 表格开始-->
				<div class="col-md-12 pl20 mt10">
					<button class="btn btn-windows add" type="button"
						onclick="add();">新增</button>
					<button class="btn btn-windows edit" type="button"
						onclick="edit();">修改</button>
					<button class="btn btn-windows delete" type="button"
						onclick="delPurchaser();">删除</button>
				</div>
			<div class="content table_box">
                 <table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w30"><input type="checkbox"
								onclick="selectAll();" id="allId" alt=""></th>
							<th class="info w50">序号</th>
							<th class="info">姓名</th>
							<th class="info">所属采购机构</th>
							<th class="info">类型</th>
							<th class="info">性别</th>
							<th class="info">职务</th>
							<th class="info">职称</th>
							<th class="info">采购资格等级</th>
							<th class="info">学历</th>
							<th class="info">电话</th>
							<th class="info">证书编号</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${purchaseList}" var="p" varStatus="vs">
							<tr class="cursor">
								<td class="tc"><input type="checkbox" name="chkItem" value="${p.id}" /></td>
								<td class="tc" onclick="show('${p.id}');">${vs.index+1}</td>
								<td class="tc" onclick="show('${p.id}');">${p.relName}</td>
								<td class="tl pl20" onclick="show('${p.id}');">${p.purchaseDepName}</td>
								<td class="tc" onclick="show('${p.id}');">
									<c:choose>
										<c:when test="${p.purcahserType== 0 }">
											军人
										</c:when>
										<c:when test="${p.purcahserType== 1 }">
											文职
										</c:when>
										<c:when test="${p.purcahserType== 2 }">
											职工
										</c:when>
										<c:when test="${p.purcahserType== 3 }">
											战士
										</c:when>
									</c:choose>
								</td>
								<c:forEach items="${genders}" var="g" >
									<c:if test="${g.id eq p.gender}">
									  <td class="tc" onclick="show('${p.id}');">${g.name}</td>
									</c:if>
					        	</c:forEach>
								<td class="tl pl20" onclick="show('${p.id}');">${p.duties}</td>
								<td class="tc" onclick="show('${p.id}');">${p.professional}</td>
								<td class="tc" onclick="show('${p.id}');">
									<c:choose>
										<c:when test="${p.quaLevel==0}">
											初
										</c:when>
										<c:when test="${p.quaLevel==1}">
											中
										</c:when>
										<c:when test="${p.quaLevel==2}">
											高
										</c:when>
									</c:choose>
								</td>
								<td class="tc" onclick="show('${p.id}');">${p.topStudy}</td>
								<td class="tc" onclick="show('${p.id}');">${p.telephone}</td>
								<td class="tl pl20" onclick="show('${p.id}');">${p.quaCode}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
		<div id="pagediv" align="right"></div>
	</div>
</body>
</html>
