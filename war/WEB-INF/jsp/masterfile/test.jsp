<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/headerTop2.jsp"%>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/buttons/1.5.6/css/buttons.dataTables.min.css" />

<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.12/js/dataTables.jqueryui.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.flash.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.html5.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.print.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.colVis.min.js"></script>
<!-- <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.foundation.min.js"></script> -->
<script type="text/javascript">
var dataSet = ${json};

$(document).ready(function() {
    $('#example').DataTable( {
    	dom: 'Bfrtip',
        buttons: [ 
        {
            extend: 'excelHtml5',
            title: 'MasterFile Data Export',
            sheetName: 'tracking data',
            
        },

//         {
//             extend: 'pdfHtml5',
//             title: 'MasterFile Data Export'
//         }
        
        ],

        
    	"lengthMenu": [[10,25, 50, -1], [10, 25, 50, "All"]],
    	"scrollX": true,
    	processing: true,
        data: dataSet,
        columns: [
        	{ title: "No." },
            { title: "imei" },
            { title: "ยี่ห้อ" },
            { title: "เลขตัวถัง" },
            { title: "unit ID" },
            { title: "ชื่อรถ" },
            { title: "ทะเบียนรถ" },
            { title: "จังหวัด" },
            { title: "ประเภทจดทะเบียน" },
            { title: "เลขใบขับขี่" },     
            { title: "lat" },
            { title: "lon." },
            { title: "เวลา GPS" },
//             { title: "recvUtcTs" },
//             { title: "speed" },
            { title: "สัญญาณ gsm" },
            { title: "จำนวนดาวเทียม" },
            { title: "เวลา DLT" },
            { title: "ผู้ขาย" },
            { title: "ลูกค้า" },
            { title: "หมายเหตุ" },
            { title: "หมายเหตุเพิ่มเติม" }
        ]
    } );
} );
</script>
<!-- /top navigation -->

<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="page-title">
		<div class="title_left">
			<h3>DLT send data</h3>
		</div>
	</div>
	<div class="clearfix"></div>



	<div class="panel panel-info">

		<div class="panel panel-default">


			<div class="panel-heading">List master file</div>
			<div class="panel-body">
				<table id="example" class="display" width="100%"></table>
			</div>
		</div>

		<!-- /top tiles -->

	</div>
	<!-- /page content -->

	<!-- footer content -->
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<!-- /footer content -->
</div>
</div>

<%@ include file="/WEB-INF/jsp/bottom.jsp"%>

</body>
</html>