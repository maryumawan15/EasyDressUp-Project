/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easydressup.facade;

import com.easydressup.enity.UserOutfit;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 
 */
@Stateless
public class UserOutfitFacade extends AbstractFacade<UserOutfit> {

    @PersistenceContext(unitName = "easydressupPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserOutfitFacade() {
        super(UserOutfit.class);
    }
    
}
