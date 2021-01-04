package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;

public class MemberFindPwdAction implements Action {
	//비밀번호 찾기 폼으로 이동하는 메서드.
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("user/find_pwd.jsp");
		
		return forward;
	}

}
