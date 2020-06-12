
<!--
Employee Top Navbar
author : jithin.kuriakose, vineesh.vijayan
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-toggleable-md navbar-dark">
	<div class="container-fluid">
		<button class="navbar-toggler navbar-toggler-right" type="button"
			data-toggle="collapse" data-target="#collapseEx12"
			aria-controls="collapseEx2" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="#"><img class="logo" alt="Logo"
			src="/resources/img/logo.png" /></a>
		<div class="navbar-collapse" id="collapseEx12">
			<ul class="pull-right">
				<li class="nav-item active pull-left"><a class="nav-link"
					href="<%=request.getContextPath()%>/home">Home <span
						class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item btn-group pull-left"><a
					class="dropdown-button dropdown-toggle" href="#!"
					data-activates="dropdown2">Flexi Benefits</a>
					<ul id="dropdown2" class="dropdown-content">

						<li><a class="dropdown-item"
							href="<%=request.getContextPath()%>/home/myFlexiPlans">My
								Flexi Plans</a></li>

						<li><a class="dropdown-item"
							href="<%=request.getContextPath()%>/home/lta/new">LTA (Leave
								Travel Allowance)</a></li>
						<!-- <li><a class="dropdown-item"
							href="#">PF (Provident
								Funds)</a></li> -->
						<li><a class="dropdown-item"
							href="<%=request.getContextPath()%>/home/myEpfHome">PF
								(Provident Funds)</a></li>
								
								<li><a class="dropdown-item"
							href="<%=request.getContextPath()%>/home/myEnpsHome">NPS
							</a></li>
					</ul></li>

				<li class="nav-item active pull-left"><a class="nav-link"
					href="<%=request.getContextPath()%>/home/myInsurancePlan">Group
						Health Insurance <span class="sr-only">(current)</span>
				</a> <a target="_blank"
					href="<%=request.getContextPath()%>/it/faces/pages/login.xhtml?sessionKey=${appContext.userLoginKey}&redirectUrl=/faces/pages/home.xhtml"
					class="nav-link">IT Declaration <span class="sr-only">(current)</span>
				</a> <a class="nav-link" target="_blank"
					href="http://www.incometaxindia.gov.in/Pages/tools/tax-calculator.aspx">Tax
						Calculator<span class="sr-only">(current)</span>
				</a></li>



				<li class="nav-item btn-group pull-left"><a
					class="dropdown-button dropdown-toggle" href="#!"
					data-activates="dropdown1">My Account</a>
					<ul id="dropdown1" class="dropdown-content">
						<c:if test="${appContext.admin}">
							<li><a class="dropdown-item"
								href="<%=request.getContextPath()%>/home/controlPanel">Control
									Panel</a></li>
						</c:if>
						<li><a class="dropdown-item"
							href="<%=request.getContextPath()%>/logout">Logout</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>
<!--/.Navbar-->
