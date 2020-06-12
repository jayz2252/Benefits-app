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
							<h4 class="h4-responsive">Treatments List</h4></br>
						</div>
						
					</div>
					<div class="row">
						<div class="col-md-12">
							<table class="table striped table-borderd">
								<thead>

										<tr>
											<th>Treatment Name</th>
											<th>Treatment Description</th>
											<th>Average Amount</th>
											<th>Actions</th>

										</tr>
									</thead>
									<tbody>

										<tr>

											<c:forEach items="${treatments}" var="tm">
												<tr>

													<td>${tm.treatmentName}</td>
													<td>${tm.description}</td>
													<td>${tm.averageAmount}</td>

													<td><a
														href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewtreatment/edit/${tm.treatmentId}"><i
															class="fa fa-pencil" aria-hidden="true"></i></a> <a
														href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewtreatment/delete/${tm.treatmentId}"><i
															class="fa fa-trash-o" aria-hidden="true"></i></a></td>


												</tr>
											</c:forEach>
									</tbody>
								</table>

								<a
									href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewtreatment/add"
									class="btn btn-primary">Add New</a>


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




