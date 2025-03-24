using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Repositories.Base;
using Repositories.Models;

namespace Repositories
{
    public class HandbagRepository :GenericRepository<Handbag>
    {
        public HandbagRepository() { }
        public async Task<List<Handbag>> GetAllAsync() => await _context.Handbags.Include(item => item.Brand).ToListAsync();
        public async Task<Handbag> GetByIdAsync(int id)
        {
            var entity = await _context.Handbags.Include(item => item.Brand).FirstOrDefaultAsync(item => item.HandbagId == id);
            if (entity != null)
            {
                _context.Entry(entity).State = EntityState.Detached;
            }
            return entity;
        }
    }
}
