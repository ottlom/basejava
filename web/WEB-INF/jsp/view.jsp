<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>

<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>

    <c:forEach var="contactEntry" items="${resume.contacts}">
        <c:if test="${contactEntry.value != null && contactEntry.value.trim().length() != 0}">
            <b>${contactEntry.key.title}:</b>
            ${contactEntry.key.toHtml(contactEntry.value)}<br/>
        </c:if>
    </c:forEach>
    <hr>

    <c:forEach var="sectionEntry" items="${resume.sections}">
        <c:if test="${sectionEntry.value != null && sectionEntry.key.toHtml(sectionEntry.value).trim().length() != 0}">
            <h4>${sectionEntry.key.title}:</h4>
            <p>${sectionEntry.value}</p>
        </c:if>
    </c:forEach>
    <hr>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>