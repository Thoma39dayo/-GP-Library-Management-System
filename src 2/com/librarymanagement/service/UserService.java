package com.librarymanagement.service;

import com.librarymanagement.user.Member;
import com.librarymanagement.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private static final Map<String, User> users = new HashMap<>();

    static {
        users.put("member1", new Member("member1", "Member 1", "password1"));
        users.put("librarian1", new com.librarymanagement.user.Librarian("librarian1", "Librarian 1", "password1"));
    }

    public User authenticate(String id, String password, Class<? extends User> userType) throws AuthenticationException {
        try {
            User user = users.get(id);
            if (user != null && user.getPassword().equals(password) && userType.isInstance(user)) {
                return user;
            }
            throw new AuthenticationException("Authentication failed");
        } catch (Exception e) {
            throw new AuthenticationException("Error during authentication: " + e.getMessage());
        }
    }

    public User getUserById(String id) {
        return users.get(id);
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        for (User user : users.values()) {
            if (user instanceof Member) {
                members.add((Member) user);
            }
        }
        return members;
    }
}

class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }
}    