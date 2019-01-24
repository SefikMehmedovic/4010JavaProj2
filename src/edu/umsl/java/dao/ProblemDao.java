package edu.umsl.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.UnavailableException;

import edu.umsl.java.bean.Problem;

public class ProblemDao {
	private Connection connection;
	private PreparedStatement results;
	private PreparedStatement maxordernum;
	private PreparedStatement setorder;
	private PreparedStatement getorder;
	private PreparedStatement getpid;
	private PreparedStatement probcnt;
	
	public ProblemDao() throws Exception {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			connection = DriverManager.getConnection("jdbc:derby://localhost:1527/mathprobs", "nbuser", "nbuser");

			results = connection.prepareStatement(
					"SELECT pid, content, order_num " + "FROM problem WHERE del = 0 ORDER BY order_num DESC");
			
			maxordernum = connection.prepareStatement("SELECT MAX(order_num) FROM problem WHERE del = 0");
			
			setorder = connection.prepareStatement("UPDATE problem SET order_num = ? WHERE pid = ? AND del = 0");
					
			getorder = connection.prepareStatement("SELECT order_num FROM problem WHERE pid = ? AND del = 0");
					
			getpid = connection.prepareStatement("SELECT pid FROM problem WHERE order_num = ? AND del = 0");
			
			probcnt = connection.prepareStatement("SELECT COUNT(pid) FROM problem WHERE del = 0");
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}
	
	public int getProblemCount() {
		int cnt = 0;
		
		try {
			ResultSet rs = probcnt.executeQuery();
			rs.next();
			
			cnt = rs.getInt(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return cnt;
	}
	
	public List<Problem> getProblemList() throws SQLException {
		List<Problem> problist = new ArrayList<Problem>();
		
		try {
			ResultSet resultsRS = results.executeQuery();

			while (resultsRS.next()) {
				Problem prob = new Problem();

				prob.setPid(resultsRS.getInt(1));
				prob.setContent(resultsRS.getString(2));
				prob.setOrder_num(resultsRS.getInt(3));

				problist.add(prob);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return problist;
	}
	
	public List<Problem> getProblemListByPage(int pg) throws SQLException {
		List<Problem> problist = new ArrayList<Problem>();
		
		int st = 10 * (pg - 1);
		
		try {
			// results.setInt(1, st);
			// results.setInt(2, 10);
			
			ResultSet resultsRS = results.executeQuery();

			while (resultsRS.next()) {
				Problem prob = new Problem();

				prob.setPid(resultsRS.getInt(1));
				prob.setContent(resultsRS.getString(2));
				prob.setOrder_num(resultsRS.getInt(3));

				problist.add(prob);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return problist;
	}
	
	public int getMaxOrderNum() {
		int maxodr = 0;
		
		try {
			ResultSet maxodrRS = maxordernum.executeQuery();
			maxodrRS.next();
			
			maxodr = maxodrRS.getInt(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return maxodr;
	}
	
	public int getProblemOrderById(int pid) {
		int myodr = 0;
		
		try {
			getorder.setInt(1, pid);
			
			ResultSet rs = getorder.executeQuery();
			rs.next();
			
			myodr = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return myodr;
	}
	
	public int getProblemIdByOrder(int odr) {
		int mypid = 0;
		
		try {
			getpid.setInt(1, odr);
			
			ResultSet rs = getpid.executeQuery();
			rs.next();
			
			mypid = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mypid;
	}
	
	public void setProblemOrderById(int pid, int odr) {
		
		try {
			setorder.setInt(1, odr);
			setorder.setInt(2, pid);
			
			setorder.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void finalize() {
		try {
			results.close();
			maxordernum.close();
			getorder.close();
			getpid.close();
			setorder.close();
			connection.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

}

