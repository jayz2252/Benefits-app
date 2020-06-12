<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<style>
select {
	display: block;
}

input.select-dropdown, ul.select-dropdown, span.caret {
	display: none !important;
}
</style>
<body>
	<%@include file="employeeNavBar.jsp"%>
	<div class="col-md-3">
		<span id="newClaimDetails" class="sticky_left">
			<table>
				<tr>
					<th colspan="2">New Claim Details</th>
				</tr>
				<tr>
					<th>Claim Ref. No:</th>
					<td>${claim.claimRefNo}</td>
				</tr>
				<tr>
					<th>Plan</th>
					<td>${claim.planEmployee.benefitPlan.planName}</td>
				</tr>
				<tr>
					<th>Maximum Amount</th>
					<td>Rs. ${claim.planEmployee.yearlyClaim / (12 / claim.planEmployee.benefitPlan.claimFrequency)}</td>
				</tr>
				<tr>
					<th>Requested Amount</th>
					<td>Rs.<span id="totalClaimAmount">0.0</span></td>
				</tr>
				<tr>
					<th>Approved Amount</th>
					<td>Rs.0.0</td>
				</tr>
			</table>
		</span>

	</div>
	<div id="main">
		<div class="wrapper">

			<section id="content" class="">
			<div class="row">
				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">New Claim Request -
								${planEmployee.benefitPlan.planName}</h4>
								
								<c:choose>
								 <c:when test="${show}">
							<a href="${claim.uploadUrl}"
								style="float: right; margin-right: -250px; margin-top: -40px;"
								target="_blank" clicked="false" class="btn blue darken-2"><i
								class="fa fa-paperclip" aria-hidden="true"></i> Upload Bills</a>
								</c:when>
								<c:otherwise>
								<a href="${claim.uploadUrl}"
								style="float: right; margin-right: -250px; margin-top: -40px;"
								target="_blank" clicked="false" class="btn blue darken-2" disabled="disabled"><i
								class="fa fa-paperclip" aria-hidden="true" disabled="disabled"></i> Upload Bills</a>
								</c:otherwise>
								</c:choose>
						</div>

					</div>
					<!--  <div class="alert alert-warning" style="margin-right: 687px;">
						 Upload PDF bills not more than <strong>5 MB</strong>.
					</div>  -->
					<c:choose>
								 <c:when test="${show}">
					<div class="alert alert-warning">
						<strong>NOTE : </strong>Bills must contain a valid claim identifier. Bills without vehicle registration number would be rejected.<br>
						  Upload PDF bills not more than <strong>5 MB.</strong>
					</div>
					</c:when>
					<c:otherwise>
					<div class="alert alert-danger">
						<strong>Warning : </strong>You have reached your maximum limit for claiming under this benefit for FY 2018-19.
					</c:otherwise>
					</c:choose>
					</div>

					

					
					<%-- <p> Attach scanned bills:
						<a href="${claim.uploadUrl}" target="_blank" clicked="false"><i class="fa fa-paperclip" aria-hidden="true"></i></a></p> --%>
				</div>
				<div class="row">
					<div class="col-md-12">
						<form action="save" method="POST" id="newClaimForm">
							<table id="claimBillsTable"
								class="table striped table-borderd dynamicTable">
								<thead>
									<tr>
										<th>Category/Sub Category</th>
										<th>Dependent</th>
										<th>Bill/Voucher No.</th>
										<th>Bill/Voucher Date</th>
										<th>Amount</th>
										<th>Issued From</th>
										<!-- <th>Comments</th> -->

									</tr>
								</thead>
								<c:choose>
								 <c:when test="${show}">
								<tbody>
									<tr>

										<td><select name="claimCtg" id="claimCtg">
												<c:forEach items="${categories}" var="ctg">
													<option value="${ctg.categoryMiscId}">${ctg.miscName}</option>
												</c:forEach>
										</select></td>
										<td><select name="claimDep" id="claimDep">
												<option value="0">Not Applicable</option>
												<c:forEach items="${dependents}" var="dep">
													<option value="${dep.dependentId}">${dep.dependentName}</option>
												</c:forEach>
										</select></td>
										<td><input type="text" name="claimBillNo"
											id="claimBillNo" required="true" /></td>
										<td><input type="date" name="claimBillDate"
											id="claimBillDate" required="true" /></td>
										<td><input type="number" name="claimBillAmount"
											id="claimBillAmount" required="true" class="bill_amount"
											onchange="onChangeBillAmount()" /></td>
										<td><input type="text" name="claimBiller"
											id="claimBiller" required="true" /></td>
										<!-- <td><input type="text" name="claimComments"
											id="claimComments" /></td> -->
									</tr>
								</tbody>
								</c:when>
								<c:otherwise>
								<tbody>
									<tr>

										<td><select name="claimCtg" id="claimCtg">
												<c:forEach items="${categories}" var="ctg">
													<option value="${ctg.categoryMiscId}">${ctg.miscName}</option>
												</c:forEach>
										</select></td>
										<td><select name="claimDep" id="claimDep">
												<option value="0">Not Applicable</option>
												<c:forEach items="${dependents}" var="dep">
													<option value="${dep.dependentId}">${dep.dependentName}</option>
												</c:forEach>
										</select></td>
										<td><input type="text" name="claimBillNo"
											id="claimBillNo" required="true" disabled="disabled"/></td>
										<td><input type="date" name="claimBillDate"
											id="claimBillDate" required="true" disabled="disabled"/></td>
										<td><input type="number" name="claimBillAmount"
											id="claimBillAmount" required="true" class="bill_amount"
											onchange="onChangeBillAmount()" disabled="disabled"/></td>
										<td><input type="text" name="claimBiller"
											id="claimBiller" required="true" disabled="disabled"/></td>
										<!-- <td><input type="text" name="claimComments"
											id="claimComments" /></td> -->
									</tr>
								</tbody>
								</c:otherwise>
								</c:choose>
								
							</table>
							<c:if test="${show}">
							<table class="striped table-borderd">
								<tr>
									<td colspan="4" style="text-align: right">Requested Amount</td>
									<th>Rs. <span id="totalClaimAmountCenter">0.0</span></th>
								</tr>
							</table>
							</c:if>
							<input type="hidden" id="rowCount" name="rowCount" value="1" />
							<input type="hidden" id="planName" name="planName"
								value="${planEmployee.benefitPlan.planName}" /> <input
								type="hidden" id="planEmployeeId" name="planEmployeeId"
								value="${planEmployee.planEmployeeId}" /> <input type="hidden"
								id="claimId" name="claimId" value="${claim.benefitPlanClaimId}" />
							<input type="hidden" id="lastRowBillAmount" /> <a
								class="btn yellow darken-3"
								href="<%=request.getContextPath()%>/home/myFlexiPlans/">Back
							</a>
							<c:choose>
								 <c:when test="${show}">
								<button type="button" id="btnAddRow" class="btn blue darken-2">Add
								Row</button>
							<button type="button" id="btnRemoveRow" class="btn red darken-1"
								onclick="removeLastRow()">Remove Last Row</button>



							<input type="submit" class="btn btn-primary" value="Submit Claim"
								id="btnClaim"/>
							</c:when>
							<c:otherwise>
							<button type="button" id="btnAddRow" class="btn blue darken-2" disabled="disabled">Add
								Row</button>
							<button type="button" id="btnRemoveRow" class="btn red darken-1"
								onclick="removeLastRow()" disabled="disabled">Remove Last Row</button>



							<input type="submit" class="btn btn-primary" value="Submit Claim"
								id="btnClaim" disabled="disabled"/>
							
							</c:otherwise>
							</c:choose>
						</form>


					</div>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
