<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop.jsp"%>
<title>ระบบจัดการการบริหารลูกค้า :: ตารางการบริการ</title>
<link rel='stylesheet' type='text/css'
	href='<c:url value="/static/styles/fullcalendar/fullcalendar.css"/>' />
<link rel='stylesheet' type='text/css'
	href='<c:url value="/static/styles/fullcalendar/fullcalendar.print.css"/>'
	media='print' />
<script type='text/javascript'
	src='<c:url value="/static/js/fullcalendar/jquery-1.5.2.min.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/static/js/fullcalendar/jquery-ui-1.8.11.custom.min.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/static/js/fullcalendar/fullcalendar.min.js"/>'></script>
<script type='text/javascript'>
	
		$(document).ready(function() {
			
			$('#calendar').fullCalendar({
				header: {
					left: 'prev,next today',
					center: 'title',
					right: 'month,agendaWeek,agendaDay'
				},
				buttonText: {
					today : 'วันนี้',
					month : 'เดือน',
					week : 'สัปดาห์',
					day : 'วัน'
				},
				monthNames:['มกราคม', 'กุมภาพันธ์', 'มีนาคม', 'เมษยน', 'พฤษภาคม', 'มิถุนายน', 'กรกฏาคม',
				            'สิงหาคม', 'กันยายน', 'ตุลาคม', 'พฤศจิกายน', 'ธันวาคม'],
				monthNamesShort:['ม.ค.', 'ก.พ.', 'มี.ค.', 'เม.ย.', 'พ.ค.', 'มิ.ย.', 'ก.ค.',
				            'ส.ค.', 'ก.ย.', 'ต.ค.', 'พ.ย.', 'ธ.ค.'],
				dayNames:['วันอาทิตย์','วันจันทร์','วันอังคาร','วันพุธ','วันพฤหัสบดี','วันศุกร์','วันเสาร์'],
				dayNamesShort:['อ','จ','อ','พ','พฤ','ศ','ส'],
				editable: false,
				events: [
					<c:forEach items="${informViews}" var="informView" varStatus="n">
					<c:if test="${n.count gt 1}">,</c:if>
						{
							title: '${informView.title}',
							start: new Date(${informView.yearStart}, ${informView.monthStart}, ${informView.dayStart}, ${informView.hourStart}, ${informView.minuteStart}),
							end: new Date(${informView.yearStart}, ${informView.monthEnd}, ${informView.dayEnd}, 17, 30),
							url: 'viewInform.htm?informId=${informView.informId}',
							color : <c:choose><c:when test="${informView.type eq 1}">'#996633'</c:when><c:otherwise>'#99CC66'</c:otherwise></c:choose>,
							textColor : <c:choose><c:when test="${informView.type eq 1}">'white'</c:when><c:otherwise>'black'</c:otherwise></c:choose>,
							allDay: false
						}
					</c:forEach>
				]
			});
			
		});
	
	</script>
<style type='text/css'>
body {
	margin-top: 40px;
	text-align: center;
	font-size: 14px;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
}

#calendar {
	width: 725px;
	margin: 0 auto;
}
</style>
<%@ include file="/WEB-INF/jsp/headerBottom.jsp"%>

<div class="content">
	<div class="Post">
		<div class="Post-body">
			<div class="Post-inner">
				<h2 class="PostHeaderIcon-wrapper">
					<span class="PostHeader">ตารางการบริการ</span>
				</h2>
				<div class="PostContent">
					<p>
					<div id='calendar'></div>
					</p>
					<p>
						* คลิก วันที่ เพื่อแสดงรายละเอียด<br /> * สี แดง คือ
						การแจ้งงานติดตั้ง<br /> * สี เขียว คือ การแจ้งงานบริการ<br />
					</p>
				</div>
				<div class="cleared"></div>
			</div>
		</div>
	</div>
</div>

</div>
<div id="pageNumber" menu="m2" sub="m2s2" class="cleared"></div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</div>
<%@ include file="/WEB-INF/jsp/version.jsp"%>
</body>
</html>