using System.Text.Json;
using System.Text.Json.Serialization;
using Grpc.Core;
using SCBS.Repositories.Models;
using SCBS.Services;
using SCBS.GrpcServices.Protos;

namespace SCBS.GrpcServices.Services
{
    public class ScheduleGrpcService : ScheduleGrpc.ScheduleGrpcBase
    {
        private readonly IScheduleService _scheduleService;
        public ScheduleGrpcService(IScheduleService scheduleService)
        {
            _scheduleService = scheduleService;
        }
        public override async Task<Item> GetById(IdRequest request, ServerCallContext context)
        {
            var schedule = await _scheduleService.GetByIdAsync(Guid.Parse(request.Id));

            var opt = new JsonSerializerOptions()
            {
                ReferenceHandler = ReferenceHandler.IgnoreCycles,
                DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
            };

            var itemString = JsonSerializer.Serialize(schedule, opt);
            var result = JsonSerializer.Deserialize<Item>(itemString, opt);
            return result != null ? await Task.FromResult(result) : new Item();
        }
        public override async Task<ItemList> GetAll(EmptyRequest request, ServerCallContext context)
        {
            var schedules = await _scheduleService.GetAllAsync();
            var response = new ItemList();

            var opt = new JsonSerializerOptions()
            {
                ReferenceHandler = ReferenceHandler.IgnoreCycles,
                DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
            };

            var itemString = JsonSerializer.Serialize(schedules, opt);
            var items = JsonSerializer.Deserialize<List<Item>>(itemString, opt);
            response.Items.AddRange(items);

            return await Task.FromResult(response);
        }
        public override async Task<ActionResult> Create(Item request, ServerCallContext context)
        {
            try
            {
                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
                };

                var itemString = JsonSerializer.Serialize(request, opt);
                var item = JsonSerializer.Deserialize<Schedule>(itemString, opt);

                var result = await _scheduleService.CreateAsync(item);
                return await Task.FromResult(new ActionResult
                {
                    Status = result,
                    Message = result > 0 ? "Create Success" : "Create Failed",
                    Data = new ItemList()
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
        public override async Task<ActionResult> Update(Item request, ServerCallContext context)
        {
            try
            {
                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
                };

                var itemString = JsonSerializer.Serialize(request, opt);
                var item = JsonSerializer.Deserialize<Schedule>(itemString, opt);

                var result = await _scheduleService.UpdateAsync(item);
                return await Task.FromResult(new ActionResult
                {
                    Status = result,
                    Message = result > 0 ? "Update Success" : "Update Failed",
                    Data = new ItemList()
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
        public override async Task<ActionResult> Remove(IdRequest request, ServerCallContext context)
        {
            try
            {
                var result = await _scheduleService.RemoveAsync(Guid.Parse(request.Id));
                return await Task.FromResult(new ActionResult
                {
                    Status = result ? 1 : 0,
                    Message = result ? "Remove Success" : "Remove Failed"
                });
            }
            catch (Exception ex)
            {
                return new ActionResult { Status = 0, Message = "Remove Failed" };
            }
        }
    }
}
