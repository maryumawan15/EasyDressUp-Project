package com.easydressup.controller;

import com.easydressup.enity.UserOutfitCalender;
import com.easydressup.facade.UserOutfitCalenderFacade;
import com.easydressup.controller.util.JsfUtil;
import com.easydressup.controller.util.PaginationHelper;
import com.easydressup.enity.Cloths;
import com.easydressup.enity.User;
import com.easydressup.enity.UserOutfit;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class UserOutfitCalenderController implements Serializable {

    private static final long serialVersionUID = -8024756479286988776L;

    private UserOutfitCalender current;
    private DataModel items = null;
    @EJB
    private com.easydressup.facade.UserOutfitCalenderFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public UserOutfitCalenderController() {
    }

    public UserOutfitCalender getSelected() {
        if (current == null) {
            current = new UserOutfitCalender();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserOutfitCalenderFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (UserOutfitCalender) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new UserOutfitCalender();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create(User user, Cloths cloth, UserOutfit outfit) {
        try {
            current.setUser(user);
            current.setDate(new Date());
            current.setCloth(cloth);
            current.setOutfit(outfit);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/messages").getString("UserOutfitCalenderCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/messages").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (UserOutfitCalender) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "editoutfit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/messages").getString("UserOutfitCalenderUpdated"));
            return "viewoutfit";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/messages").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (UserOutfitCalender) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "outfits";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/messages").getString("UserOutfitCalenderDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/messages").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public UserOutfitCalender getUserOutfitCalender(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = UserOutfitCalender.class)
    public static class UserOutfitCalenderControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserOutfitCalenderController controller = (UserOutfitCalenderController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userOutfitCalenderController");
            return controller.getUserOutfitCalender(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UserOutfitCalender) {
                UserOutfitCalender o = (UserOutfitCalender) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UserOutfitCalender.class.getName());
            }
        }

    }

}
