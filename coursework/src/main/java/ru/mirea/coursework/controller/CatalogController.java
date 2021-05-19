package ru.mirea.coursework.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mirea.coursework.entity.Product;
import ru.mirea.coursework.entity.ProductType;
import ru.mirea.coursework.entity.UserBasket;
import ru.mirea.coursework.repository.ProductTypeRepository;
import ru.mirea.coursework.repository.ProductRepository;
import ru.mirea.coursework.repository.UserBasketRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class CatalogController {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    ProductRepository productrepository;

    @Autowired
    UserBasketRepository userBasketRepository;


    @GetMapping("/catalog")
    public String catalog(@RequestParam("id") Long id, Model typeModel, Model productModel, Model uBasketModel) {
        Optional<ProductType> type = productTypeRepository.findById(id);
        Iterable<Product> products = productrepository.findByProductType(productTypeRepository.findById(id));
        typeModel.addAttribute("type", type);
        productModel.addAttribute("products", products);
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        uBasketModel.addAttribute("basket", uBasket);
        return "/catalog";
    }

    @PostMapping("/addfromcatalog")
    public String inBasketFromCatalog(Long id1, Long id2, Model typeModel, Model productModel, Model moduleId, Model modelCat){
        moduleId.addAttribute("id1", id1);
        modelCat.addAttribute("id2", id2);
       Optional<ProductType> type = productTypeRepository.findById(id2);
        Iterable<Product> products = productrepository.findByProductType(productTypeRepository.findById(id2));
        typeModel.addAttribute("type", type);
        productModel.addAttribute("products", products);
        System.out.println(id1 + " " + id2);

       Optional<Product> thisProduct = productrepository.findById(id1);
        UserBasket userBasket = new UserBasket();
        userBasket.setId(thisProduct.get().getId());
        userBasket.setName(thisProduct.get().getName());
        userBasket.setImage(thisProduct.get().getImage());
        userBasket.setPrice(thisProduct.get().getPrice());
        userBasket.setProductType(thisProduct.get().getProductType().getId());
        System.out.println(userBasket);
        userBasketRepository.save(userBasket);
        return "redirect:/catalog?id=" + id2;
    }

    @PostMapping("/deletefromcatalog")
    public String deleteFromBasketFromProduct(Long id1, Long id2, Model productModel, Model typeModel, Model uBasketModel, Model mod1, Model mod2) {
        mod1.addAttribute("id1", id1);
        mod2.addAttribute("id2", id2);
        Optional<ProductType> type = productTypeRepository.findById(id2);
        Iterable<Product> products = productrepository.findByProductType(productTypeRepository.findById(id2));
        typeModel.addAttribute("type", type);
        productModel.addAttribute("products", products);
        System.out.println(id1 + " " + id2);
        userBasketRepository.deleteById(id1);
        AtomicReference<Long> newId = new AtomicReference<>(1l);
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        userBasketRepository.deleteAll();
        uBasket.forEach(userBasket -> userBasket.setId(newId.getAndSet(newId.get() + 1)));
        userBasketRepository.saveAll(uBasket);
        System.out.println(id1 + " "  +id2);
        return "redirect:/catalog?id=" + id2;
    }
}
