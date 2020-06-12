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
							<h4 class="h4-responsive">Provident Fund Enrollment</h4>
						</div>
						<div class="col-sm-6 col-md-3">
						</div>
					</div>
					<%
					String changeSlabMessage = request.getParameter("changeSlabMessage");
					
					if (changeSlabMessage != null && !("".equals(changeSlabMessage))){
					%>
					<div class="alert alert-success">
									<strong>Done! </strong> <%=changeSlabMessage%>
								</div>
					<%} %>
					
					<%
					String successMessage = request.getParameter("successMessage");
					if(successMessage != null && !("".equals(successMessage)))
					{
					%>
					<div class="alert alert-success">
									<strong>Done! </strong> <%=successMessage%>
								</div>
					
					<%}%>
					<%
					String errorMessage = request.getParameter("errorMessage");
					if(errorMessage !=null && !("").equals(errorMessage))
					{
					%>		
					<div class="alert alert-danger">
									<strong>Oops..! </strong><%=errorMessage %>
								</div>
					<%}%>
					
					<% 
					String errorMessageDoj = request.getParameter("errorMessageDoj");
					if(errorMessageDoj!=null && !("").equals(errorMessageDoj))
					{
					%>
					<div class="alert alert-warning">
									<strong>Warning! </strong><%= errorMessageDoj %>
								</div>
					<%} %>
					
					<%
					String accountNumber = request.getParameter("accountNumber");
					if(accountNumber!=null && !("").equals(accountNumber))
					{
					%>
					<div class="alert alert-success">
									<strong>Info! Your PF Account Number With Speridian :</strong><%= accountNumber %>
								</div>
					
					<%} %>
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
									<strong>Info! Your PF Account Number With Speridian :</strong> ${accountNumber}
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
							<form:form id="pfFormId" action="/home/myEpf/submit" modelAttribute="employeePFBean" name="pfFormId"
								method="POST">

							<div class="row">
									<div>
										<label>Choose A Plan</label> 
									</div>
							</div>
							<%-- <div class="col-md-3">
								<div class="col-md-12">
									<fieldset class="form-group">
										<form:radiobutton id="planCheckVariable" name="selectPlan"
											 path="optedSlab" checked="true" value="VRBL"/> <label for="planCheckVariable">Variable
											Plan</label>
									</fieldset>
								</div>
								<div class="col-md-12">
									<fieldset class="form-group">
										<form:radiobutton id="planCheckFixed" name="selectPlan"
											path="optedSlab" value="FIXD"/> <label for="planCheckFixed">Fixed
											Plan</label>
									</fieldset>
								</div> --%>
								<c:if test="${slabChange eq 'fixedButCanChangeToVariable'}">
								<a href="<%=request.getContextPath()%>/home/myEpf/changeToVariable" title="Click to change to variable slab. You cannot rollback to previous slab for this year." class="btn yellow darken-3" >Change Plan to Variable</a>
								</c:if>
								
								
								<c:if test="${planSlab  eq 'mandatory'}">
								<div class="col-md-4">
								<fieldset class="form-group">
									<form:radiobutton path="optedSlab"  value="${employeePFBean.optedSlab}" id="mandatory" name="selectPlan" checked="true" disabled="true" />
									<label for="mandatory" data-toggle="tooltip" data-placement="bottom" title="mandatory">Mandatory
									</label>
								</fieldset></div>
							</c:if>
								
								<c:if test="${planSlab  eq 'fixed'}">
								<div class="col-md-4"> 
								<fieldset class="form-group">
									<form:radiobutton  path="optedSlab"  value="${employeePFBean.optedSlab}" id="fixedOnly" name="selectPlan" checked="true" />
									<label for="fixed" data-toggle="tooltip" data-placement="bottom" title="Fixed Plan - Premium of flat Rs1800 (employee) and Rs 1800 (employer) will be deducted from employee salary. There will not be any adminstrative charges.">Fixed
									</label>
								</fieldset> 
								</div>
								
								<div>
								<a href="#planDescriptionModal">View Plan Details</a>
								</div>
								</c:if>
								
								<c:if test="${planSlab eq 'variable'}">
									<div class="col-md-4"> 
								<fieldset class="form-group">
									<form:radiobutton  path="optedSlab"  value="${employeePFBean.optedSlab}" id="variable" name="selectPlan" checked="true" />
									<label for="variable" data-toggle="tooltip" data-placement="bottom" title="Variable Plan - Premium:12% of basic (employee & employer) + administrative charges of .66% of basic pay will be deducted from employee salary.">Variable
									</label>
								</fieldset> 
								</div>
								<div>
								<a href="#planDescriptionModal">View Plan Details</a>
								</div>				
								</c:if>
								
								<%-- <%
								String planSlabOption = request.getParameter("planSlab");
									if(planSlabOption!=null && !("").equals(planSlabOption) )
									{
									%>
								<c:if test="${planSlab  eq 'fixedOrVariable'}">
								<div class="col-md-4"> 
								<fieldset class="form-group">
									<form:radiobutton  path="optedSlab"  value="FIXD" id="fixed" name="selectPlan"  checked="true" />
									<label for="fixed" data-toggle="tooltip" data-placement="bottom" title="Fixed plan - Premium of flat Rs1800 (employee) and Rs 1800 (employer) will be deducted from employee salary. There will not be any adminstrative charges.">Fixed
									</label>
								</fieldset> 
								</div>
								
								<div class="col-md-4">
								<fieldset class="form-group">
									<form:radiobutton path="optedSlab" value="VRBL" id="variable" name="selectPlan" />
									<label for="variable" data-toggle="tooltip" data-placement="bottom" title="Variable plan - Premium:12% of basic (employee & employer) + administrative charges of .66% of basic pay will be deducted from employee salary." >Variable
									</label>
								</fieldset> 
								</div>
								<%} %>
								 --%>
								 
								 
								<c:if test="${planSlab  eq 'fixedOrVariable'}">
								
								<div class="col-md-4"> 
								<fieldset class="form-group">
									<form:radiobutton  path="optedSlab"  value="FIXD" id="fixed" name="selectPlan"  checked="true" />
									<label for="fixed" data-toggle="tooltip" data-placement="bottom" title="Fixed plan - Premium of flat Rs1800 (employee) and Rs 1800 (employer) will be deducted from employee salary. There will not be any adminstrative charges.">Fixed
									</label>
								</fieldset> 
								</div>
								
								<div class="col-md-4">
								<fieldset class="form-group">
									<form:radiobutton path="optedSlab" value="VRBL" id="variable" name="selectPlan" />
									<label for="variable" data-toggle="tooltip" data-placement="bottom" title="Variable plan - Premium:12% of basic (employee & employer) + administrative charges of .66% of basic pay will be deducted from employee salary." >Variable
									</label>
								</fieldset> 
								</div>
								
								<div>
								<span>
								<a href="#planDescriptionModal">View Plan Details</a>
								</span>
								</div>
								</c:if>
								</div>
								
								<%-- <c:if test="${planSlab ne 'mandatory'}">
								<a href="#planDescriptionModal">View Plan Details</a>
								</c:if> --%>
							<!-- ------------------	Slab change ---------- -->
								
								
								<%-- <div class="col-md-9">
									<fieldset class="form-group">
								<c:if test="${Plan  eq 'fixedPlan' && employeePFBean.status eq 'HR_APPR'}">
									<a class="btn yellow darken-3"
										href="<%=request.getContextPath()%>/home/myEpf/changeToVariable">Change to Variable Slab</a>
								</c:if>
								
								<c:if test="${Plan  eq 'fixedPlan' && employeePFBean.status eq 'HR_APPR'}">
									<a id="changeSlabToVariable" href="#changeToVariableModal" ><i class="fa fa-level-up" title="Upgrade to Variable Slab (Requires HR Approval & Payroll components revision)" aria-hidden="true" style="font-size: 30px !important"></i></a>
								</c:if>
								
								</fieldset>
								</div> --%>

								
								<div class="row">
									<div class="col-md-12">
										<label>Name (As on Aadhar)</label> <form:input type="text" id="nameOfEmployee"
											name="nameOfEmployee" path="formEmpName" required="true"></form:input>
									</div>
									<div class="w_100 float_left">
									 <div class="col-md-6">
											<!-- <input  id="name" class="form-control">-->
											<label for="dob">Date Of Birth</label>
											<form:input id="dob" name="dob" type="date" path="formDOB"
												 cssClass="form-control datepicker" required="true"/>
											
										</div>
								
									<div class="col-md-3">
										<fieldset class="form-group">
										<form:radiobutton id="male" name="male"
											 path="formGender" value="M"/> <label for="male">Male</label>
									</fieldset>
									</div>
									<div class="col-md-3">
										<fieldset class="form-group">
										<form:radiobutton id="female" name="female"
											 path="formGender" value="F"/> <label for="female">Female</label>
									</fieldset>
									</div>
									</div>
									<%-- <div class="col-md-6">
										<label>Marital Status</label> <form:input type="text"
											id="maritalStatus" name="maritalStatus" path="formMaritalStatus" required="true"></form:input>
									</div> --%>
									<div class="col-md-6">
										<label>Marital Status</label> 
									<form:select path="formMaritalStatus" id="maritalStatus">
										<form:option value="M">Married</form:option>
										<form:option value="S">Single</form:option>
										<form:option value="W">Widow</form:option>
									</form:select>
									</div>
									<div class="col-md-6">
										<label>Father's/Husband's Name</label> <form:input type="text"
											id="fatherHusbandName" name="fatherHusbandName" path="formGuardianName" required="true"/>
									</div>
									<div class="col-md-6">
										<label>Mobile Number</label> <form:input type="text" id="mobile"
											name="mobile"  path="formMobile" required="true"/>
									</div>
									<div class="col-md-3">
										<label>Office Email</label> <form:input type="text" id="emailOffice"
											name="emailOffice"  path="formOffEmail" required="true" readonly="true"/>
									</div>
									<div class="col-md-3">
										<label>Voluntary PF Amount</label> <form:input type="text" id="volPF"
											name="volPF"  path="formVoluntaryPF" required="true"/>
									</div>
									<div class="col-md-6">
										<label>Personal Email</label> <form:input type="email" id="emailPersonal"
											name="emailPersonal" path="formEmail"  required="true"/>
									</div>
									<div class="col-md-3">
										<label>Aadhar Number</label> <form:input type="text" id="aadhar"
											name="aadhar" path="formAadharNo"  required="true"/>
									</div>
									<div class="col-md-3">
										<label>PAN Number</label> <form:input type="text" id="pan"
											name="pan" path="formPan" required="true"/>
									</div>
									<div class="col-md-12">
										<label>Permanent Address</label> <form:input type="text"
											id="addressPer" name="addressPer" path="formPermanentAddress" required="true"/>
									</div>
									<div class="col-md-12">
										<label>Temporary Address</label> <form:input type="text"
											id="addressTemp" name="addressTemp" path="formCurrentAddress"  required="true"/>
									</div>

								</div>
							<div class="col-md-12 padding_left_0">
								<fieldset class="form-group">
									<input type="checkbox" id="uanCheck" 
									<c:if test="${employeePFBean.formPrevEstablishment ne null && employeePFBean.formPrevEstablishment ne ''}">
											checked 
											</c:if> />
									<label for="uanCheck">Already Opted PF From Previous Company</label>
								</fieldset> 
							</div>
							
							<div id="minorDiv" style="display:none">
								<div class="row">
									<div class="col-md-12">
									<label>Name And Address of Previous establishment</label>
									<form:input type="text" id="prevCompanyName" name="prevCompanyName" path="formPrevEstablishment" />
									</div>
									 <div class="col-md-6">
										<label>UAN Number</label> 
										<form:input type="text" id="uan" name="uan" path="uan" />
									</div>
									<div class="col-md-6">
										<label>Previous PF No</label>
										<form:input type="text" id="prevPfNo" name="prevPfNo" path="formPrevPfAccNo" />
										</div>
									
									<div class="md-form">
										<div class="col-md-6">
											<!-- <input  id="name" class="form-control">-->
											<form:input id="dojPrevCompany" name="dojPrevCompany" type="date"
												 cssClass="form-control datepicker" path="formPrevDOJ" required="true"
												 />
											<label for="dojPrevCompany">Date Of Joining (previous establishment)</label>
										</div>
										<div class="col-md-6 ">
											<!-- <input  id="name" class="form-control">-->
											<form:input id="dolPrevCompany" name="dolPrevCompany" type="date"
												 cssClass="form-control datepicker" path="formPrevDOL" required="true"/>
											<label for="dolPrevCompany">Date Of Leaving (previous establishment)</label>
										</div> 
									<%-- 	<div class="col-md-6">
										<label>Date Of Leaving (previous establishment)</label> 
										<form:input type="date" id="dolPrevCompany" name="dolPrevCompany" path="formPrevDOL" />
									</div> --%>
									</div> 
									
								</div>
						  </div>
						<div>
					<a class="btn yellow darken-3"
										href="<%=request.getContextPath()%>/home">Cancel</a>
					
					<%-- <c:if test="${employeePFBean.status eq 'SAVED' && employeePFBean.status ne 'HR_APPR' && employeePFBean.status ne 'HR_RJCT' && dateOfJoin eq 'open'}"> --%>
					
					<c:if test="${dateOfJoin eq 'open' && employeePFBean.status eq null}">
					<input type="submit" class="btn yellow darken-3" id="saveWithoutSubmit" value="save Without Submit" name="saveWithoutSubmit" onclick="savePF()" />
					 <a class="btn btn-primary" id="pfSubmit"
										href="#planDetailsModal">Enroll Now</a> 
					
					</c:if>
					<c:if test="${employeePFBean.status eq 'SAVED' && dateOfJoin eq 'open' }">
					<input type="submit" class="btn yellow darken-3" id="saveWithoutSubmit" value="save Without Submit" name="saveWithoutSubmit" onclick="savePF()" />
					 <a class="btn btn-primary" id="pfSubmit"
										href="#planDetailsModal">Enroll Now</a> 
					</c:if>
					
					<%-- 
						<c:choose>
					 <c:when test="${employeePFBean.status ne 'HR_APPR' && employeePFBean.status ne 'HR_RJCT' && dateOfJoin eq 'open'}"> 
							
									
					 <a class="btn btn-primary" id="pfSubmit"
										href="#planDetailsModal">Enroll Now</a> 
						</c:when>
						</c:choose> --%>
								</div>
								
							<input type="hidden" id="hiddenField" value="${employeePFBean.status}" />
							<input type="hidden" id="hiddenDoj" value="${dateOfJoin}" />
							<input type="hidden" id="hiddenSlab" value="${Plan}" />
							<input type="hidden" id="planSlabError" value="${planSlabError}" />
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
			<p>
				<span id="planDetailsDesc"></span>
			</p>
			<div class="col-md-12 padding_left_0">
								<fieldset class="form-group">
									<input type="checkbox" id="agreeTerms" name="agreeTerms" />
									<label for="agreeTerms">I hereby declare that the details furnished above are true and correct to the best of my knowledge
									and belief and I undertake to inform you of any changes therein, immediately. In case any of the above
									information is found to be false or untrue or misleading or misrepresenting, I am aware that I may be
									held liable for it
									</label>
								</fieldset> 
							</div>
			
		</div>

		<div class="modal-footer">
		<a id="confirmPfEnroll"
				class="waves-effect waves-red btn-flat btn-primary">Confirm</a>
			<a href="#"
				class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
		</div>

	</div>
	
	<div id="planDescriptionModal" class="modal modal-fixed-footer"
		style="height: 200px;">

		<div class="modal-content">
			<h4 style="color: #08A9C1;">
				<span id="planDetailsName"></span>Employee Provident Fund Plan Details
			</h4>
			<p>
				<span id="planDetailsDescFixed">Fixed Plan -  Premium of flat Rs.1800 (employee) and Rs.1800 (employer) will be deducted from your gross salary. </span>
			</p>
			<p>	
			<span id="planDetailsDescVariable">Variable Plan - Premium 12% of basic pay (employee & employer) + administrative charges of 0.66% of basic pay will be deducted from your gross salary.</span>
			</p>
			<p>
			I read the conditions and the accepting the same.
			</p>
		</div>

		<div class="modal-footer">
		<!-- <a id="confirmPfEnroll"
				class="waves-effect waves-red btn-flat btn-primary">Confirm</a> -->
			<a href="#"
				class="waves-effect waves-red btn-flat btn-primary modal-close">Close</a>
		</div>

	</div>
	
	
<script src="jquery-3.2.1.min.js"></script>
	<%@include file="includeFooter.jsp"%>
	<script>
		$('.datepicker').pickadate({
			selectMonths : true, // Creates a dropdown to control month
			selectYears : 80
		// Creates a dropdown of 15 years to control year
		});
		
		 $(document).ready(function() {
			
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
		    	    
		    var status = $('#hiddenField').val();
		    var dojStatus = $('#hiddenDoj').val();
		  /*   var hiddenSlab = $('#hiddenSlab').val(); */
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
			/* 	$('#pfSubmit').attr("disabled","disabled"); */
				$('#pfSubmit').hide();
				$('#saveWithoutSubmit').hide();
				}
			if(status == 'SUBM' || status=='HR_APPR' || status== 'HR_RJCT' || status== 'SL_SUBM')
			{	
				$('#pfSubmit').hide();
				$('#saveWithoutSubmit').hide();
			}
				
			
			
			/* var planOpted = ${Plan};
			if(planOpted=='fixedPlan')
				{
					
				} */
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
	$("#pfFormId").attr("action","/home/myEpf/saveWithoutSubmit");   
}	  
		    
		    
			
	</script>
</body>
</html>