<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/synch/list.js"></script>
  </head>
  <body>
   <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
	  <div class="container">
	    <ul class="breadcrumb margin-left-0">
		  <li><a href="#"> 首页</a></li>
		  <li><a>保障系统</a></li>
		  <li class="active"><a>数据同步</a></li>
	    </ul>
	    <div class="clear"></div>
    </div>
  </div>

  <div class="container">
    <input type="hidden" id="operType" name="operType" value="${operType}"/>
	  <div class="search_detail">
        <ul class="demand_list">
       	  <li class="fl">
	        <label class="fl">开始时间：</label>
	       	  <span><input type="text"  id="startTime" name="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220"/></span>
       	  </li>
       	  <li class="fl">
            <label class="fl">结束时间：</label>
            <span><input type="text" id="endTime" name="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220" /></span>
       	  </li>
          <button class="btn fl" onclick="synch()">同步</button>
      </ul>
      <div class="clear"></div>
  	</div>
	<div class="content table_box">
	  <table class="table table-bordered table-condensed table-hover table-striped" id="dataTable" >
	    <thead>
		  <tr>
		    <th class="info w50">序号</th>
			<th class="info w120">同步类型</th>
		    <th class="info w200">同步时间</th>
			<th class="info">描述</th>
		  </tr>
		</thead>
		<tbody></tbody>
	  </table>
    </div>
	<div id="pagediv" align="right"></div>
   </div>
  </body>

</html>