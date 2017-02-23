<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" class="border0" value="${id}" />
<input type="hidden" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].id" value="${id}">
</td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certName"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certCode"/></td>
<td class="tc">
	<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certType" class="w100p border0" onchange="getAptLevel(this)">
		<c:forEach items="${typeList}" var="type">
			<option value="${type.id}">${type.name}</option>
		</c:forEach>
	</select>
	<input type="hidden" value="">
</td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteSequence"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].professType"/></td>
<td class="tc">
	<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteLevel" class="w100p border0" onchange="tempSave()"></select>
</td>
<td class="tc">
	<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].isMajorFund" class="w100p border0" onchange="tempSave()">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
</td>
</td>
</tr>