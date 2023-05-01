const subscription = {
    subscriptionList: [],
    subscriptionDiv: null,
    subscriptionForm: null,

    async init() {
        this.subscriptionDiv = document.getElementById('subscriptions');
        this.subscriptionForm = document.getElementById('subscription-form');
        this.registerEventListeners();
        await this.refreshSubscriptions();
    },

    registerEventListeners() {

        if (this.subscriptionForm) {
            this.subscriptionForm.addEventListener('submit', this.addNewSubscription.bind(this));
        }else {
            console.error('Could not find subscription form!');
        }
    },

    async populateSubscription() {
        this.subscriptionList = [];
        try {
            const response = await fetch("/api/v1/subscription", {method: 'GET'});

            if (response.ok) {
                const data = await response.json();
                data.map((subscription) => {
                    this.subscriptionList.push(subscription);
                });
            }else {
                const data = await response.json();
                console.error(data.message);
                showNotification(data.message, NotificationType.Error);
            }
        }catch(error) {
            console.error(error);
            showNotification("Something went wrong. Try again later.", NotificationType.Error);
        }
    },

    async refreshSubscriptions() {
        await this.populateSubscription();

        if (this.subscriptionList.length > 0) {
            this.subscriptionDiv.innerHTML = `<h2>Subscriptions</h2>`;

            this.subscriptionList.map((subscription) => {
                this.subscriptionDiv.innerHTML += this.getSubscriptionCard(subscription);
            });
        }else {
            this.subscriptionDiv.innerHTML = `<h3>No subscriptions found!</h3>`;
        }
    },

    getSubscriptionCard(subscription) {
        return `
            <div class="subscription">
                <h3>${subscription.name}</h3>
                <button class="negative-button" onclick="subscription.deleteSubscription(${subscription.id})
                ">Delete</button>
            </div>
        `;
    },

    async deleteSubscription(subscriptionId) {
        const url = new URL(`/api/v1/subscription/${subscriptionId}`, window.location.origin);

        try {
            const response = await fetch(url, {
                method: 'DELETE'
            });

            if (response.ok) {
                await this.refreshSubscriptions();
                showNotification("Subscription deleted successfully!", NotificationType.Success);
            }else {
                const data = await response.json();
                showNotification(data.message, NotificationType.Error);
            }
        }catch(error) {
            console.error(error);
            showNotification("Something went wrong. Try again later.", NotificationType.Error);
        }
    },

    async addNewSubscription() {
        event.preventDefault();
        const success = await this.postSubscription();

        if(success) {
            this.subscriptionForm.reset();
            await this.refreshSubscriptions();
            showNotification("Subscription added successfully!", NotificationType.Success);
        }
    },

    async postSubscription() {
        const productTypeElement = document.getElementById('subscribe_product-type');
        const url = new URL("/api/v1/subscription", window.location.origin);

        const formData = new FormData();

        if(productTypeElement.value) {
            formData.append('productTypeId', productTypeElement.value);
        }else {
            showNotification("Please select a product type!", NotificationType.Error);
            return false;
        }

        try {
            const response = await fetch(url, {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                return true;
            }else {
                const data = await response.json();
                showNotification(data.message, NotificationType.Error);
                return false;
            }
        }catch(error) {
            console.error(error);
            showNotification("Something went wrong. Try again later.", NotificationType.Error);
            return false;
        }
    }
};

document.addEventListener("DOMContentLoaded", function () {
    subscription.init();
});