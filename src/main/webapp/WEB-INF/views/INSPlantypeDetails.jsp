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
							<h4 class="h4-responsive">INS Plan Type Details</h4>
							</br>
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
						
						<form:form id="editPlanFormi" method="post" action="saveINSPlanDetails"
								modelAttribute="INSPlanBean" cssClass="form-horizontal">
					
					<div class="form-group is-empty">

									<div class="md-form">
										<div class="col-md-6 padding_left_0" id="famlyCovType">
											<!-- <input  id="name" class="form-control">-->
											<select id="planType" name="planType"
												cssClass="form-control">
												<option value="0">Individual Plan</option>
												<option value="1">Family Plan</option>
											</select> <label for="planType">Plan Type</label>
										</div>
										<div  class="col-md-6 padding_left_0"  id="famlyCov">
										 <div class="form-group is-empty">
									      <div class="md-form row">
										       <!-- <input  id="name" class="form-control">-->
										       <form:input id="yearlyCoverage" name="yearlyCoverage" type="number"
											  path="yearlyCoverage" cssClass="form-control" />
										      <label for="yearlyCoverage">Total Coverage</label>
									      </div>
								         </div>
										</div>
									</div>
									<c:if test="${eaicIndicator}">
									   <div class="md-form">
									  
										<div  class="col-md-6 padding_left_0 "  id="">
										 <div class="form-group is-empty">
									      <div class="md-form row">
										       <!-- <input  id="name" class="form-control">-->
										       <form:input id="eaicYearlyDeduction" name="eaicYearlyDeduction" type="number"
											  path="eaicYearlyDeduction" cssClass="form-control" />
										      <label for="eaicYearlyDeduction">AIC Yearly Deduction</label>
									      </div>
								         </div>
										</div>
										<div  class="col-md-6 padding_left_0 "  id="">
										 <div class="form-group is-empty">
									      <div class="md-form row">
										       <!-- <input  id="name" class="form-control">-->
										       <form:input id="eaicYearlyCoverage" name="eaicYearlyCoverage" type="number"
											  path="eaicYearlyCoverage" cssClass="form-control" />
										      <label for="eaicYearlyCoverage">AIC Sum Insured</label>
									      </div>
								         </div>
										</div>
										
									</div>
									</c:if>
								</div>
					
					
					<div class="col-md-12">
						<table class="table striped table-borderd">
						         <tr>
									<th>Dependent</th>
									<th>Employee Contribution</th>
									<th>Company Contribution</th>
									<th>Coverage</th>
								</tr>
							     <tbody>
							                <tr>
												<td><div class="col-md-12 padding_left_0">
											
											<select id="dependent1" name="dependent1"
												cssClass="form-control">
												<option value="0">Select One</option>
												<option value="Spouse">Spouse</option>
												<option value="Father">Father</option>
												<option value="Mother">Mother</option>
												<option value="Son">Son</option>
												<option value="Daughter">Daughter</option>
												<option value="Self">Self</option>
											</select> 
										</div>
							                    
							                    </td>
													
												<td><div class="col-md-12 padding_left_0">
														<input type="number" name="empContribution1" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="comContridution1" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="covrage1" />
													</div>
												</td>
												
											</tr>
											
											<tr>
												<td><div class="col-md-12 padding_left_0">
											
											<select id="dependent2" name="dependent2"
												cssClass="form-control">
												<option value="0">Select One</option>
												<option value="Spouse">Spouse</option>
												<option value="Father">Father</option>
												<option value="Mother">Mother</option>
												<option value="Son">Son</option>
												<option value="Daughter">Daughter</option>
												<option value="Self">Self</option>
											</select> 
										</div>
							                    
							                    </td>
													
												<td><div class="col-md-12 padding_left_0">
														<input type="number" name="empContribution2"/>
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="comContridution2" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="covrage2" />
													</div>
												</td>
												
											</tr>
											
											<tr>
												<td><div class="col-md-12 padding_left_0">
											
											<select id="dependent3" name="dependent3"
												cssClass="form-control">
												<option value="0">Select One</option>
												<option value="Spouse">Spouse</option>
												<option value="Father">Father</option>
												<option value="Mother">Mother</option>
												<option value="Son">Son</option>
												<option value="Daughter">Daughter</option>
												<option value="Self">Self</option>
											</select> 
										</div>
							                    
							                    </td>
													
												<td><div class="col-md-12 padding_left_0">
														<input type="number" name="empContribution3" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="comContridution3" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="covrage3" />
													</div>
												</td>
												
											</tr>
											
											<tr>
												<td><div class="col-md-12 padding_left_0">
											
											<select id="dependent4" name="dependent4"
												cssClass="form-control">
												<option value="0">Select One</option>
												<option value="Spouse">Spouse</option>
												<option value="Father">Father</option>
												<option value="Mother">Mother</option>
												<option value="Son">Son</option>
												<option value="Daughter">Daughter</option>
												<option value="Self">Self</option>
											</select> 
										</div>
							                    
							                    </td>
													
												<td><div class="col-md-12 padding_left_0">
														<input type="number" name="empContribution4" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="comContridution4" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="covrage4" />
													</div>
												</td>
												
											</tr>
											
											<tr>
												<td><div class="col-md-12 padding_left_0">
											
											<select id="dependent5" name="dependent5"
												cssClass="form-control">
												<option value="0">Select One</option>
												<option value="Spouse">Spouse</option>
												<option value="Father">Father</option>
												<option value="Mother">Mother</option>
												<option value="Son">Son</option>
												<option value="Daughter">Daughter</option>
												<option value="Self">Self</option>
											</select> 
										</div>
							                    
							                    </td>
													
												<td><div class="col-md-12 padding_left_0">
														<input type="number" name="empContribution5" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="comContridution5"/>
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="covrage5" />
													</div>
												</td>
												
											</tr>
											
											<tr>
												<td><div class="col-md-12 padding_left_0">
											
											<select id="dependent6" name="dependent6"
												cssClass="form-control">
												<option value="0">Select One</option>
												<option value="Spouse">Spouse</option>
												<option value="Father">Father</option>
												<option value="Mother">Mother</option>
												<option value="Son">Son</option>
												<option value="Daughter">Daughter</option>
												<option value="Self">Self</option>
											</select> 
										</div>
							                    
							                    </td>
													
												<td><div class="col-md-12 padding_left_0">
														<input type="number" name="empContribution6" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="comContridution6" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="covrage6" />
													</div>
												</td>
												
											</tr>
											
											<tr>
												<td><div class="col-md-12 padding_left_0">
											
											<select id="dependent7" name="dependent7"
												cssClass="form-control">
												<option value="0">Select One</option>
												<option value="Spouse">Spouse</option>
												<option value="Father">Father</option>
												<option value="Mother">Mother</option>
												<option value="Son">Son</option>
												<option value="Daughter">Daughter</option>
												<option value="Self">Self</option>
											</select> 
										</div>
							                    
							                    </td>
													
												<td><div class="col-md-12 padding_left_0">
														<input type="number" name="empContribution7" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="comContridution7" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="covrage7" />
													</div>
												</td>
												
											</tr>
											
											<tr>
												<td><div class="col-md-12 padding_left_0">
											
											<select id="dependent8" name="dependent8"
												cssClass="form-control">
												<option value="0">Select One</option>
												<option value="Spouse">Spouse</option>
												<option value="Father">Father</option>
												<option value="Mother">Mother</option>
												<option value="Son">Son</option>
												<option value="Daughter">Daughter</option>
												<option value="Self">Self</option>
											</select> 
										</div>
							                    
							                    </td>
													
												<td><div class="col-md-12 padding_left_0">
														<input type="number" name="empContribution8" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="comContridution8" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="covrage8" />
													</div>
												</td>
												
											</tr>
											
											<tr>
												<td><div class="col-md-12 padding_left_0">
											
											<select id="dependent9" name="dependent9"
												cssClass="form-control">
												<option value="0">Select One</option>
												<option value="Spouse">Spouse</option>
												<option value="Father">Father</option>
												<option value="Mother">Mother</option>
												<option value="Son">Son</option>
												<option value="Daughter">Daughter</option>
												<option value="Self">Self</option>
											</select> 
										</div>
							                    
							                    </td>
													
												<td><div class="col-md-12 padding_left_0">
														<input type="number" name="empContribution9" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="comContridution9" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="covrage9" />
													</div>
												</td>
												
											</tr>
											
											<tr>
												<td><div class="col-md-12 padding_left_0">
											
											<select id="dependent10" name="dependent10"
												cssClass="form-control">
												<option value="0">Select One</option>
												<option value="Spouse">Spouse</option>
												<option value="Father">Father</option>
												<option value="Mother">Mother</option>
												<option value="Son">Son</option>
												<option value="Daughter">Daughter</option>
												<option value="Self">Self</option>
											</select> 
										</div>
							                    
							                    </td>
													
												<td><div class="col-md-12 padding_left_0">
														<input type="number" name="empContribution10" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="comContridution10" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="covrage10" />
													</div>
												</td>
												
											</tr>
							</tbody>
						</table>
						
					</div>
					<div class="form-group" ><input type="hidden" value="${INSPlanId}"/></div>
					<div class="form-group">
									<div class="">
										<a class="btn yellow darken-3"
											href="<%=request.getContextPath()%>/home/controlPanel">Cancel</a>
											
										<input type="submit" class="btn btn-primary" value="Next"/>
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
	
<script type="text/javascript">
$("#famlyCov").hide();
$('#planType').on('change',function(){

	 var planType = $(this).val();
	 if (planType =='1'){
	  $('#famlyCov').show();
	 }else{
	  $('#famlyCov').hide();
	 }
	});
</script>

	
</body>

</html>


