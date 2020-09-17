<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css" media="screen">
@import
url(
"<c:url value="
/
resources
/dojo/resources/dojo.css"/>");
@import
url(
"<c:url value="
/
resources
/dijit/themes/soria/soria.css"/>");
@import
url(
"<c:url value="
/
static
/styles/style.css"/>");
@import
url(
"<c:url value="
/
static
/styles/gygar.css"/>");
</style>
<!--[if lte IE 7]>
	<style type="text/css">
/* hack to compensate for IE and percentage widths rounding errors */
#topbar {width:60.6%;}
#outer3 {width:29.8%;}
#outer5 {width:19.2%;}
/* for IE6 */
* html #contentLayout {display:inline-block;}
/*
* html #wrapper {width: expression(document.body.clientWidth < 802? "800px" : document.body.clientWidth > 1002? "1000px" : "auto");}
* html #menu ul {width: expression(document.body.clientWidth < 802? "800px" : document.body.clientWidth > 1002? "960px" : "auto");}
*/
</style>
	<![endif]-->


<script type="text/javascript"
	src="<c:url value="/resources/dojo/dojo.js"/>"
	djconfig="parseOnLoad:true"></script>
<script type="text/javascript"
	src="<c:url value="/resources/spring/Spring.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/spring/Spring-Dojo.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/static/js/gygar.js"/>"></script>
<script type="text/javascript">dojo.require("dojo.parser");</script>

</head>
<body class="nav-md">
	<div id="wrapper">
		<div id="header">
			<%-- 		<%@ include file="banner.jsp" %> --%>
			<c:choose>
				<c:when test="${not empty userLogin.name }">
					<%@ include file="member_navi.jsp"%>
				</c:when>
				<c:otherwise>
					<%@ include file="navi.jsp"%>
				</c:otherwise>
			</c:choose>
		</div>
		<div id="contentLayout">
			<c:choose>
				<c:when test="${not empty userLogin.name}">
					<%@ include file="member_sidebar.jsp"%>
				</c:when>
				<c:otherwise>
					<%-- 				<%@ include file="sidebar.jsp" %> --%>
				</c:otherwise>
			</c:choose>