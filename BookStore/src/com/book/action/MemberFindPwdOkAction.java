package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.MemberDAO;

public class MemberFindPwdOkAction implements Action {
	//입력받은 이름과 아이디를 검사하여 같으면 새로운 비밀번호를 주고 해당 비밀번호를 메일로 보내고 메인 페이지로 이동. 다르면 alert하고 뒤로 이동.
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		
		String mem_name = request.getParameter("mem_name").trim();
		String mem_id = request.getParameter("mem_id").trim();
		MemberDAO dao = MemberDAO.getInstance();
		int res = dao.ConfirmMemberPwd(mem_name, mem_id);
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		if(res>0) {
			String new_mem_pwd = randomString();
			int result = dao.insertNewpwd(new_mem_pwd,mem_name,mem_id);
			
			if(result >0) {
				String mem_email = dao.searchEmail(mem_name,mem_id);
				try {
					//해당 이메일로 새로운 비밀번호를 보내는 메서드. 이메일이 없을 시 오류.
					navermailSend(new_mem_pwd, mem_email);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					out.println("<script>");
					out.println("alert('이메일이 없습니다.')");
					out.println("history.back()");
					out.println("</script>");
				}
				out.println("<script>");
				out.println("alert('새로운 비밀번호를 입력된 이메일로 보냈습니다. 비밀번호를 수정해주세요.')");
				out.println("</script>");
				
				session.setAttribute("pwdok", "pwdok");
	            forward.setRedirect(true);
	            forward.setPath("main.do");

			}else {
				out.println("<script>");
				out.println("alert('비밀번호 수정 실패')");
				out.println("history.back()");
				out.println("</script>");
			}
		}else {
			out.println("<script>");
			out.println("alert('없는 회원입니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
		return forward;
	}
	
	//네이버로 메일을 보내는 메서드.(smtp) /WEB-INF/lib에 mail-1.4.7.jar 필요. 
	public void navermailSend(String new_mem_pwd, String mem_email) {
		String host = "smtp.naver.com";
        String user = "4th-project@naver.com"; // 4th-project로 새로만든 naver 아이디 
        String password = "123!@#456!@#";   // 패스워드
        // SMTP 서버 정보를 설정한다.
        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.trust", "smtp.naver.com");

        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mem_email)); 
            // Subject
            message.setSubject("4조_중고도서"); //메일 제목을 입력
            // Text
            message.setContent("새로운 비밀번호 : "+new_mem_pwd,"text/html; charset=UTF-8");    //메일 내용을 입력
            // send the message
            Transport.send(message); ////전송
            System.out.println("message sent successfully...");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	//새로운 비밀번호로 새로운 20자리 무작위 문자열을 만드는 메서드.
	public String randomString() {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 20; i++) {
		    int rIndex = rnd.nextInt(3);
		    switch (rIndex) {
		    case 0:
		        // a-z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:
		        // A-Z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:
		        // 0-9
		        temp.append((rnd.nextInt(10)));
		        break;
		    }
		}
		return temp.toString();
	}
}