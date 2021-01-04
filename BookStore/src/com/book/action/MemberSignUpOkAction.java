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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MemberSignUpOkAction implements Action {
	//회원 가입 메서드.
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// 첨부파일이 저장될 위치(경로)를 설정
		String saveFolder = 
				"C:\\Users\\yyj01\\OneDrive\\문서\\GitHub\\BookShop\\BookShop\\WebContent\\upload\\user";
		
		// 첨부파일 용량(크기) 제한. - 파일 업로드 최대 크기
		int fileSize = 5 * 1024 * 1024;  // 5MB
		
		// 이진 파일 업로드릉를 위한 객체 생성.
		MultipartRequest multi = new MultipartRequest(
				request,               // 일반적인 request
				saveFolder,            // 업로드 파일 저장 위치(경로)     
				fileSize,              // 업로드될 첨부파일 최대 크기 
				"UTF-8",               // 문자 인코딩 방식
				new DefaultFileRenamePolicy()  // 파일 이름 중복 처리
		);
		
		String mem_id = multi.getParameter("mem_id").trim();
		String mem_pwd = multi.getParameter("mem_pwd").trim();
		String mem_name = multi.getParameter("mem_name").trim();
		String mem_age = multi.getParameter("mem_age").trim();
		String mem_nickname = multi.getParameter("mem_nickname").trim();
		String mem_gender = multi.getParameter("mem_gender").trim();
		String mem_birth = multi.getParameter("mem_birth_month")+"-"+multi.getParameter("mem_birth_day");	//생일 문자열 형식) mm-dd
		String mem_number = multi.getParameter("mem_number").trim();
		String mem_addr = multi.getParameter("mem_addr_1")+"|"+multi.getParameter("mem_addr_2")+"|"+multi.getParameter("mem_addr_3"); //주소 문자열 형식)우편변호|주소
		String mem_email = multi.getParameter("mem_email").trim();
		System.out.println(mem_id);
		System.out.println(mem_pwd);
		
		MemberDTO dto = new MemberDTO();
		
		dto.setMem_id(mem_id);
		dto.setMem_pwd(mem_pwd);
		dto.setMem_name(mem_name);
		dto.setMem_age(mem_age);
		dto.setMem_nickname(mem_nickname);
		dto.setMem_gender(mem_gender);
		dto.setMem_birth(mem_birth);
		dto.setMem_number(mem_number);
		dto.setMem_addr(mem_addr);
		dto.setMem_email(mem_email);
		
		MemberDAO dao = MemberDAO.getInstance();
		int res = dao.insertMember(dto);
		
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		if(res>0) {
			session.setAttribute("session_mem_id",mem_id );
			session.setAttribute("session_mem_name",mem_name );
			session.setAttribute("session_mem_age",mem_age );
			session.setAttribute("session_mem_nickname",mem_nickname);
			session.setAttribute("session_mem_gender",mem_gender);
			session.setAttribute("session_mem_birth",mem_birth );
			session.setAttribute("session_mem_addr",mem_addr);
			session.setAttribute("session_mem_email",mem_email);
			
			forward.setRedirect(true);
			forward.setPath("main.do");
		}else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		return forward;
	}

}
