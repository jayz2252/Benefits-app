<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Benefits Portal</title>
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
							<h4 class="h4-responsive">Upload Simple Claims</h4>
						</div>
						<div class="col-sm-6 col-md-3">
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
								
							<table class="tablePlanEmployees table striped table-borderd"">
							<thead>
								<tr>

									
									<th>Claim Reference #</th>
									<th>External Reference #</th>
									<th>Amount</th>
									<th>Period From</th>
									<th>Period To</th>
									<th>Issued Date</th>
									<th>Issued By</th>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${claims}" var="claims">
									<tr>
										<%-- <input type="hidden" value="${claims.planEmployeeId}" class="docId"> --%>
										<td>${claims.claimRefNo}</td>
										<td>${claims.extRefNo}</td>
										<td>${claims.amount}</td>
										<td>${claims.periodFrom}</td>
										<td>${claims.periodTo}</td>
										<td>${claims.issuedDate}</td>
										<td>${claims.issuedBy}</td>	
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<a class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel">Cancel</a>
						<a class="btn btn-primary" href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/directClaim/new/${planId}">Add</a>
						</div>
					</div>
				</div>
			</div>
			</section>
		</div>
	</div><%@include file="includeFooter.jsp"%>
</body>

<script type="text/javascript">
</script>
</html>