<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null 
            && (userPackage.UserType)(session.getAttribute("userType")) == userPackage.UserType.ADMIN)) {    
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        //list of all groups as long as the user is an admin, else, permission denied message
        out.createGroupList();
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>