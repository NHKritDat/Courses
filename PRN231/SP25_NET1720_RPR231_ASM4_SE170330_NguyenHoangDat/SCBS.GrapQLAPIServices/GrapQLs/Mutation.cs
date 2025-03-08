using SCBS.Repositories.Models;
using SCBS.Services;

namespace SCBS.GrapQLAPIServices.GrapQLs
{
    public class Mutation
    {
        private readonly IScheduleService _scheduleService;
        public Mutation(IScheduleService scheduleService) => _scheduleService = scheduleService;
        public async Task<int> CreateScheduleAsync(Schedule item)
        {
            try
            {
                return await _scheduleService.CreateAsync(item);
            }
            catch (Exception ex)
            {
                return 0;
            }
        }
        public async Task<int> UpdateScheduleAsync(Schedule item)
        {
            try
            {
                return await _scheduleService.UpdateAsync(item);
            }
            catch (Exception ex)
            {
                return 0;
            }
        }
        public async Task<bool> RemoveScheduleAsync(Guid id)
        {
            try
            {
                return await _scheduleService.RemoveAsync(id);
            }
            catch (Exception ex)
            {
                return false;

            }
        }
    }
}
