<%@page import="com.speridian.benefits2.model.pojo.BenefitPlan"%>
<%@page import="com.speridian.benefits2.service.BenefitPlanService"%>
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlanEmployee"%>
<%@page import="com.speridian.benefits2.model.pojo.Employee"%>
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
function myFunction() {
	var newTaxInfo=`
		<div style="margin:50px;background-color:rgb(241,241,241);padding:20px">
	
		<span> <b>Please find the below benefits applicable under the new tax slab:</b>  <p><b>Eligible Benefits</b></p>   </span>
		<ul>
		<li>Meal Pass Card</li>
		<li>Vehicle running and maintenance </li></ul>
		</br>
		<span><b>Non Eligible Benefits:-</b>
		<p>Any individual opting to be taxed under the new tax regime from FY 2020-21 onwards will have to give up certain exemptions and deductions.</p></span>
		
		 <p>Here is the list of exemptions and deductions that a taxpayer will have to give up while choosing the new tax regime.<p>
		 <ul><li>Leave Travel Allowance (LTA)</li>
		<li>House Rent Allowance (HRA)</li>
		<li>Conveyance</li>
		<li>Relocation allowance</li>
		<li> Helper allowance</li>
		<li>Children education allowance</li>
		<li>Other special allowances [Section 10(14)]</li>
		<li>Standard deduction</li>
		<li> Professional tax</li>
		<li> Interest on housing loan (Section 24)</li>
		<li> Chapter VI-A deduction (80C,80D, 80E and so on) (Except Section 80CCD(2) and 80JJA)</li></ul>

		<span><h5><b>Disclaimer:-</b></h5>
		<ul>
		<li>Once enrolled, the tax regime cannot be rolled back in this FY.</li>
		<li>I read and understood the conditions, and agreed to abide the same. Please click on submit to proceed.
		</li></ul>

		</span>

		</div>`;
		
		var oldTaxInfo=`<div style="margin:50px;background-color:rgb(241,241,241);padding:20px">
			<span><p>The benefits applicable for the old tax regime stands same as before. Please refer flexi benefits policy in O360 for more details.</p> </span>

			<span><h5><b>Disclaimer:-</b></h5>
			<ul>
			<li>Once enrolled, the tax regime cannot be rolled back in this FY.</li>
			<li>I read and understood the conditions, and agreed to abide the same. Please click on submit to proceed.
			</li></ul>

			</span>

			</div>
		
		</div>`;
  var x = document.getElementById("mySelect").value;
  if(x=="2"){
	  document.getElementById("demo").innerHTML = newTaxInfo ;
  }
  else if(x=="1"){
	  document.getElementById("demo").innerHTML = oldTaxInfo ;
	 
  }else{
	  document.getElementById("demo").innerHTML = "" ;
  }
  
}
</script>
<body>
	<%@include file="employeeNavBar.jsp"%>



	<div class="card" style="margin-top:60px;margin-left:400px; height: 900px">

		<div class="card-body">
			<h5 class="card-title" style="padding: 20px;background-color: rgb(76,156,176);color:white">Tax Regime For IT Declaration</h5>
			<form action="saveTaxRegime" method="POST">
			<div style="margin:30px"><select name="item" id="mySelect" style="margin:'30px'"
					onchange="myFunction()">
					<option>-------Select Tax Regime---------</option>
					<option value="1">Old Tax Regime</option>
					<option value="2">New Tax Regime</option>

				</select></div>
				 <input type="submit" value="Submit" class="btn btn-primary" style="margin-left: 30px">
			</form>

		</div>
		<p id="demo" style="margin:20px"
			></p>
	</div>



	<%@include file="includeFooter.jsp"%>


</body>
</html>