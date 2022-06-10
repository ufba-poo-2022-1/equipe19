package com.api.ticketshop.Services;

import com.api.ticketshop.Config.JwtTokenUtil;
import com.api.ticketshop.DTOs.UserDTO;
import com.api.ticketshop.Models.BillingAddressModel;
import com.api.ticketshop.Models.UserModel;
import com.api.ticketshop.Repositories.BillingAddressRepository;
import com.api.ticketshop.Repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;
    private final BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, BillingAddressRepository billingAddressRepository) {

        this.userRepository = userRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    @Transactional
    public UserModel getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public List<UserModel> listAllUsers(){
        return (List<UserModel>) userRepository.findAll();
    }

    @Transactional
    public Optional<UserModel> getUserByID(String id) {
        int intID = Integer.parseInt(id);
        return userRepository.findById(intID);
    }

    @Transactional
    public UserModel createNewUser(UserDTO userDTO) {

        BillingAddressModel billingAddressModel = new BillingAddressModel();

        BeanUtils.copyProperties(userDTO.getAddress(), billingAddressModel);

        BillingAddressModel lastBillingInsert = billingAddressRepository.save(billingAddressModel);

        UserModel userModel = convertUserDTO(userDTO, lastBillingInsert.getId());

        return userRepository.save(userModel);

    }

    @Transactional
    public UserModel updateUserInfo(String id, Map<Object, Object> fields)  {

        int intID = Integer.parseInt(id);

        UserModel user = userRepository
                            .findById(intID)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No users with this id"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(UserModel.class, key.toString());
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, value);
        });

        return userRepository.save(user);

    }


    @Transactional
    public Boolean deleteUserByID(String id){

        int intID = Integer.parseInt(id);

        if(userRepository.existsById(intID)){
            userRepository.deleteById(intID);
            return true;
        };

        return false;
    }

    @Transactional
    public BillingAddressModel getUserAddress(String id) {
        int intID = Integer.parseInt(id);
        UserModel user = userRepository
                            .findById(intID)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No users with this id"));
        return billingAddressRepository
                            .findById(user.getBilling_address_id())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No address with this id"));
    }

    @Transactional
    public BillingAddressModel updateUserAddress(String userId, Map<Object, Object> fields){

        int userIntId = Integer.parseInt(userId);

        UserModel user = userRepository
                .findById(userIntId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No users with this id"));

        BillingAddressModel billingAddress = billingAddressRepository
                .findById(user.getBilling_address_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No address with this id"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(BillingAddressModel.class, key.toString());
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, billingAddress, value);
        });

        return billingAddressRepository.save(billingAddress);

    }

    private UserModel convertUserDTO(UserDTO userDTO, Integer lastBillingInsert){

        UserModel userModel = new UserModel();

        userModel.setBilling_address_id(lastBillingInsert);
        userModel.setName(userDTO.getName());
        userModel.setSurname(userDTO.getSurname());
        userModel.setCpf(userDTO.getCpf());
        userModel.setEmail(userDTO.getEmail());
        userModel.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userModel.setTel(userDTO.getTel());
        userModel.setType(userDTO.getType());

        return userModel;
    }

    public boolean isAdmin(String token) {

        String jwt = token.split(" ")[1];

        return Integer.parseInt(jwtTokenUtil.getTypeFromToken(jwt)) == 2;

    }

    public String getUserIdFromToken(String token) {

        if (token.isEmpty()) {
            return " ";
        }

        String jwt = token.split(" ")[1];

        return jwtTokenUtil.getIDFromToken(jwt);

    }


}
