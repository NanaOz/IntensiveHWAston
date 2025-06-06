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
        <title>${author.id == null ? 'Add' : 'Edit'} Author</title>
    </head>
    <body>
        <h1>${author.id == null ? 'Add' : 'Edit'} Author</h1>
        <form action="/authors" method="post">
            <input type="hidden" name="id" value="${author.id}">
            <div>
                <label>Name:</label>
                <input type="text" name="name" value="${author.name}" required>
            </div>
            <div>
                <label>Country:</label>
                <input type="text" name="country" value="${author.country}" required>
            </div>
            <button type="submit">Save</button>
        </form>
        <a href="/authors">Back to List</a>
    </body>
</html>
