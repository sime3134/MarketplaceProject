
const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    } else {
        console.error('Could not find login form element!');
}

function handleLogin(event) {
  event.preventDefault(); // Prevent form submission and page reload

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  const formData = new FormData();
  formData.append('username', username);
  formData.append('password', password);

  fetch('/api/v1/login', {
    method: 'POST',
    body: formData
  })
  .then(async response => {
    if (response.status === 200) {
      window.location.href = '/';
    } else if (response.status === 401) {
      const errorResponse = await response.json();
      showNotification(errorResponse.message, NotificationType.Error);
    } else {
      throw new Error('Something went wrong on the server. Try again later!');
    }
  })
  .catch(error => {
    showNotification(error.message, NotificationType.Warning);
  });
}
