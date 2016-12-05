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
<fmt:bundle basename="applicationresource" prefix="page.admin.country.">
<head>
    <title><fmt:message key="title"/> </title>
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
        <div class="col-lg-6">
            <div class="row">
                <div class="alert alert-warning"><h3 align="center"><fmt:message key="table.title1"/></h3></div>
                <form class="form-horizontal" role="form" name="updateCountryForm" action="${pageContext.request.contextPath}/controller" method="POST">
                    <input type="hidden" name="code" value="${newCode}"/>
                    <input type="hidden" name="command" value="update_countries" />
                    <c:forEach var="country" items="${countries}">
                        <input type="text" class="form-control input-lg" name="country${country.id}" required value="${country.country}">
                        <input type="hidden" name="id" value="${country.id}" />
                    </c:forEach>
                    <input type="text" class="form-control input-lg" name="newCountry" >
                    <input type="text" class="form-control input-lg" name="newCountry" >
                    <br>
                    <button type="submit" class="btn btn-success"><fmt:message key="saveButton"/></button>
                </form>
            </div>
        </div>

        <div class="col-lg-6">
            <div class="row">
                <div class="alert alert-warning"><h3 align="center"><fmt:message key="table.title2"/></h3></div>
                <form class="form-horizontal" role="form" name="updateFilmForm" action="${pageContext.request.contextPath}/controller" method="POST">
                    <input type="hidden" name="code" value="${newCode}"/>
                    <input type="hidden" name="command" value="update_genres" />
                    <c:forEach var="genre" items="${genres}">
                        <input type="hidden" name="genreId" value="${genre.id}" />
                        <input type="text" class="form-control input-lg" name="genre${genre.id}" required value="${genre.genre}">
                    </c:forEach>
                    <input type="text" class="form-control input-lg" name="newGenre" >
                    <input type="text" class="form-control input-lg" name="newGenre" >
                    <br>
                    <button type="submit" class="btn btn-success"><fmt:message key="saveButton"/></button>
                </form>
            </div>
        </div>
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
