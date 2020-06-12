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
							<h4 class="h4-responsive">PF Opted Out Employees</h4>
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
									<th>Employee Name</th>
									<th>Employee Code</th>
									<th>opted Out Date</th>
									<th>Fiscal Year</th>
									<th>Status</th>
									<th>Reason</th>
						
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pfDeniedEmployees}" var="pfDeniedEmployees">
									<tr>
										<td>${pfDeniedEmployees.employee.firstName} ${pfDeniedEmployees.employee.lastName}</td>
										<td>${pfDeniedEmployees.employee.employeeCode}</td>
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfDeniedEmployees.updatedDate}" /></td>
										<td>${pfDeniedEmployees.fiscalYear}</td>
										<td>Opted Out</td>
										<td><c:choose>
										<c:when test="${pfDeniedEmployees.reasonId eq null}">
										${pfDeniedEmployees.otherReasons}</c:when>
										<c:otherwise>
										${pfDeniedEmployees.reasonId.reasonDescription}
										</c:otherwise>
										</c:choose></td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
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