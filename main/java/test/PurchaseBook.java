package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/purchaselink")
public class PurchaseBook extends HttpServlet {

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
		String qty=req.getParameter("qty");
		
		int isbn2=Integer.parseInt(isbn);
		int qty2=Integer.parseInt(qty);
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		PrintWriter pw=resp.getWriter();
		
		String query="select book_stock,book_price from book_store where isbn=?";
		String query1="update book_store set book_stock=? where isbn=?";
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, isbn2);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				int dbstock=rs.getInt(1);
				double dbPrice=rs.getDouble(2);
				if(qty2<=dbstock) {
					double total=qty2*dbPrice;
					pw.print("<h1>Total Amount is "+total+"<h1>");
					pstmt=con.prepareStatement(query1);
					pstmt.setInt(1, dbstock-qty2);
					pstmt.setInt(2, isbn2);
					pstmt.executeUpdate();
			}
				else {
					pw.print("<h1>ITEM OUT OF STOCK</h1>");
				}
			}
			else
			{
				pw.print("<h1>ITEM NOT FOUND</h1>");
			}
			}
			catch(SQLException e) {
				e.printStackTrace();
			
			
					
				}
	}
}
