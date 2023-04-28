// Define the notification types
const NotificationType = {
  Info: 'info',
  Error: 'error',
  Success: 'success'
};

let notification = [];

// Define the function to show the notification message
function showNotification(message, type) {
  // Create a new notification message element
  const notificationMessage = document.createElement('div');
  notificationMessage.classList.add('notification-message', type);

  const notificationMessageText = document.createElement('span');
  notificationMessageText.textContent = message;
  notificationMessage.appendChild(notificationMessageText);

  // Add the notification message element to the error container
  const notificationContainer = document.getElementById('notification-container');
  notificationContainer.appendChild(notificationMessage);

  // Remove the notification message element after a certain amount of time
  setTimeout(() => {
    notificationContainer.removeChild(notificationMessage);
  }, 5000);
}

function updateNotificationView() {
  const notificationsList = document.getElementById('notificationsList');
    notificationsList.innerHTML = "";

    fetch ('/api/v1/notification', {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
    })
    .then(response => {
        if(response.ok) {
            return response.json();
        } else {
            return response.json().then(data => {
                showNotification(data.message, NotificationType.Error);
            });
        }
    })
    .then(data => {
        data.forEach(notification => {
            notificationsList.innerHTML += `
                <div class="cart-item">
                  <div class="cart-item-details">
                    <h4 class="cart-item-title">${notification.message}</h4>
                    <p class="cart-item-price">Buyer: ${notification.buyerUsername}</p>
                    <p class="cart-item-seller">Product: ${notification.productName} for ${notification
                    .productPrice}SEK</p>
                  </div>
                  <button onclick="" id="cart-item-accept">Accept</button>
                  <button onclick="" id="cart-item-remove">Reject</button>
                </div>
            `;
        });
    })
    .catch(error => {
        console.error('Unable to get notifications.', error);
        showNotification("Could not refresh notifications. Please reload the page.", NotificationType.Error);
    });
}