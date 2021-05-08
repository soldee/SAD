let form = document.getElementById("keyword");
form.addEventListener('search', (event) => {
    var keyword = event.returnValue;
});

var proxy = "https://cors-anywhere.herokuapp.com/";
var url = "https://www.amazon.es/s?k=" + keyword + "&i=amazon-devices&__mk_es_ES=%C3%85M%C3%85%C5%BD%C3%95%C3%91&ref=nb_sb_noss";
console.log(keyword);

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

let req = new XMLHttpRequest();

req.open("GET", proxy+url);
req.responseType = "document";
		

req.onload = function () {
	var elements = req.responseXML.querySelectorAll("[data-component-type=s-search-result]");
    var prices = [];
    var titles = [];
    for (var i=0; i<elements.length; i++) {
        prices[i] = elements[i].querySelectorAll(".a-price-whole")[0].textContent;
        titles[i] = elements[i].querySelectorAll(".a-size-medium")[0].textContent;
    }

    var matrix = new Array(prices.length);
    for (var i = 0; i < matrix.length; i++) {
        matrix[i] = [titles[i], prices[i]];
    }
    document.getElementById("data").innerHTML = makeTableHTML(matrix);
}

req.send();		