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
                title: 'SCG Masterfile',
 
            },
            {
                extend: 'pdfHtml5',
                title: 'SCG Masterfile',
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
			<h3><%@ include file="/WEB-INF/jsp/sysName.jsp"%></h3>
		</div>
	</div>
	<div class="clearfix"></div>

	<c:if test="${status == 100}">
		<div class="panel-body">
			<!-- Update driver log service started!! -->
			<div class="alert alert-success alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
				Realtime mdvr service started!!
			</div>
		</div>
	</c:if>
	<c:if test="${status == 101}">
		<div class="panel-body">
			<!-- Update driver log service started!! -->
			<div class="alert alert-success alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
				Update driver log service started!!
			</div>
		</div>
	</c:if>
	<c:if test="${status == 102}">
		<div class="panel-body">
			<!-- Update driver log service started!! -->
			<div class="alert alert-success alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
				Location backup send to server
			</div>
		</div>
	</c:if>

	<div class="panel panel-info">

		<div class="panel panel-default">


			<div class="panel-heading">List master file</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th align="center">ลำดับ</th>
								<th>Unit Id</th>
								<th align="center">หมายเลขตัวถัง</th>
								<th align="center">ทะเบียนรถ</th>
								<th align="center">ยี่ห้อรถ</th>
								<th align="center">ประเภทรถ</th>
								<th align="center">จังหวัด</th>
								<th align="center">อัพเดทล่าสุด</th>
								<th></th>
								<!--                 <th></th> -->
							</tr>
						</thead>

						<tbody>

							<c:forEach items="${row}" var="row" varStatus="j">
								<c:forEach items="${table}" var="table" varStatus="i">
									<c:if test="${j.index % 8== 0}">

										<tr>
											<td width="5%" align="right">${table[j.index+7]}</td>
											<%-- 													<td width="5%"><c:out value="${(j.index % 37)+1}" /></td> --%>
											<%-- <td><c:out value="${table[j.index]}" /></td> --%>
											<td width="10%">'<c:out value="${table[j.index]}" /></td>
											<td width="15%"><c:out value="${table[j.index+1]}" /></td>
											<td width="10%"><c:out value="${table[j.index+2]}" /></td>
											<%-- 													<td width="5%"><c:out value="${table[j.index+3]}" /></td> --%>
											<td width="10%" align="center"><c:out value="${table[j.index+3]}" /></td>
											<td width="10%"><c:out value="${table[j.index+4]}" /></td>
											<td width="10%"><c:out value="${table[j.index+5]}" /></td>
											<td width="20%"><c:out value="${table[j.index+6]}" /></td>
<%-- 											<td width="13%"><c:out value="${table[j.index+7]}" /></td> --%>
											<td width="10%">
											<a href="editMasterFileForm.htm?unitId=${table[j.index]}" class="blue">
												<img border="0" alt="Edit" src="../img/edit-file-icon.png" width="24" height="24"></a> 
												
												</td>
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
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<!-- /footer content -->
</div>
</div>

<%@ include file="/WEB-INF/jsp/bottom.jsp"%>

</body>
</html>