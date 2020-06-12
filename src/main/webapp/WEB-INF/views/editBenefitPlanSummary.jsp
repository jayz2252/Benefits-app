<!-- 
@author : jithin.kuriakose
@page displays summary of plan going to be created
 -->
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
			<div class="row">
				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">${planPojo.planName}-Summary</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<%-- div class="md-form">
								<img alt="Plan Logo" src="data:image/png;base64,${planPojo.logo}">
							</div> --%>
						</div>
					</div>

					<c:if test="${savedStatus ne null}">
						<div class="row">
							<div class="col-md-12">
								<c:choose>
									<c:when test="${savedStatus}">
										<div class="alert alert-success">
											<strong>Success!</strong> Successfully Saved the new Flexi
											Plan
										</div>
									</c:when>
									<c:otherwise>
										<div class="alert alert-danger">
											<strong>Error: </strong> Failed to Save the plan details,
											please contact the System Administrator
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:if>
					<form:form modelAttribute="planBean" id="benefitPlanSummaryForm"
						action="save" method="POST">

						<div class="row">
							<div class="col-md-12">
								<table class="table striped table-borderd">
									<tr>
										<th>Plan Name</th>
										<td colspan="3">${planPojo.planName}</td>
									</tr>
									<tr>
										<th>Description</th>
										<td colspan="3">${planPojo.planDesc}</td>
									</tr>
									<tr>
										<th>Effective From</th>
										<td><fmt:formatDate pattern="dd-MM-yyyy"
												value="${planPojo.effFrom}" /></td>
										<th>Effective To</th>
										<td><fmt:formatDate pattern="dd-MM-yyyy"
												value="${planPojo.effTill}" /></td>
									</tr>
									<c:if test="${planPojo.claimDocumentsRequired}">
										<tr>
											<td colspan="4">Bills/Documents submission is required
												for Claim process</td>
										</tr>
									</c:if>
								</table>
								<br />
								<table class="table striped table-borderd">
									<c:choose>
										<c:when test="${planPojo.claimType eq 'CTGY'}">
											<tr>
												<th colspan="5">Category-wise Plan Amount Breakup</th>
											</tr>
											<c:forEach items="${planPojo.planCategories}" var="ctg">
												<tr>
													<td />
													<th>${ctg.categoryName}</th>
													<td colspan="2">Rs. ${ctg.categoryAmount}</td>
													<td>Rs. ${ctg.categoryAmount / (12 / planPojo.claimFrequency)}</td>
												</tr>

												<c:forEach items="${ctg.miscs}" var="misc">
													<tr>
														<td />
														<td />
														<th>${misc.miscName}</th>
														<td>Rs. ${misc.miscAmount}</td>
														<td>Rs. ${misc.miscAmount / (12 / planPojo.claimFrequency)}
														</td>
													</tr>
												</c:forEach>
												<tr>
													<th colspan="3">Category Total</th>
													<td>Rs. ${ctg.categoryTotalAmount}</td>
													<td><strong>Rs. ${ctg.categoryTotalAmount / (12 / planPojo.claimFrequency)}</strong>
														per ${planPojo.claimFrequency} month(s)</td>
												</tr>

											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${planPojo.claimType eq 'BAND'}">
													<tr>
														<th colspan="5">Band-wise Plan Amount Breakup</th>
													</tr>
													<c:forEach items="${planPojo.planBands}" var="band">
														<tr>
															<td />
															<td />
															<th>${band.band}</th>
															<td>Rs. ${band.amount}</td>
															<td><strong>Rs. ${band.amount / (12 / planPojo.claimFrequency)}</strong>
																per ${planPojo.claimFrequency} month(s)</td>
														</tr>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<th colspan="5">Fixed Plan Amount Breakup</th>
													</tr>
													<tr>
														<th>Total Yearly Deduction(Max.)</th>
														<td>Rs. ${planPojo.yearlyDeduction}<br /> Rs.
															${planPojo.yearlyDeduction / 12} per month
														</td>
														<th>Total Yearly Claim (Max.)</th>
														<td>Rs. ${planPojo.yearlyClaim}<br /> <strong>Rs.
																${planPojo.yearlyClaim / (12 / planPojo.claimFrequency)}</strong>
															<c:choose>
                                                            <c:when test="${planPojo.claimFrequency eq 1}">
                                                             monthly
                                                            </c:when>
                                                            <c:when test="${planPojo.claimFrequency eq 3}">
                                                             quarterly
                                                            </c:when>
                                                            <c:when test="${planPojo.claimFrequency eq 6}">
                                                            half yearly
                                                            </c:when>
                                                            <c:when test="${planPojo.claimFrequency eq 12}">
                                                             yearly
                                                            </c:when>
                                                            </c:choose>
														</td>
														<td></td>
													</tr>

													<c:if test="${planPojo.claimType eq 'DPNT'}">
														<tr>
															<th colspan="5">Dependent wise Plan Amount Breakup</th>
														</tr>
														<c:forEach items="${planPojo.dependentCategories}"
															var="depCtg">
															<tr>
																<th>${depCtg.relationship}, age from
																	${depCtg.minimumAge} to ${depCtg.maximumAge}</th>
																<th>Claim</th>
																<td>Rs. ${depCtg.yearlyClaim}, <br />
																	Rs. ${depCtg.yearlyClaim / ( 12 / planPojo.claimFrequency )}
																	per ${planPojo.claimFrequency} month(s)
																</td>
																<th>Deduction</th>
																<td>Rs. ${depCtg.yearlyDeduction}, <br />Rs. ${depCtg.yearlyDeduction /  12} per
																	month
																</td>
															</tr>
														</c:forEach>
													</c:if>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</table>
								<br />
								<table class="table striped table-borderd">
									<tr>
										<th colspan="5">Contacts</th>
									</tr>
									<tr>
										<th>Contact Person</th>
										<th>Designation</th>
										<th>Email</th>
										<th>Phone 1</th>
										<th>Phone 2</th>
									</tr>
									<c:forEach items="${planPojo.contacts}" var="contact">
										<tr>
											<td>${contact.fullName}</td>
											<td>${contact.designation}</td>
											<td>${contact.email}</td>
											<td>${contact.phone1}</td>
											<td>${contact.phone2}</td>
										</tr>
									</c:forEach>
								</table>
								<c:if test="${planPojo.optDocumentsRequired}">
									<br />
									<table class="table striped table-borderd">
										<tr>
											<th colspan="2">Documents Required</th>
										</tr>
										<tr>
											<th>Document</th>
											<th>Mandatory</th>
										</tr>
										<c:forEach items="${planPojo.documents}" var="document">
											<tr>
												<td>${document.documentName}</td>
												<td><c:choose>
														<c:when test="${document.mandatory eq 'true'}">
												yes
												<%-- <fieldset class="form-group">
															<input type="checkbox" checked="${document.mandatory}" value="${document.mandatory}" readonly> 
															<label for="doc5Mandatory"></label>
														</fieldset>  --%>
														</c:when>
														<c:otherwise>
												no
												<%-- <fieldset class="form-group">
															<input type="checkbox" checked="${document.mandatory}" value="${document.mandatory}" readonly> 
															<label for="doc5Mandatory"></label>
														</fieldset>  --%>
														</c:otherwise>
													</c:choose> <%-- <input type="text" id="mandatoryValue" value="${document.mandatory}" /> --%>
													<%-- <input type="checkbox"
													checked="${document.mandatory}" disabled="true"> --%>
											</tr>
										</c:forEach>
									</table>
								</c:if>
							</div>
						</div>
						<form:hidden path="mode" />
						<form:hidden path="step" />
						<c:if test="${!savedStatus or savedStatus eq null}">
							<div class="form-group">

								<div class="">
									<a class="btn yellow darken-3"
										href="<%=request.getContextPath()%>/home/controlPanel">Cancel</a>
									<input type="submit" class="btn btn-primary" value="Save Plan" />


									<input type="submit" class="btn btn-primary" value="Edit Plan"
										onclick="form.action='edit'" />

								</div>
							</div>
						</c:if>
					</form:form>
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