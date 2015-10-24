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
				<form action="controller" method="post">
					<input type="hidden" name="command" value="import_file">
					<input type="file" name="file_field">
					<button type="submit" class="btn btn-block btn-default"><fmt:message
                            key="addition.button.import"/></button>
				</form>
			</div>
			<div class="col-md-5"></div>
		</div>
	</div>
</body>
</html>