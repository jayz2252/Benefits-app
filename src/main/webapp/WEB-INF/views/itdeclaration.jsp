<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" type="text/javascript"
	src="/js/jquery-1.2.6.min.js"></script>
<script language="JavaScript" type="text/javascript"
	src="/js/jquery-ui-personalized-1.5.2.packed.js"></script>
<script language="JavaScript" type="text/javascript"
	src="/js/sprinkle.js"></script>
	<%@include file="include.jsp"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<title>IT Declaration Form</title>
<meta http-equiv="X-UA-Compatible" content="IE=EDGE">
<link rel="icon" type="image/png" href="/resources/img/favicon.ico">
<link rel="stylesheet"
	href="/BenefitsITPortal/resources/css/idfstyle.css"
	type="text/css" media="screen">
<link rel="stylesheet"
	href="/BenefitsITPortal/resources/css/jquery-ui.css"
	type="text/css" media="screen">
<link rel="stylesheet"
	href="/BenefitsITPortal/resources/css/bootstrap.min.css"
	type="text/css" media="screen">
<!-- <script src="/js/jquery-1.10.2.js" type="text/javascript"></script>
        <script src="/js/jquery-ui.js" type="text/javascript"></script>
        <script src="/js/itdeclare.js" type="text/javascript"></script> -->
