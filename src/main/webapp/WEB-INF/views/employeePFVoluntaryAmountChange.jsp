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

				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">Employee Provident Fund</h4>
						</div>
						<div class="col-sm-6 col-md-3">
						</div>
					</div>
			<div class="row">
			<div class="col-md-12">
							<form:form id="pfFormId" action="/home/pfEnrollment/changeVolAmountSubmit" modelAttribute="employeePFBean" name="pfFormId"
								method="POST">
								
							<div id="invalidVariableAmount" class="alert alert-danger"  style="display:none">
									<strong>Info!</strong> Cannot Decrease Variable Amount  
							</div>		
							<h6>Change your Voluntary Amount</h6>
									<div class="col-md-3">
										<label>Current Voluntary Amount</label> <input type="text" id="CurrVolAmount"
											name="CurrVolAmount"  path="formVoluntaryPF" value="${employeePFBean.formVoluntaryPF}" readonly="true"/>
									</div>
									<div class="col-md-3">
										<label>New Voluntary Amount</label> <form:input type="text" id="volAmount" class="groupOfTexbox"
											name="volAmount"  path="formVoluntaryPF" required="true"/>
											&nbsp;<span id="errmsg"></span>
									</div>
						</div>		
				</div> 		
								</div>
						  </div>
					<div>
					<a class="btn yellow darken-3"
										href="<%=request.getContextPath()%>/home">Home</a>
					<input type="submit" class="btn btn-primary" id="Submit" value="Submit" name="Submit" />
					</div>
					
					<input type="hidden" name="volAmountDb" id="volAmountDb" value="${employeePFBean.formVoluntaryPF}" />
					</form:form>
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
		
		// jQuery ".Class" SELECTOR.
	    $(document).ready(function() {
	        $('.groupOfTexbox').keypress(function (event) {
	            return isNumber(event, this)
	        });
	    });
	    // THE SCRIPT THAT CHECKS IF THE KEY PRESSED IS A NUMERIC OR DECIMAL VALUE.
	    function isNumber(evt, element) {

	        var charCode = (evt.which) ? evt.which : event.keyCode

	        if (
	            (charCode != 45 || $(element).val().indexOf('-') != -1) &&      // “-” CHECK MINUS, AND ONLY ONE.
	            (charCode != 46 || $(element).val().indexOf('.') != -1) &&      // “.” CHECK DOT, AND ONLY ONE.
	            (charCode < 48 || charCode > 57))
	            return false;

	        return true;
	    }    
		
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
/* $('#Submit').on("click", function() {
	var volAmtDb=parseInt($('#volAmountDb').val());
	var volAmt = parseInt($('#volAmount').val());

	if(volAmt<= volAmtDb)
	{

		//alert("Cannot decrease Variable component");

		 $("#invalidVariableAmount").show();
		return false;
	}
	else
	{
		 $("#pfFormId").submit(); 
	}
}); */

	</script>
</body>
</html>