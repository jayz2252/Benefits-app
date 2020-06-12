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
							<h4 class="h4-responsive">Edit ${planPojo.planName} -
								Documents</h4>
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

							<form:form id="editBenefitPlanDocsForm" modelAttribute="planBean"
								action="summary" method="post" cssClass="form-horizontal">
								<div class="row">
									<table class="table striped table-borderd">
										<tr>
											<th>Document Name</th>
											<th>Mandatory</th>
										</tr>
										<tr>
											<td style="width: 90%"><div
													class="col-md-12 padding_left_0">
													<!-- <input  id="name" class="form-control">-->
													<input id="doc1Name" name="doc1Name" type="text"
														cssClass="form-control" /> <label for="doc1Name">Document
														1</label>
												</div></td>
											<td style="width: 10%"><div
													class="col-md-12 padding_left_0">
													<fieldset class="form-group">
															<input type="checkbox" id="doc1Mandatory" name="doc1Mandatory"> 
															<label for="doc1Mandatory"></label>
														</fieldset>
												</div></td>
										</tr>

										<tr>
											<td style="width: 90%"><div
													class="col-md-12 padding_left_0">
													<!-- <input  id="name" class="form-control">-->
													<input id="doc2Name" name="doc2Name" type="text"
														cssClass="form-control" /> <label for="doc2Name">Document
														2</label>
												</div></td>
											<td style="width: 10%"><div
													class="col-md-12 padding_left_0">
													<fieldset class="form-group">
															<input type="checkbox" id="doc2Mandatory" name="doc2Mandatory"> 
															<label for="doc2Mandatory"></label>
														</fieldset> 
												</div></td>
										</tr>

										<tr>
											<td style="width: 90%"><div
													class="col-md-12 padding_left_0">
													<!-- <input  id="name" class="form-control">-->
													<input id="doc3Name" name="doc3Name" type="text"
														cssClass="form-control" /> <label for="doc3Name">Document
														3</label>
												</div></td>
											<td style="width: 10%"><div
													class="col-md-12 padding_left_0">
													<fieldset class="form-group">
															<input type="checkbox" id="doc3Mandatory" name="doc3Mandatory"> 
															<label for="doc3Mandatory"></label>
														</fieldset>
												</div></td>
										</tr>

										<tr>
											<td style="width: 90%"><div
													class="col-md-12 padding_left_0">
													<!-- <input  id="name" class="form-control"-->
													<input id="doc4Name" name="doc4Name" type="text"
														cssClass="form-control" /> <label for="doc4Name">Document
														4</label>
												</div></td>
											<td style="width: 10%">
											<div class="col-md-12 padding_left_0">
													<fieldset class="form-group">
															<input type="checkbox" id="doc4Mandatory" name="doc4Mandatory"> 
															<label for="doc4Mandatory"></label>
														</fieldset>
										</tr>

										<tr>
											<td style="width: 90%"><div
													class="col-md-12 padding_left_0">
													<!-- <input  id="name" class="form-control">-->
													<input id="doc5Name" name="doc5Name" type="text"
														cssClass="form-control" /> <label for="doc5Name">Document
														5</label>
												</div></td>
											<td style="width: 10%"><div
													class="col-md-12 padding_left_0">
													<fieldset class="form-group">
															<input type="checkbox" id="doc5Mandatory" name="doc5Mandatory"> 
															<label for="doc5Mandatory"></label>
														</fieldset>
												</div></td>
										</tr>
									</table>
								</div>

								<form:hidden path="mode" />
								<form:hidden path="step" />
								<div class="form-group">
									<div class="col-md-6">
										<a class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel">Cancel</a>
										<input type="submit" class="btn btn-primary" value="Next" />

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
	<!-- <script type="text/javascript">
		window.onbeforeunload = function() {
			return "You work will be lost.";
		};
	</script> -->
</body>
</html>