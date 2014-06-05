<%@page import="javax.enterprise.context.SessionScoped"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <c:if test="${empty sessionScope.res || sessionScope.res.class != 'class modeles.Genre' && sessionScope.res.class != 'class modeles.Instrument'}">
        <c:redirect url="index.jsp"></c:redirect>
    </c:if>
    <c:if test="${sessionScope.res.class == 'class modeles.Genre'}">
        <c:set var="class" value="Genre"></c:set>
    </c:if>
    <c:if test="${sessionScope.res.class == 'class modeles.Instrument'}">
        <c:set var="class" value="Instrument"></c:set>
    </c:if>
    <c:import url="/WEB-INF/header.jsp">
        <c:param name="page" value="${class} - ${sessionScope.res.nom}" />
    </c:import>    
    <body>
        <c:import url="/WEB-INF/menu.jsp"></c:import>
        <c:if test="${class == 'Genre'}">
            <c:import url="/WEB-INF/listeGenre.jsp"></c:import>
        </c:if>
        <c:if test="${class == 'Instrument'}">
            <c:import url="/WEB-INF/listeInstrument.jsp"></c:import>
        </c:if>
    </body>
</html>