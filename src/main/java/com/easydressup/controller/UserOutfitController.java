package com.easydressup.controller;

import com.easydressup.enity.UserOutfit;
import com.easydressup.facade.UserOutfitFacade;
import com.easydressup.controller.util.JsfUtil;
import com.easydressup.controller.util.PaginationHelper;
import com.easydressup.enity.Cloths;
import com.easydressup.enity.User;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class UserOutfitController implements Serializable {

    private UserOutfit current;
    private DataModel items = null;
    @EJB
    private com.easydressup.facade.UserOutfitFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public UserOutfitController() {
    }

    public UserOutfit getSelected() {
        if (current == null) {
            current = new UserOutfit();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserOutfitFacade getFacade() {
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
                    final List<UserOutfit> findRange = getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()});
                    
                    return new ListDataModel(findRange);
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "outfits";
    }

    public String prepareView() {
        current = (UserOutfit) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "viewoutfit";
    }

    public String prepareCreate() {
        current = new UserOutfit();
        selectedItemIndex = -1;
        return "createoutfit";
    }

    public String create(User user) {
        try {
            current.setUser(user);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/messages").getString("UserOutfitCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/messages").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (UserOutfit) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "editoutfit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/messages").getString("UserOutfitUpdated"));
            return "viewoutfit";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/messages").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (UserOutfit) getItems().getRowData();
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
            return "viewoutfit";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "listoutfit";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/messages").getString("UserOutfitDeleted"));
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
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
      User user=null;
        if(sessionMap.get("user")!=null){
           user=(User) sessionMap.get("user");
       }
        if (items == null) {
            items = getPagination().createPageDataModel(user);
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
        return "outfits";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "outfits";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public UserOutfit getUserOutfit(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = UserOutfit.class)
    public static class UserOutfitControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserOutfitController controller = (UserOutfitController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userOutfitController");
            return controller.getUserOutfit(getKey(value));
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
            if (object instanceof UserOutfit) {
                UserOutfit o = (UserOutfit) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UserOutfit.class.getName());
            }
        }

    }
    
    

}
