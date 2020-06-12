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
							<h4 class="h4-responsive">${claimPafDetail.claim.planEmployee.employee.firstName}
								${claimPafDetail.claim.planEmployee.employee.lastName} - PAF
								Details</h4>
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
									<td colspan="3">${claimPafDetail.claim.planEmployee.employee.firstName}
										${planEmployee.employee.lastName}</td>
									<th>Employee Code</th>
									<td colspan="3">${claimPafDetail.claim.planEmployee.employee.employeeCode}</td>
								</tr>

								<tr>
									<th>Claim Ref. No.</th>
									<td colspan="3">${claimPafDetail.claim.claimRefno}</td>
									<th>Dependent Name</th>
									<td colspan="3">${claimPafDetail.dependent.dependentName}</td>
								</tr>
							</table>

							<h4 class="h4-responsive">Installment Details</h4>

							<table class="table striped table-borderd">
								<tr>
									<th>Installment No.</th>
									<th>Approved Amount</th>
									<th>Status</th>
									<th>Pay
										<div class="remember-label">
											<div>
												<input type="checkbox" id="selectAll" name="selectAll"
													value="${claimPafDetail.claimPafId}" /> <label
													for="selectAll"></label>
											</div>
										</div>
									</th>
								</tr>

								<tr>
									<td>Installment 1</td>
									<td>${claimPafDetail.amountApprovedInstallment1}</td>
									<td><c:choose>
											<c:when test="${claimPafDetail.amount1Status eq false}">Not Paid</c:when>
											<c:when test="${claimPafDetail.amount1Status eq true}">Paid</c:when>
										</c:choose></td>
									<td><c:if test="${claimPafDetail.amount1Status eq false}">
											<div class="remember-label">
												<div>
													<input class="chk" type="checkbox" id="selectPAF1"
														name="selectPAF1" value="1" onclick="appendId(this)"/> <label
														for="selectPAF1"></label>
												</div>
											</div>
										</c:if></td>
								</tr>

								<tr>
									<td>Installment 2</td>
									<td>${claimPafDetail.amountApprovedInstallment2}</td>
									<td><c:choose>
											<c:when test="${claimPafDetail.amount2Status eq false}">Not Paid</c:when>
											<c:when test="${claimPafDetail.amount2Status eq true}">Paid</c:when>
										</c:choose></td>

									<td><c:if test="${claimPafDetail.amount2Status eq false}">
											<div class="remember-label">
												<div>
													<input class="chk" type="checkbox" id="selectPAF2"
														name="selectPAF2" value="2" onclick="appendId(this)"/> <label
														for="selectPAF2"></label>
												</div>
											</div>
										</c:if></td>
								</tr>

							</table>

							<form id="payHiddenId"
								action="/home/controlPanel/insurancePlans/searchClaims/BillsPay/Paid/${claimPafDetail.claimPafId}">
								<div class="form-group">

									<div class="col-md-6">
										<input type="hidden" id="paySelected" value=""
											name="paySelected"> <input type="submit"
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
	function append(e)

	{
		$('paySelected').val=$('paySelected').val + "," +e.val();
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
			$('#totalAdded').val($('#total').val());
		});
	</script>
</body>
</html>