<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <base href="${pageContext.request.contextPath}/">

    <title>确定中标供应商</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

  </head>
  <script type="text/javascript">
    /** 全选全不选 */
    function selectAll() {
      var checklist = document.getElementsByName("chkItem");
      var checkAll = document.getElementById("checkAll");
      if(checkAll.checked) {
        for(var i = 0; i < checklist.length; i++) {
          checklist[i].checked = true;
		      var associate = document.getElementsByName("associate"+checklist[i].value);
		      for(var k=0;k<associate.length;k++){
		    	  associate[k].checked = true;
		      }
		    	  
		      }
		          
      } else {
        for(var j = 0; j < checklist.length; j++) {
          checklist[j].checked = false;
          var associate = document.getElementsByName("associate"+checklist[j].value);
          for(var k=0;k<associate.length;k++){
        	  associate[k].checked = false;
            }
        }
      }
      
      ratio();
    }

    /** 单选 */
    function check(index){
         var count=0;
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         for(var i=0;i<checklist.length;i++){
               if(checklist[i].checked == false){
            	    
                   checkAll.checked = false;
                   break;
               }
               for(var j=0;j<checklist.length;j++){
                     if(checklist[j].checked == true){
                           checkAll.checked = true;
                           count++;
                       }
                
                 }
           }
         
         var associate = document.getElementsByName("associate"+index);
         for(var i=0;i<associate.length;i++){
        	 if($("#rela"+index).prop("checked")){
        		 $(associate[i]).prop("checked","checked");
        	 }else{
        		 $(associate[i]).prop("checked",false);
        	 }
        	 
         }
         
         ratio(index);
       
    }
    
    function ratio(index){
   
    	var checklist = document.getElementsByName ("chkItem");
    	 for(var j = 0; j < checklist.length; j++) {
             $("#"+checklist[j].value).find("#priceRatio").text("");
             $("#"+checklist[j].value).find("#wonPrice").text("");
             $("#"+checklist[j].value).find("#singQuote").text("");
             
           }
    	
    	   var lengths=$("input[name='chkItem']:checked").length;
         if(lengths != 0){
    	   var id = [];
           var ratio = [];
            if(lengths == 1){
              ratio.push("100");
            }else if(lengths == 2){
                ratio.push("70");
                ratio.push("30");
            }else if(lengths == 3){
                ratio.push("50");
                ratio.push("30");
                ratio.push("20");
            }else if(lengths == 4){
                ratio.push("40");
                ratio.push("30");
                ratio.push("20");
                ratio.push("10");
            }
            var i=0;
            

            
            //算出实际成交金额
            $('input[name="chkItem"]:checked').each(function() {
            	   id.push($(this).val());
                $("#"+$(this).val()).find("#priceRatio").text(ratio[i]);
//                var totalprice = $("#"+id[0]).find("#totalPrice").text();
               var price = 0;
                $('input[name="associate'+$(this).val()+'"]:checked').each(function() {
//                  id.push($(this).val());
                 var quote =  $("#"+$(this).val()).find("#Quotedamount").text();
                 
                 var count =  $("#"+$(this).val()).find("#purchaseCount").text();
                 price =parseFloat(price) + toDecimal((ratio[i]/100)*count*quote);
//                  alert(quote);     
//                  alert(count);
                 
              });
                $("#"+$(this).val()).find("#singQuote").text(price);
               
                i++;
              });
            
         } 
    }
    
  //保留两位小数  
    //功能：将浮点数四舍五入，取小数点后2位 
    function toDecimal(x) { 
      var f = parseFloat(x); 
      if (isNaN(f)) { 
        return; 
      } 
      f = Math.round(x*100)/100; 
      return f; 
    } 
  


      function save() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length >= 1) {
        	 layer.confirm('确定后将不可修改,是否确定', {title:'提示',offset: ['100px','300px'],shade:0.01}, function(index){
        		   var json='${supplierCheckPassJosn}';
        		   layer.close(index);
                   $.ajax({
                         type: "post",
                         url:"${pageContext.request.contextPath}/winningSupplier/comparison.do",
                         data:"checkPassId="+id+"&&jsonCheckPass="+json,
                         dataType:"json",
                         success: function(data) {
//                           alert(data);
                           if(data=="SCCUESS"){
                               window.location.href = '${pageContext.request.contextPath}/winningSupplier/selectSupplier.do?projectId=${projectId}&&flowDefineId=${flowDefineId}';
                           }else{
                               var iframeWin;
                               var type=0;
                                   layer.open({
                                         type: 2,
                                         title: '上传',
                                         shadeClose: false,
                                         shade: 0.01,
                                         area: ['367px', '180px'], //宽高
                                         content: '${pageContext.request.contextPath}/winningSupplier/upload.html?packageId=${packageId}&&flowDefineId=${flowDefineId}&&projectId=${projectId}&&checkPassId='+id,
                                         success: function(layero, index){
                                             iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                                           },
                                         btn: ['保存', '关闭'] 
                                             ,yes: function(){
                                                iframeWin.upload();
                                                type=1;
                                             }
                                             ,btn2: function(){
                                            	 delFileAjax();
                                             },
                                          end:function(){
                                        	  if(type!=1){
                                        		  delFileAjax();
                                        	  }
                                          }
                                       });
                                 }
                             }     
                     });
             });
      
        } else {
          layer.alert("请选择供应商", {
            offset: ['100px', '390px'],
            shade: 0.01
          });
        }
      }
      
      /**ajax 删除文件 **/
      function delFileAjax(){
    	  $.ajax({
              type: "POST",
              dataType : "json",
              url:'${pageContext.request.contextPath}/winningSupplier/deleFile.do?packageId=${packageId}',
              success: function(data) {
                  var map =data;
                  alert(map);
                  if(map=="SCCUESS"){
                     window.location.href = '${pageContext.request.contextPath}/winningSupplier/selectSupplier.do?projectId=${projectId}&&flowDefineId=${flowDefineId}';
                  }else{
                    layer.msg("请上传");
                  }
                  
              }
          });
      }
      
      /** 中标供应商 */
      function tabone(){
        window.location.href="${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}";
      }
      
      /** 中标通知 */
      function tabtwo(){
        var error = "${error}";
        if(error != null && error == "ERROR"){
          layer.alert("请选择中标供应商",{offset: ['100', '300px'], shade:0.01});
        }else{
          window.location.href="${pageContext.request.contextPath}/winningSupplier/template.do?projectId=${projectId}";
        }
        
        
      }
      
      /** 未中标通知 */
      function tabthree(){
        var error = "${error}";
        if (error != null && error == "ERROR" ){
          layer.alert("请选择中标供应商",{offset: ['100', '300px'], shade:0.01});
        } else{
            window.location.href="${pageContext.request.contextPath}/winningSupplier/notTemplate.do?projectId=${projectId}";  
        }
      }
      
      //点击中标供应商隐藏显示所属明细
      function ycDiv(obj,index) {
    	  //var bfb = parseFloat($(obj).parent().parent().find("td:eq(7)").text())/100;
    	  if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
              $(obj).removeClass("shrink");
              $(obj).addClass("spread");
            } else {
              if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
                $(obj).removeClass("spread");
                $(obj).addClass("shrink");
              }
            }
	    	  	if ($(obj).parent().parent().next().hasClass("hide")) {
		    		  $(obj).parent().parent().next().removeClass("hide");
			      } else {
			    	  $(obj).parent().parent().next().addClass("hide");
			      }
		    	  var detail = document.getElementsByName("detail"+index);
		    	  for(var i=0;i<detail.length;i++){
		    		  if($(detail[i]).hasClass("hide")){
		    			  $(detail[i]).removeClass("hide");
				      } else {
				    	  $(detail[i]).addClass("hide");
				      }
		    	  }
      }
      
      //关联选中
      function associateSelected(id,obj,index){
    	  var associate = document.getElementsByName("associate"+index);
    	  for(var i=0;i<associate.length;i++){
    		  if(associate[i].checked){
    			  $("#rela"+index).prop("checked","checked");
    			  break;
    		  }else if(i==associate.length-1){
    			  $("#rela"+index).prop("checked",false);
    		  }
    	  }
    	  var count = 0;
        var checklist = document.getElementsByName ("chkItem");
        var checkAll = document.getElementById("checkAll");
        for(var i=0;i<checklist.length;i++){
           if(checklist[i].checked == false){
               checkAll.checked = false;
               break;
           }
           for(var j=0;j<checklist.length;j++){
               if(checklist[j].checked == true){
                  checkAll.checked = true;
                  count++;
               }
           }
        }
        ratio();
        $.ajax({
					type: "POST",
					dataType: "json",
					async: false, //请求是否异步，默认为异步
					url: "${pageContext.request.contextPath }/project/findDetailById.do?id=" + id,
					success: function(data) {
						var purchaseCount = data.purchaseCount;
						var bfb = parseFloat($("#rela"+index).parent().parent().find("td:eq(7)").text())/100;
						if($("#rela"+index).prop("checked")){
							if("${quote}"==0){
								$(obj).parent().parent().find("td:eq(7)").text("");
								$(obj).parent().parent().find("td:eq(8)").text("");
							}else{
								var price = $(obj).parent().parent().find("td:eq(8)").text();
								$(obj).parent().parent().find("td:eq(7)").text(parseFloat(bfb*purchaseCount));
								$(obj).parent().parent().find("td:eq(9)").text(parseFloat(bfb*purchaseCount*price)/10000);
							}
						}else{
							$(obj).parent().parent().find("td:eq(7)").text("");
							$(obj).parent().parent().find("td:eq(9)").text("");
						}
						if("${quote}"==1){
							var aociate = document.getElementsByName("associate"+index);
							var realPrice = 0;
							for(var i=0;i<aociate.length;i++){
								var detailPrice = $(aociate[i]).parent().parent().find("td:eq(9)").text();
								if(detailPrice!=""){
									realPrice = realPrice + parseFloat(detailPrice);
								}
							}
							if(realPrice==0){
								$("#singQuote"+index).text("");
							}else{
								$("#singQuote"+index).text(realPrice);
							}
						}else{
							$(obj).parent().parent().find("td:eq(9)").text($("#wonPrice"+index).text());
						}
					}
				});
      }
  </script>

  <body>

      <h2 class="list_title mb0 clear">确认中标供应商</h2>

      <c:if test="${view != 1 }">
        <div class="col-md-12 col-xs-12 col-sm-12 mt10 p0">
          <button class="btn " onclick="save();" type="button">确定</button>
        </div>
      </c:if>
      <div class="content table_box pl0">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="w200">供应商名称</th>
