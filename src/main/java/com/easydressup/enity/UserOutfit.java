/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easydressup.enity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 
 */
@Entity
@Table(name = "useroutfit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserOutfit.findAll", query = "SELECT u FROM UserOutfit u")
    , @NamedQuery(name = "UserOutfit.findById", query = "SELECT u FROM UserOutfit u WHERE u.id = :id")
    , @NamedQuery(name = "UserOutfit.findByName", query = "SELECT u FROM UserOutfit u WHERE u.name = :name")})
public class UserOutfit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "outfit")
    private List<UserOutfitCalender> userOutfitCalenderList;
    @JoinColumn(name = "user", referencedColumnName = "userId")
    @ManyToOne
    private User user;

    public UserOutfit() {
    }

    public UserOutfit(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<UserOutfitCalender> getUserOutfitCalenderList() {
        return userOutfitCalenderList;
    }

    public void setUserOutfitCalenderList(List<UserOutfitCalender> userOutfitCalenderList) {
        this.userOutfitCalenderList = userOutfitCalenderList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserOutfit)) {
            return false;
        }
        UserOutfit other = (UserOutfit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.easydressup.enity.UserOutfit[ id=" + id + " ]";
    }
    
}
