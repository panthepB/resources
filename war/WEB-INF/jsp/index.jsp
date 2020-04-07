<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Distar Master file management</title>
    <link rel="icon" href="img/icons8-transfer-96.png">
    <link href="styles/login_assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="styles/login_assets/css/nook.css" rel="stylesheet">
    <link href="styles/login_assets/css/plugins/owl.carousel.css" rel="stylesheet">
    <link href="styles/login_assets/css/plugins/owl.theme.css" rel="stylesheet">
    <link href="styles/login_assets/css/plugins/owl.transitions.css" rel="stylesheet">
    <link href="styles/login_assets/css/plugins/animate.css" rel="stylesheet">
    <link href="styles/login_assets/css/plugins/magnific-popup.css" rel="stylesheet">
    <link href="styles/login_assets/css/plugins/jquery.mb.YTPlayer.min.css" rel="stylesheet">
    <link href="styles/login_assets/css/font-awesome.min.css" rel="stylesheet">
    
    <link rel="stylesheet" type="text/css" href="https://js.cit.api.here.com/v3/3.0/mapsjs-ui.css" />
<script type="text/javascript" src="https://js.cit.api.here.com/v3/3.0/mapsjs-core.js"></script>
<script type="text/javascript" src="https://js.cit.api.here.com/v3/3.0/mapsjs-service.js"></script>
<script type="text/javascript" src="https://js.cit.api.here.com/v3/3.0/mapsjs-ui.js"></script>
<script type="text/javascript" src="https://js.cit.api.here.com/v3/3.0/mapsjs-mapevents.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <script>
        document.createElement('video');
      </script>
    <![endif]-->

</head>

<body id="home">
            <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
                <div class="container">
                    <div class="navbar-header pull-left">
                        <a class="navbar-brand page-scroll" href="#page-top">
                            <span class="brand-logo"><img src="styles/login_assets/images/logo.png" onerror="this.src='styles/login_assets/images/logo.png'; this.onerror=null;" alt="Distar Tracking Web Report" title="Distar Tracking Web Report" class="img-responsive"></span>
                        </a>
                    </div>
                    <div class="pull-right">
                        <div>
                          <form class="form-list" name="login-form" commandName="loginForm" action="login.htm" method="post">
						  <div class="row">
							<div class="col-md-4"><input class="form-control"  name="username" type="text" id="passwd" placeholder="Username"></div>
							<div class="col-md-4"><input class="form-control"  name="password" type="password" id="passwd" value="" placeholder="Password"></div>
							<div><input class="btn btn-default" type="submit" name="submit" value=" เข้าสู่ระบบ " /></div>
							</div>
                          </form>
                        </div>
                    </div>
                </div>
            </nav>

            <header id="intro-carousel" class="carousel slide">
                <div class="carousel-inner">
                    <div class="item video-section active">
