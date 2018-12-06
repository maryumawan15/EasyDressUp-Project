/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easydressup.facade;

import com.easydressup.enity.User;
import com.easydressup.enity.UserGroups;
import com.easydressup.utils.PasswordUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author 
 */
@Stateless
public class UsersFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "easydressupPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(User.class);
    }

    public User createUser(User user) {
        try {
            user.setPasswd(PasswordUtils.encryptPassword(user.getPasswd()));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        UserGroups group = new UserGroups();
        group.setEmail(user.getEmail());
        user.setRole(UserGroups.USERS_GROUP);
        group.setGrouname(UserGroups.USERS_GROUP);
        em.persist(user);
        em.persist(group);

        return user;
    }

    /**
     * Find the user by email
     *
     * @param email
     * @return
     */
    public User findUserByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
        query.setParameter("email", email);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return user;
    }

}
