package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatelink")
public class UpdateBook extends HttpServlet {
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String isbn=req.getParameter("isbn");
		String name=req.getParameter("name");
		String auther=req.getParameter("auther");
		String price1=req.getParameter("price");
		String stock1=req.getParameter("stock");
		
		int isbn2=Integer.parseInt(isbn);
		double price2=Double.parseDouble(price1);
		int stock2=Integer.parseInt(stock1);
		
		PreparedStatement pstmt;
		
		String query="update book_store set book_name=?, auther_name=?, book_price=?, book_stock=? where isbn=?";
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(5, isbn2);
			pstmt.setString(1, name);
			pstmt.setString(2, auther);
			pstmt.setDouble(3, price2);
			pstmt.setInt(4, stock2);
			
			int count=pstmt.executeUpdate();
			PrintWriter pw=resp.getWriter();
			pw.print("<h1>"+count+ "Record Update Successfully</h1>");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
