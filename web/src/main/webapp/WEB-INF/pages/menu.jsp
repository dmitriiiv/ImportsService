<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include/head.jsp" %>
<html>
<head>
<%@ include file="include/style.jsp" %>
<title><fmt:message key="menu.title" /></title>
<style>
	.row .col-md-2 {
		padding-top: 40px;
	}
</style>
</head>
<body>
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-2">
                <form action="controller" method="post">
                   	<input type="hidden" name="command" value="entries">
                   	<button type="submit" class="btn btn-block btn-default"><fmt:message
                            key="menu.button.entries"/></button>
                </form>
            </div>
            <div class="col-md-2">
                <form action="controller" method="post">
                   	<input type="hidden" name="command" value="add_entries">
              		<button type="submit" class="btn btn-block btn-default"><fmt:message
                            key="menu.button.import"/></button>
                </form>
            </div>
			<div class="col-md-4"></div>
		</div>
	</div>
</body>
</html>