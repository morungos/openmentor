<script type="text/javascript">
  <!--
    function openwin(winurl) { //v2.0
    newwin = window.open(winurl,'OpenMentor','width=680,height=480,scrollbars=yes,resizable=yes');
    setTimeout('newwin.focus();',250);
    }
    function confirmDelete(URL, Message, ID)
    {
    if ( window.confirm(Message)) {
    
    window.location = URL + '?id=' + ID;
    }
    }
    //-->
</script>
<!-- This variables says whether we are a visitor or not -->
<authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
  <c:set var="loggedIn" value="true"/>
</authz:authorize>

<div id="logoAtLeft">
  <a href="index.jsp">
    <img src="images/OMLogo.jpg" border="0" alt="OpenMentor Home"/>
  </a>
</div>
<div id="centerHeader">
  <h2 class="sitehead">
    Openmentor: <decorator:title default="Welcome"/>
  </h2>
</div>
<div id="rightHeader">
  <c:choose>
    <c:when test="${loggedIn}">
      You are signed in as 
      <strong>
        <authz:authentication operation="username"/>.<br/>
      </strong>
      <a href="logout.jsp">Sign out</a>.
    </c:when>
    <c:otherwise>
      You are here as a <strong>visitor</strong>.<br/>
      <a href="signin.jsp">Sign in</a>.
    </c:otherwise>
  </c:choose>
</div>
<hr/>

	
