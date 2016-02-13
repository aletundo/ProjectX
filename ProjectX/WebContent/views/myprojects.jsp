<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/views/head-imports.jsp" />
<title>ProjectX - My Projects</title>
</head>
<body>
<jsp:include page="/views/navbar.jsp" />
<%
//Check session exists
String user = null;
if(session.getAttribute("user") == null){
    response.sendRedirect("/projectx/index.jsp");
}else user = (String) session.getAttribute("user");
String sessionID = null;
Cookie[] cookies = request.getCookies();
if(cookies !=null){
for(Cookie cookie : cookies){
    if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
}
}
%>
<div class="row center-block">
<div class="col-md-9 col-xs-12">
<div id="projects-panel" class = "panel panel-primary">
   <div class ="panel-heading">Your projects</div>
   
   <div class = "panel-body">
      <p>Hi <strong><%=user %></strong>, there are projects which you are involved in</p>
   </div>
   
   <div class = "list-group">
      <a class = "list-group-item" href=""><span class = "badge">New</span>Project1</a>
      <a class = "list-group-item" href=""><span class = "badge">New</span>Project2</a>
      <a class = "list-group-item" href=""><span class = "badge">New</span>Project3</a>
      <a class = "list-group-item" href=""><span class = "badge">New</span>Project4</a>
      <a class = "list-group-item" href=""><span class = "badge">New</span>Project5</a>
   </div>

</div>
</div>
<div class="col-md-3 col-xs-12">
Your Session ID=<%=sessionID %>
</div>
</div>
<jsp:include page="/views/footer.jsp" />
</body>
</html>