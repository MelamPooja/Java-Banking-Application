

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Withdraw
 */
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Withdraw() {
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
		double camt=Double.parseDouble(request.getParameter("amount"));
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","edb","edb");
			PreparedStatement ps=con.prepareStatement("select amount from  accounts where acno=? and aname=? and apsw=?");
			ps.setString(1, acno);
			ps.setString(2, aname);
			ps.setString(3, apsw);
			ResultSet rs=ps.executeQuery();
			double origin_amt=0.0;
			if(rs.next()) {
				origin_amt=rs.getDouble("amount");
			}
			out.println("My original balance ="+origin_amt+"<br>");
				double amount=origin_amt-camt;
				PreparedStatement ps1=con.prepareStatement("update accounts set amount=? where acno=?");
				ps1.setDouble(1, amount);
				ps1.setString(2, acno);
				int i=ps1.executeUpdate();
				out.println(i);
				out.println("my withdrawal amt is  ="+camt+"<br>");
				out.println("my original balance is"+amount+"<br>");
		}catch(Exception e) {
			out.print(e);
		}
	}

}
