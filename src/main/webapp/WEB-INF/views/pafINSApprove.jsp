
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
<body>
	<%@include file="employeeNavBar.jsp"%>

	<div id="main">
		<div class="wrapper">
			<%@include file="adminLeftNav.jsp"%>
			<section id="content" class="">

			<div class="white col-md-12">
				<div class="row">
					<div class="col-sm-6 col-md-9 py-1 px-1" style="color: #21b6ca;">
						<h4 class="h4-responsive">Verify PAF Claim -
							${claim.claimRefno}</h4>
					</div>
				</div>
				<div class="row">
					<form:form id="preAuthForm" method="post" action="${claim.insPlanEmployeeClaimId}/approve"
						modelAttribute="preAuthBean" cssClass="form-horizontal">

						<div class="col s12">
							<ul class="tabs">
								<li class="tab col s3"><a id="link_tabSubscriberInfo"
									class="active tab_link" href="#tabSubscriberInfo">Subscriber
										Info</a></li>
								<li class="tab col s3"><a id="link_tabMemberInfo"
									class="tab_link" href="#tabMemberInfo">Member Info</a></li>
								<li class="tab col s3"><a id="link_tabProviderInfo"
									class="tab_link" href="#tabProviderInfo">Provider Info</a></li>
								<li class="tab col s3"><a id="link_tabProcedureInfo"
									class="tab_link" href="#tabProcedureInfo">Procedure Info</a></li>
								
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
											required="true" />
										<label for="insurancePeriod">Insurance Period</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">

										<input type="text"
											value="${claimDetails.dependent.dependentName}"> <label
											for="member">Member</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="memberDob" id="memberDob" />
										<label for="memberDob">Date of Birth</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="memberRelationship" id="memberRelation"
											readonly="readonly" />
										<label for="memberRelation">Relationship with Employee</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<button id="btnPreviousTabMemberInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabSubscriberInfo')">Previous</button>
								<button id="btnNextTabMemberInfo" type="button" class="btn"
									onclick="showTab('tabProviderInfo')">Next</button>
							</div>

						</div>
						

						<div id="tabProviderInfo" class="col s12 tab_content">
							<%-- <div class="form-group is-empty">
								<div class="md-form auth_dropdown">
									<div class="col-md-6 padding_left_0">
																					
																					
																					<form:select path="state" id="state">


												<option>${claimDetails.hospital.state}</option>

											</form:select>
											
										<label for="state">State</label>
									</div>
								</div>
							</div>


							<div class="form-group is-empty">
								<div class="md-form auth_dropdown">
									<div class="col-md-6 padding_left_0">
										<form:select path="city" id="city">


												<option class="city">${claimDetails.hospital.city}</option>

											</form:select>
											
										<label for="city">City</label>
									</div>
								</div>
							</div> --%>
							<div class="form-group is-empty">
								<div class="md-form auth_dropdown">
									<div class="col-md-6 padding_left_0" style="font-size: 15px">

										<h5>Hospital</h5>
										<c:choose>
										<c:when test="${!otherHospital}">
										${claimDetails.hospital.hospitalName},<br />
										${claimDetails.hospital.address},<br />
										${claimDetails.hospital.city},<br />
										${claimDetails.hospital.state}<br />
										</c:when>
										<c:otherwise>
										${preAuthBean.otherHospital},<br/>
										${preAuthBean.city},<br/>
										${preAuthBean.state}<br/>
										
										</c:otherwise>
										</c:choose>



									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0"></div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<label style="color: rgba(238, 110, 115, 0.7)">Prescriber
											Details</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0"></div>
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
									<div class="col-md-12 padding_left_0" style="font-size: 15px">
										Any Specialist Services availed :
										<c:choose>
											<c:when
												test="${claimDetails.isSpecialistServicesAvailed eq 'true'}">Yes</c:when>
											<c:otherwise>
										No
										</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0"></div>
								</div>
							</div>
							<c:if
								test="${claimDetails.isSpecialistServicesAvailed eq 'true'}">
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
											<div class="col-md-6 padding_left_0"></div>
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
										<label style="color: rgba(238, 110, 115, 0.7)">PRO/Administrator
											Details</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0"></div>
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
									onclick="showTab('tabMemberInfo')">Previous</button>
								<button id="btnNextProcedureInfo" type="button" class="btn"
									onclick="showTab('tabProcedureInfo')">Next</button>
							</div>

						</div>
						<div id="tabProcedureInfo" class="col s12 tab_content">
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">

										<input type="text"
											value="${claimDetails.treatment.treatmentName}"
											id="treatment"> <label for="treatment">Treatment
											Category</label>
									</div>
								</div>
							</div>
								<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="otherTreatment" id="otherTreatment" readonly="readonly"/>
										<label for="otherTreatment">Other Treatment if any</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0"></div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="illnessType" id="ilnessType"
											readonly="readonly" />
										<label for="ilnessType">Type of Illness</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="estimatedMedicalExpense" id="estimateAmount"
											readonly="readonly" />
										<label for="estimateAmount">Estimated Medical
											Expense(Rs.)</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="amountRequired" id="amountRequired"
											readonly="readonly" />
										<label for="amountRequired">Amount Required(Rs.)</label>
									</div>
								</div>
							</div>

								<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<input type="number" name="totalApproved" id="totalApproved"
											required /> <label for="totalApproved">Total
											Approved Amount(Rs.)</label>
									</div>


								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<input type="number" name="ins1" id="ins1" required /> <label
											for="ins1">Installment 1-Amount(Rs.)</label>
									</div>
									<div class="col-md-12 padding_left_0" id="ins1Warning"
										style="display: none;">1st Installment amount exceeds 30%
										limit</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<input type="number" name="ins2" id="ins2" required /> <label
											for="ins2">Installment 2-Amount(Rs.)</label>
									</div>
									<div class="col-md-12 padding_left_0" id="ins2Warning"
										style="display: none;">2nd Installment amount exceeds 40%
										limit</div>
								</div>
							</div>
			

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<label style="color: rgba(238, 110, 115, 0.7)">Comments</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="comments" id="comments" />
										<label for="comments">Employee Comments</label>
									</div>
								</div>
							</div>
					
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<input type="text" id="hrcomments" name="hrcomments"  />
										<label for="hrcomments">Insurance Desk Comments</label>
									</div>
								</div>
							</div>
							<form:hidden path="claimId" name="claimId" />


							<div class="form-group">

								<button id="btnPreviousProcedureInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabProviderInfo')">Previous</button>
								<a class="btn red darken-3"
									href="<%=request.getContextPath() %>/home/controlPanel/insurancePlans/searchClaims/PAFHRApprove/reject/${claim.insPlanEmployeeClaimId}">Reject</a>
								
								<input type="submit" id="btnClaimApprove"
									class="btn green btn-primary" value="Approve Claim" />
							</div>
							
						</div>
						


					</form:form>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
		/* 	function amountCheck() {
				var totalApprove = $('#totalApprove').val();
				ins1 = $('#ins1').val();
				ins2 = $('#ins2').val();
				actualIns1 = totalApprove * .3;
				actualIns2 = totalApprove * .4;
				if (ins1 > actualIns1) {
					alert("1st Insallment amount exceeds 30% limit");
				}
				if (ins2 > actualIns2) {
					alert("2st Insallment amount exceeds 40% limit");
				}
			} */

		$('#ins1').on('change', function() {
			var ins1 = $('#ins1').val();
			var totalApprove = $('#totalApproved').val();
			var actualIns1 = totalApprove * .3;
			if (ins1 > actualIns1) {
				$("#ins1Warning").css("display", "block");
			} else {
				$("#ins1Warning").css("display", "none");
			}

		});

		$('#ins2').on('change', function() {
			var ins2 = $('#ins2').val();
			var totalApprove = $('#totalApproved').val();
			var actualIns2 = totalApprove * .4;
			if (ins2 > actualIns2) {
				$("#ins2Warning").css("display", "block");
			} else {
				$("#ins2Warning").css("display", "none");
			}

		});

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
	</script>

</body>
</html>
