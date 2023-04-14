const productsSection = document.querySelector('.products');

fetch('/api/v1/products')
  .then(response => response.json())
  .then(data => {
  console.log("hej")
  console.log(data)
    // Create HTML for each product and append it to the products section
    const productsHtml = data.map(product => `
      <div class="product">
        <h3>${product.name}</h3>
        <p>${product.description}</p>
        <p>Price: ${product.price}</p>
        <p>Condition: ${product.condition}</p>
      </div>
    `).join('');
    productsSection.innerHTML = productsHtml;
  })
  .catch(error => {
    // Handle any errors that occurred during the fetch request
    console.error(error);
  });
