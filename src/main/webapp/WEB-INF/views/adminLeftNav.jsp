<!--
Admin Left Navbar
author : jithin.kuriakose, vineesh.vijayan
-->
<aside id="left-sidebar-nav" class="side-nav z-depth-1 desktop">
	<div id="left" class="span3">
		<!-- FLEXI PLAN MENU -->


		<ul class="collapsible" data-collapsible="accordion">
			<li class=""><a class=""
				href="<%=request.getContextPath()%>/home/controlPanel"> <span
					class="lbl">Control Panel Home</span>
			</a></li>

			<li>
				<div class="collapsible-header">
					Flexi Benefit Plans <i class="fa fa-chevron-down right"
						aria-hidden="true"></i>
				</div>
				<div class="collapsible-body">
					<ul class="sub">
						<c:if
							test="${appContext.role eq 'SYS_ADMIN' || appContext.role eq 'HR_USER' }">
							<li><a class=""
								href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/new">
									<span>Add New Plan</span>
							</a></li>
						</c:if>

						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans">
								<span>View All Plans</span>
						</a></li>
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees">
								<span>Enrolled Employees</span>
						</a></li>
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedOutEmployees/search">
								<span>Opted out Employees</span>
						</a></li>
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/searchClaims">
								<span>Search All Claims</span>
						</a></li>

						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claimDetails">
								<span>Period wise Claims</span>
						</a></li>

						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/fileImport">
								<span>Claim Report File Upload</span>
						</a></li>



					</ul>
				</div>
			</li>

			<li>
				<div class="collapsible-header">
					LTA (Leave Travel Allowance) <i class="fa fa-chevron-down right"
						aria-hidden="true"></i>
				</div>
				<div class="collapsible-body">
					<ul class="sub">
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/lta/search">
								<span>View Requests</span>
						</a></li>
					</ul>
				</div>
			</li>

			<li>
				<div class="collapsible-header">
					PF (Provident Fund) <i class="fa fa-chevron-down right"
						aria-hidden="true"></i>
				</div>
				<div class="collapsible-body">
					<ul class="sub">
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/pf/search">
								<span>Enrolled Employees</span>
						</a></li>
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/pf/optOutSearch">
								<span>Opted Out Employees</span>
						</a></li>

						 <li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/pf/changeSlabSearch">
								<span>Slab Change Request</span>
						</a></li>
						
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/pf/changeVolAmountSearch">
								<span>Voluntary Amount Change Request</span>
						</a></li>

						 <li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/pf/searchMonth">
								<span>Monthly Report</span>
						</a></li>
						
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/pf/pfReport">
								<span>PF Report</span>
						</a></li>
						

					</ul>
				</div>
			</li>
			<li>
			<c:if
				test="${appContext.role eq 'SYS_ADMIN' ||  appContext.role eq 'FIN_USER' || appContext.role eq 'GM_USER'}">
			
				<div class="collapsible-header">
					IT Declaration <i class="fa fa-chevron-down right"
						aria-hidden="true"></i>
				</div>
				<div class="collapsible-body">
					<ul class="sub">
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/ITdeclaration/adminpage">
								<span>Setup Declaration View</span>
						</a></li>

						<li><a class=""
							href="<%=request.getContextPath()%>/ITdeclaration/viewEmployeeList">
								<span>View Employee List</span>
						</a></li>

						<li><a class=""
									href="<%=request.getContextPath()%>/home/controlPanel/unlockITDeclaration">
										<span>Unlock IT Declaration</span>
								</a></li>

						
				


						<%-- <li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/lta/reports">
								<span>Reports</span>
						</a></li> --%>

					</ul>
				</div>
				</c:if>
			</li>

			<c:if
				test="${appContext.role eq 'SYS_ADMIN' || appContext.role eq 'HR_USER' || appContext.role eq 'FIN_USER' || appContext.role eq 'INS_USER' || appContext.role eq 'GM_USER'}">
				<li>
					<div class="collapsible-header">
						Group Health Insurance <i class="fa fa-chevron-down right"
							aria-hidden="true"></i>
					</div>
					<div class="collapsible-body">
						<ul class="sub">
							<c:if
								test="${appContext.role eq 'SYS_ADMIN' || appContext.role eq 'HR_USER' || appContext.role eq 'INS_USER'}">
								<li><a class=""
									href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/new">
										<span>Add New Plan</span>
								</a></li>

							</c:if>
							<li><a class=""
								href="<%=request.getContextPath()%>/home/controlPanel/viewInsurancePlans">
									<span>View All Plans</span>
							</a></li>
							<c:if
								test="${appContext.role eq 'SYS_ADMIN' || appContext.role eq 'HR_USER' || appContext.role eq 'FIN_USER' || appContext.role eq 'INS_USER' || appContext.role eq 'GM_USER'}">
								<li><a class=""
									href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees">
										<span>Enrolled Employees</span>
								</a></li>
								<li><a class=""
									href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims">
										<span>Search Claims</span>
								</a></li>
								<li><a class=""
									href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/viewPlanReport">
										<span>Download Plan Report</span>
								</a></li>
								
								<li><a class=""
									href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/viewMonthlyPlanReport">
										<span>Monthly Report</span>
								</a></li>
								
							</c:if>
						</ul>
					</div>
				</li>
			</c:if>

			<%--  <li>
				<div class="collapsible-header">
					IT Declaration <i class="fa fa-chevron-down right" aria-hidden="true"></i>
				</div>
				<div class="collapsible-body">
					<ul class="sub">
						<li><a class=""
							href="<%=request.getContextPath()%>/ITdeclaration/viewEmployeeList">
								<span>View Employee List</span>
						</a></li>

					</ul>
				</div>
			</li> 
 --%>

			<li>
				<div class="collapsible-header">
					Reports <i class="fa fa-chevron-down right" aria-hidden="true"></i>
				</div>
				<div class="collapsible-body">
					<ul class="sub">
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/reports/employee">
								<span>All Employees</span>
						</a></li>
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claimDetails">
								<span>Flexi Plan - Period wise Claims</span>
						</a></li>
					</ul>
				</div>
			</li>
			<li>
				<div class="collapsible-header">
					Master Data <i class="fa fa-chevron-down right" aria-hidden="true"></i>
				</div>
				<div class="collapsible-body">
					<ul class="sub">
						<c:if test="${appContext.role eq 'SYS_ADMIN'}">
							<li><a class=""
								href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewhospital">
									<span>Hospitals</span>
							</a></li>

							<li><a class=""
								href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewtreatment">
									<span>Treatments</span>
							</a></li>
						</c:if>
						<li><a class=""
							href="<%=request.getContextPath()%>/home/controlPanel/masterData/itslabs">
								<span>IT Slabs</span>
						</a></li>
					</ul>
				</div>
			</li>
			<c:if
				test="${appContext.role eq 'SYS_ADMIN' || appContext.role eq 'HR_USER' }">
				<li>

					<div class="collapsible-header">
						Settings <i class="fa fa-chevron-down right" aria-hidden="true"></i>
					</div>
					<div class="collapsible-body">
						<ul class="sub">
							<c:if
								test="${appContext.role eq 'SYS_ADMIN' || appContext.role eq 'HR_USER' }">
								<li><a class=""
									href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/0">
										<span>System Properties</span>
								</a></li>
							</c:if>
							<c:if
								test="${appContext.role eq 'SYS_ADMIN' || appContext.role eq 'HR_USER' }">
								<li><a class=""
									href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/mutualDependency">
										<span>Flexi Plan Dependencies</span>
								</a></li>
							</c:if>
							<c:if test="${appContext.role eq 'SYS_ADMIN' }">
								<li><a class=""
									href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/adminSettings">
										<span>Admin Settings</span>
								</a></li>
							</c:if>
							<c:if
								test="${appContext.role eq 'SYS_ADMIN' || appContext.role eq 'HR_USER' }">
								<li><a class=""
									href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/homePageNotifications">
										<span>Home Page Notifications</span>
								</a></li>

								
							</c:if>
						</ul>
					</div>
				</li>
			</c:if>
		</ul>

	</div>
