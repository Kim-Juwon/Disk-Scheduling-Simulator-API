package link.diskscheduler.service;

import link.diskscheduler.domain.scheduler.SeekTimeScheduler;
import link.diskscheduler.dto.request.Request;
import link.diskscheduler.dto.response.Response;
import org.springframework.stereotype.Service;

@Service
public class DiskSchedulingServiceImpl implements DiskSchedulingService {
    @Override
    public Response schedule(Request request) {
        SeekTimeScheduler diskScheduler = SeekTimeScheduler.from(request);
        return diskScheduler.schedule();
    }
}
