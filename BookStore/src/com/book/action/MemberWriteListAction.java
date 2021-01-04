package com.book.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;

import com.book.model.CommuDAO;
import com.book.model.CommuDTO;

public class MemberWriteListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		
		String mem_id = session.getAttribute("session_mem_id").toString();
		String mem_nickname = session.getAttribute("session_mem_nickname").toString();
		
		String nickname_id = mem_nickname +"(" + mem_id + ")";
		System.out.println(nickname_id);
		// 페이징 작업
		int rowsize = 10;		// 한 페이지 당 보여줄 게시물 수
		int block = 5;			// 아래 보여질 페이지 최대 수
		int totalRecord = 0;	// DB 상의 게시물 전체 수
		int allPage = 0;		// 전체 페이지 수
		int page = 0;			// 현재 페이지 변수
		
		if(request.getParameter("page") !=null) {
			page =Integer.parseInt(request.getParameter("page"));
		}else {
			page = 1;  
		}
		
		// 페이지 시작번호
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		int startBlock = (((page - 1) / block) * block) + 1;
		int endBlock = (((page - 1) / block) * block) + block;
		
		CommuDAO dao = CommuDAO.getInstance();
		
		totalRecord = dao.getMemberCommuCount(nickname_id);
		
		allPage =(int)Math.ceil(totalRecord / (double)rowsize);
		
		if(endBlock > allPage) {
			endBlock = allPage;
		}
		
		List<CommuDTO> pageList = dao.getMemberCommu(nickname_id, page,rowsize);
		
		request.setAttribute("page", page);
		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);
		request.setAttribute("totalRecord", totalRecord);
		request.setAttribute("allPage", allPage);
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		request.setAttribute("startBlock", startBlock);
		request.setAttribute("endBlock", endBlock);
		request.setAttribute("List", pageList);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("mypage/member_write_list.jsp");
		return forward;
	}

}
