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
							<h4 class="h4-responsive">Group Health Insurance-Monthly
								Report</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>


					<form action="insMonthlyEmployeeList" method="POST">
						<div class="row">
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">

										<select name="insEmployeeMonth">
											<option value="01"
												${month == "01" ? "selected='selected'" : ""}>January</option>
											<option value="02"
												${month == "02" ? "selected='selected'" : ""}>February</option>
											<option value="03"
												${month == "03" ? "selected='selected'" : ""}>March</option>
											<option value="04"
												${month == "04" ? "selected='selected'" : ""}>April</option>
											<option value="05"
												${month == "05" ? "selected='selected'" : ""}>May</option>
											<option value="06"
												${month == "06" ? "selected='selected'" : ""}>June</option>
											<option value="07"
												${month == "07" ? "selected='selected'" : ""}>July</option>
											<option value="08"
												${month == "08" ? "selected='selected'" : ""}>August</option>
											<option value="09"
												${month == "09" ? "selected='selected'" : ""}>September</option>
											<option value="10"
												${month == "10" ? "selected='selected'" : ""}>October</option>
											<option value="11"
												${month == "11" ? "selected='selected'" : ""}>November</option>
											<option value="12"
												${month == "12" ? "selected='selected'" : ""}>December</option>

										</select> <label for="insEmployeeMonth">Select Month</label>
									</div>

									<div class="col-md-6 padding_left_0">
										<select name="insEmployeeYear" id="selectElementId">
											<option></option>
										</select> <label for="insEmployeeYear">Select Year</label>
									</div>

									<div class="col-md-6 padding_left_0">
										<select name="planName" id="planName">


											<c:forEach items="${insPlans}" var="insPlan">
												<option value="${insPlan.insPlanId}">${insPlan.planName}</option>
											</c:forEach>

										</select> <label for="planName">Select Plan</label>
									</div>
								</div>
							</div>

						</div>
						<div class="row">
							<div class="md-form margin_bottom_0">
								<div class="col-md-6 padding_left_0">
									<input type="submit" class="btn btn-primary"
										value="Download Report">
								</div>
							</div>
						</div>
					</form>

				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>

	<script>
		var min = 2000, max = 2070, select = document
				.getElementById('selectElementId');

		for (var i = min; i <= max; i++) {
			var opt = document.createElement('option');
			opt.value = i;
			opt.innerHTML = i;
			select.appendChild(opt);
		}
		
		select.value = new Date().getFullYear();
	</script>


</body>
</html>