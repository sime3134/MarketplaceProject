const products = [];
const cart = [];

const logoutButton = document.getElementById("logout-button");
  if (logoutButton) {
    logoutButton.addEventListener("click", logout);
  } else {
    console.error("could not find the logout button");
  }

const cartElement = document.getElementById("cartList");

  const searchForm = document.getElementById('search-form');
      if (searchForm) {
          searchForm.addEventListener('submit', function (event) {
                event.preventDefault();
                populateProducts();
          });
      } else {
          console.error('Could not find search form!');
  }

document.addEventListener("DOMContentLoaded", function () {
    handleTabs();

    populateProducts();

    populateCart();

    populateProductTypes("product-type");
    populateProductTypes("product-type2");
});

    // Fetch cart and populate
    function populateCart() {

    }

  // Fetch products and populate
  function populateProducts() {
  const productsSection = document.querySelector("#products");
  const searchForm = document.getElementById('search-form');

  const url = new URL("/api/v1/products", window.location.origin);

  for (const element of searchForm.elements) {
      if (element.tagName === "INPUT" || element.tagName === "SELECT") {
        if (element.value) {
          url.searchParams.append(element.name, element.value);
        }
      }
    }

    fetch(url)
      .then(async (response) => response.json()) // Parse response as JSON
      .then((data) => {
        // Create HTML for each product and append it to the products section
        const productsHtml = `<h2>Products</h2>` + data
          .map(
            (product) => {
            products.push(product);
             return `
           <div class="product">
                  <h3>${product.productType.name}</h3>
                  <p>Price: ${product.productPrice}</p>
                  <p>Condition: ${product.productCondition.description}</p>
                  <p>Color: ${product.color}</p>
                  <p>Seller: ${product.seller.username}</p>
                  <p>Status: ${product.isAvailable ? 'Available' : 'Sold'}</p>
                  <button onclick="addProductToCart(${product.id})" id="buy-button" class="buy-button">Add to
                  cart</button>
              </div>
        `;
        })
          .join("");
        productsSection.innerHTML = productsHtml;
      })
      .catch((error) => {
        // Handle any errors that occurred during the fetch request
        console.error(error);
      });
  }

    function addProductToCart(productId) {
    const product = products.find((product) => product.id === productId);
    cart.push(product);
    addProductToCartOnServer(productId)
      .then((success) => {
        if(success) {
          refreshCart();
        }
      })
      .catch(error => {
        console.error(error.message);
      });
    }

    const addProductToCartOnServer = async (productId) => {
      return fetch(`/api/v1/update_cart/${productId}`, {
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
          throw error; // re-throw the error
        });
    };

    const removeProductFromCartOnServer = async (productId) => {
          try {
            const response = await fetch("/api/v1/update_cart/${productId}", {
              method: "DELETE",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify({ product_id: productId }),
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
        cartElement.innerHTML += `<button id="placeOrderButton">Place your order</button>`;
        } else {
            cartElement.innerHTML = "Your cart is empty";
        }
        numberOfProductsInCartElement.innerHTML = `(${numberOfProductsInCart})`;
    }

    function removeProductFromCart(productId) {
    const product = cart.find((product) => product.id === productId);
    const index = cart.indexOf(product);
    cart.splice(index, 1);
    removeProductFromCartOnServer(productId)
        .then((success) => {
            if(success) {
                refreshCart();
            }else{
                console.error("Something happened on the server");
            }
        })
    }

  // Fetch product types and populate the dropdown
  function populateProductTypes(selectId) {
    const productTypeSelect = document.getElementById(selectId);

    fetch("/api/v1/product_types")
      .then(async (response) => response.json())
      .then((data) => {
        data.forEach((productType) => {
          const option = document.createElement("option");
          option.value = productType.id;
          option.textContent = productType.name;
          productTypeSelect.appendChild(option);
        });
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function logout() {
    fetch("/api/v1/logout", {
      method: "POST",
    })
      .then(async (response) => {
        if (response.ok) {
          window.location.href = "/login";
        } else {
          // Handle error or show a message to the user.
          console.error("Logout failed.");
        }
      })
      .catch((error) => {
        // Handle network errors or other issues.
        console.error("Error during logout:", error);
      });
  }

  function handleTabs() {
    // Tab switching
    const tabs = document.querySelectorAll(".tab");
    const tabContents = document.querySelectorAll(".tab-content");

    tabs.forEach((tab) => {
      tab.addEventListener("click", (event) => {
        const targetTab = event.target.dataset.tab;

        // Remove active class from all tabs
        tabs.forEach((t) => {
          t.classList.remove("active");
        });

        // Add active class to the clicked tab
        event.target.classList.add("active");

        // Hide all tab contents
        tabContents.forEach((content) => {
          content.classList.remove("active");
        });

        // Show the tab content associated with the clicked tab
        document.getElementById(targetTab).classList.add("active");
      });
  });
}