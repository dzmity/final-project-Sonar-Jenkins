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
<fmt:bundle basename="applicationresource" prefix="page.admin.comments.">
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
        <div class="alert alert-warning"><h3 align="center"><fmt:message key="table.title"/></h3></div>
        <form role="form" class="form-inline" action="controller">
            <input type="hidden" name="command" value="view_comments">
            <div class="form-group">
                <label for="date1"><fmt:message key="date"/> </label>
                <input type="date" class="form-control" pattern="(^19|20)\d\d-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1])$"
                       required name="firstDate" id="date1" value="${firstDate}">
             </div>
             <div class="form-group">
                <label for="date2"><fmt:message key="date"/></label>
                <input type="date" class="form-control" pattern="(^19|20)\d\d-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1])$"
                       required name="lastDate" id="date2" value="${lastDate}">
             </div>
             <button type="submit" class="btn btn-success"><fmt:message key="button.find"/></button>
        </form>

        <table class="table table-hover table-striped table-bordered center-class">
            <thead>
                <tr>
                    <th><fmt:message key="table.date" /></th>
                    <th><fmt:message key="table.user"/></th>
                    <th><fmt:message key="table.film"/></th>
                    <th><fmt:message key="table.comment"/></th>
                    <th><fmt:message key="button.ban"/></th>
                </tr>
            </thead>
            <c:forEach var="comment" items="${comments}">
                <tr>
                  <td >
                      <input type="datetime" class="form-control" required disabled  value="${raf:format(comment.date, 'd MMM H:mm', locale)}">
                  </td>
                  <td>
                    <a href="/controller?command=user_info&id=${comment.user.id}">${comment.user.login}</a>
                  </td>
                  <td>${comment.film.title}</td>
                  <td><p><c:out value="${comment.text}"/></p></td>
                  <td>
                      <a href="/controller?command=ban_comment&id=${comment.id}" class="btn btn-warning"><fmt:message key="button.ban"/></a>
                  </td>
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
