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
<fmt:bundle basename="applicationresource" prefix="page.admin.user.">
<head>
    <title>${user.login} </title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body onload="startTimer()">
<c:if  test= "${ currentUser.role.toString() != initParam.admin}">
    <c:redirect url="/index.jsp"/>
</c:if>

<div class="container">
    <div class="row">
        <div class="alert alert-warning"><h3 align="center"><fmt:message key="table1.title"/></h3></div>
        <table class="table table-hover table-striped center-class">
            <thead>
                <tr>
                    <th><fmt:message key="table1.user"/></th>
                    <th><fmt:message key="table1.role"/></th>
                    <th><fmt:message key="table1.commentCount"/></th>
                    <th><fmt:message key="table1.markCount"/></th>
                    <th><fmt:message key="table1.ban"/></th>
                </tr>
            </thead>
            <tr>
                <td>${user.login} </td>
                <td>${user.role}</td>
                <td>${user.comments.size()}</td>
                <td>${user.markCount}</td>
                <td>
                    <c:if test="${user.banTime != null}">
                        <p><span id="timer" style="color: #f00; font-size: 150%; font-weight: bold;">${raf:toSeconds(user.banTime)}</span></p>
                    </c:if>
                </td>
                <td style="text-align:right">
                    <c:choose >
                        <c:when  test = "${user.banTime != null}">
                            <a href="/controller?command=lift_ban_user&id=${user.id}" class="btn btn-success"><fmt:message key="button.liftBan"/></a>
                        </c:when>
                        <c:otherwise>
                        <form role="form" class="form-inline" action="controller">
                            <input type="hidden" name="command" value="ban_user">
                            <input type="hidden" name="id" value="${user.id}">
                            <input type="hidden" name="code" value="${newCode}">
                            <input type="number" pattern="\d+" required name="days" placeholder="<fmt:message key="days"/>" >
                            <button type="submit" class="btn btn-danger"><fmt:message key="button.ban"/></button>
                        </form>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
        <hr>
        <form role="form" class="form-inline" action="controller">
            <input type="hidden" name="code" value="${newCode}"/>
            <input type="hidden" name="command" value="upgrade_status">
            <input type="hidden" name="id" value="${user.id}">
            <div class="form-group">
                <label for="status"><fmt:message key="table1.status"/> </label>
                <input type="text" list="stat" class="form-control" required id="status" name="status" value="${user.status}">
                <datalist id="stat">
                    <option>BEGINNER</option>
                    <option>EXPERIENCED</option>
                    <option>MASTER</option>
                </datalist>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="button.change"/></button>
        </form>
        <p style="color: red">${statusError}</p>

        <table class="table table-hover table-striped table-bordered center-class">
            <thead>
                <tr>
                    <th><fmt:message key="table2.date" /></th>
                    <th><fmt:message key="table2.comment"/></th>
                    <th><fmt:message key="table1.ban"/></th>
                </tr>
            </thead>
            <c:forEach var="comment" items="${user.comments}">
                <tr>
                  <td>
                      <input type="datetime" class="form-control" required disabled name="lastDate" id="date2" value="${raf:format(comment.date, 'd MMM H:mm', locale)}">
                  </td>
                  <td><p><c:out value="${comment.text}"/></p></td>
                  <td>
                      <a href="/controller?command=ban_comment&id=${comment.id}" class="btn btn-warning"><fmt:message key="table1.ban"/></a>
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
<script type="text/javascript">
    function startTimer() {
        var my_timer = document.getElementById("timer");
        var time = my_timer.innerHTML;
        var arr = time.split(":");
        var d = arr[0];
        var h = arr[1];
        var m = arr[2];
        var s = arr[3];
        if (s == 0) {
            if (m == 0) {
                if (h == 0) {
                    if (d == 0) {
                        alert("<fmt:message key="timeOut"/>");
                        window.location.reload();
                        return;
                    }
                    d--;
                    h = 24;
                }
                h--;
                m = 60;
                if (h < 10) h = "0" + h;
            }
            m--;
            if (m < 10) m = "0" + m;
            s = 59;
        }
        else s--;
        if (s < 10) s = "0" + s;
        document.getElementById("timer").innerHTML = d+":"+ h+":"+m+":"+s;
        setTimeout(startTimer, 1000);
    }
</script>
</fmt:bundle>
<fmt:bundle basename="applicationresource" prefix="page.footer.">
    <raf:adminFooter locale="${locale}"><fmt:message key="text"/></raf:adminFooter>
</fmt:bundle>
</body>
</html>
