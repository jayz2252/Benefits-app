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
							<h4 class="h4-responsive">Edit Treatment Details</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>

					<form action="save" method="POST">
						<c:choose>
							<c:when test="${mode eq 'edit'}">
								<b>Treatment Name : </b>
								<input type="text" value="${treatment.treatmentName}"
									name="treatmentName" required="true" />
								<b>Treatment Description : </b>
								<input type="text" value="${treatment.description}"
									name="treatmentDescription" required="true" />
								<b>Average Amount : </b>
								<input type="number" value="${treatment.averageAmount}"
									name="averageAmount" required="true" />
								</br>
								<input type="hidden" value="${treatment.treatmentId}"
									name="treatmentId" />
								<input type="hidden" value="${mode}" name="mode" />
							</c:when>
							<c:otherwise>
								<b>Treatment Name : </b>
								<input type="text" value="${treatment.treatmentName}"
									name="treatmentName" required="true" />
								<b>Treatment Description : </b>
								<input type="text" value="${treatment.description}"
									name="treatmentDescription" required="true" />
								<b>Average Amount : </b>
								<input type="number" value="${treatment.averageAmount}"
									name="averageAmount" required="true" />
								</br>
								<input type="hidden" value="${treatment.treatmentId}"
									name="treatmentId" />
								<input type="hidden" value="${mode}" name="mode" />
							</c:otherwise>
						</c:choose>
						<a class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewtreatment">Cancel</a>
						<input type="submit" class="btn btn-primary"
							value="Save Treatment" />
					</form>


					<%-- <form action="save" method="POST">
								<b>Treatment Name : </b>
								<input type="text" value="${treatment.treatmentName}" name="treatmentName"
									required="true" />
								<b>Treatment Description : </b>
								<input type="text" value="${treatment.description}" name="treatmentDescription"
									required="true" />
								<b>Average Amount : </b>
								<input type="text" value="${treatment.averageAmount}" name="averageAmount"
									required="true" />
								</br>
								<input type="hidden" value="${treatment.treatmentId}"
									name="treatmentId" />
								<input type="hidden" value="${mode}" name="mode" />
							
						<input type="submit" class="btn btn-primary" value="Save Treatment List" />
					</form> --%>
				</div>
			</div>
			</section>
		</div>
	</div>

</body>
</html>