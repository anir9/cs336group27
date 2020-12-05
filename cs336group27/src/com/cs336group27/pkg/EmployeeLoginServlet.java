package com.cs336group27.pkg;

import com.cs336group27.model.*;
import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class EmployeeLoginServlet
 */
@WebServlet("/EmployeeLoginServlet")
public class EmployeeLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApplicationDB appDB = new ApplicationDB();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		Employee emp = new Employee();
		emp.setEmployeeID(Integer.parseInt(request.getParameter("eid")));
		emp.setName(request.getParameter("uname"));
		emp.setPassword(request.getParameter("psw"));
		int checkLogin=appDB.employeeLoginCheck(emp.getName(), emp.getPassword(),emp.getEmployeeID());
		if (checkLogin == 1) {
			HttpSession session = request.getSession();
			Integer count = (Integer)session.getAttribute("COUNT");
			if(count == null) {
				count = new Integer (1);
				session.setAttribute("COUNT", count);
				session.setAttribute("employee",emp);
				RequestDispatcher rd = request.getRequestDispatcher("employeeHomepage.jsp");
				rd.forward(request, response);
			}else {
				String message = "Login Failed. Employee is Already Signed In.";
				request.setAttribute("message", message);
				RequestDispatcher rd = request.getRequestDispatcher("employeeLogin.jsp");
				rd.forward(request, response);
			}
			
		}else {
			String message = "Login Failed. Invalid employeeeID, username, or password.";
			request.setAttribute("message", message);
			RequestDispatcher rd = request.getRequestDispatcher("employeeLogin.jsp");
			rd.forward(request, response);
		}
	}

}