<!--                         <a id="bgndVideo" class="player" data-property="{videoURL:'https://www.youtube.com/watch?v=vsTuJueD7_Q', containment:'.video-section', autoPlay:true, loop:true, mute:true, ratio:'16/9', startAt:4, quality:'hd720', opacity:1, showControls: false, showYTLogo:false, vol:25,}"></a> -->
                        <div class="carousel-caption">
                            <h1 class="animated slideInDown">Distar DLT send data</h1>
                            <p class="intro-text animated slideInUp">ระบบบริหารจัดการข้อมูลอุปกรณ์ติดตามสำหรับกรมขนส่งทางบก</p>
                            
                        </div>
                        <div class="video-controls">
                            <a id="video-volume" class="fa fa-volume-off fa-lg" href="#"></a>
                            <a id="video-play" class="fa fa-pause fa-lg" href="#"></a>
                        </div>
                        <div class="overlay-detail"></div>
                    </div>
                </div>
                <div class="mouse">
                  <div class="mousecaption">Scroll</div>
                </div>

            </header>
            <section id="about" class="about content-section alt-bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6">
                            <h2>บริษัท ไดสตาร์ เทค (ประเทศไทย) จำกัด</h2>
                            <h3 class="caption gray">About Distar Tech</h3>
                            <p>
                              Distar แบรนด์ชั้นนำจากประเทศเกาหลี ผู้น้ำด้านวัตกรรมและเทคโนโลยีด้านอิเล็กทรอนิกส์ที่ได้รับการยอมรับจากผู้บริโภคทั่วโลกมากกว่า 25 ปี ถึงมาตรฐานการผลิต เทคโนโลยี คุณภาพสินค้า และการเข้าถึงความต้องการของลูกค้า พร้อมนำเสนอต่อผู้บริโภคทุกระดับ และหนึ่งในสินค้าที่ประสบความสำเร็จ คือ ระบบความบันเทิงติดรถยนต์ ทีวีหลังเบาะรถบัส GPS Tracker ซึ่งในยุคต้นๆ ทาง distar มุ่งพัฒนาระบบความบันเทิงในรถยนต์ส่วนบุคคลจนเป็นที่ยอมรับทั้งในและต่างประเทศ
                            </p>

                            <!-- <blockquote>
                                There are two types of people who will tell you that you cannot make a difference in this world: those who are afraid to try and those who are afraid you will succeed.
                                <span>Ray Goforth</span>
                            </blockquote> -->

                            <!-- <h3 class="gray light">
                              จากนั้นในปี 2009 ทาง Distar พัฒนาสินค้าใหม่ในรูปแบบของความบันเทิงและความปลอดภัยในรถโดยสารสาธารณะ Distar Coach Technology ภายใต้แนวคิดที่จะนำเสนอเทคโนโลยีเพื่อความบันเทิง และความปลอดภัยให้เหนือกว่าการเดินทางด้วยพาหนะขนส่งมวลชนชนิดอื่นๆ ด้วยทีมงานที่มีประสบการณ์ทั้งจากประเทศเกาหลี และประเทศไทย โดยรวบรวมและนำเทคโนโลยีชั้นนำทั่วโลกมาพัฒนาระบบความปลอดภัยในการเดินทาง โดยเริ่มต้นจากระบบติดตามและในปัจจุบันได้พัฒนาระบบตรวจสอบภาษาสุดยอดของระบบติดตามด้วย MDVR-T (Mobile Digital Video Recorder & Tracking System) ซึ่งเป็นเทคโนโลยีที่เหนือกว่าการติดตามในรูปแบบอื่นๆ เพื่อให้ผู้ควบคุมหรือผู้ประกอบการรถบัสโดยสารสาธารณะสามารถเฝ้ามองเหตุการณ์ต่างๆ ผ่านกล้องวงจรปิดที่ติดตั้งภายในและภายนอกรถบัสได้ ทราบถึงตำแหน่งรถบนหน้าแผนที่ พฤติกรรมการขับขี่ การแจ้งเตือนสถานะต่างๆ เช่น ฉุกเฉิน หรือสถานะรถ ผ่านเซ็นเซอร์แบบเรียลไทม์ ตรวจสอบวีดีโอ ประวัติการขับขี่ย้อนหลังได้ทั้งแบบออนไลน์และออฟไลน์ สรุปรายงานการขับขี่ และสถานะต่างๆ เพื่อให้ผู้ประกอบการสามารถดูแลและควบคุมกลุ่มรถได้อย่างทั่วถึง
                            </h3>-->

                        </div>

                        <div class="col-md-6">
                            <div class="embed-responsive embed-responsive-16by9">
                                <iframe src="https://www.youtube.com/embed/vsTuJueD7_Q?controls=1" ></iframe>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section id="services" class="services content-section">
                <div class="container">
                    <div class="row text-center">
                        <div class="col-md-12">
                            <h2>Distar DLT Send Data</h2>
                            <h3 class="caption gray">DLT Send Data เป็นระบบบริหารจัดการ การส่งข้อมูลเข้ากรมขนส่งทางบก]</h3>
                        </div>

