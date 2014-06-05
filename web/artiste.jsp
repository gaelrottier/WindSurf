<%@page import="javax.enterprise.context.SessionScoped"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <c:import url="/WEB-INF/header.jsp">
        <c:param name="page" value="Artiste - ${sessionScope.res.nom}" />
    </c:import>    
    <body>
        <c:import url="/WEB-INF/menu.jsp"></c:import>
        <c:if test="${not empty sessionScope.res}">
            <p class="disconnected">
            <c:out value="${sessionScope.res.morceaux}"></c:out>
            </p>
        </c:if>
    </body>
</html>