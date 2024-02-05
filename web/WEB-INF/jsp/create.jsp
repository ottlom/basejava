<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя: <input class="input-area" required type="text" name="fullName" size="50" value="${resume.fullName}"></dt>
        </dl>

        <h3>Контакты:</h3>
        <c:forEach var="typeContact" items="${ContactType.values()}">
            <dl>
                <dt>${typeContact.title}</dt>
                <dd>
                    <input class="input-area" name="${typeContact.name()}" value="${resume.getContact(typeContact)}">
                </dd>
            </dl>
        </c:forEach>

        <h3>Резюме:</h3>
        <c:forEach var="typeSection" items="${SectionType.values()}">
            <dl>
                <dt>${typeSection.title}</dt>
                <dd>
                    <textarea class="text-area" type="text" name="${typeSection.name()}" value="${resume.getSection(typeSection)}"></textarea>
                </dd>
            </dl>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
