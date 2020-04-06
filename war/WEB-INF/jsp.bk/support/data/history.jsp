<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/headerTop.jsp" %>
	<title>ระบบจัดการการบริหารลูกค้า :: ประวัติการบริการลูกค้า</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp" %>

				<div class="content">
					<div class="Post">
						<div class="Post-body">
							<div class="Post-inner">
								<h2 class="PostHeaderIcon-wrapper"><span class="PostHeader">ประวัติการบริการลูกค้า</span></h2>
								<div class="PostContent">
									<p>
									<form:form method="POST"  commandName="searchForm" action="resHistory.htm">
										<table width="50%" class="borderTable">
											<tr class="HeadTable">
												<td colspan="2">การกรองข้อมูล</td>
											</tr>
											<tr class="subHeadTable">
												<td width="20%">คีย์เวิร์ด</td>
												<td class="subTable"><form:input path="keyword"/> <input type="submit" value="กรอง" class="styleButton" /> <input type="reset" value="เริ่มใหม่" onclick="location.href='history.htm'" class="styleButton" /></td>
											</tr>
										</table>
									</form:form>
									</p>
									<p>
										<table width="100%" class="borderTable">
											<tr class="HeadTable">
												<td width="6%" align="center">ลำดับ</td>
												<td>หน่วยงาน</td>
												<td width="15%" align="center">วันที่บริการล่าสุด</td>
												<td width="12%" align="center">จำนวนการแจ้ง</td>
											</tr>
											<c:forEach items="${customerViews}" var="customerView" varStatus="n">
											<tr class="<c:if test="${n.count%2 eq 1}">subTable</c:if>">
												<td align="center">${n.count}</td>
												<td><a href="viewHistory.htm?cusId=${customerView.cusId}">${customerView.cusName}</a></td>
												<td align="center">${customerView.informLast}</td>
												<td align="center">${customerView.amountInform}</td>
											</tr>
											</c:forEach>
										</table>
									</p>
									<p>
										* คลิก ชื่อหน่วยงาน เพื่อแสดงประวัติการบริการ<br />
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
<script type="text/javascript" >
  dojo.require("dijit.form.ValidationTextBox");
  Spring.addDecoration(new Spring.ElementDecoration({elementId : "keyword", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : { style:"width:185px", trim : true}}));
</script>
</html>