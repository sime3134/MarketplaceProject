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
        if(data.length > 0) {
            data.forEach(notification => {
                notificationsList.innerHTML +=
                    getNotificationHtml(notification, numberOfNotifications);
                numberOfNotifications++;
            });
        }else {
            notificationsList.innerHTML = "No notifications";
        }
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
        <div class="dropdown-item">
          <div class="dropdown-item-details">
            <p class="dropdown-item-title"><b>${notification.message}</b></p>
            <p class="dropdown-item-other">Buyer: ${notification.buyerUsername}</p>
            <p class="dropdown-item-other">Product: ${notification.productName}</p>
            <p class="dropdown-item-other">Price: ${notification.productPrice}SEK</p>
          </div>
          <div>
              <button onclick="orders.makePurchaseDecision(${notification.orderId}, true, ${notificationIndex})"
              class="positive-button">Accept</button>
              <button onclick="orders.makePurchaseDecision(${notification.orderId}, false, ${notificationIndex})"
              class="negative-button">Reject</button>
          </div>
        </div>
        `;
    } else if(notification.notificationType == "orderStatusNotification"
        || notification.notificationType == "subscriptionNotification") {
        return `
        <div class="dropdown-item">
          <div class="dropdown-item-details">
            <p class="dropdown-item-title"><b>${notification.message}</b></p>
          </div>
          <div>
              <button onclick="removeNotification(${notificationIndex})"
              class="negative-button">X</button>
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