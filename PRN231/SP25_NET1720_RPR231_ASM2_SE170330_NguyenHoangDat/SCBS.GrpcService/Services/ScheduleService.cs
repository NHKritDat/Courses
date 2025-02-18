using Grpc.Core;

namespace SCBS.GrpcService.Services
{
    public class ScheduleService : ScheduleProto.ScheduleProtoBase
    {
        private readonly ILogger<ScheduleService> _logger;
        public ScheduleService(ILogger<ScheduleService> logger) => _logger = logger;
        public override Task<GetAllReply> GetAll(GetAllRequest request, ServerCallContext context)
        {
            var reply = new GetAllReply();
            ScheduleItem item1 = new ScheduleItem
            {
                Id = Guid.NewGuid().ToString(),
                UserId = Guid.NewGuid().ToString(),
                WorkDate = DateTime.Now.ToString(),
                Status = "Offline",
                CreatedAt = DateTime.Now.ToString(),
                UpdatedAt = DateTime.Now.ToString()
            };
            reply.Items.Add(item1);
            ScheduleItem item2 = new ScheduleItem
            {
                Id = Guid.NewGuid().ToString(),
                UserId = Guid.NewGuid().ToString(),
                WorkDate = DateTime.Now.ToString(),
                Status = "Work",
                CreatedAt = DateTime.Now.ToString(),
                UpdatedAt = DateTime.Now.ToString()
            };
            reply.Items.Add(item2);
            return Task.FromResult(reply);
        }
    }
}
