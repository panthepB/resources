<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<script src="../js/jquery-1.10.2.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/jquery.metisMenu.js"></script>
<script src="../js/custom.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="/resources/bootstrap-datetimepicker.min.css">


<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : "yy-mm-dd"
		});
	});
</script>

<!-- /top navigation -->

	<div class="right_col" role="main">
		<!-- top tiles -->
		<div class="page-title">
			<div class="title_left">
				<h3>DLT send data</h3>
			</div>
		</div>
		<div class="clearfix"></div>
<form:form method="POST" commandName="gpsBackupForm" action="addHistoryDTK3G.htm">

	<div class="panel panel-info">

		<div class="panel-heading">GPS Backup form</div>
		<div class="panel-body">
			<form role="form">
				<div class="form-group">
					<label>UnitID</label>
					<form:input path="unitId" cssClass="form-control" />
				</div>
				<div class="form-group">
					<label>Latitude</label>
					<form:input path="lat" maxlength="20" cssClass="form-control" />
				</div>
				<div class="form-group">
					<label>Longitude</label>
					<form:input path="lon" maxlength="20" cssClass="form-control" />
				</div>
				<div class="form-group">
					<label>Date time start</label>
					<form:input path="date_start" id="datepicker" cssClass="form-control" />
				</div>

				<div class="form-group">
					<label>Time</label>
					<form:input path="time_start" id="" cssClass="form-control" />
				</div>

				<div class="form-group">
					<label>จำนวนข้อมูล</label>
					<form:input path="value_record" maxlength="5" cssClass="form-control" />
				</div>


				<input type="submit" value="Ok" class="btn btn-success" /> <input type="reset" class="btn btn-danger" value="Reset" class="styleButton" />
			</form>
		</div>
	</div>


</form:form>

</div>

			<!-- /top tiles -->

		</div>
		<!-- /page content -->

		<!-- footer content -->
		<footer>
			<div class="pull-right">
				Distar Tracking Web Report by <a href="https://www.distartech.com">DistarTech co.,LTD</a>
			</div>
			<div class="clearfix"></div>
		</footer>
		<!-- /footer content -->
	</div>
	</div>


	<%@ include file="/WEB-INF/jsp/bottom.jsp"%>


</body>
</html>