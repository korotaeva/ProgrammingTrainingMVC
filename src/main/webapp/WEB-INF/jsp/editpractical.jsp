<%--
  Created by IntelliJ IDEA.
  User: korot
  Date: 28.12.2016
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Практическое задание</title>
</head>
<style type="text/css">
    <%@include file="/WEB-INF/jsp/css/jquery.cleditor.css"%>
</style>
<script >
    <%@include file="/WEB-INF/jsp/js/jquery-1.8.2.min.js"%>
</script >
<script >
    <%@include file="/WEB-INF/jsp/js/jquery.cleditor.min.js"%>
</script >

<script>
    $(document).ready(function () { $("#description").cleditor(); });
</script>

<body>
<sec:authorize access="!isAuthenticated()">
    <p><a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Войти</a></p>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <p>Ваш логин: <sec:authentication property="principal.username" /></p>
    <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>

</sec:authorize>
<h1>Практическое задание</h1>


<form method="post"
      action="${pageContext.request.contextPath}/practical?operation=save">
    <fieldset>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <legend>
            <c:choose>
                <c:when test="${not empty practical.id }">
                    Обновить задание
                </c:when>
                <c:otherwise>
                    Добавить задание
                </c:otherwise>
            </c:choose>
        </legend>
        </sec:authorize>
        <div>
            <label for="name">Тема</label> <sec:authorize access="hasRole('ROLE_ADMIN')"><input type="text" name="name"
                                                  id="name"  value="${practical.name}" /></sec:authorize>
            <sec:authorize access="hasRole('ROLE_USER')"><h3>${practical.name}</h3> </sec:authorize>
        </div>

        <div  valign="top">
            <label for="description">Описание</label>
            <sec:authorize access="hasRole('ROLE_ADMIN')"><textarea name="description" id="description" rows="60" cols="60"> </sec:authorize>${practical.description} <sec:authorize access="hasRole('ROLE_ADMIN')"></textarea></sec:authorize>
        </div>

        <c:if test="${not empty practical.id}">
            <input type="hidden" name="id" value="${practical.id}" />
        </c:if>
        <c:if test="${not empty subjectid}">
            <input type="hidden" name="subjectid" value="${subjectid}" />
        </c:if>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />
    </fieldset>


    <div class="button-row">
        <a href="${pageContext.request.contextPath}/subject?operation=edit&pk=${subjectid}">Отмена</a>
        <sec:authorize access="hasRole('ROLE_ADMIN')">or <input type="submit" value="Сохранить" /> </sec:authorize>
    </div>
</form>

</body>
</html>