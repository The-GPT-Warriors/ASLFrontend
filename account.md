---
layout: default
title: Account
permalink: /account
---
<html>
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
                    <button onclick="updateProfile()">Update</button>
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
                        <th id="action">Actions</th>
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
            let deleteId = data.id;
            console.log(deleteId);
            const userProfileSection = document.getElementById("profileSection");
            const profilePicture = document.createElement('div');
            const profileDetails = document.createElement('div');
            profileDetails.classList.add('profile-details');
            const isAdmin = data.roles.some(role => role.name === "ROLE_ADMIN");
            const roles = data.roles.map(role => capitalizeFirstLetter(role.name.replace('ROLE_', '').toLowerCase())).join(', ');
            profileDetails.innerHTML = `
                <p><strong>Name:</strong> ${data.name}</p>
                <p><strong>Username:</strong> ${data.username}</p>
                <p><strong>Email:</strong> ${data.email}</p>
                <p><strong>Age:</strong> ${data.age}</p>
                <p><strong>Roles: </strong>${roles}</p>
            `;
            userProfileSection.appendChild(profileDetails);
            userProfileSection.dataset.isAdmin = isAdmin;
            fetchAllUserData();
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
            console.log(data);
            data.forEach(user => {
                const row = document.createElement('tr'); 
                const adminRole = user.roles.find(role => role.name === "ROLE_ADMIN");
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.age}</td>
                    <td>${adminRole ? 'Admin' : (user.roles && user.roles.length > 0 ? capitalizeFirstLetter(user.roles[0].name.replace('ROLE_', '').toLowerCase()) : 'None')}</td>
                    <td></td
                `;
                const deleteCell = row.querySelector('td:last-child');
                deleteCell.appendChild(createDelete(user.id));
                userDataContainer.appendChild(row);
            });
        })
            .catch(error => console.log('error', error));
        }
        fetchUserData();
        function capitalizeFirstLetter(str) {
            return str.charAt(0).toUpperCase() + str.slice(1);
        }
        function updateProfile() {
            alert("Update functionality to be implemented soon!");
        }
        function deleteUser(userId) {
            const requestOptions = {
                method: 'DELETE',
                mode: 'cors',
                cache: 'default',
                credentials: 'include',
            };
            fetch('http://localhost:8085/api/person/delete/${userId}', requestOptions)
                .then(response => {
                    if (!response.ok) {
                        const errorMsg = 'Delete user error: ' + response.status;
                        console.log(errorMsg);
                        switch (response.status) {
                            case 401:
                                alert("Please log into your account");
                                window.location.href = "{{site.baseurl}}/login";
                                break;
                            case 403:
                                alert("Access forbidden. You do not have permission to delete this user.");
                                break;
                            case 404:
                                alert("User not found. Please check the user ID.");
                                break;
                            default:
                                alert("Delete user failed. Please try again later.");
                        }
                        return Promise.reject('Delete user failed');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('User deleted successfully:', data);
                })
                .catch(error => console.log('error', error));
        }
        function createDelete(userId) {
            const userProfileSection = document.getElementById("profileSection");
            const admin = (userProfileSection.dataset.isAdmin === 'true');
            const actions = document.getElementById('action')
            if (!admin) {
                actions.style.display = 'none';
                return null;
            }
            const deleteButton = document.createElement('BUTTON');
            deleteButton.innerText = 'Delete';
            deleteButton.onclick = function() {
                if (confirm('Are you sure you want to delete this user?')) {
                    deleteUser(userId);
                }
            };
            return deleteButton;
        }
        function signOut() {
            var requestOptions = {
            mode: 'cors',
            cache: 'no-cache',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            redirect: 'follow',
            };
            fetch("http://localhost:8085/logout", requestOptions)
            .then(response => {
                if (!response.ok) {
                const errorMsg = 'Sign Out error: ' + response.status;
                console.log(errorMsg);
                alert("Sign Out failed. Please try again.");
                return Promise.reject('Sign Out failed');
                } else {
                    alert("Sign Out successful");
                    document.cookie.split(";").forEach(function(c) { document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/"); });
                    window.location.href = "{{site.baseurl}}/login";
                }
            })
            .catch(error => console.log('error', error));
        }
  </script>
</body>
</html>