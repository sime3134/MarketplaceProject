// Define the error types
const ErrorType = {
  Info: 'info',
  Error: 'error',
  Success: 'success'
};

// Define the function to show the error message
function showError(message, type) {
  // Create a new error message element
  const errorMessage = document.createElement('div');
  errorMessage.classList.add('error-message', type);

  const errorMessageText = document.createElement('span');
  errorMessageText.textContent = message;
  errorMessage.appendChild(errorMessageText);

  // Add the error message element to the error container
  const errorContainer = document.getElementById('error-container');
  errorContainer.appendChild(errorMessage);

  // Remove the error message element after a certain amount of time
  setTimeout(() => {
    errorContainer.removeChild(errorMessage);
  }, 5000);
}