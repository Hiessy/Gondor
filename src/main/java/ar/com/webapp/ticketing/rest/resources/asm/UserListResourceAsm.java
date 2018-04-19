package ar.com.webapp.ticketing.rest.resources.asm;


import ar.com.webapp.ticketing.domain.utils.UserList;
import ar.com.webapp.ticketing.rest.controller.UserController;
import ar.com.webapp.ticketing.rest.resources.UserListResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class UserListResourceAsm extends ResourceAssemblerSupport<UserList, UserListResource> {

    public UserListResourceAsm() {
        super(UserController.class, UserListResource.class);
    }

    @Override
    public UserListResource toResource(UserList userList){
        UserListResource finalResrouce = new UserListResource();
        finalResrouce.setUsers(new UserResourceAsm().toResources(userList.getUsers()));
        return finalResrouce;

    }

}
