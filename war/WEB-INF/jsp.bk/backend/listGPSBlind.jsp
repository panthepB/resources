<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />

<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.3.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.jqueryui.min.js"></script>

<style>
.btn-group2 .button2 {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 11px 5px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 12px;
    cursor: pointer;
    float: left;
}

.btn-group2 .button2:hover {
    background-color: #3e8e41;
}
</style>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#example').DataTable( {
	        dom: 'Bfrtip',
	        buttons: [
	            'excel', 'pdf', 'print'
	        ]
	    } );
	});
</script>

<!-- /top navigation -->

<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>สถิติการถูกบังสัญญาณ GPS</h3>
		</div>
		

				
	</div>
	<div  class="clearfix"></div>
	
<!-- 			<div style="float: right;" class="btn-group"> -->
<!-- 			  <button type="button" class="btn btn-primary" onclick="window.location.href='/DLTSendData/backend/checkGPSCut.htm?time=12';">12 ชั่วโมง</button> -->
<!-- 			  <button type="button" class="btn btn-primary"onclick="window.location.href='/DLTSendData/backend/checkGPSCut.htm?time=24';">24 ชั่วโมง</button> -->
<!-- 			  <button type="button" class="btn btn-primary"onclick="window.location.href='/DLTSendData/backend/checkGPSCut.htm?time=3';">3 วัน</button> -->
<!-- 			  <button type="button" class="btn btn-primary"onclick="window.location.href='/DLTSendData/backend/checkGPSCut.htm?time=7';">มากกว่า 7 วัน</button> -->
<!-- 			</div> -->

	<div class="panel panel-info">

		<div class="panel panel-default">


			<div class="panel-heading">รายการอุปกรณ์ที่ถูกบังสัญญาณ GPS 3 วันล่าสุด</div>
			
				

			<div class="panel-body">
				<div class="table-responsive">
					<table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th align="center">ลำดับ</th>
								<th align="center">เลขอีมี่</th>
								<th align="center">ชื่ออุปกรณ์</th>
								<th align="center">ผู้ใช้ย่อย</th>
								<th align="center">ชื่อผู้ดูแล</th>
								<th align="center">จำนวนครั้ง</th>
								<th align="center">รุ่นอุปกรณ์</th>

							</tr>
						</thead>

						<tbody>

							<c:forEach items="${row}" var="row" varStatus="j">
								<c:forEach items="${table}" var="table" varStatus="i">
									<c:if test="${j.index % 7 == 0}">

										<tr>
											<td width="5%" align="right">${table[j.index]}</td>
											<td width="15%" >${table[j.index+1]}</td>
											<td width="20%"><c:out value="${table[j.index+2]}" /></td>
											<td width="15%"><c:out value="${table[j.index+3]}" /></td>
											<td width="20%"><c:out value="${table[j.index+4]}" /></td>
											<td width="10%" align="center"><c:out value="${table[j.index+5]}" /></td>
											<td width="15%"><c:out value="${table[j.index+6]}" /></td>
											
										</tr>
									</c:if>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>

				</div>
			</div>
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