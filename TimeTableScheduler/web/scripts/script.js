function createHelp(){
	//find context node to find out what page we need to display help about
	var contextNode = document.getElementById("context");
        var context = "placeholder";//get this attribute from the context node
        var helpNode = document.getElementByID("help");
        
        //set the onclick method for the help link to displayHelp for the appropriate context
        var onclickMethod = "displayHelp('" + context + "')";
        helpNode.setAttribute("onclick",onclickMethod);
}

function displayHelp(context){
	// add the elements into the dom tree with the help for a given page
        var helpBox = document.createElement("div");
        helpBox.setAttribute("class","popup");
        
        //switch on the context name, if the context is meeting, get info from the meetong JSON file
        switch(context){
        case "placeholder":
            helpBox.innerHTML = "placeholder";//add ajax stuff to get the context help from JSON files
            break;
        default:
            helpBox.innerHTML = "";//add ajax stuff to get the context help from JSON files
        }
}
function removeHelp(){
        //remove the popup box from the dom tree
}