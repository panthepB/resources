<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/WEB-INF/jsp/title.jsp"%>

<meta http-equiv="X-UA-Compatible" content="IE=edge">

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
				<!-- 						<h1 class="page-head-line">HOME</h1> -->
				<h1 class="page-subhead-line">DLT Send data version 1.0.0</h1>

			</div>
		</div>
		<!-- /. ROW  -->
		<div class="row">
			<div class="col-md-4">
				<div class="main-box mb-red">
					<a href="#"> <i class="fa fa-bolt fa-5x"></i>
						<h5>${status}</h5>
					</a>
				</div>
			</div>
			<!-- 					<div class="col-md-4"> -->
			<!-- 						<div class="main-box mb-dull"> -->
			<!-- 							<a href="#"> <i class="fa fa-plug fa-5x"></i> -->
			<!-- 								<h5>40 Task In Check</h5> -->
			<!-- 							</a> -->
			<!-- 						</div> -->
			<!-- 					</div> -->
			<!-- 					<div class="col-md-4"> -->
			<!-- 						<div class="main-box mb-pink"> -->
			<!-- 							<a href="#"> <i class="fa fa-dollar fa-5x"></i> -->
			<!-- 								<h5>200K Pending</h5> -->
			<!-- 							</a> -->
			<!-- 						</div> -->
		</div>

	</div>
	<!-- /. ROW  -->

	<!-- 				<hr /> -->

	<!--/.ROW-->
</div>
<!-- /. PAGE INNER  -->
</div>
<!-- /. PAGE WRAPPER  -->
</div>
<!-- /. WRAPPER  -->

<div id="footer-sec">
	<center><%@ include file="/WEB-INF/jsp/footer.jsp"%>
		<%@ include file="/WEB-INF/jsp/version.jsp"%></center>
</div>
<!-- /. FOOTER  -->
</body>
</html>
