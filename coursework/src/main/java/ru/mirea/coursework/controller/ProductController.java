package ru.mirea.coursework.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.coursework.entity.Product;
import ru.mirea.coursework.entity.ProductType;
import ru.mirea.coursework.entity.UserBasket;
import ru.mirea.coursework.repository.ProductRepository;
import ru.mirea.coursework.repository.ProductTypeRepository;
import ru.mirea.coursework.repository.UserBasketRepository;

import java.util.Optional;


@Controller
public class ProductController {
    @Autowired
    ProductRepository productrepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    UserBasketRepository userBasketRepository;

    private Session session = null;
    SessionFactory  sessionFactory  = null;


    @GetMapping("/product")
    public String product(@RequestParam("id") Long id,  Model productModel, Model typeModel) {
        Optional<Product> product = productrepository.findById(id);
        productModel.addAttribute("product", product);
        Optional<ProductType> type = productTypeRepository.findById(product.get().getProductType().getId());
        typeModel.addAttribute("type", type);
        return "/product";
    }

    
    @PostMapping("/product")
    public String inBasket(@RequestParam("id") Long id, Model productModel, Model typeModel ){
        Optional<Product> product = productrepository.findById(id);
        productModel.addAttribute("product", product);
        Optional<ProductType> type = productTypeRepository.findById(product.get().getProductType().getId());
        typeModel.addAttribute("type", type);
        UserBasket userBasket = new UserBasket();
        userBasket.setImage(product.get().getImage());
        userBasket.setName(product.get().getName());
        userBasket.setPrice(product.get().getPrice());
        userBasket.setProductType(product.get().getProductType());
        userBasketRepository.save(userBasket);
        return "/about";
    }
}
