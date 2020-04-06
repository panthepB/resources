<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/headerTop.jsp" %>
	<title>ระบบจัดการการบริหารลูกค้า :: การยืนยันการแจ้งงานบริการ</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp" %>

				<div class="content">
					<div class="Post">
						<div class="Post-body">
							<div class="Post-inner">
								<h2 class="PostHeaderIcon-wrapper">
									<span class="PostHeader">การยืนยันการแจ้งงานบริการ</span></h2>
							    <div class="PostContent">
									<p>
									<form:form method="POST" commandName="confirmForm" action="saveConfirmForm.htm" name="form1"><form:hidden path="informId"/>
										
										<table width="100%" class="borderTable">
											<tr class="HeadTable">
												<td colspan="4" align="left" valign="top"><span class="style1">ข้อมูลลูกค้า</span></td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left">ชื่อหน่วยงาน</td>
												<td colspan="3" align="left" class="subTable">${inform.customer.name}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" valign="top">ผู้ติดต่อ</td>
												<td width="30%" align="left" class="subTable">${inform.contact.name}</td>
												<td width="20%" align="left" valign="top">ตำแหน่ง</td>
												<td width="30%" align="left" class="subTable">${inform.contact.depart}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" valign="top">เบอร์ติดต่อ</td>
												<td width="30%" align="left" class="subTable">${inform.contact.tel}</td>
												<td width="20%" align="left" valign="top">รายละเอียด</td>
												<td width="30%" align="left" class="subTable">${inform.contact.desc}</td>
											</tr>
											<tr class="HeadTable">
												<td colspan="4" align="left" valign="top"><span class="style1">ข้อมูลการแจ้งงาน</span></td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left">ประเภทการแจ้งงาน</td>
												<td width="30%" align="left" class="subTable"><c:choose><c:when test="${inform.type eq 1}">การติดตั้ง</c:when><c:otherwise>การบริการ</c:otherwise></c:choose></td>
												<td width="20%" align="left">เลขที่ PO</td>
												<td width="30%" align="left" class="subTable">${inform.codePO }</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left">ระบบ</td>
												<td colspan="3" align="left" class="subTable">${inform.product.name}</td>
											</tr>
											<tr class="subHeadTable">
												<td align="left" height="35" valign="top">รายละเอียดการบริการ</td>
												<td colspan="3" align="left" valign="top" class="subTable"><form:hidden path="descSupport"/>${confirmForm.descSupport}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" >วันนัด</td>
												<td colspan="3" align="left" class="subTable"><input type="text" name="dateService" id="dateService" value="${confirmForm.dateService}" dojoType="dijit.form.DateTextBox" /></td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" >เวลานัด</td>
												<td colspan="3" align="left" class="subTable"><input type="text" name="hourService" id="hourService" value="${confirmForm.hourService}" dojoType="dijit.form.NumberTextBox"  constraints="{min:0,max:23}" style="width: 40px;text-align: center;"  invalidMessage="กรุณากรอกข้อมูลให้ถูกต้อง" />:<input type="text" name="minuteSerivce" id="minuteSerivce" value="${confirmForm.minuteSerivce}" dojoType="dijit.form.NumberTextBox"  constraints="{min:0,max:59}" style="width: 40px;text-align: center;"  invalidMessage="กรุณากรอกข้อมูลให้ถูกต้อง" /> น.</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" >ระยะเวลา</td>
												<td colspan="3" align="left" class="subTable"><input type="text" name="dayService" id="dayService" value="${confirmForm.dayService}" dojoType="dijit.form.NumberTextBox"  constraints="{min:0,max:7}" style="width: 40px;text-align: center;"  invalidMessage="กรุณากรอกข้อมูลให้ถูกต้อง" /> วัน</td>
											</tr>
											<tr class="subHeadTable">
												<td align="left" valign="top">รายละเอียดการแจ้งงาน</td>
												<td colspan="3" align="left" valign="top" class="subTable"><form:textarea path="descSupport" /></td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" >ผู้บริการ</td>
												<td colspan="3" align="left" class="subTable"><form:hidden path="supporterId"/>${inform.supportor.name}
											</tr>
								  			<tr class="subHeadTable">
												<td width="20%" align="left" >ผู้แจ้ง</td>
												<td colspan="3" align="left" class="subTable">${inform.informer.name}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" >เวลาแจ้ง</td>
												<td colspan="3" align="left" class="subTable">${inform.datetimeInform}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" >ผู้บันทึก</td>
												<td colspan="3" align="left" class="subTable">${inform.admin.name}</td>
											</tr>
											<tr>
												  <td colspan="4" align="center" valign="top" class="subTable_button"><input name="submitButton" type="submit" class="styleButton" value="ยืนยันการแจ้งงาน" />
											      <input name="resetButton" type="reset" class="styleButton" value="เริ่มใหม่" /> <input name="cancel" type="button" class="styleButton" value="ยกเลิก" onclick="location.href='listInform.htm'" /></td>
								  			</tr>
										</table>
	
								</form:form>
								    </p>
                              </div>
							  <div class="cleared"></div>
							</div>
						</div>
					</div>
				</div>

	</div>
	<div  id="pageNumber" menu="m2" sub="m2s2"  class="cleared"></div>
	<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div>
<%@ include file="/WEB-INF/jsp/version.jsp" %>
</body>
<script type="text/javascript">
  dojo.addOnLoad(alertUser('${alert.text}'));
  dojo.require("dijit.form.FilteringSelect");
  dojo.require("dijit.form.SimpleTextarea");
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "supporterId", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:195px"}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "descSupport", widgetType: "dijit.form.SimpleTextarea", widgetAttrs : {style:"width:200px"}}));
</script>
</html>