<% session.invalidate(); response.sendRedirect("index.jsp"); %>

<!-- So if the redirect works, we should never see the following
message.  -->
<p>
    You have successfully logged out. 
    <a href="index.jsp">Login</a> again.
</p>
