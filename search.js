var proxy = "https://cors-anywhere.herokuapp.com/";

function makeTableHTML(myArray) {
    var result = "<table border=1>";
    for(var i=0; i<myArray.length; i++) {
        result += "<tr>";
        for(var j=0; j<myArray[i].length; j++){
            result += "<td>"+myArray[i][j]+"</td>";
        }
        result += "</tr>";
        }
        result += "</table>";

    return result;
}

let request = new XMLHttpRequest();
	
request.onload = function () {
	var elements = request.responseXML.querySelectorAll("[data-component-type=s-search-result]");
    var prices = [];
    var titles = [];
    var aux = 0;
    for (var i=0; i<elements.length; i++) {
        prices[aux] = elements[i].querySelectorAll(".a-price-whole")[0];
        titles[aux] = elements[i].querySelectorAll("[data-component-type=s-search-result] h2 a")[0];

        if(prices[aux] != null & titles[aux]!=null){
            prices[aux] = prices[aux].textContent;
            titles[aux] = titles[aux].textContent;
            aux++;
        }
    }

    var matrix = new Array(prices.length);
    for (var i = 0; i < matrix.length; i++) {
        matrix[i] = [titles[i], prices[i]];
    }
    document.getElementById("data").innerHTML = makeTableHTML(matrix);
}

let button = document.getElementById("submit2");
button.addEventListener("click", () => {
    let keyword = document.getElementById("keyword").value;
    var url = "https://www.amazon.es/s?k=" + keyword + "&i=amazon-devices&__mk_es_ES=%C3%85M%C3%85%C5%BD%C3%95%C3%91&ref=nb_sb_noss";
    request.open("GET", proxy+url);
    request.responseType = "document";
    request.send();
});