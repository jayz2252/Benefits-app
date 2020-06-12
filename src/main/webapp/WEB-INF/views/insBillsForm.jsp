
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
.alertAmount{
border-bottom-color: red !important;
background-color: #ffcccc !important;
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
											<option disabled selected>Choose Member</option>
											<c:forEach items="${planEmployee.details}" var="member">
												<option value="${member.dependent.dependentId}"
													class="member member_id_${member.dependent.
													dependentId}"
													${preAuthBean.memberId eq member.dependent.dependentId ? 'selected="selected"' : ''}
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
											value="${preAuthBean.memberDob}" />
										<label for="memberDob">Date of Birth</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<form:input path="memberRelationship" id="memberRelation"
											disabled="true" value="${preAuthBean.memberRelationship}" />
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
													${preAuthBean.treatmentId eq trt.treatmentId ? 'selected="selected"' : ''}
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
											
										</form:select>
										<label for="city">City</label>
									</div>
								</div>
							</div>
							<div class="form-group is-empty">
								<div class="md-form auth_dropdown">
									<div class="col-md-6 padding_left_0">
									
										<form:select path="hospitalId" name="hospitalId" onchange="if (this.value=='0'){this.form['other'].style.visibility='visible'}else {this.form['other'].style.visibility='hidden'};">
											<option disabled selected>Choose Hospital</option>
										</form:select>
										
										<label for="city">Hospital</label>
										
									</div>
									
									
								</div>
								<form:input type="text" path="otherHospital" placeholder="Enter Hospital here.." name="other" id="other" style="visibility:hidden;"/>
								
							</div>
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<label style="color: rgba(238, 110, 115, 0.7);margin-top: -13px;">Prescriber
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
											id="prescriberContactNo"  onchange="return phoneNumberValidation('prescriberContactNo')"/>
										<label for="prescriberContactNo">Contact No.</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<form:input path="prescriberEmail" id="prescriberEmail" onchange="return emailValidation('prescriberEmail')"/>
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
											<label for="speclistServiceRequired" style="color: rgba(238, 110, 115, 0.7);margin-top: -13px;">Do you need any
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
												id="specialistContactNo" onchange="return phoneNumberValidation('specialistContactNo')"/>
											<label for="specialistContactNo">Contact No.</label>
										</div>
									</div>
								</div>

								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<form:input path="specialistEmail" id="specialistEmail" onchange="return emailValidation('specialistEmail')"/>
											<label for="specialistEmail">Email</label>
										</div>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<label style="color: rgba(238, 110, 115, 0.7);;margin-top: -13px;">PRO/Administrator
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
										<form:input path="proContactNo" id="proContactNo" onchange="return phoneNumberValidation('proContactNo')"/>
										<label for="proContactNo">Contact No.</label>
									</div>
								</div>
							</div>

							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<form:input path="proEmail" id="proEmail" onchange="return emailValidation('proEmail')"/>
										<label for="proEmail">Email</label>
									</div>
								</div>
							</div>

							<div class="form-group">
								<button id="btnPreviousProcedureInfo" type="button"
									class="btn btn yellow darken-3"
									onclick="showTab('tabProcedureInfo')">
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

										<table id="billDetails" class="responstable dynamicTable">
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



															<td><input type="text" name="billNo${it.index}"
																id="billNo${it.index}" value="${bean.claimBill.billNo}" /></td>
															<td><input type="date" name="billDate${it.index}"
																id="billDate${it.index}"
																value="<fmt:formatDate pattern="yyyy-MM-dd" value = "${bean.claimBill.billDate}"/>" />
															</td>
															<td><input type="text" name="billIssuer${it.index}"
																id="billIssuer${it.index}"
																value="${bean.claimBill.billIssuer}" /></td>
															<td><input type="text" name="totalAmt${it.index}"
																id="totalAmt${it.index}"
																value="${bean.claimBill.amountRequested}" /></td>


															<td><%-- <input type="submit" value="Add Categories"
																onclick="setUrl(this,${planEmployee.insPlanEmployeeId})"> --%>
																<button type="submit"
															style="background-color: whitesmoke; padding: 6px 11px; border-radius: 35px;border-color: tan;"
															onclick="setUrl(this,${planEmployee.insPlanEmployeeId})"
															title="Add Categories">
															<i class="fa fa-file-text" style="color: #006699; font-size: 15px;"></i>
														</button>
																</td>
														</tr>



													</c:forEach>
													<c:set var="countLoop" value="${countLoop1}"></c:set>
												</c:if>
												<%-- <c:if test="${beans eq null}"> --%>
												<tr>
													<td></td>
													<td><input type="text" name="billNo${countLoop}"
														id="billNo${countLoop}" /></td>
													<td><input type="date" name="billDate${countLoop}"
														id="billDate${countLoop}" /></td>
													<td><input type="text" name="billIssuer${countLoop}"
														id="billIssuer${countLoop}" /></td>
													<td><input type="text" name="totalAmt${countLoop}"
														id="totalAmt${countLoop}" value="" /></td>

													<td><%-- <input type="submit"
														onclick="setUrl(this,${planEmployee.insPlanEmployeeId})"
														value="Add Categories"> --%>
														<button type="submit"
															style="background-color: whitesmoke; padding: 6px 11px; border-radius: 35px;border-color: tan;"
															onclick="setUrl(this,${planEmployee.insPlanEmployeeId})"
															title="Add Categories">
															<i class="fa fa-file-text" style="color: #006699; font-size: 15px;"></i>
														</button>
														</td>
												</tr>
												<%-- </c:if> --%>
											</tbody>
										</table>


										<input type="hidden" id="rowCount" name="rowCount"
											value="${countLoop}" />
										<button type="button" id="btnAddRow" class="btn blue darken-2">Add
											Row</button>
										<button type="button" id="billRemove"
											class="btn red darken-1" onclick="deleterow('billDetails')">Remove
											Last Row</button>
									</div>
								</div>
							</div>

