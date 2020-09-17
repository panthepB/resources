
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<!-- /top navigation -->

<link rel="stylesheet" type="text/css"
	href="http://sysapp.itoffside.com/datetimepicker/jquery.datetimepicker.css">
<script type="text/javascript"
	src="http://sysapp.itoffside.com/datetimepicker/jquery.js"></script>
<script type="text/javascript"
	src="http://sysapp.itoffside.com/datetimepicker/jquery.datetimepicker.js"></script>

<link
	href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"
	rel="stylesheet">
<script
	src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		var availableTags = [ "ActionScript", "AppleScript", "Asp", "BASIC",
				"C", "C++", "Clojure", "COBOL", "ColdFusion", "Erlang",
				"Fortran", "Groovy", "Haskell", "Java", "JavaScript", "Lisp",
				"Perl", "PHP", "Python", "Ruby", "Scala", "Scheme" ];
		$("#tags").autocomplete({
			source : availableTags
		});
	});
</script>


<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>เพิ่มข้อมูลมาสเตอร์ไฟล์</h3>
		</div>
		<div class="ui-widget">
			<label for="tags">Tags: </label> <input id="tags">
		</div>
	</div>
	<div class="clearfix"></div>


</div>


<%@ include file="/WEB-INF/jsp/bottom.jsp"%>


</body>
</html>