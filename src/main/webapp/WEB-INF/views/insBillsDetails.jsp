<!-- 
author : Swathy Raghu
Page used to fill bill details.
 -->
<%@page import="com.speridian.benefits2.model.pojo.BenefitPlanEmployee"%>
<%@page import="com.speridian.benefits2.model.pojo.Employee"%>
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<style>
.dataTables_wrapper .dataTables_length label .select-wrapper {
	float: left;
	top: 20px;
	position: absolute;
}

.dataTables_wrapper .dataTables_length label {
	height: 60px;
}

/* .slideThree */
.slideThree {
	width: 80px;
	height: 26px;
	background: #333;
	margin: 20px auto;
	position: relative;
	border-radius: 50px;
	box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px
		rgba(255, 255, 255, 0.2); &: after { content : 'REJECTED';
	color: #000;
	position: absolute;
	right: 10px;
	z-index: 0;
	font: 12px/26px Arial, sans-serif;
	font-weight: bold;
	text-shadow: 1px 1px 0px rgba(255, 255, 255, .15);
}

&
:before {
	content: 'Approved';
	color: $activeColor;
	position: absolute;
	left: 10px;
	z-index: 0;
	font: 12px/26px Arial, sans-serif;
	font-weight: bold;
}

label {
	display: block;
	width: 34px;
	height: 20px;
	cursor: pointer;
	position: absolute;
	top: 3px;
	left: 3px;
	z-index: 1;
	background: #fcfff4;
	background: linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	border-radius: 50px;
	transition: all 0.4s ease;
	box-shadow: 0px 2px 5px 0px rgba(0, 0, 0, 0.3);
}

input[type=checkbox] {
	visibility: hidden;
	&:
	checked
	+
	label
	{
	left
	:
	43px;
}

}
}
/* end .slideThree */
.responstable {
	margin: 1em 0;
	width: 100%;
	overflow: hidden;
	background: #FFF;
	color: #024457;
	border-radius: 10px;
	border: 1px solid #167F92;
}

.responstable tr {
	border: 1px solid #D9E4E6;
}

.responstable tr:nth-child(odd) {
	background-color: #EAF3F3;
}

.responstable th {
	display: none;
	border: 1px solid #FFF;
	background-color: #167F92;
	color: #FFF;
	padding: 1em;
}

.responstable th:first-child {
	display: table-cell;
	text-align: center;
}

.responstable th:nth-child(2) {
	display: table-cell;
}

.responstable th:nth-child(2) span {
	display: none;
}

.responstable th:nth-child(2):after {
	content: attr(data-th);
}

@media ( min-width : 480px) {
	.responstable th:nth-child(2) span {
		display: block;
	}
	.responstable th:nth-child(2):after {
		display: none;
	}
}

.responstable td {
	display: block;
	word-wrap: break-word;
	max-width: 7em;
}

.responstable td:first-child {
	display: table-cell;
	text-align: center;
	border-right: 1px solid #D9E4E6;
}

@media ( min-width : 480px) {
	.responstable td {
		border: 1px solid #D9E4E6;
	}
}

.responstable th,.responstable td {
	text-align: left;
	margin: .5em 1em;
}

#billDetails select {
	display: block;
}

