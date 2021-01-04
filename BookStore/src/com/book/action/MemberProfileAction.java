package com.book.action;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.MemberDAO;
import com.book.model.MemberDTO;

public class MemberProfileAction implements Action  {
	
	//네이버로 로그인 시  해당 아이디의 회원 정보를 얻어서 저장하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int res = 0;
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html; charset=UTF-8");
    	
    	//네이버에 요청할 정보를 담은 문자열.
        String token = request.getParameter("accesstoken"); // 네이버 로그인 접근 토큰;
        String header = "Bearer " + token; 					// Bearer 다음에 공백 추가
        
        String apiURL = "https://openapi.naver.com/v1/nid/me";	//회원 프로필이 넘어오는 url

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);		
        
        //responseBody = accesstoken으로 얻어온 회원 정보가 담긴 json 문자열.
        String responseBody = get(apiURL,requestHeaders);		//회원 프로필 요청
        
    	JSONParser parser = new JSONParser();
    	ActionForward forward = new ActionForward();
    	
    	//WEB-INF/lib에 json-simple-1.1.1.jar 필요.
    	JSONObject object = null;
		
    	try {
			object = (JSONObject)parser.parse(responseBody);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject object_response = (JSONObject)object.get("response");
		
		//받은 json에서 회원 정보 저장.
    	String mem_name = (String)object_response.get("name");
    	String mem_age = (String)object_response.get("age");
    	String mem_nickname = (String)object_response.get("nickname");
    	String mem_gender = (String)object_response.get("gender");
    	String mem_email = (String)object_response.get("email");
    	String mem_birth = (String)object_response.get("birthday");
    	String mem_id = (String)object_response.get("id");
    	
    	MemberDTO dto = new MemberDTO();
    	dto.setMem_name(mem_name);
    	dto.setMem_age(mem_age);
    	dto.setMem_nickname(mem_nickname);
    	dto.setMem_gender(mem_gender);
    	dto.setMem_email(mem_email);
    	dto.setMem_birth(mem_birth);
    	dto.setMem_id(mem_id);
    	
    	MemberDAO dao = MemberDAO.getInstance();
    	
    	//회원 아이디가 이미 DB에 있는 지 확인 하는 함수(searchId)
    	int result = dao.searchId(mem_id);
    	
    	if(result ==0) {			//아이디가 없는 경우
    		//회원가입 하는 메서드.
    		res = dao.insertMember(dto);
    		if(res>0) {		//네이버로 회원가입이 성공한 경우
    			
        		HttpSession session = request.getSession();
        		session.setAttribute("session_mem_name", mem_name);
        		session.setAttribute("session_mem_age", mem_age);
        		session.setAttribute("session_mem_nickname", mem_nickname);
        		session.setAttribute("session_mem_gender", mem_gender);
        		session.setAttribute("session_mem_email", mem_email);
        		session.setAttribute("session_mem_birth", mem_birth);
        		session.setAttribute("session_mem_id", mem_id);
        		//세션에 해당 정보를 모두 올려놓음. 그 뒤 메인 페이지로 이동
        		forward.setRedirect(false);
                forward.setPath("main.do");
                
    		}else {			//네이버로 회원가입이 실패한 경우
        		PrintWriter out = response.getWriter();
        		out.println("<script>");
        		out.println("alert('회원가입 실패')");
        		out.println("history.back()");
        		out.println("</script>");
        	}
    	}else {		//아이디가 있는 경우 
			HttpSession session = request.getSession();
			session.setAttribute("session_mem_name", mem_name);
			session.setAttribute("session_mem_age", mem_age);
			session.setAttribute("session_mem_nickname", mem_nickname);
			session.setAttribute("session_mem_gender", mem_gender);
			session.setAttribute("session_mem_email", mem_email);
			session.setAttribute("session_mem_birth", mem_birth);
			session.setAttribute("session_mem_id", mem_id);
			
			//메인 페이지로 이동
			forward.setRedirect(true);
			forward.setPath("main.do");
    	}
        
		return forward;
    }
	
	//회원 프로필 요청 클래스
    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }
   
    //apiUrl로 연결하는 함수.
    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }
    
    //받은 정보를 문자열로 치환해서 받는 메서드.
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}

