<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop.jsp"%>
<title>ระบบจัดการการบริหารลูกค้า :: สถานะการแจ้งงานติดตั้ง</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp"%>

<div class="content">
	<div class="Post">
		<div class="Post-body">
			<div class="Post-inner">
				<h2 class="PostHeaderIcon-wrapper">
					<span class="PostHeader">สถานะการแจ้งงานติดตั้ง</span>
				</h2>
				<div class="PostContent">
					<p></p>
				</div>
				<div class="cleared"></div>
			</div>
		</div>
	</div>
</div>

</div>
<div id="pageNumber" menu="m4" sub="m4s1" class="cleared"></div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</div>
<%@ include file="/WEB-INF/jsp/version.jsp"%>
</body>
<script type="text/javascript">
	dojo.addOnLoad(alertUser('${alert.text}'));	
</script>
</html>