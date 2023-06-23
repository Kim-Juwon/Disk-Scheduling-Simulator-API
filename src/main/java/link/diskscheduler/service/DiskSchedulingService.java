package link.diskscheduler.service;

import link.diskscheduler.dto.request.Request;
import link.diskscheduler.dto.response.Response;

public interface DiskSchedulingService {
    Response schedule(Request request);
}