</body>
<script>
	/* $("#btnClaim")
	 .on(
	 "click",
	 function() {
	 var hasErrors = false;
	 $('.doc_upload_link').each(function(){
	 var documentsMissing = false;
	 var uuid = $(this).attr('uuid');
	 var xhttp = new XMLHttpRequest();
	 xhttp.open("GET", "${docAvailabilityRestUrl}/"+uuid+"/${appContext.userLoginKey}", false);
	 xhttp.setRequestHeader("Content-type", "application/json");
	 xhttp.send();
	 var response = JSON.parse(xhttp.responseText);
	
	 if (!response.result.available){
	 documentsMissing = true;
	 return false;
	 } 
	 }  
	 if (documentsMissing){
	 hasErrors = true;
	 alert("Please upload the mandatory documents to request the claim!")
	 }
	 });
	 if (hasErrors == true){
	 alert("Please upload the mandatory documents to request the claim!")
	 $(this).attr("href", "#");
	 }else{
	
	 $(this).attr("href", "#btnClaim");
	 }
	 });

	 */
	$(document)
			.ready(
					function() {
						$("#newClaimForm")
								.submit(
										function(e) {
											var documentsMissing = false;
											xhttp = new XMLHttpRequest();
											xhttp.open("GET",
													"${DocumentAvailable}",
													false);
											xhttp.setRequestHeader(
													"Content-type",
													"application/json");
											xhttp.send();
											var response = JSON
													.parse(xhttp.responseText);

											if (!response.result.available) {
												documentsMissing = true;
											}

											if (documentsMissing) {
												alert("Please upload the mandatory documents to request the claim!")
												e.preventDefault();
											} else {
												alert("Your claim has been requested successfully!")
											}
										});
					});

	/*function documentcheck() {
		var documentsMissing = false;
		xhttp = new XMLHttpRequest();
		xhttp.open("GET", "${DocumentAvailable}", false);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
		var response = JSON.parse(xhttp.responseText);

		if (!response.result.available) {
			documentsMissing = true;
			/* return false; 
		}

		if (documentsMissing) {
			alert("Please upload the mandatory documents to request the claim!")
			$(this).attr("type", "cancel");

			/*  $(this).attr("href",""); 
		} else {
			alert("Request submitted!")
			/* $(this).attr("href","#btnClaim"); 
		

	}; */

	function onChangeBillAmount() {
		var totalReqAmt = 0;
		var currAmount = 0;
		$('.bill_amount').each(function() {
			currAmount = parseFloat($(this).val());
			totalReqAmt += currAmount;
		});
		$('#lastRowBillAmount').val(currAmount);
		$('#totalClaimAmount').html(totalReqAmt);
		$('#totalClaimAmountCenter').html(totalReqAmt);
	}

	function removeLastRow() {
		var rowCount = $('#rowCount').val();
		var currAmount = parseFloat($('#claimBillAmount' + rowCount).val());
		var totalClaimAmount = parseFloat($('#totalClaimAmount').html())
				- currAmount;

		$('#totalClaimAmount').html(totalClaimAmount);
		$('#totalClaimAmountCenter').html(totalClaimAmount);
	}
</script>

</html>