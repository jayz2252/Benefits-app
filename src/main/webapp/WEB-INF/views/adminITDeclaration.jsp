<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="/resources/img/favicon.ico">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
    $('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
        localStorage.setItem('activeTab', $(e.target).attr('href'));
    });
    var activeTab = localStorage.getItem('activeTab');
    if(activeTab){
        $('#myTab a[href="' + activeTab + '"]').tab('show');
    	}
	});
	
	
	function elementById(eId) {
		return document.getElementById(eId);
	}

	function setValue(eId, val) {
		var obj = elementById(eId);
		obj.value = val;
	}

	function showOrHide(eId) {
		var e = elementById(eId);
		e.style.display = "block";

	}
	function test() {
		showOrHide('addOrEditSectionA');
		setValue('buttonAction','ADD_A');
		
	}
</script>
<style>
body{
	background: #f1f8fb;
}
.admin_it_main{
	width: 1200px;
    margin: auto;
    padding: 10px;
    box-shadow: 0 0 8px #ccc;
    box-sizing: border-box;
    min-height: 100vh;
    background: #fff;
}

.nav-tabs {
    border-bottom: 0 !important;
}

.tab-content{
	width: 100%;
	padding: 10px;
	border: 1px solid #f1f1f1;
}
.nav-tabs>li>a {
    padding: 10px 15px;
    background: #dde9ec;
    color: #005a75;
    text-transform: capitalize;
}
.nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover {
    color: #fff;
    cursor: default;
    background-color: #005a75;
    border: 1px solid #ddd;
    border-bottom-color: transparent;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Section A and Section B</title>
</head>
<body>
<div class="admin_it_main">
<div class="admin_it_inner_main">
	<ul class="nav nav-tabs" id="myTab">
		<li class="tab active"><a data-toggle="tab" href="#sectionA">section A</a></li>
		<li class="tab"><a data-toggle="tab" href="#sectionB">section B</a></li>
		<!-- <li class="tab"><a data-toggle="tab" href="#sectionC">section C</a></li> -->
		<li class="tab"><a data-toggle="tab" href="#sectionD">section D</a></li>
		<li class="tab"><a data-toggle="tab" href="#sectionE">section E</a></li>
		<li class="tab"><a data-toggle="tab" href="#sectionF">section F</a></li>
		<li class="tab"><a data-toggle="tab" href="#sectionG">section G</a></li>
	</ul>

	<div class="tab-content">
		<div id="sectionA" class="tab-pane active">
			<h3>Section A</h3>
			<c:url var="addInvestment"
				value="/ITdeclaration/saveNewInvestmentForA"></c:url>
			<form:form action="${addInvestment}"
				commandName="iTDeclarationFeilds"
				modelAttribute="iTDeclarationFeilds">
				<div class="row">
					<div class="col-md-12">

						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Investment Descriptions</th>
									<th>Section</th>
									<th>Field Index</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${feilds1}" var="itInvestments" varStatus="i" >
									<tr>

										<td><input type="text" class="form-control" id="description"
											name="description" value="${itInvestments.description}">
										</td>
										<td><input type="text" class="form-control" id="section" name="section"
											value="${itInvestments.section}"></td>
											<td>
												<input type="text" class="form-control" id="fieldIndexA" name="fieldIndexA" value="${itInvestments.fieldIndex}" readonly="true">
											</td>
										<td><button type="button" value="EDIT" class="btn btn-info"
												onclick="showOrHide('EditSectionA');setValue('editsectA','${itInvestments.section}');setValue('editdescA','${itInvestments.description}');setValue('investmentId','${itInvestments.investmentId}');setValue('buttonAction','EDIT')">EDIT</button>
										<button type="submit" value="DELETE" class="btn btn-danger"
												onclick="setValue('buttonAction','DELETE');setValue('investmentId','${itInvestments.investmentId}')">DELETE</button></td>
										


									</tr>
								</c:forEach>
								<tr>
								<td colspan="4"><input type="button" value="ADD" class="btn btn-success"
											onclick="test();" /></td>
								</tr>
								<tr style="display: none" id="addOrEditSectionA">
									<td><input type="text" class="form-control" name='adddescA' id="adddescA"  size="10" /></td>
									<td><input type="text" class="form-control" name='addsectA' id="addsectA"/></td>
									<!-- <td><input type="number" class="form-control" name='fieldIndexA' id="fieldIndexA"/></td> -->
									<td><input type="submit" value="SAVE" class="btn btn-success"
										 /></td>

								</tr>
								
								<tr style="display: none" id="EditSectionA">
									<td><input type="text" class="form-control" name='editdescA' id="editdescA" size="10" id="desc1"/></td>
									<td><input type="text" class="form-control" name='editsectA' id="editsectA"/></td>
									<td><input type="submit" value="SAVE"
										
										style="position: absolute; margin: 0px 0px 0px 658px;" /></td>

								</tr>
							</tbody>
						</table>

						<input type="hidden" id="buttonAction" name="buttonAction">
						<input type="hidden" id="investmentId" name="investmentId">
						<input type="hidden" id="rowdesc" name="rowdesc">
						<input type="hidden" id="rowsection" name="rowsection">




					</div>
				</div>
			</form:form>
		</div>
		<div id="sectionB" class="tab-pane">
			<h3>Section B</h3>
			<c:url var="addInvestment"
				value="/ITdeclaration/saveNewInvestmentForB"></c:url>
			<form:form action="${addInvestment}"
				commandName="iTDeclarationFeilds"
				modelAttribute="iTDeclarationFeilds">
				<div class="row">
					<div class="col-md-12">

						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Investment Descriptions</th>
									<th>Section</th>
									<th>Field Index</th>
									<th colspan="2">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${feilds2}" var="itInvestments" varStatus="i">
									<tr>

										<td><input type="text" class="form-control" id="description"
											name="description" value="${itInvestments.description}" >
										</td>
										<td><input type="text" class="form-control" id="section" name="section"
											value="${itInvestments.section}"></td>
											<td>
												<input type="text" class="form-control" id="fieldIndexB" name="fieldIndexB" value="${itInvestments.fieldIndex}" readonly="true">
											</td>
										<td><button type="button" value="EDIT" class="btn btn-info"
												onclick="showOrHide('EditSectionB');setValue('editsectB','${itInvestments.section}');setValue('editdescB','${itInvestments.description}');setValue('buttonAction1','EDIT');setValue('investmentId1','${itInvestments.investmentId}')">EDIT</button>
										<button type="submit" value="DELETE" class="btn btn-danger"
												onclick="setValue('buttonAction1','DELETE');setValue('investmentId1','${itInvestments.investmentId}')">DELETE</button></td>
										

									</tr>
								</c:forEach>
								
								<tr>
								<td  colspan="4"><input type="button" value="ADD" class="btn btn-success"
											onclick="showOrHide('addOrEditSectionB');setValue('buttonAction1','ADD_B');" /></td>

								</tr>
								
								<tr style="display: none" id="addOrEditSectionB">
									<td><input type="text" class="form-control" name='adddescB' id="adddescB" size="10" /></td>
									<td><input type="text" class="form-control" name='addsectB' id="addsectB" /></td>
									<td><input type="submit" value="SAVE"
										onclick="showOrHide('addOrEditSection')" /></td>

								</tr>
								<tr style="display: none" id="EditSectionB">
									<td><input type="text" class="form-control" name='editdescB' id="editdescB" size="10" /></td>
									<td><input type="text" class="form-control" name='editsectB' id="editsectB"  /></td>
									<td><input type="submit" value="SAVE"
										onclick="showOrHide('EditSectionB')"
										style="position: absolute; margin: 0px 0px 0px 658px;" /></td>

								</tr>
							</tbody>
						</table>

						<input type="hidden" id="buttonAction1" name="buttonAction1">
						<input type="hidden" id="investmentId1" name="investmentId1">
						



					</div>
				</div>
			</form:form>
		</div>
		<!-- Section D 4--->
				 <div id="sectionD" class="tab-pane">
			<h3>Section D</h3>
			<c:url var="addHousingLoan"
				value="/ITdeclaration/saveNewInvestmentForD"></c:url>
			<form:form action="${addHousingLoan}"
				commandName="iTHouseLoanFields"
				modelAttribute="iTHouseLoanFields">
				<div class="row">
					<div class="col-md-12">

						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Investment Descriptions</th>
									<th>Type</th>
									<th>Field Index</th>
									<th colspan="2">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${fields4}" var="itHousingLoan" varStatus="i">
									<tr>

										<td><input type="text" class="form-control" id="description"
											name="description" value="${itHousingLoan.fieldLabel}" >
										</td>
										<td><input type="text" class="form-control" id="section" name="section"
											value="${itHousingLoan.type}"></td>
											<td>
												<input type="text" class="form-control" id="fieldIndexD" name="fieldIndexD" value="${itHousingLoan.fieldIndex}" readonly="true">
											</td>
										<td><button type="button" value="EDIT" class="btn btn-info"
												onclick="showOrHide('EditSectionD');setValue('editsectD','${itHousingLoan.fieldLabel}');setValue('edittypeD','${itHousingLoan.type}');setValue('buttonAction4','EDIT');setValue('investmentId4','${itHousingLoan.houseLoadFieldId}')">EDIT</button>
										<button type="submit" value="DELETE" class="btn btn-danger"
												onclick="setValue('buttonAction4','DELETE');setValue('investmentId4','${itHousingLoan.houseLoadFieldId}')">DELETE</button></td>
										

									</tr>
								</c:forEach>
								
								<tr>
								<td colspan="4"><input type="button" value="ADD" class="btn btn-success"
											onclick="showOrHide('addOrEditSectionD');setValue('buttonAction4','ADD_D');" /></td>

								</tr>
								
								<tr style="display: none" id="addOrEditSectionD">
									<td><input type="text" class="form-control" name='adddescD' id="adddescD" size="10" /></td>
									<td>
									<select class="form-control" id="addsectD" name="addsectD">
											<option value="text">Text</option>
											<option value="date">Date</option>
											<option value="textarea">Textarea</option>
  											<option value="month">Month</option>
  											<option value="year">Year</option>
  											<option value="number">Amount</option>
  											
									</select>
									<!-- <input type="text" class="form-control" name='addsectD' id="addsectD" /></td> -->
									<td><input type="submit" value="SAVE"
										onclick="showOrHide('addOrEditSection')" /></td>

								</tr>
								<tr style="display: none" id="EditSectionD">
									<td><input type="text" class="form-control" name='editsectD' id="editsectD" size="10" /></td>
									<td>
									<select class="form-control" id="edittypeD" name="edittypeD" >
											<option value="text">Text</option>
											<option value="textarea">Textarea</option>
  											<option value="month">Month</option>
  											<option value="year">Year</option>
  											<option value="number">Amount</option>
  											<option value="date">Date</option>
									</select>
									 </td>
									<td><input type="submit" value="SAVE"
										onclick="showOrHide('EditSectionD')"
										style="position: absolute; margin: 0px 0px 0px 658px;" /></td>

								</tr>
							</tbody>
						</table>

						<input type="hidden" id="buttonAction4" name="buttonAction4">
						<input type="hidden" id="investmentId4" name="investmentId4">
						



					</div>
				</div>
			</form:form>
		</div> 
		 
			<!-- Section E 5--->
				<div id="sectionE" class="tab-pane">
			<h3>Section E</h3>
			<c:url var="addHousingLoan"
				value="/ITdeclaration/saveNewInvestmentForE"></c:url>
			<form:form action="${addHousingLoan}"
				commandName="iTHouseLoanFields"
				modelAttribute="iTHouseLoanFields">
				<div class="row">
					<div class="col-md-12">

						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Investment Descriptions</th>
									<th>Type</th>
									<th>Field Index</th>
									<th colspan="2">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${fields5}" var="itHousingLoan" varStatus="i">
									<tr>

										<td><input type="text" class="form-control" id="description"
											name="description" value="${itHousingLoan.fieldLabel}" >
										</td>
										<td>${itHousingLoan.type}<%-- <input type="text" class="form-control" id="section" name="section"
											value="${itHousingLoan.type}"> --%></td>
											<td>
												<input type="text" class="form-control" id="fieldIndexE" name="fieldIndexE" value="${itHousingLoan.fieldIndex}" readonly="true">
											</td>
										<td><button type="button" value="EDIT" class="btn btn-info"
												onclick="showOrHide('EditSectionE');setValue('editsectE','${itHousingLoan.fieldLabel}');setValue('edittypeE','${itHousingLoan.type}');setValue('buttonAction5','EDIT');setValue('investmentId5','${itHousingLoan.houseLoadFieldId}')">EDIT</button>
										<button type="submit" value="DELETE" class="btn btn-danger"
												onclick="setValue('buttonAction5','DELETE');setValue('investmentId5','${itHousingLoan.houseLoadFieldId}')">DELETE</button></td>
										

									</tr>
								</c:forEach>
								
								<tr>
								<td colspan="4"><input type="button" value="ADD" class="btn btn-success"
											onclick="showOrHide('addOrEditSectionE');setValue('buttonAction5','ADD_E');" /></td>

								</tr>
								
								<tr style="display: none" id="addOrEditSectionE">
									<td><input type="text" class="form-control" name='adddescE' id="adddescE" size="10" /></td>
									<td>
									<select class="form-control" id="addsectE" name="addsectE">
											<option value="text">Text</option>
											<option value="textarea">Textarea</option>
  											<option value="month">Month</option>
  											<option value="year">Year</option>
  											<option value="number">Amount</option>
  											<option value="date">Date</option>
									</select>
									<!-- <input type="text" class="form-control" name='addsectE' id="addsectE" /> -->
									</td>
									<td><input type="submit" value="SAVE"
										onclick="showOrHide('addOrEditSection')" /></td>

								</tr>
								<tr style="display: none" id="EditSectionE">
									<td><input type="text" class="form-control" name='editsectE' id="editsectE" size="10" /></td>
									<td>
									
									<select class="form-control" id="edittypeE" name="edittypeE" >
											<option value="text">Text</option>
											<option value="textarea">Textarea</option>
  											<option value="month">Month</option>
  											<option value="year">Year</option>
  											<option value="number">Amount</option>
  											<option value="date">Date</option>
									</select>
									
									</td>
									<td><input type="submit" value="SAVE"
										onclick="showOrHide('EditSectionE')"
										style="position: absolute; margin: 0px 0px 0px 658px;" /></td>

								</tr>
							</tbody>
						</table>

						<input type="hidden" id="buttonAction5" name="buttonAction5">
						<input type="hidden" id="investmentId5" name="investmentId5">
						



					</div>
				</div>
			</form:form>
		</div>
		
		<!-- Section F 6 --->
				
				<div id="sectionF" class="tab-pane">
			<h3>Section F</h3>
			<c:url var="addHousingLoan"
				value="/ITdeclaration/saveNewInvestmentForF"></c:url>
			<form:form action="${addHousingLoan}"
				commandName="iTHouseLoanFields"
				modelAttribute="iTHouseLoanFields">
				<div class="row">
					<div class="col-md-12">

						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Investment Descriptions</th>
									<th>Type</th>
									<th>Field Index</th>
									<th colspan="2">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${fields6}" var="itHousingLoan" varStatus="i">
									<tr>

										<td><input type="text" class="form-control" id="description"
											name="description" value="${itHousingLoan.fieldLabel}" >
										</td>
										<td>${itHousingLoan.type}<%-- <input type="text" class="form-control" id="section" name="section"
											value="${itHousingLoan.type}"> --%></td>
											
											<td>
												<input type="text" class="form-control" id="fieldIndexF" name="fieldIndexF" value="${itHousingLoan.fieldIndex}" readonly="true">
											</td>
											
										<td><button type="button" value="EDIT" class="btn btn-info"
												onclick="showOrHide('EditSectionF');setValue('editsectF','${itHousingLoan.fieldLabel}');setValue('edittypeF','${itHousingLoan.type}');setValue('buttonAction6','EDIT');setValue('investmentId6','${itHousingLoan.houseLoadFieldId}')">EDIT</button>
										<button type="submit" value="DELETE" class="btn btn-danger"
												onclick="setValue('buttonAction6','DELETE');setValue('investmentId6','${itHousingLoan.houseLoadFieldId}')">DELETE</button></td>
										

									</tr>
								</c:forEach>
								
								<tr>
								<td colspan="4"><input type="button" value="ADD" class="btn btn-success"
											onclick="showOrHide('addOrEditSectionF');setValue('buttonAction6','ADD_F');" /></td>

								</tr>
								
								<tr style="display: none" id="addOrEditSectionF">
									<td><input type="text" class="form-control" name='adddescF' id="adddescF" size="10" /></td>
									<td>
									<select class="form-control" id="addsectF" name="addsectF">
											<option value="text">Text</option>
											<option value="textarea">Textarea</option>
  											<option value="month">Month</option>
  											<option value="year">Year</option>
  											<option value="number">Amount</option>
  											<option value="date">Date</option>
									</select>
								<!-- 	<input type="text" class="form-control" name='addsectB' id="addsectB" />
									 -->
									</td>
									<td><input type="submit" value="SAVE"
										onclick="showOrHide('addOrEditSection')" /></td>

								</tr>
								<tr style="display: none" id="EditSectionF">
									<td><input type="text" class="form-control" name='editsectF' id="editsectF" size="10" /></td>
									<td>
									<select class="form-control" id="edittypeF" name="edittypeF" >
											<option value="text">Text</option>
											<option value="textarea">Textarea</option>
  											<option value="month">Month</option>
  											<option value="year">Year</option>
  											<option value="number">Amount</option>
  											<option value="date">Date</option>
									</select>
									
									</td>
									<td><input type="submit" value="SAVE"
										onclick="showOrHide('EditSectionB')"
										style="position: absolute; margin: 0px 0px 0px 658px;" /></td>

								</tr>
							</tbody>
						</table>

						<input type="hidden" id="buttonAction6" name="buttonAction6">
						<input type="hidden" id="investmentId6" name="investmentId6">
						



					</div>
				</div>
			</form:form>
		</div>
		
		<!-- Section G 7--->
				
				<div id="sectionG" class="tab-pane">
			<h3>Section G</h3>
			<c:url var="addInvestment"
				value="/ITdeclaration/saveNewInvestmentForG"></c:url>
			<form:form action="${addInvestment}"
				commandName="iTDeclarationFeilds"
				modelAttribute="iTDeclarationFeilds">
				<div class="row">
					<div class="col-md-12">

						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Investment Descriptions</th>
									<th>Type</th>
									<th>Field Index</th>
									<th colspan="2">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${fields7}" var="itHousingLoan" varStatus="i">
									<tr>

										<td><input type="text" class="form-control" id="description"
											name="description" value="${itHousingLoan.fieldLabel}" >
										</td>
										<td>${itHousingLoan.type}<%-- <input type="text" class="form-control" id="section" name="section"
											value="${itHousingLoan.type}"> --%></td>
											
											<td>
												<input type="text" class="form-control" id="fieldIndexG" name="fieldIndexG" value="${itHousingLoan.fieldIndex}" readonly="true">
											</td>
										<td><button type="button" value="EDIT" class="btn btn-info"
												onclick="showOrHide('EditSectionG');setValue('editsectG','${itHousingLoan.fieldLabel}');setValue('edittypeG','${itHousingLoan.type}');setValue('buttonAction7','EDIT');setValue('investmentId7','${itHousingLoan.houseLoadFieldId}')">EDIT</button>
										<button type="submit" value="DELETE" class="btn btn-danger"
												onclick="setValue('buttonAction7','DELETE');setValue('investmentId7','${itHousingLoan.houseLoadFieldId}')">DELETE</button></td>
										

									</tr>
								</c:forEach>
								
								<tr>
								<td colspan="4"><input type="button" value="ADD" class="btn btn-success"
											onclick="showOrHide('addOrEditSectionG');setValue('buttonAction7','ADD_G');" /></td>

								</tr>
								
								<tr style="display: none" id="addOrEditSectionG">
									<td><input type="text" class="form-control" name='adddescG' id="adddescG" size="10" /></td>
									<td>
									<select class="form-control" id="addsectG" name="addsectG">
											<option value="text">Text</option>
											<option value="textarea">Textarea</option>
  											<option value="month">Month</option>
  											<option value="year">Year</option>
  											<option value="number">Amount</option>
  											<option value="date">Date</option>
									</select>
									<!-- <input type="text" class="form-control" name='addsectG' id="addsectG" /> -->
									</td>
									<td><input type="submit" value="SAVE"
										onclick="showOrHide('addOrEditSection')" /></td>

								</tr>
								<tr style="display: none" id="EditSectionG">
									<td><input type="text" class="form-control" name='editsectG' id="editsectG" size="10" /></td>
									<td>
									<select class="form-control" id="edittypeG" name="edittypeG" >
											<option value="text">Text</option>
											<option value="textarea">Textarea</option>
  											<option value="month">Month</option>
  											<option value="year">Year</option>
  											<option value="number">Amount</option>
  											<option value="date">Date</option>
									</select>
									</td>
									<td><input type="submit" value="SAVE"
										onclick="showOrHide('EditSectionG')"
										style="position: absolute; margin: 0px 0px 0px 658px;" /></td>

								</tr>
							</tbody>
						</table>

						<input type="hidden" id="buttonAction7" name="buttonAction7">
						<input type="hidden" id="investmentId7" name="investmentId7">
						



					</div>
				</div>
			</form:form>
		</div>
		
		
	</div>
</div>
</div>
</body>

</html>