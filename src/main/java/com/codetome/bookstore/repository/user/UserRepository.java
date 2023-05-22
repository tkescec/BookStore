package com.codetome.bookstore.repository.user;

import com.codetome.bookstore.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserByUsername(String username);
}
