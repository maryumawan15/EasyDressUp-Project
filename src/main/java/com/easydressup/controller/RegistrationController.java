package com.easydressup.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.easydressup.enity.User;
import com.easydressup.facade.UsersFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * This controller has the functionality related to user registration
 *
 * @author Aman Multani
 */
@ManagedBean(name = "registrationController")
@SessionScoped
public class RegistrationController implements Serializable {

    private CloseableHttpClient CLIENT = HttpClients.createDefault();
    private static final long serialVersionUID = -5115227324370407117L;
    private List<String> genders;
    private String firstName;
    private String lastName;
    private String email;
    private String passwd;
    private Date dob;
    private String gender;

    @EJB
    private UsersFacade usersFacade;

    /**
     * Creates a new instance of RegistrationController
     */
    public RegistrationController() {
    }

    /**
     * Get the gender
     *
     * @return
     */
    public List<String> getGenders() {
        genders = new ArrayList();
        genders.add("Male");
        genders.add("Female");
        genders.add("Others");
        return genders;
    }

    /**
     * Validate the form after submission
     *
     * @param event
     */
    public void validate(ComponentSystemEvent event) {
        try {
            UIComponent components = event.getComponent();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            CLIENT = HttpClients.createDefault();
            HttpGet get = new HttpGet("http://localhost:8080/easydressup/user/find/" + email);
            get.addHeader("content-type", "application/json;charset=UTF-8");
            get.addHeader("charset", "UTF-8");
            HttpResponse response = (HttpResponse) CLIENT.execute(get);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
             User restUser = mapper.readValue((EntityUtils.toString(entity)), User.class);
             System.out.println("-------------------------------------"+restUser.getEmail());
            if (usersFacade.findUserByEmail(email) != null) {
                // get email
                UIInput emailInput = (UIInput) components.findComponent("email");
                FacesMessage msg = new FacesMessage("User with this e-mail already exists");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                facesContext.addMessage(emailInput.getClientId(), msg);
                facesContext.renderResponse();
            }
        } catch (IOException ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Register the user
     */
    public void register() {
        User user = new User();
        user.setDob(dob);
        user.setFirstName(firstName);
        user.setEmail(email);
        user.setGender(gender);
        user.setLastName(lastName);
        user.setPasswd(passwd);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage("Your account is created successfully");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        usersFacade.createUser(user);
        facesContext.addMessage(null, msg);
        facesContext.renderResponse();
        dob = null;
        firstName = "";
        lastName = "";
        gender = "";
        passwd = "";
        email = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
