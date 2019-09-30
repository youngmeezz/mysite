package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.Pagination;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	//게시판 조회하기
	@RequestMapping({"", "/list"})
	public String list(Model model, @RequestParam(value = "keyword", required = false, defaultValue = "")String keyword, @RequestParam(value="page", defaultValue = "1", required = false)int page) {
		int totalCnt = boardService.getBoardCount(keyword);
		
		Pagination pagination = new Pagination(page, totalCnt, 10, 5);

		
		List<BoardVo> list = boardService.getList(keyword, pagination);
		
		model.addAttribute("list",list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pagination", pagination);
		return "board/list";
	}
	
	//게시판 글 삽입할 글쓰기 페이지 가져오기
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String insertForm() {
		return "board/write" ;
	}
	
	//게시판 글  등록 후 -> 게시판 페이지 가져오기(redirect)
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String insert(@ModelAttribute BoardVo vo,HttpSession session) {
	
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if (authUser != null) {
			vo.setUserNo(authUser.getNo());
			boardService.insert(vo);
			
		}
		
		return "redirect:/board" ;
	}
	
	
	//게시판 글 쓴 view 페이지 가져오기
	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String viewForm(@PathVariable("no") Long no, Long userNo,Model model) {
		
		BoardVo vo = boardService.get(no,userNo);
		model.addAttribute("vo",vo);
		
		//view 조회 하면 조회수 증가하기
		boardService.hit(no, userNo);
		
		return "board/view" ;

	}
	
	//게시판 수정할 폼 페이지 가져오기
	@RequestMapping(value = "/modifyform/{no}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("no") Long no, Model model) {
		
		BoardVo vo = boardService.get(no,0L);
		model.addAttribute("vo",vo);
		
		return "board/modify";
	}
	
	
	//게시판 수정한 글 등록하기  -> 게시판 페이지 가져오기(redirect) 
	//POST -> 데이터가 뒤에 붙지 않고 / GET -> 데이터파라미터 받는것
	@RequestMapping(value="/modifyform", method = RequestMethod.POST)
	public String update(@ModelAttribute BoardVo vo, HttpSession session) {
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");


		if (authUser != null) {
			vo.setUserNo(authUser.getNo());
			boardService.update(vo);
		}
		
		return "redirect:/board";
	}
		
	
	//게시판 삭제 하기  -> 게시판 페이지 가져오기(redirect)
	@RequestMapping(value="/deleteform/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no,
			@ModelAttribute BoardVo vo, HttpSession session) {
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if (authUser != null) {
			vo.setUserNo(authUser.getNo());
			boardService.delete(vo);
		}
	
		return "redirect:/board" ;
	}
	
	//게시판 답글 작성 할 폼 가져오기
	@RequestMapping(value = "/writeform/{no}", method = RequestMethod.GET)
	public String replyForm(@PathVariable("no") Long no, Model model) {
		
		BoardVo vo = boardService.get(no,0L);
		model.addAttribute("parentNo", no);
		
		return "board/write";
	}
	
	//게시판 답글 작성 후 -> 게시판 페이지 가져오기 (이때 그룹넘버(g_no),순서넘버(o_no) 업데이트시키기)
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String replyInsert(@ModelAttribute BoardVo vo,HttpSession session) {
	
		UserVo authUser = (UserVo) session.getAttribute("authUser");
				
		if (authUser != null) {
			vo.setUserNo(authUser.getNo());
			boardService.replyInsert(vo);
		}

		return "redirect:/board" ;
	}
}
