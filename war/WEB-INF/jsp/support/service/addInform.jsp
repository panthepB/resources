<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop.jsp"%>
<title>ระบบจัดการการบริหารลูกค้า :: การแจ้งงานบริการ</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp"%>

<div class="content">
	<div class="Post">
		<div class="Post-body">
			<div class="Post-inner">
				<h2 class="PostHeaderIcon-wrapper">
					<span class="PostHeader">การแจ้งงานบริการ</span>
				</h2>
				<div class="PostContent">
					<p>
						<form:form method="POST" commandName="informForm"
							action="addInformNow.htm" name="form1">

							<table width="100%" class="borderTable">
								<tr class="HeadTable">
									<td colspan="4" align="left" valign="top"><span
										class="style1">ข้อมูลลูกค้า</span></td>
								</tr>
								<tr class="subHeadTable">
									<td width="20%" align="left">ลูกค้า</td>
									<td colspan="3" align="left" class="subTable"><form:select
											path="cusId">
											<c:if test="${empty param.cusId or param.cusId==0}">
												<form:option label="ลูกค้าใหม่" value="0" />
											</c:if>
											<form:options items="${customers}" itemLabel="name"
												itemValue="id" />
										</form:select> <c:if
											test="${not empty informForm.cusId and informForm.cusId ne 0}">
											<input type="button" value="ลูกค้าใหม่" class="styleButton"
												onclick="location.href='addInform.htm?cusId=0'" />
										</c:if></td>
								</tr>
								<tr class="subHeadTable">
									<td width="20%" align="left">ชื่อ</td>
									<td colspan="3" align="left" class="subTable"><form:input
											path="cusName" /></td>
								</tr>
								<tr class="subHeadTable">
									<td width="20%" align="left" valign="top">เลขที่</td>
									<td width="30%" align="left" class="subTable"><form:input
											path="address" /></td>
									<td width="20%" align="left" valign="top">หมู่ที่</td>
									<td width="30%" align="left" class="subTable"><form:input
											path="moo" /></td>
								</tr>
								<tr class="subHeadTable">
									<td align="left" valign="top">ถนน</td>
									<td align="left" class="subTable"><form:input path="road" /></td>
									<td align="left" valign="top">ตำบล</td>
									<td align="left" class="subTable"><form:input
											path="tumbol" /></td>
								</tr>
								<tr class="subHeadTable">
									<td align="left" valign="top">อำเภอ</td>
									<td align="left" class="subTable"><form:input
											path="amphur" /></td>
									<td align="left" valign="top">จังหวัด</td>
									<td align="left" class="subTable"><form:select
											path="provinceId">
											<form:options items="${provinces}" itemLabel="name"
												itemValue="id" />
										</form:select></td>
								</tr>
								<tr class="subHeadTable">
									<td align="left" valign="top">รหัสไปรษณีย์</td>
									<td colspan="3" align="left" class="subTable"><form:input
											path="postcode" /></td>
								</tr>
								<tr class="subHeadTable">
									<td align="left" valign="top">เบอร์โทร</td>
									<td colspan="3" align="left" class="subTable"><form:input
											path="tel" /></td>
								</tr>
								<tr class="subHeadTable">
									<td align="left" valign="top">ผู้ติดต่อ</td>
									<td colspan="3" align="left" class="subTable"><form:select
											path="contactId">
											<form:option label="ผู้ติดต่อใหม่" value="0" />
											<form:options items="${contacts}" itemLabel="name"
												itemValue="id" />
										</form:select> <c:if
											test="${not empty informForm.contactId and informForm.contactId ne 0}">
											<input type="button" value="ผู้ติดต่อใหม่"
												class="styleButton"
												onclick="location.href='addInform.htm?cusId=<c:choose><c:when test="${empty informForm.cusId}">0</c:when><c:otherwise>${informForm.cusId}</c:otherwise></c:choose>&contactId=0'" />
										</c:if></td>
								</tr>
								<tr class="subHeadTable">
									<td align="left" valign="top">ชื่อ</td>
									<td align="left" class="subTable"><form:input
											path="contactName" /></td>
									<td align="left" valign="top">ตำแหน่ง</td>
									<td align="left" class="subTable"><form:input
											path="contactDepart" /></td>
								</tr>
								<tr class="subHeadTable">
									<td align="left" valign="top">เบอร์ติดต่อ</td>
									<td align="left" class="subTable"><form:input
											path="contactTel" /></td>
									<td align="left" valign="top">รายละเอียด</td>
									<td align="left" class="subTable"><form:input
											path="contactDesc" /></td>
								</tr>
								<tr class="HeadTable">
									<td colspan="4" align="left" valign="top"><span
										class="style1">ข้อมูลการแจ้งงาน</span></td>
								</tr>
								<tr class="subHeadTable">
									<td width="20%" align="left">ประเภทการแจ้งงาน</td>
									<td width="30%" align="left" class="subTable"><form:select
											path="type">
											<form:option value="1" label="การติดตั้ง" />
											<form:option value="2" label="การบริการ" />
										</form:select></td>
									<td width="20%" align="left">เลขที่ PO</td>
									<td width="30%" align="left" class="subTable"><form:input
											path="codePO" /></td>
								</tr>
								<tr class="subHeadTable">
									<td width="20%" align="left">ระบบ</td>
									<td colspan="3" align="left" class="subTable"><form:select
											path="productId">
											<form:options items="${products}" itemLabel="name"
												itemValue="id" />
										</form:select></td>
								</tr>
								<tr class="subHeadTable">
									<td width="20%" align="left">วันนัด</td>
									<td colspan="3" align="left" class="subTable"><input
										type="text" name="dateService" id="dateService"
										value="${informForm.dateService}"
										dojoType="dijit.form.DateTextBox" /></td>
								</tr>
								<tr class="subHeadTable">
									<td width="20%" align="left">เวลานัด</td>
									<td colspan="3" align="left" class="subTable"><input
										type="text" name="hourService" id="hourService"
										value="${informForm.hourService}"
										dojoType="dijit.form.NumberTextBox"
										constraints="{min:0,max:23}"
										style="width: 40px; text-align: center;"
										invalidMessage="กรุณากรอกข้อมูลให้ถูกต้อง" />:<input
										type="text" name="minuteSerivce" id="minuteSerivce"
										value="${informForm.minuteSerivce}"
										dojoType="dijit.form.NumberTextBox"
										constraints="{min:0,max:59}"
										style="width: 40px; text-align: center;"
										invalidMessage="กรุณากรอกข้อมูลให้ถูกต้อง" /> น.</td>
								</tr>
								<tr class="subHeadTable">
									<td width="20%" align="left">ระยะเวลา</td>
									<td colspan="3" align="left" class="subTable"><input
										type="text" name="dayService" id="dayService"
										value="${informForm.dayService}"
										dojoType="dijit.form.NumberTextBox"
										constraints="{min:0,max:14}"
										style="width: 40px; text-align: center;"
										invalidMessage="กรุณากรอกข้อมูลให้ถูกต้อง" /> วัน</td>
								</tr>
								<tr class="subHeadTable">
									<td align="left" valign="top">รายละเอียด</td>
									<td colspan="3" align="left" valign="top" class="subTable"><form:textarea
											path="descInform" /></td>
								</tr>
								<tr>
									<td colspan="4" align="center" valign="top"
										class="subTable_button"><input name="submitButton"
										id="proceed" type="submit" class="styleButton" value="แจ้งงาน" />
										<input name="resetButton" type="reset" class="styleButton"
										value="เริ่มใหม่" /></td>
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
<div id="pageNumber" menu="m2" sub="m2s2" class="cleared"></div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</div>
<%@ include file="/WEB-INF/jsp/version.jsp"%>
</body>
<script type="text/javascript">
  function checkCus(){
	  if(dijit.byId('cusId').getValue()==0)
	  	return false;
	  location.href='addInform.htm?cusId='+dijit.byId('cusId').getValue();
  }
  function checkCon(){
	  if(dijit.byId('contactId').getValue()==0)
		  return false;
	  location.href='addInform.htm?cusId=<c:choose><c:when test="${empty informForm.cusId}">0</c:when><c:otherwise>${informForm.cusId}</c:otherwise></c:choose>&contactId='+dijit.byId('contactId').getValue();
  }
  function checkType(){
	  if(dijit.byId('type').getValue()==2)
		  dijit.byId('codePO').setAttribute('required',false);
	  else
		  dijit.byId('codePO').setAttribute('required',true);
  }
  
  dojo.addOnLoad(alertUser('${alert.text}'));
  dojo.require("dijit.form.Button");
  dojo.require("dijit.form.FilteringSelect");
  dojo.require("dijit.form.SimpleTextarea");
  dojo.require("dijit.form.DateTextBox");
  dojo.require("dijit.form.NumberTextBox");
  dojo.require("dijit.form.ValidationTextBox");
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "cusId", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:285px",onChange:checkCus,onFocus:function(){dijit.byId('cusId').setValue('')},onBlur:function(){dijit.byId('cusId').reset()}, invalidMessage: "ไม่พบข้อมูล"}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "cusName", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:285px", trim : true, required : true}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "address", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:185px", trim : true, required : true}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "moo", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px",regExp:"[0-9]+", trim : true, required : true, invalidMessage: "กรุณากรอกข้อมูลให้ถูกต้อง"}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "road", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "tumbol", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "amphur", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "provinceId", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:185px"}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "postcode", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true,maxLength:5,regExp:"[0-9]{5}", invalidMessage: "กรุณากรอกข้อมูลให้ถูกต้อง"}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "tel", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true,maxLength:10,regExp:"[0-9]{9,10}", invalidMessage: "กรุณากรอกข้อมูลให้ถูกต้อง"}}))
  
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "contactId", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:175px",onChange:checkCon,onFocus:function(){dijit.byId('contactId').setValue('')},onBlur:function(){dijit.byId('contactId').reset()}<c:if test="${empty param.cusId or param.cusId==0}">,readOnly:true</c:if>}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "contactName", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:185px", trim : true, required : true}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "contactDepart", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:185px", trim : true, required : true}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "contactTel", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true,maxLength:10,regExp:"[0-9]{9,10}", invalidMessage: "กรุณากรอกข้อมูลให้ถูกต้อง"}}))
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "contactDesc", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:185px", trim : true}}));
  
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "type", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true,onChange:checkType, style:"width:175px"}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "codePO", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:185px", trim : true, required : true}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "productId", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:200px"}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "descInform", widgetType: "dijit.form.SimpleTextarea", widgetAttrs : {style:"width:200px", trim : true}}));
  
  Spring.addDecoration(new Spring.ValidateAllDecoration({elementId:"proceed", event:"onclick"}));
</script>
</html>