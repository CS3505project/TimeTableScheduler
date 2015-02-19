<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (((userPackage.User) session.getAttribute("user")) != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createSignUp());
    }
%>