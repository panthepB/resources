<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="sidebar1">
	<dl class="curved">
		<dt>ระบบสมาชิก</dt>
		<dd>
			<form:form method="POST" commandName="loginForm" action="login.htm">
				<table width="100%" border="0">
					<tr>
						<td>ชื่อผู้ใช้</td>
					</tr>
					<tr>
						<td><form:input path="username" /></td>
					</tr>
					<tr>
						<td>รหัสผ่าน</td>
					</tr>
					<tr>
						<td><form:password path="password" /></td>
					</tr>
					<tr height="5px">
						<td></td>
					</tr>
					<tr>
						<td><input name="proceed" id="proceed" type="submit"
							class="styleButton" value="เข้าสู่ระบบ"></td>
					</tr>
				</table>
			</form:form>
			<script type="text/javascript">
	dojo.require("dijit.form.ValidationTextBox");
	Spring.addDecoration(new Spring.ElementDecoration({elementId : "username", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:195px", required : false,invalidMessage:'คุณจำเป็นต้องกรอกข้อมูลนี้'}}));
	Spring.addDecoration(new Spring.ElementDecoration({elementId : "password", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:195px", required : false,invalidMessage:'คุณจำเป็นต้องกรอกข้อมูลนี้'}}));
	//Spring.addDecoration(new Spring.ValidateAllDecoration({elementId:'proceed', event:'onclick'}));
</script>
			<p class="last"></p>
		</dd>

	</dl>
	<dl class="curved">
		<dt>บริการออนไลน์</dt>
		<dd>
			<p>
				<a href="${companyUrl}"><img
					src="<c:url value="/images/post_company.jpg"/>"
					title="ทางเข้าบริษัท" /></a>
			</p>
			<p>
				<a href="http://www.gygar.com/" target="_blank"><img
					src="<c:url value="/images/post_gygar.jpg"/>"
					title="ผู้พัฒนาและจัดจำหน่าย" /></a>
			</p>
			<p class="last"></p>
		</dd>
	</dl>

</div>