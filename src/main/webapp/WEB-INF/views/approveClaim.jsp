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


				<div class="row">
					<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">Verify Claim - ${claim.claimRefNo}</h4>
						<a href="${claim.uploadUrl}" style="float:right;margin-right:-250px;margin-top:-40px;" target="_blank" clicked="false" class="btn blue darken-2"><i class="fa fa-download" aria-hidden="true"></i> View Bills</a>
					</div>
					<div class="col-md-12 white">
						<c:if test="${duplicatesFound}">
							<div class="alert alert-warning">
								<strong>Warning!</strong> Duplicate Bills found, please
								verify with older approved claims before approving. Click <a
									href="#duplicateModal" id="btnDuplicateBill">here</a> for more
								details.
							</div>
						</c:if>
					</div>
					<div class="col-sm-6 col-md-3">
						<!-- <div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div> -->
					</div>
				</div>
				<%-- <p>${message}</p> --%>
				<%-- <c:if test="${message}">
					<th>hello</th>
					</c:if> --%>
				<div class="row">
					<div class="col-md-12">
						<table class="table striped table-borderd">
							<tr>
								<th>Claim Ref No.</th>
								<td>${claim.claimRefNo}</td>
								<th>Submitted Date</th>
								<td>${claim.submittedDate}</td>
							</tr>
							<tr>
								<th>Employee</th>
								<td>${claim.planEmployee.employee.employeeCode}-
									${claim.planEmployee.employee.firstName}
									${claim.planEmployee.employee.lastName}</td>
								<th>Requested Amount(Rs.)</th>
								<td>${claim.totalRequestedAmount}</td>
							</tr>
						</table>
						<table class="table striped table-borderd">
							<c:if test="${employeePlan.benefitPlan.promptFieldsOnOPT}">
								<c:forEach items="${benefitPlanEmployeeField}" var="field">
									<tr>
										<th>${field.field.uiLabel}</th>
										<td>${field.value}</td>
										<th></th>
										<td></td>
										<th></th>
										<td></td>
										<th></th>
										<td></td>
										<th></th>
										<td></td>
									</tr>
									<tr>
										<th></th>
										<td></td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
						<form action="approve" method="POST">
							<table class="table striped table-borderd" id="myform">
								<thead>
									<tr>
										<c:if
											test="${claim.planEmployee.benefitPlan.claimType eq 'CTGY'}">
											<th>Category/Sub Category</th>
										</c:if>
										<th>Dependent</th>
										<th>Bill/Voucher No.</th>
										<th>Bill/Voucher Date</th>
										<th>Amount</th>
										<th>Issued From</th>
										<th>Comments</th>
										<th id="">Approved Amount</th>
										<th>Approve</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${claim.details}" var="detail">
										<tr>
											<c:if
												test="${claim.planEmployee.benefitPlan.claimType eq 'CTGY'}">
												<td><c:choose>
														<c:when test="${detail.planEmployeeDetail ne null}">
												${detail.planEmployeeDetail.misc.miscName}
												</c:when>
														<c:otherwise>									
												${detail.planEmployeeDetail.planEmployee.planCategory.categoryName}
												</c:otherwise>
													</c:choose></td>
											</c:if>
											<td><c:choose>
													<c:when test="${detail.dependent.dependentId ne null}">
														<td>${detail.dependent.dependentName}</td>
													</c:when>
													<c:otherwise>
											Not Applicable
											</c:otherwise>
												</c:choose></td>

											<td>${detail.billNo}</td>
											<td>${detail.billDate}</td>
											<td><span class="billAmount"
												id="billAmount${detail.benefitPlanClaimDetailId}">${detail.requestedAmount}</span></td>
											<td>${detail.billIssuer}</td>
											<td><input
												id="approveComments${detail.benefitPlanClaimDetailId}"
												name="approveComments${detail.benefitPlanClaimDetailId}"
												type="text" value="${detail.billDesc}" required="true"
												style="width: 81px;" /></td>

											<td class="approveAmtHide"><input type="number"
												class="approvedAmount"
												id="approvedAmount${detail.benefitPlanClaimDetailId}"
												name="approvedAmount${detail.benefitPlanClaimDetailId}"
												value="approvedAmount${detail.benefitPlanClaimDetailId}" />
											</td>
											<td><input
												id="verifyBill${detail.benefitPlanClaimDetailId}"
												type="checkbox" value="${detail.benefitPlanClaimDetailId}"
												class="verify_bill" /><label
												for="verifyBill${detail.benefitPlanClaimDetailId}"></label></td>

										</tr>
									</c:forEach>
								</tbody>

							</table>



							<div class="form-group is-empty">

								<div class="md-form">
									<!-- <input  id="name" class="form-control">-->
									<input id="claimTotal" name="claimTotal" type="number"
										value="0.0" readonly="true" class="approveAmtHide" /> <label
										class="approveAmtHide" for=claimTotal>Approved Amount</label>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<!--  <input type="password" id="password" class="form-control">-->
									<!-- <input id="approveComments" name="approveComments" type="text"
											value="" required="true"/> <label
											for="approveComments">Comments</label> -->
								</div>
							</div>

							<input type="hidden" name="claimId" id="claimId"
								value="${claim.benefitPlanClaimId}" /> <input type="hidden"
								name="claimDetailIds" id="claimDetailIds" /> <a
								class="btn yellow darken-3"
								href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/searchClaims">Back</a>
							<input type="submit" id="btnClaimApprove" class="btn btn-primary"
								value="Reject Claim" />
						</form>
					</div>
				</div>
			</div>
		</div>
		</section>
		<div id="duplicateModal" class="modal modal-fixed-footer">
			<div class="modal-content">
				<h4>Duplicate Bill(s)</h4>
				<table class="table striped table-borderd">
					<tr>
						<th>Bill No.</th>
						<th>Duplicate Claim(s)</th>
					</tr>
					<c:forEach var="d" items="${duplicates}">
						<tr>
							<td>${d.key}</td>
							<td>
							<c:forEach items="${d.value}" var="claim">
								${claim.claimRefNo},

							</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</table>
				<!-- <a href="#!"
				class="modal-action modal-close waves-effect waves-green btn-flat ">Agree</a> -->
			</div>
		</div>
	</div>
	</div><%@include file="includeFooter.jsp"%>
