<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
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
      
      
      window.location.href="${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo="+no;
    }
    
    function edit(){
      var id=[]; 
    $('input[name="chkItem"]:checked').each(function(){ 
      id.push($(this).val());
    }); 
    if(id.length==1){
      
      window.location.href="${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo="+no;
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
        window.location.href="${pageContext.request.contextPath}/park/delete.html?id="+id;
      });
    }else{
      layer.alert("请选择要删除的版块",{offset: ['222px', '390px'], shade:0.01});
    }
    }
    var index;
    function add(){
      
      index=layer.open({
        type: 1, //page层
        area: ['300px', '200px'],
        title: '',
        closeBtn: 1,
        shade:0.01, //遮罩透明度
        moveType: 1, //拖拽风格，0是默认，1是传统拖动
        shift: 1, //0-6的动画形式，-1不开启
        offset: ['80px', '600px'],
        content: $('#content'),
      });
      
   
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
    var val=$("input[name='goods']:checked").val();
    
    window.location.href="${pageContext.request.contextPath}/purchaser/add.html?type=" + val;
    layer.close(index);
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

<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container" id="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="#"> 首页</a></li>
        <li><a href="#">保障作业系统</a></li>
        <li><a href="#">采购计划管理</a></li>
        <!-- <li class="active"><a href="#">采购需求管理</a></li> -->
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <div class="container">
    <div class="headline-v2 fl">
      <h2>计划明细</h2>
    </div>
    <div class="container clear margin-top-30"  id="content" >
      <form action="${pageContext.request.contextPath}/purchaser/update.html" method="post">
       <table id="table" style="border-bottom-color: #dddddd; border-top-color: #dddddd; color: #333333; width:1600px; border-right-color: #dddddd; font-size: medium; border-left-color: #dddddd; max-width:10000px"
  border="1" cellspacing="0" cellpadding="0" class="table table-bordered table-condensed">
          <thead>
            <tr class="space_nowrap">
              <th class="info w50">序号</th>
              <th class="info w260">需求部门</th>
              <th class="info w200">物资类别<br>及名称</th>
              <th class="info w200">规格型号</th>
              <th class="info w140">质量技术标准<br>（技术参数）</th>
              <th class="info w50">计量</br>单位</th>
              <th class="info w50">采购</br>数量</th>
              <th class="info w80">单价<br>（元）</th>
              <th class="info w80">预算金额<br>（万元）</th>
              <th class="info w150">交货期限</th>
              <th class="info w100">采购方式建议</th>
              <th class="info w200">采购机构</th>
              <th class="info w260">供应商名称</th>
              <th class="info w80">是否申请<br>办理免税</th>
            <!--   <th class="info w150">物资用途<br>（仅进口）</th>
              <th class="info w150">使用单位<br>（仅进口）</th> -->
              <th class="info w260">备注</th>
              <th class="info w260">附件</th>
            </tr>
          </thead>

          <c:forEach items="${list }" var="obj" varStatus="vs">
            <tr>
              <td class="tc w50"><div class="w50">${obj.seq }</div> 
              </td>
              <td  class="tl w260"><div class="w260">${obj.department}</div></td >
              <%-- <td class="tl pl20">
                   <c:forEach items="${requires }" var="re" >
                    <c:if test="${obj.department==re.id }"> ${re.name }</c:if>
                  </c:forEach> 
          
              </td> --%>
              <%-- <td>${obj.goodsName }</td> --%>
              <td title="${obj.goodsName}" class="tl w200">
               <div class="w200">
                  <c:if test="${fn:length (obj.goodsName) > 8}">${fn:substring(obj.goodsName,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.goodsName) <= 8}">${obj.goodsName}</c:if>
               </div>
              </td >
              <%-- <td class="tc"> ${obj.stand }</td> --%>
              <td title="${obj.stand}" class="tl w200">
                <div class="w200">
                  <c:if test="${fn:length (obj.stand) > 8}">${fn:substring(obj.stand,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.stand) <= 8}">${obj.stand}</c:if>
                </div>
              </td >
              <%-- <td class="tc"> ${obj.qualitStand }</td> --%>
              <td title="${obj.qualitStand}" class="tl w200">
                <div class="w140">
                  <c:if test="${fn:length (obj.qualitStand) > 8}">${fn:substring(obj.qualitStand,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.qualitStand) <= 8}">${obj.qualitStand}</c:if>
                </div>
              </td >
              <%-- <td class="tc"> ${obj.item }</td> --%>
              <td title="${obj.item}" class="tc w50">
                <div class="w50">
                 <c:if test="${fn:length (obj.item) > 8}">${fn:substring(obj.item,0,7)}...</c:if>
                 <c:if test="${fn:length(obj.item) <= 8}">${obj.item}</c:if>
                </div>
              </td >
              <td class="tc w50">
               <div class="w50">
                 ${obj.purchaseCount }
               </div>
              </td>
              <td class="tr">
               <div class="w80">${obj.price }</div>
              </td>
              <td class="tr">
               <div class="w80">${obj.budget }</div>
              </td>
              <td class="tl">
               <div class="w150">${obj.deliverDate }</div>
              </td>
              <td class="tc">
                <div class="w100">
                 <c:forEach items="${kind}" var="kind" >
                 <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                 </c:forEach>
                </div>
              </td>
              <td class="tl">
                <div class="w200">
                  <c:forEach items="${org}" var="og" >
                   <c:if test="${og.orgId == obj.organization}">${og.name}</c:if>
                  </c:forEach>
                </div>
              </td>  
              <td title="${obj.supplier}" class="tl">
               <div class="w260">
                <c:if test="${fn:length (obj.supplier) > 8}">${fn:substring(obj.supplier,0,7)}...</c:if>
                <c:if test="${fn:length(obj.supplier) <= 8}">${obj.supplier}</c:if>
               </div>
              </td >
              <%-- <td class="tc">${obj.isFreeTax }</td> --%>
              <td title="${obj.isFreeTax}" class="tc">
               <div class="w80">
                <c:if test="${fn:length (obj.isFreeTax) > 8}">${fn:substring(obj.isFreeTax,0,7)}...</c:if>
                <c:if test="${fn:length(obj.isFreeTax) <= 8}">${obj.isFreeTax}</c:if>
               </div>
              </td >
              <%-- <td class="tc">${obj.goodsUse }</td> --%>
           <%--    <td title="${obj.goodsUse}" class="tl pl20">
              <c:if test="${fn:length (obj.goodsUse) > 8}">${fn:substring(obj.goodsUse,0,7)}...</c:if>
              <c:if test="${fn:length(obj.goodsUse) <= 8}">${obj.goodsUse}</c:if>
              </td > 
              <td class="tl pl20">${obj.useUnit }</td> --%>
              <td title="${obj.memo}" class="tl">
                <div class="w260">
                 <c:if test="${fn:length (obj.memo) > 8}">${fn:substring(obj.memo,0,7)}...</c:if>
                 <c:if test="${fn:length(obj.memo) <= 8}">${obj.memo}</c:if>
                </div>
              </td > 
           
            <td style="width:300px;" class="p0">
					  <div class="w200">
							  <u:upload id="pUp${vs.index}" businessId="${obj.id}" buttonName="上传文件" sysKey="2" typeId="${typeId}" auto="true" />
							  <u:show showId="pShow${vs.index}"  businessId="${obj.id}" sysKey="2" typeId="${typeId}" />
						 </div>	
			  </td>
						 
            </tr>

          </c:forEach>
        </table>
        
        <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
         <input class="btn btn-windows back" value="返回" type="button"
                    onclick="location.href='javascript:history.go(-1);'">
      </form>
    </div>
  </div>
</body>
</html>
