<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />

<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.3.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.12/js/dataTables.jqueryui.min.js"></script>


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
			<h3>ตรวจสอบข้อมูลผู้ใช้</h3>
		</div>
	</div>
	<div class="clearfix"></div>

	<c:if test="${status == 100}">
		<div class="panel-body">
			<!-- Update driver log service started!! -->
			<div class="alert alert-success alert-dismissable">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">×</button>
				Realtime mdvr service started!!
			</div>
		</div>
	</c:if>
	<c:if test="${status == 101}">
		<div class="panel-body">
			<!-- Update driver log service started!! -->
			<div class="alert alert-success alert-dismissable">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">×</button>
				Update driver log service started!!
			</div>
		</div>
	</c:if>
	<c:if test="${status == 102}">
		<div class="panel-body">
			<!-- Update driver log service started!! -->
			<div class="alert alert-success alert-dismissable">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">×</button>
				Location backup send to server
			</div>
		</div>
	</c:if>

	<div class="panel panel-info">

		<div class="panel panel-default">


			<!-- 			<div class="panel-heading">ตรวจสอบข้อมูลผู้ใช้</div> -->
			<div class="panel-body">
				<div class="table-responsive">
					<table id="example" class="table table-striped table-bordered"
						cellspacing="0" width="100%">
						<thead>
							<tr>
								<th align="center">ลำดับ</th>
								<th align="center">ชื่อผู้ใช้</th>
								<!-- 								<th align="center">รหัสผ่าน</th> -->
								<th align="center">ชื่อในระบบ</th>
								<th align="center">ที่อยู่</th>
								<th align="center">วันที่สร้าง</th>
								<th align="center">วันหมดอายุ</th>

							</tr>
						</thead>

						<tbody>

							<c:forEach items="${row}" var="row" varStatus="j">
								<c:forEach items="${table}" var="table" varStatus="i">
									<c:if test="${j.index % 8 == 0}">

										<tr>
											<td width="5%" align="right">${table[j.index]}</td>
											<td width="10%">${table[j.index+1]}</td>
											<%-- 											<td width="5%"><c:out value="${table[j.index+2]}" /></td> --%>
											<td width="12%"><c:out value="${table[j.index+3]}" /></td>
											<td width="35%"><c:out value="${table[j.index+4]}" /></td>
											<td width="20%" align="center"><c:out
													value="${table[j.index+5]}" /></td>
											<td width="20%"><c:out value="${table[j.index+6]}" /></td>

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