<!-- Putting neat table background-->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td bgcolor="#AAAAAA">
      <table width="100%" cellspacing="1" cellpadding="3">
        <tr class="boxhead"><td>Options</td></tr>
        <tr class="boxbody">
          <td bgcolor="#DCDCDC">
            <a href="index.jsp" class="menulinks">Open Mentor home</a>
            <hr/>
            <c:if test="${empty sessionScope.course}">
              <a class="menulinks" name="dummy">
                <em>No current course</em>
              </a><br/>
              <a href="selectCourse.html" class="menulinks">Choose course</a>
            </c:if>
            <c:if test="${! empty sessionScope.course}">
              <a class="menulinks" name="dummy">
                <em>Current course: <c:out value="${sessionScope.course}"/></em>
              </a><br/>
              <a href="selectCourse.html" class="menulinks">Change course</a><br/>
              <a href="selectAssignment.html" class="menulinks">Upload assignment</a><br/>
              <a href="selectReport.jsp" class="menulinks">View reports</a>
            </c:if>
            <hr/>
            <a href="help.jsp" class="menulinks">Background</a><br/>
            <a href="siteindex.jsp" class="menulinks">Site index</a><br/>
            <!-- "loggedIn" is set (and used) in header.jsp -->
            <c:choose>
              <c:when test="${loggedIn}">
                <a href="logout.jsp" class="menulinks">Sign out</a>
              </c:when>
              <c:otherwise>
                <a href="signin.jsp" class="menulinks">Sign in</a>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
