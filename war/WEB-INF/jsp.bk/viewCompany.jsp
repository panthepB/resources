<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/headerTop.jsp" %>
	<title>ระบบจัดการการบริการลูกค้า</title>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp" %>

				<div class="content">
				
					<div class="Post">
						<div class="Post-body">
							<div class="Post-inner">
								<div class="PostContent">
									<img src="image/about.htm" height="250px" width="720px" />
								</div>
								<div class="cleared"></div>
							</div>
						</div>
					</div>
					
					<div class="Post">
						<div class="Post-body">
							<div class="Post-inner">
								<h2 class="PostHeaderIcon-wrapper">
									<span class="PostHeader">ข้อมูลบริษัท</span>
								</h2>
								<hr/>
								<div class="PostContent">
									รหัสบริษัท : ${company.code }<br />
									ชื่อ : ${company.name}<br />
									ที่อยู่ : ${company.fullAddress}<br />
									โทร : ${company.tel}<br />
									แฟกซ์ : ${company.fax}<br />
									อีเมล : ${company.email}<br />
									เวปไซต์ : ${company.website}<br />
								</div>
								<div class="cleared"></div>
							</div>
						</div>
					</div>
					
				</div>
				
	</div>
	<div id="pageNumber" menu="m2" sub="" class="cleared"></div>
	<%@ include file="footer.jsp" %>
</div>
<%@ include file="/WEB-INF/jsp/version.jsp" %>
</body>
</html>