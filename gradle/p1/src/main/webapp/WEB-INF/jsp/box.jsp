<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
 <head>
    <title>Gradle JukeBox</title>
 </head>
 <body>
     <p>Hey there! ${name}</p>
     <form>
        <select id="playlist">
            <c:forEach var="item" items="${playlist}">
                <option>
                    <c:out value="${item}"/>
                </option>
            </c:forEach>
        </select>
     </form>
 </body>
</html>
