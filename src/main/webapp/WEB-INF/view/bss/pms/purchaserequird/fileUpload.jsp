<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/view/common/tags.jsp" %>
<%@include file="/WEB-INF/view/common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>  
  <head>  
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
    <script type="text/javascript">
 	  function uploadFile(){
 		 $.ajaxFileUpload ({
             url: '${pageContext.request.contextPath}/purchaser/upload.do', 
             secureuri: false, 
             fileElementId: 'fileName', 
             dataType: "json", 
             success: function (data){ 
            	 alert(data);
               if (data != null){ 
            	   alert(1);
            	   alert(data);
            	   if (data.success){
            		   loadData(data.obj);
            	   } else {
            		   layer.msg(data.obj);
            	   }
               }
             },error: function (data, status, e){
          	   layer.msg("上传失败");
               }
        });
 	  }
 	  
 	  function loadData(data){
 		  alert(data)
 		  if (data != null){
 			  for (var i =0;i<data.length;i++){
 				  
 			  }
 		  }
 	  }
 	  
 	</script>
 
  </head>  
  
  <body>  
    <div class="container clear margin-top-30" id="add_div" >
      <form>
      	<div>
      	  <input id="fileName" type="file" name="file" />
      	</div>
      	<div>
      	  <button type="button" class="" onclick="uploadFile();">确定</button>
      	  <button type="button" >取消</button>
      	</div>
      </form>
    </div>
  </body>
</html>