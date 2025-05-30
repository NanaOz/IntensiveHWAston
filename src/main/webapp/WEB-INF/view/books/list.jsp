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
        <title>Books</title>
    </head>
    <body>
        <h1>Books</h1>
        <div>
            <a href="/books/available">Available Books</a>
            <a href="/books/new">Add New Book</a>
        </div>
        <form action="/books/search" method="get">
            <input type="text" name="title" placeholder="Search by title">
            <button type="submit">Search</button>
        </form>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Year</th>
                <th>Available</th>
                <th>Actions</th>
            </tr>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.author.name}</td>
                    <td>${book.year}</td>
                    <td>${book.available ? 'Yes' : 'No'}</td>
                    <td>
                        <a href="/books/edit/${book.id}">Edit</a>
                        <a href="/books/delete/${book.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
