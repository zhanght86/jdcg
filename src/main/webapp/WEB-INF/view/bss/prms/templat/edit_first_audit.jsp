<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../../../common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>初审项定义</title>
<script type="text/javascript">

	function cancel(){
	    var index=parent.layer.getFrameIndex(window.name);
	    parent.layer.close(index);
	    
	}
	function submit1(){
		var name = $("#name").val();
		if(!name){
			layer.tips("请填写名称", "#name");
			return ;
		}
		var id=[]; 
		$('input[name="kind"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==0){
			layer.tips("请选择类型", "#kind");
			return ;
		}
		
		/* var creater = $("#creater").val();
		if(!creater){
			layer.tips("请填写名称", "#creater");
			return ;
		} */
		var index=parent.layer.getFrameIndex(window.name);
		$.ajax({
			url:"${pageContext.request.contextPath}/auditTemplat/editFirstAudit.html",
			data:$("#form1").serialize(),
			type:"post",
			success:function(){
				parent.location.reload();
			},
			error:function(){
				layer.msg("更新失败",{offset: ['222px', '390px']});
			}
		});
		
	}
</script>
</head>
<body>
<div class="layui-layer-wrap">
    <div class="drop_window">
        <form action="${pageContext.request.contextPath}/firstAudit/edit.html" method="post" id="form1">
              <ul class="list-unstyled">
                <li class="mt10 col-md-12 p0">
                  <label class="col-md-12 pl20">初审项名称</label>
                  <span class="col-md-12">
                    <input type="text" id="name"  name="name" value="${temitem.name }">
                  </span>
                </li>
                <li class="mt10 col-md-12 p0">
                  <label class="col-md-12 pl20">要求类型</label>
                  <span class="col-md-12">
                    <input type="radio" name="kind" <c:if test="${fn:contains(temitem.kind,'商务')}">checked="true"</c:if> value="商务" >商务&nbsp;<input type="radio" id="kind" name="kind"<c:if test="${fn:contains(temitem.kind,'技术')}">checked="true"</c:if> value="技术" >技术
                    <input type="hidden" name="templatId" value="${temitem.templatId }">
                      <input type="hidden" name="id" value="${temitem.id }">
                      <input type="hidden" name="createdAt" value="<fmt:formatDate value='${temitem.createdAt}' pattern="yyyy-MM-dd" />">
                  </span>
                </li>
                <div class="clear"></div>
               </ul>
               <div class="tc mt10 col-md-12">
                 <input type="button"  value="修改" onclick="submit1();"  class="btn btn-windows edit"/>
                <input type="button"  value="取消"  class="btn btn-windows cancel" onclick="cancel();"/>
              </div>
         </form>
     </div>
</div>
</body>
</html>