<!--                         <div class="container"> -->
<!--                             <div class="row text-center"> -->
<!--                                 <div class="col-md-4"> -->
<!--                                     <div class="row services-item sans-shadow text-center"> -->
<!--                                         <i class="fa fa-cogs fa-3x"></i> -->
<!--                                         <h4>การพัฒนาระบบ</h4> -->
<!--                                         <p>เราสร้างโดยอิงตามมาตรฐานรายงาน และปรับปรุงให้เหมาะสมกับลูกค้าคนไทย</p> -->
<!--                                     </div> -->
<!--                                 </div> -->

<!--                                 <div class="col-md-4"> -->
<!--                                     <div class="row services-item sans-shadow text-center"> -->
<!--                                         <i class="fa fa-paint-brush fa-3x"></i> -->
<!--                                         <h4>หน้าจอการทำงาน</h4> -->
<!--                                         <p>เราออกแบบให้ใช้งานง่าย สามารถทำความเข้าใจได้ง่าย</p> -->
<!--                                     </div> -->
<!--                                 </div> -->

<!--                                 <div class="col-md-4"> -->
<!--                                     <div class="row services-item sans-shadow text-center"> -->
<!--                                         <i class="fa fa-database fa-3x"></i> -->
<!--                                         <h4>ระบบฐานข้อมูล</h4> -->
<!--                                         <p>เราใช้ระบบฐานข้อมูลเดียวกับระบบ Distar tracking</p> -->
<!--                                     </div> -->
<!--                                 </div> -->

