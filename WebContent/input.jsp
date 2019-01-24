<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mathematics Questions</title>
<script type="text/javascript" src="js/math.js"></script>

<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 40%;
}

td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}
</style>
</head>
<body>


	<h3>Enter a Mathematics Question and Select a Category:</h3>
	<form action="InputServlet" method="post">
		<input type="text" name="prob"></input> <select name="category">
		<br>
			<option value="Algebra">Algebra</option>
			<option value="Geometry">Geometry</option>
			<option value="Trigonometry">Trigonometry</option>
		</select> <input type="submit" value="Assign">
	</form>

	<%
		String category = (String) request.getAttribute("category");
		String problem = (String) request.getAttribute("prob");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String mycid = request.getParameter("cid");
		int cid = 0;

		if (mycid != null) {
			try {
				cid = Integer.parseInt(mycid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		int cat_id = -1;
	%>
	<hr />
	<form id="cat">
	<select  onchange="listProbsByCategory(this.value)">
	    <option value="0">All Categories</option>
		<%
	  String[] catArr = new String[3];
		
	  catArr[0] = "Algebra";
	  catArr[1] = "Geometry";
	  catArr[2] = "Trigonometry";
	
	  for (int i = 0; i < 3; i++) {
		  if (i+1 == cid) {
         %>
		<option  value="<%= i+1 %>" selected="selected"><%= catArr[i] %></option>
		<%
		  } else {
		%>
		<option  value="<%= i+1 %>"><%= catArr[i] %></option>
		
		
		
		<%  
		  }
        } %>
	</select>
	</form>
	<br></br>
	
	<table>
		<tr>
			<td width="17%">ID</td>
			<td width="25%">Category</td>
			<td width="25%">Problem</td>
		</tr>
	</table>

	<table>
		<%
			try {
				Class.forName("org.apache.derby.jdbc.ClientDriver");
				conn = DriverManager.getConnection("jdbc:derby://localhost:1527/mathprobs", "nbuser", "nbuser");
				stmt = conn.createStatement();
				
				String query = "";

				if (cid == 0) {
					query = "SELECT * FROM math_list";
				} else {
					query = "SELECT * FROM math_list WHERE category_id=" + cid;
				}
				rs = stmt.executeQuery(query);
				while (rs.next()) {
		%>

		<tr>
			<td width="17%">
				<%
					out.println(rs.getInt(1));
				
				%>
				
			</td>
			<td width="25%">
			<%
					if(rs.getInt(4)==1)
					{
						out.println("Algebra");
					}
					
					else if(rs.getInt(4)==2)
					{
						out.println("Geometry");
					}
					
					else if(rs.getInt(4)==3)
					{
						out.println("Trigonometry");
					}
					else
					{
						out.println("other");
					}
					
			%>
			
			
			</td>
			<td width="25%">
				<%
					
					out.println(rs.getString(2));
				%>
				
			</td>
		</tr>

		<%
			}
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		%>
	</table>

</body>
</html>

