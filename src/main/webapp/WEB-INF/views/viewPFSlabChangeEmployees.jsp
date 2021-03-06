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
							<h4 class="h4-responsive">PF Slab Change Request</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<table
							class="tablePlanEmployees table striped table-borderd data_table">
							<thead>
								<tr>
									<th>

										<div class="remember-label">
											<div>
												<input type="checkbox" id="selectAll" name="selectAll"
													value="${pfEmployee.pfEmployeeId}" /> <label
													for="selectAll"></label>
											</div>
										</div>
									</th>
									<th>Employee Name</th>
									<th>Employee Code</th>
									<th>Enrolled Date</th>
									<th>Effective From</th>
									<th>Eff till</th>
									<th>Status</th>
									<th>Action</th>
									<th>Report</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pfEmployees}" var="pfEmployee">
									<tr>
										<td><c:if test="${pfEmployee.status eq 'SL_SUBM'}">
												<div class="remember-label">
													<div>
														<input class="chk" type="checkbox"
															id="selectPF${pfEmployee.pfEmployeeId}" name="selectPF"
															value="${pfEmployee.pfEmployeeId}" /> 
															<label for="selectPF${pfEmployee.pfEmployeeId}"></label>
													</div>
												</div>
											</c:if></td>
										<td><a
											href="<%=request.getContextPath()%>/home/controlPanel/pf/changeSlabSearch/viewSlabDetails/${pfEmployee.pfEmployeeId}">${pfEmployee.employee.firstName}
												${pfEmployee.employee.lastName}</a></td>
										<td>${pfEmployee.employee.employeeCode}</td>
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfEmployee.optedDate}" /></td>
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfEmployee.effFrom}"/></td>
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfEmployee.effTill}" /></td>
										<td><c:choose>
												<c:when test="${pfEmployee.status eq 'SL_SUBM'}">Slab Change Requested</c:when>
												<%-- <c:when test="${pfEmployee.status eq 'HR_APPR'}">Approved</c:when>
												<c:otherwise>Rejected</c:otherwise> --%>
											</c:choose></td>
										<td><c:if test="${pfEmployee.status eq 'SL_SUBM'}">
												<a
													href="<%=request.getContextPath()%>/home/controlPanel/pf/changeSlabSearch/approvalView/${pfEmployee.pfEmployeeId}">
													Approve</a>
											</c:if></td>
										<td><a
											href="<%=request.getContextPath()%>/home/controlPanel/pf/reportDownload/${pfEmployee.pfEmployeeId}">
												<i class="fa fa-file-pdf-o" aria-hidden="true"></i>
										</a></td>

									</tr>
								</c:forEach>
							</tbody>
						</table>

						<form id="approveHiddenId"
							action="/home/controlPanel/pf/changeSlabSearch/approveSelected">
							<div class="form-group">

								<div class="col-md-6">
									<input type="hidden" id="approveSelected" value=""
										name="approveSelected"> <input type="submit"
										class="btn btn-primary approveClass" id="approve"
										name="approve" value="Approve Selected" />

								</div>
							</div>
						</form>

					</div>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
		function Populate() {
			vals = $('.chk:checked').map(function() {
				return this.value;
			}).get().join(',');
			console.log(vals);
			$('#approveSelected').val(vals);

			if (vals != '') {
				$('#approve').prop("disabled", false);
			} else {
				$('#approve').prop("disabled", true);
			}
		}

		$('input[type="checkbox"]').on('change', function() {
			Populate()
		}).change();

		$('#approve').prop("disabled", true);
		$('.chk').click(function() {
			if ($(this).is(':checked')) {
				$('#approve').prop("disabled", false);
			} else {
				if ($('.chk').filter(':checked').length < 1) {
					$('#approve').attr('disabled', true);
				}
			}
		});

		$("#selectAll").click(function() {
			$('input:checkbox').not(this).prop('checked', this.checked);
		});
	</script>

</body>
</html>