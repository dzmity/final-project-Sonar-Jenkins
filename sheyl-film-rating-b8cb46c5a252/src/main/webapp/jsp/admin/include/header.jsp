<%--
 <%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 10.08.2016
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<fmt:setLocale value="${locale}" />
<fmt:bundle basename="applicationresource" prefix="page.header.">
<head>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>
<body>
<nav class="navbar navbar-inverse navbar-static-top-top ">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand logo" href="/controller?command=view_comments"><i class="fa fa-film fa-2x" aria-hidden="true"></i></a>
    </div>
    <ul class="nav navbar-nav">
        <li><a href="/controller?command=find_films_by_admin"><fmt:message key="films"/></a></li>
        <li><a href="/controller?command=find_actors_by_admin"><fmt:message key="actors"/></a></li>
        <li><a href="/controller?command=find_directors_by_admin"><fmt:message key="directors"/></a></li>
        <li><a href="/controller?command=view_comments"><fmt:message key="comments"/></a></li>
        <li><a href="/controller?command=find_all_users"><fmt:message key="users"/></a></li>
        <li><a href="/controller?command=view_countries_genres"><fmt:message key="additionally"/></a></li>
    </ul>
    <ul class="nav navbar-nav  navbar-right">
        <c:choose >
            <c:when  test= "${!empty  currentUser}">
                <li><a href="/controller?command=user_settings"><span class="glyphicon glyphicon-user"></span> ${currentUser.login}</a></li>
                <li><a href="/controller?command=log_out"><span class="glyphicon glyphicon-log-out"></span> <fmt:message key="logoutButton"/></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="/controller?command=sign_up"><span class="glyphicon glyphicon-user"></span> <fmt:message key="signUp"/></a></li>
                <li><a href="/controller?command=go_to_login"><span class="glyphicon glyphicon-log-in"></span> <fmt:message key="enterButton"/></a></li>
            </c:otherwise>
        </c:choose>

        <li><a href="/controller?command=change_language&code=${newCode}" >${altLocale}</a></li>
    </ul>
  </div>
</nav>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>

</body>
</fmt:bundle>
</html>
