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
						<c:choose>
							<c:when test="${pfEmployee.status eq 'HR_APPR'}">Status  :  <font size="3" color="green">APPROVED</font></c:when>
							<c:when test="${pfEmployee.status eq 'HR_RJCT'}">Status  :  <font size="3" color="red">REJECTED</font></c:when>
						</c:choose>
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
											<c:when test="${pfEmployee.formMaritalStatus eq 'M'}">Married</c:when>
											<c:when test="${pfEmployee.formMaritalStatus eq 'S'}">Single</c:when>
											<c:when
												test="${pfEmployee.formGender eq 'M' && pfEmployee.formMaritalStatus eq 'W'}">Widower</c:when>
											<c:when
												test="${pfEmployee.formGender eq 'F' && pfEmployee.formMaritalStatus eq 'W'}">Widow</c:when>
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
									<th>Personal Email</th>
									<td colspan="3">${pfEmployee.formEmail}</td>
								</tr>

								<tr>
									<th>Aadhar No</th>
									<td colspan="3">${pfEmployee.formAadharNo}</td>
									<th>Pan No</th>
									<td colspan="3">${pfEmployee.formPan}</td>
								</tr>
								<tr>
								<th>Aadhar Card </th>
								<td>
								<a href="${downloadUrl}" target="_blank"
											class="doc_upload_link" id="download"
											uuid="${document.docManUuid}"
											mandatory="${document.mandatory}" clicked="false"><i
												class="fa fa-download" aria-hidden="true" title="Aadhar Card"></i></a>
								</td>
								</tr>

							<tr>
								<td><h4 class="h4-responsive">Current PF Details</h4></td>
							</tr>							
								<tr>
									<th>PF Account No</th>
									<td colspan="3">${pfEmployee.pfAcNo}</td>
									<th>UAN</th>
									<td colspan="3">${pfEmployee.uan}</td>
								</tr>

								<tr>
									<th>Opted Slab</th>
									<td colspan="3"><c:choose>
											<c:when test="${pfEmployee.optedSlab eq 'FIXD'}">Fixed Slab</c:when>
											<c:when test="${pfEmployee.optedSlab eq 'VRBL'}">Variable Slab</c:when>
											<c:when test="${pfEmployee.optedSlab eq 'MDTRY'}">Mandatory Slab</c:when>
										</c:choose></td>
									<th>Opted Date</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${pfEmployee.optedDate}"/></td>
								</tr>

								<tr>
									<th>Effective From</th>
									<td colspan="3"><fmt:formatDate pattern="dd-MMMM-yyyy"
											value="${pfEmployee.effFrom}" /></td>
									<th>Voluntary Amount</th>
									<td colspan="3">${pfEmployee.formVoluntaryPF}</td>
								</tr>
							

							<tr>
								<td><h4 class="h4-responsive">Previous PF Details</h4></td>
							</tr>						
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

							<a class="btn yellow darken-3"
								href="<%=request.getContextPath()%>/home/myEpfHome">Back</a>

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