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
							<h4 class="h4-responsive">View LTA Requests</h4>
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



							<form:form action="search" method="POST"
								modelAttribute="searchBean">
								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-12 padding_left_0">
											<form:input type="text" path="ltaEmployeeId"
												id="ltaEmployeeId" name="ltaEmployeeId"/>
											<label for="employeeCode">Employee Code</label>
										</div>
									</div>
								</div>


								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-12 padding_left_0">
											<form:select path="block" id="block" name="block">
												<label for="block">Block</label>
												<c:forEach items="${blocks}" var="block">
													<option>${block}</option>
												</c:forEach>
											</form:select>
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
					</div>
					<div class="col-md-12">
						<table class="tablePlanEmployees table striped table-borderd"">
							<thead>
								<tr>

									<th>Employee Code</th>
									<th>Employee Name</th>
									<th>Designation</th>
									<th>Origin</th>


									<th>Destination</th>
									<th>Requested Fare</th>
									<th>Status</th>
									<th>Actions</th>
									<th>Report</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${ltaEmployees}" var="ltaEmployee">
									<tr>

										<td>${ltaEmployee.employee.employeeCode}</td>
										<td>${ltaEmployee.employee.firstName}
											${ltaEmployee.employee.lastName}</td>
										<td>${ltaEmployee.employee.designationName}</td>
										<td>${ltaEmployee.origin}</td>
										<td>${ltaEmployee.destination1}</td>
										<td>${ltaEmployee.actualFare}</td>
										<c:if test="${role eq 'HR_USER'}">

											<td><c:choose>
													<c:when test="${ltaEmployee.status eq 'SUBM'}">Submitted</c:when>
													<c:otherwise>Approved</c:otherwise>
												</c:choose></td>

											<td><c:if test="${ltaEmployee.status eq 'SUBM'}">
													<a
														href="<%=request.getContextPath()%>/home/controlPanel/lta/search/approve/${ltaEmployee.ltaEmployeeId}">
														Approve</a>
												</c:if></td>
										</c:if>

										<c:if test="${role eq 'FIN_USER'}">
											<td><c:choose>
													<c:when test="${ltaEmployee.status eq 'FIN_APPR'}">Approved</c:when>
													<c:otherwise>Not Approved</c:otherwise>
												</c:choose></td>
											<td><c:if test="${ltaEmployee.status eq 'HR_APPR'}">
													<a
														href="<%=request.getContextPath()%>/home/controlPanel/lta/search/finApprove">
														Approve</a>
												</c:if>
												<td><c:if test="${ltaEmployee.status eq 'HR_RJCT'}">
													<a
														href="<%=request.getContextPath()%>/home/controlPanel/lta/search/finApprove/${ltaEmployee.ltaEmployeeId}">
													Reject</a>
												</c:if> </td>
										</c:if>


										<c:if test="${role eq 'SYS_ADMIN'}">
											<td><c:choose>
													<c:when test="${ltaEmployee.status eq 'SUBM'}">Submitted</c:when>
													<c:when test="${ltaEmployee.status eq 'HR_APPR'}">HR Approved</c:when>
													<c:when test="${ltaEmployee.status eq 'HR_RJCT'}">HR Rejected</c:when>
													<c:otherwise>FIN Approved</c:otherwise>
												</c:choose></td>
											<td><c:if test="${ltaEmployee.status eq 'HR_APPR'}">
													<a
														href="<%=request.getContextPath()%>/home/controlPanel/lta/search/finApprove/${ltaEmployee.ltaEmployeeId}">
														FIN Approve</a>
												</c:if> <c:if test="${ltaEmployee.status eq 'SUBM'}">
													<a
														href="<%=request.getContextPath()%>/home/controlPanel/lta/search/approve/${ltaEmployee.ltaEmployeeId}">
														HR Approve</a>
														<td><c:if test="${ltaEmployee.status eq 'HR_RJCT'}">
													<a
														href="<%=request.getContextPath()%>/home/controlPanel/lta/search/finApprove/${ltaEmployee.ltaEmployeeId}">
														HR Reject</a>
												</c:if> 
												</c:if> </td>
										</c:if>
										<td><a
											href="<%=request.getContextPath()%>/home/controlPanel/lta/search/download/${ltaEmployee.ltaEmployeeId}">
												<i class="fa fa-download" aria-hidden="true"></i></i>
										</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>

				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
</body>
</html>