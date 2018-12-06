/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easydressup.controller;

import com.easydressup.enity.Category;
import com.easydressup.enity.Cloths;
import com.easydressup.enity.User;
import com.easydressup.facade.CategoryFacade;
import com.easydressup.facade.ClothsFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author Aman Multani
 */
@ManagedBean
@SessionScoped
public class ClothsUploadController {

    private Part uploadedFile;
    private String folder = "upload/cloths";
    @EJB
    private ClothsFacade clothsFacade;
    @EJB
    private CategoryFacade categoryFacade;

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void saveFile(User user,String currentCategory) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        InputStream input = null;
        try {
            input = uploadedFile.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = input.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            byte[] bytes = buffer.toByteArray();
            String fileName = uploadedFile.getSubmittedFileName();

            Cloths cloths = new Cloths();
            cloths.setName(fileName);
            cloths.setContentType(uploadedFile.getContentType());
            cloths.setUser(user);
            cloths.setUploadedOn(new Date());
            cloths.setImage(bytes);
            Category category=categoryFacade.find(currentCategory);
            cloths.setCategory(new Category(currentCategory));

            clothsFacade.create(cloths);

            FacesMessage msg = new FacesMessage("Cloth file is uploaded successfully");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            facesContext.addMessage(null, msg);
            facesContext.renderResponse();
        } catch (IOException ex) {
            FacesMessage msg = new FacesMessage("Falied to upload the file");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(null, msg);
        } finally {
            try {
                if (null != input) {
                    input.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ClothsUploadController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

   }
