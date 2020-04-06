<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/headerTop.jsp" %>
	<title>ระบบจัดการการบริหารลูกค้า :: ประวัติการบริการ</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp" %>

				<div class="content">
					<div class="Post">
						<div class="Post-body">
							<div class="Post-inner">
								<h2 class="PostHeaderIcon-wrapper">
									<span class="PostHeader">ประวัติการบริการ ${customer.name}</span></h2>
							    <div class="PostContent">
									<p>										
										<table width="100%" class="borderTable">
											<tr class="HeadTable">
												<td colspan="4" align="left" valign="top"><span class="style1">ข้อมูลลูกค้า</span></td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left">ชื่อหน่วยงาน</td>
												<td colspan="3" align="left" class="subTable">${customer.name}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" valign="top">เลขที่</td>
												<td width="30%" align="left" class="subTable">${customer.address}</td>
												<td width="20%" align="left" valign="top">หมู่ที่</td>
												<td width="30%" align="left" class="subTable">${customer.moo}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" valign="top">ถนน</td>
												<td width="30%" align="left" class="subTable">${customer.road}</td>
												<td width="20%" align="left" valign="top">ตำบล</td>
												<td width="30%" align="left" class="subTable">${customer.tumbol}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" valign="top">อำเภอ</td>
												<td width="30%" align="left" class="subTable">${customer.amphur}</td>
												<td width="20%" align="left" valign="top">จังหวัด</td>
												<td width="30%" align="left" class="subTable">${customer.province.name}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" valign="top">รหัสไปรษณีย์</td>
												<td colspan="3" align="left" class="subTable">${customer.postcode}</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%" align="left" valign="top">เบอร์โทร</td>
												<td colspan="3" align="left" class="subTable">${customer.telPhone}</td>
											</tr>
											<tr>
												<td width="20%" align="left" valign="top"  class="subHeadTable">ผู้ติดต่อ</td>
												<td colspan="3" align="left">
													<table width="100%">
														<tr class="subHeadTable">
															<td width="8%" align="center">ลำดับ</td>
															<td>ชื่อ</td>
															<td width="20%">ตำแหน่ง</td>
															<td width="20%">เบอร์โทร</td>
															<td width="12%" align="center">รายละเอียด</td>
														</tr>
														<c:forEach items="${contacts}" var="contact" varStatus="n">
														<tr class="<c:if test="${n.count%2 eq 1}">subTable</c:if>">
															<td align="center">${n.count}</td>
															<td>${contact.name }</td>
															<td>${contact.depart}</td>
															<td>${contact.tel}</td>
															<td align="center"><c:if test="${not empty contact.desc}"><img src="<c:url value="/images/note.png"/>" title="${contact.desc}" /></c:if></td>
														</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
											<tr>
												<td width="20%" align="left" valign="top"  class="subHeadTable">ระบบที่ติดตั้งแล้ว</td>
												<td colspan="3" align="left">
													<table width="100%">
														<tr class="subHeadTable">
															<td width="8%" align="center">ลำดับ</td>
															<td>ชื่อระบบ</td>
														</tr>
														<c:forEach items="${customer.products}" var="product" varStatus="n">
														<tr class="<c:if test="${n.count%2 eq 1}">subTable</c:if>">
															<td align="center">${n.count}</td>
															<td>${product.name}</td>
														</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
											<tr class="HeadTable">
												<td colspan="4" align="left" valign="top"><span class="style1">ข้อมูลการบริการ</span></td>
											</tr>
											<tr>
												<td colspan="4">
													<table width="100%">
														<tr class="subHeadTable">
															<td width="5%" align="center">ลำดับ</td>
															<td width="10%" align="center">ประเภท</td>
															<td width="25%">ระบบ</td>
															<td>วันเวลาแจ้งงาน</td>
															<td width="25%">ผู้แจ้ง</td>
															<td width="10%" align="center">สถานะ</td>
														</tr>
														<c:forEach items="${informs}" var="inform" varStatus="n">
														<tr class="<c:if test="${n.count%2 eq 1}">subTable</c:if>">
															<td align="center">${n.count}</td>
															<td align="center"><c:choose><c:when test="${inform.type eq 1}">การติดตั้ง</c:when><c:otherwise>การบริการ</c:otherwise></c:choose></td>
															<td>${inform.product.name}</td>
															<td>${inform.datetimeInform}</td>
															<td>${inform.informer.name}</td>
															<td align="center"><a href="../service/viewInform.htm?informId=${inform.id}"><c:choose><c:when test="${inform.status eq 1}">แจ้งงาน</c:when><c:when test="${inform.status eq 2 or inform.status eq 3}">รอการยืนยัน</c:when><c:when test="${inform.status eq 4}">กำลังดำเนิน</c:when><c:otherwise>เรียบร้อย</c:otherwise></c:choose></a></td>
														</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
											<tr>
												  <td colspan="4" align="center" valign="top" class="subTable_button"><input name="cancel" type="button" class="styleButton" value="กลับ" onclick="location.href='history.htm'" /></td>
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
	<div  id="pageNumber" menu="m2" sub="m2s2"  class="cleared"></div>
	<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div>
<%@ include file="/WEB-INF/jsp/version.jsp" %>
</body>
</html>