package tinkoff.unit11.controller;

import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tinkoff.unit11.controller.contracts.CurrentUserDto;
import tinkoff.unit11.controller.contracts.RegisterUserRequest;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final IUserAccessor userAccessor;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserController(UserService userService, IUserAccessor userAccessor) {
        this.userService = userService;
        this.userAccessor = userAccessor;
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserRequest request) {

        User user = modelMapper.map(request, User.class);
        userService.saveUser(user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CurrentUserDto> getCurrentUser() {

        User user = userAccessor.getCurrentUser();
        CurrentUserDto currentUser = modelMapper.map(user, CurrentUserDto.class);

        return ResponseEntity.ok(currentUser);
    }
}
