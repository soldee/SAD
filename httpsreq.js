const https = require('https');
var url = 'https://www.amazon.es/dp/B07PW9VBK5/ref=gw_es_desk_mso_smp_mn_qc1_0920?pf_rd_r=Z3KCDYYJXF2XA5TGJHDF&pf_rd_p=8f4eb5df-0177-4a04-9a04-4748e48d3d8b&pd_rd_r=1fec0921-91b7-4423-aeee-2b11105ec9a1&pd_rd_w=HmqY1&pd_rd_wg=oQvD7&ref_=pd_gw_unk';
//var url = 'https://www.amazon.es/nuevo-echo-4a-generacion-sonido-de-alta-calidad-controlador-de-hogar-digital-integrado-y-alexa-antracita/dp/B085FXHR38/ref=p13n_ds_purchase_sim_1p_dp_desktop_4?pd_rd_w=8kCT9&pf_rd_p=dc5985cb-cfda-4321-9e2d-5adc044b9d37&pf_rd_r=XYNHP1ZYPS06MVSRQZSQ&pd_rd_r=89bd7120-550d-4bf8-8b9b-5253256bc83f&pd_rd_wg=eNorc&pd_rd_i=B085FXHR38&psc=1';
//var url = 'https://www.amazon.es/amazon-smart-plug-enchufe-inteligente-wifi-compatible-con-alexa/dp/B082YTW968/ref=p13n_ds_purchase_sim_1p_dp_desktop_1?pd_rd_w=oJWbm&pf_rd_p=dc5985cb-cfda-4321-9e2d-5adc044b9d37&pf_rd_r=ZHEF85GN4PX6N0P85QMH&pd_rd_r=f6acf01e-8a56-4bb0-87ea-a44de23e8c19&pd_rd_wg=f5X46&pd_rd_i=B082YTW968&psc=1';
var str = '';

https.get(url, (res) => {
    res.on('data', (d) => {
        str += d;
    })

    res.on('end', function() {
        var index = str.indexOf('price_inside_buybox');
        var price = str.substring(index+58, index+65);

        index = str.indexOf('productTitle');
        //var productTitle = str.substring(index, index+200);
        
        console.log(price);   
    })  
})