
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
	
	<%
					String slabChangeSuccessMsg = request.getParameter("slabChangeSuccessMsg");
					if(slabChangeSuccessMsg!=null && !("").equals(slabChangeSuccessMsg))
					{
					%>
					<div class="alert alert-success">
									<strong>Done!</strong><%= slabChangeSuccessMsg %>
								</div>
					
					<%} %>
	
	<c:if test="${slabChangeSuccessMsg ne null }">
								<div class="alert alert-success">
									<strong>Done!</strong> ${slabChangeSuccessMsg}
								</div>
	</c:if> 
	<%
					String volAmountSuccessMsg = request.getParameter("volAmountSuccessMsg");
					if(volAmountSuccessMsg!=null && !("").equals(volAmountSuccessMsg))
					{
					%>
					<div class="alert alert-success">
									<strong>Done!</strong><%= volAmountSuccessMsg %>
								</div>
					
					<%} %>
	
<%-- 	<%
					String optOutSuccessMessage = request.getParameter("optOutSuccessMessage");
					if(optOutSuccessMessage!=null && !("").equals(optOutSuccessMessage))
					{
					%>
					<div class="alert alert-success">
									<strong>Done!</strong><%= optOutSuccessMessage %>
								</div>
					
					<%} %> --%>
	
	
	<c:if test="${volAmountSuccessMsg ne null }">
								<div class="alert alert-success">
									<strong>Done!</strong> ${volAmountSuccessMsg}
								</div>
	</c:if> 
	<c:if test="${optOutSuccessMessage ne null }">
								<div class="alert alert-success">
									<strong>Done!</strong> ${optOutSuccessMessage}
								</div>
	</c:if> 
	<c:if test="${optOutErrorMessage ne null }">
								<div class="alert alert-danger">
									<strong>INFO!</strong> ${optOutErrorMessage}
								</div>
	</c:if> 
	
		 <h6>Employee Provident Fund</h6>
		  <c:if test="${enroled}">
		 <div class="alert alert-success">
			<strong>Info - </strong> 
			Your UAN Number : ${uanNumber}
			
		</div> 
		</c:if>
		<c:if test="${pfacno ne null}">
		<div class="alert alert-success">
			<strong>PF ACCOUNT #</strong> : ${pfacno}
		</div> 
		 </c:if>
		
	</div>

	<div id="main">
		<div class="wrapper">
			<section id="content" class="">
			<div class="row">
				<div class="row">
					
					<c:choose>
						<c:when test="${optingEnabled}">

								<div class="col s12 m6 l4">
									<div class="card blue-grey darken-1 block_main">
										<i class="fa fa-money fa-6" aria-hidden="true"></i>
										<div class="card-content white-text">
											<span class="card-title">Employee Provident Fund</span>
											<p></p>
										</div>
										<div class="card-action">
										<c:choose>
										<c:when test="${optOutEnabled}">
										
										<c:choose>
										
											<c:when test="${planSlab  eq 'mandatory'}">
					 						<a
												href="<%=request.getContextPath()%>/home/pfEnrollment">PF details submission</a>
				 								</c:when>
				 								<c:otherwise>
											<a
												href="<%=request.getContextPath()%>/home/pfEnrollment">Enroll
												Now</a>
												
												<a id="optOutLink" href="#optOutModal">Opt Out</a>
												</c:otherwise>
											</c:choose>
											
										</c:when>
										<c:otherwise>
											Opted Out
										</c:otherwise>
										</c:choose>
										
										
										</div>
									</div>
								</div>
						

						</c:when>
						<c:otherwise>

							 <c:choose>
								<c:when test="${enroled}">
									<div class="card blue-grey darken-1 block_main">
										<i class="fa fa-money fa-6" aria-hidden="true"></i>
										 <div class="card-content white-text">
										<%--	<span class="card-title">${planEmployee.insPlan.planName}</span> --%>
										
										<span>Employee Provident Fund</span>
										</div>
										<div class="card-action">
											
											<a  href="<%=request.getContextPath()%>/home/myEpfHome/viewDetails">View
												Details</a>
												
											<c:if test="${voluntaryAmountChangePossible}">
											<a	href="<%=request.getContextPath()%>/home/myEpfHome/changeVolAmount">Change Voluntary Amount</a>
											</c:if>	
											
											 <c:choose>
												<c:when test="${slabChangePossible}">


													<a
														href="<%=request.getContextPath()%>/home/myEpfHome/changeToVariable">Change slab to Variable </a>
													

													<!-- <a
														class="btn-floating halfway-fab waves-effect waves-light red"
														href="#"> <i class="fa fa-plus" aria-hidden="true"
														data-toggle="tooltip" data-placement="left"
														title="File New Claim"> </i>
													</a> -->
												</c:when>
											</c:choose>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									 <div class="alert alert-danger">
										<strong>Alert! </strong> 
										Employee Provident Fund Not applicable. Please contact HR!
									</div> 
								</c:otherwise>
							</c:choose>

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
		<form action="<%=request.getContextPath()%>/home/myEpfHome/optOutPF"
			method="POST">
			<div class="modal-content">

				<h4 style="color: #08A9C1;">Confirm Opting Out of Provident Fund</h4>
				<p>You are going to opt out of this plan for FY
					${appContext.currentFiscalYear} as below. Please confirm to
					proceed.</p>
				<span id="planDetailsModal"></span>
				<p>Please provide a valid reason for not opting Provident Fund</p>


				<%-- <input type="hidden" class="cPlanId" name="cPlanId" id="cPlanId" />
				<input type="hidden" name="yrlyOptId" id="yrlyOptId"
					value="${empPlanYearlyOpts.yrlyOptId}" /> --%> <label
					style="font-size: medium; color: black;">Select A Valid
					Reason</label> <select name="reasonId" id="reasonId" class="reasonId"
					style="color: #0AD38A;">

					<c:forEach items="${reasons}" var="reason">
						<option value="${reason.reasonId}">${reason.reasonDescription}</option>

					</c:forEach>
					<option value="0">Others</option>
				</select> <input type="text" placeholder="Enter Reason"
					style="display: none;" name="otherReason" id="otherReason" />

			</div>
			<div class="modal-footer">



				<button
					class="modal-action modal-close waves-effect waves-green btn-flat btn-primary"
					style="color: #08A9C1;">Confirm</button>

				<a href="#"
					class="waves-effect waves-red btn-flat btn-primary modal-close">Cancel</a>
			</div>
		</form>
	</div>
	
	
	<%@include file="includeFooter.jsp"%>
</body>
<script>
$(document).ready(function() {

		$(".reasonId").change(function() {

			if ($("#reasonId").val() == 0) {

				$("#otherReason").css("display", "block");
			} else {
				//remove if unchecked

				$("#otherReason").css("display", "none");
			}
			});
});

		</script>
</html>