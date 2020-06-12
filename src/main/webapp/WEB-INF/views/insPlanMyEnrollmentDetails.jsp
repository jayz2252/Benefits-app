
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlan"%>
<%@page import="com.speridian.benefits2.service.BenefitPlanService"%>
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlanEmployee"%>
<%@page import="com.speridian.benefits2.model.pojo.Employee"%>
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<body>
	<%@include file="employeeNavBar.jsp"%>
	<div class="col-md-3">
		<div id="claimPercentageGauge" class="sticky_left"></div>
	</div>

	<div id="main">
		<div class="wrapper">
			<section id="content" class="">
			<div class="white col-md-12">
				<div class="row">
					<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">${planEmployee.insPlan.planName}-Enrollment
							Summary</h4>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<table class="table striped table-borderd">
							<tr>
								<th>Enrolled Date</th>
								<td><fmt:formatDate pattern="dd-MMM-yyyy"
										value="${planEmployee.enrolledDate}" /></td>
								<td colspan="2">Effective From <fmt:formatDate
										pattern="dd-MMM-yyyy" value="${planEmployee.effFrom}" /> <c:if
										test="${planEmployee.effTill ne null}">
						to <fmt:formatDate pattern="dd-MMM-yyyy"
											value="${planEmployee.effTill}" />
									</c:if>
								</td>
							</tr>
							<tr>
								<th>Sum Insured</th>
								<td colspan="3"><strong>Rs.<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
											value="${planEmployee.sumInsured / 1}" /> <c:choose>
											<c:when test="${planEmployee.insPlan.planType eq 'FMLY'}">
								shared across enrolled dependents
								</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${planEmployee.insPlan.planType eq 'INDI'}">
								per member
								</c:when>
													<c:otherwise>
														<!-- otherwise I don't know what to do... :-( -->
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
								</strong></td>
							</tr>

							<c:if test="${planEmployee.insPlan.eaicIncluded}">
								<tr>
									<th>Accidental Insurance Coverage(AIC) Sum Insured</th>
									<%-- 	 <td>Rs. ${planEmployee.insPlan.eaicYearlyCoverage / 1}</td>  --%>

									<td>Death and total disability Rs.<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
											value="${planEmployee.insPlan.eaicFullDisabilityCoverage}"/>
										and medical expenses due to accident Rs.<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
											value="${planEmployee.insPlan.eaicPartialDisabilityCoverage}"/></td>
									<%-- <td>Rs. ${planEmployee.insPlan.eaicFullDisabilityCoverage / 1} by Death and total disability 
									
									<th>Medical Expense due to traffic accident</th>
									 <td>Rs. ${planEmployee.insPlan.eaicPartialDisabilityCoverage / 1}</td>  --%>

									<th>Accidental Insurance Coverage(AIC) Deduction</th>
									<td>Rs.<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
											value="${planEmployee.yearlyEaicDeduction / 1}" /> per year,
										Rs.<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
											value="${planEmployee.yearlyEaicDeduction / 12}" /> monthly</td>
								</tr>
							</c:if>

							<c:if test="${planEmployee.isLoyaltyMode}">
								<tr>
									<td colspan="4">Loyalty Scheme
										${planEmployee.loyaltyLevel.loyaltyLevelName} applied
									</th>
								</tr>
							</c:if>

							<tr>
								<th>Claimed Total</th>
								<td><strong>Rs.<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
											value="${planEmployee.amountClaimed}"/></strong></td>
								<th>Balance</th>
								<td><strong>Rs.<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
											value="${planEmployee.sumInsured - planEmployee.amountClaimed}"/></strong></td>
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
								<c:forEach items="${planEmployee.details}" var="dep">
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
					</div>
				</div>
				<div class="row">
					<a href="<%=request.getContextPath()%>/home/myInsurancePlan"
						class="btn yellow darken-3">Go Back</a>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
	 var claimPercentageGauge = new FlexGauge({
         appendTo: '#claimPercentageGauge',
         dialValue: true,
         dialLabel: 'Total Benefits Claimed(%)',
         arcFillInt: ${claimPercentage},
         arcFillTotal: 100
     });
	</script>
</body>
</html>