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
	<div class="col-md-3">
		<c:choose>
			<c:when
				test="${benefitPlan.claimType eq 'CTGY' or benefitPlan.claimType eq 'DPNT'}">
				<span id="myPlanDetails" class="sticky_left">
					<table>
						<tr>
							<th colspan="2">My Plan Details</th>
						</tr>
						<tr>
							<th>Yearly Benefits</th>
							<td>Rs.<span id="myPlanYearlyTotal"
								style="font-weight: bolder;">0.0</span></td>
						</tr>
						<tr>
							<th>Claim Breakup</th>
							<td>Rs.<span id="myPlanBreakupTotalClaim" class=""
								style="font-weight: bolder;">0.0</span> per
								${benefitPlan.claimFrequency} month(s)
							</td>
						</tr>
						<tr>
							<th>Yearly Deduction</th>
							<td>Rs.<span id="myPlanYearlyTotalDeduction"
								style="font-weight: bolder;">0.0</span></td>
						</tr>
						<tr>
							<th>Monthly Deduction</th>
							<td>Rs.<span id="myPlanMonthlyDeduction" class=""
								style="font-weight: bolder;">0.0</span></td>
						</tr>

					</table>
				</span>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${benefitPlan.claimType eq 'BAND'}">
						<c:if test="${planBand ne null}">
							<span id="myPlanDetails" class="sticky_left">
								<table>
									<tr>
										<th colspan="2">My Plan Details</th>
									</tr>
									<tr>
										<th>Yearly Benefits</th>
										<td>Rs.<span id="myPlanYearlyTotal"
											style="font-weight: bolder;" class="points">${planBand.amount}</span>
										</td>
									</tr>
									<tr>
										<th>Claim Breakup</th>
										<td>Rs.<span id="myPlanBreakupTotalClaim" class="points"
											style="font-weight: bolder;">${planBand.amount /  (12 / benefitPlan.claimFrequency)}</span>
											<c:choose>
												<c:when test="${benefitPlan.claimFrequency eq 1}">
                                                             monthly
                                                            </c:when>
												<c:when test="${benefitPlan.claimFrequency eq 3}">
                                                             quarterly
                                                            </c:when>
												<c:when test="${benefitPlan.claimFrequency eq 6}">
                                                            half yearly
                                                            </c:when>
												<c:when test="${benefitPlan.claimFrequency eq 12}">
                                                             yearly
                                                            </c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<th>Yearly Deduction</th>
										<td>Rs.<span id="myPlanYearlyTotalDeduction" class="points"
											style="font-weight: bolder;">${planBand.amount}</span></td>
									</tr>
									<tr>
										<th>Monthly Deduction</th>
										<td>Rs.<span id="myPlanMonthlyDeduction" class="points"
											style="font-weight: bolder;">${planBand.amount / 12 }</span></td>
									</tr>

								</table>
							</span>
						</c:if>
					</c:when>
					<c:otherwise>
						<span id="myPlanDetails" class="sticky_left">
							<table>
								<tr>
									<th colspan="2">My Plan Details</th>
								</tr>
								<tr>
									<th>Yearly Benefits</th>
									<td>Rs.<span class="points" id="myPlanYearlyTotal"
										style="font-weight: bolder;">${benefitPlan.yearlyClaim}</span></td>
								</tr>
								<tr>
									<th>Claim Breakup</th>
									<td>Rs.<span class="points" id="myPlanBreakupTotalClaim"
										class="" style="font-weight: bolder;">${benefitPlan.yearlyClaim /  (12 / benefitPlan.claimFrequency)}</span>
										<c:choose>
											<c:when test="${benefitPlan.claimFrequency eq 1}">
                                                             monthly
                                                            </c:when>
											<c:when test="${benefitPlan.claimFrequency eq 3}">
                                                             quarterly
                                                            </c:when>
											<c:when test="${benefitPlan.claimFrequency eq 6}">
                                                            half yearly
                                                            </c:when>
											<c:when test="${benefitPlan.claimFrequency eq 12}">
                                                             yearly
                                                            </c:when>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th>Yearly Deduction</th>
									<td>Rs.<span class="points"
										id="myPlanYearlyTotalDeduction" style="font-weight: bolder;">${benefitPlan.yearlyDeduction}</span></td>
								</tr>
								<tr>
									<th>Monthly Deduction</th>
									<td>Rs.<span class="points" id="myPlanMonthlyDeduction"
										class="" style="font-weight: bolder;">${benefitPlan.yearlyClaim / 12 }</span></td>
								</tr>

							</table>
						</span>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>


	</div>
	<div id="main">
		<div class="wrapper">
			<section id="content" class="">

			<div class="white col-md-12">
				<div class="row">

					<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">${benefitPlan.planName}-Summary</h4>
					</div>
					<div class="col-sm-6 col-md-3">
						<div class="md-form">
							<input placeholder="Search..." type="text" id="form5"
								class="form-control">
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<input type="hidden" name="planId" id="planId"
							value="${benefitPlan.benefitPlanId }" />
						<table class="table striped table-borderd">
							<tr>
								<th>Plan Name</th>
								<td colspan="3">${benefitPlan.planName}</td>
							</tr>
							<tr>
								<th>Description</th>
								<td colspan="3">${benefitPlan.planDesc}</td>
							</tr>
							<tr>
								<th>Effective From</th>
								<td><fmt:formatDate pattern="dd-MM-yyyy"
										value="${benefitPlan.effFrom}" /></td>
								<th>Effective To</th>
								<td><fmt:formatDate pattern="dd-MM-yyyy"
										value="${benefitPlan.effTill}" /></td>
							</tr>

							<tr>
								<th>Plan Type <input type="hidden"
									value="${benefitPlan.claimType}" id="claimType"></th>
								<td><c:choose>
										<c:when test="${benefitPlan.claimType eq 'CTGY'}">
								Category-wise
								</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${benefitPlan.claimType eq 'BAND'}">
										Band-wise
										</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${benefitPlan.claimType eq 'DPNT'}">
												Dependent-wise 
												</c:when>
														<c:otherwise>
										Flat - Common for All
										</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose></td>
								<th>Claim Frequency</th>
								<td><span id="claimFrequency">${benefitPlan.claimFrequency}</span> month(s)
								</td>
							</tr>
							<c:if test="${benefitPlan.claimDocumentsRequired}">
								<tr>
									<td colspan="4">Bills/Documents submission is required for
										Claim process</td>
								</tr>
							</c:if>
						</table>
						<br /> <input type="hidden" value="${benefitPlan.yearlyClaim}"
							id="maxYearlyClaim">

						<table class="table striped table-borderd">
							<c:choose>
								<c:when test="${benefitPlan.claimType eq 'CTGY'}">
									<tr>
										<th colspan="5">Category-wise Plan Amount Breakup</th>
									</tr>
									<tr>
										<th colspan="3">Particulars</th>
										<th>Total Claim</th>
										<th>Breakup Claim</th>
									</tr>
									<c:forEach items="${benefitPlan.planCategories}" var="ctg">
										<tr>
											<td />

											<td><input name="group1" type="radio" class="ctg"
												id="ctg${ctg.planCategoryId}" value="${ctg.planCategoryId}" />
												<label for="ctg${ctg.planCategoryId}" onclick="change()">${ctg.categoryName}</label></td>

											<th></th>
											<td>Rs.<span class="points"
												id="yearlyTotal${ctg.planCategoryId}">${ctg.categoryAmount}</span></td>
											<td>Rs.<span class="points"
												id="breakupClaim${ctg.planCategoryId}">${ctg.categoryAmount / (12 / benefitPlan.claimFrequency)}</span></td>
											<td />

										</tr>

										<c:forEach items="${ctg.miscs}" var="misc">
											<tr>
												<td />
												<td>
													<%-- <input id="misc${misc.categoryMiscId}" type="checkbox" class="misc_all misc_${ctg.planCategoryId}" disabled="true"/> --%>

													<fieldset class="form-group">
														<input type="checkbox" id="misc${misc.categoryMiscId}"
															class="misc_all misc_${ctg.planCategoryId}"
															disabled="true" value="${misc.categoryMiscId}" /> <label
															for="misc${misc.categoryMiscId}" class="pull-right"></label>
													</fieldset>
												</td>
												<th>${misc.miscName}</th>
												<td>Rs.<span class="points"
													id="miscAmount${misc.categoryMiscId}">
														${misc.miscAmount}</span></td>
												<td>Rs. ${misc.miscAmount / (12 / benefitPlan.claimFrequency)}
												</td>
											</tr>
										</c:forEach>
										<tr>
											<th colspan="3">Category Total</th>
											<td>Rs. ${ctg.categoryTotalAmount}</td>
											<td><strong>Rs. ${ctg.categoryTotalAmount / (12 / benefitPlan.claimFrequency)}</strong>
												per ${benefitPlan.claimFrequency} month(s)</td>
										</tr>

									</c:forEach>

									<input type="hidden" name="categoryId" id="categoryId" />

								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${benefitPlan.claimType eq 'BAND'}">
											<tr>
												<th colspan="5">Band-wise Plan Amount Breakup</th>
											</tr>
											<c:forEach items="${benefitPlan.planBands}" var="band">
												<c:if test="${appContext.currentEmployee.band eq band.band}">
													<tr>

														<td />
														<td />
														<th>${band.band}</th>

														<td>Rs. ${band.amount}</td>
														<input type="hidden" name="bandAmount" id="bandAmount"
															value="${band.amount}" />
														<td><strong>Rs. ${band.amount / (12 / benefitPlan.claimFrequency)}</strong>
															per ${benefitPlan.claimFrequency} month(s)</td>


													</tr>
												</c:if>
											</c:forEach>

										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${benefitPlan.claimType eq 'DPNT'}">
													<tr>
														<th colspan="5">Dependent wise Plan Amount Breakup</th>
													</tr>
													<tr>
														<th>Select</th>
														<th>Dependent Name</th>
														<th>Relationship</th>
														<th>Deduction</th>
														<th>Claim</th>
													</tr>

													<c:forEach items="${qualifiedDependents}" var="dep">
														<tr>
															<td><input type="checkbox" id="dep${dep.depId}"
																class="dependents_checkbox" value="${dep.depId}" /> <label
																for="dep${dep.depId}" class="pull-left"></label></td>



															<c:if test="${count>2}">
																<div class="alert alert-warning">
																	<strong>Warning!</strong> The plan is applicable to two
																	dependent only
																</div>
															</c:if>



															<td>${dep.depName}</td>
															<td>${dep.relationship}</td>
															<%-- <td>
																<fieldset class="form-group">
																	<input type="checkbox" id="dep${dep.depId}"
																		class="dependents_checkbox" value="${dep.depId}" /> <label
																		for="dep${dep.depId}" class="pull-right"></label>
																</fieldset>
															</td>
															<td>${dep.depName}</td>
															<td>${dep.relationship}</td>--%>

															<td><span id="depYearlyDeductionAmount${dep.depId}">${dep.yearlyDeduction}</span></td>
															<td><span id="depYearlyClaimAmount${dep.depId}">${dep.yearlyClaim}</span></td>
														</tr>
													</c:forEach>
													<div class="alert alert-warning">
														<strong>Alert!</strong> If your dependents list is not
														displayed below please update it in the mirror.
													</div>
												</c:when>
												<c:otherwise>
													<tr>
														<th colspan="5">Fixed Plan Amount Breakup</th>
													</tr>
													<tr>
														<th>Total Yearly Deduction</th>
														<td>
															<p class="points">
																Rs.${benefitPlan.yearlyDeduction}<br />,
																Rs.${benefitPlan.yearlyDeduction / 12} (Monthly)
															</p>
														</td>


													</tr>
													<tr>

													</tr>
												</c:otherwise>
											</c:choose>

										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</table>
						<br />
						<c:if test="${benefitPlan.optDocumentsRequired}">

							<br />
							<table class="table striped table-borderd">
								<tr>
									<th colspan="2">Documents Required</th>
								</tr>
								<tr>
									<div class="alert alert-warning">
										<strong>Important Warning!</strong> Document(s) will be
										uploaded to DocMan. If you miss any document(s),it may lead to
										the Rejection of your Enrollment. Click <i
											class="fa fa-paperclip" aria-hidden="true"></i> icon against
										each document to upload
									</div>
								</tr>
								<tr>
									<th>Document</th>
									<th>Upload RC book</th>
								</tr>
								<c:forEach items="${documents}" var="document">
									<tr>
										<td>${document.documentType}<c:if
												test="${document.mandatory}">
												<span style="color: red">*</span>
											</c:if>

										</td>
										<td><a href="${document.uploadUrl}" target="_blank"
											class="doc_upload_link" id="upload_${document.documentId}"
											uuid="${document.docManUuid}"
											mandatory="${document.mandatory}" clicked="false"><i
												class="fa fa-paperclip" aria-hidden="true"></i></a></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${benefitPlan.promptFieldsOnOPT}">

							<br />
							<table class="table striped table-borderd">
								<tr>
									<th colspan="2">Mandatory Fields</th>
								</tr>
								<c:forEach items="${benefitPlanFields}" var="benefitPlanfield">
									<tr>
										<td>${benefitPlanfield.uiLabel}</td>
										<td><input
											customLabel=${benefitPlanfield.uiLabel
											}
											type=${benefitPlanfield.uiRenderType
											}
											id="${benefitPlanfield.fieldName}"
											name="${benefitPlanfield.fieldName}"
											class="plan_custom_fields"
											customRequired="${benefitPlanfield.mandatory}" placeholder="${benefitPlanfield.placeHolder}"></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<div class="form-group"> 
							<div class="">
								<a class="btn yellow darken-3"
									href="<%=request.getContextPath()%>/home/myFlexiPlans">
									Back </a>
								<c:choose>

									<c:when test="${!chooseButtonEnabled}">

										<div class="alert alert-warning">
											<strong>Sorry..!</strong> You are not eligible to opt this
											plan, please contact HR team for more information
										</div>
									</c:when>
									<c:otherwise>
										<a class="waves-effect waves-light btn"
											href="#optConfirmModal" id="btnChoosePlan">Choose This
											Plan</a>
									</c:otherwise>
								</c:choose>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</section>
	</div>
	</div>
	<!-- Modal Structure -->
	<div id="optConfirmModal" class="modal modal-fixed-footer">
		<div class="modal-content">
			<h4>Confirm Choose Plan</h4>
			<p>You are going to opt this plan for FY
				${appContext.currentFiscalYear} as below. Please confirm to proceed.</p>
			<span id="planDetailsModal"></span>

			<p>Notes :
			<ol>
				<li>This benefit is purely made for the tax savings.</li>
				<li>Once enrolled, the benefit cannot be rolled back in this FY.</li>
				<li>Any discrepancy in your submitted details may lead into claim rejection.</li>

			</ol>
			</p>


			<input type="checkbox" id="chkAgreeTermsToEnroll" /> <label
				for="chkAgreeTermsToEnroll" class="pull-left"> I read and
				understood the conditions, and agreed to abide the same. Please
				confirm to proceed </label>
		</div>
		<div class="modal-footer">
			<form action="${benefitPlan.benefitPlanId}/optit" method="POST">
				<input type="hidden" name="planId"
					value="${benefitPlan.benefitPlanId}" /> <input type="hidden"
					name="finalCategoryId" id="finalCategoryId" /> <input
					type="hidden" name="finalMiscIds" id="finalMiscIds" /> <input
					type="hidden" name="finalDepIds" id="finalDepIds" />

				<c:if test="${benefitPlan.promptFieldsOnOPT}">
					<c:forEach items="${benefitPlanFields}" var="benefitPlanfield">
						<input type="hidden" id="fieldSubmit${benefitPlanfield.fieldName}"
							name="${benefitPlanfield.fieldName}"
							class="plan_custom_fields_submit">
					</c:forEach>
				</c:if>

				<c:if test="${benefitPlan.optDocumentsRequired}">
					<c:forEach items="${documents}" var="document">
						<input type="hidden" name="docUuid${document.documentId}"
							value="${document.docManUuid}">
					</c:forEach>
				</c:if>


				<button id="btnConfirmEnrollPlan"
					class="modal-action modal-close waves-effect waves-green btn-flat btn-primary"
					disabled>Confirm</button>

				<a href="#"
					class="waves-effect waves-red btn-flat btn-primary modal-close">Cancel</a>


			</form>
			<!-- <a href="#!"
				class="modal-action modal-close waves-effect waves-green btn-flat ">Agree</a> -->
		</div>
	</div>

	<%@include file="includeFooter.jsp"%>

	<script>
		$(".ctg").on("change", function() {
			var ctgId = $(this).val();
			$(".misc_all").prop('checked', false);
			$(".misc_all").prop('disabled', true);
			$(".misc_" + ctgId).prop('disabled', false);
			var yearlyTotal = $("#yearlyTotal" + ctgId).text();
			var claimBreakup = $("#breakupClaim" + ctgId).text();
			$("#myPlanYearlyTotal").text(yearlyTotal);
			$("#myPlanMonthlyDeduction").text(parseFloat(yearlyTotal) / 12);
			$("#myPlanBreakupTotalClaim").text(claimBreakup);

			$("#finalCategoryId").val(ctgId);
		});

		$(".misc_all").on(
				"change",
				function() {
					var miscId = $(this).val();
					var miscClaimAmount = $("#miscAmount" + miscId).text();
					var miscDeductionAmount = $("#miscAmount" + miscId).text();
					var claimFrequency = $("#claimFrequency").text();
					var yearlyClaimTotal = 0;
					var yearlyDeductionTotal = 0;
					if (this.checked) {
						yearlyClaimTotal = parseFloat($("#myPlanYearlyTotal")
								.text())
								+ parseFloat(miscClaimAmount);
						yearlyDeductionTotal = parseFloat($(
								"#myPlanYearlyTotalDeduction").text())
								+ parseFloat(miscDeductionAmount);
					} else {
						yearlyClaimTotal = parseFloat($("#myPlanYearlyTotal")
								.text())
								- parseFloat(miscClaimAmount);
						yearlyDeductionTotal = parseFloat($(
								"#myPlanYearlyTotalDeduction").text())
								- parseFloat(miscDeductionAmount);
					}

					$("#myPlanYearlyTotal").text(yearlyClaimTotal.toFixed());
					$("#myPlanYearlyTotalDeduction").text(
							yearlyDeductionTotal.toFixed());

					$("#myPlanMonthlyDeduction").text(
							(parseFloat(yearlyDeductionTotal) / 12).toFixed());

					var claimBreakup = yearlyClaimTotal
							/ (12 / parseFloat(claimFrequency));
					$("#myPlanBreakupTotalClaim").text(claimBreakup); 

				});
		$(".dependents_checkbox")
				.on(
						"change",
						function() {
							var depId = $(this).val();
							var claimFrequency = $("#claimFrequency").text();
							var depClaimAmount = $(
									"#depYearlyClaimAmount" + depId).text();
							var depDeductionAmount = $(
									"#depYearlyDeductionAmount" + depId).text();
							var yearlyClaimTotal = 0;
							var yearlyDeductionTotal = 0;

							if (this.checked) {
								yearlyClaimTotal = parseFloat($(
										"#myPlanYearlyTotal").text())
										+ parseFloat(depClaimAmount);
								yearlyDeductionTotal = parseFloat($(
										"#myPlanYearlyTotalDeduction").text())
										+ parseFloat(depDeductionAmount);
							} else {
								yearlyClaimTotal = parseFloat($(
										"#myPlanYearlyTotal").text())
										- parseFloat(depClaimAmount);
								yearlyDeductionTotal = parseFloat($(
										"#myPlanYearlyTotalDeduction").text())
										- parseFloat(depDeductionAmount);
							}

							var maxYearlyClaim = parseFloat($("#maxYearlyClaim")
									.val());

							if (yearlyClaimTotal > maxYearlyClaim) {
								$(this).attr("checked", false);
								alert("Sorry, maximum available claim is Rs."
										+ maxYearlyClaim + " per year");
							} else {

								$("#myPlanYearlyTotal").text(
										yearlyClaimTotal.toFixed());
								$("#myPlanYearlyTotalDeduction").text(
										yearlyDeductionTotal.toFixed());

								$("#myPlanMonthlyDeduction").text(
										(parseFloat(yearlyDeductionTotal) / 12)
												.toFixed());
								var claimBreakup = yearlyClaimTotal
										/ (12 / parseFloat(claimFrequency));
								$("#myPlanBreakupTotalClaim")
										.text(claimBreakup);
							}

						});

		$("#btnChoosePlan")
				.on(
						"click",
						function() {
							var hasErrors = false;
							
							var claimType = $("#claimType").val();

							var str = "";
							if (claimType == 'CTGY') {

								var oneCtgSelected = false;
								$('.ctg').each(function() {
									if (this.checked) {
										oneCtgSelected = true;
									}
								});

								if (oneCtgSelected == true) {
									$(".misc_all").each(function() {
										if (this.checked) {
											str += $(this).val() + ",";
										}
									});
									$("#finalMiscIds").val(str);
								} else {
									hasErrors = true;
									alert("Please select at least one Category to Choose Plan");
								}
								
							} else if (claimType == 'DPNT') {

								$(".dependents_checkbox").each(function() {
									if (this.checked) {
										str += $(this).val() + ",";
									}
								});
								$("#finalDepIds").val(str);

								if (str == "") {
									alert("Please select at least one dependent to Choose the Plan");
									hasErrors = true;
								} 
							}  
							
							if (${benefitPlan.optDocumentsRequired}){     
								var documentsMissing = false; 
								$('.doc_upload_link').each(function(){
									var mandatory = $(this).attr('mandatory');
									
									if (mandatory){
										var uuid = $(this).attr('uuid'); 
										var xhttp = new XMLHttpRequest();
									    xhttp.open("GET", "/api/docman/document/"+uuid+"/availability/session/${appContext.userLoginKey}", false);
									    xhttp.setRequestHeader("Content-type", "application/json");
									    xhttp.send();
									    var response = xhttp.responseText;
									    
									    if (response == 'false'){
									    	documentsMissing = true;
									    	return false;
									    } 
									}
								});
								
								
								if (documentsMissing){
									hasErrors = true;
									alert("Please upload the mandatory documents to enroll the plan")
								}
							}
							
							if(${benefitPlan.promptFieldsOnOPT}){   
								var requiredFieldMissing = false;
								var requiredFieldName = "";
								$('.plan_custom_fields').each(function(){
									var required = $(this).attr('customRequired');
									if (required == "true"){
										if ($(this).val() == ""){
											requiredFieldMissing = true;
											requiredFieldName = $(this).attr('customLabel');
											return false;
										}
									}
								});

								if (requiredFieldMissing){
									alert('Please fill the required field ' + requiredFieldName);
									hasErrors = true
								}else{
									$('.plan_custom_fields').each(function(){
										var id=$(this).attr('id');
										$('#fieldSubmit'+id).val($(this).val());
									});
								}
							}
							$("#planDetailsModal").html( 
									$("#myPlanDetails").html());
							
							if (hasErrors == true){
								$(this).attr("href", "#");
							}else{
								$(this).attr("href", "#optConfirmModal");
							}
						}); 
		
		$('#chkAgreeTermsToEnroll').on('change', function(){
			if (this.checked){
				$('#btnConfirmEnrollPlan').removeAttr('disabled');
			}else{
				$('#btnConfirmEnrollPlan').attr('disabled','disabled');
			}
		});
		
		$('.doc_upload_link').on('click', function(){
			$(this).attr('clicked','true');
		});

		function checkboxes() {
			var inputElems = document.getElementsByTagName("input"), count = 0;
			for (var i = 0; i < inputElems.length; i++) {
				if (inputElems[i].type === "checkbox"
						&& inputElems[i].checked === true) {
					count++;
					//alert(count);
				}
			}
		}
		
		
		function numberWithCommas(x) {
		    return x.toString().replace(/\B(?=(?:\d{3})+(?!\d))/g, ",");
		}


		    $('.points').each(function(){
		    var v_pound = $(this).html();
		    v_pound = numberWithCommas(v_pound);

		    $(this).html(v_pound)
		        
		        })

	</script>
	<style>
	::placeholder {
    color: grey;
    opacity: 1; 
}
	</style>
	<!-- <script>
	$(window).load(function() {
		$("#content").css({'min-height':($(document).height()-110+'px')});
	});
	</script> -->
</body>
</html>