<div class="form-group is-empty" style="margin-top: 20px;">
								<div class="md-form">
									<div class="col-md-12 padding_left_0">
										<input type="text" id="pafamount"
											value="${pafRequestedAmount}" readonly="readonly"
											style="color: black; border-bottom-color: #9e9e9e; border-bottom-style: double;" />
										<label style="color: #21b6ca;">Total Requested Amount</label>
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
										<form:textarea path="comments" id="comments"/>
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
									onclick="showTab('tabProviderInfo')">Previous</button>
								<button type="button" id="bsub"
									class="btn btn darken-3"
									title="Submit">Submit</button>
								<!-- <a class="btn btn darken-3" id="bsub">Submit</a> -->
								</div>
								</div>
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
	init();
	function init(){
		var mode='${pafMode}';
		if(mode!='insert'){
		var hospital='${hospitalId}';
		var state = document.getElementById('state').value;
		citySelect(state);
		var city = document.getElementById('city').value;
		selectHospital(city,1);
		var selectItems=document.getElementById('hospitalId');
		if (selectItems.value=='0'){selectItems.form['other'].style.visibility='visible'}else {selectItems.form['other'].style.visibility='hidden'};
		var selectedDep = $('#member').val();

		$('#memberDob').val(
				$('.member_id_' + selectedDep).attr('_dob'));
		$('#memberRelation').val(
				$('.member_id_' + selectedDep)
						.attr('_relationship'));
		}
		var specialist=$('#speclistServiceRequired').is(":checked");
		if(specialist){
			$('#specialistDetails').show();
		}else{
			$('#specialistDetails').hide();
		}
	}
	$("#bsub").click(function() {
		/* var field=((document.querySelector('.alertAmount')||{}).value)||"";
		var dob=document.querySelector('#memberDob').value;
		if(dob==""){
			showTab('tabMemberInfo');
		}else{
		if(field!=""){
			/* window.alert("Missed some fields!"); */
			/*showTab('tabProviderInfo');
		}else{
			 var field=((document.querySelector('.alertAmount')||{}).value)||"";
			if(field!=""){
				
				showTab('tabProviderInfo');
			}else{
				document.getElementById("bsub").type='submit';
	    if (confirm("Do you want to Submit the Bill?")) {
	    	var comments=$('#comments').val();
	    		$("comments").text(comments);
	    	/* $(this).attr('href', '/home/controlPanel/insurancePlans/optedEmployees/${planEmployeeId1}/bills/new/save'); */
	    	/* $("#preAuthForm").attr("action","/home/controlPanel/insurancePlans/optedEmployees/"+${planEmployeeId1}+"/bills/new/save");
	    	
	    	 window.alert("Bill submitted Successfully!");
  	 
	    } else {
	    	 $(this).dialog('close');
	    }
			}
		}
	   
	} */
	var dob=document.querySelector('#memberDob').value;
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
	var totalAmount=document.querySelector('#amountRequired').value;
	var field=((document.querySelector('.alertAmount')||{}).value)||"";
	var bills=${beansize};
	
	if(dob==""){
		showTab('tabMemberInfo');
	}else{
	if(field!="" || flag == 1){
		/* window.alert("Missed some fields!"); */
		showTab('tabProviderInfo');
	}else if(totalAmount == ""){
		document.querySelector('#amountRequired').classList.add('alertAmount');
		
	}else if(bills<=0){
		window.alert("Please add bill details!");
	} else{
		document.getElementById("bsub").type='submit';
		 if (confirm("Do you want to Submit the Bill?")) {
		    	  var comments=$('#comments').val();
		    		$("comments").text(comments);  
		    	/* $(this).attr('href', '/home/myInsurancePlan/${planEmployeeId1}/newClaim/save'); */
		    	
     			$("#preAuthForm").attr("action","/home/controlPanel/insurancePlans/optedEmployees/"+${planEmployeeId1}+"/bills/new/save");
		    	 window.alert("Bill submitted Successfully!");	
		    	 
		    } else {
		    	 $(this).dialog('close');
		    }
		
	}
	
	   
	}
	});
	$('#prescriberName').on('change', function() {
		document.querySelector('#prescriberName').classList.remove('alertAmount');
	});
	
	$("select").material_select();
		/* $('#specialistDetails').hide(); */
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
/* 
		$('#treatment').on(
				'change',
				function() {
					var selectedTreatment = $(this).val();

					$('#estimateAmount').val(
							$('.treatment_id_' + selectedTreatment).attr(
									'_averageAmount'));

					Materialize.updateTextFields();
				}); */

		$('#state').on('change', function() {
			var state = $(this).val();
			citySelect(state);

		});
				function citySelect(state){
					 var selectedCity= '${preAuthBean.city}';
					 state=state.replace(' & ',' and ');
					 var sel = document.getElementById('city');
						sel.innerHTML = "";
						var xhttp = new XMLHttpRequest();
					    xhttp.open("GET", "/api/ins/state/city?state="+state, false);
					    xhttp.setRequestHeader("Content-type", "application/json");
					    xhttp.send();
					    var response = xhttp.responseText;
					    sel.innerHTML ="<option disabled selected>Choose City</option>";
					    var citiesArray = JSON.parse(response);
					    for (var i = 0; i < citiesArray.length; i++) {
					    	
					    	// create new option element
					    	var opt = document.createElement('option');

					    	// create text node to add to option element (opt)
					    	opt.appendChild( document.createTextNode(citiesArray[i]) );

					    	// set value property of opt
					    	opt.value = citiesArray[i]; 
					    	
					    	if(citiesArray[i]==selectedCity){
					    		opt.selected=true;
					    	}

					    	// add opt to end of select box (sel)
					    	sel.appendChild(opt);
					    }
				 }
		$('#city').on('change', function() {
			var city = $(this).val();
			selectHospital(city,0);

		});
		function selectHospital(city,flag){
			var selectedHospitalId='${hospitalId}' 
				city=city.replace(' & ',' and ');
			var sel = document.getElementById('hospitalId');
			sel.innerHTML = "";
			var xhttp = new XMLHttpRequest();
		    xhttp.open("GET", "/api/ins/state/city/hospital?city="+city, false);
		    xhttp.setRequestHeader("Content-type", "application/json");
		    xhttp.send();
		    var response = xhttp.responseText;
		    sel.innerHTML ="<option disabled selected>Choose Hospital</option>";
		    var hospitalArray = JSON.parse(response);
		    for (var i = 0; i < hospitalArray.length; i++) {
		    	
		    	// create new option element
		    	var opt = document.createElement('option');

		    	// create text node to add to option element (opt)
		    	opt.appendChild( document.createTextNode(hospitalArray[i].hospitalName) );

		    	// set value property of opt
		    	opt.value = hospitalArray[i].hospitalId; 
		    	if(flag==1){
		    		if(selectedHospitalId==hospitalArray[i].hospitalId){
		    			opt.selected=true;
		    			flag=0;
		    		}
		    		
		    	}

		    	// add opt to end of select box (sel)
		    	sel.appendChild(opt);
		    }
		    var opt = document.createElement('option');

	    	// create text node to add to option element (opt)
	    	opt.appendChild( document.createTextNode("Others"));

	    	// set value property of opt
	    	opt.value = 0; 
	    	if(flag==1){
	    		if(selectedHospitalId==0){
	    			opt.selected=true;
	    			flag=0;
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

		//preAuthForm 

		$('#btnSave').click(function() {
			$('#formAction').val('save');
			$('#preAuthForm').submit();
		});
		
		//$('#btnSubmit').click(function() {
		//$(this).attr('href', '/home/controlPanel/insurancePlans/optedEmployees/${planEmployeeId1}/bills/new/save');
		//	alert('Bill submitted successfully!');
		//});

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
		 $("#preAuthForm").attr("action","/home/controlPanel/insurancePlans/optedEmployees/bills/new/billDetail/" + rowIndex);
			
 }

		$("#btnAddRow").click(function() {

			/* var rowCount = parseFloat($("#rowCount").val());
			rowCount++; */
			var $clone = $(".dynamicTable tbody tr:first").clone(); 
			 var table = document.getElementById('billDetails');
			    /* ${countLoop}=(rowCount -1); */
			    var rowCount = table.rows.length;
			    rowCount--;

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
		function deleterow(tableID) {
		    var table = document.getElementById(tableID);
		    /* ${countLoop}=(rowCount -1); */
		    var rowCount = table.rows.length;
		   
			if(rowCount>2){
				 table.deleteRow(rowCount -1);
				 document.getElementById('rowCount').value=(rowCount -2); 
				$.ajax({  
                    type : 'POST',  
                    url : "/home/controlPanel/insurancePlans/optedEmployees/bills/new/billDetail/remove/" + (rowCount-1), 
                    dataType: 'json',
                });
				
				sumAmount();
				
				/* parseFloat($("#rowCount").val())=rowCount -1; */
				}
		}
		function sumAmount(){
			var table = document.getElementById('billDetails');
		    /* ${countLoop}=(rowCount -1); */
		    var rowCount = table.rows.length;
		    var sum=Number(0);
		    /* Number(sum); */
		    for (var i=0;i<rowCount;i++){
		    	/* ((document.getElementById('totalAmt'+i)||{}).value)||""; */
		    var total=((document.getElementById('totalAmt'+i)||{}).value)||"";
		    if(total!=null&total!=""){
		    	total=Number(total);
		    	sum=(sum+total);
		    }
		    
		    }
		    document.getElementById('amountRequired').value=sum;
		}
		
		
		function phoneNumberValidation(feiledId){
			
			var valueN=((document.getElementById(feiledId)||{}).value)||"";
			/* var phoneno = /^[6-9]\d{9}$/; */
			 var phoneno =/^(?:\s+|)((0|(?:(\+|)91))(?:\s|-)*(?:(?:\d(?:\s|-)*\d{9})|(?:\d{2}(?:\s|-)*\d{8})|(?:\d{3}(?:\s|-)*\d{7}))|\d{10})(?:\s+|)$/;
			if(!valueN.match(phoneno)){
				document.querySelector('#'+feiledId).classList.add('alertAmount');
				document.querySelector('#'+feiledId).innerHTML = "Enter valid Phone Number!";
			}else{
				document.querySelector('#'+feiledId).classList.remove('alertAmount');
			}
				
			
		}
function emailValidation(feiledId){
			
			var valueN=((document.getElementById(feiledId)||{}).value)||"";
			var phoneno = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if(!valueN.match(phoneno)){
				document.querySelector('#'+feiledId).classList.add('alertAmount');
				document.querySelector('#'+feiledId).innerHTML = "Enter valid Email!";
			}else{
				document.querySelector('#'+feiledId).classList.remove('alertAmount');
			}
				
			
		}
	</script>
	<script>
</script>

</body>
</html>
