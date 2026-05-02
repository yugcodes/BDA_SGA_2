<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="topbar">
  <div class="brand">Catalog studio</div>
  <nav>
    <a href="<c:url value='/books'/>">Books overview</a>
    <a href="<c:url value='/authors'/>">Authors</a>
    <a class="cta" href="<c:url value='/books/new'/>">Add book</a>
    <a class="cta alt" href="<c:url value='/authors/new'/>">Add author</a>
  </nav>
</div>
