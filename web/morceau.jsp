<%@page import="javax.enterprise.context.SessionScoped"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <c:if test="${sessionScope.res.class != 'modeles.Morceau'}">
        <c:redirect url="index.jsp"></c:redirect>
    </c:if>
    <c:import url="/WEB-INF/header.jsp">
        <c:param name="page" value="Chanson - ${sessionScope.res.titre}" />
    </c:import>
    <body>
        <c:import url="/WEB-INF/menu.jsp"></c:import>
            <p class="disconnected">
            <c:out value="${sessionScope.res.instruments}"></c:out>
        </p>

    </body>
</html>