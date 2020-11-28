package com.qrofeus.requestmanager;

public class RequestClass {
    private final String request_id;
    private final String username;
    private final String request_subject;
    private final String request_details;
    private final String email;
    private final String phone;
    private final String priority;

    public RequestClass(String request_id, String username, String request_subject, String request_details, String email, String phone, String priority) {
        this.request_id = request_id;
        this.username = username;
        this.request_subject = request_subject;
        this.request_details = request_details;
        this.email = email;
        this.phone = phone;
        this.priority = priority;
    }

    public RequestClass(String request_id, String username, String request_subject, String request_details, String email, String phone) {
        this.request_id = request_id;
        this.username = username;
        this.request_subject = request_subject;
        this.request_details = request_details;
        this.email = email;
        this.phone = phone;
        this.priority = "Low";
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

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPriority() {
        return priority;
    }
}
