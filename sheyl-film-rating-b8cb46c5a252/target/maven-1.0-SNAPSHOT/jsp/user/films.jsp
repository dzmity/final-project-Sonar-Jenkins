<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 12.08.2016
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="raf" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="include/header.jsp"%>
<html>
<fmt:setLocale value="${locale}" />
<fmt:bundle basename="applicationresource" prefix="page.films.">
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
        <%------------------------------------------SEARCH------------------------------------------------------------%>
        <c:if test="${page == 1 || page == null}">
        <div  class="col-lg-6 ">
            <div class="row">
               <div class="tabs">
                   <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab-1" data-toggle="tab"><fmt:message key="search.byName"/></a></li>
                        <li><a href="#tab-2" data-toggle="tab"><fmt:message key="search.byGenre"/></a></li>
                        <li><a href="#tab-3" data-toggle="tab"><fmt:message key="search.byYear"/></a></li>
                        <li><a href="#tab-4" data-toggle="tab"><fmt:message key="search.byDirector"/></a></li>
                        <li><a href="#tab-5" data-toggle="tab"><fmt:message key="search.byActor"/></a></li>
                   </ul>
                   <div class="tab-content">
                       <div class="tab-pane fade in active" id="tab-1">
                           <div id="imaginary_container">
                               <form class="form-horizontal" role="form" name="updateFilmForm" action="controller" method="POST">
                                   <input type="hidden" name="command" value="find_films_by_title" />
                                    <div class="input-group stylish-input-group">
                                        <input type="text" required name = "word" class="form-control"  placeholder="<fmt:message key="search.search"/>" value="${searchError}">
                                        <span class="input-group-addon">
                                            <button type="submit">
                                                <span class="glyphicon glyphicon-search"></span>
                                            </button>
                                        </span>
                                    </div>
                               </form>
                            </div>
                       </div>

                       <div class="tab-pane fade" id="tab-2">
                           <br>
                           <div id="imaginary_container2">
                               <form class="form-horizontal" role="form" name="updateFilmForm" action="controller" method="GET">
                                    <input type="hidden" name="command" value="find_films_by_genre" />
                                    <div class="input-group stylish-input-group">
                                        <input type="text" required name = "word" class="form-control"  placeholder="<fmt:message key="search.search"/>" >
                                        <span class="input-group-addon">
                                            <button type="submit">
                                                <span class="glyphicon glyphicon-search"></span>
                                            </button>
                                        </span>
                                    </div>
                               </form>
                           </div>
                       </div>

                       <div class="tab-pane fade" id="tab-3">
                           <br>
                           <div id="imaginary_container3">
                               <form class="form-horizontal" role="form" name="updateFilmForm" action="controller" method="GET">
                                   <input type="hidden" name="command" value="find_films_by_year" />
                                    <div class="input-group stylish-input-group">
                                        <input type="number" required name = "word" class="form-control"  placeholder="<fmt:message key="search.search"/>" >
                                        <span class="input-group-addon">
                                            <button type="submit">
                                                <span class="glyphicon glyphicon-search"></span>
                                            </button>
                                        </span>
                                    </div>
                               </form>
                            </div>
                       </div>

                       <div class="tab-pane fade" id="tab-4">
                           <br>
                           <div id="imaginary_container4">
                               <form class="form-horizontal" role="form" name="updateFilmForm" action="controller" method="GET">
                                   <input type="hidden" name="command" value="find_films_by_director" />
                                    <div class="input-group stylish-input-group">
                                        <input type="text" required name = "word" class="form-control"  placeholder="<fmt:message key="search.search"/>" >
                                        <span class="input-group-addon">
                                            <button type="submit">
                                                <span class="glyphicon glyphicon-search"></span>
                                            </button>
                                        </span>
                                    </div>
                               </form>
                            </div>
                       </div>
                       <div class="tab-pane fade" id="tab-5">
                           <br>
                           <div id="imaginary_container5">
                               <form class="form-horizontal" role="form" name="updateFilmForm" action="controller" method="GET">
                                   <input type="hidden" name="command" value="find_films_by_actor" />
                                    <div class="input-group stylish-input-group">
                                        <input type="text" required name = "word" class="form-control"  placeholder="<fmt:message key="search.search"/>" >
                                        <span class="input-group-addon">
                                            <button type="submit">
                                                <span class="glyphicon glyphicon-search"></span>
                                            </button>
                                        </span>
                                    </div>
                               </form>
                            </div>
                       </div>
                   </div>
               </div>
            </div>
        </div>
        <div class="col-lg-7">
            <div class="row">
                <p>${resultError}</p>
                <c:if test="${!empty resultError }">
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                </c:if>
            </div>
        </div>
        </c:if>
        <%------------------------------------------TABLE 1-----------------------------------------------------------%>


        <c:if test="${!empty leftColumn }">
            <div class="col-lg-6 " >
                <div class="row">
                    <table class="table table-hover table-striped center-class">
                        <thead>
                        <tr>
                            <th><fmt:message key="table.cover" /></th>
                            <th><fmt:message key="table.title"/></th>
                            <th><fmt:message key="table.year"/></th>
                        </tr>
                        </thead>
                        <c:forEach var="film" items="${leftColumn}">
                            <tr data-href = "/controller?command=view_film&id=${film.id}">
                                <td><img src="/images/film/${film.picturePath}.jpg" height="100"></td>
                                <td><a href="/controller?command=view_film&id=${film.id}">${film.title}</a></td>
                                <td> ${film.year}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </c:if>

        <%------------------------------------------TABLE 2-----------------------------------------------------------%>
        <c:if test="${!empty rightColumn }">
            <div class="col-lg-6">
                <div class="row">
                    <table class="table table-hover table-striped center-class">
                        <thead>
                        <tr>
                            <th><fmt:message key="table.cover" /></th>
                            <th><fmt:message key="table.title"/></th>
                            <th><fmt:message key="table.year"/></th>
                        </tr>
                        </thead>
                        <c:forEach var="film" items="${rightColumn}">
                            <tr data-href = "/controller?command=view_film&id=${film.id}">
                                <td><img src="/images/film/${film.picturePath}.jpg" height="100">  </td>
                                <td><a href="/controller?command=view_film&id=${film.id}">${film.title}</a></td>
                                <td> ${film.year} </td>
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
