package kr.co.ps119.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import kr.co.ps119.entity.MemberUser;

@Component
@Aspect
public class AspectTest_1 {
	
	@Pointcut("execution(* kr.co.ps119.controller.Controller_2.test())")
	public void pointcut_1() {
	}
	
	@Pointcut("execution(* kr.co.ps119.service.MemberService.save(kr.co.ps119.entity.MemberUser)) && args(newMemberUser)")
	public void pointcut_2(MemberUser newMemberUser) {
	}
	
	@Pointcut("execution(* kr.co.ps119.service.MemberService.delete(..)) && args(id)")
	public void pointcut_3(Long id) {
	}
	
	@Pointcut("execution(* kr.co.ps119.service.MemberService.findAll())")
	public void pointcut_4() {
	}
	
	@Before("pointcut_1()")
	public void before_1(JoinPoint jp) {
		System.out.println("Aspect(pointcut_1) : before");
		System.out.println(jp.getArgs());
	}
	
	@AfterReturning("pointcut_1()")
	public void afterReturning_1() {
		System.out.println("Aspect(pointcut_1) : after returning");
	}
	
	@AfterThrowing("pointcut_1()")
	public void afterThrowing_1() {
		System.out.println("Aspect(pointcut_1) : after throwing");
	}
	
	@After("pointcut_1()")
	public void after_1() {
		System.out.println("Aspect(pointcut_1) : after");
	}
	
	@Around("pointcut_1()")
	public void around_1(ProceedingJoinPoint jp) {
		try {
			jp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Asepct : error on around");
		}
		System.out.println("Aspect(pointcut_1) : around");
	}
	
	@Before("pointcut_2(newMemberUser)")
	public void before_2(MemberUser newMemberUser) {
		System.out.println("Aspect(pointcut_2) : before");
		System.out.println(newMemberUser);
	}
	
	@Before("pointcut_3(id)")
	public void before_3(Long id) {
		System.out.println("Aspect(pointcut_3) : before");
	}
	
	@Before("pointcut_4()")
	public void before_4() {
		System.out.println("Aspect(pointcut_4) : before");
	}
}