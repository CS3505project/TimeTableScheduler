<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
    out.println(output.createHeader());
    out.println(output.createDummyTable());
    out.println(output.createFooter());
%>
