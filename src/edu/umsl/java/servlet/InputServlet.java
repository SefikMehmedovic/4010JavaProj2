package edu.umsl.java.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.umsl.java.bean.Problem;

/**
 * Servlet implementation class InputServlet
 */
@WebServlet("/InputServlet")
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private PreparedStatement results;
	private PreparedStatement newprob;
	private PreparedStatement maxpid;
	private PreparedStatement maxorder;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InputServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			connection = DriverManager.getConnection("jdbc:derby://localhost:1527/mathprobs", "nbuser", "nbuser");

			results = connection.prepareStatement("SELECT pid, content, order_num, category_id " + "FROM math_list ORDER BY pid");
			
			maxpid = connection.prepareStatement("SELECT MAX(pid) FROM math_list");
			
			maxorder = connection.prepareStatement("SELECT MAX(order_num) FROM math_list");
			
			newprob = connection.prepareStatement("INSERT INTO math_list (pid, content, order_num, category_id) VALUES (?,?,?,?)");
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		ServletContext ctx = this.getServletContext();
		
		String category = request.getParameter("category");
		String problem = request.getParameter("prob");
		
		System.out.println(category);
		System.out.println(problem);
		
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			connection = DriverManager.getConnection("jdbc:derby://localhost:1527/mathprobs", "nbuser", "nbuser");
			results = connection.prepareStatement("SELECT ppid, content " + "FROM practice_problem ORDER BY ppid");
			
			//Statement statement = connection.createStatement();
			//int executeUpdate = statement.executeUpdate("insert into");
			
			//connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new UnavailableException(e.getMessage());
		}
	}*/
	
	//int alg = 0, geo = 0, trig = 0;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String category = request.getParameter("category");
		String problem = request.getParameter("prob");
		int cat_num = 0;
		int alg = 0, geo = 0, trig = 0;
		
		System.out.println(category);
		System.out.println(problem);
		
		boolean fill = false;
		
		//while(fill == false) {
			if(category.equals("Algebra"))
				cat_num = 1;
			else if(category.equals("Geometry"))
				cat_num = 2;
			else if(category.equals("Trigonometry"))
				cat_num = 3;
		
		List<Problem> problist = new ArrayList<Problem>();

		try {
			ResultSet maxpidRS = maxpid.executeQuery();
			
			maxpidRS.next();
			
			int mymaxpid = maxpidRS.getInt(1);
			
			ResultSet maxorderRS = maxorder.executeQuery();
			
			maxorderRS.next();
			
			int mymaxorder = maxorderRS.getInt(1);
			
			newprob.setInt(1, mymaxpid + 1);
			newprob.setString(2, problem);
			newprob.setInt(3, mymaxorder + 1);
			newprob.setInt(4, cat_num);
			
			newprob.execute();
			
//			ResultSet resultsRS = results.executeQuery();
//
//			while (resultsRS.next()) {
//				Problem prob = new Problem();
//
//				prob.setCid(resultsRS.getInt(1));
//				prob.setContent(resultsRS.getString(2));
//				
//				if(category == resultsRS.getString(2)) {
//					System.out.println(resultsRS.getString(2));
//				}
//				System.out.println(resultsRS.getString(2));
//				
//				problist.add(prob);
//			}
//
//			request.setAttribute("problist", problist);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("input.jsp");
		
		dispatcher.forward(request, response);
		
	}
	
	public void destroy() {
		try {
			results.close();
			connection.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

}
