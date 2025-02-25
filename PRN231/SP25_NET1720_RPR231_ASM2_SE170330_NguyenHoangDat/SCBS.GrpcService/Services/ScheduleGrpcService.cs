using System.Text.Json;
using System.Text.Json.Serialization;
using Grpc.Core;
using SCBS.GrpcService.Protos;
using SCBS.Repositories.Models;
using SCBS.Services;

namespace SCBS.GrpcService.Services
{
    public class ScheduleGrpcService : ScheduleProto.ScheduleProtoBase
    {
        private static ScheduleItemList Schedules { get; set; } = new ScheduleItemList();
        private readonly IScheduleService _scheduleService;
        public ScheduleGrpcService(IScheduleService scheduleService)
        {
            if (Schedules.Items.Count == 0 || _scheduleService == null)
            {
                _scheduleService = scheduleService;
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
        public override async Task<ScheduleItemList> GetAllAsync(EmptyRequest request, ServerCallContext context)
        {
            try
            {
                var result = new ScheduleItemList();
                var schedules = await _scheduleService.GetAllAsync();

                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
                };

                var schedulesString = JsonSerializer.Serialize(schedules, opt);
                var items = JsonSerializer.Deserialize<List<ScheduleItem>>(schedulesString, opt);
                result.Items.AddRange(items);

                return await Task.FromResult(result);
            }
            catch (Exception ex)
            {
                return new ScheduleItemList();
            }
        }
        public override async Task<ScheduleItem> GetByIdAsync(ScheduleIdRequest request, ServerCallContext context)
        {
            try
            {
                var schedule = await _scheduleService.GetByIdAsync(Guid.Parse(request.Id));

                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
                };

                var scheduleString = JsonSerializer.Serialize(schedule, opt);
                var result = JsonSerializer.Deserialize<ScheduleItem>(scheduleString, opt);

                return result != null ? await Task.FromResult(result) : new ScheduleItem();
            }
            catch (Exception ex)
            {
                return new ScheduleItem();
            }
        }
        public override async Task<ActionResult> CreateAsync(ScheduleItem request, ServerCallContext context)
        {
            try
            {
                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
                };

                var scheduleString = JsonSerializer.Serialize(request, opt);
                var schedule = JsonSerializer.Deserialize<Schedule>(scheduleString, opt);

                var result = await _scheduleService.Create(schedule);
                return await Task.FromResult(new ActionResult
                {
                    Status = result,
                    Message = result > 0 ? "Create Success" : "Create Failed",
                    Data = new ScheduleItemList()
                    {
                        Items = { request }
                    }
                });
            }
            catch (Exception ex)
            {
                return new ActionResult { Status = 0, Message = "Create Failed" };
            }
        }
        public override async Task<ActionResult> UpdateAsync(ScheduleItem request, ServerCallContext context)
        {
            try
            {
                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
                };

                var scheduleString = JsonSerializer.Serialize(request, opt);
                var schedule = JsonSerializer.Deserialize<Schedule>(scheduleString, opt);

                var result = await _scheduleService.Update(schedule);
                return await Task.FromResult(new ActionResult
                {
                    Status = result,
                    Message = result > 0 ? "Update Success" : "Update Failed",
                    Data = new ScheduleItemList()
                    {
                        Items = { request }
                    }
                });
            }
            catch (Exception ex)
            {
                return new ActionResult { Status = 0, Message = "Update Failed" };
            }
        }
        public override async Task<ActionResult> DeleteAsync(ScheduleIdRequest request, ServerCallContext context)
        {
            try
            {
                var result = await _scheduleService.Delete(Guid.Parse(request.Id));
                return await Task.FromResult(new ActionResult { Status = result ? 1 : 0, Message = result ? "Delete Success" : "Delete Failed" });
            }
            catch (Exception ex)
            {
                return new ActionResult { Status = 0, Message = "Delete Failed" };
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
