<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />

<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"></script>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.3.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.12/js/dataTables.jqueryui.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.html5.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.dataTables.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
.btn {
	background-color: DodgerBlue;
	border: none;
	color: white;
	padding: 8px 12px;
	font-size: 13px;
	cursor: pointer;
}

/* Darker background on mouse-over */
.btn:hover {
	background-color: RoyalBlue;
}
</style>


<script type="text/javascript" charset="utf-8">
$(document).ready(function() {

    $('#example').DataTable( {
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                title: 'DLT Masterfile',
 
            },
            {
                extend: 'pdfHtml5',
                title: 'DLT Masterfile',
                orientation: 'landscape',
                pageSize: 'A4'
            },
            'copyHtml5',
        ]
    } );
} );
</script>
<script>

					 Swal.fire({
						    title: 'กำลังโหลดข้อมูล',
						    timer: 30000,
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
				
			</script>

<!-- /top navigation -->

<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>รายการมาสเตอร์ไฟล์</h3>
		</div>
	</div>
	<div class="clearfix"></div>

	<div class="panel panel-info">

		<div class="panel panel-default">


			<div class="panel-heading">รายการ</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table id="example" class="table table-striped table-bordered"
						cellspacing="0" width="250%">
						<thead>
							<tr>
								<th align="center">ลำดับ</th>
								<th>Unit Id</th>
								<th align="center">IMEI</th>
								<th align="center">หมายเลขตัวถัง</th>
								<th align="center">ทะเบียนรถ</th>
								<th align="center">จังหวัด</th>

								<th align="center">ยี่ห้อรถ</th>
								<th align="center">ประเภทรถ</th>
								<th align="center">ชื่อลูกค้า</th>
								<th align="center">ชื่อผู้ขาย</th>

								<th align="center">อัพเดทล่าสุด</th>
								<th align="center">หมายเหตุ</th>
								<th align="center">ชื่ออุปกรณ์</th>
								<th align="center">เบอร์ซิม</th>
								<th align="center">วันที่ลงทะเบียน</th>
								<th align="center">วันที่หมดอายุ</th>

								<th align="center">Actions</th>

							</tr>
						</thead>

						<tbody>

							<c:forEach items="${listMasterFile}" var="listMasterFile"
								varStatus="n">

								<tr>
									<c:choose>
										<c:when test="${listMasterFile.dltStatus == 0}">
											<td bgcolor="#FFC0C0" width="3%" align="right">${n.count}</td>
											<td bgcolor="#FFC0C0" width="5%">'<c:out
													value="${listMasterFile.unitId}" /></td>
											<td bgcolor="#FFC0C0" width="5%"><c:out
													value="'${listMasterFile.imei}" /></td>
											<td bgcolor="#FFC0C0" width="5%"><c:out
													value="${listMasterFile.vehicleChassisNo}" /></td>
											<td bgcolor="#FFC0C0" width="5%"><c:out
													value="${listMasterFile.vehicleId}" /></td>
											<td bgcolor="#FFC0C0" width="5%"><c:out
													value="${listMasterFile.provinceCode.provinceName}" /></td>
											<%-- 													<td width="5%"><c:out value="${table[j.index+3]}" /></td> --%>
											<td bgcolor="#FFC0C0" width="5%" align="center"><c:out
													value="${listMasterFile.vehicleType}" /></td>
											<td bgcolor="#FFC0C0" width="7%" align="center"><c:out
													value="${listMasterFile.vehicleRegisterType.decription}" /></td>
											<td bgcolor="#FFC0C0" width="8%"><c:out
													value="${listMasterFile.customerName}" /></td>
											<td bgcolor="#FFC0C0" width="5%"><c:out
													value="${listMasterFile.saleName}" /></td>

											<td bgcolor="#FFC0C0" width="5%"><c:out
													value="${listMasterFile.dltUpdateTime}" /></td>
											<td bgcolor="#FFC0C0" width="14%"><c:out
													value="${listMasterFile.remark}" /></td>
											<td bgcolor="#FFC0C0" width="5%"><c:out
													value="${listMasterFile.trackerName}" /></td>
											<td bgcolor="#FFC0C0" width="5%"><c:out
													value="${listMasterFile.trackerSimNumber}" /></td>
											<td bgcolor="#FFC0C0" width="5%"><c:out
													value="${listMasterFile.trackerRegisterTime}" /></td>
											<td bgcolor="#FFC0C0" width="5%"><c:out
													value="${listMasterFile.trackerExpiredTime}" /></td>
											<!-- 											<td width="10%"> -->
											<%-- 												<c:if test = "${listMasterFile.dltStatus == '1'}">ส่งข้อมูล</c:if> --%>
											<%-- 												<c:if test = "${listMasterFile.dltStatus == '0'}">ไม่ส่ง</c:if> --%>
											<!-- 											</td> -->
											<td bgcolor="#FFC0C0" width="8%"><a
												href="editMasterFileForm.htm?unitId=${listMasterFile.unitId}"
												class="btn"> <img border="0" alt="Edit"
													src="../img/edit-property-96.png" width="24" height="24">
													แก้ไขข้อมูล
											</a> <a
												href="changeDeviceForm.htm?unitId=${listMasterFile.unitId}"
												class="btn"> <img border="0" alt="Edit"
													src="../img/icons8-transfer-96.png" width="24" height="24">เปลื่ยนอุปกรณ์
											</a></td>
										</c:when>
										<c:otherwise>
											<td width="3%" align="right">${n.count}</td>
											<td width="5%">'<c:out value="${listMasterFile.unitId}" /></td>
											<td width="5%"><c:out value="'${listMasterFile.imei}" /></td>
											<td width="5%"><c:out
													value="${listMasterFile.vehicleChassisNo}" /></td>
											<td width="5%"><c:out
													value="${listMasterFile.vehicleId}" /></td>
											<td width="5%"><c:out
													value="${listMasterFile.provinceCode.provinceName}" /></td>
											<%-- 													<td width="5%"><c:out value="${table[j.index+3]}" /></td> --%>
											<td width="5%" align="center"><c:out
													value="${listMasterFile.vehicleType}" /></td>
											<td width="7%" align="center"><c:out
													value="${listMasterFile.vehicleRegisterType.decription}" /></td>
											<td width="8%"><c:out
													value="${listMasterFile.customerName}" /></td>
											<td width="5%"><c:out value="${listMasterFile.saleName}" /></td>

											<td width="5%"><c:out
													value="${listMasterFile.dltUpdateTime}" /></td>
											<td width="14%"><c:out value="${listMasterFile.remark}" /></td>
											<td width="5%"><c:out
													value="${listMasterFile.trackerName}" /></td>
											<td width="5%"><c:out
													value="${listMasterFile.trackerSimNumber}" /></td>
											<td width="5%"><c:out
													value="${listMasterFile.trackerRegisterTime}" /></td>
											<td width="5%"><c:out
													value="${listMasterFile.trackerExpiredTime}" /></td>
											<!-- 											<td width="10%"> -->
											<%-- 												<c:if test = "${listMasterFile.dltStatus == '1'}">ส่งข้อมูล</c:if> --%>
											<%-- 												<c:if test = "${listMasterFile.dltStatus == '0'}">ไม่ส่ง</c:if> --%>
											<!-- 											</td> -->
											<td width="8%">
												<!-- 												<button class="btn"><i class="fa fa-home"></i> Home</button> -->
												<!-- 												<button class="btn"><i class="fa fa-bars"></i> Menu</button> -->
												<a
												href="editMasterFileForm.htm?unitId=${listMasterFile.unitId}"
												class="btn"> <img border="0" alt="Edit"
													src="../img/edit-property-96.png" width="24" height="24">
													แก้ไขข้อมูล
											</a> <a
												href="changeDeviceForm.htm?unitId=${listMasterFile.unitId}"
												class="btn"> <img border="0" alt="Edit"
													src="../img/icons8-transfer-96.png" width="24" height="24">
													เปลื่ยนอุปกรณ์
											</a>
											</td>
										</c:otherwise>
									</c:choose>

								</tr>
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
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<!-- /footer content -->
</div>
</div>

<%@ include file="/WEB-INF/jsp/bottom.jsp"%>

</body>
</html>