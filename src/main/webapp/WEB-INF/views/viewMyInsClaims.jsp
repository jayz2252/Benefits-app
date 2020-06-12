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
			<div class="white col-md-12">
				<div class="row">
					<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">Manage My Claims</h4>
					</div>
				</div>
				<div class="row">
					<table class="table striped table-borderd">
						<thead>
							<th>Claim Ref No.</th>
							<th>Type</th>
							<th>Requested Date</th>
							<th>Amount Requested (Rs.)</th>
							<th>Amount Approved (Rs.)</th>
							<th>Status</th>
							<th>Actions</th>
						</thead>
						<tbody>
							<c:forEach items="${claims}" var="claim">
								<tr>
									<td>${claim.claimRefno}</td>
									<td><c:choose>
											<c:when test="${claim.claimType eq 'PAF'}">
												<i class="fa fa-medkit" aria-hidden="true"
													title="Pre Authorization Form (Planned Hospitalization)"></i>
											</c:when>
											<c:otherwise>
												<i class="fa fa-hospital-o" aria-hidden="true"
													title="Bills/Expenses Reimbursement"></i>
											</c:otherwise>
										</c:choose></td>
									<td><fmt:formatDate pattern="dd-MMM-yyyy"
											value="${claim.requestedDate}" /></td>
									<td>${claim.totalReqAmount}</td>
									<td>${claim.totalApprovedAmount}</td>
									<td><c:choose>
											<c:when test="${claim.status eq 'SUBM'}">
											Submitted
										</c:when>
											<c:when test="${claim.status eq 'HR_APPR'}">
											Insurance Desk Verified, waiting for Insurance Committee Confirmation
										</c:when>
											<c:when test="${claim.status eq 'INS_APPR' && claim.claimType eq 'BILL'}">
											Insurance Desk Confirmed, waiting for Management Approval
										</c:when>
										
											<c:when test="${claim.status eq 'APPR'}">
											Approved
										</c:when>
										<c:when test="${claim.status eq 'RJCT'}">
											Rejected
										</c:when>
										<c:when test="${claim.status eq 'PAID'}">
											Payment Processed
										</c:when>
										<c:when test="${claim.status eq 'NOT_PAID'}">
											Payment Partially processed
										</c:when>
											
										</c:choose></td>
									<td><a href="<%=request.getContextPath()%>/home/myInsurancePlan/viewClaims/${claim.insPlanEmployeeClaimId}/details"><i class="fa fa-eye"
											aria-hidden="true" title="View Details"></i></a> 
											<a href="<%=request.getContextPath()%>/home/myInsurancePlan/viewClaims/pdfReport/paf/${claim.insPlanEmployeeClaimId}"><i
											class="fa fa-file-pdf-o" aria-hidden="true"
											title="Download PDF Report"></i></a> <c:if
											test="${claim.status eq 'SUBM'}">
											<a href="#"><i class="fa fa-trash" aria-hidden="true"
												title="Delete the Claim Request" style="color: #bf0b4d"></i></a>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				<c:if test="${savedExits}">
				<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">My Saved Claims</h4>
					</div>
				<div class="row">
					<table class="table striped table-borderd">
						<thead>
							<th>Claim Ref No.</th>
							<th>Type</th>
							<th>Requested Date</th>
							<th>Amount Requested (Rs.)</th>
							<th>Amount Approved (Rs.)</th>
							<th>Status</th>
							<th>Actions</th>
						</thead>
						<tbody>
							<c:forEach items="${savedClaims}" var="claim">
								<tr>
									<td>${claim.claimRefno}</td>
									<td><c:choose>
											<c:when test="${claim.claimType eq 'PAF'}">
												<i class="fa fa-medkit" aria-hidden="true"
													title="Pre Authorization Form (Planned Hospitalization)"></i>
											</c:when>
											<c:otherwise>
												<i class="fa fa-hospital-o" aria-hidden="true"
													title="Bills/Expenses Reimbursement"></i>
											</c:otherwise>
										</c:choose></td>
									<td><fmt:formatDate pattern="dd-MMM-yyyy"
											value="${claim.requestedDate}" /></td>
									<td>${claim.totalReqAmount}</td>
									<td>${claim.totalApprovedAmount}</td>
									<td>SAVED</td>
									<td><a href="<%=request.getContextPath()%>/home/myInsurancePlan/${planEmployeeID}/preAuth/new" title="Complete Pre Authorization"><i class="fa fa-check"></i></a> 
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				</c:if>
				<a href="<%=request.getContextPath()%>/home/myInsurancePlan" class="btn btn yellow darken-3" id="back">Back</a>
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