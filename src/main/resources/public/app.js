let getFetch = () => {
    let nameVar = document.getElementById("name").value;
    fetch("/movie?name="+nameVar).then(response => response.json())
        .then((response) => {
            document.getElementById("getrespmsg").innerHTML ="";
            let container = document.createElement("div");
            container.innerHTML = "<div class=\"respuesta\">"+
                                                  "<h1> Titulo: " + response.Title + "</h1>"+
                                                  "<div class =\"poster\">"+
                                                  "<img src=\"" + response.Poster +"\">"+
                                                  "</div>"+
                                                  "<h2> A&ntilde;o: " + response.Year+ "</h2>"+
                                                  "<p> Director: " + response.Director + "</p>"+
                                                  "<p> Clasificaci&oacute;n : " + response.Rated + "</p>"+
                                                  "<p> Genero: " + response.Genre + "</p>"+
                                                  "<p> Sinopsis: " + response.Plot + "</p>"+
                                                  "</div>\n";
                    document.getElementById("getrespmsg").appendChild(container);
                    document.getElementById("getrespmsg").classList.remove("display-none");
        })
};