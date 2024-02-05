<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Signup Page</title>
    <style>
        body {
            background-color: #cfdbe8;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: white;
            padding: 10px;
        }
        .title {
            font-size: 36px;
            font-weight: bold;
            color: #96c9ff;
        }
        .main {
            display: flex;
            flex-direction: column;
            align-items: center;
            background-color: white;
            margin-top: 20px;
            padding: 20px;
        }
        .form-container {
            width: 100%;
            max-width: 400px;
            margin-top: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #96c9ff;
            border-radius: 5px;
            box-sizing: border-box;
        }
        button {
            background-color: #3EB8F9;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }
        button:hover {
            background-color: #037dff;
        }
        .footer {
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: white;
            margin-top: 20px;
            padding: 10px;
        }
        .footer-text {
            font-size: 18px;
            color: #96c9ff;
        }
    </style>
</head>
<body>
    <div class="main">
        <div class="form-container">
            <form id="signup_form">
                <input type="text" placeholder="Name" id="name" required>
                <input type="date" placeholder="Birthday" id="dob" required>
                <input type="text" placeholder="Username" id="username" required>
                <input type="email" placeholder="Email" id="email" required>
                <input type="password" placeholder="Password" id="password" required>
                <button type="submit">Sign Up</button>
            </form>
        </div>
    </div>
    <script>
        document.getElementById("signup_form").addEventListener("submit", function (event) {
            event.preventDefault();
            signup_user();
        });

        function signup_user() {
            var requestOptions = {
                method: 'POST',
                mode: 'cors', // Corrected 'mod' to 'mode'
                cache: 'no-cache'
            };
            let email = document.getElementById("email").value;
            let password = document.getElementById("password").value;
            let name = document.getElementById("name").value;
            let dob = document.getElementById("dob").value;
            let username = document.getElementById("username").value;

            let requestURL = `http://localhost:8085/api/person/post?email=${email}&password=${password}&name=${name}&dob=${dob}&username=${username}`;
            console.log(requestURL);
            fetch(requestURL, requestOptions)
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => Promise.reject(text));
                }
                return response.text(); // Or response.json() if your server returns JSON
            })
            .then(data => {
                alert("Signup Complete");
                window.location.href = "https://the-gpt-warriors.github.io/ASLFrontend/login";
            })
            .catch(error => {
                alert('An unexpected error occurred: ' + error);
            });
        }
    </script>
</body>
</html>