<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        out.println(output.createDummyTable());
        out.println(output.createMeetingForm());
        out.println(output.createFooter());

        if (session.getAttribute("user") != null) {

        }
    
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>
