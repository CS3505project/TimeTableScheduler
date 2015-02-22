<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
    // insert error message from java bean as parameter
    out.println(output.createSignUp(userPackage.UserType.Student));

%>