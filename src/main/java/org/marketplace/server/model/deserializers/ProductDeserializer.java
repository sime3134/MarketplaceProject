package org.marketplace.server.model.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductCondition;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.model.User;

import java.io.IOException;
import java.util.List;

public class ProductDeserializer extends JsonDeserializer<Product> {

    private final List<User> userList;
    private final List<ProductType> productTypeList;

    public ProductDeserializer(List<User> userList, List<ProductType> productTypeList) {
        this.userList = userList;
        this.productTypeList = productTypeList;
    }

    @Override
    public Product deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        // Deserialize nested fields
        int id = node.get("id").asInt();
        double productPrice = node.get("productPrice").asDouble();
        String color = node.get("color").asText();
        String yearOfProduction = node.get("yearOfProduction").asText();
        boolean isAvailable = node.get("available").asBoolean();
        int sellerId = node.get("seller").get("id").asInt();
        User seller = findUserByid(sellerId);

        ProductCondition productCondition =
                ProductCondition.descriptionValueOf(node.get("productCondition").get("description").asText());
        int productTypeId = (node.get("productType").get("id").asInt());
        ProductType productType = findProductTypeById(productTypeId);

        return new Product(id, productType, productPrice, yearOfProduction, color, productCondition, seller,
                isAvailable);
    }

    private ProductType findProductTypeById(int productTypeId) {
        for (ProductType productType : productTypeList) {
            if (productType.getId() == productTypeId) {
                return productType;
            }
        }
        return null;
    }

    private User findUserByid(int sellerId) {
        for (User user : userList) {
            if (user.getId() == sellerId) {
                return user;
            }
        }
        return null;
    }
}
