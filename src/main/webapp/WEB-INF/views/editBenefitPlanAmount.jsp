<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<style>
.ctgCount {
	display: none;
}
</style>
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
							<h4 class="h4-responsive">Edit ${planPojo.planName} - Amount
								Breakup</h4>
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

							<form:form id="editPlanForm" method="post" action="contacts"
								modelAttribute="planBean" cssClass="form-horizontal">
								<div class="form-group is-empty">

									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<select id="claimFrequency" name="claimFrequency"
												cssClass="form-control">
												<option value="1"
													${planPojo.claimFrequency eq '1' ? 'selected="selected"' : ''}>Monthly</option>
												<option value="3"
													${planPojo.claimFrequency eq '3' ? 'selected="selected"' : ''}>Quarterly</option>
												<option value="6"
													${planPojo.claimFrequency eq '6' ? 'selected="selected"' : ''}>Half
													Yealry</option>
												<option value="12"
													${planPojo.claimFrequency eq '12' ? 'selected="selected"' : ''}>Annually</option>
											</select> <label for="claimFrequency">Claim Frequency</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<select id="claimType" name="claimType"
												cssClass="form-control">
												<option value="FLAT"
													${planPojo.claimType eq 'FLAT' ? 'selected="selected"' : ''}>Flat</option>
												<option value="BAND"
													${planPojo.claimType eq 'BAND' ? 'selected="selected"' : ''}>Band
													wise</option>
												<option value="CTGY"
													${planPojo.claimType eq 'CTGY' ? 'selected="selected"' : ''}>Category
													wise</option>
												<option value="DPNT"
													${planPojo.claimType eq 'DPNT' ? 'selected="selected"' : ''}>Dependents</option>
											</select> <label for="claimType">Claim Type</label>
										</div>

									</div>
								</div>
								<div class="form-group is-empty">

									<div class="md-form flat_deduction">
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<form:input id="yearlyDeduction" name="yearlyDeduction"
												type="text" path="yearlyDeduction" cssClass="form-control"
												style="display: block" />
											<label for="yearlyDeduction" id="lblYearyDeduction"
												style="display: block">Max Yearly Deduction(Rs.)</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<form:input id="yearlyClaim" name="yearlyClaim" type="text"
												path="yearlyClaim" cssClass="form-control"
												onchange="changeAmount(this)" style="display: block" />
											<label for="yearlyClaim" id="lblYearlyClaim"
												style="display: block">Max Yearly Claim(Rs.)</label>
										</div>


									</div>

									<div class="md-form category_count" id="category_count"
										style="display: none">
										<div class="col-md-12 padding_left_0">
											<select id="ctgCount" name="ctgCount">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
											</select> <label for="ctgCount" id="lblCtgCount">No. of
												Categories</label>
										</div>
									</div>
								</div>

								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-12 padding_left_0">
											<table style="display: none" id="tblBand"
												class="table striped table-borderd">
												<tr>
													<th>Band</th>
													<th>Amount</th>
												</tr>
												<c:forEach items="${bands}" var="band">
													<tr>
														<td>${band}</td>
														<td><div class="col-md-12 padding_left_0">
																<input type="number" name="bandAmount${band}"
																	class="breakup_amount" />
															</div></td>
													</tr>
												</c:forEach>
											</table>

											<table style="display: none" id="tblDep"
												class="table striped table-borderd">
												<tr>
													<th>Relationship</th>
													<th>Minimum Age</th>
													<th>Maximum Age</th>
													<th>Yearly Deduction</th>
													<th>Yearly Claim</th>
												</tr>
												<c:forEach begin="1" end="10" varStatus="i">
													<tr>
														<td><select id="depRelationship${i.index}"
															name="depRelationship${i.index}" cssClass="form-control">
																<c:forEach items="${relationships}" var="rel">
																	<option value="${rel}">${rel}</option>
																</c:forEach>
														</select> <label for="depRelationship${i.index}"></label>
														</td>
														<td>
															<div class="col-md-12 padding_left_0">
																<input type="number" name="depMinimumAge${i.index}" />
															</div>
														</td>
														<td>
															<div class="col-md-12 padding_left_0">
																<input type="number" name="depMaximumAge${i.index}" />
															</div>
														</td>
														<td>
															<div class="col-md-12 padding_left_0">
																<input type="number" name="depYearlyDeduction${i.index}" />
															</div>
														</td>
														<td>
															<div class="col-md-12 padding_left_0">
																<input type="number" name="depYearlyClaim${i.index}" />
															</div>
														</td>
													</tr>
												</c:forEach>
											</table>

											<table style="display: none" id="tblCtg_1"
												class="tbl_ctg_class table striped table-borderd">
												<tr>
													<th>Category 1</th>
													<td><div class="col-md-12 padding_left_0">
															<input id="ctg1Name" name="ctg1Name" class="form-control"
																type="text" placeholder="e.g. Vehicle upto 1600 cc" />
														</div></td>
													<td><div class="col-md-12 padding_left_0">
															<input id="ctg1Amount" name="ctg1Amount"
																class="form-control" type="number"
																placeholder="e.g. 15000"
																onchange="calculateCtgTotal(this,1)" />
														</div></td>
												</tr>
												<tr>
													<td>Misc 1</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc1Ctg1Name" name="misc1Ctg1Name"
																class="form-control" type="text"
																placeholder="e.g. Driver expense" />
														</div>
													</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc1Ctg1Amount" name="misc1Ctg1Amount"
																class="form-control" type="number" value="0.0"
																onchange="calculateCtgTotal(this,1)" />
														</div>
													</td>
												</tr>
												<tr>
													<td>Misc 2</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc2Ctg1Name" name="misc2Ctg1Name"
																class="form-control" type="text"
																placeholder="e.g. Driver expense" />
														</div>
													</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc2Ctg1Amount" name="misc2Ctg1Amount"
																class="form-control" type="number" value="0.0"
																onchange="calculateCtgTotal(this,1)" />
														</div>
													</td>
												</tr>
												<th colspan="2">Total</th>
												<td>
													<div class="col-md-12 padding_left_0">
														<input id="ctg1TotalAmount" name="ctg1TotalAmount"
															class="form-control" type="number" value="0.0" />
													</div>
												</td>
												</tr>
											</table>

											<table style="display: none" id="tblCtg_2"
												class="tbl_ctg_class table striped table-borderd">
												<tr>
													<th>Category 2</th>
													<td><div class="col-md-12 padding_left_0">
															<input id="ctg2Name" name="ctg2Name" class="form-control"
																type="text" placeholder="e.g. Vehicle upto 1600 cc" />
														</div></td>
													<td><div class="col-md-12 padding_left_0">
															<input id="ctg2Amount" name="ctg2Amount"
																class="form-control" type="number"
																placeholder="e.g. 15000"
																onchange="calculateCtgTotal(this,2)" />
														</div></td>
												</tr>
												<tr>
													<td>Misc 1</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc1Ctg2Name" name="misc1Ctg2Name"
																class="form-control" type="text"
																placeholder="e.g. Driver expense" />
														</div>
													</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc1Ctg2Amount" name="misc1Ctg2Amount"
																class="form-control" type="number" value="0.0"
																onchange="calculateCtgTotal(this,2)" />
														</div>
													</td>
												</tr>
												<tr>
													<td>Misc 2</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc2Ctg2Name" name="misc2Ctg2Name"
																class="form-control" type="text"
																placeholder="e.g. Driver expense" />
														</div>
													</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc2Ctg2Amount" name="misc2Ctg2Amount"
																class="form-control" type="number" value="0.0"
																onchange="calculateCtgTotal(this,2)" />
														</div>
													</td>
												</tr>
												<th colspan="2">Total</th>
												<td>
													<div class="col-md-12 padding_left_0">
														<input id="ctg2TotalAmount" name="ctg2TotalAmount"
															class="form-control" type="number" value="0.0"
															onchange="calculateCtgTotal(this,2)" />
													</div>
												</td>
												</tr>
											</table>

											<table style="display: none" id="tblCtg_3"
												class="tbl_ctg_class table striped table-borderd">
												<tr>
													<th>Category 3</th>
													<td><div class="col-md-12 padding_left_0">
															<input id="ctg3Name" name="ctg3Name" class="form-control"
																type="text" placeholder="e.g. Vehicle upto 1600 cc" />
														</div></td>
													<td><div class="col-md-12 padding_left_0">
															<input id="ctg3Amount" name="ctg3Amount"
																class="form-control" type="number"
																placeholder="e.g. 15000"
																onchange="calculateCtgTotal(this,3)" />
														</div></td>
												</tr>
												<tr>
													<td>Misc 1</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc1Ctg3Name" name="misc1Ctg3Name"
																class="form-control" type="text"
																placeholder="e.g. Driver expense" />
														</div>
													</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc1Ctg3Amount" name="misc1Ctg3Amount"
																class="form-control" type="number" value="0.0"
																onchange="calculateCtgTotal(this,3)" />
														</div>
													</td>
												</tr>
												<tr>
													<td>Misc 2</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc2Ctg3Name" name="misc2Ctg3Name"
																class="form-control" type="text"
																placeholder="e.g. Driver expense" />
														</div>
													</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc2Ctg3Amount" name="misc2Ctg3Amount"
																class="form-control" type="number" value="0.0"
																onchange="calculateCtgTotal(this,3)" />
														</div>
													</td>
												</tr>
												<tr>
													<th colspan="2">Total</th>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="ctg3TotalAmount" name="ctg3TotalAmount"
																class="form-control" type="number" value="0.0" />
														</div>
													</td>
												</tr>
											</table>

											<table style="display: none" id="tblCtg_4"
												class="tbl_ctg_class table striped table-borderd">
												<tr>
													<th>Category 4</th>
													<td><div class="col-md-12 padding_left_0">
															<input id="ctg4Name" name="ctg4Name" class="form-control"
																type="text" placeholder="e.g. Vehicle upto 1600 cc" />
														</div></td>
													<td><div class="col-md-12 padding_left_0">
															<input id="ctg4Amount" name="ctg4Amount"
																class="form-control" type="number"
																placeholder="e.g. 15000"
																onchange="calculateCtgTotal(this,4)" />
														</div></td>
												</tr>
												<tr>
													<td>Misc 1</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc1Ctg4Name" name="misc1Ctg4Name"
																class="form-control" type="text"
																placeholder="e.g. Driver expense" />
														</div>
													</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc1Ctg4Amount" name="misc1Ctg4Amount"
																class="form-control" type="number" value="0.0"
																onchange="calculateCtgTotal(this,4)" />
														</div>
													</td>
												</tr>
												<tr>
													<td>Misc 2</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc2Ctg4AName" name="misc2Ctg4AName"
																class="form-control" type="text"
																placeholder="e.g. Driver expense" />
														</div>
													</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc2Ctg4Amount" name="misc2Ctg4Amount"
																class="form-control" type="number" value="0.0"
																onchange="calculateCtgTotal(this,4)" />
														</div>
													</td>
												</tr>
												<tr>
													<th colspan="2">Total</th>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="ctg4TotalAmount" name="ctg4TotalAmount"
																class="form-control" type="number" value="0.0" />
														</div>
													</td>
												</tr>
											</table>

											<table style="display: none" id="tblCtg_5"
												class="tbl_ctg_class table striped table-borderd">
												<tr>
													<th>Category 5</th>
													<td><div class="col-md-12 padding_left_0">
															<input id="ctg5Name" name="ctg5Name" class="form-control"
																type="text" placeholder="e.g. Vehicle upto 1600 cc" />
														</div></td>
													<td><div class="col-md-12 padding_left_0">
															<input id="ctg5Amount" name="ctg5Amount"
																class="form-control" type="number"
																placeholder="e.g. 15000"
																onchange="calculateCtgTotal(this,5)" />
														</div></td>
												</tr>
												<tr>
													<td>Misc 1</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc1Ctg5Name" name="misc1Ctg5Name"
																class="form-control" type="text"
																placeholder="e.g. Driver expense" />
														</div>
													</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc1Ctg5Amount" name="misc1Ctg5Amount"
																class="form-control" type="number" value="0.0"
																onchange="calculateCtgTotal(this,5)" />
														</div>
													</td>
												</tr>
												<tr>
													<td>Misc 2</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc2Ctg5Name" name="misc2Ctg5Name"
																class="form-control" type="text"
																placeholder="e.g. Driver expense" />
														</div>
													</td>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="misc2Ctg5Amount" name="misc2Ctg5Amount"
																class="form-control" type="number" value="0.0"
																onchange="calculateCtgTotal(this,5)" />
														</div>
													</td>
												</tr>

												<tr>
													<th colspan="2">Total</th>
													<td>
														<div class="col-md-12 padding_left_0">
															<input id="ctg5TotalAmount" name="ctg5TotalAmount"
																class="form-control" type="number" value="0.0" />
														</div>
													</td>
												</tr>
											</table>

										</div>
									</div>
								</div>


								<form:hidden path="mode" />
								<form:hidden path="step" />

								<div class="form-group">
									<div class="col-md-6">
										<a class="btn yellow darken-3"
											href="<%=request.getContextPath()%>/home/controlPanel">Cancel</a>
										<input type="submit" class="btn btn-primary" value="Next"
											onclick="" />
									</div>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
			</section>

		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {

			$('select').material_select();

			$("#claimType").on("change", function() {
				if ($(this).val() == "CTGY") {
					$("#category_count").css("display", "block");
					$("#tblBand").hide();
					$(".flat_deduction").hide();
					$("#tblDep").hide();
					$(".tbl_ctg_class").hide();
				} else if ($(this).val() == "BAND") {
					$("#category_count").hide();
					$(".flat_deduction").hide();
					$("#tblBand").show();
					$("#tblDep").hide();
					$(".tbl_ctg_class").hide();
				} else if ($(this).val() == 'DPNT') {
					$(".flat_deduction").show();
					$("#tblDep").show();
					$("#tblBand").hide();
					$("#category_count").hide();
					$(".tbl_ctg_class").hide();
				} else {
					$(".flat_deduction").show();
					$("#tblDep").hide();
					$("#tblBand").hide();
					$("#category_count").hide();
					$(".tbl_ctg_class").hide();
				}
			});

			$('#ctgCount').on('change', function() {
				var count = $(this).val();

				$('.tbl_ctg_class').css('display', 'none');

				for (var i = 1; i <= count; i++) {
					$('#tblCtg_' + i).show();
				}

			});
		});
		$(window).load(function() {
			$(".category_count input.select-dropdown").attr("id", "ctgCount");
			$(".category_count span.caret").attr("id", "ctgCount");

			$('select').material_select();
		});
	</script>
</body>
</html>