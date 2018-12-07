/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easydressup.controller;

import com.easydressup.enity.User;
import com.easydressup.enity.UserGroups;
import com.easydressup.facade.UsersFacade;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.Principal;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This is this login/logout functionality controller
 *
 * @author Aman Multani
 */
@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

    private static Logger log = Logger.getLogger(LoginController.class.getName());
    @EJB
    private UsersFacade usersFacade;

    /**
     * Serial version uid
     */
    private static final long serialVersionUID = -2493491004471992469L;
    private String email;
    private String password;
    private User user;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }

    /**
     * Login action
     *
     * @return
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        try {
            request.login(email, password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed!", null));
            return "index";
        }

        Principal principal = request.getUserPrincipal();

        this.user = usersFacade.findUserByEmail(principal.getName());
        log.info("Authentication done for user: " + principal.getName());

        sessionMap.put("user", user);
        if (request.isUserInRole("admin") && user.getRole().equals(UserGroups.ADMIN_GROUP)) {
            return "/admin/home?faces-redirect=true";
        } else if (request.isUserInRole("users")) {
            return "/user/home?faces-redirect=true";
        } else {
            return "index";
        }
    }

    /**
     * Logout event handler function which invalidate the session after log
     *
     * @return
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            this.user = null;
            request.logout();
            // clear the session
            ((HttpSession) context.getExternalContext().getSession(false)).invalidate();
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
        }

        return "/index?faces-redirect=true";
    }

    public String showLoginPage() {
        return "/index?faces-redirect=true";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        if (sessionMap.get("user") != null) {
            this.user = (User) sessionMap.get("user");
        }
        return user;
    }

}
