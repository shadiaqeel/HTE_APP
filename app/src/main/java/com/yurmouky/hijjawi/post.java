package com.yurmouky.hijjawi;

/**
 * Created by dell on 23/09/2017.
 */

public class post {

    private String message;
    private String topic;
    private String url;
    private String key;

    public post() {}

    public post(String message, String topic) {
        this.message = message;
        this.topic = topic;
        url =null;
    }

    public post(String message, String topic, String url) {
        this.message = message;
        this.topic = topic;
        this.url = url;

    }
    public String getMessage() {return message;}
    public String getTopic(){return topic ;}
    public String getUrl(){return url ;}
    public String getKey(){return key ;}
    public void setKey(String key){this.key =key ;}
}
