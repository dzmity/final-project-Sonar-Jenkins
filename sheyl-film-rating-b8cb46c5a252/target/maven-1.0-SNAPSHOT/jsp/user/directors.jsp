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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include/header.jsp"%>
<html>
<fmt:setLocale value="${locale}" />
<fmt:bundle basename="applicationresource" prefix="page.directors.">
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
        <c:if test="${!empty leftColumn }">
            <div class="col-lg-6">
                <div class="row">
                    <table class="table table-hover table-striped center-class">
                        <thead>
                        <tr>
                            <th><fmt:message key="table.photo"/></th>
                            <th><fmt:message key="table.director"/></th>
                        </tr>
                        </thead>
                        <c:forEach var="director" items="${leftColumn}">
                            <tr data-href = "/controller?command=view_director&id=${director.id}">
                                <td ><img src="/images/director/${director.photoPath}.jpg" height="100"> </td>
                                <td> <a href="/controller?command=view_director&id=${director.id}" >${director.name} ${director.surname}</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </c:if>

        <c:if test="${!empty rightColumn }">
            <div class="col-lg-6">
                <div class="row">
                    <table class="table table-hover table-striped center-class">
                        <thead>
                        <tr>
                            <th><fmt:message key="table.photo"/></th>
                            <th><fmt:message key="table.director"/></th>
                        </tr>
                        </thead>
                        <c:forEach var="director" items="${rightColumn}">
                            <tr data-href = "/controller?command=view_director&id=${director.id}">
                                <td ><img src="/images/director/${director.photoPath}.jpg" height="100"> </td>
                                <td> <a href="/controller?command=view_director&id=${director.id}" >${director.name} ${director.surname}</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </c:if>

    </div>
    <c:if test="${! (page == null)}">
        <%@ include file="include/pager.jsp"%>
    </c:if>
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
