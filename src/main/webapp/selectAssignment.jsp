<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
    <title>Submit an assignment</title>
  </head>
  <body>
    <div class="breadcrumbs">
      <strong>You are here:</strong>
      <a href="index.jsp">OpenMentor</a> >> 
      <a href="selectCourse.html">Choose a Course</a> >> 
      Submit an Assignment
    </div>
    <div class="textheading">Submit an assignment</div>
    <p>
	Using this page, you can upload a marked assignment for
	analysis by Open Mentor, or you can add a new assignment to
	this course.
    </p>

    <input type="button" value="Manage Assignments" 
           onclick="location.href='listAssignments.html'"/>
    
    <p>
      Type in the details for the marked assignment to be analysed by
      Open Mentor
    </p>

    <spring:bind path="uploadData.*">
      <c:if test="${not empty status.errorMessages}">
        <div class="error">
          <c:forEach var="error" items="${status.errorMessages}">
            <c:out value="${error}" escapeXml="false"/><br/>
          </c:forEach>
        </div>
     </c:if>
    </spring:bind>

    <form method="post" action="fileUpload.html" 
          enctype="multipart/form-data">
      <table>
        <tr>
          <th>Course</th>
          <td>
            <em><c:out value="${sessionScope.course}"/></em>
            <input type="hidden" name="course" 
                   value="<c:out value="${sessionScope.course}"/>"/>
          </td>
	</tr>
        <tr>
          <th>Assignment title</th>
          <td>
            <spring:bind path="uploadData.assignment.id">            
              <select size="1" name="assignment.id" class="fixedinputsize">
                <option value="${uploadData.assignment.id}">
                  ${uploadData.assignment.assignmentTitle}                
                </option>
                <c:forEach var="ass" items="${uploadData.assignments}"
                           varStatus="status">
                  <option value="${ass.id}">
                    ${ass.assignmentTitle}
                  </option>
                </c:forEach>
              </select>
          </spring:bind></td>
        </tr>
        <tr>
          <th>File path</th>
          <td><input type="file" name="file"/></td>
        </tr>
        <tr>
          <th>Marks category given</th>
          <td>
            <spring:bind path="uploadData.grade.label">            
              <select size="1" name="grade.label" class="fixedinputsize">
                <option value="${status.value}">
                    <c:out value="${uploadData.grade.descriptor}"/> 
                    <c:if test="${! empty uploadData.grade.descriptor}"> (</c:if>                 
                    <c:out value="${uploadData.grade.label}"/>
                    <c:if test="${! empty uploadData.grade.descriptor}">)</c:if>
                </option>
                <c:forEach var="grade" items="${uploadData.grades}"
                           varStatus="status">
                  <option value="${grade.label}">
                    <c:out value="${grade.descriptor}"/> 
                    (<c:out value="${grade.label}"/>)
                  </option>
                </c:forEach>
              </select>
            </spring:bind>
          </td>
        </tr>
        <tr>
          <th>Submitting student</th>
          <td>
            <spring:bind path="uploadData.student.orgId">            
              <select size="1" name="student.orgId" class="fixedinputsize">
                <option value="${status.value}">
                  ${uploadData.student.firstName} ${uploadData.student.lastName}
                </option>
                <c:forEach var="person" items="${uploadData.students}"
                           varStatus="status">
                  <option value="${person.orgId}">
                    ${person.firstName} ${person.lastName}
                  </option>
                </c:forEach>
              </select>
            </spring:bind>
          </td>
        </tr>
        <tr>
          <th>Marking tutor</th>
          <td>
            <spring:bind path="uploadData.tutor.orgId">
              <select size="1" name="tutor.orgId" class="fixedinputsize">
                <option value="${status.value}">
                  ${uploadData.tutor.firstName} ${uploadData.tutor.lastName}
                </option>
                <c:forEach var="person" items="${uploadData.tutors}"
                           varStatus="status">
                  <option value="${person.orgId}">
                    ${person.firstName} ${person.lastName}
                  </option>
                </c:forEach>
              </select>
            </spring:bind>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <br/>
            <input type="submit" value="Submit and analyse"/>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>
