package it.micael.vanini.cinemille.service;

import it.micael.vanini.cinemille.dto.UserDetailsImpl;
import it.micael.vanini.cinemille.model.User;
import it.micael.vanini.cinemille.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Trying to retrieve user with username: {}", username);
        LOGGER.info("Checking it user exists");
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isPresent()) {
            LOGGER.info("User found");
            return new UserDetailsImpl(
                    user.get().getIdUser(),
                    user.get().getUsername(),
                    user.get().getPassword()
            );
        } else {
            LOGGER.error("User not found");
            //TODO: Eccezzione corretta
            throw new UsernameNotFoundException("User not found");
        }
    }
    public boolean existsByUsername(String username) {
        LOGGER.info("Checking if user exists with username: {}", username);
        return this.userRepository.existsByUsername(username);
    }

    public User signUp(User user) {
        LOGGER.info("Saving user with username: {}", user.getUsername());
        return this.userRepository.save(user);
    }
}
