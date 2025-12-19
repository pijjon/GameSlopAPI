package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    ShoppingCart addItem(int userId, int productId);

    void deleteCart(int userId);

    void updateItem(int userId, int productId, int quantity);
    // add additional method signatures here
}
