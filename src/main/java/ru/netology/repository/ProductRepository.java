package ru.netology.repository;

import ru.netology.domain.NotFoundException;
import ru.netology.domain.Product;

public class ProductRepository {

    private Product[] items = new Product[0];

    public void save(Product item) {

        int length = items.length + 1;
        Product[] tmp = new Product[length];
//        for (int i = 0; i < items.length; i++) {
//            tmp[i] = items[i];
//        }
        System.arraycopy(items, 0, tmp, 0, items.length);
        int lastIndex = tmp.length - 1;
        tmp[lastIndex] = item;
        items = tmp;
    }

    public void removeById(int id) {
        if (findById(id) != null) {
            int index = 0;
            Product[] tmp = new Product[items.length - 1];
            for (Product item : items) {
                if (item.getId() != id) {
                    tmp[index] = item;
                    index++;
                }
            }
            items = tmp;
        } else {
            throw new NotFoundException(id);
        }
    }


//    public Product findById(int id) {
//        for (???) {
//            if (???) {
//                return product;
//            }
//        }
//        return null;
//    }

    public Product findById(int id) {
        for (Product item : items) {
            if (item.getId() == id) {
                Product product = item;
                return product;
            }
        }
        return null;
    }


    public Product[] findAll() {
        return items;
    }


}
