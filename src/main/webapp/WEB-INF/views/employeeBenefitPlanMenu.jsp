<%@page import="com.speridian.benefits2.model.pojo.BenefitPlan"%>
<%@page import="com.speridian.benefits2.service.BenefitPlanService"%>
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlanEmployee"%>
<%@page import="com.speridian.benefits2.model.pojo.Employee"%>
<%@page import="com.speridian.benefits2.model.pojo.TaxRegime"%>
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	function myfunction(val1) {
		$('#cPlanId').val(val1);
	}
/* 	function validationFiled(){
		if($('#reasonId').val()==0)){
			document.getElementById("otherReason").required = true;
		}
		
	} */
	$(document).ready(function() {

		$(".reasonId").change(function() {

			if ($("#reasonId").val() == 0) {

				$("#otherReason").css("display", "block");
				$("input").prop('required',true);
			} else {
				//remove if unchecked

				$("#otherReason").css("display", "none");
			}
		});

		$(".plan_details").on("click", function() {
			var planId = $(this).attr('planId');

			var planName = $("#planName" + planId).val();
			var planDesc = $("#planDesc" + planId).val();

			$("#planDetailsName").html(planName);
			$("#planDetailsDesc").html(planDesc);

		});

	});
</script>
<style>
.card .card-image .card-title {
	color: #fff !important;
	position: relative !important;
	bottom: 0 !important;
	left: 5px !important;
	max-width: 100% !important;
	padding: 0px !important;
}
</style>
<body>
	<%@include file="employeeNavBar.jsp"%>

	<div id="main">
		<div class="wrapper">
			<section id="content" class="">
			<div class="row">
				<div class="row">
					<c:if test="${optStatus eq 1}">
						<div class="alert alert-success">
							<strong>Success!</strong> Your choice has been submitted
						</div>
					</c:if>
					<c:choose>
						<c:when test="${!canSubmit}">
							<c:if test="${employeePlans eq null}">
								<div class="alert alert-warning">
									<strong>Warning!</strong> Sorry, You haven't enrolled any Flexi
									Plans for FY ${appContext.currentFiscalYear}
								</div>
							</c:if>

							<c:if test="${warnMessage ne null}">
								<div class="alert alert-warning">
									<strong>Warning!</strong> ${warnMessage}
								</div>
							</c:if>





							<c:forEach items="${employeePlans}" var="plan">
								<input type="hidden"
									id="planName${plan.benefitPlan.benefitPlanId}"
									value="${plan.benefitPlan.planName}" />
								<input type="hidden"
									id="planDesc${plan.benefitPlan.benefitPlanId}"
									value="${plan.benefitPlan.planDesc}" />
								<div class="col s12 m6 l4">
									<div class="card blue-grey darken-1 block_main">
										<div class="card-image">
											<img src="${plan.benefitPlan.logoContent}"
												style="height: 150px"> <span class="card-title">${plan.benefitPlan.planName}</span>
											<a href="#planDetailsModal"
												class="card-title white-text plan_details"
												planId="${plan.benefitPlan.benefitPlanId}">${plan.benefitPlan.planName}</a>


											<input type="hidden" id="planDesc" />
											<c:choose>
												<c:when
													test="${plan.status eq 'HR_APPR' || plan.status eq 'FIN_APPR'}">
													<c:if test="${plan.benefitPlan.claimDocumentsRequired}">
														<a
															class="btn-floating halfway-fab waves-effect waves-light red"
															href="<%=request.getContextPath()%>/home/myFlexiPlans/myClaims/new/${plan.planEmployeeId}">
															<i class="fa fa-plus" aria-hidden="true"
															data-toggle="tooltip" data-placement="left"
															title="Create new claim request"> </i>
														</a>
													</c:if>
												</c:when>
												<c:otherwise>
													<br />
													<br />
													Not Approved
											</c:otherwise>
											</c:choose>
										</div>
										<c:if
											test="${plan.status eq 'HR_APPR' || plan.status eq 'FIN_APPR'}">
											<div class="card-action">
												<a
													href="<%=request.getContextPath()%>/home/myFlexiPlans/details/optedPlan/${plan.planEmployeeId}">View
													Details</a>
												<c:choose>
													<c:when test="${plan.benefitPlan.claimDocumentsRequired}">
														<a
															href="<%=request.getContextPath()%>/home/myFlexiPlans/myClaims/${plan.planEmployeeId}">Manage
															Claims </a>
													</c:when>
													<c:otherwise>
														<a
															href="<%=request.getContextPath()%>/home/myFlexiPlans/viewMyDirectClaims/${plan.planEmployeeId}">View
															Issue History</a>
													</c:otherwise>
												</c:choose>

											</div>
										</c:if>
									</div>
								</div>
							</c:forEach>


						</c:when>

						<c:otherwise>
							<c:choose>
								<c:when test="${optStatus eq -1}">
									<div class="alert alert-danger">
										<strong>Oops..!</strong> Something went wrong, please contact
										system administrator
									</div>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${optStatus eq 0}">
											<div class="alert alert-warning">
												<strong>Warning!</strong> You haven't enrolled any Flexi
												Plans for Submission
											</div>
										</c:when>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<c:set var="flag" value="true" />



							<!-- tax Regime-------------------------------------------------------------------------------------------------------------------- -->
                         
                        <%--  <div class="card-title card" >
								<div class="card-body" style="margin: 5px 5px 5px 5px">
									<h5 class="card-title">Select Tax Regime</h5>
									
									<p class="card-text"></p>
									<a href="<%=request.getContextPath()%>/home/myFlexiPlans/taxRegime" style="color:rgb(225,170,77)">Enroll
															Now</a>
								</div>
							</div> --%>
							<div style="margin:10px">
									<div class="card blue-grey darken-1 block_main">
										<div class="card-image">
											
											<span class="card-title"><a href="#planDetailsModal" class="card-title white-text plan_details" >Select Tax Regime</a></span>


										<div class="card-action">
											<%-- <c:choose>
												<c:when test="${NoPlans eq 'No Flexi Plans available'} ">
											<a href="<%=request.getContextPath()%>/home/myFlexiPlans/taxRegime">Enroll
														Now</a>
												</c:when>
												<c:otherwise>
													<a class="white-text">Enrolled</a>
													
												</c:otherwise>
											</c:choose> --%>
											
											<c:if test="${empty availablePlanList}"> <a href="<%=request.getContextPath()%>/home/myFlexiPlans/taxRegime">Enroll
														Now</a> </c:if> 
														<c:if test="${availablePlanList.size() gt 0}"><a class="white-text">Enrolled</a></c:if>


										</div>
									</div>
												
												
											
										</div>
									</div>
								</div>
							

							<!-- tax Regime-------------------------------------------------------------------------------------------------------------------- -->

							<c:forEach items="${availablePlanList}" var="plan">
								<input type="hidden" id="planName${plan.plan.benefitPlanId}"
									value="${plan.plan.planName}" />
								<input type="hidden" id="planDesc${plan.plan.benefitPlanId}"
									value="${plan.plan.planDesc}" />
								<div class="col s12 m6 l4">
									<div class="card blue-grey darken-1 block_main">
										<div class="card-image">
											<img src="${plan.plan.logoContent}" style="height: 150px">
											<span class="card-title"><a href="#planDetailsModal"
												class="card-title white-text plan_details"
												planId="${plan.plan.benefitPlanId}">${plan.plan.planName}</a></span>

											<input type="hidden" value="${plan.plan.benefitPlanId}"
												class="currentPlanId" name="currentPlanId"
												id="currentPlanId" />
											<c:choose>
												<c:when test="${plan.planStatus eq 'NOT_OPTED'}">
													<div class="card-action">

														<a
															href="<%=request.getContextPath()%>/home/myFlexiPlans/details/${plan.plan.benefitPlanId}">Enroll
															Now</a> <a href="#optOutModal" class="" name="btnChoosePlan"
															id="" onclick="myfunction('${plan.plan.benefitPlanId}')">Don't
															want this !!</a>
													</div>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${plan.planStatus eq 'DENIED'}">
															<div class="card-action">
																<a class="white-text">Not Enrolled</a>
															</div>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${plan.planStatus eq 'OPTED'}">
																	<c:if test="${plan.plan.claimDocumentsRequired}">
																		<a
																			class="btn-floating halfway-fab waves-effect waves-light red"
																			href="<%=request.getContextPath()%>/home/myFlexiPlans/myClaims/new/${plan.planEmployeeId}">
																			<i class="fa fa-plus" aria-hidden="true"
																			data-toggle="tooltip" data-placement="left"
																			title="Create new claim request"> </i>
																		</a>
																	</c:if>
																	<div class="card-action">
																		<a
																			href="<%=request.getContextPath()%>/home/myFlexiPlans/details/optedPlan/${plan.planEmployeeId}">View
																			Details</a>
																		<c:choose>
																			<c:when test="${plan.plan.claimDocumentsRequired}">
																				<a
																					href="<%=request.getContextPath()%>/home/myFlexiPlans/myClaims/${plan.planEmployeeId}">Manage
																					Claims </a>
																			</c:when>
																			<c:otherwise>
																				<a
																					href="<%=request.getContextPath()%>/home/myFlexiPlans/viewMyDirectClaims/${plan.planEmployeeId}">View
																					Issue History</a>
																			</c:otherwise>
																		</c:choose>

																	</div>
																</c:when>
																<c:otherwise>
																	<div class="card-action">
																		<a class="white-text">Enrolled</a>
																	</div>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			</section>
		</div>
	</div>

	<!-- Modal Structure -->
	<div id="optOutModal" class="modal modal-fixed-footer"
		style="height: 500px;">
		<form action="<%=request.getContextPath()%>/home/myFlexiPlans/optout"
			method="POST">
			<div class="modal-content">

				<h4 style="color: #08A9C1;">Confirm Opting Out of Plan</h4>
				<p>You are going to opt out of this plan for FY
					${appContext.currentFiscalYear} as below. Please confirm to
					proceed.</p>
				<span id="planDetailsModal"></span>
				<p>Please provide a valid reason as to why you don't want to opt
					this plan.</p>


				<input type="hidden" class="cPlanId" name="cPlanId" id="cPlanId"  />
				<input type="hidden" name="yrlyOptId" id="yrlyOptId"
					value="${empPlanYearlyOpts.yrlyOptId}" /> <label
					style="font-size: medium; color: black;">Select A Valid
					Reason</label> <select name="reasonId" id="reasonId" class="reasonId"
					style="color: #0AD38A;">

					<c:forEach items="${reasons}" var="reason">
						<option value="${reason.reasonId}">${reason.reasonDesc}</option>

					</c:forEach>
					<option value="0">Others</option>
				</select> <input type="text" placeholder="Enter Reason" 
					style="display: none;" name="otherReason" id="otherReason"/>

			</div>
			<div class="modal-footer">



				<button
					class="modal-action modal-close waves-effect waves-green btn-flat btn-primary"
					style="color: #08A9C1;">Confirm</button>

				<a href="#"
					class="waves-effect waves-red btn-flat btn-primary modal-close">Cancel</a>

				<!-- <a href="#!"
				class="modal-action modal-close waves-effect waves-green btn-flat ">Agree</a> -->
			</div>
		</form>
	</div>



	<div id="planDetailsModal" class="modal modal-fixed-footer"
		style="height: 200px;">

		<div class="modal-content">
			<h4 style="color: #08A9C1;">
				<span id="planDetailsName"></span>-Summary
			</h4>
			<p>
				<span id="planDetailsDesc"></span>
			</p>
		</div>

		<div class="modal-footer">
			<a href="#"
				class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
		</div>

	</div>


	<%@include file="includeFooter.jsp"%>
</body>
</html>