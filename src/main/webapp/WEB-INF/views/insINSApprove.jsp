
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlan"%>
<%@page import="com.speridian.benefits2.service.BenefitPlanService"%>
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlanEmployee"%>
<%@page import="com.speridian.benefits2.model.pojo.Employee"%>
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
					<td>${claimRefNo}</td>
				</tr>
				<tr>
					<th>Claim Date</th>
					<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${now}" /></td>
				</tr>
			</table>
		</span>
	</div>

	<div id="main">
		<div class="wrapper">
			<c:if test="${adminMode}">
				<%@include file="adminLeftNav.jsp"%>
			</c:if>
			<section id="content" class="">

			<div class="white col-md-12">
				<div class="row">
					<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">Bill Submission Form -
							${planEmployee.insPlan.planName}</h4>
					</div>
				</div>
				<c:if test="${adminMode}">
					<div class="row">
						<table>
							<tr>
								<td>Claim Ref No. <strong>${claimRefNo}</strong></td>
								<td>Claim Date <strong><fmt:formatDate
											pattern="dd-MMM-yyyy" value="${now}" /></strong></td>
							</tr>
						</table>
					</div>
				</c:if>
				<div class="row">
					<form:form id="preAuthForm" method="post" action="${claimId}/approve"
						modelAttribute="preAuthBean" cssClass="form-horizontal">

						<div class="col s12">
							<ul class="tabs">
								<li class="tab col s2"><a id="link_tabSubscriberInfo"
									class="active tab_link" href="#tabSubscriberInfo">Subscriber
										Info</a></li>
								<li class="tab col s2"><a id="link_tabMemberInfo"
									class="tab_link" href="#tabMemberInfo">Member Info</a></li>
								<li class="tab col s2"><a id="link_tabProcedureInfo"
									class="tab_link" href="#tabProcedureInfo">Procedure Info</a></li>
								<li class="tab col s2"><a id="link_tabProviderInfo"
									class="tab_link" href="#tabProviderInfo">Provider Info</a></li>
								<li class="tab col s2"><a id="link_tabBillInfo"
									class="tab_link" href="#tabBillInfo">Bill Info</a></li>
							</ul>
						</div>
						<div id="tabSubscriberInfo" class="col s12 tab_content">
							<table class="table striped table-borderd">
								<tr>
									<th>Name</th>
									<td>${planEmployee.employee.firstName}
										${planEmployee.employee.lastName}</td>
									<th>Associate Id</th>
									<td>${planEmployee.employee.employeeCode}</td>
								</tr>
								<tr>
									<th>Date of Birth</th>
									<td><fmt:formatDate pattern="dd-MMM-yyyy"
											value="${planEmployee.employee.dateOfBirth}" /></td>
									<th>Date of Joining</th>
									<td><fmt:formatDate pattern="dd-MMM-yyyy"
											value="${planEmployee.employee.dateOfJoin}" /></td>
								</tr>
								<tr>
									<th>Designation</th>
									<td>${planEmployee.employee.designationName}</td>
									<th>Department</th>
									<td>${planEmployee.employee.departmentName}</td>
								</tr>
								<tr>
									<th>Mobile No.</th>
									<td>${planEmployee.employee.mobileNo}</td>
									<th>Email</th>
									<td>${planEmployee.employee.email}</td>
								</tr>
								<tr>
									<th>Blood Group</th>
									<td>${planEmployee.employee.bloodGroup}</td>
									<th>Parent Office</th>
									<td>${planEmployee.employee.parentOffice}</td>
								</tr>
								<tr>
									<th>Manager</th>
									<td colspan="3">${planEmployee.employee.managerFullName}</td>
								</tr>
							</table>
							<div class="form-group">
								<button id="btnPreviousSubscriberInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabSubscriberInfo')" disabled="disabled">
									Previous</button>
								<button id="btnNextSubscriberInfo" type="button" class="btn"
									onclick="showTab('tabMemberInfo')">Next</button>
							</div>

						</div>
						<div id="tabMemberInfo" class="col s12 tab_content">
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input id="insurancePeriod" name="insurancePeriod"
											type="text" path="fiscalYear" cssClass="form-control"
											required="true" disabled="true" />
										<label for="insurancePeriod">Insurance Period</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<input type="text" id="member" name="member" value="${claimDetails.dependent.dependentName}">
										
										<label for="member">Member</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="memberDob" id="memberDob" disabled="true" />
										<label for="memberDob">Date of Birth</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="memberRelationship" id="memberRelation"
											disabled="true" />
										<label for="memberRelation">Relationship with Employee</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<button id="btnPreviousTabMemberInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabSubscriberInfo')">Previous</button>
								<button id="btnNextTabMemberInfo" type="button" class="btn"
									onclick="showTab('tabProcedureInfo')">Next</button>
							</div>

						</div> 
						 <div id="tabProcedureInfo" class="col s12 tab_content">
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<input type="text"
											value="${claimDetails.treatment.treatmentName}"
											id="treatment"> 
										<label for="treatment">Treatment Category</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="illnessType" id="ilnessType" />
										<label for="ilnessType">Type of Illness</label>
									</div>
								</div>
							</div>

							<div class="form-group">
								<button id="btnPreviousProcedureInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabMemberInfo')">Previous</button>
								<button id="btnNextProcedureInfo" type="button" class="btn"
									onclick="showTab('tabProviderInfo')">Next</button>
							</div>
						</div>
 

						<div id="tabProviderInfo" class="col s12 tab_content">
							<div class="form-group">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<input type="text" id="state" value="${preAuthBean.state}" readonly="readonly" style="color: black; border-bottom-color: #9e9e9e; border-bottom-style: double;"/>
										<label for="state" style="color: #21b6ca;">State</label>
									</div>
								</div>
							</div>


							<div class="form-group">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<input type="text" id="city" value="${preAuthBean.city}" readonly="readonly" style="color: black; border-bottom-color: #9e9e9e; border-bottom-style: double;"/>
										<label for="city" style="color: #21b6ca;">City</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<input type="text" name="hospital" id="hospital" value="${preAuthBean.hospitalName}" readonly="readonly" style="color: black; border-bottom-color: #9e9e9e; border-bottom-style: double;">
										<label for="hospital" style="color: #21b6ca;">Hospital</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<label style="color: rgba(238, 110, 115, 0.7);margin-top: -10px;">Prescriber
											Details</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<form:input path="prescriberName" id="prescriberName" />
										<label for="prescriberName">Name</label>
									</div>
									<div class="col-md-6 padding_left_0">
										<form:input path="prescriberContactNo"
											id="prescriberContactNo" />
										<label for="prescriberContactNo">Contact No.</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<form:input path="prescriberEmail" id="prescriberEmail" />
										<label for="prescriberEmail">Email</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<fieldset class="form-group">
											<form:checkbox path="speclistServiceRequired"
												id="speclistServiceRequired" />
											<label for="speclistServiceRequired">Do you need any
												Specialist's Services?</label>
										</fieldset>
									</div>
								</div>
							</div>
