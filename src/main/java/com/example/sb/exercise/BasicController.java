package com.example.sb.exercise;



import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/ex")
public class BasicController {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("/hello")			 // localhost:8090/sb/ex/hello
	public String hello() {
		return "exercise/hello";			// hello.html
	}
	
	@ResponseBody			//	html 파일을 찾지 말고, 데이터를 직접 전송
	@GetMapping("/noHtml")
	public String noHtml() {
		return "<h1>Hello Spring Boot!!!</h1>";
	}
	
	@GetMapping("/redirect")
	public String redirect() {
		return "redirect:/ex/hello";			// Redirection
	}
	
	
	@GetMapping("/params")
	public String params(Model model) {
		model.addAttribute("name", "제임스");
		return "exercise/params";
	}
	
	@GetMapping("/params2")
	public String params2(Model model, HttpServletRequest req) {		
		String name = req.getParameter("name");
		model.addAttribute("name", name);
		return "exercise/params";
	}
	
	@GetMapping("/params3")
	public String params3(Model model, String name) {		
		model.addAttribute("name", name);
		return "exercise/params";
	}
	
	@GetMapping("/params4")
	public String params4(Model model, String name, int count) {		
		model.addAttribute("name", name+count);
		return "exercise/params";
	}
	
	
	@GetMapping("/memberForm")
	public String memberForm() {
		return "exercise/memberForm";
	}
	
//	@PostMapping("/memberProc")
//	public String memberProc(Member member, Model model) {
//		System.out.println(member);
//		model.addAttribute("name", member.getName());
//		return "exercise/params";
//	}
	
	@PostMapping("/memberProc")
	public String memberProc(Member member, Model model) {
		log.info(member.toString());
		model.addAttribute("name", member.getName());
		return "exercise/params";
	}
	
	@GetMapping("/login")
	public String login() {
		return "exercise/login";
	}
	
//	@PostMapping("/login")
//	public String loginProc(String uid, String pwd, Model model) {
//		String hashedPwd = "$2a$10$gBKG8IZVNCHGVVWmDQLsXOd/KId9/5YboVtmfzAZo1j9bLpRpjw46";
//		if (uid.equals("james") && BCrypt.checkpw(pwd, hashedPwd)) {
//			model.addAttribute("msg", uid + "님이 로그인 했다.");
//			return "exercise/loginResult";
//		} else {
//			model.addAttribute("msg", "uid , 비밀번호 확인");
//			return "exercise/loginResult";
//		}
//	}
	
//	@PostMapping("/login")
//	public String loginProc(String uid, String pwd, HttpSession session, Model model) {
//		String hashedPwd = "$2a$10$gBKG8IZVNCHGVVWmDQLsXOd/KId9/5YboVtmfzAZo1j9bLpRpjw46";
//		if (uid.equals("james") && BCrypt.checkpw(pwd, hashedPwd)) {
//			model.addAttribute("msg", uid + "님이 로그인 했다.");
//			session.setAttribute("sessUid", "uid");
//			session.setAttribute("sessUname", "제임스");
//			return "exercise/loginResult";
//		} else {
//			model.addAttribute("msg", "uid , 비밀번호 확인");
//			return "exercise/loginResult";
//		}
//	}
	
	
	@PostMapping("/login")
	   public String loginProc(String uid, String pwd, HttpSession session, Model model){
	      String hashedPwd = "$2a$10$gBKG8IZVNCHGVVWmDQLsXOd/KId9/5YboVtmfzAZo1j9bLpRpjw46";
	      if (uid.equals("james")&& BCrypt.checkpw(pwd, hashedPwd)) {
	         model.addAttribute("msg", uid + "님이 로그인 했습니다.");
	         session.setAttribute("sessUid", uid);
	         session.setAttribute("sessUname", "제임스");
	         return "exercise/loginResult";
	      }else {
	         model.addAttribute("msg", "uid, 비밀번호를 확인하세요.");
	         return "exercise/loginResult";
	      }
	   }
	
	@GetMapping("/path/{uid}/{bid}")
	@ResponseBody
	public String path(@PathVariable String uid, @PathVariable int bid) {
		return "<h1>uid=" + uid + ", bid=" + bid + "</h1>";
	}
	
	
	
}
