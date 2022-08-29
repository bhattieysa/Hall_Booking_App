package com.example.hallbooking;

public class HallRegistrationModel {
    String Hall_Name;
    String Hall_address;
    String Hall_phone;
    String Indoor_Halls;
    String Outdoor_Halls;
    String HAll_Capacity;
    String Hall_Desc;
    String Rent_Car;
    String Photo_Video;
    String Extra_Decoration;
    String Image;
    String Status;

    public String getHall_Name() {
        return Hall_Name;
    }

    public void setHall_Name(String hall_Name) {
        Hall_Name = hall_Name;
    }

    public String getHall_address() {
        return Hall_address;
    }

    public void setHall_address(String hall_address) {
        Hall_address = hall_address;
    }

    public String getHall_phone() {
        return Hall_phone;
    }

    public void setHall_phone(String hall_phone) {
        Hall_phone = hall_phone;
    }

    public String getIndoor_Halls() {
        return Indoor_Halls;
    }

    public void setIndoor_Halls(String indoor_Halls) {
        Indoor_Halls = indoor_Halls;
    }

    public String getOutdoor_Halls() {
        return Outdoor_Halls;
    }

    public void setOutdoor_Halls(String outdoor_Halls) {
        Outdoor_Halls = outdoor_Halls;
    }

    public String getHAll_Capacity() {
        return HAll_Capacity;
    }

    public void setHAll_Capacity(String HAll_Capacity) {
        this.HAll_Capacity = HAll_Capacity;
    }

    public String getHall_Desc() {
        return Hall_Desc;
    }

    public void setHall_Desc(String hall_Desc) {
        Hall_Desc = hall_Desc;
    }

    public String getRent_Car() {
        return Rent_Car;
    }

    public void setRent_Car(String rent_Car) {
        Rent_Car = rent_Car;
    }

    public String getPhoto_Video() {
        return Photo_Video;
    }

    public void setPhoto_Video(String photo_Video) {
        Photo_Video = photo_Video;
    }

    public String getExtra_Decoration() {
        return Extra_Decoration;
    }

    public void setExtra_Decoration(String extra_Decoration) {
        Extra_Decoration = extra_Decoration;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public HallRegistrationModel(String hall_Name, String hall_address, String hall_phone, String indoor_Halls,
                                 String outdoor_Halls, String HAll_Capacity, String hall_Desc, String rent_Car,
                                 String photo_Video, String extra_Decoration, String image, String status) {
        Hall_Name = hall_Name;
        Hall_address = hall_address;
        Hall_phone = hall_phone;
        Indoor_Halls = indoor_Halls;
        Outdoor_Halls = outdoor_Halls;
        this.HAll_Capacity = HAll_Capacity;
        Hall_Desc = hall_Desc;
        Rent_Car = rent_Car;
        Photo_Video = photo_Video;
        Extra_Decoration = extra_Decoration;
        Image = image;
        Status = status;
    }

    public HallRegistrationModel() {
    }
}
