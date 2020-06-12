<!-- 
author : swathy.raghu
This page will provides employee search option
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
			<section id="content" class="">
			<div class="row">
				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">Leave Travel Allowance (LTA) - Financial Year 2018-19
								</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>

					<c:if test="${message ne null}">
						<div class="alert alert-success">
							<strong>Success : </strong> ${message}
						</div>
					</c:if>

					<div class="row">
						<div class="col-md-12">
							<c:set var="count" value="1" scope="page" />
							<c:choose>
								<c:when test="${ltaRequests ne null and ltaRequests.size() > 0}">
									<c:forEach items="${ltaRequests}" var="bean">
										<div class="margin_top_25">
											<h5 class="h5-responsive">
												<div class="col-md-11">
													<font color="#41c4f4">LTA Request-${count}</font>
												</div>
												<div class="col-md-1" align="right">
												<a
											href="<%=request.getContextPath()%>/home/controlPanel/lta/search/download/${bean.ltaEmployee.ltaEmployeeId}">
												<i class="fa fa-file-pdf-o" aria-hidden="true"></i>
													<c:if test="${bean.ltaEmployee.status eq 'SAVE' ||bean.ltaEmployee.status eq 'SUBM' || bean.ltaEmployee.status eq 'OPEN'}">
														<a href="#deleteLtaConfirmModal" id="deleteLta" ltaId="${bean.ltaEmployee.ltaEmployeeId}"><i
															class="fa fa-trash" aria-hidden="true"
															style="color: #bf0b4d"></i></a>
													</c:if>
												</div>
											</h5>

											<table class="table striped table-borderd">
												<tr>
													<th>Employee Name</th>
													<td>${bean.ltaEmployee.employee.firstName} ${bean.ltaEmployee.employee.lastName}</td>
													<th>Designation</th>
													<td>${bean.ltaEmployee.employee.designationName}</td>
												</tr>

												<tr>
													<th>Origin of Travel</th>
													<td>${bean.ltaEmployee.origin}</td>
													<th>Destination</th>
													<td>${bean.ltaEmployee.destination1}</td>
												</tr>

												<tr>
													<th>Actual Fare</th>
													<td>${bean.ltaEmployee.actualFare}</td>
													<th>Shortest Fare</th>
													<td>${bean.ltaEmployee.shortestFare}</td>
												</tr>
												<tr>
													<th>Status</th>
													<td><c:choose>
															<c:when test="${bean.ltaEmployee.status eq 'FIN_APPR'}">
													Approved
													</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${bean.ltaEmployee.status eq 'SAVE'}">
													Saved
													</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when
																				test="${bean.ltaEmployee.status eq 'HR_APPR'}">
													Awaiting Finance Approval
													</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when
																						test="${bean.ltaEmployee.status eq 'HR_RJCT' or 'FIN_RJCT'}">
													Rejected
													</c:when>

																					<c:otherwise>
													Submitted
													
													</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>

																</c:choose>
															</c:otherwise>
														</c:choose></td>
													<th>Approved Amount</th>
													<c:choose>
														<c:when test="${bean.ltaEmployee.status ne 'SUBM'}">
															<td>${bean.ltaEmployee.approvedAmt }</td>
														</c:when>

													</c:choose>

												</tr>

												<table
													class="tablePlanEmployees table striped table-borderd">
													<thead>
														<tr>

															<th>Dependent Name</th>
															<th>Relationship</th>
															<th>Destination</th>
															<th>Class OfTravel</th>


															<th>Cost Of Fare</th>

														</tr>
													</thead>
													<tbody>
														<c:forEach items="${bean.dependents}" var="dependent">
															<tr>

																<td>${dependent.dependent.dependentName}</td>
																<td>${dependent.dependent.relationship}</td>
																<td>${dependent.destination}</td>
																<td>${dependent.travellingClass}</td>
																<td>${dependent.fare}</td>

															</tr>
														</c:forEach>
													</tbody>
												</table>
											</table>
										</div>
										<c:set var="count" value="${count + 1}" scope="page" />
										</br>
									</c:forEach>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>"/>
						</div>
					</div>
				</div>
			</div>
			</section>
		</div>
	</div>
	<div id="deleteLtaConfirmModal" class="modal modal-fixed-footer"
		style="height: 200px;">

		<div class="modal-content">
			<h4 style="color: #08A9C1;">Confirm Delete</h4>
			<p>Are you sure you want to delete this LTA request?</p>
		</div>

		<div class="modal-footer">
		<a href="#"
				class="waves-effect waves-red btn-flat btn-primary modal-close">No</a>
			<a href="" id="deleteLtaYesButton"
				class="waves-effect waves-red btn-flat btn-primary">Yes</a>
			
		</div>

	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
		$(document).ready(function() {
			$('select').material_select();
			
			$("#deleteLta").on("click", function(){
				var ltaId = $(this).attr('ltaId');
				var contextPath = $("#contextPath").val();
				
				$("#deleteLtaYesButton").attr("href",contextPath + "/home/lta/remove/"+ltaId);
				
			});
		});
	</script>

</body>
</html>
​
