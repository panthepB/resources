<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="top_nav">
	<div class="nav_menu">
		<nav>
			<div class="nav toggle">
				<a id="menu_toggle"><i class="fa fa-bars"></i></a>
			</div>

			<ul class="nav navbar-nav navbar-right">
				<li class=""><a href="javascript:;"
					class="user-profile dropdown-toggle" data-toggle="dropdown"
					aria-expanded="false"> <img
						src="<c:url value="/image/user.htm?positionName=${userLogin.position.nameEng }&fileName=${userLogin.filename}"/>"
						alt="">${userLogin.name} <span class=" fa fa-angle-down"></span>
				</a>
					<ul class="dropdown-menu dropdown-usermenu pull-right">
						<li><a href="javascript:;"> แสดงโปรไฟล์</a></li>
						<li><a href="javascript:;">แก้ไขโปรไฟล์</a></li>
						<li><a href="<c:url value="/logout.htm"/>"><i
								class="fa fa-sign-out pull-right"></i>ออกจากระบบ</a></li>
					</ul></li>