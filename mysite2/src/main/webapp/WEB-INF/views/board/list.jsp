<%@page import="kr.co.itcen.mysite.vo.BoardVo"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
				<form id="search_form"
					action="${pageContext.servletContext.contextPath }/board"
					method="get">
					<input type="text" id="kwd" name="keyword" value="" /> <input
						type="hidden" name="page" value="${param.page }" /> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items='${list}' var='vo'>
						<tr>
							<td>${vo.no }</td>
							<td style='padding-left:${vo.depth*15}px; text-align:left;'>
								<c:if test="${vo.depth gt 0}">
									<img
										src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
								</c:if>
									<c:choose>
										<c:when test="${vo.status eq 0}">
											삭제된 게시물 입니다.
										</c:when>
										<c:otherwise>
											<a href="${pageContext.servletContext.contextPath }/board?a=viewform&no=${vo.no}&userNo=${vo.userNo}">
												${vo.title }
											</a>
										</c:otherwise>
									</c:choose>
							</td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.registerDate }</td>
							<td><c:if test="${sessionScope.authUser.no eq vo.userNo }">
									<a
										href="${pageContext.servletContext.contextPath }/board?a=deleteform&no=${vo.no}&userNo=${vo.userNo}"
										class="del">삭제</a>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${pagination.prev }">
							<li><a
								href="${pageContext.servletContext.contextPath }/board?keyword=${param.keyword }&page=${pagination.startPage - 1 }">◀</a></li>
						</c:if>
						<c:forEach begin="${pagination.startPage }"
							end="${pagination.endPage }" var="pg">
							<c:choose>
								<c:when test="${pg eq pagination.currentPage }">
									<li class="selected"><a
										href="${pageContext.servletContext.contextPath }/board?keyword=${param.keyword }&page=${pg }">${pg }</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="${pageContext.servletContext.contextPath }/board?keyword=${param.keyword }&page=${pg }">${pg }</a></li>
								</c:otherwise>
							</c:choose>

						</c:forEach>
						<c:if test="${pagination.next }">
							<li><a
								href="${pageContext.servletContext.contextPath }/board?keyword=${param.keyword }&page=${pagination.endPage + 1 }">▶</a></li>
						</c:if>
					</ul>
				</div>
				<!-- pager 추가 -->

				<div class="bottom">

					<!-- session(로그인 된 상태)이 있으면 글쓰기 창 보이고 session(로그인 안 된 상태이면) 글쓰기 창 가리기 -->
					<c:if test="${not empty authUser }">
						<a
							href="${pageContext.servletContext.contextPath }/board?a=writeform"
							id="new-book">글쓰기</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>