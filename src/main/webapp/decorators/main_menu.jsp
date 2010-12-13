<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        
<%@ include file="/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
    <!-- HTTP 1.1 -->
    <meta http-equiv="Cache-Control" content="no-store"/>
    <!-- HTTP 1.0 -->
    <meta http-equiv="Pragma" content="no-cache"/>
    <!-- Prevents caching at the Proxy Server -->
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <!-- Spacing on the next line important to web test -->
    <title>OpenMentor -  <decorator:title default="Welcome!" /></title>
    <c:set var="ctx" value="${pageContext.request.contextPath}" 
           scope="request"/>
    <link href="${ctx}/images/favicon.ico" rel="shortcut icon"/>
    <link href="${ctx}/om.css" type="text/css" rel="stylesheet"/>
    <decorator:head />
  </head>
  <body>

    <%@ include file="../include/header.jsp" %>

    <div id="navigation">
      <%@ include file="../include/main_left.jsp" %>
    </div>

    <div id="content">
      <decorator:body />
    </div>

    <div id="footer">
      <%@ include file="../include/footer.jsp" %>
    </div>

  </body>
</html>

