package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql = """
                SELECT * FROM shopping_cart
                JOIN products on products.product_id = shopping_cart.product_id
                WHERE user_id = ?;
                """;

        ShoppingCart cart = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1, userId);

            try (ResultSet row = preparedStatement.executeQuery()) {
                while (row.next()) {
                    cart = mapRow(row);
                }
            }
        } catch (SQLException e) {
            System.out.println("Issue querying for user's shopping cart items");
        }
        return cart;
    }

    public ShoppingCart addItem(int userId, int productId) {
        String sql = """
                INSERT INTO shopping_cart (user_id, product_id, quantity)
                VALUES (?, ?, 1)
                ON DUPLICATE KEY UPDATE quantity = quantity + 1;
                """;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);

            int rows = preparedStatement.executeUpdate();

            if (rows == 0) {
                System.out.println("No rows have been updated.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getByUserId(userId);
    }


    private ShoppingCart mapRow(ResultSet row) throws SQLException {
        ShoppingCart cart = new ShoppingCart();

        int productId = row.getInt("product_id");
        int quantity = row.getInt("quantity");
        String name = row.getString("name");
        BigDecimal price = row.getBigDecimal("price");
        int categoryId = row.getInt("category_id");
        String description = row.getString("description");
        String subCategory = row.getString("subcategory");
        String imageUrl = row.getString("image_url");
        int stock = row.getInt("stock");
        boolean isFeatured = row.getBoolean("featured");

        ShoppingCartItem item = new ShoppingCartItem();
        item.setProduct(new Product(productId, name, price, categoryId, description, subCategory, stock, isFeatured, imageUrl));
        item.setQuantity(quantity);
        cart.add(item);


        return cart;
    }
}
