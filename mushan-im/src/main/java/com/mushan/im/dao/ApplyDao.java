package com.mushan.im.dao;

import com.mushan.im.pojo.Apply;
import com.mushan.im.pojo.User;

import java.util.List;

public interface ApplyDao {
    int add(Apply apply);

    List<User> getFriends(Long id);
}
