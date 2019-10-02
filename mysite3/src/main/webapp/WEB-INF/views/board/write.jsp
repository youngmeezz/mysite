<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<c:choose>
					<c:when test="${not empty parentNo }">
						<c:set var="seperator"
							value="${pageContext.servletContext.contextPath }/board/reply"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="seperator"
							value="${pageContext.servletContext.contextPath }/board/write"></c:set>
					</c:otherwise>
				</c:choose>
				<form:form modelAttribute="boardVo" id="board-form" name="boardForm" cssClass="board-form"
					method="post" action="${seperator }">
					<input type="hidden" name="no" value="${parentNo }">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기</th>
						</tr>
						<tr>
							<td><label class="label" for="title">제목</label></td>
							<td><form:input path="title" id="title" /></td>
						</tr>
						<tr>
							<td><label class="label" for="contents">내용</label></td>
							<td><form:textarea path="contents" id="content"/></td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board">취소</a>
						<input type="file" name="file"> 
						<input type="submit" value="등록">
					</div>
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>