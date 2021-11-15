package tinkoff.unit11.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.exception.ForbiddenException;
import tinkoff.unit11.infrastructure.IUserAccessor;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PermissionGuardTest {

    @Autowired
    public PermissionGuard permissionGuard;

    @MockBean
    public IUserAccessor userAccessor;

    @Test
    public void sameUserTest() {

        User user = new User();
        user.setId(1L);
        when(userAccessor.getCurrentUser()).thenReturn(user);

        permissionGuard.ensureCanModify(user);
    }

    @Test
    public void adminUserTest() {

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setRole(Role.ADMIN);
        when(userAccessor.getCurrentUser()).thenReturn(currentUser);

        User user = new User();
        user.setId(5L);
        permissionGuard.ensureCanModify(currentUser);
    }

    @Test
    public void notAdminAndNotSameUserTest() {

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setRole(Role.USER);
        when(userAccessor.getCurrentUser()).thenReturn(currentUser);

        try {
            User user = new User();
            user.setId(5L);

            permissionGuard.ensureCanModify(user);
            Assertions.fail("Should have exception thrown");
        } catch (ForbiddenException ignored) {
        }
    }
}
