---
layout: default
title: Delete
permalink: /delete
---
<html>
<head>
    <script>
        function deleteImage(fileName) {
            // Endpoint URL with the fileName parameter
            const endpointUrl = `https://cosmic-backend.stu.nighthawkcodingsociety.com/image/delete/${fileName}`;
            // new XMLHttpRequest object
            const xhr = new XMLHttpRequest();
            // DELETE request to the specified URL
            xhr.open("DELETE", endpointUrl, true);
            xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
            // callback function to handle the response
            xhr.onload = function () {
                if (xhr.status === 200) {
                    // request successful
                    console.log("DELETE request successful");
                } else {
                    // request failed
                    console.error("DELETE request failed");
                }
            };
            // send DELETE request
            xhr.send();
        }
    </script>
</head>
<body>
    <button onclick="deleteImage('GitHub')">Delete Image</button>
</body>
</html>
