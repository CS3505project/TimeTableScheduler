<%@page import="toolsPackage.Database"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="MeetingRequest" class="userDataPackage.MeetingRequest" scope="session"/>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        if (MeetingRequest.getTimeTable() == null) {
            MeetingRequest.getUsersToMeet((String)request.getParameter("withType"), request);
            MeetingRequest.setTimeTable(timeTablePackage.TimeTable.getPreSetTimeTable());
            
            if (((String)request.getParameter("withType")).equals("personal")) {
                MeetingRequest.getTimeTable().initialiseTimeTable(user.getUserID());
            } else {
                MeetingRequest.getTimeTable().initialiseTimeTable(MeetingRequest.getUsersToMeet());
            }
            
            MeetingRequest.setDuration((String)request.getParameter("duration"));
            MeetingRequest.setOverPriority((String)request.getParameter("over-priority"));
        }
        
        out.println(output.createSuggestedTimeTable(MeetingRequest.getTimeTable(),
                                                    MeetingRequest.getDuration(),
                                                    MeetingRequest.getOverPriority(),
                                                    true));
        MeetingRequest.setValues(request, user);
        MeetingRequest.setActionCompleted(false);
%>
        <div class="hidden" name="context" value="scheduleMeeting"></div>
        <hgroup class="animate">
        	<h1>Add Meeting</h1>
        	<h2>Step 2 of 2</h2>
        </hgroup>
        <jsp:setProperty name="MeetingRequest" property="*"/>
        <form id="createMeetingForm" action="scheduleMeeting.jsp" method="GET">
                <label for="date">Date:</label>
        	<input type="date" name="date" id="date" value="<%= MeetingRequest.getDate() %>" required="required"><br>
                <label for="time">Time:</label>
        	<input type="text" name="time" id="time" value="<%= MeetingRequest.getTime() %>" required="required"><br>
        	<label for="priority">Priority:</label>
                <select id="priority" name="priority">
                    <% out.println(output.createPriorityDropDown()); %>
                </select>
                <label for="description">Description:</label>
        	<input type="textarea" name="description" id="description" value="<%= MeetingRequest.getDescription() %>" required="required"><br>
                <label for="venue">Venue:</label>
        	<input type="text" name="venue" id="venue" value="<%= MeetingRequest.getVenue() %>" required="required"><br>
        	<label for="submit">Submit:</label>
        	<input type="submit" id="submit" value="Next" class="animate">
        </form>
        <p>
            <%-- print errors and comit valid values to database --%>
            <%
                out.println(MeetingRequest.getErrors());
                MeetingRequest.createMeeting();
                if (MeetingRequest.isActionCompleted()) {
                    %>
                    <jsp:forward page="/index.jsp" />
                    <%
                }
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