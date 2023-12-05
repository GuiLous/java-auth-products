package com.crud.crud.user.repositories;

import com.crud.crud.DTOS.RegisterDTO;
import com.crud.crud.user.enums.UserRole;
import com.crud.crud.user.models.UserModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class IUserRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    IUserRepository userRepository;

    @Test
    @DisplayName("Should return a user by email from DB")
    void findByEmailCaseSuccess() {
        RegisterDTO data = new RegisterDTO("test@gmail.com", "123", UserRole.USER);
        this.createUser(data);

        Optional<UserDetails> result = Optional.ofNullable(this.userRepository.findByEmail(data.email()));

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not return a user by email from DB when user not exists")
    void findByEmailCaseError() {
        Optional<UserDetails> result = Optional.ofNullable(this.userRepository.findByEmail("test2@gmail.com"));

        assertThat(result.isEmpty()).isTrue();
    }

    private void createUser(RegisterDTO data) {
        UserModel newUser = new UserModel(data.email(), data.password(), data.role());
        this.entityManager.persist(newUser);
    }
}