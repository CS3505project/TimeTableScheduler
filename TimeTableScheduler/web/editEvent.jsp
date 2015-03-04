<%@page import="timeTablePackage.EventType"%>
<%@page import="timeTablePackage.TimeTable"%>
<%@page import="timeTablePackage.EventPriority"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="EditRequest" class="userDataPackage.EditRequest" scope="session"/>
<%    
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        EditRequest.setValues(request, user);
        if (EditRequest.isFormLoaded()) {
            EditRequest.setDate((String)request.getParameter("date"));
            EditRequest.setTime((String)request.getParameter("time"));
            EditRequest.setDescription((String)request.getParameter("description"));
            EditRequest.setVenue((String)request.getParameter("venue"));
        } else {
            EditRequest.setup((String)request.getParameter("eventId"));
        }
        
        TimeTable timeTable = TimeTable.getPreSetTimeTable();
        timeTable.setDisplayWeek((String)request.getParameter("displayDate"));
        timeTable.setupTimeSlots();
        timeTable.initialiseTimeTable(EditRequest.getUsersInMeeting());
        
        EditRequest.setTimeTable(timeTable);
        
        out.println(output.createSuggestedTimeTable(timeTable, 1,
                                                    EventPriority.MEETING.getPriority(),
                                                    true));
        out.println(output.createTimeTableNav(timeTable.getDisplayWeek(), request));
%>
        <div class="hidden" name="context" value="editEvent" data-userId="<%= user.getUserID() %>"></div> 
        <hgroup class="animate">
        	<h1>Edit Event</h1>
        </hgroup>

        <form id="editEvent" action="editEvent.jsp" method="GET">
            <div>
                <label for="date">Date:</label>
                <input type="date" name="date" id="date" value="<%= EditRequest.getDate() %>" required="required"><br>
            </div>
            <div>
                <label for="time">Time:</label>
                <input type="text" name="time" id="time" value="<%= EditRequest.getTime() %>" required="required"><br>
            </div>
            <div>
                <label for="description">Description:</label>
                <input type="textarea" name="description" id="description" value="<%= EditRequest.getDescription() %>" required="required"><br>
            </div>
            <div>
                <label for="venue">Venue:</label>
                <input type="text" name="venue" id="venue" value="<%= EditRequest.getVenue() %>" required="required"><br>
            </div>
            <div>
                <label for="submit">Submit:</label>
                <input type="submit" id="submit" value="Next" class="animate">
            </div>
        </form>
<%      
        if (EditRequest.numErrors() > 0) {
            out.println(output.displayErrors(EditRequest.numErrors(), EditRequest.getErrors()));
        } else if(EditRequest.isValid() && !EditRequest.checkConflict() ) {
            out.println(output.displayErrors(1, "You are trying to schedule over an existing event"));
        } else {
            if (EditRequest.editEvent()) {
                response.sendRedirect("index.jsp");
            }
        }
        EditRequest.setFormLoaded(true);
        out.println(output.createFooter());
        
    } else {
%>
        <jsp:forward page="/login.jsp" />
<%
    }
%>
