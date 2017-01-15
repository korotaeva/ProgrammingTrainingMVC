<%--
  Created by IntelliJ IDEA.
  User: korot
  Date: 24.12.2016
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="j"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <title>Темы</title>
</head>


<sec:authorize access="!isAuthenticated()">
        <p><a class="btn btn-lg btn-success" href="<j:url value="/login" />" role="button">Войти</a></p>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <p>Ваш логин: <sec:authentication property="principal.username" /></p>
        <p><a class="btn btn-lg btn-danger" href="<j:url value="/logout" />" role="button">Выйти</a></p>

    </sec:authorize>

<h1>Список тем</h1>

<sec:authorize access="hasRole('ROLE_ADMIN')"><a href="${pageContext.servletContext.contextPath}/subject?operation=create">Добавить тему</a>
</sec:authorize>
<table border="1">
    <tr>
        <td>Тема</td>
    </tr>
    <j:forEach items="${Subjects}" var="Subject" varStatus="status">
        <tr valign="top">
            <td>${Subject.name}</td>
            <td>
                <a href="${pageContext.servletContext.contextPath}/subject?operation=edit&pk=${Subject.id}">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">Редактировать/ </sec:authorize>Просмотр</a>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a href="${pageContext.servletContext.contextPath}/subject?operation=delete&pk=${Subject.id}">Удалить</a>
                </sec:authorize>
            </td>
        </tr>
    </j:forEach>
</table>
</body>
</html>
