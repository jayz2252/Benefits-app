<!-- 
@author : swathy.raghu
@page displays plans that are opted by an employee
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
							<h4 class="h4-responsive">Employee Opted Plans</h4>
						</div>

					</div>



					<div class="row">
						<div class="col-md-12">
							<table class="table striped table-borderd">
								<tr>
									<th>Employee Name</th>
									<td>${bean.employee.firstName}</td>
									<td>${bean.employee.lastName}</td>
								</tr>
								<tr>
									<th>Designation</th>
									<td colspan="3">${bean.employee.designationName}</td>
								</tr>
								<tr>
									<th>Employee Code</th>
									<td colspan="3">${bean.employee.employeeCode}</td>
								</tr>
								<tr>
									<th>Department</th>
									<td colspan="3">${bean.employee.departmentName}</td>
								</tr>
								<tr>
									<th>Parent Office</th>
									<td colspan="3">${bean.employee.parentOffice}</td>
								</tr>
								<tr>
									<th>Band</th>
									<td colspan="3">${bean.employee.band}</td>
								</tr>

							</table>
							<br />
							<c:forEach items="${bean.benefitPlanEmployee}"
								var="benefitPlanEmployee">
								<table class="table striped table-borderd">
									<tr>

										<th>${benefitPlanEmployee.benefitPlan.planName}</th>

									</tr>


									<c:choose>
										<c:when
											test="${benefitPlanEmployee.benefitPlan.claimType eq 'CTGY'}">
											<tr>
												<th colspan="5">Category</th>
											</tr>
											<tr>

												<td colspan="3">${benefitPlanEmployee.planCategory.categoryName}</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when
													test="${benefitPlanEmployee.benefitPlan.claimType eq 'BAND'}">
													<tr>
														<th colspan="5">Band-wise Plan</th>
													</tr>
													<tr>
														<td colspan="3">${benefitPlanEmployee.optingBand}</td>
													</tr>
												</c:when>

											</c:choose>
										</c:otherwise>
									</c:choose>
									<tr>
										<th>Amount Claimed</th>
										<td colspan="3">${benefitPlanEmployee.amountDeducted}</td>
									</tr>
									<tr>
										<th>Amount Deducted</th>
										<td colspan="3">${benefitPlanEmployee.amountClaimed}</td>
									</tr>
									<tr>
										<th><a href="#">View Claims</a></th>

									</tr>
								</table>
								<br />
								<br />
							</c:forEach>
							<br /> <br />


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