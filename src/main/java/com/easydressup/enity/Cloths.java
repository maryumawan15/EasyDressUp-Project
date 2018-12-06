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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 
 */
@Entity
@Table(name = "cloths")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cloths.findAll", query = "SELECT c FROM Cloths c")
    , @NamedQuery(name = "Cloths.findById", query = "SELECT c FROM Cloths c WHERE c.id = :id")
    , @NamedQuery(name = "Cloths.findByName", query = "SELECT c FROM Cloths c WHERE c.name = :name")
    , @NamedQuery(name = "Cloths.findByContentType", query = "SELECT c FROM Cloths c WHERE c.contentType = :contentType")
    , @NamedQuery(name = "Cloths.findByUploadedOn", query = "SELECT c FROM Cloths c WHERE c.uploadedOn = :uploadedOn")})
public class Cloths implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 225)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "contentType")
    private String contentType;
    @Column(name = "uploadedOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedOn;
    @Lob
    @Column(name = "image")
    private byte[] image;
    @JoinColumn(name = "category", referencedColumnName = "category")
    @ManyToOne
    private Category category;
    @JoinColumn(name = "user", referencedColumnName = "userId")
    @ManyToOne
    private User user;

    public Cloths() {
    }

    public Cloths(Integer id) {
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getUploadedOn() {
        return uploadedOn;
    }

    public void setUploadedOn(Date uploadedOn) {
        this.uploadedOn = uploadedOn;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        if (!(object instanceof Cloths)) {
            return false;
        }
        Cloths other = (Cloths) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.easydressup.enity.Cloths[ id=" + id + " ]";
    }
    
}