<!--                             </div> -->
<!--                         </div> -->

                    </div>
                </div>
            </section>

            <section id="products" class="products content-section">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4">
                            <img src="styles/login_assets/images/Report_app.png" class="center-block img-responsive">
                        </div>

                        <div class="col-md-8">
                            <div class="products-container">

                                <div class="col-md-12">
                                    <h2>ตัวอย่างรายงาน</h2>
                                    <h3 class="caption white">รายงานหืกด้า่หดส่กหด่นยหกาดยบรหดายหกรดนหยกาดหกนาดนยห</h3>
                                </div>

                                <div class="col-md-12 product-item">
                                    <div class="media-left">
                                        <span class="icon"><i class="fa fa-key fa-3x"></i></span>
                                    </div>
                                    <div class="media-body">
                                        <h3 class="media-heading">รายงานเกินความเร็วขณะเปิด-ปิดกุญแจ</h3>
                                        <p>เป็นการแสดงข้อมูลรายงานการขับรถเกิดความเร็ว หกสดาวสหาดยไาดสไยาดรไำจดกหอมทมแือา่ปดรขดไดาสำไาาหกด</p>
                                    </div>
                                </div>

                                <div class="col-md-12 product-item">
                                    <div class="media-left">
                                        <span class="icon"><i class="fa fa-arrows-alt fa-3x"></i></span>
                                    </div>
                                    <div class="media-body">
                                        <h3 class="media-heading">รายงานเกินความเร็วขณะเปิด-ปิดกุญแจ</h3>
                                        <p>เป็นการแสดงข้อมูลรายงานการขับรถเกิดความเร็ว หกสดาวสหาดยไาดสไยาดรไำจดกหอมทมแือา่ปดรขดไดาสำไาาหกด</p>
                                    </div>
                                </div>

                                <div class="col-md-12 product-item">
                                    <div class="media-left">
                                        <span class="icon"><i class="fa fa-arrows-alt fa-3x"></i></span>
                                    </div>
                                    <div class="media-body">
                                        <h3 class="media-heading">รายงานเกินความเร็วขณะเปิด-ปิดกุญแจ</h3>
                                        <p>เป็นการแสดงข้อมูลรายงานการขับรถเกิดความเร็ว หกสดาวสหาดยไาดสไยาดรไำจดกหอมทมแือา่ปดรขดไดาสำไาาหกด</p>
                                    </div>
                                </div>

                                <div class="col-md-12 product-item">
                                    <div class="media-left">
                                        <span class="icon"><i class="fa fa-arrows-alt fa-3x"></i></span>
                                    </div>
                                    <div class="media-body">
                                        <h3 class="media-heading">รายงานเกินความเร็วขณะเปิด-ปิดกุญแจ</h3>
                                        <p>เป็นการแสดงข้อมูลรายงานการขับรถเกิดความเร็ว หกสดาวสหาดยไาดสไยาดรไำจดกหอมทมแือา่ปดรขดไดาสำไาาหกด</p>
                                    </div>
                                </div>

                                <!-- <div class="col-md-6 product-item">
                                    <div class="media-left">
                                        <span class="icon"><i class="fa fa-eye fa-3x"></i></span>
                                    </div>
                                    <div class="media-body">
                                        <h3 class="media-heading">Retina Ready Graphics</h3>
                                        <p>Looks beautiful &amp; ultra-sharp on Retina Displays with Retina Icons, Fonts &amp; Images.</p>
                                    </div>
                                </div>

                                <div class="col-md-6 product-item">
                                    <div class="media-left">
                                        <span class="icon"><i class="fa fa-arrows-v fa-3x"></i></span>
                                    </div>
                                    <div class="media-body">
                                        <h3 class="media-heading">Parallax Sections</h3>
                                        <p>Comes with Parallax effect script so you can add Parallax effect to any section of the website.</p>
                                    </div>
                                </div>

                                <div class="col-md-6 product-item">
                                    <div class="media-left">
                                        <span class="icon"><i class="fa fa-video-camera fa-3x"></i></span>
                                    </div>
                                    <div class="media-body">
                                        <h3 class="media-heading">YouTube &amp; HTML5 Background Video</h3>
                                        <p>Choose to display either YouTube or HTML5 as background video.</p>
                                    </div>
                                </div>

                                <div class="col-md-6 product-item">
                                    <div class="media-left">
                                        <span class="icon"><i class="fa fa-toggle-on fa-3x"></i></span>
                                    </div>
                                    <div class="media-body">
                                        <h3 class="media-heading">Color Options</h3>
                                        <p>Choose a color that suits your brand &amp; change the color of the template with just one CSS file.</p>
                                    </div>
                                </div><

                                <div class="col-md-6 product-item">
                                    <div class="media-left">
                                        <span class="icon"><i class="fa fa-envelope-o fa-3x"></i></span>
                                    </div>
                                    <div class="media-body">
                                        <h3 class="media-heading">Contact form</h3>
                                        <p>Fully functional PHP contact form, with user input validation.</p>
                                    </div>
                                </div>-->

                            </div><!-- /.products-container -->
                        </div>

                    </div>
                </div>
            </section>

<%--             <section id="team" class="team content-section"> --%>
<!--                 <div class="container"> -->
<!--                     <div class="row text-center"> -->
<!--                         <div class="col-md-12"> -->
<!--                             <h2>ทีมอัศวิน</h2> -->
<!--                             <h3 class="caption gray">นี่คือส่วนหนึ่งของทีมอัศวิน</h3> -->
<!--                         </div> -->

<!--                         <div class="container"> -->
<!--                             <div class="row"> -->

<!--                                 <div class="col-md-4"> -->
<!--                                     <div class="team-member"> -->
<!--                                         <figure> -->
<!--                                             <img src="styles/login_assets/images/Nook.JPG" alt="" class="img-responsive"> -->
<!--                                         </figure> -->
<!--                                         <h4>Chavalid pungna</h4> -->
<!--                                         <p>Developer</p> -->
<!--                                     </div> -->
<!--                                 </div> -->

<!--                                 <div class="col-md-4"> -->
<!--                                     <div class="team-member"> -->
<!--                                         <figure> -->
<!--                                             <img src="styles/login_assets/images/Benz.JPG" alt="" class="img-responsive"> -->
<!--                                         </figure> -->
<!--                                         <h4>Panthep Nuchnoi</h4> -->
<!--                                         <p>Developer</p> -->
<!--                                     </div> -->
<!--                                 </div> -->

