
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<style>
#map {
	height: 300px;
	width: 100%;
}
</style>
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

	<div class="panel panel-info">

		<form:form method="POST" commandName="searchForm"
			action="resCheckDriverLTY.htm">

			<div class="panel-heading">ตรวจสอบสถานะรถ LTY</div>
			<div class="panel-body">
				<form role="form">

					<div class="form-group">
						<label>เลขข้างรถ</label> &nbsp <font color="red"> ${status}</font>
						<form:input path="imei" maxlength="20" cssClass="form-control" />
					</div>

					<input type="submit" value="Search" class="btn btn-success" /> <input
						type="reset" class="btn btn-danger" value="Reset"
						class="styleButton" />
				</form>
			</div>
			<div class="panel-body">
				<form role="form">
					<div class="form-group">
						<label>เลขข้างรถ</label> <input type="text" value="${vehicle}"
							class="form-control" readonly="readonly">
					</div>
					<div class="form-group">
						<label>เลขใบขับขี่</label>
						<c:choose>
							<c:when test="${driverId == null}">
								<input type="text" value="ไม่พบข้อมูล" class="form-control"
									readonly="readonly">
							</c:when>
							<c:otherwise>
								<input type="text" value="${driverId}" class="form-control"
									readonly="readonly">
							</c:otherwise>
						</c:choose>
					</div>
					<div class="form-group">
						<label>สถานะเครื่องยนต์</label>
						<c:if test="${engineStatus == 1}">
							<input type="text" value="เปิดเครื่องยนต์" class="form-control"
								readonly="readonly">
						</c:if>
						<c:if test="${engineStatus == 0}">
							<input type="text" value="ปิดเครื่องยนต์" class="form-control"
								readonly="readonly">
						</c:if>
					</div>
					<div class="form-group">
						<label>เวลาอัพเดท</label> <input type="text" value="${timeUpdate}"
							class="form-control" readonly="readonly">
					</div>
				</form>
				<div id="map"></div>
				<script>
					function initMap() {
						var uluru = {
							lat : ${lat},
							lng : ${lon}
						};
						var map = new google.maps.Map(document
								.getElementById('map'), {
							zoom : 18,
							center : uluru
						});
						var marker = new google.maps.Marker({
							position : uluru,
							map : map
						});
					}
				</script>
				<script async defer
					src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCXWPt76ty60KleMuedZLdKT4gVCxel7hM&callback=initMap">
					
				</script>
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
