
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script>
$(document).ready(function(){
	
	
});

function txtpercentage() {
	$(".mytable tr").not(":first").each(function() {

		 var amt = parseInt($(this).find(".amount input").val());
		 var per = parseInt($(this).find(".percentage input").val());
	        
			if(!isNaN(per)){
	        var result =  per * amt/100;

	        $(this).find(".covdAmt input").val(result);
			}


	  });


	
}
</script>
<style type="text/css">
table.th {
	width: 50px;
}
</style>
</head>
<%@include file="include.jsp"%>
<body>
	<%@include file="employeeNavBar.jsp"%>



	<div id="main">
		<div class="wrapper">
			<%@include file="adminLeftNav.jsp"%>
			<section id="content" class="">
			<div class="row">

				<form action="saveTreatments" method="post">
	


					<div class="col-md-12 white">

						<div class="row">
							<div class="col-sm-6 col-md-9 py-1 px-1">
								<h4 class="h4-responsive">Amount alloted for different
									Treatments</h4>
							</div>

						</div>

						<div class="row">
							<div class="col-md-12">

								<table class="mytable">
									<thead>
										<tr>
											<th>Treatment Name</th>
											<th>Description</th>
											<th>Total Coverage Amount</th>
											<th>Percentage of Covered Amount</th>
											<th>Maximum Alloted Amount</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${treatmentList.treatments}" var="treatment"
											varStatus="status">
											<tr>


												<td><input
													name="treatments[${status.index}].treatmentName"
													value="${treatment.treatmentName}" /></td>
												<td><input
													name="treatments[${status.index}].description"
													value="${treatment.description}" /></td>
												
												<td class="amount"><input 
													name="treatments[${status.index}].averageAmount"
													value="${coverageAmount}" /></td>
												<td class="percentage"><input type="text" 
													name="planTreatmentDetails${status.index}].maxCoveragePercentage" value="" onchange="txtpercentage()"></td>
												<td class="covdAmt"><input type="text" 
													name="planTreatmentDetails[${status.index}].maxCoverage" value=""></td>	
												<td><input type="hidden" name="planTreatmentDetails[${status.index}].treatment.treatmentId"
													value="${treatment.treatmentId}"/></td>	

											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="form-group" ><input type="hidden" value="${INSPlanId}"/></div>
								
								<div class="btn-group dropup">
									<input type="submit" value="Save">
								</div>

							</div>
						</div>
					</div>
				</form>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
</body>

</html>




