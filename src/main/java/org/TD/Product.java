package org.TD;

import java.time.Instant;
import java.util.Objects;

public class Product {
    private int id ;
    private String name ;
    private Instant creationDatetime;
    private Category category ;

    public String getCategoryName (){
        return category.getName();
    }

    public String getName (){
        return name;
    }

    public Instant getCreationDatetime (){
        return creationDatetime;
    }

    public int getId (){
        return id;
    }

    public Category getCategory (){
        return category;
    }

    public Product (int id , String name , Instant creationDatetime , Category category) {
        this.id = id;
        this.name = name;
        this.creationDatetime = creationDatetime;
        this.category = category;
    }

    @Override
    public String toString(){
        return "Product [id = " +  id + ", name = " + name + ", creationDatetime = " + creationDatetime + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return id == product.id
                &&  Objects.equals(name, product.name)
                &&  Objects.equals(creationDatetime, product.creationDatetime)
                &&  Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDatetime, category);
    }
}
