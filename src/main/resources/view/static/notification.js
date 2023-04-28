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
    let numberOfNotifications = 0;
    const notificationsList = document.getElementById('notificationsList');
    const notificationCount = document.getElementById('numberOfNotifications');
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
            notificationsList.innerHTML +=
                getNotificationHtml(notification, numberOfNotifications);
            numberOfNotifications++;
        });
        notificationCount.innerHTML = `(${numberOfNotifications})`;
    })
    .catch(error => {
        console.error('Unable to get notifications.', error);
        showNotification("Could not refresh notifications. Please reload the page.", NotificationType.Error);
    });
}

function getNotificationHtml(notification, notificationIndex) {
    if(notification.notificationType == "purchaseNotification") {
        return `
        <div class="cart-item">
          <div class="cart-item-details">
            <p class="cart-item-title"><b>${notification.message}</b></p>
            <p class="cart-item-price">Buyer: ${notification.buyerUsername}</p>
            <p class="cart-item-seller">Product: ${notification.productName}</p>
            <p class="cart-item-seller">Price: ${notification.productPrice}SEK</p>
          </div>
          <div class="cart-item-buttons">
              <button onclick="makePurchaseDecision(${notification.orderId}, true, ${notificationIndex})"
              id="cart-item-accept">Accept</button>
              <button onclick="makePurchaseDecision(${notification.orderId}, false, ${notificationIndex})"
              id="cart-item-remove">Reject</button>
          </div>
        </div>
        `;
    } else if(notification.notificationType == "orderStatusNotification") {
        return `
        <div class="cart-item">
          <div class="cart-item-details">
            <p class="cart-item-title"><b>${notification.message}</b></p>
          </div>
          <div class="cart-item-buttons">
              <button onclick="removeNotification(${notificationIndex})" id="cart-item-remove">X</button>
          </div>
        </div>
        `;
    }
}

function removeNotification(notificationIndex) {
    fetch(`/api/v1/notification/${notificationIndex}`, {
        method: 'POST',
    })
    .then(response => {
        if(response.ok) {
            updateNotificationView();
        } else {
            return response.json().then(data => {
                showNotification(data.message, NotificationType.Error);
            });
        }
    });
}