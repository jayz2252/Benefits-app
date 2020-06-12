
<!-- <div class="blue-grey footer">
	<div class="center">Developed by Team Java</div>
</div> -->
<!-- JQuery -->
<footer class="">

	<center class="white-text">©<span  id="add_date">2018</span> - Speridian Technologies LLC.</center>
</footer>
<script src="<c:url value="/resources/js/jquery-1.12.4.js"/>"></script> 
<!-- <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script> -->
<%-- <script src="<c:url value="/resources/js/jquery-3.1.1.min.js"/>"></script>  --%>
<script src="<c:url value="/resources/js/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.buttons.min.js"/>"></script>
<script src="<c:url value="/resources/js/buttons.flash.min.js"/>"></script>
<script src="<c:url value="/resources/js/jszip.min.js"/>"></script>
<script src="<c:url value="/resources/js/pdfmake.min.js"/>"></script>
<script src="<c:url value="/resources/js/vfs_fonts.js"/>"></script>
<script src="<c:url value="/resources/js/buttons.html5.min.js"/>"></script>
<script src="<c:url value="/resources/js/buttons.print.min.js"/>"></script>
<script src="<c:url value="/resources/js/FlexGauge.js"/>"></script>

<script type="text/javascript">

/*add year to footer */

var newDate=new Date();
var year=newDate.getFullYear();
document.querySelector("#add_date").textContent=" "+year;

</script>

<!-- Bootstrap tooltips -->
<%-- <script src="<c:url value="/resources/js/tether.min.js"/>"></script>

<!-- Bootstrap core JavaScript -->


<!-- MDB core JavaScript -->
<script src="<c:url value="/resources/js/mdb.min.js"/>"></script> --%>

<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/materialize.min.js"/>"></script>
<script src="<c:url value="/resources/js/main.js"/>"></script>
