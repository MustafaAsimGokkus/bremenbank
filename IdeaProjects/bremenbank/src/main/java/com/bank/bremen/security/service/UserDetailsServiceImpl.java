package com.bank.bremen.security.service;

import com.bank.bremen.domain.User;
import com.bank.bremen.exception.ResourceNotFoundException;
import com.bank.bremen.exception.message.ExceptionMessage;
import com.bank.bremen.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUserNameAndEnabledTrue(username).
                orElseThrow(()->new ResourceNotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND_MESSAGE,username)));
        return UserDetailsImpl.build(user);
    }


}