</body>

<script type="text/javascript">
	$('#myform input:checkbox').change(function() {
		var a = $('#myform input:checked').filter(":checked").length;
		if (a == 0) {
			$('#btnClaimApprove').val("Reject Claim");
			/* 	$('.approveAmtHide').hide(); */
		} else {
			$('#btnClaimApprove').val("Approve Claim");
			/* $('.approveAmtHide').show(); */

		}
	});

	$(document).on("keyup", ".approvedAmount", function() {
		$('#btnClaimApprove').show();
		var sum = 0;
		var index = $(this).attr('id').replace('approvedAmount', '');
		var amount = parseFloat($('#billAmount' + index).html());
		var approvedAmount = $('#approvedAmount' + index).val();
		if (approvedAmount > amount) {
			alert('Enter a value less than or equal to  ' + amount);
			$('#btnClaimApprove').hide();
		}
		$(".approvedAmount").each(function() {
			sum += +$(this).val();
		});
		$("#claimTotal").val(sum);
	});

	function abc() {
		var sum = 0;
		$(".approvedAmount").each(function() {
			sum += +$(this).val();
		});
		$("#claimTotal").val(sum);
	}

	$('input[type="checkbox"]').click(function() {
		//$(this).prev('.approveAmtHide').toggle(this.checked);
		// $(".approveAmtHide").show();
		var index = $(this).attr('id').replace('verifyBill', '');
		if ($(this).is(':checked')) {
			$('#approvedAmount' + index).show();
		} else {
			$('#approvedAmount' + index).hide();
			$('#approvedAmount' + index).val(0);
			abc();
		}
	});

	/* $(".approvedAmount").on("change", function() */
	/* 	$('.approvedAmount').keyup(function(event)	
	 {
	 var totalAmount = 0;
	 var amount=parseFloat($(".billAmount").html()); 
	 totalAmount = $('.approvedAmount').val();
	 if(totalAmount<=amount){
	 $('#claimTotal').val(totalAmount);
	 $('#btnClaimApprove').show();
	 }
	 else{
	 $('#claimTotal').val("");
	 window.alert('Please enter an amount less than or equal to    \nAmount:'+amount);
	 $('#btnClaimApprove').hide();
	
	 }
	

	 }); */

	$(".verify_bill").on("change", function() {
		var totalAmount = 0;

		var claimDetailIds = "";
		$('.verify_bill').each(function() {
			if (this.checked) {
				var detailId = $(this).val();

				claimDetailIds += ($(this).val() + ",");
				/* totalAmount=$('.approvedAmount').val(); */
				/* totalAmount += parseFloat($("#billAmount"+detailId).html()); */
			}
		});

		$("#claimDetailIds").val(claimDetailIds);
		/*  $('#claimTotal').val(totalAmount); */

	});
	function mydfunction() {
		alert("Bills are as follows");
	};

	window.addEventListener('load', function() {
		var a = $('#myform input:checked').filter(":checked").length;
		if (a == 0) {
			$('[id^=approvedAmount]').hide();
		}

	}, false);
</script>
</html>

