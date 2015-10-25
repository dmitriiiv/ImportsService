<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include/head.jsp" %>
<html>
<head>
<%@ include file="include/style.jsp" %>
<title><fmt:message key="entries.title" /></title>
<style>
	.row .col-md-8 {
		padding-top: 50px;
	}
</style>
</head>
<body>
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<table class="table table-striped article">
	        		<tr>
	        			<th>
	        				<a href="controller?command=page&page=1&orderBy=name">
	        					<fmt:message key="entries.name.title" />
	        				</a>
	        			</th>
	        			<th>
	        				<a href="controller?command=page&page=1&orderBy=surname">
	        					<fmt:message key="entries.surname.title" />
	        				</a>
	        			</th>
	        			<th>
	        				<a href="controller?command=page&page=1&orderBy=login">
	        					<fmt:message key="entries.login.title" />
	        				</a>
	        			</th>
	        			<th>
	        				<a href="controller?command=page&page=1&orderBy=email">
	        					<fmt:message key="entries.email.title" />
	        				</a>
	        			</th>
	        			<th>
	        				<a href="controller?command=page&page=1&orderBy=phone">
	        					<fmt:message key="entries.phone.title" />
	        				</a>
	        			</th>
	        		</tr>
	        		<c:forEach var="entry" items="${requestScope.entries}">
	        		<tr>
	        			<td>
	                    		${entry.name}
	        			</td>
	        			<td>
	        					${entry.surname} 
	        			</td>
	        			<td>
	        					${entry.login} 
	        			</td>
	        			<td>
	        					${entry.email} 
	        			</td>
	        			<td>
	        					${entry.phoneNumber} 
	        			</td>
	        		</tr>
	            	</c:forEach>
	        	</table>
	        	<div class="row">
            		<div class="col-md-2">
               			 <form action="controller" method="post">
                    		<input type="hidden" name="command" value="page">
                    		<input type="hidden" name="button" value="prev">
                    		<input type="hidden" name="page" value="${requestScope.page}">
                    		<input type="hidden" name="orderBy" value="${requestScope.orderBy}">
                    		<button type="submit" class="btn btn-block btn-default" ${requestScope.isActivePrev}><fmt:message
                            key="entries.button.prev"/></button>
                		</form>
            		</div>
            		<div class="col-md-3"></div>
            		<div class="col-md-2">
            			<form action="controller" method="post">
                   			<input type="hidden" name="command" value="add_entries">
              				<button type="submit" class="btn btn-block btn-default"><fmt:message
                            key="menu.button.import"/></button>
                		</form>
            		</div>
            		<div class="col-md-3"></div>
            		<div class="col-md-2">
                		<form action="controller" method="post">
                    		<input type="hidden" name="command" value="page">
                    		<input type="hidden" name="button" value="next">
                    		<input type="hidden" name="page" value="${requestScope.page}">
                    		<input type="hidden" name="orderBy" value="${requestScope.orderBy}">
                    		<button type="submit" class="btn btn-block btn-default" ${requestScope.isActiveNext}><fmt:message
                            key="entries.button.next"/></button>
                		</form>
            		</div>
        		</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
</body>
</html>