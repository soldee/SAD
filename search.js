var proxy = "https://cors-anywhere.herokuapp.com/";
let request = new XMLHttpRequest();
	

//Handler de la pagina
request.onload = function () {
	var elements = request.responseXML.querySelectorAll("[data-component-type=s-search-result]");
    var prices = [];
    var titles = [];
    var urls = [];
    var aux = 0;

    //Extraiem url, titol i preu de cada producte de la pagina
    for (var i=0; i<elements.length; i++) {
        prices[aux] = elements[i].querySelectorAll(".a-price-whole")[0];
        titles[aux] = elements[i].querySelectorAll("[data-component-type=s-search-result] h2 a")[0];

        if(prices[aux] != null & titles[aux]!=null){
            prices[aux] = prices[aux].textContent;
            if (prices[aux] == "0,00"){
                prices[aux] += " Kindle Version"
            }
            urls[aux] = "https://www.amazon.com/"+titles[aux].getAttribute('href');
            titles[aux] = titles[aux].textContent;
            aux++;
        }
    }

    //Generem html dinàmic
    var matrix = new Array(prices.length);
    for (var i = 0; i < matrix.length; i++) {
        matrix[i] = [titles[i], prices[i], urls[i]];
    }
    document.getElementById("data").innerHTML = makeTableHTML(matrix);
}


//Handler del botó submit
let button = document.getElementById("submit2");
button.addEventListener("click", () => {
    let keyword = document.getElementById("keyword").value;
    var url = "https://www.amazon.es/s?k=" + keyword ;
    request.open("GET", proxy+url);
    request.responseType = "document";
    request.send();
});


//Creacio de la taula html
function makeTableHTML(myArray) {
    var result = "<table border=1>";
    for(var i=0; i<myArray.length; i++) {
        result += "<tr>";
        
        result += "<td>"+"<a href=\""+myArray[i][2]+"\">"+myArray[i][0]+"</a></td>";
        result += "<td>"+myArray[i][1]+"</td>";
        
        result += "</tr>";
        }
        result += "</table>";
    return result;
}