package Pabasara;

public class user {
    //variables
    public String fullName,email,phone,address,dob;

    //constructor
    public user(){}

    //overloaded constructor
    public user(String fullName, String email, String phone, String address, String dob) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
    }
}
