package ru.mirea.coursework.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.coursework.entity.Product;
import ru.mirea.coursework.entity.ProductType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    public List<Product> findByProductType(Optional<ProductType> productType);

}
