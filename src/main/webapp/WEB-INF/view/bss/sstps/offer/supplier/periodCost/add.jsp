<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>添加</title>

    <script type="text/javascript">
      function down() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/periodCost/select.html?proId=" + proId;
      }
    </script>

  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">供应商报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">期间费用明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <form action="${pageContext.request.contextPath}/periodCost/save.html" method="post">

        <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>

        <div>
          <h2 class="f16 count_flow mt40">费用明细</h2>
          <ul class="ul_list mb20">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>项目名称：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input type="text" id="projectName" name="projectName" value="${pc.projectName }">
                <div class="cue">${ERR_projectName}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">报价前2年：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input type="text" id="tyaQuoteprice" name="tyaQuoteprice" value="${pc.tyaQuoteprice }">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">报价前1年：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="oyaQuoteprice" name="oyaQuoteprice" type="text" value="${pc.oyaQuoteprice }">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">报价当年：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="newQuoteprice" name="newQuoteprice" type="text" value="${pc.newQuoteprice }">
              </div>
            </li>
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0">
                <textarea class="col-md-12 col-sm-12 col-xs-12 h80" id="remark" name="remark" title="不超过250个字" placeholder="不超过250个字">${pc.remark }</textarea>
              </div>
            </li>
          </ul>
        </div>

        <div class="col-md-12 col-sm-12 col-xs-12 mt20 mb50 tc">
          <button class="btn btn-windows save" type="submit">确定</button>
          <button class="btn btn-windows cancel" type="button" onclick="down()">取消</button>
        </div>

      </form>
    </div>

  </body>

</html>