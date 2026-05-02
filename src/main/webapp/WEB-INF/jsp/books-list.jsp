<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Books panorama</title>
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
            <h1>Books joined through authors</h1>
            <p class="subtitle">Each row merges table data at the relational seam via an inner join.</p>
        </div>
        <div>
            <a class="btn" href="<c:url value='/books/new'/>">Register title</a>
        </div>
    </section>

    <section class="card-surface">
        <c:if test="${not empty errorMessage}">
            <div class="notice error">${errorMessage}</div>
        </c:if>
        <table class="data">
            <thead>
            <tr>
                <th scope="col">Title</th>
                <th scope="col">Genre</th>
                <th scope="col">Season</th>
                <th scope="col">Scope</th>
                <th scope="col">Author</th>
                <th scope="col">Nation</th>
                <th scope="col" colspan="2">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${rows}" var="row">
                <tr>
                    <td>${row.bookTitle}</td>
                    <td><span class="pill">${row.genre}</span></td>
                    <td>${row.publicationYear}</td>
                    <td>${row.pages} pp</td>
                    <td><strong>${row.authorName}</strong></td>
                    <td>${row.nationality}</td>
                    <td><a href="<c:url value='/books/edit'><c:param name='id' value='${row.bookId}'/></c:url>">Tune title</a></td>
                    <td>
                        <form class="inline-delete" action="<c:url value='/books/delete'/>" method="post"
                              onsubmit="return confirm('Remove this catalog title permanently?');">
                            <input type="hidden" name="id" value="${row.bookId}"/>
                            <button type="submit" class="btn danger">Drop title</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</main>
</body>
</html>
