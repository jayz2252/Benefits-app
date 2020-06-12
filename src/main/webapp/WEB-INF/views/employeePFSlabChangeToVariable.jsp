<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<body>
	<%@include file="employeeNavBar.jsp"%>

	<div id="main">
		<div class="wrapper">
			<%-- <%@include file="adminLeftNav.jsp"%> --%>
			<section id="content" class="">
			<div class="row">

				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">Employee Provident Fund</h4>
						</div>
						<div class="col-sm-6 col-md-3">
						</div>
					</div>
			<div class="row">
			<div class="col-md-12">
							<form:form id="pfFormId" action="/home/pfEnrollment/changeToVariableSubmit" modelAttribute="employeePFBean" name="pfFormId"
								method="POST">
							
							
							<h6>Change slab to Variable</h6>

									<%-- <div class="col-md-3">
										<label>Current Slab</label> <form:input type="text" id="optedSlab"
											name="optedSlab"  path="optedSlab" required="true" readonly="true"/>
									</div> --%>
										<div class="col-md-3">
										<label>Current Slab</label> <input type="text" id="optedSlab"
											name="optedSlab"  value="Fixed" readonly="true"/>
									</div>
						</div>		
				</div> 		
								</div>
						  </div>
					<div>
					<a class="btn yellow darken-3"
										href="<%=request.getContextPath()%>/home/myEpfHome">Go Home</a>
					<input type="submit" class="btn btn-primary" id="Submit" value="Click to change to variable slab" name="Submit" />
					</div>
					</form:form>
						</div>
			</div>

<script src="jquery-3.2.1.min.js"></script>
	<%@include file="includeFooter.jsp"%>
</body>
</html>