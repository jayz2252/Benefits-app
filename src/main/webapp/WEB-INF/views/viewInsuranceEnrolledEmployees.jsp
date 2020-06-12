<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<style>
.dataTables_wrapper div select{
	display : block !important;
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
						<div class="col-sm-6 col-md-9 py-1 px-1">
							<h4 class="h4-responsive">Insurance Enrolled Employees</h4>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="md-form">
								<input placeholder="Search..." type="text" id="form5"
									class="form-control">
							</div>
						</div>
					</div>
					
					<form:form action="optedEmployees" method="POST"  modelAttribute="bean">
								<div class="row">
									<div class="form-group is-empty">
										<div class="md-form">
										<div class="col-md-6 padding_left_0">
												<form:select path="fiscalYear">


													<c:forEach items="${appContext.fiscalYears}" var="fy">
														<option value="${fy}" ${bean.fiscalYear eq fy ? 'selected="selected"' : ''}>${fy}</option>
													</c:forEach>

												</form:select>
												<label for="FiscalYear">Select Fiscal Year</label>
											</div>
										</div>
									</div>

								</div>
								<div class="row">
									<div class="form-group is-empty margin_bottom_0">
										<div class="md-form">
											<div class="col-md-6 padding_left_0">
												<form:select path="status">
													<option value="SUBM" >Submitted</option>
													<option value="HR_APPR" ${bean.status eq 'HR_APPR' ? 'selected="selected"' : ''}>Approved</option>
													
													<%-- <option value="NO_APPR" ${bean.status eq 'NO_APPR' ? 'selected="selected"' : ''}>Not Approved</option> --%>


												</form:select>
												<label for="Status">Select Status</label>
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
							</form:form>
						</div>

					
			 <div class="col-md-12">
						<table id="dtable"
							class="tablePlanEmployees table striped table-borderd data_table">
							<thead>
								<tr>
									<th>

										 <div class="remember-label">
											<div>
												<input type="checkbox" id="selectAll" name="selectAll"/> <label
													for="selectAll"></label>
											</div>
										</div>  
									</th>
									<th>Employee Name</th>
									<th>Employee Code</th>
									<th>Enrolled Date</th>
									<th>Effective From</th>
									<th>Status</th>
									<th>Action</th>
								
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${insurancePlanEmployees}" var="insurancePlanEmployees">
									<tr>
										<td> <c:if test="${insurancePlanEmployees.status eq 'SUBM'}">
												<div class="remember-label">
													<div>
														<input class="chk" type="checkbox"
															id="selectPF${insurancePlanEmployees.insPlanEmployeeId}" name="selectPF"
															value="${insurancePlanEmployees.insPlanEmployeeId}" /> <label
															for="selectPF${insurancePlanEmployees.insPlanEmployeeId}"></label>
													</div>
												</div>
											</c:if> </td>
										<td> <a
											href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees/viewDetails/${insurancePlanEmployees.insPlanEmployeeId}">${insurancePlanEmployees.employee.firstName}
												${insurancePlanEmployees.employee.lastName}</a></td>
										<td>${insurancePlanEmployees.employee.employeeCode}</td>
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${insurancePlanEmployees.enrolledDate}" /></td>
										<td><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${insurancePlanEmployees.effFrom}" /></td>
										
										<td><c:choose>
												<c:when test="${insurancePlanEmployees.status eq 'SUBM'}">Submitted</c:when>
												<c:when test="${insurancePlanEmployees.status eq 'HR_APPR'}">Approved</c:when>
												<c:when test="${insurancePlanEmployees.status eq 'SL_SUBM'}">SL Submitted</c:when>
												<c:otherwise>Rejected</c:otherwise>
											</c:choose></td>
										<td> <c:if test="${insurancePlanEmployees.status eq 'SUBM'}">
												<a
													href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees/approvalView/${insurancePlanEmployees.insPlanEmployeeId}">
													Approve</a>
											</c:if> 
											<c:if test="${insurancePlanEmployees.status eq 'HR_APPR'}">
												<a
													href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees/${insurancePlanEmployees.insPlanEmployeeId}/paf/new">
													<i class="fa fa-medkit" aria-hidden="true" title="Create new Pre Authorization Form behalf of ${insurancePlanEmployees.employee.firstName} ${insurancePlanEmployees.employee.lastName}"></i>
													</a>
													
													<a
													href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees/${insurancePlanEmployees.insPlanEmployeeId}/bills/new">
													<i class="fa fa-file-text" aria-hidden="true" title="Bill Submission on behalf of ${insurancePlanEmployees.employee.firstName} ${insurancePlanEmployees.employee.lastName}"></i>
													</a>
													<a
													href="<%=request.getContextPath()%>/home/controlPanel/insurancePlans/optedEmployees/${insurancePlanEmployees.insPlanEmployeeId}/addDependents">
													<i class="fa fa-user-plus" aria-hidden="true" title="Add Dependence on behalf of ${insurancePlanEmployees.employee.firstName} ${insurancePlanEmployees.employee.lastName}"></i>
													</a>
											</c:if>
											
											</td>
										
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<form id="approveHiddenId"
							action="/home/controlPanel/insurancePlans/optedEmployees/approveSelected">
							<div class="form-group">

								<div class="col-md-6">
									<input type="hidden" id="approveSelected" value=""
										name="approveSelected"> <input type="submit"
										class="btn btn-primary approveClass" id="approve"
										name="approve" value="Approve Selected" />

								</div>
							</div>
						</form>

					</div>
				</div>
			</div>
			</section>
		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
	<script>
	
	//
	// Updates "Select all" control in a data table
	//
	function updateDataTableSelectAllCtrl(table){
	   var $table             = table.table().node();
	   var $chkbox_all        = $('tbody input[type="checkbox"]', $table);
	   var $chkbox_checked    = $('tbody input[type="checkbox"]:checked', $table);
	   var chkbox_select_all  = $('thead input[name="selectAll"]', $table).get(0);

	   // If none of the checkboxes are checked
	   if($chkbox_checked.length === 0){
	      chkbox_select_all.checked = false;
	      if('indeterminate' in chkbox_select_all){
	         chkbox_select_all.indeterminate = false;
	      }

	   // If all of the checkboxes are checked
	   } else if ($chkbox_checked.length === $chkbox_all.length){
	      chkbox_select_all.checked = true;
	      if('indeterminate' in chkbox_select_all){
	         chkbox_select_all.indeterminate = false;
	      }

	   // If some of the checkboxes are checked
	   } else {
	      chkbox_select_all.checked = true;
	      if('indeterminate' in chkbox_select_all){
	         chkbox_select_all.indeterminate = true;
	      }
	   }
	}

	$(document).ready(function (){
		
		
		$('#approve').prop("disabled", true);
	   // Array holding selected row IDs
	   var rows_selected = [];
	   var table = $('#dtable').DataTable({
		   'destroy':true,
			    'columnDefs': [{
			         'targets': 0,
			         'searchable':false,
			         'orderable':false,
			         'className': 'dt-body-center',
			         'render': function (data, type, full, meta){
			             return '<input type="checkbox">';
			         }
			      }],
			      
			         "scrollY":  "200px",
					 "sScrollX": "100%",
				     "sScrollXInner": "110%",
				     "bScrollCollapse": true,
				    
				    
				     
				     
				        buttons: [
				           
				            {
				                extend: 'copyHtml5',
				                text: 'copy',
				                title: 'Report'
				            },
				            {
				            	 extend: 'csvHtml5',
					                text: 'csv',
					                title: 'Report'
				            },
				            {
				            	 extend: 'excelHtml5',
					                text: 'excel',
					                title: 'Report'
				            },
				            {
				            	 extend: 'print',
					                text: 'print',
					                title: 'Report'
				            }
				        ],
			      'order': [1, 'asc'],
			      'rowCallback': function(row, data, dataIndex){
			         // Get row ID
			         var rowId = data[0];

			         // If row ID is in the list of selected row IDs
			         if($.inArray(rowId, rows_selected) !== -1){
			            $(row).find('input[type="checkbox"]').prop('checked', true);
			            $(row).addClass('selected');
			         }
			      
			      }
			   }); 
		
	 
	   

	   // Handle click on checkbox
	   $('#dtable tbody').on('click', 'input[type="checkbox"]', function(e){
	      var $row = $(this).closest('tr');
	      
	      vals = $('.chk:checked').map(function() {
				return this.value;
			}).get().join(',');
			console.log(vals);
			$('#approveSelected').val(vals);

			if (vals != '') {
				$('#approve').prop("disabled", false);
			} else {
				$('#approve').prop("disabled", true);
			}
			
			

	      // Get row data
	      var data = table.row($row).data();

	      // Get row ID
	      var rowId = data[0];

	      // Determine whether row ID is in the list of selected row IDs 
	      var index = $.inArray(rowId, rows_selected);

	      // If checkbox is checked and row ID is not in list of selected row IDs
	      if(this.checked && index === -1){
	         rows_selected.push(rowId);

	      // Otherwise, if checkbox is not checked and row ID is in list of selected row IDs
	      } else if (!this.checked && index !== -1){
	         rows_selected.splice(index, 1);
	      }

	      if(this.checked){
	         $row.addClass('selected');
	      } else {
	         $row.removeClass('selected');
	      }

	      // Update state of "Select all" control
	      updateDataTableSelectAllCtrl(table);

	      // Prevent click event from propagating to parent
	      e.stopPropagation();
	   });

	   // Handle click on table cells with checkboxes
	   $('#dtable').on('click', 'tbody td, thead th:first-child', function(e){
	      $(this).parent().find('input[type="checkbox"]').trigger('click');
	   });

	   // Handle click on "Select all" control
	   $('thead input[name="selectAll"]', table.table().container()).on('click', function(e){
	      if(this.checked){
	         $('tbody input[type="checkbox"]:not(:checked)', table.table().container()).trigger('click');
	         vals = $('.chk:checked').map(function() {
					return this.value;
				}).get().join(',');
				console.log(vals);
				$('#approveSelected').val(vals);
	         
	      } else {
	         $('tbody input[type="checkbox"]:checked', table.table().container()).trigger('click');
	         $('#approve').prop("disabled", true);
	         $('#approveSelected').val("");
	      }

	      // Prevent click event from propagating to parent
	      e.stopPropagation();
	   });

	   // Handle table draw event
	   table.on('draw', function(){
	      // Update state of "Select all" control
	      updateDataTableSelectAllCtrl(table);
	   });
	    
	   // Handle form submission event 
	   $('#frm-example').on('submit', function(e){
	      var form = this;

	      // Iterate over all selected checkboxes
	      $.each(rows_selected, function(index, rowId){
	         // Create a hidden element 
	         $(form).append(
	             $('<input>')
	                .attr('type', 'hidden')
	                .attr('name', 'id[]')
	                .val(rowId)
	         );
	      });

	      // FOR DEMONSTRATION ONLY     
	      
	      // Output form data to a console     
	      $('#dtable-console').text($(form).serialize());
	      console.log("Form submission", $(form).serialize());
	       
	      // Remove added elements
	      $('input[name="id\[\]"]', form).remove();
	       
	      // Prevent actual form submission
	      e.preventDefault();
	   });
	});
	
	
	
	
	
	
		/* function Populate() {
			vals = $('.chk:checked').map(function() {
				return this.value;
			}).get().join(',');
			console.log(vals);
			$('#approveSelected').val(vals);

			if (vals != '') {
				$('#approve').prop("disabled", false);
			} else {
				$('#approve').prop("disabled", true);
			}
		}

		$('input[type="checkbox"]').on('change', function() {
			Populate()
		}).change();

		$('#approve').prop("disabled", true);
		$('.chk').click(function() {
			if ($(this).is(':checked')) {
				$('#approve').prop("disabled", false);
			} else {
				if ($('.chk').filter(':checked').length < 1) {
					$('#approve').attr('disabled', true);
				}
			}
		});

		$("#selectAll").click(function() {
			$('input:checkbox').not(this).prop('checked', this.checked);
		}); */
	</script>
	
</body>
</html>