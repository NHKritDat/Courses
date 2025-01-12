using Microsoft.EntityFrameworkCore;
using SCBS.Repositories.Base;
using SCBS.Repositories.Models;

namespace SCBS.Repositories
{
    public class ScheduleRepository : GenericRepository<Schedule>
    {
        public new async Task<List<Schedule>> GetAll()
        {
            var schedules = await _context.Schedules.Include(s => s.User).ToListAsync();
            return schedules;
        }

        public async Task<List<Schedule>> Search(Guid id, Guid userId, DateTime workDate, string status)
        {
            var schedules = await _context.Schedules
                .Where(s => (id == Guid.Empty || s.Id == id)
                            && (userId == Guid.Empty || s.UserId == userId)
                            && (workDate == DateTime.MinValue || s.WorkDate == workDate)
                            && (string.IsNullOrEmpty(status) || s.Status == status))
                .Include(s => s.User)
                .ToListAsync();
            return schedules;
        }
    }
}
