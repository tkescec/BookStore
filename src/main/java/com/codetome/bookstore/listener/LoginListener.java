package com.codetome.bookstore.listener;

import com.codetome.bookstore.domain.User;
import com.codetome.bookstore.dto.LogDto;
import com.codetome.bookstore.repository.log.LogRepository;
import com.codetome.bookstore.repository.user.UserRepository;
import jakarta.servlet.ServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@Component
@AllArgsConstructor
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
    private UserRepository userRepository;
    private LogRepository logRepository;
    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {

        try {
            UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
            Optional<User> user = userRepository.findUserByUsername(userDetails.getUsername());
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            ServletRequest servletRequest = attributes.getRequest();

            LogDto logDto = new LogDto(
                    servletRequest.getRemoteAddr(),
                    new Timestamp(System.currentTimeMillis()),
                    user.get().getIDUser()
            );

            Number logID = logRepository.saveNewLog(logDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Login Successful");
    }
}
