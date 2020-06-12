<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Benefits Portal</title>
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
							<h4 class="h4-responsive">Add New Claims</h4>
						</div>
						<div class="col-sm-6 col-md-3">
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
						<form:form id="addForm" name="addForm"  method="POST" action="${planId}"
						 modelAttribute="claims">
                  			 	Claim Reference Number<form:input type="text" id="claimRefNo" path="claimRefNo" name="claimRefNo" />
								External Reference #<form:input type="text" path="extRefNo" id="externalRefNo" name="externalRefNo" />
								Amount<form:input type="text" path="amount" id="amount" name="amount" />
								<label for="periodFrom">Period From</label><form:input type="date" cssClass="form-control datepicker" path="periodFrom" id="periodFrom" name="periodFrom" />
								<label for="periodTo">Period To</label><form:input type="date" cssClass="form-control datepicker" path="periodTo" id="periodTo" name="periodTo" />
								<label for="issuedDate">Issued Date</label><form:input type="date" cssClass="form-control datepicker" path="issuedDate" id="issuedDate" name="issuedDate" />
								Issued By<form:input type="text" path="issuedBy" id="issuedBy"
									name="issuedBy" /> 
								<form:input path="directClaimId" type="hidden" id="directClaimId"/>	
								<a class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel">Cancel</a>
								<input type="submit" id="btnClaimApprove" class="btn btn-primary" value="Submit" />
						</form:form>
						</div>
					
					</div>
					
				</div>
			</div>
			</section>
		</div>
	</div><%@include file="includeFooter.jsp"%>
</body>

<script>
		$('.datepicker').pickadate({
			selectMonths : true, // Creates a dropdown to control month
			selectYears : 15
		// Creates a dropdown of 15 years to control year
		});
    	</script>
</html>