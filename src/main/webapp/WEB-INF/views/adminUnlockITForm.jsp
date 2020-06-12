<!-- 
author : Swathy Raghu
Page used to unlock declaration form based on employee code
 -->
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlanEmployee"%>
<%@page import="com.speridian.benefits2.model.pojo.Employee"%>
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<style>
.dataTables_wrapper .dataTables_length label .select-wrapper {
	float: left;
	top: 20px;
	position: absolute;
}

.dataTables_wrapper .dataTables_length label {
	height: 60px;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<body>
	<%@include file="employeeNavBar.jsp"%>
	<div id="main">
		<div class="wrapper">
			<%@include file="adminLeftNav.jsp"%>
			<section id="content" class="">
			<div class="row">



				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">Unlock An IT Declaration Form</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">






							<form
								action="<%=request.getContextPath()%>/home/controlPanel/unlockITDeclaration/search"
								method="POST">
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">

											<div class="col-md-6 padding_left_0">
												<input name="empCode" id="empCode" type="text" /> <label
													for="empCode">Enter Employee Code</label>
											</div>
										</div>
									</div>

								</div>

								<div class="row">
									<div class="md-form margin_bottom_0">
										<div class="col-md-6 padding_left_0">
											<input type="submit" class="btn btn-primary" value="Search">
										</div>
									</div>


								</div>
								<br /> <br />
							
											<div class="alert alert-success">
												<strong>${message}</strong>
											</div>
									
							</form>
						</div>

						<div class="col-md-12">

							<br />
							<table
								class="tablePlanEmployees table striped table-borderd data_table">
								<thead>
									<tr>


										<th>Employee Code</th>
										<th>Employee Name</th>



										<th>Fiscal Year</th>
										<th>Status</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${declaration}" var="declaration">
										<tr>
											<td>${declaration.employee.employeeCode}</td>
											<td>${declaration.employee.firstName}
												${declaration.employee.lastName}</td>
											<%-- 	<td><fmt:formatDate pattern="dd-MMM-yyyy"
													value="${declaration.requestedDate}" /></td> --%>
											<td>${declaration.financilaYear}</td>


											<td><c:choose>
													<c:when test="${declaration.status eq 'SUBMIT'}">Submitted</c:when>
													<c:when test="${declaration.status eq 'OPEN'}">Open</c:when>
												</c:choose></td>

											<td><a
												href="/home/controlPanel/unlockITDeclaration/${declaration.itEmployeeId}">Unlock</a></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>


					</div>

				</div>

			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>


</body>
</html>