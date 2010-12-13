<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 


<%@ include file="/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<!-- This repeats much done by the decorator.  Iit has to be this way
     because sitemesh doesn't decorate error pages. -->

  <head>
    <title>
      OpenMentor - Restricted Access for users
    </title>
    <c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
    <link href="${ctx}/images/favicon.ico" rel="shortcut icon"/>
    <link href="${ctx}/om.css" type="text/css" rel="stylesheet"/>
  </head>
  <body>
    <!-- Not quite a copy of header.jsp because the loginId will
         always say visitor.  So we dump that part of the header -->
    <div id="logoAtLeft">
      <a href="index.jsp">
        <img src="images/OMLogo.jpg" border="0" alt="OpenMentor Home"/>
      </a>
    </div>
    <div id="centerHeader">
      <h2 class="sitehead">
        Openmentor: Restricted Access for users
      </h2>
    </div>
    <p>
      You don't have an appropriate permission to see the page you have
      just tried to visit.
    </p>      
    <p>
      Please <a href="index.jsp">try again.</a>, or <a
      href="signin.jsp">signin</a> as a different user.
    </p>
    
    <%@ include file="../include/footer.jsp" %>

  </body>
</html>
