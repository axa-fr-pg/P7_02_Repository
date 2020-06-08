package swa.poseidon.services;

import org.springframework.stereotype.Service;

import swa.poseidon.form.UserForm;
import swa.poseidon.model.User;

@Service
public class UserServiceImpl extends EntityServiceImpl<User,UserForm> implements UserService
{
}
