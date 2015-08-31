package au.com.cathis.ma;

//Cordova importsimport org.apache.cordova.CordovaInterface;import org.apache.cordova.CallbackContext;import org.apache.cordova.CordovaPlugin;import org.apache.cordova.CordovaWebView;

//Android importsimport android.content.Context;import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
//JSON Importsimport org.json.JSONArray;import org.json.JSONException;

public class CarrierPlugin extends CordovaPlugin implement LocationListener {  public static final String ACTION_GET_CURRENT_POSITION = "getCurrentPosition”;  public static final String ACTION_GET_COUNTRY_CODE = "getCountryCode";  public LocationManager locationManager;
  public LocationListener locationListener;
  public String latitude, longitude;
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {    super.initialize(cordova, webView);    Context context = this.cordova.getActivity().getApplicationContext();    locationManager = (LocationManager)context.getSystemService(Context.TELEPHONY_SERVICE);  }
  @Override  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {    try {      if (ACTION_GET_CARRIER_NAME.equals(action)) {
	getCurrentPosition();
        callbackContext.success(latitude);        return true;      }      //We don't have a match, so it must be an invalid action      callbackContext.error("Invalid Action");      return false;    } catch (Exception e) {      //If we get here, then something horrible has happened
      System.err.println("Exception: " + e.getMessage());      callbackContext.error(e.getMessage());      return false;    }  }

  public void getCurrentPosition() {
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
  }

  @Override
  public void onLocationChanged(Location location) {
    latitude = String.valueOf(location.getLatitude());
    longitude = String.valueOf(location.getLongitude());
  }
 
  @Override
  public void onProviderDisabled(String provider) {
    Log.d("Latitude","disable");
  }
 
  @Override
  public void onProviderEnabled(String provider) {
    Log.d("Latitude","enable");
  }
 
  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    Log.d("Latitude","status");
  }}