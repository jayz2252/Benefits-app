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
				alert("Successfully rolled back");
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
						action="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/rollBackApprovedPlans/new/${planEmployeeId}"
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
												<td>${employeePlan.planCategory.categoryAmount}</td>
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
														<td>employeePlan.optingBand.band</td>
													</tr>
													<tr>
														<td>Band Amount</td>
														<td>employeePlan.optingBand.amount</td>
													</tr>
												</c:when>
											</c:choose>
										</c:otherwise>
									</c:choose>

									<tr>
										<th colspan="5">Final Amount</th>
									</tr>
									<tr>
										<th>Total Yearly Deduction</th>
										<td>Rs. ${employeePlan.yearlyDeduction}<br /> Rs.
											${employeePlan.yearlyDeduction / 12} per month
										</td>
										<th>Total Yearly Claim</th>
										<td>Rs. ${employeePlan.yearlyClaim}<br />

										</td>
										<td></td>
									</tr>



								</table>
								<br />
								<table class="table striped table-borderd">
									<tr>
										<th colspan="5">Employee</th>
									</tr>
									<tr>
										<th>Name</th>
										<th>Designation</th>


									</tr>

									<tr>
										<td>${employeePlan.employee.firstName}.${employeePlan.employee.lastName}</td>
										<td>${employeePlan.employee.designationName}</td>
									</tr>

								</table>
								<br /> <input type="hidden" name="docIds" id="docIds" /> <input
									type="hidden" name="action" id="action" /> <br />
								<div id="checkboxdiv">
									<div class="alert alert-warning">
										<strong>Warning!</strong> All documents need to be Verified in
										order to get the Plan Approved
									</div>
									<%-- <table class="table striped table-borderd">
										<tr>
											<th colspan="3">Documents Submitted</th>
										</tr>
										<tr>
											<th>Document</th>
											<th>Access</th>
											<th>Verify</th>
										</tr>

										<c:forEach items="${docList}" var="document">

											<tr>
												<td><a href="${document.downloadUrl}" target="_blank">${document.documentType}</a></td>

												<td><a href="${document.uploadUrl}" target="_blank"><i
														class="fa fa-paperclip" aria-hidden="true"></i></a> <a
													href="${document.downloadUrl}" target="_blank"><i
														class="fa fa-cloud-download" aria-hidden="true"></i></a></td>
												<td><input type="checkbox" name="checkbox"
													class="select_box" id="doc${document.documentId}"
													value="${document.documentId}" /> <label
													for="doc${document.documentId}"></label></td>
											</tr>

										</c:forEach>

									</table> --%>
								</div>
							</div>
						</div>

						<%-- <c:if test="${!savedStatus or savedStatus eq null}"> --%>
						<div class="form-group">

							<div class="col-md-6">
								<a class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel">Cancel</a>
						<%-- <c:if test="${claimSize eq 0} ">
						<a class="btn btn-primary" href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/rollBackApprovedPlans/new/${planEmployeeId}">Rollback Approval</a>
						</c:if> --%>
						<%-- <a class="btn btn-primary" href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/rollBackApprovedPlans/new/${planEmployeeId}">Rollback Approval</a> --%>
								<input type="submit" class="btn btn-primary approveClass"
									id="approve" name="approve" value="RollBack Approval" />
							</div>
						</div>

					</form>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<!-- <script type="text/javascript">
		window.onbeforeunload = function() {
			return "You work will be lost.";
		};
	</script> -->
</body>
</html>