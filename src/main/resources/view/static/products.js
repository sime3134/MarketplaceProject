const products = {
    productList: [],
    searchForm: null,
    sellForm: null,

    async init() {
        this.registerEventListeners();
        this.productList = await this.populateProducts();

        await this.populateProductTypes("product-type");
    },

    registerEventListeners() {
    searchForm = document.getElementById('products-search-form');
    sellForm = document.getElementById('sell-form');

        if (searchForm) {
            searchForm.addEventListener('submit', (event) => {
                event.preventDefault();
                this.populateProducts.bind(this)();
            });
        }else {
            console.error('Could not find search form!');
        }

        if (sellForm) {
            sellForm.addEventListener('submit', (event) => {
                event.preventDefault();
                this.addNewProduct.bind(this)();
            });
        }else {
            console.error('Could not find sell form!');
        }
    },

    async populateProducts() {
        this.productList = [];

        const url = new URL("/api/v1/product", window.location.origin);

        for (const element of searchForm.elements) {
          if (element.tagName === "INPUT" || element.tagName === "SELECT") {
            if (element.value) {
              url.searchParams.append(element.name, element.value);
            }
          }
        }

        try {
            const response = await fetch(url, {method: 'GET'});

            if (response.ok) {
                const data = await response.json();
                if (data.length > 0) {
                    data.map((product) => {
                        this.productList.push(product);
                    });
                }
                this.refreshProducts();
            }else {
                const data = await response.json();
                showNotification(data.message, NotificationType.Error);
                console.error(data.message);
            }
        }catch(error) {
            console.error(error);
            showNotification("Something went wrong on the server. Try again later!", NotificationType.Error);
        }
    },

    refreshProducts() {
        const productsSection = document.querySelector("#products");
        if(this.productList.length > 0) {
            try {
                productsSection.innerHTML = `<h2>Products</h2>` + this.productList.map((product) => {
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
                      `<button onclick="cart.addProductToCart(${product.id})" id="buy-button"
                      class="buy-button">Add to cart</button>`
                      : ''}
                    </div>
                    `;
                })
                .join("");
            }catch(error) {
                console.error(error);
                showNotification("Something went wrong loading the products. Try again later.", NotificationType
                .Error);
            }
        }else {
            productsSection.innerHTML = `
            <h2>Products</h2>
            <p>No products found</p>`;
        }
    },

    async populateProductTypes(selectId) {
        const productTypeSelects = document.getElementsByClassName(selectId);

        try {
            const response = await fetch("/api/v1/product-type", {method: 'GET'});

            if (response.ok) {
                const data = await response.json();
                if (data.length > 0) {
                    for (const productTypeSelect of productTypeSelects) {
                        data.map((productType) => {
                            const option = document.createElement('option');
                            option.value = productType.id;
                            option.text = productType.name;
                            productTypeSelect.appendChild(option);
                        });
                    }
                }
            }else {
                const data = await response.json();
                showNotification(data.message, NotificationType.Error);
            }
        }catch(error) {
            console.error(error);
            showNotification("Something went wrong loading the product types. Try again later.",
                NotificationType.Error);
        }
    },

    async addNewProduct() {
        const success = await this.addNewProductOnServer();

        if (success) {
            sellForm.reset();
            await this.populateProducts();
            showNotification("Product added successfully!", NotificationType.Success);
        }
    },

    async addNewProductOnServer() {
        const url = new URL("/api/v1/product", window.location.origin);

        const formData = new FormData();

        for (const element of sellForm.elements) {
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

        try {
            const response = await fetch(url, { method: 'POST', body: formData });

            if (response.ok) {
                return true;
            }else {
                const data = await response.json();
                showNotification(data.message, NotificationType.Error);
                return false;
            }

        }catch(error) {
            console.error(error);
            showNotification("Something went wrong on the server. Try again later.", NotificationType.Error);
            return false;
        }
    },
};

document.addEventListener("DOMContentLoaded", function () {
    products.init();
});