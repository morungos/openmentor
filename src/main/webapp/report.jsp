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
      <a href="index.jsp">OpenMentor</a> >> View ${type} report
    </div>
    
    <h3>Detailed report</h3>
        
    <p>
    This page shows the report in more detail, including the full
    text for the comments. From here, you can also:
    </p>
    
    <ul>
      <li>
        <a href="listReports.html?type=${type}">Go back to the chart view</a>
      </li>
      <li>
        <c:if test="${not full}">
          <a href="report.html?RequestType=Table&ID=${id}&ReportFor=${type}&full=true">
            See all comments
          </a>
        </c:if>
        <c:if test="${full}">
          <a href="report.html?RequestType=Table&ID=${id}&ReportFor=${type}">
            See a sample of comments
          </a>
        </c:if>
      </li>
    </ul>
    
    <p>&nbsp;</p>
    
    <c:set var="rowCount" value="0"/>
    <c:set var="limit" value="3"/>
    <c:if test="${full}">
      <c:set var="limit" value="100"/>
    </c:if>
    
    <table style="commentTable">
      <thead>
        <tr class="topHeader">
          <th style="width: 9%">Category</th>
          <th style="width: 6%">Actual</th>
          <th style="width: 6%">Ideal</th>
          <th style="width: 79%">Comment text</th>
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="cat" items="${categories}">
          <tr class="${((rowCount % 2) == 0) ? 'OffWhiteBG' : 'WhiteBG'}" valign="top">
            <th>
            <c:choose>
              <c:when test='${cat == "A"}'>Positive comments</c:when>
              <c:when test='${cat == "B"}'>Teaching points</c:when>
              <c:when test='${cat == "C"}'>Asking questions</c:when>
              <c:when test='${cat == "D"}'>Negative comments</c:when>
            </c:choose>
            <br/>
            <span class="smallText">
            <a href="help.jsp#group${cat}">(explain)</a>
            </span>
            </th>
            <c:set var="rowCount" value="${rowCount + 1}"/>
            <td class="number"><p>${actualcounts[cat]}<br/>(${actual[cat]}%)</p></td>
            <td class="number"><p>${idealcounts[cat]}<br/>(${ideal[cat]}%)</p></td>
            <td>
            <c:set var="count" value="1"/>
            <c:forEach var="comment" items="${comments[cat]}">
              <c:if test="${count <= limit}"><p>${comment}</p></c:if>
              <c:set var="count" value="${count + 1}"/>  
            </c:forEach>
            <c:if test="${count > (limit + 1)}">
              <p><i>...(and ${count - limit - 1} more)</i></p>
            </c:if>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </body>
</html>
