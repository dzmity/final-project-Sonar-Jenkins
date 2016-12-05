<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 02.08.2016
  Time: 15:38
  To change this template use File | Settings | File Templates.
  <fmt:setLocale value="${locale}"/>
<fmt:bundle basename="applicationresource">
</fmt:bundle>
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="raf" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include/header.jsp"%>
<html>
<fmt:setLocale value="${locale}" />
<fmt:bundle basename="applicationresource" prefix="page.start.">
<head>
    <title><fmt:message key="title"/> </title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="alert alert-warning"><h3 align="center"><fmt:message key="table.name"/></h3></div>
        <table class="table table-hover table-striped center-class">
            <thead>
                <tr>
                    <th><fmt:message key="table.number"/></th>
                    <th></th>
                    <th><fmt:message key="table.title"/></th>
                    <th><fmt:message key="table.rating"/></th>
                    <th><fmt:message key="table.count"/></th>
                </tr>
            </thead>
            <c:forEach var="film" items="${topFilms}">
                <c:choose >
                    <c:when  test= "${ film.key == 1}">
                        <c:set var="color" value="palegoldenrod"/>
                    </c:when>
                    <c:when  test= "${ film.key == 2}">
                        <c:set var="color" value="lightgray"/>
                    </c:when>
                    <c:when  test= "${ film.key == 3}">
                        <c:set var="color" value="goldenrod"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="color" value="aliceBlue"/>
                    </c:otherwise>
                </c:choose>
            <tr data-href = "${pageContext.request.contextPath}/controller?command=view_film&id=${film.value.id}" style="background-color: ${color} ">
              <td>${film.key}</td>
              <td> <img src="${pageContext.request.contextPath}/images/film/${film.value.picturePath}.jpg" height="80"> </td>
              <td>${film.value.title}</td>
              <td> ${film.value.rating}%</td>
              <td> ${film.value.marksCount}</td>
            </tr>
            </c:forEach>
        </table>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script>
    $(function() {
        $('tbody tr[data-href]').addClass('clickable').click( function() {
            window.location = $(this).attr('data-href');
        });
    });
</script>

</fmt:bundle>
<fmt:bundle basename="applicationresource" prefix="page.footer.">
    <raf:footer locale="${locale}"><fmt:message key="text"/></raf:footer>
</fmt:bundle>
</body>
</html>
