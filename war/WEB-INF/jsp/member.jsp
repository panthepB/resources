<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop.jsp"%>

<!-- <script src="sweetalert2.all.min.js"></script> -->
<!-- Optional: include a polyfill for ES6 Promises for IE11 and Android browser -->
<script src="https://cdn.jsdelivr.net/npm/promise-polyfill"></script>
<!-- <script src="sweetalert2.min.js"></script> -->
<!-- <link rel="stylesheet" href="sweetalert2.min.css"> -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@8"></script>

<!-- style CSS
		============================================ -->
<!-- <link rel="stylesheet" href="styles/style2.css"> -->
<!-- responsive CSS
		============================================ -->
<link rel="stylesheet" href="styles/css/responsive.css">
<meta http-equiv="refresh" content="60" />
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="member.htm" class="site_title"><span><%@ include
									file="/WEB-INF/jsp/sysName.jsp"%></span></a>
					</div>

					<div class="clearfix"></div>

					<!-- menu profile quick info -->
					<%@ include file="/WEB-INF/jsp/menuProfile_quickInfo.jsp"%>
					<!-- /menu profile quick info -->

					<br />

					<!-- sidebar menu -->
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">
						<div class="menu_section">
							<h3><%@ include file="/WEB-INF/jsp/text.jsp"%></h3>
							<ul class="nav side-menu">
								<%@ include file="/WEB-INF/jsp/member_navi.jsp"%>
							</ul>
						</div>

					</div>
					<!-- /sidebar menu -->
				</div>
			</div>

			<!-- top navigation -->

			<!-- member_sidebar -->
			<%@ include file="/WEB-INF/jsp/member_sidebar.jsp"%>
			<!-- /member_sidebar -->

			<li role="presentation" class="dropdown"><a href="javascript:;"
				class="dropdown-toggle info-number" data-toggle="dropdown"
				aria-expanded="false"> <i class="fa fa-envelope-o"></i> <span
					class="badge bg-green">${noticesize}</span>
			</a>
				<ul id="menu1" class="dropdown-menu list-unstyled msg_list"
					role="menu">
					<c:forEach items="${notices}" var="notice" varStatus="n">
						<li><a> <span class="image"><img
									src="styles/dashboard_assets/production/images/icon010.png"
									alt="Profile Image" /></span> <span> <span class="time">${notice.createDate}</span>
							</span> <span class="message"> ${notice.subject} </span>
						</a></li>
					</c:forEach>
					<li>
						<div class="text-center">
							<a> <strong>Close</strong> <i class="fa fa-close"></i>
							</a>
						</div>
					</li>
				</ul></li>

			</ul>
			</nav>
		</div>
	</div>
	<!-- /top navigation -->

	<div class="right_col" role="main">
		<!-- top tiles -->
		<div class="page-title">
			<div class="title_left">
				<h3>Dashboard</h3>
			</div>
		</div>
		<div class="clearfix"></div>

		<div class="panel-body">

			<c:if test="${status == 200}">
				<!-- Update driver log service started!! -->

				<script type="text/javascript">
					Swal.fire(
							  'บันทึกข้อมูลเรียบร้อยแล้ว',
							  ' ',
							  'success'
							)
					</script>
				<!-- 				<div class="alert alert-success alert-dismissable"> -->
				<!-- 					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button> -->
				<!-- 					บันทึกข้อมูลเรียบร้อยแล้ว -->
				<!-- 				</div> -->
			</c:if>
			<c:if test="${status == 400}">

				<script type="text/javascript">
							Swal.fire(
							  'รูปแบบข้อมูลไม่ถูกต้อง ไม่สามารถบันทึกได้',
							  ' ',
							  'error'
							)
					</script>
				<!-- 				<div class="alert alert-danger alert-dismissable"> -->
				<!-- 					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button> -->
				<!-- 					รูปแบบข้อมูลไม่ถูกต้อง ไม่สามารถบันทึกได้ -->
				<!-- 				</div> -->
			</c:if>
			<c:if test="${status == 500}">
				<script type="text/javascript">
							Swal.fire(
							  'บันทึกไม่สำเร็จ (เกิดจากปัญหาภายในระบบ)',
							  ' ',
							  'error'
							)
					</script>

			</c:if>
			<c:if test="${status == 403}">
				<script type="text/javascript">
							Swal.fire(
							  'ไม่อนุญาตให้เพิ่ม/แก้ไขข้อมูลดังกล่าว เนื่องจากเป็นข้อมูลของผู้ประกอบการท่านอื่น',
							  ' ',
							  'error'
							)
					</script>
			</c:if>
			<c:if test="${status == 401}">

				<script type="text/javascript">
							Swal.fire(
							  'ล็อกอินไม่ถูกต้อง',
							  ' ',
							  'warning'
							)
					</script>
			</c:if>
		</div>
		<!-- /top tiles -->

		<div class="section-admin container-fluid"
			style="border: 1px solid transparent;">

			<div class="row admin text-center">

				<div class="col-md-12">
					<div style="border: 1px solid #ccc; padding: 20px;">
						<p style="font-size: 18px; text-align: left;">Online rate</p>
						<div class="row">
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
								<div class="admin-content analysis-progrebar-ctn res-mg-t-15">
									<h4 class="text-left text-uppercase">
										<b>BMTA</b>
									</h4>
									<div class="row vertical-center-box vertical-center-box-tablet">
										<div class="col-xs-3 mar-bot-15 text-left">

											<label class="label bg-green"><fmt:formatNumber
													type="number" maxFractionDigits="2"
													value="${(bmtaOnline / bmtaTotal)*100}" />% <i
												class="fa fa-level-up" aria-hidden="true"></i></label>
										</div>
										<div class="col-xs-9 cus-gh-hd-pro">
											<h2 class="text-right no-margin">${bmtaOnline}/${bmtaTotal}</h2>
										</div>
									</div>
									<div class="progress progress-mini">
										<div style="width: ${(bmtaOnline / bmtaTotal)*100}%;"
											class="progress-bar bg-green"></div>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12"
								style="margin-bottom: 1px;">
								<div class="admin-content analysis-progrebar-ctn res-mg-t-30">
									<h4 class="text-left text-uppercase">
										<b>TG</b>
									</h4>
									<div class="row vertical-center-box vertical-center-box-tablet">
										<div class="text-left col-xs-3 mar-bot-15">
											<label class="label bg-red"><fmt:formatNumber
													type="number" maxFractionDigits="2"
													value="${(tgOnline / tgTotal)*100}" />% <i
												class="fa fa-level-up" aria-hidden="true"></i></label>
										</div>
										<div class="col-xs-9 cus-gh-hd-pro">
											<h2 class="text-right no-margin">${tgOnline}/${tgTotal}</h2>
										</div>
									</div>
									<div class="progress progress-mini">
										<div style="width: ${(tgOnline / tgTotal)*100}%;"
											class="progress-bar progress-bar-danger bg-red"></div>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
								<div class="admin-content analysis-progrebar-ctn res-mg-t-30">
									<h4 class="text-left text-uppercase">
										<b>MHD TG</b>
									</h4>
									<div class="row vertical-center-box vertical-center-box-tablet">
										<div class="text-left col-xs-3 mar-bot-15">
											<label class="label bg-blue"><fmt:formatNumber
													type="number" maxFractionDigits="2"
													value="${(mdvrOnline / mdvrTotal)*100}" />%<i
												class="fa fa-level-up" aria-hidden="true"></i></label>
										</div>
										<div class="col-xs-9 cus-gh-hd-pro">
											<h2 class="text-right no-margin">${mdvrOnline}/${mdvrTotal}</h2>
										</div>
									</div>
									<div class="progress progress-mini">
										<div style="width: ${(mdvrOnline / mdvrTotal)*100}0%;"
											class="progress-bar bg-blue"></div>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
								<div class="admin-content analysis-progrebar-ctn res-mg-t-30">
									<h4 class="text-left text-uppercase">
										<b>MDVR</b>
									</h4>
									<div class="row vertical-center-box vertical-center-box-tablet">
										<div class="text-left col-xs-3 mar-bot-15">
											<label class="label bg-purple">0% <i
												class="fa fa-level-up" aria-hidden="true"></i>&nbsp;
											</label>
										</div>
										<div class="col-xs-9 cus-gh-hd-pro">
											<h2 class="text-right no-margin">0/0</h2>
										</div>
									</div>
									<div class="progress progress-mini">
										<div style="width: 0%;" class="progress-bar bg-purple"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-lg-6" style="float: right; text-align: left;">
					<div class="panel panel-default" style="margin: 30px 0">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Notifications Panel
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="list-group">
								<%-- 						<c:choose> --%>
								<%-- 							<c:when test="${DTK3GStatus.status == 200}"> --%>
								<!--       							<a href="#" class="list-group-item" style="background: #6ED746;" > -->
								<%--     						</c:when> --%>
								<%-- 							<c:otherwise> --%>
								<!--         						<a href="#" class="list-group-item" style="background: #E9967A;"> -->
								<%--     						</c:otherwise> --%>
								<%-- 						</c:choose> --%>
								<%-- 						<i class="fa fa-refresh fa-fw" ></i> DTK3G-100T Status : ${DTK3GStatus.status} <span class="pull-right text-muted small" ><em>Lastupdate &nbsp;&nbsp;${DTK3GStatus.size}  &nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;  ${DTK3GStatus.timeUpdate}</em> </span>  --%>

								<c:choose>
									<c:when test="${MDVRSGWStatus2.status == 200}">
										<a href="#" class="list-group-item"
											style="background: #6ED746;">
									</c:when>
									<c:otherwise>
										<a href="#" class="list-group-item"
											style="background: #E9967A;">
									</c:otherwise>
								</c:choose>
								<i class="fa fa-refresh fa-fw"></i> MDVR SGW 75 Status :
								${MDVRSGWStatus2.status} <span
									class="pull-right text-muted small"><em>Lastupdate
										&nbsp;&nbsp;${MDVRSGWStatus2.size} &nbsp;&nbsp; :
										&nbsp;&nbsp;&nbsp; ${MDVRSGWStatus2.timeUpdate}</em> </span>

								<c:choose>
									<c:when test="${MDVRMHDStatus.status == 200}">
										<a href="#" class="list-group-item"
											style="background: #6ED746;">
									</c:when>
									<c:otherwise>
										<a href="#" class="list-group-item"
											style="background: #E9967A;">
									</c:otherwise>
								</c:choose>
								<i class="fa fa-refresh fa-fw"></i> MDVR MHD Status :
								${MDVRMHDStatus.status} <span
									class="pull-right text-muted small"><em>Lastupdate
										&nbsp;&nbsp;${MDVRMHDStatus.size} &nbsp;&nbsp; :
										&nbsp;&nbsp;&nbsp; ${MDVRMHDStatus.timeUpdate}</em> </span>

								<c:choose>
									<c:when test="${BMTAStatus == 200}">
										<a href="#" class="list-group-item"
											style="background: #6ED746;">
									</c:when>
									<c:otherwise>
										<a href="#" class="list-group-item"
											style="background: #E9967A;">
									</c:otherwise>
								</c:choose>
								<i class="fa fa-refresh fa-fw"></i> BMTA Status : ${BMTAStatus}
								<span class="pull-right text-muted small"><em>Lastupdate
										&nbsp;&nbsp;${BMTASize} &nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;
										${BMTATimeUpdate}</em> <c:choose>
										<c:when test="${TGStatus == '200'}">
											<a href="#" class="list-group-item"
												style="background: #6ED746;">
										</c:when>
										<c:otherwise>
											<a href="#" class="list-group-item"
												style="background: #E9967A;">
										</c:otherwise>
									</c:choose> <i class="fa fa-refresh fa-fw"></i> DISTAR GPS Status :
									${TGStatus} <span class="pull-right text-muted small"><em>Lastupdate
											&nbsp;&nbsp;${TGSize} &nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;
											${TGTimeUpdate}</em> </span> </a>
							</div>
							<!-- /.list-group -->
							<a href="#" class="btn btn-default btn-block">View All Alerts</a>
						</div>
						<!-- /.panel-body -->
					</div>
				</div>

				<!-- /page content -->

			</div>
			<!-- footer content -->

			<footer>
				<div class="pull-right">
					Distar Masterfile management &copy;<a
						href="https://www.distar.co.th">Distar International co.,ltd</a>
				</div>
				<div class="clearfix"></div>
			</footer>
			<!-- /footer content -->
		</div>
	</div>

	<!-- jQuery -->
	<script src="styles/dashboard_assets/vendors/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script
		src="styles/dashboard_assets/vendors/bootstrap/dist/js/bootstrap.min.js"></script>

	<script src="styles/dashboard_assets/build/js/custom.min.js"></script>

</body>
</html>