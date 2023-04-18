const orders = [];

document.addEventListener("DOMContentLoaded", function () {
    populateOrders();
});

function populateOrders() {
    fetch("/api/v1/order")
    .then(async (response) => response.json())
    .then((data) => {
        data.forEach((order) => {
            orders.push(order);
        });
        refreshOrders();
    })
    .catch((error) => {
        console.error(error);
    });
}
function refreshOrders() {
    const ordersSection = document.querySelector("#orders");
    if(order.length > 0) {
    const ordersHtml = `<h2>Orders</h2>` + orders
        .map(
            (order) => {
                return `
                <div class="order">
                    <h3>Order ID: ${order.id}</h3>
                    <p>Order Date: ${order.orderDate}</p>
                    <p>Order Total: ${order.orderTotal}</p>
                    <p>Order Status: ${order.orderStatus}</p>
                    <p>Order Items: ${order.orderItems}</p>
                </div>
            `;
            })
        .join("");
    ordersSection.innerHTML = ordersHtml;
    } else {
        ordersSection.innerHTML = `<p>No orders found</p>`;
    }
}

 function placeOrder() {
    placeOrderOnServer()
        .then((success) => {
            if(success) {
                cart = [];
                refreshCart();
            }
        })
    }

    function placeOrderOnServer() {
        return fetch("/api/v1/order", {
            method: "POST",
        })
            .then((response) => {
                if(response.ok) {
                    return true;
                }else{
                    throw new Error("Error placing order");
                }
            })
            .catch((error) => {
                console.error(error.message);
            });
    }