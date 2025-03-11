using SCBS.Repositories.Models;
using SCBS.Services;

namespace SCBS.GraphQLAPIServicesHotChocolate.GraphQLs
{
    public class Query
    {
        private readonly IScheduleService _scheduleService;
        private readonly IUserService _userService;

        public Query(IScheduleService scheduleService, IUserService userService)
        {
            _scheduleService = scheduleService;
            _userService = userService;
        }

        public async Task<List<Schedule>> GetAllSchedulesAsync()
        {
            try
            {
                var result = await _scheduleService.GetAllAsync();
                return result;
            }
            catch (Exception ex)
            {
                return new List<Schedule>();
            }
        }
        public async Task<Schedule> GetByIdScheduleAsync(Guid id)
        {
            try
            {
                var result = await _scheduleService.GetByIdAsync(id);
                return result;
            }
            catch (Exception ex)
            {
                return new Schedule();
            }
        }
        public async Task<List<Schedule>> SearchSchedules(string? title, string? location, string? status)
        {
            try
            {
                var result = await _scheduleService.Search(title, location, status);
                return result;
            }
            catch (Exception ex)
            {
                return new List<Schedule>();
            }
        }
        public async Task<List<User>> GetAllUsersAsync()
        {
            try
            {
                var result = await _userService.GetAllAsync();
                return result;
            }
            catch (Exception ex)
            {
                return new List<User>();
            }
        }
        public async Task<User> GetByIdUserAsync(Guid id)
        {
            try
            {
                var result = await _userService.GetByIdAsync(id);
                return result;
            }
            catch (Exception ex)
            {
                return new User();
            }
        }
    }
}
