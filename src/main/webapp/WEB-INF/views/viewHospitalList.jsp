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
							<h4 class="h4-responsive">Hospital List</h4>
							</br>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<table class="table">
							<thead>
								<tr>
									<th>Hospital Name</th>
									<th>State</th>
									<th>City</th>
									<th>Address</th>
									<th>Phone Number</th>
									<th>Email</th>
									<th>Logo</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${hospitals}" var="hp">
									<tr>

										<td>${hp.hospitalName}</td>
										<td>${hp.state}</td>
										<td>${hp.city}</td>
										<td>${hp.address}</td>
										<td>${hp.phoneNo}</td>
										<td>${hp.email}</td>
										<td>${hp.logo}</td>
										<td><a
											href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewhospital/edit/${hp.hospitalId}"><i
												class="fa fa-pencil" aria-hidden="true"></i></a></td>
										<td><a
											href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewhospital/delete/${hp.hospitalId}"><i
												class="fa fa-trash-o" aria-hidden="true"></i></a></td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
						<a class="btn btn-primary"
							href="<%=request.getContextPath()%>/home/controlPanel/masterData/addHospital" />Add
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

