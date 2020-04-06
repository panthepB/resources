<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />

<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.3.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.jqueryui.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.html5.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.dataTables.min.css" />


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

<!-- /top navigation -->

<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>รายการมาสเตอร์ไฟล์รอยืนยัน</h3>
		</div>
	</div>
	<div class="clearfix"></div>


	<div class="panel panel-info">

		<div class="panel panel-default">


			<div class="panel-heading">
			
			<form:form method="POST" commandName="masterFileForm" action="addMasterFileForm.htm">
					<input type="submit" value="เพิ่มข้อมูล" class="btn btn-primary" style="float: right; " />
				</form:form>
			
			</div>
			<div class="panel-body">

				<div class="table-responsive">
				
					<table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th align="center">ลำดับ</th>
								<th>Unit Id</th>
								<th align="center">IMEI </th>
								<th align="center">หมายเลขซิม </th>
								<th align="center">หมายเลขตัวถัง  </th>
								<th align="center">ทะเบียนรถ</th>
								<th align="center">จังหวัด</th>						
								<th align="center">ยี่ห้อรถ</th>
								<th align="center">ประเภทรถ </th>
								<th align="center">ชื่อลูกค้า</th>
								<th align="center">วันที่ติดตั้ง</th>


								<th align="center"></th>

							</tr>
						</thead>

						<tbody>

								<c:forEach items="${listMasterFile}" var="listMasterFile" varStatus="n">
	
										<tr>

									            <td width="3%" align="right">${n.count}</td>
									            <td width="10%"><c:out value="${listMasterFile.unitId}" /></td>
									            <td width="10%"> <c:out value="${listMasterFile.imei}" /></td>
									            <td width="7%"><c:out value="${listMasterFile.trackerSimNumber}" /></td>
												<td width="10%"><c:out value="${listMasterFile.vehicleChassisNo}" /></td>
												<td width="10%"><c:out value="${listMasterFile.vehicleId}" /></td>
												<td width="10%"><c:out value="${listMasterFile.provinceCode.provinceName}" /></td>
												<td width="7%" align="center"><c:out value="${listMasterFile.vehicleType}" /></td>
												<td width="10%" align="center"><c:out value="${listMasterFile.vehicleRegisterType.decription}" /></td>
												<td width="8%"><c:out value="${listMasterFile.customerName}" /></td>
												<td width="10%"><c:out value="${listMasterFile.installDate}" /></td>
												<td width="7%" align="center">
												<a href="editMasterFileForm.htm?unitId=${listMasterFile.unitId}" class="blue">
													<img border="0" alt="Edit" src="../img/edit-property-96.png" width="48" height="48"></a> 
												</td>

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