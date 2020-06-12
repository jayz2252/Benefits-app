<!-- 
author : jithin.kuriakose
This page will show Employees opted for each benefit plans
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
							<h4 class="h4-responsive">Direct Claim File Upload</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>
					
					<c:if test="${noFileSeclected ne null}">
								<div class="alert alert-danger">
									<strong>Oops..! </strong> ${noFileSeclected}
								</div>
							</c:if>
					
					<c:if test="${errorMessage ne null}">
								<div class="alert alert-danger">
									<strong>Oops..! </strong> ${errorMessage}
								</div>
							</c:if>
					<c:if test="${successMessage ne null}">
								<div class="alert alert-success">
									<strong>Done! </strong> ${successMessage}
								</div>
					</c:if>
					
					
					<div class="row">
						<div class="col-md-12">
						
						<div class="alert alert-warning">
  							<span id="errorMessage"></span>
						</div>

							<p>Select a file to upload: </p> <br/>
							<form action="fileImport" id="formId" method="post" enctype="multipart/form-data">


								<div class="row">
									<div class="md-form">
										<div class="col-md-12 padding_left_0">

											<input type="file" name="file" size="100" id="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" required="true"/> <br /> 
											<input	type="submit" class="btn btn-primary" value="Upload File" />
										</div>
									</div>
								</div>

							</form>


						</div>
					</div>

				</div>

			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
	
	$('#file').on( 'change', function() {
		   myfile= $( this ).val();
		   var ext = myfile.split('.').pop();
		   if(ext=="xlsx"){
			   $('#errorMessage').html("");
		   } else{
		      
		       $('#errorMessage').html("Please Upload .xlsx File");
		       $("#file").val(null);
		   }
		});
	</script>

</body>
</html>