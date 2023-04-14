const logoutButton = document.getElementById("logout-button");
  if (logoutButton) {
    logoutButton.addEventListener("click", logout);
  } else {
    console.error("could not find the logout form element");
  }

document.addEventListener("DOMContentLoaded", function () {
  handleTabs();

  // Define productsSection
  const productsSection = document.querySelector("#products");

  fetch("http://localhost:5001/api/v1/products")
    .then(async (response) => response.json()) // Parse response as JSON
    .then((data) => {
      // Create HTML for each product and append it to the products section
      const productsHtml = data
        .map(
          (product) => `
        <div class="product">
          <h3>${product.productType.name}</h3>
          <p>Color: ${product.color}</p>
          <p>Price: ${product.productPrice}</p>
          <p>Condition: ${product.productCondition}</p>
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

    populateProductTypes("product-type");
    populateProductTypes("product-type2");
});

  // Fetch product types and populate the dropdown
  function populateProductTypes(selectId) {
    const productTypeSelect = document.getElementById(selectId);

    fetch("http://localhost:5001/api/v1/product_types")
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
    fetch("http://localhost:5001/api/v1/logout", {
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