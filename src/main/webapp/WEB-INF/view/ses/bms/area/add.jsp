<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script type="text/javascript">
       var datas;
        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }
        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getCheckedNodes(true),
            v = "";
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].name + ",";
                $("#pid").val(nodes[i].id);
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            var cityObj = $("#citySel");
            cityObj.attr("value", v);
            
            hideMenu();
        }
        function showMenu() {
             var setting = {
              async:{
                    autoParam:["id"],
                    enable:true,
                    url:"${pageContext.request.contextPath}/area/listByOne.do",
                    dataType:"json",
                    type:"post",
                },
            check: {
                enable: true,
                chkStyle: "radio",
                radioType: "all"
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey:"id",
                    pId:"pId",
                    rootPId:-1,
                }
            },
            callback: {
                onClick: onClick,
                onCheck: onCheck
            }
        };
             tree = $.fn.zTree.init($("#treeDemo"), setting, datas);  
                    tree.expandAll(false);//全部展开
            var cityObj = $("#citySel");
            var cityOffset = $("#citySel").offset();
            $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        }
        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }
        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                hideMenu();
            }
        }
    </script>
  </head>
  <script type="text/javascript">
    $(function(){
        $("#save").click(function(){
            
            $.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/area/save.html",  
               data: $("#form1").serializeArray(),  
               dataType: 'json',  
               success:function(result){
                   if(result.msg){
                    layer.msg("添加成功",{offset: ['150px', '180px']});
                    parent.window.setTimeout(function(){
                            parent.window.location.href = "${pageContext.request.contextPath}/area/list.html";
                        }, 1000);
                   }
                },
                error: function(result){
                    layer.msg("添加失败",{offset: ['150px', '180px']});
                }
            });
            
        });
        /* $("#backups").click(function(){
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); 
        }); */
    });
  </script>
 <body>
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">数据字典</a></li><li><a href="#">地区管理</a></li><li class="active"><a href="#">添加地区</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
   <div class="container container_box">
       <div id="menuContent" class="menuContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
            <ul id="treeDemo" class="ztree"  style="width: 220px"></ul>
       </div>
       <div>
       <div class="headline-v2">
         <h2>添加地区</h2>
       </div>
       <form action="" id="form1" method="post">
           <div>
               <input type="hidden" name="id" id="pid" value="${area.id }">
               <ul class="ul_list">
                 <li class="col-md-6 p0">
                   <span class="col-md-12 padding-left-5">上级</span>
                   <div class="input-append">
                    <input id="citySel" class="span5" name="pname" type="text" readonly value="${area.name }"  onclick="showMenu();" />
                    <span class="add-on">i</span>
                   </div>
                 </li>
                 <li class="col-md-6 p0">
                   <span class="col-md-12 padding-left-5">名称</span>
                   <div class="input-append">
                    <input class="span5" name="name" maxlength="30" type="text">
                    <span class="add-on">i</span>
                   </div>
                 </li>
               </ul>
          </div> 
       
         <div class="col-md-12 tc">
                <button class="btn btn-windows save" id="save" type="button">保存</button>
                <button class="btn btn-windows back" id="backups" type="button" onclick="history.go(-1)">返回</button>
            </div>
            </form>
          </div>
  </div>
 </body>
</html>
