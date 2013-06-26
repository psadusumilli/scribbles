<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
 <head>
    <title>Gradle JukeBox</title>
    <link href="static/css/bootstrap.css" rel="stylesheet">
    <link href="static/css/base.css" rel="stylesheet">
    <script src="static/js/jquery-1.9.1.min.js"></script>
    <script src="static/js/base.js"></script>
 </head>
 <body>
   <div class="container-fluid">
       <div class="row-fluid">
           <div id="jukebox" class="span4 offset2">
              <h2 class="juketitle">Gradle JukeBox</h2>
              <img src="static/images/jukebox.png"/>
              <form class="jukeform">
                 <select id="playlist">
                     <c:forEach var="item" items="${playlist}">
                         <option>
                             <c:out value="${item}"/>
                         </option>
                     </c:forEach>
                 </select>
                 <div id="display"></div>
              </form>
           </div>
       </div>
   </div>
 </body>
</html>
