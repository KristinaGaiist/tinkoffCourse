package tinkoff.unit11.service;

import java.util.Objects;
import org.springframework.stereotype.Component;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.exception.ForbiddenException;
import tinkoff.unit11.infrastructure.IUserAccessor;

@Component
public class PermissionGuard {

    private final IUserAccessor userAccessor;

    public PermissionGuard(IUserAccessor userAccessor) {
        this.userAccessor = userAccessor;
    }

    public void ensureCanModify(User author) {
        User user = userAccessor.getCurrentUser();
        if (!(Objects.equals(author.getId(), user.getId()) || user.getRole().equals(Role.ADMIN))) {
            throw new ForbiddenException(String.format("%s can't access to the resource", user.getUsername()));
        }
    }
}
