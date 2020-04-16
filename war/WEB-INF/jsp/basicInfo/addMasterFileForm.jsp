<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<!-- /top navigation -->


<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/8.11.8/sweetalert2.all.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/8.11.8/sweetalert2.css" rel="stylesheet">
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

	<form:form method="POST" onsubmit="myFunction()" commandName="masterFileForm" action="addMasterFile.htm">

		<div class="panel panel-info">

			<div class="panel-heading">ข้อมูลมาสเตอร์ไฟล์</div>
			<div class="panel-body">
			<script>
				function myFunction() {
					 Swal.fire({
						    title: 'กำลังบันทึกข้อมูล',
						    timer: 5000,
						    timerProgressBar: true,
						    onBeforeOpen: () => {
						      Swal.showLoading()
						      timerInterval = setInterval(() => {
						        const content = Swal.getContent()
						        if (content) {
						          const b = content.querySelector('b')
						          if (b) {
						            b.textContent = Swal.getTimerLeft()
						          }
						        }
						      }, 100)
						    },
						    onClose: () => {
						      clearInterval(timerInterval)
						    }
						  }).then((result) => {
						    /* Read more about handling dismissals below */
						    if (result.dismiss === Swal.DismissReason.timer) {
						      console.log('I was closed by the timer')
						    }
						  })
				}
			</script>
			<c:if test="${messageCode == 200}">
				<!-- Update driver log service started!! -->
					<script type="text/javascript">
					Swal.fire(
							  'บันทึกข้อมูลเรียบร้อยแล้ว',
							  ' ',
							  'success'
							)
					</script>
			</c:if>

			<c:if test="${messageCode == 101}">				
				<script type="text/javascript">
							Swal.fire(
							  'มีหมายเลข IMEI เครื่องนี้แล้วในระบบ \n โปรดตรวจสอบข้อมูลอีกครั้งน',
							  ' ',
							  'error'
							)
					</script>
			</c:if>
			
			<c:if test="${messageCode == 102}">				
				<script type="text/javascript">
							Swal.fire(
							  'มีหมายเลขตัวถังนี้แล้วในระบบ \n โปรดตรวจสอบข้อมูลอีกครั้ง',
							  ' ',
							  'error'
							)
					</script>
			</c:if>
			<c:if test="${messageCode == 103}">				
				<script type="text/javascript">
							Swal.fire(
							  'กรุณาระบุรุ่น GPS \n โปรดตรวจสอบข้อมูลอีกครั้ง',
							  ' ',
							  'error'
							)
					</script>
			</c:if>
			<c:if test="${messageCode == 104}">				
				<script type="text/javascript">
							Swal.fire(
							  'มีข้อมูล IMEI และ หมายเลขตัวถัง นี้แล้วในระบบ \n โปรดตรวจสอบข้อมูลอีกครั้ง',
							  ' ',
							  'warning'
							)
					</script>
			</c:if>
			<c:if test="${status == 400}">
			
				<script type="text/javascript">
							Swal.fire(
							  'ล็อกอินไม่ถูกต้อง',
							  ' ',
							  'warning'
							)
					</script>
			</c:if>
			
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
								$(function() {
									var availableTags =  ${sales} ;
									$("#sales").autocomplete({
										source : availableTags
									});
								});
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
					
					<script type="text/javascript">
						function checkTel() {
							var elem = document.getElementById('customerTel').value;
							if  (!elem.match(/^([0-9\_])+$/i)) {
								//alert("กรอกได้เฉพาะ a-Z, A-Z, 0-9 และ _ (underscore)");
								document.getElementById('customerTel').value = "";
							}
						}
					</script>
					
					
					<script type="text/javascript">
						function checkTrackTel() {
							var elem = document.getElementById('tracker_sim_number').value;
							if  (!elem.match(/^([0-9\_])+$/i)) {
								//alert("กรอกได้เฉพาะ a-Z, A-Z, 0-9 และ _ (underscore)");
								document.getElementById('tracker_sim_number').value = "";
							}
						}
					</script>
					
					

