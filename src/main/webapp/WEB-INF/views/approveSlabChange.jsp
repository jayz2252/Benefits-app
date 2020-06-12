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
							<h4 class="h4-responsive">${pfEmployee.employee.firstName}
								${pfEmployee.employee.lastName} - PF Details</h4>
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
									<td colspan="3">${pfEmployee.employee.firstName}
										${pfEmployee.employee.lastName}</td>
									<th>Employee Code</th>
									<td colspan="3">${pfEmployee.employee.employeeCode}</td>
								</tr>

								<tr>
									<th>Gender</th>
									<td colspan="3"><c:choose>
											<c:when test="${pfEmployee.formGender eq 'M'}">Male</c:when>
											<c:when test="${pfEmployee.formGender eq 'F'}">Female</c:when>
										</c:choose></td>
									<th>DOB</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${pfEmployee.formDOB}" /></td>
								</tr>

								<tr>
									<th>Marital Status</th>
									<td colspan="3"><c:choose>
											<c:when test="${pfEmployee.formMaritalStatus eq 'S'}">Single</c:when>
											<c:when test="${pfEmployee.formMaritalStatus eq 'M'}">Married</c:when>
											<c:when
												test="${pfEmployee.formMaritalStatus eq 'W' && pfEmployee.formGender eq 'M'}">Widower</c:when>
											<c:when
												test="${pfEmployee.formMaritalStatus eq 'W' && pfEmployee.formGender eq 'F'}">Widow</c:when>
										</c:choose></td>
									<th>Guardian Name</th>
									<td colspan="3">${pfEmployee.formGuardianName}</td>
								</tr>

								<tr>
									<th>Permanent Address</th>
									<td colspan="3">${pfEmployee.formPermanentAddress}</td>
									<th>Temporary Address</th>
									<td colspan="3">${pfEmployee.formCurrentAddress}</td>
								</tr>

								<tr>
									<th>Mobile No</th>
									<td colspan="3">${pfEmployee.formMobile}</td>
									<th>Email Id</th>
									<td colspan="3">${pfEmployee.formEmail}</td>
								</tr>

								<tr>
									<th>Aadhar No</th>
									<td colspan="3">${pfEmployee.formAadharNo}</td>
									<th>Pan No</th>
									<td colspan="3">${pfEmployee.formPan}</td>
								</tr>

								<tr>
									<th>Current DOJ</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${pfEmployee.currentDOJ}" /></td>
									<th>Current DOL</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${pfEmployee.currentDOL}" /></td>
								</tr>
							</table>

							<div class="col-sm-6 col-md-9 py-1 px-1">
								<h4 class="h4-responsive">Current PF Details</h4>
							</div>

							<table class="table striped table-borderd">
								<tr>
									<th>PF Account No</th>
									<td colspan="3">${pfEmployee.pfAcNo}</td>
									<th>UAN</th>
									<td colspan="3">${pfEmployee.uan}</td>
								</tr>

								<tr>
									<th>Opted Slab</th>
									<td colspan="3"><c:choose>
											<c:when test="${pfEmployee.optedSlab eq 'VRBL'}">Variable</c:when>
											<c:when test="${pfEmployee.optedSlab eq 'FIXD'}">Fixed</c:when>
											<c:when test="${pfEmployee.optedSlab eq 'MDTRY'}">Mandatory</c:when>
										</c:choose></td>
									<th>Opted Date</th>
									<td colspan="3">${pfEmployee.optedDate}</td>
								</tr>

								<tr>
								`	<th>Voluntary Amount</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${pfEmployee.formVoluntaryPF}" /></td>
									
									<th>Effective From</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${pfEmployee.effFrom}" /></td>
									
								</tr>
							</table>

							<div class="col-sm-6 col-md-9 py-1 px-1">
								<h4 class="h4-responsive">Previous PF Details</h4>
							</div>

							<table class="table striped table-borderd">
								<tr>
									<th>PF No</th>
									<td colspan="3">${pfEmployee.formPrevPfAccNo}</td>
								</tr>

								<tr>
									<th>Estabishment Address</th>
									<td colspan="3">${pfEmployee.formPrevEstablishment}</td>
								</tr>

								<tr>
									<th>Date of Joining</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${pfEmployee.formPrevDOJ}" /></td>
								</tr>

								<tr>
									<th>Date of Leaving</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${pfEmployee.formPrevDOL}" /></td>
								</tr>
							</table>

							<div class="col-sm-6 col-md-9 py-1 px-1">
								<h4 class="h4-responsive">Slab History Details</h4>
							</div>

							<table class="table striped table-borderd">
								<tr>
									<th>STATUS</th>
									<th>Slab Changed To</th>
									<th>Slab From</th>
									<th>Slab To</th>
								</tr>

								<c:forEach items="${slabHistory}" var="slabHistory">
									<tr>
									<td><c:choose>
									<c:when test="${slabHistory.status eq 'HR_APPR'}">Approved</c:when>
									<c:when test="${slabHistory.status eq 'SL_SUBM'}">Slab Change Submitted</c:when>
									<c:when test="${slabHistory.status eq 'HR_RJCT'}">Rejected</c:when>
			<c:when test="${slabHistory.status eq 'SUBM'}">Submitted</c:when>
									</c:choose></td>
									
									
										<td><c:choose>
										<c:when test="${slabHistory.changedEntity eq 'Initial'}">Initial</c:when>
										<c:when test="${slabHistory.changedEntity eq 'VOL_PF'}">Voluntary amount change</c:when>
										<c:when test="${slabHistory.changedEntity eq 'SLAB'}">Slab Change</c:when>
										
										</c:choose>
										</td>
										<td>
										<c:choose>
										<c:when test="${slabHistory.entityFrom eq 'FIXD'}">Fixed</c:when>
										<c:when test="${slabHistory.entityFrom eq 'VRBL'}">Variable</c:when>
										<c:when test="${slabHistory.entityFrom eq 'MDTRY'}">Mandatory</c:when>
										<c:otherwise>${slabHistory.entityFrom}</c:otherwise>
										</c:choose>
										</td>
										<td><c:choose>
										<c:when test="${slabHistory.entityTo eq 'FIXD'}">Fixed</c:when>
										<c:when test="${slabHistory.entityTo eq 'VRBL'}">Variable</c:when>
										<c:when test="${slabHistory.entityTo eq 'MDTRY'}">Mandatory</c:when>
										<c:otherwise>${slabHistory.entityTo}</c:otherwise>
										</c:choose></td>
									</tr>
								</c:forEach>
							</table>


							<a class="btn yellow darken-3"
								href="<%=request.getContextPath()%>/home/controlPanel/pf/changeSlabSearch">Back</a>

							<a class="btn green darken-3"
								href="<%=request.getContextPath() %>/home/controlPanel/pf/changeSlabSearch/approve/${pfEmployee.pfEmployeeId}">Approve</a>

							<a class="btn red darken-3"
								href="<%=request.getContextPath() %>/home/controlPanel/pf/changeSlabSearch/reject/${pfEmployee.pfEmployeeId}">Reject</a>

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