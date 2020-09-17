
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<!-- /top navigation -->

<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>ค้นหาอุปกรณ์</h3>
		</div>
	</div>
	<div class="clearfix"></div>

	<div class="panel panel-info">

		<form:form method="POST" commandName="searchForm"
			action="resSearchForm.htm">

			<div class="panel-heading">Search tracker form</div>
			<div class="panel-body">
				<form role="form">

					<div class="form-group">
						<label>IMEI</label> ${status}
						<form:input path="imei" maxlength="20" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>Serial number</label>
						<form:input path="serialNum" id="serialNum" maxlength="20"
							cssClass="form-control" />
					</div>


					<input type="submit" value="Search" class="btn btn-success" /> <input
						type="reset" class="btn btn-danger" value="Reset"
						class="styleButton" />
				</form>
			</div>

		</form:form>

		<!-- /top tiles -->

	</div>
	<!-- /page content -->

	<!-- footer content -->
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<!-- /footer content -->
</div>
</div>

<%@ include file="/WEB-INF/jsp/bottom.jsp"%>

</body>
</html>
