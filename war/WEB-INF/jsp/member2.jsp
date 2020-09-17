<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop.jsp"%>
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="member.htm" class="site_title"><span>DLT Send
								data</span></a>
					</div>

					<div class="clearfix"></div>

					<!-- menu profile quick info -->
					<%@ include file="/WEB-INF/jsp/menuProfile_quickInfo.jsp"%>
					<!-- /menu profile quick info -->

					<br />

					<!-- sidebar menu -->
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">
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

			<li role="presentation" class="dropdown"><a href="javascript:;"
				class="dropdown-toggle info-number" data-toggle="dropdown"
				aria-expanded="false"> <i class="fa fa-envelope-o"></i> <span
					class="badge bg-green">${noticesize}</span>
			</a>
				<ul id="menu1" class="dropdown-menu list-unstyled msg_list"
					role="menu">
					<c:forEach items="${notices}" var="notice" varStatus="n">
						<li><a> <span class="image"><img
									src="styles/dashboard_assets/production/images/icon010.png"
									alt="Profile Image" /></span> <span> <span class="time">${notice.createDate}</span>
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
				<h3>DLT send data</h3>
			</div>
		</div>
		<div class="clearfix"></div>



		<div class="panel-body">



			<c:if test="${status == 100}">
				<!-- Update driver log service started!! -->
				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					Realtime mdvr service started!!
				</div>
			</c:if>
			<c:if test="${status == 101}">
				<!-- Update driver log service started!! -->
				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					Update driver log service started!!
				</div>
			</c:if>
			<c:if test="${status == 102}">
				<!-- Update driver log service started!! -->
				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					Location backup send to server
				</div>
			</c:if>
			<c:if test="${status == 103}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					มีหมายเลขเครื่องนี้อยู่แล้ว
				</div>
			</c:if>
			<c:if test="${status == 104}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					กรุณาใส่หมายเลขเครื่อง
				</div>
			</c:if>
			<c:if test="${status == 200}">
				<!-- Update driver log service started!! -->
				<div class="alert alert-success alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					บันทึกข้อมูลเรียบร้อยแล้ว
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
		<!-- /top tiles -->
		<div class="col-lg-6" style="float: right;">
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-bell fa-fw"></i> Notifications Panel
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="list-group">
						<c:choose>
							<c:when test="${DTK3GStatus.status == 200}">
								<a href="#" class="list-group-item" style="background: #6ED746;">
							</c:when>
							<c:otherwise>
								<a href="#" class="list-group-item" style="background: #E9967A;">
							</c:otherwise>
						</c:choose>
						<i class="fa fa-refresh fa-fw"></i> DTK3G-100T Status :
						${DTK3GStatus.status} <span class="pull-right text-muted small"><em>Lastupdate
								&nbsp;&nbsp;${DTK3GStatus.size} &nbsp;&nbsp; :
								&nbsp;&nbsp;&nbsp; ${DTK3GStatus.timeUpdate}</em> </span>

						<c:choose>
							<c:when test="${MDVRSGWStatus2.status == 200}">
								<a href="#" class="list-group-item" style="background: #6ED746;">
							</c:when>
							<c:otherwise>
								<a href="#" class="list-group-item" style="background: #E9967A;">
							</c:otherwise>
						</c:choose>
						<i class="fa fa-refresh fa-fw"></i> MDVR SGW 75 Status :
						${MDVRSGWStatus2.status} <span class="pull-right text-muted small"><em>Lastupdate
								&nbsp;&nbsp;${MDVRSGWStatus2.size} &nbsp;&nbsp; :
								&nbsp;&nbsp;&nbsp; ${MDVRSGWStatus2.timeUpdate}</em> </span>

						<c:choose>
							<c:when test="${MDVRMHDStatus.status == 200}">
								<a href="#" class="list-group-item" style="background: #6ED746;">
							</c:when>
							<c:otherwise>
								<a href="#" class="list-group-item" style="background: #E9967A;">
							</c:otherwise>
						</c:choose>
						<i class="fa fa-refresh fa-fw"></i> MDVR MHD Status :
						${MDVRMHDStatus.status} <span class="pull-right text-muted small"><em>Lastupdate
								&nbsp;&nbsp;${MDVRMHDStatus.size} &nbsp;&nbsp; :
								&nbsp;&nbsp;&nbsp; ${MDVRMHDStatus.timeUpdate}</em> </span>

						<c:choose>
							<c:when test="${LTYStatus.status == 200}">
								<a href="#" class="list-group-item" style="background: #6ED746;">
							</c:when>
							<c:otherwise>
								<a href="#" class="list-group-item" style="background: #E9967A;">
							</c:otherwise>
						</c:choose>
						<i class="fa fa-refresh fa-fw"></i> BUS LTY Status :
						${LTYStatus.status} <span class="pull-right text-muted small"><em>Lastupdate
								&nbsp;&nbsp;${LTYStatus.size} &nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;
								${LTYStatus.timeUpdate}</em> </span>


						<c:choose>
							<c:when test="${MDVRSGWStatus.status == 200}">
								<a href="#" class="list-group-item" style="background: #6ED746;">
							</c:when>
							<c:otherwise>
								<a href="#" class="list-group-item" style="background: #E9967A;">
							</c:otherwise>
						</c:choose>
						<i class="fa fa-refresh fa-fw"></i> TG Status :
						${MDVRSGWStatus.status} <span class="pull-right text-muted small"><em>Lastupdate
								&nbsp;&nbsp;${MDVRSGWStatus.size} &nbsp;&nbsp; :
								&nbsp;&nbsp;&nbsp; ${MDVRSGWStatus.timeUpdate}</em> </span> </a>

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
			Distar Tracking Web Report by <a href="https://www.distartech.com">DistarTech
				co.,LTD</a>
		</div>
		<div class="clearfix"></div>
	</footer>
	<!-- /footer content -->
	</div>
	</div>

	<!-- jQuery -->
	<script src="styles/dashboard_assets/vendors/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script
		src="styles/dashboard_assets/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- FastClick -->
	<script
		src="styles/dashboard_assets/vendors/fastclick/lib/fastclick.js"></script>
	<!-- NProgress -->
	<script src="styles/dashboard_assets/vendors/nprogress/nprogress.js"></script>
	<!-- bootstrap-progressbar -->
	<script
		src="styles/dashboard_assets/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
	<!-- iCheck -->
	<script src="styles/dashboard_assets/vendors/iCheck/icheck.min.js"></script>
	<!-- bootstrap-daterangepicker -->
	<script
		src="styles/dashboard_assets/production/js/moment/moment.min.js"></script>
	<script
		src="styles/dashboard_assets/production/js/datepicker/daterangepicker.js"></script>
	<!-- bootstrap-wysiwyg -->
	<script
		src="styles/dashboard_assets/vendors/bootstrap-wysiwyg/js/bootstrap-wysiwyg.min.js"></script>
	<script
		src="styles/dashboard_assets/vendors/jquery.hotkeys/jquery.hotkeys.js"></script>
	<script
		src="styles/dashboard_assets/vendors/google-code-prettify/src/prettify.js"></script>
	<!-- jQuery Tags Input -->
	<script
		src="styles/dashboard_assets/vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
	<!-- Switchery -->
	<script
		src="styles/dashboard_assets/vendors/switchery/dist/switchery.min.js"></script>
	<!-- Select2 -->
	<script
		src="styles/dashboard_assets/vendors/select2/dist/js/select2.full.min.js"></script>
	<!-- Parsley -->
	<script
		src="styles/dashboard_assets/vendors/parsleyjs/dist/parsley.min.js"></script>
	<!-- Autosize -->
	<script
		src="styles/dashboard_assets/vendors/autosize/dist/autosize.min.js"></script>
	<!-- jQuery autocomplete -->
	<script
		src="styles/dashboard_assets/vendors/devbridge-autocomplete/dist/jquery.autocomplete.min.js"></script>
	<!-- starrr -->
	<script src="styles/dashboard_assets/vendors/starrr/dist/starrr.js"></script>

	<script
		src="styles/dashboard_assets/vendors/jquery-sparkline/dist/jquery.sparkline.min.js"></script>

	<!-- Flot plugins -->
	<script
		src="styles/dashboard_assets/vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
	<script
		src="styles/dashboard_assets/vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
	<script
		src="styles/dashboard_assets/vendors/flot.curvedlines/curvedLines.js"></script>

	<!-- Flot -->
	<script src="styles/dashboard_assets/vendors/Flot/jquery.flot.js"></script>
	<script src="styles/dashboard_assets/vendors/Flot/jquery.flot.pie.js"></script>
	<script src="styles/dashboard_assets/vendors/Flot/jquery.flot.time.js"></script>
	<script src="styles/dashboard_assets/vendors/Flot/jquery.flot.stack.js"></script>
	<script
		src="styles/dashboard_assets/vendors/Flot/jquery.flot.resize.js"></script>

	<!-- Custom Theme Scripts -->
	<script
		src="styles/dashboard_assets/vendors/Chart.js/dist/Chart.min.js"></script>
	<!-- jQuery Sparklines -->
	<script
		src="styles/dashboard_assets/vendors/jquery-sparkline/dist/jquery.sparkline.min.js"></script>
	<!-- morris.js -->
	<script src="styles/dashboard_assets/vendors/raphael/raphael.min.js"></script>
	<script src="styles/dashboard_assets/vendors/morris.js/morris.min.js"></script>
	<!-- gauge.js -->
	<script
		src="styles/dashboard_assets/vendors/gauge.js/dist/gauge.min.js"></script>
	<!-- Flot plugins -->
	<script
		src="styles/dashboard_assets/vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
	<script
		src="styles/dashboard_assets/vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
	<script
		src="styles/dashboard_assets/vendors/flot.curvedlines/curvedLines.js"></script>
	<!-- DateJS -->
	<script src="styles/dashboard_assets/vendors/DateJS/build/date.js"></script>
	<script src="styles/dashboard_assets/build/js/custom.min.js"></script>

	<!-- Datatables -->
	<script
		src="styles/dashboard_assets/vendors/datatables.net/js/jquery.dataTables.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-bs/js/dataTables.bootstrap.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-buttons/js/dataTables.buttons.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-buttons/js/buttons.flash.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-buttons/js/buttons.html5.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-buttons/js/buttons.print.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-keytable/js/dataTables.keyTable.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-responsive/js/dataTables.responsive.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
	<script
		src="styles/dashboard_assets/vendors/datatables.net-scroller/js/datatables.scroller.js"></script>
	<script src="styles/dashboard_assets/vendors/jszip/dist/jszip.js"></script>
	<script src="styles/dashboard_assets/vendors/pdfmake/build/pdfmake.js"></script>
	<script
		src="styles/dashboard_assets/vendors/pdfmake/build/vfs_fonts.js"></script>

	<!-- JQVMap -->
	<script
		src="styles/dashboard_assets/vendors/jqvmap/dist/jquery.vmap.js"></script>
	<script
		src="styles/dashboard_assets/vendors/jqvmap/dist/maps/jquery.vmap.world.js"></script>
	<script
		src="styles/dashboard_assets/vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"></script>
	<!-- Chart.js -->

	<script>
		Chart.defaults.global.legend = {
			enabled : false
		};

		// Line chart
		var ctx = document.getElementById("lineChart");
		var lineChart = new Chart(ctx, {
			type : 'line',
			data : {
				labels : [ "1/7/2016 - 00:01:12", "1/7/2016 - 00:01:12",
						"1/7/2016 - 00:01:12", "1/7/2016 - 00:01:12",
						"1/7/2016 - 00:01:12", "1/7/2016 - 00:01:12",
						"1/7/2016 - 00:01:12", "1/7/2016 - 00:01:12",
						"1/7/2016 - 00:01:12", "1/7/2016 - 00:01:12",
						"1/7/2016 - 00:01:12", "1/7/2016 - 00:01:12",
						"1/7/2016 - 00:01:12", "1/7/2016 - 00:01:12",
						"1/7/2016 - 00:01:12", "1/7/2016 - 00:01:12",
						"1/7/2016 - 00:01:12", "1/7/2016 - 00:01:12",
						"1/7/2016 - 00:01:12" ],
				datasets : [ {
					label : "ระดับน้ำมัน",
					backgroundColor : "rgba(38, 185, 154, 0.31)",
					borderColor : "rgba(38, 185, 154, 0.7)",
					pointBorderColor : "rgba(38, 185, 154, 0.7)",
					pointBackgroundColor : "rgba(38, 185, 154, 0.7)",
					pointHoverBackgroundColor : "#fff",
					pointHoverBorderColor : "rgba(220,220,220,1)",
					pointBorderWidth : 1,
					data : [ 100, 99, 98, 97, 96, 95, 94, 93, 0, 0, 0, 1, 17,
							24, 34, 49, 50, 75, 100 ]
				} ]
			},
		});
	</script>


	<!-- bootstrap-daterangepicker -->
	<script>
		$(document).ready(function() {
			$('#start-date').daterangepicker({
				singleDatePicker : true,
				format : 'DD-MM-YYYY',
				calender_style : "picker_3"
			});
		});
	</script>
	<!-- /bootstrap-daterangepicker -->
	<!-- bootstrap-daterangepicker -->
	<script>
		$(document).ready(function() {
			$('#end-date').daterangepicker({
				singleDatePicker : true,
				format : 'DD-MM-YYYY',
				calender_style : "picker_3"
			}, function(start, end, label) {
				console.log(start.toISOString(), end.toISOString(), label);
			});
		});
	</script>
	<!-- /bootstrap-daterangepicker -->

	<!-- bootstrap-wysiwyg -->
	<script>
		$(document)
				.ready(
						function() {
							function initToolbarBootstrapBindings() {
								var fonts = [ 'Serif', 'Sans', 'Arial',
										'Arial Black', 'Courier',
										'Courier New', 'Comic Sans MS',
										'Helvetica', 'Impact', 'Lucida Grande',
										'Lucida Sans', 'Tahoma', 'Times',
										'Times New Roman', 'Verdana' ], fontTarget = $(
										'[title=Font]').siblings(
										'.dropdown-menu');
								$
										.each(
												fonts,
												function(idx, fontName) {
													fontTarget
															.append($('<li><a data-edit="fontName ' + fontName + '" style="font-family:\'' + fontName + '\'">'
																	+ fontName
																	+ '</a></li>'));
												});
								$('a[title]').tooltip({
									container : 'body'
								});
								$('.dropdown-menu input').click(function() {
									return false;
								}).change(
										function() {
											$(this).parent('.dropdown-menu')
													.siblings(
															'.dropdown-toggle')
													.dropdown('toggle');
										}).keydown('esc', function() {
									this.value = '';
									$(this).change();
								});

								$('[data-role=magic-overlay]')
										.each(
												function() {
													var overlay = $(this), target = $(overlay
															.data('target'));
													overlay
															.css('opacity', 0)
															.css('position',
																	'absolute')
															.offset(
																	target
																			.offset())
															.width(
																	target
																			.outerWidth())
															.height(
																	target
																			.outerHeight());
												});

								if ("onwebkitspeechchange" in document
										.createElement("input")) {
									var editorOffset = $('#editor').offset();

									$('.voiceBtn')
											.css('position', 'absolute')
											.offset(
													{
														top : editorOffset.top,
														left : editorOffset.left
																+ $('#editor')
																		.innerWidth()
																- 35
													});
								} else {
									$('.voiceBtn').hide();
								}
							}

							function showErrorAlert(reason, detail) {
								var msg = '';
								if (reason === 'unsupported-file-type') {
									msg = "Unsupported format " + detail;
								} else {
									console.log("error uploading file", reason,
											detail);
								}
								$(
										'<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'
												+ '<strong>File upload error</strong> '
												+ msg + ' </div>').prependTo(
										'#alerts');
							}

							initToolbarBootstrapBindings();

							$('#editor').wysiwyg({
								fileUploadError : showErrorAlert
							});

							window.prettyPrint;
							prettyPrint();
						});
	</script>
	<!-- /bootstrap-wysiwyg -->

	<!-- Select2 -->
	<script>
		$(document).ready(function() {
			$(".select2_single").select2({
				placeholder : "กรุณาเลือกเครื่องติดตาม",
				allowClear : true
			});
			$(".select2_group").select2({});
			$(".select2_multiple").select2({
				maximumSelectionLength : 4,
				placeholder : "With Max Selection limit 4",
				allowClear : true
			});
		});
	</script>
	<!-- /Select2 -->

	<!-- jQuery Tags Input -->
	<script>
		function onAddTag(tag) {
			alert("Added a tag: " + tag);
		}

		function onRemoveTag(tag) {
			alert("Removed a tag: " + tag);
		}

		function onChangeTag(input, tag) {
			alert("Changed a tag: " + tag);
		}

		$(document).ready(function() {
			$('#tags_1').tagsInput({
				width : 'auto'
			});
		});
	</script>
	<!-- /jQuery Tags Input -->

	<!-- Parsley -->
	<script>
		$(document).ready(function() {
			$.listen('parsley:field:validate', function() {
				validateFront();
			});
			$('#demo-form .btn').on('click', function() {
				$('#demo-form').parsley().validate();
				validateFront();
			});
			var validateFront = function() {
				if (true === $('#demo-form').parsley().isValid()) {
					$('.bs-callout-info').removeClass('hidden');
					$('.bs-callout-warning').addClass('hidden');
				} else {
					$('.bs-callout-info').addClass('hidden');
					$('.bs-callout-warning').removeClass('hidden');
				}
			};
		});

		$(document).ready(function() {
			$.listen('parsley:field:validate', function() {
				validateFront();
			});
			$('#demo-form2 .btn').on('click', function() {
				$('#demo-form2').parsley().validate();
				validateFront();
			});
			var validateFront = function() {
				if (true === $('#demo-form2').parsley().isValid()) {
					$('.bs-callout-info').removeClass('hidden');
					$('.bs-callout-warning').addClass('hidden');
				} else {
					$('.bs-callout-info').addClass('hidden');
					$('.bs-callout-warning').removeClass('hidden');
				}
			};
		});
		try {
			hljs.initHighlightingOnLoad();
		} catch (err) {
		}
	</script>
	<!-- /Parsley -->

	<!-- Autosize -->
	<script>
		$(document).ready(function() {
			autosize($('.resizable_textarea'));
		});
	</script>
	<!-- /Autosize -->

	<!-- jQuery autocomplete -->
	<script>
		$(document).ready(function() {
			var countries = {
				AD : "Andorra",
				A2 : "Andorra Test",
				AE : "United Arab Emirates",
				AF : "Afghanistan",
				AG : "Antigua and Barbuda",
				AI : "Anguilla",
				AL : "Albania",
				AM : "Armenia",
				AN : "Netherlands Antilles",
				AO : "Angola",
				AQ : "Antarctica",
				AR : "Argentina",
				AS : "American Samoa",
				AT : "Austria",
				AU : "Australia",
				AW : "Aruba",
				AX : "Åland Islands",
				AZ : "Azerbaijan",
				BA : "Bosnia and Herzegovina",
				BB : "Barbados",
				BD : "Bangladesh",
				BE : "Belgium",
				BF : "Burkina Faso",
				BG : "Bulgaria",
				BH : "Bahrain",
				BI : "Burundi",
				BJ : "Benin",
				BL : "Saint Barthélemy",
				BM : "Bermuda",
				BN : "Brunei",
				BO : "Bolivia",
				BQ : "British Antarctic Territory",
				BR : "Brazil",
				BS : "Bahamas",
				BT : "Bhutan",
				BV : "Bouvet Island",
				BW : "Botswana",
				BY : "Belarus",
				BZ : "Belize",
				CA : "Canada",
				CC : "Cocos [Keeling] Islands",
				CD : "Congo - Kinshasa",
				CF : "Central African Republic",
				CG : "Congo - Brazzaville",
				CH : "Switzerland",
				CI : "Côte d’Ivoire",
				CK : "Cook Islands",
				CL : "Chile",
				CM : "Cameroon",
				CN : "China",
				CO : "Colombia",
				CR : "Costa Rica",
				CS : "Serbia and Montenegro",
				CT : "Canton and Enderbury Islands",
				CU : "Cuba",
				CV : "Cape Verde",
				CX : "Christmas Island",
				CY : "Cyprus",
				CZ : "Czech Republic",
				DD : "East Germany",
				DE : "Germany",
				DJ : "Djibouti",
				DK : "Denmark",
				DM : "Dominica",
				DO : "Dominican Republic",
				DZ : "Algeria",
				EC : "Ecuador",
				EE : "Estonia",
				EG : "Egypt",
				EH : "Western Sahara",
				ER : "Eritrea",
				ES : "Spain",
				ET : "Ethiopia",
				FI : "Finland",
				FJ : "Fiji",
				FK : "Falkland Islands",
				FM : "Micronesia",
				FO : "Faroe Islands",
				FQ : "French Southern and Antarctic Territories",
				FR : "France",
				FX : "Metropolitan France",
				GA : "Gabon",
				GB : "United Kingdom",
				GD : "Grenada",
				GE : "Georgia",
				GF : "French Guiana",
				GG : "Guernsey",
				GH : "Ghana",
				GI : "Gibraltar",
				GL : "Greenland",
				GM : "Gambia",
				GN : "Guinea",
				GP : "Guadeloupe",
				GQ : "Equatorial Guinea",
				GR : "Greece",
				GS : "South Georgia and the South Sandwich Islands",
				GT : "Guatemala",
				GU : "Guam",
				GW : "Guinea-Bissau",
				GY : "Guyana",
				HK : "Hong Kong SAR China",
				HM : "Heard Island and McDonald Islands",
				HN : "Honduras",
				HR : "Croatia",
				HT : "Haiti",
				HU : "Hungary",
				ID : "Indonesia",
				IE : "Ireland",
				IL : "Israel",
				IM : "Isle of Man",
				IN : "India",
				IO : "British Indian Ocean Territory",
				IQ : "Iraq",
				IR : "Iran",
				IS : "Iceland",
				IT : "Italy",
				JE : "Jersey",
				JM : "Jamaica",
				JO : "Jordan",
				JP : "Japan",
				JT : "Johnston Island",
				KE : "Kenya",
				KG : "Kyrgyzstan",
				KH : "Cambodia",
				KI : "Kiribati",
				KM : "Comoros",
				KN : "Saint Kitts and Nevis",
				KP : "North Korea",
				KR : "South Korea",
				KW : "Kuwait",
				KY : "Cayman Islands",
				KZ : "Kazakhstan",
				LA : "Laos",
				LB : "Lebanon",
				LC : "Saint Lucia",
				LI : "Liechtenstein",
				LK : "Sri Lanka",
				LR : "Liberia",
				LS : "Lesotho",
				LT : "Lithuania",
				LU : "Luxembourg",
				LV : "Latvia",
				LY : "Libya",
				MA : "Morocco",
				MC : "Monaco",
				MD : "Moldova",
				ME : "Montenegro",
				MF : "Saint Martin",
				MG : "Madagascar",
				MH : "Marshall Islands",
				MI : "Midway Islands",
				MK : "Macedonia",
				ML : "Mali",
				MM : "Myanmar [Burma]",
				MN : "Mongolia",
				MO : "Macau SAR China",
				MP : "Northern Mariana Islands",
				MQ : "Martinique",
				MR : "Mauritania",
				MS : "Montserrat",
				MT : "Malta",
				MU : "Mauritius",
				MV : "Maldives",
				MW : "Malawi",
				MX : "Mexico",
				MY : "Malaysia",
				MZ : "Mozambique",
				NA : "Namibia",
				NC : "New Caledonia",
				NE : "Niger",
				NF : "Norfolk Island",
				NG : "Nigeria",
				NI : "Nicaragua",
				NL : "Netherlands",
				NO : "Norway",
				NP : "Nepal",
				NQ : "Dronning Maud Land",
				NR : "Nauru",
				NT : "Neutral Zone",
				NU : "Niue",
				NZ : "New Zealand",
				OM : "Oman",
				PA : "Panama",
				PC : "Pacific Islands Trust Territory",
				PE : "Peru",
				PF : "French Polynesia",
				PG : "Papua New Guinea",
				PH : "Philippines",
				PK : "Pakistan",
				PL : "Poland",
				PM : "Saint Pierre and Miquelon",
				PN : "Pitcairn Islands",
				PR : "Puerto Rico",
				PS : "Palestinian Territories",
				PT : "Portugal",
				PU : "U.S. Miscellaneous Pacific Islands",
				PW : "Palau",
				PY : "Paraguay",
				PZ : "Panama Canal Zone",
				QA : "Qatar",
				RE : "Réunion",
				RO : "Romania",
				RS : "Serbia",
				RU : "Russia",
				RW : "Rwanda",
				SA : "Saudi Arabia",
				SB : "Solomon Islands",
				SC : "Seychelles",
				SD : "Sudan",
				SE : "Sweden",
				SG : "Singapore",
				SH : "Saint Helena",
				SI : "Slovenia",
				SJ : "Svalbard and Jan Mayen",
				SK : "Slovakia",
				SL : "Sierra Leone",
				SM : "San Marino",
				SN : "Senegal",
				SO : "Somalia",
				SR : "Suriname",
				ST : "São Tomé and Príncipe",
				SU : "Union of Soviet Socialist Republics",
				SV : "El Salvador",
				SY : "Syria",
				SZ : "Swaziland",
				TC : "Turks and Caicos Islands",
				TD : "Chad",
				TF : "French Southern Territories",
				TG : "Togo",
				TH : "Thailand",
				TJ : "Tajikistan",
				TK : "Tokelau",
				TL : "Timor-Leste",
				TM : "Turkmenistan",
				TN : "Tunisia",
				TO : "Tonga",
				TR : "Turkey",
				TT : "Trinidad and Tobago",
				TV : "Tuvalu",
				TW : "Taiwan",
				TZ : "Tanzania",
				UA : "Ukraine",
				UG : "Uganda",
				UM : "U.S. Minor Outlying Islands",
				US : "United States",
				UY : "Uruguay",
				UZ : "Uzbekistan",
				VA : "Vatican City",
				VC : "Saint Vincent and the Grenadines",
				VD : "North Vietnam",
				VE : "Venezuela",
				VG : "British Virgin Islands",
				VI : "U.S. Virgin Islands",
				VN : "Vietnam",
				VU : "Vanuatu",
				WF : "Wallis and Futuna",
				WK : "Wake Island",
				WS : "Samoa",
				YD : "People's Democratic Republic of Yemen",
				YE : "Yemen",
				YT : "Mayotte",
				ZA : "South Africa",
				ZM : "Zambia",
				ZW : "Zimbabwe",
				ZZ : "Unknown or Invalid Region"
			};

			var countriesArray = $.map(countries, function(value, key) {
				return {
					value : value,
					data : key
				};
			});

			// initialize autocomplete with custom appendTo
			$('#autocomplete-custom-append').autocomplete({
				lookup : countriesArray
			});
		});
	</script>
	<!-- /jQuery autocomplete -->

	<!-- Starrr -->
	<script>
		$(document).ready(function() {
			$(".stars").starrr();

			$('.stars-existing').starrr({
				rating : 4
			});

			$('.stars').on('starrr:change', function(e, value) {
				$('.stars-count').html(value);
			});

			$('.stars-existing').on('starrr:change', function(e, value) {
				$('.stars-count-existing').html(value);
			});
		});
	</script>
	<!-- /Starrr -->

	<!-- Datatables -->
	<script>
		$(document).ready(function() {
			var handleDataTableButtons = function() {
				if ($("#datatable-buttons").length) {
					$("#datatable-buttons").DataTable({
						dom : "Bfrtip",
						buttons : [ {
							extend : "copy",
							className : "btn-sm"
						}, {
							extend : "csv",
							className : "btn-sm"
						}, {
							extend : "excel",
							className : "btn-sm"
						}, {
							extend : "pdfHtml5",
							className : "btn-sm"
						}, {
							extend : "print",
							className : "btn-sm"
						}, ],
						responsive : true
					});
				}
			};

			TableManageButtons = function() {
				"use strict";
				return {
					init : function() {
						handleDataTableButtons();
					}
				};
			}();

			$('#datatable').dataTable();

			$('#datatable-keytable').DataTable({
				keys : true
			});

			$('#datatable-responsive').DataTable();

			$('#datatable-scroller').DataTable({
				ajax : "js/datatables/json/scroller-demo.json",
				deferRender : true,
				scrollY : 380,
				scrollCollapse : true,
				scroller : true
			});

			$('#datatable-fixed-header').DataTable({
				fixedHeader : true
			});

			var $datatable = $('#datatable-checkbox');

			$datatable.dataTable({
				'order' : [ [ 1, 'asc' ] ],
				'columnDefs' : [ {
					orderable : false,
					targets : [ 0 ]
				} ]
			});
			$datatable.on('draw.dt', function() {
				$('input').iCheck({
					checkboxClass : 'icheckbox_flat-green'
				});
			});

			TableManageButtons.init();
		});
	</script>
	<!-- /Datatables -->
</body>
</html>