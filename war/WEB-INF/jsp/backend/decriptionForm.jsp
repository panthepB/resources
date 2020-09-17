<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<!-- /top navigation -->

<link rel="stylesheet" type="text/css"
	href="http://sysapp.itoffside.com/datetimepicker/jquery.datetimepicker.css">
<script type="text/javascript"
	src="http://sysapp.itoffside.com/datetimepicker/jquery.js"></script>
<script type="text/javascript"
	src="http://sysapp.itoffside.com/datetimepicker/jquery.datetimepicker.js"></script>

<link
	href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"
	rel="stylesheet">
<script
	src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>



<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>แก้ไขข้อมูล</h3>
		</div>
	</div>
	<div class="clearfix"></div>

	<form:form method="POST" commandName="decriptionForm"
		action="updateDecription.htm">

		<div class="panel panel-info">

			<div class="panel-heading">ข้อมูลมาสเตอร์ไฟล์</div>
			<div class="panel-body">
				<form role="form">

					<div class="form-group">
						<label>หมายเลขอีมี่</label>
						<form:input path="imei" maxlength="255" cssClass="form-control"
							readonly="true" />
					</div>
					<div class="form-group">
						<label>ชื่อแทรกกิ้ง</label>
						<form:input path="trackerName" maxlength="255"
							cssClass="form-control" readonly="true" />
					</div>
					<div class="form-group">
						<label>ชื่อผู้ใช้</label>
						<form:input path="userAccount" maxlength="255"
							cssClass="form-control" readonly="true" />
					</div>
					<div class="form-group">
						<label>ชื่อผู้ดูแล</label>
						<form:input path="userFather" maxlength="255"
							cssClass="form-control" readonly="true" />
					</div>
					<div class="form-group">
						<label>หมายเหตุ</label>
						<form:input path="remark" maxlength="255" cssClass="form-control" />
					</div>


					</br> </br> </br> <input type="submit" value="ยืนยัน" class="btn btn-success" />
					<input type="reset" class="btn btn-danger" value="ล้างข้อมูล"
						class="styleButton" />
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