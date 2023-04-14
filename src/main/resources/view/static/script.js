// Define productsSection
const productsSection = document.querySelector('.products');

fetch('http://localhost:5001/api/v1/products')
  .then(response => response.json()) // Parse response as JSON
  .then(data => {
    // Create HTML for each product and append it to the products section
    const productsHtml = data.map(product => `
      <div class="product">
        <h3>${product.productType.name}</h3>
        <p>Color: ${product.color}</p>
        <p>Price: ${product.productPrice}</p>
        <p>Condition: ${product.productCondition}</p>
      </div>
    `).join('');
    productsSection.innerHTML = productsHtml;
  })
  .catch(error => {
    // Handle any errors that occurred during the fetch request
    console.error(error);
  });

// Log the response text
fetch('http://localhost:5001/api/v1/products')
  .then(response => response.text()) // Convert response to text
  .then(text => console.log(text)) // Log the response text
  .catch(error => console.error(error)); // Handle errors
