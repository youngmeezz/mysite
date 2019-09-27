package kr.co.itcen.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.GuestBookDao;
import kr.co.itcen.web.mvc.Action;

public class DeleteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String password = request.getParameter("password");
		
		Long no_ = Long.parseLong(no);
		
		new GuestBookDao().set_delete(no, password);
		response.sendRedirect(request.getContextPath() + "/guestbook1?a=guestbook");
	}
}
