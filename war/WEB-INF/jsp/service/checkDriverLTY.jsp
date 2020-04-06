
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<style>
#map {
	height: 300px;
	width: 100%;
}
</style>

<link rel="stylesheet" type="text/css" href="https://js.cit.api.here.com/v3/3.0/mapsjs-ui.css" />
<script type="text/javascript" src="https://js.cit.api.here.com/v3/3.0/mapsjs-core.js"></script>
<script type="text/javascript" src="https://js.cit.api.here.com/v3/3.0/mapsjs-service.js"></script>
<script type="text/javascript" src="https://js.cit.api.here.com/v3/3.0/mapsjs-ui.js"></script>
<script type="text/javascript" src="https://js.cit.api.here.com/v3/3.0/mapsjs-mapevents.js"></script>
<script>
    window.onload = function() {
    	show('map');
    }
    window.onload = myFunction;
</script>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>


<!-- /top navigation -->



<div class="right_col" role="main" >
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>ตรวจสอบสถานะรถ ขสมก</h3>
		</div>
	</div>
	<div class="clearfix"></div>

	<div class="panel panel-info" >

		<form:form method="POST" commandName="searchForm" action="resCheckDriverLTY.htm">

<!-- 			<div class="panel-heading">ตรวจสอบสถานะรถ LTY</div> -->
			<div class="panel-body">
				<form role="form">

					<div class="form-group">
						<label>เลขข้างรถ</label> &nbsp <font color="red"> ${status}</font>
						<form:input path="imei" maxlength="20" cssClass="form-control" />
					</div>

					<input type="submit" value="Search" class="btn btn-success" /> <input type="reset" class="btn btn-danger" value="Reset" class="styleButton" />
				</form>
			</div>
			<div class="panel-body">
				<form role="form">
					<div class="form-group">
						<label>เลขข้างรถ</label> <input type="text" value="${vehicle}" class="form-control" readonly="readonly">
					</div>
					<div class="form-group">
						<label>เลขใบขับขี่</label>
						<c:choose>
							<c:when test="${driverId == null}">
								<input type="text" value="ไม่พบข้อมูล" class="form-control" readonly="readonly">
							</c:when>
							<c:otherwise>
								<input type="text" value="${driverId}" class="form-control" readonly="readonly">
							</c:otherwise>
						</c:choose>
					</div>
					<div class="form-group">
						<label>สถานะเครื่องยนต์</label>
						<c:if test="${engineStatus == 1}">
							<input type="text" value="เปิดเครื่องยนต์" class="form-control" readonly="readonly">
						</c:if>
						<c:if test="${engineStatus == 0}">
							<input type="text" value="ปิดเครื่องยนต์" class="form-control" readonly="readonly">
						</c:if>
					</div>
					<div class="form-group">
						<label>จำนวนดาวเทียม</label> <input type="text" value="${numStas}" class="form-control" readonly="readonly">
					</div>
					<div class="form-group">
						<label>เวลาอัพเดท</label> <input type="text" value="${timeUpdate}" class="form-control" readonly="readonly">
					</div>
<!-- 					<div class="form-group"> -->
<!-- 						</br> -->
<!--  						<input type="button" value="Google map" class="btn btn-success" onclick="show('map');">  -->
<!--  						<input type="button" value="Here map" class="btn btn-success" onclick="show('map2');">  -->
						
<!-- 					</div> -->
				</form>
				


				<div id="map2" style="width: 100%; height: 300px; background: grey; "  /></div>
				<script type="text/javascript" charset="UTF-8">
    
 					function addMarkersToMap(map) {
					
 					  var pointMarker = new H.map.Marker({lat:${lat}, lng:${lon}});
 					  map.addObject(pointMarker);
 					}

 					var platform = new H.service.Platform({
 					  app_id: 'AO0TSLtcxmb58ny1UwOH',
 					  app_code: '3aJwPn3AGDNS-0xAArtSAg',
 					  useCIT: true,
 					  useHTTPS: true
//  					 language: "fr-fr"
 					});
 					var defaultLayers = platform.createDefaultLayers();

 					var map = new H.Map(document.getElementById('map2'),
 					  defaultLayers.normal.map,{
 					  center: {lat:${lat}, lng:${lon}},
 					  zoom: 18
					});
					
 					var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));
					
 					var ui = H.ui.UI.createDefault(map, defaultLayers);

 					
 					  // Remove not needed settings control
//  					  ui.removeControl('mapsettings');
 					  
//  					var ui = H.ui.UI.createDefault(map, defaultLayers);
//  					var ui = H.ui.UI.createDefault(map, defaultLayers, 'th-TH');
					
 					addMarkersToMap(map);
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
