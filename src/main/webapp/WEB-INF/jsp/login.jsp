<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@page session="true"%>
<html>
<head>
<title>Форма авторизации</title>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>

</head>
<body onload='document.loginForm.username.focus();'>


	<div id="login-box">

		<h3>Вход в систему</h3>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<form name='loginForm'
			action="<c:url value='/j_spring_security_check' />" method='POST'>

			<table>
				<tr>
					<td>Пользователь:</td>
					<td><input type='text' name='username'></td>
				</tr>
				<tr>
					<td>Пароль:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='1'><input name="submit" type="submit"
						value="Войти" /></td>

					<td colspan='2'><a  valign="top" href="${pageContext.servletContext.contextPath}/registration">Зарегистрироваться</a></td>

					<%--<td colspan='2'><input type="submit" name="registration" value="Зарегистрироваться"></td>--%>
				</tr>


			</table>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />



		</form>


	</div>

</body>
</html>