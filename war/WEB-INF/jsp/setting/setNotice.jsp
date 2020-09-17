<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop.jsp"%>
<title>ระบบจัดการการบริหารลูกค้า :: กำหนดประกาศโรงเรียน</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp"%>

<div class="content">
	<div class="Post">
		<div class="Post-body">
			<div class="Post-inner">
				<h2 class="PostHeaderIcon-wrapper">กำหนดประกาศโรงเรียน</h2>
				<div class="PostContent">
					<p>
					<table width="100%" class="borderTable">
						<tr class="HeadTable">
							<td colspan="6">รายการประกาศโรงเรียนบ</td>
						</tr>
						<tr class="subTable">
							<td width="10%" align="center" class="subHeadTable">ลำดับ</td>
							<td class="subHeadTable">หัวข้อ</td>
							<td width="25%" align="center" class="subHeadTable">สร้างเมื่อ</td>
						</tr>
						<c:forEach items="${notices}" var="notice" varStatus="n">
							<tr class="<c:if test="${n.count % 2 != 0}">subTable</c:if>">
								<td align="center">${n.count}</td>
								<td><a href="?id=${notice.id}">${notice.subject}</a></td>
								<td align="center">${notice.createDate}
									${notice.createTime}</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="6"><form:form method="POST"
									commandName="noticeForm" action="saveNotice.htm">
									<form:hidden path="id" />
									<table width="100%">

										<tr class="subTable">
											<td width="20%" class="subHeadTable">หัวข้อ</td>
											<td><form:input path="subject" /></td>
										</tr>
										<tr class="subTable">
											<td valign="top" class="subHeadTable">รายละเอียด</td>
											<td><form:textarea path="desc" /></td>
										</tr>
										<tr>
											<td colspan="2" class="subTable_button"><input
												type="hidden" id="submitStatus" name="submitStatus" /> <c:choose>
													<c:when test="${empty param.id }">
														<input id="proceed" type="submit" class="styleButton"
															value="เพิ่มข้อมูล" onclick="goSubmit('add')" />
														<input type="reset" class="styleButton" value="เริ่มใหม่" />
													</c:when>
													<c:otherwise>
														<input id="proceed" type="submit" class="styleButton"
															value="บันทึกการแก้ไขข้อมูล" onclick="goSubmit('save')" />
														<input type="submit" class="styleButton" value="ลบข้อมูล"
															onclick="goSubmit('del');return confirm('คุณแน่ใจจะลบประกาศนี้หรือไม่');" />
														<input type="button" class="styleButton" value="ยกเลิก"
															onclick="location.href='setNotice.htm'" />
													</c:otherwise>
												</c:choose></td>
										</tr>
									</table>
								</form:form></td>
						</tr>
					</table>
					</p>
					<p>
						* คลิก หัวข้อประกาศ เพื่อแก้ไขข้อมูลของประกาศ <br />
					</p>
				</div>
				<div class="cleared"></div>
			</div>
		</div>
	</div>
</div>

</div>
<div id="pageNumber" menu="m4" sub="m4s1" class="cleared"></div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</div>
<%@ include file="/WEB-INF/jsp/version.jsp"%>
</body>
<script type="text/javascript">
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dijit.form.SimpleTextarea");
	dojo.require("dijit.form.ValidationTextBox");
	Spring.addDecoration(new Spring.ElementDecoration({elementId : "subject", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:300px", required : true }}));
	Spring.addDecoration(new Spring.ElementDecoration({elementId : "desc", widgetType : "dijit.form.SimpleTextarea", widgetAttrs : { style:"width:450px; height:200px"}}));
	Spring.addDecoration(new Spring.ElementDecoration({elementId : "status", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:110px", required : true}}));
	Spring.addDecoration(new Spring.ValidateAllDecoration({elementId:"proceed", event:"onclick"}));
</script>
</html>