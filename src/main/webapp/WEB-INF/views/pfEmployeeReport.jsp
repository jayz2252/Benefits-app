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
							<h4 class="h4-responsive">PF Employees Report</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>



					<div class="col-md-12">
						<table
							class="tablePlanEmployees table striped table-borderd data_table">
							<thead>
								<tr>
									<th>Employee Code</th>
									<th>Employee Name</th>
									<th>Location</th>
									<th>DOJ-Company</th>
									<th>Opted Date</th>
									<th>Date of slab change</th>
									<th>PF Plan at the time of joining</th>
									<th>Current PF plan</th>
									<th>Date of opting VPF (Voluntary PF)</th>
									<th>VPF contribution(per annum)</th>
									<th>UAN No.</th>
									<th>PF Acc No.</th>
									<th>Aadhar No.</th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pfEmployees}" var="pfEmployee">
									<tr>

										<td>${pfEmployee.employee.employeeCode}</td>
										<td>${pfEmployee.employee.firstName}
											${pfEmployee.employee.lastName}</td>
											<td>${pfEmployee.employee.parentOffice}</td>
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfEmployee.employee.dateOfJoin}"/></td>
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfEmployee.optedDate}" /></td>
												
												<td>
												<c:choose>
												<c:when test="${pfEmployee.optedSlab eq 'FIXD'}"> </c:when>
												<c:otherwise><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfEmployee.updatedDate}"/></c:otherwise>
												</c:choose>											
												</td>
												<td></td>
												
										<td><c:choose>
												<c:when test="${pfEmployee.optedSlab eq 'FIXD'}">Fixed</c:when>
												<c:when test="${pfEmployee.optedSlab eq 'VRBL'}">Variable</c:when>
												<c:when test="${pfEmployee.optedSlab eq 'MDTRY'}">Mandatory</c:when>
											</c:choose></td>
											
											<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfEmployee.createdDate}"/></td>
											<td>${pfEmployee.formVoluntaryPF}</td>
										
										<td>${pfEmployee.uan}</td>
										<td>${pfEmployee.pfAcNo}</td>
										
										
										<td>${pfEmployee.formAadharNo}</td>

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