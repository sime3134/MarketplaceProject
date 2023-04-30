const cart = {
    cartList: [],
    cartElement: document.getElementById("cartList"),

    async init() {
        this.cartList = await this.populateCart();
        this.refreshCart();
    },

    async populateCart() {
        try {
          const response = await fetch("/api/v1/cart");
          if (response.ok) {
            const data = await response.json();
            return data.products;
          } else {
            throw new Error("Error retrieving cart!");
          }
        } catch (error) {
          console.error(error);
          showNotification(error.message, NotificationType.Warning);
          return [];
        }
    },

    async addProductToCart(productId) {
        const success = await this.addProductToCartOnServer(productId);
        if (success) {
          const product = await this.getProductFromServer(productId);
          this.cartList.push(product);
          this.refreshCart();
          showNotification("Product added to cart!", NotificationType.Success);
        }
    },

    async getProductFromServer(productId) {
        try {
          const response = await fetch(`/api/v1/product/${productId}`);
          if (response.ok) {
            const data = await response.json();
            return data;
          } else {
            const data = await response.json();
            showNotification(data.message, NotificationType.Warning);
          }
        } catch (error) {
          console.error(error);
          showNotification("Something went wrong on the server. Try again later.", NotificationType.Warning);
          return null;
        }
    },

    async removeProductFromCart(productId) {
        const success = await this.removeProductFromCartOnServer(productId);
        if (success) {
          const product = this.cartList.find((product) => product.id === productId);
          const index = this.cartList.indexOf(product);
          this.cartList.splice(index, 1);
          this.refreshCart();
          showNotification("Product removed from cart!", NotificationType.Success);
        }
    },

    refreshCart() {
        let numberOfProductsInCart = 0;
        const numberOfProductsInCartElement = document.getElementById("numberOfProductsInCart");
        this.cartElement.innerHTML = "";

        if (this.cartList.length > 0) {
          this.cartList.forEach((product) => {
            numberOfProductsInCart++;
            this.cartElement.innerHTML += `
              <div class="cart-item">
                <div class="cart-item-details">
                  <p class="cart-item-title"><b>${product.productType.name}</b></p>
                  <p class="cart-item-price">Price: ${product.productPrice}SEK</p>
                  <p class="cart-item-seller">Seller: ${product.seller.username}</p>
                </div>
                <button onclick="cart.removeProductFromCart(${product.id})" id="cart-item-remove">Remove</button>
              </div>
            `;
          });
          this.cartElement.innerHTML += `<p><b>Total price: <span id="totalPriceSpan"></span></b></p>`;
          this.cartElement.innerHTML += `<button id="placeOrderButton">Place your order</button>`;
          const placeOrderButton = document.getElementById("placeOrderButton");
          const totalPriceSpan = document.getElementById("totalPriceSpan");
          placeOrderButton.addEventListener("click", orders.placeOrder.bind(orders));
          totalPriceSpan.innerHTML = this.cartList.reduce((total, product) => total + product.productPrice, 0);
        } else {
          this.cartElement.innerHTML = "Your cart is empty";
        }
        numberOfProductsInCartElement.innerHTML = `(${numberOfProductsInCart})`;
    },

    async addProductToCartOnServer(productId) {
        try {
          const response = await fetch(`/api/v1/cart/${productId}`, {
            method: "POST",
          });

          if (response.ok) {
            return true;
          } else {
            const data = await response.json();
            showNotification(data.message, NotificationType.Error);
            return false;
          }
        } catch (error) {
          showError("Something went wrong on the server. Please try again later.", NotificationType.Error);
          console.error(error.message);
          return false;
        }
    },

    async removeProductFromCartOnServer(productId) {
        try {
            const response = await fetch(`/api/v1/cart/${productId}`, {
                method: "DELETE",
            });
        if (!response.ok) {
            const data = await response.json();
            showNotification(data.message, NotificationType.Error);
            return false;
        }
            return true;
        } catch (error) {
            console.error("Error updating cart:", error);
            showNotification("Something went wrong on the server. Please try again later.", NotificationType.Error);
            return false;
        }
    },
};

document.addEventListener("DOMContentLoaded", function () {
    cart.init();
});