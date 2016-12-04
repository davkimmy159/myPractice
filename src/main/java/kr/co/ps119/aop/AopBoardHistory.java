package kr.co.ps119.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AopBoardHistory {

	/*
	@Pointcut("execution(* kr.co.ps119.stomp.messageVO.StompEditorContent.setChatAreaMessage(..))")								//	Board editor content update
	public void pointcutOnEditorContentUpdate(Long boardId, String editorContent) {
	}
	
	@Around("pointcutOnEditorContentUpdate(boardId, editorContent)")
	public Object onEditorContentUpdate(ProceedingJoinPoint jp, Long boardId, String editorContent) throws Throwable {
		
		
		return jp.proceed(new Object[] {boardId, editorContent});
	}
	
	@Pointcut("execution(* kr.co.ps119.service.BoardService.updateBoardDBContent(..)) && args(boardId, editorContent)")			//	Board DB content update
	public void pointcutOnUpdateBoardDBContent(Long boardId, String editorContent) {
	}
	
	@Around("pointcutOnUpdateBoardDBContent(boardId, editorContent)")
	public Object onUpdateBoardDBContent(ProceedingJoinPoint jp, Long boardId, String editorContent) throws Throwable {
		
		
		return jp.proceed(new Object[] {boardId, editorContent});
	}
	*/
	
	/*
	@Pointcut("execution(* kr.co.ps119.stomp.messageVO.StompEditorContent.setChatAreaMessage(..)) || " +	//	Board editor content update
			  "execution(* kr.co.ps119.service.BoardService.updateBoardDBContent(..)) || " +				//	Board DB content update
			  "execution(* kr.co.ps119.service.MemoService.saveOneMemo(..)) || " +							//	Memo create
			  "execution(* kr.co.ps119.service.MemoService.updateOneMemo(..)) || " +						//	Memo content update
			  "execution(* kr.co.ps119.service.MemoService.deleteById(..))")								//	Memo delete
	public void createBoardHistoryPointcut() {
	}
	*/
	
}
