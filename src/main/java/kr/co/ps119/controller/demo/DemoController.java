package kr.co.ps119.controller.demo;

import kr.co.ps119.data.entity.*;
import kr.co.ps119.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class DemoController {

	@Autowired
	DemoService demoService;

	/*
	 * @ModelAttribute("memberUserForm") public MemberUserForm mem() { return
	 * new MemberUserForm("Test", 12); }
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String printList(Model model) {
		List<MemberUser> list = demoService.findAll();
		model.addAttribute("list", list);
		if (!model.containsAttribute("memberUserForm"))
			model.addAttribute("memberUserForm", null);
		return "member_table";
	}

	@RequestMapping(value = "control", method = RequestMethod.POST, params = "insert")
	public String insert(Model model, @Validated MemberUserForm memberUserForm, BindingResult result) {
		if (result.hasErrors()) {
			return printList(model);
		}
		MemberUser memberUser = new MemberUser();
		BeanUtils.copyProperties(memberUserForm, memberUser);
		MemberUser addedMemberUse = demoService.save(memberUser);
		return printList(model);
	}

	@RequestMapping(value = "control", method = RequestMethod.POST, params = "update")
	public String update(Model model, @RequestParam(name = "modId") Long modId) {
		demoService.delete(modId);
		return printList(model);
	}

	@RequestMapping(value = "control", method = RequestMethod.POST, params = "delete")
	public String delete(Model model, @RequestParam(name = "modId") Long modId) {
		demoService.delete(modId);
		return printList(model);
	}

	@RequestMapping(value = "writing", method = RequestMethod.POST)
	public String writing() {
		return "a";
	}
	
	@RequestMapping(value = "test")
	public String test() {
		return "test";
	}
	
	@RequestMapping(value = "index")
	public String index() {
		return "index";
	}
	

	@RequestMapping(value = "login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "account_creation")
	public String accountCreation() {
		return "account_creation";
	}
	
}
