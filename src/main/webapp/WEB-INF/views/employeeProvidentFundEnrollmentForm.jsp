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
			<%-- <%@include file="adminLeftNav.jsp"%> --%>
			<section id="content" class="">
			<div class="row">

				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">Employee Provident Fund Enrollment</h4>
						</div>
						<div class="col-sm-6 col-md-9 py-1 px-1"></div>
						<a href="${employeePFBean.uploadUrl}" target="_blank"
							class="doc_upload_link" id="upload"
							uuid="${employeePFBean.docmanUUId}" clicked="false"
							style="float: right; margin-left: 20%; margin-top: -4%;"><button
								style="background-color: rgb(36, 115, 242); padding: 6px; color: white;">
								<i class="fa fa-paperclip" aria-hidden="true"
									style="margin-right: 2px;"></i> Upload your Aadhar Card
							</button></a>

					</div>
					<%
						String changeSlabMessage = request.getParameter("changeSlabMessage");

						if (changeSlabMessage != null && !("".equals(changeSlabMessage))) {
					%>
					<div class="alert alert-success">
						<strong>Done! </strong>
						<%=changeSlabMessage%>
					</div>
					<%
						}
					%>

					<%
						String successMessage = request.getParameter("successMessage");
						if (successMessage != null && !("".equals(successMessage))) {
					%>
					<div class="alert alert-success">
						<strong>Done! </strong>
						<%=successMessage%>
					</div>

					<%
						}
					%>
					<%
						String errorMessage = request.getParameter("errorMessage");
						if (errorMessage != null && !("").equals(errorMessage)) {
					%>
					<div class="alert alert-danger">
						<strong>Oops..! </strong><%=errorMessage%>
					</div>
					<%
						}
					%>

					<%
						String errorMessageDoj = request.getParameter("errorMessageDoj");
						if (errorMessageDoj != null && !("").equals(errorMessageDoj)) {
					%>
					<div class="alert alert-warning">
						<strong>Warning! </strong><%=errorMessageDoj%>
					</div>
					<%
						}
					%>

					<%
						String accountNumber = request.getParameter("accountNumber");
						if (accountNumber != null && !("").equals(accountNumber)) {
					%>
					<div class="alert alert-success">
						<strong>Info! Your PF Account Number With Speridian :</strong><%=accountNumber%>
					</div>

					<%
						}
					%>
					<c:if test="${errorMessage ne null}">
						<div class="alert alert-danger">
							<strong>Oops..! </strong> ${errorMessage}
						</div>
					</c:if>
					<c:if test="${successMessage ne null}">
						<div class="alert alert-success">
							<strong>Done! </strong> ${successMessage}
						</div>
					</c:if>
					<c:if test="${errorMessageDoj ne null}">
						<div class="alert alert-warning">
							<strong>Warning! </strong> ${errorMessageDoj}
						</div>
					</c:if>
					<c:if test="${accountNumber ne null}">
						<div class="alert alert-success">
							<strong>Info! Your PF Account Number With Speridian :</strong>
							${accountNumber}
						</div>
					</c:if>


					<c:if test="${message ne null && dateOfJoin ne 'closed'}">
						<div class="alert alert-danger">
							<strong>Oops! </strong> ${message}
						</div>
					</c:if>
					<c:if test="${notApplicableMessage ne null }">
						<div class="alert alert-danger">
							<strong>Oops! </strong> ${notApplicableMessage}
						</div>
					</c:if>

					<div class="row">
						<div class="col-md-12">
							<form:form id="pfFormId" action="/home/pfEnrollment/submit"
								modelAttribute="employeePFBean" name="pfFormId" method="POST"> 
								
								<style>
