<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<%@ include file="/WEB-INF/view/common/map.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
	$(function () {
	var address;
    Highcharts.setOptions({
        lang:{
            drillUpText:"返回 > {series.name}"
        }
    });

    var data = Highcharts.geojson(Highcharts.maps['countries/cn/custom/cn-all-china']),small = $('#mapsId').width() < 400;
    // 给城市设置随机数据
  	var serverData=${data};
    $.each(data, function (i) {
        this.drilldown = this.properties['drill-key'];
        this.value = serverData[this.properties['drill-key']];
    });
		function getPoint(e){
			console.log(e.point.name);
		}
	function getShow(e){
		alert(1);
	}
    //初始化地图
    $('#mapsId').highcharts('Map', {

        chart : {
					spacingBottom:30,
				 
            events: {
               
            }
        },
			
        credits:{
					href:"javascript:goHome()",
            text:""
        },
        title : {
            text : '',
        },

        subtitle: {
            text: '中国',
            floating: true,
            align: 'right',
            y: 50,
            style: {
                fontSize: '16px'
            }
        },

        legend: small ? {} : {
					 // enabled: false,
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },
        tooltip:{
        pointFormat:"{point.properties.cn-name}:{point.value}"
        },
        colorAxis: {
            min: 0,
            minColor: '#E6E7E8',
            maxColor: '#005645',
					labels:{
						style:{
								"color":"red","fontWeight":"bold"
						}
					}
        },

        mapNavigation: {
            enabled: true,
            buttonOptions: {
                verticalAlign: 'bottom'
            }
        },

        plotOptions: {
            map: {
                states: {
                    hover: {
                        color: '#EEDD66'
                    }
                }
            }
        },

        series : [{
            data : data,
            name: '中国',
            dataLabels: {
                enabled: true,
                format: '{point.properties.cn-name}'
            },
            point: {
               events: {
                   click: function () { 
                   address=this.name;
                       window.location.href="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address="+address+"&judge=3";
                    }
                  }
           }
        }],

        drilldown: {
					
            activeDataLabelStyle: {
                color: '#FFFFFF',
                textDecoration: 'none',
                textShadow: '0 0 3px #000000'
            },
            drillUpButton: {
                relativeTo: 'spacingBox',
                position: {
                    x: 0,
                    y: 60
                }
            }
        }
    });
});

var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";  
var base64DecodeChars = new Array(  
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,  
    52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,  
    -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,  
    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,  
    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,  
    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);  


function base64decode(str) {  
    var c1, c2, c3, c4;  
    var i, len, out;  
  
    len = str.length;  
    i = 0;  
    out = "";  
    while(i < len) {  
    /* c1 */  
    do {  
        c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];  
    } while(i < len && c1 == -1);  
    if(c1 == -1)  
        break;  
  
    /* c2 */  
    do {  
        c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];  
    } while(i < len && c2 == -1);  
    if(c2 == -1)  
        break;  
  
    out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));  
  
    /* c3 */  
    do {  
        c3 = str.charCodeAt(i++) & 0xff;  
        if(c3 == 61)  
        return out;  
        c3 = base64DecodeChars[c3];  
    } while(i < len && c3 == -1);  
    if(c3 == -1)  
        break;  
  
    out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));  
  
    /* c4 */  
    do {  
        c4 = str.charCodeAt(i++) & 0xff;  
        if(c4 == 61)  
        return out;  
        c4 = base64DecodeChars[c4];  
    } while(i < len && c4 == -1);  
    if(c4 == -1)  
        break;  
    out += String.fromCharCode(((c3 & 0x03) << 6) | c4);  
    }  
    return out;  
}  
function goHome(){
	window.open("http://www.peng8.net/");
}
/* function getGithub()
	{
		$.getJSON("https://api.github.com/repos/peng8/GeoMap/contents/json/bei_jing.geo.json", function(data){
		console.log(base64decode(data.content));
		}); 
	}*/
