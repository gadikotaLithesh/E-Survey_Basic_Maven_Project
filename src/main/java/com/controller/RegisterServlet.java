package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		String umobile=request.getParameter("contact");
		String ReUpwd=request.getParameter("re_pass");
		RequestDispatcher dispatcher =null;
		if(uname == null || uname.equals("")) {
			request.setAttribute("status","invalidName");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		if(uemail == null || uemail.equals("")) {
			request.setAttribute("status","invalidEmail");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		if(upwd == null || upwd.equals("")) {
			request.setAttribute("status","invalidUpwd");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}else if(!upwd.equals(ReUpwd)){
			request.setAttribute("status","invalidConfirmPassword");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		if(umobile == null || umobile.equals("")) {
			request.setAttribute("status","invalidMobile");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}else if(umobile.length()>10){
			request.setAttribute("status","invalidMobileLength");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			
		}else if(umobile.length()<10){
			request.setAttribute("status","invalidMobileLength");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/user_details?useSSL=false","root","root");
			PreparedStatement pst =con.prepareStatement("insert into users(uname,upwd,uemail,umobile) values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, umobile);
			
			int rowCount = pst.executeUpdate();
			
			if(rowCount >0)
			{
				request.setAttribute("status","success");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			else {
				request.setAttribute("status","failed");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				
			}
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}

}
