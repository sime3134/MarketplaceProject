const registerForm = document.getElementById("register-form");
if(registerForm) {
    registerForm.addEventListener('submit', handleRegister);
} else {
    console.error('Could not find register form element!');
}

function handleRegister(event) {
    event.preventDefault(); // Prevent form submission and page reload

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('email').value;
    const dateOfBirth = document.getElementById('dateOfBirth').value;

    const formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);
    formData.append('firstName', firstName);
    formData.append('lastName', lastName);
    formData.append('email', email);
    formData.append('dateOfBirth', dateOfBirth);

    try {
      fetch('/api/v1/register', {
        method: 'POST',
        body: formData
      })
      .then(async response => {
        if (response.status === 201) {
          window.location.href = '/login';
        } else if (response.status === 400) {
          const errorResponse = await response.json();
          showError(errorResponse.message, ErrorType.Error);
        } else {
          throw new Error('Something went wrong... Try again later!');
        }
      })
    } catch (error) {
      showError(error.message, ErrorType.Error);
    }
}