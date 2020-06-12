<%@page import="com.speridian.benefits2.model.pojo.BenefitPlan"%>
<%@page import="com.speridian.benefits2.service.BenefitPlanService"%>
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlanEmployee"%>
<%@page import="com.speridian.benefits2.model.pojo.Employee"%>
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
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
				<div class="col-sm-6 col-md-9 py-1 px-1">
					<h4 class="h4-responsive">${planEmployee.benefitPlan.planName}-Summary</h4>
				</div>
				<div class="col-sm-6 col-md-3"></div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<table class="table striped table-borderd">
						<tr>
							<th>Financial Year</th>
							<td>${planEmployee.fiscalYear}</td>
							<th>Opted Date</th>
							<td><fmt:formatDate value="${planEmployee.optedDate}"
									pattern="dd/MM/yyyy" /></td>
							
						</tr>
						<tr>
							<c:choose>
								<c:when test="${planEmployee.benefitPlan.claimType eq 'CTGY'}">
									<th>Opted Category</th>
									<td>${planEmployee.planCategory.categoryName}</td>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${planEmployee.benefitPlan.claimType eq 'BAND'}">
											<th>Opted Band</th>
											<td>${planEmployee.optingBand}</td>
										</c:when>
										<c:otherwise>
											<th>Plan Type</th>
											<td>${planEmployee.benefitPlan.claimType eq 'DPNT' ? 'DEPENDENT' : planEmployee.benefitPlan.claimType}</td>
										</c:otherwise>
									</c:choose>

								</c:otherwise>
							</c:choose>
							<th>Yearly Benefits</th>
							<td>
								<p class="points">Rs.${planEmployee.yearlyClaim}</p>
							</td>
							
						</tr>
						<tr />
						<c:choose>
							<c:when test="${planEmployee.benefitPlan.claimDocumentsRequired}">
								<tr>
									<th colspan="4">Claim Details</th>
								</tr>
								<tr>
									<%-- <th style="text-align: center; font-size: initial;">Claim
										Limit
										<p></p>
										<p></p>
										<p style="margin-top: 15%;">Rs.${planEmployee.yearlyClaim}</p>
									</th> --%>
									<th style="text-align: center; font-size: initial;"><c:choose>
											<c:when
												test="${planEmployee.benefitPlan.claimDocumentsRequired}">

												<a
													href="<%=request.getContextPath()%>/home/myFlexiPlans/myClaims/${planEmployee.planEmployeeId}">
													Total Claims Submitted
													<p>${approvedClaimCount + rejectedClaimCount + pendingClaimCount}</p>
													<p>Rs.${totalClaimAmountSubmited}</p>
												</a>
											</c:when>
											<c:otherwise>
												<span class="span_element">Total Claims Submitted
													<p>${approvedClaimCount + rejectedClaimCount + pendingClaimCount}</p>
													<p>Rs.${totalClaimAmountSubmited}</p>
												</span>


											</c:otherwise>
										</c:choose></th>
									<th
										style="text-align: center; color: darkgreen; font-size: initial;">Approved
										<p>${approvedClaimCount}</p>
										<p>Rs.${approvedTotal}</p>
									</th>
									<th
										style="text-align: center; color: crimson; font-size: initial;">Rejected
										<p>${rejectedClaimCount}</p>
										<p>Rs.${rejectedAmount}</p>
									</th>
									<th
										style="text-align: center; color: orange; font-size: initial;">Pending
										<p>${pendingClaimCount}</p>
										<p>Rs.${pendingAmount}</p>
									</th>
								</tr>

								<tr>
									<td colspan="4"></td>
								</tr>

								<tr>
									<th>Maximum Deduction</th>
									<td>
										<p class="points">Rs.${planEmployee.yearlyDeduction}</p>
									</td>
									<th>Maximum Claim</th>
									<td>
										<p class="points">Rs.${planEmployee.yearlyClaim}</p>
									</td>
									
								</tr>
								<tr>
									<th>Total Amount Deducted</th>
									<td>
										<p class="points">Rs.${planEmployee.amountDeducted}</p>
									</td>
									<th>Total Amount Claimed</th>
									<td>
										<p class="points">Rs.${planEmployee.amountClaimed}</p>
									</td>
									
								</tr>

								<tr>
									<th>Claim %</th>
									<td colspan="3">${claimPercentage}%</td>

								</tr>
							</c:when>
						</c:choose>
					</table>

					<table class="table striped table-borderd">

						<c:if test="${planEmployee.benefitPlan.optDocumentsRequired}">
							<tr>
								<th colspan="2">Verified Document(s)</th>
							</tr>
							<c:forEach items="${documents}" var="doc">
								<tr>
									<th><a target="_blank" href="${doc.downloadUrl}">${doc.documentType}</a></th>
									<td><a target="_blank" href="${doc.downloadUrl}"><i
											class="fa fa-download" aria-hidden="true" /></a></td>
								</tr>
							</c:forEach>
						</c:if>

						<c:if test="${planEmployee.benefitPlan.promptFieldsOnOPT}">
							<tr>
								<th colspan="2">Additional Detail(s)</th>
							</tr>
							<c:forEach items="${planEmployee.fields}" var="field">
								<tr>
									<th>${field.field.uiLabel}</th>
									<td>${field.value}</td>
								</tr>
							</c:forEach>
						</c:if>

					</table>

					<a class="btn yellow darken-3"
						href="<%=request.getContextPath()%>/home/myFlexiPlans">Back</a>


				</div>
			</div>

			</section>
		</div>

	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
	var documentRequired=${planEmployee.benefitPlan.optDocumentsRequired};
	if(documentRequired){
		var claimPercentageGauge = new FlexGauge({
			 appendTo: '#claimPercentageGauge',
	         dialValue: true,
	         dialLabel: 'Total Benefits Claimed(%)',
	         arcFillInt: ${claimPercentage},
	         arcFillTotal: 100     
    });
	}
	 
	 function numberWithCommas(x) {
		    return x.toString().replace(/\B(?=(?:\d{3})+(?!\d))/g, ",");
		}
	 
	    $('.points').each(function() {
	    var v_pound = $(this).html();
	    v_pound = numberWithCommas(v_pound);

	    $(this).html(v_pound)
	        
	        })
	</script>
</body>
</html>
