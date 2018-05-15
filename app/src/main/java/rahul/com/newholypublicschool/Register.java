package rahul.com.newholypublicschool;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Register implements KvmSerializable {

    private String chanid;
    private String org_name;
    private String name;
    private String mobile;
    private String stateid;
    private String distid;
    private String taluka;
    private String vill;
    private String pin;
    private String pan;
    private String adhar;
    private String pestlic;
    private String fertlic;
    private String lat;
    private String longitude;
    private String empid;
    private String association;
    private String villid;
    private String newvillid;
    private String newvillname,address,distributer1,distributer2,business,gender,dob;
    private String seed_lic;
    private String seed_validity;
    private String pes_validity;
    private String fer_validity;

    public String getCr_by() {
        return cr_by;
    }

    private String cr_by;

    public Register(String org_name, String name, String mobile,
                    String stateid, String distid, String taluka, String vill, String villid, String newvillid, String newvillname,
                    String pin, String pan, String adhar, String pestlic,
                    String fertlic, String lat, String longitude, String empid,
                    String association, String address, String distributer1, String distributer2, String business, String gender, String dob, String seed_lic, String seed_validity, String pes_validity, String fer_validity, String emp_id) {

        this.org_name = org_name;
        this.name = name;
        this.mobile = mobile;
        this.stateid = stateid;
        this.distid = distid;
        this.taluka = taluka;
        this.vill = vill;
        this.villid = villid;
        this.newvillid = newvillid;
        this.newvillname = newvillname;
        this.pin = pin;
        this.pan = pan;
        this.adhar = adhar;
        this.pestlic = pestlic;
        this.fertlic = fertlic;
        this.lat = lat;
        this.longitude = longitude;
        this.empid = empid;
        this.association = association;
        this.chanid="0";
        this.address=address;
        this.distributer1=distributer1;
        this.distributer2=distributer2;
        this.business=business;
        this.gender=gender;
        this.dob=dob;
        this.seed_lic=seed_lic;
        this.seed_validity=seed_validity;
        this.pes_validity=pes_validity;
        this.fer_validity=fer_validity;
        this.cr_by=emp_id;
    }

    //Own_name,district,mobile,Village,chanid,chantype
    public String getchanid()
    {
        return chanid;
    }
    public String getretname()
    {
        return org_name;
    }
    public String getmobile()
    {
        return mobile;
    }
    public String getchantype()
    {
        return "3";
    }
    public String getvillage()
    {
        return vill;
    }

    public String getdistrict()
    {
        return distid;
    }
    public String getVillid()
    {
        return newvillid;
    }
    public String getVillname()
    {
        return newvillname;
    }

    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "org_name";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "name";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "mobile";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "stateid";
                break;

            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "distid";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "taluka";
                break;

            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "vill";
                break;

            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "villid";
                break;

            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "newvillid";
                break;

            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "newvillname";
                break;

            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "pin";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "pan";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "adhar";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "pestlic";
                break;

            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "fertlic";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "lat";
                break;

            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "longitude";
                break;

            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "empid";
                break;

            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "association";
                break;
            case 19:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "chanid";
                break;
            case 20:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "address";
                break;
            case 21:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "distributer1";
                break;
            case 22:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "distributer2";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "business";
                break;
            case 24:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "gender";
                break;
            case 25:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "dob";
                break;

            case 26:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "seed_lic";
                break;
            case 27:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "seed_validity";
                break;
            case 28:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "pes_validity";
                break;
            case 29:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "fer_validity";
                break;
            default:
                break;
        }
    }

    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                org_name = (value.toString());
                break;
            case 1:
                name = value.toString();
                break;
            case 2:
                mobile = value.toString();
                break;

            case 3:
                stateid = value.toString();
                break;
            case 4:
                distid = value.toString();
                break;
            case 5:
                taluka = value.toString();
                break;
            case 6:
                vill = value.toString();
                break;
            case 7:
                villid = value.toString();
                break;
            case 8:
                newvillid = value.toString();
                break;
            case 9:
                newvillname = value.toString();
                break;

            case 10:
                pin = value.toString();
                break;
            case 11:
                pan = value.toString();
                break;
            case 12:
                adhar = value.toString();
                break;
            case 13:
                pestlic = value.toString();
                break;
            case 14:
                fertlic = value.toString();
                break;
            case 15:
                lat = value.toString();
                break;

            case 16:
                longitude = value.toString();
                break;
            case 17:
                empid = value.toString();
                break;
            case 18:
                association = value.toString();
                break;
            case 19:
                chanid = value.toString();
                break;
            case 20:
                address = value.toString();
                break;
            case 21:
                distributer1 = value.toString();
                break;
            case 22:
                distributer2 = value.toString();
                break;
            case 23:
                business = value.toString();
                break;
            case 24:
                gender = value.toString();
                break;
            case 25:
                dob = value.toString();
                break;

            case 26:
                seed_lic = value.toString();
                break;
            case 27:
                seed_validity = value.toString();
                break;
            case 28:
                pes_validity = value.toString();
                break;
            case 29:
                fer_validity = value.toString();
                break;
            default:
                break;
        }
    }



    @Override
    public Object getProperty(int arg0) {

        switch (arg0) {
            case 0:
                return org_name;
            case 1:
                return name;

            case 2:
                return mobile;

            case 3:
                return stateid;

            case 4:
                return distid;

            case 5:
                return taluka;

            case 6:
                return vill;
            case 7:
                return villid;
            case 8:
                return newvillid;
            case 9:
                return newvillname;

            case 10:
                return pin;

            case 11:
                return pan;

            case 12:
                return adhar;

            case 13:
                return pestlic;

            case 14:
                return fertlic;

            case 15:
                return lat;

            case 16:
                return longitude;

            case 17:
                return empid;

            case 18:
                return association;
            case 19:
                return chanid;
            case 20:
                return address;
            case 21:
                return distributer1;
            case 22:
                return distributer2;
            case 23:
                return business;
            case 24:
                return gender;
            case 25:
                return dob;

            case 26:
                return seed_lic;
            case 27:
                return seed_validity;
            case 28:
                return pes_validity;
            case 29:
                return fer_validity;
            default:
                break;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 30;
    }

}