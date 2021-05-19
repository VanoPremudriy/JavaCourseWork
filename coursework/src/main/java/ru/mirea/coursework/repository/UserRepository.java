package ru.mirea.coursework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mirea.coursework.entity.User;
public interface UserRepository extends CrudRepository<User, Long> {
}
