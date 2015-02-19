<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    //destroy user object 
    session.setAttribute("user", null);
    session.setAttribute("UserType", null);
    //disply page output
    outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
    out.println(output.createLogout());
%>
