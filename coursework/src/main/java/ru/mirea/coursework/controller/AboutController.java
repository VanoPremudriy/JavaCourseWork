package ru.mirea.coursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mirea.coursework.entity.UserBasket;
import ru.mirea.coursework.repository.UserBasketRepository;

import java.util.concurrent.atomic.AtomicReference;

@Controller
public class AboutController {

    @Autowired
    UserBasketRepository userBasketRepository;

    @GetMapping("/about")
    public String about(Model uBasketModel){
        Iterable<UserBasket> uBasket = userBasketRepository.findAll();
        uBasketModel.addAttribute("basket", uBasket);
        return "about";
    }

    @PostMapping("/deletefromabout")
    public String deleteFromBasketFromAbout(Long id, Model model, Model uBasketModel) {
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
        return "redirect:/about";
    }

}
