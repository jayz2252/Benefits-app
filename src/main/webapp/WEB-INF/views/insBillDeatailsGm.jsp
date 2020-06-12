<!-- 
author : swathy.raghu
This page will provides employee search option
 -->
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<style>
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

@media ( min-width : 480px) {
	.responstable th,.responstable td {
		display: table-cell;
		padding: 1em;
	}
}

.back {
    display: block;
    width: 115px;
    height: 25px;
    background: #4E9CAF;
    padding: 10px;
    text-align: center;
    border-radius: 5px;
    color: white;
    font-weight: bold;
}
</style>

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
						<div class="col-sm-6 col-md-9 py-1 px-1" style="color: #21b6ca;">
							<h4 class="h4-responsive">Bill Details(Based On Category)</h4>

						</div>


						<div class="form-group is-empty">
							<div class="md-form">
								<div class="col-md-6 padding_left_0">

									<label>Bill No: ${bill.billNo}</label>
								</div>
							</div>
						</div>

						<div class="form-group is-empty">
							<div class="md-form">
								<div class="col-md-6 padding_left_0">

									<label>Amount Requested: ${bill.amountRequested}</label>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
							
										<table class="responstable">
								<thead>
									<tr>
										<td>Category</td>
										<td>Item</td>
										<td>Unit Cost</td>
										<td>Quantity</td>
										<td>Total Amount</td>
										<td>Verified Amount</td>
										<td>Status</td>
									</tr>
								</thead>
								<tbody>

									<c:if test="${billDetails ne null }">
										<c:forEach items="${billDetails}" var="detail">
											<tr>

												<td>${detail.category.categoryDesc}</td>
												<td>${detail.item}</td>
												<td>${detail.unitCost}</td>
												<td>${detail.quantity}</td>

												<td>${detail.totalCost}</td>
												<td>${detail.approvedAmount}</td>

									
												<td>
												<c:choose>
												<c:when test="${detail.status eq 'APPR'}">
												Approved
												</c:when>
												
												<c:when test="${detail.status eq 'RJCT'}">
												Rejected
												</c:when>
											
												
												</c:choose>
												</td>
											</tr>


										</c:forEach>

									</c:if>


								</tbody>
							</table>

						</div>
					</div>
					
					<div class="row">


						<div class="col-md-12">

							<div class="form-group">

								<a
									href="<%= request.getContextPath()%>/home/controlPanel/insurancePlans/searchClaims/billsGMApprove/${claimId}" class="btn btnPrimary yellow darken-3">Back</a>
							</div>
						</div>
					</div>
					
					
				</div>
			</div>
			</section>
		</div>
	</div>
	
	<%@include file="includeFooter.jsp"%>
	<script>
		$(document).ready(function() {
			$('select').material_select();
		
		});
	</script>

</body>
</html>
​
