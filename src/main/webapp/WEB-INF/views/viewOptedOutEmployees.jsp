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
							<h4 class="h4-responsive">Opted Out Employees</h4>
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
							<form:form  action="search" method="POST" modelAttribute="bean">
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<form:select path="benefitPlanId" name="flexiPlan"  >

													<c:forEach items="${plans}" var="plan">
														<option value="${plan.benefitPlanId}" ${bean.benefitPlanId eq plan.benefitPlanId ? 'selected="selected"' : ''}>${plan.planName}</option>
													</c:forEach>
												</form:select> <label for="flexiPlan">Select Flexi Plan</label>
											</div>

											
											<div class="col-md-6 padding_left_0">
												
												<form:select path="fiscalYear">


													<c:forEach items="${appContext.fiscalYears}" var="fy">
														<option value="${fy}" ${bean.fiscalYear eq fy ? 'selected="selected"' : ''}>${fy}</option>
													</c:forEach>

												</form:select>

												 <label for="FiscalYear">Select Fiscal Year</label>
											</div>
											
												
											<div class="row">
												<div class="md-form">
													<div class="col-md-6 padding_left_0">
														<input type="submit" class="btn btn-primary"
															value="Search">
													</div>
												</div>
											</div>

										</div>
									</div>
								</div>
							</form:form>
						</div>
					</div>




					<div class="col-md-12">
						<table class="tablePlanEmployees table striped table-borderd data_table">
							<thead>
								<tr>

									<th>Employee Code</th>
									<th>Employee Name</th>
									<th>Designation</th>
									<th>Plan Name</th>
									<th>Reason for Opting Out</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${planDenies}" var="planDenies">
									<tr>

										<td>${planDenies.employee.employeeCode}</td>
										<td>${planDenies.employee.firstName}
											${planDenies.employee.lastName}</td>
										<td>${planDenies.employee.designationName}</td>
										<td>${planDenies.benefitPlan.planName}</td>
										<c:choose>
											<c:when test="${planDenies.planDenyReasonsMaster eq null}">
												<td>${planDenies.otherReason}</td>
											</c:when>
											<c:otherwise>
												<td>${planDenies.planDenyReasonsMaster.reasonDesc}</td>
											</c:otherwise>
										</c:choose>




									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>


				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
</body>
</html>