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
							<h4 class="h4-responsive">Edit Home Page Notifications</h4>
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
								<b>Message : </b>
								<input type="text" value="${notification.message}" name="message"
									required="true" />
								
								<b>Valid From : </b>
								<input type="date" id="validFrom" value="<fmt:formatDate pattern="dd MMMM,yyyy"
													value="${notification.validFrom}" />" name="validFrom" class="form-control datepicker"
									required="true" />
									<label for="validFrom"></label>
								
								<b>Valid Till : </b>
								<input type="date" id="validTill" value="<fmt:formatDate pattern="dd MMMM,yyyy"
													value="${notification.validTill}" />" name="validTill" class="form-control datepicker"
									required="true" />	
									<label for="validTill"></label>	
									
								</br>
								<input type="hidden" value="${notification.notificationId}"
									name="notificationId" />
								<input type="hidden" value="${mode}" name="mode" />
							</c:when>
							
							
							<c:otherwise>
								<b>Message : </b>
								<input type="text" value="${notification.message}" name="message"
									required="true" />
							 
							 	<b>Valid From : </b>
								<input type="date" id="validFrom" value="<fmt:formatDate pattern="dd MMMM,yyyy"
													value="${notification.validFrom}" />" name="validFrom" class="form-control datepicker"
									required="true" />
									<label for="validFrom"></label>
								
								<b>Valid Till : </b>
								<input type="date" id="validTill" value="<fmt:formatDate pattern="dd MMMM,yyyy"
													value="${notification.validTill}" />" name="validTill" class="form-control datepicker"
									required="true" />
									<label for="validTill"></label>
								
								<input type="hidden" value="${mode}" name="mode" />
							</c:otherwise>
						</c:choose>
						</br>
						<a class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/homePageNotifications">Cancel</a>
						<input type="submit" class="btn btn-primary" value="Save Notification" />
					</form>
				</div>
			</div>
			</section>
		</div>
	</div>
	
	<%@include file="includeFooter.jsp"%>
	<script>
		$('.datepicker').pickadate({
			selectMonths : true, // Creates a dropdown to control month
			selectYears : 15
		// Creates a dropdown of 15 years to control year
		});
	</script>

</body>
</html>