package org.marketplace.server.model.deserializers;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.OrderStatus;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class used for deserializing Orders from JSON format
 */

public class OrderDeserializer extends JsonDeserializer<Order> {
    private final List<User> userList;
    private final List<Product> productList;

    public OrderDeserializer(List<User> userTable, List<Product> productTable) {
        this.userList = userTable;
        this.productList = productTable;
    }

    @Override
    public Order deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        int id = node.get("id").asInt();
        LocalDateTime date = LocalDateTime.parse(node.get("timestamp").asText());
        int buyerId = node.get("buyer").get("id").asInt();
        User buyer = findUserById(buyerId);
        int productId = node.get("product").get("id").asInt();
        Product product = findProductById(productId);
        OrderStatus orderStatus = OrderStatus.descriptionValueOf(node.get("orderStatus").get("status").asText());

        return new Order(id, buyer, product, date, orderStatus);
    }

    private Product findProductById(int productId) {
        for (Product product : productList) {
            if (product.getId() == productId) {
                return product;
            }
        }
        System.out.println("Product with id " + productId + " not found");
        return null;
    }

    private User findUserById(int sellerId) {
        for (User user : userList) {
            if (user.getId() == sellerId) {
                return user;
            }
        }
        System.out.println("User with id " + sellerId + " not found");
        return null;
    }
}
