package tinkoff.unit11.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tinkoff.unit11.controller.contracts.AuthDto;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.service.UserService;

@RestController
@RequestMapping("/basicAuth")
public class BasicAuthController {

    private final UserService userService;

    public BasicAuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public AuthDto auth(Principal principal) {
        Role role = userService.getRoleByUsername(principal.getName());
        return new AuthDto("You are authenticated", role);
    }
}
