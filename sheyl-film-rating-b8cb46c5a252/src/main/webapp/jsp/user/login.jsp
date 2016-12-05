<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="raf" uri="customtags" %>
<%@ include file="/jsp/user/include/header.jsp"%>

<html>
<fmt:setLocale value="${locale}" />
<fmt:bundle basename="applicationresource" prefix="page.registration.">
<head>
    <title></title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>
<body>

<div class="container">
    <div class="row">

        <div id="legend">
            <legend class=""><fmt:message key="logIn"/></legend>
        </div>

        <div class="col-lg-7 col-lg-offset-1" >
        <form class="form-horizontal"  role="form" name="loginForm" action="${pageContext.request.contextPath}/controller" method="POST">
            <input type="hidden" name="command" value="log_in" />

            <c:if test="${not empty successMessage}">
                <div class="form-group">
                    <div class="col-lg-offset-4 col-lg-8">
                        <span style="color: green;">${successMessage}</span>
                    </div>
                </div>
            </c:if>

            <div class="form-group">
                <label for="login" class="col-lg-4 control-label"><fmt:message key="login"/></label>
                <div class="col-lg-8 input-group">
                    <span class="input-group-addon"><i class="fa fa-user" aria-hidden="true"></i></span>
                    <input type="text" pattern="^[a-zA-ZёЁа-яА-Я][ёЁа-яА-Я\w-]{2,9}$" required name="login" id="login" class="form-control" placeholder="<fmt:message key="login" />">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" data-toggle="popover"  data-content ="<fmt:message key="info.login.text"/>" data-placement="right" data-trigger="focus">
                            <i class="fa fa-info" aria-hidden="true"></i> info
                        </button>
                    </span>
                </div>
            </div>

            <div class="form-group">
                <label for="val1" class="col-lg-4 control-label"><fmt:message key="password"/></label>
                <div class="col-lg-8 input-group">
                    <span class="input-group-addon"><i class="fa fa-unlock-alt" aria-hidden="true"></i></span>
                    <input type="password" pattern="^[-ёЁа-яА-Я\w\s]{6,20}$" required name="password" id="val1"
                           class="form-control" placeholder="<fmt:message key="password" />">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" data-toggle="popover"  data-content="<fmt:message key="info.name.text" />" data-placement=" right" data-trigger="focus">
                            <i class="fa fa-info" aria-hidden="true"></i> info
                        </button>
                    </span>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-4 col-sm-8 input-group">
                  <span style="color: red" >${loginError}</span>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-4 col-sm-8 input-group">
                    <button type="submit" class="btn btn-success"><fmt:message key="enterButton"/></button>
                </div>
            </div>
        </form>
        </div>
    </div>

</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script>
    $(function() {
		$('[data-toggle="tooltip"]').tooltip();
		$('[data-toggle="popover"]').popover();
	});
</script>
</fmt:bundle>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<fmt:bundle basename="applicationresource" prefix="page.footer.">
    <raf:footer locale="${locale}"><fmt:message key="text"/></raf:footer>
</fmt:bundle>
</body>
</html>
