<%@page import="userPackage.UserType"%>
<%@page import="timeTablePackage.TimeTable"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {        
%> 
        <div class="hidden" name="context" value="index" data-userId="<%= user.getUserID() %>"></div> 
<%
        outputPackage.Output output = new outputPackage.Output(request, (userPackage.UserType)(session.getAttribute("userType")));
        out.println(output.createHeader());
        
        TimeTable timeTable = TimeTable.getPreSetTimeTable();
        timeTable.setDisplayWeek((String)request.getParameter("displayDate"));
        timeTable.setupTimeSlots();
        if (user.getUserType().equals(UserType.ADMIN)) {
            timeTable.intialiseAdminTimeTable(user.getUserID());
        } else {
            timeTable.initialiseTimeTable(user.getUserID());
        }
        
        out.println(output.createUserTimeTable(timeTable, (String)request.getParameter("filter"), user.getUserID()));
        out.println(output.createTimeTableNav(timeTable.getDisplayWeek(), request));
        out.println(output.createFooter());
    } else {
        
%>
        <jsp:forward page="/login.jsp" />
<%
        
    }
%>
