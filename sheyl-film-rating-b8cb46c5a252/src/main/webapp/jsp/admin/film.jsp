<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 17.08.2016
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="raf" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/admin/include/header.jsp"%>
<html>
<fmt:setLocale value="${locale}" />
<fmt:bundle basename="applicationresource" prefix="page.film.">
<head>
	<title>${film.title} </title>
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

        <form class="form-horizontal" role="form" name="updateFilmForm" action="controller" method="POST">
            <input type="hidden" name="code" value="${newCode}" />
            <c:choose >
                <c:when  test= "${!empty  film}">
                    <input type="hidden" name="command" value="update_film" />
                    <input type="hidden" name="id" value="${film.id}" />
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="command" value="add_film" />
                </c:otherwise>
            </c:choose>

            <div class="col-lg-5" >
            <div class="form-group">
                <label for="title" class="col-lg-2 control-label"><fmt:message key="film.title"/></label>
                <div class="col-lg-10">
                  <input required name="title" id="title" class="form-control" value="${film.title}">
                </div>
            </div>

            <div class="form-group">
                <label for="description" class="col-lg-2 control-label"><fmt:message key="description"/></label>
                <div class="col-lg-10">
                  <textarea required name="description" id="description" rows="4" class="form-control">${film.description}</textarea>
                </div>
            </div>

            <div class="form-group">
                <label for="year" class="col-lg-2 control-label"><fmt:message key="year"/></label>
                <div class="col-lg-10">
                  <input required type="number" name="year" id="year" class="form-control" value="${film.year}">
                </div>
            </div>

            <div class="form-group">
                <label for="length" class="col-lg-2 control-label"><fmt:message key="length"/></label>
                <div class="col-lg-10">
                  <input required type="number" name="length" id="length" class="form-control" value="${film.length}">
                </div>
            </div>

            <div class="form-group">
                <label for="country" class="col-lg-2 control-label"><fmt:message key="country"/></label>
                <div class="col-lg-10">
                  <input required type="text" name="country" class="form-control" id="country" placeholder="${film.country.country}" value="${film.country.country}">
                  <p style="color: red">${countryError}</p>
                </div>
            </div>

            <div class="form-group">
                <label for="director" class="col-lg-2 control-label"><fmt:message key="director"/></label>
                <div class="col-lg-10">
                  <input required type="text" name="director" class="form-control" id="director" placeholder="${film.director.name} ${film.director.surname}" value="${film.director.name} ${film.director.surname}">
                  <p style="color: red">${directorError}</p>
                </div>
            </div>

            <div class="form-group">
                <label for="picture" class="col-lg-2 control-label"><fmt:message key="picture"/></label>
                <div class="col-lg-10">
                  <input required type="text" name="picture"  class="form-control" id="picture" value="${film.picturePath}">
                </div>
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" class="btn btn-href btn-block " data-toggle="modal" data-target="#modal-1">
                        <fmt:message key="loadButton"/>
                    </button>
                </div>

            </div>

            <div class="form-group">
                <label for="trailer" class="col-lg-2 control-label"><fmt:message key="trailer.path"/></label>
                <div class="col-lg-10">
                  <input required type="text" name="trailer" id="trailer" class="form-control" value="${film.trailerPath}">
                </div>
            </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-success"><fmt:message key="changeButton"/></button>
                <a class="btn btn-warning" href="/controller?command=find_films_by_admin"><fmt:message key="cancelButton"/></a>
            </div>
          </div>
        </div>
        <div class="col-lg-4" >
            <c:forEach var="actor" items="${film.actors}">
                <div class="form-group">
                <label for="actor" class="col-lg-2 control-label"><fmt:message key="actor"/></label>
                    <div class="col-lg-10">
                        <input type="text" name="actor" id="actor" class="form-control" value="${actor.name} ${actor.surname}">
                    </div>
                </div>
            </c:forEach>
            <div class="form-group">
                <label for="actor2" class="col-lg-2 control-label"></label>
                <div class="col-lg-10">
                    <input type="text" name="actor" id="actor2" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="actor3" class="col-lg-2 control-label"></label>
                <div class="col-lg-10">
                    <input type="text" name="actor" id="actor3" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="actor1" class="col-lg-2 control-label"></label>
                <div class="col-lg-10">
                    <input type="text" name="actor" id="actor1" class="form-control">
                    <p style="color: red">${actorError}</p>
                </div>
            </div>
        </div>
        <div class="col-lg-3" >
            <c:forEach var="genre" items="${film.genres}">
                <div class="form-group">
                <label for="genre" class="col-lg-2 control-label"><fmt:message key="genre"/></label>
                    <div class="col-lg-10">
                        <input type="text" name="genre" id="genre" class="form-control" value="${genre.genre}">
                    </div>
                </div>
            </c:forEach>
            <div class="form-group">
                <label for="genre1" class="col-lg-2 control-label"></label>
                <div class="col-lg-10">
                    <input type="text" name="genre" id="genre1" class="form-control" >
                </div>
            </div>
            <div class="form-group">
                <label for="genre2" class="col-lg-2 control-label"></label>
                <div class="col-lg-10">
                    <input type="text" name="genre" id="genre2" class="form-control" >
                </div>
            </div>
            <div class="form-group">
                <label for="genre3" class="col-lg-2 control-label"></label>
                <div class="col-lg-10">
                    <input type="text" name="genre" id="genre3" class="form-control" >
                    <p style="color: red">${genreError}</p>
                </div>
            </div>
        </div>
        </form>
<%---------------------------------------------Modal Window-----------------------------------------------------------%>
        <div class="modal fade" id="modal-1">
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
                        <form action="${pageContext.request.contextPath}/controller" method="post" onSubmit="return checkFile(this)" enctype="multipart/form-data">
                            <input type="hidden" name="code" value="${newCode}"/>
                            <input type="text" class="btn-lg" required name="name" placeholder="<fmt:message key="browseButton.text"/>">
                            <input type="hidden" name="command" value="load_image">
                            <input type="hidden" name="folder" value="film">

                            <label class="btn btn-default btn-file btn-lg">
                                <fmt:message key="browseButton"/><input type="file" name="file" accept="image/png, image/jpeg" style="display: none;">
                            </label>
                            <button type="submit" class="btn btn-success btn-lg"><fmt:message key="sendButton"/></button>
                            <span style="color: red" id="loadMessage"></span>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
    <br>
    <br>
</div>
<script type="text/javascript">
    function checkFile(form) {
        val = form.file.value;

        var loadMessage = "";
        if (val == "") {
            loadMessage = "<fmt:message key="loadMessage"/>";
            document.getElementById("loadMessage").innerHTML = loadMessage;
            return false;
        } else return true;
    }
</script>
</fmt:bundle>
<fmt:bundle basename="applicationresource" prefix="page.footer.">
    <raf:adminFooter locale="${locale}"><fmt:message key="text"/></raf:adminFooter>
</fmt:bundle>
</body>
</html>
