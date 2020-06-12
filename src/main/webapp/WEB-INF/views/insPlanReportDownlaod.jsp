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
							<h4 class="h4-responsive">Insurance Plan Report</h4>
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
							<table class="tablePlanEmployees table striped table-borderd">
								<thead>
									<tr>
										<th>Plan Name</th>
										<th>Report</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${insPlans}" var="insPlan">
										<tr>
											<td>${insPlan.planName}</td>
											<td><a
												href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/viewPlanReport/download/${insPlan.insPlanId}"><i
													class="fa fa-file-excel-o" aria-hidden="true" ></i></td>
										</tr>
									</c:forEach>
								</tbody>

							</table>

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


