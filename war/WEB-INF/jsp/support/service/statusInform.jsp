<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/headerTop.jsp" %>
	<title>ระบบจัดการการบริหารลูกค้า :: สถานะการแจ้งงาน</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp" %>

				<div class="content">
					<div class="Post">
						<div class="Post-body">
							<div class="Post-inner">
								<h2 class="PostHeaderIcon-wrapper"><span class="PostHeader">สถานะการแจ้งงาน</span></h2>
								<div class="PostContent">
									<p>
										<table width="100%" class="borderTable">
											<tr class="HeadTable">
												<td width="6%" align="center">ลำดับ</td>
												<td>หน่วยงาน</td>
												<td width="22%">ระบบ</td>
												<td align="center">ประเภท</td>
												<td width="22%" align="center">วันเวลาแจ้ง</td>
												<td width="12%" align="center">สถานะ</td>
											</tr>
											<c:forEach items="${informs}" var="inform" varStatus="n">
											<tr class="<c:if test="${n.count%2 eq 1}">subTable</c:if>">
												<td align="center">${n.count}</td>
												<td>${inform.customer.name}</td>
												<td>${inform.product.name}</td>
												<td align="center"><c:choose><c:when test="${inform.type eq 1}">การติดตั้ง</c:when><c:otherwise>การบริการ</c:otherwise></c:choose></td>
												<td align="center">${inform.datetimeInform}</td>
												<td align="center"><c:choose>
													<c:when test="${inform.status eq 1}"><a href="editInform.htm?informId=${inform.id}">แจ้งงาน</a></c:when>
													<c:when test="${inform.status eq 2}"><a href="sconfirmInform.htm?informId=${inform.id}">ยืนยัน</a></c:when>
													<c:when test="${inform.status eq 3}">รอผู้บริการยืนยัน</c:when>
													<c:when test="${inform.status eq 4}"><a href="viewInform.htm?informId=${inform.id}">กำลังดำเนิน</a></c:when>
												</c:choose></td>
											</tr>
											</c:forEach>
										</table>
									</p>
									<p>
										* คลิก แจ้งงาน เพื่อแก้ไขรายละเอียดการแจ้งงาน<br />
										* คลิก ยืนยัน เพื่อตอบรับวันเวลาที่นัดหมาย<br />
										* คลิก กำลังดำเนิน เพื่อดูสถานะการดำเนินงานในปัจจุบัน<br />
									</p>
								</div>
								<div class="cleared"></div>
							</div>
						</div>
					</div>				
				</div>
				
	</div>
	<div id="pageNumber" menu="m2" sub="m2s2" class="cleared"></div>
	<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div>
<%@ include file="/WEB-INF/jsp/version.jsp" %>
</body>
</html>