@media ( min-width : 480px) {
	.responstable th,.responstable td {
		display: table-cell;
		padding: 1em;
	}
}
</style>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<body>
	<%@include file="employeeNavBar.jsp"%>
	<div id="main">
		<div class="wrapper">
			<%@include file="adminLeftNav.jsp"%>
			<section id="content" class="">
			<form
				action="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees/billDetails/new/save/${billNo}"
				method="post">

				<div class="row">



					<div class="col-md-12 white">

						<div class="row">

							<div class="col-sm-6 col-md-9 py-1 px-1" style="color: #21b6ca;">
								<h4 class="h4-responsive">Bill Details(Based On Category)</h4>

							</div>


							<div class="form-group is-empty">
								<div class="md-form">
									<div class="col-md-6 padding_left_0">

										<label>Bill No: ${billNo}</label>
									</div>
								</div>
							</div>
						</div>
						<input type="hidden" value="${categories}" name="category"
							id="category">
						<div class="row">

							<c:set value="0" var="countLoop" scope="page" />
							<div class="col-md-12">

								<br />
								<table id="billDetails" class="responstable dynamicTable">
									<thead>
										<tr>
											<td>Category</td>
											<td>Item</td>
											<td>Unit Cost</td>
											<td>Quantity</td>
											<td>Total Amount</td>


										</tr>
									</thead>
									<tbody>

										<c:if test="${bean.billDetails ne null }">
											<c:forEach items="${bean.billDetails}" var="detail"
												varStatus="i">
												<tr>
													<c:set var="countLoop1" value="${i.index + 1}" scope="page" />

													<td><select id="categoryId${i.index}"
														name="categoryId${i.index}">
															<option disabled selected>Choose Category</option>
															<c:forEach items="${categories}" var="category">
																<option class="display_none"
																	value="${category.categoryId}"
																	${category.categoryId eq detail.category.categoryId ? 'selected="selected"' : ''}>${category.categoryDesc}</option>
															</c:forEach>
													</select></td>
													<td><input type="text" name="item${i.index}"
														id="item${i.index}" value="${detail.item}" /></td>
													<td><input type="text" name="unitCost${i.index}"
														id="unitCost${i.index}" value="${detail.unitCost}" onkeyup="changeTotal(this)"/></td>
													<td><input type="text" name="quantity${i.index}"
														id="quantity${i.index}" value="${detail.quantity}" onkeyup="changeTotal(this)"/></td>

													<td><input type="text" name="totalAmt${i.index}"
														id="totalAmt${i.index}" value="${detail.totalCost}" /></td>



												</tr>


											</c:forEach>
											<c:set var="countLoop" value="${countLoop1}"></c:set>

										</c:if>

										<tr>


											<td><select id="categoryId${countLoop}"
												name="categoryId${countLoop}">
													<option disabled selected>Choose Category</option>
													<c:forEach items="${categories}" var="category">
														<option value="${category.categoryId}"
															id2="${category.unitCost}">${category.categoryDesc}</option>
													</c:forEach>
											</select></td>
											<td><input type="text" name="item${countLoop}"
												id="item${countLoop}" /></td>
											<td><input type="text" name="unitCost${countLoop}"
												id="unitCost${countLoop}" onkeyup="changeTotal(this)"/></td>
											<td><input type="text" name="quantity${countLoop}"
												id="quantity${countLoop}" onkeyup="changeTotal(this)" /></td>

											<td><input type="text" name="totalAmt${countLoop}"
												id="totalAmt${countLoop}" value="" /></td>


											<td></td>
										</tr>

									</tbody>
								</table>

								<input type="hidden" id="rowCount" name="rowCount"
									value="${countLoop}" />

								<button type="button" id="btnAddRow" class="btn blue darken-2">Add
									Row</button>
								<button type="button" id="btnRemoveRow" class="btn red darken-1">Remove
									Last Row</button>
							</div>


						</div>


						<div class="row">


							<div class="col-md-12">

								<div class="form-group">

									<input type="text" id="totalAmount" name="totalAmount"
										value="${total}" /><label for="totalAmount">Bill
										Total(Rs.)</label>
								</div>
							</div>
						</div>

						<div class="row">


							<div class="col-md-12">

								<div class="form-group">
<a
									href="<%= request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees/${planEmployeeId}/bills/new#tabBillInfo" class="btn btnPrimary yellow darken-3">Back</a>
									<input type="submit" id="btnClaimApprove"
										class="btn green btn-primary" value="Save Bill Details"/>
										
								</div>
								
							</div>
						</div>
					</div>

				</div>
			</form>
			</section>
		</div>
	</div>
	<%@include file="insBillDetailsFooter.jsp"%>
	<script type="text/javascript">
	var e1=document.getElementById('rowCount').value;
	//document.getElementById('rowCount').value='0';
	changeTotal(e1);
		function setUnitCost(e) {

			var unitCost = $(e).attr("id2");

			rowIndex = row.index();
			$('#unitCost' + rowIndex).val(unitCost);

		}
		function changeTotal(e) {

			row = $(e).closest('tr');
			rowIndex = row.index();
			var total = 0;
			/* $('#totalAmount').val(total); */
			var qnty = $('#quantity' + rowIndex).val();
			var cost = $('#unitCost' + rowIndex).val();
			if(qnty!="" & cost!=""){
			$('#totalAmt' + rowIndex).val(parseFloat(qnty) * parseFloat(cost));

			
			var rowCount = $('#rowCount').val();
			for ( var int = 0; int <= rowCount; int++) {
				total = parseFloat(total)
						+ parseFloat($('#totalAmt' + int).val());

			}
			if(isNaN(total)){
				total = 0;
			}
			
			}
			$('#totalAmount').val(total);
			

		}
		$("#btnAddRow").click(
				function() {

					var rowCount = parseFloat($("#rowCount").val());
					rowCount++;
					var $clone = $(".dynamicTable tbody tr:first").clone();

					$clone.attr({
						id : "bill" + rowCount,
						name : "bill" + rowCount,
						style : "" // remove "display:none"
					});

					$clone.find("input").each(
							function() {

								var id = this.id.substr(0, this.id.length - 1)
										+ rowCount;

								var name = this.name.substr(0,
										this.name.length - 1)
										+ rowCount;
								$(this).attr({

									id : id,
									name : name

								});
							});

					$clone.find("select").each(
							function() {
								var id = this.id.substr(0, this.id.length - 1)
										+ rowCount;

								var name = this.name.substr(0,
										this.name.length - 1)
										+ rowCount;
								$(this).attr({

									id : id,
									name : name

								});
							});

					$clone.find("input[type='text']").each(function() {
						$(this).val("");
					});
					$clone.find("input[type='number']").each(function() {
						$(this).val("");
					});
					$clone.find("input[type='date']").each(function() {
						$(this).val("");
					});
					$(".dynamicTable tbody").append($clone);
					$("#rowCount").val(rowCount);

				});

		/* $( window ).load(
		 function() {
		 // Run code }); */
	</script>

</body>
</html>