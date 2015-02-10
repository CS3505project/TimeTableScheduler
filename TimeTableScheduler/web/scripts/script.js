function createHelp(){
	//find context node to find out what page we need to display help about
	var contextNode = document.getElementById("context");
}

function displayHelp(context){
	// add the elements into the dom tree with the help for a given page
        var helpBox = document.createElement("div");
        helpBox.setAttribute("class","popup");
        helpBox.innerHTML = "";//add ajax stuff to get the context help from JSON files
}
