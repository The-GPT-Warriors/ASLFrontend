---
layout: default
title: Account
permalink: /account
---
<style>

</style>
<html>
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
        userDataContainer.innerHTML = `
          <h1><strong>${data.name}</strong></h1>
          <p>Email: ${data.email}</p>
          <p>Age: ${data.age}</p>
          <p>ID: ${data.id}</p>
          <button onclick="signOut()">Sign Out</button>
        `;
        console.log(data);
      })
      .catch(error => console.log('error', error));
}
</script>