.highlight {
	border: 1px solid red !important;
}
</style>
								<div class="col-md-12 padding_left_0">
									<fieldset class="form-group">
										<input type="checkbox" id="uanCheck"
											<c:if test="${employeePFBean.formPrevEstablishment ne null && employeePFBean.formPrevEstablishment ne ''}">
											checked 
											</c:if> />
										<label for="uanCheck">Opted for PF in previous
											employment</label>
									</fieldset>
								</div>

								<div id="minorDiv" style="display: none">
									<div class="row">
										<div class="col-md-12">
											<label>Name And Address of Previous establishment</label>
											<form:input type="text" id="prevCompanyName"
												name="prevCompanyName" path="formPrevEstablishment" />
										</div>
										<div class="col-md-6">
											<label>UAN Number</label>
											<form:input type="text" id="uan" name="uan" path="uan" />
										</div>
										<div class="col-md-6">
											<label>Previous PF No</label>
											<form:input type="text" id="prevPfNo" name="prevPfNo"
												path="formPrevPfAccNo" />
										</div>

										<div class="md-form">
											<div class="col-md-6">
												<!-- <input  id="name" class="form-control">-->
												<form:input id="dojPrevCompany" name="dojPrevCompany"
													type="text" cssClass="form-control datepicker lta_period"
													path="formPrevDOJ" required="true" />
												<label for="dojPrevCompany">Date Of Joining
													(previous establishment)</label>
											</div>
											<div class="col-md-6 ">
												<!-- <input  id="name" class="form-control">-->
												<form:input id="dolPrevCompany" name="dolPrevCompany"
													type="text" cssClass="form-control datepicker lta_period"
													path="formPrevDOL" required="true" />
												<label for="dolPrevCompany">Date Of Leaving
													(previous establishment)</label>
											</div>

										</div>

									</div>
								</div>
								<c:if test="${planSlab ne 'mandatory' }">
									<div class="col-md-12 padding_left_0">
										<fieldset class="form-group">
											<input type="checkbox" id="vpfCheck"
												<c:if test="${employeePFBean.formVoluntaryPF ne null}">
											checked 
											</c:if> />
											<label for="vpfCheck">Voluntary Provident Fund- Would
												you like to contribute more than your normal deduction?</label>
										</fieldset>
									</div>
								</c:if>
								<div id="vpfDiv" style="display: none">
									<div class="row">
										<div class="col-md-3">
											<c:if test="${planSlab ne 'mandatory' }">

												<label>Voluntary PF Amount(Per Annum)</label>
												<form:input type="text" id="volPF"
													onkeypress='return validateQty(event);' name="volPF"
													path="formVoluntaryPF" placeholder="Enter voluntary amount" />
											</c:if>
										</div>
									</div>

								</div>

								<div class="row">
									<div>
										<label>Choose A Plan</label>
									</div>
								</div>
								<a id="planDetailsOnLoad" href="#modalOnload"
									style="display: none">click Here</a>
								<c:if test="${planSlab  eq 'mandatory'}">
									<div class="col-md-4">
										<fieldset class="form-group">
											<form:radiobutton path="optedSlab" value="MDTRY"
												id="mandatory" name="selectPlan" checked="true"
												readonly="true" />
											<label for="mandatory" data-toggle="tooltip"
												data-placement="bottom" title="mandatory">Mandatory
											</label>
										</fieldset>
									</div>
								</c:if>

								<c:if test="${planSlab  eq 'fixedOnly'}">
									<div class="col-md-4">
										<fieldset class="form-group">
											<form:radiobutton path="optedSlab" value="FIXD"
												id="fixedOnly" name="selectPlan" checked="true"
												readonly="true" />
											<label for="fixed" data-toggle="tooltip"
												data-placement="bottom"
												title="Fixed Plan - Premium of flat Rs1800 (employee) and Rs 1800 (employer) will be deducted from employee salary. There will not be any adminstrative charges.">Fixed
											</label>
										</fieldset>
									</div>

									<div>
										<a href="#fixedPlanDescriptionModal">View Plan Details</a>
									</div>
								</c:if>

								<c:if test="${planSlab eq 'variable'}">
									<div class="col-md-4">
										<fieldset class="form-group">
											<form:radiobutton path="optedSlab" value="VRBL" id="variable"
												name="selectPlan" checked="true" />
											<label for="variable" data-toggle="tooltip"
												data-placement="bottom"
												title="Variable Plan - Premium:12% of basic (employee & employer) + administrative charges of .66% of basic pay will be deducted from employee salary.">Variable
											</label>
										</fieldset>
									</div>
									<div>
										<a href="#planDescriptionModal">View Plan Details</a>
									</div>
								</c:if>

								<c:if test="${planSlab  eq 'fixedOrVariable'}">

									<div class="col-md-2">
										<fieldset class="form-group">
											<form:radiobutton path="optedSlab" class="slab" value="FIXD"
												id="fixed" name="selectPlan" checked="true"
												onclick="fixedShow();" />
											<label for="fixed" data-toggle="tooltip"
												data-placement="bottom"
												title="Fixed plan - Premium of flat Rs1800 (employee) and Rs 1800 (employer) will be deducted from employee salary. There will not be any adminstrative charges.">Fixed
											</label>
										</fieldset>
									</div>

									<div class="col-md-2">
										<fieldset class="form-group">
											<form:radiobutton path="optedSlab" class="slab" value="VRBL"
												id="variable" name="selectPlan" onclick="variableShow()" />
											<label for="variable" data-toggle="tooltip"
												data-placement="bottom"
												title="Variable plan - Premium:12% of basic (employee & employer) + administrative charges of .66% of basic pay will be deducted from employee salary.">Variable
											</label>
										</fieldset>
									</div>

									<div>
										<span> <a href="#planDescriptionModal">View Plan
												Details</a>
										</span>
									</div>
								</c:if>
						</div>
						<div class="row">
							<div class="col-md-12">
								<label>Name (As on Aadhar)</label>
								<form:input type="text" id="nameOfEmployee"
									name="nameOfEmployee" path="formEmpName" required="true"></form:input>
							</div>
							<div class="w_100 float_left">
								<%--  <div class="col-md-6">
											<!-- <input  id="name" class="form-control">-->
											<label for="dob">Date Of Birth</label>
											<form:input id="dob" name="dob" type="date" path="formDOB"
												 cssClass="form-control datepicker" required="true" readonly="true"/>
											
										</div> --%>
								<div class="col-md-6">
									<label for="dob">Date Of Birth</label>
									<form:input type="text" id="dob" name="dob" path="formDOB"
										required="true" readonly="true" />
								</div>
								<div class="col-md-3">
									<fieldset class="form-group">
										<form:radiobutton id="male" name="male" path="formGender"
											value="M" />
										<label for="male">Male</label>
									</fieldset>
								</div>
								<div class="col-md-3">
									<fieldset class="form-group">
										<form:radiobutton id="female" name="female" path="formGender"
											value="F" />
										<label for="female">Female</label>
									</fieldset>
								</div>
							</div>

							<div class="col-md-2">
								<fieldset class="form-group">
									<form:radiobutton id="single" name="single"
										path="formMaritalStatus" value="S" checked="true" />
									<label for="single">Single</label>
								</fieldset>
							</div>
							<div class="col-md-2">
								<fieldset class="form-group">
									<form:radiobutton id="married" name="married"
										path="formMaritalStatus" value="M" />
									<label for="married">Married</label>
								</fieldset>
							</div>
							<div class="col-md-2">
								<fieldset class="form-group">
									<form:radiobutton id="widow" name="widow"
										path="formMaritalStatus" value="W" />
									<label for="widow">Widow</label>
								</fieldset>
							</div>

							<%-- 	<div class="col-md-6">
										<label>Marital Status</label> 
									<form:select path="formMaritalStatus" id="maritalStatus" name="maritalStatus">
										<form:option value="M">Married</form:option>
										<form:option value="S">Single</form:option>
										<form:option value="W">Widow</form:option>
									</form:select>
									</div> --%>
							<div class="col-md-6">
								<label>Father's/Husband's Name</label>
								<form:input type="text" id="fatherHusbandName"
									name="fatherHusbandName" path="formGuardianName"
									required="true" />
							</div>
							<div class="col-md-6">
								<label>Mobile Number</label>
								<form:input type="text" id="mobile" name="mobile"
									path="formMobile" required="true"
									onkeypress='return validateQty(event);' />
							</div>
							<div class="col-md-3">
								<label>Office Email</label>
								<form:input type="text" id="emailOffice" name="emailOffice"
									path="formOffEmail" required="true" readonly="true" />
							</div>
							<%-- <div id="vpfDiv" style="display:none">
									<c:if test="${planSlab ne 'mandatory' }">
									<div class="col-md-3">
										<label>Voluntary PF Amount</label> <form:input type="text" id="volPF" class="groupOfTexbox"
											name="volPF"  path="formVoluntaryPF"/>
									</div>
									</c:if>
									</div> --%>
							<div class="col-md-6">
								<label>Personal Email</label>
								<form:input type="email" id="emailPersonal" name="emailPersonal"
									path="formEmail" required="true" />
							</div>
							<div class="col-md-3">
								<label>Aadhar Number</label>
								<form:input onkeypress='return validateQty(event);' type="text"
									id="aadhar" name="aadhar" path="formAadharNo" required="true"
									minlength="12" maxlength="12" />
							</div>
							<div class="col-md-3">
								<label>PAN Number</label>
								<form:input type="text" id="pan" name="pan" path="formPan"
									required="true" readonly="true" />
								<form:input type="hidden" path="docmanUUId"
									value="${employeePFBean.docmanUUId}" />
							</div>
							<div class="col-md-12">
								<label>Permanent Address</label>
								<form:input type="text" id="addressPer" name="addressPer"
									path="formPermanentAddress" required="true" />
							</div>
							<div class="col-md-12">
								<label>Temporary Address</label>
								<form:input type="text" id="addressTemp" name="addressTemp"
									path="formCurrentAddress" required="true" />
							</div>

						</div>
						<!-- here -->
						<div>
							<a class="btn yellow darken-3"
								href="<%=request.getContextPath()%>/home">Home</a>

							<c:if test="${enrollmentPossible eq true}">
								<input type="submit" class="btn yellow darken-3"
									id="saveWithoutSubmit" value="save Without Submit"
									name="saveWithoutSubmit" onclick="savePF()" />
								<c:choose>
									<c:when test="${planSlab  eq 'mandatory'}">


										<a class="btn btn-primary" id="pfSubmit" href="#">Submit</a>
										<!-- onclick="mySubmit()" -->

									</c:when>
									<c:otherwise>
										<a class="btn btn-primary" id="pfSubmit"
											href="#">Enroll Now</a>

									</c:otherwise>
								</c:choose>
							</c:if>
						</div>

						<input type="hidden" id="hiddenField"
							value="${employeePFBean.status}" /> <input type="hidden"
							id="hiddenDoj" value="${dateOfJoin}" /> <input type="hidden"
							id="hiddenSlab" value="${Plan}" /> <input type="hidden"
							id="planSlabError" value="${planSlabError}" /> <input
							type="hidden" id="popUpStatus" value="${employeePFBean.status}" />
						</form:form>
					</div>
				</div>

			</div>
		</div>
	</div>
	</div>


	<div id="planDetailsModal" class="modal modal-fixed-footer"
		style="height: 200px;">

		<div class="modal-content">
			<h4 style="color: #08A9C1;">
				<span id="planDetailsName"></span>Confirm Provident Fund Enrollment
			</h4>
			<c:if test="${planSlab  eq 'fixedOnly'}">
				<p>
					<span id="planDetailsDescFixed">Fixed Plan - Premium of flat
						Rs.1800 (employee) and Rs.1800 (employer) will be deducted from
						your gross salary. </span>
				</p>
			</c:if>
			<c:if test="${planSlab  eq 'fixedOrVariable'}">
				<div id="fixedPlanDiv" style="display: block">
					<p>
						<span id="planDetailsDescFixed">Fixed Plan - Premium of
							flat Rs.1800 (employee) and Rs.1800 (employer) will be deducted
							from your gross salary. </span>
					</p>
				</div>
				<div id="variablePlanDiv" style="display: none">
					<p>
						<span id="planDetailsDescVariable">Variable Plan - Premium
							12% of basic pay (employee & employer) will be deducted from your
							gross salary.</span>
						<!-- + administrative charges of 0.65% of basic pay will be deducted from your gross salary -->
					</p>
				</div>
			</c:if>

			<div class="col-md-12 padding_left_0">
				<fieldset class="form-group">
					<input type="checkbox" id="agreeTerms" name="agreeTerms" /> <label
						for="agreeTerms">I hereby declare that the details
						furnished above are true and correct to the best of my knowledge
						and I agreed to pay the said amount towards the PF account. </label>
				</fieldset>
			</div>

		</div>

		<div class="modal-footer">
			<a id="confirmPfEnroll"
				class="waves-effect waves-red btn-flat btn-primary">Confirm</a> <a
				href="#"
				class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
		</div>

	</div>

	<div id="planDescriptionModal" class="modal modal-fixed-footer"
		style="height: 200px;">

		<div class="modal-content">
			<h4 style="color: #08A9C1;">
				<span id="planDetailsName"></span>Employee Provident Fund Plan
				Details
			</h4>
			<p>
				<span id="planDetailsDescFixed">Fixed Plan - Premium of flat
					Rs.1800 (employee) and Rs.1800 (employer) will be deducted from
					your gross salary. </span>
			</p>
			<p>
				<span id="planDetailsDescVariable">Variable Plan - Premium of
					12% of basic pay (on both the employee and employer i.e.24%) will
					be deducted from your gross salary.</span>
				<!-- + administrative charges of 0.65% of basic pay will be deducted from your gross salary -->
			</p>
			<p>I read the conditions and the accepting the same.</p>
		</div>

		<div class="modal-footer">
			<!-- <a id="confirmPfEnroll"
				class="waves-effect waves-red btn-flat btn-primary">Confirm</a> -->
			<a href="#"
				class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
		</div>

	</div>

	<div id="fixedPlanDescriptionModal" class="modal modal-fixed-footer"
		style="height: 200px;">

		<div class="modal-content">
			<h4 style="color: #08A9C1;">
				<span id="planDetailsName"></span>Employee Provident Fund Plan
				Details
			</h4>
			<p>
				<span id="planDetailsDescFixed">Fixed Plan - Premium of flat
					Rs.1800 (employee) and Rs.1800 (employer) will be deducted from
					your gross salary. </span>
			</p>
			<p>
				<span id="planDetailsDescVariable">Variable Plan - Premium of
					12% of basic pay (on both the employee and employer i.e.24%) will
					be deducted from your gross salary</span>
				<!-- + administrative charges of 0.65% of basic pay will be deducted from your gross salary -->
			</p>
			<p>I read the conditions and the accepting the same.</p>
		</div>

		<div class="modal-footer">
			<!-- <a id="confirmPfEnroll"
				class="waves-effect waves-red btn-flat btn-primary">Confirm</a> -->
			<a href="#"
				class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
		</div>

	</div>

	<c:if test="${planSlab  ne 'mandatory'}">
		<div id="modalOnload" class="modal modal-fixed-footer"
			style="height: 200px;">

			<div class="modal-content">
				<h4 style="color: #08A9C1;">
					<span id="planDetailsName"></span>Employee Provident Fund Plan
					Details
				</h4>
				<c:if test="${planSlab  eq 'fixedOnly'}">
					<p>
						<span id="planDetailsDescFixed">Fixed Plan - Premium of
							flat Rs.1800 (employee) and Rs.1800 (employer) will be deducted
							from your gross salary. </span>
					</p>
					<p>
						<span id="planDetailsDescVariable">Variable Plan - Premium
							of 12% of basic pay (on both the employee and employer i.e.24%)
							will be deducted from your gross salary.</span>
						<!-- + administrative charges of 0.65% of basic pay will be deducted from your gross salary -->
					</p>
				</c:if>
				<c:if test="${planSlab  eq 'fixedOrVariable'}">
					<p>
						<span id="planDetailsDescFixed">Fixed Plan - Premium of
							flat Rs.1800 (employee) and Rs.1800 (employer) will be deducted
							from your gross salary. </span>
					</p>
					<p>
						<span id="planDetailsDescVariable">Variable Plan - Premium
							of 12% of basic pay (on both the employee and employer i.e.24%)
							will be deducted from your gross salary.</span>
						<!-- + administrative charges of 0.65% of basic pay will be deducted from your gross salary -->
					</p>
				</c:if>
				<p>I read the conditions and the accepting the same.</p>
			</div>

			<div class="modal-footer">
				<!-- <a id="confirmPfEnroll"
				class="waves-effect waves-red btn-flat btn-primary">Confirm</a> -->
				<a href="#"
					class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
			</div>

		</div>
	</c:if>

	<%@include file="includeFooter.jsp"%>
	<script>
	onPageLoad();
	    $("#pfSubmit").click(function() {
	    	
	    	
	    	
	    	if($('#pan').val() == ''|| $('#aadhar').val()== ''||$('#nameOfEmployee').val() == ''|| $('#dob').val()== ''||$('#fatherHusbandName').val() == ''|| $('#mobile').val()== ''||$('#emailOffice').val() == ''|| $('#emailPersonal').val()== ''||$('#addressPer').val() == ''|| $('#addressTemp').val()== ''){
	    		if($('#pan').val() == ''){
	    			$('#pan').addClass("highlight");
	    			$('#pan').focus();
		    	}
	    		if($('#aadhar').val() == ''){
	    			$('#aadhar').addClass("highlight");
	    			$('#aadhar').focus();
		    	}
	    		if($('#nameOfEmployee').val() == ''){
	    			$('#nameOfEmployee').addClass("highlight");
	    			$('#nameOfEmployee').focus();
		    	}
	    		if($('#dob').val() == ''){
	    			$('#dob').addClass("highlight");
	    			$('#dob').focus();
		    	}
	    		if($('#fatherHusbandName').val() == ''){
	    			$('#fatherHusbandName').addClass("highlight");
	    			$('#fatherHusbandName').focus();
		    	}
	    		if($('#mobile').val() == ''){
	    			$('#mobile').addClass("highlight");
	    			$('#mobile').focus();
		    	}
	    		if($('#emailOffice').val() == ''){
	    			$('#emailOffice').addClass("highlight");
	    			$('#emailOffice').focus();
		    	}
	    		if($('#emailPersonal').val() == ''){
	    			$('#emailPersonal').addClass("highlight");
	    			$('#emailPersonal').focus();
		    	}
	    		if($('#addressPer').val() == ''){
	    			$('#addressPer').addClass("highlight");
	    			$('#addressPer').focus();
		    	}
	    		if($('#addressTemp').val() == ''){
	    			$('#addressTemp').addClass("highlight");
	    			$('#addressTemp').focus();
		    	}
			        $(this).attr('href','#');
				 }else{
					 
					 	var uuid = '${employeePFBean.docmanUUId}'; 
						var xhttp = new XMLHttpRequest();
					    xhttp.open("GET", "/api/docman/document/"+uuid+"/availability/session/${appContext.userLoginKey}", false);
					    console.log(uuid);
					    xhttp.setRequestHeader("Content-type", "application/json");
					    xhttp.send();
					    
					    var response = xhttp.responseText;
					    console.log(response);
					    if (response == 'false'){
					    	alert("Upload Aadhar card for submission!!!");
					    	$(this).attr('href','#');
					    } else{
					    	$(this).attr('href','#planDetailsModal');
						 	/* $("a").trigger("click"); */
						 	$("#planDetailsModal").dialog();
						 	/* return false; */
					 
				 }
			}
	    	});
	    
	function onPageLoad(){
		var periodF = '${employeePFBean.formPrevDOJ}';
		var periodT = '${employeePFBean.formPrevDOL}';
		if ( periodF!= ''
			|| periodT != '') {
		
		 document.getElementById("dojPrevCompany").value =periodF;
		document.getElementById("dolPrevCompany").value =periodT;
		
		
		

		 $('.datepicker').pickadate({
			selectMonths : true, // Creates a dropdown to control month
			selectYears : 15
		// Creates a dropdown of 15 years to control year
		}); 
	}
	}
	    
	    
		$('.datepicker').pickadate({
			selectMonths : true, // Creates a dropdown to control month
			selectYears : 80
		// Creates a dropdown of 15 years to control year
		});
		
		 $(document).ready(function() {
			 
			 $('.groupOfTexbox').keypress(function (event) {
			        return isNumber(event, this)
			    });
			 
			  $('#agreeTermsSlabChange').click(function(){
			        
			        if(this.checked){
			               
			             $('#confirmSlabChange').removeAttr('disabled');
			        }
			        else {
			        	$('#confirmSlabChange').attr("disabled","true");
			        }
			    });
			 
		     $('#uanCheck').change(function() {

		        $('#minorDiv').toggle();  

			  }); 
		 var checkedBox = $('#uanCheck').is(':checked');
		    if(checkedBox==true)
	    	{
	    		$('#minorDiv').toggle();  
	    	}
		    
		    $('#vpfCheck').change(function() {

		        $('#vpfDiv').toggle();  

			  }); 
		 var checkedBox = $('#vpfCheck').is(':checked');
		    if(checkedBox==true)
	    	{
	    		$('#vpfDiv').toggle();  
	    	}
		    	    
		    
		    /* commmented
		    
		    var status = $('#hiddenField').val();
		    var dojStatus = $('#hiddenDoj').val();
		
		    var planSlabError= $('#planSlabError').val();
		 
			if(status=='HR_APPR' || status== 'HR_RJCT' || status=='SL_SUBM')
				{
					$('#pfFormId input').attr('disabled', true);
					
				}
			if(dojStatus=='closed')
				{
				$('#pfFormId input').attr('disabled', true);
				}
			if(planSlabError=='false')
				{
		
				$('#pfSubmit').hide();
				$('#saveWithoutSubmit').hide();
				}
			if(status == 'SUBM' || status=='HR_APPR' || status== 'HR_RJCT' || status== 'SL_SUBM')
			{	
				$('#pfSubmit').hide();
				$('#saveWithoutSubmit').hide();
			} 
				
			*/
			
			/* var planOpted = ${Plan};
			if(planOpted=='fixedPlan')
				{
					
				} */
		}); 
		 
		 $('#vpfCheck').on('click',function(){
			 var checked=$('#vpfCheck').is(':checked');
			 
			 if(checked==true)
			    {  
				 $('#volPF').attr('required',true);
			    }
		 });
		 
		$('#uanCheck').on('click',function(){
			   var checked=$('#uanCheck').is(':checked');
			    if(checked==true)
			    {   
			    	
			        $('#prevCompanyName').attr('required',true);
			        $('#uan').attr('required',true);
			        $('#dolPrevCompany').attr('required',true);
			        $('#dojPrevCompany').attr('required',true);
			        $('#prevPfNo').attr('required',true);
			    }
			    if(checked==false)
			    {
			    	$('#prevCompanyName').val(null);
			    	$('#uan').val(null);
			        $('#dolPrevCompany').val(null);
			        $('#dojPrevCompany').val(null);
			        $('#prevPfNo').val(null);
			        
			        $('#prevCompanyName').attr('required',false);
			        $('#uan').attr('required',false);
			        $('#dolPrevCompany').attr('required',false);
			        $('#dojPrevCompany').attr('required',false);
			        $('#prevPfNo').attr('required',false);
			        
			    }
			  });
		
		
		
		
		$('#confirmPfEnroll').click(function(e) {
	       var agreeTerms=$('#agreeTerms').is(':checked');
	      if(agreeTerms==true)
	      {
	    	  
	    	  if ($("#pfFormId").valid()) {
	        	 $("#pfFormId").submit(); 
	    	  }
	    	  else{
	    		  return false;
	    	  }
	    	  
	      }else{
	    	  $('#agreeTerms').attr('required',true);
	      		}
	    	  });
		
