<!-- 
author : swathy.raghu
This page will provides employee search option
 -->
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
			<%@include file="adminLeftNav.jsp"%>
			<section id="content" class="">
			<div class="row">



				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">Employee List</h4>
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

							<form:form action="employee" method="POST"  modelAttribute="bean1">

								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<form:input type="text" path="employeeCode" id="employeeId"  />
												<label for="employeeCode">Employee Code</label>
											</div>

											<div class="col-md-6 padding_left_0">
												<form:select id="designation" name="designation" path="desg">
													<c:forEach items="${designation}" var="designation">

														<option value="${designation}" ${designation eq bean1.desg ? 'selected="selected"' : ''}>${designation}</option>
													</c:forEach>home/controlPanel/reports/employee

												</form:select> <label for="designation">Select Designation</label>
											</div>


										</div>
									</div>
								
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<form:select id="dept" name="dept" path="dept">
													<c:forEach items="${dept}" var="dept">

														<option value="${dept}" ${dept eq bean1.dept ? 'selected="selected"' : ''}>${dept}</option>
													</c:forEach>

												</form:select> <label for="dept">Select Department</label>
											</div>

										</div>

									</div>

								</div>
								<div class="row">
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<input type="submit" class="btn btn-primary" value="Search">
										</div>
									</div>
								</div>
							</form:form>
						</div>

					<div class="col-md-12">
						<table class="tablePlanEmployees table striped table-borderd"">
							<thead>
								<tr>

									<th>Employee Code</th>
									<th>Employee Name</th>
									<th>Designation</th>
									<th>Department</th>


									<th>Number Of plans Opted</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bean}" var="planEmployeebean">
									<tr>

										<td>${planEmployeebean.employee.employeeCode}</td>
										<td>${planEmployeebean.employee.firstName}
											${planEmployeebean.employee.lastName}</td>
										<td>${planEmployeebean.employee.designationName}</td>
										<td>${planEmployeebean.employee.departmentName}</td>

										<td>${planEmployeebean.noOfPlans}</td>

										<td>
										<c:if test="${planEmployeebean.noOfPlans ne 0}">
										<a
											href="<%=request.getContextPath()%>/home/controlPanel/reports/employee/search/view/${planEmployeebean.employee.employeeId}">
												<i class="fa fa-eye" aria-hidden="true"></i>

										</a>
										<a
											href="<%=request.getContextPath()%>/home/controlPanel/reports/employee/search/download/${planEmployeebean.employee.employeeId}">
												<i class="fa fa-download" aria-hidden="true"></i></i>
										</a>
										</c:if>
										</td>
								
									
									
										

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
	<script>
		$(document).ready(function() {
			$('select').material_select();
		});
	</script>

</body>
</html>