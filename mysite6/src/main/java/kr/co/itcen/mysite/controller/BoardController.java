package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	
	//게시판 글 삽입할 글쓰기 페이지 가져오기 (validation)
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String insertForm(@RequestParam(value = "file", required = false)MultipartFile multipartFile,Model model, @ModelAttribute BoardVo boardVo) {
	
		String url = boardService.restore(multipartFile);
		model.addAttribute("url",url);	
		return "board/write" ;
	}
	
	
	
	//게시판 글  등록 후 -> 게시판 페이지 가져오기(redirect) (validation)
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String insert(@ModelAttribute("boardVo") @Valid BoardVo vo,BindingResult result, Model model,HttpSession session) {
		
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
	
		
		if( result.hasErrors() ) {
			model.addAllAttributes(result.getModel());
			return "board/write";
		}
		

		if (authUser != null) {
			vo.setUserNo(authUser.getNo());
			boardService.insert(vo);
			
		}
		return "redirect:/board" ;
	}
	
	
	
	//게시판 글 쓴 view 페이지 가져오기
	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String viewForm(@PathVariable("no") Long no, Long userNo,Model model, @ModelAttribute BoardVo boardVo) {
		
		BoardVo vo = boardService.get(no,userNo);
		model.addAttribute("vo",vo);
		
		//view 조회 하면 조회수 증가하기
		boardService.hit(no, userNo);
		
		return "board/view" ;
	}
	
	
	//게시판 수정할 폼 페이지 가져오기 (validation)
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("no") Long no, Model model, @ModelAttribute BoardVo boardVo) {
		
		BoardVo vo = boardService.get(no,0L);
		model.addAttribute("vo",vo);
		
		return "board/modify";
	}
	
	
	//게시판 수정한 글 등록하기  -> 게시판 페이지 가져오기(redirect) (validation)
	//POST -> 데이터가 뒤에 붙지 않고 / GET -> 데이터파라미터 받는것
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String update(@ModelAttribute("boardVo") @Valid BoardVo vo,BindingResult result, Model model,HttpSession session) {
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if( result.hasErrors() ) {
			model.addAllAttributes(result.getModel());
			return "board/modify";
		}
		
		if (authUser != null) {
			vo.setUserNo(authUser.getNo());
			boardService.update(vo);
		}
		
		return "redirect:/board";
	}
		
	
	//게시판 삭제 하기  -> 게시판 페이지 가져오기(redirect)
	@RequestMapping(value="/delete/{no}", method = RequestMethod.GET)
	public String delete(@ModelAttribute("boardVo") @Valid BoardVo vo,BindingResult result, Model model,HttpSession session) {
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if( result.hasErrors() ) {
			model.addAllAttributes(result.getModel());
			return "redirect:/board";
		}
		
		//삭제된 게시물 검색 안되게 하기
		if (authUser != null) {
			vo.setUserNo(authUser.getNo());
			boardService.delete(vo);
		}
	
		return "redirect:/board";
	}
	
	//게시판 답글 작성 할 폼 가져오기(validation)
	@RequestMapping(value = "/write/{no}", method = RequestMethod.GET)
	public String replyForm(@PathVariable("no") Long no, Model model, @ModelAttribute BoardVo boardVo) {
		
		BoardVo vo = boardService.get(no,0L);
		model.addAttribute("parentNo", no);
		
		return "board/write";
	}
	
	

	
	//게시판 답글 작성 후 -> 게시판 페이지 가져오기 (이때 그룹넘버(g_no),순서넘버(o_no) 업데이트시키기)(validation)
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String replyInsert(@ModelAttribute("boardVo") @Valid BoardVo vo,BindingResult result, Model model,HttpSession session) {
	
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if( result.hasErrors() ) {
			model.addAllAttributes(result.getModel());
			return "board/write";
		}
		
		if (authUser != null) {
			vo.setUserNo(authUser.getNo());
			boardService.replyInsert(vo);
		}

		return "redirect:/board";
	}
	

	

	
	//글 작성하기에 파일업로드 포함시키기
//	@RequestMapping("/upload")
//	public String upload(
//			@RequestParam(value = "email", required = true, defaultValue= "")String email,
//			@RequestParam(value = "file1", required = false)MultipartFile multipartFile,
//			Model model) {
//			
//			//System.out.println("email:" + email);
//			
//			String url = boardService.restore(multipartFile);
//			model.addAttribute("url",url);
//			return "result";
//	}
	
}
