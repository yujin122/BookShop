package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.MemberDAO;
import com.book.model.MemberDTO;


public class MemberEditOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String mem_name = request.getParameter("mem_name").trim();
		String mem_pwd = request.getParameter("mem_pwd").trim();
		String mem_age = request.getParameter("mem_age").trim();
		String mem_nickname = request.getParameter("mem_nickname").trim();
		String mem_gender = request.getParameter("mem_gender").trim();
		String mem_birth = request.getParameter("mem_birth_month")+"-"+request.getParameter("mem_birth_day").trim();
		String mem_id = request.getParameter("mem_id").trim();
		String mem_number = request.getParameter("mem_number").trim();
		String mem_addr = request.getParameter("mem_addr_1")+"|"+request.getParameter("mem_addr_2")+"|"+request.getParameter("mem_addr_3").trim();	
		String mem_email = request.getParameter("mem_email").trim();
		
		
		MemberDTO dto = new MemberDTO();
		
		dto.setMem_name(mem_name);
		dto.setMem_age(mem_age);
		dto.setMem_nickname(mem_nickname);
		dto.setMem_gender(mem_gender);
		dto.setMem_birth(mem_birth);
		dto.setMem_id(mem_id);
		dto.setMem_pwd(mem_pwd);
		dto.setMem_number(mem_number);
		dto.setMem_addr(mem_addr);
		dto.setMem_email(mem_email);
		
		MemberDAO dao = MemberDAO.getInstance();
		int check = dao.updateMember(dto);
		
		ActionForward forward = new ActionForward();
		
		if(check > 0) {
			forward.setRedirect(true);
			forward.setPath("member_cont.do");
		}else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('다시 수정해주세요')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
