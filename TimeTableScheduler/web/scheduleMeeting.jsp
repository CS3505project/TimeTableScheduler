<%@page import="userPackage.UserType"%>
<%@page import="timeTablePackage.TimeTable"%>
<%@page import="timeTablePackage.EventPriority"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="MeetingRequest" class="userDataPackage.MeetingRequest" scope="session"/>
<%    
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        MeetingRequest.setValues(request, user);
        if (!MeetingRequest.isSetup()) {
            System.out.println("setting up");
            MeetingRequest.setup((String)request.getParameter("withType"),
                                 (String)request.getParameter("duration"),
                                 (String)request.getParameter("individualID"),
                                 (String)request.getParameter("groupID"));            
        } else {
            MeetingRequest.setDate((String)request.getParameter("date"));
            MeetingRequest.setTime((String)request.getParameter("time"));
            MeetingRequest.setDescription((String)request.getParameter("description"));
            MeetingRequest.setVenue((String)request.getParameter("venue"));
        }
        
        TimeTable timeTable = TimeTable.getPreSetTimeTable();
        timeTable.setDisplayWeek((String)request.getParameter("displayDate"));
        timeTable.setupTimeSlots();
        timeTable.initialiseTimeTable(MeetingRequest.getUsersToMeet());
        
        MeetingRequest.setTimeTable(timeTable);
        
        out.println(output.createSuggestedTimeTable(timeTable,
                                                    MeetingRequest.getDuration(),
                                                    EventPriority.MEETING.getPriority(),
                                                    true));
        
        out.println(output.createTimeTableNav(timeTable.getDisplayWeek(), request));
%>
        <div class="hidden" name="context" value="scheduleMeeting" data-userId="<%= user.getUserID() %>"></div>
        <hgroup class="animate">
        	<h1>Add Meeting</h1>
        	<h2>Step 2 of 2</h2>
        </hgroup>
        <form id="createMeetingForm" action="scheduleMeeting.jsp" method="GET">
            <div>
                <label for="date">Date:</label>
                <input type="text" name="date" id="date" value="<%= MeetingRequest.getDate() %>" required="required"><br>
            </div>
            <div>
                <label for="time">Time:</label>
                <input type="text" name="time" id="time" value="<%= MeetingRequest.getTime() %>" required="required"><br>
            </div>
            <div>
                <label for="description">Description:</label>
                <input type="textarea" name="description" id="description" value="<%= MeetingRequest.getDescription() %>" required="required"><br>
            </div>
            <div>
                <label for="venue">Venue:</label>
                <input type="text" name="venue" id="venue" value="<%= MeetingRequest.getVenue() %>" required="required"><br>
            </div>
            <div>
                <label for="submit">Submit:</label>
                <input type="submit" id="submit" value="Next" class="animate">
            </div>
        </form>
<%      
        if (MeetingRequest.numErrors() > 0) {
            out.println(output.displayErrors(MeetingRequest.numErrors(), MeetingRequest.getErrors()));
        } else if(MeetingRequest.isValid() && MeetingRequest.checkConflict() ) {
            out.println(output.displayErrors(1, "You are trying to schedule over an existing event"));
        } else {
            if (MeetingRequest.createMeeting()) {
                response.sendRedirect("index.jsp");
            } 
        }
        MeetingRequest.setFormLoaded(true);
        out.println(output.createFooter());
        
    } else {
%>
        <jsp:forward page="/login.jsp" />
<%
    }
%>
