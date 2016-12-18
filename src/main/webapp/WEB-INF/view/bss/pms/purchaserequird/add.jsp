<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<jsp:include page="/WEB-INF/view/common.jsp" />
		<script type="text/javascript">
			//跳转到增加页面
			function add() {
				window.location.href = "${pageContext.request.contextPath}/purchaser/add.html";
			}

			//鼠标移动显示全部内容
			var index;

			function chakan() {
				index = layer.open({
					type: 1, //page层
					area: ['600px', '500px'],
					title: '编制说明',
					closeBtn: 1,
					shade: 0.01, //遮罩透明度
					moveType: 1, //拖拽风格，0是默认，1是传统拖动
					shift: 1, //0-6的动画形式，-1不开启
					offset: ['80px', '400px'],
					content: $('#content'),
				});
			}

			function closeLayer() {
				layer.close(index);
			}

			function uploadExcel() {
				layer.open({
					type: 2, //page层
					area: ['600px', '500px'],
					title: '文件上传',
					closeBtn: 1,
					shade: 0.01, //遮罩透明度
					moveType: 1, //拖拽风格，0是默认，1是传统拖动
					shift: 1, //0-6的动画形式，-1不开启
					offset: ['80px', '400px'],
					content: '${pageContext.request.contextPath}/purchaser/fileUpload.html',
				});
			}

			//上传excel文件
			function upload() {
				$.ajaxFileUpload({
					url: '${pageContext.request.contextPath}/purchaser/upload.do',
					secureuri: false,
					fileElementId: 'fileName',
					dataType: "text",
					success: function(data) {
						if(data == "ERROR") {
							layer.msg("文件名错误");
						} else if(data == "exception") {
							layer.msg("格式错误");
						} else {
							layer.msg("上传成功");
							window.location.href = "${pageContext.request.contextPath}/purchaser/list.html";
						}
					},
					error: function(data, status, e) {
						layer.msg("上传失败");
					}
				});
			}

			function loadTrInfo(obj) {

			}

			function adds() {
				var name = $("#jhmc").val();
				var no = $("#jhbh").val();
				var type = $("#wtype").val();
				if(name == "") {
					layer.tips("计划名称不允许为空", "#jhmc");
				} else if(no == "") {
					layer.tips("计划编号不允许为空", "#jhbh");
				} else if(type == "") {
					layer.tips("物资类别不允许为空", "#wtype");
				} else {
					$("#fjhmc").val(name);
					$("#fjhbh").val(no);
					$("#ptype").val(type);

					$("#add_form").submit();
				}
			}

			function hide() {
				$("#add_div").hide();
			}

			//动态添加
			function aadd() {
				var value = $("#xqbm").val();
				var detailRow = document.getElementsByName("detailRow");
				var id = null;
				$.ajax({
					url: "${pageContext.request.contextPath}/purchaser/getId.html",
					type: "post",
					success: function(data) {
						id = data;
						var tr = $("input[name=dyadds]").parent().parent().prev();
						// var tr=$(obj).parent().parent();
						$(tr).children(":first").children(":first").val(data);
						var s = $("#count").val();
						s++;
						$("#count").val(s);
						// var trs = $(obj).parent().parent();
						$(detailRow[detailRow.length-1]).after("<tr class='tc'><td><input type='hidden' name='list[" + s + "].id' />" +
							"<input type='text' name='list[" + s + "].seq' /><input value='" + id + "' type='hidden' name='list[" + s + "].parentId' /></td>" +
							"<td name='department'><input type='text' name='list[" + s + "].department' readonly='readonly' value='"+value+"'/></td>" +
							"<td><input type='text' name='list[" + s + "].goodsName' /></td>" +
							"<td><input type='text' name='list[" + s + "].stand' /></td>" +
							"<td><input type='text' name='list[" + s + "].qualitStand' /></td>" +
							"<td><input type='text' name='list[" + s + "].item' /> </td>" +
							"<td><input type='text' name='list[" + s + "].purchaseCount' /></td>" +
							"<td><input type='text' name='list[" + s + "].price' /></td>" +
							"<td><input type='text' name='list[" + s + "].budget' readonly='readonly' /></td>" +
							"<td><input type='text' name='list[" + s + "].deliverDate' /></td>" +
							"<td><select name='list[" + s + "].purchaseType' style='width:90px'> <option value='' >请选择</option>" +
							" <c:forEach items='${list2 }' var='obj'> <option value='${obj.id }'>${obj.name }</option></c:forEach>  </select></td>" +
							"<td><input type='text' name='list[" + s + "].supplier' /></td>" +
							"<td><input type='text' name='list[" + s + "].isFreeTax' /></td>" +
							"<td><input type='text' name='list[" + s + "].goodsUse' /></td>" +
							"<td><input type='text' name='list[" + s + "].useUnit' /></td>" +
							"<td><input type='text' name='list[" + s + "].memo' /></td>" +
							"<td><input type='text' name='list[" + s + "].status' value='暂存' readonly='readonly' /></td>" +
							+"<tr/>");
					}
				});
			}

			
			//保存
			function incr() {
				var name = $("#jhmc").val();
				var no = $("#jhbh").val();
				var type = $("#wtype").val();
				var depName = $("#xqbm").val();
				if($.trim(name) == "") {
					layer.tips("计划名称不允许为空", "#jhmc");
				} else if($.trim(no) == "") {
					layer.tips("计划编号不允许为空", "#jhbh");
				}else if($.trim(depName) == ""){
					layer.tips("需求部门不允许为空", "#xqbm");
				}else {
					$("#detailJhmc").val(name);
					$("#detailJhbh").val(no);
					$("#detailType").val(type);
					$("#detailXqbm").val(depName);
					$("#add_form").submit();
				}
			}

			function down() {
				window.location.href = "${pageContext.request.contextPath}/purchaser/download.html?filename=模板.xlsx";
			}

			function delets() {
				var tr = $("input[name=delt]").parent().parent();
				$(tr).prev().remove();
			}
			var datas;
			var treeObj;
			$(function() {

				var setting = {
					async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/category/createtree.do",
						dataType: "json",
						type: "post",
					},
					callback: {
						onClick: zTreeOnClick, //点击节点触发的事件
						//beforeRemove: zTreeBeforeRemove,
						//beforeRename: zTreeBeforeRename, 
						//onRemove: zTreeOnRemove,
						//onRename: zTreeOnRename,
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId",
							rootPId: 0,
						}
					},
				};
				//控制树的显示和隐藏
				var expertsTypeId = $("#expertsTypeId").val();
				if(expertsTypeId == 1 || expertsTypeId == "1") {
					treeObj = $.fn.zTree.init($("#ztree"), setting, datas);
					$("#ztree").show();
				} else {
					treeObj = $.fn.zTree.init($("#ztree"), setting, datas);
					$("#ztree").hide();
				}
			});

			function typeShow() {
				/* 	 var expertsTypeId = $("#expertsTypeId").val();
					 if(expertsTypeId==1 || expertsTypeId=="1"){ */
				$("#ztree").show();
				layer.open({
					type: 1,
					title: '信息',
					skin: 'layui-layer-rim',
					shadeClose: true,
					offset: ['20%', '20%'],
					area: ['45%', '70%'],
					content: $("#catalogue")
				});
				$(".layui-layer-shade").remove();
				/*  }else{
					 $("#ztree").hide();
				 } */

			}
			var treeid = null;
			/*树点击事件*/
			function zTreeOnClick(event, treeId, treeNode) {
				treeid = treeNode.id;

			}

			function typehide() {
				layer.closeAll();
			}

			function same() {
				$.ajax({
					url: "${pageContext.request.contextPath}/purchaser/getId.html",
					type: "post",

					success: function(data) {

						var tr = $("input[name=dyadds]").parent().parent().prev();;
						var id = $(tr).children(":first").children(":last").val();

						var s = $("#count").val();
						s++;
						$("#count").val(s);
						//  var trs = $(obj).parent().parent();
						$(tr).after("<tr><td class='tc'><input style='border: 0px;' type='hidden' name='list[" + s + "].id' value='" + data + "' />" +
							"<input style='border: 0px;' type='text' name='list[" + s + "].seq' /><input style='border: 0px;' value='" + id + "' type='hidden' name='list[" + s + "].parentId' /></td>" +
							"<td class='tc'> <input  style='border: 0px;'  type='text' name='list[" + s + "].department' /> </td>" +
							"<td class='tc'> <input  style='border: 0px;' type='text' name='list[" + s + "].goodsName' /> </td>" +
							"<td class='tc'> <input  style='border: 0px;' type='text' name='list[" + s + "].stand' /> </td>" +
							"<td class='tc'> <input  style='border: 0px;' type='text' name='list[" + s + "].qualitStand' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].item' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseCount' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].price' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].budget' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].deliverDate' /> </td>" +
							"<td class='tc'>  <select name='list[" + s + "].purchaseType' style='width:90px'> <option value='' >请选择</option>" +
							" <c:forEach items='${list2 }' var='obj'> <option value='${obj.id }'>${obj.name }</option></c:forEach>  </select></td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].supplier' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].isFreeTax' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].goodsUse' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].useUnit' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].memo' /> </td>" +

							+"<tr/>");
					},
					error: function() {

					}
				});
			}

			function news(obj) {
				var s = $("#count").val();
				s++;
				$("#count").val(s);
				var trs = $(obj).parent().parent();
				$(trs).after("<tr><td class='tc'><input style='border: 0px;' type='text' name='list[" + s + "].id' />" +
					"<input style='border: 0px;' type='text' name='list[" + s + "].seq' /><input style='border: 0px;' value='" + id + "' type='hidden' name='list[" + s + "].parentId' /></td>" +
					"<td class='tc'> <input  style='border: 0px;'  type='text' name='list[" + s + "].department' /> </td>" +
					"<td class='tc'> <input  style='border: 0px;' type='text' name='list[" + s + "].goodsName' /> </td>" +
					"<td class='tc'> <input  style='border: 0px;' type='text' name='list[" + s + "].stand' /> </td>" +
					"<td class='tc'> <input  style='border: 0px;' type='text' name='list[" + s + "].qualitStand' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].item' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseCount' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].price' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].budget' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].deliverDate' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseType' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].supplier' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].isFreeTax' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].goodsUse' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].useUnit' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].memo' /> </td>" +
					"<td class='tc'><input class='add' name='dyadds' type='button' onclick='aadd(this)' value='添加子节点'>" +
					"<input class='btn btn-windows add' name='delt' type='button' onclick='same(this)' value='添加同级节点'>" +
					" <input class='btn btn-windows add' name='delt' type='button' onclick='news(this)' value='新加任务'></td>" +
					+"<tr/>");

			}

			//选择采购方式
			function changeType(obj) {
				$(obj).parent().next().find("input").val("");
				var purchaseType = $("#select option:selected").text(); //选中的文本
				if(purchaseType == "单一来源") {
					$(obj).parent().next().find("input").removeAttr("disabled");
				} else {
					$(obj).parent().next().find("input").attr("disabled", "disabled");
				}
			}

			//检索名字
			function listName(obj) {
				/**var ul = document.getElementById("materialName");
	    ul.style.position = "absolute";
	    ul.style.top = ($(obj).offset().top+33)+"px";
	    ul.style.left = $(obj).offset().left+"px";*/
				var name = $(obj).val();
				if(name == "" || name == null) {
					$("#materialName").html("");
					return;
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/purchaser/listName.do?name=" + name,
					success: function(data) {
						if(data) {
							var html = "";
							for(var i = 0; i < data.length; i++) {
								html += "<li class='pointer' style='list-style:none;' onclick='getValue(this)'>" + data[i].name + "</li>";

							}
						}
						$(obj).after($("#listName"));
						$("#materialName").html(html);
						$("#listName").removeClass("dnone");
					}
				});
			}
			
			//只能输入数字
			function checkNum(obj,num){
				var vals=$(obj).val();
				var reg= /^\d+\.?\d*$/;  
				if(!reg.exec(vals)){
					$(obj).val("");
				}else{
					if(num==1){
						var count = $(obj).val();
						var price = $(obj).parent().next().find("input").val();
						$(obj).parent().next().next().find("input").val(count*price);
					}else if(num==2){
						var count = $(obj).parent().prev().find("input").val();
						var price = $(obj).val();
						$(obj).parent().next().find("input").val(count*price);
					}
				}
			}
			
			//需求部门赋值
			function assignDepartment(obj){
				var value = $(obj).val();
				var department = document.getElementsByName("department");
				for(var i=0;i<department.length;i++){
					$(department[i]).find("input").val(value);
				}
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#">首页</a>
					</li>
					<li>
						<a href="#">保障作业系统</a>
					</li>
					<li>
						<a href="#">采购计划管理</a>
					</li>
					<li class="active">
						<a href="#">采购需求管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<div>
				<h2 class="count_flow"><i>1</i>计划主信息</h2>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span> 计划名称</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" name="name" id="jhmc">
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span> 计划编号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" name="no" id="jhbh">
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">类别</span>
						<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select name="planType" id="wtype">
								<c:forEach items="${list }" var="obj">
									<option value="${obj.id }">${obj.name }</option>
								</c:forEach>
							</select>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>需求部门</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" name="depName" id="xqbm" onkeyup="assignDepartment(this)">
							<span class="add-on">i</span>
						</div>
					</li>
				</ul>

			</div>
			<div class="padding-top-10 clear">
				<h2 class="count_flow"><i>2</i>计划明细</h2>
				<ul class="ul_list">
					<div class="col-md-12 p115 mt10">
						<button class="btn btn-windows add" onclick="aadd()">添加</button>
						<%--
	<button  class="btn btn-windows add" onclick="same()">添加同级</button>
	--%><button class="btn btn-windows output" onclick="down()">下载模板</button>
						<button class="btn btn-windows output" onclick="uploadExcel();">导入</button>
						<button class="btn padding-left-10 padding-right-10 btn_back" onclick="typeShow()">查看产品分类目录</button>
						<button class="btn padding-left-10 padding-right-10 btn_back" onclick="chakan()">查看编制说明</button>
					</div>
					<div class="col-md-12 col-xs-12 col-sm-12 mt5 over_scroll" id="add_div">

						<form id="add_form" action="${pageContext.request.contextPath}/purchaser/adddetail.html" method="post">
							<table class="table table-bordered table-condensed ">
								<thead>
									<tr class="info">
										<th class="w50">序号</th>
										<th class="w100">需求部门</th>
										<th class="w200">物种名称</th>
										<th class="w100">规格型号</th>
										<th class="w100">质量技术标准（技术参数）</th>
										<th class="w100">计量单位</th>
										<th class="w100">采购数量</th>
										<th class="w150">单价（元）</th>
										<th class="w150">预算金额（万元）</th>
										<th class="w100">交货期限</th>
										<th class="w120">采购方式建议</th>
										<th class="w200">供应商名称</th>
										<th class="w80">是否申请办理免税</th>
										<th class="w200">物资用途（仅进口）</th>
										<th class="w200">使用单位（仅进口）</th>
										<th class="w200">备注</th>
										<th class="w100">状态</th>
									</tr>
								</thead>
								<tbody>
									<tr name="detailRow">
										<td class="tc w50">
											<input type="hidden" name="list[0].id" id="purid" value="">
											<input type="text" name="list[0].seq" value="">
										</td>
										<td class="w100" name="department"><input type="text" name="list[0].department" readonly="readonly"></td>
										<td class="w200"><input type="text" name="list[0].goodsName"></td>
										<td class="tc w100"><input type="text" name="list[0].stand"></td>
										<td class="tc w100"><input type="text" name="list[0].qualitStand"></td>
										<td class="tc w100"><input type="text" name="list[0].item"></td>
										<td class="tc w100"><input type="text" name="list[0].purchaseCount" onkeyup="checkNum(this,1)"></td>
										<td class="tc w150"><input type="text" name="list[0].price" onkeyup="checkNum(this,2)"></td>
										<td class="tc w150"><input type="text" name="list[0].budget" readonly="readonly"></td>
										<td class="w100"><input type="text" name="list[0].deliverDate"></td>
										<td class="w120">
											<select name="list[0].purchaseType" class="w100" id="select" onchange="changeType(this)">
												<option value="">请选择</option>
												<c:forEach items="${list2 }" var="obj">
													<option value="${obj.id }">${obj.name }</option>
												</c:forEach>
											</select>
										</td>
										<td class="tc w200"><input type="text" name="list[0].supplier" disabled="disabled"></td>
										<td class="tc w80"><input type="text" name="list[0].isFreeTax"></td>
										<td class="tc w200"><input type="text" name="list[0].goodsUse"></td>
										<td class="tc w200"><input type="text" name="list[0].useUnit"></td>
										<td class="tc w200"><input type="text" name="list[0].memo"></td>
										<td class="tc w100"><input type="text" name="list[0].status" value="暂存" readonly="readonly"></td>
									</tr>
								</tbody>
								<tr class="dnone">

									<td class="tc" colspan="16">
										<!--  <input type="hidden" name="planType" value="" id="ptype"> -->
										<input class="btn btn-windows add" name="dyadds" type="button" onclick="aadd()" value="添加">
										<!--  <input class="btn btn-windows delete" name="delt" type="button" onclick="delets()" value="删除"> -->

									</td>
								</tr>
							</table>
							<!--  <input class="btn btn-windows reset" value="取消"
				type="button" onclick="hide()"> -->

							<input type="hidden" name="planName" id="detailJhmc">
							<input type="hidden" name="planNo" id="detailJhbh">
							<input type="hidden" name="planType" id="detailType">
							<input type="hidden" name="planDepName" id="detailXqbm"/>
						</form>

					</div>

				</ul>
				<input class="btn btn-windows save" style="margin-left: 500px;" type="button" onclick="incr()" value="提交">
				<button class="btn btn-windows back" onclick="location.href='javascript:history.go(-1);'">返回</button>
			</div>

			<div id="content" class="dnone">
				<p align="center">编制说明
					<p style="margin-left: 20px;">1、请严格按照序号顺序为：一、(一)、1、(1)、a、(a)的顺序填写序号</p>

					<p style="margin-left: 20px;">2、任务明细最多为六级,请勿多于六级</p>

					<p style="margin-left: 20px;">3、请勿空行填写</p>

					<p style="margin-left: 20px;">4、需求单位名称不能为空</p>

					<p style="margin-left: 20px;">5、请按表式填写计划明细。用户可以编辑行，但不能增加或删除列。</p>

					<p style="margin-left: 20px;">6、最子级请严格按照填写说明填写，父级菜单请将序号与金额填写正确(金额=所有子项金额/10000)
					</p>

					<p style="margin-left: 20px;">7、采购方式填写选项包括：公开招标、邀请招标、竞争性谈判、询价、单一来源。</p>

					<p style="margin-left: 20px;">8、选择单一来源采购方式的，必须填写供应商名称；选择其他采购方式的不填。</p>

					<p style="margin-left: 20px;">9、规格型号和质量技术标准内容分别不得超过250、1000字。超过此范围的，请以附件形式另报。</p>

					<p style="margin-left: 20px;">10、采购数量、单价和预算金额必须为数字格式。其中单价单位为“元”，预算金额单位为“万元”。</p>
					<button class="btn padding-left-10 padding-right-10 btn_back" style="margin-left: 230px;" onclick="closeLayer()">确定</button>

			</div>

			<input type="hidden" id="count" value="0">
			<div id="catalogue" class="dnone">
				<div id="ztree" class="ztree"></div>
			</div>

			<form id="" action="${pageContext.request.contextPath}/purchaser/ztree.html" method="post">
				<input type="hidden" name="planName" id="fjhmc">
				<input type="hidden" name="planNo" id="fjhbh">
				<input type="hidden" name="type" value="" id="ptype">

			</form>
		</div>
	</body>

</html>