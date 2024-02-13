

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Account
 */
@WebServlet("/Account")
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Account() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String acno=request.getParameter("acno");
		String aname=request.getParameter("aname");
		String apsw=request.getParameter("apsw");
		String cpsw=request.getParameter("cpsw");
		double amount=Double.parseDouble(request.getParameter("amount"));
		String address=request.getParameter("address");
		 long mblnumber=Long.parseLong(request.getParameter("mblnumber"));
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","edb","edb");
			PreparedStatement ps=con.prepareStatement("insert into accounts values(?,?,?,?,?,?,?)");
			ps.setString(1, acno);
			ps.setString(2, aname);
			ps.setString(3, apsw);
			ps.setString(4, cpsw);
			ps.setDouble(5, amount);
			ps.setString(6, address);
			ps.setLong(7, mblnumber);
			
			int i=ps.executeUpdate();
			out.print(i+"   registered successfully...");
		}catch(Exception e) {
			out.print(e);
		}		
	}
}
