package com.example.sb.users;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired private UserService userSvc;
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model) {
		List<User> list = userSvc.getUserList(page);
		model.addAttribute("userList", list);
		return "user/list";
		// localhost:8090/sb/user/list/1
	}
	
	@GetMapping("/register")
	public String register() {
		return "user/register";
	}
	
	@PostMapping("/register")
	public String registerProc(String uid, String pwd, String pwd2, String uname, String email) {
		if(userSvc.getUserByUid(uid) == null && pwd.equals(pwd2)) {
			String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
			User user = new User(uid, hashedPwd, uname, email);
			userSvc.registerUser(user);
		}
		return "redirect:/user/list/1";
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String loginProc(String uid, String pwd, HttpSession session, Model model) {
		int result = userSvc.login(uid, pwd);
		if (result == userSvc.CORRECT_LOGIN) {
			User user = userSvc.getUserByUid(uid);
			session.setAttribute("sessUid", "uid");
			session.setAttribute("sessUname", "uname");
			model.addAttribute("msg", uid + "님이 로그인 했다");
			return "redirect:/user/list/1";
		} else if (result == userSvc.WRONG_PASSWORD){
			model.addAttribute("msg", "비밀번호 확인");
			return "redirect:user/login";
		} else {
			model.addAttribute("msg", "아이디 확인");
			return "redirect:user/login";
		}
		
	}
	
	@GetMapping("/update")
	public String update(String uid, Model model) {
		User user = userSvc.getUserByUid(uid);
		model.addAttribute("user", user);
		return "user/update";
	}
	
	@PostMapping("/update")
	public String updateProc(String uid, String pwd, String pwd2, String hashedPwd,
						 	String uname, String email) {
		if (pwd != null && pwd.equals(pwd2))
			hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
		User user = new User(uid, hashedPwd, uname, email);
		userSvc.updateUser(user);
		return "redirect:/user/list/1";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam String uid) {
		userSvc.deleteUser(uid);
		return "redirect://user/list/1";
		
	}
}
