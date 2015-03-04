<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
%>
        <div class="hidden" name="context" value="addLab" data-userId="<%= user.getUserID() %>"></div> 
        <h1 class="banner">Add Practical</h1>
        <hgroup class="animate">
        	<h1>Add Practical</h1>
        	<h2>Step 1 of 2</h2>
        </hgroup>
        <form id="createMeetingForm" action="scheduleLab.jsp" method="GET">
            <div>
                <label for="moduleCode">Module</label>
                <% out.println(output.createModuleDropDown()); %>
            </div>
            <div>
                <label for="duration">Duration:</label>
                <select name="duration">
                    <% out.println(output.createDurationDropDown()); %>
                </select>
            </div>
            <div>
                <label for="semester">Semester:</label>
                <select name="semester">
                    <option value="1">Semester 1</option>
                    <option value="2">Semester 2</option>
                </select>
            </div>
            <div>
                <label for="submit">Submit:</label>
        	<input type="submit" id="submit" value="Next" class="animate">
            </div>
        </form>
    <%
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>