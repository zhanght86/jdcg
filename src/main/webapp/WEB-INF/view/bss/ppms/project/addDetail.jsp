<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+"";
%>
<!DOCTYPE HTML>
<html>

  <head>
  	<%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      /** 全选全不选 */
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

      function check(ele) {
        var flag = $(ele).prop("checked");
        var purchaseType = $("input[name='chkItem']:checked").parents("tr").find("td").eq(10).text();
        purchaseType = $.trim(purchaseType);
        var goodUse = $("input[name='chkItem']:checked").parents("tr").find("td").eq(13).text();
        goodUse = $.trim(goodUse);
        if(!goodUse) {
          goodUse = null;
        }
        if(!purchaseType) {
          purchaseType = null;
        }
        var id = $(ele).val();
        $.ajax({
          url: "${pageContext.request.contextPath}/project/checkDeail.html",
          data: "id=" + id,
          type: "post",
          dataType: "json",
          success: function(result) {
            for(var i = 0; i < result.length; i++) {
              $("input[name='chkItem']").each(function() {
                var v1 = result[i].id;
                var v3 = result[i].purchaseType;
                if(v3 == purchaseType) {
                  var v2 = $(this).val();
                  if(v1 == v2) {
                    $(this).prop("checked", flag);
                  }
                } else {
                  layer.alert("采购方式不相同", {
                    offset: ['222px', '390px'],
                    shade: 0.01
                  });
                }
              });
            }
            $("input[name='chkItem']:checked").each(function() {
              var currpurchaseType = $(this).parents("tr").find("td").eq(10).text();
              currpurchaseType = $.trim(currpurchaseType);
              if(!currpurchaseType) {
                currpurchaseType = null;
              }
              if(currpurchaseType != purchaseType) {
                $(this).prop("checked", false);
                layer.alert("采购方式不相同", {
                  offset: ['222px', '390px'],
                  shade: 0.01
                });
              }
            });
            $("input[name='chkItem']:checked").each(function() {
              var currGoodUse = $(this).parents("tr").find("td").eq(13).text();
              currGoodUse = $.trim(currGoodUse);
              if(!currGoodUse) {
                currGoodUse = null;
              }
              if(currGoodUse != goodUse) {
                $(this).prop("checked", false);
                layer.alert("进口方式不相同", {
                  offset: ['222px', '390px'],
                  shade: 0.01
                });
              }
            });
          },
          error: function() {
            layer.msg("失败", {
              offset: ['222px', '390px']
            });
          }
        });
      }

      function save() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length > 0) {
          $("#detail_id").val(id);
          $("#save_form_id").submit();
        }
      }

      function cancel() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="#"> 首页</a>
          </li>
          <li>
            <a href="#">保障作业</a>
          </li>
          <li>
            <a href="#">项目管理</a>
          </li>
          <li class="active">
            <a href="#">选择明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <!-- 录入采购计划开始-->
    <div class="container">
      <!-- 项目戳开始 -->
      <div class="clear"></div>
      <div class="col-md-12 tab-pane active">
        <h1 class="f16 count_flow">
      <i>01</i>
                        请填写信息
      </h1>
      </div>
      <%
            session.setAttribute("tokenSession", tokenValue);
         %>
      <input type="hidden" name="token2" value="<%=tokenValue%>">
      <span class="f14 fl"><i class="spredm">*</i>&nbsp;项目名称：</span>
      <div class="fl">
        <input id="pic" type="text" class="toinline" name="name" /> &nbsp;&nbsp;
      </div>

      <span class="f14 fl"><i class="spredm">*</i>&nbsp;项目编号：</span>
      <div class="fl">
        <input id="pc" type="text" class="toinline" name="projectNumber" />
      </div>

      <div class="col-md-12 tab-pane active">
        <h1 class="f16 count_flow">
      <i>02</i>
                        选择采购明细
      </h1>
      </div>

      <div class="container">
        <div class="col-md-8">
          <button class="btn btn-windows save" type="button" onclick="save();">确定</button>
          <button class="btn btn-windows back" type="button" onclick="location.href='javascript:history.go(-1);'">返回</button>
        </div>
      </div>
      <div class="container margin-top-5">
        <div class="content padding-left-25 padding-right-25 padding-top-5">
          <table class="table table-striped table-bordered table-hover">
            <thead>
              <tr>
                <th class="info w50">序号</th>
                <th class="info">需求部门</th>
                <th class="info">物资名称</th>
                <th class="info">规格型号</th>
                <th class="info">质量技术标准</th>
                <th class="info">计量单位</th>
                <th class="info">采购数量</th>
                <th class="info">单价（元）</th>
                <th class="info">预算金额（万元）</th>
                <th class="info">交货期限</th>
                <th class="info">采购方式建议</th>
                <th class="info">供应商名称</th>
                <th class="info">是否申请办理免税</th>
                <th class="info">物资用途（进口）</th>
                <th class="info">使用单位（进口）</th>
                <th class="info">备注</th>
                <th class="info w30">操作</th>
              </tr>
            </thead>
            <tbody id="tbody_id">
              <c:forEach items="${info}" var="obj" varStatus="vs">
                <tr style="cursor: pointer;">
                  <td class="tc w50">${obj.seq}</td>
                  <td class="tc">${obj.department}</td>
                  <td class="tc">${obj.goodsName}</td>
                  <td class="tc">${obj.stand}</td>
                  <td class="tc">${obj.qualitStand}</td>
                  <td class="tc">${obj.item}</td>
                  <td class="tc">${obj.purchaseCount}</td>
                  <td class="tc">${obj.price}</td>
                  <td class="tc">${obj.budget}</td>
                  <td class="tc">${obj.deliverDate}</td>
                  <td class="tc"> ${obj.purchaseType}</td>
                  <td class="tc">${obj.supplier}</td>
                  <td class="tc">${obj.isFreeTax}</td>
                  <td class="tc">${obj.goodsUse}</td>
                  <td class="tc">${obj.useUnit}</td>
                  <td class="tc">${obj.memo}</td>
                  <td class="tc w30">
                    <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check(this)" alt="">
                  </td>
                </tr>

              </c:forEach>
            </tbody>

          </table>

          <div id="pagediv" align="right"></div>
        </div>
      </div>
    </div>

    <form id="save_form_id" action="${pageContext.request.contextPath}/project/create.html" method="post">
      <input id="detail_id" name="id" type="hidden" />
    </form>
  </body>

</html>