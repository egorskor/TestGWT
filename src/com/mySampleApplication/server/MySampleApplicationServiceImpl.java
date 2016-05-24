package com.mySampleApplication.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mySampleApplication.client.MySampleApplicationService;
import java.util.Date;
import java.util.Calendar;

public class MySampleApplicationServiceImpl extends RemoteServiceServlet implements MySampleApplicationService {
    // Implementation of sample interface method
    public String getMessage(String msg) {

        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
    public String getMessage(long msg)
    {
        Calendar SaveData = Calendar.getInstance();
        SaveData.setTimeInMillis(msg);
        SaveData.setTime(new Date());
        Calendar currentData = Calendar.getInstance();
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(currentData.getTime().getTime() - SaveData.getTime().getTime());
        return result.get(Calendar.MONTH)+" month "+(result.get(Calendar.YEAR)-1970) +" year";
    }
}