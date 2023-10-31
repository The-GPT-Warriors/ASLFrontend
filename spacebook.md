---
layout: default
title: Spacebook
permalink: /spacebook
---
<html>
<head>
    <title>Cosmic Carnage</title>
    <style>
        canvas {
        background-color: black;
        display: block;
        margin: 0 auto;
    }
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        h1 {
            text-align: center;
        }
        #createPlayerForm {
            background-color: gray;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        label {
            font-weight: bold;
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            color: black;
        }
        input[type="submit"] {
            background-color: gray;
            color: black;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
</head>
<html>
<head>
    <title>Spacebook</title>
</head>
<body>
    <h1>Upload Files</h1>
    <form id="fileUploadForm" action="#" enctype="multipart/form-data">
        <input type="file" id="fileInput" name="file">
        <button type="button" onclick="uploadFile()">Upload</button>
    </form>
    <p id="responseMessage"></p>
    <script>
        function uploadFile() {
            const fileInput = document.getElementById('fileInput');
            const file = fileInput.files[0];
            if (!file) {
                alert("Please select a file to upload.");
                return;
            }
            const formData = new FormData();
            formData.append('file', file);
            fetch('https://cosmic-backend.stu.nighthawkcodingsociety.com/api/spacebook/', { //add to this
                method: 'POST',
                body: formData,
            })
            .then(response => response.json())
            .then(data => {
                document.getElementById('responseMessage').textContent = data.message;
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById('responseMessage').textContent = 'Upload failed.';
            });
        }
    </script>
</body>
