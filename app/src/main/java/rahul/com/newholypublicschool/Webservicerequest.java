package rahul.com.newholypublicschool;


import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.ArrayList;


public class Webservicerequest {



    // String URL="http://103.231.100.146/dsclweb/service.asmx";
    // String URL="http://shriramsampark.com/sampark/service.asmx";
    String URL="http://surya.somee.com/Service/Service.asmx";
    //String URL="http://192.168.1.123/New_dscl/dsclwebtest/service.asmx";
    //String URL="http://192.168.1.123/dsclwebtest/service.asmx";
    public String MobileWebservice(String Methodname, ArrayList<String> propertyArray)


    {


        //String URL="http://182.18.134.227/Dsclbussiness/mfarm/service.asmx";http://103.231.100.146/dsclweb/service.asmx
        //String URL="http://103.231.100.146/dsclwebtest/service.asmx";
        // String URL="http://103.231.100.146/dsclweb/service.asmx";

        String NAMESPACE="http://tempuri.org/";
        String SOAPAction=NAMESPACE+Methodname;
        SoapObject request=new SoapObject(NAMESPACE, Methodname);

        HttpTransportSE androidhttptransport=new HttpTransportSE(URL,300000);

        for(int icount=0;icount<propertyArray.size();icount+=2){

            request.addProperty(propertyArray.get(icount), propertyArray.get(icount+1));
        }
        request.addProperty("Connection","Close");


        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        String resultstring = null;

        try {

            androidhttptransport.debug = true;
            androidhttptransport.call(SOAPAction, envelope);
            Log.d("HTTP REQUEST ",androidhttptransport.requestDump);
            Log.d("HTTP RESPONSE", androidhttptransport.responseDump);

            //	androidhttptransport.call(SOAPAction, envelope);
            Object results=(Object)envelope.getResponse();
            resultstring=results.toString();

        }  catch (Exception e) {

            e.printStackTrace();

        }


        return resultstring;
    }






    public String MobileWebservice(Context cnt, String Methodname, ArrayList<String> propertyArray)


    {


        PowerManager pm = (PowerManager) cnt.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();

        // String URL="http://103.231.100.146/dsclwebtest/service.asmx";
        String NAMESPACE="";

      //  String NAMESPACE="http://aksha/app/";
        String SOAPAction=NAMESPACE+Methodname;
        SoapObject request=new SoapObject(NAMESPACE, Methodname);

        HttpTransportSE androidhttptransport=new HttpTransportSE(URL,99999999);

        for(int icount=0;icount<propertyArray.size();icount+=2){

            request.addProperty(propertyArray.get(icount), propertyArray.get(icount+1));
        }
        request.addProperty("Connection","Close");


        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        String resultstring = null;

        try {
            androidhttptransport.call(SOAPAction, envelope);
            Object results=(Object)envelope.getResponse();
            resultstring=results.toString();

        }  catch (Exception e) {

            e.printStackTrace();

        }

        mWakeLock.release();

        return resultstring;
    }






////////////////web service function , dscl create farmer


    public String mobileWebserviceRegister(Context context, String Methodname, Register propertyArray)
    {
//String URL = "http://103.231.100.146/dsclwebtest/service.asmx";

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();


        String NAMESPACE="http://aksha/app/";
        String SOAPAction=NAMESPACE+Methodname;
        String METHOD_NAME = Methodname;

        SoapObject request=new SoapObject(NAMESPACE, Methodname);

//SoapObject logs=new SoapObject(NAMESPACE, "demo");

        PropertyInfo pn=new PropertyInfo();
        pn.setName("objCrt");
        pn.setValue(propertyArray);
        pn.setType(propertyArray.getClass());
        request.addProperty(pn);


//request.addSoapObject(logs);

        HttpTransportSE androidhttptransport=new HttpTransportSE(URL);

        ArrayList<HeaderProperty> h=new ArrayList<HeaderProperty>();
        h.add(new HeaderProperty("Content-Type", "text/xml;charset=utf-8"));
        h.add(new HeaderProperty("SOAPAction", SOAPAction));
//request.addProperty("Content-Type", "text/xml;charset=utf-8");
//request.addProperty("SOAPAction", SOAPAction);


        System.setProperty("http.keepAlive", "false");
        try {
            androidhttptransport.getServiceConnection().setRequestProperty("Connection", "close");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }



        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.implicitTypes=true;
        envelope.setAddAdornments(false);
        envelope.encodingStyle=SoapSerializationEnvelope.ENV;
        envelope.setOutputSoapObject(request);
//	envelope.addMapping(NAMESPACE, "CreateDemocls", new CreateDemocls().getClass());
        String resultstring = null;

        try {
            androidhttptransport.debug = true;
            androidhttptransport.call(SOAPAction, envelope,h);
            Log.d("HTTP REQUEST ",androidhttptransport.requestDump);
            Log.d("HTTP RESPONSE", androidhttptransport.responseDump);
            Object results=(Object)envelope.getResponse();
            androidhttptransport.reset();
            resultstring=results.toString();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();

        try {
            androidhttptransport.getServiceConnection().disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try{
                Toast.makeText(context, "Connection Failed", Toast.LENGTH_LONG).show();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        mWakeLock.release();
        return resultstring;
    }










}
