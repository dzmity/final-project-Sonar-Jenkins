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
    <title><fmt:message key="title"/> </title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>
<body>

<div class="container">
      <div class="row">
         <h1><fmt:message key="title"/></h1>
          <div class="navbar navbar-inverse navbar-static-top">
          <div class="container">
              <div class="navbar-header">
                  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive-menu">
                     <span class="sr-only"></span>
                      <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                  </button>
                  <a class="navbar-brand logo" href="/controller?command=start"><img src="/images/movie.png" width="70"></a>
              </div>
              <div class="collapse navbar-collapse" id="responsive-menu">
                  <ul class="nav navbar-nav">
                     <li><a href="#"></a></li>
                      <li><a href="/controller?command=find_all_films"><fmt:message key="films"/></a></li>
                      <li><a href="/controller?command=find_all_actors"><fmt:message key="actors"/></a></li>
                      <li><a href="/controller?command=find_all_directors"><fmt:message key="directors"/></a></li>
                      <%--<li><a href="#"><fmt:message key="rating"/></a></li>--%>
                  </ul>

                  <ul class="nav navbar-nav  navbar-right">
                        <c:choose >
                            <c:when  test= "${!empty  currentUser}">
                                <li><a href="/controller?command=user_settings"><span class="glyphicon glyphicon-user"></span> ${currentUser.login}</a></li>
                                <li><a href="/controller?command=log_out"><span class="glyphicon glyphicon-log-out"></span> <fmt:message key="logoutButton"/></a></li>
                            </c:when>
                            <c:otherwise>

                                <li><form action="controller" class="navbar-form " method="post">
                                  <input type="hidden" name="command" value="log_in">
                                 <div class="form-group">
                                     <input type="text" required class="form-control" name="login" pattern="^[a-zA-ZёЁа-яА-Я][ёЁа-яА-Я\w-]{2,9}$" placeholder="<fmt:message key="login"/>" >
                                 </div>
                                  <div class="form-group">
                                      <input type="password" required class="form-control" name="password" pattern="^[-ёЁа-яА-Я\w\s]{6,20}$" placeholder="<fmt:message key="password"/>" >
                                  </div>
                                 <button type="submit" class="btn-md btn-link" >
                                     <span class="glyphicon glyphicon-log-in"></span> <fmt:message key="enterButton"/>
                                 </button>

                            </form></li>

                                <li><a href="/controller?command=sign_up"><span class="glyphicon glyphicon-user"></span> <fmt:message key="signUp"/></a></li>
                            </c:otherwise>
                        </c:choose>

                        <li><a href="/controller?command=change_language&code=${newCode}" >${altLocale}</a></li>
                  </ul>
              </div>
          </div>
      </div>
      </div>
  </div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
</body>
</fmt:bundle>
</html>