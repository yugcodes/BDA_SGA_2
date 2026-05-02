<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Author form</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="anonymous"/>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+3:wght@400;600;700&display=swap" rel="stylesheet"/>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
</head>
<body>
<jsp:include page="layout-header.jsp"/>

<main class="page">
    <section class="title-row">
        <div>
            <h1><c:choose><c:when test="${formMode == 'edit'}">Curate biography</c:when><c:otherwise>Invite a new author</c:otherwise></c:choose></h1>
            <p class="subtitle">
                Narrative arcs begin with accurate metadata.
            </p>
        </div>
        <div>
            <a class="btn secondary" href="<c:url value='/authors'/>">Back</a>
        </div>
    </section>

    <section class="card-surface" style="padding:1.5rem;">
        <c:choose>
            <c:when test="${formMode == 'edit'}">
                <form:form modelAttribute="author" action="${pageContext.request.contextPath}/authors/edit" method="post">
                    <form:hidden path="id"/>
                    <div class="form-grid">
                        <div>
                            <form:label path="name" cssClass="label">Displayed name</form:label>
                            <form:input cssClass="field" path="name"/>
                            <form:errors path="name" cssClass="error-inline" element="span"/>
                        </div>
                        <div>
                            <form:label path="nationality" cssClass="label">Nationality</form:label>
                            <form:input cssClass="field" path="nationality"/>
                        </div>
                    </div>
                    <div style="margin-top:1rem;">
                        <form:label path="biography" cssClass="label">Biography snapshot</form:label>
                        <form:textarea cssClass="field" path="biography"/>
                    </div>
                    <div style="margin-top:1.5rem;display:flex;gap:0.8rem;">
                        <button class="btn" type="submit">Persist changes</button>
                    </div>
                </form:form>
            </c:when>
            <c:otherwise>
                <form:form modelAttribute="author" action="${pageContext.request.contextPath}/authors" method="post">
                    <div class="form-grid">
                        <div>
                            <form:label path="name" cssClass="label">Displayed name</form:label>
                            <form:input cssClass="field" path="name"/>
                            <form:errors path="name" cssClass="error-inline" element="span"/>
                        </div>
                        <div>
                            <form:label path="nationality" cssClass="label">Nationality</form:label>
                            <form:input cssClass="field" path="nationality"/>
                        </div>
                    </div>
                    <div style="margin-top:1rem;">
                        <form:label path="biography" cssClass="label">Biography snapshot</form:label>
                        <form:textarea cssClass="field" path="biography"/>
                    </div>
                    <div style="margin-top:1.5rem;display:flex;gap:0.8rem;">
                        <button class="btn" type="submit">Create author shell</button>
                    </div>
                </form:form>
            </c:otherwise>
        </c:choose>
    </section>
</main>
</body>
</html>
