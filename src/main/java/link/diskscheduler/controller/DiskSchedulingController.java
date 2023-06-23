package link.diskscheduler.controller;

import link.diskscheduler.dto.request.Request;
import link.diskscheduler.dto.response.Response;
import link.diskscheduler.service.DiskSchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class DiskSchedulingController {
    private final DiskSchedulingService schedulingService;

    @PostMapping("/schedule")
    public Response schedule(@RequestBody @Valid Request request) {
        return schedulingService.schedule(request);
    }
}
