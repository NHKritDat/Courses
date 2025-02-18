﻿using Microsoft.EntityFrameworkCore;
using SCBS.Repositories.Base;
using SCBS.Repositories.Models;

namespace SCBS.Repositories
{
    public class ScheduleRepository : GenericRepository<Schedule>
    {
        public ScheduleRepository() { }
        public new async Task<List<Schedule>> GetAllAsync()
        {
            var schedules = await _context.Schedules.Include(s => s.User).ToListAsync();
            return schedules;
        }

        public new async Task<Schedule?> GetByIdAsync(Guid id) => await _context.Schedules.Include(s => s.User).FirstOrDefaultAsync(s => s.Id == id);

        public async Task<List<Schedule>> Search(DateTime workDate, string status)
        {
            var schedules = await _context.Schedules
                .Where(s => (workDate == DateTime.MinValue || s.WorkDate == workDate)
                            && (string.IsNullOrEmpty(status) || s.Status == status))
                .Include(s => s.User)
                .ToListAsync();
            return schedules;
        }
    }
}
