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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


@Controller
public class ProductController {
    @Autowired
    ProductRepository productrepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    UserBasketRepository userBasketRepository;



    @GetMapping("/product")
    public String product(@RequestParam("id") Long id,  @ModelAttribute("bskt") UserBasket bskt, Model productModel, Model typeModel, Model uBasketModel, Model bsktModel, Model basketSum) {
        bsktModel.addAttribute("bskt",bskt);
        Optional<Product> product = productrepository.findById(id);
        productModel.addAttribute("product", product);
        Optional<ProductType> type = productTypeRepository.findById(product.get().getProductType().getId());
        typeModel.addAttribute("type", type);
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        uBasketModel.addAttribute("basket", uBasket);
        AtomicInteger sum = new AtomicInteger();
        uBasket.forEach(userBasket -> sum.set(sum.get() + userBasket.getPrice()));
        int sum1 = sum.get();
        basketSum.addAttribute("sum", sum1);
        return "/product";
    }


    @PostMapping("/addfromproduct")
    public String inBasketFromProduct(@RequestParam("id") Long id, @ModelAttribute("bskt") UserBasket bskt, Model productModel, Model typeModel, Model uBasketModel, Model bsktModel){
        bsktModel.addAttribute("bskt",bskt);
        Optional<Product> product = productrepository.findById(id);
        productModel.addAttribute("product", product);
        Optional<ProductType> type = productTypeRepository.findById(product.get().getProductType().getId());
        typeModel.addAttribute("type", type);
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        uBasketModel.addAttribute("basket", uBasket);
        UserBasket userBasket = new UserBasket();
        userBasket.setImage(product.get().getImage());
        userBasket.setName(product.get().getName());
        userBasket.setPrice(product.get().getPrice());
        userBasket.setProductType(product.get().getProductType().getId());
        userBasketRepository.save(userBasket);
        return "redirect:/product?id=" + id;
    }

    @PostMapping("/deletefromproduct")
    public String deleteFromBasketFromProduct(Long id1, Long id2, Model productModel, Model typeModel, Model uBasketModel, Model mod1, Model mod2) {
        mod1.addAttribute("id1", id1);
        mod2.addAttribute("id2", id2);
        Optional<Product> product = productrepository.findById(id2);
        productModel.addAttribute("product", product);
        Optional<ProductType> type = productTypeRepository.findById(product.get().getProductType().getId());
        typeModel.addAttribute("type", type);
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        uBasketModel.addAttribute("basket", uBasket);
        userBasketRepository.deleteById(id1);
        AtomicReference<Long> newId = new AtomicReference<>(1l);
        uBasket = userBasketRepository.findAll();
        userBasketRepository.deleteAll();
        uBasket.forEach(userBasket -> userBasket.setId(newId.getAndSet(newId.get() + 1)));
        userBasketRepository.saveAll(uBasket);
        System.out.println(id1 + " "  +id2);
        return "redirect:/product?id=" + id2;
    }
}
