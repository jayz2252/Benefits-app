
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlan"%>
<%@page import="com.speridian.benefits2.service.BenefitPlanService"%>
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlanEmployee"%>
<%@page import="com.speridian.benefits2.model.pojo.Employee"%>
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
.alertAmount {
	border-bottom-color: red !important;
	background-color: #ffcccc !important;
}
</style>
<%@include file="include.jsp"%>
<body>
	<%@include file="employeeNavBar.jsp"%>
	<c:if test="${!adminMode}">
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
	</c:if>
	<div id="main">
		<div class="wrapper">
			<c:if test="${adminMode}">
				<%@include file="adminLeftNav.jsp"%>
			</c:if>
			<section id="content" class="">

			<div class="white col-md-12">
				<div class="row">
					<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">Pre Authorization Form -
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
					<form:form id="preAuthForm" method="post" action="new"
						modelAttribute="preAuthBean" cssClass="form-horizontal">

						<div class="col s12">
							<ul class="tabs">
								<li class="tab col s3"><a id="link_tabSubscriberInfo"
									class="active tab_link" href="#tabSubscriberInfo">Subscriber
										Info</a></li>
								<li class="tab col s3"><a id="link_tabMemberInfo"
									class="tab_link" href="#tabMemberInfo">Member Info</a></li>
								<li class="tab col s3"><a id="link_tabProcedureInfo"
									class="tab_link" href="#tabProcedureInfo">Procedure Info</a></li>
								<li class="tab col s3"><a id="link_tabProviderInfo"
									class="tab_link" href="#tabProviderInfo">Provider Info</a></li>
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
								<c:if test="${!adminMode}">
									<a href="<%=request.getContextPath()%>/home/myInsurancePlan"
										class="btn btn yellow darken-3" id="back">Back</a>
								</c:if>
								<c:if test="${adminMode}">
									<button id="btnPreviousSubscriberInfo" type="button"
										class="btn btn yellow darken-3"
										onclick="showTab('tabSubscriberInfo')" disabled="disabled">
										Previous</button>
								</c:if>
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
											required="required" disabled="true" />
										<label for="insurancePeriod">Insurance Period</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:select path="memberId" id="member">
											<option disabled selected>Choose Member</option>
											<c:forEach items="${planEmployee.details}" var="member">
												<option value="${member.dependent.dependentId}"
													${preAuthBean.memberId eq member.dependent.dependentId ? 'selected="selected"' : ''}
													class="member member_id_${member.dependent.
													dependentId}"
													_dob="<fmt:formatDate pattern='dd-MMM-yyyy' value='${member.dependent.dateOfBirth}'/>"
													_relationship="${member.dependent.relationship}">${member.dependent.dependentName}</option>
											</c:forEach>
										</form:select>
										<label for="member">Member</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="memberDob" id="memberDob" disabled="true"
											required="required" />
										<label for="memberDob">Date of Birth</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="memberRelationship" id="memberRelation"
											disabled="true" required="required" />
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
										<form:select path="treatmentId" id="treatment">
											<option disabled selected>Choose Treatment</option>
											<c:forEach items="${treatments}" var="trt">
												<option value="${trt.treatmentId}"
													class="treatment_id_${trt.treatmentId}"
													${preAuthBean.treatmentId eq trt.treatmentId ? 'selected="selected"' : ''}
													_averageAmount="${trt.averageAmount}">${trt.treatmentName}</option>
											</c:forEach>
										</form:select>
										<label for="treatment">Treatment Category</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="otherTreatment" id="otherTreatment" />
										<label for="otherTreatment">Other Treatment</label>
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

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="estimatedMedicalExpense" id="estimateAmount"
											required="required" />
										<label for="estimateAmount">Estimated Medical
											Expense(Rs.)</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="amountRequired" id="amountRequired"
											required="required" />
										<label for="amountRequired">Amount Required(Rs.)</label>
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

							<div class="form-group">
								<!-- doc upload -->
								<div class="alert alert-warning">
									<strong>Important Warning!</strong> Documents will be uploaded
									to DocMan. Click <i class="fa fa-paperclip" aria-hidden="true"></i>
									icon to launch Upload page
								</div>
								<table class="table striped table-borderd">
									<tr>
										<th>Claim Related Documents</th>
										<td><a target="_blank" href="${docUploadUrl}"
											title="Upload/Delete"><i class="fa fa-paperclip"
												aria-hidden="true"></i></a> <a target="_blank"
											href="${docDownloadUrl}" title="Download"><i
												class="fa fa-cloud-download" aria-hidden="true"></i></a></td>
									</tr>
								</table>
							</div>
						</div>


						<div id="tabProviderInfo" class="col s12 tab_content">
							<div class="form-group is-empty">
								<div class="md-form auth_dropdown">
									<div class="col-md-6 padding_left_0">
										<form:select path="state" id="state">
											<option disabled selected>Choose State</option>
											<c:forEach items="${states}" var="st">
												<option value="${st}" class="state_${st}"
													${preAuthBean.state eq st ? 'selected="selected"' : ''}>${st}</option>
											</c:forEach>
										</form:select>
										<label for="state">State</label>
									</div>
								</div>
							</div>


							<div class="form-group is-empty">
								<div class="md-form auth_dropdown">
									<div class="col-md-6 padding_left_0">
										<form:select path="city" id="city">
											<option disabled selected>Choose City</option>
											<%-- <c:forEach items="${cities}" var="ct">
												<option value="${ct.city}"
													class="city city_${ct.state} display_none"
													${preAuthBean.city eq ct.city ? 'selected="selected"' : ''}>${ct.city}</option>
											</c:forEach> --%>
										</form:select>
										<label for="city">City</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form auth_dropdown">
									<div class="col-md-6 padding_left_0">
										<form:select path="hospitalId" name="hospitalId"
											id="hospitalId"
											onchange="if (this.value=='0'){this.form['other'].style.visibility='visible'}else {this.form['other'].style.visibility='hidden'};">

											<%-- <c:forEach items="${hospitals}" var="hosp">
												<option class="hospital hospital_${hosp.city} display_none"
													value="${hosp.hospitalId}"
													${preAuthBean.hospitalId eq hosp.hospitalId ? 'selected="selected"' : ''}>${hosp.hospitalName}</option>
											</c:forEach> --%>
											<option disabled selected>Choose Hospital</option>
										</form:select>

										<label for="city">Hospital</label>

									</div>


								</div>
								<form:input type="text" path="otherHospital"
									placeholder="Enter Hospital here.." name="other" id="other"
									style="visibility:hidden;" class=""/>

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
										<form:input path="prescriberName" id="prescriberName"
											required="required" />
										<label for="prescriberName">Doctor's Name</label>
									</div>
									<div class="col-md-6 padding_left_0">
										<form:input path="prescriberContactNo"
											id="prescriberContactNo" required="required"
											onchange="return phoneNumberValidation('prescriberContactNo')" />
										<label for="prescriberContactNo">Contact No.</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<form:input path="prescriberEmail" id="prescriberEmail"
											required="required"
											onchange="return emailValidation('prescriberEmail')" />
										<label for="prescriberEmail">Email</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<fieldset class="form-group">
											<form:checkbox path="speclistServiceRequired"
												id="speclistServiceRequired" cssClass="incurenceCheckBox" />
											<label for="speclistServiceRequired"
												style="color: rgba(238, 110, 115, 0.7); margin-left: 36px; margin-top: -12px;">Do
												you need any Specialist's Services?</label>
										</fieldset>
									</div>
								</div>
							</div>

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
											<label for="specialistName">Doctor's Name</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<form:input path="specialistContactNo"
												id="specialistContactNo"
												onchange="return phoneNumberValidation('specialistContactNo')" />
											<label for="specialistContactNo">Contact No.</label>
										</div>
									</div>
								</div>

								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<form:input path="specialistEmail" id="specialistEmail"
												onchange="return emailValidation('specialistEmail')" />
											<label for="specialistEmail">Email</label>
										</div>
									</div>
								</div>
							</div>

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
										<form:input path="proName" id="proName" required="required" />
										<label for="proName">Name</label>
									</div>
									<div class="col-md-6 padding_left_0">
										<form:input path="proContactNo" id="proContactNo"
											required="required"
											onchange="return phoneNumberValidation('proContactNo')" />
										<label for="proContactNo">Contact No.</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<form:input path="proEmail" id="proEmail" required="required"
											onchange="return emailValidation('proEmail')" />
										<label for="proEmail">Email</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:textarea path="comments" id="comments" />
										<label for="comments">Comments</label>
									</div>
								</div>
							</div>
							<form:hidden path="claimId" name="claimId" />
							<input type="hidden" name="docmanUuid" id="docmanUuid"
								value="${docmanUuid}" /> <input type="hidden" name="formAction"
								id="formAction" value="save" /> <input type="hidden"
								name="pafMode" id="pafMode" value="${pafMode}" />

							<div class="form-group">

								<button id="btnPreviousProcedureInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabProcedureInfo')">Previous</button>
								<button id="btnSave" type="button" class="btn blue darken-2">Save</button>
								<button id="btnSubmit" type="button" class="btn btn_primary">Save
									& Submit</button>
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
		init();
		function init() {
			var mode = '${pafMode}';
			if (mode != 'insert') {
				var hospital = '${hospitalId}';
				var state = document.getElementById('state').value;
				citySelect(state);
				var city = document.getElementById('city').value;
				selectHospital(city, 1);
				var selectItems = document.getElementById('hospitalId');
				if (selectItems.value == '0') {
					selectItems.form['other'].style.visibility = 'visible';
				} else {
					selectItems.form['other'].style.visibility = 'hidden';
				}
				

			}

			var specialist = $('#speclistServiceRequired').is(":checked");
			if (specialist) {
				$('#specialistDetails').show();
			} else {
				$('#specialistDetails').hide();
			}

		}

		$('#member').on('change',function() {
					var selectedDep = $(this).val();

					$('#memberDob').val($('.member_id_' + selectedDep).attr('_dob'));
					$('#memberRelation').val($('.member_id_' + selectedDep).attr('_relationship'));

					Materialize.updateTextFields();

				});

		/* 	$('#treatment').on(
					'change',
					function() {
						var selectedTreatment = $(this).val();

						$('#estimateAmount').val(
								$('.treatment_id_' + selectedTreatment).attr(
										'_averageAmount'));

						Materialize.updateTextFields();
					});
		 */
		$('#state').on('change', function() {
			var state = $(this).val();
			citySelect(state);

		});
		
		function citySelect(state) {
			document.querySelector('#state').classList.remove('alertAmount');
			var selectedCity = '${preAuthBean.city}';
			state = state.replace(' & ', ' and ');
			var sel = document.getElementById('city');
			sel.innerHTML = "";
			var xhttp = new XMLHttpRequest();
			xhttp.open("GET", "/api/ins/state/city?state=" + state, false);
			xhttp.setRequestHeader("Content-type", "application/json");
			xhttp.send();
			var response = xhttp.responseText;
			sel.innerHTML = "<option disabled selected>Choose City</option>";
			var citiesArray = JSON.parse(response);
			for (var i = 0; i < citiesArray.length; i++) {

				// create new option element
				var opt = document.createElement('option');

				// create text node to add to option element (opt)
				opt.appendChild(document.createTextNode(citiesArray[i]));

				// set value property of opt
				opt.value = citiesArray[i];

				if (citiesArray[i] == selectedCity) {
					opt.selected = true;
				}

				// add opt to end of select box (sel)
				sel.appendChild(opt);
			}
		}

		$('#city').on('change', function() {
			var city = $(this).val();
			selectHospital(city, 0);

		});
		function selectHospital(city, flag) {
			document.querySelector('#city').classList.remove('alertAmount');
			document.querySelector('#hospitalId').classList.remove('alertAmount');
			var selectedHospitalId = '${hospitalId}'

			city = city.replace(' & ', ' and ');
			var sel = document.getElementById('hospitalId');
			sel.innerHTML = "";
			var xhttp = new XMLHttpRequest();
			xhttp.open("GET", "/api/ins/state/city/hospital?city=" + city,
					false);
			xhttp.setRequestHeader("Content-type", "application/json");
			xhttp.send();
			var response = xhttp.responseText;
			sel.innerHTML = "<option disabled selected>Choose Hospital</option>";
			var hospitalArray = JSON.parse(response);
			for (var i = 0; i < hospitalArray.length; i++) {

				// create new option element
				var opt = document.createElement('option');

				// create text node to add to option element (opt)
				opt.appendChild(document
						.createTextNode(hospitalArray[i].hospitalName));

				// set value property of opt
				opt.value = hospitalArray[i].hospitalId;
				if (flag == 1) {
					if (selectedHospitalId == hospitalArray[i].hospitalId) {
						opt.selected = true;
						flag = 0;
					}

				}

				// add opt to end of select box (sel)
				sel.appendChild(opt);
			}
			var opt = document.createElement('option');

			// create text node to add to option element (opt)
			opt.appendChild(document.createTextNode("Others"));

			// set value property of opt
			opt.value = 0;
			if (flag == 1) {
				if (selectedHospitalId == 0) {
					opt.selected = true;
					flag = 0;
				}
			}
			// add opt to end of select box (sel)
			sel.appendChild(opt);
		}

		$('#speclistServiceRequired').on('change', function() {
			if (this.checked) {
				$('#specialistDetails').show();
			} else {
				$('#specialistDetails').hide();
			}
		});
		$('#prescriberName').on('change', function() {
			document.querySelector('#prescriberName').classList.remove('alertAmount');
		});
		 $('#other').on('change', function() {
			 document.querySelector('#other').classList.remove('alertAmount');

			});

		//preAuthForm

		$('#btnSave').click(function() {
							
							var dob = document.querySelector('#memberDob').value;
							var state=document.querySelector('#state').value;
							var city=document.querySelector('#city').value;
							var hospital=document.querySelector('#hospitalId').value;
							var doctor =document.querySelector('#prescriberName').value;
							var docNumber=document.querySelector('#prescriberContactNo').value;
							var flag=0;
							if(hospital == '0'){
								
								var other=document.querySelector('#other').value;
								if(other == ""){
									flag=1;
									document.querySelector('#other').classList.add('alertAmount');
								}
							}
							if(doctor == ""){
								flag=1;
								document.querySelector('#prescriberName').classList.add('alertAmount');
							}
							if(docNumber == ""){
								flag=1;
								document.querySelector('#prescriberContactNo').classList.add('alertAmount');
							}
 						var field = ((document.querySelector('.alertAmount') || {}).value)|| "";
							if (dob == "") {
								showTab('tabMemberInfo');
							} else {
								if (field != "" || flag == 1) {
									/* window.alert("Missed some fields!"); */
									showTab('tabProviderInfo');
								}else if(city == 'Choose City' || state == 'Choose State' || hospital == 'Choose Hospital') {
									document.querySelector('#state').classList.add('alertAmount');
									document.querySelector('#city').classList.add('alertAmount');
									document.querySelector('#hospitalId').classList.add('alertAmount');
								}else {
									$('#formAction').val('save');
									$('#preAuthForm').submit(); 
								}
							}

						});

		$('#btnSubmit').click(function() {
							
							var dob = document.querySelector('#memberDob').value;
							var state=document.querySelector('#state').value;
							var city=document.querySelector('#city').value;
							var hospital=document.querySelector('#hospitalId').value;
							var doctor =document.querySelector('#prescriberName').value;
							var docNumber=document.querySelector('#prescriberContactNo').value;
							var flag=0;
							if(hospital == '0'){
								
								var other=document.querySelector('#other').value;
								if(other == ""){
									flag=1;
									document.querySelector('#other').classList.add('alertAmount');
								}
							}
							if(doctor == ""){
								flag=1;
								document.querySelector('#prescriberName').classList.add('alertAmount');
							}
							if(docNumber == ""){
								flag=1;
								document.querySelector('#prescriberContactNo').classList.add('alertAmount');
							}
							var field = ((document.querySelector('.alertAmount') || {}).value)|| "";
							if (dob == "") {
								showTab('tabMemberInfo');
							}else if(city == 'Choose City' || state == 'Choose State' || hospital == 'Choose Hospital') {
								document.querySelector('#state').classList.add('alertAmount');
								document.querySelector('#city').classList.add('alertAmount');
								document.querySelector('#hospitalId').classList.add('alertAmount');
							} else {
								if (field != "" || flag ==1) {
									/* window.alert("Missed some fields!"); */
									showTab('tabProviderInfo');
								} else {

									$('#formAction').val('submit');
									$('#preAuthForm').submit();
								}

							}
						});

		function showTab(activeTabId) {
			$('ul.tabs').tabs('select_tab', activeTabId);
		}

		function phoneNumberValidation(feiledId) {
			document.querySelector('#'+feiledId).classList.remove('alertAmount');
			var valueN = ((document.getElementById(feiledId) || {}).value)|| "";
			/* var phoneno = /^[6-9]\d{9}$/; */
			var phoneno = /^(?:\s+|)((0|(?:(\+|)91))(?:\s|-)*(?:(?:\d(?:\s|-)*\d{9})|(?:\d{2}(?:\s|-)*\d{8})|(?:\d{3}(?:\s|-)*\d{7}))|\d{10})(?:\s+|)$/;
			if (!valueN.match(phoneno)) {
				document.querySelector('#' + feiledId).classList.add('alertAmount');
				document.querySelector('#' + feiledId).innerHTML = "Enter valid Phone Number!";
			} else {
				document.querySelector('#' + feiledId).classList.remove('alertAmount');
			}

		}
		function emailValidation(feiledId) {

			var valueN = ((document.getElementById(feiledId) || {}).value)|| "";
			var phoneno = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if (!valueN.match(phoneno)) {
				document.querySelector('#' + feiledId).classList.add('alertAmount');
				document.querySelector('#' + feiledId).innerHTML = "Enter valid Email!";
			} else {
				document.querySelector('#' + feiledId).classList.remove('alertAmount');
			}

		}
	</script>

</body>
</html>
