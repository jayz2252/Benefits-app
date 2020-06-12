<!--
Admin Left Navbar
author : ardra.madhu
-->
<aside id="left-sidebar-nav" class="side-nav z-depth-1 desktop">
	<div id="left" class="span3 notif_left">
	
	 <ul class="collection">
		 <marquee direction="up" scrollamount="2">
			 <c:forEach items="${notifications}" var="hpList">
		     	<li class="collection-item"><font color="#009baf" size =4>${hpList}</font></li>
		   	 </c:forEach>
	   	 </marquee>
     </ul>
    
    </div>
</aside>

