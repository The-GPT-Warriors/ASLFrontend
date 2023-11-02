---
layout: default
title: Spacebook
permalink: /spacebook
---
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload Image</title>
    <style>
        body {
            background: black fixed;
            background-size: cover;
            color: #fff;
            font-family: 'Arial', sans-serif;
        }
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
        document.getElementById("uploadForm").addEventListener("submit", function (event) {
            event.preventDefault();
            const form = new FormData(this);
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "https://cosmic-backend.stu.nighthawkcodingsociety.com/image", true);
            xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
            xhr.onload = function () {
                if (xhr.status === 200) {
                    console.log("Image request successful!");
                } else {
                    console.error("Image request failed with status: " + xhr.status);
                }
            };
            xhr.send(form);
        });
    </script>
</body>
</html>
