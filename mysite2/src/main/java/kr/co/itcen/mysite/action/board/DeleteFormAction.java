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

public class DeleteFormAction implements Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo)session.getAttribute("authUser");
	
		Long no = Long.parseLong(request.getParameter("no"));
	
		BoardVo boardVo = new BoardVo();
		
		boardVo.setNo(no);

		new BoardDao().delete(no, authUser.getNo());

		response.sendRedirect(request.getContextPath() + "/board");
	}
}
