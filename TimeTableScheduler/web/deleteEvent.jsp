<%@page import="userPackage.User"%>
<%@page import="userDataPackage.DeleteRequest"%>
<%
    User user = (User)session.getAttribute("user");
    if (user != null) {        
        
        DeleteRequest delete = new DeleteRequest();
        if (delete.deleteMeeting((String)request.getParameter("eventId"), ((User)session.getAttribute("user")).getUserID())) {
            response.sendRedirect("index.jsp");
        }
    } else {
        
%>
        <jsp:forward page="/login.jsp" />
<%
        
    }
%>

