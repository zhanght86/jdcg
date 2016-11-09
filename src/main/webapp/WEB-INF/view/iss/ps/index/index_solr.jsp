<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<script type="text/javascript">
     $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    groups: "${pages}">=3?3:"${pages}", //连续显示分页数
    		total: "${total}",
		    startRow: "${startRow}",
		    endRow : "${endRow}",
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	var condition = "${oldCondition}";
		            location.href = "${pageContext.request.contextPath}/index/solrSearch.html?page="+e.curr+"&condition="+condition;
		        }
		    }
		});
});
</script>
</head>

<body>
  <div class="wrapper">
  <jsp:include page="/index_head.jsp"></jsp:include>
  </div>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		  <li><a href="#"> 首页</a></li><li><a href="#">全文搜索信息</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container content height-350 job-content ">
   <div class="f18">共查到关于<span class="searchFont">${oldCondition}</span>的信息${solrMap['tdsTotal']}条</div>
    <div class="col-md-12 p20 border1 margin-top-20">
        <%--<div class="tab-v1">
          <h2 class="nav nav-tabs border0 padding-left-15">
            ${typeName}
		  </h2>
        </div>
          --%><div class="tab-content margin-bottom-20 margin-top-10">
            <div class="tab-pane fade active in">
              <div class="tag-box margin-bottom-0 padding-0">
                <ul class="categories li_square padding-left-15 margin-bottom-0">
                <c:forEach items="${solrMap['indexList']}" var="i">
                  <li>
                   <a href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${i.id}" title="" target="_self"><span class="f18 mr5">·</span>${i.title }</a>
                   <span class="hex pull-right"><fmt:formatDate value='${i.publishtime}' pattern="yyyy年MM月dd日 " /></span>
                  </li> 
                </c:forEach>             
                </ul>
              </div>
            </div>
          </div>
	     <div id="pagediv" align="right"></div></div>
        </div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
