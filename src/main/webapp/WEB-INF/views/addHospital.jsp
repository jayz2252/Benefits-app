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
			<div class="white col-md-12">
			<div class="row">


				<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">Add Hospital </h4></br>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>
						<form:form id="editHospitalForm" method="POST" action="saveHospital"
							modelAttribute="hospitalBean" cssClass="form-horizontal">
							<div class="form-group is-empty">

								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<!-- <input  id="name" class="form-control">-->
										<form:input id="hospitalName" name="hospitalName" type="text"
											path="hospitalName" cssClass="form-control" required="true" />
										<label for="haspitalName">Hospital Name</label>
									</div>
									
								<div class="col-md-6 padding_left_0">
									<!-- <input  id="name" class="form-control">-->
									<form:input id="state" name="state" type="text" path="state"
										cssClass="form-control" />
									<label for="state">State</label>
								</div>
							</div>
							</div>
							<div class="form-group is-empty">


								<div class="md-form">
									<div class="col-md-6 padding_left_0">
										<!-- <input  id="name" class="form-control">-->
										<form:input id="city" name="city" type="text" path="city"
											cssClass="form-control" required="true" />
										<label for="city">City</label>
									</div>
									
								<div class="col-md-6 padding_left_0">
									<form:input id="address" name="address" type="text"
										path="address" cssClass="form-control" required="true" />
									<label for="address">Address</label>
								</div>
							</div>
							</div>
							<div class="md-form">
								<div class="col-md-6 padding_left_0">
									<form:input id="phoneNo" name="phoneNo" type="text"
										path="phoneNo" cssClass="form-control" required="true" />
									<label for="phoneNo">Phone No</label>
								</div>

								<div class="col-md-6 padding_left_0">
									<form:input id="email" name="email" type="text" path="email"
										cssClass="form-control" required="true" />
									<label for="email">Email</label>
								</div>
							</div>
							
							<div class="form-group">
								<div class="">
										<a  class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewhospital"/>Cancel</a>
									<input type="submit" class="btn btn-primary" value="Save" />
								</div>
							</div>
							<form:hidden path="hospitalId" />
						</form:form>
					</div>
					
				</div>
			</div>
			
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>

</body>
</html>