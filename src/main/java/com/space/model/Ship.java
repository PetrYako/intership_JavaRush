package com.space.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

@Entity
@Table(name = "ship")
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "planet")
    @NotBlank
    private String planet;

    @Column(name = "shipType")
    @Enumerated(value = EnumType.STRING)
    private ShipType shipType;

    @Column(name = "prodDate")
    @Temporal(TemporalType.DATE)
    private Date prodDate;

    @Column(name = "isUsed")
    @NotNull
    private boolean isUsed;

    @Column(name = "speed")
    @NotNull
    private double speed;

    @Column(name = "crewSize")
    @NotNull
    private int crewSize;

    @Column(name = "rating")
    @NotNull
    private double rating;

    public Ship() {}

    public Ship(Integer id, String name, String planet, ShipType shipType, Date prodDate, double speed, int crewSize) {
        this.id = id;
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.speed = speed;
        this.crewSize = crewSize;
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

    public boolean isNew() {
        return getId() == null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;
        return id == ship.id &&
                isUsed == ship.isUsed &&
                Double.compare(ship.speed, speed) == 0 &&
                crewSize == ship.crewSize &&
                Double.compare(ship.rating, rating) == 0 &&
                name.equals(ship.name) &&
                planet.equals(ship.planet) &&
                shipType == ship.shipType &&
                prodDate.equals(ship.prodDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
