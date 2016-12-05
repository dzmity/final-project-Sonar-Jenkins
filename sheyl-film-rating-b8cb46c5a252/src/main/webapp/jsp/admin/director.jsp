<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 17.08.2016
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="raf" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/admin/include/header.jsp"%>
<html>
<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="applicationresource" prefix="page.director.">
<head>
	<title>${director.name} ${director.surname}</title>
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
        <form class="form-horizontal" role="form" name="updateFilmForm" action="${pageContext.request.contextPath}/controller" method="POST">
            <input type="hidden" name="code" value="${newCode}"/>
            <c:choose >
                <c:when  test= "${!empty director}">
                    <input type="hidden" name="command" value="update_director" />
                    <input type="hidden" name="id" value="${director.id}" />
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="command" value="add_director" />
                </c:otherwise>
            </c:choose>

            <div class="col-lg-7" >
                <div class="form-group">
                    <label for="name" class="col-lg-2 control-label"><fmt:message key="name"/></label>
                    <div class="col-lg-10">
                        <input required name="name" id="name" pattern="^([ёЁа-яА-Я]+|[a-zA-Z]+)$"
                               class="form-control" value="${director.name}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="surname" class="col-lg-2 control-label"><fmt:message key="surname"/></label>
                    <div class="col-lg-10">
                        <input required name="surname" id="surname" class="form-control" value="${director.surname}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="country" class="col-lg-2 control-label"><fmt:message key="country"/></label>
                    <div class="col-lg-10">
                        <input required name="country" id="country" class="form-control" value="${director.country.country}">
                        <p style="color: red">${countryError}</p>
                    </div>
                </div>

                <div class="form-group">
                    <label for="photo" class="col-lg-2 control-label"><fmt:message key="photo"/></label>
                    <div class="col-lg-10">
                        <input required type="text" name="photo"  class="form-control" id="photo" value="${director.photoPath}">
                    </div>
                    <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" class="btn btn-href btn-block " data-toggle="modal" data-target="#modal-1">
                        <fmt:message key="loadButton"/>
                    </button>
                </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-success"><fmt:message key="changeButton"/></button>
                        <a class="btn btn-warning" href="${pageContext.request.contextPath}/controller?command=find_directors_by_admin"><fmt:message key="cancelButton"/></a>
                        <p style="color: red">${actorError}</p>
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
                            <input type="hidden" name="folder" value="director">

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
