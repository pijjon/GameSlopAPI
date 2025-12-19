package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        return null;
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
