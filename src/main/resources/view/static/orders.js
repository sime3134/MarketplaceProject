const orders = {
    orderList: [],

    async init() {
        this.registerEventListeners();
        await this.populateOrders();
    },

    registerEventListeners() {
        const orderSearchForm = document.querySelector("#order-search-form");

        if (orderSearchForm) {
            orderSearchForm.addEventListener("submit", this.handleOrderSearch.bind(this));
        } else {
            console.error("Could not find order search form!");
        }
    },

    async handleOrderSearch(event) {
        event.preventDefault(); // Prevent form submission and page reload
        await this.populateOrders();
    },

    async populateOrders() {
        try {
            const startDateField = document.querySelector("#start-date");
            const endDateField = document.querySelector("#end-date");
            const response = await fetch(`/api/v1/order?startDate=${startDateField.value}&endDate=${endDateField.value}`, {
                method: "GET",
            });

            if (response.ok) {
                const data = await response.json();
                this.orderList = [];
                if (data.length > 0) {
                    data.map((order) => {
                        this.orderList.push(order);
                    });
                }
                this.refreshOrders();
            } else {
                const data = await response.json();
                showNotification(data.message, NotificationType.Error);
                console.error(data.message);
            }
        } catch (error) {
            console.error(error);
            showNotification("Something went wrong on the server. Try again later!", NotificationType.Error);
        }
    },

    refreshOrders() {
        const ordersSection = document.querySelector("#orders");
        if (this.orderList.length > 0) {
            const ordersHtml = `<h2>Orders</h2>` +
                this.orderList
                    .map((order) => {
                        const orderDate = new Date(order.timestamp);

                        // Format the date and time
                        const formattedDate = orderDate.toLocaleDateString();
                        const formattedTime = orderDate.toLocaleTimeString();

                        return `
                            <div class="order">
                                <h3>Order ID: ${order.id}</h3>
                                <p>Ordered at: ${formattedDate}, ${formattedTime}</p>
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
    },

    async makePurchaseDecision(orderId, accepted, notificationIndex) {
        try {
            const response = await fetch(`/api/v1/order/${orderId}?accepted=${accepted}&notificationIndex=${notificationIndex}`, {
                method: "PUT",
            });

            if (response.ok) {
                updateNotificationView();
                if (accepted) {
                    showNotification("Confirmation was sent to the buyer.", NotificationType.Success);
                    await products.populateProducts();
                }
            } else {
                const data = await response.json();
                showNotification(data.message, NotificationType.Error);
                console.error(data.message);
            }
        } catch (error) {
            console.error(error);
            showNotification("Something went wrong on the server. Try again later!", NotificationType.Error);
        }
    },

    async placeOrder() {
        const success = await this.placeOrderOnServer();

        if (success) {
            cart.cartList = [];
            cart.refreshCart();
            await this.populateOrders();
            showNotification("Order placed successfully!", NotificationType.Success);
        }
    },

    async placeOrderOnServer() {
        try {
            const response = await fetch(`/api/v1/order`, {
                method: 'POST',
            });

            if (response.ok) {
                return true;
            } else {
                const data = await response.json();
                showNotification(data.message, NotificationType.Error);
                return false;
            }
        } catch (error) {
            console.error(error.message);
            showNotification("Something went wrong on the server. Please try again later!", NotificationType.Error);
            return false;
        }
    },
};

document.addEventListener("DOMContentLoaded", function () {
    orders.init();
});