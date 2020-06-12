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
			<%@include file="notificationHomePage.jsp"%>

			<section id="content" class="">
			<div class="row">
				<div class="white col-md-12">
					<div class="row">

						<div class="col s12 m6 l4">
							<div class="card blue-grey darken-1 block_main">
								<i class="fa fa-medkit fa-6" aria-hidden="true"></i>
								<div class="card-content white-text">
									<span class="card-title">Insurance Helpdesk</span>
									<p>
										<strong>Email : </strong>insurance-desk@speridian.com
									</p>
									<p>
										<strong>Phone : </strong>+91 859393 1293
									</p>
								</div>

							</div>
						</div>

						<div class="col s12 m6 l4">
							<div class="card blue-grey darken-1 block_main">
								<i class="fa fa-plane fa-6" aria-hidden="true"></i>
								<div class="card-content white-text">
									<span class="card-title">Leave Travel Allowance</span>
								</div>
								<div class="card-action">
									<a href="<%=request.getContextPath()%>/home/lta/new">File
										New Request</a>
								</div>
							</div>
						</div>

						<div class="col s12 m6 l4">
							<div class="card blue-grey darken-1 block_main">
								<i class="fa fa-percent fa-6" aria-hidden="true"></i>
								<div class="card-content white-text">
									<span class="card-title">Income Tax Declaration</span>
								</div>
								<div class="card-action">
									<a target="_blank"
										href="<%=request.getContextPath()%>/it/faces/pages/login.xhtml?sessionKey=${appContext.userLoginKey}&redirectUrl=/faces/pages/home.xhtml">Declare
										Now</a>
								</div>
							</div>
						</div>

						<div class="col s12 m6 l4">
							<div class="card blue-grey darken-1 block_main">
								<i class="fa fa-users fa-6" aria-hidden="true"></i>
								<div class="card-content white-text">
									<span class="card-title">Group Health Insurance</span>

								</div>
								<div class="card-action">
									<a target="_blank"
										href="<%=request.getContextPath()%>/home/myInsurancePlan">Click
										Here</a>
								</div>
							</div>
						</div>

						<div class="col s12 m6 l4">
							<div class="card blue-grey darken-1 block_main">
								<i class="fa fa-line-chart fa-6" aria-hidden="true"></i>
								<div class="card-content white-text">
									<span class="card-title">Flexi Benefits</span>

								</div>
								<div class="card-action">
									<a href="<%=request.getContextPath()%>/home/myFlexiPlans">View
										All</a>
								</div>
							</div>
						</div>

						<div class="col s12 m6 l4">
							<div class="card blue-grey darken-1 block_main">
								<i class="fa fa-line-chart fa-6" aria-hidden="true"></i>
								<div class="card-content white-text">
									<span class="card-title">Why Benefits</span>

								</div>
								<div class="card-action">
									<a href="#">Checkout</a>
								</div>
							</div>
						</div>
					</div>
					<div class="col s12">
						<div class="white z-depth-2">
							<div class="blue left w-100">
								<div class="col-sm-12 col-md-12">
									<h4 class="h4-responsive white-text">New Income Tax Slabs
										for FY ${appContext.currentFiscalYear}</h4>
								</div>
							</div>
							<div class="row">
								<div class="">
									<table class="table striped table-borderd">
										<thead>

											<tr>
												<th>Slab</th>
												<th>New Slab Tax Rate</th>
												<th>Existing Tax Rate (Old Slab)</th>
												
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${taxSlabs}" var="slab">
												<tr>
													<td>${slab.ctgName}</td>
													<td>${slab.ctgValue}</td>
													<td>${slab.ctgValue2}</td>
													
												</tr>
											</c:forEach>
										</tbody>
									</table>
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