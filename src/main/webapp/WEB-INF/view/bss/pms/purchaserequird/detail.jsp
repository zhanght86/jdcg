 <%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
 
  <tr name="detailRow">
  		<td class="tc">${index+1}</td>
  
				 						<td class="tc  p0">
											<input type="hidden" name="list[${index }].id"  value="${id }">
										
											<input type="text" name="list[${index }].seq" value="" onblur="getSeq(this)"    class="m0 border0 w50 tc">
											<input type="hidden" name="list[${index }].parentId"  value="">
										</td>
										<td class=" p0" >
										
									<%-- 	<input type="text" name="list[${index }].department"   value="${obj.department}"> --%>
										<%-- 	<input type="hidden" name="list[${index }].department" value="${orgId }" > --%>
								  			<input type="text"  name="list[${index }].department" readonly="readonly" value="${orgName}"  class="m0 border0 w260" >
											
										
										</td>
										<td class=" p0">
											<input type="text"  class="m0 border0 w200" name="list[${index }].goodsName" onkeyup="listName(this)" onblur="lossValue()" value="" />
										</td>
										<td class="tc  p0"><input type="text" class="m0 w200 border0" name="list[${index }].stand" value="${objs.stand}"></td>
										<td class="tc  p0"><input type="text" class="m0 w140 border0" name="list[${index }].qualitStand" value="" class=""></td>
										<td class="tc  p0"><input type="text" class="m0 w50 border0" name="list[${index }].item" value="" ></td>
										
										
										<td class="tc p0" >
											<input type='hidden'  value='' >
										<input type="text" class="m0 border0 w50" name="list[${index }].purchaseCount" onkeyup="checkNum(this,1)" value="" >
											<input type='hidden'  value='' >
										</td>
										
										
										<td class="tc  p0" >
											<input type='hidden'  value='' >
										<input type="text" class="m0 border0 w100" name="list[${index }].price" onkeyup="checkNum(this,2)" value="" >
											<input type='hidden'  value='' >
										</td>
										
										<td class="tc  p0">
											<input type='hidden'  value='' >
										<input type="text" class="m0 w100 border0" name="list[${index }].budget"   value=" " >
											<input type='hidden'  value='' >
										</td>
										
										
										<td class=" p0"><input type="text" class="m0 border0" name="list[${index }].deliverDate" value=" " ></td>
										<td class="p0">
											<select name="list[${index }].purchaseType" class="m0 border0" onchange="changeType(this)" >
												<option value="">请选择</option>
												<c:forEach items="${list2 }" var="objd">
														<option value="${objd.id }"> ${objd.name }</option>
													 
												</c:forEach>
											</select>
										</td>
										<td class="tc  p0"><input type="text" name="list[${index }].supplier"  class="m0 w260 border0" ></td>
										<td class="tc  p0"><input type="text" name="list[${index }].isFreeTax" class="m0 border0"></td>
									 	<td class="tc  p0" name='userNone'><input type="text" name="list[${index }].goodsUse" class="m0 border0"></td>
										 <td class="tc p0"  name='userNone' ><input type="text" name="list[${index }].useUnit" class="m0 w260 border0"></td>
									     <td class="tc  p0"><input type="text" name="list[${index }].memo" value=" " class="m0 border0" ></td>
									 	<td class="tc p0"><button type="button" class="btn" onclick="delRowIndex(this)">删除</button></td>
 		</tr>