<!--               <th class="w100">参加时间</th> -->
              <th style="width: 110px;">&nbsp;总报价&nbsp;（万元）</th>
              <th style="width: 50px;">总得分</th>
              <th style="width: 20px;">排名</th>
              <c:if test="${view == 1}">
                <th style="width: 50px;">中标状态</th>
              </c:if>
              <th class="w50">占比（%）</th>
              <th class="w100">实际成交总价（万元）</th>
<!--               <th style="width: 80px;">中标金额（万元）</th> -->
            </tr>
          </thead>
          <c:forEach items="${supplierCheckPass}" var="checkpass" varStatus="vs">
            <tr id="${checkpass.id}">
              <td class="tc opinter"><input onclick="check('${checkpass.id}');" id="rela${checkpass.id}" type="checkbox" name="chkItem" value="${checkpass.id}" /></td>
              <td class="opinter" title="${checkpass.supplier.supplierName }">
               <span onclick="ycDiv(this,'${checkpass.id}')" class="count_flow shrink hand"></span>
                <c:choose>
                 <c:when test="${fn:length(checkpass.supplier.supplierName) >10}">
                    ${fn:substring(checkpass.supplier.supplierName , 0, 10)}...
                 </c:when>
                 <c:otherwise>
                  ${checkpass.supplier.supplierName}
                 </c:otherwise>
                 </c:choose>
               </td>
