package com.grocery.shop.service;

import com.grocery.shop.dto.UserDto;
import com.grocery.shop.exception.UserAlreadyExistsException;
import com.grocery.shop.model.Role;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.UserRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void saveUser_ifEmailDoesNotExist() {
        final UserDto userDto = new UserDto();
        userDto.setEmail("johndoe5@gmail.com");
        userDto.setPassword("123456");

        final User expectedUser = new User();
        expectedUser.setEmail(userDto.getEmail());
        expectedUser.setPassword(userDto.getPassword());
        expectedUser.setRoles(Role.USER);

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn(anyString());

        userService.saveUser(userDto);

        verify(userRepository).save(userArgumentCaptor.capture());

        final User actualUser = userArgumentCaptor.getValue();

        SoftAssertions.assertSoftly(softly->{
            softly.assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
            softly.assertThat(actualUser.getRoles()).isEqualTo(expectedUser.getRoles());
        });
    }

    @Test
    void saveUser_ifEmailExists() {
        UserDto userDto = new UserDto();
        userDto.setEmail("johndoe5@gmail.com");
        userDto.setPassword("123456");

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        assertThatExceptionOfType(UserAlreadyExistsException.class).isThrownBy(()->userService.saveUser(userDto));
    }
}
