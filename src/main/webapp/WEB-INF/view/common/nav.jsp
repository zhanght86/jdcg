<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <div class="wrapper">
	<div class="header-v4 header-v5">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container">
            <div class="col-md-4 padding-bottom-30 mt10">
              <a href="">
                 <img alt="Logo" src="${pageContext.request.contextPath}/public/ZHH/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-8 topbar-v1 col-md-12 padding-0">
              <ul class="top-v1-data padding-0">
			    <c:forEach items="${sessionScope.resource}" var="resource" varStatus="vs">
					 <c:if test="${resource.menulevel == 2 }">
						 <li class="dropdown ${resource.icon}">
							<a <c:if test='${resource.url == null}'>ria-expanded="false" data-toggle="dropdown" class="dropdown-toggle p0_30 " href="javascript:void(0);"</c:if>
							<c:if test='${resource.url != null && resource.name != "安全退出"}'>href="javascript:loadHtml('${pageContext.request.contextPath}/${resource.url}');" </c:if>
							<c:if test='${resource.url != null && resource.name == "安全退出"}'>href="${pageContext.request.contextPath}/${resource.url}" </c:if> >
							<div class="top_icon ${resource.icon}_icon"></div>
							<span>${resource.name}</span>
							</a>
					 		
							<ul class="dropdown-menu">
							 	<c:forEach items="${sessionScope.resource}" var="res" varStatus="vs">
							 		<c:if test="${resource.id == res.parentId.id}">
				                   		<li class="line-block">
				                   			<a href="<c:if test='${res.url == null}'>javascript:void(0);</c:if><c:if test='${res.url != null}'>javascript:loadHtml('${pageContext.request.contextPath}/${res.url}');</c:if>" class="son-menu"><span class="mr5">◇</span>${res.name}</a>
			                   				<ul class="dropdown-menuson dropdown-menu">
				                   				<c:forEach items="${sessionScope.resource}" var="r" varStatus="vs">
				                   					<c:if test="${res.id == r.parentId.id}">
							                   			<li><a href="javascript:loadHtml('${pageContext.request.contextPath}/${r.url}');" class="son-menu son-three"><span class="mr5">◇</span>${r.name}</a></li>
				                   					</c:if>
				                   				</c:forEach>
			                   				</ul>
				                   		</li>
				                 	</c:if>
					 			</c:forEach>
			               	</ul>
						</li>
					</c:if>
				</c:forEach>
			    <li class="dropdown">
				    <a ria-expanded="false" data-toggle="dropdown" class="dropdown-toggle p0_30 " href="javascript:void(0);">
					  <span>页面样式帮助</span>
					</a>
					<ul class="dropdown-menu">
						<li class="line-block">
					    	<a href="${pageContext.request.contextPath}/static/backbottom.jsp" target="home" class="son-menu"><span class="mr5">◇</span>后台主页</a>
							<a href="${pageContext.request.contextPath}/static/backhead.jsp" target="home" class="son-menu"><span class="mr5">◇</span>后台头部</a>
							<a href="${pageContext.request.contextPath}/static/detail.jsp" target="home" class="son-menu"><span class="mr5">◇</span>表单页面</a>
							<a href="${pageContext.request.contextPath}/static/details.jsp" target="home" class="son-menu"><span class="mr5">◇</span>上下结构表单页面</a>
							<a href="${pageContext.request.contextPath}/static/order.jsp" target="home" class="son-menu"><span class="mr5">◇</span>列表页面</a>
							<a href="${pageContext.request.contextPath}/static/show_order.jsp" target="home" class="son-menu"><span class="mr5">◇</span>详情页面</a>
							<a href="${pageContext.request.contextPath}/static/manage.jsp" target="home" class="son-menu"><span class="mr5">◇</span>左右布局页面</a>
							<a href="${pageContext.request.contextPath}/static/evaluation.jsp" target="home" class="son-menu"><span class="mr5">◇</span>实施页面</a>
						</li>
					</ul>
				</li>
			  </ul>
			</div>
    </div>
	</div>
	</div>
   </div>
</div>