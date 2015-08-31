package org.apache.cordova.antplus.heartrate;

	import java.math.BigDecimal;
    import java.util.EnumSet;

	import org.apache.cordova.CordovaPlugin;
	import org.apache.cordova.CallbackContext;

    import org.json.JSONArray;
    import org.json.JSONException;
    import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc;
    import com.dsi.ant.plugins.antplus.pccbase.AntPluginPcc.IPluginAccessResultReceiver;
	import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc.DataState;
	import com.dsi.ant.plugins.antplus.pcc.AntPlusHeartRatePcc.IHeartRateDataReceiver;
	import com.dsi.ant.plugins.antplus.pcc.defines.DeviceState;
	import com.dsi.ant.plugins.antplus.pcc.defines.EventFlag;
	import com.dsi.ant.plugins.antplus.pcc.defines.RequestAccessResult;
	import com.dsi.ant.plugins.antplus.pccbase.PccReleaseHandle;
	import com.dsi.ant.plugins.antplus.pccbase.AntPluginPcc.IDeviceStateChangeReceiver;

    /***********************************************************
     * This class interfaces with an ant+ heart rate monitor
     * Two actions are supported:
     * 1. ConnectHeartRateMonitor
     * 2. GetHeartRate
     * 
     * The actions are called from JavaScript.
     ***********************************************************/
    public class HeartRateMonitorInterface extends CordovaPlugin {

        AntPlusHeartRatePcc hrPcc = null;
        protected PccReleaseHandle<AntPlusHeartRatePcc> releaseHandle = null;
        RequestAccessResult resultstatus = null;
        CallbackContext callbackContext_ = null;
        String hrmHeartRate = "";
        String hrmHeartBeatCount = "";
        String hrmHeartBeatEventTime = "";

        @Override
        public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        	callbackContext_ = callbackContext;
            if (action.equals("ConnectHeartRateMonitor")) {
				// starts the search for an Ant+ HRM
                releaseHandle = 
            		AntPlusHeartRatePcc.requestAccess(this.cordova.getActivity(),
            		                                  this.cordova.getActivity(), 
            		                                  base_IPluginAccessResultReceiver, 
            		                                  base_IDeviceStateChangeReceiver);

                return true;
            }
            if (action.equals("GetHeartRate")) {
            	sendresult(hrmHeartRate, callbackContext_);
                return true;
            }
            return false;
        }
        /////////////////////////////////////////////////////////////////////
        // Callback for AntPlusHeartRatePcc.requestAccess
        // Class Method "onResultReceived" called
        /////////////////////////////////////////////////////////////////////
        IPluginAccessResultReceiver<AntPlusHeartRatePcc> base_IPluginAccessResultReceiver =
            new IPluginAccessResultReceiver<AntPlusHeartRatePcc>()
            {
                //Handle the result, connecting to events on success or reporting failure to user.
                @Override
                public void onResultReceived(AntPlusHeartRatePcc result, RequestAccessResult resultCode,
                    DeviceState initialDeviceState)
                {
                	resultstatus = resultCode;
                    switch(resultCode)
                    {
                        case SUCCESS:
                            hrPcc = result;
                            sendresult(result.getDeviceName(), callbackContext_);
                            subscribeToHrEvents();
                            break;
                        case CHANNEL_NOT_AVAILABLE:
                            sendresult("Channel not available", callbackContext_);
                            break;
                        case ADAPTER_NOT_DETECTED:
                            sendresult("Adapter not detected", callbackContext_);
                            break;
                        case BAD_PARAMS:
                            sendresult("Bad Params", callbackContext_);
                             break;
                        case OTHER_FAILURE:
                            sendresult("Other Failure", callbackContext_);
                            break;
                        case DEPENDENCY_NOT_INSTALLED:
                            sendresult("DEPENDENCY_NOT_INSTALLED", callbackContext_);
                            break;
                        case USER_CANCELLED:
                            sendresult("USER_CANCELLED", callbackContext_);
                             break;
                        case UNRECOGNIZED:
                            sendresult("UNRECOGNIZED", callbackContext_);
                            break;
                        default:
                            sendresult("default failure", callbackContext_);
                            break;
                    }
                }
            };
        /////////////////////////////////////////////////////////////////////////
        //Receives state changes.
        //This is just a placeholder for this application
        /////////////////////////////////////////////////////////////////////////
        protected  IDeviceStateChangeReceiver base_IDeviceStateChangeReceiver =
            new IDeviceStateChangeReceiver()
        {
            @Override
            public void onDeviceStateChange(final DeviceState newDeviceState)
            {
            	
            }
        };
        ///////////////////////////////////////////////////////////////////////////
        // Heart Rate is the only value we are concerned about.
        // Other parameter values are not used for this Application
        ///////////////////////////////////////////////////////////////////////////
        public void subscribeToHrEvents()
        {
            hrPcc.subscribeHeartRateDataEvent(new IHeartRateDataReceiver()
            {
				@Override
				public void onNewHeartRateData(
						final long estTimestamp,
						EnumSet<EventFlag> eventFlags,
						final int computedHeartRate, 
						final long heartBeatCount,
						final BigDecimal heartBeatEventTime, 
						final DataState dataState) 
				{
	                // Mark heart rate with asterisk if zero detected
	                final String textHeartRate = String.valueOf(computedHeartRate)
	                    + ((DataState.ZERO_DETECTED.equals(dataState)) ? "*" : "");

	                // Mark heart beat count and heart beat event time with asterisk if initial value
	                final String textHeartBeatCount = String.valueOf(heartBeatCount)
	                    + ((DataState.INITIAL_VALUE.equals(dataState)) ? "*" : "");
	                final String textHeartBeatEventTime = String.valueOf(heartBeatEventTime)
	                    + ((DataState.INITIAL_VALUE.equals(dataState)) ? "*" : "");
	                //Copy results for Cordova Retrieval
	                hrmHeartRate = textHeartRate;
	                hrmHeartBeatCount = textHeartBeatCount;
	                hrmHeartBeatEventTime = textHeartBeatEventTime;

 				}
            	
            });
        };
        ////////////////////////////////////////////////////////////////
        // JavaScript success or error callback invoked as applicable
        ////////////////////////////////////////////////////////////////
        private void sendresult(String result, CallbackContext callbackContext) {
            if (result != null && result.length() > 0) {
                callbackContext.success(result);
            } else {
                callbackContext.error("Empty string received.");
            }
        }
    }