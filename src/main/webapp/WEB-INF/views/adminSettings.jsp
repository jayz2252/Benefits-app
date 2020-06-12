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
							<h4 class="h4-responsive">Admin Settings</h4>
							</br>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>
					<div class="col-md-12 select_block">
						<table class="table data_table">
							<thead>
								<tr>
									<th>User Name</th>
									<th>Role</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${plans}" var="plan">
									<tr>
										<td>${plan.userName}</td>
										<c:choose>
											<c:when test="${plan.role eq 'SYS_ADMIN'}">
												<td>System Admin</td>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${plan.role eq 'HR_USER'}">
														<td>HR</td>
													</c:when>
													<c:otherwise>
														<td>Finance</td>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										<td><a
											href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/deleteUser/${plan.userRoleId}">
												<i class="fa fa-trash-o" data-toggle="tooltip"
												data-placement="left" title="Delete User"></i>
										</a></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
						<a class="btn btn-primary"
							href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/addNewUser" />Add
						New</a>
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

