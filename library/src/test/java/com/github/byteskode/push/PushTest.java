package com.github.byteskode.push;

import android.content.Context;
import com.github.byteskode.push.api.DeviceApi;
import com.google.firebase.FirebaseApp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author lally elias
 * @email lallyelias87@gmail.com, lally.elias@byteskode.com
 */

@Config(sdk = 23)
@RunWith(RobolectricTestRunner.class)
public class PushTest {
    private Context context;
    private String registrationToken = "XtSVEtyw023iTy";
    private String apiAuthorizationToken = "TtSVEtyw023iTy";
    private String apiBaseUrl = "https://www.example.com/";

    @Before
    public void setup() {
        context = ShadowApplication.getInstance().getApplicationContext();
        Push.initialize(context, apiBaseUrl, apiAuthorizationToken);
        FirebaseApp.initializeApp(context);
    }


    @Test
    public void shouldBeAbleToGetPushInstance() {

        Push push = Push.getInstance();

        assertThat(push, is(not(equalTo(null))));
        assertThat(push.getApiBaseUrl(), is(not(equalTo(null))));
        assertThat(push.getApiAuthorizationToken(), is(not(equalTo(null))));
        assertThat(push.getDeviceApi(), is(not(equalTo(null))));
    }

    @Test
    public void shouldBeAbleToGetDeviceUUID() {
        Push push = Push.getInstance();

        String uuid1 = push.getUUID();
        String uuid2 = push.getUUID();

        assertThat(uuid1, is(not(equalTo(null))));
        assertThat(uuid2, is(not(equalTo(null))));
        assertThat(uuid1, is(equalTo(uuid2)));
    }

    @Test
    public void shouldBeAbleToInitializeDeviceApi() {

        Push push = Push.getInstance();

        DeviceApi deviceApi = push.getDeviceApi();
        assertThat(deviceApi, is(not(equalTo(null))));
    }


    @Test
    public void shouldBeAbleToSetApiBaseUrl() {

        Push push = Push.getInstance();
        String apiBaseUrl = push.setApiBaseUrl(this.apiBaseUrl);

        assertThat(apiBaseUrl, equalTo(this.apiBaseUrl));
    }

    @Test
    public void shouldBeAbleToGetApiBaseUrl() {

        Push push = Push.getInstance();
        push.setApiBaseUrl(this.apiBaseUrl);

        String apiBaseUrl = push.getApiBaseUrl();

        assertThat(apiBaseUrl, equalTo(this.apiBaseUrl));
    }

    @Test
    public void shouldBeAbleToSetApiAuthorizationToken() {

        Push push = Push.getInstance();
        String authorizationToken = push.setApiAuthorizationToken(this.apiAuthorizationToken);

        assertThat(authorizationToken, equalTo(this.apiAuthorizationToken));
    }

    @Test
    public void shouldBeAbleToGetApiAuthorizationToken() {

        Push push = Push.getInstance();
        push.setApiAuthorizationToken(this.apiAuthorizationToken);

        String apiAuthorizationToken = push.getApiAuthorizationToken();

        assertThat(apiAuthorizationToken, equalTo(this.apiAuthorizationToken));
    }

    @Test
    public void shouldBeAbleToGetInstanceId() {
    }


    @Test
    public void shouldBeAbleToSubscribeToPushTopic() {
        String topic = "MAIZE";
        Set<String> topics = Push.getInstance().subscribe(topic);
        assertThat(topics.contains(topic), equalTo(true));
    }

    @Test
    public void shouldBeAbleToUnSubscribeToPushTopic() {
        String topic = "MAIZE";

        Push push = Push.getInstance();
        push.subscribe(topic);

        Set<String> topics = push.unsubscribe(topic);
        assertThat(topics.contains(topic), equalTo(false));
    }

    @Test
    public void shouldBeAbleToObtainSubscribedPushTopics() {
        String topic = "MAIZE";

        Push push = Push.getInstance();
        push.subscribe(topic);

        Set<String> topics = push.getTopics();
        assertThat(topics.contains(topic), equalTo(true));
    }

    @Test
    public void shouldBeAbleToSaveDeviceRegistrationToken() {
        Push push = Push.getInstance();
        String token = push.setRegistrationToken(registrationToken);
        assertThat(token, equalTo(registrationToken));
    }

    @Test
    public void shouldBeAbleToGetDeviceRegistrationToken() {
        Push push = Push.getInstance();
        push.setRegistrationToken(registrationToken);

        String token = push.getRegistrationToken();
        assertThat(token, equalTo(registrationToken));
    }

    @Test
    public void shouldBeAbleToCheckIfDeviceHasInternetConnection() {
        Push push = Push.getInstance();

        boolean connected = push.isConnected();

        assertThat(connected, equalTo(true));
    }

    @Test
    public void shouldBeAbleToSaveDeviceExtra() {
        Push push = Push.getInstance();
        Map<String, String> extras = push.putExtra("phone", "11111111111");
        assertThat(extras.get("phone"), equalTo("11111111111"));
    }

    @Test
    public void shouldBeAbleToGetDeviceExtras() {
        Push push = Push.getInstance();
        push.putExtra("phone", "11111111111");
        Map<String, String> extras = push.getExtras();
        assertThat(extras.get("phone"), equalTo("11111111111"));
    }

    @After
    public void cleanup() {
        Push.getInstance().clear();
        context = null;
    }
}
