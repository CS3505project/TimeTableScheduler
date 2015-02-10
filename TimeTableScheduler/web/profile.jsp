<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    outputPackage.Output output = new outputPackage.Output(request);
    out.println(output.createHeader());
    //profile info
    out.println(output.createFooter());
%>
