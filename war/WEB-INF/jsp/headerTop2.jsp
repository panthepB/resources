<!DOCTYPE html>
<html lang="en">

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <title><%@ include file="/WEB-INF/jsp/sysName.jsp"%></title>
    <link rel="icon" href="../img/icons8-transfer-96.png">
    
    <link rel="stylesheet" type="text/css" href="../styles/jquery.datetimepicker.css">
	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../js/jquery.datetimepicker.js"></script>
	<script type="text/javascript" src="../js/jszip.js"></script>
	<script type="text/javascript" src="../js/vfs_fonts.js"></script>
<!-- 	<script type="text/javascript" src="../js/pdfmake.minjs"></script> -->

<!--     <link href="../styles/datetimepicker.css" rel="stylesheet"> -->
<!--     Bootstrap -->
    <link href="../styles/dashboard_assets/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<!--     Font Awesome -->
    <link href="../styles/dashboard_assets/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<!--     NProgress -->
    <link href="../styles/dashboard_assets/vendors/nprogress/nprogress.css" rel="stylesheet">
<!--     bootstrap-progressbar -->
    <link href="../styles/dashboard_assets/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <link href="../styles/dashboard_assets/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
<!--     Custom Theme Style -->
    <link href="../styles/dashboard_assets/build/css/custom.css" rel="stylesheet">
    
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.css">
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.full.js"></script>
    


<!--     iCheck -->
    <link href="../styles/dashboard_assets/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
<!--     bootstrap -->
    <link href="../styles/dashboard_assets/vendors/google-code-prettify/bin/prettify.min.css" rel="stylesheet">
<!--     Select2 -->
    <link href="../styles/dashboard_assets/vendors/select2/dist/css/select2.min.css" rel="stylesheet">
<!--     Switchery -->
    <link href="../styles/dashboard_assets/vendors/switchery/dist/switchery.min.css" rel="stylesheet">
<!--     starrr -->
    <link href="../styles/dashboard_assets/vendors/starrr/dist/starrr.css" rel="stylesheet">
<!--     Datatables -->
    <link href="../styles/dashboard_assets/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="../styles/dashboard_assets/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="../styles/dashboard_assets/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="../styles/dashboard_assets/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="../styles/dashboard_assets/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
 <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9/dist/sweetalert2.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/sweetalert2@9/dist/sweetalert2.min.css" rel="stylesheet">
 
 </head>
  
  <body class="nav-md" >

	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="../member.htm" class="site_title"><span><%@ include file="/WEB-INF/jsp/sysName.jsp"%></span></a>
					</div>
					
					<div class="clearfix"></div>

					<!-- menu profile quick info -->
					<%@ include file="/WEB-INF/jsp/menuProfile_quickInfo.jsp"%>
					<!-- /menu profile quick info -->

					<br />

					<!-- sidebar menu -->
					<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
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

			<li role="presentation" class="dropdown"><a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false"> <i class="fa fa-envelope-o"></i> <span
					class="badge bg-green">${noticesize}</span>
			</a>
				<ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
					<c:forEach items="${notices}" var="notice" varStatus="n">
						<li><a> <span class="image"><img src="../styles/dashboard_assets/production/images/icon010.png" alt="Profile Image" /></span> <span> <span class="time">${notice.createDate}</span>
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

