<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader()); 
%>
            <h1>Profile</h1> 
            <div class='profile'>
                <div class='img'></div>
                <h1><%= user.getFirstName() %> <%= user.getSurName() %></h1>
                <h2><%= user.getEmail() %></h2>
                <p>Hello,m I am a profile</p>
                <p>Hsdfsdf am a zzproxfile blbsjbjbv</p>
                <div>
                    <h1>Some Stats Maybe</h1>
                    <p>blah blah blah</p>
                </div>
            </div>
<%
        out.println(output.createFooter());

    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>
