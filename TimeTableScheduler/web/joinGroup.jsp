<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="GroupRequest" class="userDataPackage.GroupRequest" scope="request" />
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());

        GroupRequest.setValues(request, user);
%>
        <hgroup>
        	<h1>Join Group</h1>
        </hgroup>
        <form action="joinGroup.jsp" method="GET">
        	<label for="gname">Group:</label>
        	<select name="gname" id="gname">
                <% out.println(output.createJoinGroupDropDown(user.getUserID())); %>
                </select><br>
        	<label for="submit">Submit:</label>
        	<input type="submit" id="submit" value="Next">
        </form>

        <% if (GroupRequest.numErrors() > 0) { %>
            <div class="errors">
                <h1><span><% out.println(GroupRequest.numErrors()); %></span></h1>
                <p>
                    <% out.println(GroupRequest.getErrors()); %>
                </p>
            </div>
<%
        }
        if(GroupRequest.joinGroup((String)request.getParameter("gname"))) {
            response.sendRedirect("index.jsp");
        }
        GroupRequest.setFormLoaded(true);
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>
