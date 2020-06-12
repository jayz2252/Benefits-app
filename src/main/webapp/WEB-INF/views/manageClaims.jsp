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
							<h4 class="h4-responsive">Manage Claims -
								${claim.planEmployee.benefitPlan.planName}</h4>
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
							<table class="table striped table-borderd">
								<thead>
									<tr>
										<th>FY</th>
										<th>Claim Ref No.</th>
										<th>Emp Code</th>
										<th>Emp Name</th>
										<th>Submitted Date</th>
										<th>Requested Amount(Rs.)</th>
										<th>Total Bills</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${claims}" var="claim">
										<tr>
											<td>${claim.fiscalYear}</td>
											<td><a
												href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claims/approveView/${claim.benefitPlanClaimId}">
													${claim.claimRefNo} </a></td>
											<td>${claim.planEmployee.employee.employeeCode}</td>
											<td>${claim.planEmployee.employee.firstName}
												${claim.planEmployee.employee.lastName}</td>
											<td>${claim.submittedDate}</td>
											<td>${claim.totalRequestedAmount}</td>
											<td>${claim.details.size()}</td>
											<td><a
												href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claims/approveView/${claim.benefitPlanClaimId}">
													<i class="fa fa-eye" aria-hidden="true"
													data-toggle="tooltip" data-placement="left"
													title="Open for Approval/Reject"></i>
											</a></td>
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