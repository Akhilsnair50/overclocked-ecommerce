package com.shopme.common.entity.section;

import com.shopme.common.entity.Brand;
import jakarta.persistence.*;

@Entity
@Table(name = "sections_brands")
public class BrandSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "brand_order")
    private int brandOrder;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBrandOrder() {
        return brandOrder;
    }

    public void setBrandOrder(int brandOrder) {
        this.brandOrder = brandOrder;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}
