<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    <base href="${pageContext.request.contextPath}/">

    <title>抽取条件</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">

    <script type="text/javascript">
    $(function (){
    	 selectLikeExpert();
    var cate="${listCon.conTypes[0].categoryName}";
    if (cate != null && cate != ''){
    	 $("#dnone").removeClass("dnone");
    }else{
        $("#dnone").addClass("dnone");
    }
    var json = '${extConTypeJson}';
    var extConType = $.parseJSON(json);
   for(var i= 0 ; i < extConType.length;i++){
	  if(extConType[i].expertsType != null){
	   if(extConType[i].expertsType.code == 'GOODS_PROJECT'){
		   $("#goodsProjectCount").removeClass("dnone");
		   $("#goodsProject").val(extConType[i].expertsCount);
	  }
	  if(extConType[i].expertsType.code == 'GOODS_SERVER'){
		  $("#goodsServerCount").removeClass("dnone");
		  $("#goodsServer").val(extConType[i].expertsCount);
	  }
	  if(extConType[i].expertsType.code == 'GOODS'){
		  $("#goodsCount").removeClass("dnone");
		  $("#goods").val(extConType[i].expertsCount);
	  }
	  if(extConType[i].expertsType.code == 'SERVICE'){
		   $("#serviceCount").removeClass("dnone");
		   $("#service").val(extConType[i].expertsCount);
	  }
	  if(extConType[i].expertsType.code == 'PROJECT'){
		   $("#projectCount").removeClass("dnone");
		   $("#project").val(extConType[i].expertsCount);
	  }
	  }
   }
  
    });
    

      //专家类型级联
      function cascade(sl) {
        if($(sl).val() == 1) {
          $("#dnone").css("display", "block");

        } else {
          $("#dnone").css("display", "none");
          $("#supplierTypeIds").attr("value", "");
          $("#supplierType").attr("value", "");
        }

      }
      /**年龄*/
      function age(){
    	 var min = $("#ageMinC").val();
    	 var max = $("#ageMaxC").val(); 
    	 if(min != null && min != '' && max != null && max != '' ){
    		 
    		 selectLikeExpert();
    	 }
      } 
      
      /**暂存*/
      function temporary(){
    	  window.location.href="${pageContext.request.contextPath}/ExpExtract/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}";
      }
      
      function selectLikeExpert(){
    	    var v = document.getElementById("city").value;    
    	     $("#address").val(v);
    	     var area = document.getElementById("area").value; 
    	     $("#province").val(area);
    	     $.ajax({
    	         cache: true,
    	         type: "POST",
    	         dataType : "json",
    	         url:'${pageContext.request.contextPath}/ExtCondition/selectLikeExpert.do',
    	         data:$('#form1').serialize(),// 你的formid
    	         async: false,
    	         success: function(data) {
    	          $("#count").text(data);
    	         }
    	     });
    	     
    	return false;
    	}

      

      //专家地址
       function areas(){
      var areas=$("#area").find("option:selected").val();
      $.ajax({
          type:"POST",
          url:"${pageContext.request.contextPath}/SupplierExtracts/city.do",
          data:{area:areas},
          dataType:"json",
          success: function(data){
               var list = data;
               $("#city").empty();
               var  html="";
               var areas=$("#area").find("option:selected").text();
               if(areas == '全国'){
            	   html="<option value=''>所有省市</option>";
               }else{
            	   layer.prompt({
                       formType: 2,
                       shade:0.01,
                       title: '限制地区原因',
                       btn: ['确定','取消'],
                      cancel:function(index,layero){
                    	  $("#area option:first").prop("selected", 'selected');
                         $("#city").empty();
                         $("#city").append("<option value=''>所有省市</option>");
                         selectLikeExpert();
                         layer.close(index);
                     }
                    },function (value, ix, elem){
                        $("#addressReason").val(value);
                        selectLikeExpert();
                         layer.close(ix);
                    });
            	   html="<option value=''>所有市</option>";
            	  
               }
               for(var i=0;i<list.length;i++){
            	  
                 html +="<option value="+list[i].id+">"+list[i].name+"</option>";
               }
               $("#city").append(html);
               selectLikeExpert();
          }
      });
    }

  
      //ajax提交表单
      function cityt() {
    	 var  eCount =$("#eCount").val();
    	 if(eCount != null && eCount != '' ){
    	
    	/*   var iframeWin;
    	  
    	  var typeCode = $("#expertsTypeCode").val();
    	     var addressReson = $("#addressReson").val();
    	     if(typeCode == '' && addressReson == '' ){
    	    	 fax();
    	     }else{
		    	  layer.open({
		              type: 2,
		              title: "选择",
		              shadeClose: true,
		              shade: 0.01,
		              offset: '20px',
		              area: ['90%', '70%'],
		              content: '${pageContext.request.contextPath}/ExpExtract/reasonnumber.do?expertsTypeCode='+$("#expertsTypeCode").val()+'&&addressReson='+$("#city option:selected").val()+ $("#area option:selected").val()+'&&eCount='+$("#eCount").val(),
		              success: function(layero, index) {
		                iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		              },
		              btn: ['保存', '关闭'],
		              yes: function() {
		                iframeWin.save();
		                var type=$("#hiddentype").val();
		                if(type != null && type != '' && type == '1'){
		                	fax();     	
		                    layer.closeAll();
		                }
		                
		           
		              },
		              btn2: function() {
		                  layer.closeAll();
		                } //iframe的url
		            });
    

       
    	     } */
    	     
    		    var count=   $("#sunCount").val();
    		    if(parseInt(count) > parseInt(eCount)){
    		      layer.msg("数量不能大于总数量");
    		    }else if(parseInt(count) < parseInt(eCount)){
    		      layer.msg("数量不能小于总数量");
    		    }else{
    	 	 fax();
    		    	
    		    }
    	     
         }else{
        	 $("#expertsCountError").text("不能为空");
         }
    	     return false;
      }
      
      /**点击抽取按钮*/
      function fax(){
    	   $.ajax({
               type: "POST",
               url: "${pageContext.request.contextPath}/ExpExtract/isFinish.do",
               data: {packageId:"${packageId}"},
               dataType: "json",
               success: function(data){
                 if(data=="SUCCESS"){
                     layer.confirm('是否完成本次抽取？', {
                            btn: ['确定','取消'],offset: ['40%', '40%'], shade:0.01
                          }, function(index){
                            ext();
                            layer.close(index);
                          }, function(index){
                            layer.close(index);
                          });
                   } else {
                     ext();
                   }
                 }
              
         });
    	  
      }
      
      function ext(){
    	  $("#tbody").empty();
          var v = document.getElementById("city").value;    
          $("#address").val(v);
          var area = document.getElementById("area").value; 
          $("#province").val(area);
         $("#addressStr").val($("#area").find("option:selected").text()+$("#city").find("option:selected").text());
            $.ajax({
              cache: true,
              type: "POST",
              dataType: "json",
              url: '${pageContext.request.contextPath}/ExtCondition/saveExtCondition.html',
              data: $('#form1').serialize(), // 你的formid
              async: false,
              success: function(data) {
                $("#tenderTime").text("");
                $("#responseTime").text("");
                $("#agediv").text("");
                $("#supervisediv").text("");
                $("#typeArray").text("");
                $("#expertsCountError").text("");
                var map = data;
                $("#tenderTime").text(map.tenderTime);
                $("#responseTime").text(map.responseTime);
                $("#agediv").text(map.age);
                $("#supervisediv").text(map.supervise);
                $("#typeArray").text(map.typeArray);
                $("#expertsCountError").text(map.expertsCountError);
                if(map.sccuess == "sccuess") {
                   var list=map.extRelateListYes;
                     var noList=map.extRelateListNo;
                     var extConType=map.extConType;
                   var tex="";
                   if (list != null && list.length !=0){
                	   isEmpty = 1;
                        var k=0;
                   for(var i=0;i<list.length;i++){
                	       k=i;
                       if(list[i]!=null){
                        if(list[0]!=null){
                          var html="";
                          $("#extcontype").empty();
                          for(var l=0;l<extConType.length;l++){
                              html+="";
                               if(extConType[l].expertsType != null && extConType[l].expertsType != ''){
                                  if(extConType[l].expertsType.kind == 6){
                                     html+="专家类别："+extConType[l].expertsType.name+"技术";
                                  }else{
                                      html+="专家类别："+extConType[l].expertsType.name;
                                  }
                                }
                                 
                                 html+="&nbsp;&nbsp;&nbsp;抽取数量:"+extConType[l].alreadyCount+"/"+extConType[l].expertsCount;
                                html+="<br/>";
                              }
                          $("#extcontype").append(html);
                        } 
                       tex+="<tr class='cursor'>"+
                           "<td class='tc' onclick='show();'>"+(i+1)+"</td>"+
                           "<td class='tc' onclick='show();'>*****</td>"+
                           "<td class='tc' onclick='show();'>"+list[i].expert.mobile+"</td>";
			                       tex+="<td class='tc'>";
			                       var ddl ='${ddListJson}';
			                       var listData=$.parseJSON(ddl);//解析json字符串
			                        var st='';
			                         var split = list[i].expert.expertsTypeId.split(",");
			                         for(var c=0; c<split.length;c++){
			                           for(var b=0;b<listData.length;b++){
			                             if(split[c] == listData[b].id){
			                               st+=listData[b].name+",";
			                             }
			                           }
			                         }
			                         tex+=st.substring(0, st.length-1);
			                         tex+="</td>";

                           tex+="<td class='tc' onclick='show();'>*****</td>"+
                           "<td class='tc' onclick='show();'>*****</td>"+
                       " <td class='tc' >"+
                         "<select id='select' onchange='operation(this);'>";
                         
                          if(list[i].operatingType==1){
                              tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1' selected='selected' disabled='disabled'>能参加</option>";
                          }else if(list[i].operatingType==2){
                              tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1'>能参加</option>"+
                              "<option value='"+list[i].id+","+list[i].expertConditionId+",3'>不能参加</option>"+
                              "<option selected='selected' value='"+list[i].id+","+list[i].expertConditionId+",2'>待定</option>";
                          }else if(list[i].operatingType==3){
                              tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1' selected='selected' disabled='disabled'>不能参加</option>";
                          }else{
                              tex+= "<option >请选择</option>"+
                                  "<option value='"+list[i].id+","+list[i].expertConditionId+",1'>能参加</option>"+
                              "<option value='"+list[i].id+","+list[i].expertConditionId+",3'>不能参加</option>"+
                              "<option  value='"+list[i].id+","+list[i].expertConditionId+",2'>待定</option>";
                          }
                          tex+="</select>"+
                        "</td>"+
                   "</tr>";
                   }
                   }
                   for(var i=0;i<noList.length;i++){
                       
                       tex+="<tr class='cursor'>"+
                             "<td class='tc' onclick='show();'>"+((i+1)+(k+1))+"</td>"+
                             "<td class='tc' onclick='show();'>*****</td>"+
                             "<td class='tc' onclick='show();'>*****</td>"+
                             "<td class='tc' onclick='show();'>*****</td>"+
                             "<td class='tc' onclick='show();'>*****</td>"+
                             "<td class='tc' onclick='show();'>*****</td>"+
                             "<td class='tc'>请选择</td>"+
                           "</tr>";
                         
                     }
                     $("#tbody").append(tex);
                }else{
                    layer.alert("本条件没有查询结果!",{offset: ['222px', '390px'], shade:0.01});
                }
                   
                }
              }

            });
          
      }
      
      /**完成**/
      function finish(){
    	  
    	  layer.confirm('是否需要打印', {
               btn: ['打印','取消'],offset: ['40%', '40%'], shade:0.01
             }, function(index){
            
                window.location.href="${pageContext.request.contextPath}/ExpExtract/showRecord.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
             }, function(index){
            	 window.location.href="${pageContext.request.contextPath}/ExpExtract/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
              
             }); 
       
    	  
    	  
    /*    $.ajax({
               type: "POST",
               url: "${pageContext.request.contextPath}/ExpExtract/isFinish.do",
               data: {packageId:"${packageId}"},
               dataType: "json",
               success: function(data){
            	   if(data=="SUCCESS"){
            		     layer.confirm('是否完成本次抽取？', {
            	              btn: ['确定','取消'],offset: ['40%', '40%'], shade:0.01
            	            }, function(index){
            	               window.location.href="${pageContext.request.contextPath}/ExpExtract/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
            	            }, function(index){
            	              layer.close(index);
            	            });
            	          }else{
            	        	  window.location.href="${pageContext.request.contextPath}/ExpExtract/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}"; 
            	          }
            	   }
              
    	   });  */
    	  
      }
      
      function operation(select){
    	   var x,y;
    	  var oRect =select.getBoundingClientRect();
    	   x=oRect.left -450;
     	  y=oRect.top -150;
          layer.confirm('确定本次操作吗？', {
            btn: ['确定','取消'],offset: [y,x], shade:0.01
          }, function(ix){
            var strs= new Array();
            var v=select.value;
             strs=v.split(",");
             layer.close(ix);
            if(strs[2]=="3"){
              layer.prompt({
                  formType: 2,
                  shade:0.01,
                  offset: [y, x],
                  title: '不参加理由'
                }, function(value, ix, elem){
                     ajaxs(select.value,value);
                     layer.close(ix);
                },function(value, ix, elem){
                  select.options[0].selected = true;
                  layer.close(ix);
                });
            }else{
            select.disabled=true;
               ajaxs(select.value,'');
            }
          }, function(ix){
        	  select.options[0].selected = true;
            layer.close(ix);
            
          });
        }
        
       function ajaxs(id,v){
         $.ajax({
               type: "POST",
               url: "${pageContext.request.contextPath}/ExpExtract/resultextract.do",
               data: {id:id,reason:v,packageId:"${packageId}"},
               dataType: "json",
               success: function(data){
                           var list=data;
                           var strTypes = 0;
                           if('sccuess'==list){
                           }else{
                           var tex='';
                           for(var i=0;i<list.length;i++){
                               if(list[i]!=null){
                          
                                if(list[0]!=null){
                                  var html="";
                                  $("#extcontype").empty();
                                  for(var l=0;l<list[0].conType.length;l++){
                                  html+="";
                                     if(list[0].conType[l].expertsType != null && list[0].conType[l].expertsType != ''){
                                    	 if(list[0].conType[l].expertsType.kind == 6){
                                    		  html+= "专家类别："+list[0].conType[l].expertsType.name+"技术";
                                    	 }else{
                                    		   html+= "专家类别："+list[0].conType[l].expertsType.name;
                                    	 }
                                     }
                                     html+="&nbsp;&nbsp;&nbsp;抽取数量:"+list[0].conType[l].alreadyCount+"/"+list[0].conType[l].expertsCount;
                                    html+="<br/>";
                                  }
                                  $("#extcontype").append(html);
                                } 
                                tex+="<tr class='cursor'>"+
                                   "<td class='tc' onclick='show();'>"+(i+1)+"</td>"+
                                   "<td class='tc' onclick='show();'>*****</td>"+
                                   "<td class='tc' onclick='show();'>"+list[i].expert.mobile+"</td>";
		                                tex+="<td class='tc'>";
		                                var ddl ='${ddListJson}';
		                                var listData=$.parseJSON(ddl);//解析json字符串
		                                 var st='';
		                                  var split = list[i].expert.expertsTypeId.split(",");
		                                  for(var c=0; c<split.length;c++){
		                                    for(var b=0;b<listData.length;b++){
		                                      if(split[c] == listData[b].id){
		                                        st+=listData[b].name+",";
		                                      }
		                                    }
		                                  }
		                                  tex+=st.substring(0, st.length-1);
		                                  tex+="</td>";
                                   
                                   
                                   tex+="<td class='tc' onclick='show();'>*****</td>"+
                                   "<td class='tc' onclick='show();'>*****</td>"+
                               " <td class='tc' >"+
                                 "<select id='select' onchange='operation(this);'>";
                                  if(list[i].operatingType==1){
                                	  strTypes = 1;
                                      tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1' selected='selected' disabled='disabled'>能参加</option>";
                                  }else if(list[i].operatingType==2){
                                	  strTypes = 2;
                                      tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1'>能参加</option>"+
                                      "<option value='"+list[i].id+","+list[i].expertConditionId+",3'>不能参加</option>"+
                                      "<option selected='selected' value='"+list[i].id+","+list[i].expertConditionId+",2'>待定</option>";
                                  }else if(list[i].operatingType==3){
                                	  strTypes = 3;
                                      tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1' selected='selected' disabled='disabled'>不能参加</option>";
                                  }else{
                                	  strTypes = 4;
                                      tex+= "<option >请选择</option>"+
                                          "<option value='"+list[i].id+","+list[i].expertConditionId+",1'>能参加</option>"+
                                      "<option value='"+list[i].id+","+list[i].expertConditionId+",3'>不能参加</option>"+
                                      "<option  value='"+list[i].id+","+list[i].expertConditionId+",2'>待定</option>";
                                  }
                                  tex+="</select>"+
                                "</td>"+
                           "</tr>";
                           }
                           }
                        	   $('#tbody tr:lt('+list.length+')').remove();
                               $("#tbody ").prepend(tex);  
                           
                         }
                           if(strTypes != 4 && strTypes != 0){
                               layer.alert("抽取完成,剩余为以满足条件",{offset: ['222px', '222px'], shade:0.01});
                           }
               }
           });
       }
       
       function resetQuery(){
           $("#form1").find(":input[type='text']").removeAttr("title");
           $("#form1").find(":input[type='text']").val("");
           $("#area").find("option:first").prop("selected", 'selected');
           $("#dnone").addClass("dnone");
            areas();
         }


      function supervise() {
        //  iframe层
        layer.open({
          type: 2,
          title: "填写监督人员",
          shadeClose: true,
          shade: 0.01,
          offset: '20px',
          move: false,
          area: ['90%', '50%'],
          content: '${pageContext.request.contextPath}/SupplierExtracts/showSupervise.do',
          success: function(layero, index) {
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          },
          btn: ['保存', '关闭'],
          yes: function() {
            iframeWin.add();

          },
          btn2: function() {
              layer.closeAll();
            } //iframe的url
        });
      }

      function opens(cate) {
    	  var type=$("#expertsTypeId").val();
        //  iframe层
        var iframeWin;
        layer.open({
          type: 2,
          title: "选择条件",
          shadeClose: true,
          shade: 0.01,
          area: ['430px', '400px'],
          offset: '20px',
          content: '${pageContext.request.contextPath}/ExpExtract/addHeading.do?type='+type, //iframe的url
          success: function(layero, index) {
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          },
          btn: ['保存', '重置'],
          yes: function() {
            iframeWin.getChildren(cate);
            selectLikeExpert();

          },
          btn2: function() {
            opens();
          }
        });
      }
      
      function expTypeChange(select){
    	  
//     	  alert(select.value);
    	  var id = select.value;
    	  if( id != 1){
//     		  alert($(select).parent().parent().parent().html());
    		  $(select).parent().parent().parent().find("#extCategoryNameC").val("");
    		  $(select).parent().parent().parent().find("#extCategoryId").val("");
    		  $(select).parent().parent().parent().find("#extCategoryName").val("");
    	  }
    	  
      }
    </script>
  </head>