</aside>

<div class="mobile">

	<ul id="slide-out" class="side-nav collapsible left-aligned"
		data-collapsible="accordion">
		<li class=""><a class="control_panel_mob"
			href="<%=request.getContextPath()%>/home/controlPanel"> <span
				class="lbl">Control Panel Home</span>
		</a></li>
		<li>
			<div class="collapsible-header">
				Flexi Benefit Plans <i class="fa fa-chevron-down right"
					aria-hidden="true"></i>
			</div>
			<div class="collapsible-body">
				<ul class="sub">
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/new">
							<span>Add New Plan</span>
					</a></li>

					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans">
							<span>View All Plans</span>
					</a></li>
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees">
							<span>Opted Employees</span>
					</a></li>
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/claimDetails">
							<span>Claim Details</span>
					</a></li>
				</ul>
			</div>
		</li>

		<li>
			<div class="collapsible-header">
				Reports <i class="fa fa-chevron-down right" aria-hidden="true"></i>
			</div>
			<div class="collapsible-body">
				<ul class="sub">
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/new">
							<span>All Employees</span>
					</a></li>

					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans">
							<span>Insurance Claims</span>
					</a></li>
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans">
							<span>Flexi Benefit Plans vs Opted Employees</span>
					</a></li>
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans">
							<span>Insurance Package vs Opted Employees</span>
					</a></li>
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/flexiPlans/optedEmployees">
							<span>Opted Employees</span>
					</a></li>

				</ul>
			</div>
		</li>
		<li>
			<div class="collapsible-header">
				Master Data <i class="fa fa-chevron-down right" aria-hidden="true"></i>
			</div>
			<div class="collapsible-body">
				<ul class="sub">
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewhospital">
							<span>Hospitals</span>
					</a></li>

					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/masterData/viewtreatment">
							<span>Treatments</span>
					</a></li>
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/masterData/itslabs">
							<span>IT Slabs</span>
					</a></li>
				</ul>
			</div>
		</li>

		<li>
			<div class="collapsible-header">
				Settings <i class="fa fa-chevron-down right" aria-hidden="true"></i>
			</div>
			<div class="collapsible-body">
				<ul class="sub">
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/0">
							<span>System Properties</span>
					</a></li>
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/mutualDependency">
							<span>Flexi Plan Dependencies</span>
					</a></li>
					<li><a class=""
						href="<%=request.getContextPath()%>/home/controlPanel/settings/properties/adminSettings">
							<span>Admin Settings</span>
					</a></li>


				</ul>
			</div>
		</li>
	</ul>
	<a href="#" data-activates="slide-out"
		class="button-collapse left_resp_menu"><i
		class="fa fa-angle-double-right" aria-hidden="true"></i></a>

</div>
