<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
    <title>Assignments</title>
  </head>
  <body>
    <div class="breadcrumbs">
      <strong>You are here:</strong>
      <a href="index.jsp">OpenMentor</a> >> 
      <a href="selectAssignment.html">Submit an Assignment</a> >>
      Manage Assignments
    </div>
    <div class="textheading">
      Here are the assignments for 
      <c:out value="${sessionScope.course}"/>
    </div>
    <%@ include file="/messages.jsp"%>
    <p>
      Use this screen to add or edit an assignment.
    </p>
    <p>     
      <button onclick="location.href=
              'editAssignment.html?course.id=${sessionScope.course}'">
        Add
      </button>
      a new assignment.
    </p>
    <table class="list">
      <thead>
        <tr class="BlueBG">
          <th>
            <div class="tableHeadingWhite">
              <fmt:message key="assignment.assignmentTitle"/>
            </div>
          </th>
          <th>
            <div class="tableHeadingWhite">
              <fmt:message key="commands.commands"/>
            </div>
          </th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="assignment" items="${assignments}" varStatus="status">
          <c:choose>
            <c:when test="${status.count % 2 == 0}">
              <tr class="WhiteBG">
            </c:when>
            <c:otherwise>
              <tr class="OffWhiteBG">
            </c:otherwise>
          </c:choose>
          <td>${assignment.assignmentTitle}</td>
          <td>
            <a href="editAssignment.html?id=${assignment.id}">
              <fmt:message key="commands.edit"/>
            </a> 
            |
            <a href="editAssignment.html?id=${assignment.id}">
              <fmt:message key="commands.delete"/>
            </a>
          </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </body>
</html>
