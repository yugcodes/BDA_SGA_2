<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Authors catalog</title>
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
            <h1>Author roster</h1>
            <p class="subtitle">Create, revisit, refresh biographical footprints.</p>
        </div>
        <div>
            <a class="btn" href="<c:url value='/authors/new'/>">Register writer</a>
        </div>
    </section>

    <section class="card-surface">
        <c:if test="${not empty errorMessage}">
            <div class="notice error">${errorMessage}</div>
        </c:if>

        <table class="data">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Nationality</th>
                <th scope="col">Focus</th>
                <th scope="col" colspan="2">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${authors}" var="author">
                <tr>
                    <td><strong>${author.name}</strong></td>
                    <td>${author.nationality}</td>
                    <td>${author.biography}</td>
                    <td><a href="<c:url value='/authors/edit'><c:param name='id' value='${author.id}'/></c:url>">Revise profile</a></td>
                    <td>
                        <form class="inline-delete" action="<c:url value='/authors/delete'/>" method="post"
                              onsubmit="return confirm('Remove this writer and all their catalog titles?');">
                            <input type="hidden" name="id" value="${author.id}"/>
                            <button type="submit" class="btn danger">Drop entry</button>
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
