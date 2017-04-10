package by.achramionok.email;

import by.achramionok.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by Kirill on 10.04.2017.
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private User user;


    public OnRegistrationCompleteEvent(Object source) {
        super(source);
    }

    public OnRegistrationCompleteEvent(
            User user, Locale locale, String appUrl) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
