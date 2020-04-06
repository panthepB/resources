<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop.jsp"%>

<!-- style CSS
		============================================ -->
<!-- <link rel="stylesheet" href="styles/style2.css"> -->
<!-- responsive CSS
		============================================ -->
<link rel="stylesheet" href="styles/css/responsive.css">

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="member.htm" class="site_title"><span>DLT Send data</span></a>
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
						<li><a> <span class="image"><img src="styles/dashboard_assets/production/images/icon010.png" alt="Profile Image" /></span> <span> <span class="time">${notice.createDate}</span>
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



			<c:if test="${status == 100}">
				<!-- Update driver log service started!! -->
				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					Realtime mdvr service started!!
				</div>
			</c:if>
			<c:if test="${status == 101}">
				<!-- Update driver log service started!! -->
				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					Update driver log service started!!
				</div>
			</c:if>
			<c:if test="${status == 102}">
				<!-- Update driver log service started!! -->
				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					Location backup send to server
				</div>
			</c:if>
			<c:if test="${status == 103}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					มีหมายเลขเครื่องนี้อยู่แล้ว
				</div>
			</c:if>
			<c:if test="${status == 104}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					กรุณาใส่หมายเลขเครื่อง
				</div>
			</c:if>
			<c:if test="${status == 200}">
				<!-- Update driver log service started!! -->
				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					บันทึกข้อมูลเรียบร้อยแล้ว
				</div>
			</c:if>
			<c:if test="${status == 400}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					รูปแบบข้อมูลไม่ถูกต้อง ไม่สามารถบันทึกได้
				</div>
			</c:if>
			<c:if test="${status == 500}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					บันทึกไม่สำเร็จ (เกิดจากปัญหาภายในระบบ)
				</div>
			</c:if>
			<c:if test="${status == 403}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					ไม่อนุญาตให้เพิ่ม/แก้ไขข้อมูลดังกล่าว เนื่องจากเป็นข้อมูลของผู้ประกอบการท่านอื่น
				</div>
			</c:if>
			<c:if test="${status == 401}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					ล็อกอินไม่ถูกต้อง
				</div>
			</c:if>
		</div>
		<!-- /top tiles -->
		
		<div class="section-admin container-fluid" style="border: 1px solid transparent;">
		
            <div class="row admin text-center">
           
                <div class="col-md-12"  > 
                 <div style="border: 1px solid #ccc; padding: 20px;">
                 	<p style="font-size: 18px; text-align: left;" >
						Max online per day
					</p>
                    <div class="row" >
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                            <div class="admin-content analysis-progrebar-ctn res-mg-t-15">
                                <h4 class="text-left text-uppercase"><b>BMTA</b></h4>
                                <div class="row vertical-center-box vertical-center-box-tablet">
                                    <div class="col-xs-3 mar-bot-15 text-left">
                                    
                                        <label class="label bg-green"><fmt:formatNumber type="number" maxFractionDigits="2" value="${(bmtaOnline / bmtaTotal)*100}"/>% <i class="fa fa-level-up" aria-hidden="true"></i>&nbsp;</label>
                                    </div>
                                    <div class="col-xs-9 cus-gh-hd-pro">
                                        <h2 class="text-right no-margin">${bmtaOnline}/${bmtaTotal}</h2>
                                    </div>
                                </div>
                                <div class="progress progress-mini">
                                    <div style="width: ${(bmtaOnline / bmtaTotal)*100}%;" class="progress-bar bg-green"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12" style="margin-bottom:1px;">
                            <div class="admin-content analysis-progrebar-ctn res-mg-t-30">
                                <h4 class="text-left text-uppercase"><b>TG</b></h4>
                                <div class="row vertical-center-box vertical-center-box-tablet">
                                    <div class="text-left col-xs-3 mar-bot-15">
                                        <label class="label bg-red"><fmt:formatNumber type="number" maxFractionDigits="2" value="${(tgOnline / tgTotal)*100}"/>% <i class="fa fa-level-up" aria-hidden="true"></i>&nbsp;</label>
                                    </div>
                                    <div class="col-xs-9 cus-gh-hd-pro">
                                        <h2 class="text-right no-margin">${tgOnline}/${tgTotal}</h2>
                                    </div>
                                </div>
                                <div class="progress progress-mini">
                                    <div style="width: ${(tgOnline / tgTotal)*100}%;" class="progress-bar progress-bar-danger bg-red"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                            <div class="admin-content analysis-progrebar-ctn res-mg-t-30">
                                <h4 class="text-left text-uppercase"><b>MHD</b></h4>
                                <div class="row vertical-center-box vertical-center-box-tablet">
                                    <div class="text-left col-xs-3 mar-bot-15">
                                        <label class="label bg-blue">0% <i class="fa fa-level-up" aria-hidden="true"></i>&nbsp;</label>
                                    </div>
                                    <div class="col-xs-9 cus-gh-hd-pro">
                                        <h2 class="text-right no-margin">0/0</h2>
                                    </div>
                                </div>
                                <div class="progress progress-mini">
                                    <div style="width: 0%;" class="progress-bar bg-blue"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                            <div class="admin-content analysis-progrebar-ctn res-mg-t-30">
                                <h4 class="text-left text-uppercase"><b>MDVR</b></h4>
                                <div class="row vertical-center-box vertical-center-box-tablet">
                                    <div class="text-left col-xs-3 mar-bot-15">
                                        <label class="label bg-purple">0% <i class="fa fa-level-up" aria-hidden="true"></i>&nbsp;</label>
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
                
