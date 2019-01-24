package edu.umsl.java.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.umsl.java.dao.ProblemDao;

/**
 * Servlet implementation class SwitchOrderServlet
 */
@WebServlet("/switchorder")
public class SwitchOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProblemDao probdao = null;
		
		try {
			int dir = Integer.parseInt(request.getParameter("dir"));
			int pid = Integer.parseInt(request.getParameter("pid"));
			
			probdao = new ProblemDao();
			
			int crtodr = probdao.getProblemOrderById(pid);
			
			if (dir == 0) { // down
				int pid2 = probdao.getProblemIdByOrder(crtodr - 1);
				
				probdao.setProblemOrderById(pid2, crtodr);
				probdao.setProblemOrderById(pid, crtodr - 1);
				
				crtodr--;
			} else { // up
				int pid2 = probdao.getProblemIdByOrder(crtodr + 1);
				
				probdao.setProblemOrderById(pid2, crtodr);
				probdao.setProblemOrderById(pid, crtodr + 1);
				
				crtodr++;
			}
			
			int mxodr = probdao.getMaxOrderNum();
			
			int myodr = mxodr + 1 - crtodr;
			
			int mypg = (int) Math.ceil(myodr / 10.0);
			
			response.sendRedirect("listmath?pg=" + mypg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			response.sendRedirect("listmath");
		}
	}

}

