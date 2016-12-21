<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
  $(function(){
   var no = generateMixed();
    $("#documentNumber").val(no);
  });
  function start(){
   
    $("#form1").submit();
  }
  
  function generateMixed() {
    var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
       var res = "";
       for(var i = 0; i < 6 ; i ++) {
           var id = Math.ceil(Math.random()*35);
           res += chars[id];
       }
       return res;
  }
  
  function cancel(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
  }
</script>  
</head>

<body>
<div class="container">
  <form id="att" action="${pageContext.request.contextPath}/advancedProject/transmit.html"  method="post" name="form1" class="simple" target="_parent">
    <div id="openDiv" class="layui-layer-wrap" >
      <div class="drop_window">
        <ul class="list-unstyled">
           <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
             <div class="center" ><span>采购任务名称:</span><input  type="text" name="name" ></div>
             <div  class="center"><span>采购任务编号:</span><input id="documentNumber"  type="text" name="documentNumber"></div>
	              <input type="hidden" name="id" value="${projectId}"/>
	              <input type="hidden" name="proName" value="${proName}"/>
	              <input type="hidden" name="projectNumber" value="${projectNumber}"/>
	              <input type="hidden" name="department" value="${department}"/>
	              <input type="hidden" name="purchaseType" value="${purchaseType}"/>
	              <div class="center" ><span>预研通知书上传:</span></div>
	              <u:upload id="upload_id" businessId="${projectId}" multiple="true" buttonName="上传文档"  auto="true" typeId="${advancedAdvice}" sysKey="2"/>
	               <u:show showId="upload_id" businessId="${projectId}" sysKey="2" typeId="${advancedAdvice}"/>
          </li>
           <div class="clear"></div>
        </ul>
      </div>
    </div>
    <div class="tc mt10 col-md-12">
      <input class="btn btn-windows save" type="submit" value="确认">
      <input class="btn btn-windows reset" value="取消" type="button" onclick="cancel();">
    </div>
  </form>
</div>
</body>
</html>