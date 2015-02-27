<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="GroupRequest" class="userDataPackage.GroupRequest" scope="session">
    <jsp:setProperty name="GroupRequest" property="*"/>
</jsp:useBean>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        GroupRequest.setValues(request, user);
        GroupRequest.setGroupName((String)request.getParameter("groupName"));
        GroupRequest.setGroupType((String)request.getParameter("groupType"));
        GroupRequest.setUsersInGroup((String[])request.getParameterValues("inGroup"));
%>
        <hgroup>
        	<h1>Create Group</h1>
        </hgroup>
        <form>
        	<div><label for="groupName">Group Name:</label>
        	<input type="text" name="groupName" id="groupName" value="<%= GroupRequest.getGroupName() %>" required="required"></div>
                <div><label for="groupType">Group Type:</label>
                <select name="groupType">
                    <% out.println(output.createGroupTypeDropDown()); %>
                </select></div>
                <div><label for="inGroup">In Group:</label>                
                <select multiple="multiple" name="inGroup">
                    <% out.println(output.createIndividualDropDown(user.getUserID())); %>
                </select>
                <div><label for="submit">Submit:</label>
        	<input type="submit" id="submit" value="Next"></div>
        </form>
        <p>
            <%-- print errors and comit valid values to database --%>
            <% 
                if (GroupRequest.isFormLoaded()) {
                    out.println(GroupRequest.getErrors());
                }
                GroupRequest.createGroup();
                
            %>
        </p>
 <%
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>