<!--                                 <div class="col-md-4"> -->
<!--                                     <div class="team-member"> -->
<!--                                         <figure> -->
<!--                                             <img src="styles/login_assets/images/Top.JPG" alt="" class="img-responsive"> -->
<!--                                         </figure> -->
<!--                                         <h4>Kornthawat pornpipat</h4> -->
<!--                                         <p>Developer</p> -->
<!--                                     </div> -->
<!--                                 </div> -->


<!--                                  <div class="col-md-4"> -->
<!--                                     <div class="team-member"> -->
<!--                                         <figure> -->
<!--                                             <img src="styles/login_assets/images/Chay.JPG" alt="" class="img-responsive"> -->
<!--                                         </figure> -->
<!--                                         <h4>Manit ngosorn</h4> -->
<!--                                         <p>IT Support</p> -->
<!--                                     </div> -->
<!--                                 </div> -->

<!--                                 <div class="col-md-4"> -->
<!--                                     <div class="team-member"> -->
<!--                                         <figure> -->
<!--                                             <img src="styles/login_assets/images/Champ.JPG" alt="" class="img-responsive"> -->
<!--                                         </figure> -->
<!--                                         <h4>Ammales atikomrungsarit</h4> -->
<!--                                         <p>IT Support</p> -->
<!--                                     </div> -->
<!--                                 </div> -->

<!--                                 <div class="col-md-4"> -->
<!--                                     <div class="team-member"> -->
<!--                                         <figure> -->
<!--                                             <img src="styles/login_assets/images/P.JPG" alt="" class="img-responsive"> -->
<!--                                         </figure> -->
<!--                                         <h4>ยังมีอีกหลายท่าน</h4> -->
<!--                                         <p>ที่พร้อมให้การบริการท่านอยู่ตลอด 24 ชั่วโมง</p> -->
<!--                                     </div> -->
<!--                                 </div> -->

<!--                             </div> -->
<!--                         </div> -->

<!--                     </div> -->
<!--                 </div> -->
<%--             </section> --%>

<%--             <section id="portfolio" class="portfolio content-section"> --%>
<!--                 <div class="container"> -->
<!--                     <div class="row text-center"> -->
<!--                         <div class="col-md-12"> -->
<!--                             <h2>ภาพต่างๆของระบบ</h2> -->
<!--                             <h3 class="caption white"></h3> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                 </div> -->

<!--                 <div class="container project-container text-center"> -->
<!--                     <div class="recent-project-carousel owl-carousel owl-theme popup-gallery"> -->
<!--                         <div class="item recent-project"> -->
<!--                             <img src="styles/login_assets/images/gallery/gellery-01.JPG" alt=""> -->
<!--                         </div> -->

<!--                         <div class="item recent-project"> -->
<!--                             <img src="styles/login_assets/images/gallery/gellery-01.JPG" alt=""> -->
<!--                         </div> -->

<!--                         <div class="item recent-project"> -->
<!--                             <img src="styles/login_assets/images/gallery/gellery-01.JPG" alt=""> -->
<!--                         </div> -->

<!--                     </div> -->

<!--                     <div class="customNavigation project-navigation text-center"> -->
<!--                         <a class="btn-prev"><i class="fa fa-angle-left fa-2x"></i></a> -->
<!--                         <a class="btn-next"><i class="fa fa-angle-right fa-2x"></i></a> -->
<!--                     </div> -->

<!--                 </div> -->
<%--             </section> --%>

<%--             <section id="clients" class="our-clients content-section text-center"> --%>
<!--                 <div class="container"> -->
<!--                     <div class="row"> -->
<!--                         <div class="col-md-12"> -->
<!--                             <h2>ลูกค้าที่ไว้วางใจให้เราดูแล</h2> -->
<!--                             <h3 class="caption white">นี่คือลูกค้าส่วนหนึ่งของเราที่ให้ระบบของ Distar Tracking </h3> -->
<!--                         </div> -->
<!--                     </div> -->