<!--               <td class="tc opinter" onclick=""> -->
<%--                 <fmt:formatDate value='${checkpass.joinTime}' pattern="yyyy-MM-dd " /> --%>
<!--               </td> -->
              <td class="tc opinter" id="totalPrice" onclick="">${checkpass.totalPrice}</td>
              <td class="tc opinter" onclick="">${checkpass.totalScore}</td>
              <td class="tc opinter" onclick="">${(vs.index+1)}</td>
                <c:if test="${view == 1}">
               <c:if test="${checkpass.isWonBid != 1}">
                <td class="tc opinter">未中标</td>
               </c:if>
               <c:if test="${checkpass.isWonBid == 1}">
                <td class="tc opinter">已中标</td>
               </c:if>
               </c:if>
               <td class="tc opinter" id="priceRatio">${checkpass.priceRatio}</td>
               <td class="tc opinter" id="singQuote">
               		<c:if test="${quote==0 }">
               			<input type="text" name="" id=""/>
               		</c:if>
               		<c:if test="${quote==1 }">
               		
               		</c:if>
               </td>
<%--                <td class="tc opinter" id="wonPrice${vs.index }">${checkpass.wonPrice }</td> --%>
            </tr>
              <tr class="tc hide" >
                <td colspan="10">
	                <table class="table table-bordered table-condensed table-hover table-striped">
	                <tr class="tc ">
	                 <th class="hide"></th>
			              <th class="w30">序号</th>
			              <th class="150">物资名称</th>
			              <th>规格型号</th>
			              <th>质量技术标准</th>
			              <th>计量单位</th>
			              <th>采购数量</th>
			              <th>单价（元）</th>
			              <th>报价（万元）</th>
			             </tr>
	                <c:forEach items="${detailList }" var="detail" varStatus="p">
	                  <tr name="detail${checkpass.id}" id="${detail.id}" class="tc hide">
	                    <td class="hide"><input type="checkbox" value="${detail.id}" onclick="associateSelected('${detail.id}',this,${checkpass.id})" name="associate${checkpass.id}"/></td>
	                    <td>${detail.serialNumber }</td>
	                    <td title="${detail.goodsName}">
	                       <c:choose>
                       <c:when test="${fn:length(detail.goodsName ) > 20}">
                           ${fn:substring(detail.goodsName  , 0, 20)}......
                       </c:when>
                       <c:otherwise>
                                ${detail.goodsName }
                        </c:otherwise>
                       </c:choose>
	                    </td>
	                    <td class="w150" title=" ${detail.stand }">
	                     <c:choose>
                       <c:when test="${fn:length(detail.stand) > 20}">
                           ${fn:substring(detail.stand , 0, 20)}......
                       </c:when>
                       <c:otherwise>
                           ${detail.stand }
                        </c:otherwise>
                       </c:choose>
	                    </td>
	                    <td title="${detail.qualitStand }">
	                     <c:choose>
                       <c:when test="${fn:length(detail.qualitStand ) > 20}">
                           ${fn:substring(detail.qualitStand , 0, 20)}......
                       </c:when>
                       <c:otherwise>
                            ${detail.qualitStand }
                        </c:otherwise>
                       </c:choose>
	                     </td>
	                    <td>${detail.item }</td>
	                    <c:if test="${quote==0 }">
	                      <td>${checkpass.wonPrice }</td>
	                    </c:if>
	                      <td id="purchaseCount">${detail.purchaseCount}</td>
	                      <td>${detail.price }</td>
	                      <td id="Quotedamount">${detail.price }</td>
	                  </tr>
	              </c:forEach>
	            </table>
            </td>
         </tr>
         </c:forEach>
         
         </table>
           <div class="col-md-12 tc">
              <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
            </div>
      </div>
  </body>

</html>