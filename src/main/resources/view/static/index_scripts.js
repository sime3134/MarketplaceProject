const logoutButton = document.getElementById("logout-button");
  if (logoutButton) {
    logoutButton.addEventListener("click", logout);
  } else {
    console.error("could not find the logout button");
  }

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

    populateProductTypes("product-type");
    populateProductTypes("product-type2");
});

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
            (product) => `
           <div class="product">
                  <h3>${product.productType.name}</h3>
                  <p>Price: ${product.productPrice}</p>
                  <p>Condition: ${product.productCondition.description}</p>
                  <p>Color: ${product.color}</p>
                  <p>Seller: ${product.seller.username}</p>
                  <button class="buy-button">Add to cart</button>
              </div>
        `
          )
          .join("");
        productsSection.innerHTML = productsHtml;
      })
      .catch((error) => {
        // Handle any errors that occurred during the fetch request
        console.error(error);
      });
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