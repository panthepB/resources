<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>
		
	<!-- /top navigation -->
        
		<link rel="stylesheet" type="text/css" href="http://sysapp.itoffside.com/datetimepicker/jquery.datetimepicker.css">
        <script type="text/javascript" src="http://sysapp.itoffside.com/datetimepicker/jquery.js"></script>
        <script type="text/javascript" src="http://sysapp.itoffside.com/datetimepicker/jquery.datetimepicker.js"></script>
	
<!-- 	<link rel="stylesheet" type="text/css" href="../styles/jquery.datetimepicker.css"> -->
<!-- 	<script type="text/javascript" src="../js/jquery.js"></script> -->
<!-- 	<script type="text/javascript" src="../js/jquery.datetimepicker.js"></script> -->

	<div class="right_col" role="main">
		<!-- top tiles -->
		<div class="page-title">
			<div class="title_left">
				<h3>DLT send data</h3>
			</div>
		</div>
		<div class="clearfix"></div>

				<form:form method="POST" commandName="masterFileForm" action="resEditDevice.htm">

					<div class="panel panel-info">

						<div class="panel-heading">อัพเดทวันหมดอายุ</div>
						<div class="panel-body">
							<form role="form">
							
								

								<div class="form-group">
									<label>ชื่อแทรกกิ้ง</label>
									<form:input path="tracker_name" maxlength="50" cssClass="form-control" readonly="true"/>
								</div>
								<div class="form-group">
									<label>IMEI</label>
									<form:input path="imei" maxlength="20" id="imei" cssClass="form-control" readonly="true"/>
								</div>
								<div class="form-group">
									<label>รหัสผ่านแทรกกิ้ง</label>
									<form:input path="tracker_password" maxlength="10" cssClass="form-control" readonly="true"/>
								</div>
								<div class="form-group">
									<label>ซิมการ์ด</label>
									<form:input path="tracker_sim_number" maxlength="10" cssClass="form-control" readonly="true"/>
								</div>
								<div class="form-group">
									<label>วันที่ลงทะเบียน</label>
									<form:input path="tracker_register_time" maxlength="50" id="registerdate" cssClass="form-control" />
								</div>
								<div class="form-group">
									<label>วันที่หมดอายุ</label>
									<form:input path="tracker_expired_time"maxlength="50" id="startdate" cssClass="form-control" />
									<form:hidden path="timeBefore" />
								</div>
								
								<input type="submit" value="Ok" class="btn btn-success" /> 
							</form>
						</div>
					</div>

        <script type="text/javascript">
            jQuery('#startdate').datetimepicker({
                lang:'en',
                timepicker:false,
                format:'Y-m-d 23:59:59.0'
            });
        </script>
        
        <script type="text/javascript">
            jQuery('#registerdate').datetimepicker({
                lang:'en',
                timepicker:false,
                format:'Y-m-d 23:59:59.0'
            });
        </script>

				</form:form>

		<!-- footer content -->
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
		<!-- /footer content -->
	</div>
	</div>


	<%@ include file="/WEB-INF/jsp/bottom.jsp"%>


</body>
</html>