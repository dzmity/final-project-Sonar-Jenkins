<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <body>

 <c:if test="${currentUser.role.toString() == initParam.admin}">
    <jsp:forward page="/controller?command=view_comments"/>
  </c:if>
  <jsp:forward page="/controller?command=start"/>

  </body>
</html>
