package ru.mirea.coursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mirea.coursework.entity.Product;
import ru.mirea.coursework.entity.ProductType;
import ru.mirea.coursework.entity.UserBasket;
import ru.mirea.coursework.repository.UserBasketRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class IndexController {

    @Autowired
    UserBasketRepository userBasketRepository;

    @GetMapping(value = {"/", "/index"})
    public String index(Model uBasketModel, Model basketSum){
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        uBasketModel.addAttribute("basket", uBasket);
        AtomicInteger sum = new AtomicInteger();
        uBasket.forEach(userBasket -> sum.set(sum.get() + userBasket.getPrice()));
        int sum1 = sum.get();
        basketSum.addAttribute("sum", sum1);
        return "index";
    }

    @PostMapping("/deletefromindex")
    public String deleteFromBasketFromIndex(Long id, Model model, Model uBasketModel) {
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        model.addAttribute("id", id);
        uBasketModel.addAttribute("basket", uBasket);
        System.out.println(id);
        userBasketRepository.deleteById(id);
        AtomicReference<Long> newId = new AtomicReference<>(1l);
        uBasket = userBasketRepository.findAll();
        userBasketRepository.deleteAll();
        uBasket.forEach(userBasket -> userBasket.setId(newId.getAndSet(newId.get() + 1)));
        userBasketRepository.saveAll(uBasket);
        System.out.println(id);
        return "redirect:/";
    }
}
