<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<c:choose>
					<c:when test="${not empty parentNo }">
						<form:form
							 modelAttribute="boardVo"
							 id="board-form" 
							 name="boardForm"
							 method="post" 
							 action="${pageContext.servletContext.contextPath }/board/reply">	
							<input type = "hidden" name = "no" value = "${parentNo }">
						 </form:form>
					</c:when>
					<c:otherwise>
						<form:form
							 modelAttribute="boardVo"
							 id="board-form" 
							 name="boardForm"
							 method="post" 
							 action="${pageContext.servletContext.contextPath }/board/write">	
						</form:form>
					</c:otherwise>
				</c:choose>
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기</th>
						</tr>
						<tr>
							<!-- <td class="label">제목</td> -->
							<td><label class="block-label" for="title">제목</label></td>
							<td>
							<form:input path="title"/>
						<!-- 	<input type="text" name="title" value=""> -->
							
							</td>
						</tr>
						<tr>
							<!-- <td class="label">내용</td> -->
							<td><label class="block-label" for="contents">내용</label></td>
							<td>
								<!-- <textarea id="content" name="contents"></textarea> -->
								<form:input path="contents"/>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board">취소</a>
						<input type="file" name="file">
						<input type="submit" value="등록"> 
					</div>
			<%-- 	</form:form> --%>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
			<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>