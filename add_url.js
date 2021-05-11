var proxy = "https://cors-anywhere.herokuapp.com/";
var products = [];
var index = 0;
var p_update = false;
            
let req = new XMLHttpRequest();
            


//Handler de la pagina
req.onload = function () {

    var price = scrape_price();
    
    //Comprovem si es vol afegir url o actualitzar preus
    if (p_update == true) {
        products[index][1] == price;
        index++;
        p_update = false;
        return;
    }
    var title = scrape_title();
    var img = scrape_img();

    //Mostra missatge de confirmacio i actualitza array de productes guardats
    alert('TRACKING NEW PRODUCT!\n\n' + title.trim() + '\n' + price);
    var url  = document.getElementById('url').value;
    products[index] = [title, price, img, url];
    index++;

    //Generacio de codi dinamic html
    product.innerHTML = display_products(); 
}



function scrape_price() {
    var price = req.responseXML.getElementById("price_inside_buybox");
    if (price == null) {
        price = req.responseXML.querySelectorAll("#priceblock_ourprice");
    }
    if (price == null){
        price = req.responseXML.getElementById("price");
    }
    if (price == null){
        price = req.responseXML.getElementById("kindle-price");
    }
    return price.textContent;
}


function scrape_title() {
    var title = req.responseXML.getElementById("productTitle");
    if (title == null) {
        title = req.responseXML.getElementById('gc-asin-title');
    }
    return title.textContent;
}


function scrape_img() {
    var img = req.responseXML.getElementById("imgTagWrapperId");
    if (img == null) {
        img = false;
    }
    else {
        img = img.innerHTML.split("\"");
	    img = img[img.indexOf(" src=")+1];
    }
    return img;
}



//Handler del boto submit
const submit = document.getElementById('submit');
submit.addEventListener('click', ()=> {
    var url = document.getElementById('url').value;

    //Comprovem que el producte no estigui guardat
    for (i=0; i<products.length; i++) {
        if (products[i][3] == url) {
            alert('Product is already being tracked');
            return;
        }
    }
    //Fem la GET request de la pagina
    req.open("GET", proxy+url);
    req.responseType = "document";
	req.send();
});



//Mostrar tots els productes guardats
function display_products () {
    var html = "";
    for (var i=0; i<products.length; i++){
        html += "<h3>" + products[i][0] + "</h3>" + "<h4>Price:" + products[i][1] + "</h4>"; 
    }
    return html;
}



//Acualitza els preus
function price_update () {
    console.log("actualitza");
    if (products.length == 0) {
        return;
    }
    p_update = true;
    index = 0;
    for (i=0; i<products.length; i++) {
        req.open("GET", proxy+products[i][3]);
        req.responseType = "document";
	    req.send();
    }
    product.innerHTML = display_products();
}

setInterval (price_update, 30000);
