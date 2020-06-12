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
							<h4 class="h4-responsive"><font color="#24D9DC">${insPlanEmployeeClaim.planEmployee.employee.firstName}
								${insPlanEmployeeClaim.planEmployee.employee.lastName} - Claim
								Details</font></h4>
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
							<table class="table striped table-borderd">

								<tr>
									<th>Employee Name</th>
									<td colspan="3">${insPlanEmployeeClaim.planEmployee.employee.firstName}
										${insPlanEmployeeClaim.planEmployee.employee.lastName}</td>
									<th>Employee Code</th>
									<td colspan="3">${insPlanEmployeeClaim.planEmployee.employee.employeeCode}</td>
								</tr>

								<tr>
									<th>Claim Ref. No.</th>
									<td colspan="3">${insPlanEmployeeClaim.claimRefno}</td>
									<th>Fiscal Year</th>
									<td colspan="3">${insPlanEmployeeClaim.fiscalYear}</td>
								</tr>
							</table>
	<input type="hidden" value="${insPlanEmployeeClaim.totalApprovedAmount}" id="total">
							<h4 class="h4-responsive"><font color="#24D9DC">Bill Amount Details</font></h4>
					
					
					<table class="table striped table-borderd">
								<tr>
									<th>Bill #</th>
									<th>Approved Amount</th>
									<th>Status</th>
									<th>	
											<div>
												<input type="checkbox" id="selectAll" name="selectAll"
													value="${insPlanEmployeeClaim.insPlanEmployeeClaimId}" /> <label
													for="selectAll"></label>
											</div>
			
									</th>
								</tr>
								<c:forEach items="${billList}" var="billList">
								<tr>
									<td>${billList.billNo}</td>
									<td>${billList.amountApproved}</td>
									<td><c:choose>
											<c:when test="${billList.status eq 'PAID'}">Paid</c:when>
											<c:when test="${billList.status eq 'NOT PAID' || billList.status eq 'APPR'}">Not Paid</c:when>
										</c:choose></td>
									<td><c:if test="${billList.status ne 'PAID'}">
												<div class="remember-label">
													<div>
														<input class="chk" type="checkbox"
															id="selectBill${billList.claimBillId}" name="selectBill"
															value="${billList.claimBillId}" /> <label
															for="selectBill${billList.claimBillId}" onclick="append(this,${billList.amountApproved})"></label>
													</div>
												</div>
											</c:if></td>
								</tr>
								</c:forEach>
							</table>
							
							<form id="payHiddenId"
								action="/home/controlPanel/insurancePlans/searchClaims/payBills/Paid/${insPlanEmployeeClaim.insPlanEmployeeClaimId}">
								<div class="form-group">

									<div class="col-md-6">
										<input type="hidden" id="paySelected" value=""
											name="paySelected">
										<input type="text" id="totalAdded" name="totalAdded" value="totalAdded"  />
											 <input type="submit"
											class="btn btn-primary payClass" id="pay" name="pay"
											value="Pay" />
									</div>
								</div>
							</form> 

						</div>
					</div>

				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
	
	
	function append(e,approvedAmount) {
		row = $(e).closest('tr');
		rowIndex = row.index();
		if ($(e).prop("checked") == true) {
			$('#paySelected').val($('#paySelected').val + "," + $(e).val());
			$('#totalAdded').val(
					parseFloat($('#totalAdded').val())
							+ parseFloat(approvedAmount));
			
		} else {
			$('#totalAdded').val(
					parseFloat($('#totalAdded').val())
							- parseFloat(approvedAmount));
		}
	}
	
	
	function Populate() {
			vals = $('.chk:checked').map(function() {
				return this.value;
			}).get().join(',');
			console.log(vals);
			$('#paySelected').val(vals);

			if (vals != '') {
				$('#pay').prop("disabled", false);
			} else {
				$('#pay').prop("disabled", true);
			}
		}

		$('input[type="checkbox"]').on('change', function() {
			Populate()
		}).change();

		$('#pay').prop("disabled", true);
		$('.chk').click(function() {
			if ($(this).is(':checked')) {
				$('#pay').prop("disabled", false);
			} else {
				if ($('.chk').filter(':checked').length < 1) {
					$('#pay').attr('disabled', true);
				}
			}
		});

		$("#selectAll").click(function() {
			$('input:checkbox').not(this).prop('checked', this.checked);
			$('#paySelected').val("all");
			$('#totalAdded').val($('#total').val());
		});
	</script>
</body>
</html>