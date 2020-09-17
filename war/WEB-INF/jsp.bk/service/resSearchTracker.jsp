<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>
<!-- /top navigation -->

<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>DLT send data</h3>
		</div>
	</div>
	<div class="clearfix"></div>
	<form:form method="POST" commandName="searchForm"
		action="updateTrackerForm.htm">

		<div class="panel panel-info">

			<div class="panel-heading">Search tracker form</div>
			<div class="panel-body">
				<form role="form">

					<div class="form-group">
						<label>IMEI</label>
						<form:input path="imei" maxlength="20" cssClass="form-control"
							readonly="true" />
					</div>
					<div class="form-group">
						<label>Serial number</label>
						<form:input path="serialNum" id="serialNum" maxlength="20"
							cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>Tracker name</label>
						<form:input path="trackerName" id="trackerName" maxlength="20"
							cssClass="form-control" readonly="true" />
					</div>
					<div class="form-group">
						<label>Tracker sim number</label>
						<form:input path="trackerSimNumber" id="trackerSimNumber"
							maxlength="20" cssClass="form-control" readonly="true" />
					</div>
					<div class="form-group">
						<label>Tracker register time</label>
						<form:input path="trackerRegisterTime" id="trackerRegisterTime"
							maxlength="20" cssClass="form-control" readonly="true" />
					</div>
					<div class="form-group">
						<label>Tracker expired time</label>
						<form:input path="trackerExpiredTime" id="trackerExpiredTime"
							maxlength="20" cssClass="form-control" readonly="true" />
					</div>


					<input type="submit" value="Update" class="btn btn-success" /> <input
						type="reset" class="btn btn-danger" value="Reset"
						class="styleButton" />
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
		Distar Tracking Web Report by <a href="https://www.distartech.com">DistarTech
			co.,LTD</a>
	</div>
	<div class="clearfix"></div>
</footer>
<!-- /footer content -->
</div>
</div>


<%@ include file="/WEB-INF/jsp/bottom.jsp"%>


</body>
</html>