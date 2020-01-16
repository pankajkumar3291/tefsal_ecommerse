package com.tefsalkw;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.tefsalkw.activity.DishDashaProductActivity;
import com.tefsalkw.app.TefalApp;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.tefal", appContext.getPackageName());
    }


    @Test
    public void sendProductNotification() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        Intent intent = null;
        intent = new Intent(appContext.getApplicationContext(), DishDashaProductActivity.class);
        intent.putExtra("store_id", "2");
        intent.putExtra("product_id", "319");

        TefalApp.getInstance().setStoreId("2");
        TefalApp.getInstance().setProductId("319");
        TefalApp.getInstance().setWhereFrom("textile");
        TefalApp.getInstance().setFlage("dish");
        TefalApp.getInstance().setFromPush("yes");

        intent.setAction("FromPushNotification");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

       // PendingIntent pendingIntent = PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        appContext.startActivity(intent);






    }

}
