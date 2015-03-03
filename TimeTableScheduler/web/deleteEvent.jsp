<%@page import="userDataPackage.DeleteRequest"%>
<%
    userPackage.User user = (userPackage.User)session.getAttribute("user");
    if (user != null) {        
        
        DeleteRequest delete = new DeleteRequest();
        delete.deleteMeeting((String)request.getParameter("meetingId"), (String)request.getParameter("userId"));
        
    } else {
        
%>
        <jsp:forward page="/login.jsp" />
<%
        
    }
%>

