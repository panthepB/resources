<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="profile">
              <div class="profile_pic">
              <img src="<c:url value="/image/user.htm?positionName=${userLogin.position.nameEng }&fileName=${userLogin.filename}"/>" class="img-circle profile_img"/>
              </div>
              <div class="profile_info">
                <span>ยินดีต้อนรับ,</span>
                <h2>${userLogin.name}</h2>
              </div>
            </div>