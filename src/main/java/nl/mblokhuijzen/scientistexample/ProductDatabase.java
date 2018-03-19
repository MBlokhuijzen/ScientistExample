package nl.mblokhuijzen.scientistexample;

public class ProductDatabase {


    public Product getProductV1(int id) {
        if (id == 1) {
            return new Product(1, "Product1");
        } else {
            return new Product(10, "Product10");
        }
    }


    public Product getProductV2(int id) {
        try {
            int randomNumber = (int) (Math.random() * 100 + 1);

            Thread.sleep(randomNumber);

            if (id == 1) {
                return new Product(1, "Product1");
            } else if (id == 2) {
                return new Product(2, "Product2");
            } else {
                return new Product(10, "Product10");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new Product(0, "No Product");
        }

    }
}
