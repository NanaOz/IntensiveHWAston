<%--
  Created by IntelliJ IDEA.
  User: 79826
  Date: 30.05.2025
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>${book.id == null ? 'Add' : 'Edit'} Book</title>
    </head>
    <body>
        <h1>${book.id == null ? 'Add' : 'Edit'} Book</h1>
        <form action="/books" method="post">
            <input type="hidden" name="id" value="${book.id}">
            <div>
                <label>Title:</label>
                <input type="text" name="title" value="${book.title}" required>
            </div>
            <div>
                <label>Author:</label>
                <select name="author.id" required>
                    <option value="">Select Author</option>
                    <c:forEach items="${authors}" var="author">
                        <option value="${author.id}" ${book.author != null && book.author.id == author.id ? 'selected' : ''}>
                                ${author.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label>Year:</label>
                <input type="number" name="year" value="${book.year}" required>
            </div>
            <div>
                <label>Available:</label>
                <input type="checkbox" name="available" ${book.available ? 'checked' : ''}>
            </div>
            <button type="submit">Save</button>
        </form>
        <a href="/books">Back to List</a>
    </body>
</html>
