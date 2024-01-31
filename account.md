---
layout: default
title: Account
permalink: /account
---
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

        #userData {
            margin-top: 20px;
            text-align: center;
        }

        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }

        table, th, td {
            border: 2px solid #96c9ff;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #3EB8F9;
            color: white;
            font-weight: bold;
        }

        button {
            background-color: #3EB8F9;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            margin-top: 20px;
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
<body>
  <div class="main">
        <div class="container">
            <div class="header">
                <div class="title">Account</div>
            </div>
            <div id="userData"></div>
            <table>
                <thead id="userData">
                </thead>
                <tbody>
                    <!-- Add rows with user data -->
                </tbody>
            </table>
            <button onclick="signOut()">Sign Out</button>
        </div>
    </div>
  <script>
      function fetchUserData() {
      var requestOptions = {
        method: 'GET',
        mode: 'cors',
        cache: 'default',
        credentials: 'include',
      };
      fetch("http://localhost:8085/api/person/jwt", requestOptions)
        .then(response => {
                if (!response.ok) {
                    const errorMsg = 'Login error: ' + response.status;
                    console.log(errorMsg);
                    switch (response.status) {
                        case 401:
                            alert("Please log into your account");
                            window.location.href = "https://the-gpt-warriors.github.io/ASLFrontend/login";
                            break;
                        case 403:
                            alert("Access forbidden. You do not have permission to access this resource.");
                            break;
                        case 404:
                            alert("User not found. Please check your credentials.");
                            break;
                        default:
                            alert("Login failed. Please try again later.");
                    }
                    return Promise.reject('Login failed');
                }
                return response.json();
            })
        .then(data => {
          const userDataContainer = document.getElementById("userData");
          console.log(data.name);
          userDataContainer.innerHTML = `
            <h1><strong>${data.name}</strong></h1>
            <p>ID: ${data.id}</p>
            <p>Username: ${data.username}</p>
            <p>Email: ${data.email}</p>
            <p>Age: ${data.dob}</p>
          `;
          console.log(data);
        })
        .catch(error => console.log('error', error));
        }
  </script>
</body>