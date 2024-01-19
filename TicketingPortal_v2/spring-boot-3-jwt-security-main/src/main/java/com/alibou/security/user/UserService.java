package com.alibou.security.user;

import com.alibou.security.exceptionHandling.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public void addOver60sCard(String token) throws CustomException {
        User user = repository.findUserByTokens_token(token);
        if (user.isHasAFamilyCard()) throw new CustomException(HttpStatus.CONFLICT, "Can not have two types of discount cards");
        user.setHasAOver60sCard(true);
    }

    public void addFamilyCard(String token) throws CustomException {
        User user = repository.findUserByTokens_token(token);
        if (user.isHasAOver60sCard()) throw new CustomException(HttpStatus.CONFLICT, "Can not have two types of discount cards");
        user.setHasAFamilyCard(true);
    }
}
