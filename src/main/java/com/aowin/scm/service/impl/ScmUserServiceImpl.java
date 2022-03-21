package com.aowin.scm.service.impl;

import com.aowin.scm.dao.ScmUserDao;
import com.aowin.scm.pojo.ScmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ScmUserServiceImpl implements com.aowin.scm.service.ScmUserService {
    @Autowired
    private ScmUserDao scmUserDao;


    @Override
    public ScmUser findUserByAccount(String account) {
        return scmUserDao.findUserByAccount(account);
    }

    @Override
    public List<ScmUser> findAll(Map<String, Object> params) {
        return scmUserDao.findAll(params);
    }

    @Override
    public int countAll() {
        return scmUserDao.countAll();
    }

    @Override
    public void addUser(ScmUser scmUser) {
        scmUserDao.addUser(scmUser);
    }

    @Override
    public void removeByAccount(String account) {
        scmUserDao.removeByAccount(account);
    }

    @Override
    public void updateUser(ScmUser scmUser) {
        scmUserDao.updateUser(scmUser);
    }


}
