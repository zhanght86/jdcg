<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" value="${id}" />
<input type="hidden" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].id" value="${id}">
</td>
<td class="tc"><input type="text" class=“border0” name="supplierMatSe.listSupplierCertSes[${certSeNumber}].name"/></td>
<td class="tc"><input type="text" class=“border0” name="supplierMatSe.listSupplierCertSes[${certSeNumber}].levelCert"/></td>
<td class="tc"><input type="text" class=“border0” name="supplierMatSe.listSupplierCertSes[${certSeNumber}].licenceAuthorith"/></td>
<td class="tc"><input type="text" class=“border0” readonly="readonly" onClick="WdatePicker()" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].expStartDate"/></td>
<td class="tc"><input type="text" class=“border0” readonly="readonly" onClick="WdatePicker()" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].expEndDate"/></td>
<td class="tc">
	<select name="supplierMatSe.listSupplierCertSes[${certSeNumber}].mot" class="w100p">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
</td>
<td class="tc w200">
 <div class="w200">
 <u:upload id="se_up_${certSeNumber}" multiple="true" businessId="${id}" typeId="1" sysKey="1"  auto="true" />
 <u:show showId="se_show_${certSeNumber}" businessId="${id}" typeId="1" sysKey="1" />
 </div>
</td>
</tr>