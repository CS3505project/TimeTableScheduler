<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
%>
        <div class="hidden" name="context" value="messages" data-userId="<%= user.getUserID() %>"></div> 
<%    
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        out.println(output.createMessages(user));
%>  
        <hgroup class="menu">
            <h1>Messages</h1>
            <h2>action: <input type="submit"></h2>
        </hgroup>
<%
        out.println(output.createFooter());
    } else {
        
%>
        <jsp:forward page="/login.jsp" />
<%
        
    }
%>