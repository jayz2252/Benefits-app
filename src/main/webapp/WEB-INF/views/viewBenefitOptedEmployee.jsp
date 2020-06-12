<!-- 
@author : swathy.raghu
@page displays details of plan-employee
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
							<h4 class="h4-responsive">Benefit Opted Employee</h4>
						</div>

					</div>



					<div class="row">
						<div class="col-md-12">

							<br />
							<table class="table striped table-borderd">
								<tr>
									<th colspan="5">Employee</th>
								</tr>
								<tr>
									<th>Name</th>
									<th>Designation</th>


								</tr>

								<tr>
									<td>${employeePlan.employee.firstName}
										${employeePlan.employee.lastName}</td>
									<td>${employeePlan.employee.designationName}</td>
								</tr>

							</table>
							<br />
							<table class="table striped table-borderd">
								<tr>
									<th>Plan Name</th>
									<td colspan="3">${employeePlan.benefitPlan.planName}</td>
								</tr>
								<tr>
									<th>Description</th>
									<td colspan="3">${employeePlan.benefitPlan.planDesc}</td>
								</tr>
								<tr>
									<th>Effective From</th>
									<td><fmt:formatDate pattern="dd-MM-yyyy"
											value="${employeePlan.benefitPlan.effFrom}" /></td>
									<th>Effective To</th>
									<td><fmt:formatDate pattern="dd-MM-yyyy"
											value="${employeePlan.benefitPlan.effTill}" /></td>
								</tr>

							</table>
							<br />
							<table class="table striped table-borderd">
								<c:choose>
									<c:when test="${employeePlan.benefitPlan.claimType eq 'CTGY'}">
										<tr>
											<th colspan="5">Category</th>
										</tr>
										<tr>
											<td>Category Name</td>
											<td>${employeePlan.planCategory.categoryName}</td>
										</tr>
										<tr>
											<td>Category Amount</td>
											<td>${employeePlan.planCategory.categoryAmount}</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when
												test="${employeePlan.benefitPlan.claimType eq 'BAND'}">
												<tr>
													<th colspan="5">Band</th>
												</tr>
												<tr>
													<td>Band Name</td>
													<td>${employeePlan.optingBand}</td> 
												</tr>
												<tr>
													<td>Band Amount</td> 
													<td>Rs.${amount}</td>  
												</tr>
											</c:when>
										</c:choose>
									</c:otherwise>
								</c:choose>

								<tr>
									<th colspan="5">Final Amount</th>
								</tr>
								<tr>
									<th>Total Yearly Deduction</th>
									<td>Rs.${employeePlan.yearlyDeduction}<br />,
										Rs.${employeePlan.yearlyDeduction / 12} per month
									</td>



									<td></td>
								</tr>
								<c:if test="${employeePlan.benefitPlan.promptFieldsOnOPT}">
									<c:forEach items="${benefitPlanEmployeeField}" var="field">
										<tr>
											<td>${field.field.uiLabel}</td>
											<td>${field.value}</td>
										</tr>
									</c:forEach>
								</c:if>
							</table>

							<br />

						</div>
					</div>
					<a class="btn yellow darken-3"
						href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees">Back</a>
				</div>


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