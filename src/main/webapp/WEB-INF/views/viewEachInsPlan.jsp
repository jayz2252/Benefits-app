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
							<h4 class="h4-responsive">${insPlan.planName}-Summary</h4>
						</div>
						<div class="col-sm-6 col-md-3"></div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<table class="table striped table-borderd">
								<tr>
									<th>Plan Name</th>
									<td colspan="3">${insPlan.planName}</td>
								</tr>
								<tr>
									<th>Description</th>
									<td colspan="3">${insPlan.planDesc}</td>
								</tr>

								<tr>
									<th>Plan Type</th>
									<c:choose>
										<c:when test="${insPlan.planType eq 'FMLY'}">
											<td colspan="3">Family Pool</td>
										</c:when>
										<c:when test="${insPlan.planType eq 'INDI'}">
											<td colspan="3">Individual</td>
										</c:when>
									</c:choose>

								</tr>

								<tr>
									<th>Yearly Coverage</th>
									<td colspan="3">${insPlan.yearlyCoverage}</td>
								</tr>

								<tr>
									<th>Effective From</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MM-yyyy"
													value="${insPlan.effFrom}" /></td>
								</tr>

								<tr>
									<th>Effective Till</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MM-yyyy"
											value="${insPlan.effTill}" /></td>
								</tr>

								<tr>
									<th>Other Treatments Applicable</th>
									<c:choose>
										<c:when test="${insPlan.othTreatmentsAppicable eq true}">
											<td colspan="3">Yes</td>
										</c:when>
										<c:when test="${insPlan.othTreatmentsAppicable eq false}">
											<td colspan="3">No</td>
										</c:when>
									</c:choose>
								</tr>

								<tr>
									<th>Accidental Insurance Coverage(AIC) Included</th>
									<c:choose>
										<c:when test="${insPlan.eaicIncluded eq true}">
											<td colspan="3">Yes</td>
										</c:when>
										<c:when test="${insPlan.eaicIncluded eq false}">
											<td colspan="3">No</td>
										</c:when>
									</c:choose>
								</tr>


								<c:if test="${insPlan.eaicIncluded eq true}">
									<tr>
										<td><h4 class="h4-responsive">Employee Accidental
												Insurance Coverage</h4></td>
									</tr>
									<tr>
										<th>Yearly Deduction</th>
										<td colspan="3">${insPlan.eaicYearlyDeduction}</td>
									</tr>

									<tr>
										<th>Yearly Coverage</th>
										<td colspan="3">${insPlan.eaicYearlyCoverage}</td>
									</tr>
								</c:if>


							</table>

							<table class="table striped table-borderd">

								<tr>
									<td><h4 class="h4-responsive">Treatment Details</h4></td>
								</tr>
								<tr>

									<th>Treatment Name</th>
									<th>Average Expenditure(Rs)</th>
									<th>Max. Coverage(Rs)</th>
									<th>Max. Coverage(%)</th>

								</tr>


								<tbody>
									<c:forEach items="${insPlan.treatments}" var="insTreatment">
										<tr>
											<td>${insTreatment.treatment.treatmentName}</td>
											<td>${insTreatment.treatment.averageAmount}</td>
											<td>${insTreatment.maxCoverage}</td>
											<td>${insTreatment.maxCoveragePercentage}</td>
										</tr>
									</c:forEach>
								</tbody>

<tr>
									<td><h4 class="h4-responsive">Contributions/Deductions</h4></td>
								</tr>
								<tr>
									<th>Dependent</th>
									<th>Employee Contribution</th>
									<th>Company Contribution</th>
									</tr>
								<tbody>
									<c:choose>
										<c:when test="${insPlan.loyaltyLevelIncluded eq true}">
										<c:forEach items="${insPlan.dependentDetails}" var="depDetail">
												<tr>
													<td>${depDetail.depRelationship}</td>
													<td>Rs. ${depDetail.empYearlyDeduction}, Rs.
														${depDetail.empYearlyDeduction / 12} per month</td>
													<td>Rs. ${depDetail.cmpYearlyDeduction}, Rs.
														${depDetail.cmpYearlyDeduction / 12} per month</td>
												</tr>
											</c:forEach>
										</c:when>
									</c:choose>
								</tbody>
</table>
								<a class="btn yellow darken-3"
									href="<%=request.getContextPath()%>/home/controlPanel/viewInsurancePlans">Back</a>
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
