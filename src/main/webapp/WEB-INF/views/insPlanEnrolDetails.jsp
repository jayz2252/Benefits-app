
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlan"%>
<%@page import="com.speridian.benefits2.service.BenefitPlanService"%>
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlanEmployee"%>
<%@page import="com.speridian.benefits2.model.pojo.Employee"%>
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<body>
	<%@include file="employeeNavBar.jsp"%>

	<div class="col-md-3">
		<span id="myPlanDetails" class="sticky_left">
			<table>
				<tr>
					<td>Employee Contribution</td>
					<c:choose>
					<c:when test="${saved}">
					<th><span id="totalEmpContribution">${empContribution}.00</span></th>
					</c:when>
					<c:otherwise>
					<th><span id="totalEmpContribution">${selfEmployeeContribution}</span></th>
					</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>Company Contribution</td>
					<th><span id="totalCmpContribution">${selfCompanyContribution}</span></th>
				</tr>
				<tr>
					<td>Monthly Deduction</td>
					<c:choose>
					<c:when test="${saved}">
					<th><span id="totalMonthlyDeduction">${empContribution / 12}</span></th>
					</c:when>
					<c:otherwise>
					<th><span id="totalMonthlyDeduction">${selfEmployeeContribution / 12}</span></th>
					</c:otherwise>
					</c:choose>
				</tr>
			</table>
		</span>
	</div>
	<div id="main">
		<div class="wrapper">
			<section id="content" class="">

			<div class="white col-md-12">
				<div class="row">
					<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">${plan.planName}-Summary</h4>
						<c:if test="${saved}">
							<div class="alert alert-warning" role="alert">Showing
								Saved Info.</div>
						</c:if>
						
						
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<table class="table striped table-borderd">
							<tr>
								<th>Plan Name</th>
								<td>${plan.planName}</td>
							</tr>
							<tr>
								<th>Details</th>
								<td>${plan.planDesc}</td>
							</tr>

							<tr>
								<th>Sum Insured</th>
								<td><strong>Rs.<span id="sumInsured"> <fmt:formatNumber
												type="number" minFractionDigits="2" maxFractionDigits="2"
												value="${plan.yearlyCoverage}" /></span> <c:choose>
											<c:when test="${plan.planType eq 'FMLY'}">
						 shared across enrolled dependents
						</c:when>
											<c:otherwise>
						 individually
						</c:otherwise>
										</c:choose>
								</strong></td>
							</tr>
							<tr>
								<th>Coverage Period</th>
								<td colspan="2"><fmt:formatDate pattern="dd-MMM-yyyy"
										value="${plan.effFrom}" /> to <fmt:formatDate
										pattern="dd-MMM-yyyy" value="${plan.effTill}" /></td>
							</tr>

							<c:choose>
								<c:when test="${plan.eaicIncluded}">
									<tr>

										<th>Accidental Insurance Coverage(AIC), Sum Insured</th>
										<%-- <span id="eaicInsured" style="display: block;">${plan.eaicYearlyCoverage}</span> --%>

										<td><strong><span>
													<ul>
														<li>Death and total disability-Rs<fmt:formatNumber
																type="number" minFractionDigits="2"
																maxFractionDigits="2"
																value="${plan.eaicFullDisabilityCoverage}" /></li>
														<li>Medical expenses due to accident -Rs<fmt:formatNumber
																type="number" minFractionDigits="2"
																maxFractionDigits="2"
																value="${plan.eaicPartialDisabilityCoverage}" /></li>
													</ul>
											</span></strong></td>

									</tr>
									<tr>
										<th>Accidental Insurance Coverage(AIC) Premium</th>
										<td><strong>Rs.<span id="eaicDeduction"><fmt:formatNumber
														type="number" minFractionDigits="2" maxIntegerDigits="3"
														value="${plan.eaicYearlyDeduction}" /></span> per member yearly,
												Rs.<fmt:formatNumber type="number" minFractionDigits="2"
													maxIntegerDigits="3"
													value="${plan.eaicYearlyDeduction / 12}" /> monthly
										</strong></td>
									</tr>
								</c:when>
								<c:otherwise>
									<th colspan="2">Accidental Insurance Coverage(AIC) cannot
										be subscribed</th>
								</c:otherwise>
							</c:choose>
							<!-- 	<tr>
								<td colspan="2">

									<h5>Loyalty Scheme</h5>
									<ul>
										<li>Organization will pay the premium of employees and
											their spouse those who completes 5 years.</li>
										<li>Organization will pay the full premium of employees
											those who completes 3 years.</li>
										<li>Organization will pay half the premium of employees
											who is lesser than 3 years.</li>
										<li>The above scheme is not available for Sesame
											employees.</li>
									</ul>

								</td>
							</tr> -->
							<tr>
								<th>Plan Claims & Deductions</th>
								<td>
									<ul>
										<li><a href="#viewSchemeModal">View
												Contributions/Deductions</a></li>
										<li><a href="#viewTreatmentsModal">View Treatments
												Applicable</a></li>
									</ul>
								</td>
							</tr>
						</table>

						<table class="table striped table-borderd">
							<thead>
								<tr>
									<th>Dependents</th>
									<th>Relationship</th>
									<th>Date of Birth</th>
									<th>Gender</th>
									<th>Employee Yearly Contribution(Rs.)</th>
									<th>Company Yearly Contribution(Rs.)</th>
									<th>AIC Contribution(Rs.)</th>
									<th>Insurance</th>
									<c:if test="${plan.eaicIncluded}">
										<th>AIC</th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${eligibleDependents}" var="dep">
									<tr>
										<td>${dep.depName}</td>
										<td>${dep.depRelationship}</td>
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${dep.depDateOfBirth}" /></td>
										<td><c:choose>
												<c:when test="${dep.depGender eq 'F'}">
													<i class="fa fa-female" aria-hidden="true"></i>
												</c:when>
												<c:otherwise>
													<i class="fa fa-male" aria-hidden="true"></i>
												</c:otherwise>
											</c:choose></td>
										<td>${dep.employeeContribution}</td>
										<td>${dep.companyContribution}</td>
										<td>${dep.eaicYearlyDeduction}</td>
										<td><c:choose>
										<c:when test="${dep.isSelf}">
													<input type="checkbox" id="insuranceDep${dep.depId}"
														class="ins_opt_checkbox opt_checkbox" value="${dep.depId}"
														checked="checked" disabled=disabled>
												</c:when>
												<c:when test="${dep.isINSEnroled}">
													<input type="checkbox" id="insuranceDep${dep.depId}"
														class="ins_opt_checkbox opt_checkbox" value="${dep.depId}"
														checked="checked">
												</c:when>
												<%-- <c:when test="${saved}">
													<input type="checkbox" id="insuranceDep${dep.depId}"
														class="ins_opt_checkbox opt_checkbox" value="${dep.flag}">
												</c:when> --%>
												<c:otherwise>
													<input type="checkbox" id="insuranceDep${dep.depId}"
														class="ins_opt_checkbox opt_checkbox" value="${dep.depId}">
												</c:otherwise>
											</c:choose> <label for="insuranceDep${dep.depId}"></label> <input
											id="empYearlyDeductions${dep.depId}" type="hidden"
											value="${dep.employeeContribution}" /> <input
											id="cmpYearlyDeductions${dep.depId}" type="hidden"
											value="${dep.companyContribution}" /></td>
										<c:if test="${plan.eaicIncluded}">
											<td><c:choose>
													<c:when test="${dep.isEAICEnroled}">
													<input type="checkbox" id="eaicDep${dep.depId}"
															class="eaic_opt_checkbox opt_checkbox"
															value="${dep.depId}" checked="checked">
													</c:when>
													<c:when test="${dep.isSelf}">
													<input type="checkbox" id="eaicDep${dep.depId}"
															class="eaic_opt_checkbox opt_checkbox"
															value="${dep.depId}">
													</c:when>
													 <c:when test="${dep.isINSEnroled and !dep.isEAICEnroled}">
													<input type="checkbox" id="eaicDep${dep.depId}"
															class="eaic_opt_checkbox opt_checkbox"
															value="${dep.depId}">
													</c:when> 
													<c:otherwise>
														<input type="checkbox" id="eaicDep${dep.depId}"
															class="eaic_opt_checkbox opt_checkbox"
															value="${dep.depId}" disabled>
													</c:otherwise>
												</c:choose><label for="eaicDep${dep.depId}"></label></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						 <%-- <a
							href="<%=request.getContextPath()%>/home/myInsurancePlan/planDetails/${plan.insPlanId}/optit"
							class="btn yellow darken-3" id="saveWithoutSubmit"
							value="save Without Submit" name="savewithoutsubmit"> Save
							without submit</a> --%> 
							
							
					
							
					
					<c:choose>
					<c:when test="${isAdmin}">
					
					<form action="/home/controlPanel/insurancePlans/optedEmployees/${planEmployeeId}/addDependents/enroll" method="post" id="insEnrollmentForm">
					<input type="hidden" name="loyaltyId" id="loyaltyId"
					value="${loyalty.insPlanLoyaltyLevelsId}" /> 
					<input type="hidden"
					name="insEnrolledDep" id="insEnrolledDep" value="${selfId}," /> <input
					type="hidden" name="eaicEnrolledDep" id="eaicEnrolledDep" /> <input
					type="hidden" name="cmpYearlyDeduction" id="cmpYearlyDeduction" />
				<input type="hidden" name="empYearlyDeduction"
					id="empYearlyDeduction" />
					<a href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees"
							class="btn yellow darken-3">Go Back</a>
							<input type="hidden" name="loyaltyId" id="loyaltyId"
					value="${loyalty.insPlanLoyaltyLevelsId}" /> <input type="hidden"
					name="insEnrolledDeps" id="insEnrolledDeps" value="${selfId}," /> <input
					type="hidden" name="eaicEnrolledDeps" id="eaicEnrolledDeps" /> <input
					type="hidden" name="cmpYearlyDeductions" id="cmpYearlyDeductions" />
				<input type="hidden" name="empYearlyDeductions"
					id="empYearlyDeductions" />
					<button  id="btnChoosePlan"
					class="waves-effect waves-light btn">Enroll Now</button>
					
					</form>
					</c:when>
					<c:otherwise>
					
					<form action="${plan.insPlanId}/optits" method="post" id="insEnrollmentForm">
					<input type="hidden" name="loyaltyId" id="loyaltyId"
					value="${loyalty.insPlanLoyaltyLevelsId}" /> 
					<input type="hidden"
					name="insEnrolledDep" id="insEnrolledDep" value="${selfId}," /> <input
					type="hidden" name="eaicEnrolledDep" id="eaicEnrolledDep" /> <input
					type="hidden" name="cmpYearlyDeduction" id="cmpYearlyDeduction" />
				<input type="hidden" name="empYearlyDeduction"
					id="empYearlyDeduction" />
					<a href="<%=request.getContextPath()%>/home/myInsurancePlan"
							class="btn yellow darken-3">Go Back</a>
							<button type="submit" name="savewithoutsubmit" value="savewithoutsubmit"
								class="btn yellow darken-3" id="save" >Save without Submit</button>
								<a href="#" id="btnChoosePlan" class="waves-effect waves-light btn">Enroll Now</a>
								</form>
					
					</c:otherwise>
					</c:choose>
					
								
						</form>
							

						



					</div>
				</div>
			</div>
			</section>
		</div>
	</div>

	<!-- View Scheme Modal Structure -->
	<div id="viewSchemeModal" class="modal modal-fixed-footer">
		<div class="modal-content">
			<h4>View Scheme (Contributions/Deductions)</h4>
			<%-- 	<c:if test="${loyaltyApplicable }">
				<div class="alert alert-info">
					<strong>Loyalty </strong>${loyalty.loyaltyLevelName}
				</div>
			</c:if> --%>
			<table class="table striped table-borderd">
				<thead>
					<th>Dependent</th>
					<th>Employee Contribution</th>
					<th>Company Contribution</th>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${loyaltyApplicable}">
							<c:forEach items="${loyalty.details}" var="detail">
								<tr>
									<td>${detail.depRelationship}</td>
									<c:choose>
										<c:when test="${detail.employeeYearlyDeduction  eq 0}">
											<td>Nil</td>
										</c:when>

										<c:otherwise>
											<td>Rs. <fmt:formatNumber type="number"
													minFractionDigits="2"
													value="${detail.employeeYearlyDeduction}"
													maxFractionDigits="2"></fmt:formatNumber>, Rs. <fmt:formatNumber
													type="number" minFractionDigits="2"
													value="${detail.employeeYearlyDeduction / 12}"
													maxFractionDigits="2"></fmt:formatNumber> per month
											</td>

										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${detail.companyYearlyDeduction  eq 0}">
											<td>Nil</td>
										</c:when>
										<c:otherwise>
											<td>Rs. <fmt:formatNumber type="number"
													minFractionDigits="2"
													value="${detail.companyYearlyDeduction}"
													maxFractionDigits="2"></fmt:formatNumber>, Rs. <fmt:formatNumber
													type="number" minFractionDigits="2"
													value="${detail.companyYearlyDeduction / 12}"
													maxFractionDigits="2"></fmt:formatNumber> per month
											</td>

										</c:otherwise>

									</c:choose>


								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach items="${plan.dependentDetails}" var="detail">
								<tr>
									<td>${detail.depRelationship}</td>
									<c:choose>
										<c:when test="${detail.empYearlyDeduction  eq 0}">
											<td>Nil</td>
										</c:when>

										<c:otherwise>
											<td>Rs. <fmt:formatNumber type="number"
													minFractionDigits="2" value="${detail.empYearlyDeduction}"
													maxFractionDigits="2"></fmt:formatNumber>, Rs. <fmt:formatNumber
													type="number" minFractionDigits="2"
													value="${detail.empYearlyDeduction / 12}"
													maxFractionDigits="2"></fmt:formatNumber> per month
											</td>

										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${detail.cmpYearlyDeduction  eq 0}">
											<td>Nil</td>
										</c:when>
										<c:otherwise>
											<td>Rs. <fmt:formatNumber type="number"
													minFractionDigits="2" value="${detail.cmpYearlyDeduction}"
													maxFractionDigits="2"></fmt:formatNumber>, Rs. <fmt:formatNumber
													type="number" minFractionDigits="2"
													value="${detail.cmpYearlyDeduction / 12}"
													maxFractionDigits="2"></fmt:formatNumber> per month
											</td>

										</c:otherwise>

									</c:choose>


								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<div class="modal-footer">
			<a href="#"
				class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
		</div>
	</div>


	<!-- Treatments Modal Structure -->
	<div id="viewTreatmentsModal" class="modal modal-fixed-footer">
		<div class="modal-content">
			<h4>View Treatment Details</h4>

			<c:choose>
				<c:when test="${plan.othTreatmentsAppicable}">
					<div class="alert alert-info">
						Coverage is available for other treatments as well. <strong>Maximum
							Coverage will be determined by the Insurance Desk</strong>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-warning">
						<strong>Oops...!</strong> Covered only for the below Treatments...
					</div>
				</c:otherwise>
			</c:choose>

			<table class="table striped table-borderd">
				<thead>
					<th>Treatments</th>
					<th>Average Expenditure(Rs.)</th>
					<th>Maximum Coverage(Rs.)</th>
					<th>Percentage (%)</th>
				</thead>
				<tbody>
					<c:forEach items="${plan.treatments}" var="trtmnt">
						<tr>
							<td>${trtmnt.treatment.treatmentName}</td>
							<td>${trtmnt.treatment.averageAmount}</td>
							<td>${trtmnt.maxCoverage}</td>
							<td>${trtmnt.maxCoveragePercentage}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
		<div class="modal-footer">
			<a href="#"
				class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
		</div>
	</div>

	<!-- Confirm Enrollment Modal Structure -->
	<div id="confirmEnroll" class="modal modal-fixed-footer">
		<div class="modal-content">
			<h4>Confirm Enrolling Plan</h4>
			<p>You are going to Enrol ${plan.planName} for this FY. The
				details of your Plan is given below</p>
			<span id="planDetailsModal"></span>
			<div class="row">
				<div style="" class="col-md-12 padding_left_0">
					<ul>

						<li><p style="color: blue; font-size: 15px">Conditions</p></li>

						<li>1. Enrollment to plan is possible once in the insurance
							policy irrespective of employee’s Date Of Joining.</li>
						<li>2. Once enrolled, cannot be rolled back.</li>
						<li>3. Optional for employees covered under ESIC.</li>
						<li>4. Employees moving out of ESIC would be given option to
							enroll under this plan from the month of the employee moving out
							of ESIC.</li>
						
						<li>5. Adding dependents at the time of claim or event of
							claim is not permissible.</li>
						<li>6. Adding of dependent is restricted after the time of
							enrollment, additions of spouse and new born is permissible
							within two weeks of marriage/child birth.</li>
						
								<li>7. The premium for the employees and their dependents joining between the Insurance periods will be deducted from the month of joining and for the remaining Policy Period. </li>
								<li>8. On submission of claim the total premium for the Policy Period for self and all the enrolled dependents will be deducted irrespective of the claim amount in the claim submitted month’s payroll. </li>
								<li>9. The total premium for the entire Policy Period for self and all the enrolled dependents will be deducted irrespective of the claim amount in the claim submitted month for the employees joining in between the policy period. </li>
								<li>10. Please refer Policy Document & Guidelines-Medical Insurance in mirror documents for more details. </li>
