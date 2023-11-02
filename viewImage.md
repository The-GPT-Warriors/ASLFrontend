---
layout: default
title: Image
permalink: /image
---
<html>
<head>
    <title>Space Image Viewer</title>
    <style>
        body {
            background-color: #000;
            color: #fff;
            font-family: Arial, sans-serif;
            text-align: center;
        }
        h1 {
            font-size: 36px;
        }
        #fileNameInput {
            background-color: #333;
            border: 1px solid #555;
            color: #fff;
            padding: 10px;
            margin: 10px;
        }
        button {
            background-color: #0077ff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #0055cc;
        }
        #imageContainer {
            margin-top: 20px;
        }
        img {
            max-width: 100%;
            margin: 10px;
        }
    </style>
</head>
<body>
    <h1>Space Image Viewer</h1>
    <input type="text" id="fileNameInput" placeholder="Enter file name">
    <button onclick="fetchImage()">Fetch Image</button>
    <div id="imageContainer"></div>
    <script>
        function fetchImage() {
            const fileName = document.getElementById('fileNameInput').value;
            const imageContainer = document.getElementById('imageContainer');
            if (fileName) {
                const endpoint = `https://cosmic-backend.stu.nighthawkcodingsociety.com/image/${fileName}`;
                fetch(endpoint)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.blob();
                    })
                    .then(blob => {
                        const imageURL = URL.createObjectURL(blob);
                        const image = document.createElement('img');
                        image.src = imageURL;
                        imageContainer.innerHTML = '';
                        imageContainer.appendChild(image);
                    })
                    .catch(error => {
                        console.error('Error fetching image:', error);
                        imageContainer.innerHTML = 'Image not found';
                    });
            }
        }
        function fetchAllImages() {
            const imageContainer = document.getElementById('imageContainer');
            imageContainer.innerHTML = '';
            fetch('https://cosmic-backend.stu.nighthawkcodingsociety.com/images')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    data.forEach(fileName => {
                        const imageURL = `https://cosmic-backend.stu.nighthawkcodingsociety.com/image/${fileName}`;
                        const image = document.createElement('img');
                        image.src = imageURL;
                        imageContainer.appendChild(image);
                    });
                })
                .catch(error => {
                    console.error('Error fetching images:', error);
                });
        }
        // Call fetchAllImages when the page loads
        fetchAllImages();
    </script>
</body>
</html>
