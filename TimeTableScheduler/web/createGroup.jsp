<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="GroupRequest" class="userDataPackage.GroupRequest" scope="request" />
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        if (!((String)session.getAttribute("prevRequest")).equals(request.getRequestURI())) {
            session.setAttribute("GroupRequest", new userDataPackage.GroupRequest());
            session.setAttribute("prevRequest", request.getRequestURI());
        }
        
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        GroupRequest.setValues(request, user);
        if (GroupRequest.isFormLoaded()) {
            GroupRequest.setGroupName((String)request.getParameter("groupName"));
            GroupRequest.setGroupType((String)request.getParameter("groupType"));
            GroupRequest.setUsersInGroup((String[])request.getParameterValues("inGroup"));
        }
%>
        <hgroup>
        	<h1>Create Group</h1>
        </hgroup>
        <form action="createGroup.jsp" method="GET">
            <div><label for="groupName">Group Name:</label>
            <input type="text" name="groupName" id="groupName" value="<%= GroupRequest.getGroupName() %>" required="required"></div>
            <div><label for="groupType">Group Type:</label>
            <select name="groupType">
                <% out.println(output.createGroupTypeDropDown()); %>
            </select></div>
            <div><label for="inGroup">In Group:</label>                
            <select multiple="multiple" name="inGroup">
                <% out.println(output.createIndividualDropDown(user.getUserID())); %>
            </select></div>
            <div><label for="submit">Submit:</label>
            <input type="submit" id="submit" value="Next"></div>
        </form>
        <% if (GroupRequest.numErrors() > 0) { %>
            <div class="errors">
                <h1><span><% out.println(GroupRequest.numErrors()); %></span></h1>
                <p>
                    <% 
                        out.println(GroupRequest.getErrors());
                    %>
                </p>
            </div>
 <%
        }
        if (GroupRequest.createGroup()) {
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
