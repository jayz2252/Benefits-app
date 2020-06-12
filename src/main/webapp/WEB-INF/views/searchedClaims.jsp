<!-- 
author : jithin.kuriakose
This page will show Employees opted for each benefit plans
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
							<h4 class="h4-responsive">Searching Claims</h4>
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



							<form:form action="searchClaims" method="POST"  modelAttribute="bean">
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<form:input type="text" id="claimRefNo" path="claimRefNo" name="claimRefNo" /><label
													for="claimRefNo">Claim Reference No</label>
											</div>


											<div class="col-md-6 padding_left_0">
												<form:select path="benefitPlanId" name="flexiPlan">

													<c:forEach items="${plans}" var="plan">
														<option value="${plan.benefitPlanId}" ${bean.benefitPlanId eq plan.benefitPlanId ? 'selected="selected"' : ''}>${plan.planName}</option>
													</c:forEach>
												</form:select> <label for="flexiPlan">Select Flexi Plan</label>
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
														<option value="${fy}" ${bean.fiscalYear eq fy ? 'selected="selected"' : ''}>${fy}</option>
													</c:forEach>

												</form:select>

												 <label for="FiscalYear">Select Fiscal Year</label>
											</div>


											<div class="col-md-6 padding_left_0">
												<form:input type="text" path="employeeCode" id="employeeId"  />
												<label for="employeeCode">Employee Code</label>
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
												<form:select path="status" id="Status" name="Status">
													<c:if test="${role eq 'HR_USER'}">
										
													<option value="HR_APPR">Approved</option>

													<option value="SUBM" ${bean.status eq 'SUBM' ? 'selected="selected"' : ''}>Not Approved</option>

													</c:if>
													<c:if test="${role eq 'FIN_USER'}">
													<option value="FIN_APPR">Approved</option>

													<option value="HR_APPR" ${bean.status eq 'HR_APPR' ? 'selected="selected"' : ''}>Not Approved</option>
												
												</c:if>
												
													<c:if test="${role eq 'SYS_ADMIN'}">
													<option value="FIN_APPR">Financial Approved</option>
													<option value="SUBM">Submitted</option>
													<option value="HR_APPR" ${bean.status eq 'HR_APPR' ? 'selected="selected"' : ''}>HR Approved</option>
												
												</c:if>
												



												</form:select> <label for="Status">Select Status</label>
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
						<table class="tablePlanEmployees table striped table-borderd data_table">
							<thead>
								<tr>
									<th>

										<div class="remember-label">
											<div>
												<input type="checkbox" id="selectAll" name="selectAll"
													value="${planClaim.benefitPlanClaimId}" /> <label
													for="selectAll"></label>
											</div>
										</div>
									</th>
									<th>Claim Reference No</th>
									<th>Employee Code</th>
									<th>Employee Name</th>
									<th>Submitted Date</th>
									<th>Requested Amount</th>
									<th>Approved Amount</th>
									<th>Flexi Plan Name</th>
									<th>Status</th>
									<th>Action</th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${planClaims}" var="planClaim">
									<tr>
										<td><c:if test="${planClaim.status eq 'HR_APPR'}">
												<div class="remember-label">
													<div>
														<input class="chk" type="checkbox"
															id="selectPF${planClaim.benefitPlanClaimId}" name="selectPF"
															value="${planClaim.benefitPlanClaimId}" /> <label
															for="selectPF${planClaim.benefitPlanClaimId}"></label>
													</div>
												</div>
											</c:if></td>
										<td>${planClaim.claimRefNo}</td>
										<td>${planClaim.planEmployee.employee.employeeCode}</td>
										<td>${planClaim.planEmployee.employee.firstName}
											${planClaim.planEmployee.employee.lastName}</td>
										<td><fmt:formatDate pattern="dd-MM-yyyy"
												value="${planClaim.submittedDate}" /></td>
										<td>${planClaim.totalRequestedAmount}</td>
										<td>${planClaim.totalApprovedAmount}</td>

										<td>${planClaim.planEmployee.benefitPlan.planName}</td>


										

										
										<c:if test="${role eq 'HR_USER'}">
										
										<td><c:choose>
										       <c:when test="${planClaim.status eq 'SUBM'}">Submitted</c:when>
									           <c:otherwise>Approved</c:otherwise>
										     </c:choose>
										</td>
										
										<td>
										<c:if test="${planClaim.status eq 'SUBM'}">
												<a
													href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claims/approveView/${planClaim.benefitPlanClaimId}">
													Approve</a>
											</c:if>
											
										</td>	
										</c:if>	
											
											<c:if test="${role eq 'FIN_USER'}">
											<td><c:choose>
										       <c:when test="${planClaim.status eq 'FIN_APPR'}">Approved</c:when>
									           <c:otherwise>Not Approved</c:otherwise>
										     </c:choose>
										</td>
										<td>
											<c:if test="${planClaim.status eq 'HR_APPR'}">
												<a
													href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claims/finApproveView/${planClaim.benefitPlanClaimId}">
													Approve</a>
											</c:if>
											</td>
											</c:if>
											
											
											<c:if test="${role eq 'SYS_ADMIN'}">
											<td><c:choose>
										       <c:when test="${planClaim.status eq 'SUBM'}">Submitted</c:when>
										       <c:when test="${planClaim.status eq 'HR_APPR'}">HR Approved</c:when>
										       <c:when test="${planClaim.status eq 'HR_RJCT'}">HR Rejected</c:when>
									           <c:otherwise>FIN Approved</c:otherwise>
										     </c:choose>
											</td>
											<td>
											<c:if test="${planClaim.status eq 'HR_APPR'}">
												<a
													href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claims/finApproveView/${planClaim.benefitPlanClaimId}">
													FIN Approve</a>
											</c:if>
											<c:if test="${planClaim.status eq 'HR_RJCT'}">
												<a
													href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claims/finApproveView/${planClaim.benefitPlanClaimId}">
													HR Rejected</a>
											</c:if>
											<c:if test="${planClaim.status eq 'SUBM'}">
												<a
													href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claims/approveView/${planClaim.benefitPlanClaimId}">
													HR Approve</a>
											</c:if>
											</td>
											</c:if>
											

											
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
						<form id="approveHiddenId"
							action="/home/controlPanel/flexiPlans/searchClaims/finApproveSelected">
							<div class="form-group">

								<div class="col-md-6">
									<input type="hidden" id="approveSelected" value=""
										name="approveSelected"> <input type="submit"
										class="btn btn-primary approveClass" id="approve"
										name="approve" value="Approve Selected" />

								</div>
							</div>
						</form>
						
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
		
		function Populate() {
			vals = $('.chk:checked').map(function() {
				return this.value;
			}).get().join(',');
			console.log(vals);
			$('#approveSelected').val(vals);

			if (vals != '') {
				$('#approve').prop("disabled", false);
			} else {
				$('#approve').prop("disabled", true);
			}
		}

		$('input[type="checkbox"]').on('change', function() {
			Populate()
		}).change();

		$('#approve').prop("disabled", true);
		$('.chk').click(function() {
			if ($(this).is(':checked')) {
				$('#approve').prop("disabled", false);
			} else {
				if ($('.chk').filter(':checked').length < 1) {
					$('#approve').attr('disabled', true);
				}
			}
		});

		$("#selectAll").click(function() {
			$('input:checkbox').not(this).prop('checked', this.checked);
		});
		
	</script>

</body>
</html>