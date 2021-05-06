package ru.mirea.coursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.coursework.entity.Product;
import ru.mirea.coursework.entity.ProductType;
import ru.mirea.coursework.repository.ProductRepository;
import ru.mirea.coursework.repository.ProductTypeRepository;

import java.util.Optional;


@Controller
public class ProductController {
    @Autowired
    ProductRepository productrepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @GetMapping("/product")
    public String product(@RequestParam("id") Long id,  Model productModel, Model typeModel) {
        Optional<Product> product = productrepository.findById(id);
        productModel.addAttribute("product", product);
        Optional<ProductType> type = productTypeRepository.findById(product.get().getProductType().getId());
        typeModel.addAttribute("type", type);
        return "/product";
    }
}
