package ru.mirea.coursework.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.coursework.entity.UserBasket;

@Repository
public interface UserBasketRepository extends CrudRepository<UserBasket, Long> {
}
