package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.CategoryDAO;
import com.book.model.CategoryDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GetSubCategoryAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int cate_code = Integer.parseInt(request.getParameter("select").trim());
		
		CategoryDAO dao = CategoryDAO.getInstance();
		
		List<CategoryDTO> list = dao.SubCateList(cate_code);
		
		JsonArray arr = new JsonArray();
		
		for(int i = 0; i < list.size(); i++) {
			
			CategoryDTO dto = list.get(i);
			
			JsonObject job = new JsonObject();
			
			job.addProperty("cate_name", dto.getCate_name());
			job.addProperty("cate_code", dto.getCate_code());
			
			arr.add(job);
		}
		
		PrintWriter out = response.getWriter();
		
		out.println(arr);
		out.close();
		
		return null;
	}

}
