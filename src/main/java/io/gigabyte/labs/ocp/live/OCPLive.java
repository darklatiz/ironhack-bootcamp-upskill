package io.gigabyte.labs.ocp.live;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

enum Color {
    RED, GREEN, BLUE
}

enum Size {
    SMALL, MEDIUM, LARGE, YUGE
}

class Product {
    public String name;
    public Color color;
    public Size size;

    public Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }
}

class ProductFilter {
    public Stream<Product> filterByColor(List<Product> products, Color color) {
        return products.stream().filter(p -> p.color == color);
    }

    public Stream<Product> filterBySize(List<Product> products, Size size) {
        return products.stream().filter(p -> p.size == size);
    }

    public Stream<Product> filterBySizeAndColor(List<Product> products, Size size, Color color) {
        return products.stream().filter(p -> p.size == size && p.color == color);
    }

    public Stream<Product> filterBySizeAndName(List<Product> products, Size size, String name) {
        return products.stream().filter(p -> p.size == size && p.name.equalsIgnoreCase(name));
    }

    public Stream<Product> filter(List<Product> products, Predicate<Product> predicate) {
        return products.stream().filter(predicate);
    }
    // state space explosion
    // 3 criteria = 7 methods
}

// we introduce two new interfaces that are open for extension
interface Specification<T> {
    boolean isSatisfied(T item);
}

interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> spec);
}

class ColorSpecification implements Specification<Product> {
    private Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product p) {
        return p.color == color;
    }
}

class SizeSpecification implements Specification<Product> {
    private Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product p) {
        return p.size == size;
    }
}

class NameSpecification implements Specification<Product> {
    private String name;

    public NameSpecification(String name) {
        this.name = name;
    }

    @Override
    public boolean isSatisfied(Product p) {
        return p.name.equalsIgnoreCase(name);
    }
}

class AndSpecification<T> implements Specification<T> {
    private Specification<T> first, second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }

}

class BetterFilter implements Filter<Product> {
    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
}

class OCPDemo {
    public static void main(String[] args) {
        Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
        Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
        Product camisa = new Product("Camisa", Color.GREEN, Size.LARGE);
        Product house = new Product("House", Color.BLUE, Size.LARGE);

        List<Product> products = List.of(apple, tree, house, camisa);

        ProductFilter pf = new ProductFilter();
        System.out.println("Green products (old):");
        pf.filterByColor(products, Color.GREEN)
          .forEach(p -> System.out.println(" - " + p.name + " is green"));

        // ^^ BEFORE

        // vv AFTER
        BetterFilter bf = new BetterFilter();
        System.out.println("Green products (new):");
        bf.filter(products, new ColorSpecification(Color.BLUE))
          .forEach(p -> System.out.println(" - " + p.name + " is bl"));

        System.out.println("Large products:");
        bf.filter(products, new SizeSpecification(Size.MEDIUM))
          .forEach(p -> System.out.println(" - " + p.name + " is large"));
//
        System.out.println("Large blue items:");
        bf.filter(products,
            new AndSpecification<>(
              new ColorSpecification(Color.BLUE),
              new SizeSpecification(Size.LARGE)
            ))
          .forEach(p -> System.out.println(" - " + p.name + " is large and blue"));
    }
}