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
		$('#example').DataTable();
	});
</script>


<!-- /top navigation -->

<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>DLT send data</h3>
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


			<div class="panel-heading">List master file</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table id="example" class="table table-striped table-bordered"
						cellspacing="0" width="100%">
						<thead>
							<tr>
								<th align="center">ลำดับ</th>
								<th align="center">ชื่ออุปกรณ์</th>
								<th align="center">DevNum / UnitId</th>
								<th align="center">ชื่อผู้ขับขี่</th>
								<th align="center">เลขที่ใบขับขี่</th>
								<th align="center">สถานะ</th>
								<th align="center">เวลารูดบัตร</th>

							</tr>
						</thead>

						<tbody>

							<c:forEach items="${row}" var="row" varStatus="j">
								<c:forEach items="${table}" var="table" varStatus="i">
									<c:if test="${j.index % 7 == 0}">

										<tr>
											<td width="5%" align="right">${table[j.index]}</td>
											<td width="12%">${table[j.index+1]}</td>
											<td width="27%"><c:out value="${table[j.index+2]}" /></td>
											<td width="20%"><c:out value="${table[j.index+3]}" /></td>
											<td width="10%"><c:out value="${table[j.index+4]}" /></td>
											<td width="5%" align="center"><c:if
													test="${table[j.index+5] == 0}">
													<font color="red">ล็อกเอาท์</font>
												</c:if> <c:if test="${table[j.index+5] == 1}">
													<font color="green">ล็อกอิน</font>
												</c:if> <%-- 											<c:out value="${table[j.index+5]}" /></td> --%>
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