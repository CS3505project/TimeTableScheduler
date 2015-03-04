<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="GroupRequest" class="userDataPackage.GroupRequest" scope="session" />
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {  
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        GroupRequest.setValues(request, user);
        if (GroupRequest.isFormLoaded()) {
            GroupRequest.setGroupName((String)request.getParameter("groupName"));
            GroupRequest.setGroupType((String)request.getParameter("groupType"));
            GroupRequest.setUsersInGroup((String[])request.getParameterValues("inGroup"));
        }
%>
        <div class="hidden" name="context" value="createGroup" data-userId="<%= user.getUserID() %>"></div> 
        <h1 class="banner">Create Group</h1>
        <hgroup>
        	<h1>Create Group</h1>
        </hgroup>
        <form action="createGroup.jsp" method="GET">
            <div>
                <label for="groupName">Group Name:</label>
                <input type="text" name="groupName" id="groupName" value="<%= GroupRequest.getGroupName() %>" required="required">
            </div>
            <div>
                <label for="groupType">Group Type:</label>
                <select name="groupType">
                    <% out.println(output.createGroupTypeDropDown()); %>
                </select>
            </div>
            <div class="autoHeight">
                <label for="inGroup">In Group:<span>(Hold the Ctrl key to select multiple items)</span></label>                
                <select multiple="multiple" name="inGroup">
                    <% out.println(output.createIndividualDropDown(user.getUserID())); %>
                </select>
            </div>
            <div>
                <label for="submit">Submit:</label>
                <input type="submit" id="submit" value="Next">
            </div>
        </form>
<% 
        GroupRequest.setFormLoaded(true);
        if (GroupRequest.numErrors() > 0) {
            out.println(output.displayErrors(GroupRequest.numErrors(), GroupRequest.getErrors()));
        }
        if (GroupRequest.createGroup()) {
            out.println("Group created successfully.");
        }
        
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>
