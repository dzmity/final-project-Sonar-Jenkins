<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 02.08.2016
  Time: 20:13
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
<fmt:bundle basename="applicationresource" prefix="page.actor.">
<head>
	<title>${actor.name} ${actor.surname} </title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>

<div class="container">
	<div class="row">
		<h2 align="center">${actor.name} ${actor.surname}</h2>
<%------------------------------------Left Row------------------------------------------------------------------------%>
		<div class="col-lg-3">
			<div class="row">
			<img src="images/actor/${actor.photoPath}.jpg" alt="" width="100%">
			</div>
		</div>
<%------------------------------------Center Row----------------------------------------------------------------------%>
		<div class="col-lg-7">

			<table class="table table-hover table-striped center-class">
				<tr>
				  <td><fmt:message key="profession"/> </td>
				  <td><fmt:message key="actor"/></td>
				</tr>
				<tr>
				  <td><fmt:message key="height"/> </td>
				  <td> ${actor.height}</td>
				</tr>
				<tr>
				  <td><fmt:message key="dob"/> </td>
				  <td><fmt:formatDate value="${actor.dateOfBirth}" dateStyle="full"/></td>
				</tr>
				<tr>
				  <td><fmt:message key="count"/> </td>
				  <td>${films.size()}</td>
				</tr>
			</table>
		</div>
<%-------------------------------------Right Row----------------------------------------------------------------------%>
		<div class="col-lg-2">
			<div class="row">
			<h4><fmt:message key="films"/></h4>
			<c:forEach var="film" items="${films}">
				<p><a href="/controller?command=view_film&id=${film.id}" >${film.title}(${film.year})</a></p>
			</c:forEach>
			</div>
		</div>

	</div>

	<div class="row">


	</div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<br><br><br><br><br><br><br><br><br>
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
