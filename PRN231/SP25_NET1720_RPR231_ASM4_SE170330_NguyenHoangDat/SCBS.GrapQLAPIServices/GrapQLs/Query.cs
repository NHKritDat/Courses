using System.Threading.Tasks;
using SCBS.Repositories.Models;
using SCBS.Services;

namespace SCBS.GrapQLAPIServices.GrapQLs
{
    public class Query
    {
        private readonly IScheduleService _scheduleService;
        public Query(IScheduleService scheduleService) => _scheduleService = scheduleService;

        public async Task<List<Schedule>> GetSchedules()
        {
            try
            {
                return await _scheduleService.GetAllAsync();
            }
            catch (Exception ex)
            {
                return new List<Schedule>();
            }
        }
        public async Task<Schedule> GetScheduleById(Guid id)
        {
            try
            {
                return await _scheduleService.GetByIdAsync(id);
            }
            catch (Exception ex)
            {
                return new Schedule();
            }
        }
        public async Task<List<Schedule>> Search(string? title, string? location, string? status)
        {
            try
            {
                return await _scheduleService.Search(title, location, status);
            }
            catch (Exception ex)
            {
                return new List<Schedule>();
            }
        }
    }
}
