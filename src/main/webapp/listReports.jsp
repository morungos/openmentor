<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
    <title>Reports</title>
  </head>
  <body>
    <div class="breadcrumbs">
      <strong>You are here:</strong> 
      <a href="index.jsp">OpenMentor</a> >> View ${type} reports
    </div>
    <c:choose>
      <c:when test='${type == "course"}'>
        <div class="textheading"> Show course report </div>
        <h3>Summary report for ${course.id}</h3>
        <img src="report.jpg?RequestType=ChartImage&ID=${course.id}&ReportFor=course&ChartType=BarChart" 
             alt="Bar chart for ${course.id}"/>
        <p><em><a href="report.html?RequestType=Table&ID=${course.id}&ReportFor=course">See detailed information</a></em></p>
      </c:when>
      <c:when test='${type == "tutor"}'>
        <div class="textheading"> Show tutor reports </div>
        <c:forEach var="tutor" items="${tutors}" varStatus="status">
          <h3>Summary report for ${tutor.firstName} ${tutor.lastName} (${tutor.orgId})</h3>
          <img src="report.jpg?RequestType=ChartImage&ID=${tutor.orgId}&ReportFor=tutor&ChartType=BarChart" 
               alt="Bar chart for ${tutor.firstName} ${tutor.lastName}"/>
          <p><em><a href="report.html?RequestType=Table&ID=${tutor.orgId}&ReportFor=tutor">See detailed information</a></em></p>
        </c:forEach>
      </c:when>
      <c:when test='${type == "student"}'>
        <div class="textheading"> Show student reports </div>
        <c:forEach var="student" items="${students}" varStatus="status">
          <h3>Summary report for ${student.firstName} ${student.lastName} (${student.orgId})</h3>
          <img src="report.jpg?RequestType=ChartImage&ID=${student.orgId}&ReportFor=student&ChartType=BarChart" 
               alt="Bar chart for ${student.firstName} ${student.lastName}"/>
          <p><em><a href="report.html?RequestType=Table&ID=${student.orgId}&ReportFor=student">See detailed information</a></em></p>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <div class="textheading"> Invalid report type: ${type} </div>
        <p>
          This is an internal error in Open Mentor - please let us know
          that you got this, because it should never happen!
        </p>
      </c:otherwise>
    </c:choose>
  </body>
</html>
