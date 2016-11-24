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
<fmt:bundle basename="applicationresource" prefix="page.admin.films.">
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
        <div class="alert alert-warning"><h3 align="center"><fmt:message key="table.name"/></h3></div>

        <table class="table table-hover table-striped center-class">
            <thead>
                <tr>
                    <th><fmt:message key="table.id" /></th>
                    <th><fmt:message key="table.title"/></th>
                    <th><a href="/controller?command=create_film&code=${newCode}" class="btn btn-default"><fmt:message key="button.add"/></a> </th>
                </tr>
            </thead>
            <c:forEach var="film" items="${films}">
                <tr >
                  <td >${film.id} </td>
                  <td>${film.title}</td>
                  <td><a href="/controller?command=view_film&id=${film.id}"class="btn btn-info"><fmt:message key="button.view"/></a> </td>
                  <td><a href="/controller?command=change_film&id=${film.id}" class="btn btn-warning"><fmt:message key="button.update"/></a> </td>
                  <td>
                      <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modal-${film.id}"><fmt:message key="button.delete"/></button>
                  </td>
                </tr>

                <div class="modal fade" id="modal-${film.id}">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content ">

                            <div class="modal-header">
                               <button class="close" type="button" data-dismiss="modal">
                                   <i class="fa fa-close"></i>
                                </button>
                                <h4 class="modal-title"><fmt:message key="modalWindow.title"/></h4>
                            </div>

                            <div class="modal-body">
                                <p><fmt:message key="modalWindow.text"/></p>
                            </div>

                            <div class="modal-footer">
                                <button class="btn btn-danger" type="button" data-dismiss="modal"><fmt:message key="modalWindow.button"/></button>
                                <a href="/controller?command=delete_film&id=${film.id}" class="btn btn-success"><fmt:message key="button.delete"/></a>
                            </div>

                        </div>
                    </div>
                </div>
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
