<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>





<body>
	<%@include file="employeeNavBar.jsp"%>
	<div class="col-md-3">
		<div id="claimPercentageGauge" class="sticky_left"></div>
	</div>
	<div id="main">
		<div class="wrapper">

			<section id="content" class="">
			<div class="row">
				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">My Claims -
								${planEmployee.benefitPlan.planName}</h4>
						</div>

					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<c:if test="${warnMessage ne null}">
							<div class="alert alert-warning">
								<strong>Oops! </strong>${warnMessage}
							</div>
						</c:if>
						<div class="col-md-12 select_block">
							<table class="table striped table-borderd data_table">
								<thead>
									<tr>
										<th>Claim Ref No.</th>
										<th>Submitted Date</th>
										<th>Request Amount(Rs.)</th>
										<th>Approved Amount(Rs.)</th>
										<th>Status</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${myClaims}" var="claim">
										<tr>

											<td>${claim.claimRefNo}</td>
											<td><fmt:formatDate value="${claim.submittedDate}"
													pattern="dd-MMM-yyyy" /></td>
											<td>${claim.totalRequestedAmount}</td>
											<td>${claim.totalApprovedAmount}</td>
											<td><c:choose>
													<c:when test="${claim.status eq 'SUBM'}">
									Submitted
									</c:when>
													<c:otherwise>

														<c:choose>
															<c:when test="${claim.status eq 'HR_APPR'}">
									Awaiting Finance Approval
									</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${claim.status eq 'FIN_APPR'}">
									Approved
									</c:when>
																	<c:otherwise>
													
									Rejected
									</c:otherwise>
																</c:choose>

															</c:otherwise>
														</c:choose>


													</c:otherwise>

												</c:choose></td>
											<td><a target="_blank"
												href="<%=request.getContextPath()%>/pdf/claim/${claim.benefitPlanClaimId}">
													<i class="fa fa-download" aria-hidden="true"
													data-toggle="tooltip" data-placement="left"
													title="Download Receipt"></i>
											</a> <c:if test="${claim.status eq 'SUBM'}">
													<a
														href="<%=request.getContextPath()%>/home/myFlexiPlans/myClaims/remove/${claim.benefitPlanClaimId}">
														<i class="fa fa-trash-o" aria-hidden="true"
														data-toggle="tooltip" data-placement="left"
														title="Delete this Claim Request"></i>
													</a>
												</c:if>
												<a href="${claim.uploadUrl}" target="_blank" clicked="false"><i
								class="fa fa-paperclip"  title="Bills Uploaded"></i></a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<a class="btn yellow darken-3"
							href="<%=request.getContextPath()%>/home/myFlexiPlans/">Back</a>
						<a class="btn btn-primary"
							href="<%=request.getContextPath()%>/home/myFlexiPlans/myClaims/new/${planEmployee.planEmployeeId}">
							New Claim Request </a>
					</div>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
	 var claimPercentageGauge = new FlexGauge({
         appendTo: '#claimPercentageGauge',
         dialValue: true,
         dialLabel: 'Total Benefits Claimed(%)',
         arcFillInt: ${claimPercentage},
         arcFillTotal: 100
     });
	</script>
</body>
</html>