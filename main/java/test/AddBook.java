package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addlink")
public class AddBook extends HttpServlet{
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
		String name=req.getParameter("name");
		String auther=req.getParameter("auther");
		String price2=req.getParameter("price");
		String stock2=req.getParameter("stock");
		
		double price1=Double.parseDouble(price2);
		int stock1=Integer.parseInt(stock2);
		
		PreparedStatement pstmt=null;
		String query="insert into book_store(book_name,auther_name,book_price,book_stock) values(?,?,?,?)";
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, auther);
			pstmt.setDouble(3, price1);
			pstmt.setInt(4, stock1);
			int count=pstmt.executeUpdate();
			PrintWriter pw=resp.getWriter();
			pw.print("<h1>Inserted Book Successfully"+count+"</h1>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
