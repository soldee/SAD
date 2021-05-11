var proxy = "https://cors-anywhere.herokuapp.com/";
var products = [];
var index = 0;
var p_update = false;
console.log(sessionStorage.length);
            
let req = new XMLHttpRequest();
            
for (i=0; i<sessionStorage.length; i++) {
    var item = sessionStorage.getItem(i);
    if (item != null){
        
    }
}


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

    display_products();
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
        sessionStorage.setItem(i, products[i]);
    }  
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
    display_products();
}

setInterval (price_update, 300000);


//https://www.amazon.es/Cargador-Amazon-PowerFast-compatible-dispositivos/dp/B01I0IGFMK/ref=p13n_ds_purchase_sim_1p_dp_desktop_4?pd_rd_w=O8Vjr&pf_rd_p=dc5985cb-cfda-4321-9e2d-5adc044b9d37&pf_rd_r=GT27VXC23YG6FXTACSCH&pd_rd_r=c1dd0c6e-e432-4837-98c9-8ee130716142&pd_rd_wg=oykQR&pd_rd_i=B01I0IGFMK&psc=1
//https://www.amazon.es/Bedsure-Juego-S%C3%A1banas-90x190-200/dp/B08613Z67P/ref=zg_bs_kitchen_1?_encoding=UTF8&psc=1&refRID=2HRF79ZS8ZY8BTJMMBXJ