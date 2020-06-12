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
							<h4 class="h4-responsive">Verify Claim - ${claim.claimRefNo}</h4>
							<a href="${claim.uploadUrl}" style="float:right;margin-right:-250px;margin-top:-40px;" target="_blank" clicked="false" class="btn blue darken-2"><i class="fa fa-download" aria-hidden="true"></i>
 View Bills</a>
						</div>
						<div class="col-sm-6 col-md-3">
							<!-- <div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div> -->
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<table class="table striped table-borderd">
								<tr>
									<th>Claim Ref No.</th>
									<td>${claim.claimRefNo}</td>
									<th>Submitted Date</th>
									<td>${claim.submittedDate}</td>
									<th>Approved By</th>
									<td>${claim.hrApprovedBy}</td>


								</tr>
								<tr>
									<th>Employee</th>
									<td>${claim.planEmployee.employee.employeeCode}-
										${claim.planEmployee.employee.firstName}
										${claim.planEmployee.employee.lastName}</td>
									<th>Requested Amount(Rs.)</th>
									<td>${claim.totalRequestedAmount}</td>
									<th>Approved Amount(Rs.)</th>
									<td>${claim.totalApprovedAmount}</td>

								</tr>

							</table>
							<table class="table striped table-borderd">
							<c:if test="${employeePlan.benefitPlan.promptFieldsOnOPT}">
								<c:forEach items="${benefitPlanEmployeeField}" var="field">
									<tr>
										<th>${field.field.uiLabel}</th>
										<td>${field.value}</td>
										<th></th>
										<td></td>
										<th></th>
										<td></td>
										<th></th>
										<td></td>
										<th></th>
										<td></td>
									</tr>
									<tr>
										<th></th>
										<td></td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
							<form action="approve" method="POST">
								<table class="table striped table-borderd" id="myform">
									<thead>
										<tr>
											<c:if
												test="${claim.planEmployee.benefitPlan.claimType eq 'CTGY'}">
												<th>Category/Sub Category</th>
											</c:if>
											<th>Dependent</th>
											<th>Bill/Vouhcer No.</th>
											<th>Bill/Voucher Date</th>
											<th>Requested Amount</th>
											<th>Issued From</th>

											<th>Approved Amount</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${claim.details}" var="detail">
											<tr>
												<c:if
													test="${claim.planEmployee.benefitPlan.claimType eq 'CTGY'}">
													<td><c:choose>
															<c:when test="${detail.planEmployeeDetail ne null}">
												${detail.planEmployeeDetail.misc.miscName}
												</c:when>
															<c:otherwise>									
												${detail.planEmployeeDetail.planEmployee.planCategory.categoryName}
												</c:otherwise>
														</c:choose></td>
												</c:if>
												<td><c:choose>
													<c:when test="${detail.dependent.dependentId ne null}">
														<td>${detail.dependent.dependentName}</td>
													</c:when>
													<c:otherwise>
											Not Applicable
											</c:otherwise>
												</c:choose></td>
												<td>${detail.billNo}</td>

												<td><fmt:formatDate type="date"
														value="${detail.billDate}" /></td>
												<td>${detail.requestedAmount}</td>
												<td>${detail.billIssuer}</td>
												<td>${detail.approvedAmount}</td>



											</tr>
										</c:forEach>
									</tbody>
								</table>



								<input type="hidden" name="claimId" id="claimId"
									value="${claim.benefitPlanClaimId}" /> <a
									class="btn yellow darken-3"
									href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/searchClaims">Back</a>

								<input type="submit" id="btnClaimApprove"
									class="btn btn-primary" value="Approve Claim" /> <a
									class="btn red darken-3"
									href="<%=request.getContextPath() %>/home/controlPanel/flexiPlans/claims/finApproveView/reject/${claim.benefitPlanClaimId}">Reject
									Claim</a>
							</form>
						</div>
					</div>
				</div>
			</div>
			</section>
		</div>
	</div><%@include file="includeFooter.jsp"%>
</body>


</html>

