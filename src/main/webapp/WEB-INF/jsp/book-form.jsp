<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Book intake</title>
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
            <h1><c:choose><c:when test="${formMode == 'edit'}">Edit catalog entry</c:when><c:otherwise>New acquisition</c:otherwise></c:choose></h1>
            <p class="subtitle">
                Stitch titles to their storytellers across the library graph.
            </p>
        </div>
        <div>
            <a class="btn secondary" href="<c:url value='/books'/>">Back</a>
        </div>
    </section>

    <section class="card-surface" style="padding:1.5rem;">
        <c:choose>
            <c:when test="${formMode == 'edit'}">
                <form:form modelAttribute="book" action="${pageContext.request.contextPath}/books/edit" method="post" cssClass="stack">
                    <form:hidden path="id"/>
                    <div class="form-grid">
                        <div>
                            <form:label path="title" cssClass="label">Title</form:label>
                            <form:input cssClass="field" path="title"/>
                            <form:errors path="title" cssClass="error-inline" element="span"/>
                        </div>
                        <div>
                            <form:label path="publicationYear" cssClass="label">Publication year</form:label>
                            <form:input cssClass="field" path="publicationYear"/>
                        </div>
                        <div>
                            <form:label path="genre" cssClass="label">Shelf voice</form:label>
                            <form:input cssClass="field" path="genre"/>
                        </div>
                        <div>
                            <form:label path="pages" cssClass="label">Pages</form:label>
                            <form:input cssClass="field" path="pages"/>
                        </div>
                        <div>
                            <form:label path="authorId" cssClass="label">Author linkage</form:label>
                            <form:select cssClass="field" path="authorId">
                                <form:option value="" label="Select voice"/>
                                <form:options items="${authors}" itemValue="id" itemLabel="name"/>
                            </form:select>
                            <form:errors path="authorId" cssClass="error-inline" element="span"/>
                        </div>
                    </div>
                    <div style="margin-top:1.5rem;display:flex;gap:0.8rem;">
                        <button class="btn" type="submit">Sync metadata</button>
                    </div>
                </form:form>
            </c:when>
            <c:otherwise>
                <form:form modelAttribute="book" action="${pageContext.request.contextPath}/books" method="post" cssClass="stack">
                    <div class="form-grid">
                        <div>
                            <form:label path="title" cssClass="label">Title</form:label>
                            <form:input cssClass="field" path="title"/>
                            <form:errors path="title" cssClass="error-inline" element="span"/>
                        </div>
                        <div>
                            <form:label path="publicationYear" cssClass="label">Publication year</form:label>
                            <form:input cssClass="field" path="publicationYear"/>
                        </div>
                        <div>
                            <form:label path="genre" cssClass="label">Shelf voice</form:label>
                            <form:input cssClass="field" path="genre"/>
                        </div>
                        <div>
                            <form:label path="pages" cssClass="label">Pages</form:label>
                            <form:input cssClass="field" path="pages"/>
                        </div>
                        <div>
                            <form:label path="authorId" cssClass="label">Author linkage</form:label>
                            <form:select cssClass="field" path="authorId">
                                <form:option value="" label="Select voice"/>
                                <form:options items="${authors}" itemValue="id" itemLabel="name"/>
                            </form:select>
                            <form:errors path="authorId" cssClass="error-inline" element="span"/>
                        </div>
                    </div>
                    <div style="margin-top:1.5rem;display:flex;gap:0.8rem;">
                        <button class="btn" type="submit">Create catalog spine</button>
                    </div>
                </form:form>
            </c:otherwise>
        </c:choose>
    </section>
</main>
</body>
</html>
