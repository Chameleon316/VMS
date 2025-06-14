package org.lw.vms.DTOs;

public record UpdateWorkingHourRequest(
        Integer assignmentId,
        double workingHour
) {
}
