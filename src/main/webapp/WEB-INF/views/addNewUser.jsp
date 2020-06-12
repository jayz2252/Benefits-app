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
							<h4 class="h4-responsive">Add New User</h4>
						</div>
						<div class="col-sm-6 col-md-3"></div>
					</div>

					<c:if test="${not empty ErrorMessage}">
						<div class="alert alert-danger">
							<strong>${ErrorMessage}</strong>
						</div>
					</c:if>

					<div class="row">
						<div class="col-md-12">

							<form action="searchUser" id="editPlanForm" method="post"
								cssClass="form-horizontal">
								<div class="form-group is-empty">

									<div class="md-form">
										<div class="col-md-12 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<input id="empCode" name="empCode" type="text"
												cssClass="form-control" required="true" /> <label
												for="empCode">Employee Code</label>
										</div>

									</div>
								</div>

								<div class="form-group is-empty"></div>
								<div class="form-group">
									<div class="col-md-6">
										<input type="submit" class="btn btn-primary" value="Search" />
									</div>
								</div>
							</form>
						</div>
					</div>
					<c:if test="${not empty employee}">
						<table class="table">
							<thead>
								<tr>
									<th>Employee Code</th>
									<th>Name</th>
									<th>Department Name</th>
									<th>Email</th>
								</tr>
							</thead>
							<tbody>


								<tr>

									<td>${employee.employeeCode}</td>
									<td>${employee.firstName}${employee.middleName}
										${employee.lastName}</td>
									<td>${employee.departmentName}</td>
									<td>${employee.email}</td>

								</tr>


							</tbody>
						</table>
					</c:if>


					<div class="row">
						<div class="col-md-12">

							<form action="addNewUser" id="editPlanForm" method="post"
								cssClass="form-horizontal">
								<div class="form-group is-empty">

									<div class="md-form">
										<div class="col-md-12 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<input id="userName" name="userName" type="text"
												cssClass="form-control" required="true"
												value="${employee.userName}" readonly="readonly"
												style="color: black;" /> <label for="planName">User
												Name</label>
										</div>

									</div>
								</div>
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<select id="role" name="role" class="role">

													<option value="SYS_ADMIN">System Admin</option>
													<option value="HR_USER">HR</option>
													<option value="FINANCE_USER">FINANCE</option>

												</select> <label for="flexiPlan">Select User Role</label>
											</div>


										</div>
									</div>

								</div>
								<div class="form-group is-empty"></div>
								<div class="form-group">
									<div class="col-md-6">
										<input type="submit" class="btn btn-primary" value="Add User" />
									</div>
								</div>
							</form>
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