<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Webcam Capture</title>
</head>
<body>

<video autoplay playsinline></video>
<button id="captureButton">Capture and Upload</button>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        var video = document.querySelector('video');
        var captureButton = document.getElementById('captureButton');

        navigator.mediaDevices.getUserMedia({ video: true })
            .then(function (stream) {
                video.srcObject = stream;
                video.onloadedmetadata = function (e) {
                    video.play();
                };
            })
            .catch(function (err) {
                console.log('Error: ' + err);
            });

        captureButton.addEventListener('click', function () {
            var canvas = document.createElement('canvas');
            canvas.width = video.videoWidth;
            canvas.height = video.videoHeight;
            var ctx = canvas.getContext('2d');
            ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
            var dataURL = canvas.toDataURL('image/jpeg');

            // Convert dataURL to Blob
            var blobBin = atob(dataURL.split(',')[1]);
            var array = [];
            for (var i = 0; i < blobBin.length; i++) {
                array.push(blobBin.charCodeAt(i));
            }
            var blob = new Blob([new Uint8Array(array)], { type: 'image/jpeg' });

            // Create FormData and append the Blob
            var formData = new FormData();
            formData.append('image', blob);
            formData.append('fileName', 'webcam_image_' + Date.now() + '.jpg');

            // Send POST request to the backend
            fetch('http://localhost:8085/image', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                console.log(data.message);
                alert('Image uploaded successfully!');
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Image upload failed. Please try again.');
            });
        });
    });
</script>

</body>
</html>
