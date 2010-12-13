<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
    <title>Add a new assignment</title>
  </head>
  <body>
    <div class="breadcrumbs">
      <strong>You are here:</strong>
      <a href="index.jsp">OpenMentor</a> >> 
      <a href="selectAssignment.html">Submit an Assignment</a> >>
      <a href="listAssignments.html">Manage Assignments</a> >>
      Add assignment
    </div>
    <%@ include file="/messages.jsp"%>
    <div class="textheading">
      Edit an assignment for <c:out value="${assignment.courseId}"/>
    </div>
    <spring:bind path="assignment.*">
      <c:if test="${not empty status.errorMessages}">
        <div class="error">
          <c:forEach var="error" items="${status.errorMessages}">
            <c:out value="${error}" escapeXml="false"/><br />
          </c:forEach>
        </div>
     </c:if>
    </spring:bind>

    <p>Please give the title of the assignment:</p>
    <form method="post" action="editAssignment.html"
          onsubmit="return validateAssignment(this)">
      <spring:bind path="assignment.id">
        <input type="hidden" name="id" value="${status.value}"/> 
      </spring:bind>
      <table>
        <tr>
          <td>
            <spring:bind path="assignment.assignmentTitle">
              <input type="text" name="assignmentTitle" value="${status.value}"/>
              <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
          </td>
        </tr>
        <tr>
          <td>
            <input type="submit" class="button" name="save" value="Save"/>
            <c:if test="${not empty param.id}">
              <input type="submit" class="button" name="delete" value="Delete"/>
            </c:if>
          </td>
        </tr>
      </table>
    </form>
    <html:javascript formName="assignment"/>
  </body>
</html>
