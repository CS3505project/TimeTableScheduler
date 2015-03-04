<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {   
%>
        <div class="hidden" name="context" value="groups" data-userId="<%= user.getUserID() %>"></div> 
<%      
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        out.println(output.createAllGroupList(user.getUserID(), user.getUserType()));
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>