let products = [];

  const searchForm = document.getElementById('search-form');
      if (searchForm) {
          searchForm.addEventListener('submit', function (event) {
                event.preventDefault();
                populateProducts();
          });
      } else {
          console.error('Could not find search form!');
        }

  const productForm = document.getElementById('product-form');
        if (productForm) {
            productForm.addEventListener('submit', function (event) {
                  event.preventDefault();
                  addNewProduct();
            });
        } else {
            console.error('Could not find product form!');
        }

document.addEventListener("DOMContentLoaded", function () {
    populateProducts();

    populateProductTypes("product-type");
    populateProductTypes("product-type2");
});

  // Fetch products and populate
  function populateProducts() {
  const searchForm = document.getElementById('search-form');
  products = [];

  const url = new URL("/api/v1/product", window.location.origin);

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
      if(data.length > 0) {
        // Create HTML for each product and append it to the products section
        data.map(
            (product) => {
                products.push(product);
        });
      }
        refreshProducts();
      }).catch((error) => {
        // Handle any errors that occurred during the fetch request
        console.error(error);
      });
  }

  // Fetch product types and populate the dropdown
  function populateProductTypes(selectId) {
    const productTypeSelect = document.getElementById(selectId);

    fetch("/api/v1/product_type")
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

  function refreshProducts() {
    const productsSection = document.querySelector("#products");
    if(products.length > 0) {
        try{
        const productsHtml = `<h2>Products</h2>` +
            products.map(
                (product) => {
                console.log(product);
                    return `
                      <div class="product${product.available ? '' : ' sold'}">
                        <h3>${product.productType.name}</h3>
                        <p><b>Status: ${product.available ? 'Available' : 'Sold'}</b></p>
                        <p><b>Price: ${product.productPrice}SEK</b></p>
                        <p>Condition: ${product.productCondition.description}</p>
                        <p>Color: ${product.color}</p>
                        <p>Year of production: ${product.yearOfProduction}</p>
                        <p>Seller: ${product.seller.username}</p>
                        ${product.available ?
                          `<button onclick="addProductToCart(${product.id})" id="buy-button" class="buy-button">Add to cart</button>`
                          : ''}
                      </div>
                    `;
                })
            .join("");
        productsSection.innerHTML = productsHtml;
        }catch(error) {
            console.error(error);
        }
    }else {
        productsSection.innerHTML = "<h2>No products found</h2>";
    }
}

function addNewProduct() {
    addNewProductToServer()
    .then((success) => {
       if (success) {
            const productForm = document.getElementById('product-form');
            productForm.reset();
            populateProducts();
            showNotification("Product added successfully", NotificationType.Success);
        }
    });
}

const addNewProductToServer = async () => {
    const productForm = document.getElementById('product-form');

    const url = new URL("/api/v1/product", window.location.origin);

    const formData = new FormData();

    for (const element of productForm.elements) {
        if (element.tagName === "INPUT" || element.tagName === "SELECT") {
          if (element.value) {
            formData.append(element.name, element.value);
          }else {
            if(element.placeholder) {
                showNotification(element.placeholder + " is required", NotificationType.Error);
            }else if(element.dataset.placeholder) {
                showNotification(element.dataset.placeholder + " is required", NotificationType.Error);
            }
          }
        }
      }

      return fetch(url, {
            method: 'POST',
            body: formData
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
            showNotification("Something went wrong on the server. Please try again later.", NotificationType.Error);
            return false;
          });
 }