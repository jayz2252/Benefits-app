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
							<h4 class="h4-responsive">
								<c:choose>
									<c:when test="${planBean.mode eq 'add'}">
							Add New Flexi Plan
						</c:when>
									<c:otherwise>
							Edit Flexi Plan
						</c:otherwise>
								</c:choose>
							</h4>
						</div>
						<div class="col-sm-6 col-md-3"></div>
					</div>

					<div class="row">
						<div class="">

							<form:form id="editPlanForm" method="post" action="new/amountBreakup"
								modelAttribute="planBean" cssClass="form-horizontal">
								<div class="form-group is-empty">

									<div class="md-form">
										<div class="col-md-12 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<form:input id="planName" name="planName" type="text"
												path="planName" cssClass="form-control" required="true" />
											<label for="planName">Plan Name</label>
										</div>

									</div>
								</div>
								<div class="form-group is-empty">
									<div class="md-form row">
										<!-- <input  id="name" class="form-control">-->
										<form:input id="planDesc" name="planDesc" type="text"
											path="planDesc" cssClass="form-control" />
										<label for="planDesc">Description</label>
									</div>
								</div>
								<div class="form-group is-empty">
									

									<div class="md-form">
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<form:input id="effFrom" name="effFrom" type="date"
												path="effFrom" cssClass="form-control datepicker"
												required="true" />
											<label for="effFrom">Effective From</label>
										</div>
										<div class="col-md-6 padding_left_0">
											<!-- <input  id="name" class="form-control">-->
											<form:input id="effTill" name="effTill" type="date"
												path="effTill" cssClass="form-control datepicker" />
											<label for="effTill">Effective To</label>
										</div>
									</div>
								</div>

								<div class="form-group remember-label">
										<div>
											<input type="checkbox" id="claimDocsRequired"
												name="claimDocsRequired" /> <label for="claimDocsRequired">Bill/Document
												submission required for claim?</label>
										</div>
									</div>
								<div class="form-group remember-label">
									<div>
											<input type="checkbox" id="active" name="active" /> <label
												for="active">Active</label>
									</div>
								</div>
								

								<form:hidden path="mode" id="mode"/>

								<div class="form-group">
									<div class="">
										<a class="btn yellow darken-3"
											href="<%=request.getContextPath()%>/home/controlPanel">Cancel</a>
										<input type="submit" class="btn btn-primary" value="Next" onclick='<c:if test="${planBean.mode eq 'edit'}">form.action="amountBreakup"</c:if>'/>
									</div>
								</div>
								
							</form:form>
						</div>
					</div>
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