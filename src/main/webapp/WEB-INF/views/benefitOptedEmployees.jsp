<!-- 
author : jithin.kuriakose
This page will show Employees opted for each benefit plans
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
<script>
	/* $(document).ready(function() {
		$(".approveClass").click(function() {

			var output = "";
			$('.docId').each(function() {

				output += $(this).val() + ",";
			});

			$("#docIds").val(output);

		});

	}); */
</script>
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
							<h4 class="h4-responsive">Opted Employees</h4>
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

							<c:if test="${claimSize ne null and claimSize > 0}">
								<div class="alert alert-warning">
									<strong>Cannot revoke!!</strong> This Employee has already
									submitted ${claimSize} claims
								</div>
							</c:if>




							<form:form action="optedEmployees" method="POST"
								modelAttribute="bean">
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<form:select path="benefitPlanId">
													<c:forEach items="${plans}" var="plan">
														<option value="${plan.benefitPlanId}"
															${bean.benefitPlanId eq plan.benefitPlanId ? 'selected="selected"' : ''}>${plan.planName}</option>
													</c:forEach>
												</form:select>
												<label for="flexiPlan">Select Flexi Plan</label>
											</div>
											<div class="col-md-6 padding_left_0">
												<form:select path="fiscalYear">


													<c:forEach items="${appContext.fiscalYears}" var="fy">

														<option value="${fy}"
															${bean.fiscalYear eq fy ? 'selected="selected"' : ''}>${fy}</option>

													</c:forEach>

												</form:select>
												<label for="FiscalYear">Select Fiscal Year</label>
											</div>
										</div>
									</div>

								</div>
								<div class="row">
									<div class="form-group is-empty margin_bottom_0">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<form:select path="status">

													<option value="HR_APPR">Approved</option>
													<option value="NO_APPR"
														${bean.status eq 'NO_APPR' ? 'selected="selected"' : ''}>Not Approved</option>


												</form:select>
												<label for="Status">Select Status</label>
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
							</form:form>
						</div>
						<%-- <form
							action="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees/report"
							method="post">
							<input type="hidden" name="docIds" id="docIds" /> <input
								type="submit" value="Download"
								class="approveClass btn blue darken-3">
						</form> --%>

						<%-- <form id="approveHiddenId" action="/home/controlPanel/flexiPlans/optedEmployees/approveSelected" >
						<div class="form-group">

							<div class="col-md-6">
								<a class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees/approveSelected">Approve Selected</a>
							<input type="hidden" id="approveSelected" value="" name="approveSelected"> 
							<input type="submit" class="btn btn-primary approveClass"
									id="approve" name="approve" value="Approve Selected" />
							
						</div>
						</div>
						</form> --%>

						<div class="col-md-12">

							<br />
							<table
								class="tablePlanEmployees table striped table-borderd data_table">
								<thead>
									<tr>
										<th><c:choose>

												<c:when test="${bean.status eq 'NO_APPR'}">
													<div class="remember-label">
														<div>
															<input type="checkbox" id="selectAllPlans"
																name="selectAllPlans" value="${employee.planEmployeeId}" />
															<label for="selectAllPlans"></label>
														</div>
													</div>
												</c:when>
												<c:otherwise>


												</c:otherwise>
											</c:choose></th>
										<th>FY</th>
										<th>Employee Code</th>
										<th>Employee Name</th>
										<th>Opted Date</th>
										<th>Effective From</th>
										<th>Category</th>
										<th>Amount</th>
										<th>Status</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${employees}" var="employee">
										<tr>
											<input type="hidden" value="${employee.planEmployeeId}"
												class="docId">

											<td><c:if test="${employee.status eq 'NO_APPR'}">
													<div class="remember-label">
														<div>
															<input class="chk" type="checkbox"
																id="selectPlans${employee.planEmployeeId}"
																name="selectPlans" value="${employee.planEmployeeId}" />
															<label for="selectPlans${employee.planEmployeeId}"></label>
														</div>
													</div>
												</c:if></td>
											<td>${employee.empBenYearlyOpt.fiscalYear}</td>
											<td>${employee.employee.employeeCode}</td>
											<td>${employee.employee.firstName}
												${employee.employee.lastName}</td>
											<td><fmt:formatDate pattern="dd-MMMM-yyyy"
													value="${employee.optedDate}" /></td>
											<td><fmt:formatDate pattern="dd-MMMM-yyyy"
													value="${employee.effFrom}" /></td>
											<td><c:choose>
													<c:when test="${employee.benefitPlan.claimType eq 'CTGY'}">
										 		 Category - ${employee.planCategory.categoryName}
										 			</c:when>
													<c:otherwise>
														<c:choose>
															<c:when
																test="${employee.benefitPlan.claimType eq 'BAND'}">
        										 					Band - ${employee.optingBand}
        										 			</c:when>
															<c:otherwise>
        														<c:choose>
																	<c:when
																		test="${employee.benefitPlan.claimType eq 'DPNT'}">
        										 						Dependent
        															</c:when>
																	<c:otherwise>
        										 	 					Common
        										 					</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose></td>

											<td>${employee.yearlyDeduction}</td>

											<td><c:choose>
													<c:when test="${employee.status eq 'NO_APPR'}">

										

       											 Not Approved
        
    										</c:when>
													<c:when test="${employee.status eq 'HR_RJCT'}">

										

       											 Rejected
        
    										</c:when>
													<c:otherwise>
       											 Approved
        							
    										</c:otherwise>
												</c:choose></td>

											<td><a
												href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees/view/${employee.planEmployeeId}">
													<i class="fa fa-eye" aria-hidden="true"
													data-toggle="tooltip" data-placement="left"
													title="View Details"></i>
											</a> <c:if test="${employee.status eq 'NO_APPR'}">
													<a
														href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees/approve/${employee.planEmployeeId}">
														<i class="fa fa-check" aria-hidden="true"
														data-toggle="tooltip" data-placement="left"
														title="Approve this"></i>
													</a>
												</c:if> <c:if test="${employee.status eq 'HR_APPR'}">
													<a
														href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/rollBackApprovedPlans/${employee.planEmployeeId}">
														<i class="fa fa-retweet" aria-hidden="true"
														data-toggle="tooltip" data-placement="left"
														title="Rollback Approved Plans"></i>
													</a>
												</c:if></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
							<form id="approveHiddenId"
								action="/home/controlPanel/flexiPlans/optedEmployees/approveSelected">
								<div class="form-group">

									<div class="col-md-6">
										<%-- <a class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees/approveSelected">Approve Selected</a> --%>
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

		$("#selectAllPlans").click(function() {
			$('input:checkbox').not(this).prop('checked', this.checked);
		});
	</script>

</body>
</html>