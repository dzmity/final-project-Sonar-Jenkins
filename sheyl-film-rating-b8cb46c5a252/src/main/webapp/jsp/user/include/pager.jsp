<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 15.08.2016
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>

</head>
<body>
<hr>
<br>
<fmt:setLocale value="${locale}" />
<fmt:bundle basename="applicationresource" prefix="page.pager.">
<div class="row">
    <nav>
       <ul class="pager">
           <li class="previous ${leftClass}"><a href="${pageContext.request.contextPath}/${leftHref}">&larr; <fmt:message key="previous"/></a></li>
           <li>${page}</li>
           <li class=" next ${rightClass}"><a href="${pageContext.request.contextPath}/${rightHref}"><fmt:message key="next"/> &rarr;</a></li>
       </ul>
    </nav>
    <br>
    <br>
</div>
</fmt:bundle>
</body>
</html>
