<!--
Admin Control Panel
author : vineesh.vijayan
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
			<%@include file="adminLeftNav.jsp"%>
			<section id="content" class="">
			<div class="row">
				<div class="col-md-12 white">

					<div class="row">
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">Control Panel Dashboard</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<div class="col s12 m6 l4">
								<div class="card blue-grey darken-1">
									<div class="card-content white-text">
										<span class="card-title">Flexi Benefit Plans</span>
									</div>
									<div class="card-action">
										<a href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans">View All Plans</a>
									</div>
								</div>
							</div>
							<div class="col s12 m6 l4">
								<div class="card blue-grey darken-1">
									<div class="card-content white-text">
										<span class="card-title">Group Medical Insurance Plans</span>
									</div>
									<div class="card-action">
										<a href="#">View Claim Requests</a>
									</div>
								</div>
							</div>
							<div class="col s12 m6 l4">
								<div class="card blue-grey darken-1">
									<div class="card-content white-text">
										<span class="card-title">Income Tax Declaration</span>
									</div>
									<div class="card-action">
										<a href="#">Customize IT Declaration</a>
									</div>
								</div>
							</div>
							<div class="col s12 m6 l4">
								<div class="card blue-grey darken-1">
									<div class="card-content white-text">
										<span class="card-title">Income Tax Declaration</span>
									</div>
									<div class="card-action">
										<a href="#">Update IT Slabs</a>
									</div>
								</div>
							</div>
							<div class="col s12 m6 l4">
								<div class="card blue-grey darken-1">
									<div class="card-content white-text">
										<span class="card-title">Reports</span>
									</div>
									<div class="card-action">
										<a href="#">View All Employees</a>
									</div>
								</div>
							</div>
							<div class="col s12 m6 l4">
								<div class="card blue-grey darken-1">
									<div class="card-content white-text">
										<span class="card-title">Reports</span>
									</div>
									<div class="card-action">
										<a href="#">Flexi Plans vs Employees</a>
									</div>
								</div>
							</div>
							<div class="col s12 m6 l4">
								<div class="card blue-grey darken-1">
									<div class="card-content white-text">
										<span class="card-title">Reports</span>
									</div>
									<div class="card-action">
										<a href="#">Insurance Plan vs Employees</a>
									</div>
								</div>
							</div>
							<div class="col s12 m6 l4">
								<div class="card blue-grey darken-1">
									<div class="card-content white-text">
										<span class="card-title">Settings</span>
									</div>
									<div class="card-action">
										<a href="#">Change Master Data</a>
									</div>
								</div>
							</div>
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