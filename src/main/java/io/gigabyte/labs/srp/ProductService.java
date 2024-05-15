package io.gigabyte.labs.srp;


public class ProductService {
    private ProductRepository accesoBD;
    private LoggingService logger;
    private NotificationService notifier;

    public ProductService(ProductRepository repository, LoggingService logger, NotificationService notifier) {
        this.accesoBD = repository;
        this.logger = logger;
        this.notifier = notifier;
    }

    public void addProduct(Product product, User createdBy) {
        if (accesoBD.validateProduct(product)) {
            accesoBD.saveProduct(product);
            logger.log("Product added: " + product.getId());
            notifier.sendNotification(createdBy.getEmail(), "Product Added", "A new product has been added: " + product.getName());
        }
    }
}

class Product{
    public String getId() {
        return null;
    }

    public String getName() {
        return null;
    }

    public int getPrice() {
        return 0;
    }

    public int getQuantity() {
        return 0;
    }

    public Object getCategory() {
        return null;
    }
}