const logoutButton = document.getElementById("logout-button");
  if (logoutButton) {
    logoutButton.addEventListener("click", logout);
  } else {
    console.error("could not find the logout button");
  }

function logout() {
    fetch("/api/v1/logout", {
      method: "POST",
    })
      .then(async (response) => {
        if (response.ok) {
          window.location.href = "/login";
        } else {
          // Handle error or show a message to the user.
          console.error("Logout failed.");
        }
      })
      .catch((error) => {
        // Handle network errors or other issues.
        console.error("Error during logout:", error);
      });
  }