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
							<h4 class="h4-responsive">LTA Request Approval</h4>
						</div>
						<div class="col-sm-6 col-md-3"></div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<table class="table striped table-borderd">
								<tr>
									<th>Employee Name</th>
									<td>${ltaEmployee.employee.firstName}.${ltaEmployee.employee.lastName}</td>
									<th>Designation</th>
									<td>${ltaEmployee.employee.designationName}</td>
								</tr>
								<tr>
									<th>Actual Fare</th>
									<td>${ltaEmployee.actualFare}</td>
									<th>Shortest Fare</th>
									<td>${ltaEmployee.shortestFare}</td>
								</tr>

								<tr>
									<th>Origin of Travel</th>
									<td>${ltaEmployee.origin}</td>
									<th>Destination</th>
									<td>${ltaEmployee.destination1}</td>
								</tr>
								
								<tr>
									<th>From Date</th>
									<td>
									<fmt:formatDate pattern="dd-MMM-yyyy"
											value="${ltaEmployee.periodFrom}" />
									</td>
									<th>To Date</th>
									<td><fmt:formatDate pattern="dd-MMM-yyyy"
											value="${ltaEmployee.periodTill}" /></td>
								</tr>
							</table>
						<br/>
						<br/>
						<form id="btnRequestApprove" action="<%=request.getContextPath()%>/home/controlPanel/lta/search/approve" method="POST">
						
						<div>
							<div class="form-group is-empty margin_top_25">

								<div class="md-form">
									<input id="approvedAmt" name="approvedAmt" type="number"
										value="0.0" class="approveAmtHide" /> <label
										class="approveAmtHide" for=claimTotal>Approved Amount</label>
								</div>
							</div>
							</div>
							
							<div class="col-md-12">
						<table class="tablePlanEmployees table striped table-borderd"">
							<thead>
								<tr>

									<th>Dependent Name</th>
									<th>Relationship</th>
									<th>Origin</th>
									<th>Destination</th>
									<th>Mode Of Transport</th>
									<th>Class of Travel</th>
									<th>Cost of Fare</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${ltaDependents}" var="ltaDependent">
									<tr>

										<td>${ltaDependent.dependent.dependentName}</td>
										<td>${ltaDependent.dependent.relationship}</td>
										<td>${ltaDependent.origin}</td>
										<td>${ltaDependent.destination}</td>
										<td>${ltaDependent.modeOfTransport}</td>
										<td>${ltaDependent.travellingClass}</td>
										<td>${ltaDependent.fare}</td> 
									</tr>
								</c:forEach>
							</tbody>
						</table>
							
							<input type="hidden" value="${ltaEmployee.ltaEmployeeId}" name="ltaEmployeeId" />



								<a class="btn yellow darken-3"
									href="<%=request.getContextPath()%>/home/controlPanel/lta/search">Back</a>
									
								<input type="submit" id="btnRequestApprove"
									class="btn btn-primary" value="Approve Request" />
								
								<a class="btn red darken-3"
									onclick="mydfunction()" href="" id="reject" disabled="disabled">Reject Request</a>
									<input type="text" 
									id="declineReason" name="declineReason" placeholder="Do you want to Reject? Enter reason here.."/>
							
									
								</form>
	
						</div>
					</div>
					
				</div>
			</div>
			</section>
		</div>
	</div><%@include file="includeFooter.jsp"%>
</body>

<script>
$('#declineReason').on('change', function(){
	var reason = $(this).val();
	$('#reject').attr('href','/home/controlPanel/lta/search/reject/${ltaEmployee.ltaEmployeeId}?declineReason='+reason);
});

function mydfunction() {
	
	alert("LTA request Rejected!");
	//window.location.href = "home/controlPanel/lta/search";
};
$(document).ready(function () {
    $('#declineReason').on('input change', function () {
        if ($(this).val() != '') {
            $('#reject').attr('disabled', false);
        }
        else {
            $('#reject').attr('disabled', true);
        }
    });
});
//$("#reject").click(function() {
	 
	 // if (confirm("Do you want to Reject the LTA Request?")) {
	 //  	$(this).attr('href', 'home/controlPanel/lta/search');
	 //  	 window.alert("LTA request rejected!");
	 //  } else {
	  // 	 $(this).dialog('close');
	 //  }
</script>
</html>

