package com.book.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FrontController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset = utf-8");
		
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		
		String command = uri.substring(path.length()+1);
		
		Action action = null;
		ActionForward forward = null;
		
		Properties prop = new Properties();

		FileInputStream fis = new FileInputStream("C:\\Users\\yyj01\\OneDrive\\문서\\GitHub\\BookShop\\BookShop\\src\\com\\book\\controller\\mapping.properties");
		
		prop.load(fis);
		
		String value = prop.getProperty(command);
		
		System.out.println("value >> " + value);
	
		if(value.substring(0, 7).equals("execute")) {
			
			StringTokenizer st = new StringTokenizer(value, "|");
			String url_1 = st.nextToken();
			String url_2 = st.nextToken(); 
		
			try {
				Class url = Class.forName(url_2);
				
				action = (Action)url.newInstance();
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}else {	
			forward = new ActionForward();
			forward.setRedirect(false);		
			forward.setPath(value);
		}
		
		if(forward != null) {
			if(forward.isRedirect()) {	
				response.sendRedirect(forward.getPath());
			}else{
				RequestDispatcher rd = request.getRequestDispatcher(forward.getPath());
				rd.forward(request, response);
			}
		}
	}
}
