let orders = [];

document.addEventListener("DOMContentLoaded", function () {
    populateOrders();
});

function populateOrders() {
    fetch("/api/v1/order")
    .then(async (response) => response.json())
    .then((data) => {
        orders = [];
        if(data.length > 0) {
            data.map((order) => {
                orders.push(order);
            });
        }
        refreshOrders();
    })
    .catch((error) => {
        console.error(error);
    });
}
function refreshOrders() {
    const ordersSection = document.querySelector("#orders");
    if(orders.length > 0) {
    const ordersHtml = `<h2>Orders</h2>` + orders
        .map(
            (order) => {
            console.log(order)
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
        ordersSection.innerHTML = `<p>No orders found</p>`;
    }
}

 function placeOrder() {
    placeOrderOnServer()
        .then((success) => {
            if(success) {
                cart = [];
                refreshCart();
                populateOrders();
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
                    throw new Error('Error submitting order');
                  }
                })
                .catch(error => {
                  console.error(error.message);
                });
            };