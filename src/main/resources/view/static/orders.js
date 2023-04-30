let orders = [];

const orderSearchForm = document.querySelector("#order-search-form");
if(orderSearchForm) {
    orderSearchForm.addEventListener("submit", handleOrderSearch);
} else {
    console.error("Could not find order search form!");
}

document.addEventListener("DOMContentLoaded", function () {
    populateOrders();
});

function handleOrderSearch() {
    event.preventDefault(); // Prevent form submission and page reload
    populateOrders();
}

function makePurchaseDecision(orderId, accepted, notificationIndex) {
    fetch(`/api/v1/order/${orderId}?accepted=${accepted}&notificationIndex=${notificationIndex}`, {
        method: "PUT",
    })
    .then(async (response) => {
        if (response.ok) {
            updateNotificationView();
            if(accepted) {
                showNotification("Confirmation was sent to the buyer.", NotificationType.Success);
                populateProducts();
            }
        } else {
            response.json().then((data) => {
                showNotification(data.message, NotificationType.Error);
                console.error(data.message);
            });
        }
    })
    .catch((error) => {
        console.error(error);
        showNotification("Something went wrong on the server. Try again later!", NotificationType.Error);
    });
}

async function populateOrders() {
    try {
        const startDateField = document.querySelector("#start-date");
        const endDateField = document.querySelector("#end-date");
        const response = await fetch(`/api/v1/order?startDate=${startDateField.value}&endDate=${endDateField.value}`, {
            method: "GET",
        });

        if (response.ok) {
            const data = await response.json();
            orders = [];
            if (data.length > 0) {
                data.map((order) => {
                    orders.push(order);
                });
            }
            refreshOrders();
        } else {
            const data = await response.json();
            showNotification(data.message, NotificationType.Error);
            console.error(data.message);
        }
    } catch (error) {
        console.error(error);
        showNotification("Something went wrong on the server. Try again later!", NotificationType.Error);
    }
}

function refreshOrders() {
    const ordersSection = document.querySelector("#orders");
    if(orders.length > 0) {
    const ordersHtml = `<h2>Orders</h2>` +
    orders
        .map(
            (order) => {
                return `
                <div class="order">
                    <h3>Order ID: ${order.id}</h3>
                    <p>Ordered at: ${order.timestamp[0]}-${order.timestamp[1]}-${order.timestamp[2]}, ${order
                    .timestamp[3]}.${order.timestamp[4]}</p>
                    <p>Order Total: ${order.product.productPrice}</p>
                    <p>Order Status: ${order.orderStatus.status}</p>
                    <p>Order Item: ${order.product.productType.name}</p>
                    <p style="padding-left: 20px">Seller: ${order.product.seller.username}</p>
                    <p style="padding-left: 20px">Price: ${order.product.productPrice}</p>
                </div>
            `;
            })
        .join("");
    ordersSection.innerHTML = ordersHtml;
    } else {
        ordersSection.innerHTML = `
        <h2>Orders</h2>
        <p>No orders found</p>`;
    }
}

 function placeOrder() {
    placeOrderOnServer()
        .then((success) => {
            if(success) {
                cart = [];
                refreshCart();
                populateOrders();
                showNotification("Order placed successfully!", NotificationType.Success);
            }
        });
    }

    const placeOrderOnServer = async () => {
              return fetch(`/api/v1/order`, {
                method: 'POST',
              })
                .then(response => {
                  if (response.ok) {
                    return true;
                  } else {
                    return response.json().then(data => {
                        showNotification(data.message, NotificationType.Error);
                        return false;
                    });
                  }
                })
                .catch(error => {
                  console.error(error.message);
                  showNotification("Something went wrong on the server. Please try again later!", NotificationType.Error);
                  return false;
                });
            };