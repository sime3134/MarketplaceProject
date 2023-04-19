const products = [];

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
    populateProducts();

    populateProductTypes("product-type");
    populateProductTypes("product-type2");
});

  // Fetch products and populate
  function populateProducts() {
  const productsSection = document.querySelector("#products");
  const searchForm = document.getElementById('search-form');

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
        } else {
            productsSection.innerHTML = `<p>No products found</p>`;
        }
      })
      .catch((error) => {
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