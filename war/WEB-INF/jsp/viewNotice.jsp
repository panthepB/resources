<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop.jsp"%>
<title>ระบบจัดการการบริการลูกค้า</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp"%>

<div class="content">
	<div class="Post">
		<div class="Post-body">
			<div class="Post-inner">
				<h2 class="PostHeaderIcon-wrapper">
					<img src="<c:url value="/images/PostHeaderIcon.png"/>" /> <span
						class="PostHeader">${notice.subject }</span>
				</h2>
				<h4>(สร้างเมื่อ ${notice.createDateTime})</h4>
				<hr />
				<div class="PostContent">${notice.desc }</div>
				<hr />
				<p>
					<a href="<c:url value="/" />">กลับ</a>
				</p>
				<div class="cleared"></div>
			</div>
		</div>
	</div>
</div>
</div>
<div id="pageNumber" menu="m1" sub="" class="cleared"></div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</div>
<%@ include file="/WEB-INF/jsp/version.jsp"%>
</body>
</html>