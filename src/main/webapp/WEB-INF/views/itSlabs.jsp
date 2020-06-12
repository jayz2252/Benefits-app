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
							<h4 class="h4-responsive">Income Tax Slabs</h4>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<table class="table striped table-borderd">
								<thead>

									<tr>
										<th>Slab</th>
										<th>Tax %</th>
										<th>FY</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${taxSlabs}" var="slab">
										<tr>
											<td>${slab.ctgName}</td>
											<td>${slab.ctgValue}</td>
											<td>${slab.fiscalYear}</td>
											<td><a
												href="<%=request.getContextPath()%>/home/controlPanel/masterData/itslabs/edit/${slab.incomeTaxSlabId}"><i
													class="fa fa-pencil" aria-hidden="true"></i></a> <a
												href="<%=request.getContextPath()%>/home/controlPanel/masterData/itslabs/delete/${slab.incomeTaxSlabId}"><i
													class="fa fa-trash-o" aria-hidden="true"></i></a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							<a
								href="<%=request.getContextPath()%>/home/controlPanel/masterData/itslabs/add"
								class="btn btn-primary">Add New</a>
						</div>
					</div>


				</div>


			</div>
			</section>

		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
		function generateRows() {
			var d = document.getElementById("div");
			d.innerHTML += "<p><input type='text' value=' 'name='categoryName' id='categoryName'><label for='categoryName'>Slab</label></p>";
			d.innerHTML += "<p><input type='text' name='Tax' value='' id='categoryValue' name='categoryValue'><label for='categoryValue'>Tax</label></p>";
			d.innerHTML += "<p><select id='fiscal_year'> <option value='2017' id='fiscal_year'>2017</option></select><label for='fiscal_year'></label></p>";
			/* d.innerHTML+="<p><select id='fiscal_year' name='fiscal_year'><option>1</option></select><label for='ctgCount' id='lblCtgCount'>No. of Categories</label></p>"; */
		}

		$('.datepicker').pickadate({
			selectMonths : true, // Creates a dropdown to control month
			selectYears : 15
		// Creates a dropdown of 15 years to control year
		});
	</script>
</body>
</html>