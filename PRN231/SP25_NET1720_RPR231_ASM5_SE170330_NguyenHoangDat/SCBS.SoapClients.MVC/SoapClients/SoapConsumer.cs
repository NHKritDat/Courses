using SCBSWCFServiceReference;

namespace SCBS.SoapClients.MVC.SoapClients
{
    public class SoapConsumer
    {
        private readonly IScheduleSoapService _scheduleSoapService;
        public SoapConsumer()
        {
            _scheduleSoapService = new ScheduleSoapServiceClient(ScheduleSoapServiceClient.EndpointConfiguration.BasicHttpBinding_IScheduleSoapService);
        }
        public async Task<List<Schedule>> GetSchedules()
        {
            try
            {
                var result = await _scheduleSoapService.GetSchedulesAsync();
                return result.ToList();
            }
            catch
            {
                return new List<Schedule>();
            }
        }
        public async Task<Schedule> GetSchedule(string id)
        {
            try
            {
                var result = await _scheduleSoapService.GetScheduleAsync(id);
                return result;
            }
            catch
            {
                return new Schedule();
            }
        }
        public async Task<int> CreateSchedule(Schedule schedule)
        {
            try
            {
                var result = await _scheduleSoapService.CreateScheduleAsync(schedule);
                return result;
            }
            catch
            {
                return 0;
            }
        }
        public async Task<bool> DeleteSchedule(string id)
        {
            try
            {
                var result = await _scheduleSoapService.DeleteScheduleAsync(id);
                return result;
            }
            catch
            {
                return false;
            }
        }
        public async Task<int> UpdateSchedule(Schedule schedule)
        {
            try
            {
                var result = await _scheduleSoapService.UpdateScheduleAsync(schedule);
                return result;
            }
            catch
            {
                return 0;
            }
        }
        public async Task<List<User>> GetUsers()
        {
            try
            {
                var result = await _scheduleSoapService.GetUsersAsync();
                return result.ToList();
            }
            catch
            {
                return new List<User>();
            }
        }
    }
}
