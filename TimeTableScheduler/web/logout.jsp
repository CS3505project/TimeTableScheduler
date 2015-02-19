<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- add soemthing that will destroy the session info--%>
<%
    session.setAttribute("user", null);
    session.setAttribute("UserType", null);
    outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
    out.println(output.createLogout());
%>