<div class="col-lg-6" style="float: right; text-align: left;" >
			<div class="panel panel-default" style="margin: 30px 0">
				<div class="panel-heading">
					<i class="fa fa-bell fa-fw"></i> Notifications Panel
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="list-group" >
						<c:choose>
							<c:when test="${DTK3GStatus.status == 200}">
      							<a href="#" class="list-group-item" style="background: #6ED746;" >
    						</c:when>
							<c:otherwise>
        						<a href="#" class="list-group-item" style="background: #E9967A;">
    						</c:otherwise>
						</c:choose>
						<i class="fa fa-refresh fa-fw" ></i> DTK3G-100T Status : ${DTK3GStatus.status} <span class="pull-right text-muted small" ><em>Lastupdate &nbsp;&nbsp;${DTK3GStatus.size}  &nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;  ${DTK3GStatus.timeUpdate}</em> </span> 
						
						<c:choose>
							<c:when test="${MDVRSGWStatus2.status == 200}">
      							<a href="#" class="list-group-item" style="background: #6ED746;">
    						</c:when>
							<c:otherwise>
        						<a href="#" class="list-group-item" style="background: #E9967A;">
    						</c:otherwise>
						</c:choose>
						<i class="fa fa-refresh fa-fw"></i> MDVR SGW 75 Status : ${MDVRSGWStatus2.status} <span class="pull-right text-muted small"><em>Lastupdate &nbsp;&nbsp;${MDVRSGWStatus2.size}   &nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;   ${MDVRSGWStatus2.timeUpdate}</em>
						</span>
						
						<c:choose>
							<c:when test="${MDVRMHDStatus.status == 200}">
      							<a href="#" class="list-group-item" style="background: #6ED746;">
    						</c:when>
							<c:otherwise>
        						<a href="#" class="list-group-item" style="background: #E9967A;">
    						</c:otherwise>
						</c:choose>
						<i class="fa fa-refresh fa-fw"></i> MDVR MHD Status : ${MDVRMHDStatus.status} <span class="pull-right text-muted small"><em>Lastupdate &nbsp;&nbsp;${MDVRMHDStatus.size}   &nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;   ${MDVRMHDStatus.timeUpdate}</em>
						</span>
						
						<c:choose>
							<c:when test="${LTYStatus.status == 200}">
      							<a href="#" class="list-group-item" style="background: #6ED746;">
    						</c:when>
							<c:otherwise>
        						<a href="#" class="list-group-item" style="background: #E9967A;">
    						</c:otherwise>
						</c:choose>
						<i class="fa fa-refresh fa-fw"></i> BUS LTY Status : ${LTYStatus.status} <span class="pull-right text-muted small"><em>Lastupdate &nbsp;&nbsp;${LTYStatus.size}    &nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;   ${LTYStatus.timeUpdate}</em> </span>

						<c:choose>
							<c:when test="${MDVRSGWStatus.status == 200}">
      							<a href="#" class="list-group-item" style="background: #6ED746;">
    						</c:when>
							<c:otherwise>
        						<a href="#" class="list-group-item" style="background: #E9967A;">
    						</c:otherwise>
						</c:choose>
						<i class="fa fa-refresh fa-fw"></i> TG Status : ${MDVRSGWStatus.status} <span class="pull-right text-muted small"><em>Lastupdate &nbsp;&nbsp;${MDVRSGWStatus.size}    &nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;   ${MDVRSGWStatus.timeUpdate}</em>
						</span>

<%-- 						<c:choose> --%>
<%-- 							<c:when test="${SCCC.status == 'S00'}"> --%>
<!--       							<a href="#" class="list-group-item" style="background: #6ED746;"> -->
<%--     						</c:when> --%>
<%-- 							<c:otherwise> --%>
<!--         						<a href="#" class="list-group-item" style="background: #E9967A;"> -->
<%--     						</c:otherwise> --%>
<%-- 						</c:choose> --%>
						<i class="fa fa-refresh fa-fw"></i> SCCC Status : ${SCCC.status} <span class="pull-right text-muted small"><em>Lastupdate &nbsp;&nbsp;${SCCC.size}    &nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;   ${SCCC.timeUpdate}</em>
						</span>
						</a>

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
			Distar DLT Send data by <a href="https://www.distartech.com">DistarTech co.,LTD</a>
		</div>
		<div class="clearfix"></div>
	</footer>
	<!-- /footer content -->
	</div>
	</div>

	<!-- jQuery -->
	<script src="styles/dashboard_assets/vendors/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="styles/dashboard_assets/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
	
	<script src="styles/dashboard_assets/build/js/custom.min.js"></script>

</body>
</html>