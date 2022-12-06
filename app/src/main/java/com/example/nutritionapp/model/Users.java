package com.example.nutritionapp.model;
import com.example.nutritionapp.model.User;
public class Users {
    private User[] users;
    public int total;
    int skip;
    int limit;

    public Users(User[] users, int total, int skip, int limit)
    {
        this.users = users;
        this.total = total;
        this.skip = skip;
        this.limit = limit;
    }

    public User[] getUsers()
    {return this.users;}


}
