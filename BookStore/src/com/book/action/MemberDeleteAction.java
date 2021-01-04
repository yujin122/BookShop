package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.AllQnADAO;
import com.book.model.BookBuyDAO;
import com.book.model.BookCartDAO;
import com.book.model.BookRequestDAO;
import com.book.model.BookSaleDAO;
import com.book.model.CommuDAO;
import com.book.model.MemberDAO;
import com.book.model.MentDAO;
import com.book.model.QnABoardDAO;

public class MemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		String mem_nickname = session.getAttribute("session_mem_nickname").toString();
		
		String nickname_id = mem_nickname+"("+mem_id+")";
		String pwd = request.getParameter("pwd");
		
		MemberDAO mem_dao = MemberDAO.getInstance();
		String mem_pwd = mem_dao.getPwd(mem_id);
		
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		
		if(pwd.equals(mem_pwd)) {
			// 판매정보
			BookSaleDAO bs_dao = BookSaleDAO.getInstance();
			bs_dao.deleteBookSale(mem_id);
			
			// 구매정보
			BookBuyDAO bb_dao = BookBuyDAO.getInstance();
			bb_dao.deleteBookBuy(mem_id);
			
			// 장바구니정보
			BookCartDAO bc_dao = BookCartDAO.getInstance();
			bc_dao.deleteBookCart(mem_id);
			
			// 문의글정보
			QnABoardDAO qb_dao = QnABoardDAO.getInstance();
			qb_dao.deleteQnA(mem_id);
			
			// qna정보
			AllQnADAO aq_dao = AllQnADAO.getInstance();
			aq_dao.deleteAllQnA(mem_id);
			
			// 도서요청정보
			BookRequestDAO br_dao = BookRequestDAO.getInstance();
			br_dao.deleteBookRequest(mem_id);
			
			// 커뮤니티정보
			CommuDAO c_dao = CommuDAO.getInstance();
			c_dao.deleteMemCont(nickname_id);
			
			// 댓글정보
			MentDAO m_dao = MentDAO.getInstance();
			m_dao.deleteMent(nickname_id);
			
			int res = mem_dao.deleteMember(mem_id);
			
			if(res > 0) {
				session.invalidate();
				
				forward.setRedirect(true);
				forward.setPath("main.do");			
			}else {
				out.println("<script>");
				out.println("alert('회원탈퇴 실패')");
				out.println("history.back()");
				out.println("</script>");
			}
		}else {
			out.println("<script>");
			out.println("alert('비밀번호가 틀렸습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
