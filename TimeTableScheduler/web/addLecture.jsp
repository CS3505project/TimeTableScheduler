<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
%>
        <div class="hidden" name="context" value="addLecture" data-userId="<%= user.getUserID() %>"></div> 
        <h1 class="banner">Add Lecture</h1>
        <hgroup class="animate">
        	<h1>Add Lecture</h1>
        	<h2>Step 1 of 2</h2>
        </hgroup>
        <form id="createMeetingForm" action="scheduleLecture.jsp" method="GET">
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
        	<input type="semester" name="semester" id="semester" required="required"><br>
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