<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Http Test</title>
</head>
<body>
	<%
		String result = request.getParameter("par");
		out.println("<h1>parameters:" + result + "</h1>");
	%>
</body>
</html>