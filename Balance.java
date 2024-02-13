

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Balance
 */
@WebServlet("/Balance")
public class Balance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Balance() {
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
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","edb","edb");
			PreparedStatement ps=con.prepareStatement("select * from accounts where acno=? and aname=? and apsw=?");
			ps.setString(1, acno);
			ps.setString(2, aname);
			ps.setString(3, apsw);
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			out.print("<table border='1'>");
			int n=rsmd.getColumnCount();
			for(int i=1;i<=n;i++) 
				out.println("<th> <font color=blue size=3>"+"<br>"+rsmd.getColumnName(i));
			out.println("<tr>");
				while(rs.next()) 
				{
					for( int i=1;i<=n;i++) 
					{
						out.println("<td><br>"+rs.getString(i));
					}
					out.println("<tr>");
				}
			out.print("</table>");
			con.close();
		}
		catch(Exception ex) {
			out.print(ex);
		}
	}

}
