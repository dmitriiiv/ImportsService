<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include/head.jsp" %>
<html>
<head>
<%@ include file="include/style.jsp" %>
<title><fmt:message key="addition.title" /></title>
<style>
	.row .col-md-2 {
		padding-top: 50px;
	}
</style>
</head>
<body>
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-5"></div>
			<div class="col-md-2">
				<form action="upload" method="post" enctype="multipart/form-data">
					<input type="file" name="fileName" required>
					<p style="color: #ff0000;">${requestScope.errorMessage}</p>
					<button type="submit" class="btn btn-block btn-default"><fmt:message
                            key="addition.button.import"/></button>
				</form>
			</div>
			<div class="col-md-5"></div>
		</div>
	</div>
</body>
</html>