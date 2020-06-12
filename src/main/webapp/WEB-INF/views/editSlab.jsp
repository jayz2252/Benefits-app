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
							<h4 class="h4-responsive">Edit Income Tax Slabs</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>
					<div class="col-md-12">
					<form action="save" method="POST">
						<c:choose>
							<c:when test="${mode eq 'edit'}">
								<b>Slab : </b>
								<input type="text" value="${slab.ctgName}" name="categoryName" 
									required="true" />
								<b>Tax : </b>
								<input type="number" value="${slab.ctgValue}" name="categoryValue"
									required="true" />
								<b>FY : </b>${slab.fiscalYear} </br>
								</br>
								<input type="hidden" value="${slab.incomeTaxSlabId}"
									name="slabId" />
								<input type="hidden" value="${mode}" name="mode" />
							</c:when>
							<c:otherwise>
								<b>Slab : </b>
								<input type="text" value="${slab.ctgName}" name="categoryName"
									required="true" />
								<b>Tax : </b>
								<input type="number" value="${slab.ctgValue}" name="categoryValue"
									required="true" />
								<b>FY : </b>
								<select id="fiscalYear" name="fiscalYear" class="form-control">
									<option>2016-17</option>
									<option>2017-18</option>
									<option>2018-19</option>
									<option>2019-20</option>
									<option>2020-21</option>
								</select>
								<label for="fiscalYear"></label>
								<input type="hidden" value="${mode}" name="mode" />
							</c:otherwise>
						</c:choose>
						<a class="btn yellow darken-3" href="<%=request.getContextPath()%>/home/controlPanel/masterData/itslabs">Cancel</a>
						<input type="submit" class="btn btn-primary" value="Save Slab" />
					</form>
					</div>
				</div>
			</div>
			</section>
		</div>
	</div>

</body>
</html>