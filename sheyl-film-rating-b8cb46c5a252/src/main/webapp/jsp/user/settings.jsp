<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 17.08.2016
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="raf" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose >
    <c:when  test= "${ currentUser.role.toString() == initParam.admin}">
        <%@ include file="/jsp/admin/include/header.jsp"%>
    </c:when>
    <c:otherwise>
        <%@ include file="/jsp/user/include/header.jsp"%>
    </c:otherwise>
</c:choose>

<html>
<fmt:setLocale value="${locale}" />
<fmt:bundle basename="applicationresource" prefix="page.registration.">
<head>
	<title>${currentUser.login}</title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>

<body onload="startTimer()">
<c:if  test= "${ currentUser == null}">
    <c:redirect url="/index.jsp"/>
</c:if>

<div class="container">
    <div class="row well">
        <br><br><br>

        <div class="col-lg-2 well" style="background-color: white">

            <img src="${pageContext.request.contextPath}/images/user/${currentUser.picturePath}.jpeg" alt="" width="100%">
            <br>
            <button type="button" class="btn btn-href btn-block" data-toggle="modal" data-target="#modal-1">
                <fmt:message key="changePhotoButton"/>
            </button>

            <div class="modal fade" id="modal-1">
                <div class="modal-dialog modal-lg">

                    <div class="modal-content ">
                        <div class="modal-header">
                            <button class="close" type="button" data-dismiss="modal">
                                <i class="fa fa-close"></i>
                            </button>
                            <h4 class="modal-title"><fmt:message key="modalWindow.title"/></h4>
                        </div>

                        <div class="modal-body">
                            <p><fmt:message key="modalWindow.text"/></p>
                            <form action="${pageContext.request.contextPath}/controller" method="post" onSubmit="return checkFile(this)" enctype="multipart/form-data">
                                <input type="hidden" name="code" value="${newCode}"/>
                                <input type="hidden" name="command" value="update_user_image">
                                <label class="btn btn-default btn-file">
                                    <fmt:message key="browseButton"/><input type="file" name="file" accept="image/png, image/jpeg" style="display: none;">
                                </label>
                                <button type="submit" class="btn btn-success"><fmt:message key="sendButton"/></button>
                                <span style="color: red" id="loadMessage"></span>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>


