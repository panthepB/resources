<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<!-- /top navigation -->

<!-- <link rel="stylesheet" type="text/css" href="http://sysapp.itoffside.com/datetimepicker/jquery.datetimepicker.css"> -->
<!-- <script type="text/javascript" src="http://sysapp.itoffside.com/datetimepicker/jquery.js"></script> -->
<!-- <script type="text/javascript" src="http://sysapp.itoffside.com/datetimepicker/jquery.datetimepicker.js"></script> -->

<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>


<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">

						<script type="text/javascript">
								$(function() {
									var availableTags =  ${customers} ;
									$("#customers").autocomplete({
										source : availableTags
									});
								});
							</script>
							
							<script type="text/javascript">
								$(function() {
									var availableTags =  ${sales} ;
									$("#sales").autocomplete({
										source : availableTags
									});
								});
							</script>
							
							<script type="text/javascript">
								$(function() {
									var availableTags =  ${vehicleTypes} ;
									$("#vehicleTypes").autocomplete({
										source : availableTags
									});
								});
							</script>


<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>เพิ่มข้อมูลมาสเตอร์ไฟล์</h3>
		</div>
	</div>
	<div class="clearfix"></div>

	<form:form method="POST" commandName="masterFileForm" action="addMasterFile.htm">

		<div class="panel panel-info">

			<div class="panel-heading">ข้อมูลมาสเตอร์ไฟล์</div>
			<div class="panel-body">
				<form role="form">
	

					<script type="text/javascript">
						function checkChassisNo() {
							var elem = document
									.getElementById('vehicleChassisNo').value;
							if (!elem.match(/^([a-z0-9\_])+$/i)) {
								//alert("กรอกได้เฉพาะ a-Z, A-Z, 0-9 และ _ (underscore)");
								document.getElementById('vehicleChassisNo').value = "";
							}
						}
					</script>
					<script type="text/javascript">
						function checkIMEI() {
							var elem = document.getElementById('imei').value;
							if (!elem.match(/^([a-z0-9\_])+$/i)) {
								//alert("กรอกได้เฉพาะ a-Z, A-Z, 0-9 และ _ (underscore)");
								document.getElementById('imei').value = "";
							}
						}
					</script>
					
							

<!-- 					<div class="form-group"> -->
<!-- 						<label for="tags">Tags: </label> <input id="tags" class="form-control"> -->
<!-- 					</div> -->
					
					
					
					<div class="form-group">
						<label>ชื่อลูกค้า</label>
<!-- 						<input id="tags" class="form-control"> -->
						<form:input path="customerName" id="customers" autocomplete="on" maxlength="50" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>ชื่อผู้ขาย</label>
						<form:input path="saleName" id="sales" autocomplete="on" maxlength="50" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>IMEI</label>
						<form:input path="imei" maxlength="20" id="imei" onkeyup="checkIMEI();" cssClass="form-control" />
					</div>
					<div class="form-group">
					<label>สถานะการย้ายเครื่อง</label>
					</br>
					<label class="checkbox-inline">

						<c:if test="${masterFileForm.move eq '0'}">
							<input type="checkbox" id="move" name="move" checked data-toggle="toggle" data-on="Distar" data-off="Move" data-onstyle="success" data-offstyle="danger">
						</c:if>
						<c:if test="${masterFileForm.move eq '1'}">
							<input type="checkbox" id="move" name="move" data-toggle="toggle" data-on="Distar" data-off="Move" data-onstyle="success" data-offstyle="danger">
						</c:if>					
					
					</label>
					</div>
					<div class="form-group">
						<label>unitID</label>
						<form:input path="unitId" maxlength="50" cssClass="form-control" readonly="true"/>
					</div>
					<div class="form-group">
						<label>Device Number (MDVR)</label>
						<form:input path="devNum" maxlength="20" id="devNum"  cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>ทะเบียนรถ</label>
						<form:input path="vehicleId" id="vehicleId" maxlength="7" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>ยี่ห้อรถ</label>
						<form:input path="vehicleType" id="vehicleTypes"  autocomplete="on"maxlength="20" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>หมายเลขตัวถัง</label>
						<form:input path="vehicleChassisNo" maxlength="25" id="vehicleChassisNo" onkeyup="checkChassisNo();" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>ประเภทรถ</label>
						<form:select path="vehicleRegisterType" cssClass="form-control">
							<form:options items="${listVehicle}" itemValue="vehicleRegisterType"  itemLabel="decription" />
						</form:select>
					</div>
					<div class="form-group">
						<label>จังหวัด</label>
						<form:select path="provinceCode" cssClass="form-control">
							<form:option value="0" label="กรุณาเลือก" />
							<form:options items="${listProvince}" itemValue="provinceCode" itemLabel="provinceName" />
						</form:select>
					</div>
					<div class="form-group">
						<label>รุ่น GPS</label>
						<form:select path="gpsModel" cssClass="form-control">
							<form:option value="0" label="กรุณาเลือก" />
							<form:option value="0430001" label="MDVR 400H" />
							<form:option value="0430002" label="MDVR 800H" />
							<form:option value="0430003" label="DTK-3G100T" />
							<form:option value="0430008" label="MHD-3G14H" />
							<form:option value="0430006" label="MHD-4GW48SH" />
							<form:option value="0430005" label="LTY-12" />
