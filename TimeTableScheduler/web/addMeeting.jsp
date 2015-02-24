<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="MeetingRequest" class="userDataPackage.MeetingRequest" scope="session"/>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        out.println(output.createUserTimeTable(user.getUserID(),(String)request.getAttribute("filter")));
        MeetingRequest.setValues(request, user);
%>
        <hgroup>
        	<h1>Add Meeting</h1>
        	<h2>Step 1 of 2</h2>
        </hgroup>
        <jsp:setProperty name="MeetingRequest" property="*"/>
        <form>
        	<label for="date">Date:</label>
        	<input type="date" name="date" id="date" value="<%= MeetingRequest.getDate() %>" required="required"><br>
        	<label for="select\">Group:</label>
        	<select id="select">
                // stuff from database
        	</select><br>\n" +
        	<label for="meetingName">Meeting Name:</label>
        	<input type="text" name="meetingName" id="meetingName" value="<%= MeetingRequest.getMeetingName() %>" required="required"><br>
                <label for="description">Description:</label>
        	<input type="textarea" name="description" id="description" value="<%= MeetingRequest.getDescription() %>" required="required"><br>
                <label for="priority">Priority:</label>
                <label for="time">Time:</label>
        	<input type="text" name="time" id="time" value="<%= MeetingRequest.getTime() %>" required="required"><br>
        	<label for="venue">Venue:</label>
        	<input type="text" name="venue" id="venue" value="<%= MeetingRequest.getVenue() %>" required="required"><br>
        	<label for="submit">Submit:</label>
        	<input type="submit" id="submit" value="Next">
        </form>
        <p>
            <%-- print errors and comit valid values to database --%>
            <%= MeetingRequest.getErrors()%>
            <%= MeetingRequest.createMeeting() %>
        </p>
    <%
        out.println(output.createFooter());
    } else {
        
    %>
    <jsp:forward page="/login.jsp" />
    <%
        
    }
%>
