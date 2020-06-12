
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
							<h4 class="h4-responsive">Claim Details</h4>
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



						<form:form action="claimDetails" method="post" modelAttribute="bean">
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<form:select path="benefitPlanId">
													<c:forEach items="${plans}" var="plan">
														<option value="${plan.benefitPlanId}" >${plan.planName}</option>            
													</c:forEach> 
												</form:select>
												<label for="flexiPlan">Select Flexi Plan</label>
											</div>
											<div class="col-md-6 padding_left_0">
												<form:select path="fiscalYear">


													<c:forEach items="${appContext.fiscalYears}" var="fy">
														<option value="${fy}">${fy}</option>
													</c:forEach>

												</form:select>
												<label for="FiscalYear">Select Fiscal Year</label>
											</div>
										</div>
									</div>

								</div>
							<%-- 	<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<select path="status">

													<option value="HR_APPR">Approved</option>
													<option value="NO_APPR" ${bean.status eq 'NO_APPR' ? 'selected="selected"' : ''}>Not Approved</option>


												</select>
												<label for="Status">Select Status</label>
											</div>

										</div>

									</div>

								</div> --%>
								<div class="row">
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<input type="submit" class="btn btn-primary" value="Search">
										</div>
									</div>
								</div>
							</form:form>
						</div>

						<table class="tablePlanEmployees table striped table-borderd data_table">
							<thead><tr></tr>
								<tr>

									<th>Employee Name</th>
									<th>Employee Code</th>
									<th>Designation</th>
									<th>Department</th>
									<th>Location</th>
									<th>Date of Joining</th>
									
									<c:forEach var="i" begin="1" end="${frequency}">
										
   										<th>Requested Amount (Period ${i})</th>
										<th>Alloted Amount (Period ${i})</th>
										<th>Approved Amount (Period ${i})</th>
										<th>Pay Rolled Amount (Period ${i})</th>
										
									</c:forEach>
									
									<th>Taxable Amount</th>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listClaims}" var="planClaim">
									<tr>
									
										<td>${planClaim.planEmployee.employee.firstName}
											${planClaim.planEmployee.employee.lastName}</td>
										<td>${planClaim.planEmployee.employee.employeeCode}</td>
										<td>${planClaim.planEmployee.employee.designationName}</td>
										<td>${planClaim.planEmployee.employee.departmentName}</td>
										<td>${planClaim.planEmployee.employee.parentOffice}</td>
										
										<td><fmt:formatDate pattern="dd-MM-yyyy"
												value="${planClaim.planEmployee.employee.dateOfJoin}" /></td>
										
												
										<c:forEach items="${planClaim.claimPeriods}" var="claimPeriod">
											
											<td>${claimPeriod.totalRequestedAmount}</td>
											<td>${claimPeriod.alottedAmount}</td>
											<td>${claimPeriod.totalApprovedAmount}</td>
											<td>${claimPeriod.payRolledAmount}</td>
											
										</c:forEach>
										
										<td>${planClaim.taxableAmt}</td>
												

									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div></div>
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