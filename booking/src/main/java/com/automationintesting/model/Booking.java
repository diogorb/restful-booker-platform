package com.automationintesting.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Booking {

    @JsonProperty
    private int bookingid;

    @JsonProperty
    private int roomid;

    @JsonProperty
    @NotBlank(message = "Firstname should not be blank")
    @Size(min = 3, max = 18)
    private String firstname;

    @JsonProperty
    @NotBlank(message = "Lastname should not be blank")
    @Size(min = 3, max = 30)
    private String lastname;

    @JsonProperty
    @NotNull(message = "Total price should not be null")
    @Min(10)
    @Max(999)
    private int totalprice;

    @JsonProperty
    @NotNull(message = "Deposit paid should not be null")
    private boolean depositpaid;

    @JsonProperty(value = "bookingdates")
    @Valid
    private BookingDates bookingDates;

    @JsonProperty
    private Optional<@NotEmpty @Email String> email;

    @JsonProperty
    private Optional<@NotEmpty @Size(min = 11, max = 21) String> phone;

    public Booking(int bookingid, int roomid, String firstname, String lastname, int totalprice, boolean depositpaid, BookingDates bookingDates) {
        this.bookingid = bookingid;
        this.roomid = roomid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingDates = bookingDates;
    }

    public Booking(int bookingid, int roomid, String firstname, String lastname, int totalprice, boolean depositpaid, BookingDates bookingDates, String email, String phone) {
        this.bookingid = bookingid;
        this.roomid = roomid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingDates = bookingDates;
        this.email = Optional.of(email);
        this.phone = Optional.of(phone);
    }

    public Booking(ResultSet result) throws SQLException {
        this.bookingid = result.getInt("bookingid");
        this.roomid = result.getInt("roomid");
        this.firstname = result.getString("firstname");
        this.lastname = result.getString("lastname");
        this.totalprice = result.getInt("totalprice");
        this.depositpaid = result.getBoolean("depositpaid");
        this.bookingDates = new BookingDates(result.getDate("checkin"), result.getDate("checkout"));
    }

    public Booking() {
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public BookingDates getBookingDates() {
        return bookingDates;
    }

    public int getRoomid() {
        return roomid;
    }

    public int getBookingid() {
        return bookingid;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<String> getPhone() {
        return phone;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public void setBookingDates(BookingDates bookingDates) {
        this.bookingDates = bookingDates;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public void setEmail(String email) {
        this.email = Optional.of(email);
    }

    public void setPhone(String phone) {
        this.phone = Optional.of(phone);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "roomid=" + roomid +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", totalprice=" + totalprice +
                ", depositpaid=" + depositpaid +
                ", bookingDates=" + bookingDates +
                '}';
    }


    public static class BookingBuilder {

        private int roomid;
        private String firstname;
        private String lastname;
        private int totalprice;
        private boolean depositpaid;
        private Date checkin;
        private Date checkout;
        private String email;
        private String phone;

        public BookingBuilder setRoomid(int roomid){
            this.roomid = roomid;

            return this;
        }

        public BookingBuilder setFirstname(String firstname) {
            this.firstname = firstname;

            return this;
        }

        public BookingBuilder setLastname(String lastname) {
            this.lastname = lastname;

            return this;
        }

        public BookingBuilder setTotalprice(int totalprice) {
            this.totalprice = totalprice;

            return this;
        }

        public BookingBuilder setDepositpaid(boolean depositpaid) {
            this.depositpaid = depositpaid;

            return this;
        }

        public BookingBuilder setCheckin(Date checkin) {
            this.checkin = checkin;

            return this;
        }

        public BookingBuilder setCheckout(Date checkout) {
            this.checkout = checkout;

            return this;
        }

        public BookingBuilder setEmail(String email) {
            this.email = email;

            return this;
        }

        public BookingBuilder setPhone(String phone) {
            this.phone = phone;

            return this;
        }

        public Booking build(){
            BookingDates bookingDates = new BookingDates(checkin, checkout);

            if(email == null && phone == null){
                return new Booking(0, roomid, firstname, lastname, totalprice, depositpaid, bookingDates);
            } else {
                return new Booking(0, roomid, firstname, lastname, totalprice, depositpaid, bookingDates, email, phone);
            }
        }
    }
}
