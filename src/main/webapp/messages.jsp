<%-- Success Messages --%>
<c:if test="${not empty message}">
    <div class="message">${message}</div>
    <c:remove var="message"/>
</c:if>
