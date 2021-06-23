package unit11.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import unit11.controller.contracts.UserDto;
import unit11.entity.User;
import unit11.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody UserDto user) {

        User userEntity = modelMapper.map(user, User.class);
        userService.saveUser(userEntity);

        return ResponseEntity.noContent().build();
    }
}
