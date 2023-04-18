let cart = [];

const cartElement = document.getElementById("cartList");

document.addEventListener("DOMContentLoaded", function () {
    populateCart();
});

// Fetch cart and populate
    function populateCart() {
       fetch("/api/v1/cart")
       .then(async (response) => response.json())
       .then((data) => {
       console.log(data);
           data.products.forEach((product) => {
                cart.push(product);
           });
           refreshCart();
           if(data.products.length > 0) {
               const totalPriceSpan = document.getElementById("totalPriceSpan");
                  totalPriceSpan.innerHTML = data.totalPrice;
           }
       })
         .catch((error) => {
            console.error(error);
         });
    }

function removeProductFromCart(productId) {
    removeProductFromCartOnServer(productId)
        .then((success) => {
            if(success) {
                const product = cart.find((product) => product.id === productId);
                const index = cart.indexOf(product);
                cart.splice(index, 1);
                refreshCart();
            }else{
                console.error("Something happened on the server when trying to remove the product from the cart");
            }
        })
}

function refreshCart() {
    console.log("refreshing cart");
    let numberOfProductsInCart = 0;
    const numberOfProductsInCartElement = document.getElementById("numberOfProductsInCart");
    cartElement.innerHTML = "";
    if(cart.length > 0) {
        cart.forEach((product) => {
        numberOfProductsInCart++;
        cartElement.innerHTML += `
            <div class="cart-item">
              <div class="cart-item-details">
                <h4 class="cart-item-title">${product.productType.name}</h4>
                <p class="cart-item-price">Price: ${product.productPrice}</p>
                <p class="cart-item-seller">Seller: ${product.seller.username}</p>
              </div>
              <button onclick="removeProductFromCart(${product.id})" id="cart-item-remove">Remove</button>
            </div>
            `;
        });
        cartElement.innerHTML += `<p><b>Total price: <span id="totalPriceSpan"></span></b></p>`;
        cartElement.innerHTML += `<button id="placeOrderButton">Place your order</button>`;
        const placeOrderButton = document.getElementById("placeOrderButton");
        const totalPriceSpan = document.getElementById("totalPriceSpan");
        placeOrderButton.addEventListener("click", placeOrder);
        totalPriceSpan.innerHTML = cart.reduce((total, product) => total + product.productPrice, 0);
        } else {
            cartElement.innerHTML = "Your cart is empty";
        }
        numberOfProductsInCartElement.innerHTML = `(${numberOfProductsInCart})`;
    }

    function addProductToCart(productId) {
        addProductToCartOnServer(productId)
          .then((success) => {
            if(success) {
                const product = products.find((product) => product.id === productId);
                cart.push(product);
                refreshCart();
            }
          })
          .catch(error => {
            console.error(error.message);
          });
        }

        const addProductToCartOnServer = async (productId) => {
          return fetch(`/api/v1/cart/${productId}`, {
            method: 'POST',
          })
            .then(response => {
              if (response.ok) {
                return true;
              } else {
                throw new Error('Error updating cart');
              }
            })
            .catch(error => {
              console.error(error.message);
            });
        };

        const removeProductFromCartOnServer = async (productId) => {
              try {
                const response = await fetch(`/api/v1/cart/${productId}`, {
                  method: "DELETE",
                });

                if (!response.ok) {
                  throw new Error(`HTTP error ${response.message}`);
                }
                return true;
              } catch (error) {
                console.error("Error updating cart:", error);
                return false;
              }
            };