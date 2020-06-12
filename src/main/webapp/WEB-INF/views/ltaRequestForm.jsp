<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$(".check").change(function() {

			if ($("#modeOfTransport").value() == "Others") {

				$("#other-input").css("display", "block");
			} else {
				//remove if unchecked

				$("#other-input").css("display", "none");
			}
		});

		$("#originMain").on("change", function() {
			var origin = $(this).val();
			$(".origin_dep").val(origin);
		});

		$("#destinationMain").on("change", function() {
			var destination = $(this).val();
			$(".destination_dep").val(destination);
		});
	});
</script>

<style>
ul.dropdown-content {
	display: none;
}

ul.dropdown-content+select {
	display: block;
}

input.select-dropdown, span.caret {
	display: none !important;
}

.margin_top_25 {
	margin-top: 25px;
}

.sticky_left .overflow_div {
	overflow-y: scroll;
	height: 100%;
	float: left;
	width: 100%;
	position: relative;
}
</style>
<body>
	<%@include file="employeeNavBar.jsp"%>
	<div class="col-md-3 sticky_left">
		<div class="overflow_div">
			<h4 class="h4-responsive">Available Leave Requests</h4>
			<table class="table striped table-borderd">
				<tr>
					<th>Date(s)</th>
					<th>Type</th>
				</tr>
				<c:forEach items="${availableLeaves}" var="leave">
					<c:if test="${leave.approvedBy ne '0'}">
						<tr>
							<td><c:choose>
									<c:when test="${leave.fromDate eq leave.toDate}">
										<fmt:formatDate pattern="dd-MMM-yyyy"
											value="${leave.fromDate.toGregorianCalendar().time}" />
									</c:when>
									<c:otherwise>
										<fmt:formatDate pattern="dd-MMM-yyyy"
											value="${leave.fromDate.toGregorianCalendar().time}" /> to <fmt:formatDate
											pattern="dd-MMM-yyyy"
											value="${leave.toDate.toGregorianCalendar().time}" />
									</c:otherwise>
								</c:choose></td>
							<td>${leave.leaveType}</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>

	<div id="main">
		<div class="wrapper">
			<section id="content" class="">
			<div class="row">
				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">Declaration for Leave Travel
								Allowance (LTA) - Financial Year ${appContext.currentFiscalYear}</h4>
							<br /> <font size="3" color="#41c4f4">LTA EXEMPT IS TWO
								JOURNEYS IN A BLOCK OF FOUR CALENDAR YEARS (Current Block is
								calendar year ${appContext.currentLtaBlock} )</font>
							<div class="row">
								<div class="alert alert-info">
									Request(s) Remaining : <strong>${ltaBalance}</strong>
								</div>
								<c:if test="${found}">
									<div class="alert alert-warning">Showing saved info.</div>
								</c:if>
								<c:if test="${saved}">
									<div class="alert alert-success">Your request has been
										saved!</div>
								</c:if>
								<c:if test="${send}">
									<div class="alert alert-success">LTA Request Submitted!</div>
								</c:if>
							</div>
						</div>
						<div class="col-sm-6 col-md-3">
							<a href="/home/lta/new/viewHistory"><button
									id="btnViewHistory" class="btn blue darken-2 margin_top_25">View
									History</button></a>
						</div>
					</div>

					<div class="row">
						<c:if test="${error ne null}">
							<div class="alert alert-danger">
								<strong>Error: </strong> ${error}
							</div>

						</c:if>
						<c:if test="${message ne null}">
							<div class="alert alert-success">
								<strong>Success : </strong> ${message}
							</div>
						</c:if>
					</div>

					<div class="row">
						<div class="">

							<form:form id="formId" method="post"
								action="/home/ltaModule/new/save" modelAttribute="ltaEmployee"
								cssClass="form-horizontal">


								<input type="hidden" name="block"
									value="${appContext.currentLtaBlock}" />
								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<form:input id="originMain" name="originMain" type="text"
												path="origin" cssClass="form-control" required="true"
												disabled="${ltaInEligible}" />
											<label for="origin">Origin Of Journey</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<form:input id="destinationMain" name="destinationMain"
												type="text" path="destination1" cssClass="form-control"
												required="true" disabled="${ltaInEligible}" />
											<label for="destination1">Journey Destination</label>
										</div>

									</div>
								</div>

								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-12 padding_left_0">
											<form:input id="routeDescription" name="routeDescription"
												type="text" path="routeDescription" cssClass="form-control"
												required="true" disabled="${ltaInEligible}" />
											<label for="routeDescription">Short description of
												route used</label>
										</div>
									</div>
								</div>

								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<form:input id="periodFrom" name="periodFrom" type="text"
												path="periodFrom"
												cssClass="form-control datepicker lta_period"
												required="true" disabled="${ltaInEligible}" />
											<label for="periodFrom">Period From</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<form:input id="periodTill" name="periodTill" type="text"
												path="periodTill"
												cssClass="form-control datepicker lta_period"
												required="true" disabled="${ltaInEligible}" />
											<label for="periodTill">Period Till</label>
										</div>
									</div>
								</div>

								<table id="ltaIteneraryTable"
									class="table striped table-borderd dynamicTable">
									<thead>
										<tr>
											<th>Dependent</th>
											<th>Origin</th>
											<th>Destination</th>
											<th>Mode Of Transport</th>
											<th>Other Mode If Any</th>
											<th>Travel Class</th>
											<th>Cost of Fare</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${dependent eq null}">

												<tr>
													<td><select name="lTADep" id="lTADep">
															<c:forEach items="${dependents}" var="dep1">
																<option value="${dep1.dependentId}">${dep1.dependentName}</option>
															</c:forEach>
													</select></td>
													<td><input type="text" name="origin" id="origin"
														class="origin_dep" path="dept1.origin" /></td>
													<td><input type="text" name="destination"
														class="destination_dep" path="dept1.destination1"
														id="destination" /></td>

													<td><select name="modeOfTransport"
														id="modeOfTransport" class="check"
														path="dept1.modeOfTransport">
															<option value="Air">Air</option>
															<option value="Rail">Rail</option>
															<option value="Bus">Bus</option>
															<option value="Cab">Cab</option>
															<option value="Others">Others</option>

													</select></td>
													<td><input type="text" id="otherTransportMode"
														name="otherTransportMode" /> <label
														for="otherTransportMode">Unknown Mode Of Transport</label></td>
													<td><input type="text" id="travellingClass"
														name="travellingClass" /></td>
													<td><input type="number" name="costOfFare"
														id="costOfFare" class="cost_OfFare"
														onchange="onChangeBillAmount()" path="dept1.actualFare"
														required="true" /></td>

												</tr>
											</c:when>

											<c:otherwise>
												<c:forEach items="${dependent}" var="dept">
													<tr>

														<td><select name="lTADep" id="lTADep">
																<c:forEach items="${dependents}" var="dep">
																	<c:if
																		test="${dep.dependentId eq dept.dependent.dependentId}">
																		<option value="${dep.dependentId}" selected="selected">${dep.dependentName}</option>
																	</c:if>
																	<c:if
																		test="${dep.dependentId ne dept.dependent.dependentId}">
																		<option value="${dep.dependentId}">${dep.dependentName}</option>
																	</c:if>
																</c:forEach>
														</select></td>
														<td><input type="text" name="origin" id="origin"
															class="origin_dep" path="origin" value="${dept.origin}" /></td>
														<td><input type="text" name="destination"
															class="destination_dep" path="destination1"
															id="destination" value="${dept.destination}" /></td>

														<td><select name="modeOfTransport"
															id="modeOfTransport" class="check">
																<option value="Bus""${dept.modeOfTransport eq 'Bus' ? 'selected="selected"' : '' }">Bus</option>
																<option value="Air"
																	${dept.modeOfTransport eq 'Air' ? 'selected="selected"' : ''}>Air</option>
																<option value="Rail"
																	${dept.modeOfTransport eq 'Rail' ? 'selected="selected"' : ''}>Rail</option>
																<%-- <option value="Bus" "${dept.modeOfTransport eq  'Bus' ? 'selected="selected"' : '' }">Bus</option> --%>
																<option value="Cab"
																	${dept.modeOfTransport eq 'Cab' ? 'selected="selected"' : ''}>Cab</option>
																<option value="Others"
																	${dept.modeOfTransport ne 'Air' && dept.modeOfTransport ne 'Rail' && dept.modeOfTransport ne 'Bus' && dept.modeOfTransport ne 'Cab' ? 'selected="selected"' : ''}>Others</option>

														</select></td>

														<td><input type="text" id="otherTransportMode"
															name="otherTransportMode"
															value="${dept.modeOfTransport != 'Air' && dept.modeOfTransport != 'Rail' && dept.modeOfTransport !=  'Bus' && dept.modeOfTransport != 'Cab' ?  dept.modeOfTransport: ''}" />
															<label for="otherTransportMode">Unknown Mode Of
																Transport</label></td>
																<td><input type="text" id="travellingClass"
														name="travellingClass" value="${dept.travellingClass}"/></td>
														<td><input type="number" name="costOfFare"
															id="costOfFare" class="cost_OfFare"
															onchange="onChangeBillAmount()" path="dept.actualFare"
															value="${dept.fare}" /></td>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</tbody>




								</table>
								<input type="hidden" id="rowCount" name="rowCount" value="1" />
								<input type="hidden" id="flag" name="flag" path="flag" />
								<input type="hidden" id="lastRowBillAmount" />
								<input type="hidden" id="ltaEmployeeId" name="ltaEmployeeId"
									path="ltaEmployeeId" />
								<button type="button" id="btnAddRow" class="btn blue darken-2">Add
									Row</button>
								<button type="button" id="btnRemoveRow" class="btn red darken-1"
									onclick="removeLastRow()">Remove Last Row</button>

								<br />
								<br />
								<div class="form-group is-empty margin_top_25">
									<div class="md-form">
										<div class="col-md-12 padding_left_0">

											<form:input id="actualFare" name="actualFare" type="number"
												disabled="${ltaInEligible}" path="actualFare"
												required="true" placeholder="Actual Fare" />

											<label for="actualFare">Actual Fare</label>
										</div>
									</div>
								</div>
								<input type="hidden" name="depcount" id="depcount"
									class="depcount" value="0" />
								<div class="form-group is-empty">
									<div class="md-form">
										<div class="col-md-12 padding_left_0">
											<form:input id="shortestFare" name="shortestFare"
												type="number" path="shortestFare" required="true"
												disabled="${ltaInEligible}" />
											<label for="shortestFare">Shortest Fare</label>
										</div>
									</div>
								</div>
								<c:if test="${!ltaInEligible}">
									<div>
										<input type="submit" onclick="myFunction()" id="submit"
											class="btn btn-primary" value="Submit" name="submits"
											disabled /> <input type="submit" class="btn yellow darken-3"
											id="saveWithoutSubmit" value="save Without Submit"
											name="savewithoutsubmit" />
								</c:if>
						</div>

						<div class="form-group is-empty margin_top_25">
							<p>
								<font size="3" color="#41c4f4">${message}</font>
							</p>
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
	<script>
		Date_Formatting_After_Save();

		$('.datepicker').pickadate({
			selectMonths : true, // Creates a dropdown to control month
			selectYears : 15
		// Creates a dropdown of 15 years to control year
		});

		window.onload = function() {
			/* document.getElementById("origin").value = $('#originMain').val(); */
			console.log(document.getElementById("origin").value);
			document.getElementById("destination").value = $('#destinationMain')
					.val();
			/*  document.getElementById("costOfFare").value = $('#actualFare').val(); */
		}

		/* $(document).ready(function() {
		 $('#origin').val(origin);
		}   */

		/* function savelta() {
			var flag = 0;
			$('#flag').val(flag); 
		} */

		$(document).ready(function() {
			$('#submit').prop('disabled', false);
		});

		function myFunction() {
			alert("LTA request submitted successfully!");
		}

		/* function savewithsubmit() {
			var flag = 1; 
			$('#flag').val(flag);
		} 
		 */
		function onChangeBillAmount() {
			var totalReqAmt = 0;
			var currAmount = 0;
			$('.cost_OfFare').each(function() {
				currAmount = parseFloat($(this).val());
				totalReqAmt += currAmount;
			});
			$('#lastRowBillAmount').val(currAmount);
			$('#actualFare').val(totalReqAmt);
			//$('#totalClaimAmountCenter').html(totalReqAmt);
		}

		function removeLastRow() {
			var rowCount = $('#rowCount').val();
			var currAmount = parseFloat($('#costOfFare' + rowCount).val());
			var actualFare = parseFloat($('#actualFare').val()) - currAmount;
			$('#actualFare').val(actualFare);
		}

		/* changing date to our format after calling from controller after save the form*/
		function Date_Formatting_After_Save() {

			var rowcount = '${rowcount}';

			document.getElementById("rowCount").value = rowcount;
			/* to add the names of the dependendent details feilds into a dinamic manner */
			for (var i = 2; i <= rowcount; i++) {
				document.getElementById('lTADep').id = 'lTADep' + i;
				document.getElementById('origin').id = 'origin' + i;
				document.getElementById('destination').id = 'destination' + i;
				document.getElementById('modeOfTransport').id = 'modeOfTransport'
						+ i;
				document.getElementById('otherTransportMode').id = 'otherTransportMode'
						+ i;
				document.getElementById('travellingClass').id = 'travellingClass'
						+ i;
				document.getElementById('costOfFare').id = 'costOfFare' + i;
				document.getElementById('lTADep').name = 'lTADep' + i;
				document.getElementById('origin').name = 'origin' + i;
				document.getElementById('destination').name = 'destination' + i;
				document.getElementById('modeOfTransport').name = 'modeOfTransport'
						+ i;
				document.getElementById('otherTransportMode').name = 'otherTransportMode'
						+ i;
				document.getElementById('costOfFare').name = 'costOfFare' + i;
				document.getElementById('travellingClass').name = 'travellingClass'
						+ i;

			}

			if (document.getElementById('periodFrom').value != ''
					|| document.getElementById('periodTill').value != '') {
				const monthNames = [ "January", "February", "March", "April",
						"May", "June", "July", "August", "September",
						"October", "November", "December" ];
				console.log("Inside fuction");
				var periodF = '${jsPeriodFrom}';
				var periodFrom = Number(periodF);
				console.log(periodFrom);
				var date1 = new Date(periodFrom);
				var periodT = '${jsPeriodTill}';
				var periodTill = Number(periodT);
				console.log(periodTill);
				var date2 = new Date(periodTill);
				var fromDate = date1.getDate();
				var tillDate = date2.getDate();
				var fromMonth = monthNames[date1.getMonth()];
				var tillMonth = monthNames[date2.getMonth()];
				var fromYear = date1.getFullYear();
				var tillYear = date2.getFullYear();
				document.getElementById("periodFrom").value = fromDate + ' '
						+ fromMonth + ', ' + fromYear;
				document.getElementById("periodTill").value = tillDate + ' '
						+ tillMonth + ', ' + tillYear;
				console.log("Fuction exit");
				/* correctDateFrom(date1,fromDate,fromMonth,fromYear);
				correctDateTill(date2,tillDate,tillMonth,tillYear); */
				/* onChangeBillAmount() */

				$('.datepicker').pickadate({
					selectMonths : true, // Creates a dropdown to control month
					selectYears : 15
				// Creates a dropdown of 15 years to control year
				});
			}

		}
	</script>
	<style>
::placeholder {
	color: grey;
	opacity: 1;
}
</style>
</body>
</html>