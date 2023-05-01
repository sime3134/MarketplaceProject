document.addEventListener("DOMContentLoaded", function () {
    connectUser();
    updateNotificationView();
});

function connectUser() {
  const ws = new WebSocket('ws://localhost:8080/api/v1/connect');
  ws.onmessage = (event) => {
    const messageData = JSON.parse(event.data);
    if(messageData) {
        if(messageData.messageType == "ERROR") {
            showNotification(messageData.message, NotificationType.Error);
        } else if(messageData.messageType == "INFO" || messageData.messageType == "PURCHASE_NOTIFICATION") {
            showNotification(messageData.message, NotificationType.Info);
        } else if(messageData.messageType == "SUCCESS") {
            showNotification(messageData.message, NotificationType.Success);
        }else if(messageData.messageType == "ORDER_STATUS_NOTIFICATION") {
            showNotification(messageData.message, NotificationType.Info);
            orders.populateOrders();
            products.populateProducts();
        }else if(messageData.messageType == "SUBSCRIPTION_NOTIFICATION") {
            showNotification(messageData.message, NotificationType.Info);
            products.populateProducts();
        }

        updateNotificationView();
    }
  };
}