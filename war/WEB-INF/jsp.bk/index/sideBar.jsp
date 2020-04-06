<%@ include file="/WEB-INF/jsp/headerBottom.jsp"%>

<!-- <link href="styles/bootstrap.css" rel="stylesheet" /> -->
<!-- <link href="styles/font-awesome.css" rel="stylesheet" /> -->
<!-- <link href="styles/basic.css" rel="stylesheet" /> -->
<!-- <link href="styles/custom.css" rel="stylesheet" /> -->

</head>
<body>

	<script src="js/jquery-1.10.2.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/jquery.metisMenu.js"></script>
	<script src="js/custom.js"></script>

	<div id="wrapper">

        <nav class="navbar navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="../../DLTSendData/index.htm">DiStar</a>
            </div>

            <div class="header-right">

<!--                 <a href="message-task.html" class="btn btn-info" title="New Message"><b>30 </b><i class="fa fa-envelope-o fa-2x"></i></a> -->
<!--                 <a href="message-task.html" class="btn btn-primary" title="New Task"><b>40 </b><i class="fa fa-bars fa-2x"></i></a> -->
<!--                 <a href="login.html" class="btn btn-danger" title="Logout"><i class="fa fa-exclamation-circle fa-2x"></i></a> -->

            </div>
        </nav>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
<!--                    <li>
                        <a href="#"><i class="fa fa-desktop "></i>MDVR<span class="fa arrow"></span></a>
                         <ul class="nav nav-second-level">
                            <li>
<!--                                 <a href="panel-tabs.html"><i class="fa fa-toggle-on"></i>Tabs & Panels</a> -->
<!--                                <a href='/DLTSendData/service/updateDriverLog.htm'>Update driver license</a>
                            </li>
                            <li>
                            	<a href='/DLTSendData/service/addGPSRealTimeMDVR.htm'>RealTime service</a>
                            </li>
                        </ul>
                    </li> -->
                     <li>
                        <a href="#"><i class="fa fa-yelp "></i>3G Tracking<span class="fa arrow"></span></a>
                         <ul class="nav nav-second-level">
                            <li>
                                <li class='last'><a href='/DLTSendData/service/addRealTimeDTK3G.htm'><span>RealTime service</span></a></li>
                            </li>
                             <li>
                                <li class='last'><a href='/DLTSendData/service/addHistoryForm.htm'><span>Add GPS Backup</span></a></li>
                            </li>
                            <li>
                                <li class='last'><a href='/DLTSendData/service/searchForm.htm'><span>Search Tracker</span></a></li>
                            </li>
                            
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-yelp "></i>MDVR MHD<span class="fa arrow"></span></a>
                         <ul class="nav nav-second-level">
                            <li>
                                <li class='last'><a href='/DLTSendData/service/addRealTimeMHD.htm'><span>RealTime service</span></a></li>
                            </li>
                            <li>
                                <li class='last'><a href='/DLTSendData/service/getRealtimeMHD.htm'><span>Get RealTime</span></a></li>
                            </li>
                            
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-yelp "></i>Bus system<span class="fa arrow"></span></a>
                         <ul class="nav nav-second-level">
                            <li>
                                <li class='last'><a href='/DLTSendData/service/addRealTimeBus.htm'><span>RealTime service</span></a></li>
                            </li>
                            
                        </ul>
                    </li>
                    
                     <li>
                        <a href="#"><i class="fa fa-bicycle "></i>Master file<span class="fa arrow"></span></a>
                         <ul class="nav nav-second-level">
                           
                             <li>
                                <a href='/DLTSendData/service/addMasterFileForm.htm'><span>Add master file form</span></a>
                            </li>
                             <li>
                                <a href='/DLTSendData/service/getMesterFileList.htm'><span>Get list master file</span></a>
                            </li>
                             <li>
                             	<a href='/DLTSendData/service/rmvMasterFileForm.htm'><span>Remove master file</span></a>
                             </li>
                           
                        </ul>
                    </li>
                     
                   
                    <li>
                        <a href="#"><i class="fa fa-square-o "></i>About</a>
                    </li>
                </ul>
               

            </div>

        </nav>