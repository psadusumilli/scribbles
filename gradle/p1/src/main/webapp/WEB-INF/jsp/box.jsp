<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
 <head>
    <title>Gradle JukeBox</title>
    <link href="static/css/base.css" rel="stylesheet">
    <script src="static/js/jquery-1.9.1.min.js"></script>
    <script src="static/js/base.js"></script>
 </head>
 <body>
     <p>Hey there, ${name}</p>
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
