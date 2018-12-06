/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easydressup.enity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 
 */
@Entity
@Table(name = "useroutfitcalender")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserOutfitCalender.findAll", query = "SELECT u FROM UserOutfitCalender u")
    , @NamedQuery(name = "UserOutfitCalender.findById", query = "SELECT u FROM UserOutfitCalender u WHERE u.id = :id")
    , @NamedQuery(name = "UserOutfitCalender.findByDate", query = "SELECT u FROM UserOutfitCalender u WHERE u.date = :date")})
public class UserOutfitCalender implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "outfit", referencedColumnName = "id")
    @ManyToOne
    private UserOutfit outfit;
    @JoinColumn(name = "cloth", referencedColumnName = "id")
    @ManyToOne
    private Cloths cloth;
    @JoinColumn(name = "user", referencedColumnName = "userId")
    @ManyToOne
    private User user;

    public UserOutfitCalender() {
    }

    public UserOutfitCalender(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserOutfit getOutfit() {
        return outfit;
    }

    public void setOutfit(UserOutfit outfit) {
        this.outfit = outfit;
    }

    public Cloths getCloth() {
        return cloth;
    }

    public void setCloth(Cloths cloth) {
        this.cloth = cloth;
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
        if (!(object instanceof UserOutfitCalender)) {
            return false;
        }
        UserOutfitCalender other = (UserOutfitCalender) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.easydressup.enity.UserOutfitCalender[ id=" + id + " ]";
    }
    
}
