
<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="include.jsp"%>
<body>
	<%@include file="employeeNavBar.jsp"%>
</body>









 
Admin Control Panel
author : vineesh.vijayan
-->
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
    <div class="col s12">
      <ul class="tabs">
        <li class="tab col s3"><a class="active" href="#test1">Hospital</a></li>
        <li class="tab col s3"><a href="#test2">Treatment</a></li>
      </ul>
    </div>
    <div id="test1" class="col s12">
			<h3>Hospitals</h3>
			<p>Some content. jhospiotab</p>
	</div>
    <div id="test2" class="col s12">
			<h3>Treatment</h3>
			<p>Some content tratment.</p>
	</div>
  </div>
			
			
				
			</div>
			</div>
			</section>
			

			


		</div>
	</div>
	<%@include file="includeFooter.jsp"%>
</body>
</html> 