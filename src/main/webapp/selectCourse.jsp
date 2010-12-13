<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
    <title>Courses</title>
  </head>
  <body>
    <div class="breadcrumbs">
      <strong>You are here:</strong> 
      <a href="index.jsp">OpenMentor</a> >> Choose a Course
    </div>
    <div class="textheading"> Choose a course </div>
    <p>
      Before you can use Open Mentor, you'll need to select a course
      to work with. Choose a course, and then press the "Select
      Course" button to work with that course.
    </p>
    <form id="selectCourses" action="selectCourse.html" method="post">
      <table class="list">
        <thead>
          <tr class="BlueBG">
            <th colspan="2">
              <div class="tableHeadingWhite">
                <fmt:message key="course.id"/>
              </div>
            </th>
            <th>
              <div class="tableHeadingWhite">
                <fmt:message key="course.courseTitle"/>
              </div>
            </th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="course" items="${courses}" varStatus="status">
            <c:choose>
              <c:when test="${status.count % 2 == 0}">
                <c:set var="rowClass" value="WhiteBG"/>
              </c:when>
              <c:otherwise>
                <c:set var="rowClass" value="OffWhiteBG"/>
              </c:otherwise>
            </c:choose>
            <c:set var="rowStyle" value=""/>
            <c:set var="buttonChecked" value=""/>
            <c:if test="${sessionScope.course == course.id}">
              <c:set var="rowStyle" value="font-weight: bold"/>
              <c:set var="buttonChecked" value="checked='checked'"/>
            </c:if>
            <tr class="${rowClass}" style="${rowStyle}">
              <td><input type="radio" name="course" 
              value="${course.id}" ${buttonChecked}/></td>
              <td>${course.id}</td>
              <td>${course.courseTitle}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <p>
        <input type="submit" value="Select course"/>
      </p>
    </form>
    <c:if test="${! empty sessionScope.course}">
      <p>
        Now you can upload an assessment, to get feedback from Open
        Mentor about your use of comments.
      </p>
      <ul>
        <li><a href="selectAssignment.html">Upload an assignment</a></li>
      </ul>
    </c:if>    
  </body>
</html>
