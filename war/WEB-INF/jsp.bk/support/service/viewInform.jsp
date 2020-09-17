<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop.jsp"%>
<title>ระบบจัดการการบริหารลูกค้า ::
	รายละเอียดการบริการของการแจ้งงาน</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp"%>

<div class="content">
	<div class="Post">
		<div class="Post-body">
			<div class="Post-inner">
				<h2 class="PostHeaderIcon-wrapper">
					<span class="PostHeader">รายละเอียดการบริการของการแจ้งงาน</span>
				</h2>
				<div class="PostContent">
					<p>
					<table width="100%" class="borderTable">
						<tr class="HeadTable">
							<td colspan="4" align="left" valign="top"><span
								class="style1">ข้อมูลลูกค้า</span></td>
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
							<td colspan="4" align="left" valign="top"><span
								class="style1">ข้อมูลการแจ้งงาน</span></td>
						</tr>
						<tr class="subHeadTable">
							<td width="20%" align="left">ประเภทการแจ้งงาน</td>
							<td width="30%" align="left" class="subTable"><c:choose>
									<c:when test="${inform.type eq 1}">การติดตั้ง</c:when>
									<c:otherwise>การบริการ</c:otherwise>
								</c:choose></td>
							<td width="20%" align="left">เลขที่ PO</td>
							<td width="30%" align="left" class="subTable">${inform.codePO }</td>
						</tr>
						<tr class="subHeadTable">
							<td width="20%" align="left">ระบบ</td>
							<td colspan="3" align="left" class="subTable">${inform.product.name}</td>
						</tr>
						<tr class="subHeadTable">
							<td width="20%" align="left">นัดวันที่</td>
							<td width="30%" align="left" class="subTable">${inform.dateService}</td>
							<td width="20%" align="left">เวลา</td>
							<td width="30%" align="left" class="subTable">${inform.timeService}</td>
						</tr>
						<tr class="subHeadTable">
							<td width="20%" align="left">ระยะเวลา</td>
							<td colspan="3" align="left" class="subTable">${inform.dayService}
								วัน</td>
						</tr>
						<tr class="subHeadTable">
							<td align="left" valign="top">รายละเอียดการแจ้ง</td>
							<td colspan="3" align="left" valign="top" class="subTable">${inform.descInform}</td>
						</tr>
						<tr class="subHeadTable">
							<td align="left" valign="top">รายละเอียดการบริการ</td>
							<td colspan="3" align="left" valign="top" class="subTable">${inform.descSupport}</td>
						</tr>
						<tr class="HeadTable">
							<td colspan="4" align="left" valign="top">รายละเอียดการทำงาน</td>
						</tr>
						<tr>
							<td colspan="4">
								<table width="100%">
									<tr class="subHeadTable">
										<td width="6%" align="center">ลำดับ</td>
										<td width="15%" align="center">วันดำเนินงาน</td>
										<td width="19%" align="center">ช่วงเวลา</td>
										<td width="18%">รายละเอียดงาน</td>
										<td width="7%" align="center">OnSite</td>
										<td width="25%">ผู้ดำเนินการ</td>
										<td width="10%" align="center">สถานะ</td>
									</tr>
									<c:forEach items="${informDetails}" var="informDetail"
										varStatus="n">
										<tr class="subTable">
											<td align="center">${n.count}</td>
											<td align="center">${informDetail.date}</td>
											<td align="center">${informDetail.timeStart}<c:if
													test="${not empty informDetail.timeEnd}">-${informDetail.timeEnd}</c:if></td>
											<td>${informDetail.desc}</td>
											<td align="center"><c:choose>
													<c:when test="${informDetail.onSite eq 1}">ใช่</c:when>
													<c:otherwise>ไม่</c:otherwise>
												</c:choose></td>
											<td>${informDetail.supportor.name}</td>
											<td align="center"><c:choose>
													<c:when test="${informDetail.status eq 1}">นัดบริการ</c:when>
													<c:when test="${informDetail.status eq 2}">บริการแล้ว</c:when>
													<c:otherwise>เรียบร้อย</c:otherwise>
												</c:choose></td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center" valign="top"
								class="subTable_button"><input name="back" type="button"
								class="styleButton" value="กลับ" onclick="history.back()" /></td>
						</tr>
					</table>
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
  dojo.addOnLoad(alertUser('${alert.text}'));
  dojo.require("dijit.form.Button");
  dojo.require("dijit.form.FilteringSelect");
  dojo.require("dijit.form.DateTextBox");
  dojo.require("dijit.form.ValidationTextBox");
  dojo.require("dijit.form.NumberTextBox");

  Spring.addDecoration(new Spring.ElementDecoration({elementId : "desc", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:450px"}}));
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "supporterId", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:195px"}}));
  Spring.addDecoration(new Spring.ValidateAllDecoration({elementId:"proceed", event:"onclick"}));
</script>
</html>