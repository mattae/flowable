package com.mattae.snl.plugins.flowable.services.model;

import lombok.Getter;
import lombok.Setter;
import org.flowable.idm.api.User;
import org.flowable.ui.common.model.UserRepresentation;

@Getter
@Setter
public class ExtendedUserRepresentation extends UserRepresentation {
    protected String picture;

    public ExtendedUserRepresentation(User user, String picture) {
        super(user);
        this.picture = picture;
    }
}
