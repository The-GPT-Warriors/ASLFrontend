---
layout: default
title: API's
permalink: /trivia
---
<html>
<script>
    const data = JSON.stringify({
    	field: 'domain',
    	page: 0,
    	term: ''
    });
    const xhr = new XMLHttpRequest();
    xhr.withCredentials = false;
    xhr.addEventListener('readystatechange', function () {
    	if (this.readyState === this.DONE) {
    		retdata = this.responseText;
            console.log(retdata)
            // filter and log text based off moon entry
            // if moon entry is not found, log "moon not found"
            retdata = JSON.parse(retdata)
            // for item in data['bodies'], check to see if text entry matches
            // if it does, log the item
            // if it doesn't, log "moon not found"
    	}
    });
    xhr.open('GET', 'https://api.le-systeme-solaire.net/rest/bodies/');
    xhr.send(data);
    function moonSearch() {
    console.log("searching");
    var moon = document.getElementById("moon").value.toLowerCase(); // Convert user input to lowercase
    var moonFound = false; // Track if the moon is found
    for (var i = 0; i < retdata['bodies'].length; i++) {
        var moonName = retdata['bodies'][i]['englishName'].toLowerCase(); // Convert moon name to lowercase for comparison
        if (moonName === moon) {
            console.log(retdata['bodies'][i]);
            document.getElementById("mooninfo").innerHTML = retdata['bodies'][i]['englishName'] + " is a moon of " + retdata['bodies'][i]['aroundPlanet']['planet'] + " and has a radius of " + retdata['bodies'][i]['meanRadius'] + " km. It has an axial tilt of " + retdata['bodies'][i]['axialTilt'] + " degrees.";
            moonFound = true;
            break;
        }
    }
    if (!moonFound) {
        document.getElementById("mooninfo").innerHTML = "Moon not found";
    }
}

</script>

<h1>Moon API 😮 😮</h1>

<input type="text" id="moon" name="moon" placeholder="Enter a moon name" required>

<button onclick="moonSearch()">Search</button>

<div id="mooninfo"></div>

<h1>Project API 🤫 🤫</h1>