<script type="text/javascript">
      function showExpertType() {
        var setting = {
          check: {
            enable: true,
            chkboxType: {
              "Y": "",
              "N": ""
            }
          },
          view: {
            dblClickExpand: false
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
            }
          },
          callback: {
            beforeClick: beforeClick,
            onCheck: onCheck
          }
        };
        $.ajax({
          type: "GET",
          async: false,
          url: "${pageContext.request.contextPath}/ExpExtract/projectType.do",
          dataType: "json",
          success: function(zNodes) {
            tree = $.fn.zTree.init($("#treeExpertType"), setting, zNodes);
            tree.expandAll(true); //全部展开
          }
        });
        var cityObj = $("#expertsTypeName");
        var cityOffset = $("#expertsTypeName").offset();
        $("#expertTypeContent").css({
          left: cityOffset.left + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownExpertType);
      }

      function onBodyDownExpertType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#expertTypeContent").length > 0)) {
          hideExpertType();
        }
      }

      function hideExpertType() {
        $("#expertTypeContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownExpertType);

      }

      function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeExpertType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeExpertType"),
          nodes = zTree.getCheckedNodes(true),
          v = "";
        var rid = "";
          //设置隐藏展示
          $("#goodsServerCount").addClass("dnone");
          $("#goodsProjectCount").addClass("dnone"); 
          $("#goodsCount").addClass("dnone"); 
          $("#projectCount").addClass("dnone");
          $("#serviceCount").addClass("dnone");
          $("#goodsServer").val("");
          $("#goodsProject").val(""); 
          $("#goods").val(""); 
          $("#project").val("");
          $("#service").val("");

       var codes = "";
       var code = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
          rid += nodes[i].id + ",";
          codes += nodes[i].code + ",";
          code += nodes[i].code;
          if('GOODS_SERVER' == nodes[i].code  ){
              $("#dnone").addClass("dnone"); 
              $("#goodsServerCount").removeClass("dnone");
          }else if('GOODS_PROJECT' == nodes[i].code){
               $("#dnone").addClass("dnone");
              $("#goodsProjectCount").removeClass("dnone"); 
          }else if('GOODS' == nodes[i].code){
              $("#dnone").removeClass("dnone");
              $("#goodsCount").removeClass("dnone"); 
          }else if('PROJECT' == nodes[i].code){
              $("#dnone").removeClass("dnone"); 
              $("#projectCount").removeClass("dnone");
          }else if('SERVICE' == nodes[i].code){
              $("#dnone").removeClass("dnone"); 
              $("#serviceCount").removeClass("dnone");
          }
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        $("#expertsTypeName").val(v);
        $("#expertsTypeName").attr("title", v);
        $("#expertsTypeId").val(rid);
        $("#expertsTypeCode").val(codes);
        
        if (v != null && v != ''){
            $("#dnone").removeClass("dnone");
           if('GOODS_SERVER' == code || 'GOODS_PROJECT' == code || 'GOODS_SERVERGOODS_PROJECT' == code){
               $("#dnone").addClass("dnone"); 
           }
          } else{
             $("#dnone").addClass("dnone");
          }
        $("#categoryName").val("");
        $("#categoryId").val("");
        selectLikeExpert();
        
      }
    </script>
    
    <script type="text/javascript">
      function showExpertsFromType() {
        var setting = {
          check: {
            enable: true,
            chkboxType: {
              "Y": "",
              "N": ""
            }
          },
          view: {
            dblClickExpand: false
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
            }
          },
          callback: {
            beforeClick: beforeClickFrom,
            onCheck: onCheckFrom
          }
        };
        $.ajax({
          type: "GET",
          async: false,
          url: "${pageContext.request.contextPath}/ExpExtract/expertsFrom.do",
          dataType: "json",
          success: function(zNodes) {
            tree = $.fn.zTree.init($("#treeFromType"), setting, zNodes);
            tree.expandAll(true); //全部展开
          }
        });
        var cityObj = $("#expertsFromName");
        var cityOffset = $("#expertsFromName").offset();
        $("#expertFromContent").css({
          left: cityOffset.left + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownFromType);
      }

      function onBodyDownFromType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#expertFromContent").length > 0)) {
          hideFromType();
        }
      }

      function hideFromType() {
        $("#expertFromContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownFromType);

      }

      function beforeClickFrom(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeFromType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheckFrom(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeFromType"),
          nodes = zTree.getCheckedNodes(true),
          v = "";
        var rid = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
          rid += nodes[i].id + ",";
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        	$("#expertsFromName").val(v);
        	   $("#expertsFromName").attr("title",v);
        $("#expertsFrom").val(rid);
        selectLikeExpert();
      }
      
      
      /**点击触发事件*/
      function chane(){
        var sun="0";
         var goodsCount = $("#goods").val();
         if(goodsCount != null && goodsCount != ''){
           sun =parseInt(sun)+parseInt(goodsCount);
         }
           var projectCount = $("#project").val();
           if(projectCount != null && projectCount != ''){
               sun= parseInt(sun)+parseInt(projectCount);
             }
           var serviceCount = $("#service").val();
           if(serviceCount != null && serviceCount != ''){
               sun =parseInt(sun) + parseInt(serviceCount);
             }
           var goodsServerCount = $("#goodsServer").val();
           if(goodsServerCount != null && goodsServerCount != ''){
               sun = parseInt(sun) + parseInt(goodsServerCount);
             }
           var goodsProjectCount = $("#goodsProject").val();
           if(goodsProjectCount != null && goodsProjectCount != ''){
               sun = parseInt(sun)+parseInt(goodsProjectCount);
             }
           $("#sunCount").val(sun);
      }
      
    </script>

  <body>

    <!--面包屑导航开始-->
  <div id="expertTypeContent" class="expertTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeExpertType" class="ztree" style="margin-top:0;"></ul>
  </div>
   <div id="expertFromContent" class="expertFromContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeFromType" class="ztree" style="margin-top:0;"></ul>
  </div>
    <c:if test="${typeclassId!=null && typeclassId !=''}">
      <div class="margin-top-10 breadcrumbs ">
        <div class="container">
          <ul class="breadcrumb margin-left-0">
            <li>
              <a href="#"> 首页</a>
            </li>
            <li>
              <a href="#">支撑环境系统</a>
            </li>
            <li>
              <a href="#">专家管理</a>
            </li>
            <li>
              <a href="#">专家抽取</a>
            </li>
            <li class="active">
              <a href="#">添加专家抽取</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
      </div>
    </c:if>
    <div class="container">
<!--       <div class="headline-v2"> -->
<!--         <h2>抽取条件</h2> -->
<!--       </div> -->
    </div>
    <div class="container">
      <div id="supplierTypeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
        <ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
      </div>
      <form id="form1" method="post">
      <input id="sunCount" type="hidden">
        <div class="container container_box">
          <!--         专家所在地区 Id-->
          <input type="hidden" name="addressId" id="address" value="">
            <input type="hidden" name="address" id="addressStr" value="">
          <!--         专家id-->
          <input type="hidden" name="expertId" id="expertId" value="">
          <!--         项目id -->
          <input type="hidden" name="projectId" id="pid" value="${packageId}">
          <!-- 类型   -->
          <input type="hidden" name="typeclassId" value="${typeclassId}" />
             <!--  满足多个条件 -->
        <input type="hidden" name="isMulticondition" id="isSatisfy" >
          <!-- 物资技术专家 -->
<!--         <input type="hidden" name="goodsCount" id="goodsCount" > -->
          <!--  工程技术专家 -->
<!--         <input type="hidden" name="projectCount" id="projectCount" > -->
<!--         服务技术专家 -->
<!--         <input type="hidden" name="serviceCount" id="serviceCount" > -->
<!--         物资服务经济 -->
<!--         <input type="hidden" name="goodsServerCount" id="goodsServerCount" > -->
<!--         工程经济 -->
<!--         <input type="hidden" name="goodsProjectCount" id="goodsProjectCount" > -->
<!--              限制地区理由 -->
        <input type="hidden" name="addressReason" id="addressReason" >
<!--         省 -->
        <input type="hidden"  name="province" id="province" />
        <input type="hidden" name="" id="hiddentype">
          <div>
            <h2 class="count_flow"><i>1</i>抽取条件</h2>
            <ul class="ul_list">
              <li class="col-md-3 col-sm-6 col-xs-12  pl15">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">专家地区：</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <select class="col-md-6 col-sm-6 col-xs-6 p0" id="area" onchange="areas();" >
                       
                     <option value="">全国</option>
                      <c:forEach  items="${privnce }" var="prin">
                   <c:if test="${prin.id==area.parentId }">
                    <option value="${prin.id }" selected="selected" >${prin.name }</option>
                   </c:if>
                   <c:if test="${prin.id!=area.parentId }">
                    <option value="${prin.id }"  >${prin.name }</option>
                   </c:if>
                   </c:forEach>
                  </select>
                  <select name="extractionSites" class="col-md-6 col-sm-6 col-xs-6 p0" id="city" onchange="selectLikeExpert();">
               
                     <option value="">所有省市</option>
                  <c:forEach  items="${city }" var="city">
                   <c:if test="${city.id==listCon.address}">
                    <option value="${city.id }" selected="selected" >${city.name }</option>
                   </c:if>
                   <c:if test="${city.id!=listCon.address }">
                    <option value="${city.id }"  >${city.name }</option>
                   </c:if>
                </c:forEach>
                  </select>
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12 ">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">年龄：</span>
                <div class="input-append col-sm-12 col-xs-12 col-md-12 p0">
               
                  <input class="col-md-5 col-sm-5 col-xs-5" maxlength="2" value="${listCon.ageMin}" onchange="age();" id="ageMinC" name="ageMin" type="text"> 
                  <span class="f14 fl col-md-2 col-sm-2 col-xs-2">至</span>
                  <input class="col-md-5 col-sm-5 col-xs-5" value="${listCon.ageMax}" maxlength="2" onchange="age();" id="ageMaxC" name="ageMax" type="text">
                  
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">专家来源：</span>
                 <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <c:set value="" var="froms"></c:set>
                     <c:forEach var="ay" items="${listCon.expertsFromSplit}" >
                      <c:forEach var="from" items="${find}">
                      <c:if test="${ay eq from.id}"> 
                      <c:set value="${froms},${from.name}" var="froms"></c:set>
                    </c:if> 
                      </c:forEach> 
                    </c:forEach>
                    <input   id="expertsFromName"  type="text" readonly name="expertsFromName" value="${fn:substring(froms,1,froms.length())}" onclick="showExpertsFromType();" />
                    <input type="hidden" name="expertsFrom" id="expertsFrom" value="" />
                    <span class="add-on">i</span>
                 </div>
              </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">执业资格：</span>
                 <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <input   id="expertsQualification"  type="text" name="expertsQualification" value="${listCon.conTypes[0].expertsQualification} " onchange="selectLikeExpert();" />
                    <span class="add-on">i</span>
                 </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">抽取总人数：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                  <input class="input_group " maxlength="6" name="expertsCount" value="${sumCount}"  id="eCount" type="text">
                  <span class="add-on">i</span>
                  <div class="cue" id="expertsCountError"></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12 ">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">专家类别：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                  <c:set value="" var="typeName"></c:set>
                  <c:set value="" var="typeCode"></c:set>
                 <c:set value="" var="typeId"></c:set>
                  <c:forEach items="${listCon.conTypes}" var="conType">
		                <c:forEach items="${conType.expertsTypeSplit}" var="split">
			                <c:forEach var="project" items="${ddList}">
			                 <c:if test="${split eq project.id}">
			                  <c:set value="${typeName},${project.name}" var="typeName"></c:set>
			                  <c:set value="${typeId},${project.id}" var="typeId"></c:set>
			                  <c:set value="${typeCode},${project.code}" var="typeCode"></c:set>
			                 </c:if>
			                </c:forEach>
		                </c:forEach>
                  </c:forEach>
                   <input   id="expertsTypeName"  type="text" readonly name="expertsTypeName" value="${fn:substring(typeName,1,typeName.length() )}" onclick="showExpertType();" />
                  <input type="hidden" name="expertsTypeId" id="expertsTypeId" value="${fn:substring(typeId,1,typeId.length() )}"  />
                     <input type="hidden" name="expertsTypeCode" id="expertsTypeCode" value="${fn:substring(typeCode,1,typeCode.length() )}"  />
                  <span class="add-on">i</span>
                </div>
              </li>
            
               <li class="col-md-3 col-sm-3 col-xs-3  dnone clear" id="goodsCount" >
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">物资技术人数：</span>
                 <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <input     type="text" name="goodsCount" id="goods"  value="${con.expertsCount}" onchange="chane();" />
                    <span class="add-on">i</span>
                 </div>
              </li>
           
              <li class="col-md-3 col-sm-3 col-xs-3 dnone " id="projectCount">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">工程技术人数：</span>
                 <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <input     type="text" name="projectCount" id="project"  value="${con.expertsCount }"  onchange="chane();" />
                    <span class="add-on">i</span>
                 </div>
              </li>
            
              <li class="col-md-3 col-sm-3 col-xs-3 dnone "  id="serviceCount">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">服务技术人数：</span>
                 <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <input    type="text" name="serviceCount" id="service" value="${con.expertsCount }"  onchange="chane();" />
                    <span class="add-on">i</span>
                 </div>
              </li>
          
              <li class="col-md-3 col-sm-3 col-xs-3  dnone"  id="goodsServerCount" >
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">服务经济人数：</span>
                 <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <input   type="text" name="goodsServerCount" id="goodsServer" value="${con.expertsCount }" onchange="chane();" />
                    <span class="add-on">i</span>
                 </div>
              </li>
          
              <li class="col-md-3 col-sm-3 col-xs-3 dnone" id="goodsProjectCount" >
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">工程经济人数：</span>
                 <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <input    type="text" name="goodsProjectCount" id="goodsProject" value="${con.expertsCount }"  onchange="chane();" />
                    <span class="add-on">i</span>
                 </div>
              </li>
       
                 <li class="col-md-12 col-sm-12 col-xs-12 dnone" id="dnone">
                <span class="col-md-12 padding-left-5 col-sm-6 col-xs-6">品目：</span>
                <div class="input-append input_group col-sm-6 col-xs-6 p0">
                  <input class="input_group " readonly id="categoryName" name="categoryName"  value="${listCon.conTypes[0].categoryName}"  onclick="opens(this);" type="text">
                    <input  readonly id="categoryId" type="hidden" name="categoryId">
                  <span class="add-on">i</span>
                </div>
              </li>
              <li class="col-md-12 col-sm-12 col-xs-12 tc">
            <div class="">
            <button class="btn btn-windows add" id="save" onclick="cityt();" type="button">抽取</button>
                <button class="btn btn-windows add" id="save" onclick="finish();" type="button">完成抽取</button>
                    <button class="btn btn-windows add" id="save" onclick="temporary();" type="button">暂存</button>
<!--             <button class="btn btn-windows add" id="save" onclick="resetQuery();" type="button">重置</button> -->
            </div>
          </li>
            </ul>
               <!--=== Content Part ===-->
               <div class="" style="width: 100%">
            <h2 class="count_flow"><i>2</i>抽取结果</h2>
             <div align="center" id="countdnone" class="f26 ">满足条件共有<span class="f26 red" id="count">0</span>人</div>
             </div>
    <ul class="ul_list">
      <!-- Begin Content -->
      <div class="col-md-12"  >
        <div id="extcontype">
        <c:forEach var="con" items="${extConType}">
      <c:if test="${con.expertsType != null }">
        <c:if test="${con.expertsType.kind == 6 }">
                                              专家类别：${con.expertsType.name }技术
                  </c:if>
                  <c:if test="${con.expertsType.kind != 6 }">
                                            专家类别：${con.expertsType.name }
                   
                  </c:if>
      </c:if>
       
     
                          &nbsp;&nbsp;&nbsp;&nbsp;抽取数量${con.alreadyCount}/${con.expertsCount }                             
            <br />
          </c:forEach>
        </div>
        <div class="col-md-12" >

          <div class="clear"></div>
         <table id="table" class="table table-bordered table-condensed">
          <thead>
            <tr>
              <th class="info w50">序号</th>
              <th class="info">专家姓名</th>
              <th class="info">联系电话</th>
              <th class="info">专家类别</th>
              <th class="info">工作单位名称</th>
              <th class="info">专家技术职称</th>
              <th class="info">操作</th>
            </tr>
          </thead>
          <tbody id="tbody">
            <c:forEach items="${extRelateListYes}" var="listyes"
              varStatus="vst">
              <tr class='cursor '>
                <td class='tc'>${vst.index+1}</td>
                <td class='tc'>*****</td>
                <td class='tc'>${listyes.expert.mobile}</td>
                <td class="tc">
                   <c:set value="" var="typeId"></c:set>
                   <c:set var="splits" value="${fn:split(listyes.expert.expertsTypeId,',')}"></c:set>
                 <c:forEach items="${splits }" var="split">
                  <c:forEach var="project" items="${ddList}">
                   <c:if test="${split eq project.id}">
                    <c:set value="${typeId},${project.name}" var="typeId"></c:set>
                   </c:if>
                  </c:forEach>
                </c:forEach>
                ${fn:substring(typeId,1,typeId.length())}
                </td>
                <td class='tc'>*****</td>
                <td class='tc'>*****</td>
                <td class='tc'><select id='select'
                  onchange='operation(this);'>
                    <c:choose>
                      <c:when test="${listyes.operatingType==1}">
                        <option selected="selected" disabled="disabled"
                          value='${listyes.id},${listyes.expertConditionId},1'>能参加</option>
                      </c:when>
                      <c:when test="${listyes.operatingType==2}">
                        <option value='${listyes.id},${listyes.expertConditionId},1'>能参加</option>
                        <option value='${listyes.id},${listyes.expertConditionId},3'>不能参加</option>
                        <option selected="selected" disabled="disabled"
                          value='${listyes.id},${listyes.expertConditionId},2'>待定</option>
                      </c:when>
                      <c:when test="${listyes.operatingType==3}">
                        <option selected="selected" disabled="disabled"
                          value='${listyes.id},${listyes.expertConditionId},3'>不能参加</option>
                      </c:when>
                      <c:otherwise>
                        <option>请选择</option>
                        <option value='${listyes.id},${listyes.expertConditionId},1'>能参加</option>
                        <option value='${listyes.id},${listyes.expertConditionId},3'>不能参加</option>
                        <option value='${listyes.id},${listyes.expertConditionId},2'>待定</option>
                      </c:otherwise>
                    </c:choose>
                </select></td>
              </tr>
            </c:forEach>
            <c:forEach items="${extRelateListNo }" var="listno" varStatus="vs">
              <tr class='cursor'>
                <td class='tc'>${(vs.index+1)+1}</td>
                <td class='tc'>*****</td>
                <td class='tc'>*****</td>
                <td class='tc'>*****</td>
                <td class='tc'>*****</td>
                <td class='tc'>*****</td>
                <td class='tc'>请选择</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
        </div>
      </div>
        </ul>
          </div>
        </div>
      </form>
    </div>
  </body>

</html>