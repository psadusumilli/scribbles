<html>
 <head>
    <title>Gradle JukeBox</title>
 </head>
 <body>
     <p>Hey there!</p>
     <form>
        <select id="playlist">
            <c:forEach var="item" items="${playlist}">
                <option value="<c:out value='${item}'">
                    <c:out value="${item}" />
                </option>
            </c:forEach>
        </select>
     </form>
 </body>
</html>
