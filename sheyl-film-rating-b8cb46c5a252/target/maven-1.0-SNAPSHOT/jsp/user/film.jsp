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
<fmt:bundle basename="applicationresource" prefix="page.film.">
<head>
	<title>${film.title} </title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>

<div class="container">
	<div class="row">
		<h2 align="center">${film.title}</h2>

<%------------------------------------------LEFT COLUMN---------------------------------------------------------------%>
		<div class="col-lg-3">
			<div class="row">
			<img src="images/film/${film.picturePath}.jpg" alt="" width="100%">
			<h3><fmt:message key="rating"/></h3>
			<div class="progress danger">
				<div class="progress-bar progress-bar-success" style="min-width: 5%; width: ${film.rating}%" >
					${film.rating}%
				</div>
			</div>

			<p style="color: red">${markError}</p>

			<c:if test="${! empty currentUser}">
				<form name="commentForm" action="controller" method="POST">
					<input type="hidden" name="code" value="${newCode}"/>
					<input type="hidden" name="command" value="add_mark"/>
					<input type="hidden" name="filmId" value="${film.id}"/>
					<fieldset class="rating" >
						<legend><fmt:message key="rate"/></legend>
						<input type="radio" id="star10" name="rating" value="10" onClick="subm(this.form)"/><label for="star10" title="Great!">10 stars</label>
						<input type="radio" id="star9" name="rating" value="9" onClick="subm(this.form)"/><label for="star9" title="9">9 stars</label>
						<input type="radio" id="star8" name="rating" value="8" onClick="subm(this.form)"/><label for="star8" title="8">8 stars</label>
						<input type="radio" id="star7" name="rating" value="7" onClick="subm(this.form)"/><label for="star7" title="7">7 stars</label>
						<input type="radio" id="star6" name="rating" value="6" onClick="subm(this.form)"/><label for="star6" title="6">6 stars</label>
						<input type="radio" id="star5" name="rating" value="5" onClick="subm(this.form)"/><label for="star5" title="passably">5 stars</label>
						<input type="radio" id="star4" name="rating" value="4" onClick="subm(this.form)"/><label for="star4" title="4">4 stars</label>
						<input type="radio" id="star3" name="rating" value="3" onClick="subm(this.form)"/><label for="star3" title="3">3 stars</label>
						<input type="radio" id="star2" name="rating" value="2" onClick="subm(this.form)"/><label for="star2" title="2">2 stars</label>
						<input type="radio" id="star1" name="rating" value="1" onClick="subm(this.form)"/><label for="star1" title="Sucks big time!">1 star</label>
					</fieldset>
				</form>
			</c:if>
			</div>
		</div>
<%------------------------------------------CENTER COLUMN--------------------------------------------------------------%>
		<div class="col-lg-7">

			<table class="table table-hover table-striped center-class">
				<tr>
					<td><fmt:message key="year"/> </td>
					<td>${film.year}</td>
				</tr>
				<tr>
					<td><fmt:message key="country"/> </td>
					<td>${film.country.country}</td>
				</tr>
				<tr>
					<td><fmt:message key="director"/> </td>
					<td>
						<a href="/controller?command=view_director&id=${film.director.id}">
						${film.director.name} ${film.director.surname}
						</a>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="genre"/> </td>
					<td>
						<c:forEach var="genre" items="${film.genres}">${genre.genre} </c:forEach>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="length"/></td>
					<td>${film.length} <fmt:message key="minutes"/></td>
				</tr>
			</table>

			<h3><fmt:message key="description"/></h3>
			<p>${film.description}</p>

			<%--------------------------------------TRAILER--------------------------------%>
			<hr>
			<h3><fmt:message key="trailer"/></h3>
			<iframe align="center" width="100%" height="300" src="${film.trailerPath}" frameborder="0" allowfullscreen></iframe>
			<hr>

			<%-------------------------------------COMMENTS--------------------------------%>

			<div class="page-header">
				<h3>
					<small class="pull-right"><fmt:message key="comment"/> ${film.comments.size()}</small><fmt:message key="comments"/>
				</h3>
			</div>

			<c:choose >
				<c:when  test= "${! empty currentUser}">
				<form name="commentForm" action="controller" method="POST">

					<div class="form-group">
						<input type="hidden" name="code" value="${newCode}"/>
						<input type="hidden" name="command" value="add_comment" />
						<input type="hidden" name="filmId" value="${film.id}" />
						<input type="hidden" name="userId" value="${currentUser.id}" />
						<textarea name="comment" required class="form-control" placeholder="<fmt:message key="text.comment" />"></textarea>
					</div>

					<div class="form-group">
						<c:choose >
						<c:when  test= "${currentUser.banTime == null}">
						<button type="submit" class="btn btn-success" >
							<i class="fa fa-share"></i> <fmt:message key="text.commentButton" />
						</button>
					</div>
				</form>
				</c:when>
				<c:otherwise>
			</div>
			</form>
			<button class="btn btn-warning"
					data-toggle="popover"
					title="<fmt:message key="warning.title" />"
					data-content="<fmt:message key="ban.text" />"
					data-placement="left"
					data-trigger="focus">
				<i class="fa fa-share"></i> <fmt:message key="text.commentButton" />
			</button>
			<br>
			</c:otherwise>
			</c:choose>
			</c:when>
			<c:otherwise>
				<p style="color: red"><fmt:message key="warning" /></p>
			</c:otherwise>
			</c:choose>
			<br>
			<div class="comments-list">
				<c:forEach var="comment" items="${film.comments}">
					<div class="media">
						<p class="pull-right"><small>${raf:format(comment.date, 'd MMMM yyyy   H:mm', locale)}</small></p>
						<a class="media-left" href="#">
							<img src="/images/user/${comment.user.picturePath}.jpeg" width="64 px" height="64 px">
						</a>
						<div class="media-body">
							<h4 class="media-heading user_name">${comment.user.login}</h4>
							<p><c:out value="${comment.text}"/></p>
						</div>
					</div>
				</c:forEach>
			</div>
			<br>
			<br>
			<br>
		</div>

<%------------------------------------------------RIGHT COLUMN--------------------------------------------------------%>
		<div class="col-lg-2">
			<div class="row">
			<h4><fmt:message key="actors"/></h4>
			<c:forEach var="actor" items="${film.actors}">
				<p><a href="/controller?command=view_actor&id=${actor.id}" >${actor.name} ${actor.surname}</a></p>
			</c:forEach>
			</div>
		</div>
	</div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">
    function subm(form) {
        form.submit();
    }
</script>

<script>
	$(function() {
		$('[data-toggle="tooltip"]').tooltip();
		$('[data-toggle="popover"]').popover();
	});
</script>
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
