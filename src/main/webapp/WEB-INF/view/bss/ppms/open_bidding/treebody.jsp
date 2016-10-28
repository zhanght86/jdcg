<%@page import="bss.model.ppms.ScoreModel"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
        <link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/oms/css/purchase.css"> 
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle.css">
	
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/oms/js/select-tree.js"></script>
	<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/oms/js/validate-extend.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ScoreModel scoreModel = (ScoreModel)request.getAttribute("scoreModel");
System.out.print(scoreModel);
%>
<script type="text/javascript">
	function choseModel(){
		var model = $("#model").val();
		console.dir(model);
		$("#showParamButton").hide();
		if(model==""){
			$("#showbutton").hide();
			$("#show_table tbody tr").remove();
		}else if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model1 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="1"){
			var addSubtractTypeName = $("#sm2").val();
			$("#show_table tbody tr").remove();
			if(addSubtractTypeName=="0"){
				$("#model21 tbody tr").clone().appendTo("#show_table tbody");
			}else if(addSubtractTypeName=="1"){
				$("#model22 tbody tr").clone().appendTo("#show_table tbody");
			}else{
				//默认加分实例
				$("#model21 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="2"){
			$("#show_table tbody tr").remove();
			$("#model3 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="3"){
			$("#show_table tbody tr").remove();
			$("#model4 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="4"){
			$("#show_table tbody tr").remove();
			$("#model5 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="5"){
			$("#show_table tbody tr").remove();
			$("#model6 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="6"){
			$("#show_table tbody tr").remove();
			$("#model7 tbody tr").clone().appendTo("#show_table tbody");
			//$("#showbutton").show();
			$("#showParamButton").show();
		}else if(model=="7"){
			$("#show_table tbody tr").remove();
			$("#model8 tbody tr").clone().appendTo("#show_table tbody");
			//$("#showbutton").show();
			$("#showParamButton").show();
		}
	}
	function modelTwoAddSubstact(){
		var model = $("#model").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model21 tbody tr").clone().appendTo("#show_table tbody");
		}else{
			$("#show_table tbody tr").remove();
			$("#model22 tbody tr").clone().appendTo("#show_table tbody");
		}
	}
	function gernerator(){
		var model = $("#model").val();
		if(model=="0"){
			gerneratorOne();
		}else if(model=="1"){
			gerneratorTwo();
		}else if(model=="2"){
			gerneratorThree();
		}else if(model=="3"){
			gerneratorFour();
		}else if(model=="4"){
			gerneratorFive();
		}else if(model=="5"){
			gerneratorSix();
		}
	}
	function gerneratorOne(){
		var judgeContent = $("#judgeContent").val();
		var standardScore = $("#standardScore").val();
		var str = judgeContent+" "+"是"+standardScore+"分 "+"否0分";
		$("#easyUnderstandContent1").val(str);
	}
	function gerneratorTwo(){
		var reviewParam = $("#reviewParam").val();
		var addSubtractTypeName = $("#addSubtractTypeName").val();
		
		var reviewStandScore = $("#reviewStandScore").val();
		var maxScore = $("#maxScore").val();
		var minScore = $("#minScore").val();
		var unitScore = $("#unitScore").val();
		var unit = $("#unit").val();
		
		var type ="";
		if(addSubtractTypeName=="0"){
			type = " 加分类型" + " 每单位得" +unitScore +"分" + " 起始分值为" + reviewStandScore+"分"+" 最高分不超过"+maxScore+"分";
			var str = reviewParam + type ; 
			$("#easyUnderstandContent21").val(str);
		}else{
			type = " 减分类型" +" 基准分值为"+reviewStandScore+"分" +" 每单位减"+unitScore+"分"+" 最低分值为"+minScore+"分";
			var str = reviewParam + type ; 
			$("#easyUnderstandContent22").val(str);
		}
		
	}
	function gerneratorThree(){
		var reviewParam = $("#reviewParam").val();
		var unit = $("#unit").val();
		var score = $("#score").val();
		//var addSubtractTypeName = $("#addSubtractTypeName").val();
		var maxScore = $("#maxScore").val();
		var minScore = $("#minScore").val();
		var str = "加分实例:以"+reviewParam+"最低值为基准排序递增，第一名得"+minScore+"分,依次递增"+score+"分,最高分为"+maxScore+"分";
		$("#easyUnderstandContent3").val(str);
	}
	function gerneratorFour(){
		var reviewParam = $("#reviewParam").val();
		var unit = $("#unit").val();
		var score = $("#score").val();
		var maxScore = $("#maxScore").val();
		var minScore = $("#minScore").val();
		var str = "减分实例:以"+reviewParam+"最高值为基准排序递减，第一名得"+maxScore+"分,依次递减"+score+"分,最低分为"+minScore+"分";
		$("#easyUnderstandContent4").val(str);
	}
	function gerneratorFive(){
		var reviewParam = $("#reviewParam").val();
		var standardScore = $("#standardScore").val();
		var unit = $("#unit").val();
		var str = "以" + reviewParam +"最高为基准,得分=("+reviewParam+"/基准值)*"+standardScore;
		$("#easyUnderstandContent5").val(str);
	}
	function gerneratorSix(){
		var reviewParam = $("#reviewParam").val();
		var standardScore = $("#standardScore").val();
		var unit = $("#unit").val();
		var str = "以" + reviewParam +"最低为基准,得分=(基准值/"+reviewParam+")*"+standardScore;
		$("#easyUnderstandContent6").val(str);
	}
	function associate(){
		var s = validteModel().form();
		console.dir(s);
		if(s){
			$("#formID").attr('action','${pageContext.request.contextPath}/intelligentScore/operatorScoreModel.do').submit();
		}else{
			return;
		}
	}
	function pageOnLoad(){
		var model = $("#sm").val();
		$("#showParamButton").hide();
		$("#model").val(model);
		//console.dir(model==undefined);
		if(model !=undefined && model==""){
			$("#showbutton").hide();
			$("#show_table tbody tr").remove();
		}else if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model1 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="1"){
			var addSubtractTypeName = $("#sm2").val();
			$("#addSubtractTypeName").val(addSubtractTypeName);
			$("#show_table tbody tr").remove();
			if(addSubtractTypeName!=undefined && addSubtractTypeName=="0"){
				$("#model21 tbody tr").clone().appendTo("#show_table tbody");
			}else if(addSubtractTypeName=="1"){
				$("#model22 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="2"){
			$("#show_table tbody tr").remove();
			$("#model3 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="3"){
			$("#show_table tbody tr").remove();
			$("#model4 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="4"){
			$("#show_table tbody tr").remove();
			$("#model5 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="5"){
			$("#show_table tbody tr").remove();
			$("#model6 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="6"){
			$("#show_table tbody tr").remove();
			$("#model7 tbody tr").clone().appendTo("#show_table tbody");
			//$("#showbutton").show();
			$("#showParamButton").show();
		}else if(model=="7"){
			$("#show_table tbody tr").remove();
			$("#model8 tbody tr").clone().appendTo("#show_table tbody");
			//$("#showbutton").show();
			$("#showParamButton").show();
		}
	}
</script>  
<script type="text/javascript">
	//validate
	function validteModel(){
		return $("#formID").validate({
			ignore: [],
			focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
			onkeyup : false,
			rules : {
				standardScore : {
					required : true,
					number:true
				},
				judgeContent : {
					required : true
				},
				/* easyUnderstandContent : {
					required : true
				}, */
				reviewParam : {
					required : true
				},
				reviewStandScore : {
					required : true,
					number:true
				},
				maxScore : {
					required : true,
					number:true
				},
				unitScore : {
					required : true,
					number:true
				},
				unit : {
					required : true
				},
				minScore : {
					required : true,
					number:true
				},
				intervalNumber : {
					required : true,
					number:true
				}
			},
			messages : {
				standardScore : {
					required : "该项满分值为必填项",
					number:"必须为数字"
				},
				judgeContent : {
					required : "该项内容为必填项"
				},
				/* easyUnderstandContent : {
					required : "请点击生成白话文"
				}, */
				reviewParam : {
					required : "该项内容为必填项"
				},
				reviewStandScore : {
					required : "该项内容为必填项",
					number:"必须为数字"
				},
				maxScore : {
					required : "该项内容为必填项",
					number:"必须为数字"
				},
				unitScore : {
					required : "该项内容为必填项",
					number:"必须为数字"
				},
				unit : {
					required : "该项内容为必填项"
				},
				minScore : {
					required : "该项内容为必填项",
					number:"必须为数字"
				},
				intervalNumber : {
					required : "该项内容为必填项",
					number:"必须为数字"
				}
			},
			showErrors: function(errorMap, errorList) {
	           $.each(this.successList, function(index, value) {
	             return $(value).popover("hide");
	           });
           	   return $.each(errorList, function(index, value) {
             		var _popover;
             		_popover = $(value.element).popover({
                    trigger: "manual",
                    placement: "top",
                    content: value.message,
                    template: "<div class=\"popover\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
               });
             _popover.data("bs.popover").options.content = value.message;
             return _popover.popover("show");
           });
         }
		}); 
	}
</script>
    </head>
<body onload="pageOnLoad();">
	<input type="hidden" id="sm" value="${scoreModel.typeName }">
	<input type="hidden" id="sm2" value="${scoreModel.addSubtractTypeName }">
	<div>
		<form
			action=""
			method="post"  id="formID">
			<div class="mt20 mr20">
				<span>选择模型</span> <select id="model" name="typeName"
					onchange="choseModel();">
					<option value="">请选择</option>
					<option value="0">模型1:是否判断</option>
					<option value="1">模型2:按项加减分</option>
					<option value="2">模型3:评审数额最高递减</option>
					<option value="3">模型4:评审数额最低递增</option>
					<option value="4">模型5:评审数额高计算</option>
					<option value="5">模型6:评审数额低计算</option>
					<option value="6">模型7:评审数额地区间递增</option>
					<option value="7">模型8:评审数额高区间递减</option>
				</select>
			</div>
			<input id="packageId" name="packageId" type="hidden" value="${packageId }">
			<input id="markTermId" name="markTermId" type="hidden" value="${markTermId }">
			<input id="id" type="hidden" name="id" value="${scoreModel.id }">
			<input id ="" type="hidden" name="name" value="${markTermName }">
			<table class="table table-striped table-bordered table-hover mt20"
				id="show_table" style="width: 305px;">
				<tbody>
				</tbody>
			</table>
		</form>
	</div>
	<div class="col-md-12" id="showbutton" style="display: none;">
		<div class="mt40 tc mb50">
			<input type="button" class="btn  padding-right-20 btn_back margin-5"
				onclick="gernerator();" value="翻译成白话文"> <input type="button"
				class="btn  padding-right-20 btn_back margin-5"
				onclick="associate();" value="关联模型">
		</div>
	</div>
	<div class="col-md-12" id="showParamButton" style="display: none;">
		<div class="mt40 tc mb50">
			<input type="button" class="btn  padding-right-20 btn_back margin-5"
				onclick="addParamInterval();" value="添加参数区间"> <input type="button"
				class="btn  padding-right-20 btn_back margin-5"
				onclick="associate();" value="关联模型">
		</div>
	</div>
	<!-- 八大模型 -->
	<table id="model1" style="display: none;width: 305px;">
		<tbody>
			<tr>
				<td style="width: 300px;">标准分值</td>
				<td><input name="standardScore" id="standardScore" value="${scoreModel.standardScore }" title="该项的满分值为多少"></td>
			</tr>
			<tr>
				<td>判断内容</td>
				<td><input name="judgeContent" id="judgeContent" value="${scoreModel.judgeContent }" title="该项内容为判断的唯一依据"></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="wh212-67" name="easyUnderstandContent" id="easyUnderstandContent1" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain" value="" readonly="readonly">是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model21" style="display: none;width: 305px;">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例如，近五年获得省以上工商部门颁发知名品牌的数量，一个得1分"></td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName" onchange="modelTwoAddSubstact();"><option value="0" selected="selected">加分</option><option value="1">减分</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">起始参数</td>
				<td><input name="reviewStandScore" id="reviewStandScore" value="${scoreModel.reviewStandScore }" title="该项的起始分值为多少，默认是0"></td>
			</tr>
			<tr>
				<td style="width: 300px;">最高分</td>
				<td><input name="maxScore" id="maxScore" value="${scoreModel.maxScore }" title="该项的满分值是多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">每单位分值</td>
				<td><input name="unitScore" id="unitScore" value="${scoreModel.unitScore }" title="每项单位得分值是多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位"></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="wh212-67" name="easyUnderstandContent" id="easyUnderstandContent21">${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain"  readonly="readonly">按项加减分.采购文件明确标准分值，加减分项，加减分值和最高最低分值限制，按照加减分项的项目名称，系统自动计算得分。如(正偏离，负偏离)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model22" style="display: none;width: 305px;">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例如，近五年获得省以上工商部门颁发知名品牌的数量，一个得1分"></td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName"><option value="0">加分</option><option value="1" selected="selected">减分</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">基准分值</td>
				<td><input name="reviewStandScore" id="reviewStandScore" value="${scoreModel.reviewStandScore }"  title="该项从基准分值往下开始扣分"></td>
			</tr>
			<tr>
				<td style="width: 300px;">最低分</td>
				<td><input name="minScore" id="minScore" title="该项的最低分是多少" value="${scoreModel.minScore }"></td>
			</tr>
			<tr>
				<td style="width: 300px;">每单位分值</td>
				<td><input name="unitScore" id="unitScore" value="${scoreModel.unitScore }" title="每项单位减分值是多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位"></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="wh212-67" name="easyUnderstandContent" id="easyUnderstandContent22">${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea name="standExplain" id="standExplain"  readonly="readonly">按项加减分.采购文件明确标准分值，加减分项，加减分值和最高最低分值限制，按照加减分项的项目名称，系统自动计算得分。如(正偏离，负偏离)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model3" style="display: none;width: 305px;">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例如，矿泉水容量从小到大排列，第一名得最低分，依次递增"></td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位"></td>
			</tr>
			<tr>
				<td style="width: 300px;">分差</td>
				<td><input name="score" id="score" value="${scoreModel.score }" title="依次加多少分"></td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName3"><option value="0" selected="selected">加分</option><option value="1">减分</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">最高分</td>
				<td><input name="maxScore" id="maxScore" value="${scoreModel.maxScore }" title="该项的最高值是多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">最低分</td>
				<td><input name="minScore" id="minScore" value="${scoreModel.minScore }" title="该项的最低分是多少"></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="wh212-67" name="easyUnderstandContent" id="easyUnderstandContent3" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain" value="" readonly="readonly">是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model4" style="display: none;width: 305px;">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例如自行车重量从小到大排序，第一名得最高分，依次递减分值"></td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位"></td>
			</tr>
			<tr>
				<td style="width: 300px;">分差</td>
				<td><input name="score" id="score" value="${scoreModel.score }" title="依次递减多少分"></td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName4"><option value="0">加分</option><option value="1" selected="selected">减分</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">最高分</td>
				<td><input name="maxScore" id="maxScore" value="${scoreModel.maxScore }" title="该项的最高值是多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">最低分</td>
				<td><input name="minScore" id="minScore" value="${scoreModel.minScore }" title="该项的最低值是多少"></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="wh212-67" name="easyUnderstandContent" id="easyUnderstandContent4" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain" value="" readonly="readonly">是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model5" style="display: none;width: 305px;">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例：根据企业近三年平均资产总额评分，最高值为基准分值,得分=(企业资产总额/基准值)*标准分值"></td>
			</tr>
			<tr>
				<td style="width: 300px;">标准分值</td>
				<td><input name="standardScore" id="standardScore" value="${scoreModel.standardScore }" title="该项的满分值为多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位"></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="wh212-67" name="easyUnderstandContent" id="easyUnderstandContent5" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain" value="" readonly="readonly">是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model6" style="display: none;width: 305px;">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例:满足招标文件要求且报价最低为基准值，得分=(基准值/最低报价)*标准分值 "></td>
			</tr>
			<tr>
				<td style="width: 300px;">标准分值</td>
				<td><input name="standardScore" id="standardScore" value="${scoreModel.standardScore }" title="该项的满分值为多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位"></td>
			</tr>
			<tr>
				<td>加减分类型</td>
				<td><select name="addSubtractTypeName" id="addSubtractTypeName4"><option value="0">加分</option><option value="1" selected="selected">减分</option></select></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td><textarea readonly="readonly" class="wh212-67" name="easyUnderstandContent" id="easyUnderstandContent6" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain" value="" readonly="readonly">是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model71" style="display: none;width: 305px;">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例:百公里油耗,6升以下为满分，每增加一升扣0.5分，其中百公里油耗为评审参数"></td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位"></td>
			</tr>
			<tr>
				<td style="width: 300px;">区间类型</td>
				<td><select name="intervalTypeName" id="intervalTypeName71"><option value="0">差额相等</option><option value="1" selected="selected">差额区间</option></select></td>
			</tr>
			<tr>
				<td style="width: 300px;">评审基准数</td>
				<td><input name="reviewStandScore" id="reviewStandScore" value="${scoreModel.reviewStandScore }" title="该项内容为评审参数参照值"></td>
			</tr>
			<tr>
				<td style="width: 300px;">每区间等差额</td>
				<td><input name="intervalNumber" id="intervalNumber" value="${scoreModel.intervalNumber }" title="每个区间之间的差额"></td>
			</tr>
			<tr>
				<td style="width: 300px;">加减分分值</td>
				<td><input name="score" id="score" value="${scoreModel.score }" title="加减多少分"></td>
			</tr>
			<tr>
				<td style="width: 300px;">评审参数截止数</td>
				<td><input name="score" id="score" value="${scoreModel.score }" title="如果加分，高于截止数为满分，如果减分，低于截止数为0分"></td>
			</tr>
			<tr>
				<td style="width: 300px;">最高分</td>
				<td><input name="maxScore" id="maxScore" value="${scoreModel.maxScore }" title="该项的满分值是多少"></td>
			</tr>
			<tr>
				<td style="width: 300px;">最低分</td>
				<td><input name="minScore" id="minScore" value="${scoreModel.minScore }" title="该项的最低分是多少"></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain" value="" readonly="readonly">是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)</textarea></td>
			</tr>
			
		</tbody>
	</table>
	<table id="model72" style="display: none;width: 305px;">
		<tbody>
			<tr>
				<td style="width: 300px;">评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }" title="例:百公里油耗,6升以下为满分，每增加一升扣0.5分，其中百公里油耗为评审参数"></td>
			</tr>
			<tr>
				<td style="width: 300px;">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" title="评审参数的单位"></td>
			</tr>
			<tr>
				<td style="width: 300px;">区间类型</td>
				<td><select name="intervalTypeName" id="intervalTypeName71"><option value="0">差额相等</option><option value="1" selected="selected">差额区间</option></select></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain" value="" readonly="readonly">是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)</textarea></td>
			</tr>
		</tbody>
	</table>
	<table id="model73" style="display: none;width: 305px;">
		<tbody>
			
			<tr>
				<td>当前模型标准解释</td>
				<td><textarea class="wh212-67" name="standExplain" id="standExplain" value="" readonly="readonly">是否判断.采购文件明确满足或不满足项的临界值或有无的项目要求。评审系统自动识别满足不满足，生成通过或否决的结果，如(必要设备，关键技术，员工人数等)</textarea></td>
			</tr>
		</tbody>
	</table>
	<!-- 八大模型 -->
</body>