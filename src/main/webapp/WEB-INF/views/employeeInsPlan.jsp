
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

		<h6>Insurance Documents</h6>
		<ul>
			<li><a
				href="<%=request.getContextPath()%>/home/fileDownload?filename=paf.pdf&ext=pdf"
				target="_blank" title="Pre Authorization Form"><i
					class="fa fa-file-pdf-o" aria-hidden="true"></i> Pre authorization
					Form</a></li>
			<li><a
				href="<%=request.getContextPath()%>/home/fileDownload?filename=non_admissible_expenses.xlsx&ext=xlsx"
				target="_blank" title="Non-admissible Expenses Sheet"><i
					class="fa fa-file-excel-o" aria-hidden="true"></i></i> Non-Admissible
					Expenses</a></li>
			<li><a
				href="<%=request.getContextPath()%>/home/fileDownload?filename=health_insurance_process_flow.pdf&ext=pdf"
				target="_blank" title="Insurance Process Flow"><i
					class="fa fa-file-pdf-o" aria-hidden="true"></i> Insurance Process
					Flow</a></li>

			<li><a
				href="<%=request.getContextPath()%>/home/fileDownload?filename=mediclaim_form.pdf&ext=pdf"
				target="_blank" title="Medical claim Form"><i
					class="fa fa-file-pdf-o" aria-hidden="true"></i> Medical claim Form</a></li>
		</ul>

	</div>

	<div id="main">
		<div class="wrapper">
			<section id="content" class="">
			<div class="row">
				<div class="row">

					<c:choose>
						<c:when test="${optingEnabled}">
							<c:if test="${enrolLastDate ne null}">
								<div class="alert alert-warning">
									<marquee>
										<!-- <strong>Warning!</strong>  -->Last date of enrolment choice is on
										or before
										<fmt:formatDate pattern="dd-MMM-yyyy" value="${enrolLastDate}" />
									</marquee>
								</div>
							</c:if>
							<c:forEach items="${allPlans}" var="plan">
								<div class="col s12 m6 l4">
									<div class="card blue-grey darken-1 block_main">
										<i class="fa fa-medkit fa-6" aria-hidden="true"></i>
										<div class="card-content white-text">
											<span class="card-title">${plan.planName}</span>
											<p>Kindly update your dependent details. If no changes
												made on dependent, then last year data would be taken after
												deadline.</p>
										</div>
										<div class="card-action">
											<a
												href="<%=request.getContextPath()%>/home/myInsurancePlan/planDetails/${plan.insPlanId}">Enroll
												Now</a>
										</div>
									</div>
								</div>
							</c:forEach>

						</c:when>
						<c:when test="${saved}">
							<c:forEach items="${allPlans}" var="plan">
								<div class="col s12 m6 l4">
									<div class="card blue-grey darken-1 block_main">
										<i class="fa fa-medkit fa-6" aria-hidden="true"></i>
										<div class="card-content white-text">
											<span class="card-title">${plan.planName}</span>
											<p>Kindly update your dependent details. If no changes
												made on dependent, then last year data would be taken after
												deadline.</p>
										</div>
										<div class="card-action">
											<a
												href="<%=request.getContextPath()%>/home/myInsurancePlan/planDetails/${plan.insPlanId}">Enroll
												Now</a>
										</div>
									</div>
								</div>
							</c:forEach>

						</c:when>
						<c:otherwise>

							<c:choose>
								<c:when test="${enroled}">
									<div class="card blue-grey darken-1 block_main">
										<i class="fa fa-medkit fa-6" aria-hidden="true"></i>
										<div class="card-content white-text">
											<span class="card-title">${planEmployee.insPlan.planName}</span>
										</div>
										<div class="card-action">

											<c:choose>
												<c:when test="${submitClaimEnabled}">


													<a
														href="<%=request.getContextPath()%>/home/myInsurancePlan/${planEmployee.insPlanEmployeeId}/viewClaims">Manage
														Claims</a>
													<a
														href="<%=request.getContextPath()%>/home/myInsurancePlan/${planEmployee.insPlanEmployeeId}/preAuth/new">New
														Pre Authorization Form</a>

													<a
														href="<%=request.getContextPath()%>/home/myInsurancePlan/${planEmployee.insPlanEmployeeId}/newClaim">New
														Claim</a>
													<c:choose>
														<c:when test="${canSubmitClaim}">
															<a
																class="btn-floating halfway-fab waves-effect waves-light red"
																href="<%=request.getContextPath()%>/home/myInsurancePlan/${planEmployee.insPlanEmployeeId}/newClaim">
																<i class="fa fa-plus" aria-hidden="true"
																data-toggle="tooltip" data-placement="left"
																title="File New Claim"> </i>
															</a>
														</c:when>
														<c:otherwise>
															<a href="<%=request.getContextPath()%>/home/myInsurancePlan/${planEmployee.insPlanEmployeeId}/preAuth/new"
																class="btn-floating halfway-fab waves-effect waves-light red"><i
																class="fa fa-plus" aria-hidden="true"
																data-toggle="tooltip" data-placement="left"
																title="File Pre Authorization"> </i></a>
																
																 
																
														</c:otherwise>

													</c:choose>

												</c:when>
											</c:choose>
											<a
												href="<%=request.getContextPath()%>/home/myInsurancePlan/enrollDetails/${planEmployee.insPlanEmployeeId}">View
												Details</a>


										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="alert alert-danger">
										<strong>Alert! </strong> Last date of enrolment was on or
										before
										<fmt:formatDate pattern="dd-MM-yyyy" value="${enrolLastDate}" />
										, please contact HR team
									</div>
								</c:otherwise>
							</c:choose>

						</c:otherwise>
					</c:choose>

				</div>
			</div>
			</section>

		</div>


		<%@include file="includeFooter.jsp"%>
</body>

</html>