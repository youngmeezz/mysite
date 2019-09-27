package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo)session.getAttribute("authUser");

		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		String parentNo = request.getParameter("parent_no");
		
		BoardVo boardVo = new BoardVo();
		boardVo.setTitle(title);
		boardVo.setContents(contents);
		boardVo.setUserNo(authUser.getNo());
		
		if (parentNo != null && parentNo.length() > 0) {
			boardVo.setNo(Long.parseLong(parentNo));
			new BoardDao().replyInsert(boardVo);
		} else {
			new BoardDao().insert(boardVo);
		}
	
		response.sendRedirect(request.getContextPath() + "/board");
		
	}
}

