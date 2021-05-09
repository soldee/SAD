var proxy = "https://cors-anywhere.herokuapp.com/";
var products = [];
var index = 0;
            
let req = new XMLHttpRequest();
            
req.onload = function () {
    var price = req.responseXML.getElementById("price_inside_buybox").textContent;
	var title = req.responseXML.getElementById("productTitle").textContent;
	var img = req.responseXML.getElementById("imgTagWrapperId").innerHTML.split("\"");
	img = img[img.indexOf(" src=")+1];

    alert('TRACKING NEW PRODUCT!\n\n' + title.trim() + '\n' + price);
    products[index] = [title, price, img];
    console.log(products[0][1]);
    index++;

    product.innerHTML = display_products(); 
}

const submit = document.getElementById('submit');

submit.addEventListener('click', ()=> {
    const url = document.getElementById('url').value;
    req.open("GET", proxy+url);
    req.responseType = "document";
	req.send();
});


function display_products () {
    var html = "";
    for (var i=0; i<products.length; i++){
        html += "<h3>" + products[i][0] + "</h3" + "<h4>Price:" + products[i][1] + "</h4>"; 
    }
    return html;
}