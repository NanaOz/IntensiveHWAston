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
        <title>Authors</title>
    </head>
    <body>
        <h1>Authors</h1>
        <form action="/authors/search" method="get">
            <input type="text" name="name" placeholder="Search by name">
            <button type="submit">Search</button>
        </form>
        <a href="/authors/new">Add New Author</a>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Country</th>
                <th>Actions</th>
            </tr>
            <c:forEach items="${authors}" var="author">
                <tr>
                    <td>${author.id}</td>
                    <td>${author.name}</td>
                    <td>${author.country}</td>
                    <td>
                        <a href="/authors/edit/${author.id}">Edit</a>
                        <a href="/authors/delete/${author.id}">Delete</a>
                        <a href="/authors/${author.id}/books">View Books</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
