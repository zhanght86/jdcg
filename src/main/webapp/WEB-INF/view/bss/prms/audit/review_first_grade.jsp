<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
  	<%@ include file="/WEB-INF/view/common.jsp" %>
    <title>项目评分</title>
<style> 
table{border-collapse:collapse;border-spacing:0px; width:100%; border:#ddd solid 0px;} 
table td{border:1px solid #ddd;height:30px; text-align:center; border-left:0px;} 
table th{ background:#f7f7f7; color:#a10333; border:#ddd solid 1px; white-space:nowrap; height:30px; border-top:0px;border-left:0px;} 
.t_left{width:35%; height:auto; float:left;border-top:1px solid #ddd;border-left:1px solid #ddd;} 
.t_r_content{width:100%; height:auto; background:#fff; overflow:auto;} 
.cl_freeze{height:auto;overflow:hidden; width:100%;}  
.t_r1{width:64.5%; height:auto; float:left;border-top:1px solid #ddd; border-right:#ddd solid 1px;} 
.t_r2{width:64.5%; height:auto; float:left;border-top:1px solid #ddd; border-right:#ddd solid 1px;} 
.t_r_t{width:100%; overflow:hidden;} 
.bordertop{ border-top:0px;} 
.t_r1 table{width:1700px;} 
.t_r2 table{width:735px;} 
.t_r_title1{width:1700px;}
.t_r_title2{width:735px;}
</style> 
<script type="text/javascript">
$(document).ready(function() { 
	 $("input[name='expertValue']").bind("keypress", function(event) {  
   var event= event || window.event;  
   var getValue = $(this).val();  
   //控制第一个不能输入小数点"."  
   if (getValue.length == 0 && event.which == 46) {  
       event.preventDefault();  
       return;  
   }  
   //控制只能输入一个小数点"."  
   if (getValue.indexOf('.') != -1 && event.which == 46) {  
       event.preventDefault();  
       return;  
   }  
   //控制只能输入的值  
   if (event.which && (event.which < 48 || event.which > 57) && event.which != 8 && event.which != 46) {  
       event.preventDefault();  
        return;  
       }  
   });  
   //失去焦点是触发  
   $("input[name='expertValue']").bind("blur", function(event) {  
   var value = $(this).val(), reg = /\.$/;  
   if (reg.test(value)) {  
   value = value.replace(reg, "");  
   $(this).val(value);  
   }  
   });  
   });  
   
	function audit(obj,id,supplierId,typeName,markTermId,quotaId){
		if(typeName=='2' || typeName=='3' ||typeName=='4' ||typeName=='5' ){
			var flag = 0;
			//填写的所有分数
			var expertValues=[];
			$(obj).parent().parent().find("input[name='expertValue']").each(function(){
				//该行的所有填写的值
				var value = $(this).val();
				expertValues.push(value);
				//判断是否有未填写的
				if(value=='' || value==null || value==undefined){
					flag++;
				}
			});
			//flag为0证明都填写了
			if(flag==0){
					var supplierIds = [];
					$(obj).parent().parent().find("input[name='supplierId']").each(function(){
						//所有供应商的id
						var value = $(this).val();
						supplierIds.push(value);
					});
					$("#markTermId").val(markTermId);
					$("#supplierIds").val(supplierIds);
					$("#expertValues").val(expertValues);
					$("#id").val(id);
					$("#typeName").val(typeName);
					$("#quotaId").val(quotaId);
					$.ajax({
						url:'${pageContext.request.contextPath}/reviewFirstAudit/caseGrade.html',
						data:$("#score_form").serialize(),
						type:"post",
						dataType:'JSON',
						success:function(data){
							for(var i = 0;i<data.length;i++){
								$(obj).parent().parent().find("input[name='supplierId']").each(function(){
									//算出的分数
									if(data[i].supplierId == $(this).val()){
										var dataScore = toDecimal(data[i].score);
										$(this).next().val(dataScore);
										$(this).next().next().html("<font color='red' size='5px'>" + dataScore + "</font>");
									}
								});
							}
						}
					});
			}
		}else{
		
			var expertValue = $(obj).val();
			if(expertValue != ""){
			$("#supplierIds").val(supplierId);
			$("#expertValues").val(expertValue);
			$("#id").val(id);
			$("#typeName").val(typeName);
			$("#quotaId").val(quotaId);
			$.ajax({
				url:'${pageContext.request.contextPath}/reviewFirstAudit/caseGradeTwo.html',
				data:$("#score_form").serialize(),
				type:"post",
				dataType:"JSON",
				success:function(data){
					data = toDecimal(data);
					$(obj).parent().next().find("input[name='expertScore']").val(data);
					// 修改,将input框改为直接显示,input设置为hidden,将input值传给span
					$(obj).parent().next().find("input[name='expertScore']").next().html("<font color='red' size='5px'>" + data + "</font>");
				}
				
			});
		}
		}
		
	}
	//功能：将浮点数四舍五入，取小数点后2位    
    function toDecimal(x) {    
        var f = parseFloat(x);    
        if (isNaN(f)) {    
            return;    
        }    
        f = Math.round(x*100)/100;    
        return f;    
    } 
	//提交
	function submit1(){
		var count = 0;
		$("#table2").find("input[name='expertValue']").each(function(){
			if($(this).val()==""){
				count++;
			}
		});
		$("#table2").find("input[name='expertScore']").each(function(){
			if($(this).val()==""){
				count++;
			}
		});
		if(count==0){
		$("#form1").submit();
		}else{
			layer.msg("还有未评分项",{offset: ['350px', '800px']});
		}
		
	}
	function zancun(){
		var expertId = "${expertId}";
		var packageId = "${packageId}";
		var projectId = "${projectId}";
		$.ajax({
			url: "${pageContext.request.contextPath}/reviewFirstAudit/zanCun.do",
			data: {"expertId": expertId, "packageId": packageId, "projectId": projectId},
			success: function(){
				layer.msg("已暂存!",{offset: ['400px', '750px']});
			}
		});
	}
  </script>
  <!-- 锁表头js -->
<script type="text/javascript">
    function FixTable(TableID, FixColumnNumber, width, height) {
    if ($("#" + TableID + "_tableLayout").length != 0) {
        $("#" + TableID + "_tableLayout").before($("#" + TableID));
        $("#" + TableID + "_tableLayout").empty();
    }
    else {
        $("#" + TableID).after("<div id='" + TableID + "_tableLayout' style='overflow:hidden;height:" + height + "px; width:" + width + "px;'></div>");
    }
    $('<div id="' + TableID + '_tableFix"></div>'
    + '<div id="' + TableID + '_tableHead"></div>'
    + '<div id="' + TableID + '_tableColumn"></div>'
    + '<div id="' + TableID + '_tableData"></div>').appendTo("#" + TableID + "_tableLayout");
    var oldtable = $("#" + TableID);
    var tableFixClone = oldtable.clone(true);
    tableFixClone.attr("id", TableID + "_tableFixClone");
    $("#" + TableID + "_tableFix").append(tableFixClone);
    var tableHeadClone = oldtable.clone(true);
    tableHeadClone.attr("id", TableID + "_tableHeadClone");
    $("#" + TableID + "_tableHead").append(tableHeadClone);
    var tableColumnClone = oldtable.clone(true);
    tableColumnClone.attr("id", TableID + "_tableColumnClone");
    $("#" + TableID + "_tableColumn").append(tableColumnClone);
    $("#" + TableID + "_tableData").append(oldtable);
    $("#" + TableID + "_tableLayout table").each(function () {
        $(this).css("margin", "0");
    });
    var HeadHeight = $("#" + TableID + "_tableHead thead").height();
    HeadHeight += 2;
    $("#" + TableID + "_tableHead").css("height", HeadHeight);
    $("#" + TableID + "_tableFix").css("height", HeadHeight);
    var ColumnsWidth = 0;
    var ColumnsNumber = 0;
    $("#" + TableID + "_tableColumn tr:last td:lt(" + FixColumnNumber + ")").each(function () {
        ColumnsWidth += $(this).outerWidth(true);
        ColumnsNumber++;
    });
    ColumnsWidth += 2;
    if ($.browser.msie) {
        switch ($.browser.version) {
            case "7.0":
                if (ColumnsNumber >= 3) ColumnsWidth--;
                break;
            case "8.0":
                if (ColumnsNumber >= 2) ColumnsWidth--;
                break;
        }
    }
    $("#" + TableID + "_tableColumn").css("width", ColumnsWidth);
    $("#" + TableID + "_tableFix").css("width", ColumnsWidth);
    $("#" + TableID + "_tableData").scroll(function () {
        $("#" + TableID + "_tableHead").scrollLeft($("#" + TableID + "_tableData").scrollLeft());
        $("#" + TableID + "_tableColumn").scrollTop($("#" + TableID + "_tableData").scrollTop());
    });
    $("#" + TableID + "_tableFix").css({ "overflow": "hidden", "position": "relative", "z-index": "50", "background-color": "#F7F7F7" });
    $("#" + TableID + "_tableHead").css({ "overflow": "hidden", "width": width - 17, "position": "relative", "z-index": "45", "background-color": "#F7F7F7" });
    $("#" + TableID + "_tableColumn").css({ "overflow": "hidden", "height": height - 17, "position": "relative", "z-index": "40", "background-color": "#F7F7F7" });
    $("#" + TableID + "_tableData").css({ "overflow": "scroll", "width": width, "height": height, "position": "relative", "z-index": "35" });
    if ($("#" + TableID + "_tableHead").width() > $("#" + TableID + "_tableFix table").width()) {
        $("#" + TableID + "_tableHead").css("width", $("#" + TableID + "_tableFix table").width());
        $("#" + TableID + "_tableData").css("width", $("#" + TableID + "_tableFix table").width() + 17);
    }
    if ($("#" + TableID + "_tableColumn").height() > $("#" + TableID + "_tableColumn table").height()) {
        $("#" + TableID + "_tableColumn").css("height", $("#" + TableID + "_tableColumn table").height());
        $("#" + TableID + "_tableData").css("height", $("#" + TableID + "_tableColumn table").height() + 17);
    }
    $("#" + TableID + "_tableFix").offset($("#" + TableID + "_tableLayout").offset());
    $("#" + TableID + "_tableHead").offset($("#" + TableID + "_tableLayout").offset());
    $("#" + TableID + "_tableColumn").offset($("#" + TableID + "_tableLayout").offset());
    $("#" + TableID + "_tableData").offset($("#" + TableID + "_tableLayout").offset());
}
$(document).ready(function () {
		var boxwidth = $("#content").width();
            FixTable("table", 1, boxwidth, 460);
        });
        
</script>
  </head>
  
<body style="font-size: 5px !important;">
  <div class="dnone">
  	<form  id="score_form">
  	  <input type="hidden" name="supplierIds" id="supplierIds">
  	  <input type="hidden" name="expertValues" id="expertValues">
  	  <input type="hidden" name="markTermId" id="markTermId">
      <input type="hidden" name="scoreModelId" id="id">
  	  <input type="hidden" name="typeName" id="typeName">
  	  <input type="hidden" name="quotaId" id="quotaId">
  	  <input type="hidden" name="projectId" id="projectId" value="${projectId }">
  	  <input type="hidden" name="packageId" id="packageId" value="${packageId }">
  	</form>
  </div>
  <div class="tab-content clear">
    <div class=class="col-md-12 tab-pane active"  id="tab-1">
 	  <div class="container clear margin-top-30" id="package">
 	    <div class="col-md-12 col-sm-12 col-xs-12 f18">
		  <div class="col-md-6 col-xs-12 col-sm-6 p0">项目名称：${project.name }（${pack.name }）</div>
		  <div  class="col-md-6 col-xs-12 col-sm-6 p0 tr">编号：${project.projectNumber }</div>
        </div>
 	    </table>
		<form action="${pageContext.request.contextPath}/expert/saveGrade.html" id="form1" method="post" >
		  <!--项目id  -->
	   	  <input type="hidden" name="projectId" id="projectId" value="${projectId }">
		  <!--包id  -->
	   	  <input type="hidden" name="packageId" id="packageId" value="${packageId }">
	        
	        <table class="m0" id="table" style="overflow: hidden;word-spacing: keep-all;" >
			  <tr>
			      <th colspan="4"></th>
			  </tr>
			  <tr>
		   	  	  <th width="25% tc">评审项目</th>
		   	      <th width="25% tc">评审指标</th>
		   	      <th width="25% tc">指标模型</th>
		   	      <th width="25% tc">标准分值</th>
			  </tr>
			    <c:forEach items="${markTermList}" var="markTerm">
			   		<c:forEach items="${scoreModelList}" var="score" varStatus="vs">
			    	  <c:if test="${score.markTerm.pid eq markTerm.id}">
			    	    <tr>
			    	      <td class="tc" width="25%" rowspan="${score.count}" <c:if test="${score.count eq '0' or score.count == 0}">style="display: none"</c:if> >${markTerm.name}</td>
			    	      <td class="tc" width="25%"><a href="javascript:void();" title="${score.reviewContent}">${score.name}</a></td>
			 	  		  <td class="tc" width="25%">
			 	    	    <c:if test="${score.typeName == 0}">模型一A</c:if>
			 	            <c:if test="${score.typeName == 1}">模型二</c:if>
				 	        <c:if test="${score.typeName == 2}">模型三</c:if>
				 	        <c:if test="${score.typeName == 3}">模型四 A</c:if>
				 	        <c:if test="${score.typeName == 4}">模型五</c:if>
				 	        <c:if test="${score.typeName == 5}">模型六</c:if>
				 	        <c:if test="${score.typeName == 6}">模型七</c:if>
				 	        <c:if test="${score.typeName == 7}">模型八</c:if>
				 	        <c:if test="${score.typeName == 8}">模型一B</c:if>
				 	        <c:if test="${score.typeName == 9}">模型四B</c:if>
				 	      </td>
				 	      <td class="tc" width="25% tc">${score.standardScore}</td>
				 	    </tr>
				 	  </c:if>
				 	</c:forEach>
				 </c:forEach>
			</table>	    
		  </div>
		  </div>
		  <div class="<c:if test="${size > 3}">t_r1</c:if> <c:if test="${size <= 3}">t_r2</c:if> mt20">
			<div class="t_one">
			<div class="t_r_t" id="t_r_t"> 
			<div class="<c:if test="${size > 3}">t_r_title1</c:if> <c:if test="${size <= 3}">t_r_title2</c:if>">
			<table>
				<tr>
			      <c:forEach items="${supplierList}" var="supplier">
				      <th colspan="2" class="tc" width="${length1}">${supplier.suppliers.supplierName}</th>
				    </c:forEach>
			  	</tr>
			  	<tr>
			  		<c:forEach items="${supplierList}" var="supplier">
			        	<th class="tc">评委填写</th>
		   		        <th class="tc">评审得分</th>
	   		  	  	</c:forEach>
			  	</tr>
		  	</table> 
			</div> 
			</div> 
		  <div class="t_r_content" id="t_r_content" onscroll="controlScroll()"> 
			<table> 
			<c:forEach items="${markTermList}" var="markTerm">
			   		<c:forEach items="${scoreModelList}" var="score" varStatus="vs">
			    	  <c:if test="${score.markTerm.pid eq markTerm.id}">
			    	    <tr>
				 	      <c:forEach items="${supplierList}" var="supplier">
					 	    <c:choose>
					 	      <c:when test="${score.typeName == '0'}">
					 	        <td class="tc p0" width="${length2}">
					 	          <select name="expertValue" title="单位:${score.unit}"
					 	            class="w50"  onchange="audit(this,'${score.id}','${supplier.suppliers.id}','${score.typeName}','${score.markTermId}','')"
					 	          >
					 	            <option value=""></option>
					 	            <option value="1" 
					 	              <c:forEach items="${scores}" var="sco">
					 	                <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id and sco.expertValue eq '1'}">selected=selected</c:if>
					 	              </c:forEach>
					 	            >是</option>
					 	            <option value="0"
					 	              <c:forEach items="${scores}" var="sco">
					 	                <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id and sco.expertValue eq '0'}">selected=selected</c:if>
					 	              </c:forEach>
					 	            >否</option>
					 	          </select>
					 	        </td>
					 	      </c:when>
					 	      <c:otherwise>
					 	        <td class="tc p0" width="${length2}">
					 	          <input type="text" title="单位:${score.unit}" name="expertValue" id="ipt5" onpaste="return false;" class="m0 w50"
					 	            onchange="audit(this,'${score.id}','${supplier.suppliers.id}','${score.typeName}','${score.markTermId}','')"
					 	            <c:forEach items="${scores}" var="sco">
					 	              <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}">value="${sco.expertValue}"</c:if>
					 	            </c:forEach>
					 	          >
					 	        </td>
					 	      </c:otherwise>
					 	    </c:choose>
					 	    <td class="tc" width="${length2}">
					 	      <input type="hidden" name="supplierId"  value="${supplier.suppliers.id}"/>
					 	      <input type="hidden" name="expertScore" readonly="readonly" style="width: 80px;" 
					 	      	<c:forEach items="${scores}" var="sco">
					 	          <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}">value="${sco.score}"</c:if>
					 	        </c:forEach>
					 	      />
					 	      <span><c:forEach items="${scores}" var="sco">
					 	          <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}"><font color="red" class="f18">${sco.score}</font></c:if>
					 	        </c:forEach></span>
					 	    </td>
				 	      </c:forEach>
					    </tr> 
					  </c:if>
			        </c:forEach>
			    </c:forEach>
			</table> 
			</div>
			</div> 
		</div>
			<div class="tc col-md-12 col-sm-12 col-xs-12 mt20">
			  <input type="button" onclick="submit1();"  value="提交" class="btn btn-windows git">
			  <input type="button" onclick="zancun();"  value="暂存" class="btn btn-windows save">
			  <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'"><br/><br/><br/>
		    </div>
		</form>
		
	  </div>
	</div>
  </div>
</body>
</html>