<script src="<c:url value="/resources/js/jquery-1.10.2.js"/>"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript" src="./javascript.js"></script>
<style>
.myButton {
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #599bb3
		), color-stop(1, #408c99));
	background: -moz-linear-gradient(top, #599bb3 5%, #408c99 100%);
	background: -webkit-linear-gradient(top, #599bb3 5%, #408c99 100%);
	background: -o-linear-gradient(top, #599bb3 5%, #408c99 100%);
	background: -ms-linear-gradient(top, #599bb3 5%, #408c99 100%);
	background: linear-gradient(to bottom, #599bb3 5%, #408c99 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#599bb3',
		endColorstr='#408c99', GradientType=0);
	background-color: #599bb3;
	-moz-border-radius: 8px;
	-webkit-border-radius: 8px;
	border-radius: 5px;
	display: inline-block;
	cursor: pointer;
	color: #ffffff;
	font-family: Arial;
	font-size: 13px;
	font-weight: bold;
	padding: 9px 9px;
	text-decoration: none;
	text-shadow: 0px 1px 0px #3d768a;
}

.myButton:hover {
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #408c99
		), color-stop(1, #599bb3));
	background: -moz-linear-gradient(top, #408c99 5%, #599bb3 100%);
	background: -webkit-linear-gradient(top, #408c99 5%, #599bb3 100%);
	background: -o-linear-gradient(top, #408c99 5%, #599bb3 100%);
	background: -ms-linear-gradient(top, #408c99 5%, #599bb3 100%);
	background: linear-gradient(to bottom, #408c99 5%, #599bb3 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#408c99',
		endColorstr='#599bb3', GradientType=0);
	background-color: #408c99;
}

.myButton:active {
	position: relative;
	top: 1px;
}

.cho_btn_small {
	-moz-box-shadow: 0px 1px 0px 0px #f0f7fa;
	-webkit-box-shadow: 0px 1px 0px 0px #f0f7fa;
	box-shadow: 0px 1px 0px 0px #f0f7fa;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #33bdef
		), color-stop(1, #019ad2));
	background: -moz-linear-gradient(top, #33bdef 5%, #019ad2 100%);
	background: -webkit-linear-gradient(top, #33bdef 5%, #019ad2 100%);
	background: -o-linear-gradient(top, #33bdef 5%, #019ad2 100%);
	background: -ms-linear-gradient(top, #33bdef 5%, #019ad2 100%);
	background: linear-gradient(to bottom, #33bdef 5%, #019ad2 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#33bdef',
		endColorstr='#019ad2', GradientType=0);
	background-color: #33bdef;
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
	border-radius: 6px;
	border: 1px solid #057fd0;
	display: inline-block;
	cursor: pointer;
	color: #ffffff;
	font-family: Arial;
	font-size: 15px;
	font-weight: bold;
	padding: 6px 24px;
	text-decoration: none;
	text-shadow: 0px -1px 0px #5b6178;
}

.cho_btn_small:hover {
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #019ad2
		), color-stop(1, #33bdef));
	background: -moz-linear-gradient(top, #019ad2 5%, #33bdef 100%);
	background: -webkit-linear-gradient(top, #019ad2 5%, #33bdef 100%);
	background: -o-linear-gradient(top, #019ad2 5%, #33bdef 100%);
	background: -ms-linear-gradient(top, #019ad2 5%, #33bdef 100%);
	background: linear-gradient(to bottom, #019ad2 5%, #33bdef 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#019ad2',
		endColorstr='#33bdef', GradientType=0);
	background-color: #019ad2;
}

.cho_btn_small:active {
	position: relative;
	top: 1px;
}

.display_none {
	display: none;
}

.invest_tbl input[type="text"] {
	width: 142px !important;
}

table {
	border-collapse: collapse;
}

#rentDetails input[type="file"] {
	width: 155px;
	margin-top: 5px;
}

#rentDetails tr td:nth-child(7) input {
	width: 78px !important;
}

#rentDetails tr td:nth-child(8) input[type="text"] {
	width: 150px !important;
}

#rentDetails select {
	width: 63px !important;
}

#rentDetails tr td:nth-child(2),#rentDetails tr td:nth-child(3) {
	width: 66px;
}

#rentDetails tr td:nth-child(4) {
	width: 103px;
}

#rentDetails tr td:nth-child(5) {
	width: 81px;
}

#rentDetails tr td:nth-child(6) {
	width: 90px;
}

#rentDetails tr td:nth-child(7) {
	width: 79px !important;
}

#rentDetails tr td:nth-child(8) {
	width: 157px;
}

#rentDetails tr td:nth-child(9) {
	width: 60px;
}

#rentDetails tr td:first-child {
	width: 25px;
}
</style>

<script type="text/javascript">
	/* function myFunction(val) {
	 var test= document.getElementById('section');
	 test.value=val;
	 }
	 */
	 function geturlAFromDocman(docidindex,obj,docid){
			
			
			var list = $('#investmentADocURLList').val();
			var url = list.split(",");
			var upload = null;
			var id = null;
			for (var int = 1; int < url.length; int++) {
				var res = url[int].split('&');
				if(res[2] == docid)
					{
					upload = res[1];
					id = res[2];
					break;
					}
			}
			if(upload != null)
				{
				
				$(obj).attr("href",upload);
				}
			
			
			
		}
	 function geturlBFromDocman(docidindex,obj,docid){
			
			
			var list = $('#investmentBDocURLList').val();
			var url = list.split(",");
			var upload = null;
			var id = null;
			for (var int = 1; int < url.length; int++) {
				var res = url[int].split('&');
				if(res[2] == docid)
					{
					upload = res[1];
					id = res[2];
					break;
					}
			}
			if(upload != null)
				{
				
				$(obj).attr("href",upload);
				}
			
			
			
		}
	
	 function getUploadurlAFromDocman(docidindex,obj,docid){
			
			
			var list = $('#investmentADocURLListUpload').val();
			var url = list.split(",");
			var upload = null;
			var id = null;
			for (var int = 1; int < url.length; int++) {
				var res = url[int].split('&');
				if(res[2] == docid)
					{
					upload = res[1];
					id = res[2];
					break;
					}
			}
			if(upload != null)
				{
				
				$(obj).attr("href",upload);
				}
			
			
			
		}
	 function getUploadurlBFromDocman(docidindex,obj,docid){
			
			
			var list = $('#investmentBDocURLListUpload').val();
			var url = list.split(",");
			var upload = null;
			var id = null;
			for (var int = 1; int < url.length; int++) {
				var res = url[int].split('&');
				if(res[2] == docid)
					{
					upload = res[1];
					id = res[2];
					break;
					}
			}
			if(upload != null)
				{
				
				$(obj).attr("href",upload);
				}
			
			
			
		}
		

</script>
</head>
<body cz-shortcut-listen="true">
	<div
		style="float: right; margin-top: 10px; margin-right: 170px; margin-bottom: 20px;">
		<br>
		<!--<a href="http://benefit.speridian.com/index.php/login/logout">Log Out</a>-->
		<a href="http://benefit.speridian.com/dashboard" class="myButton">&lt;&lt;
			Back</a>
	</div>

	<c:set var="investmentA_count" scope="session" value="1" />
	<c:set var="investmentB_count" scope="session" value="1" />

	<input type="hidden" value="${investmentADocURLList}"
		name="investmentADocURLList" id="investmentADocURLList" />
	<input type="hidden" value="${investmentBDocURLList}"
		name="investmentBDocURLList" id="investmentBDocURLList" />
	<input type="hidden" value="${rentDetailsDocURLList}"
		name="rentDetailsDocURLList" id="rentDetailsDocURLList" />

	<input type="hidden" value="${investmentADocURLListUpload}"
		name="investmentADocURLListUpload" id="investmentADocURLListUpload" />
	<input type="hidden" value="${investmentBDocURLListUpload}"
		name="investmentBDocURLListUpload" id="investmentBDocURLListUpload" />
	<input type="hidden" value="${rentDetailsDocURLListUpload}"
		name="rentDetailsDocURLListUpload" id="rentDetailsDocURLListUpload" />

	<c:url var="addNewInvestment" value="/ITdeclaration/saveNewInvestment"></c:url>
	<div style="width: 100%; float: left;">
		<form:form id="it_declare" action="${addNewInvestment}" method="post"
			enctype="multipart/form-data" modelAttribute="iTEmployee">
			<input type="hidden" name="itd_id" id="itd_id" value="160">
			<table width="980" border="0" cellspacing="0" cellpadding="0"
				class="tax_table" style="margin: auto !important;">
				<tbody>
					<tr>
						<td class="head">INCOME TAX DECLARATION FORM FOR FINANCIAL
							YEAR ${fiscalYear} <br> <span
							style="font-weight: none; font-size: 12px;">(Please be
								informed that this link serves only as a data entry towards your
								investment declaration and there is no provision to upload the
								documents)</span>

						</td>
					</tr>
					<tr>
						<td align="left" valign="top"><table border="0"
								cellspacing="0" cellpadding="0" class="table">
								<tbody>
									<tr>
										<td width="29%">Employee Code</td>
										<td colspan="3">${iTEmployee.employeeDetails.employeeCode}</td>
									</tr>
									<tr>
										<td>Employee Name</td>
										<td colspan="3">${iTEmployee.employeeDetails.firstName}
											${iTEmployee.employeeDetails.lastName}</td>
									</tr>
									<tr>
										<td>Permanent Address(Compulsory for the purpose of HRA
											Exemption) <font color="#FF0000">*</font>
										</td>
										<td colspan="3"><form:textarea
												path="itYearlyEmployeeDeclaration.permanentAddress" id="q22"
												style="width: 650px;" required=""></form:textarea></td>
									</tr>
									<tr>
										<td>DOJ</td>

										<fmt:formatDate
											value="${iTEmployee.employeeDetails.dateOfJoin}"
											var="fmtDate" type="date" />
										<td width="30%">
											<!--<font color="#FF0000">16/Oct/2013</font>--> <input
											type="text" readonly="readonly" value="${fmtDate}"
											name="emp_doj" id="emp_doj" value="" required=""
											style="text-align: left; height: 25px; width: 180px;">
										</td>
										<td width="11%">DOB</td>
										<td width="30%"><fmt:formatDate type="date"
												value="${iTEmployee.employeeDetails.dateOfBirth}" /> &nbsp;</td>
									</tr>

									<tr>
										<td>PAN No. (Compulsory)<font color="#FF0000">*</font></td>
										<td><form:input type="text"
												path="itYearlyEmployeeDeclaration.pan" id="q25"
												maxlength="15" style="height: 25px; width: 180px;"
												required=""></form:input> &nbsp;</td>
										<td>Gender</td>
										<td>${iTEmployee.employeeDetails.gender}&nbsp;</td>
									</tr>
									<tr>
										<td colspan="4" class="subhead">Contact Details</td>
									</tr>
									<tr>
										<td>Mobile No. (Compulsory)<font color="#FF0000">*</font></td>
										<td colspan="2">Phone No &amp; Extn.</td>
										<td>Email ID</td>
									</tr>
									<tr>
										<td>+91-&nbsp; <form:input type="text"
												path="itYearlyEmployeeDeclaration.personalMobileNumber"
												id="q26" style="height: 25px;" maxlength="10" value=""
												onkeypress="return isNumberKey(event)" readonly="readonly"></form:input></td>
										<td colspan="2"><form:input type="text"
												path="itYearlyEmployeeDeclaration.officePhoneNumber"
												id="q27" style="height: 25px;" maxlength="12"
												readonly="readonly"></form:input> &nbsp; <form:input
												type="text"
												path="itYearlyEmployeeDeclaration.officePhoneNumberExt"
												id="q28" style="height: 25px; width: 75px;" maxlength="5"
												readonly="readonly"></form:input></td>
										<td>${iTEmployee.employeeDetails.email}</td>
									</tr>
									<tr>
										<td colspan="2">Location of Posting</td>
										<td colspan="2">Alternate Email ID</td>
									</tr>
									<tr>
										<td colspan="2">${iTEmployee.employeeDetails.parentOffice}</td>
										<td colspan="2"><form:input type="text"
												path="itYearlyEmployeeDeclaration.alternateEmail" id="q34"
												maxlength="60"
												style="height: 25px; width: 300px; text-align: left"
												readonly="readonly" onblur="checkEmail(this.value)"></form:input></td>
									</tr>
								</tbody>
							</table></td>
					</tr>
					<tr>
						<td class="note">I undertake to make the undermentioned
							investments under various sections of the Income Tax Act on or
							before 15-01-2017 and submit its proof to the Company.</td>
					</tr>
					<tr>
						<td valign="top"><table border="0" cellspacing="0"
								cellpadding="0" class="table">
								<tbody>
									<tr>
										<td width="5%" align="center" class="smallhead">A</td>
										<td class="subhead">Investment Description</td>
										<td width="12%" align="center" class="subhead">Section</td>
										<td width="18%" class="subhead">Declared Amount</td>
										<td width="18%" class="subhead">Supporting Documents</td>
										<td width="18%" class="subhead">Action</td>

									</tr>
									<tr>
										<td align="center">&nbsp;</td>
										<td colspan="3" class="subhead2">Investments under
											Sections 80C &amp; 80CCC qualifying for deductions upto
											Rs.1.5 Lac.</td>
										<td><span style="font-size: 11px; color: red;">File
												extensions with doc, docx, pdf, jpg, png, jpeg, zip, rar are
												allowed only. If you have more than 1 certificates in
												corresponding investments, please zip the files in to one
												and upload.</span></td>
										<td></td>
									</tr>

									<tr>
										<td colspan="6" style="padding: 0"><c:set var="AIndex"
												value="0" scope="page"></c:set>
											<table class="invest_tbl" id="InvstmntA" style="width: 100%;">
												<tbody>
													<c:forEach var="empInvstmentDetails"
														items="${iTEmployee.empInvestmentDetailsA}"
														varStatus="invstmntDetails">

														<c:set var="AIndex" value="${invstmntDetails.index}"
															scope="page"></c:set>
														<tr class="idrow${AIndex}">
															<td style="width: 29px"></td>
															<td style="width: 92px;"><select
																name="descA${AIndex}"
																onclick="setValue('sectionA${AIndex}',this.options[this.selectedIndex].getAttribute('Asection'));
															setValue('invstmntAId${AIndex}',this.options[this.selectedIndex].getAttribute('AinvstmntId'))">
																	<option
																		value="${empInvstmentDetails.investments.description}"
																		Asection="${empInvstmentDetails.investments.section}"
																		AinvstmntId="${empInvstmentDetails.investments.investmentId}">${empInvstmentDetails.investments.description}</option>
																	<c:forEach items="${ITInvestmentCategoryA}"
																		var="itInvestments_A">
																		<option value="${itInvestments_A.description}"
																			Asection="${itInvestments_A.section}"
																			AinvstmntId="${itInvestments_A.investmentId}">${itInvestments_A.description}
																		</option>

																	</c:forEach>
															</select></td>
															<td style="width: 222px;"><input type="text"
																id="sectionA${AIndex}" name="sectionA${AIndex}"
																value="${empInvstmentDetails.investments.section}">
																<input type="hidden" id="invstmntAId${AIndex}"
																name="invstmntAId${AIndex}"
																value="${empInvstmentDetails.investments.investmentId}">
																<input type="hidden" id="invstmntDetailAId${AIndex}"
																name="invstmntDetailAId${AIndex}"
																value="${empInvstmentDetails.investmentDetailId}"
																readonly="readonly"></td>
															<td style="width: 165px"><span class="WebRupee">Rs</span>
																<input type="text" id="amountA${AIndex}"
																name="amountA${AIndex}"
																value="${empInvstmentDetails.investmentAmount}"
																onkeyup="javscript:validateDecimalAmt(this);"
																onblur="javscript:validateDecimalBlurAmt(this);"
																onchange="totalBlurAmount(this);" readonly="readonly"></td>


															<td><c:choose>
																	<c:when
																		test="${iTEmployee.itYearlyEmployeeDeclaration.mode ne 'DECLARE'}">


																		<c:choose>
																			<c:when
																				test="${iTEmployee.itYearlyEmployeeDeclaration.newFlag eq true }">

		
																								<a href="${investmentADocURLList}" target="blank">
																							<c:choose>
																								<c:when test="${pageStatus eq 'VIEW_PROOF'}">
																						Click Here To Download File
																					</c:when>
																								<c:otherwise>
																					Click Here To Upload File
																					</c:otherwise>
																							</c:choose>
																						</a>

											
																			</c:when>
																			<c:otherwise>
																				<a href="#"
																					onclick="getUploadurlAFromDocman(${AIndex},this,'${empInvstmentDetails.docmanUUID}');"
																					target="blank"> Click here to download file</a>

																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<font color="red">No Docs Uploaded</font>
																	</c:otherwise>

																</c:choose></td>


															<c:set var="investmentA_count" scope="session" value="0" />


														</tr>
													</c:forEach>

													<c:set var="AIndex" value="${AIndex + 1}" scope="page"></c:set>
													<input name="addMoreAInvstmntIndex"
														id="addMoreAInvstmntIndex" type="hidden" value="${AIndex}"></input>
													<input name="addMoreAInvstmntRemoveKeys"
														id="addMoreAInvstmntRemoveKeys" type="hidden" value=""></input>
												</tbody>
											</table></td>
									</tr>



									<tr>
										<td align="center">&nbsp;</td>
										<td colspan="5"><i>- Fees exclude payment towards
												development fees, donation or any payment of similar nature</i></td>
									</tr>
									<tr>
										<td align="center" class="subhead2">&nbsp;</td>
										<td colspan="3" class="subhead2">Total Investments U/S
											80C- Limited to Rs1,50,000/- Only</td>
										<td class="subhead2"><span class="WebRupee">Rs</span>&nbsp;
											<input type="text" name="totAmt" id="totAmt"
											value="${totalA}" readonly=""></td>
										<td>&nbsp;</td>
									</tr>

									<tr>
										<td width="5%" align="center" class="smallhead">B</td>
										<td class="subhead">Investments over and above Rs.1.5 Lac</td>
										<td width="12%" align="center" class="subhead">Section</td>
										<td width="18%" class="subhead">Declared Amount</td>
										<td>Supported Documents</td>
										<td>Action</td>
									</tr>
									<tr>
										<td colspan="6" style="padding: 0"><c:set var="BIndex"
												value="0" scope="page"></c:set>
											<table id="InvstmntB" class="invest_tbl" style="width: 100%;">
												<tbody>
													<c:forEach var="empInvstmentDetails"
														items="${iTEmployee.empInvestmentDetailsB}"
														varStatus="invstmntDetails">

														<c:set var="BIndex" value="${invstmntDetails.index}"
															scope="page"></c:set>
														<tr class="idrow${BIndex}">
															<td style="width: 29px"></td>


															<td><c:choose>
																	<c:when
																		test="${empInvstmentDetails.investments.description eq null}">
																		<span id="DetaildescB${BIndex}"><a
																			href="#openModalB" data-toggle="modal">Click here
																				to select investment</a></span>
																	</c:when>
																	<c:otherwise>

																		<td><span>${empInvstmentDetails.investments.description}</span>
																		</td>


																	</c:otherwise>
																</c:choose></td>



															<td style="width: 222px;"><input type="text"
																id="sectionB${BIndex}" name="sectionB${BIndex}"
																value="${empInvstmentDetails.investments.section}"
																readonly="readonly"> <input type="hidden"
																id="invstmntBId${BIndex}" name="invstmntBId${BIndex}"
																value="${empInvstmentDetails.investments.investmentId}">
																<input type="hidden" id="invstmntDetailBId${BIndex}"
																name="invstmntDetailBId${BIndex}"
																value="${empInvstmentDetails.investmentDetailId}"
																readonly="readonly"></td>
															<td style="width: 165px;"><span class="WebRupee">Rs</span>
																<input type="text" id="amountB${BIndex}"
																name="amountB${BIndex}"
																value="${empInvstmentDetails.investmentAmount}"
																onkeyup="javscript:validateDecimalAmt(this);"
																onblur="javscript:validateDecimalBlurAmt(this);"
																onchange="totalBlurAmount(this);" readonly="readonly"></td>
															<!-- <td align="center"><input type="file" name="q1File"
																id="q1File"> <input type="hidden" value=""
																name="q1h" id="q1h"></td>
																
 -->
															<td><c:choose>
																	<c:when
																		test="${iTEmployee.itYearlyEmployeeDeclaration.mode ne 'DECLARE'}">

																											<c:choose>
													<c:when test="${iTEmployee.itYearlyEmployeeDeclaration.newFlag eq true }">
												
													
																	<input type="hidden"
																		id="investmentBDocmanUUID${BIndex}"
																		value="${investmentbDocmanUUIDs}"
																		name="investmentBDocmanUUID{BIndex}" />
																					<a href="${investmentBDocURLList}"
																		
																		target="blank"> <c:choose>
																			<c:when test="${pageStatus eq 'VIEW_PROOF'}">
																						Click Here To Download File
																					</c:when>
																			<c:otherwise>
																					Click Here To Upload File
																					</c:otherwise>
																		</c:choose></a>
															
													
													</c:when>
													<c:otherwise>
																		<a href="#"
																			onclick="getUploadurlBFromDocman(${BIndex},this,'${empInvstmentDetails.docmanUUID}');"
																			target="blank"> Click here to download file</a>
																			</c:otherwise></c:choose>
																	</c:when>
					
																	<c:otherwise>
																		<font color="red">No Docs Uploaded</font>
																	</c:otherwise>

																</c:choose></td>
															<c:set var="investmentB_count" scope="session" value="0" />



														</tr>
													</c:forEach>

													<c:set var="BIndex" value="${BIndex + 1}" scope="page"></c:set>
													<input name="addMoreBInvstmntIndex"
														id="addMoreBInvstmntIndex" type="hidden" value="${BIndex}"></input>
													<input name="addMoreBInvstmntRemoveKeys"
														id="addMoreBInvstmntRemoveKeys" type="hidden" value=""></input>
												</tbody>
											</table></td>
									</tr>
									<tr>
										<td align="center" class="subhead2">&nbsp;</td>
										<td colspan="3" class="subhead2">Total Investments Over
											Rs1,50,000/- Only</td>
										<td class="subhead2"><span class="WebRupee">Rs</span>&nbsp;
											<input type="text" name="totAmt" id="totAmt"
											value="${totalB}" readonly=""></td>
										<td>&nbsp;</td>
									</tr>


									<tr class="">
										<td align="center" class="smallhead">C</td>
										<td colspan="5" class="subhead">HRA EXEMPTION U/S 10/ 13A</td>
									</tr>
									<tr>
										<td align="center">&nbsp;</td>
										<td colspan="5" class="subhead2">(Applicable in case of
											rented/leased accommodation, not having residential property
											in the same city (Place of work)</td>
									</tr>
									<tr class=" idrow1">
										<td align="center">1</td>
										<td colspan="2">Address:</td>
										<td colspan="3"><form:textarea id="q15"
												path="itYearlyEmployeeDeclaration.rentAddress"
												style="height: 80px; width: 400px;" readonly="readonly"></form:textarea>
											&nbsp;</td>
									</tr>

									<tr>
										<td colspan="6" style="padding: 0"><table
												style="width: 100%">
												<tbody>
													<tr style="background: #E0F2F7">
														<td>&nbsp;</td>
														<td colspan="8">Rent Details (From
															${iTEmployee.fromDate} to ${iTEmployee.toDate})&nbsp;</td>
													</tr>
													<tr style="background: #E0F2F7; font-weight: bold;">
														<td align="center" style="width: 29px">Sl No.</td>
														<td colspan="2" style="width: 114px">Period</td>
														<td style="width: 119px">Monthly&nbsp;Rent&nbsp; (Rs)</td>
														<td style="width: 94px">No. of Months</td>
														<td align="center" style="width: 100px">Metro&nbsp;City&nbsp;(Y/N)</td>
														<td style="width: 100px">Total&nbsp;Rent&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
														<td>Landlord's&nbsp;PAN/Declaration&nbsp;&nbsp;</td>

													</tr>
													<c:set var="currFiscalYr"
														value="${iTEmployee.itYearlyEmployeeDeclaration.fiscalYear}"
														scope="page"></c:set>

													<tr>
														<td colspan="8" style="padding: 0"><c:set
																var="rentDetailsIndex" value="0" scope="page"></c:set> <c:set
																var="sno" value="1" scope="page"></c:set>
															<table id="rentDetails" style="width: 100%;">
																<tbody>
																	<c:forEach var="empRentDetails"
																		items="${iTEmployee.empRentDetails}"
																		varStatus="rentDetails">
																		<c:set var="rentDetailsIndex"
																			value="${rentDetails.index}" scope="page"></c:set>
																		<tr class="rentRow${rentDetailsIndex}">

																			<td align="center">${sno}<input type="hidden"
																				name="itd_rent_details_id${rentDetailsIndex}"
																				id="itd_rent_details_id${rentDetailsIndex}"
																				value="${empRentDetails.rentDetailId}"> <input
																				type="hidden"
																				name="itd_old_document${rentDetailsIndex}"
																				id="itd_old_document1"
																				value="SP2350-Vineesh-Vijayan-Document1.JPG">
																			</td>

																			<td><select name="frmPeriod${rentDetailsIndex}"
																				id="frmPeriod${rentDetailsIndex}"
																				onchange="calculatePeriod(${rentDetailsIndex});"
																				style="width: 85px;">
																					<option value="">---From---</option>
																					<option value="1_${currFiscalYr}"
																						${fn:substring(empRentDetails.fromPeriod,0,1) eq '1'?'selected':''}>APR
																						${currFiscalYr}</option>
																					<option value="2_${currFiscalYr}"
																						${fn:substring(empRentDetails.fromPeriod,0,1) eq '2'?'selected':''}>MAY
																						${currFiscalYr}</option>
																					<option value="3_${currFiscalYr}"
																						${fn:substring(empRentDetails.fromPeriod,0,1) eq '3'?'selected':''}>JUN
																						${currFiscalYr}</option>
																					<option value="4_${currFiscalYr}"
																						${fn:substring(empRentDetails.fromPeriod,0,1) eq '4'?'selected':''}>JUL
																						${currFiscalYr}</option>
																					<option value="5_${currFiscalYr}"
																						${fn:substring(empRentDetails.fromPeriod,0,1) eq '5'?'selected':''}>AUG
																						${currFiscalYr}</option>
																					<option value="6_${currFiscalYr}"
																						${fn:substring(empRentDetails.fromPeriod,0,1) eq '6'?'selected':''}>SEP
																						${currFiscalYr}</option>
																					<option value="7_${currFiscalYr}"
																						${fn:substring(empRentDetails.fromPeriod,0,1) eq '7'?'selected':''}>OCT
																						${currFiscalYr}</option>
																					<option value="8_${currFiscalYr}"
																						${fn:substring(empRentDetails.fromPeriod,0,1) eq '8'?'selected':''}>NOV
																						${currFiscalYr}</option>
																					<option value="9_${currFiscalYr}"
																						${fn:substring(empRentDetails.fromPeriod,0,1) eq '9'?'selected':''}>DEC
																						${currFiscalYr}</option>
																					<option value="10_${currFiscalYr + 1}"
																						${fn:substring(empRentDetails.fromPeriod,0,2) eq '10'?'selected':''}>JAN
																						${currFiscalYr + 1}</option>
																					<option value="11_${currFiscalYr + 1}"
																						${fn:substring(empRentDetails.fromPeriod,0,2) eq '11'?'selected':''}>FEB
																						${currFiscalYr + 1}</option>
																					<option value="12_${currFiscalYr + 1}"
																						${fn:substring(empRentDetails.fromPeriod,0,2) eq '12'?'selected':''}>MAR
																						${currFiscalYr + 1}</option>
																			</select></td>
																			<td><select name="toPeriod${rentDetailsIndex}"
																				id="toPeriod${rentDetailsIndex}"
																				onchange="calculatePeriod(${rentDetailsIndex});"
																				style="width: 85px;">
																					<option value="">---To---</option>
																					<option value="1_${currFiscalYr}"
																						${fn:substring(empRentDetails.toPeriod,0,1) eq '1'?'selected':''}>APR
																						${currFiscalYr}</option>
																					<option value="2_${currFiscalYr}"
																						${fn:substring(empRentDetails.toPeriod,0,1) eq '2'?'selected':''}>MAY
																						${currFiscalYr}</option>
																					<option value="3_${currFiscalYr}"
																						${fn:substring(empRentDetails.toPeriod,0,1) eq '3'?'selected':''}>JUN
																						${currFiscalYr}</option>
																					<option value="4_${currFiscalYr}"
																						${fn:substring(empRentDetails.toPeriod,0,1) eq '4'?'selected':''}>JUL
																						${currFiscalYr}</option>
																					<option value="5_${currFiscalYr}"
																						${fn:substring(empRentDetails.toPeriod,0,1) eq '5'?'selected':''}>AUG
																						${currFiscalYr}</option>
																					<option value="6_${currFiscalYr}"
																						${fn:substring(empRentDetails.toPeriod,0,1) eq '6'?'selected':''}>SEP
																						${currFiscalYr}</option>
																					<option value="7_${currFiscalYr}"
																						${fn:substring(empRentDetails.toPeriod,0,1) eq '7'?'selected':''}>OCT
																						${currFiscalYr}</option>
																					<option value="8_${currFiscalYr}"
																						${fn:substring(empRentDetails.toPeriod,0,1) eq '8'?'selected':''}>NOV
																						${currFiscalYr}</option>
																					<option value="9_${currFiscalYr}"
																						${fn:substring(empRentDetails.toPeriod,0,1) eq '9'?'selected':''}>DEC
																						${currFiscalYr}</option>
																					<option value="10_${currFiscalYr + 1}"
																						${fn:substring(empRentDetails.toPeriod,0,2) eq '10'?'selected':''}>JAN
																						${currFiscalYr + 1}</option>
																					<option value="11_${currFiscalYr + 1}"
																						${fn:substring(empRentDetails.toPeriod,0,2) eq '11'?'selected':''}>FEB
																						${currFiscalYr + 1}</option>
																					<option value="12_${currFiscalYr + 1}"
																						${fn:substring(empRentDetails.toPeriod,0,2) eq '12'?'selected':''}>MAR
																						${currFiscalYr + 1}</option>
																			</select></td>
																			<td>
																				<!--<span class="WebRupee">Rs</span>&nbsp;--> <input
																				type="text" id="RMonthly${rentDetailsIndex}"
																				name="RMonthly${rentDetailsIndex}"
																				style="width: 75px; height: 25px;"
																				onkeyup="javascript:validateDecimalAmt(this);"
																				onblur="javscript:validateDecimalBlurAmt(this);totRent(${rentDetailsIndex});"
																				value="${empRentDetails.monthlyRent}">
																			</td>
																			<td><input type="text"
																				id="RMonths${rentDetailsIndex}"
																				name="RMonths${rentDetailsIndex}"
																				style="width: 40px; height: 25px; text-align: left;"
																				onblur="javascript:totRent(${rentDetailsIndex});"
																				value="${empRentDetails.noOfMonths}" readonly=""></td>
																			<td align="center"><input type="radio"
																				id="RMetro${rentDetailsIndex}"
																				name="RMetro${rentDetailsIndex}" value="Y"
																				${empRentDetails.metroCity eq 'Y'?'checked':''}>
																				Y &nbsp; <input type="radio"
																				id="RMetro${rentDetailsIndex}"
																				name="RMetro${rentDetailsIndex}" value="N"
																				${empRentDetails.metroCity eq 'N'?'checked':''}>
																				N</td>
																			<td><span class="WebRupee">Rs</span>&nbsp; <input
																				type="text" id="RTotal${rentDetailsIndex}"
																				name="RTotal${rentDetailsIndex}"
																				style="width: 85px; height: 25px;"
																				value="${empRentDetails.totalRent}" readonly=""></td>

																			<td><c:choose>
																					<c:when
																						test="${iTEmployee.itYearlyEmployeeDeclaration.mode ne 'DECLARE'}">
																						<a href="${rentDetailsDocURLListUpload}"
																							target="blank"><i>Click Here to Download
																								File</i> </a>
																					</c:when>
																					<c:otherwise>
																						<font color="red">No Docs Uploaded</font>
																					</c:otherwise>

																				</c:choose></td>

																		</tr>
																		<c:set var="sno" value="${sno + 1}" scope="page"></c:set>
																	</c:forEach>
																	<c:set var="rentDetailsIndex"
																		value="${rentDetailsIndex + 1}" scope="page"></c:set>
																	<input name="snoIndex" id="snoIndex" type="hidden"
																		value="${sno}"></input>
																	<input name="addMoreRentDetailsIndex"
																		id="addMoreRentDetailsIndex" type="hidden"
																		value="${rentDetailsIndex}"></input>
																	<input name="addMoreRentDetailsRemoveKeys"
																		id="addMoreRentDetailsRemoveKeys" type="hidden"
																		value=""></input>


																</tbody>

															</table> <%-- 	<table width="100" style="width: 100%;">
																<tbody>
																	<tr>
																		<input type="hidden" id="fiscalYr" name="fiscalYr"
																			value="${currFiscalYr}">
																		<td colspan="8"><input type="button"
																			value="Add New Rent Detail"
																			onclick="addMoreRentDetails()"></td>
																	</tr>
																</tbody>
															</table> --%></td>
													</tr>



													<tr style="background: #E0F2F7;">
														<td colspan="9" align="left"><input type="hidden"
															name="rent_declaration_count" id="rent_declaration_count"
															value="6"> Note: If annual rent exceeds Rs.1 Lac,
															it is mandatory to report PAN number of Landlord.</td>
													</tr>
													<tr style="background: #E0F2F7; font-weight: bold;">
														<td colspan="9" align="right">&nbsp;</td>
													</tr>
												</tbody>
											</table></td>
									</tr>



									<tr class="">
										<td align="center" class="smallhead">D</td>
										<td colspan="5" class="subhead">INTEREST PAYMENT ON
											HOUSING LOAN-I</td>
									</tr>
									<tr>
										<td></td>
										<td colspan="4">Self Occupied Property</td>
										<td><c:choose>
												<c:when
													test="${iTEmployee.itYearlyEmployeeDeclaration.mode ne 'DECLARE'}">


													<a href="${selfOccupiedUrlUpload}" target="blank">Click
														here to download file</a>
												</c:when>
												<c:otherwise>
													<font color="red">No Docs Uploaded</font>
												</c:otherwise>

											</c:choose></td>
									</tr>
									<c:forEach items="${ITInvestmentCategoryD}"
										var="itinvestments_d" varStatus="loop">

										<tr class=" idrow0">
											<td align="center">${loop.index+1}</td>
											<td colspan="4">${itinvestments_d.fieldLabel}</td>

											<c:choose>
												<c:when test="${itinvestments_d.type=='year'}">ye
													<td><form:select id="d${loop.index}"
															path="sectionD[${loop.index}].feildvalue">

														</form:select></td>
													<input type="hidden" name="sectionD[${loop.index}].feildid"
														value="${itinvestments_d.houseLoadFieldId}"
														readonly="readonly" />
													<input type="hidden"
														name="sectionD[${loop.index}].category" value="D"
														readonly="readonly" />
													<input type="hidden"
														name="sectionD[${loop.index}].feildtype"
														value="${itinvestments_d.type}" readonly="readonly" />
												</c:when>
												<c:when test="${itinvestments_d.type=='month'}">mnt
													<td><form:select id="d${loop.index}"
															path="sectionD[${loop.index}].feildvalue">

														</form:select> &nbsp;</td>
													<input type="hidden" name="sectionD[${loop.index}].feildid"
														value="${itinvestments_d.houseLoadFieldId}"
														readonly="readonly" />
													<input type="hidden"
														name="sectionD[${loop.index}].category" value="D"
														readonly="readonly" />
													<input type="hidden"
														name="sectionD[${loop.index}].feildtype"
														value="${itinvestments_d.type}" readonly="readonly" />
												</c:when>
												<c:when test="${itinvestments_d.type=='textarea'}">
													<td colspan=""><form:textarea id="d${loop.index}"
															path="sectionD[${loop.index}].feildvalue"
															style="height: 80px; width: 400px;" readonly="readonly"></form:textarea>
														&nbsp;</td>
													<input type="hidden" name="sectionD[${loop.index}].feildid"
														value="${itinvestments_d.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionD[${loop.index}].category" value="D" />
													<input type="hidden"
														name="sectionD[${loop.index}].feildtype"
														value="${itinvestments_d.type}" />
												</c:when>

												<c:when test="${itinvestments_d.type=='Amount'}">
													<td><input type="number" id="d${loop.index}"
														path="sectionD[${loop.index}].feildvalue"
														readonly="readonly" /></td>
													<input type="hidden" name="sectionD[${loop.index}].feildid"
														value="${itinvestments_d.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionD[${loop.index}].category" value="D" />
													<input type="hidden"
														name="sectionD[${loop.index}].feildtype"
														value="${itinvestments_d.type}" />
												</c:when>
												<c:otherwise>
													<td><form:input
															path="sectionD[${loop.index}].feildvalue"
															id="d${loop.index}" readonly="readonly"></form:input>
														&nbsp;</td>
													<input type="hidden" name="sectionD[${loop.index}].feildid"
														value="${itinvestments_d.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionD[${loop.index}].category" value="D" />
													<input type="hidden"
														name="sectionD[${loop.index}].feildtype"
														value="${itinvestments_d.type}" />
												</c:otherwise>

											</c:choose>


										</tr>
										<c:set var="DIndex" value="${loop.index}" scope="page"></c:set>
									</c:forEach>

									<input name="DLASTIndex" id="DLASTIndex" type="hidden"
										value="${DIndex}"></input>


									<tr class="">
										<td align="center" class="smallhead">E</td>
										<td colspan="4" class="subhead">Pre Construction Interest
											Details</td>
									</tr>




									<c:forEach items="${ITInvestmentCategoryE}"
										var="itInvestments_E" varStatus="loop">
										<tr class=" idrow0">
											<td align="center">${loop.index+1}</td>
											<td colspan="4">${itInvestments_E.fieldLabel}</td>

											<c:choose>
												<c:when test="${itInvestments_E.type=='year'}">
													<td><form:select
															path="sectionE[${loop.index}].feildvalue"
															id="e${loop.index}">

														</form:select></td>
													<input type="hidden" name="sectionE[${loop.index}].feildid"
														value="${itInvestments_E.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionE[${loop.index}].category" value="E" />
													<input type="hidden"
														name="sectionE[${loop.index}].feildtype"
														value="${itInvestments_E.type}" />
												</c:when>
												<c:when test="${itInvestments_E.type=='month'}">
													<td><form:select
															path="sectionE[${loop.index}].feildvalue"
															id="e${loop.index}">

														</form:select> &nbsp;</td>
													<input type="hidden" name="sectionE[${loop.index}].feildid"
														value="${itInvestments_E.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionE[${loop.index}].category" value="E" />
													<input type="hidden"
														name="sectionE[${loop.index}].feildtype"
														value="${itInvestments_E.type}" />
												</c:when>
												<c:when test="${itInvestments_E.type=='textarea'}">
													<td colspan=""><form:textarea id="e${loop.index}"
															path="sectionE[${loop.index}].feildvalue"
															style="heiEht: 80px; width: 400px;" readonly="readonly"></form:textarea>
														&nbsp;</td>
													<input type="hidden" name="sectionE[${loop.index}].feildid"
														value="${itInvestments_E.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionE[${loop.index}].category" value="E" />
													<input type="hidden"
														name="sectionE[${loop.index}].feildtype"
														value="${itInvestments_E.type}" />

												</c:when>
												<c:when test="${itInvestments_E.type=='Amount'}">
													<td><input type="number" id="e${loop.index}"
														path="sectionE[${loop.index}].feildvalue"
														readonly="readonly" /></td>
													<input type="hidden" name="sectionE[${loop.index}].feildid"
														value="${itInvestments_E.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionE[${loop.index}].category" value="E" />
													<input type="hidden"
														name="sectionE[${loop.index}].feildtype"
														value="${itInvestments_E.type}" />
												</c:when>
												<c:otherwise>
													<td><form:input
															path="sectionE[${loop.index}].feildvalue"
															id="e${loop.index}" readonly="readonly"></form:input>
														&nbsp;</td>

													<input type="hidden" name="sectionE[${loop.index}].feildid"
														value="${itInvestments_E.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionE[${loop.index}].category" value="E" />
													<input type="hidden"
														name="sectionE[${loop.index}].feildtype"
														value="${itInvestments_E.type}" />
												</c:otherwise>
											</c:choose>
										</tr>
										<c:set var="EIndex" value="${loop.index}" scope="page"></c:set>
									</c:forEach>

									<input name="ELASTIndex" id="ELASTIndex" type="hidden"
										value="${EIndex}"></input>
									<tr class="">
										<td align="center" class="smallhead">F</td>
										<td colspan="4" class="subhead">INTEREST PAYMENT ON
											HOUSING LOAN-II</td>
									</tr>

									<tr>
										<td></td>
										<td colspan="4">Let out(Rental) property</td>
										<td><c:choose>
												<c:when
													test="${iTEmployee.itYearlyEmployeeDeclaration.mode ne 'DECLARE'}">


													<a href="${letOutUrlUpload}" target="blank">Click here
														to download file</a>
												</c:when>
												<c:otherwise>
													<font color="red">No Docs Uploaded</font>
												</c:otherwise>

											</c:choose></td>
									</tr>

									<c:forEach items="${ITInvestmentCategoryF}"
										var="itInvestments_F" varStatus="loop">
										<tr class=" idrow0">
											<td align="center">${loop.index+1}</td>
											<td colspan="4">${itInvestments_F.fieldLabel}</td>

											<c:choose>
												<c:when test="${itInvestments_F.type=='year'}">
													<td><form:select
															path="sectionF[${loop.index}].feildvalue"
															id="f${loop.index}">
															<form:option value="">--Select Year--</form:option>
															<form:option value="2017">2017</form:option>
															<form:option value="2016">2016</form:option>
															<form:option value="2015">2015</form:option>
															<form:option value="2014">2014</form:option>
															<form:option value="2013">2013</form:option>
															<form:option value="2012">2012</form:option>
															<form:option value="2011">2011</form:option>
															<form:option value="2010">2010</form:option>

														</form:select></td>
													<input type="hidden" name="sectionF[${loop.index}].feildid"
														value="${itInvestments_F.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionF[${loop.index}].category" value="F" />
													<input type="hidden"
														name="sectionF[${loop.index}].feildtype"
														value="${itInvestments_F.type}" />
												</c:when>
												<c:when test="${itInvestments_F.type=='month'}">
													<td><form:select
															path="sectionF[${loop.index}].feildvalue"
															id="f${loop.index}">
															<form:option value="">--select month--</form:option>
															<form:option value="january">january</form:option>
															<form:option value="february">february</form:option>
															<form:option value="march">march</form:option>
															<form:option value="april">april</form:option>
															<form:option value="may">may</form:option>
															<form:option value="june">june</form:option>
															<form:option value="july">july</form:option>
															<form:option value="august">august</form:option>
															<form:option value="september">september</form:option>
															<form:option value="october">october</form:option>
															<form:option value="november">november</form:option>
															<form:option value="december">december</form:option>

														</form:select> &nbsp;</td>
													<input type="hidden" name="sectionF[${loop.index}].feildid"
														value="${itInvestments_F.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionF[${loop.index}].category" value="F" />
													<input type="hidden"
														name="sectionF[${loop.index}].feildtype"
														value="${itInvestments_F.type}" />
												</c:when>
												<c:when test="${itInvestments_F.type=='textarea'}">
													<td colspan=""><form:textarea id="f${loop.index}"
															path="sectionF[${loop.index}].feildvalue"
															style="height: 80px; width: 400px;" readonly="readonly"></form:textarea>
														&nbsp;</td>
													<input type="hidden" name="sectionF[${loop.index}].feildid"
														value="${itInvestments_F.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionF[${loop.index}].category" value="F" />
													<input type="hidden"
														name="sectionF[${loop.index}].feildtype"
														value="${itInvestments_F.type}" />
												</c:when>
												<c:when test="${itInvestments_F.type=='Amount'}">
													<td><input type="number" id="f${loop.index}"
														path="sectionF[${loop.index}].feildvalue"
														readonly="readonly" /></td>
													<input type="hidden" name="sectionF[${loop.index}].feildid"
														value="${itInvestments_F.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionF[${loop.index}].category" value="F" />
													<input type="hidden"
														name="sectionF[${loop.index}].feildtype"
														value="${itInvestments_F.type}" />
												</c:when>
												<c:otherwise>
													<td><form:input
															path="sectionF[${loop.index}].feildvalue"
															id="f${loop.index}" readonly="readonly"></form:input>
														&nbsp;</td>
													<input type="hidden" name="sectionF[${loop.index}].feildid"
														value="${itInvestments_F.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionF[${loop.index}].category" value="F" />
													<input type="hidden"
														name="sectionF[${loop.index}].feildtype"
														value="${itInvestments_F.type}" />
												</c:otherwise>

											</c:choose>
										</tr>

										<c:set var="FIndex" value="${loop.index}" scope="page"></c:set>
									</c:forEach>

									<input name="FLASTIndex" id="FLASTIndex" type="hidden"
										value="${FIndex}"></input>
									<tr class="">
										<td align="center" class="smallhead">G</td>
										<td colspan="4" class="subhead">Pre Construction Interest
											Details</td>
									</tr>


									<c:forEach items="${ITInvestmentCategoryG}"
										var="itInvestments_G" varStatus="loop">
										<tr class=" idrow0">
											<td align="center">${loop.index+1}</td>
											<td colspan="4">${itInvestments_G.fieldLabel}</td>

											<c:choose>
												<c:when test="${itInvestments_G.type=='year'}">
													<td><form:select
															path="sectionG[${loop.index}].feildvalue"
															id="g${loop.index}">
															<form:option value="">--Select Year--</form:option>
															<form:option value="2017">2017</form:option>
															<form:option value="2016">2016</form:option>
															<form:option value="2015">2015</form:option>
															<form:option value="2014">2014</form:option>
															<form:option value="2013">2013</form:option>
															<form:option value="2012">2012</form:option>
															<form:option value="2011">2011</form:option>
															<form:option value="2010">2010</form:option>

														</form:select></td>
													<input type="hidden" name="sectionG[${loop.index}].feildid"
														value="${itInvestments_G.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionG[${loop.index}].category" value="G" />
													<input type="hidden"
														name="sectionG[${loop.index}].feildtype"
														value="${itInvestments_G.type}" />
												</c:when>
												<c:when test="${itInvestments_G.type=='month'}">
													<td><form:select
															path="sectionG[${loop.index}].feildvalue"
															id="g${loop.index}">
															<form:option value="">--select month--</form:option>
															<form:option value="january">january</form:option>
															<form:option value="february">february</form:option>
															<form:option value="march">march</form:option>
															<form:option value="april">april</form:option>
															<form:option value="may">may</form:option>
															<form:option value="june">june</form:option>
															<form:option value="july">july</form:option>
															<form:option value="august">august</form:option>
															<form:option value="september">september</form:option>
															<form:option value="october">october</form:option>
															<form:option value="november">november</form:option>
															<form:option value="december">december</form:option>

														</form:select> &nbsp;</td>
													<input type="hidden" name="sectionG[${loop.index}].feildid"
														value="${itInvestments_G.houseLoadFieldId}"
														readonly="readonly" />
													<input type="hidden"
														name="sectionG[${loop.index}].category" value="G"
														readonly="readonly" />
													<input type="hidden"
														name="sectionG[${loop.index}].feildtype"
														value="${itInvestments_G.type}" /readonly="readonly">
												</c:when>
												<c:when test="${itInvestments_G.type=='textarea'}">
													<td colspan=""><form:textarea id="g${loop.index}"
															path="sectionG[${loop.index}].feildvalue"
															style="height: 80px; width: 400px;"></form:textarea>
														&nbsp;</td>
													<input type="hidden" name="sectionG[${loop.index}].feildid"
														value="${itInvestments_G.houseLoadFieldId}"
														readonly="readonly" />
													<input type="hidden"
														name="sectionG[${loop.index}].category" value="G"
														readonly="readonly" />
													<input type="hidden"
														name="sectionG[${loop.index}].feildtype"
														value="${itInvestments_G.type}" readonly="readonly" />

												</c:when>
												<c:when test="${itInvestments_G.type=='Amount'}">
													<td><input type="number" id="g${loop.index}"
														path="sectionG[${loop.index}].feildvalue"
														readonly="readonly" /></td>
													<input type="hidden" name="sectionG[${loop.index}].feildid"
														value="${itInvestments_G.houseLoadFieldId}"
														readonly="readonly" />
													<input type="hidden"
														name="sectionG[${loop.index}].category" value="G"
														readonly="readonly" />
													<input type="hidden"
														name="sectionG[${loop.index}].feildtype"
														value="${itInvestments_G.type}" readonly="readonly" />
												</c:when>
												<c:otherwise>
													<td><form:input
															path="sectionG[${loop.index}].feildvalue"
															id="g${loop.index}"></form:input> &nbsp;</td>
													<input type="hidden" name="sectionG[${loop.index}].feildid"
														value="${itInvestments_G.houseLoadFieldId}" />
													<input type="hidden"
														name="sectionG[${loop.index}].category" value="G"
														readonly="readonly" />
													<input type="hidden"
														name="sectionG[${loop.index}].feildtype"
														value="${itInvestments_G.type}" readonly="readonly" />

												</c:otherwise>

											</c:choose>
										</tr>
										<c:set var="GIndex" value="${loop.index}" scope="page"></c:set>
									</c:forEach>

									<input name="GLASTIndex" id="GLASTIndex" type="hidden"
										value="${GIndex}"></input>






									<tr>
										<td height="0" colspan="8" align="center" valign="bottom"><table>


												<tbody>
													<tr>
														<%-- <td style="border: 0 !important;"><a
															href="<%=request.getContextPath()%>/ITdeclaration/viewEmployeeList"
															class="myButton">&lt;&lt; Back</a></td>

					 --%>
														<!-- onclick="javascript:return validateIDForm(1);"-->
														<!--<td style="border:none;"><input type="button" class="cho_btn_small" name="idPrint" value="Print">
                                </td>-->

													</tr>
												</tbody>
											</table></td>
									</tr>
								</tbody>
							</table></td>
					</tr>





				</tbody>
			</table>

			<input type="hidden" id="buttonAction" name="buttonAction" />
			<input type="hidden" id="totalAinvstmnt" name="totalAinvstmnt" />





		</form:form>


	</div>


	<div id="ui-datepicker-div"
		class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div>
</body>
</html>

