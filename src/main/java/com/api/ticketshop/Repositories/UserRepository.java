package com.api.ticketshop.Repositories;

import com.api.ticketshop.Models.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel, Integer> {

    UserModel findByEmail(String email);
}
