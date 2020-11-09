package com.qrofeus.requestmanager;

public class RequestClass {
    private final String request_id;
    private final String username;
    private final String request_subject;
    private final String request_details;

    public RequestClass(String request_id, String username, String request_subject, String request_details) {
        this.request_id = request_id;
        this.username = username;
        this.request_subject = request_subject;
        this.request_details = request_details;
    }

    public String getRequest_id() {
        return "Request ID: " + request_id;
    }

    public String getUsername() {
        return "Username: " + username;
    }

    public String getRequest_subject() {
        return "Subject: " + request_subject;
    }

    public String getRequest_details() {
        return "Details: " + request_details;
    }
}
