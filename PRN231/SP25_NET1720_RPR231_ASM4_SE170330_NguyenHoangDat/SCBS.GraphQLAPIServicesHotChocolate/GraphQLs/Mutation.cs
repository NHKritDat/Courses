using SCBS.Repositories.Models;
using SCBS.Services;

namespace SCBS.GraphQLAPIServicesHotChocolate.GraphQLs
{
    public class Mutation
    {
        private readonly IScheduleService _scheduleService;

        public Mutation(IScheduleService scheduleService)
        {
            _scheduleService = scheduleService;
        }
        public async Task<int> CreateScheduleAsync(Schedule item)
        {
            try
            {
                var result = await _scheduleService.CreateAsync(item);
                return result;
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
                var result = await _scheduleService.UpdateAsync(item);
                return result;
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
                var result = await _scheduleService.RemoveAsync(id);
                return result;
            }
            catch (Exception ex)
            {
                return false;
            }
        }
    }
}
