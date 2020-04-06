<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/headerTop.jsp" %>
	<title>ระบบจัดการการบริหารลูกค้า :: การแจ้งงานบริการ</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp" %>

				<div class="content">
					<div class="Post">
						<div class="Post-body">
							<div class="Post-inner">
								<h2 class="PostHeaderIcon-wrapper">
									<span class="PostHeader">แก้ไขการแจ้งงานบริการ</span></h2>
							    <div class="PostContent">
									<p>
									<form:form method="POST" commandName="informForm" action="editInformNow.htm" name="form1"><form:hidden path="informId"/>
										
										<table width="100%" class="borderTable">
											<tr class="HeadTable">
												<td colspan="4" align="left" valign="top"><span class="style1">ข้อมูลลูกค้า</span></td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left">ลูกค้า</td>
												<td colspan="3" align="left" class="subTable"><form:hidden path="cusId"/><form:hidden path="cusName"/>${informForm.cusName}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" valign="top">เลขที่</td>
												<td width="30%" align="left" class="subTable"><form:input path="address"/></td>
												<td width="20%" align="left" valign="top">หมู่ที่</td>
												<td width="30%" align="left" class="subTable"><form:input path="moo"/></td>
											</tr>
											<tr class="subHeadTable">
												<td align="left" valign="top">ถนน</td>
												<td align="left" class="subTable"><form:input path="road"/></td>
												<td align="left" valign="top">ตำบล</td>
												<td align="left" class="subTable"><form:input path="tumbol"/></td>
											</tr>
											<tr class="subHeadTable">
												<td align="left" valign="top">อำเภอ</td>
												<td align="left" class="subTable"><form:input path="amphur"/></td>
												<td align="left" valign="top">จังหวัด</td>
												<td align="left" class="subTable"><form:select path="provinceId">
													<form:options items="${provinces}" itemLabel="name" itemValue="id"/>
												</form:select></td>
											</tr>
											<tr class="subHeadTable">
												<td align="left" valign="top">รหัสไปรษณีย์</td>
												<td colspan="3" align="left" class="subTable"><form:input path="postcode"/></td>
											</tr>
											<tr class="subHeadTable">
												<td align="left" valign="top">เบอร์โทร</td>
												<td colspan="3" align="left" class="subTable"><form:input path="tel"/></td>
											</tr>
											<tr class="subHeadTable">
												<td align="left" valign="top">ผู้ติดต่อ</td>
												<td colspan="3" align="left" class="subTable"><form:select path="contactId">
													<form:option label="ผู้ติดต่อใหม่" value="0" />
													<form:options items="${contacts}" itemLabel="name" itemValue="id" />
												</form:select> <c:if test="${informForm.cusId ne 0}"><input type="button" value="ผู้ติดต่อใหม่" class="styleButton" onclick="location.href='editInform.htm?informId=${informForm.informId}&contactId=0'" /></c:if></td>
											</tr>
											<tr class="subHeadTable">
												<td align="left" valign="top">ชื่อ</td>
												<td align="left" class="subTable"><form:input path="contactName"/></td>
												<td align="left" valign="top">ตำแหน่ง</td>
												<td align="left" class="subTable"><form:input path="contactDepart"/></td>
											</tr>
											<tr class="subHeadTable">
												<td align="left" valign="top">เบอร์ติดต่อ</td>
												<td align="left" class="subTable"><form:input path="contactTel"/></td>
												<td align="left" valign="top">รายละเอียด</td>
												<td align="left" class="subTable"><form:input path="contactDesc"/></td>
											</tr>
											<tr class="HeadTable">
												<td colspan="4" align="left" valign="top"><span class="style1">ข้อมูลการแจ้งงาน</span></td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left">ประเภทการแจ้งงาน</td>
												<td colspan="3" align="left" class="subTable"><form:select path="type">
													<form:option value="1" label="การติดตั้ง"/>
													<form:option value="2" label="การบริการ"/>
												</form:select></td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left">ระบบ</td>
												<td colspan="3" align="left" class="subTable"><form:select path="productId">
													<form:options items="${products}" itemLabel="name" itemValue="id"/>
												</form:select></td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" >วันนัด</td>
												<td colspan="3" align="left" class="subTable"><input type="text" name="dateService" id="อ" value="${informForm.dateService}" dojoType="dijit.form.DateTextBox" /></td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" >เวลานัด</td>
												<td colspan="3" align="left" class="subTable"><input type="text" name="hourService" id="hourService" value="${informForm.hourService}" dojoType="dijit.form.NumberTextBox"  constraints="{min:0,max:23}" style="width: 40px;text-align: center;"  invalidMessage="กรุณากรอกข้อมูลให้ถูกต้อง" />:<input type="text" name="minuteSerivce" id="minuteSerivce" value="${informForm.minuteSerivce}" dojoType="dijit.form.NumberTextBox"  constraints="{min:0,max:59}" style="width: 40px;text-align: center;"  invalidMessage="กรุณากรอกข้อมูลให้ถูกต้อง" /> น.</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" >ระยะเวลา</td>
												<td colspan="3" align="left" class="subTable"><input type="text" name="dayService" id="dayService" value="${informForm.dayService}" dojoType="dijit.form.NumberTextBox"  constraints="{min:0,max:7}" style="width: 40px;text-align: center;"  invalidMessage="กรุณากรอกข้อมูลให้ถูกต้อง" /> วัน</td>
											</tr>
											<tr class="subHeadTable">
												<td align="left" valign="top">รายละเอียด</td>
												<td colspan="3" align="left" valign="top" class="subTable"><form:textarea path="descInform"/></td>
											</tr>
											<tr>
												  <td colspan="4" align="center" valign="top" class="subTable_button"><input name="submitButton" id="proceed" type="submit" class="styleButton" value="บันทึกการแก้ไข" />
											      <input name="resetButton" type="reset" class="styleButton" value="เริ่มใหม่" /> <input name="cancelButton" type="button" class="styleButton" value="ยกเลิก" onclick="location.href='statusInform.htm'" /></td>
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
dojo.require("dijit.form.Button");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dijit.form.SimpleTextarea");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.NumberTextBox");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.form.ValidationTextBox");
Spring.addDecoration(new Spring.ElementDecoration({elementId : "address", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:185px", trim : true, required : true}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "moo", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px",regExp:"[0-9]+", trim : true, required : true, invalidMessage: "กรุณากรอกข้อมูลให้ถูกต้อง"}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "road", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "tumbol", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "amphur", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "provinceId", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:185px"}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "postcode", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true,maxLength:5,regExp:"[0-9]{5}", invalidMessage: "กรุณากรอกข้อมูลให้ถูกต้อง"}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "tel", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true,maxLength:10,regExp:"[0-9]{9,10}", invalidMessage: "กรุณากรอกข้อมูลให้ถูกต้อง"}}))

Spring.addDecoration(new Spring.ElementDecoration({elementId : "contactId", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:175px",onChange:function(){if(dijit.byId('contactId').getValue()=='') return false;location.href='editInform.htm?informId=${informForm.informId}&contactId='+dijit.byId('contactId').getValue()},onFocus:function(){dijit.byId('contactId').setValue('')},onBlur:function(){dijit.byId('contactId').reset()}}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "contactName", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:185px", trim : true, required : true}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "contactDepart", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:185px", trim : true, required : true}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "contactTel", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true,maxLength:10,regExp:"[0-9]{9,10}", invalidMessage: "กรุณากรอกข้อมูลให้ถูกต้อง"}}))
Spring.addDecoration(new Spring.ElementDecoration({elementId : "contactDesc", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:185px", trim : true}}));

Spring.addDecoration(new Spring.ElementDecoration({elementId : "type", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:175px"}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "productId", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:200px"}}));
Spring.addDecoration(new Spring.ElementDecoration({elementId : "descInform", widgetType: "dijit.form.SimpleTextarea", widgetAttrs : {style:"width:200px", trim : true}}));

Spring.addDecoration(new Spring.ValidateAllDecoration({elementId:"proceed", event:"onclick"}));
</script>
</html>