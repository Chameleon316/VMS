package org.lw.vms.entity;

import org.lw.vms.enums.FeedbackRating;

import java.time.LocalDateTime;

public class Feedback {
    private int feedbackId;
    private FeedbackRating rating;
    private String comment;
    private LocalDateTime feedbackTime;
}
