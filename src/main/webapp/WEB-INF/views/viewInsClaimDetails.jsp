<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<style>
.responstable {
	margin: 1em 0;
	width: 100%;
	overflow: hidden;
	background: #FFF;
	color: #024457;
	border-radius: 10px;
	border: 1px solid #167F92;
}

.responstable tr {
	border: 1px solid #D9E4E6;
}

.responstable tr:nth-child(odd) {
	background-color: #EAF3F3;
}

.responstable th {
	display: none;
	border: 1px solid #FFF;
	background-color: #167F92;
	color: #FFF;
	padding: 1em;
}

.responstable th:first-child {
	display: table-cell;
	text-align: center;
}

.responstable th:nth-child(2) {
	display: table-cell;
}

.responstable th:nth-child(2) span {
	display: none;
}

.responstable th:nth-child(2):after {
	content: attr(data-th);
}

@media ( min-width : 480px) {
	.responstable th:nth-child(2) span {
		display: block;
	}
	.responstable th:nth-child(2):after {
		display: none;
	}
}

.responstable td {
	display: block;
	word-wrap: break-word;
	max-width: 7em;
}

.responstable td:first-child {
	display: table-cell;
	text-align: center;
	border-right: 1px solid #D9E4E6;
}

@media ( min-width : 480px) {
	.responstable td {
		border: 1px solid #D9E4E6;
	}
}

.responstable th,.responstable td {
	text-align: left;
	margin: .5em 1em;
}

