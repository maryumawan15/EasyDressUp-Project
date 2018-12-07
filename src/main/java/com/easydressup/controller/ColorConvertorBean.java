/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easydressup.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Maryum
 */
@ManagedBean
@SessionScoped
public class ColorConvertorBean implements Serializable {

    private Map<String, String> colors = new HashMap<String, String>();
    private String selectedColor = "red";

    /**
     * Creates a new instance of ColorConvertorBean
     */
    public ColorConvertorBean() {
    }

    public Map<String, String> getColors() {
        colors.put("Red", "red");
        colors.put("Black", "black");
        colors.put("Orange", "orange");
        return colors;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    //value change event listener
    public void countryLocaleCodeChanged(ValueChangeEvent e) {

        String color = e.getNewValue().toString();
        setSelectedColor(color);

    }
}
