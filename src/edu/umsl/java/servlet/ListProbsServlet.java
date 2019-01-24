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
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.umsl.java.bean.Problem;

/**
 * Servlet implementation class ListProbsServlet
 */
@WebServlet("/listProbs")
public class ListProbsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PreparedStatement results;
	private ServletRequest request;
	
	//String category = request.getParameter("category");
	//String problem = request.getParameter("prob");
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			connection = DriverManager.getConnection("jdbc:derby://localhost:1527/mathprobs", "nbuser", "nbuser");

			results = connection.prepareStatement("SELECT ppid, content " + "FROM practice_problem ORDER BY ppid");
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}
	
	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			results.close();
			connection.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Problem> problist = new ArrayList<Problem>();

		try {
			ResultSet resultsRS = results.executeQuery();

			while (resultsRS.next()) {
				Problem prob = new Problem();

				prob.setPid(resultsRS.getInt(1));
				prob.setContent(resultsRS.getString(2));

				problist.add(prob);
			}

			request.setAttribute("problist", problist);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");

		dispatcher.forward(request, response);

	}

}