<li><b> 11. No other additions/removal of dependents shall
								be entertained, claims shall not be processed for any undeclared
								dependents. </b></li>
					</ul>
				</div>

			</div>
			<!-- <p ><i style="color: red;"> I read and understood the group mediclaim insurance policy and agreed to abide the same.</i> Please confirm to proceed.</p> -->
			<div class="row">
				<div class="col-md-12 padding_left_0">
					<fieldset class="form-group">
						<input type="checkbox" id="agreeTerms" name="agreeTerms" /> <label
							for="agreeTerms">I read and understood the policy,
							conditions, and agree to abide the same. Please confirm to
							proceed </label>
					</fieldset>
				</div>
			</div>

		</div>
		<div class="modal-footer">
		<c:choose>
		<c:when test="${isAdmin}">
		<form action="/home/controlPanel/insurancePlans/optedEmployees/${planEmployeeId}/addDependents/enroll" method="POST">
				<%-- <input type="hidden" name="loyaltyId" id="loyaltyId"
					value="${loyalty.insPlanLoyaltyLevelsId}" /> <input type="hidden"
					name="insEnrolledDeps" id="insEnrolledDeps" value="${selfId}," /> <input
					type="hidden" name="eaicEnrolledDeps" id="eaicEnrolledDeps" /> <input
					type="hidden" name="cmpYearlyDeductions" id="cmpYearlyDeductions" />
				<input type="hidden" name="empYearlyDeductions"
					id="empYearlyDeductions" /> --%>
				<button disabled id="confirmButton"
					class="modal-action modal-close waves-effect waves-green btn-flat btn-primary">
					Confirm</button>
				<a href="#"
					class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
			</form>
		
		</c:when>
		<c:otherwise>
		<form action="${plan.insPlanId}/optit" method="POST">
				<input type="hidden" name="loyaltyId" id="loyaltyId"
					value="${loyalty.insPlanLoyaltyLevelsId}" /> <input type="hidden"
					name="insEnrolledDeps" id="insEnrolledDeps" value="${selfId}," /> <input
					type="hidden" name="eaicEnrolledDeps" id="eaicEnrolledDeps" /> <input
					type="hidden" name="cmpYearlyDeductions" id="cmpYearlyDeductions" />
				<input type="hidden" name="empYearlyDeductions"
					id="empYearlyDeductions" />
				<button disabled id="confirmButton"
					class="modal-action modal-close waves-effect waves-green btn-flat btn-primary">
					Confirm</button>
				<a href="#"
					class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
			</form>
		
		</c:otherwise>
		</c:choose>
		
		
			
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
		$('.opt_checkbox')
				.on(
						'change',
						function() {
							var eaicUnitContribution = parseFloat($(
									'#eaicDeduction').html());
							var yearlyEmployeeDeduction = 0;
							var yearlyCompanyDeduction = 0;
							var eaicTotalContribution = 0;
							var insTotalEmployeeContribution = 0;
							var insEnrolledDependents = "";
							var eaicEnrolledDependents = "";
							var depId = $(this).val();
							if (this.checked) {
								
							} else {
								
								$('#eaicDep' + depId).removeAttr('checked');
							}
							$('.eaic_opt_checkbox')
									.each(
											function() {
												if (this.checked) {
													eaicTotalContribution += eaicUnitContribution;

													eaicEnrolledDependents += ($(
															this).val() + ",");
												}
											});
							$('.ins_opt_checkbox')
									.each(
											function() {
												if (this.checked) {
													var depId = $(this).val();
													var companyContribution = $(
															'#cmpYearlyDeductions'
																	+ depId)
															.val();
													
													var employeeContribution = $(
															'#empYearlyDeductions'
																	+ depId)
															.val();
													insTotalEmployeeContribution += parseFloat(employeeContribution);
													yearlyCompanyDeduction += parseFloat(companyContribution);

													insEnrolledDependents += ($(
															this).val() + ",");
												}
											});

							yearlyEmployeeDeduction = eaicTotalContribution
									+ insTotalEmployeeContribution;

							$('#totalEmpContribution').html(
									yearlyEmployeeDeduction.toFixed(2));
							$('#totalCmpContribution').html(
									yearlyCompanyDeduction.toFixed(2));
							$('#totalMonthlyDeduction').html(
									(yearlyEmployeeDeduction / 12).toFixed(2));

							$('#insEnrolledDeps').val(insEnrolledDependents);
							$('#insEnrolledDep').val(insEnrolledDependents);
							$('#eaicEnrolledDep').val(eaicEnrolledDependents);
							$('#eaicEnrolledDeps').val(eaicEnrolledDependents);

						});
		$('.ins_opt_checkbox').on('change', function() {
			var depId = $(this).val();
			if (this.checked) {
				$('#eaicDep' + depId).removeAttr('disabled');
			} else {
				$('#eaicDep' + depId).attr('disabled', 'true');
				$('#eaicDep' + depId).removeAttr('checked');
			}
		});

		$('#agreeTerms').click(function() {
			//check if checkbox is checked
			if ($(this).is(':checked')) {

				$('#confirmButton').removeAttr('disabled'); //enable input

			} else {
				$('#confirmButton').attr('disabled', true); //disable input
			}
		});
		
		$('#save').click(function() {
			alert("Plan info saved successfully!");
			
		});

		$("#btnChoosePlan")
				.on(
						"click",
						function() {
							
								var oneOptionSelected = false;
								$('.opt_checkbox').each(function() {
									if (this.checked) {
										oneOptionSelected = true;
									}
								});
								if (oneOptionSelected) {
									$(this).attr('href', '#confirmEnroll');
									$("#planDetailsModal").html(
											$("#myPlanDetails").html());

									$('#cmpYearlyDeductions').val(
											$('#totalCmpContribution').html());
									$('#cmpYearlyDeduction').val(
											$('#totalCmpContribution').html());
									$('#empYearlyDeductions').val(
											$('#totalEmpContribution').html());
									$('#empYearlyDeduction').val(
											$('#totalEmpContribution').html());
								} else {
									$(this).attr('href', '#');
									alert('Please select atleast one memeber');
								}
							
							
						});
		
		$("#save")
		.on(
				"click",
				function() {
					var oneOptionSelected = false;
					$('.opt_checkbox').each(function() {
						if (this.checked) {
							oneOptionSelected = true;
						}
					});
					if (oneOptionSelected) {
						$(this).attr('href', '#confirmEnroll');
						$("#planDetailsModal").html(
								$("#myPlanDetails").html());

						$('#cmpYearlyDeductions').val(
								$('#totalCmpContribution').html());
						$('#cmpYearlyDeduction').val(
								$('#totalCmpContribution').html());
						$('#empYearlyDeductions').val(
								$('#totalEmpContribution').html());
						$('#empYearlyDeduction').val(
								$('#totalEmpContribution').html());
					} else {
						$(this).attr('href', '#');
						alert('Please select atleast one memeber');
					}
				});
		
		$(window).load(function() {
			var eaicUnitContribution = parseFloat($(
			'#eaicDeduction').html());
	var yearlyEmployeeDeduction = 0;
	var yearlyCompanyDeduction = 0;
	var eaicTotalContribution = 0;
	var insTotalEmployeeContribution = 0;
	var insEnrolledDependents = "";
	var eaicEnrolledDependents = "";

	$('.eaic_opt_checkbox')
			.each(
					function() {
						if (this.checked) {
							eaicTotalContribution += eaicUnitContribution;

							eaicEnrolledDependents += ($(
									this).val() + ",");
						}
					});
	$('.ins_opt_checkbox')
			.each(
					function() {
						if (this.checked) {
							var depId = $(this).val();
							var companyContribution = $(
									'#cmpYearlyDeductions'
											+ depId)
									.val();
							
							var employeeContribution = $(
									'#empYearlyDeductions'
											+ depId)
									.val();
							insTotalEmployeeContribution += parseFloat(employeeContribution);
							yearlyCompanyDeduction += parseFloat(companyContribution);

							insEnrolledDependents += ($(
									this).val() + ",");
						}
					});

	yearlyEmployeeDeduction = eaicTotalContribution
			+ insTotalEmployeeContribution;

	$('#totalEmpContribution').html(
			yearlyEmployeeDeduction.toFixed(2));
	$('#totalCmpContribution').html(
			yearlyCompanyDeduction.toFixed(2));
	$('#totalMonthlyDeduction').html(
			(yearlyEmployeeDeduction / 12).toFixed(2));

	$('#insEnrolledDeps').val(insEnrolledDependents);
	$('#insEnrolledDep').val(insEnrolledDependents);
	$('#eaicEnrolledDep').val(eaicEnrolledDependents);
	$('#eaicEnrolledDeps').val(eaicEnrolledDependents);

		});
		
		
	</script>
</body>
</html>