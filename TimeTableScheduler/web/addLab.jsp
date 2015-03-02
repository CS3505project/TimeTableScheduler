<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
%>
        <div class="hidden" name="context" value="addLab"></div>
        <hgroup class="animate">
        	<h1>Add Practical</h1>
        	<h2>Step 1 of 2</h2>
        </hgroup>
        <form id="createMeetingForm" action="scheduleLab.jsp" method="GET">
                <label for="moduleCode">Module</label>
                <% out.println(output.createModuleDropDown(user.getUserID())); %>
                <label for="duration">Duration:</label>
        	<input type="duration" name="duration" id="date" required="required"><br>
                <label for="semester">Semester:</label>
        	<input type="semester" name="semester" id="semester" required="required"><br>
        	<label for="submit">Submit:</label>
        	<input type="submit" id="submit" value="Next" class="animate">
        </form>
    <%
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>