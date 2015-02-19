<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    outputPackage.Output output = new outputPackage.Output(request, userPackage.UserType.STUDENT);
    out.println(output.createHeader());
    out.println(output.createDummyTable());
    out.println(output.createMeetingForm());
    out.println(output.createFooter());
    
    if (session.getAttribute("user") != null) {
        
    }
%>
