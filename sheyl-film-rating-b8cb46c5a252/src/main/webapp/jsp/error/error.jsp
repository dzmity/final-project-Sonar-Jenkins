<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="raf" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose >
    <c:when  test= "${currentUser.role.toString() == initParam.admin}">
        <%@ include file="/jsp/admin/include/header.jsp"%>
    </c:when>
    <c:otherwise>
        <%@ include file="/jsp/user/include/header.jsp"%>
    </c:otherwise>
</c:choose>
<html>
<fmt:setLocale value="${locale}" />
<fmt:bundle basename="applicationresource" prefix="page.error.">
    <head>
        <title><fmt:message key="title"/></title>
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/font-awesome.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>

    <div class="container">
        <div class="row">
            <br/>
            <fmt:message key="request"/> ${pageContext.errorData.requestURI} <fmt:message key="fail"/>
            <br/>
            <br/>
            <b><fmt:message key="servlet"/></b> ${pageContext.errorData.servletName}
            <br/>
            <b><fmt:message key="status"/></b> ${pageContext.errorData.statusCode}
            <br/>
            <b><fmt:message key="exception"/></b> ${pageContext.exception}
            <br/>
            <b><fmt:message key="message.exception"/></b>
            <c:forEach var="trace"
                items="${pageContext.exception.stackTrace}">
                <p>${trace}</p>
            </c:forEach>
            <br/>
        </div>
    </div>
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
    <br>
    <br>
    <br>
    <br>
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