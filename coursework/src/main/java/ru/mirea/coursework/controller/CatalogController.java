package ru.mirea.coursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mirea.coursework.entity.Product;
import ru.mirea.coursework.entity.ProductType;
import ru.mirea.coursework.repository.ProductTypeRepository;
import ru.mirea.coursework.repository.ProductRepository;

import java.util.Optional;

@Controller
public class CatalogController {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    ProductRepository productrepository;


    @GetMapping("/catalog")
    public String catalog(@RequestParam("id") Long id, Model typeModel, Model productModel) {
        Optional<ProductType> type = productTypeRepository.findById(id);
        Iterable<Product> products = productrepository.findByProductType(productTypeRepository.findById(id));
        typeModel.addAttribute("type", type);
        productModel.addAttribute("products", products);
        return "/catalog";
    }
}
