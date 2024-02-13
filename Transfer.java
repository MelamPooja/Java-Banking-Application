import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Transfer
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transfer() {
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
		String tacno=request.getParameter("acno");
		Double amount=Double.parseDouble(request.getParameter("amount"));
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","edb","edb");
			PreparedStatement ps1=con.prepareStatement("select * from accounts where acno=? ");
			ps1.setString(1, acno);
          ResultSet rs=ps1.executeQuery();
          double orginalamt=0.0;
          if(rs.next())
          {
        	  orginalamt=rs.getDouble("amount");  
          }
          out.print("my original balance is="+orginalamt+"<br>");
          out.print("my transfer amount is="+amount+"<br>");
		PreparedStatement ps2=con.prepareStatement("select amount from accounts where acno=?");
		ps2.setString(1,acno);
		ResultSet rs1=ps2.executeQuery();
		double tamt=0.0;
		if(rs1.next())
		{
			tamt=rs1.getDouble("amount");
		}
		out.print("target account original balance is:"+tamt);
		double amt=tamt+amount;
		PreparedStatement ps3=con.prepareStatement("update accounts set amount=? where acno=?");
		ps3.setDouble(1,amount);
		ps3.setString(2,tacno);
		ps3.executeUpdate();
		out.print("after transfer target accounts bal is:"+amt);
		if(amount>orginalamt)
		{
			out.print("Transfer the amount whithin the orginalamt");
		}
		else {
			 double amount1=orginalamt-amount;
			PreparedStatement ps4=con.prepareStatement("update accounts set amount=?where acno=?and aname=?and apsw=?");
			ps4.setDouble(1, amount);
			ps4.setString(2,acno);
			ps4.setString(3, aname);
			ps4.setString(4, apsw);
			ps4.executeUpdate();
			out.print("after transfer my account bal is:"+amount1);
		}
		}
		catch (Exception ex) {
			out.print(ex);
		}
	}
}