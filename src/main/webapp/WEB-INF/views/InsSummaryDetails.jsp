<!-- 
@author : jithin.kuriakose
@page displays summary of plan going to be created
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
							<h4 class="h4-responsive">Summary</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<%-- div class="md-form">
								<img alt="Plan Logo" src="data:image/png;base64,${planPojo.logo}">
							</div> --%>
						</div>
					</div>

					<%-- <c:if test="${savedStatus ne null}">
						<div class="row">
							<div class="col-md-12">
								<c:choose>
									<c:when test="${savedStatus}">
										<div class="alert alert-success">
											<strong>Success!</strong> Successfully Saved the new Flexi
											Plan
										</div>
									</c:when>
									<c:otherwise>
										<div class="alert alert-danger">
											<strong>Error: </strong> Failed to Save the plan details,
											please contact the System Administrator
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:if> --%>
					<form  id="INSPlanSummaryForm"
						action="InsPlanSummarySave" method="POST">

						<div class="row">
							<div class="col-md-12">
							
							 <table class="table striped table-borderd">
								  <tr><th>Add New INS Plan</th></tr>
								</table>
							
								<table>
									<tr>
										<th>Plan Name</th>
										<td colspan="3">${INSPlanPojo.planName}</td>
									</tr>
									<tr>
										<th>Description</th>
										<td colspan="3">${INSPlanPojo.planDesc}</td>
									</tr>
									<tr>
										<th>Effective From</th>
										<td><fmt:formatDate pattern="dd-MM-yyyy"
												value="${INSPlanPojo.effFrom}" /></td>
										<th>Effective To</th>
										<td><fmt:formatDate pattern="dd-MM-yyyy"
												value="${INSPlanPojo.effTill}" /></td>
									</tr>
									
									<c:if test="${INSPlanPojo.loyaltyLevelIncluded}">
										<tr>
											<td colspan="4">Loyalty Level is Included</td>
										</tr>
									</c:if>
									<c:if test="${INSPlanPojo.eaicIncluded}">
										<tr>
											<td colspan="4">AIC is Included</td>
										</tr>
									</c:if>
									<c:if test="${INSPlanPojo.othTreatmentsAppicable}">
										<tr>
											<td colspan="4">Other Treatment is Applicable</td>
										</tr>
									</c:if>
									<c:if test="${INSPlanPojo.active}">
										<tr>
											<td colspan="4">This plan is Active</td>
										</tr>
									</c:if>
								</table>
								<br />
								<table class="table striped table-borderd">
								  <tr><th>INS Plan Type Details</th></tr>
								</table>
								<table class="table striped table-borderd">
								    <tr>
										<th>Individual Plan</th>
										<td colspan="3">${INSPlanPojo.planType}</td>
									</tr>
									<tr>
										<th>Total Coverage</th>
										<td colspan="3">${INSPlanPojo.yearlyCoverage}</td>
									</tr>
									
									<c:if test="${INSPlanPojo.eaicIncluded}">
										<tr>
										   <th>Accidental Insurance Coverage(AIC) Yearly Deduction</th> 
											<td>Death and total disability-${planEmployee.insPlan.eaicFullDisabilityCoverage} and medical expenses due to accident -${planEmployee.insPlan.eaicPartialDisabilityCoverage}</td>
										</tr>
										<tr>
										    <th>Accidental Insurance Coverage(AIC) SUM Insured</th>
											<td colspan="4">${INSPlanPojo.eaicYearlyCoverage}</td>
										</tr>
									</c:if>
								</table>
								
								<table class="table striped table-borderd">
									<tr>
										<th colspan="5">Dependent Contribution Details</th>
									</tr>
									<tr>
										<th>Dependent</th>
										<th>Employee Contribution</th>
										<th>Company Contribution</th>
										<th>Coverage</th>
										
									</tr>
									<c:forEach items="${INSPlanPojo.dependentDetails}" var="dep">
										<tr>
											<td>${dep.depRelationship}</td>
											<td>${dep.empYearlyDeduction}</td>
											<td>${dep.cmpYearlyDeduction}</td>
											<td>${dep.yearlyCoverage}</td>
											
										</tr>
									</c:forEach>
								</table> 
								
							<table class="table striped table-borderd">
									<tr>
										<th colspan="5">Amount Allowed for Different Treatment</th>
									</tr>
									<tr>
										<th>Treatment Name</th>
										<th>Description</th>
										<th>Total Coverage Amount</th>
										<th>Percentage of Covered amount</th>
										<th>Maximum Allowed Amount</th>
										
									</tr>
									<c:forEach items="${INSPlanPojo.treatments}" var="treatmnt">
										<tr>
											<td>${treatmnt.treatment.treatmentName}</td>
											<td>${treatmnt.treatment.description}</td>
											<td>${treatmnt.treatment.averageAmount}</td>
											<td>${treatmnt.maxCoveragePercentage}</td>
											<td>${treatmnt.maxCoverage}</td>
											
										</tr>
									</c:forEach>
								</table>
								
								
								<table class="table striped table-borderd">
									<tr>
										<th colspan="5">INS Plan Loyalty Details</th>
									</tr>
									<tr>
										<th>Dependent</th>
										<th>Company Contribution</th>
										<th>Employee Contribution</th>
										
									</tr>
									
									<c:forEach items="${INSPlanPojo.loyaltyLevels.details}" var="loyalty">
										<tr>
											<td>${loyalty.depRelationship}</td>
											<td>${loyalty.companyYearlyDeduction}</td>
											<td>${loyalty.employeeYearlyDeduction}</td>
											
										</tr>
									</c:forEach>
								</table>
								
								
								
								<table class="table striped table-borderd">
								    <tr>
										<th colspan="5">INS Plan Loyalty Details</th>
									</tr>
								    <tr>
										<th>Loyalty Name</th>
										<td colspan="3">${INSPlanPojo.loyaltyLevels.loyaltyLevelName}</td>
									</tr>
									<tr>
										<th>Description</th>
										<td colspan="3">${INSPlanPojo.loyaltyLevels.loyaltyLevelDesc}</td>
									</tr>
									<tr>
										<th>Minimum Year Experience</th>
										<td colspan="3">${INSPlanPojo.loyaltyLevels.loyaltyMinYears}</td>
									</tr>
									<tr>
										<th>Maximum Year Experience</th>
										<td colspan="3">${INSPlanPojo.loyaltyLevels.loyaltyMaxYears}</td>
									</tr>
									
								</table>
							
								
								<br />
								
								
							</div>
						</div>
						<div class="form-group">
									<div class="">
										<input type="submit" class="btn btn-primary" value="Next"/>
									</div>
								</div>
					</form>
				</div>
			
			</div>
			</section>
		
		</div>
	</div>
	
	

	
	
	<%@include file="includeFooter.jsp"%>
	<!-- <script type="text/javascript">
		window.onbeforeunload = function() {
			return "You work will be lost.";
		};
	</script> -->
</body>
</html>