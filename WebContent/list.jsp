<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="edu.umsl.java.bean.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript"
	src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
</script>

<script type="text/javascript" src="js/MathBank.js"></script>
<script type="text/javascript">
	window.MathJax = {
		tex2jax : {
			inlineMath : [ [ '$', '$' ], [ "\\(", "\\)" ] ],
			processEscapes : true
		}
	};
</script>
<title>Math Question Bank</title>
</head>
<body>
	<input id="crtpg" type="hidden" value="${crtpg}" />
	<%
		List<Problem> myproblist = (List<Problem>) request.getAttribute("problist");

		int maxodr = (Integer) request.getAttribute("maxordernum");
	%>
	<div class="container">
		<div class="row">
			<div class="col-md-offset-2 col-md-8">
				<table width="100%" class="table table-bordered table-striped">
					<tr>
						<td colspan="3">
							<table width="100%">
								<tr>
									<td width="70%"></td>
									<td width="20%">
										<div class="input-group">
											<input id="probpage" type="text" class="form-control" style="width:5em"
												placeholder="${crtpg}/${maxpg}">
											<button type="button" onclick="goToProblemsAtPage()" style="height:2.4em"
												class="btn btn-default btn-md">
												<span class="glyphicon glyphicon-share-alt"></span>
											</button>
										</div>
									</td>
									<td width="10%">
										<div class="input-group-btn">
											<c:if test="${crtpg > 1}">
												<button type="button" onclick="loadProblemsAtPage(0)" style="height:2.4em"
													class="btn btn-default">
													<span class="glyphicon glyphicon-triangle-left"></span>
												</button>
											</c:if>
											<c:if test="${crtpg < maxpg}">
												<button type="button" onclick="loadProblemsAtPage(1)" style="height:2.4em"
													class="btn btn-default">
													<span class="glyphicon glyphicon-triangle-right"></span>
												</button>
											</c:if>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<%
						for (Problem prob : myproblist) {
					%>
					<tr>
						<td width="8%" class="text-center"><%=prob.getPid()%></td>
						<td width="84%"><%=prob.getContent()%></td>
						<td width="8%">
							<table>
								<%
									if (prob.getOrder_num() < maxodr) {
								%>
								<tr>
									<td><button type="button" class="btn btn-default btn-md" style="height:2.4em"
											onclick="moveProbOrder(1,<%=prob.getPid()%>)">
											<span class="glyphicon glyphicon-arrow-up"></span>
										</button></td>
								</tr>
								<%
									}

										if (prob.getOrder_num() > 1) {
								%>
								<tr>
									<td><button type="button" class="btn btn-default btn-md" style="height:2.4em"
											onclick="moveProbOrder(0,<%=prob.getPid()%>)">
											<span class="glyphicon glyphicon-arrow-down"></span>
										</button></td>
								</tr>
								<%
									}
								%>
							</table>
						</td>
					</tr>
					<%
						}
					%>
					<tr>
						<td colspan="3">
							<table width="100%">
								<tr>
									<td width="70%" class="text-center"></td>
									<td width="20%">
										<div class="input-group">
											<input id="probpage2" type="text" class="form-control" style="width:5em"
												placeholder="${crtpg}/${maxpg}">
											<button type="button" onclick="goToProblemsAtPage()" style="height:2.4em"
												class="btn btn-default btn-md">
												<span class="glyphicon glyphicon-share-alt"></span>
											</button>
										</div>
									</td>
									<td width="10%">
										<div class="input-group-btn">
											<c:if test="${crtpg > 1}">
												<button type="button" onclick="loadProblemsAtPage(0)" style="height:2.4em"
													class="btn btn-default">
													<span class="glyphicon glyphicon-triangle-left"
														aria-label="Left"></span>
												</button>
											</c:if>
											<c:if test="${crtpg < maxpg}">
												<button type="button" onclick="loadProblemsAtPage(1)" style="height:2.4em"
													class="btn btn-default">
													<span class="glyphicon glyphicon-triangle-right"
														aria-label="Right"></span>
												</button>
											</c:if>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>