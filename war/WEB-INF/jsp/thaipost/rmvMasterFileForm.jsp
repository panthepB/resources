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

			<form:form method="POST" commandName="masterFileForm" action="rmvMasterFile.htm">

				<div class="panel panel-info">

					<div class="panel-heading">Remove master file form</div>
					<div class="panel-body">
						<form role="form">
							<div class="form-group">
								<label>Unit id</label>
								<form:input path="unitId" maxlength="30" cssClass="form-control" />
							</div>
							<form:hidden path="status" id="status" /> 
							
<!-- 							<div class="form-group"> -->
<!-- 								<label>IMEI</label> -->
<%-- 								<form:input path="imei" maxlength="20" cssClass="form-control" /> --%>
<!-- 							</div> -->

<!-- 							<div class="form-group"> -->
<!-- 								<label>รุ่น GPS</label> -->
<%-- 								<form:select path="gpsModel" cssClass="form-control"> --%>
<%-- 									<form:option value="0" label="กรุณาเลือก" /> --%>
<%-- 									<form:option value="0430001" label="MDVR 400H" /> --%>
<%-- 									<form:option value="0430002" label="MDVR 800H" /> --%>
<%-- 									<form:option value="0430003" label="DTK-3G100T" /> --%>
<%-- 									<form:option value="0430004" label="MHD-3G14H" /> --%>
<%-- 									<form:option value="0430006" label="MHD-4GW48SH" /> --%>
<%-- 									<form:option value="0430005" label="LYT-12" /> --%>
<%-- 								</form:select> --%>
<!-- 							</div> -->


							<input type="submit" value="Ok" class="btn btn-success" /> <input type="reset" class="btn btn-danger" value="Reset" class="styleButton" />
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