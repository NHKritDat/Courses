using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using SCBS.Repositories.Base;
using SCBS.Repositories.Models;

namespace SCBS.Repositories
{
    public class ScheduleRepository : GenericRepository<Schedule>
    {
        public ScheduleRepository() { }
        public async Task<List<Schedule>> GetAllAsync() => await _context.Schedules.Include(item => item.User).ToListAsync();
        public async Task<Schedule> GetByIdAsync(Guid id)
        {
            var entity = await _context.Schedules.Include(item => item.User).FirstOrDefaultAsync(item => item.Id == id);
            if (entity != null)
            {
                _context.Entry(entity).State = EntityState.Detached;
            }
            return entity;
        }
        public async Task<List<Schedule>> Search(string? title, string? location, string? status) => await _context.Schedules
                .Include(item => item.User)
                .Where(item =>
                (string.IsNullOrEmpty(title) || item.Title.Contains(title))
                            && (string.IsNullOrEmpty(location) || item.Location.Contains(location))
                            && (string.IsNullOrEmpty(status) || item.Status.Contains(status))
                )
                .ToListAsync();
    }
}
