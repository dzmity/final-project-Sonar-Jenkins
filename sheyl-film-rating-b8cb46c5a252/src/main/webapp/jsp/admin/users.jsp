<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 12.08.2016
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="raf" uri="customtags" %>
<%@ include file="/jsp/admin/include/header.jsp"%>
<html>
<fmt:setLocale value="${locale}" />
<fmt:bundle basename="applicationresource" prefix="page.admin.user.">
<head>
    <title>${user.login} </title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
<c:if  test= "${ currentUser.role.toString() != initParam.admin}">
    <c:redirect url="/index.jsp"/>
</c:if>

<div class="container">
    <div class="row">
        <div class="alert alert-warning"><h3 align="center"><fmt:message key="table1.title"/></h3></div>
        <table class="table table-hover table-striped center-class">
            <thead>
                <tr>
                    <th><fmt:message key="table1.user"/></th>
                    <th><fmt:message key="table1.role"/></th>
                    <th><fmt:message key="table1.commentCount"/></th>
                    <th><fmt:message key="table1.markCount"/></th>
                </tr>
            </thead>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>
                        <a href="/controller?command=user_info&id=${user.id}">${user.login}</a>
                    </td>
                    <td>${user.role}</td>
                    <td>${user.comments.size()}</td>
                    <td>${user.markCount}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
    <br>
    <br>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>

</fmt:bundle>
<fmt:bundle basename="applicationresource" prefix="page.footer.">
    <raf:adminFooter locale="${locale}"><fmt:message key="text"/></raf:adminFooter>
</fmt:bundle>
</body>
</html>
