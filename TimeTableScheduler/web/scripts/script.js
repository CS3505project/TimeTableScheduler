//run on pageload
$(document).ready(function(){
    /////////////////////////////////
    //Make Meeting Form Interactive//
    /////////////////////////////////
    $("#individualSelectDiv").hide();
    
    $('#createMeetingForm input').on('change', function(){
        var checked = $('input[name="withType"]:checked', '#createMeetingForm').val();
        switch (checked){
            case 'personal':
                $("#individualSelect").prop('disabled', true);
                $("#groupSelect").prop('disabled', true);

                $("#individualSelectDiv").hide("drop", {direction: "vertical"}, 300);
                $("#groupSelectDiv").hide("drop", {direction: "vertical"}, 300);
                break;
            case 'group':
                $("#individualSelect").prop('disabled', true);
                $("#groupSelect").prop('disabled', false);

                $("#individualSelectDiv").hide("drop", {direction: "vertical"}, 300);
                $("#groupSelectDiv").show("drop", {direction: "vertical"}, 300);
                break;
            case 'individual':
                $("#individualSelect").prop('disabled', false);
                $("#groupSelect").prop('disabled', true);

                $("#individualSelectDiv").show("drop", {direction: "vertical"}, 300);
                $("#groupSelectDiv").hide("drop", {direction: "vertical"}, 300);
                break;
        }
    });
    //////////////////////////////
    //Create the popup box node //
    //////////////////////////////
    helpToggle = false;
    var helpBox = document.createElement("div");
    helpBox.setAttribute("class","popup");
    helpBox.setAttribute("id","helpPopup");
    var closeButton = document.createElement("button");
    closeButton.setAttribute("class","animate");
    closeButton.onclick = function(){
        $("#helpPopup").hide("drop", {direction: "left"}, 250);
        //reset help button to show rather than hide popup
        document.getElementById("help").onclick = function(){
            displayHelp();
            };
        };
    $(closeButton).html("Close");
    $(helpBox).append("<div><h1></h1><p></p></div>");
    $(helpBox).append(closeButton);
    $("body").append(helpBox); 
    //put context in the help button
    
    $("#help").click(function(){
        displayHelp();
    });
    $("#helpPopup").hide();
    
    ////////////////////////////////////////////////////////////
    //Get User's Unread Messages with AJAX and display red box//
    ////////////////////////////////////////////////////////////
    var userID = $("div[name='context']").attr("data-userID");
    //get the number of messages from the servlet
    $.get('UserMessageServlet',{userID:userID},function(data, textStatus) {
        numMessages = data;
        if(numMessages > 0){
            if(numMessages <=20){
                $("#msg").append("<span class='unread'>" + numMessages + "</span>");
            }else{
                $("#msg").append("<span class='unread'>20+</span>");
            }
        }
    }, "text");

    ///////////////////////////////////////////////////////////
    //set flashing animation for clicked animated class nodes//
    ///////////////////////////////////////////////////////////
    $(".animate").click(function(){
        $(this).effect("highlight", {color:"#777788"}, 125 );
    });
    /////////////////////////////////////////////////////
    //put selectable info into form from table on click//
    /////////////////////////////////////////////////////
    $(".selectable").click(function(){
        $("td.selected").removeClass("selected");
        $(this).addClass("selected");
        var infoNode = $("div.hidden", this);
        var date = infoNode.attr("data-date");
        var time = infoNode.attr("data-time");
        $('#date').val(date);
        $('#time').val(time);
    });
    //////////////////////////////////////////////////////////////////
    //Show event description when event in timetable is hovered over//
    //////////////////////////////////////////////////////////////////
    $(".hoverable").hover(
        function(){//on mouse over
            var description = $(this).attr("data-description");
            $(this).append("<div class='relative'><div class='popupDescription' id='popupDescription'>"+ description+"</div></div>");
            $('#popupDescription').hide();
            $('#popupDescription').show("fade", {}, 150);
        },
        function(){//on mouse leave
            $('#popupDescription').hide("fade", {}, 150);
            $('#popupDescription').remove();
        });
    /////////////////////////////////////////////////////////
    //add little blue triangle to current page on side menu//
    /////////////////////////////////////////////////////////
    var page = document.location.href.match(/[^\/]+$/)[0];
    var split = page.split('?');
    split = split[0];
    
    $("nav a[href='"+split+"']").append("<span class='triangle'></span>");
    $("nav a[href='"+split+"']").addClass("current");
    
    ///////////////////////////////////////////////////////
    //make the selected filter highlited in the filter bar//
    ////////////////////////////////////////////////////////
    var splitByAmpersand = page.split('&');
    $("ul.filters li a[href='"+splitByAmpersand[0]+"']").addClass("selected");
    
    /////////////////////////////////////////////
    //update the messagecounter every x seconds//
    /////////////////////////////////////////////
    var intervalLength = 10000;
    
    $(function(){
        setInterval(function() {
            var currentMessages = $("#msg span").text();
            currentMessages = currentMessages.match(/\d+/g);
            currentMessages = parseInt(currentMessages, 10);
            
            $.get('UserMessageServlet',{userID:userID},function(data, textStatus) {
                numMessages = data;
                numMessages = numMessages.match(/\d+/g);
                numMessages = parseInt(numMessages, 10);
                
                if(numMessages !== currentMessages && numMessages > 0){

                    $("#msg span").remove();
                    
                    if(numMessages <=20){
                        $("#msg").append("<span class='unread'>" + numMessages + "</span>");
                    }else{
                        $("#msg").append("<span class='unread'>20+</span>");
                    }
                    $("#msg span").removeClass("unread");
                    $("#msg span").addClass("unread");
                }
                if(numMessages === 0){
                    $("#msg span").remove();
                }
            }, "text");
        },intervalLength);}
    );
}); 


function displayHelp(){
    var context = $("div[name='context']").attr("value");
    if(helpToggle){//help is onscreen, hide the popup
        $("#helpPopup").hide("drop", {direction: "left"}, 250);
        //set the helpToggle to show that help is now shown
        helpToggle = true;
    }
    else{//help is hidden, show the popup
        $("#helpPopup").show("drop", {direction: "left"}, 250);
        //set the helpToggle to show that help is now hidden
        helpToggle = false;
    }
    //Switch on the context name, if the context is meeting, get info from the meetong JSON file
    switch(context){
        case "placeholder":
             $("#helpPopup div p").html("this is placeholder text");//add ajax stuff to get the context help from JSON files
            break;
        case "addMeeting":
            $("#helpPopup div h1").html("Add a Meeting");
            $("#helpPopup div p").html("Meeting help asdasd adg a dg adg a adg a a dg adg adg  adgf adg  gdag dag adg   gddgdgdgdggd<br> asdasdasd");//add ajax stuff to get the context help from JSON files
            break;
        default:
            $("#helpPopup div h1").html("Help");
            $("#helpPopup div p").html("No help file for given context");//add ajax stuff to get the context help from JSON files
    }
}
