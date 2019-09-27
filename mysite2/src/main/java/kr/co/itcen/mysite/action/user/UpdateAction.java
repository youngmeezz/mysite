package kr.co.itcen.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		new UserDao().update(authUser.getNo(), name, password, gender);
		
		UserVo userVo = new UserVo();
		userVo.setNo(authUser.getNo());
		userVo.setEmail(authUser.getEmail());
		userVo.setName(name);
		userVo.setGender(gender);
		
		//인증처리(Session 처리)
		session.setAttribute("authUser", userVo);
		
		WebUtils.redirect(request, response, request.getContextPath());
	}
}
