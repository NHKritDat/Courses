using Grpc.Core;
using SCBS.GrpcService.Protos;

namespace SCBS.GrpcService.Services
{
    public class ScheduleService : ScheduleProto.ScheduleProtoBase
    {
        private static ScheduleItemList Schedules { get; set; } = new ScheduleItemList();
        public ScheduleService()
        {
            if (Schedules.Items.Count == 0)
            {
                Schedules.Items.Add(new ScheduleItem
                {
                    Id = Guid.NewGuid().ToString(),
                    UserId = Guid.NewGuid().ToString(),
                    WorkDate = DateTime.Now.ToString(),
                    Status = "Offline",
                    CreatedAt = DateTime.Now.ToString(),
                    UpdatedAt = DateTime.Now.ToString()
                });
                Schedules.Items.Add(new ScheduleItem
                {
                    Id = Guid.NewGuid().ToString(),
                    UserId = Guid.NewGuid().ToString(),
                    WorkDate = DateTime.Now.ToString(),
                    Status = "Work",
                    CreatedAt = DateTime.Now.ToString(),
                    UpdatedAt = DateTime.Now.ToString()
                });
            }
        }

        public override Task<ScheduleItemList> GetAll(EmptyRequest request, ServerCallContext context)
        {
            return Task.FromResult(Schedules);
        }
        public override Task<ScheduleItem?> GetById(ScheduleIdRequest request, ServerCallContext context)
        {
            var item = Schedules.Items.FirstOrDefault(x => x.Id == request.Id);
            return Task.FromResult(item);
        }
        public override Task<ActionResult> Create(ScheduleItem request, ServerCallContext context)
        {
            if (request != null)
            {
                Schedules.Items.Add(request);
                return Task.FromResult(new ActionResult() { Status = 1, Message = "Create Success", Data = Schedules });
            }
            return Task.FromResult(new ActionResult() { Status = 0, Message = "Create Failed", Data = Schedules });
        }
        public override Task<ActionResult> Update(ScheduleItem request, ServerCallContext context)
        {
            if (request != null)
            {
                var item = Schedules.Items.FirstOrDefault(x => x.Id == request.Id);
                if (item != null)
                {
                    item.UserId = request.UserId;
                    item.WorkDate = request.WorkDate;
                    item.Status = request.Status;
                    item.UpdatedAt = DateTime.Now.ToString();
                    return Task.FromResult(new ActionResult() { Status = 1, Message = "Update Success", Data = Schedules });
                }
            }
            return Task.FromResult(new ActionResult() { Status = 0, Message = "Update Failed", Data = Schedules });
        }
        public override Task<ActionResult> Delete(ScheduleIdRequest request, ServerCallContext context)
        {
            var item = Schedules.Items.FirstOrDefault(x => x.Id == request.Id);
            if (item != null)
            {
                Schedules.Items.Remove(item);
                return Task.FromResult(new ActionResult() { Status = 1, Message = "Delete Success", Data = Schedules });
            }
            return Task.FromResult(new ActionResult() { Status = 0, Message = "Delete Failed", Data = Schedules });
        }
    }
}
