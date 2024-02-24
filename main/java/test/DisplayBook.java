package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Display")
public class DisplayBook extends HttpServlet {

	Connection con;
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/1ejm9?user=root&password=tiger");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Statement stmt=null;
		ResultSet rs=null;
		
		PrintWriter pw=resp.getWriter();
		String query="select * from book_store";
		int count;
		try {
			stmt=con.createStatement();
			rs = stmt.executeQuery(query);
			pw.print("<table border='2'>");
			pw.print("<tr>");
			pw.print("<th>BOOK ISBN</th>");
			pw.print("<th>BOOk NAME </th>");
			pw.print("<th>AUTHER NAME</th> ");
			pw.print("<th>BOOK PRICE</th>");
			pw.print("<th>BOOK STOCK</th>");
			pw.print("</tr");
			while(rs.next())
			{
				pw.print("<tr>");
				pw.print("<td>"+rs.getInt(1)+"</td>");
				pw.print("<td>"+rs.getString(2)+"</td>");
				pw.print("<td>"+rs.getString(3)+"</td>");
				pw.print("<td>"+rs.getDouble(4)+"</td>");
				pw.print("<td>"+rs.getInt(5)+"</td>");
				pw.print("</tr>");
				
			}
			pw.print("</table>");
//			pw.print("<h1>"+count+" Display record</h1>");

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
