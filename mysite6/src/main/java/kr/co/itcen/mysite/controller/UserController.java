package kr.co.itcen.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.security.Auth;
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

	//로그인 폼 들어가기
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	//회원정보 수정한 폼 보여주기 GET(READ)
	@Auth("USER")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateform(@AuthUser UserVo authUser,Model model) {
		
		//System.out.println(authUser);
		authUser = userService.getUserByNo(authUser.getNo());
		model.addAttribute("userInfo", authUser);

		return "user/update";
	}

	// 회원정보 수정하기 POST(CREATE)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, @ModelAttribute UserVo userVo) {

		if (authUser != null) {
			userVo.setNo(authUser.getNo());
			userService.update(userVo);
		}

		return "redirect:/";
	}
	
	@RequestMapping(value="/auth", method=RequestMethod.POST)
	public void auth() {
		
	}
	 
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout() {
		
	}
}
