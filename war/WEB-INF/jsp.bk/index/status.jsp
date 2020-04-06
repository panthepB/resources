<%@ include file="/WEB-INF/jsp/include.jsp"%>
<head>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/WEB-INF/jsp/title.jsp"%>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<link rel="stylesheet" href="../styles/styles.css">
<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
<script src="../js/script.js"></script>

<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery.validationEngine.js"></script>
<link rel="stylesheet" href="../styles/validationEngine.jquery.css" type="text/css" />

<link href="../styles/bootstrap.css" rel="stylesheet" />
<link href="../styles/font-awesome.css" rel="stylesheet" />
<link href="../styles/basic.css" rel="stylesheet" />
<link href="../styles/custom.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />

<%@ include file="/WEB-INF/jsp/index/sideBar.jsp"%>

<script src="../js/jquery-1.10.2.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/jquery.metisMenu.js"></script>
<script src="../js/custom.js"></script>


<div id="page-wrapper">
	<div id="page-inner">

		<div class="row">
			<div class="col-md-12">
				<h1 class="page-head-line">DLT Send data</h1>
				<h1 class="page-subhead-line">
				</h1>


			</div>
		</div>

		<!-- 		<div class="panel panel-default"> -->

		<div class="panel-body">

				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
					${status}
				</div>
			
			
		</div>

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
