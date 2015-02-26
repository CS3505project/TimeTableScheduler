<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
%>
        <div class="hidden" name="context" value="addMeeting"></div>
        <hgroup class="animate">
        	<h1>Add Meeting</h1>
        	<h2>Step 1 of 2</h2>
        </hgroup>
        <form id="createMeetingForm" action="scheduleMeeting.jsp" method="GET">
                <% out.println(output.createMeetingFormGroupDropdown(user)); %>
                <label for="duration">Duration:</label>
        	<input type="duration" name="duration" id="date" required="required"><br>
                <label for="over-priority">Schedule Over:</label>
                <select id="over-priority" name="over-priority">
                    <% out.println(output.createPriorityDropDown()); %>
                </select>
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