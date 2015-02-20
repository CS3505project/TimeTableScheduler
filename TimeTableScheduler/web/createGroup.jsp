<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        out.println(output.createCreateGroupForm());
        out.println(output.createFooter());

        inputPackage.Input input = new inputPackage.input();
        if (input.createGroup(request.getAttribute("groupname"))) {
            // take to next of create group process
            out.println("<p>Group created</p>");
        } else {
            // error occurred
            out.println("<p>Error, group not created.</p>");
        }


    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>
