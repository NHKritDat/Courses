using SCBS.Repositories;
using SCBS.Repositories.Models;

namespace SCBS.Services
{
    public interface IScheduleService
    {
        Task<List<Schedule>> GetAllAsync();
        Task<Schedule?> GetByIdAsync(Guid id);
        Task<int> Create(Schedule schedule);
        Task<int> Update(Schedule schedule);
        Task<bool> Delete(Guid id);
        Task<List<Schedule>> Search(DateTime workDate, string status);
    }
    public class ScheduleService : IScheduleService
    {
        private readonly ScheduleRepository _scheduleRepository;
        public ScheduleService() => _scheduleRepository = new ScheduleRepository();

        public async Task<int> Create(Schedule schedule) => await _scheduleRepository.CreateAsync(schedule);

        public async Task<bool> Delete(Guid id)
        {
            var item = await _scheduleRepository.GetByIdAsync(id);
            if (item != null)
            {
                return await _scheduleRepository.RemoveAsync(item);
            }
            return false;
        }

        public async Task<List<Schedule>> GetAllAsync() => await _scheduleRepository.GetAllAsync();

        public async Task<Schedule?> GetByIdAsync(Guid id) => await _scheduleRepository.GetByIdAsync(id);

        public async Task<List<Schedule>> Search(DateTime workDate, string status) => await _scheduleRepository.Search(workDate, status);

        public async Task<int> Update(Schedule schedule) => await _scheduleRepository.UpdateAsync(schedule);
    }
}
