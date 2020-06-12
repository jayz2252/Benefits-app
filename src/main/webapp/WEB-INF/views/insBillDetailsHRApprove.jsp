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

/* 

h1 {
	color: #eee;
	font: 30px Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	text-shadow: 0px 1px black;
	text-align: center;
	margin-bottom: 50px;
}

input[type=checkbox] {
	visibility: hidden;
}


/* SLIDE TWO */
.slideTwo {
	width: 80px;
	height: 30px;
	background: #333;
	margin: 20px auto;
	-webkit-border-radius: 50px;
	-moz-border-radius: 50px;
	border-radius: 50px;
	position: relative;
	-webkit-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px
		rgba(255, 255, 255, 0.2);
	-moz-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px
		rgba(255, 255, 255, 0.2);
	box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px
		rgba(255, 255, 255, 0.2);
}

.slideTwo:after {
	content: '';
	position: absolute;
	top: 14px;
	left: 14px;
	height: 2px;
	width: 52px;
	-webkit-border-radius: 50px;
	-moz-border-radius: 50px;
	border-radius: 50px;
	background: #111;
	-webkit-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px
		rgba(255, 255, 255, 0.2);
	-moz-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px
		rgba(255, 255, 255, 0.2);
	box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px
		rgba(255, 255, 255, 0.2);
}

.slideTwo label {
	display: block;
	width: 22px;
	height: 22px;
	-webkit-border-radius: 50px;
	-moz-border-radius: 50px;
	border-radius: 50px;
	-webkit-transition: all .4s ease;
	-moz-transition: all .4s ease;
	-o-transition: all .4s ease;
	-ms-transition: all .4s ease;
	transition: all .4s ease;
	cursor: pointer;
	position: absolute;
	top: 4px;
	z-index: 1;
	left: 4px;
	-webkit-box-shadow: 0px 2px 5px 0px rgba(0, 0, 0, 0.3);
	-moz-box-shadow: 0px 2px 5px 0px rgba(0, 0, 0, 0.3);
	box-shadow: 0px 2px 5px 0px rgba(0, 0, 0, 0.3);
	background: #fcfff4;
	background: -webkit-linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	background: -moz-linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	background: -o-linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	background: -ms-linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	background: linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(  startColorstr='#fcfff4',
		endColorstr='#b3bead', GradientType=0);
}

.slideTwo label:after {
	content: '';
	position: absolute;
	width: 10px;
	height: 10px;
	-webkit-border-radius: 50px;
	-moz-border-radius: 50px;
	border-radius: 50px;
	background: #333;
	left: 6px;
	top: 6px;
	-webkit-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 1), 0px 1px 0px
		rgba(255, 255, 255, 0.9);
	-moz-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 1), 0px 1px 0px
		rgba(255, 255, 255, 0.9);
	box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 1), 0px 1px 0px
		rgba(255, 255, 255, 0.9);
}

.slideTwo input[type=checkbox]:checked+label {
	left: 54px;
}

.slideTwo input[type=checkbox]:checked+label:after {
	background: #00bf00;
}

* /

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
				action="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees/billDetailsHRApprove/new/save/${bill.claimBillId}"
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
											<td>Approved Amount</td>
											<td>Action</td>
										</tr>
									</thead>
									<tbody>

										<c:if test="${billDetails ne null }">
											<c:forEach items="${billDetails}" var="detail" varStatus="i">
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
														id="unitCost${i.index}" value="${detail.unitCost}" /></td>
													<td><input type="text" name="quantity${i.index}"
														id="quantity${i.index}" value="${detail.quantity}" /></td>

													<td><input type="text" name="totalAmt${i.index}"
														id="totalAmt${i.index}" value="${detail.totalCost}" /></td>
													<td><input type="text" name="apprAmt${detail.claimBillDetailId}"
														id="apprAmt${i.index}" value="${detail.approvedAmount}" /></td>

													<td>

															<input type="hidden" name="detailId${i.index}"
																id="detailId${i.index}"
																value="${detail.claimBillDetailId}">
															<!-- <section title=".slideThree"> .slideThree
														<div class="slideTwo"> -->
															<input type="checkbox" value="None" id="check${i.index}"
																name="check${i.index}" 
														${detail.status eq 'HR_APPR'?'checked':''}		onchange="update(this)" />
															<label for="check${i.index}"></label>
															<!-- </div>
														end .slideThree </section></td>
 -->
														</td>
												</tr>


											</c:forEach>
											<c:set var="countLoop" value="${countLoop1}"></c:set>

										</c:if>



									</tbody>
								</table>

							</div>


						</div>


						<div class="row">


							<div class="col-md-12">

								<div class="form-group">

									<input type="text" id="totalAmount" name="totalAmount"
										value="${total}" /><label for="totalAmount">Bill
										Total Approved(Rs.)</label>
								</div>
							</div>
						</div>
						<div class="row">


							<div class="col-md-12">

								<div class="form-group">

									<input type="text" id="totalAmountr" name="totalAmountr"
										value="${totalr}" /><label for="totalAmount">Total
										Amount Rejected(Rs.)</label>
								</div>
							</div>
						</div>

						<div class="row">


							<div class="col-md-12">

								<div class="form-group">

									<input type="submit" id="btnClaimApprove"
										class="btn green btn-primary" value="Save Bill Details" />
								</div>
							</div>
						</div>
					</div>

				</div>
				<input type="hidden" name="apprIds" id="apprIds"> <input
					type="hidden" name="rjctIds" id="rjctIds">
			</form>
			</section>
		</div>
	</div>
	<%@include file="insBillDetailsFooter.jsp"%>
	<script type="text/javascript">
		function setUnitCost(id, e) {

			var unitCost = $(e).attr("id2");

			rowIndex = row.index();
			$('#unitCost' + rowIndex).val(unitCost);

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

		function changeTotal(e) {

			row = $(e).closest('tr');
			rowIndex = row.index();
			var qnty = $('#quantity' + rowIndex).val();
			var cost = $('#unitCost' + rowIndex).val();

			$('#totalAmt' + rowIndex).val(parseFloat(qnty) * parseFloat(cost));

			$('#totalAmount').val(
					parseFloat($('#apprAmt' + rowIndex).val())
							+ parseFloat($('#totalAmount').val()));
		}

		function update(e) {
			row = $(e).closest('tr');
			rowIndex = row.index();
			if ($(e).prop("checked") == true) {
				
				var appr = $('#apprIds').val();
				var detail = $('#detailId' + rowIndex).val();
				$('#apprIds').val( appr + "," + detail);
				
				$('#totalAmount').val(
						parseFloat($('#apprAmt' + rowIndex).val())
								+ parseFloat($('#totalAmount').val()));
				$('#totalAmountr').val(
						parseFloat($('#totalAmountr').val())
								- parseFloat($('#apprAmt' + rowIndex).val()));

			} else {
				$('#totalAmount').val(
						parseFloat($('#totalAmount').val())
								- parseFloat($('#apprAmt' + rowIndex).val()));
				$('#totalAmountr').val(
						parseFloat($('#totalAmount').val())
								+ parseFloat($('#apprAmt' + rowIndex).val()));

			}
		}

		$(window).load(function() {
			// Run code
		});

		function appr(e) {
			row = $(e).closest('tr');
			rowIndex = row.index();
			$('#apprIds').val = $('#apprIds').val + ","
					+ $('#detailId' + rowIndex).val();

		}
	</script>

</body>
</html>