<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <base href="${pageContext.request.contextPath}/" target="_self">

    <title>模版管理</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
  <link rel="stylesheet" type="text/css" href="styles.css">
  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">

  </head>
  <script type="text/javascript">
    $(function() {
      laypage({
        cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
        pages: "${list.pages}", //总页数
        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip: true, //是否开启跳页
        total: "${list.total}",
        startRow: "${list.startRow}",
        endRow: "${list.endRow}",
        groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
        curr: function() { //通过url获取当前页，也可以同上（pages）方式获取

          return "${list.pageNum}";
        }(),
        jump: function(e, first) { //触发分页后的回调
          if(!first) { //一定要加此判断，否则初始时会无限刷新
            $("#page").val(e.curr);
            $("#form").submit();
          }
        }
      });
    });
    
    function selectAll() {
      var checklist = document.getElementsByName("chkItem");
      var checkAll = document.getElementById("checkAll");
      if(checkAll.checked) {
        for(var i = 0; i < checklist.length; i++) {
          checklist[i].checked = true;
        }
      } else {
        for(var j = 0; j < checklist.length; j++) {
          checklist[j].checked = false;
        }
      }
    }

    function showSupplier() {
      var id = [];
      $('input[name="chkItem"]:checked').each(function() {
        id.push($(this).val());
      });

      var packages = "${packId }";
      var projectId = "${projectId }";
      window.location.href = "${pageContext.request.contextPath}/saleTender/saveSupplier.html?ids=" + id.toString() + "&packages=" + packages + "&projectId=" + projectId;

    }
    
    function check() {
      var count = 0;
      var checklist = document.getElementsByName("chkItem");
      var checkAll = document.getElementById("checkAll");
      for(var i = 0; i < checklist.length; i++) {
        if(checklist[i].checked == false) {
          checkAll.checked = false;
          break;
        }
        for(var j = 0; j < checklist.length; j++) {
          if(checklist[j].checked == true) {
            checkAll.checked = true;
            count++;
          }
        }
      }
    }

    function resetQuery() {
      $("#supplierName").val("");
    }
  </script>

  <body>
    <!--面包屑导航开始-->
    <h2 class="search_detail">
        <form action="" method="post" id="form" class="mb0">
          <ul class="demand_list">
            <input type="hidden" id="page"  name="page" />
            <li class="fl"><label class="fl">供应商名称：</label><span><input
                type="text" id="supplierName" class="" value="${supplierName}"  name="supplierName"/></span></li>
            <input type="submit" onclick="query()" class="btn" value="查询"/>
            <input type="reset" class="btn" onclick="resetQuery();" value="重置">
          </ul>
          <div class="clear"></div>
        </form>
      </h2>
    <div class="tl">
      <button class="btn btn-windows save" onclick="showSupplier()">保存</button>
      <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
    </div>

    <input type="hidden" name="packages" value="${packId }" />
    <input type="hidden" name="projectId" value="${projectId }" />

    <table class="table table-bordered table-condensed">
      <thead>
        <tr>
          <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
          <th class="info">供应商名称</th>
          <th class="info">联系人姓名</th>
          <th class="info">联系人电话</th>
          <th class="info">注册地址</th>
        </tr>
      </thead>
      <c:forEach items="${list.list}" var="ext" varStatus="vs">
        <tr>
          <td class="tc opinter"><input onclick="check()" type="checkbox" name="chkItem" value="${ext.id}" /></td>

          <td class="tc opinter">${ext.supplierName }</td>

          <td class="tc opinter">${ext.contactName}</td>

          <td class="tc opinter">${ext.contactTelephone}</td>

          <td class="tc opinter">${ext.addressName}</td>
        </tr>
      </c:forEach>
    </table>
    <div id="pagediv" align="right"></div>
  </body>

</html>