
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
  const errorMessageElement = document.getElementById('errorMessage');

  const formData = new FormData();
  formData.append('username', username);
  formData.append('password', password);

try {
  fetch('http://localhost:5001/api/v1/login', {
    method: 'POST',
    body: formData
  })
  .then(async response => {
    if (response.status === 200) {
      window.location.href = 'http://localhost:5001/';
    } else if (response.status === 401) {
      const errorResponse = await response.json();
      errorMessageElement.innerText = errorResponse.message;
      errorMessageElement.style.display = 'block';
    } else {
      throw new Error('Something went wrong... Try again later!');
    }
  })
} catch (error) {
  errorMessageElement.innerText = error.message;
  errorMessageElement.style.display = 'block';
}
}