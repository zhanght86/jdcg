<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tld/upload" prefix="f"%>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
function start(){
             layer.confirm('您确认要启动项目吗?',{
                offset: ['50px','90px'],
                shade:0.01,
                btn:['是','否'],
                },function(){
                    $("#att").submit();
                },function(){
                    var index=parent.layer.getFrameIndex(window.name);
                     parent.layer.close(index);
                }
                    
            ); 
               
}
function cancel(){
     var index=parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
     
}
</script>  
</head>

<body>
    <form id="att" action="${pageContext.request.contextPath}/project/start.html" 
        method="post" name="form1" class="simple" target="_parent">
        <input type="hidden" name="id" value="${project.id}"/>
         <div class="drop_window" id="delTask">
              <ul class="ul_list">
                 <li class="col-md-3 margin-0 padd">
                    <span class="col-md-12 padding-left-5">上传项目批文：</span>
                   <f:upload id="upload_id" businessId="${project.id}" typeId="${dataId}" sysKey="2"/>
                     <f:show showId="upload_id" businessId="${project.id}" sysKey="2" typeId="${dataId}"/>
                </li>
                 <li class="col-md-3 margin-0 padding-0 ">
			       <span class="col-md-12 padding-left-5">项目负责人：</span>
			       <div class="select_common">
			        <select name="principal">
			          <option selected="selected" value="">-请选择-</option>
                        <c:forEach items="${purchaseInfo}" var="info">
                            <option value="${info.relName}">${info.relName}</option>
                        </c:forEach> 
			        </select>
			       </div>
			     </li> 
                <div class="clear"></div>
              </ul>
         </div>
        
       <%--  <f:upload id="upload_id" businessId="${project.id}" typeId="${dataId}" sysKey="2"/>
        <f:show showId="upload_id" businessId="${project.id}" sysKey="2" typeId="${dataId}"/>
        <span class="f14 fl">项目负责人：</span>
        <div class="fl" id="name" >
          <select name="principal" style="width:100px" id="select">
       <option selected="selected" value="">-请选择-</option>
            <c:forEach items="${purchaseInfo}" var="info">
                            <option value="${info.relName}">${info.relName}</option>
            </c:forEach> 
         </select> 
        </div> --%>
        <div class="tc mt10 col-md-12">
     <a class="btn btn-windows save" onclick="start();">确认</a>
         <input class="btn btn-windows reset" value="取消" type="button" onclick="cancel();">
         </div>
    </form>
</body>
</html>