@media ( min-width : 480px) {
	.responstable th,.responstable td {
		display: table-cell;
		padding: 1em;
	}
}
</style>
<body>
	<%@include file="employeeNavBar.jsp"%>
	<div class="col-md-3">
		<span id="myClaimDetails" class="sticky_left">
			<table>
				<tr>
					<th>Claim Ref No.</th>
					<td>${claim.claimRefno}</td>
				</tr>
				<tr>
					<th>Claim Date</th>
					<td><fmt:formatDate pattern="dd-MMM-yyyy"
							value="${claim.requestedDate}" /></td>
				</tr>
			</table>
		</span>
	</div>
	<div id="main">
		<div class="wrapper">
			<section id="content" class="">
			<div class="white col-md-12">
				<div class="row">
					<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">Manage My Claims</h4>
					</div>
				</div>
				<div class="row">
					<div class="col s12">
						<ul class="tabs">
							<li class="tab col s3"><a id="link_tabSubscriberInfo"
								class="active tab_link" href="#tabSubscriberInfo">Subscriber
									Info</a></li>
							<li class="tab col s2"><a id="link_tabMemberInfo"
								class="tab_link" href="#tabMemberInfo">Member Info</a></li>
							<li class="tab col s2"><a id="link_tabProcedureInfo"
								class="tab_link" href="#tabProcedureInfo">Procedure Info</a></li>
							<li class="tab col s2"><a id="link_tabProviderInfo"
								class="tab_link" href="#tabProviderInfo">Provider Info</a></li>
								<c:if test="${isBill}">
								<li class="tab col s2"><a id="link_tabBillInfo"
								class="tab_link" href="#tabBillInfo">Bill Info</a></li>
								</c:if>
						</ul>
					</div>
					<div id="tabSubscriberInfo" class="col s12 tab_content">
						<table class="table striped table-borderd">
							<tr>
								<th>Name</th>
								<td>${claim.planEmployee.employee.firstName}
									${claim.planEmployee.employee.lastName}</td>
								<th>Associate Id</th>
								<td>${claim.planEmployee.employee.employeeCode}</td>
							</tr>
							<tr>
								<th>Date of Birth</th>
								<td><fmt:formatDate pattern="dd-MMM-yyyy"
										value="${claim.planEmployee.employee.dateOfBirth}" /></td>
								<th>Date of Joining</th>
								<td><fmt:formatDate pattern="dd-MMM-yyyy"
										value="${claim.planEmployee.employee.dateOfJoin}" /></td>
							</tr>
							<tr>
								<th>Designation</th>
								<td>${claim.planEmployee.employee.designationName}</td>
								<th>Department</th>
								<td>${claim.planEmployee.employee.departmentName}</td>
							</tr>
							<tr>
								<th>Mobile No.</th>
								<td>${claim.planEmployee.employee.mobileNo}</td>
								<th>Email</th>
								<td>${claim.planEmployee.employee.email}</td>
							</tr>
							<tr>
								<th>Blood Group</th>
								<td>${claim.planEmployee.employee.bloodGroup}</td>
								<th>Parent Office</th>
								<td>${claim.planEmployee.employee.parentOffice}</td>
							</tr>
							<tr>
								<th>Manager</th>
								<td colspan="3">${claim.planEmployee.employee.managerFullName}</td>
							</tr>
						</table>
						<div class="form-group">
							<a href="<%=request.getContextPath()%>/home/myInsurancePlan/${planEmployeeId}/viewClaims" class="btn btn yellow darken-3" id="back">Back</a>
							<button id="btnPreviousSubscriberInfo" type="button" class="btn"
								onclick="showTab('tabMemberInfo')">Next</button>
						</div>

					</div>
					<div id="tabMemberInfo" class="col s12 tab_content">
						<table class="table striped table-borderd">
							<tr>
								<th>Insurance Period</th>
								<td>${claim.fiscalYear}</td>
							</tr>
							<tr>
								<th>Member</th>
								<td>${claim.claimPafDetail.dependent.dependentName}</td>
							</tr>
							<tr>
								<th>Date of Birth</th>
								<td>
								<fmt:formatDate pattern="dd-MMM-yyyy"
										value="${claim.claimPafDetail.dependent.dateOfBirth}" />
								</td>
							</tr>
							<tr>
								<th>Relationship</th>
								<td>${claim.claimPafDetail.dependent.relationship}</td>
							</tr>
						</table>
						<div class="form-group">
							<button id="btnPreviousMemberInfo" type="button"
								class="btn btn yellow darken-3"
								onclick="showTab('tabSubscriberInfo')">Previous</button>
							<button id="btnNextMemeberInfo" type="button" class="btn"
								onclick="showTab('tabProcedureInfo')">Next</button>
						</div>
					</div>
					<div id="tabProcedureInfo" class="col s12 tab_content">

						<table class="table striped table-borderd">
							<tr>
								<th>Treatment Category</th>
								<td>${claim.claimPafDetail.treatment.treatmentName}</td>
							</tr>
							<tr>
								<th>Type of Illness</th>
								<td>${claim.claimPafDetail.illnessType}</td>
							</tr>
							<tr>
								<th>Estimated Medical Expense</th>
								<td>Rs.${claim.claimPafDetail.amountEstimatedExpense}</td>
							</tr>
							<tr>
								<th>Advance Required</th>
								<td>Rs. ${claim.claimPafDetail.amountAdvanceRequired}</td>
							</tr>
						</table>
						<div class="form-group">
							<button id="btnPreviousProcedureInfo" type="button"
								class="btn btn yellow darken-3"
								onclick="showTab('tabMemberInfo')">Previous</button>
							<button id="btnNextProcedureInfo" type="button" class="btn"
								onclick="showTab('tabProviderInfo')">Next</button>
						</div>
					</div>


					<div id="tabProviderInfo" class="col s12 tab_content">


						<table class="table striped table-borderd">
							<tr>
								<th>Hospital</th>
								<td colspan="3">
								<c:choose>
								<c:when test="${selectedHospital}">
								${claim.claimPafDetail.hospital.hospitalName},
									${claim.claimPafDetail.hospital.city},
									${claim.claimPafDetail.hospital.state}
								</c:when>
								<c:otherwise>
								${claim.claimPafDetail.otherHospital},
									${claim.claimPafDetail.otherHospitalCity},
									${claim.claimPafDetail.otherHospitalState}
								
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
							<tr>
								<th colspan="4">Prescriber Details</th>
							</tr>
							<tr>
								<th>Name</th>
								<td>${claim.claimPafDetail.prescriberName}</td>
								<th>Contact No.</th>
								<td>${claim.claimPafDetail.prescriberContactNo}</td>
							</tr>
							<tr>
								<th>Email</th>
								<td>${claim.claimPafDetail.prescriberEmail}</td>
								<td />
								<td />
							</tr>

							<tr>
								<th colspan="4">Specialist Details</th>
							</tr>
							<tr>
								<th>Name</th>
								<td>${claim.claimPafDetail.specialistName}</td>
								<th>Contact No.</th>
								<td>${claim.claimPafDetail.specialistContactNo}</td>
							</tr>
							<tr>
								<th>Email</th>
								<td>${claim.claimPafDetail.specialistEmail}</td>
								<td />
								<td />
							</tr>

							<tr>
								<th colspan="4">PRO/Administrator Details</th>
							</tr>
							<tr>
								<th>Name</th>
								<td>${claim.claimPafDetail.proName}</td>
								<th>Contact No.</th>
								<td>${claim.claimPafDetail.proContactNo}</td>
							</tr>
							<tr>
								<th>Email</th>
								<td>${claim.claimPafDetail.proEmail}</td>
								<td />
								<td />
							</tr>

							<tr>
								<th>Your Comments</th>
								<td colspan="3">${claim.claimPafDetail.employeeComments}</td>
							</tr>
							<tr>
								<th>HR Comments</th>
								<td colspan="3">${claim.claimPafDetail.hrComments}</td>
							</tr>
							<tr>
								<th>Insurance Desk Comments</th>
								<td colspan="3">${claim.claimPafDetail.insDeskComments}</td>
							</tr>
						</table>
						<div class="form-group">
							<button id="btnPreviousProviderInfo" type="button"
								class="btn btn yellow darken-3"
								onclick="showTab('tabProcedureInfo')">Previous</button>
								<c:choose>
								<c:when test="${isBill}">
								<button id="btnNextProcedureInfo" type="button" class="btn"
								onclick="showTab('tabBillInfo')">Next</button>
								
								</c:when>
								<c:otherwise>
								<button id="btnNextProcedureInfo" type="button" class="btn"
								disabled="disabled">Next</button>
								</c:otherwise>
								</c:choose>
							
						</div>
					</div>
					<c:if test="${isBill}">
					<div id="tabBillInfo" class="col s12 tab_content">
					<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0"></div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<label style="color: rgba(0, 0, 255, 0.3)"> Bill
											Submission(Attach Bill Info By Category)</label>
									</div>
								</div>
							</div>


							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">

										<table class="responstable ">
											<thead>
												<tr>

													<td>Bill No</td>
													<td>Bill Date</td>
													<td>Bill Issuer</td>
													<td>Total Amount</td>

													<td>Action</td>
												</tr>
											</thead>
											<tbody>
												<c:if test="${bills ne null}">


													<c:forEach items="${bills}" var="bill">
														<tr>
															<td>${bill.billNo}</td>
															<td><fmt:formatDate pattern="dd-MMM-yyyy"
											value="${bill.billDate}" /></td>
															<!-- <td></td> -->
															<td>${bill.billIssuer}</td>

															<td>${bill.amountRequested}</td>
															<td><a
																href="<%=request.getContextPath()%>/home/myInsurancePlan/viewClaims/details/${bill.claimBillId}"><i
																	class="fa fa-external-link" aria-hidden="true"></i></a></td>
														</tr>
													</c:forEach>



												</c:if>

											</tbody>
										</table>


									</div>
								</div>
							</div>




							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<input type="number" id="amountRequired"
											readonly="readonly" value="${claim.totalReqAmount}"/>
										<label for="amountRequired">Total Amount claimed(Bill
											Total)</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<input type="number" name="totalApproved" id="totalApproved"
											value="${claim.totalApprovedAmount}" readonly="readonly" /> <label
											for="totalApproved">Total Approved Amount(Rs.)</label>
									</div>


								</div>
							</div>
								<div class="form-group">

								<button id="btnPreviousProcedureInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabProviderInfo')">Previous</button>
									<button id="btnNextProcedureInfo" type="button" class="btn"
								disabled="disabled">Next</button>

							</div>
					
					
					</div>
					</c:if>
					
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script type="text/javascript">
		function showTab(activeTabId) {
			$('ul.tabs').tabs('select_tab', activeTabId);
		}
	</script>
</body>
</html>