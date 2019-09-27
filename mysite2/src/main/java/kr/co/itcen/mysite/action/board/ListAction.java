package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.pagination.*;
import kr.co.itcen.mysite.dao.UserBoardDao;
import kr.co.itcen.mysite.vo.UserBoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String keyword = request.getParameter("keyword");
		if(keyword == null) {
			keyword = "";
		}
		String pageStr = request.getParameter("page");
		int page = Integer.parseInt((pageStr == null || pageStr.length() ==0) ? "1" : pageStr);
		
		
		
		UserBoardDao dao = new UserBoardDao();

		int totalCnt = dao.getBoardCnt(keyword);
		Pagination pagination = new Pagination(page, totalCnt, 10, 5);

		/*index(list)*/
		List<UserBoardVo> list = dao.getList(keyword, pagination);
		request.setAttribute("list", list);
		request.setAttribute("pagination", pagination);
	
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");

	}   

}
