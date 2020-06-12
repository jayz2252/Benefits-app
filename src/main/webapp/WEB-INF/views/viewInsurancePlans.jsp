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
							<h4 class="h4-responsive">View Insurance Plans</h4>
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
							<table class="table striped table-borderd">
								<thead>
									<tr>
										<th>Plan Name</th>
										<th>Plan Type</th>
										<th>Effective From</th>
										<th>Eff Till</th>
										<th>Yearly Coverage</th>
										<th>View</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${insPlans}" var="insplan">
										<tr>
											<td>${insplan.planName}</td>
											<td>
											<c:choose>
												<c:when test="${insplan.planType eq 'FMLY'}">
													Family Pool
												</c:when>
												<c:when test="${insplan.planType eq 'INDI'}">
													Individual
												</c:when>
											</c:choose>
											</td>
											<td><fmt:formatDate pattern="dd-MM-yyyy"
													value="${insplan.effFrom}" /></td>
											<td><fmt:formatDate pattern="dd-MM-yyyy"
													value="${insplan.effTill}" /></td>
											<td>${insplan.yearlyCoverage}</td>
											<td><a
												href="<%=request.getContextPath()%>/home/controlPanel/viewInsurancePlans/viewPlan/${insplan.insPlanId}"><i
													class="fa fa-eye" aria-hidden="true" data-toggle="tooltip"
													data-placement="left" title="View Plan Details"></i></a></td>
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
