package com.mattae.snl.plugins.flowable.services;

import com.mattae.snl.plugins.security.extensions.IdentityProviderExtension;
import io.github.jbella.snl.core.api.services.ExtensionService;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.Picture;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@ConditionalOnBean(ExtensionService.class)
public class UsersRolesUpdateService {
    private final IdentityService identityService;
    private final ExtensionService extensionService;

    @Scheduled(cron = "0 */5 * ? * *")
    @Transactional
    public void updateIdentityManagement() {
        updateUsers();
        updateGroup();
        updateGroupMembership();
    }

    private void updateUsers() {
        var usernames = extensionService.getPriorityExtension(IdentityProviderExtension.class).getUsers()
            .stream().map(IdentityProviderExtension.User::username)
            .toList();
        identityService.createUserQuery().list()
            .forEach(user -> {
                if (!usernames.contains(user.getId())) {
                    identityService.createGroupQuery().list()
                        .forEach(group -> identityService.deleteMembership(user.getId(), group.getId()));
                    identityService.setUserPicture(user.getId(), null);
                    identityService.deleteUser(user.getId());
                }
            });
        extensionService.getPriorityExtension(IdentityProviderExtension.class).getUsers()
            .forEach(user -> {
                org.flowable.idm.api.User flowableUser = identityService
                    .createUserQuery().userId(user.username()).singleResult();
                boolean newRecord = false;
                if (flowableUser == null) {
                    newRecord = true;
                    flowableUser = identityService.newUser(user.username());
                }
                if (newRecord || (!Objects.equals(user.email(), flowableUser.getEmail()) ||
                    !Objects.equals(user.firstName(), flowableUser.getFirstName()) ||
                    !Objects.equals(user.lastName(), flowableUser.getLastName()) ||
                    !Objects.equals(user.displayName(), flowableUser.getDisplayName()))) {
                    flowableUser.setEmail(user.email());
                    flowableUser.setFirstName(user.firstName());
                    flowableUser.setLastName(user.lastName());
                    flowableUser.setDisplayName(user.displayName());
                    identityService.saveUser(flowableUser);
                }
                String avatar = user.picture();
                if (avatar != null) {
                    String regex = "data:(.*);base64,(.*)";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(avatar);
                    if (matcher.matches()) {
                        byte[] data = Base64.getDecoder().decode(matcher.group(2));
                        Picture picture = new Picture(data, matcher.group(1));
                        identityService.setUserPicture(user.username(), picture);
                    }
                }
            });
    }

    private void updateGroup() {
        var roles = extensionService.getPriorityExtension(IdentityProviderExtension.class).getRoles()
            .stream()
            .map(IdentityProviderExtension.Role::name)
            .toList();
        identityService.createGroupQuery().list()
            .forEach(group -> {
                if (!roles.contains(group.getName())) {
                    identityService.createUserQuery().list()
                        .forEach(user -> identityService.deleteMembership(user.getId(), group.getId()));
                    identityService.deleteGroup(group.getId());
                }
            });
        extensionService.getPriorityExtension(IdentityProviderExtension.class).getRoles()
            .forEach(role -> {
                Group group = identityService.createGroupQuery().groupId(role.name()).singleResult();
                if (group == null) {
                    group = identityService.newGroup(role.name());
                    group.setName(role.name());
                    identityService.saveGroup(group);
                }
            });
    }

    private void updateGroupMembership() {
        extensionService.getPriorityExtension(IdentityProviderExtension.class).getUsers()
            .forEach(user -> {
                identityService.createGroupQuery().list().forEach(r -> {
                    identityService.deleteMembership(user.username(), r.getName());
                });
                user.roles()
                    .forEach(role -> {
                        identityService.createMembership(user.username(), role.name());
                    });
            });
    }
}
