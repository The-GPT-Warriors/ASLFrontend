---
layout: default
title: Spacebook
permalink: /spacebook
---
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload Image</title>
    <style>
        #uploadForm {
            margin: 50px;
            padding: 20px;
            background-color: rgba(0, 0, 0, 0.7);
            border-radius: 10px;
        }
        input[type="file"],
        input[type="text"],
        input[type="submit"] {
            display: block;
            margin: 10px 0;
            padding: 10px;
            width: 100%;
            border: none;
            border-radius: 5px;
            background-color: rgba(51, 51, 51, 0.8);
            color: #fff;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <form id="uploadForm" enctype="multipart/form-data" method="post">
        <input type="file" name="image" accept=".jpg, .png, .gif">
        <input type="text" name="fileName" placeholder="Enter file name">
        <input type="submit" value="Upload Image">
    </form>
<script>
    // aevent listener for the form submission
    document.getElementById("uploadForm").addEventListener("submit", function (event) {
        event.preventDefault();
        // create a new FormData object with the form data
        const form = new FormData(this);
        // create a new XMLHttpRequest object
        const xhr = new XMLHttpRequest();
        // POST request to backend
        xhr.open("POST", "https://cosmic-backend.stu.nighthawkcodingsociety.com/image", true);
        xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
        // define what to do when the request is complete
        xhr.onload = function () {
            // check if the request was successful
            if (xhr.status === 200) {
                console.log("Image request successful!");
            } else {
                // error checking
                console.error("Image request failed with status: " + xhr.status);
            }
        };
        // send the form data with the XMLHttpRequest
        xhr.send(form);
    });
</script>

</body>
</html>