<!--                     <div class="row client-slider"> -->
<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                         <div class="item col-xs-4 col-md-2 i"> -->
<!--                             <a href="#" title="#"> -->
<!--                                 <img src="styles/login_assets/images/customer-logo.png" class="img-responsive"> -->
<!--                             </a> -->
<!--                         </div> -->

<!--                     </div> -->

<!--                 </div> -->
<%--             </section> --%>

<%--             <section class="testimonials content-section"> --%>
<!--                 <div class="container"> -->
<!--                     <div class="row text-center"> -->
<!--                         <div class="col-md-12"> -->
<!--                             <h2>การตอบรับจากลูกค้า</h2> -->
<!--                             <h3 class="caption gray">นี่คือสิ่งที่ลูกค้าพูดถึงเรา</h3> -->
<!--                         </div> -->

<!--                         <div class="container"> -->
<!--                             <div class="row"> -->
<!--                                 <div class="col-md-8 col-md-offset-2"> -->
<!--                                     <div class="client-testimonials owl-carousel owl-theme"> -->

<!--                                         <div class="item"> -->
<!--                                             <p class="speech">รายงานดูเป็นมาตรฐานดีครับ เหมาะกับการทำรายงานให้เจ้านายดูมากๆ สวยครับ</p> -->
<!--                                             <div class="client-info"> -->
<!--                                                 <img src="styles/login_assets/images/customer-01.JPG" /> -->
<!--                                                 <h4>SAM</h4> -->
<!--                                                 <span>แอดมิน</span> -->
<!--                                             </div> -->
<!--                                         </div> -->

<!--                                         <div class="item"> -->
<!--                                             <p class="speech">เยี่ยมเลยครับเรามาสามารถดึงข้อมูลให้นายดูได้หลากหลายครับ นายชอบมากเลยครับ</p> -->
<!--                                             <div class="client-info"> -->
<!--                                                 <img src="styles/login_assets/images/customer-01.JPG" /> -->
<!--                                                 <h4>Johe</h4> -->
<!--                                                 <span>แอดมิน</span> -->
<!--                                             </div> -->
<!--                                         </div> -->

<!--                                         <div class="item"> -->
<!--                                             <p class="speech">รายงานแสดงผลในรูปแบบที่ดูง่ายครับ ชอบครับ</p> -->
<!--                                             <div class="client-info"> -->
<!--                                                 <img src="styles/login_assets/images/customer-01.JPG" /> -->
<!--                                                 <h4>คุณแชมป์</h4> -->
<!--                                                 <span>ผู้ดูแลระบบ Tracking</span> -->
<!--                                             </div> -->
<!--                                         </div> -->

<!--                                     </div> -->
<!--                                 </div> -->

<!--                             </div> -->
<!--                         </div> -->

