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
							<h4 class="h4-responsive">
								
							Opt New Benefit Plan
						
							</h4>
						</div>
					
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
						
							<form:form id="editPlanForm" method="post"
								action="/home/controlPanel/flexiPlans/newPlan"
								modelAttribute="planBean" cssClass="form-horizontal">
								<div class="col-md-12">

									<div class="">
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											
											<label for="planName">Plan Name</label>
											<label for="planName">"${benefitPlan.planName}"</label>
											
										</div>
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											
											<label for="claimType">Claim Type</label>
											<label for="claimType">"${benefitPlan.claimType}"</label>
										</div>
									</div>
								</div>
								<div class="form-group is-empty col-md-12">
									<div class="">
										<!-- <input  id="name" class="form-control">-->
										
										<label for="planDesc">Description</label>
										<label for="description">"${benefitPlan.planDesc}"</label>
									</div>
								</div>
								<div class="col-md-12">


									<div class="">
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<label for="effFrom">Effective From</label>
											<label for="effFrom">"${benefitPlan.effFrom}"</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											
											<label for="effTill">Effective To</label>
											<label for="effTill">"${benefitPlan.effTill}"</label>
										</div>
									</div>
								</div>
								<div class="col-md-12">

									<div class="">
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<label for="yearlyDeduction">Yearly Deduction</label>
											<label for="yearlyDeduction">"${benefitPlan.yearlyDeduction}"</label>
										</div>
										
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-md-6">
										<input type="submit" class="btn btn-primary" value="Opt" />
										<input type="hidden" name="planId" value="${benefitPlan.benefitPlanId}"/>

										<button class="btn btn-primary" value="back">Back</button>
									</div>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			
			</section>

		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
	 $('.datepicker').pickadate({
		    selectMonths: true, // Creates a dropdown to control month
		    selectYears: 15 // Creates a dropdown of 15 years to control year
		  });
		        
	</script>
</body>
</html>