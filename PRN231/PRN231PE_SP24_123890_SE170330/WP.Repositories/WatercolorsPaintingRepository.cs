using Microsoft.EntityFrameworkCore;
using WP.Repositories.Base;
using WP.Repositories.Models;

namespace WP.Repositories
{
    public class WatercolorsPaintingRepository : GenericRepository<WatercolorsPainting>
    {
        public WatercolorsPaintingRepository() { }
        public async Task<List<WatercolorsPainting>> GetAllAsync() => await _context.WatercolorsPaintings.Include(item => item.Style).ToListAsync();
        public async Task<WatercolorsPainting> GetByIdAsync(string id)
        {
            var entity = await _context.WatercolorsPaintings.Include(item => item.Style).FirstOrDefaultAsync(item => item.PaintingId == id);
            if (entity != null)
            {
                _context.Entry(entity).State = EntityState.Detached;
            }
            return entity;
        }
    }
}
