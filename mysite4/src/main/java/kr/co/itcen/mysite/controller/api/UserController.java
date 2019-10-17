package kr.co.itcen.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.itcen.mysite.dto.JSONResult;
import kr.co.itcen.mysite.service.UserService;

@Controller("userApiController") //id가 필요 bean설정때 id가 필요해가지고 controller이름이 겹치지 않게 해주기 api라는 패키지가 구분해주지만 복잡하게 만들지 말고 이름 같이 쓰고 bean앞에 id 쓰기
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public JSONResult checkEmail(@RequestParam(value="email", required=true, defaultValue="")String email) {
		Boolean exist = userService.existUser(email);
		return JSONResult.success(exist);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("result","success");
//		map.put("data",exist);
		
//		return exist ? "exists" : "not exist";
//		JSONResult result = new JSONResult();
//		result.setResult("ok");
//		result.setResult(exist ? "있음" : "없음");
//		
//		return result;
	}
}
