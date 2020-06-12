
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
					<form action="createInsurance" method="post">
						<h2>Plan created successfully</h2>

						<div class="row">
							<div class="col-sm-6 col-md-9 py-1 px-1">
								<h4 class="h4-responsive">Review your Plan</h4>
							</div>

						</div>

						<div class="row">
							<div class="col-md-12">

								<table class="table">
									<thead>
										<tr>
											<th>Plan Name</th>
											<th>Self Insured Amount</th>
											<th>Plan Description</th>

										</tr>
									</thead>
									<tbody>

										<tr>

											<td>${insurancePlan.insplanName}</td>
											<td>${insurancePlan.selfinsuredAmount}</td>
											<td>${insurancePlan.planDescription}</td>



										</tr>

									</tbody>
								</table>


							</div>
						</div>
					</form>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
</body>
</html>




