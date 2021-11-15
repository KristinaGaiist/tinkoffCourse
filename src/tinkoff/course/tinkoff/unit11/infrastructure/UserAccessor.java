package tinkoff.unit11.infrastructure;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.repository.UserRepository;

@Component
public class UserAccessor implements IUserAccessor {

    private final UserRepository userRepository;

    public UserAccessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalStateException("user not found");
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }
}