<!--                     </div> -->
<!--                 </div> -->
<%--             </section> --%>

            <section class="cta-one-section content-section alt-bg-light">
                <div class="container">
                    <div class="row text-center">
                        <div class="col-md-6">
                            <h2>Helpdesk Support</h2>
                            <h3 class="caption gray">บุคคลผู้ให้ความช่วยเหลือแก่ผู้ใช้ระบบ (อัศวิน)</h3>
                            <p>อัศวินมีเจ้าหน้าที่ให้ความช่วยเหลือแก่ผู้ใช้ระบบ ไม่ว่าจะเป็นปัญหาด้านใดก็ตามที่เกิดขึ้นจากการดำเนินงานของระบบ </p>

                            <a href="tel:0917706236" class="btn btn-outlined btn-lg">ติดต่อ อัศวิน (091-770-6236)</a>
                        </div>

                        <div class="col-md-6">
                            <img src="styles/login_assets/images/monitor.png" class="img-responsive">
                        </div>

                    </div>
                </div>
            </section>

            <section id="counter" class="counter-section content-section">
                <div class="container">
                    <div class="row text-center">
                        <div class="col-md-12">
                            <h2 class="white">Monitor Room</h2>
                            <h3 class="caption">ห้อง Monitor ที่จะคอยตรวจสอบดูแลระบบให้ท่าน เพื่อให้ท่านได้วางใจ เรามีส่วนนี้คอยดูแลให้ครับ</h3>
                        </div>

                        <div class="col-sm-6 counter-wrap">
                            <strong><span class="timer">7</span></strong>
                            <span class="count-description">วัน</span>
                        </div>

                        <div class="col-sm-6 counter-wrap">
                            <strong><span class="timer">24</span></strong>
                            <span class="count-description">ชั่วโมง</span>
                        </div>

                    </div>
                </div>
            </section>

            <section class="cta-two-section">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-9">
                            <h3>หรือ!! คุณสามารถเข้าระบบ Realtime Distar tracking ได้ที่นี่</h3>
                            <p>www.distartracking.com และ www.distargps.com</p>
                        </div>
                        <div class="col-sm-3">
                            <a href="http://www.distargps.com" class="btn btn-overcolor">เข้าสู่ระบบ</a>
                        </div>
                    </div>
                </div>
            </section>

            <section id="contact" class="contact content-section no-bottom-pad">
                <div class="container">
                    <div class="row text-center">
                        <div class="col-md-12">
                            <h2>ต่อต่อเรา</h2>
                            <h3 class="caption gray">สามารถติดต่อขอคำปรึกษาหรือให้คำแนะนำเกี่ยวกับระบบ เพื่อปรับปรุงและพัฒนาระบบให้ดียิ่งขึ้นและนำไปสู่ความเป็นมาตรฐาน อย่ากลัวกลัวที่จะติดต่อเรา</h3>
                        </div>

                    </div>
                </div>

                <div class="container">
                    <div class="row form-container">

                        <div class="col-md-8 contact-form">
                            <h3>ข้อมูลรายละเอียดของคุณ</h3>
                            <form class="ajax-form" id="contactForm" method="post" action="styles/login_assets/php/contact.php">
                                <div class="form-group">
                                    <input type="text" class="form-control" id="name" name="name" placeholder="ชื่อ-นามสกลุล" value="" required>
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control" id="email" name="email" placeholder="อีเมล์" value="" required>
                                </div>
                                <div class="form-group">
                                    <input type="phone" class="form-control" id="phone" name="phone" placeholder="เบอร์โทรศัพท์" value="" required>
                                </div>
                                <div class="form-group">
                                    <textarea class="form-control" rows="4" name="message" placeholder="ข้อความของคุณ..." required></textarea>
                                </div>
                                <div class="form-group">
                                    <button type="submit" name="submit" class="btn btn-default"><i class="fa fa-paper-plane fa-fw"></i> ส่งข้อความถึงเรา</button>
                                </div>
                            </form>
                        </div>

                        <div class="col-md-4 contact-address">
                            <h3>ที่อยู่</h3>
                            <p>38/2 ชั้นเอ็ม หมู่ 2
                                <br />ถนนรัตนาธิเบศร์ ต.บางรักพัฒนา
                                <br />อ.บางบัวทอง จ.นนทบุรี
                                <br />11110</p>
                            <ul>
                                <li><span>Email:</span>support@distartech.com</li>
                                <li><span>Phone:</span>02-926-5858</li>
                                <li><span>Fax:</span>02-926-5559</li>
                            </ul>
                        </div>

                    </div>
                </div>

<!--                 <div class="container-fluid buffer-forty-top"> -->
<!--                     <div class="row"> -->
<%--                         <section id="cd-google-map no-bottom-pad"> --%>
<!--                             <div id="google-container"></div> -->
<!--                             <div id="cd-zoom-in"></div> -->
<!--                             <div id="cd-zoom-out"></div> -->
<%--                         </section> --%>
<!--                     </div> -->
<!--                 </div> -->

