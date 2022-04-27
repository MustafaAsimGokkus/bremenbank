package com.bank.bremen.controller;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import com.bank.bremen.controller.request.TransactionRequest;
import com.bank.bremen.controller.response.Response;
import com.bank.bremen.domain.Transaction;
import com.bank.bremen.domain.User;
import com.bank.bremen.security.service.UserDetailsImpl;
import com.bank.bremen.service.AccountService;
import com.bank.bremen.service.UserService;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    @Mock
    AccountService mockAccountService;

    @Mock
    UserService mockUserService;

    @InjectMocks //we inject the above 2 mocks to AccountController
    AccountController underTest;

    @Test
    void create_shouldReturnResponseWhenDeposit(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(600.0);
        transactionRequest.setComment("This is adeposit of 600 Euros");
        User user = new User();
        user.setId(1L);

        UserDetailsImpl currentUser = mock(UserDetailsImpl.class);
        currentUser.setId(1L);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext= mock(SecurityContext.class);

        // when authentication is successfull set context
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        //return curr user
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .thenReturn(currentUser);

        //u expect same user when you use userservice use this method
        when(mockUserService.findById(currentUser.getId())).thenReturn(user);
        doNothing().when(mockAccountService).deposit(transactionRequest,user);

        //deposit is controller method
        ResponseEntity <Response> depositResponse = underTest.deposit(transactionRequest);
        Response actual =depositResponse.getBody();
        assertAll(
                ()->assertNotNull(actual),
                ()->assertEquals(HttpStatus.CREATED,depositResponse.getStatusCode()),
                ()-> assertEquals(true,actual.getIsSuccess())
        );
    }

}
