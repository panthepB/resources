<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/headerTop.jsp" %>
	<title>ระบบจัดการการบริหารลูกค้า :: กำหนดสิทธิการใช้งาน</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp" %>

				<div class="content">
					<div class="Post">
						<div class="Post-body">
							<div class="Post-inner">
								<h2 class="PostHeaderIcon-wrapper">กำหนดสิทธิการใช้งาน</h2>
								<div class="PostContent">
                                	<p>
                                  <table width="65%" class="borderTable">
      <tr class="HeadTable">
                                        <td colspan="3">รายการตำแหน่งในระบบ</td>
                                    </tr>
                                      <tr class="subHeadTable">
                                        <td width="10%" height="22" align="center">ลำดับที่</td>
                                        <td align="left">ชื่อตำแหน่ง</td>
                                        <td width="15%" align="center">สถานะ</td>
                                    </tr>
                                    <c:forEach items="${positions}" var="position" varStatus="n">
                                      <tr class="<c:if test="${n.count % 2 != 0}">subTable</c:if>">
                                        <td align="center">${n.count}</td>
                                        <td align="left"><a href="?id=${position.id}">${position.name}</a></td>
                                        <td align="center"><c:choose><c:when test="${position.status eq 1}">เปิด</c:when><c:when test="${position.status eq 2}">ปิด</c:when></c:choose></td>
                                    </tr>
                                    </c:forEach>
                                  </table>
                                    </p>
                                    <c:if test="${not empty privilegeForm}">
                                     <p>
                                     <form:form method="POST" commandName="privilegeForm" action="savePrivilege.htm"><form:hidden path="positionId"/>
                                    <table width="65%" class="borderTable">
                                        <tr>
                                          <td colspan="2" class="HeadTable">รายละเอียดสิทธิการใช้งาน</td>
                                        </tr>
                                        <tr class="subTable">
                                          <td width="20%" class="subHeadTable">ชื่อตำแหน่ง</td>
                                          <td>${privilegeForm.position.name}</td>
                                        </tr>
                                        <tr class="subTable">
                                          <td valign="top" class="subHeadTable">รายละเอียด</td>
                                          <td valign="top">
                                          <table width="100%">
                                          <c:forEach items="${groups}" var="group" varStatus="n">
                                          	<tr>
                                              <td colspan="3" class="subHeadTable">${group.name}</td>
                                            </tr>
                                            <c:forEach items="${group.modules}" var="module" varStatus="n2">
                                            	<tr>
                                            		<td>&nbsp;</td>
                                            		<td>&nbsp;</td>
                                              		<td>${module.name}</td>
                                           		</tr>
                                           		<c:forEach items="${module.tasks}" var="task" varStatus="n3">
                                           			<tr>
		                                              <td>&nbsp;</td>
		                                              <td>&nbsp;</td>
		                                              <td><form:checkbox path="positionTasks" value="${task.id}" label="${task.name}"/></td>
		                                            </tr>
                                           		</c:forEach>
                                            </c:forEach>
                                          </c:forEach>
                                          </table>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td colspan="2" class="subTable_button"><input type="submit" name="Button2" value="บันทึกข้อมูล" class="styleButton" />
                                            <input type="reset" name="Submit22" value="เริ่มใหม่" class="styleButton" /> <input type="button" value="ยกเลิก" class="styleButton" onclick="location.href='setPrivilege.htm'" /></td>
                                        </tr>
                                    </table>
                                  </form:form>
                                    </p>
                                    </c:if>
							  </div>
								<div class="cleared"></div>
							</div>
						</div>
					</div>
				</div>

	</div>
	<div  id="pageNumber" menu="m4" sub="m4s1"  class="cleared"></div>
	<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div>
<%@ include file="/WEB-INF/jsp/version.jsp" %>
</body>
</html>