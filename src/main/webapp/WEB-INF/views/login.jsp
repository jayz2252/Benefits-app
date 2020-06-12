<!--
Login page
author : jithin.kuriakose, vineesh.vijayan
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="include.jsp"%>

</head>
<body class="outlay-login-page">
	<div style="height: 100vh">
		<div class="flex-center ">
			<div class="container-fluid login_fluid">
				<div class="row">
					<div class="col m12 col s12">
						<div class="panel login-panel animated fadeIn">
						
												
						
							<div class="panel-heading text-center">
								<h3>Employee Benefits Portal</h3>
								<p>Use Exchange Server/AD Credentials</p>
							</div>
							<div class="panel-body">
								<c:if test="${error ne null}">
									<div class="alert alert-danger">
										<strong>Authentication Failed: </strong>${error}
									</div>
								</c:if>
								<form:form id="loginForm" method="post" action="login"
									modelAttribute="loginBean" cssClass="form-horizontal">
									<div class="form-group is-empty">

										<div class="md-form">
											<!-- <input  id="name" class="form-control">-->
											<form:input id="userName" name="userName" type="text"
												path="userName" cssClass="form-control" required="true" />
											<label for=userName>User Name</label>
										</div>
									</div>
									<div class="form-group is-empty">
										<div class="md-form">
											<!--  <input type="password" id="password" class="form-control">-->
											<form:password id="password" name="password" path="password"
												cssClass="form-control" required="true" />
											<label for="password">Password</label>
										</div>
									</div>
									<!-- <div class="form-group remember-label">
										<div class="col-sm-12">
											<fieldset class="form-group">
												<input type="checkbox" id="checkbox1"> <label
													for="checkbox1">Remember Me</label>
											</fieldset>
										</div>
									</div> -->
									<div class="form-group">
										<input type="submit" class="btn btn-primary btn-block text_style_normal signinbut"
											value="Sign in">
									</div>
								</form:form>
								<div class="logo_login">
									<img src="<c:url value="/resources/img/Speridian_Logo.svg"/>" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
</body>
</html>