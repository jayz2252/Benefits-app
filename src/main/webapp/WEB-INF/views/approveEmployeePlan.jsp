<!-- 
@author : swathy.raghu
@page displays details of plan-employee that needs to be approved/rejected
 -->
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$(".approveClass").click(function() {

			//if all checked
			if ($('#checkboxdiv :checkbox:not(:checked)').length == 0) {
				$("#action").val("approve");
				alert("Successfully Approved");
			} else {
				var output = "";
				$('#checkboxdiv :checkbox').each(function() {
					if ($(this).prop("checked") == true) {
						output += $(this).val() + ",";
					}
				});
				$("#action").val("save");
				$("#docIds").val(output);
				alert("Not Approved Since All documents are Not Verified");
			}

		});

	});
</script>

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
							<h4 class="h4-responsive">Plan Choose Approval</h4>
						</div>

					</div>

					<!-- <c:if test="${savedStatus ne null}">
						<div class="row">
							<div class="col-md-12">
								<c:choose>
									<c:when test="${savedStatus}">
										<div class="alert alert-success">
											<strong>Success!</strong> Successfully approved
										</div>
									</c:when>
									<c:otherwise>
										<div class="alert alert-danger">
											<strong>Error: </strong> Failed to approve,
											please contact the System Administrator and re-submit required documents
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:if> -->

					<form
						action="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees/approve/${employeePlan.planEmployeeId}"
						method="POST">

						<div class="row">
							<div class="col-md-12">
								<table class="table striped table-borderd">
									<tr>
										<th>Plan Name</th>
										<td colspan="3">${employeePlan.benefitPlan.planName}</td>
									</tr>
									<tr>
										<th>Description</th>
										<td colspan="3">${employeePlan.benefitPlan.planDesc}</td>
									</tr>
									<tr>
										<th>Effective From</th>
										<td><fmt:formatDate pattern="dd-MM-yyyy"
												value="${employeePlan.benefitPlan.effFrom}" /></td>
										<th>Effective To</th>
										<td><fmt:formatDate pattern="dd-MM-yyyy"
												value="${employeePlan.benefitPlan.effTill}" /></td>
									</tr>

								</table>
								<br />
								<table class="table striped table-borderd">
									<c:choose>
										<c:when test="${employeePlan.benefitPlan.claimType eq 'CTGY'}">
											<tr>
												<th colspan="5">Category</th>
											</tr>
											<tr>
												<td>Category Name</td>
												<td>${employeePlan.planCategory.categoryName}</td>
											</tr>
											<tr>
												<td>Category Amount</td>
												<td>Rs.${employeePlan.planCategory.categoryAmount}</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when
													test="${employeePlan.benefitPlan.claimType eq 'BAND'}">
													<tr>
														<th colspan="5">Band</th>
													</tr>
													<tr>
														<td>Band Name</td>
														<td>${employeePlan.optingBand}</td>
													</tr>
													<tr>
														<td>Amount</td>
														<td>${employeePlan.yearlyDeduction}</td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when
															test="${employeePlan.benefitPlan.claimType eq 'DPNT'}">

															<tr>
																<th colspan="5">Dependents</th>
															</tr>

															<tr>
																<th>Name</th>
																<th>Relationship With Employee</th>
															</tr>
															<c:forEach items="${employeePlan.dependentDetails}"
																var="dependents">
																<tr>

																	<td>${dependents.dependent.dependentName}</td>
																	<td>${dependents.dependent.relationship}</td>
																</tr>
															</c:forEach>

														</c:when>


													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:otherwise>

									</c:choose>

									<tr>
										<th colspan="5">Final Amount</th>
									</tr>
									<tr>
										<th>Total Yearly Deduction</th>
										<td>
											<p class="points">
												Rs.${employeePlan.yearlyDeduction}<br />,
												Rs.${employeePlan.yearlyDeduction / 12} per month
											</p>
										</td>

										<td></td>
									</tr>



								</table>
								<br />
								<table class="table striped table-borderd">
									<tr>
										<th colspan="4">Employee Details</th>
									</tr>
									<tr>
										<th>Name</th>
										<td>${employeePlan.employee.firstName}
											${employeePlan.employee.lastName}</td>
									</tr>
									<tr>
										<th>Designation</th>
										<td>${employeePlan.employee.designationName}</td>

									</tr>
								</table>
								<c:if test="${employeePlan.benefitPlan.promptFieldsOnOPT}">
								<table class="table striped table-borderd">


									
										<tr>
											<th colspan="2">Custom Field(s)</th>
											<th>Verified</th>
										</tr>
										<c:forEach items="${benefitPlanEmployeeField}" var="field">
											<tr>
												<td>${field.field.uiLabel}</td>
												<td>${field.value}</td>
												<td><input id="verifyField${field.planEmployeeFieldId}"
													type="checkbox" class="verify_field"> <label
													for="verifyField${field.planEmployeeFieldId}"
													class="pull-left" /></td>
											</tr>
										</c:forEach>
									


								</table>
								</c:if>

								<br /> <input type="hidden" name="docIds" id="docIds" /> <input
									type="hidden" name="action" id="action" /> <br />
								<div id="checkboxdiv">
									<div class="alert alert-warning">
										<strong>Warning!</strong> All documents need to be Verified in
										order to get the Plan Approved
									</div>

								</div>
								<c:if test="${employeePlan.benefitPlan.optDocumentsRequired}">
								<table class="table striped table-borderd">
								<tr>
								<th colspan="2">Document(s)</th>
								</tr>
								<c:forEach items="${docList}" var="doc">
									<tr>
										<td><a href="${doc.uploadUrl}" target="_blank">${doc.documentType}</a></td>
										<td><a href="${doc.uploadUrl}" target="_blank"
											class="doc_upload_link" id="upload_${doc.documentId}"
											uuid="${doc.docManUuid}"
											mandatory="${doc.mandatory}" clicked="false"><i
												class="fa fa-paperclip" aria-hidden="true"></i></a></td>
									</tr>
								</c:forEach>
									
								</table>
								</c:if>
								<input type="text" id="declineReason" name="declineReason"
									placeholder="Do you want to Reject? Enter reason here.." />
							</div>
						</div>


						<%-- <c:if test="${!savedStatus or savedStatus eq null}"> --%>
						<c:if
							test="${employeePlan.status ne 'HR_RJCT' or employeePlan.status ne 'HR_APPR'}">
							<div class="form-group">

								<div class="col-md-6">
									<a class="btn yellow darken-3"
										href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees">Back</a>
									<c:choose>
										<c:when test="${employeePlan.benefitPlan.promptFieldsOnOPT}">
											<input type="submit" class="btn btn-primary approveClass"
												id="approve" name="approve" value="Approve"
												disabled="disabled" />
										</c:when>
										<c:otherwise>
											<input type="submit" class="btn btn-primary approveClass"
												id="approve" name="approve" value="Approve" />
										</c:otherwise>
									</c:choose>


								</div>

								<div class="col-md-6">
									<a class="btn red darken-1" id="deny" onclick="mydfunction()"
										disabled="disabled" href="">Deny</a>


								</div>

							</div>
						</c:if>



					</form>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>

	<script>
		$('#declineReason')
				.on(
						'change',
						function() {
							var reason = $(this).val();
							$('#deny')
									.attr(
											'href',
											'/home/controlPanel/flexiPlans/optedEmployees/reject/${employeePlan.planEmployeeId}?declineReason='
													+ reason);
						});

		function mydfunction() {
			alert("Plan Rejected!");
		};

		$(document).ready(function() {
			$('#declineReason').on('input change', function() {
				if ($(this).val() != '') {
					$('#deny').attr('disabled', false);
				} else {
					$('#deny').attr('disabled', true);
				}
			});
		});

		$('.verify_field').on('change', function() {
			var allVerified = checkAllVerified();

			//if (allVerified) {
			//	$('#approve').removeAttr('disabled');
			//} else {
			//	$('#approve').attr('disabled', 'disabled');
			//}
		});

		function checkAllVerified() {
			var allVerified = true;

			$('.verify_field').each(function() {
				if (!$(this).is(":checked")) {
					allVerified = false;
					return false;

				}
			});

			if (allVerified) {
				$('#approve').removeAttr('disabled');
			} else {
				$('#approve').attr('disabled', 'disabled');
			}
			;
		};
	</script>
	<!-- <script type="text/javascript">
		window.onbeforeunload = function() {
			return "You work will be lost.";
		};
	</script> -->
</body>
</html>