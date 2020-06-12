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
							<h4 class="h4-responsive">${insPlanEmployee.employee.firstName}
								${insPlanEmployee.employee.lastName} - Insurance Details</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>

					<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">Employee Details</h4>
					</div>

					<div class="row">
						<div class="col-md-12">
							<table class="table striped table-borderd">
								<tr>
									<th>Employee Name</th>
									<td colspan="3">${insPlanEmployee.employee.firstName}
										${insPlanEmployee.employee.lastName}</td>
									<th>Employee Code</th>
									<td colspan="3">${insPlanEmployee.employee.employeeCode}</td>
								</tr>

								<tr>
									<th>Location</th>
									<td colspan="3">${insPlanEmployee.employee.parentOffice}</td>
									<th>Date Of Birth</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${insPlanEmployee.employee.dateOfBirth}"/></td>
								</tr>
							</table>

							<div class="col-sm-6 col-md-9 py-1 px-1">
								<h4 class="h4-responsive">Insurance Plan Details</h4>
							</div>

							<table class="table striped table-borderd">
								<tr>
									<th>Insurance Plan Name</th>
									<td colspan="3">${insPlanEmployee.insPlan.planName}</td>
									<th>Enrolled Date</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${insPlanEmployee.effFrom}" /></td>
								</tr>

								<tr>
									<th>Effective From</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${insPlanEmployee.effFrom}" /></td>
									<th>Effective Till</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${insPlanEmployee.effTill}" /></td>
								</tr>
								<tr>
									<th>Yearly Deduction</th>
									<td colspan="3">${insPlanEmployee.yearlyEmpDeduction}</td>
									<th>Sum Insured</th>
									<td colspan="3">${insPlanEmployee.sumInsured}</td>
								</tr>	
							</table>
							
							
					 	<table class="table striped table-borderd">
							<thead>
								<tr>
									<th colspan="5">Members</th>
								</tr>
								<tr>
									<th>Dependent</th>
									<th>Relationship</th>
									<th>Gender</th>
									<th>Insurance</th>
									<th>AIC</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${insPlanEmployee.details}" var="dep">
									<tr>
										<td>${dep.dependent.dependentName}</td>
										<td>${dep.dependent.relationship}</td>
										<td><c:choose>
												<c:when test="${dep.dependent.gender eq 'F'}">
													<i class="fa fa-female" aria-hidden="true"></i>
												</c:when>
												<c:otherwise>
													<i class="fa fa-male" aria-hidden="true"></i>
												</c:otherwise>
											</c:choose></td>
										<td><i class="fa fa-check" aria-hidden="true"></i></td>
										<td><c:choose>
												<c:when test="${dep.eaicEnrolled}">
													<i class="fa fa-check" aria-hidden="true"></i>
												</c:when>
												<c:otherwise>
													<i class="fa fa-times" aria-hidden="true"></i>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

							<a class="btn yellow darken-3"
									href="<%=request.getContextPath() %>/home/controlPanel/insurancePlans/optedEmployees">Back</a>
									
							<a class="btn green darken-3"
								href="<%=request.getContextPath() %>/home/controlPanel/insurancePlans/optedEmployees/approve/${insPlanEmployee.insPlanEmployeeId}"
								>Approve</a> 
								
							<a class="btn red darken-3"
								href="<%=request.getContextPath() %>/home/controlPanel/insurancePlans/optedEmployees/reject/${insPlanEmployee.insPlanEmployeeId}"
								>Reject</a> 
							
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