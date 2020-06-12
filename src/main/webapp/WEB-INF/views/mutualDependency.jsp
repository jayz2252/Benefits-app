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
							<h4 class="h4-responsive">Mutual Dependencies</h4>
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
									<th>Plan 1</th>
									<th>Plan 2</th>
								</tr>
							</thead>
							<tbody>
								
									<c:forEach items="${plans}" var="plan">
									<tr>

										<td>${plan.plan1.planName}</td>
										<td>${plan.plan2.planName}</td>
									</tr>
								</c:forEach>
								
							</tbody>
						</table>
						<a class="btn btn-primary"
							href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/addNewMutualDependency" />Add
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

