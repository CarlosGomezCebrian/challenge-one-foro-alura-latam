package edu.foro.api.domain.user;

import edu.foro.api.domain.user.UpdateUserData;
import edu.foro.api.domain.course.Category;
import edu.foro.api.infra.errors.IntegrityValidity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public DataDetailUser setDataRegistrationUser(DataRegistrationUser dataRegistrationUser) {
        try {
            // Encriptar la contraseña antes de guardarla en la base de datos
            String passwordEncrypted = bCryptPasswordEncoder.encode(dataRegistrationUser.password_hash());
            dataRegistrationUser = new DataRegistrationUser(
                    dataRegistrationUser.login(),
                    passwordEncrypted,
                    dataRegistrationUser.first_name(),
                    dataRegistrationUser.last_name(),
                    dataRegistrationUser.email(),
                    dataRegistrationUser.image_url(),
                    dataRegistrationUser.category()
            );

            User user = new User(dataRegistrationUser);
            userRepository.save(user);
            return new DataDetailUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error registering user: " + e.getMessage());
        }
    }

    public Page<DataDetailUser> listarActivated(Pageable pageable) {
        return userRepository.findByActivatedTrue(pageable).map(DataDetailUser::new);
    }

    @Transactional
    public void inactive(InactiveUserData inactiveUserData) {

        Optional<User> optionalUser = userRepository.findById(inactiveUserData.id());
        if(optionalUser.isEmpty()){
            throw new IntegrityValidity("User with ID " + inactiveUserData.id() + " not found");
        }

        User user = userRepository.getReferenceById(inactiveUserData.id());
        user.inactiveUser();
    }

    @Transactional
    public DataDetailUser setDataUpdateUser(UpdateUserData updateUserData) {
        Optional<User> optionalUser = userRepository.findById(updateUserData.id());
        if(optionalUser.isEmpty()){
            throw new IntegrityValidity("User with ID " + updateUserData.id() + " not found");
        }
        User userUp = optionalUser.get();
        String login, passwordEncrypted, first_name, last_name, email, image_url;
        Category category;

        if (updateUserData.login() != null && !updateUserData.login().isEmpty()){
            login = updateUserData.login();
        } else {
             login = userUp.getLogin();
        }
        if(updateUserData.password_hash() != null && !updateUserData.password_hash().isEmpty()){
             passwordEncrypted = bCryptPasswordEncoder.encode(updateUserData.password_hash());
        } else {
             passwordEncrypted = userUp.getPassword_hash();
        }
        if (updateUserData.first_name() != null && !updateUserData.first_name().isEmpty()){
            first_name = updateUserData.first_name();
        } else {
            first_name = userUp.getFirst_name();
        }
        if (updateUserData.last_name() != null && !updateUserData.last_name().isEmpty()){
            last_name = updateUserData.last_name();
        } else {
            last_name = userUp.getLast_name();
        }
        if (updateUserData.email() != null && !updateUserData.email().isEmpty()){
            email = updateUserData.email();
        } else {
            email = userUp.getEmail();
        }
        if (updateUserData.image_url() != null && !updateUserData.image_url().isEmpty()){
            image_url = updateUserData.image_url();
        } else {
            image_url = userUp.getImage_url();
        }
        if (updateUserData.category() != null ){
            category = updateUserData.category();
        } else {
            category = userUp.getCategory();
        }

        User user = userRepository.getReferenceById(updateUserData.id());
        user.UpdateUser(login, passwordEncrypted, first_name, last_name,
                       email, image_url, category);
        return new DataDetailUser(user);

    }
}

