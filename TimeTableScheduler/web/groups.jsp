<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    outputPackage.Output output = new outputPackage.Output(request);
    out.println(output.createHeader());
    //list of all groups as long as the user is an admin, else, permission denied message
    out.println(output.createFooter());
%>