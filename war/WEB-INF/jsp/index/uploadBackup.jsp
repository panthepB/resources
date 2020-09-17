<%@ include file="/WEB-INF/jsp/include.jsp"%>
<head>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/WEB-INF/jsp/title.jsp"%>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<link rel="stylesheet" href="../styles/styles.css">
<script src="http://code.jquery.com/jquery-latest.min.js"
	type="text/javascript"></script>
<script src="js/script.js"></script>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.validationEngine.js"></script>
<link rel="stylesheet" href="styles/validationEngine.jquery.css"
	type="text/css" />

<link href="styles/bootstrap.css" rel="stylesheet" />
<link href="styles/font-awesome.css" rel="stylesheet" />
<link href="styles/basic.css" rel="stylesheet" />
<link href="styles/custom.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />

<%@ include file="/WEB-INF/jsp/index/sideBar.jsp"%>

<script src="js/jquery-1.10.2.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/jquery.metisMenu.js"></script>
<script src="js/custom.js"></script>


<div id="page-wrapper">
	<div id="page-inner">

		<div class="row">
			<div class="col-md-12">
				<h1 class="page-head-line">DLT Send data</h1>
				<h1 class="page-subhead-line"><%@ include
						file="/WEB-INF/jsp/version.jsp"%>
				</h1>


			</div>
		</div>

		<!-- 		<div class="panel panel-default"> -->

		<div class="panel-body">
			<c:if test="${status == 200}">
				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					บันทึกสำเร็จ
				</div>
			</c:if>
			<c:if test="${status == 400}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					รูปแบบข้อมูลไม่ถูกต้อง ไม่สามารถบันทึกได้
				</div>
			</c:if>
			<c:if test="${status == 500}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					บันทึกไม่สำเร็จ (เกิดจากปัญหาภายในระบบ)
				</div>
			</c:if>
			<c:if test="${status == 403}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					ไม่อนุญาตให้เพิ่ม/แก้ไขข้อมูลดังกล่าว
					เนื่องจากเป็นข้อมูลของผู้ประกอบการท่านอื่น
				</div>
			</c:if>
			<c:if test="${status == 401}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					ล็อกอินไม่ถูกต้อง
				</div>
			</c:if>


		</div>

		<form action="UploadServlet" method="post"
			enctype="multipart/form-data">
			<input type="file" name="file" size="50" /> <br /> <input
				type="submit" value="Upload File" />
		</form>
		<!-- 		</div> -->


		<!-- /. ROW  -->
		<!-- 		<div class="row"> -->
		<!-- 			<div class="col-md-4"> -->
		<!-- 				<div class="main-box mb-red"> -->
		<!-- 					<a href="#"> <i class="fa fa-bolt fa-5x"></i> -->
		<!-- 						<h5>Zero Issues</h5> -->
		<!-- 					</a> -->
		<!-- 				</div> -->
		<!-- 			</div> -->
		<!-- 			<div class="col-md-4"> -->
		<!-- 				<div class="main-box mb-dull"> -->
		<!-- 					<a href="#"> <i class="fa fa-plug fa-5x"></i> -->
		<!-- 						<h5>40 Task In Check</h5> -->
		<!-- 					</a> -->
		<!-- 				</div> -->
		<!-- 			</div> -->
		<!-- 			<div class="col-md-4"> -->
		<!-- 				<div class="main-box mb-pink"> -->
		<!-- 					<a href="#"> <i class="fa fa-dollar fa-5x"></i> -->
		<!-- 						<h5>200K Pending</h5> -->
		<!-- 					</a> -->
		<!-- 				</div> -->
		<!-- 			</div> -->

		<!-- 		</div> -->
		<!-- /. ROW  -->

		<!-- 		<div class="row"> -->
		<!-- 			<div class="col-md-8"> -->
		<!-- 				<div class="row"> -->
		<!-- 					<div class="col-md-12"> -->

		<!-- 						<div id="reviews" class="carousel slide" data-ride="carousel"> -->

		<!-- 							<div class="carousel-inner"> -->
		<!-- 								<div class="item active"> -->

		<!-- 									<div class="col-md-10 col-md-offset-1"> -->

		<!-- 										<h4> -->
		<!-- 											<i class="fa fa-quote-left"></i>Lorem ipsum dolor sit amet, consectetur adipiscing Lorem ipsum dolor sit amet, consectetur adipiscing elit onec molestie non sem vel condimentum. <i -->
		<!-- 												class="fa fa-quote-right"></i> -->
		<!-- 										</h4> -->
		<!-- 										<div class="user-img pull-right"> -->
		<!-- 											<img src="img/user.gif" alt="" class="img-u image-responsive" /> -->
		<!-- 										</div> -->
		<!-- 										<h5 class="pull-right"> -->
		<!-- 											<strong class="c-black">Lorem Dolor</strong> -->
		<!-- 										</h5> -->
		<!-- 									</div> -->
		<!-- 								</div> -->
		<!-- 								<div class="item"> -->
		<!-- 									<div class="col-md-10 col-md-offset-1"> -->

		<!-- 										<h4> -->
		<!-- 											<i class="fa fa-quote-left"></i>Lorem ipsum dolor sit amet, consectetur adipiscing Lorem ipsum dolor sit amet, consectetur adipiscing elit onec molestie non sem vel condimentum. <i -->
		<!-- 												class="fa fa-quote-right"></i> -->
		<!-- 										</h4> -->
		<!-- 										<div class="user-img pull-right"> -->
		<!-- 											<img src="img/user.png" alt="" class="img-u image-responsive" /> -->
		<!-- 										</div> -->
		<!-- 										<h5 class="pull-right"> -->
		<!-- 											<strong class="c-black">Lorem Dolor</strong> -->
		<!-- 										</h5> -->
		<!-- 									</div> -->

		<!-- 								</div> -->
		<!-- 								<div class="item"> -->
		<!-- 									<div class="col-md-10 col-md-offset-1"> -->

		<!-- 										<h4> -->
		<!-- 											<i class="fa fa-quote-left"></i>Lorem ipsum dolor sit amet, consectetur adipiscing Lorem ipsum dolor sit amet, consectetur adipiscing elit onec molestie non sem vel condimentum. <i -->
		<!-- 												class="fa fa-quote-right"></i> -->
		<!-- 										</h4> -->
		<!-- 										<div class="user-img pull-right"> -->
		<!-- 											<img src="img/user.gif" alt="" class="img-u image-responsive" /> -->
		<!-- 										</div> -->
		<!-- 										<h5 class="pull-right"> -->
		<!-- 											<strong class="c-black">Lorem Dolor</strong> -->
		<!-- 										</h5> -->
		<!-- 									</div> -->
		<!-- 								</div> -->
		<!-- 							</div> -->
		<!-- 							INDICATORS -->
		<!-- 							<ol class="carousel-indicators"> -->
		<!-- 								<li data-target="#reviews" data-slide-to="0" class="active"></li> -->
		<!-- 								<li data-target="#reviews" data-slide-to="1"></li> -->
		<!-- 								<li data-target="#reviews" data-slide-to="2"></li> -->
		<!-- 							</ol> -->
		<!-- 							PREVIUS-NEXT BUTTONS -->

		<!-- 						</div> -->

		<!-- 					</div> -->

		<!-- 				</div> -->
		<!-- 				/. ROW  -->



		<!-- 			</div> -->
		<!-- 			<div class="col-md-4"> -->
		<!-- 				<div class="panel panel-info"> -->
		<!-- 					<div class="panel-heading"> -->
		<!-- 						<i class="fa fa-bell fa-fw"></i>Notifications Panel -->
		<!-- 					</div> -->

		<!-- 					<div class="panel-body"> -->
		<!-- 						<div class="list-group"> -->

		<!-- 							<a href="#" class="list-group-item"> <i class="fa fa-twitter fa-fw"></i>3 New Followers <span class="pull-right text-muted small"><em>12 minutes ago</em> </span> -->
		<!-- 							</a> <a href="#" class="list-group-item"> <i class="fa fa-envelope fa-fw"></i>Message Sent <span class="pull-right text-muted small"><em>27 minutes ago</em> </span> -->
		<!-- 							</a> <a href="#" class="list-group-item"> <i class="fa fa-tasks fa-fw"></i>New Task <span class="pull-right text-muted small"><em>43 minutes ago</em> </span> -->
		<!-- 							</a> <a href="#" class="list-group-item"> <i class="fa fa-upload fa-fw"></i>Server Rebooted <span class="pull-right text-muted small"><em>11:32 AM</em> </span> -->
		<!-- 							</a> <a href="#" class="list-group-item"> <i class="fa fa-bolt fa-fw"></i>Server Crashed! <span class="pull-right text-muted small"><em>11:13 AM</em> </span> -->
		<!-- 							</a> <a href="#" class="list-group-item"> <i class="fa fa-warning fa-fw"></i>Server Not Responding <span class="pull-right text-muted small"><em>10:57 AM</em> </span> -->
		<!-- 							</a> <a href="#" class="list-group-item"> <i class="fa fa-bolt fa-fw"></i>Server Crashed! <span class="pull-right text-muted small"><em>11:13 AM</em> </span> -->
		<!-- 							</a> <a href="#" class="list-group-item"> <i class="fa fa-warning fa-fw"></i>Server Not Responding <span class="pull-right text-muted small"><em>10:57 AM</em> </span> -->
		<!-- 							</a> <a href="#" class="list-group-item"> <i class="fa fa-shopping-cart fa-fw"></i>New Order Placed <span class="pull-right text-muted small"><em>9:49 AM</em> </span> -->
		<!-- 							</a> -->
		<!-- 						</div> -->
		<!-- 						/.list-group -->
		<!-- 						<a href="#" class="btn btn-info btn-block">View All Alerts</a> -->
		<!-- 					</div> -->

		<!-- 				</div> -->
		<!-- 			</div> -->
		<!-- 		</div> -->

		<!-- 	</div> -->
		<!-- /. PAGE INNER  -->
	</div>
	<!-- /. PAGE WRAPPER  -->
</div>
<!-- /. WRAPPER  -->

<div id="footer-sec">
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</div>
</body>
</html>