<%-- 							<form:option value="0430007" label="TXM-3GM2C" /> --%>
<%-- 							<form:option value="0430010" label="TXU-31S" /> --%>
							<form:option value="0430017" label="TBA1-GM2C (8730)" />
							<form:option value="0430012" label="TB-G4WHS (8732)" />
							<form:option value="0430013" label="MHD-C8AG (HDD)" />
							<form:option value="0430014" label="MHD-C8SG (SD Card)" />
							<form:option value="0430015" label="TDF-422F" />
							<form:option value="0430016" label="GT06E" />
							<form:option value="0430018" label="TD2-F211" />
						</form:select>
					</div>

					<div class="form-group">
					<label>เครื่องรูดบัตร</label>
					</br>
					<label class="checkbox-inline">

						<c:if test="${masterFileForm.cardReader eq '1'}">
							<input type="checkbox" id="cardReader" name="cardReader" checked data-toggle="toggle" data-on="มี" data-off="ไม่มี" data-onstyle="success" data-offstyle="danger">
						</c:if>
						<c:if test="${masterFileForm.cardReader eq '0'}">
							<input type="checkbox" id="cardReader" name="cardReader" data-toggle="toggle" data-on="ส่ง" data-off="ไม่ส่ง" data-onstyle="success" data-offstyle="danger">
						</c:if>					
					
					</label>
					
					</div>
					
					<div class="form-group">
						<label>วันที่ติดตั้ง</label>
						<form:input path="installDate" id="dateInstall"  cssClass="form-control" />
					</div>
					<div class="form-group">
					<label>ส่งข้อมูลเข้าขนส่ง</label>
					</br>
					<label class="checkbox-inline">
						<c:if test="${masterFileForm.dltStatus eq '1'}">
							<input type="checkbox" id="dltStatus" name="dltStatus" checked data-toggle="toggle" data-on="ส่ง" data-off="ไม่ส่ง" data-onstyle="success" data-offstyle="danger">
						</c:if>
						<c:if test="${masterFileForm.dltStatus eq '0'}">
							<input type="checkbox" id="dltStatus" name="dltStatus" data-toggle="toggle" data-on="ส่ง" data-off="ไม่ส่ง" data-onstyle="success" data-offstyle="danger">
						</c:if>					
					
					</label>
					
					</div>
					
					<div class="form-group">
						<label>หมายเหตุ</label>
						<form:input path="remark" cssClass="form-control" />
					</div>
					
					<div class="form-group">
						<label>บันทึกเพิ่มเติม</label>
						<form:input path="remark2" cssClass="form-control" />
					</div>

					<script type="text/javascript">
						jQuery('#dateInstall').datetimepicker({
							lang : 'th',
							locale: 'en',
							timepicker : false,
							format : 'Y-m-d'
						});
					</script>
					</br>
					</br>
					</br>


					<input type="submit" value="ยืนยัน" class="btn btn-success" /> <input type="reset" class="btn btn-danger" value="ล้างข้อมูล" class="styleButton" />
				</form>
			</div>
		</div>


	</form:form>

	<!-- footer content -->
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<!-- /footer content -->
</div>
</div>


<%@ include file="/WEB-INF/jsp/bottom.jsp"%>


</body>
</html>