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
						
						<form action="saveINSLoyalty" method="post">
						
						
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<input id="loyaltyLevelName" name="loyaltyLevelName1" type="text"
												 />
											<label>Loyalty Name</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<input id="loyaltyLevelDesc" name="loyaltyLevelDesc1" type="text"
												 />
											<label>Description</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<input id="loyaltyMinYears" name="loyaltyMinYears1" type="text"
												 />
											<label>Minimum Year Experince</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<input id="loyaltyMaxYears" name="loyaltyMaxYears1" type="text"
												 />
											<label>Maximum Year Experince</label>
										</div>
										
										<div class="col-md-12">
											<table class="table striped table-borderd">
											
						        			 <tr>
												<th>Dependent</th>
												<th>Employee Contribution</th>
												<th>Company Contribution</th>
												
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
														<input type="text" name="employeeYearlyDeduction1" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="text" name="companyYearlyDeduction1"/>
													</div>
												</td>
												
												
											</tr>
											
											<tr>
							                    <td><div class="col-md-12 padding_left_0">
											
											<select id="dependent1" name="dependent2"
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
														<input type="text" name="employeeYearlyDeduction2" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="text" name="companyYearlyDeduction2"/>
													</div>
												</td>
												
												
											</tr>
											
											<tr>
							                    <td><div class="col-md-12 padding_left_0">
											
											<select id="dependent1" name="dependent3"
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
														<input type="text" name="employeeYearlyDeduction3" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="text" name="companyYearlyDeduction3"/>
													</div>
												</td>
												
												
											</tr>
											
											<tr>
							                    <td><div class="col-md-12 padding_left_0">
											
											<select id="dependent1" name="dependent4"
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
														<input type="text" name="employeeYearlyDeduction4" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="text" name="companyYearlyDeduction4"/>
													</div>
												</td>
												
												
											</tr>
											
											<tr>
							                    <td><div class="col-md-12 padding_left_0">
											
											<select id="dependent1" name="dependent5"
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
														<input type="text" name="employeeYearlyDeduction5" />
													</div></td>
												
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="text" name="companyYearlyDeduction5"/>
													</div>
												</td>
												
												
											</tr>
											</tbody>
										</table>
										
										
										<div class="btn-group dropup">
									<input type="submit" value="Save">
								</div>
										</div>
										

									</div>
						
						</form>
				</div>

			</div>
		</div>

		</section>
	</div>
	</div>

	<%@include file="includeFooter.jsp"%>
	
	<script type="text/javascript">
$(function () {
 $("#famlyCovType").change(function () {
  if ($(this).val() == "1") {
   $("#famlyCov").show();
  } else {
   $("#famlyCov").hide();
  }
 });
});
</script>

	
</body>

</html>


