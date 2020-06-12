
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
					<form:form id="preAuthForm" method="post" action=""
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
										<form:select path="memberId" id="member">
											
											<c:forEach items="${planEmployee.details}" var="member">
												<option value="${member.dependent.dependentId}"
													class="member member_id_${member.dependent.
													dependentId}" ${preAuthBean.memberId eq member.dependent.dependentId ? 'selected="selected"' : ''}
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
										<form:input path="memberDob" id="memberDob" disabled="true" value="${preAuthBean.memberDob}"/>
										<label for="memberDob">Date of Birth</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="memberRelationship" id="memberRelation"
											disabled="true" value="${preAuthBean.memberRelationship}"/>
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
												<option value="${trt.treatmentId}" ${preAuthBean.treatmentId eq trt.treatmentId ? 'selected="selected"' : ''}
													class="treatment_id_${trt.treatmentId}" 
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
							<div class="form-group is-empty">
								<div class="md-form auth_dropdown">
									<div class="col-md-6 padding_left_0">
										<form:select path="state" id="state">
											
											<c:forEach items="${states}" var="st">
												<option value="${st}" class="state_${st}" ${preAuthBean.state eq st ? 'selected="selected"' : ''}>${st}</option>
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
											
											<c:forEach items="${cities}" var="ct">
												<option value="${ct.city}"
													class="city city_${ct.state} " ${preAuthBean.city eq ct ? 'selected="selected"' : ''}>${ct.city}</option>
											</c:forEach>
										</form:select>
										<label for="city">City</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form auth_dropdown">
									<div class="col-md-6 padding_left_0">
										<form:select path="hospitalId" name="hospitalId">
											
											<c:forEach items="${hospitals}" var="hosp">
												<option class="hospital hospital_${hosp.city} " ${preAuthBean.hospitalId eq hosp.hospitalId ? 'selected="selected"' : ''}
													value="${hosp.hospitalId}">${hosp.hospitalName}</option>
											</c:forEach>
										</form:select>
										<label for="city">Hospital</label>
									</div>
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
									<div class="col-md-6 padding_left_0">
										<form:input path="prescriberName" id="prescriberName"
											/>
										<label for="prescriberName">Name</label>
									</div>
									<div class="col-md-6 padding_left_0">
										<form:input path="prescriberContactNo"
											id="prescriberContactNo"  />
										<label for="prescriberContactNo">Contact No.</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<form:input path="prescriberEmail" id="prescriberEmail"
											/>
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
									<div class="col-md-6 padding_left_0">
										<form:input path="proName" id="proName"  />
										<label for="proName">Name</label>
									</div>
									<div class="col-md-6 padding_left_0">
										<form:input path="proContactNo" id="proContactNo"
											/>
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


							<c:set var="countLoop" value="0" scope="page" />
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">

										<table class="responstable dynamicTable">
											<thead>
												<tr>
													<td></td>
													<td>Bill No</td>
													<td>Bill Date</td>
													<td>Bill Issuer</td>
													<td>Total Amount</td>
												
													<td>Action</td>
												</tr>
											</thead>
											<tbody>
											<c:if test="${beans ne null}">
											 <c:forEach items="${beans}" var="bean" varStatus="it">
 
												 	<c:set var="countLoop1" value="${it.index + 1}"
												 		scope="page" /> 
													<tr>
														<td></td>
													
													
													
													  <td><input type="text"
															name="billNo${it.index}"
															id="billNo${it.index}" value="${bean.claimBill.billNo}" /></td> 
							 							  <td><input type="text"
															name="billDate${it.index}"
															id="billDate${it.index}"
															value="<fmt:formatDate type = "date" 
         value = "${bean.claimBill.billDate}" />" /> </td>
														<td><input type="text"
															name="billIssuer${it.index}"
															id="billIssuer${it.index}"
															value="${bean.claimBill.billIssuer}" /></td>
														<td><input type="text"
															name="totalAmt${it.index}"
															id="totalAmt${it.index}"
															value="${bean.claimBill.amountRequested}" /></td> 
														  

														<td><input type="submit" value="Add Bills" onclick="setUrl(this,${planEmployee.insPlanEmployeeId})"></td>
													</tr>



												</c:forEach> 
											 <c:set var="countLoop" value="${countLoop1}"></c:set> 
												</c:if>
												<tr>
													<td></td>
													<td><input type="text" name="billNo${countLoop}"
														id="billNo${countLoop}" /></td>
													<td><input type="text" name="billDate${countLoop}"
														id="billDate${countLoop}" /></td>
													<td><input type="text" name="billIssuer${countLoop}"
														id="billIssuer${countLoop}" /></td>
													<td><input type="text" name="totalAmt${countLoop}"
														id="totalAmt${countLoop}" value="" /></td>
													
													<td><input type="submit" onclick="setUrl(this,${planEmployee.insPlanEmployeeId})"
														value="Add Bills"></td>
												</tr>

											</tbody>
										</table>


										<input type="hidden" id="rowCount" name="rowCount"
											value="${countLoop}" />
										<button type="button" id="btnAddRow" class="btn blue darken-2">Add
											Row</button>
										<button type="button" id="btnRemoveRow"
											class="btn red darken-1" onclick="removeLastRow()">Remove
											Last Row</button>
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
										<form:textarea path="comments" id="comments" />
										<label for="comments">Comments</label>
									</div>
								</div>
							</div> 
							 <form:hidden path="claimId" name="claimId" /> 
							<input type="hidden" name="formAction" id="formAction"
								value="save" /> <input type="hidden" name="pafMode"
								id="pafMode" value="${pafMode}" />

							<div class="form-group">

								<button id="btnPreviousProcedureInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabProcedureInfo')">Previous</button>

								<a
									href="/home/controlPanel/insurancePlans/optedEmployees/${planEmployeeId1}/PAFbills/new/save">Submit</a>

							</div>
						</div>
					<%-- 	<input type="hidden" name="planEmployeeId" id="planEmployeeId"
							value="${planEmployee.insPlanEmployeeId} }"> --%>

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

		function setUrl(e,planEmployeeId) {

			 row = $(e).closest('tr'); 
			
			rowIndex = row.index(); 
		 $("#preAuthForm")
					.attr(
							"action",
							"/home/controlPanel/insurancePlans/optedEmployees/PAFbills/new/billDetail/" + rowIndex);

		 
			/* $("#preAuthForm")
			.attr(
					"action",
					"/home/controlPanel/insurancePlans/optedEmployees/bills/new/billDetail/0"); */

		}

		$("#btnAddRow").click(function() {

			var rowCount = parseFloat($("#rowCount").val());
			rowCount++;
			var $clone = $(".dynamicTable tbody tr:first").clone();

			$clone.attr({
				id : "bill" + rowCount,
				name : "bill" + rowCount,
				style : "" // remove "display:none"
			});
			
			$clone.find("input").each(function() {
				
			var id = this.id.substr(0,this.id.length-1) + rowCount;
				
				var name = this.name.substr(0,this.name.length-1) + rowCount;
				$(this).attr({

					id : id,
					name : name

				});
			});
			$clone.find("select").each(function() {
var id = this.id.substr(0,this.id.length-1) + rowCount;
				
				var name = this.name.substr(0,this.name.length-1) + rowCount;
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
