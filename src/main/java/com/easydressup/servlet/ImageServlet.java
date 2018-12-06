/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easydressup.servlet;

import com.easydressup.enity.Cloths;
import com.easydressup.facade.ClothsFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is the image retrieval servlet
 *
 e* @author
 */
@WebServlet(urlPatterns = {"/image/*"})
public class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = -5776835694664426775L;
    @EJB
    private ClothsFacade service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getPathInfo().substring(1));
        Cloths cloths = service.find(id);
        response.setContentType(cloths.getContentType());
        response.setContentLength(cloths.getImage().length);
        response.getOutputStream().write(cloths.getImage());
    }
}