<%-------------------------------------------UPDATE FORM--------------------------------------------------------------%>
        <div class="col-lg-8 ">
            <div class="well" style="background-color: white">
                <div class="row">
                    <div class="col-lg-11">
                        <form class="form-horizontal" role="form" name="updateUserForm" action="${pageContext.request.contextPath}/controller" method="POST">
                            <input type="hidden" name="code" value="${newCode}"/>
                            <input type="hidden" name="command" value="update_user" />

                            <div class="form-group">
                                <label for="firstName" class="col-lg-2 control-label"><fmt:message key="firstName"/></label>
                                <div class="col-lg-10">
                                    <input required name="firstName" pattern="^([ёЁа-яА-Я]+|[a-zA-Z]+)$" id="firstName" class="form-control" value="${currentUser.name}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="lastName" class="col-lg-2 control-label"><fmt:message key="lastName"/></label>
                                <div class="col-lg-10">
                                    <input required name="lastName" pattern="^([ёЁа-яА-Я]+|[a-zA-Z]+)$" id="lastName" class="form-control" value="${currentUser.surname}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="login" class="col-lg-2 control-label"><fmt:message key="login"/></label>
                                <div class="col-lg-10">
                                    <input required name="login" pattern="^[a-zA-ZёЁа-яА-Я][ёЁа-яА-Я\w-]{2,9}$" id="login" class="form-control" value="${currentUser.login}">
                                </div>
                            </div>

                            <c:if test="${not empty loginError}">
                                <div class="form-group">
                                    <div class="col-lg-10 col-lg-offset-2 ">
                                        <span style="color: red;">${loginError}</span>
                                    </div>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <label for="email" class="col-lg-2 control-label"><fmt:message key="email"/></label>
                                <div class="col-lg-10">
                                    <input disabled name="email" id="email" class="form-control" value="${currentUser.email}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="status" class="col-lg-2 control-label"><fmt:message key="status"/></label>
                                <div class="col-lg-10">
                                    <input disabled name="status" id="status" class="form-control" value="${currentUser.status}">
                                </div>
                            </div>

                            <c:if test="${not empty passwordError}">
                                <div class="form-group">
                                    <div class="col-lg-10 col-lg-offset-2 ">
                                        <span style="color: red;">${passwordError}</span>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty passwordInfo}">
                                <div class="form-group">
                                    <div class="col-lg-offset-2 col-lg-10 ">
                                        <span style="color: green;">${passwordInfo}</span>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty error}">
                                <div class="form-group">
                                    <div class="col-lg-offset-2 col-lg-10 ">
                                        <span style="color: red;">${error}</span>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${currentUser.banTime != null}">
                                <div class="form-group">
                                    <label for="timer" class="col-lg-2 control-label"><fmt:message key="ban"/></label>
                                    <div class="col-lg-10 ">
                                        <span id="timer" style="color: #f00; font-size: 150%; font-weight: bold;">${raf:toSeconds(currentUser.banTime)}</span>
                                    </div>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-success"><fmt:message key="changeButton"/></button>
                                    <a href="#spoiler-1" data-toggle="collapse" class="btn btn-primary spoiler collapsed">
                                        <fmt:message key="changePasswordButton"/>
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
<%-------------------------------------------CHANGE PASSWORD----------------------------------------------------------%>

		<div class="col-lg-8 col-lg-offset-2 ">
			<div class="collapse well" id="spoiler-1" style="background-color: white">
                <div class="row">
                    <div class="col-lg-11">
                        <form class="form-horizontal" onSubmit="return checkPw(this)" role="form" name="changePasswordForm" action="${pageContext.request.contextPath}/controller" method="POST">
                            <input type="hidden" name="code" value="${newCode}"/>
                            <input type="hidden" name="command" value="change_password" />

                            <div class="form-group">
                                <label for="val3" class="col-lg-2 control-label"><fmt:message key="old"/></label>
                                <div class="col-lg-10 input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock" aria-hidden="true"></i></span>
                                    <input type="password" pattern="^[-ёЁа-яА-Я\w\s]{6,20}$"
                                           required name="oldPassword" id="val3" class="form-control"
                                           placeholder="<fmt:message key="oldPassword" />">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="val1" class="col-lg-2 control-label"><fmt:message key="new"/></label>
                                <div class="col-lg-10 input-group">
                                    <span class="input-group-addon"><i class="fa fa-unlock-alt" aria-hidden="true"></i></span>
                                    <input type="password" pattern="^[-ёЁа-яА-Я\w\s]{6,20}$"
                                           required name="newPassword" id="val1" class="form-control"
                                           placeholder="<fmt:message key="newPassword" />">
                                    <span class="input-group-btn">
                                        <button class="btn btn-default" type="button"
                                                data-toggle="popover"
                                                data-content="<fmt:message key="info.password.text" />"
                                                data-placement="right" data-trigger="focus">
                                            <i class="fa fa-info" aria-hidden="true"></i>
                                            <fmt:message key="infoButton" />
                                        </button>
                                    </span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="val2" class="col-lg-2 control-label"><fmt:message key="confirm"/></label>
                                <div class="col-lg-10 input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock" aria-hidden="true"></i></span>
                                    <input type="password" pattern="^[-ёЁа-яА-Я\w\s]{6,20}$"
                                           required name="confirm" id="val2" class="form-control"
                                           placeholder="<fmt:message key="confirmPassword" />">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10 input-group">
                                  <span style="color: red" id="message"></span>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10 input-group">
                                    <button type="submit" class="btn btn-success"><fmt:message key="changeButton"/></button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
			</div>
		</div>
    </div>
</div>
<script type="text/javascript">
    function checkPw(form) {
        val1 = form.newPassword.value;
        val2 = form.confirm.value;
        var message = "";
        if (val1 != val2) {
            message = "<fmt:message key="message"/>";
            document.getElementById("message").innerHTML = message;
            return false;
        } else return true;
    }
</script>
<script type="text/javascript">
    function checkFile(form) {
        val = form.file.value;

        var loadMessage = "";
        if (val == "") {
            loadMessage = "<fmt:message key="loadMessage"/>";
            document.getElementById("loadMessage").innerHTML = loadMessage;
            return false;
        } else return true;
    }
</script>
<script type="text/javascript">
    function startTimer() {
        var my_timer = document.getElementById("timer");
        var time = my_timer.innerHTML;
        var arr = time.split(":");
        var d = arr[0];
        var h = arr[1];
        var m = arr[2];
        var s = arr[3];
        if (s == 0) {
            if (m == 0) {
                if (h == 0) {
                    if (d == 0) {
                        alert("<fmt:message key="timeOut"/> ");
                        window.location.reload();
                        return;
                    }
                    d--;
                    h = 24;

                }
                h--;
                m = 60;
                if (h < 10) h = "0" + h;
            }
            m--;
            if (m < 10) m = "0" + m;
            s = 59;
        }
        else s--;
        if (s < 10) s = "0" + s;
        document.getElementById("timer").innerHTML = d+":"+ h+":"+m+":"+s;
        setTimeout(startTimer, 1000);
    }
</script>
<script>
    $(function() {
		$('[data-toggle="tooltip"]').tooltip();
		$('[data-toggle="popover"]').popover();
	});
</script>
</fmt:bundle>
<fmt:bundle basename="applicationresource" prefix="page.footer.">
    <c:choose >
        <c:when  test= "${ currentUser.role.toString() == initParam.admin}">
            <raf:adminFooter locale="${locale}"><fmt:message key="text"/></raf:adminFooter>
        </c:when>
        <c:otherwise>
            <raf:footer locale="${locale}"><fmt:message key="text"/></raf:footer>
        </c:otherwise>
    </c:choose>
</fmt:bundle>
</body>
</html>
