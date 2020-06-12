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
							<h4 class="h4-responsive">Manage Benefit Plans</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12 select_block">
							<table class="table striped table-borderd data_table">
								<thead>
									<tr>
										<th>Plan Name</th>

										<th>Claim Type</th>
										<th>Effective From</th>
										<th>Effective Till</th>
										<th>Yearly Claim(Rs.)</th>
										<th>Active</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${plans}" var="plan">
										<tr>

											<td><a
												href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/view/${plan.benefitPlanId}">${plan.planName}</a></td>
											<td><c:choose>
													<c:when test="${plan.claimType eq 'CTGY'}">
											Category-wise
											</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${plan.claimType eq 'BAND'}">
											Band-wise
											</c:when>
															<c:otherwise>
											Flat
											</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose></td>
											<td><fmt:formatDate pattern="dd-MM-yyyy"
													value="${plan.effFrom}" /></td>
											<td><fmt:formatDate pattern="dd-MM-yyyy"
													value="${plan.effTill}" /></td>
											<td>${plan.yearlyClaim}</td>

											<td><input id="active${plan.benefitPlanId}" type="checkbox" checked="${plan.active}"
												disabled="true" /><label for="active${plan.benefitPlanId}"></label></td>
											<td><a
												href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/view/${plan.benefitPlanId}"><i
													class="fa fa-eye" aria-hidden="true" data-toggle="tooltip" data-placement="left" title="Review this Plan"></i></a>  
													<c:choose>
													<c:when test="${plan.active}">
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/switchActive/${plan.benefitPlanId}"><i
															class="fa fa-times-circle-o" aria-hidden="true" data-toggle="tooltip" data-placement="left" title="Disable this Plan(Currently Enabled)"></i></a>
													</c:when>
													<c:otherwise>
														<a
															href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/switchActive/${plan.benefitPlanId}"><i
															class="fa fa-check-square-o" aria-hidden="true" data-toggle="tooltip" data-placement="left" title="Enable this Plan(Currently Disabled)"></i></a>
													</c:otherwise>
												</c:choose> <a
												href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claims/${plan.benefitPlanId}">
												<i class="fa fa-money" aria-hidden="true" data-toggle="tooltip" data-placement="left" title="View All Pending Claims for this Plan"></i>
												</a></td>
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
</body>
</html>