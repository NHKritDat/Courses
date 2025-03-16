using SCBS.Repositories;
using SCBS.Repositories.Models;

namespace SCBS.Services
{
    public interface IScheduleService
    {
        Task<int> CreateAsync(Schedule item);
        Task<List<Schedule>> GetAllAsync();
        Task<Schedule> GetByIdAsync(Guid id);
        Task<bool> RemoveAsync(Guid id);
        Task<List<Schedule>> Search(string? title, string? location, string? status);
        Task<int> UpdateAsync(Schedule item);
    }
    public class ScheduleService : IScheduleService
    {
        private readonly ScheduleRepository _scheduleRepository;
        public ScheduleService() => _scheduleRepository = new ScheduleRepository();
        public async Task<int> CreateAsync(Schedule item) => await _scheduleRepository.CreateAsync(item);

        public async Task<bool> RemoveAsync(Guid id)
        {
            var item = await _scheduleRepository.GetByIdAsync(id);
            if (item != null)
            {
                return await _scheduleRepository.RemoveAsync(item);
            }
            return false;
        }

        public async Task<List<Schedule>> GetAllAsync() => await _scheduleRepository.GetAllAsync();

        public async Task<Schedule> GetByIdAsync(Guid id) => await _scheduleRepository.GetByIdAsync(id);

        public async Task<List<Schedule>> Search(string? title, string? location, string? status) => await _scheduleRepository.Search(title, location, status);

        public async Task<int> UpdateAsync(Schedule item) => await _scheduleRepository.UpdateAsync(item);
    }

}
