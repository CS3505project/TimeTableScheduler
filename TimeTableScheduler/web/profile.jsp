<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    outputPackage.Output output = new outputPackage.Output(request);
    out.println(output.createHeader());
    out.println(output.createProfileBox());
    out.println(output.createFooter());
%>
