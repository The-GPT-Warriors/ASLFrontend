---
layout: default
title: Account
permalink: /account
---
<body>
  <div class="main">
        <div class="container">
            <div class="header">
                <div class="title">Account</div>
            </div>
            <div class="profile-section" id="profileSection">
                <div class="profile-picture">
                    <img src="https://github.com/The-GPT-Warriors/ASLFrontend/assets/107821010/52cd3a28-b6b5-44d2-a9d8-a1f7c50410c0" alt="Profile Picture">
                </div>
                <div class="profile-details">
                </div>
            </div>
            <div></div>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Age</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="userDataContainer">
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
                            window.location.href = "{{site.baseurl}}/login";
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
            const userProfileSection = document.getElementById("profileSection");
            const profilePicture = document.createElement('div');
            const profileDetails = document.createElement('div');
            profileDetails.classList.add('profile-details');
            profileDetails.innerHTML = `
                <p><strong>Name:</strong> ${data.name}</p>
                <p><strong>Username:</strong> ${data.username}</p>
                <p><strong>Email:</strong> ${data.email}</p>
                <p><strong>DOB:</strong> ${formatDOB(data.dob)}</p>
            `;
            userProfileSection.appendChild(profileDetails);
        })
        .catch(error => console.log('error', error));
    }
      function fetchAllUserData() {
      var requestOptions = {
        method: 'GET',
        mode: 'cors',
        cache: 'default',
        credentials: 'include',
      };
      fetch("http://localhost:8085/api/person/", requestOptions)
        .then(response => {
                if (!response.ok) {
                    const errorMsg = 'Login error: ' + response.status;
                    console.log(errorMsg);
                    switch (response.status) {
                        case 401:
                            alert("Please log into your account");
                            window.location.href = "{{site.baseurl}}/login";
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
            const userDataContainer = document.getElementById("userDataContainer");
            userDataContainer.innerHTML = ``
            data.forEach(user => {
            const row = document.createElement('tr'); 
            row.innerHTML = `
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${formatDOB(user.dob)}</td>
            `;
            userDataContainer.appendChild(row);
            });
        })
            .catch(error => console.log('error', error));
        }
        fetchUserData();
        fetchAllUserData();
        function formatDOB(dateString) {
            const date = new Date(dateString);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            return `${month}-${day}-${year}`;
        }
  </script>
</body>