function savePF()
{
	$("#pfFormId").attr("action","/home/pfEnrollment/saveWithoutSubmit");   
}	  
	


// THE SCRIPT THAT CHECKS IF THE KEY PRESSED IS A NUMERIC OR DECIMAL VALUE.
function isNumber(evt, element) {

    var charCode = (evt.which) ? evt.which : event.keyCode

    if (
        (charCode != 45 || $(element).val().indexOf('-') != -1) &&      // “-” CHECK MINUS, AND ONLY ONE.
        (charCode != 46 || $(element).val().indexOf('.') != -1) &&      // “.” CHECK DOT, AND ONLY ONE.
        (charCode < 48  || charCode > 57)
        )
        return false;

    return true;
}    
		    
$(window).load(function(){  
	/* alert("------YOU ARE HERE------"); */
		
	if($('#popUpStatus').val()=="")
	{
	
	   $('#planDetailsOnLoad').click();
		
	}
		
	    }); 
	    
function variableShow()
{
	//alert("-------here----");
	$("#variablePlanDiv").css("display","block");
	$("#fixedPlanDiv").css("display","none");
	}

function fixedShow()
{
	//alert("-------fixed here----");
	$("#fixedPlanDiv").css("display","block");
	$("#variablePlanDiv").css("display","none");
	}
	
function validateQty(event) {
    var key = window.event ? event.keyCode : event.which;

if (event.keyCode == 8 || event.keyCode == 46
 || event.keyCode == 37 || event.keyCode == 39) {
    return true;
}
else if ( key < 48 || key > 57 ) {
    return false;
}
else return true;
};
	
	</script>
</body>
</html>