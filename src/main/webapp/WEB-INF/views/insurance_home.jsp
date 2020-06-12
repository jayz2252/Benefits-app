
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
table.th {
	width: 50px;
}
</style>
</head>
<%@include file="include.jsp"%>
<body>
	<%@include file="employeeNavBar.jsp"%>



	<div id="main">
		<div class="wrapper">
			<%@include file="adminLeftNav.jsp"%>
			<section id="content" class="">
			<div class="row">

				<form action="create/insurance" method="post">
					<h1>Create New Insurance Plans</h1>
					<div>
						<table>
							<tr>
								<td>Plan name : <input type="text" name="insplanName"
									value="">

								</td>
							</tr>
							<tr>
								<td>Self Insured Amount: <input type="text"
									name="selfinsuredAmount" value="">

								</td>
							</tr>
							<tr>
								<td>Dependent Insured Amount: <input type="text"
									name="dependentInsuredAmount" value="">

								</td>
							</tr>
							<tr>
								<td>Plan Description: <input type="text"
									name="planDescription" value="">

								</td>
							</tr>


						</table>
					</div>


					<div class="col-md-12 white">

						<div class="row">
							<div class="col-sm-6 col-md-9 py-1 px-1">
								<h4 class="h4-responsive">Amount alloted for different
									Treatments</h4>
							</div>

						</div>

						<div class="row">
							<div class="col-md-12">

								<table class="table">
									<thead>
										<tr>
											<th>Treatment Name</th>
											<th>Description</th>
											<th>Amount</th>
											<th>Covered Amount</th>
											<th>Select</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${treatmentList.treatments}" var="treatment"
											varStatus="status">
											<tr>


												<td><input
													name="treatments[${status.index}].treatmentName"
													value="${treatment.treatmentName}" /></td>
												<td><input
													name="treatments[${status.index}].description"
													value="${treatment.description}" /></td>
												<td><input
													name="treatments[${status.index}].averageAmount"
													value="${treatment.averageAmount}" /></td>
												<td><input type="text"
													name="coveredAmounts[${status.index}]" value=""></td>
												<td><input type="checkbox"
													name="treatments[${status.index}].treatmentId"
													value="${treatment.treatmentId}" /></td>

											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="btn-group dropup">
									<input type="submit" value="Create Plan">
								</div>

							</div>
						</div>
					</div>
				</form>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
</body>

</html>




