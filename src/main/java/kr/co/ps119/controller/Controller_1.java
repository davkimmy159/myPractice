package kr.co.ps119.controller;

import kr.co.ps119.entity.*;
import kr.co.ps119.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(
		value = "/",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class Controller_1 {

	@Autowired
	Service_1 service1;

	@GetMapping(value = "test")
	public String main() {
		return "room_body";
	}

	@GetMapping(value = "index")
	public String user_index() {
		return "index_body";
	}
	
	

	
	/*
	@GetMapping(value = "account_creation")
	public String accountCreation() {
		return "account_creation";
	}
	*/

	/*
	 * @RequestMapping(method = RequestMethod.GET) public String printList(Model
	 * model) { List<MemberUser> list = service1.findAll();
	 * model.addAttribute("list", list); if
	 * (!model.containsAttribute("memberUserForm"))
	 * model.addAttribute("memberUserForm", null); return "member_table"; }
	 * 
	 * @RequestMapping(value = "control", method = RequestMethod.POST, params =
	 * "insert") public String insert(Model model, @Validated MemberUserForm
	 * memberUserForm, BindingResult result) { if (result.hasErrors()) { return
	 * printList(model); } MemberUser memberUser = new MemberUser();
	 * BeanUtils.copyProperties(memberUserForm, memberUser); MemberUser
	 * addedMemberUse = service1.save(memberUser); return printList(model); }
	 * 
	 * @RequestMapping(value = "control", method = RequestMethod.POST, params =
	 * "update") public String update(Model model, @RequestParam(name = "modId")
	 * Long modId) { service1.delete(modId); return printList(model); }
	 * 
	 * @RequestMapping(value = "control", method = RequestMethod.POST, params =
	 * "delete") public String delete(Model model, @RequestParam(name = "modId")
	 * Long modId) { service1.delete(modId); return printList(model); }
	 * 
	 * @RequestMapping(value = "writing", method = RequestMethod.POST) public
	 * String writing() { return "a"; }
	 */
}
