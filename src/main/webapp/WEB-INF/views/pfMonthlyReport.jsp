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
							<h4 class="h4-responsive">PF-Monthly Report</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>

					<form action="pfMonthlyEmployeeList" method="POST">
						<div class="row">
							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">
									
										<select name="pfEmployeeMonth">
											<option value="01" ${month == "01" ? "selected='selected'" : ""}>January</option>
											<option value="02" ${month == "02" ? "selected='selected'" : ""}>February</option>
											<option value="03" ${month == "03" ? "selected='selected'" : ""}>March</option>
											<option value="04" ${month == "04" ? "selected='selected'" : ""}>April</option>
											<option value="05" ${month == "05" ? "selected='selected'" : ""}>May</option>
											<option value="06" ${month == "06" ? "selected='selected'" : ""}>June</option>
											<option value="07" ${month == "07" ? "selected='selected'" : ""}>July</option>
											<option value="08" ${month == "08" ? "selected='selected'" : ""}>August</option>
											<option value="09" ${month == "09" ? "selected='selected'" : ""}>September</option>
											<option value="10" ${month == "10" ? "selected='selected'" : ""}>October</option>
											<option value="11" ${month == "11" ? "selected='selected'" : ""}>November</option>
											<option value="12" ${month == "12" ? "selected='selected'" : ""}>December</option>
											
										</select> <label for="pfEmployee">Select Month</label>
									</div>
									
									<div class="col-md-6 padding_left_0">
										<select name="pfEmployeeYear" id="selectElementId">
											<option></option>
										</select> <label for="pfEmployeeYear">Select Year</label>
									</div>
									
									
								</div>
							</div>

						</div>
						<div class="row">
							<div class="md-form margin_bottom_0">
								<div class="col-md-6 padding_left_0">
									<input type="submit" class="btn btn-primary" value="Search">
								</div>
							</div>
						</div>
						</form>
						
						<div class="col-md-12">
							<table
							class="tablePlanEmployees table striped table-borderd data_table">
							<thead>
								<tr>
									<th>Employee Code</th>
									<th>Employee Name</th>
									<th>Location</th>
									<th>DOJ-Company</th>
									<th>PF Opted Date</th>
									<th>Date of slab change</th>
									<th>PF Plan at the time of joining</th>
									<th>Current PF plan</th>
									<th>Date of opting VPF (Voluntary PF)</th>
									<th>VPF contribution</th>
									<th>UAN No.</th>
									<th>PF Acc No.</th>
									<th>Aadhar No.</th>
								</tr>
							</thead>
							
							<tbody>
								<c:forEach items="${pfMonthlyList}" var="pfMonthlyList">
									<tr>
										<td>${pfMonthlyList.employee.employeeCode}</td>
										<td>${pfMonthlyList.employee.firstName} ${pfMonthlyList.employee.lastName}</td>
										<td>${pfMonthlyList.employee.parentOffice}</td>
										
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfMonthlyList.employee.dateOfJoin}" /></td>
										
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfMonthlyList.optedDate}" /></td>
												
										<td>
												<c:choose>
												<c:when test="${pfMonthlyList.optedSlab eq 'FIXD'}"> </c:when>
												<c:otherwise><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${pfMonthlyList.updatedDate}"/></c:otherwise>
												</c:choose>											
												</td>
										<td></td>
										
										<td>
										<c:choose>
										<c:when test="${pfMonthlyList.optedSlab eq 'FIXD'}">Fixed</c:when>
										<c:when test="${pfMonthlyList.optedSlab eq 'VRBL'}">Variable</c:when>
										<c:when test="${pfMonthlyList.optedSlab eq 'MDTRY'}">Mandatory</c:when>
										</c:choose></td>
										
										<td></td>
										<td></td>
										
										<td>${pfMonthlyList.uan}</td>
										<td>${pfMonthlyList.pfAcNo}</td>
										<td>${pfMonthlyList.formAadharNo}</td>
							
							
							</tr>
							</c:forEach>
							</tbody>
							</table>
						
						</div>
						
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
	var min = 2000,
    max = 2070,
    select = document.getElementById('selectElementId');

    for (var i = min; i<=max; i++){
       var opt = document.createElement('option');
       opt.value = i;
       opt.innerHTML = i;
       select.appendChild(opt);
    }
    select.value = new Date().getFullYear()
</script>
	
</body>
</html>