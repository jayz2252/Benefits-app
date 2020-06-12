<!-- 
author : jithin.kuriakose
This page will show Employees opted for each benefit plans
 -->
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlanEmployee"%>
<%@page import="com.speridian.benefits2.model.pojo.Employee"%>
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 	
<style>
.select-wrapper ul, .select-wrapper .caret, .select-wrapper input{display:none !important;}
select.initialized{display:block !important;}
</style>
	
	
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
							<h4 class="h4-responsive">Opted Employees</h4>
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



							<form action="addNewMutualDependency" method="POST">
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<select id="plan1" name="plan1" class="planSelect">
							<!-- 					<option value="" disabled="disabled">Select Plan</option> -->
													<c:forEach items="${plans}" var="plan" >
														<option value="${plan.benefitPlanId}" class="${plan.benefitPlanId}" data-class="${plan.benefitPlanId}">${plan.planName}</option>
													</c:forEach>
												</select> <label for="flexiPlan">Select Plan 1</label>
											</div> 
											
			

										</div>
									</div>

								</div>
								
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<select id="plan2" name="plan2" class="planSelect">
												<!-- <option value="" >Select Plan</option> -->
													<c:forEach items="${plans}" var="plan">
														<option value="${plan.benefitPlanId}" class="${plan.benefitPlanId}" data-class="${plan.benefitPlanId}">${plan.planName}</option>
													</c:forEach>
												</select> <label for="flexiPlan">Select Plan 2</label>
											</div>
			

										</div>
									</div>

								</div>
								<div class="row">
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<input type="submit" class="btn btn-primary" value="Add">
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		</section>
	</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$('select').material_select();
		});
		
		$('select').change(function() {
		    var selectedOptions = $('select option:selected');
		    $('select option').removeAttr('disabled');

		    selectedOptions.each(function() {
		        
		        var value = this.value;
		        if (value != ''){           
		        var id = $(this).parent('select').attr('id');
		        var options = $('select:not(#' + id + ') option[value=' + value + ']');
		        options.attr('disabled', 'true');
		        $('[disabled="true"]').removeAttr('disabled');
		        }
		    });


		});

		
				
	</script>

</body>
</html>