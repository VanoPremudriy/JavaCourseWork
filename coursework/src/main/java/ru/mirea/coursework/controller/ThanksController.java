package ru.mirea.coursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.coursework.entity.User;
import ru.mirea.coursework.entity.UserBasket;
import ru.mirea.coursework.repository.UserBasketRepository;
import ru.mirea.coursework.repository.UserRepository;

import java.util.Optional;

@Controller
public class ThanksController {

    @Autowired
    UserBasketRepository userBasketRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/thanks")
    public String thanks(@RequestParam("id") Long id,  Model uBasketModel, Model userModel){
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        uBasketModel.addAttribute("basket", uBasket);
        Optional<User> user = userRepository.findById(id);
        userModel.addAttribute("user", user);
        return "/thanks";
    }
}