<div   id="map2" style="width: 100%; height: 500px; background: white;  padding: 40px"  /></div>
				<script type="text/javascript" charset="UTF-8">
    
 					function addMarkersToMap(map) {
					
 					  var pointMarker = new H.map.Marker({lat:13.8748746, lng:100.4276403});
 					  map.addObject(pointMarker);
 					}

 					var platform = new H.service.Platform({
 					  app_id: 'AO0TSLtcxmb58ny1UwOH',
 					  app_code: '3aJwPn3AGDNS-0xAArtSAg',
 					  useCIT: true,
 					  useHTTPS: true
//  					 language: "fr-fr"
 					});
 					var defaultLayers = platform.createDefaultLayers();

 					var map = new H.Map(document.getElementById('map2'),
 					  defaultLayers.normal.map,{
 					  center: {lat:13.8748746, lng:100.4276403},
 					  zoom: 18
					});
					
 					var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));
					
 					var ui = H.ui.UI.createDefault(map, defaultLayers);

 					
 					  // Remove not needed settings control
//  					  ui.removeControl('mapsettings');
 					  
//  					var ui = H.ui.UI.createDefault(map, defaultLayers);
//  					var ui = H.ui.UI.createDefault(map, defaultLayers, 'th-TH');
					
 					addMarkersToMap(map);
   </script> 


            <footer>
                <div class="container">
                    <div class="row text-center">
                        <div class="col-md-12 segment">
                            <a href="#" title="Distar Tracking Web Report">
                                <h2>
                                    <img src="styles/login_assets/images/logo.png" onerror="this.src='styles/login_assets/images/logo.png'; this.onerror=null;" alt="Distar Tracking Web Report" title="Distar Tracking Web Report" class="img-responsive">
                                </h2>
                            </a>
                            <p class="white">Social Media</p>
                        </div>
                    </div>

                    <div class="row text-center">
                        <div class="col-md-12 social segment">
                            <h4></h4>
                            <a href="//www.facebook.com/distarthailand"><i class="fa fa-facebook fa-3x"></i></a>
                            <a href="//www.youtube.com/channel/UCPZWTOoYbZIDtjxkd1e5CQA"><i class="fa fa-youtube fa-3x"></i></a>
                            <a href="//www.instagram.com/distartechthailand/"><i class="fa fa-instagram fa-3x"></i></a>
                        </div>
                    </div>

                </div>

                <div class="copynote">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12 text-center">
                                &copy; 2016. DistarTech Thailand. All rights reserved.
                            </div>

                        </div>
                    </div>
                </div>

                <div class="nav pull-right scroll-top">
                    <a href="#home" title="Scroll to top"><i class="fa fa-angle-up"></i></a>
                </div>

            </footer>

    <script src="styles/login_assets/js/jquery.min.js"></script>
    <script src="styles/login_assets/js/bootstrap.min.js"></script>
    <script src="styles/login_assets/js/plugins/wow.min.js"></script>
    <script src="styles/login_assets/js/plugins/owl.carousel.min.js"></script>
    <script src="styles/login_assets/js/plugins/jquery.parallax-1.1.3.js"></script>
    <script src="styles/login_assets/js/plugins/jquery.magnific-popup.min.js"></script>
    <script src="styles/login_assets/js/plugins/jquery.mb.YTPlayer.min.js"></script>
    <script src="styles/login_assets/js/plugins/jquery.countTo.js"></script>
    <script src="styles/login_assets/js/plugins/jquery.inview.min.js"></script>
    <script src="styles/login_assets/js/plugins/pace.min.js"></script>
    <script src="styles/login_assets/js/plugins/jquery.easing.min.js"></script>
    <script src="styles/login_assets/js/plugins/jquery.validate.min.js"></script>
    <script src="styles/login_assets/js/plugins/additional-methods.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDYtwnUdeVub7dMAoLiFupwTI4PprKWzFg"></script>
    <script src="styles/login_assets/js/nook.js"></script>
</body>

</html>
