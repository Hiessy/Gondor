package ar.com.webapp.ticketing.rest.resources.asm;

import ar.com.webapp.ticketing.core.model.entities.User;
import ar.com.webapp.ticketing.rest.controller.UserController;
import ar.com.webapp.ticketing.rest.resources.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


public class UserResourceAsm extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAsm() {
        super(UserController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User user){
        UserResource res = new UserResource();
        res.setRid(user.getId());
        res.setUserName(user.getUserName());
        res.setName(user.getName());
        res.setPassword(user.getPassword());
        res.setProfile(user.getProfile());
        res.setArea(user.getArea());
        res.setStatus(user.getStatus());
        res.setEmail(user.getEmail());
        res.add(linkTo(methodOn(UserController.class).getUser(user.getUserName())).withSelfRel());
        res.add(linkTo(methodOn(UserController.class).findAllTickets(user.getUserName())).withRel("tickets"));
        return res;
    }
}