<!-- 					<div class="form-group"> -->
<!-- 						<label for="tags">Tags: </label> <input id="tags" class="form-control"> -->
<!-- 					</div> -->
					
					
					<div class="form-group">
						<label>ชื่อผู้ขาย</label>
						<form:input path="saleName" id="sales" autocomplete="on" maxlength="50" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>ชื่อลูกค้า</label>
<!-- 						<input id="tags" class="form-control"> -->
						<form:input path="customerName" id="customers" autocomplete="on" maxlength="50" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>ที่อยู่</label>
						<form:input path="remark2" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>เบอร์ติดต่อลูกค้า</label>
						<form:input path="customerTel" id="customerTel"  onkeyup="checkTel();" maxlength="10" cssClass="form-control" />
					</div>

					<div class="form-group">
						<label>รุ่น GPS</label>
						<form:select path="gpsModel" cssClass="form-control">
							<form:option value="0" label="กรุณาเลือก" />
							<form:option value="0430003" label="DTK-3G100T" />
							<form:option value="0430013" label="MHD-C8AG (HDD)" />
							<form:option value="0430014" label="MHD-C8SG (SD Card)" />
							<form:option value="0430015" label="TDF-422F" />
							<form:option value="0430016" label="GT06E" />
							<form:option value="0430018" label="TD2-F211" />
						</form:select>
					</div>
					<div class="form-group">
						<label>IMEI</label>
						<form:input path="imei" maxlength="20" id="imei" onkeyup="checkIMEI();" cssClass="form-control" />
					</div>
					
					<div class="form-group">
						<label>เบอร์โทรของซิมที่ใช้กับอุปกรณ์</label>
						<form:input path="tracker_sim_number" id="tracker_sim_number"  onkeyup="checkTrackTel();" maxlength="10" cssClass="form-control" />
					</div>
					<div class="form-group" hidden="true">
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
						<label>ทะเบียนรถ</label>
						<form:input path="vehicleId" id="vehicleId" maxlength="7" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>จังหวัด</label>
						<form:select path="provinceCode" cssClass="form-control">
							<form:option value="0" label="กรุณาเลือก" />
							<form:options items="${listProvince}" itemValue="provinceCode" itemLabel="provinceName" />
						</form:select>
					</div>
					<div class="form-group">
						<label>หมายเลขตัวถัง</label>
						<form:input path="vehicleChassisNo" maxlength="25" id="vehicleChassisNo" onkeyup="checkChassisNo();" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>ยี่ห้อรถ</label>
						<form:input path="vehicleType" id="vehicleTypes"  autocomplete="on"maxlength="20" cssClass="form-control" />
					</div>
					
					<div class="form-group">
						<label>ประเภทรถ</label>
						<form:select path="vehicleRegisterType" cssClass="form-control">
							<form:options items="${listVehicle}" itemValue="vehicleRegisterType"  itemLabel="decription" />
						</form:select>
					</div>
					
					<div class="form-group">
						<label>วันที่ติดตั้ง</label>
						<form:input path="installDate" id="dateInstall"  cssClass="form-control" />
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
					


					<script>
							$('#dateInstall').datetimepicker({
								lang : 'en',
	 							locale: 'en',
	 							timepicker : false,
	 							format : 'Y-m-d'
							});
					</script>
					
					</br>
					</br>
					</br>

					<button onclick="confirmFunction()" id="rvmBt" class="btn btn-success" title="ยืนยัน" ><i class="fa fa-check"></i> ยืนยัน</button>
<!-- 					<input type="submit" value="ยืนยัน" class="btn btn-success" /> <i class="fa fa-trash"></i> -->
					<a	onclick="return confirm('Are you sure?')" id="rvmBt" href="masterfileList.htm"  class="btn btn-danger" title="ยกเลิกทำรายการ" ><i class="fa fa-undo"></i> ยกเลิก</a>
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