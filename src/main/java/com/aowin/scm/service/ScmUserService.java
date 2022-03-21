package com.aowin.scm.service;

import com.aowin.scm.pojo.ScmUser;

import java.util.List;
import java.util.Map;

public interface ScmUserService {

    ScmUser findUserByAccount(String account);

    List<ScmUser> findAll(Map<String, Object> params);

    int countAll();

    void addUser(ScmUser scmUser);

    void removeByAccount(String account);

    void updateUser(ScmUser scmUser);

}
