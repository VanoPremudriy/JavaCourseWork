package ru.mirea.coursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mirea.coursework.entity.User;
import ru.mirea.coursework.entity.UserBasket;
import ru.mirea.coursework.repository.UserBasketRepository;
import ru.mirea.coursework.repository.UserRepository;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class PurchaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserBasketRepository userBasketRepository;

    @GetMapping("/purchase")
    public String purchase(@ModelAttribute("new_user") User newUser, Model uBasketModel, Model basketSum){
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        uBasketModel.addAttribute("basket", uBasket);
        AtomicInteger sum = new AtomicInteger();
        uBasket.forEach(userBasket -> sum.set(sum.get() + userBasket.getPrice()));
        int sum1 = sum.get();
        basketSum.addAttribute("sum", sum1);
        return "/purchase";
    }


    @PostMapping("/adduser")
    public String addUser(@ModelAttribute("new_user") User newUser, Model uBasketModel) {
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        uBasketModel.addAttribute("basket", uBasket);
        userRepository.save(newUser);
        userBasketRepository.deleteAll();
        return "redirect:/thanks?id=" + newUser.getId();
    }
}
