
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
	

	<div id="main">
		<div class="wrapper">
			<section id="content" class="">
			<div class="row">
				<div class="row">
					
					
					

								<div class="col s12 m6 l4">
									<div class="card blue-grey darken-1 block_main">
										<i class="fa fa-money fa-6" aria-hidden="true"></i>
										<div class="card-content white-text">
											<span class="card-title">Employee NPS</span>
											<p></p>
										</div>
										<div class="card-action">
									
				 								
				 							
											<a
												href="<%=request.getContextPath()%>/home/npsEnrollmentt">Enroll
												Now</a>
												
												<a id="optOutLink" href="#optOutModal">Opt Out</a>
											
											
								
										
										</div>
									</div>
								</div>
						
<div id="optOutModal" class="modal modal-fixed-footer"
		style="height: 500px;">
		<form action="<%=request.getContextPath()%>/home/myEnpsHome/optOutNPS"
			method="POST">
			<div class="modal-content">

				<h4 style="color: #08A9C1;">Confirm Opting Out of NPS </h4>
				<p>You are going to opt out of this plan for FY
					${appContext.currentFiscalYear} as below. Please confirm to
					proceed.</p>
				<span id="planDetailsModal"></span>
				<p>Please provide a valid reason for not opting NPS</p>


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
					
				

							



		
</body>

</html>