<c:if test="${speclistServiceRequired eq  true}">
							<div id="specialistDetails">
								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<label style="color: rgba(238, 110, 115, 0.7)">Specialist
												Details</label>
										</div>
									</div>
								</div>

								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<form:input path="specialistName" id="specialistName" />
											<label for="specialistName">Name</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<form:input path="specialistContactNo"
												id="specialistContactNo" />
											<label for="specialistContactNo">Contact No.</label>
										</div>
									</div>
								</div>

								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<form:input path="specialistEmail" id="specialistEmail" />
											<label for="specialistEmail">Email</label>
										</div>
									</div>
								</div>
							</div>
</c:if>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<label style="color: rgba(238, 110, 115, 0.7);margin-top: -10px;">PRO/Administrator
											Details</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<form:input path="proName" id="proName" />
										<label for="proName">Name</label>
									</div>
									<div class="col-md-6 padding_left_0">
										<form:input path="proContactNo" id="proContactNo" />
										<label for="proContactNo">Contact No.</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<form:input path="proEmail" id="proEmail" />
										<label for="proEmail">Email</label>
									</div>
								</div>
							</div>

							<div class="form-group">
								<button id="btnPreviousProcedureInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabProcedureInfo')" disabled="disabled">
									Previous</button>
								<button id="btnNextbILLInfo" type="button" class="btn"
									onclick="showTab('tabBillInfo')">Next</button>
							</div>

						</div> 
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
													<td>Verified Amount</td>
													<td>Action</td>
												</tr>
											</thead>
											<tbody>
												<c:if test="${bills ne null}">


													<c:forEach items="${bills}" var="bill">
														<tr>
															<td>${bill.billNo}</td>
															<td>
															<fmt:formatDate pattern="dd/MMM/yyyy" value = "${bill.billDate}"/>	</td>
															<td>${bill.billIssuer}</td>

															<td>${bill.amountRequested}</td>
															<td>${bill.amountVerified}</td>
															<td><a
																href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/billsHRApprove/billDetail/${bill.claimBillId}"><i
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
									<div class="col-md-6 padding_left_0">
										<label style="color: rgba(0, 0, 255, 0.3)"> CategoryWise Splittup of Bills</label>
									</div>
								</div>
							</div>


