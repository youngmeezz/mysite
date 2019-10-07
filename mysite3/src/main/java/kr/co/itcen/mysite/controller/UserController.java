package kr.co.itcen.mysite.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.security.Auth;
import kr.co.itcen.mysite.security.Auth.Role;
import kr.co.itcen.mysite.security.AuthUser;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/joinsuccess", method = RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}

//	@RequestMapping(value = "/join", method = RequestMethod.POST)
//	public String join(@ModelAttribute @Valid UserVo vo, Model model, BindingResult result) {
//		
////		model.addAttribute("userVo", vo);
////		if(vo.getEmail().indexOf("@") < 0) {
////			model.addAttribute("userVo",vo);
////			//파라미터를 model + Attribute
////		}
//		
//		if( result.hasErrors() ) {
////			List<ObjectError> list = result.getAllErrors();
////			for(ObjectError error : list) {
////				System.out.println(error);
////			}
//			model.addAllAttributes(result.getModel());//key값이 
//			
//			
////			Map<String, Object> m = result.getModel();
////			for(String key : m.keySet()) {
////				model.addAttribute("",m.get(key));
////			}
//			
//			return "user/join";
//		}
//		
//		userService.join(vo);
//		return "redirect:/user/joinsuccess";
//		}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
		@ModelAttribute("userVo") @Valid UserVo vo,
		BindingResult result,
		Model model) {
		
		if( result.hasErrors() ) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String login(UserVo vo, HttpSession session, Model model) {
//
//		UserVo userVo = userService.getUser(vo);
//		if (userVo == null) {
//			model.addAttribute("result", "fail");
//			return "user/login";
//		}
//		// 로그인 처리
//		session.setAttribute("authUser", userVo);
//		return "redirect:/";
//	}

	
	// 로그아웃
	//	@Auth(Role="ADMIN")
	
//	@RequestMapping(value = "/logout", method = RequestMethod.GET)
//	public String logout(HttpSession session) {
//		// 접근 제어(ACL)
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if (authUser != null) {
//			session.removeAttribute("authUser");
//			session.invalidate();
//		}
//		return "redirect:/";
//	}

	
	//회원정보 수정한 폼 보여주기 GET(READ)
	@Auth("USER")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser,Model model) {
		
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		Long no = authUser.getNo();
//		
//		System.out.println(authUser);
//		
//		
//		UserVo userVo = userService.getUserByNo(no);
//		model.addAttribute("userVo",userVo);
//		if (authUser == null) {
//			return "redirect:/";
//		}
//		
		authUser = userService.getUserByNo(authUser.getNo());
		model.addAttribute("userInfo", authUser);

		return "user/update";
	}

	// 회원정보 수정하기 POST(CREATE)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(UserVo vo, HttpSession session, Model model) {
		// 접근 제어(ACL)
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser != null) {

			vo.setNo(authUser.getNo());
			userService.update(vo);
			// 로그인된 auth 수정 처리?
			session.setAttribute("authUser", vo);
		}

		return "redirect:/";
	}
	
//	@ExceptionHandler(UserDaoException.class)
//	public String handlerException() {
//		return "error/exception.jsp";
//	}


}
