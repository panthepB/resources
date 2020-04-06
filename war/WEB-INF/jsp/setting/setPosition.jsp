<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/headerTop.jsp" %>
	<title>ระบบจัดการการบริหารลูกค้า :: กำหนดตำแหน่งในระบบ</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp" %>

				<div class="content">
					<div class="Post">
						<div class="Post-body">
							<div class="Post-inner">
								<h2 class="PostHeaderIcon-wrapper">กำหนดตำแหน่งในระบบ</h2>
								<div class="PostContent">
								<p>
                                  <table width="80%" class="borderTable">
      								<tr class="HeadTable">
                                        <td colspan="4">รายการตำแหน่งในระบบ</td>
                                    </tr>
                                    <tr class="subHeadTable">
                                        <td width="10%" align="center">ลำดับที่</td>
                                        <td width="40%" align="left">ชื่อตำแหน่ง</td>
                                        <td width="40%" align="left">ชื่อตำแหน่งภาษาอังกฤษ</td>
                                        <td align="center">สถานะ</td>
                                    </tr>
                                    <c:forEach items="${positions}" var="position" varStatus="n"> 
                                      <tr class="<c:if test="${n.count % 2 != 0}">subTable</c:if>">
                                        <td align="center">${n.count }</td>
                                        <td align="left"><a href="?id=${position.id}">${position.name}</a></td>
                                        <td align="left">${position.nameEng}</td>
                                        <td align="center"><c:choose>
                                        						<c:when test="${position.status==1}">เปิด</c:when>
                                        						<c:otherwise>ปิด</c:otherwise>
                                        					</c:choose></td>
                                    </tr>
            						</c:forEach>
                                  </table>
                                    </p>
                                    <p>
                                    <form:form method="POST" commandName="positionForm" action="savePosition.htm">
								      <table width="90%" class="borderTable">
                                        <tr class="HeadTable">
                                          <td colspan="2">ฟอร์มการกำหนดข้อมูลตำแหน่งในระบบ<form:hidden path="school" /></td>
                                        </tr>
                                        <tr class="subTable">
                                          <td width="25%" class="subHeadTable">ชื่อตำแหน่ง</td>
                                          <td><form:input path="name" /><form:hidden path="id" /></td>
                                        </tr>
                                        <tr class="subTable">
                                          <td width="25%" class="subHeadTable">ชื่อตำแหน่งภาษาอังกฤษ</td>
                                          <td><form:input path="nameEng" /></td>
                                        </tr>
                                        <tr class="subTable">
                                          <td valign="top" class="subHeadTable">รายละเอียด</td>
                                          <td><form:textarea path="desc" cols="35" rows="6" /></td>
                                        </tr>
                                        <tr class="subTable">
                                          <td valign="top" class="subHeadTable">สถานะ</td>
                                          <td><form:select path="status">
                                           <form:option value="-1" label="กรุณาเลือก" />
                                          <form:option value="1" label="เปิด" />
                                          <form:option value="0" label="ปิด" />
                                          </form:select></td>
                                        </tr>
                                        <tr class="subTable">
                                         <td colspan="2" class="subTable_button"><input type="hidden" id="submitStatus" name="submitStatus" /> <c:choose>
                                              <c:when test="${empty param.id }"><input id="proceed" type="submit" class="styleButton" value="เพิ่มข้อมูล" onclick="goSubmit('add')" /> <input type="reset" class="styleButton" value="เริ่มใหม่" /></c:when>
                                              <c:otherwise><input id="proceed" type="submit" class="styleButton" value="บันทึกการแก้ไขข้อมูล" onclick="goSubmit('save')" /> <input type="submit" class="styleButton" value="ลบข้อมูล" onclick="goSubmit('del');return confirm('คุณแน่ใจจะลบสังกัดนี้หรือไม่');" /> <input type="button" class="styleButton" value="ยกเลิก" onclick="location.href='setPosition.htm'" /></c:otherwise>
                                              </c:choose></td>
                                        </tr>
                                      </table>
                                  </form:form>
								    </p>
								    <p>
								    * ชื่อตำแหน่งภาษาอังกฤษของตำแหน่งผู้ดูแลระบบต้องเป็น admin<br/>
								    * ชื่อตำแหน่งภาษาอังกฤษของตำแหน่งลูกค้าต้องเป็น customer<br/>
								    </p>   
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
<script type="text/javascript">;
	dojo.require("dijit.form.Textarea");
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dijit.form.ValidationTextBox");
	Spring.addDecoration(new Spring.ElementDecoration({elementId : "name", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:200px" ,invalidMessage:'คุณจำเป็นต้องกรอกข้อมูลนี้', required : true }}));
	Spring.addDecoration(new Spring.ElementDecoration({elementId : "nameEng", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {style:"width:200px",invalidMessage:'คุณจำเป็นต้องกรอกข้อมูลนี้' , required : true }}));
	Spring.addDecoration(new Spring.ElementDecoration({elementId : "desc", widgetType : "dijit.form.SimpleTextarea", widgetAttrs : {style:"width:345px; height:110px"}}));
	Spring.addDecoration(new Spring.ElementDecoration({elementId : "status", widgetType: "dijit.form.FilteringSelect", widgetAttrs : {hasDownArrow : true, style:"width:110px"}}));
	Spring.addDecoration(new Spring.ValidateAllDecoration({elementId:'proceed', event:'onclick'}));
</script>
</html>