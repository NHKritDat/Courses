using System.ServiceModel;
using System.Text.Json;
using System.Text.Json.Serialization;
using SCBS.Services;
using SCBS.SoapApiServices.SoapModels;

namespace SCBS.SoapApiServices.SoapServices
{
    [ServiceContract]
    public interface IScheduleSoapService
    {
        [OperationContract]
        Task<List<Schedule>> GetSchedules();
        [OperationContract]
        Task<Schedule> GetSchedule(string id);
        [OperationContract]
        Task<int> CreateSchedule(Schedule Schedule);
        [OperationContract]
        Task<int> UpdateSchedule(Schedule Schedule);
        [OperationContract]
        Task<bool> DeleteSchedule(string id);
        [OperationContract]
        Task<List<User>> GetUsers();
    }
    public class ScheduleSoapService : IScheduleSoapService
    {
        private readonly IScheduleService _scheduleService;
        private readonly IUserService _userService;
        public ScheduleSoapService(IScheduleService scheduleService, IUserService userService)
        {
            _scheduleService = scheduleService;
            _userService = userService;
        }
        public async Task<int> CreateSchedule(Schedule schedule)
        {
            try
            {
                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull
                };
                var scheduleString = JsonSerializer.Serialize(schedule, opt);
                var item = JsonSerializer.Deserialize<SCBS.Repositories.Models.Schedule>(scheduleString, opt);
                return await _scheduleService.CreateAsync(item);
            }
            catch
            {
                return 0;
            }
        }

        public async Task<bool> DeleteSchedule(string id)
        {
            var guid = Guid.Parse(id);

            return await _scheduleService.RemoveAsync(guid);
        }

        public async Task<Schedule> GetSchedule(string id)
        {
            try
            {
                var guid = Guid.Parse(id);

                var item = await _scheduleService.GetByIdAsync(guid);
                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull
                };
                var itemString = JsonSerializer.Serialize(item, opt);
                var result = JsonSerializer.Deserialize<Schedule>(itemString, opt);

                return result;
            }
            catch
            {
                return new Schedule();
            }
        }

        public async Task<List<Schedule>> GetSchedules()
        {
            try
            {
                var items = await _scheduleService.GetAllAsync();
                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull
                };
                var itemsString = JsonSerializer.Serialize(items, opt);
                var result = JsonSerializer.Deserialize<List<Schedule>>(itemsString, opt);

                return result;
            }
            catch (Exception ex)
            {
                return new List<Schedule>();
            }
        }

        public async Task<List<User>> GetUsers()
        {
            try
            {
                var items = await _userService.GetAllAsync();
                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull
                };
                var itemsString = JsonSerializer.Serialize(items, opt);
                var result = JsonSerializer.Deserialize<List<User>>(itemsString, opt);

                return result;
            }
            catch (Exception ex)
            {
                return new List<User>();
            }
        }

        public async Task<int> UpdateSchedule(Schedule schedule)
        {
            try
            {
                var opt = new JsonSerializerOptions()
                {
                    ReferenceHandler = ReferenceHandler.IgnoreCycles,
                    DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull
                };
                var scheduleString = JsonSerializer.Serialize(schedule, opt);
                var item = JsonSerializer.Deserialize<SCBS.Repositories.Models.Schedule>(scheduleString, opt);

                return await _scheduleService.UpdateAsync(item);
            }
            catch
            {
                return 0;
            }
        }
    }
}
