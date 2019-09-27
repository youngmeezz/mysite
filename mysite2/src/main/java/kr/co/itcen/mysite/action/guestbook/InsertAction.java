package kr.co.itcen.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.GuestBookDao;
import kr.co.itcen.mysite.vo.GuestBookVo;
import kr.co.itcen.web.mvc.Action;

public class InsertAction implements Action{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String writer = request.getParameter("name");
		String password = request.getParameter("pass");
		String text = request.getParameter("content");
		
		GuestBookVo vo = new GuestBookVo();
		vo.setWriter(writer);
		vo.setPassword(password);
		vo.setText(text);
	
		new GuestBookDao().insert(vo);
	
		response.sendRedirect(request.getContextPath() + "/guestbook1?a=guestbook");
	}
}
