<!-- 
author : swathy.raghu
This page allows you to search claims 
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
							<h4 class="h4-responsive">Search Insurance Claims</h4>
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



							<form:form action="" method="POST" modelAttribute="bean">
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<form:input type="text" id="claimRefNo" path="claimRefNo"
													name="claimRefNo" />
												<label for="claimRefNo">Claim Reference No</label>
											</div>


											<div class="col-md-6 padding_left_0">
												<form:select path="claimType" name="claimType">


													<option value="PAF"
														${bean.claimType eq 'PAF' ? 'selected="selected"' : ''}>PAF</option>
													<option value="BILL"
														${bean.claimType eq 'BILL' ? 'selected="selected"' : ''}>Bills</option>
												</form:select>
												<label for="flexiPlan">Select Claim Type</label>
											</div>


										</div>
									</div>
								</div>
								<!-- <div class="row">
									

								</div> -->
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">

												<form:select path="fiscalYear">


													<c:forEach items="${appContext.fiscalYears}" var="fy">
														<option value="${fy}"
															${bean.fiscalYear eq fy ? 'selected="selected"' : ''}>${fy}</option>
													</c:forEach>

												</form:select>

												<label for="FiscalYear">Select Fiscal Year</label>
											</div>


											<div class="col-md-6 padding_left_0">
												<form:input type="text" path="empCode" id="empCode" />
												<label for="empCode">Employee Code</label>
											</div>


										</div>
									</div>
								</div>
								<!-- <div class="row">
									

								</div> -->
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">


												<form:input type="text" id="fromDate" path="fromDate"
													onfocus="(this.type='date')"
													onfocusout="(this.type='text')" name="fromDate" />
												<label for="fromDate">From Date</label>
											</div>
											<div class="col-md-6 padding_left_0">
												<form:input type="text" id="toDate" path="toDate"
													onfocus="(this.type='date')"
													onfocusout="(this.type='text')" name="toDate" />
												<label for="toDate">To Date</label>

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
							<table
								class="tablePlanEmployees table striped table-borderd data_table">
								<thead>
									<tr>

										<th>Claim Reference No</th>
										<th>Employee Code</th>
										<th>Employee Name</th>
										<th>Requested Date</th>
										<th>Requested Amount</th>
										<th>Approved Amount</th>
										<th>Claim Type</th>
										<th>Status</th>
										<th>Action</th>


									</tr>
								</thead>
								<tbody>
									<c:forEach items="${claims}" var="planClaim">
										<tr>
											<td>${planClaim.claimRefno}</td>
											<td>${planClaim.planEmployee.employee.employeeCode}</td>
											<td>${planClaim.planEmployee.employee.firstName}
												${planClaim.planEmployee.employee.lastName}</td>
											<td><fmt:formatDate pattern="dd-MMM-yyyy"
													value="${planClaim.requestedDate}" /></td>
											<td>${planClaim.totalReqAmount}</td>
											<td>${planClaim.totalApprovedAmount}</td>

											<td>${planClaim.claimType}</td>



											<td><c:choose>
													<c:when test="${planClaim.status eq 'SUBM'}">Waiting For Insurance Desk Approval</c:when>
													<c:when test="${planClaim.status eq 'HR_APPR'}">Waiting For Insurance Committee Approval</c:when>
													<c:when
														test="${planClaim.status eq 'INS_APPR' && planClaim.claimType eq 'BILL'}">Waiting For Management Approval</c:when>
													<c:when
														test="${planClaim.status eq 'INS_APPR' && planClaim.claimType eq 'PAF'}">Approved</c:when>
													<c:when test="${planClaim.status eq 'APPR'}">Approved</c:when>
													<c:when test="${planClaim.status eq 'HR_RJCT'}">Insurance Desk Rejected</c:when>
													<c:when test="${planClaim.status eq 'INS_RJCT'}">Insurance Committee Rejected</c:when>
													<c:when test="${planClaim.status eq 'GM_RJCT'}">GM Rejected</c:when>
													<c:when test="${planClaim.status eq 'PAID'}">Paid</c:when>
													<c:when test="${planClaim.status eq 'NOT_PAID'}">Payment Rejected</c:when>
													<c:when test="${planClaim.status eq 'PART_PAID'}">Partially Approved</c:when>
												</c:choose></td>

											<c:if test="${role eq 'HR_USER'}">

												<td>
												
												
												<c:if
														test="${planClaim.claimType eq 'BILL'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/viewClaim/${planClaim.insPlanEmployeeClaimId}">
															<i class="fa fa-eye" aria-hidden="true"></i></a>
													</c:if>
													
													<c:if
														test="${planClaim.claimType eq 'PAF'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/viewPAFDetails/${planClaim.insPlanEmployeeClaimId}">
															<i class="fa fa-eye" aria-hidden="true"></i></a>
													</c:if>
												<c:if
														test="${planClaim.status eq 'SUBM' && planClaim.claimType eq 'PAF'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/PAFHRApprove/${planClaim.insPlanEmployeeClaimId}">
															Ins Desk Approve</a>
													</c:if> <c:if
														test="${planClaim.status eq 'SUBM' && planClaim.claimType eq 'BILL'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees/${planClaim.insPlanEmployeeClaimId}/billsHRApprove/new">
															Ins Desk Approve</a>
													</c:if></td>
											</c:if>

											<c:if test="${role eq 'INS_USER'}">

												<td>
												<c:if
														test="${planClaim.claimType eq 'BILL'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/viewClaim/${planClaim.insPlanEmployeeClaimId}">
															<i class="fa fa-eye" aria-hidden="true"></i></a>
													</c:if>
													
													<c:if
														test="${planClaim.claimType eq 'PAF'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/viewPAFDetails/${planClaim.insPlanEmployeeClaimId}">
															<i class="fa fa-eye" aria-hidden="true"></i></a>
													</c:if>
													
												<c:if
														test="${planClaim.status eq 'HR_APPR' && planClaim.claimType eq 'BILL'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/billsINSApprove/${planClaim.insPlanEmployeeClaimId}">Ins
															Committee Approve</a>
													</c:if>
													<c:if
														test="${planClaim.status eq 'HR_APPR' && planClaim.claimType eq 'PAF'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/PAFINSApprove/${planClaim.insPlanEmployeeClaimId}">
															Ins Committee Approve</a>
													</c:if></td>
											</c:if>

											<c:if test="${role eq 'GM_USER'}">

												<td>
												<c:if
														test="${planClaim.claimType eq 'BILL'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/viewClaim/${planClaim.insPlanEmployeeClaimId}">
															<i class="fa fa-eye" aria-hidden="true"></i></a>
													</c:if>
													
													<c:if
														test="${planClaim.claimType eq 'PAF'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/viewPAFDetails/${planClaim.insPlanEmployeeClaimId}">
															<i class="fa fa-eye" aria-hidden="true"></i></a>
													</c:if>
													
												<c:if
														test="${planClaim.status eq 'INS_APPR' && planClaim.claimType eq 'BILL'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/billsGMApprove/${planClaim.insPlanEmployeeClaimId}">
															Management Approve</a>
													</c:if></td>
											</c:if>


											<c:if test="${role eq 'FIN_USER'}">
												<td><c:if
														test="${planClaim.claimType eq 'PAF' && planClaim.status eq 'APPR'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/PAFPay/${planClaim.insPlanEmployeeClaimId}">
															Pay</a>
													</c:if>
													<c:if
														test="${planClaim.claimType eq 'PAF' && planClaim.status eq 'PART_PAID'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/PAFPay/${planClaim.insPlanEmployeeClaimId}">
															Pay</a>
													</c:if>
													 <c:if
														test="${planClaim.claimType eq 'BILL' && planClaim.status eq 'APPR'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/payBills/${planClaim.insPlanEmployeeClaimId}">
															Pay</a>
													</c:if></td>
											</c:if>

											<c:if test="${role eq 'SYS_ADMIN'}">


												<td>
												<c:if
														test="${planClaim.claimType eq 'BILL'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/viewClaim/${planClaim.insPlanEmployeeClaimId}">
															<i class="fa fa-eye" aria-hidden="true"></i></a>
													</c:if>
													
													<c:if
														test="${planClaim.claimType eq 'PAF'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/viewPAFDetails/${planClaim.insPlanEmployeeClaimId}">
															<i class="fa fa-eye" aria-hidden="true"></i></a>
													</c:if>
													
												<c:if
														test="${planClaim.status eq 'INS_APPR' && planClaim.claimType eq 'BILL'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/billsGMApprove/${planClaim.insPlanEmployeeClaimId}">
															GM Approve</a>
													</c:if> <c:if
														test="${planClaim.status eq 'HR_APPR' && planClaim.claimType eq 'BILL'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/billsINSApprove/${planClaim.insPlanEmployeeClaimId}">Ins
															Committee Approve</a>
													</c:if>
													<c:if
														test="${planClaim.status eq 'HR_APPR' && planClaim.claimType eq 'PAF'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/PAFINSApprove/${planClaim.insPlanEmployeeClaimId}">
															Ins Committee Approve</a>
													</c:if> <c:if
														test="${planClaim.status eq 'SUBM' && planClaim.claimType eq 'PAF'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/PAFHRApprove/${planClaim.insPlanEmployeeClaimId}">
															Ins Desk Approve</a>
													</c:if> <c:if
														test="${planClaim.status eq 'SUBM' && planClaim.claimType eq 'BILL'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees/${planClaim.insPlanEmployeeClaimId}/billsHRApprove/new">
															Ins Desk Approve</a>
													</c:if> 
													
													<c:if
														test="${planClaim.claimType eq 'PAF' && planClaim.status eq 'PART_PAID'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/PAFPay/${planClaim.insPlanEmployeeClaimId}">
															Pay</a>
													</c:if>
													<%-- 		<c:if test="${planClaim.approved eq 'true' && planClaim.claimType eq 'PAF'}">
											<a	href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/billUploads/${planClaim.insPlanEmployeeClaimId}">
													<i class="fa fa-upload" aria-hidden="true"
													data-toggle="tooltip" data-placement="left"
													title="Bill uploads"></i>
												</a>
											</c:if> --%> <%-- 	<c:if test="${planClaim.approved eq 'true' && planClaim.claimType eq 'BILL'}">
											<a	href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/payBills/${planClaim.insPlanEmployeeClaimId}">
													<i class="fa fa-credit-card" aria-hidden="true"
													data-toggle="tooltip" data-placement="left"
													title="pay bills"></i>
												</a>
											</c:if> --%> <c:if
														test="${planClaim.claimType eq 'PAF' && planClaim.status eq 'APPR'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/PAFPay/${planClaim.insPlanEmployeeClaimId}">
															Pay</a>
													</c:if> <c:if
														test="${planClaim.claimType eq 'BILL' && planClaim.status eq 'APPR'}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/payBills/${planClaim.insPlanEmployeeClaimId}">
															Pay</a>
													</c:if></td>
											</c:if>



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