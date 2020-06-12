

$('.modal').modal({
	dismissible : true,
	opacity : .5,
	inDuration : 300,
	outDuration : 200,
	startingTop : '4%',
	endingTop : '10%',
	ready : function(modal, trigger) {
		console.log(modal, trigger);
	},
	complete : function() {
	}
});

$(document).ready(
			
		
		
		function() {
	
		
			
			

			
			$('#btnRemoveRow').click(function() {
				var rowCount = parseFloat($("#rowCount").val());
				if (rowCount > 0) {
					rowCount--;
					$('.dynamicTable tbody tr:last-child').remove();
					$("#rowCount").val(rowCount);
				}
			});

			
			
			 $('.data_table').DataTable({
				 "scrollY":        "200px",
				 "sScrollX": "100%",
			     "sScrollXInner": "110%",
			     "bScrollCollapse": true,
			     dom: 'lBfrtip',
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
			        ]
			 });

		});

/**
 * normal java script functions

 */

function setValue(element, value) {
	$(element).val(value);
}


function calculateCtgTotal(elem, ctgIndex) {
	var ctgAmount = parseFloat(document.getElementById("ctg" + ctgIndex
			+ "Amount").value);

	var misc1Amount = parseFloat(document.getElementById("misc1Ctg" + ctgIndex
			+ "Amount").value);
	var misc2Amount = parseFloat(document.getElementById("misc2Ctg" + ctgIndex
			+ "Amount").value);

	var totalCtgAmount = ctgAmount + misc1Amount + misc2Amount;

	document.getElementById("ctg" + ctgIndex + "TotalAmount").value = totalCtgAmount;

}



$(window).load(function() {
	$('.button-collapse').sideNav({
	    menuWidth: 300, 
	    edge: 'left', 
	    closeOnClick: true,
	    draggable: true 
	  }
	);
	
	/*$(".notif_left").css({'min-height':($("window").height()-75+'px')});
	$(".notif_left marquee").css({'min-height':($("window").height()-75+'px')});*/
	});

$("input.datepicker").on("focus", function(){
	 $(this).next().next().addClass("active")
});
$("#content").css({'min-height':($(document).height()-110+'px')});
$("#main").css({'min-height':($(document).height()-115+'px')});



