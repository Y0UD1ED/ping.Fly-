let map = L.map("mymap").setView([47.202200, 38.935373], 16); 

var points_x = [];
var points_y = [];
var points = [];
var N = 0;
var layerGroup = L.layerGroup().addTo(map);

points.push([47.202200, 38.935373]);

map.on('click', function(e){
    var location = e.latlng;
    var marker = L.marker([e.latlng.lat, e.latlng.lng]).addTo(map);
    marker.addTo(layerGroup);
    N = N+1;
    var coord = [marker.getLatLng().lat, marker.getLatLng().lng];
    points.push(coord);
    points_x.push(marker.getLatLng().lat);
    points_y.push(marker.getLatLng().lng);
    draw(points);
    

});

function draw(marray){
    var line = L.polyline(marray).addTo(map);
    line.addTo(layerGroup);
}


L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", { 
attribution: "© OpenStreetMap contributors", 
maxZoom: 20, 
minZoom: 2, 
tileSize: 512, 
zoomOffset: -1, 
}).addTo(map); 

let ytut = L.marker([47.202200, 38.935373]).addTo(map);
ytut.bindPopup("<b>Стартовая точка</b><br>Вы здесь."); 

document.getElementById('myButton').onclick = myFunction

function myFunction() {
    //console.log(point[-1].lat)
    fetch("https://fd73-2001-41d0-306-45a5-00.ngrok-free.app/flight", {
        method: "POST",
        body: JSON.stringify({
            "flightData": [
                {"oX":points_x[0],
                "oY":points_y[0],
                "height":100
                },
                {"oX":points_x[1],
                "oY":points_y[1],
                "height":100
                },
                {"oX":points_x[2],
                "oY":points_y[2],
                "height":100
                },
                {"oX":points_x[3],
                "oY":points_y[3],
                "height":100
                }
            ]
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    }).then((response) => response.json())
    .then((json) => console.log(json));
  }



