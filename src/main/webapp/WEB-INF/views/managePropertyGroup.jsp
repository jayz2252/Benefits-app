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
							<h4 class="h4-responsive">Manage System Properties</h4>
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
							
							<form action="save" method="POST">
							
							<div class="col s12">
							<ul class="tabs">
							<li class="tab col s2"><a id="link_tabGeneral"
									class="active tab_link" href="#tabGeneral">General</a></li>
								<li class="tab col s2"><a id="link_tabFlexiPlan"
									class="tab_link" href="#tabFlexiPlan">Flexi Plan</a></li>
								<li class="tab col s2"><a id="link_tabInsurance"
									class="tab_link" href="#tabInsurance">Insurance</a></li>
								<li class="tab col s2"><a id="link_tabIt"
									class="tab_link" href="#tabIt">IT</a></li>	
								<li class="tab col s2"><a id="link_tabLta"
									class="tab_link" href="#tabLta">LTA</a></li>
									<li class="tab col s2"><a id="link_tabPf"
									class="tab_link" href="#tabPf">PF</a></li>
							</ul>
						</div>
						
						
						<div id="tabGeneral" class="col s12 tab_content" style="margin-top: 32px;">
							<c:forEach items="${genList}" var="genList">
							<div class="form-group is-empty">
										<div class="md-form">
											<c:choose>
												<c:when test="${genList.propertyType eq 'date'}">
													<input id="genList${genList.benefitPropertyId}"
														name="${genList.propertyCode}" type="${genList.propertyType}"
														value="${genList.propertyValue}" required="${genList.mandatory}"
														Class="form-control datepicker" />
												</c:when>
												<c:otherwise>
													<input id="genList${genList.benefitPropertyId}"
														name="${genList.propertyCode}" type="${genList.propertyType}"
														value="${genList.propertyValue}" required="${genList.mandatory}"
														cssClass="form-control" />
												</c:otherwise>
											</c:choose>
											<label for="genList${genList.benefitPropertyId}">${genList.propertyName}</label>
										</div>
									</div>
							
							
							</c:forEach>
							</div>
						
						<div id="tabFlexiPlan" class="col s12 tab_content" style="margin-top: 32px;">
							<c:forEach items="${flexiList}" var="flexiList">
							<div class="form-group is-empty">
										<div class="md-form">
											<c:choose>
												<c:when test="${flexiList.propertyType eq 'date'}">
													<input id="flexiList${flexiList.benefitPropertyId}"
														name="${flexiList.propertyCode}" type="${flexiList.propertyType}"
														value="${flexiList.propertyValue}" required="${flexiList.mandatory}"
														Class="form-control datepicker" />
												</c:when>
												<c:otherwise>
													<input id="flexiList${flexiList.benefitPropertyId}"
														name="${flexiList.propertyCode}" type="${flexiList.propertyType}"
														value="${flexiList.propertyValue}" required="${flexiList.mandatory}"
														cssClass="form-control" />
												</c:otherwise>
											</c:choose>
											<label for="flexiList${flexiList.benefitPropertyId}">${flexiList.propertyName}</label>
										</div>
									</div>
							
							
							</c:forEach>
							</div>
							
							
							<div id="tabInsurance" class="col s12 tab_content" style="margin-top: 32px;">
							<c:forEach items="${insList}" var="insList">
							<div class="form-group is-empty">
										<div class="md-form">
											<c:choose>
												<c:when test="${insList.propertyType eq 'date'}">
													<input id="insList${insList.benefitPropertyId}"
														name="${insList.propertyCode}" type="${insList.propertyType}"
														value="${insList.propertyValue}" required="${insList.mandatory}"
														Class="form-control datepicker" />
												</c:when>
												<c:otherwise>
													<input id="insList${insList.benefitPropertyId}"
														name="${insList.propertyCode}" type="${insList.propertyType}"
														value="${insList.propertyValue}" required="${insList.mandatory}"
														cssClass="form-control" />
												</c:otherwise>
											</c:choose>
											<label for="insList${insList.benefitPropertyId}">${insList.propertyName}</label>
										</div>
									</div>
							
							
							</c:forEach>
							</div>
						
						
						<div id="tabIt" class="col s12 tab_content" style="margin-top: 32px;">
							<c:forEach items="${itList}" var="itList">
							<div class="form-group is-empty">
										<div class="md-form">
											<c:choose>
												<c:when test="${itList.propertyType eq 'date'}">
													<input id="itList${itList.benefitPropertyId}"
														name="${itList.propertyCode}" type="${itList.propertyType}"
														value="${itList.propertyValue}" required="${itList.mandatory}"
														Class="form-control datepicker" />
												</c:when>
												<c:otherwise>
													<input id="itList${itList.benefitPropertyId}"
														name="${itList.propertyCode}" type="${itList.propertyType}"
														value="${itList.propertyValue}" required="${itList.mandatory}"
														cssClass="form-control" />
												</c:otherwise>
											</c:choose>
											<label for="itList${itList.benefitPropertyId}">${itList.propertyName}</label>
										</div>
									</div>
							
							
							</c:forEach>
							</div>
						
						<div id="tabLta" class="col s12 tab_content" style="margin-top: 32px;">
							<c:forEach items="${ltaList}" var="ltaList">
							<div class="form-group is-empty">
										<div class="md-form">
											<c:choose>
												<c:when test="${ltaList.propertyType eq 'date'}">
													<input id="ltaList${ltaList.benefitPropertyId}"
														name="${ltaList.propertyCode}" type="${ltaList.propertyType}"
														value="${ltaList.propertyValue}" required="${ltaList.mandatory}"
														Class="form-control datepicker" />
												</c:when>
												<c:otherwise>
													<input id="ltaList${ltaList.benefitPropertyId}"
														name="${ltaList.propertyCode}" type="${ltaList.propertyType}"
														value="${ltaList.propertyValue}" required="${ltaList.mandatory}"
														cssClass="form-control" />
												</c:otherwise>
											</c:choose>
											<label for="ltaList${ltaList.benefitPropertyId}">${ltaList.propertyName}</label>
										</div>
									</div>
							
							
							</c:forEach>
							</div>
							
							<div id="tabPf" class="col s12 tab_content" style="margin-top: 32px;">
							<c:forEach items="${pfList}" var="pfList">
							<div class="form-group is-empty">
										<div class="md-form">
											<c:choose>
												<c:when test="${pfList.propertyType eq 'date'}">
													<input id="pfList${pfList.benefitPropertyId}"
														name="${pfList.propertyCode}" type="${pfList.propertyType}"
														value="${pfList.propertyValue}" required="${pfList.mandatory}"
														Class="form-control datepicker" />
												</c:when>
												<c:otherwise>
													<input id="pfList${pfList.benefitPropertyId}"
														name="${pfList.propertyCode}" type="${pfList.propertyType}"
														value="${pfList.propertyValue}" required="${pfList.mandatory}"
														cssClass="form-control" />
												</c:otherwise>
											</c:choose>
											<label for="pfList${pfList.benefitPropertyId}">${pfList.propertyName}</label>
										</div>
									</div>
							
							
							</c:forEach>
							</div>
						
							<input type="submit" class="btn" value="Save All" />
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
	$('.datepicker').pickadate({
		selectMonths: true,
		selectYears: 0,
		labelMonthNext: 'Next Month',
		labelMonthPrev: 'Last Month',
		
		labelMonthSelect: 'Select Month',
		labelYearSelect: 'Select Year',
		
		monthsFull: [ 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December' ],
		monthsShort: [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ],
		weekdaysFull: [ 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday' ],
		weekdaysShort: [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ],
		
		weekdaysLetter: [ 'S', 'M', 'T', 'W', 'T', 'F', 'S' ],
		
		today: 'Today',
		clear: 'Clear',
		close: 'Close',
		
		format: 'dd/mm/yyyy'
		});
		
	function showTab(activeTabId) {
		$('ul.tabs').tabs('select_tab', activeTabId);
	}
	
	</script>
	</body>
</html>