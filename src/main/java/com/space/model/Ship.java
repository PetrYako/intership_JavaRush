package com.space.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "Ship")
public class Ship {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private long id;

    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "planet")
    private String planet;

    @JoinColumn(name = "shipType")
    @Enumerated(value = EnumType.STRING)
    private ShipType shipType;

    @JoinColumn(name = "prodDate")
    @Temporal(TemporalType.DATE)
    private Date prodDate;

    @JoinColumn(name = "isUsed")
    private boolean isUsed;

    @JoinColumn(name = "speed")
    private double speed;

    @JoinColumn(name = "crewSize")
    private int crewSize;

    @JoinColumn(name = "rating")
    private double rating;

    public Ship() {}

    public Ship(String name, String planet, ShipType shipType, Date prodDate, double speed, int crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.speed = speed;
        this.crewSize = crewSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(int crewSize) {
        this.crewSize = crewSize;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getYear() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(prodDate);

        return calendar.get(Calendar.YEAR);
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name=\"" + name + '\"' +
                ", planet=\"" + planet + '\"' +
                ", shipType=\"" + shipType  + "\"" +
                ", prodDate=" + prodDate +
                ", isUsed=" + isUsed +
                ", speed=" + speed +
                ", crewSize=" + crewSize +
                ", rating=" + rating +
                "}";
    }
}
