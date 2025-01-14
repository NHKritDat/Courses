using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SCBS.Repositories;
using SCBS.Repositories.Models;

namespace SCBS.Services
{
    public interface ISchedule2Service
    {
        Task<List<Schedule>> GetAll();
        Task<Schedule> GetById(Guid id);
        Task<int> Create(Schedule schedule);
        Task<int> Update(Schedule schedule);
        Task<bool> Delete(Guid id);
        Task<List<Schedule>> Search(DateTime workDate, string status);
    }
    public class Schedule2Service : ISchedule2Service
    {
        private readonly ScheduleRepository _scheduleRepository;
        public Schedule2Service()
        {
            _scheduleRepository = new ScheduleRepository();
        }

        public async Task<int> Create(Schedule schedule)
        {
            return await _scheduleRepository.CreateAsync(schedule);
        }

        public async Task<bool> Delete(Guid id)
        {
            var item = await _scheduleRepository.GetByIdAsync(id);
            if (item != null)
            {
                return await _scheduleRepository.RemoveAsync(item);
            }
            return false;
        }

        public Task<List<Schedule>> GetAll()
        {
            return _scheduleRepository.GetAllAsync();
        }

        public Task<Schedule> GetById(Guid id)
        {
            return _scheduleRepository.GetByIdAsync(id);
        }

        public Task<List<Schedule>> Search(DateTime workDate, string status)
        {
            return _scheduleRepository.Search(workDate, status);
        }

        public async Task<int> Update(Schedule schedule)
        {
            return await _scheduleRepository.UpdateAsync(schedule);
        }
    }
}
