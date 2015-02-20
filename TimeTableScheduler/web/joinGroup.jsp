<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        out.println(output.createJoinGroupForm());
        out.println(output.createFooter());
        
        inputPackage.Input input = new inputPackage.input();
        if (input.joinGroup(request.getAttribute(""))) {
            // take to next of create group process
            out.println("<p>Added to group</p>");
        } else {
            // error occurred
            out.println("<p>Error, group not added.</p>");
        }

    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>
