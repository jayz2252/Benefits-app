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
							<h4 class="h4-responsive">Edit ${planPojo.planName} -
								Contacts</h4>
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

							<form:form id="editBenefitPlanContactsForm"
								modelAttribute="planBean" action="docs" method="post"
								cssClass="form-horizontal">
								<div class="row">
									<div class="md-form">
										<table class="table striped table-borderd">
											<tr>
												<th>Contact Name</th>
												<th>Designation</th>
												<th>Email</th>
												<th>Phone 1</th>
												<th>Phone 2</th>
												<th>Primary Contact</th>
											</tr>
											<tr>
												<td><div class="col-md-12 padding_left_0">
														<input type="text" name="contact1Name" required="true"/>
													</div></td>

												<td>
													<div class="input-field col s12">
														<select name="contact1Designation"
															id="contact1Designation">
															<c:forEach items="${designations}" var="designation">
																<option>${designation}</option>
															</c:forEach>
														</select>
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="email" name="contact1Email" required="true"/>
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="contact1Phone1" required="true"/>
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="contact1Phone2" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<fieldset class="form-group">
															<input type="checkbox" id="contact1primaryContactFlag" name="contact1primaryContactFlag" checked="true" readonly> 
															<label for="contact1primaryContactFlag"></label>
														</fieldset>
														<!-- <input type="checkbox" id="primaryContact" value="contact1"
															/> -->

													<!-- <fieldset class="form-group">
														<input type="checkbox" id="contact1primaryContactFlag" name="contact1primaryContactFlag"> 
														<label for="contact1primaryContactFlag"></label>
											</fieldset> -->
														<!-- <input type="checkbox" id="primaryContact" value="contact1"
															/> -->

													</div>
												</td>
											</tr>
													
											<tr>
												<td><div class="col-md-12 padding_left_0">
														<input type="text" name="contact2Name" />
													</div></td>
												<td>
													<div class="input-field col s12">
														<select name="contact2Designation"
															id="contact2Designation">
															<c:forEach items="${designations}" var="designation">
																<option>${designation}</option>
															</c:forEach>
														</select>
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="email" name="contact2Email" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="contact2Phone1" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="contact2Phone2" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">

													<fieldset class="form-group">
														<input type="checkbox" id="contact2primaryContactFlag" name="contact2primaryContactFlag"> 
														<label for="contact2primaryContactFlag"></label>
													</fieldset>

														<!-- <fieldset class="form-group">
														<input type="checkbox" id="contact2primaryContactFlag" name="contact2primaryContactFlag"> 
														<label for="contact2primaryContactFlag"></label>
											</fieldset>	 -->
												<!-- 		<input id="primaryContact" value="contact2" type="radio" /> -->
													</div>
												</td>
											</tr>
											<tr>
												<td><div class="col-md-12 padding_left_0">
														<input type="text" name="contact3Name" />
													</div></td>
												<td>
													<div class="input-field col s12">
														<select name="contact3Designation"
															id="contact3Designation">
															<c:forEach items="${designations}" var="designation">
																<option>${designation}</option>
															</c:forEach>
														</select>
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="email" name="contact3Email" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="contact3Phone1" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">
														<input type="number" name="contact3Phone2" />
													</div>
												</td>
												<td>
													<div class="col-md-12 padding_left_0">

														<fieldset class="form-group">
															<input type="checkbox" id="contact3primaryContactFlag" name="contact3primaryContactFlag"> 
															<label for="contact3primaryContactFlag"></label>
														</fieldset>

													
													</div>
												</td>
											</tr>
										</table>
										<form:hidden path="mode" />
										<form:hidden path="step" />
										<div class="form-group">
											<div class="col-md-6">
												<a class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel">Cancel</a>
												<input type="submit" class="btn btn-primary" value="Next" />
											</div>
										</div>
									</div>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script type="text/javascript">
		window.onbeforeunload = function() {
			return "You work will be lost.";
		};
	</script>
</body>
</html>