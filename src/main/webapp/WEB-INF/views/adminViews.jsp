<!-- 
author : swathy.raghu
This page will provides employee search option
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
							<h4 class="h4-responsive">Employee List</h4>
							<form
								action="<%=request.getContextPath()%>/home/test/report/declare"
								method="post" style="width: 50%;">
								<input type="submit" value="Exports to Excel"
									class="approveClass btn blue darken-3">
							</form>
							<%-- <form
								action="<%=request.getContextPath()%>/home/test/report/proof"
								method="post"
								style="width: 40%; float: right; margin-top: -60px;">
								<input type="submit" value="Proof Submission Excel"
									class="approveClass btn blue darken-3">
							</form> --%>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
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
						<table class="tablePlanEmployees table striped table-borderd"">
							<thead>
								<tr>

									<th>Employee Code</th>
									<th>Employee Name</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${employeeList}" var="avList">
									<tr>
										<td>${avList.employeeCode}</td>
										<td>${avList.employeeName}</td>

										<td><a
											href="<%=request.getContextPath()%>/it/faces/pages/login.xhtml?sessionKey=${sessionKey}&redirectUrl=/faces/pages/itHome.xhtml?itEmployeeId=${avList.itEmployeeId}"
											target="_blank">view</a></td>

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
	<script>
		$(document).ready(function() {
			$('select').material_select();
		});
	</script>

</body>
</html>









































