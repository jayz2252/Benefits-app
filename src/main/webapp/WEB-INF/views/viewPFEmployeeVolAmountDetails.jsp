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
							<h4 class="h4-responsive">${slabHistory.pfEmployee.employee.firstName}
								${slabHistory.pfEmployee.employee.lastName} - PF Details</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>

					<div class="col-sm-6 col-md-9 py-1 px-1">
						<h4 class="h4-responsive">Employee Details</h4>
					</div>

					<div class="row">
						<div class="col-md-12">
							<table class="table striped table-borderd">
								<tr>
									<th>Employee Name</th>
									<td colspan="3">${slabHistory.pfEmployee.employee.firstName}
										${slabHistory.pfEmployee.employee.lastName}</td>
									<th>Employee Code</th>
									<td colspan="3">${slabHistory.pfEmployee.employee.employeeCode}</td>
								</tr>

								<tr>
									<th>Gender</th>
									<td colspan="3"><c:choose>
												<c:when test="${slabHistory.pfEmployee.formGender eq 'M'}">MALE</c:when>
												<c:when test="${slabHistory.pfEmployee.formGender eq 'F'}">FEMALE</c:when>

									</c:choose></td>
									<th>DOB</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${slabHistory.pfEmployee.formDOB}"/></td>
								</tr>

								<tr>
									<th>Marital Status</th>
									<td colspan="3">
									<c:choose>
												<c:when test="${slabHistory.pfEmployee.formMaritalStatus eq 'M'}">MARRIED</c:when>
												<c:when test="${slabHistory.pfEmployee.formMaritalStatus eq 'W'}">WIDOW</c:when>
												<c:when test="${slabHistory.pfEmployee.formMaritalStatus eq 'S'}">SINGLE</c:when>
				
									</c:choose>
									</td>
									<th>Guardian Name</th>
									<td colspan="3">${slabHistory.pfEmployee.formGuardianName}</td>
								</tr>

								<tr>
									<th>Permanent Address</th>
									<td colspan="3">${slabHistory.pfEmployee.formPermanentAddress}</td>
									<th>Temporary Address</th>
									<td colspan="3">${slabHistory.pfEmployee.formCurrentAddress}</td>
								</tr>

								<tr>
									<th>Mobile No</th>
									<td colspan="3">${slabHistory.pfEmployee.formMobile}</td>
									<th>Email Id</th>
									<td colspan="3">${slabHistory.pfEmployee.formEmail}</td>
								</tr>

								<tr>
									<th>Aadhar No</th>
									<td colspan="3">${slabHistory.pfEmployee.formAadharNo}</td>
									<th>Pan No</th>
									<td colspan="3">${slabHistory.pfEmployee.formPan}</td>
								</tr>

								<tr>
									<th>Current DOJ</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${slabHistory.pfEmployee.currentDOJ}" /></td>
									<th>Current DOL</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${slabHistory.pfEmployee.currentDOL}" /></td>
								</tr>
							</table>


							<div class="col-sm-6 col-md-9 py-1 px-1">
								<h4 class="h4-responsive">Voluntary Change Details</h4>
							</div>
							
							<table class="table striped table-borderd">
								<tr>
									<th>Current VPF</th>
									<td colspan="3">${slabHistory.entityFrom}</td>
									<th>Requested VPF</th>
									<td colspan="3">${slabHistory.entityTo}</td>
								</tr>
								</table>

							<div class="col-sm-6 col-md-9 py-1 px-1">
								<h4 class="h4-responsive">Current PF Details</h4>
							</div>

							<table class="table striped table-borderd">
								<tr>
									<th>PF Account No</th>
									<td colspan="3">${slabHistory.pfEmployee.pfAcNo}</td>
									<th>UAN</th>
									<td colspan="3">${slabHistory.pfEmployee.uan}</td>
								</tr>

								<tr>
									<th>Opted Slab</th>
									<td colspan="3"><c:choose>
												<c:when test="${slabHistory.pfEmployee.optedSlab eq 'FIXD'}">FIXED</c:when>
												<c:when test="${slabHistory.pfEmployee.optedSlab eq 'VRBL'}">VARIABLE</c:when>
												<c:when test="${slabHistory.pfEmployee.optedSlab eq 'MDTRY'}">MANDATORY</c:when>
												
									</c:choose></td>
									<th>Opted Date</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${slabHistory.pfEmployee.optedDate}"/></td>
								</tr>

								<tr>
									<th>Effective From</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${slabHistory.pfEmployee.effFrom}" /></td>
									<th>Effective Till</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${slabHistory.pfEmployee.effTill}" /></td>
								</tr>
							</table>

							<div class="col-sm-6 col-md-9 py-1 px-1">
								<h4 class="h4-responsive">Previous PF Details</h4>
							</div>

							<table class="table striped table-borderd">
								<tr>
									<th>PF No</th>
									<td colspan="3">${slabHistory.pfEmployee.formPrevPfAccNo}</td>
								</tr>

								<tr>
									<th>Estabishment Address</th>
									<td colspan="3">${slabHistory.pfEmployee.formPrevEstablishment}</td>
								</tr>

								<tr>
									<th>Date of Joining</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${slabHistory.pfEmployee.formPrevDOJ}" /></td>
								</tr>

								<tr>
									<th>Date of Leaving</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${slabHistory.pfEmployee.formPrevDOL}" /></td>
								</tr>
							</table>


							<a class="btn yellow darken-3"
									href="<%=request.getContextPath() %>/home/controlPanel/pf/changeVolAmountSearch"
									>Back</a>
									
							<a class="btn green darken-3"
								href="<%=request.getContextPath() %>/home/controlPanel/pf/search/volAmountApprove/${slabHistory.pfEmpSlabHistoryId}"
								>Approve</a> 
								
							<a class="btn red darken-3"
								href="<%=request.getContextPath() %>/home/controlPanel/volAmountApprove/reject/${slabHistory.pfEmpSlabHistoryId}"
								>Reject</a>  
							

													

						</div>
					</div>

				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
</body>
</html>