function submit(){
	form1.submit();
}
function chongzhi(){
	$("#supplierName").val('');
	$("#startDate").val('');
	$("#endDate").val('');
	$("#contactName").val('');
	$("#category").val('');
	$("#supplierType").val('');
	$("#categoryIds").val('');
	$("#supplierTypeIds").val('');
	window.location.href="${pageContext.request.contextPath}/supplierQuery/highmaps.html?judge=3";
}
$(function() {
		var optionNodes = $("option");
		for ( var i = 1; i < optionNodes.length; i++) {
			if ("${sup.supplierType}" == $(optionNodes[i]).val()) {
				optionNodes[i].selected = true;
			}
		}
	});
				function beforeClickCategory(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeRole");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		    }
		    function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		    }
		
		function onCheckCategory(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeRole"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var rid = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				rid += nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (rid.length > 0 ) rid = rid.substring(0, rid.length-1);
			var cityObj = $("#category");
			cityObj.attr("value", v);
			$("#categoryIds").val(rid); 
		}
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeSupplierType"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var rid = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				rid += nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (rid.length > 0 ) rid = rid.substring(0, rid.length-1);
			var cityObj = $("#supplierType");
			cityObj.attr("value", v);
			$("#supplierTypeIds").val(rid); 
		}
			function showCategory() {
			var setting = {
			check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
				callback: {
					beforeClick: beforeClickCategory,
					onCheck: onCheckCategory
				}
			};
	        $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/category/query_category.do?categoryIds="+" ",
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeRole"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#category");
			var cityOffset = $("#category").offset();
			$("#roleContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownOrg);
		}
		function showSupplierType() {
			var setting = {
			check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
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
             url: "${pageContext.request.contextPath}/supplier_type/find_supplier_type.do?supplierId=''",
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#supplierType");
			var cityOffset = $("#supplierType").offset();
			$("#supplierTypeContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownSupplierType);
		}
		function hideRole() {
			$("#roleContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownOrg);
			
		}
		function hideSupplierType() {
			$("#supplierTypeContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownOrg);
			
		}
		function onBodyDownOrg(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length>0)) {
				hideRole();
			}
		}
		function onBodyDownSupplierType(event) {
			if (!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length>0)) {
				hideSupplierType();
			}
		}
</script>
</head>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		  <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商管理</a></li><li class="active"><a href="#">入库供应商查询</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeRole" class="ztree" style="margin-top:0;"></ul>
	   </div>
	    <div id="supplierTypeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
	   </div>
  <body>
  <div class="container">
    <div class="headline-v2">
     <h2>供应商数量统计</h2>
   </div> 
   <h2 class="search_detail">
  			<form id="form1" action="${pageContext.request.contextPath}/supplierQuery/highmaps.html?judge=3" method="post">
		       <input type="hidden" name="page" id="page">
		       <ul class="demand_list">
                  <li>
                    <label class="fl">供应商名称：</label><span><input id="supplierName"  name="supplierName" value="${sup.supplierName }" type="text"></span>
                  </li>
                  <li>
                    <label class="fl">注册时间：</label><span><input id="startDate" name="startDate" class="Wdate w230" type="text"  value='<fmt:formatDate value="${sup.startDate }" pattern="YYYY-MM-dd"/>'
                        onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
                        <span class="f14">至</span>
                        <input id="endDate" name="endDate" value='<fmt:formatDate value="${sup.endDate }" pattern="YYYY-MM-dd"/>'  class="Wdate w230" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/>
                        </span>
                  </li>
                  <li>
                    <label class="fl">联系人：</label><span><input id="contactName" name="contactName" value="${sup.contactName }" type="text"></span>
                  </li> 
                  <li>
                    <label class="fl">供应商类型：</label><span><input id="supplierType" class="span2" type="text" readonly name="supplierType" value="${supplierType }" onclick="showSupplierType();" />
                             <input   type="hidden" name="supplierTypeIds"  id="supplierTypeIds" value="${supplierTypeIds }" /></span>
                  </li>
                  <li>
                    <label class="fl">品目：</label><span> <input id="category" class="span2" type="text" readonly name="categoryNames" value="${categoryNames }" onclick="showCategory();" />
                           <input   type="hidden" name="categoryIds"  id="categoryIds" value="${categoryIds }"   /></span>
                  </li>
                </ul>
                 <div class="col-md-12 clear tc mt10">
                    <button type="button" onclick="submit()" class="btn">查询</button>
                    <button type="button" onclick="chongzhi()" class="btn">重置</button> 
                </div>
                <div class="clear"></div>
		     </form>
		  </h2>
     </div>
  <div id="mapsId"></div>
  </body>
</html>