<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">

										<table class="responstable ">
											<thead>
												<tr>

													<td>Category Name</td>
													<td>Requested Amount</td>
													<td>Approved Amount</td>
													<td>Rejected Amount</td>

													
												</tr>
											</thead>
											<tbody>
												<c:if test="${categoryLIst ne null}">


													<c:forEach items="${categoryLIst}" var="cat">
														<tr>
															<td>${cat.categoryName}</td>
															<td>
															${cat.reqAmt}	</td>
															<td>${cat.apprAmt}</td>

															<td>${cat.rejAmt}</td>
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
										<form:input path="amountRequired" id="amountRequired"
											required="true" />
										<label for="amountRequired">Total Amount claimed(Bill
											Total)</label>
									</div>
								</div>
							</div> 
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<input type="number"name="totalApproved" id="totalApproved" value="${claim.totalApprovedAmount}"
											 /> <label for="totalApproved">Total
											Approved Amount(Rs.)</label>
									</div>


								</div>
							</div>
							
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:textarea path="comments" id="comments" />
										<label for="comments">Employee Comments</label>
									</div>
								</div>
							</div>
							
								<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:textarea path="hrComments" id="hrComments" /><label
											for="hrComments">HR Comments</label>
									</div>
								</div>
							</div>
							
								<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<textarea name="insComments" id="insComments" ></textarea><label
											for="insComments">Insurance Committe Comments</label>
									</div>
								</div>
							</div>



							
							
							<input type="hidden" name="formAction" id="formAction"
								value="save" /> <input type="hidden" name="pafMode"
								id="pafMode" value="${pafMode}" />

							<div class="form-group">

								<button id="btnPreviousProcedureInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabProviderInfo')">Previous</button>
								<a class="btn red darken-3"
									href="<%=request.getContextPath() %>/home/controlPanel/insurancePlans/searchClaims/billsINSApprove/${claim.insPlanEmployeeClaimId}/reject">Reject</a>
								<input type="submit" id="btnClaimApprove"
									class="btn green btn-primary" value="Approve Claim" />							</div>
						</div>
				

					</form:form>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="insBillDetailsFooter.jsp"%>
	<script src="jquery-3.2.1.min.js"></script>
	<script>
		$("select").material_select();
		$('#specialistDetails').hide();
		$('#member').on(
				'change',
				function() {
					var selectedDep = $(this).val();

					$('#memberDob').val(
							$('.member_id_' + selectedDep).attr('_dob'));
					$('#memberRelation').val(
							$('.member_id_' + selectedDep)
									.attr('_relationship'));

					Materialize.updateTextFields();

				});

		$('#treatment').on(
				'change',
				function() {
					var selectedTreatment = $(this).val();

					$('#estimateAmount').val(
							$('.treatment_id_' + selectedTreatment).attr(
									'_averageAmount'));

					Materialize.updateTextFields();
				});

		$('#state').on('change', function() {
			var state = $(this).val();

			$('.city').addClass('display_none');

			$('.city_' + state).removeClass('display_none');

		});

		$('#city').on('change', function() {
			var city = $(this).val();

			$('.hospital').addClass('display_none');

			$('.hospital_' + city).removeClass('display_none');

		});

		$('#speclistServiceRequired').on('change', function() {
			if (this.checked) {
				$('#specialistDetails').show();
			} else {
				$('#specialistDetails').hide();
			}
		});

		//preAuthForm 

		$('#btnSave').click(function() {
			$('#formAction').val('save');
			$('#preAuthForm').submit();
		});

		$('#btnSubmit').click(function() {
			$('#formAction').val('submit');
			$('#preAuthForm').submit();
		});

		function showTab(activeTabId) {
			$('ul.tabs').tabs('select_tab', activeTabId);
		}

		function setUrl(e, planEmployeeId) {

			row = $(e).closest('tr');

			rowIndex = row.index();
			$("#preAuthForm").attr(
					"action",
					"/home/controlPanel/insurancePlans/optedEmployees/bills/new/billDetail/"
							+ rowIndex);

		}

		$("#btnAddRow").click(
				function() {

					var rowCount = parseFloat($("#rowCount").val());
					rowCount++;
					var $clone = $(".dynamicTable tbody tr:first").clone();

					$clone.attr({
						id : "bill" + rowCount,
						name : "bill" + rowCount,
						style : "" // remove "display:none"
					});

					$clone.find("input").each(
							function() {

								var id = this.id.substr(0, this.id.length - 1)
										+ rowCount;

								var name = this.name.substr(0,
										this.name.length - 1)
										+ rowCount;
								$(this).attr({

									id : id,
									name : name

								});
							});
					$clone.find("select").each(
							function() {
								var id = this.id.substr(0, this.id.length - 1)
										+ rowCount;

								var name = this.name.substr(0,
										this.name.length - 1)
										+ rowCount;
								$(this).attr({

									id : id,
									name : name

								});
							});

					$clone.find("input[type='text']").each(function() {
						$(this).val("");
					});
					$clone.find("input[type='number']").each(function() {
						$(this).val("");
					});
					$clone.find("input[type='date']").each(function() {
						$(this).val("");
					});
					$(".dynamicTable tbody").append($clone);
					$("#rowCount").val(rowCount);

				});
	</script>

</body>
</html>
